package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.ao.content.CmsCategoryAo;
import com.rongji.rjsoft.entity.content.CmsCategory;
import com.rongji.rjsoft.query.content.CmsCategoryQuery;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.content.CmsCategoryTreeVo;
import com.rongji.rjsoft.vo.content.CmsCategoryVo;

import java.util.List;

/**
 * <p>
 * 文章类别信息表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
public interface ICmsCategoryService extends IService<CmsCategory> {

    /**
     * 添加文章类别
     * @param cmsCategoryAo 文章类别表单
     * @return 添加结果
     */
    boolean add(CmsCategoryAo cmsCategoryAo);

    /**
     * 编辑文章类别
     * @param cmsCategoryAo 文章类别表单
     * @return 编辑结果
     */
    boolean edit(CmsCategoryAo cmsCategoryAo);

    /**
     * 删除文章类别
     * @param categoryId 文章类别id
     * @return 删除结果
     */
    boolean delete(Long[] categoryId);

    /**
     * 禁用文章类别
     * @param categoryId 文章类别id
     * @return 禁用结果
     */
    boolean disable(Long[] categoryId);

    /**
     * 文章类别分页列表
     * @param cmsCategoryQuery 查询对象
     * @return 文章类别分页列表
     */
    CommonPage<CmsCategoryVo> pageList(CmsCategoryQuery cmsCategoryQuery);

    /**
     * 文章类别树
     * @param cmsCategoryQuery 查询对象
     * @return 文章类别树
     */
    List<CmsCategoryTreeVo> tree(CmsCategoryQuery cmsCategoryQuery);
}
