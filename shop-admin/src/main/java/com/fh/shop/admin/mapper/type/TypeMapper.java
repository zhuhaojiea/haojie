package com.fh.shop.admin.mapper.type;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.type.TypeSearchParam;
import com.fh.shop.admin.po.type.Type;

import java.util.List;

public interface TypeMapper extends BaseMapper<Type> {
    Long findListCount(TypeSearchParam typeSearchParam);

    List<Type> findPageList(TypeSearchParam typeSearchParam);
}
