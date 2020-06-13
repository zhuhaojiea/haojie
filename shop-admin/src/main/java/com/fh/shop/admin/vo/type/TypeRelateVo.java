package com.fh.shop.admin.vo.type;

import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.vo.attr.AttrVo;
import com.fh.shop.admin.vo.spec.SpecVo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TypeRelateVo implements Serializable {

    private List<Brand> brandList = new ArrayList<>();

    private List<AttrVo> attrVoList = new ArrayList<>();

    private List<SpecVo> specVoList = new ArrayList<>();

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<AttrVo> getAttrVoList() {
        return attrVoList;
    }

    public void setAttrVoList(List<AttrVo> attrVoList) {
        this.attrVoList = attrVoList;
    }

    public List<SpecVo> getSpecVoList() {
        return specVoList;
    }

    public void setSpecVoList(List<SpecVo> specVoList) {
        this.specVoList = specVoList;
    }
}
