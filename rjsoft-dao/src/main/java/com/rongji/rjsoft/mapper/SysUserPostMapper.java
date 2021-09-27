package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.entity.system.SysUserPost;

import java.util.List;

/**
 * <p>
 * 用户与岗位关联表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
public interface SysUserPostMapper extends BaseMapper<SysUserPost> {

    /**
     * 批量新增用户岗位关系
     * @param list 岗位信息
     */
    void batchUserPost(List<SysUserPost> list);


    /**
     * 通过用户id查询岗位
     * @param userId 用户id
     * @return 用户岗位
     */
    List<Integer> getPostsByUserId(Long userId);
}
