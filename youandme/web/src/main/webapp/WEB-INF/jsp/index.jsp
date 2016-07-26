<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--引入jstl标签库 -->
<%@include file="common/tag.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>index</title>
    <link href="<%=basePath%>resources/css/index.css" rel="stylesheet">
</head>
<body>

    <h1>这是youandme主页！！！</h1>
    ${userModel.userId}
    ${userModel.username}
    ${userModel.joinTime}

    <!--用户上传动态 -->
    <form id= "postDynamicsForm">
        <textarea name="dynamicsText"></textarea>
        <input type="file" name="dynamicsFile" />
        <input type="button" value="发表" id="dynamicsButton"/>
    </form>

    <!-- 刷新进入页面时显示用户全部动态-->
    <div id="dynamicsContainer">
    <div id="dynamicsContainerFade"></div>
    <c:forEach var="dynamics" items="${dynamicsModel}">
        <c:set var="dynamicsFileType" value="${dynamics.dynamicsFile}"></c:set>
        <c:set var="dynamicsFileTypeFinal" value="${fn:substringAfter(dynamicsFileType,'.')}"></c:set>
        <!--动态为文字+图片 -->
        <c:if test="${dynamicsFileTypeFinal =='jpg'}">
            <div class="DynamicsDiv">
                <span >${dynamics.dynamicsId} </span>
                <span >${dynamics.dynamicsText}</span>
                <img src="http://139.129.47.176/J2ee fileUpload/Social dynamics/${dynamics.dynamicsFile}">
            </div>
        </c:if>

        <!--动态为文字+视频 -->
        <c:if test="${dynamicsFileTypeFinal=='mp4'}">
            <div class="DynamicsDiv">
                <span >${dynamics.dynamicsId} </span>
                <span >${dynamics.dynamicsText}</span>
                <video  class="video-js vjs-default-skin vjs-big-play-centered"
                    controls width="640" height="360">
                    <source src="http://139.129.47.176/J2ee fileUpload/Social dynamics/${dynamics.dynamicsFile}" type='video/mp4' />
                </video>
            </div>
        </c:if>
    </c:forEach>

    </div>

<a href="http://hupu.com">虎扑体育</a>

<script src="<%=basePath%>resources/js/jquery-2.0.0.min.js" type="text/javascript"></script>
<script src="<%=basePath%>resources/js/index.js" type="text/javascript" charset="GB2312"></script>

</body>
</html>
