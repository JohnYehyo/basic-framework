package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.system.SysDictData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.query.system.dict.DictQuery;
import com.rongji.rjsoft.vo.system.dict.DictDataVo;
import com.rongji.rjsoft.vo.system.dict.SysDictDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-03
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 获取字典分页列表
     * @param page 分页对象
     * @param dictQuery 查询条件
     * @return 分页列表
     */
    IPage<SysDictDataVo> getPages(IPage<SysDictDataVo> page, @Param("param") DictQuery dictQuery);

    /**
     * 获取字典列表
     * @return 字典列表
     */
    List<DictDataVo> listOfDict();
}
