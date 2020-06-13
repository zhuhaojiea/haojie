package com.fh.shop.admin.biz.spec;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.spec.ISpecMapper;
import com.fh.shop.admin.mapper.spec.ISpecValueMapper;
import com.fh.shop.admin.param.spec.SpecParam;
import com.fh.shop.admin.param.spec.SpecSearchParam;
import com.fh.shop.admin.param.spec.SpecUpdateParam;
import com.fh.shop.admin.po.spec.Spec;
import com.fh.shop.admin.po.spec.SpecValue;
import com.fh.shop.admin.vo.spec.SpecVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("specService")
public class ISpecServiceImpl implements ISpecService {

    @Autowired
    private ISpecMapper specMapper;
    @Autowired
    private ISpecValueMapper specValueMapper;

    @Override
    public ServerResponse addSpec(SpecParam specParam) {
        String specNames = specParam.getSpecNames();
        String specNameSorts = specParam.getSpecNameSorts();
        String specValues = specParam.getSpecValues();
        if (StringUtils.isEmpty(specNames) || StringUtils.isEmpty(specNameSorts) || StringUtils.isEmpty(specValues)) {
            return ServerResponse.error(ResponseEnum.SPEC_INFO_IS_NULL);
        }
        // 信息不为空
        String[] specNameArr = specNames.split(",");
        String[] specNameSortArr = specNameSorts.split(",");
        String[] specValueArr = specValues.split(";");
        for (int i = 0; i < specNameArr.length; i++) {
            String specName = specNameArr[i];
            String specNameSort = specNameSortArr[i];
            Spec spec = new Spec();
            spec.setSpecName(specName);
            spec.setSort(Integer.valueOf(specNameSort));
            specMapper.insert(spec);
            Long specId = spec.getId();
            String specValueInfo = specValueArr[i];
            String[] specValueInfoArr = specValueInfo.split(",");
            for (int j = 0; j < specValueInfoArr.length; j++) {
                String specValue = specValueInfoArr[j];
                String[] infoArr = specValue.split("=");
                SpecValue specValueDB = new SpecValue();
                specValueDB.setSpecId(specId);
                specValueDB.setSort(Integer.valueOf(infoArr[1]));
                specValueDB.setSpecValue(infoArr[0]);
                specValueMapper.insert(specValueDB);
            }
        }
        return ServerResponse.success();
    }

    @Override
    public DataTableResult findList(SpecSearchParam specSearchParam) {
        // 查询总条数
        Long count = specMapper.findListCount(specSearchParam);
        // 查询分页列表
        List<Spec> specList = specMapper.findPageList(specSearchParam);
        // 组装结果
        return new DataTableResult(specSearchParam.getDraw(), count, count, specList);
    }

    @Override
    public ServerResponse deleteSpec(Long id) {
        // 先删除规格值表
        QueryWrapper<SpecValue> specValueQueryWrapper = new QueryWrapper<>();
        specValueQueryWrapper.eq("specId", id);
        specValueMapper.delete(specValueQueryWrapper);
        // 再删除规格表
        specMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findSpec(Long id) {
        // 查询规格
        Spec spec = specMapper.selectById(id);
        // 查询规格对应的规格值
        QueryWrapper<SpecValue> specValueQueryWrapper = new QueryWrapper<>();
        specValueQueryWrapper.eq("specId", id);
        List<SpecValue> specValues = specValueMapper.selectList(specValueQueryWrapper);
        // 组装数据
        SpecVo specVo = new SpecVo();
        specVo.setSpec(spec);
        specVo.setSpecValues(specValues);
        return ServerResponse.success(specVo);
    }

    @Override
    public ServerResponse updateSpec(SpecUpdateParam specUpdateParam) {
        // 先更新规格表
        Spec spec = new Spec();
        Long id = specUpdateParam.getId();
        spec.setId(id);
        spec.setSort(Integer.valueOf(specUpdateParam.getSpecNameSort()));
        spec.setSpecName(specUpdateParam.getSpecName());
        specMapper.updateById(spec);
        // 删除规格值表和当前规格对应的数据
        QueryWrapper<SpecValue> specValueQueryWrapper = new QueryWrapper<>();
        specValueQueryWrapper.eq("specId", id);
        specValueMapper.delete(specValueQueryWrapper);
        // 再重新插入新的规格值
        String specValues = specUpdateParam.getSpecValues();
        if (StringUtils.isNotEmpty(specValues)) {
                String[] specValueInfoArr = specValues.split(",");
                for (int j = 0; j < specValueInfoArr.length; j++) {
                    String specValue = specValueInfoArr[j];
                    String[] infoArr = specValue.split("=");
                    SpecValue specValueDB = new SpecValue();
                    specValueDB.setSpecId(id);
                    specValueDB.setSort(Integer.valueOf(infoArr[1]));
                    specValueDB.setSpecValue(infoArr[0]);
                    specValueMapper.insert(specValueDB);
                }
            }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findAllList() {
        QueryWrapper<Spec> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        List<Spec> specs = specMapper.selectList(queryWrapper);
        return ServerResponse.success(specs);
    }
}
