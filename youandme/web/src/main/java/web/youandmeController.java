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

    //�Զ�װ��serviceд�õĽӿڣ���ʵ�ֶ��󣬴���SpringIOC�����У�
    @Autowired
    private youandmeService youandmeService;

    //��¼ҳ�棬����ע��
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPage(Model model){
        return "login";
    }

    //��ҳ
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String indexPage(Model model){
        return "index";
    }

    //ע�����
    @RequestMapping(value = "/registerUser",
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public youandmeResult<User> registerResponse(HttpServletRequest request){
        //��ȡ��ֵ�Բ���
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        //ע��service
        youandmeService.register(username,password,email);

        User user = new User(username);
        return new youandmeResult<User>(user,true,"register success!");
    }

    //��¼����
    @RequestMapping(value = "/userLogin",
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public youandmeResult loginResponse(HttpServletRequest request){

        String stringToLogin = request.getParameter("stringToLogin");
        String password = request.getParameter("password");

        boolean loginBool = youandmeService.login(stringToLogin, password);
        if(loginBool==false){//���ܵ�¼
            return new youandmeResult("fail to login!Please check your Information!",false);
        }
        else{//�ܵ�¼
            return new youandmeResult("login success",true);
        }
    }
}
