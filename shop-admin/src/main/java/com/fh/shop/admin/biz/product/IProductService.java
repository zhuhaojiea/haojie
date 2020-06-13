package com.fh.shop.admin.biz.product;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface IProductService {

    public void addProduct(Product product);

    public Map findList(ProductSearchParam productSearchParam);

    ServerResponse deleteProduct(Long id);

    Product findProduct(Long id);

    void updateProduct(Product product);

    List<Product> findAllList(ProductSearchParam productSearchParam);

    ServerResponse deleteBatch(Long[] idArr);

    ServerResponse updateHotStatus(Long id, Integer status);

    ServerResponse updateStatus(Long id, Integer status);

    String buildPdfHtml(List<Product> productList);


    ServerResponse importExcel(String path);


    String buildWordFile(List<Product> productList);
}
