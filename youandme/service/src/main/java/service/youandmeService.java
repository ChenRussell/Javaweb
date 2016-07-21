package service;

import entity.User;

/**
 * Created by Administrator on 2016/7/20.
 */
public interface youandmeService {

    int register(String username,String password,String email);//注册

    boolean login(String stringToLogin,String password);//登录,可能是用户名登录也可能是邮箱登录
}
