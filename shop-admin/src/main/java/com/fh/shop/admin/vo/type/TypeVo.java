package com.fh.shop.admin.vo.type;

import com.fh.shop.admin.po.attr.Attr;
import com.fh.shop.admin.po.type.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeVo implements Serializable {

    private Type type = new Type();

    private List<Long> specIdList = new ArrayList<>();

    private List<Long> brandIdList = new ArrayList<>();

    private List<Attr> attrList = new ArrayList<>();

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Long> getSpecIdList() {
        return specIdList;
    }

    public void setSpecIdList(List<Long> specIdList) {
        this.specIdList = specIdList;
    }

    public List<Long> getBrandIdList() {
        return brandIdList;
    }

    public void setBrandIdList(List<Long> brandIdList) {
        this.brandIdList = brandIdList;
    }

    public List<Attr> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<Attr> attrList) {
        this.attrList = attrList;
    }
}
