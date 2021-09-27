package com.rongji.rjsoft.service;

import com.rongji.rjsoft.ao.system.SysDictDataAo;
import com.rongji.rjsoft.entity.system.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.query.system.dict.DictQuery;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.system.dict.DictDataListVo;
import com.rongji.rjsoft.vo.system.dict.SysDictDataVo;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-03
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 通过标签及类型检查字典是否存在
     * @param sysDictDataAo 字典信息
     * @return 是否存在
     */
    boolean checkDataByLabel(SysDictDataAo sysDictDataAo);

    /**
     * 保存字典
     * @param sysDictDataAo 字典信息
     * @return 保存结果
     */
    boolean saveData(SysDictDataAo sysDictDataAo);

    /**
     * 编辑字典
     * @param sysDictDataAo 字典信息
     * @return 编辑结果
     */
    boolean editData(SysDictDataAo sysDictDataAo);

    /**
     * 删除字典
     * @param dictCodes 字典code
     * @return 删除结果
     */
    boolean deleteData(Long[] dictCodes);

    /**
     * 通过字典编码获取字典信息
     * @param dictCode 字典code
     * @return 字典信息
     */
    SysDictDataVo getDataByCode(Long dictCode);

    /**
     * 获取字典分页列表
     * @param dictQuery 查询条件
     * @return 字典分页列表
     */
    CommonPage<SysDictDataVo> pageList(DictQuery dictQuery);

    /**
     * 获取字典列表
     * @return 字典列表
     */
    List<DictDataListVo> listOfDict();
}
