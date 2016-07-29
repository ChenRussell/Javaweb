package dao;

import entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2016/7/28.
 */
public interface UserDao {

    /**
     * �����û�Ψһ��ʶ����ͷ��
     * @param userId
     * @return
     */
    int updateUserHeadImg( @Param("userId") int userId,
                           @Param("headImg") String headImg);

    /**
     * �����û�id�����û�ȫ����Ϣ�����ڸ���session
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
