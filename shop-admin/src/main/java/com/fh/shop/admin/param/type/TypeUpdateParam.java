package com.fh.shop.admin.param.type;

import java.io.Serializable;

public class TypeUpdateParam implements Serializable {

    private String typeName;

    private Long typeId;

    private String brandIds;

    private String specIds;

    private String attrNames;

    private String attrValues;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getSpecIds() {
        return specIds;
    }

    public void setSpecIds(String specIds) {
        this.specIds = specIds;
    }

    public String getAttrNames() {
        return attrNames;
    }

    public void setAttrNames(String attrNames) {
        this.attrNames = attrNames;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues;
    }
}
