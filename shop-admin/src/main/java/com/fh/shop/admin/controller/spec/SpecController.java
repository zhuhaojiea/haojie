package com.fh.shop.admin.controller.spec;

import com.fh.shop.admin.biz.spec.ISpecService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.spec.SpecParam;
import com.fh.shop.admin.param.spec.SpecSearchParam;
import com.fh.shop.admin.param.spec.SpecUpdateParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("/spec")
@Controller
public class SpecController {

    @Resource(name="specService")
    private ISpecService specService;

    @RequestMapping("/toAdd")
    public String toAdd() {
      return "spec/add";
    }

    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(SpecParam specParam) {
        return specService.addSpec(specParam);
    }

    @RequestMapping("/toList")
    public String toList() {
        return "spec/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataTableResult list(SpecSearchParam specSearchParam) {
        return specService.findList(specSearchParam);
    }

    @RequestMapping("/all")
    @ResponseBody
    public ServerResponse findAllList() {
        return specService.findAllList();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ServerResponse delete(Long id) {
        return specService.deleteSpec(id);
    }

    @RequestMapping("/toEdit")
    public String toEdit() {
        return "spec/edit";
    }

    @RequestMapping("/find")
    @ResponseBody
    public ServerResponse findSpec(Long id) {
        return specService.findSpec(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    public ServerResponse update(SpecUpdateParam specUpdateParam) {
        return specService.updateSpec(specUpdateParam);
    }
}
