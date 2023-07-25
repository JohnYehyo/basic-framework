package com.rongji.rjsoft.web.controller.commom;

import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.service.MultiTaskService;
import com.rongji.rjsoft.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 多线程任务示例
 * @author: JohnYehyo
 * @create: 2023-07-25 09:14:16
 */
@Api(tags = "多线程任务示例")
@RestController
@RequestMapping(value = "multi-task")
@AllArgsConstructor
public class MultiTaskController {

    private final MultiTaskService MultiTaskService;

    @ApiOperation("需要合并结果的异步任务")
    @GetMapping(value = "merge-task")
    public ResponseVo mergeDemo(){
        return ResponseVo.response(ResponseEnum.SUCCESS, MultiTaskService.mergeDemo());
    }

    @ApiOperation("同步任务")
    @GetMapping(value = "sync-task")
    public ResponseVo syncDemo(){
        return ResponseVo.response(ResponseEnum.SUCCESS, MultiTaskService.syncDemo());
    }
}