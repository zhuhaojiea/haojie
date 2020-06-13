package com.fh.shop.admin.param.spec;

import java.io.Serializable;

public class SpecParam implements Serializable {

    private String specNames;

    private String specNameSorts;

    private String specValues;

    public String getSpecNames() {
        return specNames;
    }

    public void setSpecNames(String specNames) {
        this.specNames = specNames;
    }

    public String getSpecNameSorts() {
        return specNameSorts;
    }

    public void setSpecNameSorts(String specNameSorts) {
        this.specNameSorts = specNameSorts;
    }

    public String getSpecValues() {
        return specValues;
    }

    public void setSpecValues(String specValues) {
        this.specValues = specValues;
    }
}
