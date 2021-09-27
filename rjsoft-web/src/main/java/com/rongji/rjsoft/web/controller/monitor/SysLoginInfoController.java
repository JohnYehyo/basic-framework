package com.rongji.rjsoft.web.controller.monitor;


import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.monitor.login.LoginInfoQuery;
import com.rongji.rjsoft.service.ISysLoginInfoService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.monitor.login.LoginInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统访问记录 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-09
 */
@Api(tags = "监控管理-系统访问")
@RestController
@RequestMapping("/sysLoginInfo")
@AllArgsConstructor
public class SysLoginInfoController {

    private final ISysLoginInfoService sysLoginInfoService;


    /**
     * 登录信息分页列表
     * @param loginInfoQuery 查询条件
     * @return 分页结果
     */
    @ApiOperation(value = "登录信息列表查询")
    @GetMapping(value = "list")
    public Object list(LoginInfoQuery loginInfoQuery){
        CommonPage<LoginInfoVo> pages = sysLoginInfoService.getPages(loginInfoQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, pages);
    }
}
