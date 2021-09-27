package com.rongji.rjsoft.web.controller.content;


import com.rongji.rjsoft.ao.content.CmsSiteAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.content.CmsSiteQuery;
import com.rongji.rjsoft.service.ICmsSiteService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.content.CmsSiteTreeVo;
import com.rongji.rjsoft.vo.content.CmsSiteVo;
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
 * 站点信息表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
@Api(tags = "CMS-站点信息管理")
@RestController
@RequestMapping("/cmsSite")
@AllArgsConstructor
public class CmsSiteController {

    private final ICmsSiteService cmsSiteService;

    /**
     * 新增站点信息
     * @param cmsSiteAo 站点信息
     * @return 新增结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "新增站点信息")
    @PostMapping(value = "site")
    @LogAction(module = "站点信息管理", method = "新增站点信息", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(CmsSiteAo.insert.class) @RequestBody CmsSiteAo cmsSiteAo){
        if(cmsSiteService.add(cmsSiteAo)){
            return ResponseVo.success("保存站点信息成功");
        }
        return ResponseVo.error("保存站点信息失败");
    }

    /**
     * 编辑站点信息
     * @param cmsSiteAo 站点信息
     * @return 编辑结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "编辑站点信息")
    @PutMapping(value = "site")
    @LogAction(module = "站点信息管理", method = "编辑站点信息", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object edit(@Validated(CmsSiteAo.update.class) @RequestBody CmsSiteAo cmsSiteAo){
        if(cmsSiteService.edit(cmsSiteAo)){
            return ResponseVo.success("更新站点信息成功");
        }
        return ResponseVo.error("更新站点信息失败");
    }

    /**
     * 删除站点信息
     * @param siteId 站点id
     * @return 删除结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "删除站点信息")
    @ApiImplicitParam(name = "siteId", value = "站点id", required = true)
    @DeleteMapping(value = "site/{siteId}")
    @LogAction(module = "站点信息管理", method = "删除站点信息", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable Long[] siteId){
        if(cmsSiteService.delete(siteId)){
            return ResponseVo.success("删除成功");
        }
        return ResponseVo.error("删除失败");
    }

    /**
     * 站点信息分页列表
     * @param cmsSiteQuery 查询条件
     * @return 站点信息分页列表
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "站点信息分页列表")
    @GetMapping(value = "page")
    public Object page(CmsSiteQuery cmsSiteQuery){
        CommonPage<CmsSiteVo> commonPage = cmsSiteService.pageList(cmsSiteQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, commonPage);
    }

    /**
     * 站点树
     * @param cmsSiteQuery 查询条件
     * @return 站点树
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "站点树")
    @GetMapping(value = "tree")
    public Object tree(CmsSiteQuery cmsSiteQuery){
        return ResponseVo.response(ResponseEnum.SUCCESS, cmsSiteService.tree(cmsSiteQuery));
    }

}
