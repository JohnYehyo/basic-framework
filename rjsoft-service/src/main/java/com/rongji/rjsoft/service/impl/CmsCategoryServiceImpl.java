package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.ao.content.CmsCategoryAo;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.entity.content.CmsCategory;
import com.rongji.rjsoft.entity.content.CmsColumn;
import com.rongji.rjsoft.enums.EnableEnum;
import com.rongji.rjsoft.mapper.CmsCategoryMapper;
import com.rongji.rjsoft.query.content.CmsCategoryQuery;
import com.rongji.rjsoft.service.ICmsCategoryService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.content.CmsCategoryTreeVo;
import com.rongji.rjsoft.vo.content.CmsCategoryVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 文章类别信息表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-15
 */
@Service
@AllArgsConstructor
public class CmsCategoryServiceImpl extends ServiceImpl<CmsCategoryMapper, CmsCategory> implements ICmsCategoryService {

    private final CmsCategoryMapper cmsCategoryMapper;

    /**
     * 添加文章类别
     * @param cmsCategoryAo 文章类别表单
     * @return 添加结果
     */
    @Override
    public boolean add(CmsCategoryAo cmsCategoryAo) {
        CmsCategory parent = cmsCategoryMapper.selectById(cmsCategoryAo.getParentId());
        CmsCategory cmsCategory = new CmsCategory();
        BeanUtil.copyProperties(cmsCategoryAo, cmsCategory);
        cmsCategory.setAncestors(parent.getAncestors() + "," + parent.getParentId());
        return cmsCategoryMapper.insert(cmsCategory) > 0;
    }

    /**
     * 编辑文章类别
     * @param cmsCategoryAo 文章类别表单
     * @return 编辑结果
     */
    @Override
    public boolean edit(CmsCategoryAo cmsCategoryAo) {
        CmsCategory parent = cmsCategoryMapper.selectById(cmsCategoryAo.getParentId());
        CmsCategory old = cmsCategoryMapper.selectById(cmsCategoryAo.getCategoryId());
        CmsCategory cmsCategory = new CmsCategory();
        BeanUtil.copyProperties(cmsCategoryAo, cmsCategory);
        cmsCategory.setAncestors(parent.getAncestors() + "," + parent.getParentId());

        if (null != parent && null != old) {
            String newAncestors = parent.getAncestors() + "," + parent.getCategoryId();
            String oldAncestors = old.getAncestors();
            old.setAncestors(newAncestors);
            cmsCategory.setAncestors(newAncestors);
            //修改该节点下所有节点的ancestors
            updateSiteChildren(old.getCategoryId(), newAncestors, oldAncestors);
        }

        return cmsCategoryMapper.updateById(cmsCategory) > 0;
    }

    private void updateSiteChildren(Long categoryId, String newAncestors, String oldAncestors) {

        List<CmsCategory> list = cmsCategoryMapper.selectChildrenByCategoryId(categoryId);
        if(null == list || list.size() == 0){
            return;
        }
        for (CmsCategory cmsCategory : list) {
            cmsCategory.setAncestors(cmsCategory.getAncestors().replace(oldAncestors, newAncestors));
        }
        int count = cmsCategoryMapper.batchUpdateChildreAncestors(list);
    }

    /**
     * 删除文章类别
     * @param categoryId 文章类别id
     * @return 删除结果
     */
    @Override
    public boolean delete(Long[] categoryId) {
        return cmsCategoryMapper.deleteBatchIds(Arrays.asList(categoryId)) > 0;
    }

    /**
     * 禁用文章类别
     * @param categoryId 文章类别id
     * @return 禁用结果
     */
    @Override
    public boolean disable(Long[] categoryId) {
        return cmsCategoryMapper.updateStatus(categoryId);
    }

    /**
     * 文章类别分页结果
     * @param cmsCategoryQuery 查询对象
     * @return 文章类别分页结果
     */
    @Override
    public CommonPage<CmsCategoryVo> pageList(CmsCategoryQuery cmsCategoryQuery) {
        IPage<CmsCategoryVo> page = new Page<>(cmsCategoryQuery.getCurrent(), cmsCategoryQuery.getPageSize());
        page = cmsCategoryMapper.getPages(page, cmsCategoryQuery);
        return CommonPageUtils.assemblyPage(page);
    }

    /**
     * 文章类别树
     * @param cmsCategoryQuery 查询对象
     * @return 文章类别树
     */
    @Override
    public List<CmsCategoryTreeVo> tree(CmsCategoryQuery cmsCategoryQuery) {
        LambdaQueryWrapper<CmsCategory> wrapper = new LambdaQueryWrapper<>();
        List<CmsCategory> list;
        List<CmsCategoryTreeVo> treeList = new ArrayList<>();
        CmsCategoryTreeVo cmsCategoryTreeVo;
        cmsCategoryQuery.setCategoryId(cmsCategoryQuery.getCategoryId() == null ? 0L : cmsCategoryQuery.getCategoryId());
        //查询以cmsSiteQuery.getSiteId为父节点的所有站点
        wrapper.eq(CmsCategory::getParentId, cmsCategoryQuery.getCategoryId());
        wrapper.eq(CmsCategory::getStatus, EnableEnum.DISABLE);
        list = cmsCategoryMapper.selectList(wrapper);
        for (CmsCategory cmsCategory : list) {
            cmsCategoryTreeVo = new CmsCategoryTreeVo();
            BeanUtil.copyProperties(cmsCategory, cmsCategoryTreeVo);
            cmsCategoryTreeVo.setParentNode(!isLeaf(cmsCategoryTreeVo));
            treeList.add(cmsCategoryTreeVo);
        }
        return treeList;
    }

    private boolean isLeaf(CmsCategoryTreeVo cmsCategoryTreeVo) {
        LambdaQueryWrapper<CmsCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CmsCategory::getParentId, cmsCategoryTreeVo.getCategoryId());
        Integer count = cmsCategoryMapper.selectCount(wrapper);
        return count > 0 ? false : true;
    }


}
