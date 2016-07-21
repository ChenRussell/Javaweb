$(function(){

    /*注册*/
    $("#myButton").on('click',function(){

        var username = $("#username").val();
        var password = $("#password").val();
        var email = $("#email").val();
        $.post("registerUser",{username:username,password:password,email:email},function(result){

            alert(result['info']);
            var user = result['data'];
            alert(user['username']);
            $("#username").val("");
            $("#email").val("");
            $("#password").val("");
            $("#stringToLogin").val(user['username']);
        });
    });

    /*登录*/
    $("#button2").on('click',function(){

        var stringToLogin = $("#stringToLogin").val();
        var password = $("#Loginpassword").val();
        $.post("userLogin",{stringToLogin:stringToLogin,password:password},function(result){

            var isSuccess = result['success'];
            if(isSuccess){//登录成功
                window.location.href= "index";//客户端重定向至首页
            }
            else{//登录失败
                var loginInfo = result['info'];
                alert(loginInfo);//提示失败信息
            }
        });
    });
});
