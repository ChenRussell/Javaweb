import entity.User;
import login.LoginDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/7/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class LoginDaoTest {

    @Autowired
    private LoginDao loginDao;
    @Test
    public void testInsertUser() throws Exception {
        String username = "Hill";
        String password = "123";
        String email = "1151650717@qq.com";
        int insertResult = loginDao.insertUser(username,password,email);
        System.out.println(insertResult);
    }

    @Test
    public void testQueryAllUser() throws Exception {
        String stringToLogin = "1151650717@qq.com";
        String password = "123";
        String testId = loginDao.selectPyFromAllUser(stringToLogin,password);
        System.out.println(testId);
    }
}