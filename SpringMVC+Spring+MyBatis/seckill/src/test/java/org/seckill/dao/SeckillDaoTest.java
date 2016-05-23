package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/5/23.
 */
/*
*配置spring与junit的整合，junit启动时加载springIOC容器
* */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉spring junit配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入dao实现类依赖
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void testReduceNumber() throws Exception {

        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(6,killTime);
        System.out.println("updateCount:"+updateCount);
    }

    @Test
    public void testQueryById() throws Exception {

        /*
        数据库中有TimeStamp
        jdbc.url加上zeroDateTimeBehavior=convertToNull
        * */
        int id = 6;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception {
        /*
        java没有保存形参的记录   limit #{offset},#{limit} ->queryAll(arg0,arg1)
        List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
        */
        List<Seckill> seckillList = seckillDao.queryAll(0,100);
        for (Seckill seckill:seckillList){
            System.out.println(seckill);
        }
    }
}