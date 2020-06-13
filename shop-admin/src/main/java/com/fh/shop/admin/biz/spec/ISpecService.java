package com.fh.shop.admin.biz.spec;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.spec.SpecParam;
import com.fh.shop.admin.param.spec.SpecSearchParam;
import com.fh.shop.admin.param.spec.SpecUpdateParam;

public interface ISpecService {

    public ServerResponse addSpec(SpecParam specParam);

    DataTableResult findList(SpecSearchParam specSearchParam);

    ServerResponse deleteSpec(Long id);

    ServerResponse findSpec(Long id);

    ServerResponse updateSpec(SpecUpdateParam specUpdateParam);

    ServerResponse findAllList();

}
