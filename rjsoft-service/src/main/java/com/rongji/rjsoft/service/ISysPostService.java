package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.ao.system.SysPostAo;
import com.rongji.rjsoft.entity.system.SysPost;
import com.rongji.rjsoft.query.system.post.PostSelectQuery;
import com.rongji.rjsoft.query.system.post.PostQuery;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.system.post.SysPostSelectVo;
import com.rongji.rjsoft.vo.system.post.SysPostVo;

import java.util.List;

/**
 * <p>
 * 岗位信息表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
public interface ISysPostService extends IService<SysPost> {

    /**
     * 检查岗位是否存在
     * @param sysPostAo 岗位信息
     * @return 检查结果
     */
    boolean checkPostByName(SysPostAo sysPostAo);

    /**
     * 添加岗位
     * @param sysPostAo 岗位信息
     * @return 添加结果
     */
    boolean savePost(SysPostAo sysPostAo);

    /**
     * 编辑岗位
     * @param sysPostAo 岗位信息
     * @return 添加结果
     */
    boolean updatePost(SysPostAo sysPostAo);

    /**
     * 删除岗位
     * @param postIds 岗位信息
     * @return 删除结果
     */
    boolean deletePosts(Long[] postIds);

    /**
     * 岗位分页列表
     * @param postQuery 查询条件
     * @return 分页结果
     */
    CommonPage<SysPostVo> pagesOfPost(PostQuery postQuery);

    /**
     * 通过岗位id查询岗位信息
     * @param postId 岗位id
     * @return 岗位信息
     */
    Object getPostById(Long postId);

    /**
     * 岗位列表
     * @param postSelectQuery 查询条件
     * @return 岗位列表
     */
    List<SysPostSelectVo> listOfPost(PostSelectQuery postSelectQuery);
}
