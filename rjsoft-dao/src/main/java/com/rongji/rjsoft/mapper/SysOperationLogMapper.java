package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.monitor.SysOperationLog;
import com.rongji.rjsoft.query.monitor.log.OperationLogQuery;
import com.rongji.rjsoft.vo.monitor.log.OperatorLogVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-08
 */
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {

    /**
     * 操作日志分页列表
     * @param pages 分页对象
     * @param operationLogQuery 查询参数
     * @return 操作日志分页
     */
    IPage<OperatorLogVo> getPages(IPage<OperatorLogVo> pages, @Param("param") OperationLogQuery operationLogQuery);
}
