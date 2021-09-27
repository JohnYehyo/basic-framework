package com.rongji.rjsoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.entity.system.SysUserPost;
import com.rongji.rjsoft.mapper.SysUserPostMapper;
import com.rongji.rjsoft.service.ISysUserPostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户与岗位关联表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
@Service
@AllArgsConstructor
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {

    private final SysUserPostMapper sysUserPostMapper;

    /**
     * 通过用户id查询岗位
     * @param userId 用户id
     * @return 用户岗位
     */
    @Override
    public List<Integer> getPostsByUserId(Long userId) {
        return sysUserPostMapper.getPostsByUserId(userId);
    }
}
