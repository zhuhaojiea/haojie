package com.fh.shop.admin.po.area;

import java.io.Serializable;

public class Area implements Serializable {

    private Long id;

    private String areaName;

    private Long fId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getfId() {
        return fId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
    }
}
