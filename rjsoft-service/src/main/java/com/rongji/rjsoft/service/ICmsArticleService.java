package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.ao.content.CmsArticleAo;
import com.rongji.rjsoft.ao.content.CmsArticleAuditAo;
import com.rongji.rjsoft.entity.content.CmsArticle;
import com.rongji.rjsoft.query.content.CmsArticleQuery;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.content.CmsArticleInfoVo;
import com.rongji.rjsoft.vo.content.CmsArticleVo;

/**
 * <p>
 * 文章信息表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-22
 */
public interface ICmsArticleService extends IService<CmsArticle> {

    /**
     * 添加文章
     * @param cmsArticleAo 文章表单信息
     * @return 添加结果
     */
    boolean saveArticle(CmsArticleAo cmsArticleAo);

    /**
     * 编辑文章
     * @param cmsArticleAo 文章表单信息
     * @return 编辑结果
     */
    boolean updateArticle(CmsArticleAo cmsArticleAo);

    /**
     * 删除文章
     * @param articleId 文章ID
     * @return 删除结果
     */
    boolean deleteArticle(Long[] articleId);

    /**
     * 审核文章
     * @param cmsArticleAuditAo 文章状态信息
     * @return 审核结果
     */
    boolean audit(CmsArticleAuditAo cmsArticleAuditAo);

    /**
     * 文章列表
     * @param cmsArticleQuery 查询对象
     * @return 文章列表
     */
    CommonPage<CmsArticleVo> getPage(CmsArticleQuery cmsArticleQuery);

    /**
     * 文章详情
     * @param articleId 文章id
     * @return 文章详情
     */
    CmsArticleInfoVo getInfo(Long articleId);
}
