package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.entity.monitor.SysOperationLog;
import com.rongji.rjsoft.mapper.SysOperationLogMapper;
import com.rongji.rjsoft.query.monitor.log.OperationLogQuery;
import com.rongji.rjsoft.service.ISysOperationLogService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.monitor.log.OperatorLogInfoVo;
import com.rongji.rjsoft.vo.monitor.log.OperatorLogVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-08
 */
@Service
@AllArgsConstructor
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements ISysOperationLogService {

    private final SysOperationLogMapper sysOperationLogMapper;

    /**
     * 操作日志分页列表
     * @param operationLogQuery 查询条件
     * @return 操作日志分页数据
     */
    @Override
    public CommonPage<OperatorLogVo> pageList(OperationLogQuery operationLogQuery) {
        if(null == operationLogQuery.getCurrent()){
            operationLogQuery.setCurrent(1);
        }
        if(null == operationLogQuery.getPageSize()){
            operationLogQuery.setPageSize(10);
        }
        IPage<OperatorLogVo> pages = new Page<>(operationLogQuery.getCurrent(), operationLogQuery.getPageSize());
        pages = sysOperationLogMapper.getPages(pages, operationLogQuery);
        return CommonPageUtils.assemblyPage(pages);
    }

    /**
     * 操作日志详情
     * @param id 日志id
     * @return 操作日志详情
     */
    @Override
    public OperatorLogInfoVo getLogById(Long id) {
        SysOperationLog sysOperationLog = sysOperationLogMapper.selectById(id);
        OperatorLogInfoVo operatorLogInfoVo = new OperatorLogInfoVo();
        BeanUtil.copyProperties(sysOperationLog, operatorLogInfoVo);
        return operatorLogInfoVo;
    }
}
