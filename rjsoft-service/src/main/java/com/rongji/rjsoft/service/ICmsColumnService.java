package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.ao.content.CmsColumnAo;
import com.rongji.rjsoft.entity.content.CmsColumn;
import com.rongji.rjsoft.query.content.CmsColumnQuery;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.content.CmsColumnTreeVo;
import com.rongji.rjsoft.vo.content.CmsColumnVo;

import java.util.List;

/**
 * <p>
 * 栏目信息表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
public interface ICmsColumnService extends IService<CmsColumn> {

    /**
     * 添加栏目
     * @param cmsColumnAo 栏目信息
     * @return 添加结果
     */
    boolean add(CmsColumnAo cmsColumnAo);

    /**
     * 编辑栏目
     * @param cmsColumnAo 栏目信息
     * @return 编辑结果
     */
    boolean edit(CmsColumnAo cmsColumnAo);

    /**
     * 删除栏目
     * @param columnId 栏目id
     * @return 删除结果
     */
    boolean delete(Long[] columnId);

    /**
     * 栏目分页列表
     * @param cmsColumnQuery 查询条件
     * @return 栏目分页列表
     */
    CommonPage<CmsColumnVo> pageList(CmsColumnQuery cmsColumnQuery);

    /**
     * 栏目树查询
     * @param cmsColumnQuery 查询条件
     * @return 栏目树查询
     */
    List<CmsColumnTreeVo> tree(CmsColumnQuery cmsColumnQuery);
}
