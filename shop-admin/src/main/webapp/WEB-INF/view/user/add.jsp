<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/26 0026
  Time: 上午 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/common/head.jsp"/>
    <title>增加用户</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal">
                <div class="form-group">
                    <label  class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="userName" placeholder="请输入用户名...">
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-sm-2 control-label">真实姓名</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="realName" placeholder="请输入真实姓名...">
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control" id="password" placeholder="请输入密码...">
                    </div>
                </div>
                <div class="form-group" id="areaDiv">
                    <label  class="col-sm-2 control-label">地区</label>
                </div>
                <div style="text-align: center;">
                    <button type="button" class="btn btn-primary" onclick="addUser();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                    <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/common/script.jsp"/>


<script>
    $(function () {
        initAreaList(0);
    })
    
    function initAreaList(id, obj) {
        // 先清除
        if (obj) {
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            type:"post",
            url:"/area/findChilds.jhtml",
            data:{"id":id},
            success:function (result) {
                if (result.code == 200) {
                    var v_areaList = result.data;
                    if (v_areaList.length > 0) {
                        var v_html = '<div class="col-sm-3"><select class="form-control" onchange="initAreaList(this.value, this)" name="areaSelect"><option value="-1">===请选择===</option>';
                        for (var i = 0; i < v_areaList.length; i++) {
                            var v_area = v_areaList[i];
                            v_html += "<option value='"+v_area.id+"'>"+v_area.areaName+"</option>";
                        }
                        v_html += "</select></div>";
                        $("#areaDiv").append(v_html);
                    }
                }
                console.log(result);
            }
        })
    }
    
    function addUser() {
        var v_param = {};
        v_param.userName = $("#userName").val();
        v_param.realName = $("#realName").val();
        v_param.password = $("#password").val();
        v_param.shengId = $("select[name='areaSelect']")[0].value;
        v_param.shiId = $($("select[name='areaSelect']")[1]).val();
        v_param.xianId = $($("select[name='areaSelect']")[2]).val();
        $.ajax({
            type:"post",
            url:"/user/add.jhtml",
            data:v_param,
            success:function (result) {
                if (result.code == 200) {
                    location.href="/user/toList.jhtml";
                }
            }
        })
    }
</script>
</body>
</html>
