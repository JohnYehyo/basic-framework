package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.system.SysPost;
import com.rongji.rjsoft.query.system.post.PostSelectQuery;
import com.rongji.rjsoft.query.system.post.PostQuery;
import com.rongji.rjsoft.vo.system.post.SysPostSelectVo;
import com.rongji.rjsoft.vo.system.post.SysPostVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 岗位信息表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-08-23
 */
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     * 岗位列表
     * @param page 分页对象
     * @param postQuery 查询条件
     * @return 分页结果
     */
    IPage<SysPostVo> pagesOfPost(IPage<SysPostVo> page, @Param("params") PostQuery postQuery);

    /**
     * 岗位下拉列表
     * @param postSelectQuery 查询条件
     * @return 岗位列表
     */
    List<SysPostSelectVo> listOfPost(PostSelectQuery postSelectQuery);
}
