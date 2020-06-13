package com.fh.shop.admin.param.cate;

import com.fh.shop.admin.po.cate.Category;

import java.io.Serializable;

public class CategoryUpdateParam implements Serializable {

    private Category category;

    private Integer relateFlag;

    private String ids;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getRelateFlag() {
        return relateFlag;
    }

    public void setRelateFlag(Integer relateFlag) {
        this.relateFlag = relateFlag;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
