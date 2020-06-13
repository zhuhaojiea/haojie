package com.fh.shop.admin.controller.type;

import com.fh.shop.admin.biz.type.ITypeService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.type.TypeParam;
import com.fh.shop.admin.param.type.TypeSearchParam;
import com.fh.shop.admin.param.type.TypeUpdateParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/type")
public class TypeController {

    @Resource(name="typeService")
    private ITypeService typeService;

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "type/add";
    }

    @RequestMapping("/toList")
    public String toList() {
        return "type/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataTableResult list(TypeSearchParam typeSearchParam) {
        return typeService.findList(typeSearchParam);
    }

    @RequestMapping("/findTypeRelate")
    @ResponseBody
    public ServerResponse findTypeRelate(Long typeId) {
        return typeService.findTypeRelate(typeId);
    }

    @RequestMapping("/all")
    @ResponseBody
    public ServerResponse findAll() {
        return typeService.findAll();
    }

    @RequestMapping("/toEdit")
    public String toEdit() {
        return "type/edit";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse deleteType(Long id) {
        return typeService.deleteType(id);
    }

    @RequestMapping("/find")
    @ResponseBody
    public ServerResponse findType(Long id) {
        return typeService.findType(id);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(TypeParam typeParam) {
        return typeService.addType(typeParam);
    }

    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse update(TypeUpdateParam typeUpdateParam) {
        return typeService.updateType(typeUpdateParam);
    }
}
