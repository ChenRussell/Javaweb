import dao.DynamicsDao;
import entity.socialDynamics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/7/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class DynamicsDaoTest {

    @Autowired
    private DynamicsDao dynamicsDao;
    @Test
    public void testInsertDynamics() throws Exception {
        int userId = 116;
        String dynamicsText = "½ñÌì´òÇòÀ²£¡¹þ¹þ¹þ¹þ";
        String dynamicsFile = "D:\\file";
        int insertDynamicsResult = dynamicsDao.insertDynamics(userId,dynamicsText,dynamicsFile);
        System.out.println(insertDynamicsResult);
    }

    @Test
    public void testSelectAllDynamics() throws Exception {

        List<socialDynamics> list = dynamicsDao.selectAllDynamics();
        for(socialDynamics socialDynamics:list){
            System.out.println(socialDynamics.getDynamicsText());
        }
    }

    @Test
    public void testSelectMaxDynamicsId() throws Exception {
        System.out.println(dynamicsDao.selectMaxDynamicsId());
    }

    @Test
    public void testSelectDynamicsFromPos() throws Exception {
        List<socialDynamics> list = dynamicsDao.selectDynamicsFromPos(15);
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}