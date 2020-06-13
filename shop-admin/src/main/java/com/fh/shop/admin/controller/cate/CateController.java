package com.fh.shop.admin.controller.cate;

import com.fh.shop.admin.biz.cate.ICategoryService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.cate.CategoryUpdateParam;
import com.fh.shop.admin.po.cate.Category;
import com.fh.shop.admin.po.product.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/cate")
@Controller
public class CateController {
    @Resource(name = "categoryService")
    private ICategoryService categoryService;

    @RequestMapping("/index")
    public String index() {
        return "cate/index";
    }

    @RequestMapping("/all")
    @ResponseBody
    public ServerResponse findAll() {
        return categoryService.findAllCategoryList();
    }

    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse findCateListById(Long id) {
        return categoryService.findCateListById(id);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delteCates(@RequestParam("ids[]") List<Long> ids) {
        return categoryService.delteCates(ids);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse addCate(Category category) {
        return categoryService.addCate(category);
    }

    @RequestMapping("/find")
    @ResponseBody
    public ServerResponse findCate(Long id) {
        return categoryService.findCate(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse updateCate(CategoryUpdateParam categoryUpdateParam) {
        return categoryService.updateCate(categoryUpdateParam);
    }
}
