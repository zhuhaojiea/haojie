package com.fh.shop.admin.biz.cate;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.cate.CategoryUpdateParam;
import com.fh.shop.admin.po.cate.Category;

import java.util.List;

public interface ICategoryService {

    public ServerResponse findAllCategoryList();

    ServerResponse delteCates(List<Long> ids);

    ServerResponse addCate(Category category);

    ServerResponse findCate(Long id);

    ServerResponse updateCate(CategoryUpdateParam categoryUpdateParam);

    ServerResponse findCateListById(Long id);
}
