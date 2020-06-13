package com.fh.shop.admin.biz.goods;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.goods.GoodsAddParam;
import com.fh.shop.admin.param.goods.GoodsSearchParam;

public interface IGoodsService {

    public ServerResponse addGoods(GoodsAddParam goodsAddParam);

    DataTableResult findGoodsList(GoodsSearchParam goodsSearchParam);
}
