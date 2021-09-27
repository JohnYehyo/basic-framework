package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.ao.content.CmsColumnAo;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.entity.content.CmsColumn;
import com.rongji.rjsoft.entity.content.CmsSite;
import com.rongji.rjsoft.mapper.CmsColumnMapper;
import com.rongji.rjsoft.query.content.CmsColumnQuery;
import com.rongji.rjsoft.service.ICmsColumnService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.content.CmsColumnTreeVo;
import com.rongji.rjsoft.vo.content.CmsColumnVo;
import com.rongji.rjsoft.vo.content.CmsSiteTreeVo;
import com.rongji.rjsoft.vo.content.CmsSiteVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 栏目信息表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
@Service
@AllArgsConstructor
public class CmsColumnServiceImpl extends ServiceImpl<CmsColumnMapper, CmsColumn> implements ICmsColumnService {

    private final CmsColumnMapper cmsColumnMapper;

    /**
     * 添加栏目信息
     *
     * @param cmsColumnAo 栏目信息
     * @return 添加结果
     */
    @Override
    public boolean add(CmsColumnAo cmsColumnAo) {
        CmsColumn parent = cmsColumnMapper.selectById(cmsColumnAo.getParentId());
        CmsColumn cmsColumn = new CmsColumn();
        BeanUtil.copyProperties(cmsColumnAo, cmsColumn);
        cmsColumn.setAncestors(parent.getAncestors() + "," + parent.getParentId());
        return cmsColumnMapper.insert(cmsColumn) > 0;
    }

    /**
     * 编辑栏目信息
     *
     * @param cmsColumnAo 栏目信息
     * @return 编辑结果
     */
    @Override
    public boolean edit(CmsColumnAo cmsColumnAo) {
        CmsColumn parent = cmsColumnMapper.selectById(cmsColumnAo.getParentId());
        CmsColumn old = cmsColumnMapper.selectById(cmsColumnAo.getColumnId());

        CmsColumn cmsColumn = new CmsColumn();
        BeanUtil.copyProperties(cmsColumnAo, cmsColumn);
        cmsColumn.setAncestors(parent.getAncestors() + "," + parent.getParentId());

        if (null != parent && null != old) {
            String newAncestors = parent.getAncestors() + "," + parent.getColumnId();
            String oldAncestors = old.getAncestors();
            old.setAncestors(newAncestors);
            cmsColumn.setAncestors(newAncestors);
            //修改该节点下所有节点的ancestors
            updateSiteChildren(old.getColumnId(), newAncestors, oldAncestors);
        }

        return cmsColumnMapper.updateById(cmsColumn) > 0;
    }

    private void updateSiteChildren(Long siteId, String newAncestors, String oldAncestors) {
        List<CmsColumn> list = cmsColumnMapper.selectChildrenByColumnId(siteId);
        if(null == list || list.size() == 0){
            return;
        }
        for (CmsColumn cmsColumn : list) {
            cmsColumn.setAncestors(cmsColumn.getAncestors().replace(oldAncestors, newAncestors));
        }
        int count = cmsColumnMapper.batchUpdateChildreAncestors(list);
    }


    /**
     * 删除栏目
     *
     * @param columnId 栏目id
     * @return 删除结果
     */
    @Override
    public boolean delete(Long[] columnId) {
        return cmsColumnMapper.deleteBatchIds(Arrays.asList(columnId)) > 0;
    }

    /**
     * 栏目分页列表
     * @param cmsColumnQuery 查询条件
     * @return 栏目分页列表
     */
    @Override
    public CommonPage<CmsColumnVo> pageList(CmsColumnQuery cmsColumnQuery) {
        IPage<CmsColumnVo> page = new Page<>(cmsColumnQuery.getCurrent(), cmsColumnQuery.getPageSize());
        page = cmsColumnMapper.getPage(page, cmsColumnQuery);
        return CommonPageUtils.assemblyPage(page);
    }

    /**
     * 栏目树
     * @param cmsColumnQuery 查询条件
     * @return 栏目树
     */
    @Override
    public List<CmsColumnTreeVo> tree(CmsColumnQuery cmsColumnQuery) {
        LambdaQueryWrapper<CmsColumn> wrapper = new LambdaQueryWrapper<>();
        List<CmsColumn> list;
        List<CmsColumnTreeVo> treeList = new ArrayList<>();
        CmsColumnTreeVo cmsColumnTreeVo;
        cmsColumnQuery.setColumnId(cmsColumnQuery.getColumnId() == null ? 0L : cmsColumnQuery.getColumnId());
        //查询以cmsSiteQuery.getSiteId为父节点的所有站点
        wrapper.eq(CmsColumn::getParentId, cmsColumnQuery.getColumnId());
        list = cmsColumnMapper.selectList(wrapper);
        for (CmsColumn cmsColumn : list) {
            cmsColumnTreeVo = new CmsColumnTreeVo();
            BeanUtil.copyProperties(cmsColumn, cmsColumnTreeVo);
            cmsColumnTreeVo.setParentNode(!isLeaf(cmsColumnTreeVo));
            treeList.add(cmsColumnTreeVo);
        }
        return treeList;
    }

    private boolean isLeaf(CmsColumnTreeVo cmsColumnTreeVo) {
        LambdaQueryWrapper<CmsColumn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CmsColumn::getParentId, cmsColumnTreeVo.getColumnId());
        Integer count = cmsColumnMapper.selectCount(wrapper);
        return count > 0 ? false : true;
    }
}
