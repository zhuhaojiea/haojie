package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.po.brand.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper brandMapper;


    @Override
    public ServerResponse findAllBrandList() {
        List<Brand> allBrandList = brandMapper.findAllBrandList();
        return ServerResponse.success(allBrandList);
    }
}
