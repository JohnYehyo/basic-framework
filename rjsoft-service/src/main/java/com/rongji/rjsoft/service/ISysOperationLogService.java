package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.entity.monitor.SysOperationLog;
import com.rongji.rjsoft.query.monitor.log.OperationLogQuery;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.monitor.log.OperatorLogInfoVo;
import com.rongji.rjsoft.vo.monitor.log.OperatorLogVo;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-08
 */
public interface ISysOperationLogService extends IService<SysOperationLog> {

    /**
     * 操作日志分页列表
     * @param operationLogQuery 查询条件
     * @return 操作日志分页数据
     */
    CommonPage<OperatorLogVo> pageList(OperationLogQuery operationLogQuery);

    /**
     * 操作日志详情
     * @param id 日志id
     * @return 操作日志详情
     */
    OperatorLogInfoVo getLogById(Long id);
}
