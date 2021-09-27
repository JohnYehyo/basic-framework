package com.rongji.rjsoft.web.controller.content;


import com.rongji.rjsoft.ao.content.CmsColumnAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.content.CmsColumnQuery;
import com.rongji.rjsoft.service.ICmsColumnService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.content.CmsColumnVo;
import com.rongji.rjsoft.vo.content.CmsSiteVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 栏目信息表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
@Api(tags = "CMS-栏目信息管理")
@RestController
@RequestMapping("/cmsColumn")
@AllArgsConstructor
public class CmsColumnController {

    private final ICmsColumnService cmsColumnService;

    /**
     * 新增栏目信息
     * @param cmsColumnAo 栏目信息
     * @return 新增结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "新增栏目信息")
    @PostMapping(value = "column")
    @LogAction(module = "栏目信息管理", method = "新增栏目信息", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(CmsColumnAo.insert.class) @RequestBody CmsColumnAo cmsColumnAo){
        if(cmsColumnService.add(cmsColumnAo)){
            return ResponseVo.success("保存栏目信息成功");
        }
        return ResponseVo.error("保存栏目信息失败");
    }

    /**
     * 编辑栏目信息
     * @param cmsColumnAo 栏目信息
     * @return 编辑结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "编辑栏目信息")
    @PutMapping(value = "column")
    @LogAction(module = "栏目信息管理", method = "编辑栏目信息", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object edit(@Validated(CmsColumnAo.update.class) @RequestBody CmsColumnAo cmsColumnAo){
        if(cmsColumnService.edit(cmsColumnAo)){
            return ResponseVo.success("更新栏目信息成功");
        }
        return ResponseVo.error("更新栏目信息失败");
    }

    /**
     * 删除栏目信息
     * @param columnId 栏目id
     * @return 删除结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "删除栏目信息")
    @ApiImplicitParam(name = "columnId", value = "栏目id", required = true)
    @DeleteMapping(value = "column/{columnId}")
    @LogAction(module = "栏目信息管理", method = "删除栏目信息", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable Long[] columnId){
        if(cmsColumnService.delete(columnId)){
            return ResponseVo.success("删除成功");
        }
        return ResponseVo.error("删除失败");
    }

    /**
     * 栏目信息分页列表
     * @param cmsColumnQuery 查询条件
     * @return 栏目信息分页列表
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "栏目信息分页列表")
    @GetMapping(value = "page")
    public Object page(CmsColumnQuery cmsColumnQuery){
        CommonPage<CmsColumnVo> commonPage = cmsColumnService.pageList(cmsColumnQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, commonPage);
    }

    /**
     * 栏目树
     * @param cmsColumnQuery 查询条件
     * @return 栏目树
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "栏目树")
    @GetMapping(value = "tree")
    public Object tree(CmsColumnQuery cmsColumnQuery){
        return ResponseVo.response(ResponseEnum.SUCCESS, cmsColumnService.tree(cmsColumnQuery));
    }

}
