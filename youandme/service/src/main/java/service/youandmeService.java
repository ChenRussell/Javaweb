package service;


import entity.SocialDynamics;
import entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public interface youandmeService {

    /**
     * 注册
     * @param username
     * @param password
     * @param email
     * @return
     */
    int register(String username,String password,String email);

    /**
     * 登录，可能是用户名登录也可能是邮箱登录
     * @param stringToLogin
     * @param password
     * @return
     */
    User login(String stringToLogin,String password);

    /**
     * 文件上传 //TODO
     * @param request
     * @param userId
     */
    void fileUpload(HttpServletRequest request,int userId);//文件上传

    /**
     * 用户上传动态，默认只能是文字+一张图片或是文字+一段视频
     * @param request
     * @param userId
     */
    void postDynamics(HttpServletRequest request,int userId);

    /**
     * 刷新进入主页时查看全部动态
     * @return
     */
    List<SocialDynamics> showDynamics();

    /**
     * 返回当前页面动态最大主键值，如果没有就是0
     * @return
     */
    int curMaxDynamicsId();

    /**
     * 从pos开始局部刷新新的动态
     * @param pos
     * @return
     */
    List<SocialDynamics> showNewDynamics(int pos);

    boolean changeHeadImg(HttpServletRequest request,int userId);

    /**
     * 用户更改个人信息，成功返回true，失败返回false
     * @param userId
     * @param username
     * @param email
     * @param address
     * @param description
     * @return
     */
    boolean changePersonalInfo(int userId,String username,String email,String address,String description);

    User queryUserById(int userId);
}
