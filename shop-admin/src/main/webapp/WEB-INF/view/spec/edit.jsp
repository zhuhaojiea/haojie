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
    <title>修改规格</title>
</head>
<body>
    <jsp:include page="/common/nav.jsp"/>

    <button type="button" class="btn btn-primary" onclick="saveSpec();"><i class="glyphicon glyphicon-ok"></i> 提交</button>

    <table class="table table-bordered" id="specTable">
        <tr>
            <td style="width: 100px">规格名:</td>
            <td style="width: 200px"><input type="text" class="form-control" style="width: 200px" id="specName"/> </td>
            <td style="width: 120px">规格排序:</td>
            <td style="width: 100px"><input type="text" class="form-control" style="width: 100px" id="specNameSort"/> </td>
            <td><button type="button" class="btn btn-primary" onclick="addSpecValue(this);"><i class="glyphicon glyphicon-plus"></i> 增加规格值</button></td>
        </tr>

    </table>





    <textarea style="display: none;" id="specValueDiv">
        <tr>
            <td style="width: 100px">规格值:</td>
            <td style="width: 200px"><input type="text" class="form-control" style="width: 200px" name="specValue" value="##specValue##"/> </td>
            <td style="width: 120px">规格值排序:</td>
            <td style="width: 100px"><input type="text" class="form-control" style="width: 100px" name="specValueSort" value="##specValueSort##"/> </td>
            <td><button type="button" class="btn btn-danger" onclick="deleteSpecValue(this);"><i class="glyphicon glyphicon-trash"></i> 删除规格值</button></td>
        </tr>
    </textarea>

    <jsp:include page="/common/script.jsp"/>

    <script>

        $(function () {
            initSpec();
        })

        function initSpec() {
            var v_id = '${param.id}';
            $.ajax({
                type:"post",
                url:"/spec/find.jhtml?id="+v_id,
                success:function (result) {
                    if (result.code == 200) {
                        console.log(result);
                        var v_data = result.data;
                        $("#specName").val(v_data.spec.specName);
                        $("#specNameSort").val(v_data.spec.sort);
                        var v_specValueArr = v_data.specValues;
                        var v_html = $("#specValueDiv").val();
                        for (var i = 0; i < v_specValueArr.length; i++) {
                            var v_specValueInfo = v_specValueArr[i];
                            var v_result = v_html.replace(/##specValue##/g, v_specValueInfo.specValue)
                                .replace(/##specValueSort##/g, v_specValueInfo.sort);
                            $("#specTable tbody").append(v_result);
                        }
                    }
                }
            })
        }

        function addSpecValue(obj) {
            var v_html = $("#specValueDiv").val();
            $(obj).parents("tbody").append(v_html.replace(/##specValue##/g, "").replace(/##specValueSort##/g, ""));
        }
        
        function deleteSpecValue(obj) {
            $(obj).parents("tr").remove();
        }
        
        function saveSpec() {
            var v_id = '${param.id}';
            // var v_specNameArr = [];
            // var v_specNameSortArr = [];
            //
            // $("input[name='specName']").each(function () {
            //     if (this.value.length > 0) {
            //         v_specNameArr.push(this.value);
            //     }
            //
            // })
            //
            // $("input[name='specNameSort']").each(function () {
            //     if (this.value.length > 0) {
            //         v_specNameSortArr.push(this.value);
            //     }
            //
            // })

            var v_specName = $("#specName").val();
            var v_specNameSort = $("#specNameSort").val();
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
            var v_param = {};
            v_param.specName = v_specName;
            v_param.specNameSort = v_specNameSort;
            v_param.id = v_id;

            v_param.specValues = v_specValues;

            $.ajax({
                "type":"post",
                data:v_param,
                url:"/spec/update.jhtml",
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
