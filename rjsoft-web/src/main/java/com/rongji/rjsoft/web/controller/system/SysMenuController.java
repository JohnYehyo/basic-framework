package com.rongji.rjsoft.web.controller.system;


import com.rongji.rjsoft.ao.system.SysMenuAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.system.menu.MenuQuery;
import com.rongji.rjsoft.service.ISysMenuService;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.system.menu.MenuTreeVo;
import com.rongji.rjsoft.vo.system.menu.SysMenuInfoVo;
import com.rongji.rjsoft.vo.system.menu.SysMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
@Api(tags = "系统管理-菜单管理")
@RestController
@RequestMapping("/sysMenu")
@AllArgsConstructor
public class SysMenuController {

    private final ISysMenuService sysMenuService;
    private final TokenUtils tokenUtils;


    /**
     * 新增菜单
     *
     * @param sysMenuAo 菜单表单
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:menu:add')")
    @ApiOperation(value = "新增菜单")
    @PostMapping(value = "menu")
    @LogAction(module = "菜单管理", method = "添加菜单", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(SysMenuAo.add.class) @RequestBody SysMenuAo sysMenuAo) {
        //检查菜单名
        if (sysMenuService.checkMenuByName(sysMenuAo)) {
            return ResponseVo.error(ResponseEnum.FAIL.getCode(), "菜单名已存在!");
        }
        if(sysMenuService.saveMenu(sysMenuAo) > 0){
            return ResponseVo.success("新增菜单信息成功");
        }
        return ResponseVo.error("新增菜单信息失败");
    }

    /**
     * 编辑菜单
     *
     * @param sysMenuAo 菜单表单
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:menu:update')")
    @ApiOperation(value = "编辑菜单")
    @PutMapping(value = "menu")
    @LogAction(module = "菜单管理", method = "编辑菜单", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object edit(@Validated(SysMenuAo.update.class) @RequestBody SysMenuAo sysMenuAo) {
        //检查菜单名
        if (sysMenuService.checkMenuByName(sysMenuAo)) {
            return ResponseVo.error(ResponseEnum.FAIL.getCode(), "菜单名已存在!");
        }
        if (sysMenuAo.getMenuId().equals(sysMenuAo.getParentId())) {
            return ResponseVo.error(ResponseEnum.FAIL.getCode(), "上级菜单不能选择自己!");
        }
        if(sysMenuService.editMenu(sysMenuAo) > 0){
            return ResponseVo.success("编辑菜单信息成功");
        }
        return ResponseVo.error("编辑菜单信息失败");
    }

    /**
     * 删除菜单
     *
     * @param menuIds 菜单表单
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:menu:delete')")
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(name = "menuIds", value = "菜单id", required = true)
    @DeleteMapping(value = "menu/{menuIds}")
    @LogAction(module = "菜单管理", method = "删除菜单", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable("menuIds") Long[] menuIds) {
        if(sysMenuService.deleteMenu(menuIds) > 0){
            return ResponseVo.success("删除菜单信息成功");
        }
        return ResponseVo.error("删除菜单信息失败");
    }

    /**
     * 菜单列表
     *
     * @param menuQuery 菜单查询条件
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:menu:list')")
    @ApiOperation(value = "菜单列表查询")
    @GetMapping(value = "list")
    public Object list(MenuQuery menuQuery) {
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        menuQuery.setUserId(userId);
        List<SysMenuInfoVo> list = sysMenuService.listOfMenu(menuQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, list);
    }

    /**
     * 通过id获取菜单详情
     *
     * @param menuId 菜单查询条件
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:menu:query')")
    @ApiOperation(value = "通过id获取菜单详情")
    @ApiImplicitParam(name = "menuId", value = "菜单Id", required = true)
    @GetMapping(value = "meun/{menuId}")
    public Object menu(@PathVariable("menuId") Long menuId) {
        SysMenuVo sysMenuVo = sysMenuService.getMenuById(menuId);
        return ResponseVo.response(ResponseEnum.SUCCESS, sysMenuVo);
    }


    /**
     * 获取菜单树
     *
     * @return 返回结果
     */
    @ApiOperation(value = "获取菜单树")
    @GetMapping(value = "meunTree")
    public Object menu() {
        List<MenuTreeVo> list = sysMenuService.getMenuTree();
        return ResponseVo.response(ResponseEnum.SUCCESS, list);
    }

}
