package com.fh.shop.admin.biz.area;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.area.IAreaMapper;
import com.fh.shop.admin.po.area.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("areaService")
public class IAreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaMapper areaMapper;

    @Override
    public ServerResponse findAllAreaList() {
        List<Area> allAreaList = areaMapper.findAllAreaList();
        return ServerResponse.success(allAreaList);
    }

    @Override
    public ServerResponse addArea(Area area)  {
        areaMapper.addArea(area);
        return ServerResponse.success(area.getId());
    }

    @Override
    public ServerResponse deleteAreas(Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        areaMapper.deleteAreas(idList);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findArea(Long id) {
        Area area = areaMapper.findArea(id);
        return ServerResponse.success(area);
    }

    @Override
    public ServerResponse updateArea(Area area) {
        areaMapper.updateArea(area);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findChilds(Long id) {
        List<Area> areas = areaMapper.findChilds(id);
        return ServerResponse.success(areas);
    }
}
