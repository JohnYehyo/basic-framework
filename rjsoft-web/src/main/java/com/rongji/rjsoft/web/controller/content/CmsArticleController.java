package com.rongji.rjsoft.web.controller.content;


import com.rongji.rjsoft.ao.content.CmsArticleAo;
import com.rongji.rjsoft.ao.content.CmsArticleAuditAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.content.CmsArticleQuery;
import com.rongji.rjsoft.service.ICmsArticleService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.content.CmsArticleInfoVo;
import com.rongji.rjsoft.vo.content.CmsArticleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 文章信息表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-22
 */
@Api(tags = "CMS-文章管理")
@RestController
@RequestMapping("/cmsArticle")
@AllArgsConstructor
public class CmsArticleController {

    private final ICmsArticleService cmsArticleService;

    /**
     * 添加文章
     *
     * @param cmsArticleAo 文章表单信息
     * @return 添加结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('cms:article:add')")
    @ApiOperation(value = "添加文章")
    @PostMapping(value = "article")
    @LogAction(module = "文章管理", method = "添加文章", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(CmsArticleAo.insert.class) @RequestBody CmsArticleAo cmsArticleAo) {
        if (cmsArticleService.saveArticle(cmsArticleAo)) {
            return ResponseVo.success("添加文章成功");
        }
        return ResponseVo.error("添加文章失败");
    }

    /**
     * 编辑文章
     *
     * @param cmsArticleAo 文章表单信息
     * @return 添加结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('cms:article:update')")
    @ApiOperation(value = "编辑文章")
    @PutMapping(value = "article")
    @LogAction(module = "文章管理", method = "编辑文章", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object update(@Validated(CmsArticleAo.update.class) @RequestBody CmsArticleAo cmsArticleAo) {
        if (cmsArticleService.updateArticle(cmsArticleAo)) {
            return ResponseVo.success("编辑文章成功");
        }
        return ResponseVo.error("编辑文章失败");
    }

    /**
     * 删除文章
     *
     * @param articleId 文章ID
     * @return 删除结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('cms:article:audit')")
    @ApiOperation(value = "删除文章")
    @ApiImplicitParam(name = "articleId", value = "文章ID", required = true)
    @DeleteMapping(value = "article/{articleId}")
    @LogAction(module = "文章管理", method = "删除文章", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object audit(@PathVariable Long[] articleId) {
        if (cmsArticleService.deleteArticle(articleId)) {
            return ResponseVo.success("删除文章成功");
        }
        return ResponseVo.error("删除文章失败");
    }

    /**
     * 审核文章
     *
     * @param cmsArticleAuditAo 文章状态信息
     * @return 审核结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('cms:article:update')")
    @ApiOperation(value = "编辑文章")
    @PutMapping(value = "audit")
    @LogAction(module = "文章管理", method = "审核文章", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object audit(@Valid @RequestBody CmsArticleAuditAo cmsArticleAuditAo) {
        if (cmsArticleService.audit(cmsArticleAuditAo)) {
            return ResponseVo.success("审核文章成功");
        }
        return ResponseVo.error("审核文章失败");
    }

    /**
     * 文章列表
     *
     * @param cmsArticleQuery 查询对象
     * @return 文章列表
     */
    @PreAuthorize("@permissionIdentify.hasPermi('cms:article:update')")
    @ApiOperation(value = "文章列表")
    @GetMapping(value = "list")
    public Object list(CmsArticleQuery cmsArticleQuery) {
        CommonPage<CmsArticleVo> page = cmsArticleService.getPage(cmsArticleQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);
    }

    /**
     * 文章详情
     *
     * @param articleId 文章id
     * @return 文章详情
     */
    @PreAuthorize("@permissionIdentify.hasPermi('cms:article:query')")
    @ApiOperation(value = "文章详情")
    @ApiImplicitParam(name = "articleId", value = "文章ID", required = true)
    @GetMapping(value = "article/{articleId}")
    public Object info(@PathVariable Long articleId) {
        CmsArticleInfoVo cmsArticleInfoVo = cmsArticleService.getInfo(articleId);
        return ResponseVo.response(ResponseEnum.SUCCESS, cmsArticleInfoVo);
    }

}
