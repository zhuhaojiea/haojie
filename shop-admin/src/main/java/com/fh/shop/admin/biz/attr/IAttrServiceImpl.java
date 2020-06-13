package com.fh.shop.admin.biz.attr;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.attr.IAttrMapper;
import com.fh.shop.admin.mapper.attr.IAttrValueMapper;
import com.fh.shop.admin.param.attr.AttrUpdateParam;
import com.fh.shop.admin.po.attr.Attr;
import com.fh.shop.admin.po.attr.AttrValue;
import com.fh.shop.admin.vo.attr.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("attrService")
public class IAttrServiceImpl implements IAttrService {

    @Autowired
    private IAttrMapper attrMapper;
    @Autowired
    private IAttrValueMapper attrValueMapper;

    @Override
    public ServerResponse findAttr(Long id) {
        // 查询属性信息
        Attr attr = attrMapper.selectById(id);
        // 查询属性值信息
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("attrId", id);
        List<AttrValue> attrValueList = attrValueMapper.selectList(attrValueQueryWrapper);
        // 组装数据
        AttrVo attrVo = new AttrVo();
        attrVo.setAttr(attr);
        attrVo.setAttrValueList(attrValueList);
        return ServerResponse.success(attrVo);
    }

    @Override
    public ServerResponse updateAttr(AttrUpdateParam attrUpdateParam) {
        //t_attr：
            //更新
        Long attrId = attrUpdateParam.getAttrId();
        Attr attr = new Attr();
        attr.setId(attrId);
        attr.setAttrName(attrUpdateParam.getAttrName());
        String attrValues = attrUpdateParam.getAttrValues();
        attr.setAttrValue(attrValues);
        attrMapper.updateById(attr);
       //t_attr_value：
            //删除
        QueryWrapper<AttrValue> attrValueQueryWrapper = new QueryWrapper<>();
        attrValueQueryWrapper.eq("attrId", attrId);
        attrValueMapper.delete(attrValueQueryWrapper);
            //增加
        if (StringUtils.isNotEmpty(attrValues)) {
            String[] attrValueArr = attrValues.split(",");
            for (String s : attrValueArr) {
                AttrValue attrValue = new AttrValue();
                attrValue.setAttrId(attrId);
                attrValue.setAttrValue(s);
                attrValue.setTypeId(attrUpdateParam.getTypeId());
                attrValueMapper.insert(attrValue);
            }
        }
        return ServerResponse.success();
    }
}
