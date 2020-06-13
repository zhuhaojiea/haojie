package com.fh.shop.admin.biz.type;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.type.TypeParam;
import com.fh.shop.admin.param.type.TypeSearchParam;
import com.fh.shop.admin.param.type.TypeUpdateParam;

public interface ITypeService {

    public ServerResponse addType(TypeParam typeParam);

    DataTableResult findList(TypeSearchParam typeSearchParam);

    ServerResponse deleteType(Long id);

    ServerResponse findType(Long id);

    ServerResponse updateType(TypeUpdateParam typeUpdateParam);

    ServerResponse findAll();


    ServerResponse findTypeRelate(Long typeId);
}
