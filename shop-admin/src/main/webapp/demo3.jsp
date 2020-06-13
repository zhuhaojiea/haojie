<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/12 0012
  Time: 下午 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link href="/js/DataTables/DataTables-1.10.20/css/dataTables.bootstrap.min.css" rel="stylesheet">
</head>
<body>
<table id="productTable" class="table table-striped table-bordered" style="width:100%">
    <thead>
    <tr>
        <th>商品id</th>
        <th>商品名</th>
        <th>价格</th>
    </tr>
    </thead>

    <tfoot>
    <tr>
        <th>商品id</th>
        <th>商品名</th>
        <th>价格</th>
    </tr>
    </tfoot>
</table>

<script src="/js/jquery.min.js"></script>
<script src="/js/DataTables/DataTables-1.10.20/js/jquery.dataTables.min.js"></script>
<script src="/js/DataTables/DataTables-1.10.20/js/dataTables.bootstrap.min.js"></script>

<script>
    $(function () {
        $('#productTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],
            "serverSide": true,
            "ajax": {
                "url": "/product/list.jhtml",
                "type": "POST"
            },
            "columns": [
                { "data": "id" },
                { "data": "productName" },
                { "data": "price" }
            ]
        });
    })
</script>
</body>
</html>
