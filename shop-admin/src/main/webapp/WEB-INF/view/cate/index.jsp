<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/21 0021
  Time: 上午 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>分类管理</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="/common/nav.jsp"></jsp:include>

    <div class="container">
        <div class="row">
            <div class="col-md-12" id="cateDiv">
                <table id="cateTable" class="table table-bordered">
                    <thead>
                        <tr>
                            <th>分类名</th>
                            <th>类型名</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div id="addCateDiv" style="display:none;">
        <form class="form-horizontal">
            <div class="form-group">
                <label  class="col-sm-2 control-label">分类名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="addCateName" placeholder="请输入分类名...">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">上级分类</label>
                <div class="col-sm-10" id="addCateSelectDiv">

                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">关联类型列表</label>
                <div class="col-sm-10" id="addTypeRadioDiv">

                </div>
            </div>
        </form>
    </div>

    <div id="updateCateDiv" style="display:none;">
        <form class="form-horizontal">
            <div class="form-group">
                <label  class="col-sm-2 control-label">分类名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="updateCateName" placeholder="请输入分类名...">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">上级分类</label>
                <div class="col-sm-10" id="updateCateSelectDiv">

                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">关联类型列表</label>
                <div class="col-sm-10" id="updateTypeRadioDiv">

                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">关联到子类</label>
                <div class="col-sm-10">
                    <input type="checkbox" id="relateCheckbox" checked="true"/>
                </div>
            </div>
        </form>
    </div>


    <jsp:include page="/common/script.jsp"></jsp:include>
