package com.fh.shop.admin.biz.type;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.attr.IAttrMapper;
import com.fh.shop.admin.mapper.attr.IAttrValueMapper;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.mapper.spec.ISpecMapper;
import com.fh.shop.admin.mapper.spec.ISpecValueMapper;
import com.fh.shop.admin.mapper.type.TypeBrandMapper;
import com.fh.shop.admin.mapper.type.TypeMapper;
import com.fh.shop.admin.mapper.type.TypeSpecMapper;
import com.fh.shop.admin.param.type.TypeParam;
import com.fh.shop.admin.param.type.TypeSearchParam;
import com.fh.shop.admin.param.type.TypeUpdateParam;
import com.fh.shop.admin.po.attr.Attr;
import com.fh.shop.admin.po.attr.AttrValue;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.spec.SpecValue;
import com.fh.shop.admin.po.type.Type;
import com.fh.shop.admin.po.type.TypeBrand;
import com.fh.shop.admin.po.type.TypeSpec;
import com.fh.shop.admin.vo.attr.AttrVo;
import com.fh.shop.admin.vo.spec.SpecVo;
import com.fh.shop.admin.vo.type.TypeRelateVo;
import com.fh.shop.admin.vo.type.TypeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("typeService")
public class ITypeServiceImpl implements ITypeService {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private TypeBrandMapper typeBrandMapper;
    @Autowired
    private TypeSpecMapper typeSpecMapper;
    @Autowired
    private IAttrMapper attrMapper;
    @Autowired
    private IAttrValueMapper attrValueMapper;
    @Autowired
    private IBrandMapper brandMapper;
    @Autowired
    private ISpecMapper specMapper;
    @Autowired
    private ISpecValueMapper specValueMapper;

    @Override
    public ServerResponse addType(TypeParam typeParam) {
        String typeName = typeParam.getTypeName();
        String specIds = typeParam.getSpecIds();
        String brandIds = typeParam.getBrandIds();
        Type type = new Type();
        type.setTypeName(typeName);
        typeMapper.insert(type);
        Long typeId = type.getId();
        // 添加类型规格关联表
        if (StringUtils.isNotEmpty(specIds)) {
            String[] specIdArr = specIds.split(",");
            for (String specId : specIdArr) {
                TypeSpec typeSpec = new TypeSpec();
                typeSpec.setTypeId(typeId);
                typeSpec.setSpecId(Long.valueOf(specId));
                typeSpecMapper.insert(typeSpec);
            }
        }
        // 添加类型品牌关联表
        if (StringUtils.isNotEmpty(brandIds)) {
            String[] brandIdArr = brandIds.split(",");
            for (String bandId : brandIdArr) {
                TypeBrand typeBrand = new TypeBrand();
                typeBrand.setBrandId(Long.valueOf(bandId));
                typeBrand.setTypeId(typeId);
                typeBrandMapper.insert(typeBrand);
            }
        }
        // 添加属性表并关联类型id
        String attrNames = typeParam.getAttrNames();
        String attrValues = typeParam.getAttrValues();
        if (StringUtils.isNotEmpty(attrNames) && StringUtils.isNotEmpty(attrValues)) {
            String[] attrNameArr = attrNames.split(",");
            String[] attrValueArr = attrValues.split(";");
            for (int i = 0; i < attrNameArr.length; i++) {
                String attrName = attrNameArr[i];
                String attrValueInfo = attrValueArr[i];
                Attr attr = new Attr();
                attr.setTypeId(typeId);
                attr.setAttrName(attrName);
                attr.setAttrValue(attrValueInfo);
                attrMapper.insert(attr);
                Long attrId = attr.getId();
                // 插入属性对应的属性值
                String[] attrValueList = attrValueInfo.split(",");
                for (int j = 0; j < attrValueList.length; j++) {
                    AttrValue attrValue = new AttrValue();
                    attrValue.setAttrId(attrId);
                    attrValue.setAttrValue(attrValueList[j]);
                    attrValue.setTypeId(typeId);
                    attrValueMapper.insert(attrValue);
                }
            }
        }
        return ServerResponse.success();
    }

    @Override
    public DataTableResult findList(TypeSearchParam typeSearchParam) {
        // 获取总条数
        Long count = typeMapper.findListCount(typeSearchParam);
        // 获取分页列表
        List<Type> typeList = typeMapper.findPageList(typeSearchParam);
        return new DataTableResult(typeSearchParam.getDraw(), count, count, typeList);
    }

