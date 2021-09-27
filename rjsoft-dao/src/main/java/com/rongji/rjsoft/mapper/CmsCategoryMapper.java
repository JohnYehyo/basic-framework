package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.content.CmsCategory;
import com.rongji.rjsoft.entity.content.CmsColumn;
import com.rongji.rjsoft.query.content.CmsCategoryQuery;
import com.rongji.rjsoft.vo.content.CmsCategoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章类别信息表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
public interface CmsCategoryMapper extends BaseMapper<CmsCategory> {

    /**
     * 禁用文章类别
     * @param categoryIds 文章类别id数组
     * @return 禁用结果
     */
    boolean updateStatus(Long[] categoryIds);

    /**
     * 文章分类分页列表
     * @param page 分页对象
     * @param cmsCategoryQuery 查询对象
     * @return 文章分类分页列表
     */
    IPage<CmsCategoryVo> getPages(IPage<CmsCategoryVo> page, @Param("param") CmsCategoryQuery cmsCategoryQuery);

    /**
     *
     * @param categoryId
     * @return
     */
    List<CmsCategory> selectChildrenByCategoryId(Long categoryId);

    /**
     *
     * @param list
     * @return
     */
    int batchUpdateChildreAncestors(List<CmsCategory> list);
}



