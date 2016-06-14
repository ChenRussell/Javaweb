import dtoPackage.Exposer;
import dtoPackage.SeckillExecution;
import entityPackage.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import servicePackage.SeckillService;

import java.util.List;


/**
 * Created by Administrator on 2016/6/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-Redisdao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void testGetById() throws Exception {
        Seckill seckill = seckillService.getById(5);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        int id = 5;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);

        //exposer=Exposer{exposed=true, md5='791ced66f7f47df119f2facd98f330a1', seckillId=5, now=0, start=0, end=0}
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        int id = 5;
        String phone = "11051189778";
        String md5 = "791ced66f7f47df119f2facd98f330a1";
        SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
        logger.info("execution={}",execution);


        //控制台有transaction控制，说明事务启用成功

    }
}