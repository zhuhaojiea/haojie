<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/13 0013
  Time: 上午 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/common/head.jsp"/>
  <title>类型管理</title>

</head>
<body>
<jsp:include page="/common/nav.jsp"/>
<div class="container">

    <div class="row">

    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    类型列表
                    <button type="button" class="btn btn-success" onclick="toAdd();"><i class="glyphicon glyphicon-plus"></i> 增加</button>
                    <button type="button" class="btn btn-danger" onclick="deleteBatch();"><i class="glyphicon glyphicon-trash"></i>批量删除</button>
                    <button type="button" class="btn btn-success" onclick="exportExcel();"><i class="glyphicon glyphicon-download"></i>导出Excel</button>
                    <button type="button" class="btn btn-success" onclick="importExcel();"><i class="glyphicon glyphicon-upload"></i>导入Excel</button>
                    <button type="button" class="btn btn-success" onclick="exportWord();"><i class="glyphicon glyphicon-download"></i>导出Word</button>
                    <button type="button" class="btn btn-success" onclick="exportPdf();"><i class="glyphicon glyphicon-download"></i>导出PDF</button>

                </div>
                    <table id="typeTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>类型名</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <tr>
                            <th>选择</th>
                            <th>类型名</th>
                            <th>操作</th>
                        </tr>
                        </tfoot>
                    </table>
            </div>

        </div>
    </div>

</div>

<div id="execelDiv" style="display: none;">
    <form>
        <input type="file" id="execelFile" name="fileInfo"/>
        <input type="text" id="filePath"/>
    </form>
</div>


<jsp:include page="/common/script.jsp"/>

