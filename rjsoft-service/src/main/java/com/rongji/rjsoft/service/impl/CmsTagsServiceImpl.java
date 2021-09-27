package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rongji.rjsoft.ao.content.CmsTagsAo;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.entity.content.CmsTags;
import com.rongji.rjsoft.mapper.CmsTagsMapper;
import com.rongji.rjsoft.query.content.CmsTagsQuery;
import com.rongji.rjsoft.query.content.CmsTagsSimpleQuery;
import com.rongji.rjsoft.service.ICmsTagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.ResponseVo;
import com.rongji.rjsoft.vo.content.CmsTagsVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 * 标签信息表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-18
 */
@Service
@AllArgsConstructor
public class CmsTagsServiceImpl extends ServiceImpl<CmsTagsMapper, CmsTags> implements ICmsTagsService {

    private CmsTagsMapper cmsTagsMapper;

    /**
     * 新增标签信息
     * @param cmsTagsAo 标签表单
     * @return 新增结果
     */
    @Override
    public Object saveTag(CmsTagsAo cmsTagsAo) {
        //检查标签唯一
        boolean result = checkUnique(cmsTagsAo);
        if(result){
            return ResponseVo.error("系统中已有此标签!");
        }
        CmsTags cmsTags = new CmsTags();
        BeanUtil.copyProperties(cmsTagsAo, cmsTags);
        if(cmsTagsMapper.insert(cmsTags) > 0){
            return ResponseVo.success("新增标签成功");
        }
        return ResponseVo.error("新增标签失败");
    }

    private boolean checkUnique(CmsTagsAo cmsTagsAo) {
        LambdaQueryWrapper<CmsTags> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CmsTags::getTagName, cmsTagsAo.getTagName()).last(" limit 0,1");
        CmsTags cmsTags = cmsTagsMapper.selectOne(wrapper);
        return null != cmsTags && !cmsTags.getTagId().equals(cmsTagsAo.getTagId());
    }

    /**
     * 更新标签信息
     * @param cmsTagsAo 标签表单
     * @return 更新结果
     */
    @Override
    public Object updateTag(CmsTagsAo cmsTagsAo) {
        //检查标签唯一
        boolean result = checkUnique(cmsTagsAo);
        if(result){
            return ResponseVo.error("系统中已有此标签!");
        }
        CmsTags cmsTags = new CmsTags();
        BeanUtil.copyProperties(cmsTagsAo, cmsTags);
        if(cmsTagsMapper.updateById(cmsTags) > 0){
            return ResponseVo.success("更新标签成功");
        }
        return ResponseVo.error("更新标签失败");
    }

    /**
     * 删除标签信息
     * @param tag_id 标签id数组
     * @return 删除结果
     */
    @Override
    public Object deleteTags(Long[] tag_id) {
        if(cmsTagsMapper.deleteBatchIds(Arrays.asList(tag_id)) > 0){
            return ResponseVo.success("删除标签成功");
        }
        return ResponseVo.error("删除标签失败");
    }

    /**
     * 标签信息分页
     * @param cmsTagsQuery 查询对象
     * @return 标签信息分页
     */
    @Override
    public CommonPage<CmsTagsVo> getPage(CmsTagsQuery cmsTagsQuery) {
        IPage<CmsTagsVo> page = new Page<>();
        page = cmsTagsMapper.getPage(page, cmsTagsQuery);
        return CommonPageUtils.assemblyPage(page);
    }

    /**
     * 标签信息列表
     * @param cmsTagsSimpleQuery 查询对象
     * @return 标签信息列表
     */
    @Override
    public Object getList(CmsTagsSimpleQuery cmsTagsSimpleQuery) {
        return cmsTagsMapper.getList(cmsTagsSimpleQuery);
    }
}
