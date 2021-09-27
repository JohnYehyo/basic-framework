package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.ao.content.CmsTagsAo;
import com.rongji.rjsoft.entity.content.CmsTags;
import com.rongji.rjsoft.query.content.CmsTagsQuery;
import com.rongji.rjsoft.query.content.CmsTagsSimpleQuery;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.content.CmsTagsVo;

/**
 * <p>
 * 标签信息表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-18
 */
public interface ICmsTagsService extends IService<CmsTags> {

    /**
     * 新增标签信息
     * @param cmsTagsAo 标签表单
     * @return 新增结果
     */
    Object saveTag(CmsTagsAo cmsTagsAo);

    /**
     * 更新标签信息
     * @param cmsTagsAo 标签表单
     * @return 更新结果
     */
    Object updateTag(CmsTagsAo cmsTagsAo);

    /**
     * 删除标签信息
     * @param tag_id 标签id数组
     * @return 删除结果
     */
    Object deleteTags(Long[] tag_id);

    /**
     * 标签信息分页
     * @param cmsTagsQuery 查询对象
     * @return 标签信息分页
     */
    CommonPage<CmsTagsVo> getPage(CmsTagsQuery cmsTagsQuery);

    /**
     * 标签信息列表
     * @param cmsTagsSimpleQuery 查询对象
     * @return 标签信息列表
     */
    Object getList(CmsTagsSimpleQuery cmsTagsSimpleQuery);
}
