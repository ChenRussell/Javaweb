package service.impl;

import login.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.youandmeService;

/**
 * Created by Administrator on 2016/7/20.
 */
//@Service注解：将写好的youandmeService注入到SpringIOC容器中
@Service
public class youandmeServiceImpl implements youandmeService {

    //自动装配在SpringIOC容器中的dao模块接口,不需要手动新建相应实例
    @Autowired
    private LoginDao loginDao;

    public int register(String username, String password,String email) {
        int result = loginDao.insertUser(username,password,email);
        return result;
    }

    public boolean login(String stringToLogin, String password) {
        String queryAllResult = loginDao.selectPyFromAllUser(stringToLogin,password);
        if(queryAllResult==null){
            return false;
        }
        else{
            return true;
        }
    }
}
