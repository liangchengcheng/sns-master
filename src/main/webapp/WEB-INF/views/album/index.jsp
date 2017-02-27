<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="type" content="photo">
    <meta name="id" content="">
    <c:if test="${not empty sessionScope.user}">
        <meta name="isLogin" content="true"/>
    </c:if>
    <c:if test="${empty sessionScope.user}">
        <meta name="isLogin" content="false"/>
    </c:if>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/bootstrap2.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/navbar.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/semantic.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css">

    <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/semantic.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/basic.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/code.js"></script>
    <%-- <script type="text/javascript" src="<%=request.getContextPath() %>/js/album.js"></script> --%>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/comment.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/follow.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/login.js"></script>

</head>
<body>
<%@ include file="../topbar.jsp" %>
<%@ include file="../login_modal.jsp" %>

</body>