<script>
    var v_cateTableHtml;
    var v_addCateDivHtml;
    var v_updateCateDivHtml;
    $(function () {
        // 备份
        v_cateTableHtml = $("#cateDiv").html();
        v_addCateDivHtml = $("#addCateDiv").html();
        v_updateCateDivHtml = $("#updateCateDiv").html();
        initCateTable();
        // console.log(v_cateTableHtml);
        // initCateTable().then(result => {
        //     console.log("=============");
        //     // 获取分类列表数据
        //     cates = result.data;
        //     // 对分类列表进行递归排序
        //     resultArr = []; // 重新初始化
        //     sortList(cates, resultArr, 0, 0);
        //     // console.log(resultArr);
        //     // 生成treeTable
        //     buildTreeTable(resultArr);
        // }).catch(error => {
        //     console.log(error);
        // });
    })
    
    
    function addCate(obj) {
         // 得到了新增数据的父id
       var v_id = $(obj).parents("tr").data("tt-id");
        // 构建select列表[上级分类]
        buildSelect(resultArr, v_id);
        // 构建radio列表 [关联的类型列表]
        buildTypeRadioList();
        // 填充对话框
        var v_addCateDlg = bootbox.dialog({
            title: '添加分类',
            message:$("#addCateDiv form"),
            size:"large",
            buttons: {
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确认',
                    className: 'btn-primary',
                    callback: function(){
                        // 获取数据
                        var v_param = {};
                        v_param.categoryName = $("#addCateName", v_addCateDlg).val();
                        v_param.fatherId = $("#addCateSelect", v_addCateDlg).val();
                        v_param.typeId = $("input[name='typeIds']:checked", v_addCateDlg).val();
                        v_param.typeName = $("input[name='typeIds']:checked", v_addCateDlg).data("type-name");
                        console.log(v_param);
                        $.ajax({
                            type:"post",
                            url:"/cate/add.jhtml",
                            data:v_param,
                            success:function (result) {
                                if (result.code == 200) {
                                    initCateTable();
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
        $("#addCateDiv").html(v_addCateDivHtml);
    }
    
    function buildTypeRadioList() {
        $.ajax({
            type:"post",
            url:"/type/all.jhtml",
            async:false,
            success:function (result) {
                if (result.code == 200) {
                    var v_typeArr = result.data;
                    var v_html = "<input type='radio' value='-1' checked name='typeIds' data-type-name=''>无";
                    for (let type of v_typeArr) {
                        v_html += "&nbsp;&nbsp;<input type='radio' data-type-name='"+type.typeName+"' name='typeIds' value='"+type.id+"'>"+type.typeName;
                    }
                    $("#addTypeRadioDiv").html(v_html);
                }
            }
        })
    }

    function buildUpdateTypeRadioList() {
        $.ajax({
            type:"post",
            url:"/type/all.jhtml",
            async:false,
            success:function (result) {
                if (result.code == 200) {
                    var v_typeArr = result.data;
                    var v_html = "<input type='radio' value='-1' checked name='upate_typeIds' data-type-name=''>无";
                    for (let type of v_typeArr) {
                        v_html += "&nbsp;&nbsp;<input type='radio' data-type-name='"+type.typeName+"' name='upate_typeIds' value='"+type.id+"'>"+type.typeName;
                    }
                    $("#updateTypeRadioDiv").html(v_html);
                }
            }
        })
    }

    function buildSelect(datas, id) {
        var v_html = "<select id='addCateSelect'><option value='-1'>===请选择===</option><option value='0'>分类根目录</option>";
        for (let data of datas)
        {
            v_html+="<option value='"+data.id+"'>"+buildIndent(data.level)+data.categoryName+"</option>";
        }

        v_html+="</select>";
        $("#addCateSelectDiv").html(v_html);
        // 选中
        $("#addCateSelect").val(id);
    }

    function buildUpdateSelect(datas) {
        var v_html = "<select id='updateCateSelect'><option value='-1'>===请选择===</option><option value='0'>分类根目录</option>";
        for (let data of datas)
        {
            v_html+="<option value='"+data.id+"'>"+buildIndent(data.level)+data.categoryName+"</option>";
        }

        v_html+="</select>";
        $("#updateCateSelectDiv").html(v_html);
    }

    function buildIndent(level) {
        var res = "";

        for (var i = 0; i < level; i++)
        {
            res+="&nbsp;&nbsp;"
        }

        return res;
    }





    let cates = [];
    let resultArr = [];
    function initCateTable() {
        // return new Promise((resolve, reject) => {
        //     $.ajax({
        //         type:"post",
        //         url:"/cate/all.jhtml",
        //         success:function (result) {
        //             // console.log(result);
        //             if (result.code == 200) {
        //                 resolve(result);
        //             } else {
        //                 reject(result);
        //             }
        //         }
        //
        //     })
        // })

        $.ajax({
            type: "post",
            url: "/cate/all.jhtml",
            success: function (result) {
                // console.log(result);
                if (result.code == 200) {
                    // 获取分类列表数据
                    cates = result.data;
                    // 对分类列表进行递归排序
                    resultArr = []; // 重新初始化
                    sortList(cates, resultArr, 0, 0);
                    // console.log(resultArr);
                    // 生成treeTable
                    buildTreeTable(resultArr);
                }
            }
        })

    }
    
    function updateCate(obj) {
        // 得到当前节点的id[当前分类的id]
        var v_id = $(obj).parents("tr").data("tt-id");
        // 构建select列表[上级分类]
        buildUpdateSelect(resultArr);
        // 构建radio列表 [关联的类型列表]
        buildUpdateTypeRadioList();
        // 发送ajax查询对应分类的信息
        $.ajax({
            type:"post",
            url:"/cate/find.jhtml",
            data:{"id":v_id},
            success:function (result) {
                if (result.code == 200) {
                    console.log(result);
                    var v_cate = result.data;
                    // 进行回填[选中 下拉列表  radio 分类名]
                    $("#updateCateName").val(v_cate.categoryName);
                    $("#updateCateSelect").val(v_cate.fatherId);
                    $("#updateCateName").val(v_cate.categoryName);
                    $("input[name='upate_typeIds']").each(function () {
                        if (v_cate.typeId == this.value) {
                            this.checked = true;
                        }
                    })
                    // 弹出更新的对话框窗口
                    // 填充对话框
                    var v_updateCateDlg = bootbox.dialog({
                        title: '修改分类',
                        message:$("#updateCateDiv form"),
                        size:"large",
                        buttons: {
                            confirm: {
                                label: '<span class="glyphicon glyphicon-ok"></span>确认',
                                className: 'btn-primary',
                                callback: function(){
                                    // 获取最新的数据
                                    var v_param = {};
                                    // 发送ajax请求时的级联属性赋值
                                    v_param["category.id"] = v_id;
                                    v_param["category.categoryName"] = $("#updateCateName", v_updateCateDlg).val();
                                    v_param["category.fatherId"] = $("#updateCateSelect", v_updateCateDlg).val();
                                    v_param["category.typeId"] = $("input[name='upate_typeIds']:checked", v_updateCateDlg).val();
                                    v_param["category.typeName"] = $("input[name='upate_typeIds']:checked", v_updateCateDlg).data("type-name");
                                    // 获取 关联到子类 的复选框 是否被选中
                                    v_param.relateFlag = $("#relateCheckbox", v_updateCateDlg).prop("checked")?1:0;
                                    // 获取 当前节点 的子子孙孙的 节点id
                                    var resultArr = [];
                                    getSubTree(cates, resultArr, v_id);
                                    // var idArr = [];
                                    // for (let result of resultArr) {
                                    //     idArr.push(result.id);
                                    // }
                                    let idArr = resultArr.map(y => y.id);
                                    v_param.ids = idArr.length == 0 ? "":idArr.join(",");
                                    console.log(v_param);
                                    // 调用ajax进行更新
                                    $.ajax({
                                        type:"post",
                                        url:"/cate/update.jhtml",
                                        data:v_param,
                                        success:function (result) {
                                            if (result.code == 200) {
                                                initCateTable();
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
                    $("#updateCateDiv").html(v_updateCateDivHtml);
                }
            }
        })









    }


    
    function buildTreeTable(cateArr) {
        // 还原
        $("#cateDiv").html(v_cateTableHtml);
        // =============================重新构建
        let v_html = '<tr data-tt-id="0"><td>分类根目录&nbsp;&nbsp;<button type="button" class="btn btn-link" ><i class="glyphicon glyphicon-plus"></i> 新增下级</button></td><td></td><td></td></tr>';
        for (let cate of cateArr) {
            v_html+='<tr data-tt-id="'+cate.id+'" data-tt-parent-id="'+cate.fatherId+'">' +
                '<td>'+cate.categoryName+'&nbsp;&nbsp;<button type="button" class="btn btn-link" onclick="addCate(this);"><i class="glyphicon glyphicon-plus"></i> 新增下级</button></td><td>'+cate.typeName+'</td>' +
                '<td>' +
                '<button type="button" class="btn btn-danger" onclick="deleteCate(this)"><i class="glyphicon glyphicon-trash" ></i> 删除</button>\n' +
                ' <button type="reset" class="btn btn-warning" onclick="updateCate(this)"><i class="glyphicon glyphicon-pencil"></i>编辑</button></td>' +
                '</tr>';
        }
        // 向tbody中插入组装好的html代码
        $("#cateTable tbody").html(v_html);
        // 初始化treetable
        $("#cateTable").treetable({
            expandable: true,
            initialState:'expanded'
        });
    }

    function deleteCate(obj) {
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
                    var v_id = $(obj).parents("tr").data("tt-id");
                    var v_resultArr = [];
                    getSubTree(cates, v_resultArr, v_id);
                    var v_ids = [];
                    for (let item of v_resultArr) {
                        v_ids.push(item.id);
                    }
                    v_ids.push(v_id);
                    console.log(v_ids);
                    $.ajax({
                        type:"post",
                        url:"/cate/delete.jhtml",
                        data:{ids:v_ids},
                        success:function (result) {
                            if (result.code == 200) {
                                // 刷新
                                initCateTable();
                            }
                        }
                    })
                }
            }
        })

    }

    function sortList(cates, resultArr, id, level) {
        let childs = getChilds(cates, id);
        for (let c of childs) {
            c.level = level;
            resultArr.push(c);
            sortList(cates, resultArr, c.id, c.level+1);
        }
    }
    
    function getChilds(cates, id) {
        let childs = [];
        for (let cate of cates) {
            if (cate.fatherId == id) {
                childs.push(cate);
            }
        }
        return childs;
    }
    
    
    function getSubTree(cates, resultArr, id) {
        for (let cate of cates) {
            if (id == cate.fatherId) {
                resultArr.push(cate);
                getSubTree(cates, resultArr, cate.id);
            }
        }
    }
</script>
</body>
</html>
