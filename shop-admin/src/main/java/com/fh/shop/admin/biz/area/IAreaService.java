package com.fh.shop.admin.biz.area;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.area.Area;

public interface IAreaService {

    public ServerResponse findAllAreaList();

    ServerResponse addArea(Area area);

    ServerResponse deleteAreas(Long[] ids);

    ServerResponse findArea(Long id);

    ServerResponse updateArea(Area area);

    ServerResponse findChilds(Long id);
}
