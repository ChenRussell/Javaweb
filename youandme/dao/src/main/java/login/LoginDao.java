package login;

import entity.User;
import org.apache.ibatis.annotations.Param;


/**
 * Created by Administrator on 2016/7/20.
 */
public interface LoginDao {
    /**
     * �����û�����ע���ʺ�
     * @param username
     * @param password
     * @return
     */
    int insertUser(@Param("username") String username,@Param("password") String password,@Param("email") String email);


    /**
     * ��¼������ȫ��User�Ƿ�����Ӧ�û���������
     * ��user_idת��ΪString����Ϊ�գ����ʾû���û���/���䲻���ڻ��������
     * @param stringToLogin
     * @param password
     * @return
     */
    String selectPyFromAllUser(@Param("stringToLogin") String stringToLogin,@Param("password") String password);
}
