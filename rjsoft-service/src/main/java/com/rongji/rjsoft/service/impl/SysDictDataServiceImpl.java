package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rongji.rjsoft.ao.system.SysDictDataAo;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.entity.system.SysDictData;
import com.rongji.rjsoft.mapper.SysDictDataMapper;
import com.rongji.rjsoft.query.system.dict.DictQuery;
import com.rongji.rjsoft.service.ISysDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.system.dict.DictDataListVo;
import com.rongji.rjsoft.vo.system.dict.DictDataVo;
import com.rongji.rjsoft.vo.system.dict.SysDictDataVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-03
 */
@Service
@AllArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    private final SysDictDataMapper sysDictDataMapper;


    /**
     * 通过标签及类型检查字典是否存在
     *
     * @param sysDictDataAo 字典信息
     * @return 是否存在
     */
    @Override
    public boolean checkDataByLabel(SysDictDataAo sysDictDataAo) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictData::getDictLabel, sysDictDataAo.getDictLabel())
                .eq(SysDictData::getDictType, sysDictDataAo.getDictType())
                .last(" limit 1");
        SysDictData sysDictData = sysDictDataMapper.selectOne(wrapper);
        if (null != sysDictData &&
                sysDictDataAo.getDictCode().longValue() != sysDictData.getDictCode().longValue()) {
            return true;
        }
        return false;
    }

    /**
     * 保存字典
     *
     * @param sysDictDataAo 字典信息
     * @return 保存结果
     */
    @Override
    public boolean saveData(SysDictDataAo sysDictDataAo) {
        SysDictData sysDictData = new SysDictData();
        BeanUtil.copyProperties(sysDictDataAo, sysDictData);
        return sysDictDataMapper.insert(sysDictData) > 0;
    }

    /**
     * 编辑字典
     *
     * @param sysDictDataAo 字典信息
     * @return 编辑结果
     */
    @Override
    public boolean editData(SysDictDataAo sysDictDataAo) {
        SysDictData sysDictData = new SysDictData();
        BeanUtil.copyProperties(sysDictDataAo, sysDictData);
        return sysDictDataMapper.updateById(sysDictData) > 0;
    }

    /**
     * 删除字典
     *
     * @param dictCodes 字典id
     * @return 删除结果
     */
    @Override
    public boolean deleteData(Long[] dictCodes) {
        return sysDictDataMapper.deleteBatchIds(Arrays.asList(dictCodes)) > 0;
    }

    /**
     * 通过字典编码获取字典信息
     *
     * @param dictCode 字典code
     * @return 字典信息
     */
    @Override
    public SysDictDataVo getDataByCode(Long dictCode) {
        SysDictData sysDictData = sysDictDataMapper.selectById(dictCode);
        SysDictDataVo sysDictDataVo = new SysDictDataVo();
        BeanUtil.copyProperties(sysDictData, sysDictDataVo);
        return sysDictDataVo;
    }

    /**
     * 获取字典分页列表
     *
     * @param dictQuery 查询条件
     * @return 字典分页列表
     */
    @Override
    public CommonPage<SysDictDataVo> pageList(DictQuery dictQuery) {
        if (dictQuery.getCurrent() == null) {
            dictQuery.setCurrent(1);
        }
        if (dictQuery.getPageSize() == null) {
            dictQuery.setPageSize(10);
        }
        IPage<SysDictDataVo> page = new Page<>(dictQuery.getCurrent(), dictQuery.getPageSize());
        page = sysDictDataMapper.getPages(page, dictQuery);
        return CommonPageUtils.assemblyPage(page);
    }

    /**
     * 获取字典列表
     *
     * @return 字典列表
     */
    @Override
    public List<DictDataListVo> listOfDict() {
        List<DictDataVo> list = sysDictDataMapper.listOfDict();
        Map<String, List<DictDataVo>> collect = list.stream().collect(Collectors.groupingBy(DictDataVo::getDictType));
        List<DictDataListVo> data = new ArrayList<>();
        collect.forEach((k,v)->{
            DictDataListVo dictDataListVo = new DictDataListVo();
            dictDataListVo.setDictType(k);
            dictDataListVo.setList(v);
            data.add(dictDataListVo);
        });
        return data;
    }
}
