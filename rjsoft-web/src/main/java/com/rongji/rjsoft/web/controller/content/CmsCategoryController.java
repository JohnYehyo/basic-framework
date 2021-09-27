package com.rongji.rjsoft.web.controller.content;


import com.rongji.rjsoft.ao.content.CmsCategoryAo;
import com.rongji.rjsoft.ao.content.CmsColumnAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.content.CmsCategoryQuery;
import com.rongji.rjsoft.query.content.CmsColumnQuery;
import com.rongji.rjsoft.service.ICmsCategoryService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.content.CmsCategoryVo;
import com.rongji.rjsoft.vo.content.CmsColumnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 文章类别信息表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
@Api(tags = "CMS-文章类别管理")
@RestController
@RequestMapping("/cmsCategory")
@AllArgsConstructor
public class CmsCategoryController {
    
    
    private ICmsCategoryService cmsCategoryService;

    /**
     * 新增文章类别信息
     * @param cmsCategoryAo 文章类别信息
     * @return 新增结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "新增文章类别信息")
    @PostMapping(value = "category")
    @LogAction(module = "文章类别信息管理", method = "新增文章类别信息", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(CmsCategoryAo.insert.class) @RequestBody CmsCategoryAo cmsCategoryAo){
        if(cmsCategoryService.add(cmsCategoryAo)){
            return ResponseVo.success("保存文章类别信息成功");
        }
        return ResponseVo.error("保存文章类别信息失败");
    }

    /**
     * 编辑文章类别信息
     * @param cmsCategoryAo 文章类别信息
     * @return 编辑结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "编辑文章类别信息")
    @PutMapping(value = "category")
    @LogAction(module = "文章类别信息管理", method = "编辑文章类别信息", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object edit(@Validated(CmsCategoryAo.update.class) @RequestBody CmsCategoryAo cmsCategoryAo){
        if(cmsCategoryService.edit(cmsCategoryAo)){
            return ResponseVo.success("更新文章类别信息成功");
        }
        return ResponseVo.error("更新文章类别信息失败");
    }

    /**
     * 删除文章类别信息
     * @param categoryId 文章类别id
     * @return 删除结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "删除文章类别信息")
    @ApiImplicitParam(name = "categoryId", value = "文章类别id", required = true)
    @DeleteMapping(value = "category/{categoryId}")
    @LogAction(module = "文章类别信息管理", method = "删除文章类别信息", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable Long[] categoryId){
        if(cmsCategoryService.delete(categoryId)){
            return ResponseVo.success("删除成功");
        }
        return ResponseVo.error("删除失败");
    }

    /**
     * 禁用文章类别信息
     * @param categoryId 文章类别id
     * @return 禁用除结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "禁用文章类别信息")
    @ApiImplicitParam(name = "categoryId", value = "文章类别id", required = true)
    @DeleteMapping(value = "disable/{categoryId}")
    @LogAction(module = "文章类别信息管理", method = "删除文章类别信息", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object disable(@PathVariable Long[] categoryId){
        if(cmsCategoryService.disable(categoryId)){
            return ResponseVo.success("禁用成功");
        }
        return ResponseVo.error("禁用失败");
    }

    /**
     * 文章类别信息分页列表
     * @param cmsCategoryQuery 查询条件
     * @return 文章类别信息分页列表
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "文章类别信息分页列表")
    @GetMapping(value = "page")
    public Object page(CmsCategoryQuery cmsCategoryQuery){
        CommonPage<CmsCategoryVo> commonPage = cmsCategoryService.pageList(cmsCategoryQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, commonPage);
    }

    /**
     * 文章类别树
     * @param cmsCategoryQuery 查询条件
     * @return 文章类别树
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "文章类别树")
    @GetMapping(value = "tree")
    public Object tree(CmsCategoryQuery cmsCategoryQuery){
        return ResponseVo.response(ResponseEnum.SUCCESS, cmsCategoryService.tree(cmsCategoryQuery));
    }

}
