/**
 * Created by Administrator on 2016/5/27.
 */
//�����Ҫ�����߼�js����
//javascript ģ�黯
var seckill={
    //��װ��ɱ��ص�ajax��url
    URL:{

    },
    //��֤�ֻ���
    validatePhone:function(phone){
        if(phone&&phone.length==11&&!isNaN(phone)){
            return true;
        }
        else{
            return false;
        }
    },
    //����ҳ��ɱ�߼�
    detail:{
        //����ҳ��ʼ��
        init:function(params){
            //�ֻ���֤�͵�½����ʱ����
            //��cookie�в����ֻ���
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            //��֤�ֻ���
            if(!seckill.validatePhone(killPhone)){
                //��phone
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show:true,  //��ʾ������
                    backdrop:'static', //��ֹλ�ùر�
                    keyboard:false //�رռ����¼�
                });

                $("#killPhoneBtn").click(function(){
                    var inputPhone = $("#killPhoneKey").val();
                    console.log("inputPhone="+inputPhone);//TODO
                    if(seckill.validatePhone(inputPhone)){
                        //�绰д��cookie
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        //ˢ��ҳ��
                        window.location.reload();
                    }
                    else{
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">����绰����</label>').show(300);
                    }
                });
            }
        }
    }
}

