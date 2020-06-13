package com.fh.shop.admin.vo.goods;

import com.fh.shop.admin.po.goods.Goods;
import com.fh.shop.admin.po.goods.GoodsCommon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodsVo implements Serializable {

    private GoodsCommon goodsCommon = new GoodsCommon();

    private List<Goods> goodsList = new ArrayList<>();

    public GoodsCommon getGoodsCommon() {
        return goodsCommon;
    }

    public void setGoodsCommon(GoodsCommon goodsCommon) {
        this.goodsCommon = goodsCommon;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
