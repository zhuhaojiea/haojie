<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/16 0016
  Time: 下午 16:27
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
    <title>地区管理</title>
    <!-- Bootstrap -->
    <link href="/js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/js/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
</head>
<body>
<jsp:include page="/common/nav.jsp"/>
<div class="panel panel-primary">
    <div class="panel-heading">
        地区管理
        <button type="button" class="btn btn-success" onclick="addArea();"><i class="glyphicon glyphicon-plus"></i> 增加</button>
        <button type="reset" class="btn btn-danger" onclick="deleteAreas();"><i class="glyphicon glyphicon-trash"></i>删除</button>
        <button type="reset" class="btn btn-warning" onclick="updateArea()"><i class="glyphicon glyphicon-pencil"></i>修改</button>
    </div>
    <ul id="areaTree" class="ztree"></ul>
</div>

<div id="addAreaDiv" style="display: none;">

    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">地区名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="add_areaName" placeholder="请输入地区名...">
            </div>
        </div>
    </form>

</div>


<div id="updateAreaDiv" style="display: none;">

    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">地区名</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="update_areaName" placeholder="请输入地区名...">
            </div>
        </div>
    </form>

</div>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="/js/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/zTree/js/jquery.ztree.core.min.js"></script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script src="/js/bootbox/bootbox.locales.min.js"></script>
<SCRIPT type="text/javascript">

    var v_addAreaHtml;
    var v_updateAreaHtml;
    $(function () {
        initTree();
        // 备份
        v_addAreaHtml = $("#addAreaDiv").html();
        v_updateAreaHtml = $("#updateAreaDiv").html();

    })
    
    
    function updateArea() {
        // 获取当前选中的节点
        var treeObj = $.fn.zTree.getZTreeObj("areaTree");
        var nodes = treeObj.getSelectedNodes();
        if (nodes.length == 1) {
            var v_node = nodes[0];
            var v_id = v_node.id;
            $.ajax({
                type:"post",
                url:"/area/find.jhtml",
                data:{"id":v_id},
                success:function (result) {
                    if (result.code == 200) {
                        var v_data = result.data;

                        $("#update_areaName").val(v_data.areaName);
                        // 弹出对话框
                        var v_areaUpdateDlg = bootbox.dialog({
                            title: '修改地区',
                            message:$("#updateAreaDiv form"),
                            size:"large",
                            buttons: {
                                confirm: {
                                    label: '<span class="glyphicon glyphicon-ok"></span>确认',
                                    className: 'btn-primary',
                                    callback: function(){
                                        var v_areaName = $("#update_areaName", v_areaUpdateDlg).val();
                                        $.ajax({
                                            type:"post",
                                            url:"/area/update.jhtml",
                                            data:{"id":v_id, "areaName":v_areaName},
                                            success:function (result) {
                                                if (result.code == 200) {
                                                    // 调用ztree的方法进行更新
                                                    v_node.areaName = v_areaName;
                                                    treeObj.updateNode(v_node);
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
                        $("#updateAreaDiv").html(v_updateAreaHtml);
                    }
                }
            })

        } else if (nodes.length > 1) {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span><b><font color='red'> 至少选择一个节点</font></b>",
                size: 'small',
                title: "提示信息"
            });
        }
    }
    
    function deleteAreas() {
        // 获取当前选中的节点
        var treeObj = $.fn.zTree.getZTreeObj("areaTree");
        var nodes = treeObj.getSelectedNodes();
        if (nodes.length == 0) {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span><b><font color='red'> 至少选择一个节点</font></b>",
                size: 'small',
                title: "提示信息"
            });
        } else {
            // 删除
            // 删除数据库
            var ids = []; // 声明一个空数组
            var v_selectedNodes = treeObj.transformToArray(nodes);
            for (var i = 0; i < v_selectedNodes.length; i++) {
                ids.push(v_selectedNodes[i].id);
            }
            $.ajax({
                type:"post",
                url:"/area/deleteAreas.jhtml",
                data:{"ids":ids},
                success:function (result) {
                    if (result.code == 200) {
                        for (var i=0, l=nodes.length; i < l; i++) {
                            treeObj.removeNode(nodes[i]);
                        }
                    }
                }
            })
        }
    }
    
    
    function addArea() {
        // 获取当前选中的节点
        var treeObj = $.fn.zTree.getZTreeObj("areaTree");
        var nodes = treeObj.getSelectedNodes();
        if (nodes.length == 1) {
            var v_node = nodes[0];
            var v_fid = v_node.id;
            // 弹出对话框
            var v_areaAddDlg = bootbox.dialog({
                title: '添加地区',
                message:$("#addAreaDiv form"),
                size:"large",
                buttons: {
                    confirm: {
                        label: '<span class="glyphicon glyphicon-ok"></span>确认',
                        className: 'btn-primary',
                        callback: function(){
                            // 获取信息
                            var param = {};
                            var v_areaName = $("#add_areaName", v_areaAddDlg).val();
                            param.areaName = v_areaName;
                            param.fId = v_fid;
                            $.ajax({
                                type:"post",
                                url:"/area/add.jhtml",
                                data:param,
                                success:function (result) {
                                    console.log(result);
                                    if (result.code == 200) {
                                        // 调用ztree自身的方法进行前台节点的添加[js]
                                        var treeObj = $.fn.zTree.getZTreeObj("areaTree");
                                        var newNode = {id:result.data,areaName:v_areaName,fId:v_fid};
                                        // 新增节点的父节点
                                        // 当前新增节点的数据
                                        treeObj.addNodes(v_node, newNode);
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
            $("#addAreaDiv").html(v_addAreaHtml);
        } else if (nodes.length > 1) {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选择一个节点",
                size: 'small',
                title: "提示信息"
            });
        } else {
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span><b><font color='red'> 至少选择一个节点</font></b>",
                size: 'small',
                title: "提示信息"
            });
        }
        console.log(nodes);
    }

    function initTree() {
        // 配置信息
        var info = {
            data: {
                simpleData: {
                    enable: true,
                    pIdKey:"fId"

                },
                key: {
                    name: "areaName"
                }
            }
        };

        // 获取数据
        $.ajax({
            type:"post",
            url:"/area/list.jhtml",
            success:function (result) {
                if (result.code == 200) {
                    console.log(result);
                    // 初始化
                    $.fn.zTree.init($("#areaTree"), info, result.data);
                }
            }
        })


    }





</SCRIPT>
</body>
</html>
