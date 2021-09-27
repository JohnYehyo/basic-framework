package com.rongji.rjsoft.web.controller.system;


import com.rongji.rjsoft.ao.system.SysRoleAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.system.role.RoleQuery;
import com.rongji.rjsoft.query.system.role.RoleSelectQuery;
import com.rongji.rjsoft.service.ISysRoleService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.system.role.SysRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-04-26
 */
@Api(tags = "系统管理-角色管理")
@RestController
@RequestMapping("/sysRole")
@AllArgsConstructor
public class SysRoleController {

    private final ISysRoleService sysRoleService;


    /**
     * 查询角色分页列表
     * @param roleQuery 查询条件
     * @return 分页数据
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:role:list')")
    @ApiOperation(value = "查询角色分页列表")
    @GetMapping(value = "page")
    public Object page(RoleQuery roleQuery){
        CommonPage<SysRoleVo> page = sysRoleService.pagesOfRole(roleQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);
    }

    /**
     * 通过角色id查询角色信息
     * @param roleId 角色id
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:role:query')")
    @ApiImplicitParam(name = "roleId", value = "角色id", required = true)
    @ApiOperation(value = "通过用户id查询角色信息")
    @GetMapping(value = "role/{roleId}")
    public Object getUserById(@PathVariable("roleId") Long roleId) {
        return ResponseVo.response(ResponseEnum.SUCCESS, sysRoleService.getRoleInfoById(roleId));
    }

    /**
     * 新增角色信息
     * @param sysRoleAo 角色表单信息
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:role:add')")
    @ApiOperation(value = "新增角色信息")
    @PostMapping(value = "role")
    @LogAction(module = "角色管理", method = "添加角色", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(SysRoleAo.add.class) @RequestBody SysRoleAo sysRoleAo){
        //检查角色名是否存在
        if(sysRoleService.checkRoleByRoleName(sysRoleAo)){
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "角色名已存在!");
        }
        //检查角色权限是否存在
        if(sysRoleService.checkRoleByRoleKey(sysRoleAo)){
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "角色权限已存在!");
        }
        if(sysRoleService.addRole(sysRoleAo) > 0){
            return ResponseVo.success("添加角色信息成功");
        }
        return ResponseVo.error("添加角色信息失败");
    }

    /**
     * 编辑角色信息
     * @param sysRoleAo 角色表单信息
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:role:update')")
    @ApiOperation(value = "修改角色信息")
    @PutMapping(value = "role")
    @LogAction(module = "角色管理", method = "编辑角色", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object edit(@Validated(SysRoleAo.update.class) @RequestBody SysRoleAo sysRoleAo){
        //检查角色名是否存在
        if(sysRoleService.checkRoleByRoleName(sysRoleAo)){
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "角色名已存在!");
        }
        //检查角色权限是否存在
        if(sysRoleService.checkRoleByRoleKey(sysRoleAo)){
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "角色权限已存在!");
        }
        if(sysRoleService.editRole(sysRoleAo) > 0){
            return ResponseVo.success("修改角色信息成功");
        }
        return ResponseVo.error("修改角色信息失败");
    }

    /**
     * 删除角色
     * @param roleIds 角色id
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:role:delete')")
    @ApiOperation(value = "删除角色信息")
    @ApiImplicitParam(name = "roleIds", value = "角色id", required = true)
    @DeleteMapping(value = "role/{roleIds}")
    @LogAction(module = "角色管理", method = "删除角色", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable("roleIds") Long[] roleIds){
        if(sysRoleService.deleteRole(roleIds) > 0){
            return ResponseVo.success("删除角色信息成功");
        }
        return ResponseVo.error("删除角色信息失败");
    }

    /**
     * 查询角色列表
     * @param roleSelectQuery 查询条件
     * @return 分页数据
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:role:list')")
    @ApiOperation(value = "查询角色列表")
    @GetMapping(value = "list")
    public Object list(RoleSelectQuery roleSelectQuery){
        return ResponseVo.response(ResponseEnum.SUCCESS, sysRoleService.listOfRole(roleSelectQuery));
    }
}
