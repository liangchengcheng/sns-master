<%--
  Created by IntelliJ IDEA.
  User: lcc
  Date: 2017/2/24
  Time: 上午7:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--页面的样式--%>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/menu.css">
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="navbar-container container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="<c:url value="/" />">OSF</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active">
                    <%--这个地址需要修改--%>
                    <a href="<c:url value="./explore.jsp" />">探索</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${not empty sessionScope.user}">
                    <li>
                        <div class="ui simple dropdown item">
                            ${sessionScope.user.user_name}
                            <i class="drop icon"></i>
                            <div class="ui vertical menu">
                                <a class="item" href="<c:url value="/notifications/comment" />">
                                    评论
                                    <div class="ui red label">${notifications.comment }</div>
                                </a>
                                <a class="item" href="<c:url value="/notifications/like" />">
                                    喜欢
                                    <div class="ui red label">${notifications.like }</div>
                                </a>
                                <a class="item" href="<c:url value="/notifications/follow" />">
                                    关注
                                    <div class="ui red label">${notifications.follow }</div>
                                </a>
                                <a class="item" href="<c:url value="/notifications/system" />">
                                    系统消息
                                    <div class="ui red label">${notifications.system }</div>
                                </a>

                                <a href='<c:url value="/account/setting/info" />' class="item">设置</a>
                                <a href='<c:url value="/account/logout" />' class="item">退出</a>
                            </div>
                        </div>
                    </li>
                </c:if>
            </ul>
        </div>

    </div>
</nav>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
