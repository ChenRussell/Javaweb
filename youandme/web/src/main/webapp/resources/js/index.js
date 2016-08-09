
/*点赞动态操作*/
var likeIt = function(obj){
    var dynamicsId = obj.parentNode.id;
    $.ajax({
        url:'clickLike',
        type:'POST',
        data:{dynamicsId:dynamicsId},
        success:function(result){
            if(result['success']==true){
                //点赞
                $("#"+dynamicsId+"like").css('background-image','url("../resources/src/like2.png")');
                $("#"+dynamicsId+"likeh4").replaceWith("<h4 id='"+dynamicsId+"likeh4'>"+result['data']+"</h4>");
                $("#"+dynamicsId+"likeh4").css('color','#e81c4f');
            }
            else{
                //取消点赞
                $("#"+dynamicsId+"like").css('background-image','url("../resources/src/like.png")');
                $("#"+dynamicsId+"likeh4").replaceWith("<h4 id='"+dynamicsId+"likeh4'>"+result['data']+"</h4>");
                $("#"+dynamicsId+"likeh4").css('color','#56abe4');
            }
        },
        error:function(){
            alert("System inner error!");
        }
    });
};
/**
 * 用户点击动态评论图标，出现动态详情模态框，包括动态信息，点赞人头像，评论等。
 * @param obj
 */
