$(function(){

    /*ע��*/
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

    /*��¼*/
    $("#button2").on('click',function(){

        var stringToLogin = $("#stringToLogin").val();
        var password = $("#Loginpassword").val();
        $.post("userLogin",{stringToLogin:stringToLogin,password:password},function(result){

            var isSuccess = result['success'];
            if(isSuccess){//��¼�ɹ�
                window.location.href= "index";//�ͻ����ض�������ҳ
            }
            else{//��¼ʧ��
                var loginInfo = result['info'];
                alert(loginInfo);//��ʾʧ����Ϣ
            }
        });
    });
});
