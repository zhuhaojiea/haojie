package com.fh.shop.admin.mapper.area;

import com.fh.shop.admin.po.area.Area;

import java.util.List;

public interface IAreaMapper {

    public List<Area> findAllAreaList();

    void addArea(Area area);

    void deleteAreas(List<Long> ids);

    Area findArea(Long id);

    void updateArea(Area area);

    List<Area> findChilds(Long id);
}
