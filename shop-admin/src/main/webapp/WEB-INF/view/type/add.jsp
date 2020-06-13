<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/16 0016
  Time: 上午 10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"/>
    <title>添加类型</title>
</head>
<body>
    <jsp:include page="/common/nav.jsp"/>

    <div class="container">
        <form>
            <div class="row">
                <div class="col-md-12" style="margin: 5px">
                        <div class="form-group" >
                            <label  class="col-sm-2 control-label">类型名</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="typeName" placeholder="请输入类型名...">
                            </div>
                        </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">规格列表</div>
                            <table class="table table-bordered" id="specTable">
                               <tbody>
                               </tbody>
                            </table>
                    </div>

                </div>
                <div class="col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">品牌列表</div>
                            <table class="table table-bordered" id="brandTable">
                                <tbody>
                                </tbody>
                            </table>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-primary">
                        <div class="panel-heading">属性列表
                            <button type="button" class="btn btn-success" onclick="addAttr();"><i class="glyphicon glyphicon-plus"></i>添加属性</button>
                        </div>
                        <table class="table table-bordered" id="attrTable">
                            <thead>
                            <tr>
                                <th>属性名</th>
                                <th>属性值</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><input type="text" class="form-control" name="attrName"></td>
                                    <td><input type="text" class="form-control" name="attrValue"></td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div style="text-align: center">
                <button type="button" class="btn btn-primary" onclick="saveType();"><i class="glyphicon glyphicon-ok"></i> 提交</button>
                <button type="reset" class="btn btn-primary"><i class="glyphicon glyphicon-ok"></i> 重置</button>
            </div>
        </form>
    </div>

    <textarea id="trDiv" style="display: none;">
         <tr>
                                    <td><input type="text" class="form-control" name="attrName"></td>
                                    <td><input type="text" class="form-control" name="attrValue"></td>
                                    <td><button type="button" class="btn btn-link" onclick="deleteAttr(this)">删除</button></td>
         </tr>
    </textarea>

    <jsp:include page="/common/script.jsp"/>
    <script>
        $(function () {
            initSpecTable();
            initBrandTable();
        })
        
        function addAttr() {
            $("#attrTable tbody").append($("#trDiv").val());
        }

        function deleteAttr(obj) {
            $(obj).parents("tr").remove();
        }
        
        function initSpecTable() {
            $.ajax({
                type:"post",
                url:"/spec/all.jhtml",
                success:function (result) {
                    if (result.code == 200) {
                        var v_specArr = result.data;
                        var v_total = v_specArr.length;
                        var v_tdSize = 3;
                        var v_trCount = v_total%v_tdSize==0?v_total/v_tdSize:Math.ceil(v_total/v_tdSize);
                        var v_html = "";
                        for (var i = 0; i < v_trCount; i++) {
                            v_html += "<tr>";
                            // 每行的开始下标
                            var v_index = v_tdSize*i;
                            for (var j = 0; j < v_tdSize; j++) {
                                var specInfo = v_specArr[v_index+j];
                                if (specInfo) {
                                    v_html += "<td><input type='checkbox' value='"+specInfo.id+"' name='specIds'>"+specInfo.specName+"</td>"
                                } else {
                                    v_html += "<td></td>"
                                }
                            }
                            v_html += "</tr>";
                        }
                        console.log(v_html);
                        $("#specTable tbody").html(v_html);
                    }
                }
            })
        }
        
        
        function initBrandTable() {
            $.ajax({
                type:"post",
                url:"/brand/all.jhtml",
                success:function (result) {
                    if (result.code == 200) {
                        var v_brandArr = result.data;
                        var v_total = v_brandArr.length;
                        var v_tdSize = 5;
                        var v_trCount = v_total%v_tdSize==0?v_total/v_tdSize:Math.ceil(v_total/v_tdSize);
                        var v_html = "";
                        for (var i = 0; i < v_trCount; i++) {
                            v_html += "<tr>";
                            // 每行的开始下标
                            var v_index = v_tdSize*i;
                            for (var j = 0; j < v_tdSize; j++) {
                                var brandInfo = v_brandArr[v_index+j];
                                if (brandInfo) {
                                    v_html += "<td><input type='checkbox' value='"+brandInfo.id+"' name='brandIds'>"+brandInfo.brandName+"</td>"
                                } else {
                                    v_html += "<td></td>"
                                }
                            }
                            v_html += "</tr>";
                        }
                        console.log(v_html);
                        $("#brandTable tbody").html(v_html);
                    }
                }
            })
        }
        
        
        function saveType() {
            var v_typeName = $("#typeName").val();
            var v_brandIdArr = [];
            var v_specIdArr = [];
            var v_attrNameArr = [];
            var v_attrValueArr = [];
            $("input[name='specIds']:checkbox:checked").each(function () {
                v_specIdArr.push(this.value);
            })
            $("input[name='brandIds']:checkbox:checked").each(function () {
                v_brandIdArr.push(this.value);
            })
            // 获取所有的属性名
            $("input[name='attrName']").each(function () {
                v_attrNameArr.push(this.value);
            })
            // 获取所有的属性值
            $("input[name='attrValue']").each(function () {
                v_attrValueArr.push(this.value);
            })
            var v_brandIds = v_brandIdArr.join(",");
            var v_specIds = v_specIdArr.join(",");
            var v_param = {};
            v_param.typeName = v_typeName;
            v_param.brandIds = v_brandIds;
            v_param.specIds = v_specIds;
            v_param.attrNames = v_attrNameArr.join(",");
            v_param.attrValues = v_attrValueArr.join(";");

            $.ajax({
                type:"post",
                url:"/type/add.jhtml",
                data:v_param,
                success:function (result) {
                    if (result.code == 200) {
                        location.href = "/type/toList.jhtml";
                    }
                }
            })


        }
    </script>
</body>
</html>
