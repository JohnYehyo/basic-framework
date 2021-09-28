package com.rongji.rjsoft.web.controller.system;

import cn.hutool.core.bean.BeanUtil;
import com.rongji.rjsoft.ao.system.LoginAo;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.entity.system.SysUser;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.service.ISysLoginService;
import com.rongji.rjsoft.service.ISysMenuService;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.system.menu.SysMenuInfoVo;
import com.rongji.rjsoft.vo.system.user.CurrentUserInfoVo;
import com.rongji.rjsoft.vo.system.user.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @description: 登录
 * @author: JohnYehyo
 * @create: 2021-04-26 15:31:17
 */
@Api(tags = "系统管理-登录管理")
@RestController
@AllArgsConstructor
public class SysLoginController {

    private final ISysLoginService sysLoginService;
    private final TokenUtils tokenUtils;
    private final ISysMenuService sysMenuService;

    /**
     * 登录
     * @param loginAo 登录信息
     * @return 登录结果
     */
    @ApiOperation(value = "登录")
    @PostMapping(value = "login")
    public Object login(@Valid @RequestBody LoginAo loginAo){
        String token = sysLoginService.login(loginAo);
        return ResponseVo.response(ResponseEnum.SUCCESS, token);
    }

    /**
     * 获取当前用户信息
     * @return 当前用户信息
     */
    @ApiOperation(value = "获取当前用户信息")
    @GetMapping(value = "getUserInfo")
    public Object userInfo(){
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        SysUserVo sysUserVo = new SysUserVo();
        BeanUtil.copyProperties(user, sysUserVo);
        //暂时不需要
//        Set<String> roles = sysRoleService.getRoleKeysByUserId(user.getUserId());
//        Set<String> menus = sysMenuService.getMenuPermsByUserId(user.getUserId());
        CurrentUserInfoVo currentUserInfoVo = new CurrentUserInfoVo(sysUserVo, null, null);
        return ResponseVo.response(ResponseEnum.SUCCESS, currentUserInfoVo);
    }

    /**
     * 获取当前用户路由信息
     * @return 当前用户路由信息
     */
    @ApiOperation(value = "获取当前用户路由信息")
    @GetMapping(value = "routes")
    public Object routes(){
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        List<SysMenuInfoVo> list = sysMenuService.getRoutesByUserId(user.getUserId());
        return ResponseVo.response(ResponseEnum.SUCCESS, list);
    }

}
