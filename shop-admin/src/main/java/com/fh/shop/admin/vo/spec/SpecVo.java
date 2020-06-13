package com.fh.shop.admin.vo.spec;

import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.spec.SpecValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecVo implements Serializable {

    private Spec spec = new Spec();

    private List<SpecValue> specValues = new ArrayList<>();

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    public List<SpecValue> getSpecValues() {
        return specValues;
    }

    public void setSpecValues(List<SpecValue> specValues) {
        this.specValues = specValues;
    }
}
