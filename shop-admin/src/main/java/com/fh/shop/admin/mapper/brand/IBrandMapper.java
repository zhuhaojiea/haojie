package com.fh.shop.admin.mapper.brand;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.brand.Brand;

import java.util.List;

public interface IBrandMapper extends BaseMapper<Brand> {

    public List<Brand> findAllBrandList();

    Long findBrandIdByName(String brandName);

    void addBrand(Brand brand);

}
