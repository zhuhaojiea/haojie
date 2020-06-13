<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/13 0013
  Time: 下午 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"/>
    <title>Title</title>
</head>
<body>

<button type="button" class="btn btn-primary" onclick="addSpec();"><i class="glyphicon glyphicon-plus"></i> 增加规格</button>
<button type="button" class="btn btn-primary" onclick="addSpec();"><i class="glyphicon glyphicon-ok"></i> 提交</button>

<table class="table table-bordered">
    <tbody>
        <tr>
            <td style="width:120px;">规格名:</td>
            <td style="width: 220px;"><input type="text" class="form-control" style="width:200px" name="specName"/></td>
            <td style="width:120px;">排序:</td>
            <td style="width:220px"/> <input type="text" class="form-control" style="width:100px" name="specNameSort"></td>
            <td><button type="button" class="btn btn-primary" onclick="addSpecValue(this);"><i class="glyphicon glyphicon-plus"></i> 增加规格值</button></td>
        </tr>
    </tbody>
</table>


<div style="display: none" id="specTemplate">
    <table class="table table-bordered">
        <tbody>
        <tr>
            <td>
                <button type="button" class="btn btn-danger" onclick="deleteSpec(this);"><i class="glyphicon glyphicon-trash"></i> 删除</button>
            </td>
        </tr>
        <tr>
            <td style="width:100px;">规格名:</td>
            <td style="width: 220px;"><input type="text" class="form-control" style="width:200px" name="specName"/></td>
            <td style="width:100px;">排序:</td>
            <td style="width:120px"/> <input type="text" class="form-control" style="width:100px" name="specNameSort"></td>
            <td><button type="button" class="btn btn-primary" onclick="addSpecValue(this);"><i class="glyphicon glyphicon-plus"></i> 增加规格值</button></td>
        </tr>
        </tbody>
    </table>
</div>

<textarea style="display: none" id="specValueTemplate">
    <tr>
        <td style="width:120px;">规格值排序:</td>
        <td style="width: 220px;"><input type="text" class="form-control" style="width:200px" name="specValueSort"/></td>
        <td style="width:120px;">规格值:</td>
        <td style="width:220px" > <input type="text" class="form-control" style="width:200px" name="specValue"></td>
        <td>
            <button type="button" class="btn btn-danger" onclick="deleteSpecValue(this);"><i class="glyphicon glyphicon-trash"></i> 删除</button>
        </td>
    </tr>
</textarea>
<jsp:include page="/common/script.jsp"/>


<script>
    function addSpec() {
        console.log($("body table").html());
        $("body > table").last().after($("#specTemplate").html());
    }
    
    function addSpecValue(obj) {
         $(obj).parents("tbody").append($("#specValueTemplate").val());
        console.log($("#specValueTemplate").val())
    }
    
    function deleteSpecValue(obj) {
        $(obj).parents("tr").remove();
    }
    
    function deleteSpec(obj) {
        $(obj).parents("table").remove();
    }
</script>
</body>
</html>