var commentIt = function(obj){

    $('.dynamisDetailOuter,.dynamisDetailContainer').fadeIn(300);
    $('.dynamisDetailContainer').css('transform','scale(1)');
    $('html,body').css('overflow-y','hidden');
    var dynamicsId = obj.parentNode.id;
    $('.dynamisDetailContainer').empty();//清空模态框内原有的内容
    /*动态信息*/
    $.ajax({
        url:"detailDynamicsById",
        type:"POST",
        data:{dynamicsId:dynamicsId},
        success:function(result){

            var dynamics = result['data'];
            $('.dynamisDetailContainer').append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+dynamics['user']['headImg']+'">');
            $('.dynamisDetailContainer').append('<span><a href="'+dynamics["user"]["userId"]+'/userDetail">'+dynamics['user']['username']+'</a></span>');
            $('.dynamisDetailContainer').append('<span>'+dynamics['user']['email']+'</span>');
            $('.dynamisDetailContainer').append('<span>'+dynamics['dynamicsText']+'</span>');

            if(dynamics['dynamicsFile'].substr(dynamics['dynamicsFile'].lastIndexOf("."))=='.jpg'){
                $('.dynamisDetailContainer').append("<img src='http://139.129.47.176/J2ee fileUpload/Social dynamics/"+dynamics['dynamicsFile']+"'>");
            }else{
                $('.dynamisDetailContainer').append(
                    "<video class='video-js vjs-default-skin vjs-big-play-centered' width='640' height='360' controls> " +
                    "<source src='http://139.129.47.176/J2ee fileUpload/Social dynamics/"+dynamics['dynamicsFile']+"' type='video/mp4' />" +
                    "</video>");
            }

            $('.dynamisDetailContainer').append('<span>LIKES <h6>'+dynamics["likeNum"]+'</h6> </span>');

            //动态点赞用户
            $.ajax({
                url:"dynamicsOfLikeUser",
                type:"POST",
                data:{dynamicsId:dynamicsId},
                success:function(result){
                    var items = result['data'];
                    $('.dynamisDetailContainer').append('<div id="usersFade"></div>');
                    for(var i=0;i<items.length-1;i++){
                        $("#usersFade").append('<a href="'+items[i]['userId']+'/userDetail">' +
                            '<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+items[i]['headImg']+'">' +
                            '</a>');
                    }
                    $('.dynamisDetailContainer').append('<div class="sendCommentDiv"></div>');
                    $('.sendCommentDiv').append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+items[i]['headImg']+'">');
                    $('.sendCommentDiv').append('<textarea class="commentText" placeholder="Comment what you want to say!"></textarea>');
                    $('.sendCommentDiv').append('<input type="button" value="Comment" onclick="sendComment('+dynamicsId+')" />');

                    /**
                     * 显示当前动态全部评论
                     */
                    $.ajax({
                        url:"showCommentOfDynamics",
                        type:"POST",
                        data:{dynamicsId:dynamicsId},
                        success:function(result){
                            var commentItems = result['data'];
                            for(var i = 0;i<commentItems.length;i++){
                                $('.dynamisDetailContainer').append('<div class="commentLittleDiv" id="comment'+commentItems[i]['commentId']+'"></div>');
                                $('#comment'+commentItems[i]['commentId']).append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+commentItems[i]['sendUser']['headImg']+'">');
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+commentItems[i]['sendUser']['username']+'</span>');
                                $('#comment'+commentItems[i]['commentId']).append('<span>' +
                                    '<span>'+"@"+commentItems[i]['receiveUsername']+'</span>' +
                                    ' <span>'+commentItems[i]['commentText']+'</span>'+
                                    '</span>');
                                $('#comment'+commentItems[i]['commentId']).append('<img src="../resources/src/like.png" title="点赞">');
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+commentItems[i]['likeNum']+'</span>');
                                $('#comment'+commentItems[i]['commentId']).append('<img src="../resources/src/reply.png" title="回复" onclick="replyComment('+commentItems[i]['commentId']+')">');
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+commentItems[i]['commentNum']+'</span>');
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+(i+1)+"#"+'</span>');
                                var now = new Date(commentItems[i]['commentTime']);
                                $('#comment'+commentItems[i]['commentId']).append('<span>'+now.getFullYear()+"-"+now.getMonth()+"-"+now.getDay()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds()+'</span>');

                                /**
                                 * 显示评论的所有回复
                                 */
                                var helper = commentItems[i]['commentId'];
                                $.ajax({
                                    url:"showReplyOfComment",
                                    type:"POST",
                                    async:false,
                                    data:{commentId:commentItems[i]['commentId']},
                                    success:function(result){
                                        var replyList = result['data'];
                                        for(var j = 0;j<replyList.length;j++){
                                            $('.replyCommentDiv').css('display','none');
                                            $('#comment'+helper).append('<div class="replyCommentDivTrue" id="reply'+replyList[j]["replyId"]+'" style="display:none;"></div>');
                                            $('#reply'+replyList[j]["replyId"]).append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+replyList[j]['sendUser']['headImg']+'">');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>'+replyList[j]['sendUser']['username']+'</span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>'+
                                                '<span>'+"@"+replyList[j]['receiveUsername']+'</span>'+
                                                '<span>'+replyList[j]['replyText']+'</span>'+
                                                '</span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<img src="../resources/src/like.png" title="点赞">');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>'+replyList[j]['likeNum']+'</span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<img src="../resources/src/reply.png" title="回复" onclick="replyCommentReply('+replyList[j]['replyId']+' '+","+' '+helper+')">');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>0</span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<span></span>');
                                            $('#reply'+replyList[j]["replyId"]).append('<span>'+replyList[j]['replyTime']+'</span>');
                                            $('#reply'+replyList[j]["replyId"]).fadeIn(400);
                                        }
                                    },
                                    error:function(){

                                    }
                                });
                            }
                        },
                        error:function(){
                            alert("System inner error!");
                        }
                    });
                },
                error:function(){
                    alert("System inner error");
                }
            });
        },
        error:function(){
            alert("System inner error");
        }
    });
};

/**
 * 用户对动态发表评论
 * @param obj
 */
