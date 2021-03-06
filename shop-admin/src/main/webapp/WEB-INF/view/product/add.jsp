<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/11 0011
  Time: 下午 17:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet"
          href="/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">

    <link type="text/css" rel="stylesheet" href="/js/fileinput5/css/fileinput.css" />

</head>
<body>
<jsp:include page="/common/nav.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal">
                <div class="form-group">
                    <label  class="col-sm-2 control-label">商品名</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="productName" placeholder="请输入商品名...">
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-sm-2 control-label">主图</label>
                    <div class="col-sm-10">
                       <input type="file" id="mainImage" name="imageInfo">
                        <input type="text" id="mainImagePath"/>
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-sm-2 control-label">商品价格</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="price" placeholder="请输入商品价格...">
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-sm-2 control-label">品牌</label>
                    <div class="col-sm-10" id="brandSelectDiv">

                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-sm-2 control-label">创建时间</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="createDate" placeholder="请选择时间...">
                    </div>
                </div>
                <div style="text-align: center;">
                    <button type="button" class="btn btn-primary" onclick="addProduct();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                    <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="/js/bootstrap3/js/bootstrap.min.js"></script>
<script src="/js/bootstrap-datetimepicker/js/moment-with-locales.js"></script>
<script src="/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="/js/fileinput5/js/fileinput.min.js"></script>
<script src="/js/fileinput5/js/locales/zh.js"></script>

<script>

    $(function () {
        initBrandList();
        initDate();
        initMainImage();
    })
    
    function initMainImage() {
        var s = {
            language: 'zh',
            uploadUrl: "/file/uploadImage.jhtml",
            showUpload : false,
            showRemove : false,
            allowedFileExtensions : [ 'jpg', 'png', 'jpeg', 'gif'] //上传的文件的后缀名
        };
        $("#mainImage").fileinput(s).on("fileuploaded", function (event, r, previewId, index){
            var result = r.response;
            if (result.code == 200) {
                // 赋值
                $("#mainImagePath").val(result.data);
            }
        });
    }

    function initDate() {
        $('#createDate').datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        });
    }
    
    function initBrandList() {
        $.ajax({
            type:"post",
            url:"/brand/all.jhtml",
            success:function (result) {
                if (result.code == 200) {
                    var v_html = "<select class='form-control' id='brandSelect'><option value='-1'>===请选择===</option>";
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

    function addProduct() {
        var v_productName = $("#productName").val();
        var v_price = $("#price").val();
        var v_brandId = $("#brandSelect").val();
        var v_createDate = $("#createDate").val();

        var v_param = {};
        v_param.productName = v_productName;
        v_param.price = v_price;
        v_param.brandId = v_brandId;
        v_param.createDate = v_createDate;
        v_param.mainImagePath = $("#mainImagePath").val();

        $.ajax({
            type:"post",
            url:"/product/add.jhtml",
            data:v_param,
            success:function (result) {
                // 在浏览器端打印信息
                console.log(result);
                if (result.code == 200) {
                    location.href="/product/toList.jhtml";
                }
            }
        })
    }
</script>
</body>
</html>
