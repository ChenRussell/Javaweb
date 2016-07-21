package web;

import dto.youandmeResult;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.youandmeService;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/7/20.
 */
@Controller
@RequestMapping(value = "/youandme")
public class youandmeController {

    //自动装载service写好的接口（已实现对象，存在SpringIOC容器中）
    @Autowired
    private youandmeService youandmeService;

    //登录页面，包含注册
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPage(Model model){
        return "login";
    }

    //主页
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String indexPage(Model model){
        return "index";
    }

    //注册操作
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
        return new youandmeResult<User>(user,true,"register success!");
    }

    //登录操作
    @RequestMapping(value = "/userLogin",
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public youandmeResult loginResponse(HttpServletRequest request){

        String stringToLogin = request.getParameter("stringToLogin");
        String password = request.getParameter("password");

        boolean loginBool = youandmeService.login(stringToLogin, password);
        if(loginBool==false){//不能登录
            return new youandmeResult("fail to login!Please check your Information!",false);
        }
        else{//能登录
            return new youandmeResult("login success",true);
        }
    }
}
