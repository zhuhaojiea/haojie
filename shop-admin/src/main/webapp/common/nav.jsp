<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/30 0030
  Time: 下午 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">飞狐电商后台管理</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li  id="li_1"><a href="/product/toList.jhtml#1">商品管理</a></li>
                <li  id="li_5"><a href="/goods/toList.jhtml#5">SPU管理</a></li>
                <li  id="li_6"><a href="/type/toList.jhtml#6">类型管理</a></li>
                <li  id="li_7"><a href="/cate/index.jhtml#7">分类管理</a></li>
                <li  id="li_8"><a href="/spec/toList.jhtml#8">规格管理</a></li>
                <li id="li_2"><a href="/user/toList.jhtml#2">用户管理</a></li>
                <li id="li_3"><a href="/area/index.jhtml#3">地区管理</a></li>
                <li id="li_4"><a href="/log/toList.jhtml#4">日志管理</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎${user.realName}登录
                    <c:if test="${!empty user.loginTime}">
                    ，上次登录时间<fmt:formatDate value="${user.loginTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                    </c:if>
                    今天是第${user.loginCount}次登录
                </a></li>
                <li><a href="/user/logout.jhtml">退出</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<script src="/js/jquery.min.js"></script>

<script>
    $(function() {
        initActiveMenu();
    })


    function initActiveMenu() {
        var v_id = location.hash.substring(1);
        $("#li_"+v_id).addClass("active");
    }
</script>


