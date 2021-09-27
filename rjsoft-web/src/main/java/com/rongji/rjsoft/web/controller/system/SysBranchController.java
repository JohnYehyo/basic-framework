package com.rongji.rjsoft.web.controller.system;


import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.service.ISysBranchService;
import com.rongji.rjsoft.vo.ResponseVo;
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
 * 行政区划表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-03
 */
@Api(tags = "系统管理-行政区划管理")
@RestController
@RequestMapping("/sysBranch")
@AllArgsConstructor
public class SysBranchController {

    private final ISysBranchService sysBranchService;

    /**
     * 行政区划异步树
     *
     * @param branchCode
     * @return
     */
    @ApiOperation(value = "行政区划异步树")
    @ApiImplicitParam(name = "branchCode", value = "行政区划code")
    @GetMapping(value = "asynchTree")
    public Object asynchTree(String branchCode) {
        return ResponseVo.response(ResponseEnum.SUCCESS, sysBranchService.asynchTree(branchCode));
    }

}
