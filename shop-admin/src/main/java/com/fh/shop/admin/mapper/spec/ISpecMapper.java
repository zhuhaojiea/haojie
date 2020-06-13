package com.fh.shop.admin.mapper.spec;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.spec.SpecSearchParam;
import com.fh.shop.admin.po.spec.Spec;

import java.util.List;

public interface ISpecMapper extends BaseMapper<Spec> {

    Long findListCount(SpecSearchParam specSearchParam);

    List<Spec> findPageList(SpecSearchParam specSearchParam);
}
