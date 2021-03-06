<%--
  Created by IntelliJ IDEA.
  User: lcc
  Date: 2017/2/24
  Time: 上午7:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>404</title>
    <link rel="shortcut icon" href="<%=request.getContextPath() %>/img/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/bootstrap2.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/semantic.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css">
    <style>
        body{
            background-color: white;
        }
    </style>
</head>
<body>

<%@ include file="topbar.jsp" %>

<div class="container">
    <div class="row">
        <div class="span9 offset1">
            <div class="error page img">
                <img src="<c:url value="/img/404.jpg" />" alt="" />
            </div>
            <div class="error page code">
                404
            </div>
        </div>
    </div>
</div>

</body>
</html>
