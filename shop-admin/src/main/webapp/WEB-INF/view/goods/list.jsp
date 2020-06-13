<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/28 0028
  Time: 上午 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品列表</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="/common/nav.jsp"></jsp:include>

    <div class="container">

        <div class="row">

            <div class="col-md-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">商品查询</div>
                    <div class="panel-body">
                        <form class="form-horizontal" id="productForm">
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">商品名</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="productName" name="productName" placeholder="请输入商品名...">
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">品牌</label>
                                <div class="col-sm-4" id="brandSelectDiv">

                                </div>
                            </div>
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
                        商品列表
                        <button type="button" class="btn btn-success" onclick="toAdd();"><i class="glyphicon glyphicon-plus"></i> 增加</button>
                        <button type="button" class="btn btn-danger" onclick="deleteBatch();"><i class="glyphicon glyphicon-trash"></i>批量删除</button>
                        <button type="button" class="btn btn-success" onclick="exportExcel();"><i class="glyphicon glyphicon-download"></i>导出Excel</button>
                        <button type="button" class="btn btn-success" onclick="importExcel();"><i class="glyphicon glyphicon-upload"></i>导入Excel</button>
                        <button type="button" class="btn btn-success" onclick="exportWord();"><i class="glyphicon glyphicon-download"></i>导出Word</button>
                        <button type="button" class="btn btn-success" onclick="exportPdf();"><i class="glyphicon glyphicon-download"></i>导出PDF</button>

                    </div>
                    <table id="goodsTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th></th>
                            <th>选择</th>
                            <th>商品id</th>
                            <th>商品名</th>
                            <th>价格</th>
                            <th>库存</th>
                            <th>品牌名</th>
                            <th>分类名</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <tr>
                            <th></th>
                            <th>选择</th>
                            <th>商品id</th>
                            <th>商品名</th>
                            <th>价格</th>
                            <th>库存</th>
                            <th>品牌名</th>
                            <th>分类名</th>
                            <th>操作</th>
                        </tr>
                        </tfoot>
                    </table>
                </div>

            </div>
        </div>

    </div>

    <jsp:include page="/common/script.jsp"></jsp:include>
    <script>
        $(function () {
            initGoodsTable();
            initEvent();
            initBrandList();
        })
        
        function search() {
            var v_param = {};
            v_param.productName = $("#productName").val();
            v_param.brandId = $("#brandSelect").val();

            v_goodsTable.settings()[0].ajax.data = v_param;
            v_goodsTable.ajax.reload();
        }

        function initBrandList() {
            $.ajax({
                type:"post",
                url:"/brand/all.jhtml",
                success:function (result) {
                    if (result.code == 200) {
                        var v_html = "<select class='form-control' id='brandSelect' name='brandInfoId'><option value='-1'>===请选择===</option>";
                        var v_brandList = result.data;
                        for (var i = 0; i < v_brandList.length; i++) {
                            var v_brand = v_brandList[i];
                            v_html += "<option value='"+v_brand.id+"'>"+v_brand.brandName+"</option>";
                        }
                        v_html += "</select>";
                        $("#brandSelectDiv").html(v_html);
                    }
                }
            })
        }

        function initEvent() {
            $('#goodsTable tbody').on('click', 'td.details-control', function () {
                var tr = $(this).closest('tr');
                var row = v_goodsTable.row( tr );

                if ( row.child.isShown() ) {
                    // This row is already open - close it
                    row.child.hide();
                    tr.removeClass('shown');
                }
                else {
                    // Open this row
                    row.child(format(row.data()) ).show();
                    tr.addClass('shown');
                }
            } );
        }
        
        function toAdd() {
            location.href="/goods/toAdd.jhtml";
        }

        function format(row) {
            console.log(row);
            var v_html = "<div>";
            for (let sku of row.goodsList) {
                v_html += "<table class='table  table-bordered' style='width:48%;float: left;margin: 5px'>" +
                    "<tr>" +
                    "<td>SKU商品名:</td>" +
                    "<td>"+sku.productName+"</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>价格:</td>" +
                    "<td>"+sku.price+"</td>" +
                    "</tr>" +
                    "<tr>" +
                    "<td>库存:</td>" +
                    "<td>"+sku.stock+"</td>"+
                    "</tr>" +
                    "</table>"
            }
            v_html += "</div>";
            return v_html;
        }

        var v_goodsTable;
        function initGoodsTable() {
            v_goodsTable = $('#goodsTable').DataTable({
                "language": {
                    "url": "/js/DataTables/Chinese.json"
                },
                // 是否允许检索
                "searching": false,
                "processing": true,
                "lengthMenu": [5,10,15,20],
                "serverSide": true,
                "ajax": {
                    "url": "/goods/list.jhtml",
                    "type": "POST"
                },
                "columns": [
                    {
                        "className":      'details-control',
                        "orderable":      false,
                        "data":           null,
                        "defaultContent": ''
                    },
                    {
                        "data":"goodsCommon.id",
                        "render": function (data, type, row, meta) {
                            return "<input type='checkbox' name='ids' value='"+data+"'/>"
                        }
                    },
                    { "data": "goodsCommon.id" },
                    { "data": "goodsCommon.productName" },
                    { "data": "goodsCommon.price" },
                    { "data": "goodsCommon.stock" },
                    { "data": "goodsCommon.brandName" },
                    { "data": "goodsCommon.cateName" },
                    {
                        "data": "goodsCommon.id",
                        "render": function (data, type, row, meta) {
                            return ' <button type="button" class="btn btn-warning" onclick="toAdd();"><i class="glyphicon glyphicon-pencil"></i>编辑</button>\n' +
                                '<button type="button" class="btn btn-danger" onclick="deleteBatch();"><i class="glyphicon glyphicon-trash"></i>删除</button>'
                        }
                    }
                ]
            });
        }
    </script>
</body>
</html>
