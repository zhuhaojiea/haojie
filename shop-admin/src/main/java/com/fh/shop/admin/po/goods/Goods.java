package com.fh.shop.admin.po.goods;

import java.io.Serializable;
import java.math.BigDecimal;

public class Goods implements Serializable {

    private Long id;

    private Long commonId;

    private String productName;

    private BigDecimal price;

    private Long stock;

    private String specValueInfo;

    private String specValueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommonId() {
        return commonId;
    }

    public void setCommonId(Long commonId) {
        this.commonId = commonId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getSpecValueInfo() {
        return specValueInfo;
    }

    public void setSpecValueInfo(String specValueInfo) {
        this.specValueInfo = specValueInfo;
    }

    public String getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(String specValueId) {
        this.specValueId = specValueId;
    }
}