<script>
    var v_execelDivHtml = "";
    $(function () {
        initTable();
    })



    
    function exportWord() {
        var v_productForm = document.getElementById("productForm");
        v_productForm.action="/product/exportWord.jhtml";
        v_productForm.method="post";
        v_productForm.submit();
    }



    function initFile() {
        var s = {
            language: 'zh',
            uploadUrl: "/file/uploadFile.jhtml",
            showUpload : false,
            showRemove : false,
            allowedFileExtensions : [ 'xls', 'xlsx'] //上传的文件的后缀名
        };
        $("#execelFile").fileinput(s).on("fileuploaded", function (event, r, previewId, index){
            var result = r.response;
            if (result.code == 200) {
                $("#filePath", v_excelDlg).val(result.data);
            }
        });
    }
    var v_excelDlg;
    function importExcel() {
        // 初始化fileinput
        initFile();
        v_excelDlg = bootbox.dialog({
            title: '导入数据',
            message:$("#execelDiv form"),
            size:"large",
            buttons: {
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确认',
                    className: 'btn-primary',
                    callback: function(){
                        var v_path = $("#filePath", v_excelDlg).val();
                        $.ajax({
                            type:"post",
                            url:"/product/importExcel.jhtml",
                            data:{"path":v_path},
                            success:function (result) {
                                if (result.code == 200) {
                                    search();
                                }
                            }
                        })
                    }
                },
                cancel: {
                    label: '<span class="glyphicon glyphicon-remove"></span>取消',
                    className: 'btn-danger'
                }
            }
        });
        // 还原
         $("#execelDiv").html(v_execelDivHtml);
    }
    
    function exportPdf() {
        var v_productForm = document.getElementById("productForm");
        v_productForm.action="/product/exportPdf.jhtml";
        v_productForm.method="post";
        v_productForm.submit();
    }
    
    
    

    function deleteBatch() {
        if (v_ids.length > 0) {
            bootbox.confirm({
                message: "你确定删除吗?",
                size: 'small',
                title: "提示信息",
                buttons: {
                    confirm: {
                        label: '<span class="glyphicon glyphicon-ok"></span>确定',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '<span class="glyphicon glyphicon-remove"></span>取消',
                        className: 'btn-danger'
                    }
                },
                callback: function (result) {
                    if (result) {
                        // 发送请求，进行删除
                        $.ajax({
                            type:"post",
                            url:"/product/deleteBatch.jhtml",
                            data:{"ids":v_ids},
                            success:function (result) {
                                if (result.code == 200) {
                                    // 刷新
                                    search();
                                }
                            }
                        })
                    }
                }
            })
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span><b><font color='red'> 至少选择一个</font></b>",
                size: 'small',
                title: "提示信息"
            });
        }
    }
    
    function toAdd() {
        location.href="/type/toAdd.jhtml";
    }
    
    function exportExcel() {
       var v_userForm = document.getElementById("userForm");
        v_userForm.action="/user/exportExcel.jhtml";
        v_userForm.method="post";
        // 给三个隐藏域赋值
        $("#shengId").val($("select[name='areaSelect']")[0].value);
        $("#shiId").val($($("select[name='areaSelect']")[1]).val());
        $("#xianId").val($($("select[name='areaSelect']")[2]).val());
        v_userForm.submit();
    }


    function initDate(elementId) {
        $('#'+elementId).datetimepicker({
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
    
    function isExist(id) {
        for (var i = 0; i < v_ids.length; i++) {
            if (id == v_ids[i]) {
                return true;
            }
        }
        return false;
    }

    var typeTable;
    function initTable() {
        typeTable = $('#typeTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],
            "serverSide": true,
            "ajax": {
                "url": "/type/list.jhtml",
                "type": "POST"
            },
            "columns": [
                {
                    "data":"id",
                    "render": function (data, type, row, meta) {
                        return "<input type='checkbox' name='ids' value='"+data+"'/>"
                    }
                },
                { "data": "typeName" },

                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        var v_isHot = row.isHot;
                        var v_text = "";
                        var v_icon = "";
                        var v_color = "";
                        var v_hot_status;

                        var v_status = row.status;
                        var v_status_text = "";
                        var v_status_icon = "";
                        var v_status_color = "";
                        var v_up_status;

                        if (v_isHot == 1) {
                            // 非热销
                            v_text = "非热销";
                            v_hot_status = 0;
                            v_icon = "glyphicon glyphicon-hand-down";
                            v_color = "btn btn-default";
                        } else {
                            v_text = "热销";
                            v_hot_status = 1;
                            v_icon = "glyphicon glyphicon-fire";
                            v_color = "btn btn-success";
                        }
                        if (v_status == 1) {
                            // 下架
                            v_status_text = "下架";
                            v_status_icon = "glyphicon glyphicon-arrow-down";
                            v_status_color = "btn btn-default";
                            v_up_status = 0;
                        } else {
                            // 上架
                            v_status_text = "上架";
                            v_status_icon = "glyphicon glyphicon-arrow-up";
                            v_status_color = "btn btn-success";
                            v_up_status = 1;
                        }
                       return "<button type=\"button\" class=\"btn btn-warning\" onclick=\"editType('"+data+"');\"><i class=\"glyphicon glyphicon-pencil\"></i>修改</button>\n" +
                           " <button type=\"button\" class=\"btn btn-danger\" onclick=\"deleteType('"+data+"')\"><i class=\"glyphicon glyphicon-trash\"></i>删除</button>" +
                           " <button type=\"button\" class=\""+v_color+"\" onclick=\"updateHotStatus('"+data+"','"+v_hot_status+"')\"><i class=\""+v_icon+"\"></i>"+v_text+"</button>"+
                           " <button type=\"button\" class=\""+v_status_color+"\" onclick=\"updateStatus('"+data+"','"+v_up_status+"')\"><i class=\""+v_status_icon+"\"></i>"+v_status_text+"</button>";
                    }
                },
            ]
        });
    }
    
    function editType(id) {
        event.stopPropagation();
        location.href = "/type/toEdit.jhtml?id="+id;
    }
    
    function updateHotStatus(id, status) {
        // 停止事件冒泡
        event.stopPropagation();
        $.ajax({
            type:"post",
            url:"/product/updateHotStatus.jhtml?id="+id+"&status="+status,
            success:function (result) {
                if (result.code == 200) {
                    search();
                }
            }
        })
    }
    
    function updateStatus(id, status) {
        // 停止事件冒泡
        event.stopPropagation();
        $.ajax({
            type:"post",
            url:"/product/updateStatus.jhtml?id="+id+"&status="+status,
            success:function (result) {
                if (result.code == 200) {
                    search();
                }
            }
        })
    }

    function deleteType(id) {
        bootbox.confirm({
            message: "你确定删除吗?",
            size: 'small',
            title: "提示信息",
            buttons: {
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确定',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<span class="glyphicon glyphicon-remove"></span>取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
               if (result) {
                    // 发送请求，进行删除
                   $.ajax({
                       type:"post",
                       url:"/type/delete.jhtml",
                       data:{"id":id},
                       success:function (result) {
                           if (result.code == 200) {
                               // 刷新
                               search();
                           }
                       }
                   })
               }
            }
        })
    }
    
    function search() {
        // var v_param = {};
        //
        // v_param.shengId = $("select[name='areaSelect']")[0].value;
        // v_param.shiId = $($("select[name='areaSelect']")[1]).val();
        // v_param.xianId = $($("select[name='areaSelect']")[2]).val();
        //
        // v_param.userName = $("#userName").val();
        //
        // userTable.settings()[0].ajax.data = v_param;
        typeTable.ajax.reload();
    }
</script>
</body>
</html>
