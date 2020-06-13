package com.fh.shop.admin.controller.area;

import com.fh.shop.admin.annotation.Log;
import com.fh.shop.admin.biz.area.IAreaService;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.area.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/area")
public class AreaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    @Resource(name="areaService")
    private IAreaService areaService;

    @RequestMapping("/index")
    public String index() {
        return "/area/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ServerResponse findAllAreaList() {
        return areaService.findAllAreaList();
    }

    @RequestMapping("/findChilds")
    @ResponseBody
    public ServerResponse findChilds(Long id) {
        return areaService.findChilds(id);
    }


    @RequestMapping("/add")
    @Log(info = "添加地区")
    public @ResponseBody ServerResponse addArea(Area area) {
        // 核心代码
        ServerResponse serverResponse = areaService.addArea(area);
        return serverResponse;
    }

    @RequestMapping("/deleteAreas")
    @Log(info = "删除地区")
    @ResponseBody
    public ServerResponse deleteAreas(@RequestParam("ids[]") Long[] ids) {
        // 核心代码
        ServerResponse serverResponse = areaService.deleteAreas(ids);
        return serverResponse;
    }

    @RequestMapping("/find")
    @ResponseBody
    public ServerResponse findArea(Long id) {
        return areaService.findArea(id);
    }

    @RequestMapping("/update")
    @ResponseBody
    @Log(info = "修改地区")
    public ServerResponse updateArea(Area area) {
        return areaService.updateArea(area);
    }


}
