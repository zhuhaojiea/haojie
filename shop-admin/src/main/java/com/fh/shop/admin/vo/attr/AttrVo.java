package com.fh.shop.admin.vo.attr;

import com.fh.shop.admin.po.attr.Attr;
import com.fh.shop.admin.po.attr.AttrValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttrVo implements Serializable {

    private Attr attr = new Attr();

    private List<AttrValue> attrValueList = new ArrayList<>();

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public List<AttrValue> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<AttrValue> attrValueList) {
        this.attrValueList = attrValueList;
    }
}
