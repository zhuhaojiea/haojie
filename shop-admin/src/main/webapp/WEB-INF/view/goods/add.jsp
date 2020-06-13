<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/25 0025
  Time: 上午 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>商品添加</title>
</head>
<body>
    <jsp:include page="/common/nav.jsp"></jsp:include>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">商品名</label>
                        <div class="col-sm-6">
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
                        <label  class="col-sm-2 control-label">价格</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="price" placeholder="价格...">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">库存</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="stock" placeholder="库存...">
                        </div>
                    </div>
                    <div class="form-group" id="cateDiv">
                        <label  class="col-sm-2 control-label">分类</label>

                    </div>
                    <div id="relateInfoDiv">
                        
                    </div>
                    <div style="text-align: center;">
                        <button type="button" class="btn btn-primary" onclick="addGoods();"><i class="glyphicon glyphicon-ok"></i> 增加</button>
                        <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="/common/script.jsp"></jsp:include>
<script>
    $(function () {
        initCate(0);
    })
    
    function addGoods() {
        var v_param = {};
        v_param["goodsCommon.productName"] = $("#productName").val();
        v_param["goodsCommon.price"] = $("#price").val();
        v_param["goodsCommon.stock"] = $("#stock").val();
        // 1:a,3:222;16:内存,66:24G;15:属性,7:48 attrInfo
        //v_param["goodsCommon.attrInfo"]
        // 获取页面中所有的属性下拉列表
        var attrInfoArr = [];
        $("select[name='attrSelect']").each(function () {
            if ($(this).val()>-1) {
                // 获取相关属性的信息
                var v_optionInfo = $(this).find("option:selected");
                var atrrInfo = v_optionInfo.data("attr-name-id")+":"
                    +v_optionInfo.data("attr-name")+","+$(this).val()+":"+v_optionInfo.data("attr-value-name");
                attrInfoArr.push(atrrInfo);
            }
        });
        v_param["goodsCommon.attrInfo"] = attrInfoArr.length > 0 ? attrInfoArr.join(";"):"";
        // 7:颜色,11:红色,12:绿色;18:配置,23:标准版,24:豪华版
        //v_param["goodsCommon.specInfo"]
        // 获取页面中所有的规格信息
        var resultArr = [];
        for (let spec of specArr) {
            var specValueArr = [];
            var specValueList = [];
            // 放选中的规格值信息 ['11:红色','12:绿色']
            $("input[name='specIds_"+spec.id+"']:checked").each(function () {
                specValueArr.push($(this).val()+":"+$(this).data("specvalue-info"));
            })
            // ['7:颜色','11:红色','12:绿色']
            if (specValueArr.length > 0) {
                // 只有 选中了 规格值信息，才会放对应的规格名
                specValueList.push(spec.id+":"+spec.specName);
                for (let specValue of specValueArr) {
                    specValueList.push(specValue);
                }
            }
            // 如果specValueList里面有数据
            if (specValueList.length > 0) {
                // 7:颜色,11:红色,12:绿色     18:配置,23:标准版,24:豪华版
                var r = specValueList.join(",");
                // ['7:颜色,11:红色,12:绿色', '18:配置,23:标准版,24:豪华版']
                resultArr.push(r);
            }
        }
        // 经过 resultArr.join(";") 7:颜色,11:红色,12:绿色;18:配置,23:标准版,24:豪华版
        v_param["goodsCommon.specInfo"] = resultArr.length > 0 ? resultArr.join(";"):"";

        v_param["goodsCommon.brandId"] = $("#brandSelect").val();
        v_param["goodsCommon.brandName"] = $("#brandSelect option:selected").data("brand-name");
        // v_param["goodsCommon.cate1"] = $("#brandSelect option:selected").data("brand-name");
        v_param["goodsCommon.cate1"] = $($("select[name='cateSelect']")[0]).val();
        v_param["goodsCommon.cate2"] = $($("select[name='cateSelect']")[1]).val();
        v_param["goodsCommon.cate3"] = $($("select[name='cateSelect']")[2]).val();
        v_param["goodsCommon.cateName"] = $($("select[name='cateSelect']")[0]).find("option:selected").data("cate-name")+"->" +
                                          $($("select[name='cateSelect']")[1]).find("option:selected").data("cate-name")+"->" +
                                          $($("select[name='cateSelect']")[2]).find("option:selected").data("cate-name");

        var v_priceArr = [];
        $("input[name='skuPrice']").each(function () {
            v_priceArr.push(this.value);
        })
        v_param.prices = v_priceArr.length > 0 ? v_priceArr.join(","):"";
        var v_stockArr = [];
        $("input[name='skuStock']").each(function () {
            v_stockArr.push(this.value);
        })
        v_param.stocks = v_stockArr.length > 0 ? v_stockArr.join(","):"";

        // 11:红色,23:标准版;12:绿色,23:标准版;11:红色,24:豪华版
        var v_specValueArr = [];
        $("input[name='specValueInfo']").each(function () {
            v_specValueArr.push(this.value);
        })
        v_param.specValueInfos = v_specValueArr.length > 0 ? v_specValueArr.join(";"):"";

        console.log(v_param);
        $.ajax({
            type:"post",
            url:"/goods/add.jhtml",
            data:v_param,
            success:function (result) {
                if (result.code == 200) {
                    location.href="/goods/toList.jhtml";
                }
            }
        })
    }
    
    function initCate(id, obj) {
        if (obj) {
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            type:"post",
            url:"/cate/list.jhtml",
            data:{"id":id},
            success:function (result) {
                if (result.code == 200) {
                        var cateArr = result.data;
                        if (cateArr.length == 0) {
                            // 当前是最底层分类
                            var typeId = $(obj).find("option:selected").data("type-id");
                            initTypeRelate(typeId);
                            return;
                        }
                        if (cateArr.length > 0) {
                            var v_html = '<div class="col-sm-3"><select class="form-control" name="cateSelect" onchange="initCate(this.value, this)"><option value="-1">===请选择===</option>';
                            for (let cate of cateArr) {
                                v_html += '<option data-cate-name="'+cate.categoryName+'" data-type-id="'+cate.typeId+'" value="'+cate.id+'">'+cate.categoryName+'</option>';
                            }
                            v_html += "</select></div>";
                            $("#cateDiv").append(v_html);
                        }
                }
            }
        })
    }
    
    function initTypeRelate(typeId) {
        // 没有绑定类型
        if (!typeId || typeId == -1) {
            // 先清空div的内容
            $("#relateInfoDiv").html("");
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>当前分类未绑定类型，请进行绑定！！！</b>",
                size: 'small',
                title: "提示信息"
            });
            return;
        }
        $.ajax({
            type:"post",
            url:"/type/findTypeRelate.jhtml?typeId="+typeId,
            success:function (result) {
                console.log(result);
                if (result.code == 200) {
                    // 先清空div的内容
                    $("#relateInfoDiv").html("");
                    var v_data = result.data;
                    var v_brandList = v_data.brandList;
                    var v_attrVoList = v_data.attrVoList;
                    var v_specVoList = v_data.specVoList;
                    // 再写入
                    initBrandList(v_brandList);
                    initAttrList(v_attrVoList);
                    initSpecList(v_specVoList);
                }
            }
        })
    }
    
    function initBrandList(brandList) {
        var v_html = '<div class="form-group">' +
            '<label  class="col-sm-2 control-label">品牌</label>' +
            '<div class="col-sm-3"><select class="form-control" id="brandSelect"><option value="-1" data-brand-name="">===请选择===</option>';
        for(let brand of brandList) {
            v_html += '<option data-brand-name="'+brand.brandName+'" value="'+brand.id+'">'+brand.brandName+'</option>';
        }
        v_html +=  '</div></select></div>';
        $("#relateInfoDiv").append(v_html);
    }
    
    function initAttrList(attrVoList) {
        var v_html = '<div class="form-group">' +
            '<label  class="col-sm-2 control-label">属性</label>';
        for (let attrVo of attrVoList) {
            var item = '<div class="col-sm-3">';
            item += '<div class="input-group"><span class="input-group-addon" id="basic-addon1">'+attrVo.attr.attrName+'</span>';
            item += '<select class="form-control" name="attrSelect"><option value="-1">===请选择===</option>';
            var v_attrValueArr = attrVo.attrValueList;
            for (let a of v_attrValueArr) {
                item += '<option data-attr-name="'+attrVo.attr.attrName+'" data-attr-name-id="'+attrVo.attr.id+'" data-attr-value-name="'+a.attrValue+'" value="'+a.id+'">'+a.attrValue+'</option>'
            }
            item += '</select></div></div>';
            v_html += item;
        }
        v_html += "</div>";
        $("#relateInfoDiv").append(v_html);
    }
    var specArr = [];
    function initSpecList(specVoList) {
        for (let specVo of specVoList) {
            // 组装 规格 数组
            specArr.push(specVo.spec);
            var v_html = '<div  class="form-group">';
            v_html += '<label  class="col-sm-2 control-label">'+specVo.spec.specName+'</label><div class="col-sm-10">'
            var v_specValueArr = specVo.specValues;
            for (let specValueInfo of v_specValueArr) {
                v_html += '&nbsp;&nbsp;<input type="checkbox" onclick="buildTable();" data-specvalue-info="'+specValueInfo.specValue+'" name="specIds_'+specVo.spec.id+'" value="'+specValueInfo.id+'">'+specValueInfo.specValue;
            }
            v_html+='</div></div>';
            $("#relateInfoDiv").append(v_html);
        }

    }
    
    function calSPUStock() {
        // 一个SPU的库存量=多个SKU库存量的总和
        var v_spuStock = 0;
        $("input[name='skuStock']").each(function () {
            v_spuStock += parseInt(this.value.length==0?0:this.value);
        })
        $("#stock").val(v_spuStock);
    }
    
    function calSPUPrice() {
       // 一个SPU的价格=多个SKu中最低的那个价格值
        var v_priceArr = [];
        $("input[name='skuPrice']").each(function () {
            v_priceArr.push(parseInt(this.value.length==0?0:this.value));
        })
        console.log(v_priceArr);
        // 对数组进行排序
        v_priceArr.sort((x,y)=>x-y);
        $("#price").val(v_priceArr[0]);
    }
    
    function buildTable() {
        // 先删除表格
        $("#specTableDiv").remove();
        // 先组成 [['1:红色','12:绿色','5:黄色'],['3:16G','4:32G']]  二维数组
        // 生成表格头【根据 规格名 动态生成的表格头】
        var res = [];
        var headerArr = [];
        for (let spec of specArr) {
            var specid= spec.id;
            var tempArr = [];
            $("input[name='specIds_"+specid+"']:checked").each(function () {
                var v_specValueId = $(this).val();
                var v_specValueInfo = $(this).data("specvalue-info");
                tempArr.push(v_specValueId+":"+v_specValueInfo);
            })
            if (tempArr.length > 0) {
                res.push(tempArr);
                // 只有当 这组规格值的复选框被选中的时候 才 添加规格名作为 头
                headerArr.push(spec.specName);
            }
        }
        if (headerArr.length > 1) {
            console.log("生成表格");
            // 调用递归排列组合生成一个 一维数组 ['1:红色,3:16G','1:红色,4:32G','12:绿色,3:16G','12:绿色,4:32G'....]
            var trArr = getData(res);
            console.log(trArr);
            // 动态生成表格的头  动态生成表格的行 以及 行中的 部分数据
            var v_tableHtml = '<div class="row" id="specTableDiv"><div class="col-md-10 col-md-offset-2" ><table class="table table-striped table-bordered">';
            // 动态生成header
            var v_headerHtml = "<tr>";
            for (let header of headerArr) {
                v_headerHtml += '<th>'+header+'</th>';
            }
            v_headerHtml += '<th>价格</th><th>库存</th>';
            v_headerHtml += "</tr>";
            // 动态生成body
            var v_bodyHtml= "<tbody>";
            for (let tr of trArr) {
                v_bodyHtml += '<tr>'
                var tdArr = tr.split(",");
                for (let td of tdArr) {
                    var specValue = td.split(":")[1];
                    v_bodyHtml += '<td>'+specValue+'</td>';
                }
                v_bodyHtml += '<td><input name="specValueInfo" type="hidden" value="'+tr+'"><input type="text" class="form-control" name="skuPrice" onblur="calSPUPrice()" placeholder="价格..." style="width:120px"></td><td><input type="text" name="skuStock" style="width:120px" class="form-control"  onblur="calSPUStock();" placeholder="库存..." value="0"></td>'
                v_bodyHtml += '</tr>';
            }
            v_bodyHtml += '</tbody>';
            // 把header和body组装到table中去
            v_tableHtml += v_headerHtml;
            v_tableHtml += v_bodyHtml;
            // table表格的结尾
            v_tableHtml += '</div></div></table>';
            // 把table追加到div中去
            $("#relateInfoDiv").append(v_tableHtml);
        }
    }

    function getData(arr) {
        if (arr.length > 1) {
            var res = [];
            var base = arr[0];
            arr.splice(0,1);
            var next = getData(arr);
            for (var i = 0; i < base.length; i++) {
                for (var j = 0; j < next.length; j++) {
                    res.push(base[i]+","+next[j]);
                }
            }
            return res;
        } else {
            return arr[0];
        }
    }
</script>
</body>
</html>
