package web;

import dto.youandmeResult;
import entity.User;
import entity.socialDynamics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.youandmeService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
@Controller
@RequestMapping(value = "/youandme")
public class youandmeController {

    //自动装载service写好的接口（已实现对象，存在SpringIOC容器中）
    @Autowired
    private youandmeService youandmeService;


    /**
     * 登录页面，包含注册
     * @param model
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPage(Model model){
        return "login";
    }



    /**
     * 主页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String indexPage(HttpServletRequest request,Model model){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(user==null){
            //return "redirect:/youandme/login";//客户端请求重定向，URL地址栏改变，有些浏览器会有sessionId在地址栏重写//TODO
            return "forward:/youandme/login";//服务器内部请求转发，URL地址栏不会改变
        }
        else {
            //将session中的user传入Model中供jsp页面使用
            model.addAttribute("userModel",user);

            //每次刷新主页都显示全部动态信息
            List<socialDynamics> list = youandmeService.showDynamics();
            model.addAttribute("dynamicsModel",list);

            //用session记录当前最大的动态id
            int pos = youandmeService.curMaxDynamicsId();
            session.setAttribute("pos",pos);
            session.setMaxInactiveInterval(3600);//session生命周期为一个小时

            return "index";
        }
    }



    /**
     * 注册操作
     * @param request
     * @return
     */
    @RequestMapping(value = "/registerUser",
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public youandmeResult<User> registerResponse(HttpServletRequest request){
        //获取键值对参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //注册service
        youandmeService.register(username,password,email);

        User user = new User(username);
        return new youandmeResult<User>(user,true,"register success!");//注册成功只返回用户名
    }



    /**
     * 登录操作
     * @param request
     * @return
     */
    @RequestMapping(value = "/userLogin",
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public youandmeResult loginResponse(HttpServletRequest request){

        HttpSession session = request.getSession();
        if((User)session.getAttribute("user")!=null){
            //同一个浏览器在注销账号前不能再登录
            return new youandmeResult(false,"you are already logged in an account in the same browser!");
        }

        String stringToLogin = request.getParameter("stringToLogin");
        String password = request.getParameter("password");
        User user = youandmeService.login(stringToLogin, password);
        if(user == null){//不能登录
            return new youandmeResult(false,"fail to login!Please check your Information!");
        }
        else{//能登录，服务端保存用户信息至session
            session.setAttribute("user",user);
            return new youandmeResult(true,"login success");
        }
    }



    /**
     * 文件上传，支持多个
     * @param request
     */
    @RequestMapping(value = "/postFile")
    @ResponseBody
    public void postFile(HttpServletRequest request) {

        /**注意：
         * form表单中有参数enctype="multipart/form-data"
         * request.getParameter("testUsername");就会失效，返回值为null
         */
        /*String testUsernameString = request.getParameter("testUsername");
        System.out.println(testUsernameString);*/

        //获取当前用户的id
        HttpSession session = request.getSession();
        int userId = ((User) session.getAttribute("user")).getUserId();
        //执行上传文件
        youandmeService.fileUpload(request, userId);
    }



    /**
     * 用户上传动态操作
     * @param request
     * @return
     */
    @RequestMapping(value = "/postDynamics",
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public youandmeResult postDynamics(HttpServletRequest request){

        HttpSession session = request.getSession();
        int userId = ((User) session.getAttribute("user")).getUserId();

        //得到还没发表动态前表中最大的动态主键值，强制转换后自动拆箱
        int pos = (Integer)session.getAttribute("pos");

        //执行发表动态，即插入数据库与上传动态文件
        youandmeService.postDynamics(request, userId);

        //返回新的动态用于局部刷新
        List<socialDynamics> list = youandmeService.showNewDynamics(pos);
        session.setAttribute("pos",youandmeService.curMaxDynamicsId());

        return new youandmeResult<List<socialDynamics>>(list,true);
    }
}
