package com.rongji.rjsoft.web.controller.content;


import com.rongji.rjsoft.ao.content.CmsTagsAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.content.CmsTagsQuery;
import com.rongji.rjsoft.query.content.CmsTagsSimpleQuery;
import com.rongji.rjsoft.service.ICmsTagsService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.content.CmsTagsSimpleVo;
import com.rongji.rjsoft.vo.content.CmsTagsVo;
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
 * 标签信息表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-18
 */
@Api(tags = "CMS-标签信息管理")
@RestController
@RequestMapping("/cmsTags")
@AllArgsConstructor
public class CmsTagsController {

    private final ICmsTagsService cmsTagsService;

    /**
     * 添加标签信息
     * @param cmsTagsAo 标签表单
     * @return 添加结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "添加标签信息")
    @PostMapping(value = "tag")
    @LogAction(module = "标签信息管理", method = "添加标签信息", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(CmsTagsAo.insert.class) @RequestBody CmsTagsAo cmsTagsAo){
        return cmsTagsService.saveTag(cmsTagsAo);
    }

    /**
     * 更新标签信息
     * @param cmsTagsAo 标签表单
     * @return 更新结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "编辑标签信息")
    @PutMapping(value = "tag")
    @LogAction(module = "标签信息管理", method = "编辑标签信息", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object update(@Validated(CmsTagsAo.update.class) @RequestBody CmsTagsAo cmsTagsAo){
        return cmsTagsService.updateTag(cmsTagsAo);
    }

    /**
     * 添加标签信息
     * @param tag_id 标签表单
     * @return 添加结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "删除标签信息")
    @ApiImplicitParam(name = "tag_id", value = "标签id", required = true)
    @DeleteMapping(value = "tag/{tag_id}")
    @LogAction(module = "标签信息管理", method = "删除标签信息", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable Long[] tag_id){
        return cmsTagsService.deleteTags(tag_id);
    }

    /**
     * 标签信息分页
     * @param cmsTagsQuery 查询对象
     * @return 标签信息分页
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "标签信息分页")
    @GetMapping(value = "page")
    public Object page(CmsTagsQuery cmsTagsQuery){
        CommonPage<CmsTagsVo> commonPage = cmsTagsService.getPage(cmsTagsQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, commonPage);
    }

    /**
     * 标签信息列表
     * @param cmsTagsSimpleQuery 查询对象
     * @return 标签信息列表
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "标签信息列表")
    @GetMapping(value = "list")
    public Object list(CmsTagsSimpleQuery cmsTagsSimpleQuery){
        return cmsTagsService.getList(cmsTagsSimpleQuery);
    }

}
