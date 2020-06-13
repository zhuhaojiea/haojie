package com.fh.shop.admin;

import com.fh.shop.admin.biz.product.IProductService;
import com.fh.shop.admin.mapper.product.IProductMapper;
import com.fh.shop.admin.po.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-common.xml"})
public class TestProduct {

    @Resource(name="productService")
    private IProductService productService;

    @Autowired
    private IProductMapper productMapper;

    @Test
    public void testAddProduct() {
        Product product = new Product();
        product.setProductName("小米9s");
        product.setPrice(new BigDecimal(1000));
        productService.addProduct(product);
    }

    @Test
    public void test2() {
       // productMapper.deleteProduct(31L);
    }
}
