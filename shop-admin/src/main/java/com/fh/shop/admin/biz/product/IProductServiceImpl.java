package com.fh.shop.admin.biz.product;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.mapper.product.IProductMapper;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.vo.product.ProductVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Service("productService")
public class IProductServiceImpl implements IProductService {

    @Autowired
    private IProductMapper productMapper;
    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public void addProduct(Product product){
        // 赋值录入时间
        Date now = new Date();
        product.setInsertTime(now);
        product.setUpdateTime(now);
        productMapper.insert(product);
    }


    @Override
    public Map findList(ProductSearchParam productSearchParam) {
        // 获取总条数
        Long totalCount = productMapper.findCount(productSearchParam);
        // 获取分页列表
        List<Product> productList = productMapper.findPageList(productSearchParam);
        // po转vo
        List<ProductVo> productVoList = new ArrayList<>();
        for (Product product : productList) {
            ProductVo productVo = new ProductVo();
            productVo.setId(product.getId());
            productVo.setProductName(product.getProductName());
            productVo.setPrice(product.getPrice().toString());
            productVo.setBrandName(product.getBrandName());
            productVo.setCreateDate(DateUtil.date2str(product.getCreateDate(), DateUtil.Y_M_D));
            productVo.setInsertTime(DateUtil.date2str(product.getInsertTime(), DateUtil.FULL_TIME));
            productVo.setUpdateTime(DateUtil.date2str(product.getUpdateTime(), DateUtil.FULL_TIME));
            productVo.setMainImagePath(product.getMainImagePath());
            productVo.setIsHot(product.getIsHot());
            productVo.setStatus(product.getStatus());
            productVoList.add(productVo);
        }
        Map result = new HashMap();
        result.put("draw", productSearchParam.getDraw());
        result.put("recordsFiltered", totalCount);
        result.put("recordsTotal", totalCount);
        result.put("data", productVoList);
        return result;
    }

    @Override
    public ServerResponse deleteProduct(Long id) {
        // 删除操作
        productMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public Product findProduct(Long id) {
        Product product = productMapper.selectById(id);
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        product.setUpdateTime(new Date());
        productMapper.updateById(product);
    }

    @Override
    public List<Product> findAllList(ProductSearchParam productSearchParam) {
        List<Product> productList = productMapper.findAllList(productSearchParam);
        return productList;
    }



    @Override
    public ServerResponse deleteBatch(Long[] idArr) {
        List<Long> idList = Arrays.asList(idArr);
        productMapper.deleteBatchIds(idList);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateHotStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        product.setIsHot(status);
        productMapper.updateById(product);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        product.setStatus(status);
        productMapper.updateById(product);
        return ServerResponse.success();
    }

    @Override
    public String buildPdfHtml(List<Product> productList) {
        //将 数据 和 模板文件 通过 freemarker结合起来生成html代码
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(), SystemConstant.TEMPLATE_PATH);
        String htmlContent = "";
        try {
            Template template = configuration.getTemplate(SystemConstant.PDF_TEMPLATE);
            Map result = new HashMap();
            result.put("products", productList);
            // 融合
            StringWriter sw = new StringWriter();
            template.process(result, sw);
            htmlContent = sw.toString();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (TemplateException e1) {
            e1.printStackTrace();
        }
        return htmlContent;
    }



    @Override
    public ServerResponse importExcel(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            // 读取excel文件，获取数据
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            System.out.println(lastRowNum);
            // 查询所有品牌
            List<Brand> allBrandList = brandMapper.findAllBrandList();
            for (int i = 1; i <= lastRowNum; i++) {
                // 构建数据
                Product product = buildProduct(sheet, i, allBrandList);
                // 将数据插入到数据库中
                productMapper.insert(product);
            }
            // 删除文件
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResponse.success();
    }

    @Override
    public String buildWordFile(List<Product> productList) {
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        String filePath = "";
        try {
            //2.将数据转为指定的格式
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("utf-8");
            configuration.setClassForTemplateLoading(this.getClass(), SystemConstant.TEMPLATE_PATH);
            Template template = configuration.getTemplate(SystemConstant.WORD_TEMPLATE);
            // po转vo
            List<ProductVo> productVoList = buildProductVoList(productList);
            Map data = new HashMap();
            data.put("products", productVoList);
            filePath = SystemConstant.WORD_SAVE_PATH + UUID.randomUUID().toString() + SystemConstant.WORD_SUFFIX;
            fileOutputStream = new FileOutputStream(filePath);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
            template.process(data, outputStreamWriter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStreamWriter) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }

    private List<ProductVo> buildProductVoList(List<Product> productList) {
        List<ProductVo> productVoList = new ArrayList<>();
        for (Product product : productList) {
            ProductVo productVo = new ProductVo();
            productVo.setId(product.getId());
            productVo.setProductName(product.getProductName());
            productVo.setPrice(product.getPrice().toString());
            productVo.setBrandName(product.getBrandName());
            productVo.setCreateDate(DateUtil.date2str(product.getCreateDate(), DateUtil.Y_M_D));
            productVoList.add(productVo);
        }
        return productVoList;
    }


    private Product buildProduct(XSSFSheet sheet, int i, List<Brand> allBrandList) {
        XSSFRow row = sheet.getRow(i);
        String productName = row.getCell(0).getStringCellValue();
        double price = row.getCell(1).getNumericCellValue();
        Date createDate = row.getCell(2).getDateCellValue();
        String brandName = row.getCell(3).getStringCellValue();

        // 根据品牌名找到对应的品牌id
        // Long brandId = brandMapper.findBrandIdByName(brandName);
        Long brandId = findBrandId(allBrandList, brandName);
        if (brandId == null) {
            // 证明当前品牌不存在，则进行插入
            Brand brand = new Brand();
            brand.setBrandName(brandName);
            brandMapper.addBrand(brand);
            brandId = brand.getId();
        }
        Product product = new Product();
        product.setProductName(productName);
        product.setBrandId(brandId);
        product.setPrice(new BigDecimal(price));
        product.setCreateDate(createDate);
        Date insertTime = new Date();
        product.setInsertTime(insertTime);
        product.setUpdateTime(insertTime);
        return product;
    }

    private Long findBrandId(List<Brand> allBrandList, String brandName) {
        Long brandId = null;
        for (Brand brand : allBrandList) {
            if (brand.getBrandName().equals(brandName)) {
                brandId = brand.getId();
            }
        }
        return brandId;
    }


}
