package service.impl;

import dao.CommentDao;
import dao.DynamicsDao;
import dao.UserDao;
import entity.CommentInfo;
import entity.ReplyInfo;
import entity.SocialDynamics;
import entity.User;
import dao.LoginDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.youandmeService;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
//@Serviceע�⣺��д�õ�youandmeServiceע�뵽SpringIOC������
@Service
public class youandmeServiceImpl implements youandmeService {

    //�Զ�װ����SpringIOC�����е�daoģ��ӿ�,����Ҫ�ֶ��½���Ӧʵ��
    @Autowired
    private LoginDao loginDao;

    @Autowired
    private DynamicsDao dynamicsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    public int register(String username, String password,String email) {

        /*ע��ʱ��Ϊ��������ʱ�䣡*/
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(date);

        int result = loginDao.insertUser(username,password,email,dateString);
        return result;
    }

    public User login(String stringToLogin, String password) {

        User user = loginDao.selectUserFromAllUser(stringToLogin, password);
        return user;
        //����¼���ɹ�������Ϊnull
        //����¼�ɹ����������ݿ���ȫ���ֶ�
    }


    /**
     * �ļ��ϴ�����Http�����û�Ψһ��ʶΪ����
     * @param request
     * @param userId
     */
    public void fileUpload(HttpServletRequest request,int userId) {

        try{
            //����һ��DiskFileItemFactory����
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //����һ���ļ��ϴ�������
            ServletFileUpload upload = new ServletFileUpload(factory);
            //����ϴ��ļ�������������
            upload.setHeaderEncoding("UTF-8");
            //�ж��ύ�����������Ƿ����ϴ���������
            if(!ServletFileUpload.isMultipartContent(request)){
                //���մ�ͳ��ʽ��ȡ����
                return;
            }
            /*��Http��Servlet����������ύ�����ݽ���ΪList
             *ÿһ��FileItem��Ӧһ��Form����������,�ɶ�ѡ����
             */
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //���fileitem�з�װ������ͨ�����������
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //�����ͨ����������ݵ�������������
                    String value = item.getString("UTF-8");//TODO
                    System.out.println(name + "=" + value);
                }
                else{
                    //���fileitem�з�װ�����ϴ��ļ�
                    //�õ��ϴ����ļ����ƣ�
                    String filename = item.getName();//TODO
                    System.out.println(filename);
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
                    //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //�ļ��Ѿ����ύ��Servlet�У���ȡ�ϴ��ļ���������
                    InputStream in = item.getInputStream();

                    //����������·��
                    String savePath = "C:\\wamp\\www\\J2ee fileUpload\\Social dynamics\\"+userId;
                    File file = new File(savePath);
                    //�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
                    if (!file.exists() && !file.isDirectory()) {
                        System.out.println(savePath + "Ŀ¼�����ڣ���Ҫ����");
                        //����Ŀ¼
                        file.mkdir();
                    }

                    //����һ���ļ������
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    //����һ��������
                    byte buffer[] = new byte[1024];
                    //�ж��������е������Ƿ��Ѿ�����ı�ʶ
                    int len = 0;
                    //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
                    while((len=in.read(buffer))>0){
                        //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
                        out.write(buffer, 0, len);
                    }
                    //�ر�������
                    in.close();
                    //�ر������
                    out.close();
                    //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
                    item.delete();
                    System.out.println("�ϴ��ļ��ɹ�");
                }
            }
        }catch (Exception e) {
            System.out.println("�ϴ��ļ�ʧ��");
            e.printStackTrace();
        }
    }

    public void postDynamics(HttpServletRequest request, int userId) {

        String dynamicsText = "";
        String dynamicsFile = "";
        try{
            //����һ��DiskFileItemFactory����
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //����һ���ļ��ϴ�������
            ServletFileUpload upload = new ServletFileUpload(factory);
            //����ϴ��ļ�������������
            upload.setHeaderEncoding("UTF-8");
            //�ж��ύ�����������Ƿ����ϴ���������
            if(!ServletFileUpload.isMultipartContent(request)){
                //���մ�ͳ��ʽ��ȡ����
                return;
            }
            /*��Http��Servlet����������ύ�����ݽ���ΪList
             *ÿһ��FileItem��Ӧһ��Form����������,�ɶ�ѡ����
             */
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //���fileitem�з�װ������ͨ�����������
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //�����ͨ����������ݵ�������������
                    dynamicsText = item.getString("UTF-8");
                    System.out.println(name + "=" + dynamicsText);
                }
                else{
                    //�õ��ϴ����ļ����ƣ�
                    dynamicsFile = item.getName();
                    System.out.println(dynamicsFile);
                    if(dynamicsFile==null || dynamicsFile.trim().equals("")){
                        continue;
                    }
                    //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
                    //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
                    dynamicsFile = dynamicsFile.substring(dynamicsFile.lastIndexOf("\\")+1);
                    //�ļ��Ѿ����ύ��Servlet�У���ȡ�ϴ��ļ���������
                    InputStream in = item.getInputStream();

                    //����������·��
                    String savePath = "C:\\wamp\\www\\J2ee fileUpload\\Social dynamics\\"+userId;
                    File file = new File(savePath);
                    //�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
                    if (!file.exists() && !file.isDirectory()) {
                        System.out.println(savePath + "Ŀ¼�����ڣ���Ҫ����");
                        //����Ŀ¼
                        file.mkdir();
                    }

                    //����һ���ļ������
                    FileOutputStream out = new FileOutputStream(savePath+"/"+dynamicsFile);
                    //����һ��������
                    byte buffer[] = new byte[1024];
                    //�ж��������е������Ƿ��Ѿ�����ı�ʶ
                    int len = 0;
                    //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
                    while((len=in.read(buffer))>0){
                        //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
                        out.write(buffer, 0, len);
                    }
                    //�ر�������
                    in.close();
                    //�ر������
                    out.close();
                    //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
                    item.delete();
                    System.out.println("�ϴ���̬�ɹ�");
                    //����̬�����Ϣ�������ݿ���
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    dynamicsDao.insertDynamics(userId, dynamicsText,userId+"/"+dynamicsFile,now);//���ݿ���в��붯̬��Ϣ
                    userDao.updateDynamicsNum(userId);//�û����¶�̬������+1��
                }
            }
        }catch (Exception e) {
            System.out.println("�ϴ���̬ʧ��");
            e.printStackTrace();
        }
    }

    public List<SocialDynamics> showDynamics() {
        List<SocialDynamics> list = dynamicsDao.selectAllDynamics();
        return list;
    }

    public int curMaxDynamicsId() {
        String curMaxDynamicsIdString = dynamicsDao.selectMaxDynamicsId();
        if(curMaxDynamicsIdString==null){
            return 0;
        }
        else{
            return Integer.valueOf(curMaxDynamicsIdString);
        }
    }

    public List<SocialDynamics> showNewDynamics(int pos) {
        List<SocialDynamics> list = dynamicsDao.selectDynamicsFromPos(pos);
        return list;
    }

    public boolean changeHeadImg(HttpServletRequest request, int userId) {
        String headimgName = "";
        try{
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            servletFileUpload.setHeaderEncoding("UTF-8");
            if(!ServletFileUpload.isMultipartContent(request)){
                System.out.println("���Ǳ����ݣ�");
                return false;
            }
            List<FileItem> list = servletFileUpload.parseRequest(request);
            for(FileItem item:list){
                if(item.isFormField()){
                    //��ͨ�����ı�����
                }else {
                    headimgName = item.getName();
                    headimgName = "isHeadImg"+headimgName.substring(headimgName.lastIndexOf("\\") + 1);
                    InputStream inputStream = item.getInputStream();
                    String savePath = "C:\\wamp\\www\\J2ee fileUpload\\Social dynamics\\"+userId;
                    File file = new File(savePath);
                    if(!file.exists()&&!file.isDirectory()){
                        //�½��ļ���
                        System.out.println("�½�ͷ���ļ���");
                        file.mkdir();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(savePath+"/"+headimgName);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = inputStream.read(buffer))>0){
                        fileOutputStream.write(buffer,0,len);
                    }
                    inputStream.close();
                    fileOutputStream.close();
                    item.delete();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        userDao.updateUserHeadImg(userId,userId+"/"+headimgName);//�������ݿ��е�ͷ����Ϣ
        return true;
    }

    public boolean changePersonalInfo(int userId, String username, String email, String address, String description) {
        int updateResult = userDao.updateUser(userId,username,email,address,description);
        if(updateResult==1){
            return true;
        }
        else {
            return false;
        }
    }

    public User queryUserById(int userId) {
        User user = userDao.selectUserById(userId);
        return user;
    }

    public String clickLikeDynamics(int dynamicsId, int userId) {
        int flag = 0;
        int selectLikeResult = dynamicsDao.selectLike(dynamicsId, userId);
        if(selectLikeResult == 0){
            //�û���û����
            flag = 1;
            dynamicsDao.updateLikeNum(dynamicsId);
            dynamicsDao.insertLike(dynamicsId, userId);

        }else if(selectLikeResult==1) {
            //�û��Ѿ�����
            dynamicsDao.updateLikeNumSub(dynamicsId);
            dynamicsDao.deleteLike(dynamicsId, userId);
        }
        int newLikeNum = dynamicsDao.selectLikeNum(dynamicsId);
        if(flag ==1){
            return newLikeNum+".like";
        }else{
            return newLikeNum+".unlike";
        }
    }

    public List<Integer> showWhichLike(int userId) {
        List<Integer> list = dynamicsDao.selectWhichLike(userId);
        return list;
    }

    public SocialDynamics showDetailDynamicsById(int dynamicsId) {
        SocialDynamics socialDynamics = dynamicsDao.selectDetailDynamicsById(dynamicsId);
        return socialDynamics;
    }

    public List<User> showLikeUserOfDynamics(int dynamicsId) {
        List<User> list = dynamicsDao.selectLikeUserOfDynamics(dynamicsId);
        return list;
    }

    public CommentInfo sendComment(int dynamicsId, int sendId,String commentText) {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        SocialDynamics socialDynamics = dynamicsDao.selectDetailDynamicsById(dynamicsId);
        int insertCommentResult = commentDao.insertComment(dynamicsId, sendId, socialDynamics.getUser().getUsername(), commentText,now);
        if(insertCommentResult == 1){
            CommentInfo commentInfo = commentDao.selectNewestCommentOfUser(sendId);
            return commentInfo;
        }
        return null;
    }

    public List<CommentInfo> showCommentById(int dynamicsId) {
        List<CommentInfo> list = commentDao.selectCommentByDynamicsId(dynamicsId);
        return list;
    }

    public CommentInfo showComment(int commentId) {
        CommentInfo commentInfo = commentDao.selectCommentById(commentId);
        return commentInfo;
    }

    public ReplyInfo sendReply(int commentId, int sendId, String replyText) {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        String receiveUsername = commentDao.selectCommentById(commentId).getSendUser().getUsername();
        commentDao.insertReply(commentId, sendId, receiveUsername, replyText, now);

        ReplyInfo replyInfo = commentDao.selectReplyInfoBysendId(sendId);
        return replyInfo;
    }

    public List<ReplyInfo> showAllReplyByCommentId(int commentId) {
        List<ReplyInfo> list = commentDao.selectReplyInfoByCommentId(commentId);
        return list;
    }

    public ReplyInfo sendReplyOfReply(int replyId, int sendId, String replyText) {

        Timestamp now = new Timestamp(System.currentTimeMillis());

        ReplyInfo replyInfo = commentDao.selectReplyInfoById(replyId);//�û�Ҫ���лظ��Ļظ���
        String receiveUsername = replyInfo.getSendUser().getUsername();
        int commentId = replyInfo.getCommentId();

        commentDao.insertReply(commentId,sendId,receiveUsername,replyText,now);

        ReplyInfo replyInfo2 = commentDao.selectReplyInfoBysendId(sendId);//�û������»ظ�
        return replyInfo2;
    }

    public ReplyInfo showReplyInfo(int replyId) {
        ReplyInfo replyInfo = commentDao.selectReplyInfoById(replyId);
        return replyInfo;
    }
}
