package com.fh.shop.admin.controller.brand;

import com.fh.shop.admin.biz.brand.IBrandService;
import com.fh.shop.admin.common.ServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Resource(name="brandService")
    private IBrandService brandService;

    @RequestMapping("/all")
    @ResponseBody
    public ServerResponse findAllBrandList() {
        System.out.println("============品牌列表");
        ServerResponse serverResponse = brandService.findAllBrandList();
        return serverResponse;
    }
}
