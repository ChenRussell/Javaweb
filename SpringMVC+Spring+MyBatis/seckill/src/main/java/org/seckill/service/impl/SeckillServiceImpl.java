package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnums;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SeckillDao seckillDao;
    private SuccessKilledDao successKilledDao;

    //md5盐值字符串，用于混淆MD5
    private final String slat = "asd$%^$156120#BbK0-%^%*&!&*fef~{}@##VJ*{))&@@@@#";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(int seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(int seckillId) {

        Seckill seckill = seckillDao.queryById(seckillId);

        if(seckill==null){
            return new Exposer(false,seckillId);
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        if(nowTime.getTime()<startTime.getTime()
                || nowTime.getTime()>endTime.getTime()){
            return new Exposer(startTime.getTime(),endTime.getTime(),nowTime.getTime(),false,seckillId);
        }
        //转化特定字符串的过程，不可逆
        String md5 = getMD5(seckillId);//
        return new Exposer(md5,true,seckillId);

    }

    //生成并返回一个md5
    private  String getMD5(int seckillId){

        String base = seckillId+"/"+slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public SeckillExecution executeSeckill(int seckillId, String userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {

        if(md5==null||md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑; 减库存+记录购买明细
        Date nowTime = new Date();
        try {
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            if(updateCount<=0){
                throw new SeckillCloseException("seckill is closed");
            }
            else{
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
                //唯一：seckillId,userPhone（联合主键）
                if(insertCount<=0){
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated");
                }
                else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnums.SUCCESS,successKilled);  //枚举
                }
            }
        }
        catch (SeckillCloseException e1){
            throw e1;
        }
        catch (RepeatKillException e2){
            throw e2;
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            //所有编译期异常转化为运行期异常 ？   以便rollback回滚
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }
}
