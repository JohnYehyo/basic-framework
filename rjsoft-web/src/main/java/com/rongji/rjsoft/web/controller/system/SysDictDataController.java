package com.rongji.rjsoft.web.controller.system;


import com.rongji.rjsoft.ao.system.SysDictDataAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.query.system.dict.DictQuery;
import com.rongji.rjsoft.service.ISysDictDataService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.system.dict.SysDictDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-03
 */
@Api(tags = "系统管理-字典数据管理")
@RestController
@RequestMapping("/sysDictData")
@AllArgsConstructor
public class SysDictDataController {

    private final ISysDictDataService sysDictDataService;


    /**
     * 添加字典
     *
     * @param sysDictDataAo 字典信息
     * @return 添加结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "添加字典")
    @PostMapping(value = "dict")
    @LogAction(module = "字典管理", method = "添加字典", logType = LogTypeEnum.INSERT, operatorType = OperatorTypeEnum.WEB)
    public Object add(@Validated(SysDictDataAo.insert.class) @RequestBody SysDictDataAo sysDictDataAo) {
        //检查是否存在同类型同标签的字典
        if (sysDictDataService.checkDataByLabel(sysDictDataAo)) {
            return ResponseVo.error("该字典已存在!");
        }
        if (sysDictDataService.saveData(sysDictDataAo)) {
            return ResponseVo.success("添加字典成功!");
        }
        return ResponseVo.error("添加字典失败");
    }

    /**
     * 编辑字典
     *
     * @param sysDictDataAo 字典信息
     * @return 编辑结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "编辑字典")
    @PutMapping(value = "dict")
    @LogAction(module = "字典管理", method = "编辑字典", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object update(@Validated(SysDictDataAo.update.class) @RequestBody SysDictDataAo sysDictDataAo) {
        //检查是否存在同类型同标签的字典
        if (sysDictDataService.checkDataByLabel(sysDictDataAo)) {
            return ResponseVo.error("该字典已存在!");
        }
        if (sysDictDataService.editData(sysDictDataAo)) {
            return ResponseVo.success("编辑字典成功!");
        }
        return ResponseVo.error("编辑字典失败");
    }

    /**
     * 删除字典
     *
     * @param dictCodes 字典编码
     * @return 删除结果
     */
    @PreAuthorize("@permissionIdentify.hasRole('admin')")
    @ApiOperation(value = "删除字典")
    @ApiImplicitParam(value = "字典编码", name = "dictCodes", required = true)
    @DeleteMapping(value = "dict/{dictCodes}")
    @LogAction(module = "字典管理", method = "删除字典", logType = LogTypeEnum.DELETE, operatorType = OperatorTypeEnum.WEB)
    public Object delete(@PathVariable("dictCodes") Long[] dictCodes) {
        if (sysDictDataService.deleteData(dictCodes)) {
            return ResponseVo.success();
        }
        return ResponseVo.error();
    }

    /**
     * 通过字典编码获取字典信息
     *
     * @param dictCode 字典编码
     * @return 删除结果
     */
    @ApiOperation(value = "通过字典编码获取字典信息")
    @ApiImplicitParam(value = "字典编码", name = "dictCode", required = true)
    @GetMapping(value = "dict/{dictCode}")
    public Object dictData(@PathVariable("dictCode") Long dictCode) {
        return ResponseVo.response(ResponseEnum.SUCCESS, sysDictDataService.getDataByCode(dictCode));
    }

    /**
     * 获取字典分页列表
     *
     * @param dictQuery 查询条件
     * @return 删除结果
     */
    @ApiOperation(value = "获取字典分页列表")
    @GetMapping(value = "page")
    public Object pageList(DictQuery dictQuery) {
        CommonPage<SysDictDataVo> page = sysDictDataService.pageList(dictQuery);
        return ResponseVo.response(ResponseEnum.SUCCESS, page);
    }

    /**
     * 获取字典列表
     * @return
     */
    @ApiOperation(value = "获取字典列表")
    @GetMapping(value = "list")
    public Object list(){
        return ResponseVo.response(ResponseEnum.SUCCESS, sysDictDataService.listOfDict());
    }

}
