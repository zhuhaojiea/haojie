package com.fh.shop.admin.biz.cate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.mapper.cate.ICategoryMapper;
import com.fh.shop.admin.param.cate.CategoryUpdateParam;
import com.fh.shop.admin.po.cate.Category;
import com.fh.shop.admin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("categoryService")
public class ICategoryServiceImpl implements ICategoryService {

    @Autowired
    public ICategoryMapper categoryMapper;

    @Override
    public ServerResponse findAllCategoryList() {
        // 先查缓存
        String cateListJson = RedisUtil.get(SystemConstant.CATELIST_KEY);
        // 缓存中有数据，直接返回
        if (StringUtils.isNotEmpty(cateListJson)) {
            // 将json格式的字符串转为java对象
            List<Category> categories = JSONObject.parseArray(cateListJson, Category.class);
            // 续命
            RedisUtil.expire(SystemConstant.CATELIST_KEY, SystemConstant.CATELIST_KEY_EXPIRE);
            return ServerResponse.success(categories);
        }
        // 缓存中没有数据，查询数据库，并且将数据往缓存中放一份，再返回
        List<Category> categoryList = categoryMapper.selectList(null);
        // 将java对象转换为json格式的字符串
        String cateJsonStr = JSONObject.toJSONString(categoryList);
        // 存一份到缓存中
        RedisUtil.setEx(SystemConstant.CATELIST_KEY, cateJsonStr, SystemConstant.CATELIST_KEY_EXPIRE);
        // 顺带着再将数据返回给前台
        return ServerResponse.success(categoryList);
    }

    @Override
    public ServerResponse delteCates(List<Long> ids) {
        RedisUtil.delete("cateList");
        categoryMapper.deleteBatchIds(ids);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse addCate(Category category) {
        RedisUtil.delete("cateList");
        categoryMapper.insert(category);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findCate(Long id) {
        Category category = categoryMapper.selectById(id);
        return ServerResponse.success(category);
    }

    @Override
    public ServerResponse updateCate(CategoryUpdateParam categoryUpdateParam) {
        RedisUtil.delete("cateList");
        Category category = categoryUpdateParam.getCategory();
        categoryMapper.updateById(category);
        // 如果关联到子类的复选框被选中
        if (categoryUpdateParam.getRelateFlag() == SystemConstant.NEED_UPDATE_CHILDS) {
            // 将当前节点信息子子孙孙的typeid和typename进行更新，更新的依据 子子孙孙id的集合
            String ids = categoryUpdateParam.getIds();
            if (StringUtils.isNotEmpty(ids)) {
                // 才能证明当前节点下 有 子子孙孙 的节点
                Category cate = new Category();
                cate.setTypeName(category.getTypeName());
                cate.setTypeId(category.getTypeId());
                QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
                String[] idArr = ids.split(",");
//                List<Long> idList = new ArrayList<>();
//                for (String s : idArr) {
//                    idList.add(Long.parseLong(s));
//                }
                List<Long> idList = Arrays.stream(idArr).map(x -> Long.parseLong(x)).collect(Collectors.toList());
                categoryQueryWrapper.in("id", idList);
                categoryMapper.update(cate, categoryQueryWrapper);
            }

        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findCateListById(Long id) {
//        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
//        categoryQueryWrapper.eq("fatherId", id);
//        List<Category> categoryList = categoryMapper.selectList(categoryQueryWrapper);
//        return ServerResponse.success(categoryList);
        // 先从缓存中取出来 所有的分类
        String cateListJson = RedisUtil.get(SystemConstant.CATELIST_KEY);
        List<Category> categoryList = new ArrayList<>();
        if (StringUtils.isEmpty(cateListJson)) {
            // 缓存中没有数据，查询数据库，并且将数据往缓存中放一份，再返回
            categoryList = categoryMapper.selectList(null);
            // 将java对象转换为json格式的字符串
            String cateJsonStr = JSONObject.toJSONString(categoryList);
            // 存一份到缓存中
            RedisUtil.setEx(SystemConstant.CATELIST_KEY, cateJsonStr, SystemConstant.CATELIST_KEY_EXPIRE);
        } else {
            categoryList = JSONObject.parseArray(cateListJson, Category.class);
            // 续命
            RedisUtil.expire(SystemConstant.CATELIST_KEY, SystemConstant.CATELIST_KEY_EXPIRE);
        }
        // 过滤数据
        List<Category> categoryListInfo = categoryList.stream().filter(x -> x.getFatherId() == id).collect(Collectors.toList());
        // 返回数据
        return ServerResponse.success(categoryListInfo);
    }
}
