package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.ao.system.SysPostAo;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.entity.system.SysPost;
import com.rongji.rjsoft.mapper.SysPostMapper;
import com.rongji.rjsoft.query.system.post.PostSelectQuery;
import com.rongji.rjsoft.query.system.post.PostQuery;
import com.rongji.rjsoft.service.ISysPostService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.system.post.SysPostSelectVo;
import com.rongji.rjsoft.vo.system.post.SysPostVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
@Service
@AllArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    private final SysPostMapper sysPostMapper;

    /**
     * 检查岗位是否存在
     *
     * @param sysPostAo 岗位信息
     * @return 检查结果
     */
    @Override
    public boolean checkPostByName(SysPostAo sysPostAo) {
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPost::getPostName, sysPostAo.getPostName()).last(" limit 1");
        SysPost sysPost = sysPostMapper.selectOne(wrapper);
        if (null != sysPost && sysPost.getPostId().longValue() != sysPostAo.getPostId()) {
            return true;
        }
        return false;
    }

    /**
     * 添加岗位
     *
     * @param sysPostAo 岗位信息
     * @return 添加结果
     */
    @Override
    public boolean savePost(SysPostAo sysPostAo) {
        SysPost sysPost = new SysPost();
        BeanUtil.copyProperties(sysPostAo, sysPost);
        return sysPostMapper.insert(sysPost) > 0;
    }

    /**
     * 编辑岗位
     *
     * @param sysPostAo 岗位信息
     * @return 添加结果
     */
    @Override
    public boolean updatePost(SysPostAo sysPostAo) {
        SysPost sysPost = new SysPost();
        BeanUtil.copyProperties(sysPostAo, sysPost);
        return sysPostMapper.updateById(sysPost) > 0;
    }

    /**
     * 删除岗位
     *
     * @param postIds 岗位信息
     * @return 删除结果
     */
    @Override
    public boolean deletePosts(Long[] postIds) {
        return sysPostMapper.deleteBatchIds(Arrays.asList(postIds)) > 0;
    }

    /**
     * 岗位列表
     * @param postQuery 查询条件
     * @return 分页结果
     */
    @Override
    public CommonPage<SysPostVo> pagesOfPost(PostQuery postQuery) {
        if (postQuery.getCurrent() == null) {
            postQuery.setCurrent(1);
        }
        if (postQuery.getPageSize() == null) {
            postQuery.setPageSize(10);
        }
        IPage<SysPostVo> page = new Page<>(postQuery.getCurrent(), postQuery.getPageSize());
        page = sysPostMapper.pagesOfPost(page, postQuery);
        return CommonPageUtils.assemblyPage(page);
    }

    /**
     * 岗位下拉列表
     * @param postSelectQuery 查询条件
     * @return 岗位列表
     */
    @Override
    public List<SysPostSelectVo> listOfPost(PostSelectQuery postSelectQuery) {
        return sysPostMapper.listOfPost(postSelectQuery);
    }

    /**
     * 通过岗位id查询岗位信息
     * @param postId 岗位id
     * @return 岗位信息
     */
    @Override
    public Object getPostById(Long postId) {
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPost::getPostId, postId).last(" limit 1");
        return sysPostMapper.selectOne(wrapper);
    }
}
