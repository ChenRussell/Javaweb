package login;

import entity.User;
import org.apache.ibatis.annotations.Param;


/**
 * Created by Administrator on 2016/7/20.
 */
public interface LoginDao {
    /**
     * 插入用户，即注册帐号
     * @param username
     * @param password
     * @return
     */
    int insertUser(@Param("username") String username,@Param("password") String password,@Param("email") String email);


    /**
     * 登录，查找全部User是否有相应用户名或邮箱
     * 将user_id转化为String。若为空，则表示没有用户名/邮箱不存在或密码错误
     * @param stringToLogin
     * @param password
     * @return
     */
    String selectPyFromAllUser(@Param("stringToLogin") String stringToLogin,@Param("password") String password);
}
