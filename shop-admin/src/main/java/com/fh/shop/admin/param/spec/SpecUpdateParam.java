package com.fh.shop.admin.param.spec;

import java.io.Serializable;

public class SpecUpdateParam implements Serializable {

    private String specName;

    private String specNameSort;

    private Long id;

    private String specValues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecNameSort() {
        return specNameSort;
    }

    public void setSpecNameSort(String specNameSort) {
        this.specNameSort = specNameSort;
    }

    public String getSpecValues() {
        return specValues;
    }

    public void setSpecValues(String specValues) {
        this.specValues = specValues;
    }
}
