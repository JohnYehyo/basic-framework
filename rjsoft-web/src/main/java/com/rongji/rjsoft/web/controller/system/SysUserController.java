package com.rongji.rjsoft.web.controller.system;


import com.rongji.rjsoft.ao.system.SysUserAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.system.user.UserQuery;
import com.rongji.rjsoft.service.ISysUserService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.system.user.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-04-26
 */
@Api(tags = "系统管理-用户管理")
@RestController
@RequestMapping("/sysUser")
@AllArgsConstructor
public class SysUserController {

    private final ISysUserService sysUserService;

    /**
     * 查询用户列表
     * @param userQuery 查询条件
     * @return 分页数据
     */
    @PreAuthorize("@permissionIdentify.hasAnyPermi('system:user:list')")
    @ApiOperation(value = "查询用户列表")
    @GetMapping(value = "list")
    public Object list(UserQuery userQuery){
        CommonPage<SysUserVo> page = sysUserService.listOfUser(userQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);
    }

    /**
     * 通过用户id查询用户信息
     * @param userId 用户id
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasAnyPermi('system:user:query')")
    @ApiImplicitParam(name = "id", value = "用户id", required = true)
    @ApiOperation(value = "通过用户id查询用户信息")
    @GetMapping(value = "user/{id}")
    public Object getUserById(@PathVariable("id") Long userId) {
        return ResponseVo.response(ResponseEnum.SUCCESS, sysUserService.getUserInfoById(userId));
    }

    /**
     * 新增用户信息
     * @param user 用户表单信息
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasAnyPermi('system:user:add')")
    @ApiOperation(value = "注册用户信息")
    @PostMapping(value = "user")
    @LogAction(module = "用户管理", method = "注册用户", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object register(@Validated(SysUserAo.add.class) @RequestBody SysUserAo user){
        //检查用户名、手机号、邮箱是否存在 --- 如果不需明确指出哪项重复可以合并为一次查询
        if(sysUserService.checkUserByUserName(user)){
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "用户名已存在!");
        }
        if(StringUtils.isNotEmpty(user.getPhonenumber()) && sysUserService.checkUserByPhone(user)){
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "手机号已存在!");
        }
        if(StringUtils.isNotEmpty(user.getEmail()) && sysUserService.checkUserByEmail(user)){
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "邮箱存在!");
        }
        if(sysUserService.addUser(user) > 0){
            return ResponseVo.success("注册成功");
        }
        return ResponseVo.error("注册失败");
    }

    /**
     * 编辑用户信息
     * @param user 用户表单信息
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasAnyPermi('system:user:update')")
    @ApiOperation(value = "修改用户信息")
    @PutMapping(value = "user")
    @LogAction(module = "用户管理", method = "注册用户", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object edit(@Validated(SysUserAo.update.class) @RequestBody SysUserAo user){
        //手机号、邮箱是否存在 --- 如果不需明确指出哪项重复可以合并为一次查询
        if(StringUtils.isNotEmpty(user.getPhonenumber()) && sysUserService.checkUserByPhone(user)){
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "手机号已存在!");
        }
        if(StringUtils.isNotEmpty(user.getEmail()) && sysUserService.checkUserByEmail(user)){
            return ResponseVo.error(ResponseEnum.USER_ROLE_INFO_MUTI.getCode(), "邮箱存在!");
        }
        if(Constants.ADMIN_ID.longValue() == user.getUserId().longValue()){
            return ResponseVo.error("管理员账户信息不能修改!");
        }
        if(sysUserService.editUser(user) > 0){
            return ResponseVo.success("修改用户信息成功");
        }
        return ResponseVo.error("修改用户信息失败");
    }

    /**
     * 删除用户
     * @param userIds 用户id
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasAnyPermi('system:user:delete')")
    @ApiOperation(value = "删除用户信息")
    @ApiImplicitParam(name = "userIds", value = "用户id", required = true)
    @DeleteMapping(value = "user/{userIds}")
    @LogAction(module = "用户管理", method = "删除用户", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable("userIds") Long[] userIds){
        if(sysUserService.deleteUser(userIds) > 0){
            return ResponseVo.success("删除用户信息成功");
        }
        return ResponseVo.error("删除用户信息失败");
    }

    /**
     * 重置密码
     * @return 返回结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @PostMapping(value = "restPwd/{userId}")
    public Object restPwd(@PathVariable Long userId){
        if(sysUserService.restPwd(userId)){
            return ResponseVo.success("重置用户密码成功");
        }
        return ResponseVo.error("重置用户密码失败");
    }

}
