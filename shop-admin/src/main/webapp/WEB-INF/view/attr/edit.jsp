<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/29 0029
  Time: 下午 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"/>
    <title>属性编辑</title>
</head>
<body>
    <jsp:include page="/common/nav.jsp"/>
    <div class="container">
        <div class="row">
            <div class="col-md-12" style="margin: 5px;">
                <div class="form-group" >
                    <label  class="col-sm-2 control-label">属性名</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="attrName" >
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">属性值列表
                        <button type="button" class="btn btn-success" onclick="addAttrValue();"><i class="glyphicon glyphicon-plus"></i>添加属性值</button>
                    </div>
                    <table class="table table-bordered" id="attrValueTable">
                        <thead>
                        <tr>
                            <th>删除</th>
                            <th>属性值</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div style="text-align: center">
            <button type="button" class="btn btn-primary" onclick="updateAttr();"><i class="glyphicon glyphicon-ok"></i> 提交</button>
            <button type="reset" class="btn btn-primary"><i class="glyphicon glyphicon-ok"></i> 重置</button>
        </div>
    </div>

    <textarea id="attrValueDiv" style="display: none">
         <tr>
                            <td><input type="checkbox" name="attrValueIds"/> </td>
                            <td><input type="text" class="form-control" name="attrValue" value="##attrValue##"></td>
                            <td></td>
         </tr>
    </textarea>

    <textarea id="attrValueTr" style="display: none">
         <tr>
                            <td></td>
                            <td><input type="text" class="form-control" name="attrValue"></td>
                            <td><button type="button" class="btn btn-link" onclick="deleteAttrValue(this)">删除</button></td>
         </tr>
    </textarea>
    
    <jsp:include page="/common/script.jsp"/>
<script>
    $(function () {
        initAttr();
    })
    
    

    var v_attrId = '${param.attrId}';
    var v_typeId = '${param.typeId}';
    
    function addAttrValue() {
        $("#attrValueTable tbody").append($("#attrValueTr").val());
    }
    
    function deleteAttrValue(obj) {
        $(obj).parents("tr").remove();
    }
    
    function updateAttr() {
        var v_param = {};
        v_param.attrId = v_attrId;
        v_param.attrName = $("#attrName").val();
        v_param.typeId = v_typeId;
        var v_attrValueArr = [];
        $("input[name='attrValue']").each(function () {
            // 如果当前行没有复选框 或者 有复选框但是复选框没被选中
            var v_checkboxArr = $(this).parents("tr").find("input[name='attrValueIds']");
            if (!v_checkboxArr[0] || !v_checkboxArr[0].checked) {
                v_attrValueArr.push(this.value);
            }
        })
        v_param.attrValues = v_attrValueArr.join(",");
        $.ajax({
            type:"post",
            url:"/attr/update.jhtml",
            data:v_param,
            success:function (result) {
                if (result.code == 200) {
                    location.href = "/type/toList.jhtml";
                }
            }
        })

    }

    function initAttr() {
        $.ajax({
            type:"post",
            url:"/attr/find.jhtml?id="+v_attrId,
            success:function (result) {
                if (result.code == 200) {
                    console.log(result);
                   var v_attr = result.data.attr;
                   var v_attrValueArr = result.data.attrValueList;
                   $("#attrName").val(v_attr.attrName);
                   var v_attrHtml = $("#attrValueDiv").val();
                    for (var i = 0; i < v_attrValueArr.length; i++) {
                        var v_attrValue = v_attrValueArr[i];
                        var v_result = v_attrHtml.replace(/##attrValue##/g, v_attrValue.attrValue);
                        $("#attrValueTable tbody").append(v_result);
                    }
                }
            }
        })
    }
</script>
</body>
</html>
