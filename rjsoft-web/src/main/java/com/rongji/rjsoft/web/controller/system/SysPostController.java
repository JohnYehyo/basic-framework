package com.rongji.rjsoft.web.controller.system;


import com.rongji.rjsoft.ao.system.SysPostAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.system.post.PostQuery;
import com.rongji.rjsoft.query.system.post.PostSelectQuery;
import com.rongji.rjsoft.service.ISysPostService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.system.post.SysPostSelectVo;
import com.rongji.rjsoft.vo.system.post.SysPostVo;
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
 * 岗位信息表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
@Api(tags = "系统管理-岗位管理")
@RestController
@RequestMapping("/sysPost")
@AllArgsConstructor
public class SysPostController {

    private final ISysPostService sysPostService;


    /**
     * 添加岗位
     * @param sysPostAo 岗位信息
     * @return 添加结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:post:add')")
    @ApiOperation(value = "添加岗位")
    @PostMapping(value = "post")
    @LogAction(module = "岗位管理", method = "添加岗位", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(SysPostAo.insert.class) @RequestBody SysPostAo sysPostAo){
        //检查岗位名称
        if(sysPostService.checkPostByName(sysPostAo)){
            return ResponseVo.error(ResponseEnum.FAIL.getCode(), "岗位已存在");
        }
        return sysPostService.savePost(sysPostAo);
    }

    /**
     * 编辑岗位
     * @param sysPostAo 岗位信息
     * @return 添加结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:post:update')")
    @ApiOperation(value = "编辑岗位")
    @PutMapping(value = "post")
    @LogAction(module = "岗位管理", method = "编辑岗位", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object update(@Validated(SysPostAo.update.class) @RequestBody SysPostAo sysPostAo){
        //检查岗位名称
        if(sysPostService.checkPostByName(sysPostAo)){
            return ResponseVo.error(ResponseEnum.FAIL.getCode(), "岗位已存在");
        }
        return sysPostService.updatePost(sysPostAo);
    }

    /**
     * 删除岗位
     * @param postIds 岗位id
     * @return 删除结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:post:delete')")
    @ApiOperation(value = "删除岗位")
    @ApiImplicitParam(value = "岗位id", name = "postIds", required = true)
    @DeleteMapping(value = "post/{postIds}")
    @LogAction(module = "岗位管理", method = "删除岗位", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable("postIds") Long[] postIds){
        return ResponseVo.response(ResponseEnum.SUCCESS, sysPostService.deletePosts(postIds));
    }

    /**
     * 岗位分页列表
     * @param postQuery 查询条件
     * @return 查询结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:post:page')")
    @ApiOperation(value = "岗位分页列表")
    @GetMapping(value = "page")
    public Object page(PostQuery postQuery){
        CommonPage<SysPostVo> commonPage = sysPostService.pagesOfPost(postQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, commonPage);
    }

    /**
     * 岗位列表
     * @param postSelectQuery 查询条件
     * @return 查询结果
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:post:list')")
    @ApiOperation(value = "岗位列表")
    @GetMapping(value = "list")
    public Object list(PostSelectQuery postSelectQuery){
        List<SysPostSelectVo> list = sysPostService.listOfPost(postSelectQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, list);
    }

    /**
     * 通过岗位id查询岗位信息
     * @param postId 岗位id
     * @return 岗位信息
     */
    @PreAuthorize("@permissionIdentify.hasPermi('system:post:query')")
    @ApiOperation(value = "通过岗位id查询岗位信息")
    @ApiImplicitParam(value = "岗位id", name = "postId", required = true)
    @GetMapping(value = "post/{postId}")
    public Object postById(@PathVariable("postId") Long postId){
        return ResponseVo.response(ResponseEnum.SUCCESS, sysPostService.getPostById(postId));
    }


}
