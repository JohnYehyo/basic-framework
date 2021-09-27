package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.content.CmsColumn;
import com.rongji.rjsoft.query.content.CmsColumnQuery;
import com.rongji.rjsoft.vo.content.CmsColumnVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 栏目信息表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
public interface CmsColumnMapper extends BaseMapper<CmsColumn> {

    /**
     * 查询栏目分页列表
     * @param page 分页对象
     * @param cmsColumnQuery 查询条件
     * @return 栏目分页列表
     */
    IPage<CmsColumnVo> getPage(IPage<CmsColumnVo> page, @Param("param") CmsColumnQuery cmsColumnQuery);

    /**
     * 查询该节点下栏目节点
     * @param columnId 站点id
     * @return 该节点下的所有节点
     */
    List<CmsColumn> selectChildrenByColumnId(Long columnId);

    /**
     * 更新该节点下所有节点的Ancestors
     * @param list 该节点下所有节点
     * @return 更新结果
     */
    int batchUpdateChildreAncestors(List<CmsColumn> list);
}
