package com.fh.shop.admin.param.product;

import com.fh.shop.admin.param.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ProductSearchParam extends Page {

    private String productName;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private Long brandInfoId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minCreateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxCreateDate;

    public Date getMinCreateDate() {
        return minCreateDate;
    }

    public void setMinCreateDate(Date minCreateDate) {
        this.minCreateDate = minCreateDate;
    }

    public Date getMaxCreateDate() {
        return maxCreateDate;
    }

    public void setMaxCreateDate(Date maxCreateDate) {
        this.maxCreateDate = maxCreateDate;
    }

    public Long getBrandInfoId() {
        return brandInfoId;
    }

    public void setBrandInfoId(Long brandInfoId) {
        this.brandInfoId = brandInfoId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
