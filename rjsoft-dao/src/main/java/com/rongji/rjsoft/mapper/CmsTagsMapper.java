package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.content.CmsTags;
import com.rongji.rjsoft.query.content.CmsTagsQuery;
import com.rongji.rjsoft.query.content.CmsTagsSimpleQuery;
import com.rongji.rjsoft.vo.content.CmsTagsSimpleVo;
import com.rongji.rjsoft.vo.content.CmsTagsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标签信息表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-18
 */
public interface CmsTagsMapper extends BaseMapper<CmsTags> {

    /**
     * 标签信息分页
     * @param page 分页对象
     * @param cmsTagsQuery 查询对象
     * @return 标签信息分页信息
     */
    IPage<CmsTagsVo> getPage(IPage<CmsTagsVo> page, @Param("param") CmsTagsQuery cmsTagsQuery);

    /**
     * 标签信息列表
     * @param cmsTagsSimpleQuery 查询对象
     * @return 标签信息列表
     */
    List<CmsTagsSimpleVo> getList(CmsTagsSimpleQuery cmsTagsSimpleQuery);
}
