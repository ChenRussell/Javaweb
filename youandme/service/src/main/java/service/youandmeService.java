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
     * ע��
     * @param username
     * @param password
     * @param email
     * @return
     */
    int register(String username,String password,String email);

    /**
     * ��¼���������û�����¼Ҳ�����������¼
     * @param stringToLogin
     * @param password
     * @return
     */
    User login(String stringToLogin,String password);

    /**
     * �ļ��ϴ� //TODO
     * @param request
     * @param userId
     */
    void fileUpload(HttpServletRequest request,int userId);//�ļ��ϴ�

    /**
     * �û��ϴ���̬��Ĭ��ֻ��������+һ��ͼƬ��������+һ����Ƶ
     * @param request
     * @param userId
     */
    void postDynamics(HttpServletRequest request,int userId);

    /**
     * ˢ�½�����ҳʱ�鿴ȫ����̬
     * @return
     */
    List<SocialDynamics> showDynamics();

    /**
     * ���ص�ǰҳ�涯̬�������ֵ�����û�о���0
     * @return
     */
    int curMaxDynamicsId();

    /**
     * ��pos��ʼ�ֲ�ˢ���µĶ�̬
     * @param pos
     * @return
     */
    List<SocialDynamics> showNewDynamics(int pos);

    boolean changeHeadImg(HttpServletRequest request,int userId);

    /**
     * �û����ĸ�����Ϣ���ɹ�����true��ʧ�ܷ���false
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
