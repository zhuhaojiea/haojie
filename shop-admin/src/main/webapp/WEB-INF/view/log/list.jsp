<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/1 0001
  Time: 下午 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"/>
    <title>日志管理</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"/>

<div class="container">

    <div class="row">

        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">日志查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="userForm">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名...">
                            </div>
                        </div>
                        <div class="form-group" id="areaDiv">
                            <label  class="col-sm-2 control-label">地区</label>

                        </div>
                        <input type="text" id="shengId" name="shengId"/>
                        <input type="text" id="shiId" name="shiId"/>
                        <input type="text" id="xianId" name="xianId"/>
                        <div style="text-align: center;">
                            <button type="button" class="btn btn-primary" onclick="search();"><i class="glyphicon glyphicon-search"></i> 查询</button>
                            <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    日志列表
                    <button type="button" class="btn btn-success" onclick="toAdd();"><i class="glyphicon glyphicon-plus"></i> 增加</button>
                    <button type="button" class="btn btn-danger" onclick="deleteBatch();"><i class="glyphicon glyphicon-trash"></i>批量删除</button>
                    <button type="button" class="btn btn-success" onclick="exportExcel();"><i class="glyphicon glyphicon-download"></i>导出Excel</button>
                    <button type="button" class="btn btn-success" onclick="importExcel();"><i class="glyphicon glyphicon-upload"></i>导入Excel</button>
                    <button type="button" class="btn btn-success" onclick="exportWord();"><i class="glyphicon glyphicon-download"></i>导出Word</button>
                    <button type="button" class="btn btn-success" onclick="exportPdf();"><i class="glyphicon glyphicon-download"></i>导出PDF</button>

                </div>
                <table id="logTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <th>用户名</th>
                        <th>真实姓名</th>
                        <th>操作信息</th>
                        <th>操作时间</th>
                        <th>状态</th>
                        <th>描述</th>
                        <th>参数信息</th>
                    </tr>
                    </thead>

                    <tfoot>
                    <tr>
                        <th>选择</th>
                        <th>用户名</th>
                        <th>真实姓名</th>
                        <th>操作信息</th>
                        <th>操作时间</th>
                        <th>状态</th>
                        <th>描述</th>
                        <th>参数信息</th>
                    </tr>
                    </tfoot>
                </table>
            </div>

        </div>
    </div>

</div>

<jsp:include page="/common/script.jsp"/>
<script>
    $(function () {
        initTable();
    })

    var logTable;
    function initTable() {
        logTable = $('#logTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],
            "serverSide": true,
            "ajax": {
                "url": "/log/list.jhtml",
                "type": "POST"
            },
            "columns": [
                {
                    "data":"id",
                    "render": function (data, type, row, meta) {
                        return "<input type='checkbox' name='ids' value='"+data+"'/>"
                    }
                },
                { "data": "userName" },
                { "data": "realName" },
                { "data": "info" },
                { "data": "insertTime" },
                { "data": "status",
                    "render": function (data, type, row, meta) {
                        return data==1?"成功":"失败";
                    }
                },
                { "data": "content" },
                { "data": "paramInfo" }

            ]
        });
    }
</script>
</body>
</html>
