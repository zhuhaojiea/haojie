<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/14 0014
  Time: 上午 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"/>
    <title>增加规格</title>
</head>
<body>
    <jsp:include page="/common/nav.jsp"/>

    <button type="button" class="btn btn-primary" onclick="addSpec();"><i class="glyphicon glyphicon-plus"></i> 增加规格</button>
    <button type="button" class="btn btn-primary" onclick="saveSpec();"><i class="glyphicon glyphicon-ok"></i> 提交</button>

    <table class="table table-bordered" id="specTable">
        <tr>
            <td style="width: 100px">规格名:</td>
            <td style="width: 200px"><input type="text" class="form-control" style="width: 200px" name="specName"/> </td>
            <td style="width: 120px">规格排序:</td>
            <td style="width: 100px"><input type="text" class="form-control" style="width: 100px" name="specNameSort"/> </td>
            <td><button type="button" class="btn btn-primary" onclick="addSpecValue(this);"><i class="glyphicon glyphicon-plus"></i> 增加规格值</button></td>
        </tr>

    </table>




    <div style="display: none;" id="specTableDiv">

        <table class="table table-bordered">
            <tr>
                <td><button type="button" class="btn btn-danger" onclick="deleteSpecTable(this);"><i class="glyphicon glyphicon-trash"></i>删除</button></td>
            </tr>
            <tr>
                <td style="width: 100px">规格名:</td>
                <td style="width: 200px"><input type="text" class="form-control" style="width: 200px" name="specName"/> </td>
                <td style="width: 120px">规格排序:</td>
                <td style="width: 100px"><input type="text" class="form-control" style="width: 100px" name="specNameSort"/> </td>
                <td><button type="button" class="btn btn-primary" onclick="addSpecValue(this);"><i class="glyphicon glyphicon-plus"></i> 增加规格值</button></td>
            </tr>
        </table>
    </div>

    <textarea style="display: none;" id="specValueDiv">
        <tr>
            <td style="width: 100px">规格值:</td>
            <td style="width: 200px"><input type="text" class="form-control" style="width: 200px" name="specValue"/> </td>
            <td style="width: 120px">规格值排序:</td>
            <td style="width: 100px"><input type="text" class="form-control" style="width: 100px" name="specValueSort"/> </td>
            <td><button type="button" class="btn btn-danger" onclick="deleteSpecValue(this);"><i class="glyphicon glyphicon-trash"></i> 删除规格值</button></td>
        </tr>
    </textarea>

    <jsp:include page="/common/script.jsp"/>

    <script>
        function addSpec() {
            $("body>table").last().after($("#specTableDiv").html());
        }
        
        function deleteSpecTable(obj) {
            $(obj).parents("table").remove();
        }
        
        function addSpecValue(obj) {
            $(obj).parents("tbody").append($("#specValueDiv").val());
        }
        
        function deleteSpecValue(obj) {
            $(obj).parents("tr").remove();
        }
        
        function saveSpec() {
            var v_specNameArr = [];
            var v_specNameSortArr = [];

            $("input[name='specName']").each(function () {
                if (this.value.length > 0) {
                    v_specNameArr.push(this.value);
                }

            })

            $("input[name='specNameSort']").each(function () {
                if (this.value.length > 0) {
                    v_specNameSortArr.push(this.value);
                }

            })

            var v_specNames = v_specNameArr.join(",");
            var v_specNameSorts = v_specNameSortArr.join(",");
            var v_specValues = "";
            $("body>table").each(function () {
                var v_specValueArr = [];
                var v_specValueSortArr = [];
                $(this).find("input[name='specValue']").each(function () {
                        v_specValueArr.push(this.value);
                })

                $(this).find("input[name='specValueSort']").each(function () {
                        v_specValueSortArr.push(this.value);
                })
                var v_temp = "";
                for (var i = 0; i < v_specValueArr.length; i++) {
                    v_temp += "," + v_specValueArr[i]+"="+v_specValueSortArr[i];
                }
                if (v_temp.length > 0) {
                    v_temp = v_temp.substring(1);
                }
                v_specValues += ";"+v_temp;
            })
            if (v_specValues.length > 0) {
                v_specValues = v_specValues.substring(1);
            }
            console.log(v_specNames);
            console.log(v_specNameSorts);
            console.log(v_specValues);

            var v_param = {};
            v_param.specNames = v_specNames;
            v_param.specNameSorts = v_specNameSorts;
            v_param.specValues = v_specValues;

            $.ajax({
                "type":"post",
                data:v_param,
                url:"/spec/add.jhtml",
                success:function (result) {
                    if (result.code == 200) {
                        // 跳转
                        location.href = "/spec/toList.jhtml";
                    }
                }
            })
        }
    </script>
</body>
</html>
