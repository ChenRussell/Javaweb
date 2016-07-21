package service.impl;

import login.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.youandmeService;

/**
 * Created by Administrator on 2016/7/20.
 */
//@Serviceע�⣺��д�õ�youandmeServiceע�뵽SpringIOC������
@Service
public class youandmeServiceImpl implements youandmeService {

    //�Զ�װ����SpringIOC�����е�daoģ��ӿ�,����Ҫ�ֶ��½���Ӧʵ��
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