var sendComment  =function(obj){

    var commentContent = $(".commentText").val();
    $.ajax({
        url:"sendComment",
        type:"POST",
        data:{dynamicsId:obj,commentContent:commentContent},
        success:function(result){

            var commentInfo = result['data'];
            $('.dynamisDetailContainer').append('<div class="commentLittleDiv" id="comment'+commentInfo['commentId']+'"></div>');
            $('#comment'+commentInfo['commentId']).append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+commentInfo['sendUser']['headImg']+'">');
            $('#comment'+commentInfo['commentId']).append('<span>'+commentInfo['sendUser']['username']+'</span>');
            $('#comment'+commentInfo['commentId']).append('<span>' +
                '<span>'+"@"+commentInfo['receiveUsername']+'</span>' +
                ' <span>'+commentInfo['commentText']+'</span>'+
                '</span>');
            $('#comment'+commentInfo['commentId']).append('<img src="../resources/src/like.png" title="点赞">');
            $('#comment'+commentInfo['commentId']).append('<span>'+commentInfo['likeNum']+'</span>');
            $('#comment'+commentInfo['commentId']).append('<img src="../resources/src/reply.png" title="回复" onclick="replyComment('+commentInfo['commentId']+')">');
            $('#comment'+commentInfo['commentId']).append('<span>'+commentInfo['commentNum']+'</span>');
            $('#comment'+commentInfo['commentId']).append('<span>'+"?#"+'</span>');
            var now = new Date(commentInfo['commentTime']);
            $('#comment'+commentInfo['commentId']).append('<span>'+now.getFullYear()+"-"+now.getMonth()+"-"+now.getDay()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds()+'</span>');
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

/**
 * 回复动态的评论,显示当前回复用户头像，输入框，被回复用户名
 * @param commentId
 */
var replyComment = function(commentId){

    $.ajax({
        url:"replyCommentHelp",
        type:"POST",
        async:false,
        data:{commentId:commentId},
        success:function(result){
            var list = result['data'];
            $("#comment"+commentId).append('<div class="replyCommentDiv"></div>');
            $('.replyCommentDiv').append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+list[0]["headImg"]+'">');
            $('.replyCommentDiv').append('<textarea class="replyCommentContent" placeholder="@'+list[1]['username']+'"></textarea>');
            $('.replyCommentDiv').append('<input type="button" value="Reply" onclick="appendComment('+commentId+')">')
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

/**
 * 用户回复动态的评论
 * @param commentId
 */
var appendComment = function(commentId){

    var replyCommentContent = $(".replyCommentContent").val();
    $.ajax({
        url:"sendReply",
        type:"POST",
        data:{commentId:commentId,replyCommentContent:replyCommentContent},
        success:function(result){
            var replyInfo = result['data'];
            $('.replyCommentDiv').css('display','none');
            $('#comment'+commentId).append('<div class="replyCommentDivTrue" id="reply'+replyInfo["replyId"]+'" style="display:none;"></div>');
            $('#reply'+replyInfo["replyId"]).append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+replyInfo['sendUser']['headImg']+'">');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['sendUser']['username']+'</span>');
            $('#reply'+replyInfo["replyId"]).append('<span>'+
                '<span>'+"@"+replyInfo['receiveUsername']+'</span>'+
                '<span>'+replyInfo['replyText']+'</span>'+
                '</span>');
            $('#reply'+replyInfo["replyId"]).append('<img src="../resources/src/like.png" title="点赞">');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['likeNum']+'</span>');
            $('#reply'+replyInfo["replyId"]).append('<img src="../resources/src/reply.png" title="回复" onclick="replyCommentReply('+replyInfo['replyId']+' '+","+' '+commentId+')">');
            $('#reply'+replyInfo["replyId"]).append('<span>0</span>');
            $('#reply'+replyInfo["replyId"]).append('<span></span>');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['replyTime']+'</span>');
            $('#reply'+replyInfo["replyId"]).fadeIn(400);
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

/**
 * 用户回复动态评论中的回复，帮助显示当前用户头像，被回复用户的名字
 * @param replyId
 */
var replyCommentReply = function(replyId,commentId){

    $.ajax({
        url:"replyReplyHelp",
        type:"POST",
        data:{replyId:replyId},
        success:function(result){
            var list = result['data'];
            $("#comment"+commentId).append('<div class="replyCommentDiv"></div>');
            $('.replyCommentDiv').append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+list[0]["headImg"]+'">');
            $('.replyCommentDiv').append('<textarea class="replyCommentContent" placeholder="@'+list[1]['username']+'"></textarea>');
            $('.replyCommentDiv').append('<input type="button" value="Reply" onclick="appendReplyOfReply('+replyId+')">')
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

/**
 * 用户对评论中的回复发表回复
 * @param replyId
 */
var appendReplyOfReply = function(replyId){

    var replyText = $(".replyCommentContent").val();
    $.ajax({
        url:"sendReplyOfReply",
        type:"POST",
        data:{replyId:replyId,replyText:replyText},
        success:function(result){
            console.log(result);
            var replyInfo = result['data'];
            $('.replyCommentDiv').css('display','none');
            $('#comment'+replyInfo['commentId']).append('<div class="replyCommentDivTrue" id="reply'+replyInfo["replyId"]+'" style="display:none;"></div>');
            $('#reply'+replyInfo["replyId"]).append('<img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/'+replyInfo['sendUser']['headImg']+'">');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['sendUser']['username']+'</span>');
            $('#reply'+replyInfo["replyId"]).append('<span>'+
                '<span>'+"@"+replyInfo['receiveUsername']+'</span>'+
                '<span>'+replyInfo['replyText']+'</span>'+
                '</span>');
            $('#reply'+replyInfo["replyId"]).append('<img src="../resources/src/like.png" title="点赞">');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['likeNum']+'</span>');
            $('#reply'+replyInfo["replyId"]).append('<img src="../resources/src/reply.png" title="回复" onclick="replyCommentReply('+replyInfo['replyId']+' '+","+' '+replyInfo['commentId']+')">');
            $('#reply'+replyInfo["replyId"]).append('<span>0</span>');
            $('#reply'+replyInfo["replyId"]).append('<span></span>');
            $('#reply'+replyInfo["replyId"]).append('<span>'+replyInfo['replyTime']+'</span>');
            $('#reply'+replyInfo["replyId"]).fadeIn(400);
        },
        error:function(){
            alert("System inner error!");
        }
    });
};

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
                        $("#"+items[i]["dynamicsId"]).append("<span id='"+items[i]['dynamicsId']+'like'+"' onclick='likeIt(this)'><h4 id='"+items[i]['dynamicsId']+'likeh4'+"'>"+items[i]['likeNum']+"</h4></span>");
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
                        $("#"+items[i]["dynamicsId"]).append("<span id='"+items[i]['dynamicsId']+'like'+"' onclick='likeIt(this)'><h4 id='"+items[i]['dynamicsId']+'likeh4'+"'>"+items[i]['likeNum']+"</h4></span>");
                        $("#"+items[i]["dynamicsId"]).append('<span><h4>13</h4></span>');
                    }
                }
            },
            error: function () {
                alert("System error!");
            }
        });
    });

    /**
     * 进入主页时对已经点赞的动态进行 “点赞” 显示
     */
    $.ajax({
        url:'showLikeHelp',
        type:'GET',
        success:function(result){
            var likeList = result['data'];
            for(var i=0;i<likeList.length;i++){

                $("#"+likeList[i]+"like").css('background-image','url("../resources/src/like2.png")');
                $("#"+likeList[i]+"likeh4").css('color','#e81c4f');
            }
        },
        error:function(){
            alert("System inner error!");
        }
    });

    /**
     * 用户点击模态框外的暗黑色覆盖层，模态框与覆盖层消失
     */
    $('.dynamisDetailOuter').on('click',function(){
        $('.dynamisDetailOuter,.dynamisDetailContainer').fadeOut(300,function(){
            $('html').css('overflow-y','auto');
            $(this).removeAttr('style');
        })
    });
});
