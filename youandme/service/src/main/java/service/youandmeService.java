package service;


import entity.CommentInfo;
import entity.ReplyInfo;
import entity.SocialDynamics;
import entity.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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

    /**
     * �û����ޣ����ص��޺�ǰ��̬�����޵�����
     * @param dynamicsId
     * @param userId
     * @return
     */
    String clickLikeDynamics(int dynamicsId,int userId);

    List<Integer> showWhichLike(int userId);

    SocialDynamics showDetailDynamicsById(int dynamicsId);

    List<User> showLikeUserOfDynamics(int dynamicsId);

    /**
     * �û����ض���̬��������
     * @param dynamicsId
     * @param sendId
     * @param commentText
     * @return
     */
    CommentInfo sendComment(int dynamicsId,int sendId,String commentText);

    /**
     * ���ݶ�̬id���Ҹö�̬��ȫ������
     * @param dynamicsId
     * @return
     */
    List<CommentInfo> showCommentById(int dynamicsId);

    /**
     * ��������id����������Ϣ
     * @param commentId
     * @return
     */
    CommentInfo showComment(int commentId);

    /**
     * �û�����Զ�̬���۵Ļظ�������ReplyInfo����
     * @param commentId
     * @param sendId
     * @param replyText
     */
    ReplyInfo sendReply(int commentId,int sendId,String replyText);

    /**
     * �������۵�ȫ���ظ�
     * @param commentId
     * @return
     */
    List<ReplyInfo> showAllReplyByCommentId(int commentId);

    /**
     * �û������۵Ļظ����лظ�
     * @param replyId
     * @param sendId
     * @param replyText
     * @return
     */
    ReplyInfo sendReplyOfReply(int replyId,int sendId,String replyText);

    ReplyInfo showReplyInfo(int replyId);
}
