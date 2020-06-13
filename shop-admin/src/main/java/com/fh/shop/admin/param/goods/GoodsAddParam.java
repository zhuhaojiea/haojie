package com.fh.shop.admin.param.goods;

import com.fh.shop.admin.po.goods.GoodsCommon;

import java.io.Serializable;

public class GoodsAddParam implements Serializable {

    private GoodsCommon goodsCommon = new GoodsCommon();
    // 1000,255,100
    private String prices;
    // 10,5,28
    private String stocks;
    // 11:红色,23:标准版;12:绿色,23:标准版;11:红色,24:豪华版
    private String specValueInfos;

    public GoodsCommon getGoodsCommon() {
        return goodsCommon;
    }

    public void setGoodsCommon(GoodsCommon goodsCommon) {
        this.goodsCommon = goodsCommon;
    }



    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public String getSpecValueInfos() {
        return specValueInfos;
    }

    public void setSpecValueInfos(String specValueInfos) {
        this.specValueInfos = specValueInfos;
    }
}
