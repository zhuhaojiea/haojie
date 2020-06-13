package com.fh.shop.admin.biz.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.goods.IGoodsCommonMapper;
import com.fh.shop.admin.mapper.goods.IGoodsMapper;
import com.fh.shop.admin.param.goods.GoodsAddParam;
import com.fh.shop.admin.param.goods.GoodsSearchParam;
import com.fh.shop.admin.po.goods.Goods;
import com.fh.shop.admin.po.goods.GoodsCommon;
import com.fh.shop.admin.vo.goods.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("goodsService")
public class IGoodsServiceImpl implements IGoodsService {
    @Autowired
    private IGoodsMapper goodsMapper;
    @Autowired
    private IGoodsCommonMapper goodsCommonMapper;

    @Override
    public ServerResponse addGoods(GoodsAddParam goodsAddParam) {
        // 插入goods_common[SPU表]
        GoodsCommon goodsCommon = goodsAddParam.getGoodsCommon();
        goodsCommonMapper.insert(goodsCommon);
        Long commonId = goodsCommon.getId();
        String spuProductName = goodsCommon.getProductName();
        // 插入goods表[SKU表]
        // 1000,255,100
        String prices = goodsAddParam.getPrices();
        // 10,5,28
        String stocks = goodsAddParam.getStocks();
        // 11:红色,23:标准版;12:绿色,23:标准版;11:红色,24:豪华版
        String specValueInfos = goodsAddParam.getSpecValueInfos();
        if (StringUtils.isEmpty(prices) || StringUtils.isEmpty(stocks) || StringUtils.isEmpty(specValueInfos)) {
            return ServerResponse.error(ResponseEnum.SKU_INFO_IS_EMPTY);
        }
        String[] priceArr = prices.split(",");
        String[] stockArr = stocks.split(",");
        String[] speceValueArr = specValueInfos.split(";");
        for (int i = 0; i < priceArr.length; i++) {
            Goods goods = new Goods();
            goods.setPrice(new BigDecimal(priceArr[i]));
            goods.setStock(Long.parseLong(stockArr[i]));
            goods.setCommonId(commonId);
            String specValue = speceValueArr[i]; // 11:红色,23:标准版,66:32G
            String[] specValueIdAndNameArr = specValue.split(",");
            StringBuilder skuProductName = new StringBuilder();
            StringBuilder skuSpecValueId = new StringBuilder();
            skuProductName.append(spuProductName);
            for (String s : specValueIdAndNameArr) {
                skuProductName.append(s.split(":")[1]);
                skuSpecValueId.append(",").append(s.split(":")[0]);
            }
            // sku的名字 SKU的商品名 格式【spu名字+sku对应的规格值的名字】 例如 小米6s红色64G
            goods.setProductName(skuProductName.toString());
            // SKU规格值ID 格式【11,23】
            goods.setSpecValueId(skuSpecValueId.substring(1));
            // SKU规格值信息 格式【11:红色,23:标准版】
            goods.setSpecValueInfo(specValue);
            goodsMapper.insert(goods);
        }
        return ServerResponse.success();
    }

    @Override
    public DataTableResult findGoodsList(GoodsSearchParam goodsSearchParam) {
        // 获取总条数[分页依据的是 主表 spu]
        Long totalCount = goodsCommonMapper.findGoodsListCount(goodsSearchParam);
        List<GoodsVo> goodsVoList = new ArrayList<>();
        if (totalCount > 0) {
            // 获取分页列表
            List<GoodsCommon> goodsCommonList = goodsCommonMapper.findGoodsPageList(goodsSearchParam);
            // 获取当前页中goodCommond的id集合
            List<Long> idList = goodsCommonList.stream().map(x -> x.getId()).collect(Collectors.toList());
            // 查询t_goods表中和当前GoodsCommon对应的goods列表
            QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
            goodsQueryWrapper.in("commonId", idList);
            List<Goods> goodsList = goodsMapper.selectList(goodsQueryWrapper);
            goodsCommonList.forEach(x -> {
                GoodsVo goodsVo = new GoodsVo();
                goodsVo.setGoodsCommon(x);
                List<Goods> goodsInfoList = goodsList.stream().filter(y -> y.getCommonId() == x.getId()).collect(Collectors.toList());
                goodsVo.setGoodsList(goodsInfoList);
                goodsVoList.add(goodsVo);
            });
        }
        // 组装数据
        return new DataTableResult(goodsSearchParam.getDraw(), totalCount, totalCount, goodsVoList);
    }
}
