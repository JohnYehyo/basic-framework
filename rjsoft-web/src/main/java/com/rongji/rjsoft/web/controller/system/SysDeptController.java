package com.rongji.rjsoft.web.controller.system;


import com.rongji.rjsoft.ao.system.SysDeptAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.system.dept.DeptQuey;
import com.rongji.rjsoft.service.ISysDeptService;
import com.rongji.rjsoft.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-04-26
 */
@Api(tags = "系统管理-部门管理")
@RestController
@RequestMapping("/sysDept")
@AllArgsConstructor
public class SysDeptController {

    private final ISysDeptService sysDeptService;


    /**
     * 新增部门信息
     * @param sysDeptAo 部门表单信息
     * @return 新增结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:dept:add')")
    @ApiOperation(value = "新增部门信息")
    @PostMapping(value = "dept")
    @LogAction(module = "部门管理", method = "新增部门", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(SysDeptAo.insert.class) @RequestBody SysDeptAo sysDeptAo){
        //检查部门名称是否存在
        if(sysDeptService.checkDeptByName(sysDeptAo)){
            return ResponseVo.error("部门已存在");
        }
        if(sysDeptService.saveDept(sysDeptAo)){
            return ResponseVo.success("新增部门信息成功");
        }
        return ResponseVo.error("新增部门信息失败");
    }

    /**
     * 编辑部门信息
     * @param sysDeptAo 部门表单信息
     * @return 新增结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:dept:update')")
    @ApiOperation(value = "编辑部门信息")
    @PutMapping(value = "dept")
    @LogAction(module = "部门管理", method = "编辑部门", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object update(@Validated(SysDeptAo.update.class) @RequestBody SysDeptAo sysDeptAo){
        //检查部门名称是否存在
        if(sysDeptService.checkDeptByName(sysDeptAo)){
            return ResponseVo.error("部门已存在");
        }
        if(sysDeptService.updateDept(sysDeptAo)){
            return ResponseVo.success("编辑部门信息成功");
        }
        return ResponseVo.error("编辑部门信息失败");
    }

    /**
     * 删除部门信息
     * @param deptIds 部门id
     * @return 删除结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:dept:delete')")
    @ApiOperation(value = "删除部门信息")
    @ApiImplicitParam(value = "部门id", name = "deptIds", required = true)
    @DeleteMapping(value = "dept/{deptIds}")
    @LogAction(module = "部门管理", method = "删除部门", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable("deptIds") Long[] deptIds){
        if(sysDeptService.deleteDept(deptIds)){
            return ResponseVo.success("删除部门信息成功");
        }
        return ResponseVo.error("删除部门信息失败");
    }

    /**
     * 通过id查询部门信息
     * @param deptId 部门id
     * @return 部门信息
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:dept:query')")
    @ApiOperation(value = "通过id查询部门信息")
    @ApiImplicitParam(value = "部门id", name = "deptId", required = true)
    @GetMapping(value = "dept/{deptId}")
    public Object dept(@PathVariable("deptId") Long deptId){
        return ResponseVo.response(ResponseEnum.SUCCESS, sysDeptService.getDeptById(deptId));
    }

    /**
     * 部门列表
     * @param deptQuey 查询条件
     * @return 部门信息
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:dept:list')")
    @ApiOperation(value = "部门列表")
    @GetMapping(value = "list")
    public Object list(DeptQuey deptQuey){
        return ResponseVo.response(ResponseEnum.SUCCESS, sysDeptService.listByCondition(deptQuey));
    }

    /**
     * 部门下拉列表
     * @return 部门信息
     */
    @ApiOperation(value = "部门下拉列表")
    @ApiImplicitParam(name = "deptId", value = "机构id")
    @GetMapping(value = "asynchTree")
    public Object treelist(Long deptId){
        return ResponseVo.response(ResponseEnum.SUCCESS, sysDeptService.listByUser(deptId));
    }

    /**
     * 按角色查询部门下拉列表
     * @return 部门信息
     */
    @ApiOperation(value = "按角色查询部门下拉列表")
    @ApiImplicitParam(value = "角色id", name = "roleId", required = true)
    @GetMapping(value = "treelist/{roleId}")
    public Object treelistByRole(@PathVariable("roleId") Long roleId){
        return ResponseVo.response(ResponseEnum.SUCCESS, sysDeptService.listByRoleId(roleId));
    }

}
