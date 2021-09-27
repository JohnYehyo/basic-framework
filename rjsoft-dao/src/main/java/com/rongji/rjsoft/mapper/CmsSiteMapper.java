package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.content.CmsSite;
import com.rongji.rjsoft.query.content.CmsSiteQuery;
import com.rongji.rjsoft.vo.content.CmsSiteBlurryTreeVo;
import com.rongji.rjsoft.vo.content.CmsSiteVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 站点信息表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
public interface CmsSiteMapper extends BaseMapper<CmsSite> {

    /**
     * 站点信息分页列表
     *
     * @param page         分页对象
     * @param cmsSiteQuery 查询条件
     * @return 站点信息分页列表
     */
    IPage<CmsSiteVo> getPage(IPage<CmsSiteVo> page, @Param("param") CmsSiteQuery cmsSiteQuery);

    /**
     * 查询该节点下的所有节点
     * @param siteId 站点id
     * @return 该节点下的所有节点
     */
    List<CmsSite> selectChildrenBySiteId(Long siteId);

    /**
     * 更新该节点下所有节点的Ancestors
     * @param list 该节点下所有节点
     * @return 更新结果
     */
    int batchUpdateChildreAncestors(List<CmsSite> list);
}
