package com.fh.shop.admin.controller.goods;

import com.fh.shop.admin.biz.goods.IGoodsService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.goods.GoodsAddParam;
import com.fh.shop.admin.param.goods.GoodsSearchParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("/goods")
@Controller
public class GoodsController {

    @Resource(name="goodsService")
    private IGoodsService goodsService;

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "goods/add";
    }

    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse addGoods(GoodsAddParam goodsAddParam) {
        return goodsService.addGoods(goodsAddParam);
    }

    @RequestMapping("/toList")
    public String toList() {
        return "goods/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataTableResult findGoodsList(GoodsSearchParam goodsSearchParam) {
        return goodsService.findGoodsList(goodsSearchParam);
    }
}
