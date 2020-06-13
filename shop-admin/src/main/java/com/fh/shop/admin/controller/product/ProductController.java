package com.fh.shop.admin.controller.product;

import com.fh.shop.admin.annotation.Log;
import com.fh.shop.admin.biz.product.IProductService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.ExcelUtil;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.vo.product.ProductVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Resource(name="productService")
    private IProductService productService;

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "product/add";
    }

    @RequestMapping("/add")
    @ResponseBody
    @Log(info = "添加商品")
    public ServerResponse add(Product product) throws Exception {
        productService.addProduct(product);
        return ServerResponse.success();
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(ProductSearchParam productSearchParam, HttpServletResponse response) {
         // 1.查询符合条件的数据
        List<Product> productList =  productService.findAllList(productSearchParam);
//         // 2.将 数据 转为 指定的格式 【excel中的Workbook】
//        XSSFWorkbook workbook = productService.buildWorkBook(productList);
        String[] headers = {"商品名", "品牌名", "价格", "生产日期"};
        String[] props = {"productName", "brandName", "price", "createDate"};
        XSSFWorkbook workbook = ExcelUtil.buildWorkBook(productList, headers, props, "商品列表");
        // 3.下载
        FileUtil.excelDownload(workbook, response);
    }

    @RequestMapping("/importExcel")
    @ResponseBody
    public ServerResponse importExcel(String path) {
        return productService.importExcel(path);
    }

    @RequestMapping("/exportPdf")
    public void exportPdf(ProductSearchParam productSearchParam, HttpServletResponse response) {
        //1.查询符合条件的数据
        List<Product> productList =  productService.findAllList(productSearchParam);
        //2.将数据转为指定的格式
        String htmlContent = productService.buildPdfHtml(productList);
        //3.通过IText将html转为pdf文件,下载
        FileUtil.pdfDownloadFile(response, htmlContent);
    }

    @RequestMapping("/exportWord")
    public void exportWord(ProductSearchParam productSearchParam, HttpServletRequest request, HttpServletResponse response) {
        //1.查询符合条件的数据
        List<Product> productList =  productService.findAllList(productSearchParam);
        //2.生成word文件
        String filePath = productService.buildWordFile(productList);
        //3.下载
        FileUtil.downloadFile(request, response, new File(filePath));
        //4.删除
        FileUtil.deleteFile(filePath);
    }


    @RequestMapping("/deleteBatch")
    @ResponseBody
    public ServerResponse deleteBatch(@RequestParam("ids[]") Long[] idArr) {
        return productService.deleteBatch(idArr);
    }

    @RequestMapping("/updateHotStatus")
    @ResponseBody
    public ServerResponse updateHotStatus(Long id, Integer status) {
        return productService.updateHotStatus(id, status);
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public ServerResponse updateStatus(Long id, Integer status) {
        return productService.updateStatus(id, status);
    }

    @RequestMapping("/toList")
    public String toList() {
        System.out.println("============toList");
        return "/product/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map findList(ProductSearchParam productSearchParam) {
        System.out.println("============商品列表");
        return productService.findList(productSearchParam);
    }

    @RequestMapping("/deleteProduct")
    @ResponseBody
    @Log(info = "删除商品")
    public ServerResponse deleteProduct(Long id, HttpServletRequest request) {
        Product product = productService.findProduct(id);
        String mainImagePath = product.getMainImagePath();
        if (StringUtils.isNotEmpty(mainImagePath)) {
            String realPath = request.getServletContext().getRealPath(mainImagePath);
            File file = new File(realPath);
            if (file.exists()) {
                file.delete();
            }
        }
        return productService.deleteProduct(id);
    }

    @RequestMapping("/toEdit")
    public String toEdit() {
        return "product/edit";
    }

    @RequestMapping("/findProduct")
    @ResponseBody
    public ServerResponse findProduct(Long id) {
        // 查询商品
        Product product = productService.findProduct(id);
        // 组装
        return ServerResponse.success(product);
    }

    @RequestMapping("/updateProduct")
    @ResponseBody
    public ServerResponse updateProduct(Product product, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(product.getMainImagePath())) {
            // 上传新图片
            // 删除老图片
            String oldMainImagePath = product.getOldMainImagePath();
            if (StringUtils.isNotEmpty(oldMainImagePath)) {
                String realPath = request.getServletContext().getRealPath(oldMainImagePath);
                File file = new File(realPath);
                if (file.exists()) {
                    file.delete();
                }
            }
        } else {
            // 还是老图片
            product.setMainImagePath(product.getOldMainImagePath());
        }
        productService.updateProduct(product);
        return ServerResponse.success();
    }

}
