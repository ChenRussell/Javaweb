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
//@Service注解：将写好的youandmeService注入到SpringIOC容器中
@Service
public class youandmeServiceImpl implements youandmeService {

    //自动装配在SpringIOC容器中的dao模块接口,不需要手动新建相应实例
    @Autowired
    private LoginDao loginDao;

    @Autowired
    private DynamicsDao dynamicsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommentDao commentDao;

    public int register(String username, String password,String email) {

        /*注册时间为服务器的时间！*/
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(date);

        int result = loginDao.insertUser(username,password,email,dateString);
        return result;
    }

    public User login(String stringToLogin, String password) {

        User user = loginDao.selectUserFromAllUser(stringToLogin, password);
        return user;
        //若登录不成功，返回为null
        //若登录成功，返回数据库中全部字段
    }


    /**
     * 文件上传，以Http请求，用户唯一标识为参数
     * @param request
     * @param userId
     */
    public void fileUpload(HttpServletRequest request,int userId) {

        try{
            //创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
                return;
            }
            /*将Http向Servlet请求过程中提交的数据解析为List
             *每一个FileItem对应一个Form表单的输入项,可多选！！
             */
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");//TODO
                    System.out.println(name + "=" + value);
                }
                else{
                    //如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();//TODO
                    System.out.println(filename);
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //文件已经被提交到Servlet中，获取上传文件的输入流
                    InputStream in = item.getInputStream();

                    //服务器绝对路径
                    String savePath = "C:\\wamp\\www\\J2ee fileUpload\\Social dynamics\\"+userId;
                    File file = new File(savePath);
                    //判断上传文件的保存目录是否存在
                    if (!file.exists() && !file.isDirectory()) {
                        System.out.println(savePath + "目录不存在，需要创建");
                        //创建目录
                        file.mkdir();
                    }

                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    System.out.println("上传文件成功");
                }
            }
        }catch (Exception e) {
            System.out.println("上传文件失败");
            e.printStackTrace();
        }
    }

    public void postDynamics(HttpServletRequest request, int userId) {

        String dynamicsText = "";
        String dynamicsFile = "";
        try{
            //创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
                return;
            }
            /*将Http向Servlet请求过程中提交的数据解析为List
             *每一个FileItem对应一个Form表单的输入项,可多选！！
             */
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    dynamicsText = item.getString("UTF-8");
                    System.out.println(name + "=" + dynamicsText);
                }
                else{
                    //得到上传的文件名称，
                    dynamicsFile = item.getName();
                    System.out.println(dynamicsFile);
                    if(dynamicsFile==null || dynamicsFile.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    dynamicsFile = dynamicsFile.substring(dynamicsFile.lastIndexOf("\\")+1);
                    //文件已经被提交到Servlet中，获取上传文件的输入流
                    InputStream in = item.getInputStream();

                    //服务器绝对路径
                    String savePath = "C:\\wamp\\www\\J2ee fileUpload\\Social dynamics\\"+userId;
                    File file = new File(savePath);
                    //判断上传文件的保存目录是否存在
                    if (!file.exists() && !file.isDirectory()) {
                        System.out.println(savePath + "目录不存在，需要创建");
                        //创建目录
                        file.mkdir();
                    }

                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath+"/"+dynamicsFile);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    System.out.println("上传动态成功");
                    //将动态相关信息插入数据库中
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    dynamicsDao.insertDynamics(userId, dynamicsText,userId+"/"+dynamicsFile,now);//数据库表中插入动态信息
                    userDao.updateDynamicsNum(userId);//用户更新动态数量（+1）
                }
            }
        }catch (Exception e) {
            System.out.println("上传动态失败");
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
                System.out.println("不是表单数据！");
                return false;
            }
            List<FileItem> list = servletFileUpload.parseRequest(request);
            for(FileItem item:list){
                if(item.isFormField()){
                    //普通输入文本数据
                }else {
                    headimgName = item.getName();
                    headimgName = "isHeadImg"+headimgName.substring(headimgName.lastIndexOf("\\") + 1);
                    InputStream inputStream = item.getInputStream();
                    String savePath = "C:\\wamp\\www\\J2ee fileUpload\\Social dynamics\\"+userId;
                    File file = new File(savePath);
                    if(!file.exists()&&!file.isDirectory()){
                        //新建文件夹
                        System.out.println("新建头像文件夹");
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
        userDao.updateUserHeadImg(userId,userId+"/"+headimgName);//更新数据库中的头像信息
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
            //用户还没点赞
            flag = 1;
            dynamicsDao.updateLikeNum(dynamicsId);
            dynamicsDao.insertLike(dynamicsId, userId);

        }else if(selectLikeResult==1) {
            //用户已经点赞
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

        ReplyInfo replyInfo = commentDao.selectReplyInfoById(replyId);//用户要进行回复的回复条
        String receiveUsername = replyInfo.getSendUser().getUsername();
        int commentId = replyInfo.getCommentId();

        commentDao.insertReply(commentId,sendId,receiveUsername,replyText,now);

        ReplyInfo replyInfo2 = commentDao.selectReplyInfoBysendId(sendId);//用户的最新回复
        return replyInfo2;
    }

    public ReplyInfo showReplyInfo(int replyId) {
        ReplyInfo replyInfo = commentDao.selectReplyInfoById(replyId);
        return replyInfo;
    }
}
