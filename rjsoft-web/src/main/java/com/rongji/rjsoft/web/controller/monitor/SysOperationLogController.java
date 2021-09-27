package com.rongji.rjsoft.web.controller.monitor;


import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.monitor.log.OperationLogQuery;
import com.rongji.rjsoft.service.ISysOperationLogService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.monitor.log.OperatorLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-08
 */
@Api(tags = "监控管理-操作日志")
@RestController
@RequestMapping("/sysOperationLog")
@AllArgsConstructor
public class SysOperationLogController {

    private final ISysOperationLogService sysOperationLogService;

    /**
     * 操作日志分页列表
     * @param operationLogQuery 查询条件
     * @return 操作日志分页数据
     */
    @ApiOperation("操作日志分页列表")
    @GetMapping(value = "list")
    public Object list(OperationLogQuery operationLogQuery){
        CommonPage<OperatorLogVo> page = sysOperationLogService.pageList(operationLogQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);
    }

    /**
     * 操作日志详情
     * @param id 日志id
     * @return 操作日志详情
     */
    @ApiOperation(value = "操作日志详情")
    @ApiImplicitParam(name = "id", value = "日志id", required = true)
    @GetMapping(value = "log/{id}")
    public Object log(@PathVariable Long id){
        return sysOperationLogService.getLogById(id);
    }
}
