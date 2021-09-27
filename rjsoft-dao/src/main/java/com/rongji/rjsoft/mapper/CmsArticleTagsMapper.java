package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.entity.content.CmsArticleTags;

import java.util.List;

/**
 * <p>
 * 文章标签关系表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-22
 */
public interface CmsArticleTagsMapper extends BaseMapper<CmsArticleTags> {

    /**
     * 添加文章标签关系
     * @param list 文章标签关系
     * @return 添加结果
     */
    int batchInsert(List<CmsArticleTags> list);

    /**
     * 通过文章id获取标签
     * @param articleId 文章id
     * @return 标签
     */
    List<String> getTagsByArticleId(Long articleId);
}