    @Override
    public ServerResponse deleteType(Long id) {
        // 删除类型表
        typeMapper.deleteById(id);
        // 删除类型规格关联表
        QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
        typeSpecQueryWrapper.eq("typeId", id);
        typeSpecMapper.delete(typeSpecQueryWrapper);
        // 删除类型品牌关联表
        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", id);
        typeBrandMapper.delete(typeBrandQueryWrapper);
        // 删除类型对应的属性信息
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", id);
        attrMapper.delete(attrQueryWrapper);
        // 删除对应的属性值信息
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("typeId", id);
        attrValueMapper.delete(attrValueQueryWrapper);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findType(Long id) {
        TypeVo typeVo = new TypeVo();
        // 获取对应的类型
        Type type = typeMapper.selectById(id);
        // 获取对应的规格id集合
        QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
        typeSpecQueryWrapper.eq("typeId", id);
        List<TypeSpec> typeSpecList = typeSpecMapper.selectList(typeSpecQueryWrapper);
        List<Long> specIdList = new ArrayList<>();
        for (TypeSpec typeSpec : typeSpecList) {
            specIdList.add(typeSpec.getSpecId());
        }
        // 获取对应的品牌id集合
        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", id);
        List<TypeBrand> typeBrandList = typeBrandMapper.selectList(typeBrandQueryWrapper);
        List<Long> brandIdList = new ArrayList<>();
        for (TypeBrand typeBrand : typeBrandList) {
            brandIdList.add(typeBrand.getBrandId());
        }
        // 获取对应的属性集合
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", id);
        List<Attr> attrList = attrMapper.selectList(attrQueryWrapper);
        // 组装数据
        typeVo.setType(type);
        typeVo.setSpecIdList(specIdList);
        typeVo.setBrandIdList(brandIdList);
        typeVo.setAttrList(attrList);
        return ServerResponse.success(typeVo);
    }

    @Override
    public ServerResponse updateType(TypeUpdateParam typeUpdateParam) {
        //类型名的更新 t_type 【typeid】
        Type type = new Type();
        Long typeId = typeUpdateParam.getTypeId();
        type.setId(typeId);
        type.setTypeName(typeUpdateParam.getTypeName());
        typeMapper.updateById(type);
        //类型和规格关联信息的更新 t_type_spec
            //删除 类型规格关联表 中和当前类型 对应的所有的规格数据 【typeid】
            QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
            typeSpecQueryWrapper.eq("typeId", typeId);
            typeSpecMapper.delete(typeSpecQueryWrapper);
            //再重新插入新的 类型规格 的关联信息
            String specIds = typeUpdateParam.getSpecIds();
            if (StringUtils.isNotEmpty(specIds)) {
                    String[] specIdArr = specIds.split(",");
                    for (String specId : specIdArr) {
                        TypeSpec typeSpec = new TypeSpec();
                        typeSpec.setTypeId(typeId);
                        typeSpec.setSpecId(Long.valueOf(specId));
                        typeSpecMapper.insert(typeSpec);
                    }
                }
        //类型和品牌关联信息的更新 t_type_brand
            //删除 类型品牌关联表 中和当前类型 对应所有的品牌数据
            QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
            typeBrandQueryWrapper.eq("typeId", typeId);
            typeBrandMapper.delete(typeBrandQueryWrapper);
            //再重新插入新的 类型品牌 的关联信息
            String brandIds = typeUpdateParam.getBrandIds();
            if (StringUtils.isNotEmpty(brandIds)) {
                    String[] brandIdArr = brandIds.split(",");
                    for (String bandId : brandIdArr) {
                        TypeBrand typeBrand = new TypeBrand();
                        typeBrand.setBrandId(Long.valueOf(bandId));
                        typeBrand.setTypeId(typeId);
                        typeBrandMapper.insert(typeBrand);
                    }
                }
        //类型和属性的关联 t_attr t_attr_value
            // 删除类型对应的属性信息
            QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
            attrQueryWrapper.eq("typeId", typeId);
            attrMapper.delete(attrQueryWrapper);
            // 删除对应的属性值信息
            QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
            attrValueQueryWrapper.eq("typeId", typeId);
            attrValueMapper.delete(attrValueQueryWrapper);
         //在重新将当前的数据 插入到这两张表中
            // 添加属性表并关联类型id
            String attrNames = typeUpdateParam.getAttrNames();
            String attrValues = typeUpdateParam.getAttrValues();
            if (StringUtils.isNotEmpty(attrNames) && StringUtils.isNotEmpty(attrValues)) {
                String[] attrNameArr = attrNames.split(",");
                String[] attrValueArr = attrValues.split(";");
                for (int i = 0; i < attrNameArr.length; i++) {
                    String attrName = attrNameArr[i];
                    String attrValueInfo = attrValueArr[i];
                    Attr attr = new Attr();
                    attr.setTypeId(typeId);
                    attr.setAttrName(attrName);
                    attr.setAttrValue(attrValueInfo);
                    attrMapper.insert(attr);
                    Long attrId = attr.getId();
                    // 插入属性对应的属性值
                    String[] attrValueList = attrValueInfo.split(",");
                    for (int j = 0; j < attrValueList.length; j++) {
                        AttrValue attrValue = new AttrValue();
                        attrValue.setAttrId(attrId);
                        attrValue.setAttrValue(attrValueList[j]);
                        attrValue.setTypeId(typeId);
                        attrValueMapper.insert(attrValue);
                    }
                }
            }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findAll() {
        List<Type> types = typeMapper.selectList(null);
        return ServerResponse.success(types);
    }

    @Override
    public ServerResponse findTypeRelate(Long typeId) {
        // 获取类型对应的品牌列表
        List<Brand> brandList = buildBrandList(typeId);
        // 获取类型对应的属性列表
        List<AttrVo> attrVoList = buildAttrVoList(typeId);
        // 获取类型对应的规格列表
        List<SpecVo> specVoList = buildSpecVoList(typeId);
        // 组装数据
        TypeRelateVo typeRelateVo = new TypeRelateVo();
        typeRelateVo.setBrandList(brandList);
        typeRelateVo.setAttrVoList(attrVoList);
        typeRelateVo.setSpecVoList(specVoList);
        return ServerResponse.success(typeRelateVo);
    }

    private List<SpecVo> buildSpecVoList(Long typeId) {
        List<SpecVo> specVoList = new ArrayList<>();
        // 根据typeid在 类型规格关联表 中得到对应的 规格id列表
        QueryWrapper<TypeSpec> typeSpecQueryWrapper = new QueryWrapper<>();
        typeSpecQueryWrapper.eq("typeid", typeId);
        List<TypeSpec> typeSpecList = typeSpecMapper.selectList(typeSpecQueryWrapper);
        List<Long> specIdList = typeSpecList.stream().map(x -> x.getSpecId()).collect(Collectors.toList());
        // 根据规格 id列表 在规格表中找到对应的 规格 列表
//        List<Spec> specList = specMapper.selectBatchIds(specIdList);
        QueryWrapper<Spec> specQueryWrapper = new QueryWrapper<>();
        specQueryWrapper.in("id", specIdList);
        specQueryWrapper.orderByAsc("sort");
        List<Spec> specList = specMapper.selectList(specQueryWrapper);
        // 根据规格 id列表 在规格值表中找到对应的 规格值 列表
        QueryWrapper<SpecValue> specValueQueryWrapper = new QueryWrapper<>();
        specValueQueryWrapper.in("specId", specIdList);
        specValueQueryWrapper.orderByAsc("sort");
        List<SpecValue> specValueList = specValueMapper.selectList(specValueQueryWrapper);
        specList.forEach(x -> {
            SpecVo specVo = new SpecVo();
            specVo.setSpec(x);
            specVo.setSpecValues(specValueList.stream().filter(y -> y.getSpecId()==x.getId()).collect(Collectors.toList()));
            specVoList.add(specVo);
        });
        return specVoList;
    }

    private List<AttrVo> buildAttrVoList(Long typeId) {
        List<AttrVo> attrVoList = new ArrayList<>();
        // 根据typeId在属性表中找到所有对应的属性信息
        QueryWrapper<Attr> attrQueryWrapper = new QueryWrapper<>();
        attrQueryWrapper.eq("typeId", typeId);
        List<Attr> attrList = attrMapper.selectList(attrQueryWrapper);
        // 根据typeId在属性值表中找到所有对应的属性值信息
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("typeId", typeId);
        List<AttrValue> attrValueList = attrValueMapper.selectList(attrValueQueryWrapper);
        attrList.forEach(x -> {
            AttrVo attrVo = new AttrVo();
            attrVo.setAttr(x);
            List<AttrValue> attrValues = attrValueList.stream().filter(y -> y.getAttrId() == x.getId()).collect(Collectors.toList());
            attrVo.setAttrValueList(attrValues);
            attrVoList.add(attrVo);
        });
        return attrVoList;
    }

    private List<Brand> buildBrandList(Long typeId) {
        // 首先获取类型对应的品牌id集合 type_brand
        QueryWrapper<TypeBrand> typeBrandQueryWrapper = new QueryWrapper<>();
        typeBrandQueryWrapper.eq("typeId", typeId);
        List<TypeBrand> typeBrandList = typeBrandMapper.selectList(typeBrandQueryWrapper);
        List<Long> brandIdList = typeBrandList.stream().map(x -> x.getBrandId()).collect(Collectors.toList());
        // 根据品牌id集合从品牌表中取出来对应的品牌信息
        return brandMapper.selectBatchIds(brandIdList);
    }
}
