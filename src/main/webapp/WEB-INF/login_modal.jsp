<%--
  Created by IntelliJ IDEA.
  User: lcc
  Date: 2017/2/24
  Time: 上午10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="ui small modal login-tip">
    <div class="header">
        请先登录
    </div>

    <div class="content">
        <div id="error" class="ui error message hidden">
            <ul class="list"></ul>
        </div>
        <form class="ui large form">
            <div class="field">
                <div class="ui left icon input">
                    <i class="user icon"></i>
                    <input id="email" type="text" name="email" placeholder="电子邮箱">
                </div>
            </div>

            <div class="field">
                <div class="ui left icon input">
                    <i class="lock icon"></i>
                    <input id="password" type="password" name="password" placeholder="密码">
                </div>
            </div>

            <div id="loginbtn" class="ui fluid large green submit button">登录</div>
            <div class="ui horizontal divider">
                Or
            </div>
            <a href='<c:url value="/account/register"/>' class="ui fluid large submit button">注册</a>
            <!-- </div> -->
        </form>
    </div>
</div>
