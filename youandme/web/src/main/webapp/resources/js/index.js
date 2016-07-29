$(function(){

    /*ajaxFormData*/
    $("#dynamicsButton").on('click',function(){
        var formData = new FormData($( "#postDynamicsForm" )[0]);
        $.ajax({
            url: 'postDynamics' ,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (result) {
                //异步请求最新动态数据，局部刷新
                var items = result['data'];
                for(var i=items.length-1;i>=0;i--){
                    //与主页刷新不同，这里ajax局部刷新要反序输出，因为在数据库排序会降低性能与浪费时间，相应的查找语句没有设置DESC
                    if(items[i]['dynamicsFile'].substr(items[i]['dynamicsFile'].lastIndexOf("."))=='.jpg'){
                        alert(items[i]["user"]);
                        var listItem = '<div class="DynamicsDiv" id='+items[i]["dynamicsId"]+'></div>';
                        $("#dynamicsContainerFade").append(listItem);
                        $("#"+items[i]["dynamicsId"]).append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+items[i]['user']['headImg']+'">');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]["user"]["username"]+'</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]["createTime"]+'</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span></span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>浏览(187)</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]['dynamicsText']+'</span>');
                        $("#"+items[i]["dynamicsId"]).append("<img src='http://139.129.47.176/J2ee fileUpload/Social dynamics/"+items[i]['dynamicsFile']+"'>");
                        $("#"+items[i]["dynamicsId"]).append('<span><h4>16</h4></span>');
                        $("#"+items[i]["dynamicsId"]).append('<span><h4>35</h4></span>');
                        $("#"+items[i]["dynamicsId"]).append('<span><h4>13</h4></span>');
                    }
                    else{

                        var listItem = '<div class="DynamicsDiv" id='+items[i]["dynamicsId"]+'></div>';
                        $("#dynamicsContainerFade").append(listItem);
                        $("#"+items[i]["dynamicsId"]).append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+items[i]['user']['headImg']+'">');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]["user"]["username"]+'</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]["createTime"]+'</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span></span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>浏览(187)</span>');
                        $("#"+items[i]["dynamicsId"]).append('<span>'+items[i]['dynamicsText']+'</span>');
                        $("#"+items[i]["dynamicsId"]).append(
                            "<video class='video-js vjs-default-skin vjs-big-play-centered' width='640' height='360' controls> " +
                            "<source src='http://139.129.47.176/J2ee fileUpload/Social dynamics/"+items[i]['dynamicsFile']+"' type='video/mp4' />" +
                            "</video>");
                        $("#"+items[i]["dynamicsId"]).append('<span><h4>16</h4></span>');
                        $("#"+items[i]["dynamicsId"]).append('<span><h4>35</h4></span>');
                        $("#"+items[i]["dynamicsId"]).append('<span><h4>13</h4></span>');
                    }
                }
            },
            error: function () {
                alert("System error!");
            }
        });
    });
});
