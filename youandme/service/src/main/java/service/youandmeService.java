package service;

import entity.User;

/**
 * Created by Administrator on 2016/7/20.
 */
public interface youandmeService {

    int register(String username,String password,String email);//ע��

    boolean login(String stringToLogin,String password);//��¼,�������û�����¼Ҳ�����������¼
}
