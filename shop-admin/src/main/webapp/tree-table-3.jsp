<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/21 0021
  Time: 上午 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"/>
    <title>Title</title>
</head>
<body>
<%--treetable = tree[保证展示的层级关系 data-tt-id="201" data-tt-parent-id="200"] + table[展示多列数据]--%>
    <table id="areaTable" class="table table-bordered">
        <tr>
            <th>地区名</th>
            <th>邮编</th>
            <th>操作</th>
        </tr>
        <tr data-tt-id="0">
            <td>全国</td>
            <td>100</td>
            <td><button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button></td>
        </tr>

        <tr data-tt-id="1" data-tt-parent-id="0">
            <td>河南</td>
            <td>111</td>
            <td><button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button></td>
        </tr>

        <tr data-tt-id="100" data-tt-parent-id="1">
            <td>河南1</td>
            <td>112</td>
            <td><button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button></td>
        </tr>

        <tr data-tt-id="101" data-tt-parent-id="100">
            <td>河南11</td>
            <td>113</td>
            <td><button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button></td>
        </tr>
        <tr data-tt-id="2" data-tt-parent-id="0">
            <td>河北</td>
            <td>211</td>
            <td><button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button></td>
        </tr>
        <tr data-tt-id="200" data-tt-parent-id="2">
            <td>河北1</td>
            <td>212</td>
            <td><button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button></td>
        </tr>
        <tr data-tt-id="201" data-tt-parent-id="200">
            <td>河北11</td>
            <td>2121</td>
            <td><button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button></td>
        </tr>
        <tr data-tt-id="202" data-tt-parent-id="2">
            <td>河北2</td>
            <td>213</td>
            <td><button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button></td>
        </tr>
        <tr data-tt-id="203" data-tt-parent-id="202">
            <td>河北21</td>
            <td>2131</td>
            <td><button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button></td>
        </tr>
    </table>

   <jsp:include page="/common/script.jsp"></jsp:include>
    <script>
        $("#areaTable").treetable({expandable: true})
    </script>
</body>
</html>
