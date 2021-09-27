package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.ao.content.CmsArticleAo;
import com.rongji.rjsoft.ao.content.CmsArticleAuditAo;
import com.rongji.rjsoft.common.security.util.SecurityUtils;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.entity.content.CmsArticle;
import com.rongji.rjsoft.entity.content.CmsArticleContent;
import com.rongji.rjsoft.entity.content.CmsArticleTags;
import com.rongji.rjsoft.mapper.CmsArticleContentMapper;
import com.rongji.rjsoft.mapper.CmsArticleMapper;
import com.rongji.rjsoft.mapper.CmsArticleTagsMapper;
import com.rongji.rjsoft.query.content.CmsArticleQuery;
import com.rongji.rjsoft.service.ICmsArticleService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.content.CmsArticleInfoVo;
import com.rongji.rjsoft.vo.content.CmsArticleVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章信息表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-22
 */
@Service
@AllArgsConstructor
public class CmsArticleServiceImpl extends ServiceImpl<CmsArticleMapper, CmsArticle> implements ICmsArticleService {

    private CmsArticleMapper cmsArticleMapper;

    private CmsArticleContentMapper cmsArticleContentMapper;

    private CmsArticleTagsMapper cmsArticleTagsMapper;

    /**
     * 添加文章
     *
     * @param cmsArticleAo 文章表单信息
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveArticle(CmsArticleAo cmsArticleAo) {

        //保存文章
        CmsArticle cmsArticle = new CmsArticle();
        boolean result = insertArticle(cmsArticleAo, cmsArticle);
        cmsArticleAo.setArticleId(cmsArticle.getArticleId());

        //保存文章内容
        boolean result1 = saveContent(cmsArticleAo);
        //保存文章标签
        boolean result2 = saveTags(cmsArticleAo);

        return result && result1 && result2;
    }

    private boolean insertArticle(CmsArticleAo cmsArticleAo, CmsArticle cmsArticle) {
        cmsArticleAo.setAuthorId(SecurityUtils.getLoginUser().getUser().getUserId());
        cmsArticleAo.setAuthorName(SecurityUtils.getLoginUser().getUser().getUserName());
        BeanUtil.copyProperties(cmsArticleAo, cmsArticle);
        return cmsArticleMapper.insert(cmsArticle) > 0;
    }

    private boolean saveTags(CmsArticleAo cmsArticleAo) {
        List<CmsArticleTags> list = new ArrayList<>();
        Long[] tagIds = cmsArticleAo.getTagIds();
        CmsArticleTags cmsArticleTags;
        for (int i = 0; i < tagIds.length; i++) {
            cmsArticleTags = new CmsArticleTags();
            cmsArticleTags.setArticleId(cmsArticleAo.getArticleId());
            cmsArticleTags.setTagId(tagIds[i]);
            list.add(cmsArticleTags);
        }
        return cmsArticleTagsMapper.batchInsert(list) > 0;
    }

    private boolean saveContent(CmsArticleAo cmsArticleAo) {
        CmsArticleContent cmsArticleContent = new CmsArticleContent();
        BeanUtil.copyProperties(cmsArticleAo, cmsArticleContent);
        return cmsArticleContentMapper.insert(cmsArticleContent) > 0;
    }

    /**
     * 编辑文章
     *
     * @param cmsArticleAo 文章表单信息
     * @return 编辑结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateArticle(CmsArticleAo cmsArticleAo) {
        //编辑文章
        boolean result = editArticle(cmsArticleAo);
        //编辑文章标签
        LambdaUpdateWrapper<CmsArticleTags> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CmsArticleTags::getArticleId, cmsArticleAo.getArticleId());
        cmsArticleTagsMapper.delete(wrapper);
        saveTags(cmsArticleAo);
        //编辑文章内容
        return updateContent(cmsArticleAo);
    }

    private boolean editArticle(CmsArticleAo cmsArticleAo) {
        cmsArticleAo.setAuthorId(SecurityUtils.getLoginUser().getUser().getUserId());
        cmsArticleAo.setAuthorName(SecurityUtils.getLoginUser().getUser().getUserName());
        CmsArticle cmsArticle = new CmsArticle();
        BeanUtil.copyProperties(cmsArticleAo, cmsArticle);
        return cmsArticleMapper.updateById(cmsArticle) > 0;
    }

    private boolean updateContent(CmsArticleAo cmsArticleAo) {
        CmsArticleContent cmsArticleContent = new CmsArticleContent();
        BeanUtil.copyProperties(cmsArticleAo, cmsArticleContent);
        LambdaUpdateWrapper<CmsArticleContent> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CmsArticleContent::getArticleId, cmsArticleContent.getArticleId());
        return cmsArticleContentMapper.update(cmsArticleContent, wrapper) > 0;
    }

    /**
     * 删除文章
     *
     * @param articleId 文章ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteArticle(Long[] articleId) {
        return cmsArticleMapper.deleteBatchIds(Arrays.asList(articleId)) > 0;
    }

    /**
     * 审核文章
     *
     * @param cmsArticleAuditAo 文章状态信息
     * @return 审核结果
     */
    @Override
    public boolean audit(CmsArticleAuditAo cmsArticleAuditAo) {
        return cmsArticleMapper.audit(cmsArticleAuditAo);
    }

    /**
     * 文章列表
     * @param cmsArticleQuery 查询对象
     * @return 文章列表
     */
    @Override
    public CommonPage<CmsArticleVo> getPage(CmsArticleQuery cmsArticleQuery) {
        IPage<CmsArticleVo> page = new Page<>();
        page = cmsArticleMapper.getPage(page, cmsArticleQuery);
        List<CmsArticleVo> records = page.getRecords();
        LambdaQueryWrapper<CmsArticleTags> wrapper;
        for (CmsArticleVo cmsArticleVo: records) {
            cmsArticleVo.setTags(cmsArticleTagsMapper.getTagsByArticleId(cmsArticleVo.getArticleId()));
        }
        return CommonPageUtils.assemblyPage(page);
    }

    /**
     * 文章详情
     * @param articleId 文章id
     * @return 文章详情
     */
    @Override
    public CmsArticleInfoVo getInfo(Long articleId) {
        CmsArticleInfoVo cmsArticleInfoVo = cmsArticleMapper.getInfo(articleId);
        cmsArticleInfoVo.setTags(cmsArticleTagsMapper.getTagsByArticleId(cmsArticleInfoVo.getArticleId()));
        return cmsArticleInfoVo;
    }
}
