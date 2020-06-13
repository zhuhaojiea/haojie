package com.fh.shop.admin.biz.log;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.log.ILogMapper;
import com.fh.shop.admin.param.log.LogSearchParam;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.vo.log.LogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("logService")
public class ILogServiceImpl implements ILogService {

    @Autowired
    private ILogMapper logMapper;

    @Override
    public void addLog(Log log) {
        logMapper.addLog(log);
    }

    @Override
    public DataTableResult findLogList(LogSearchParam logSearchParam) {
        // 获取总条数
        Long totalCount = logMapper.findLogListCount(logSearchParam);
        // 获取分页列表
        List<Log> logList = logMapper.findLogPageList(logSearchParam);
        // po转vo
        List<LogVo> logVoList = new ArrayList<>();
        for (Log log : logList) {
            LogVo logVo = new LogVo();
            logVo.setContent(log.getContent());
            logVo.setInfo(log.getInfo());
            logVo.setUserName(log.getUserName());
            logVo.setRealName(log.getRealName());
            logVo.setId(log.getId());
            logVo.setParamInfo(log.getParamInfo());
            logVo.setStatus(log.getStatus());
            logVo.setInsertTime(DateUtil.date2str(log.getInsertTime(), DateUtil.FULL_TIME));
            logVoList.add(logVo);
        }
        // 组装数据
        return new DataTableResult(logSearchParam.getDraw(), totalCount, totalCount, logVoList);
    }
}
