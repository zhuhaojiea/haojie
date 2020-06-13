package com.fh.shop.admin.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductMapper extends BaseMapper<Product>{


    Long findCount(ProductSearchParam productSearchParam);

    List<Product> findPageList(ProductSearchParam productSearchParam);

    List<Product> findAllList(ProductSearchParam productSearchParam);

}
