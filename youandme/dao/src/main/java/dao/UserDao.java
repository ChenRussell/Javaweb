package dao;

import entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2016/7/28.
 */
public interface UserDao {

    /**
     * 根据用户唯一标识更新头像
     * @param userId
     * @return
     */
    int updateUserHeadImg( @Param("userId") int userId,
                           @Param("headImg") String headImg);

    /**
     * 根据用户id查找用户全部信息，用于更新session
     * @param userId
     * @return
     */
    User selectUserById(@Param("userId") int userId);


    int updateUser(@Param("userId") int userId,
                   @Param("username") String username,
                   @Param("email") String email,
                   @Param("address") String address,
                   @Param("description") String description);
}
