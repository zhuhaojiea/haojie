package com.fh.shop.admin.param.goods;

import com.fh.shop.admin.param.Page;

import java.io.Serializable;

public class GoodsSearchParam extends Page implements Serializable {

    private String productName;

    private Long brandId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
