package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.ao.system.SysDeptAo;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.entity.system.SysBranch;
import com.rongji.rjsoft.entity.system.SysDept;
import com.rongji.rjsoft.enums.EnableEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.mapper.SysBranchMapper;
import com.rongji.rjsoft.mapper.SysDeptMapper;
import com.rongji.rjsoft.query.system.dept.DeptQuey;
import com.rongji.rjsoft.service.ISysDeptService;
import com.rongji.rjsoft.vo.system.dept.SysDeptAllTreeVo;
import com.rongji.rjsoft.vo.system.dept.SysDeptTreeVo;
import com.rongji.rjsoft.vo.system.dept.SysDeptVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-03
 */
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {


    private final SysDeptMapper sysDeptMapper;

    private final TokenUtils tokenUtils;

    private final SysBranchMapper sysBranchMapper;


    /**
     * 检查部门名称是否存在
     *
     * @param sysDeptAo 部门信息
     * @return 是否存在
     */
    @Override
    public boolean checkDeptByName(SysDeptAo sysDeptAo) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDept::getDeptName, sysDeptAo.getDeptName()).last(" limit 1");
        SysDept sysDept = sysDeptMapper.selectOne(wrapper);
        if (null != sysDept && sysDept.getDeptId().longValue() != sysDeptAo.getDeptId().longValue()) {
            return true;
        }
        return false;
    }

    /**
     * 添加部门信息
     *
     * @param sysDeptAo 部门信息
     * @return 添加结果
     */
    @Override
    public boolean saveDept(SysDeptAo sysDeptAo) {
        SysDept sysDept = new SysDept();
        BeanUtil.copyProperties(sysDeptAo, sysDept);
        SysDept parentDept = sysDeptMapper.selectById(sysDeptAo.getParentId());
        if (null == parentDept) {
            sysDept.setAncestors("0");
        } else {
            if (EnableEnum.DISABLE.getCode() == parentDept.getStatus()) {
                throw new BusinessException(ResponseEnum.FAIL.getCode(), "父级部门已停用");
            }
            sysDept.setAncestors(parentDept.getAncestors() + "," + parentDept.getDeptId());
        }
        return sysDeptMapper.insert(sysDept) > 0;
    }

    /**
     * 编辑部门信息
     *
     * @param sysDeptAo 部门信息
     * @return 编辑结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDept(SysDeptAo sysDeptAo) {
        SysDept sysDept = new SysDept();
        BeanUtil.copyProperties(sysDeptAo, sysDept);
        SysDept parentDept = sysDeptMapper.selectById(sysDeptAo.getParentId());
        SysDept dept = sysDeptMapper.selectById(sysDeptAo.getDeptId());

        if (null == dept) {
            throw new BusinessException(ResponseEnum.FAIL.getCode(), "请联系管理员!");
        }
        if (null == sysDeptAo.getParentId()) {
            sysDept.setParentId(0L);
        }

        String newAncestors = "0";
        if (null != parentDept) {
            newAncestors = parentDept.getAncestors() + "," + parentDept.getDeptId();
        }
        String oldAncestors = dept.getAncestors();
        dept.setAncestors(newAncestors);
        sysDept.setAncestors(newAncestors);
        updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        return sysDeptMapper.updateById(sysDept) > 0;
    }

    /**
     * 更新本部门下属部门ancestor
     *
     * @param deptId       部门id
     * @param newAncestors 新ancestor
     * @param oldAncestors 老ancestor
     */
    private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> list = sysDeptMapper.selectChildrenByDeptId(deptId);
        if(null == list || list.size() == 0){
            return;
        }
        for (SysDept sysDept : list) {
            sysDept.setAncestors(sysDept.getAncestors().replace(oldAncestors, newAncestors));
        }
        int count = sysDeptMapper.batchUpdateChildreAncestors(list);
    }

    /**
     * 删除部门信息
     *
     * @param deptIds 部门id
     * @return 删除结果
     */
    @Override
    public boolean deleteDept(Long[] deptIds) {
        return sysDeptMapper.deleteBatchIds(Arrays.asList(deptIds)) > 0;
    }

    /**
     * 通过id获取部门信息
     *
     * @param deptId 部门id
     * @return 部门信息
     */
    @Override
    public SysDeptVo getDeptById(Long deptId) {
        SysDept sysDept = sysDeptMapper.selectById(deptId);
        SysDeptVo sysDeptVo = new SysDeptVo();
        BeanUtil.copyProperties(sysDept, sysDeptVo);
        LambdaQueryWrapper<SysBranch> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysBranch::getBranchCode, sysDept.getBranchCode());
        wrapper.last(" limit 0, 1");
        SysBranch sysBranch = sysBranchMapper.selectOne(wrapper);
        sysDeptVo.setBranchName(sysBranch.getBranchName());
        sysDeptVo.setBranchCode(sysBranch.getBranchCode());
        return sysDeptVo;
    }

    /**
     * 部门列表
     *
     * @param deptQuey 查询条件
     * @return 部门列表
     */
    @Override
    public List<SysDeptVo> listByCondition(DeptQuey deptQuey) {
        return sysDeptMapper.listByCondition(deptQuey);
    }

    /**
     * 获取当前用户部门树
     *
     * @param deptId 部门id
     * @return
     */
    @Override
    public List<SysDeptTreeVo> listByUser(Long deptId) {
        List<SysDeptTreeVo> list = new ArrayList<>();
        if(null == deptId){
            LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
            deptId = loginUser.getUser().getDeptId();
            SysDeptTreeVo sysDeptTreeVo = sysDeptMapper.getSimpleAsynchTreeById(deptId);
            sysDeptTreeVo.setParentNode(!isLeaf(sysDeptTreeVo));
            list.add(sysDeptTreeVo);
            return list;
        }
        list = sysDeptMapper.getAsynchDeptTreeByIds(deptId);
        for (SysDeptTreeVo sysDeptTreeVo : list) {
            sysDeptTreeVo.setParentNode(!isLeaf(sysDeptTreeVo));
        }
        return list;
    }

    /**
     * 判断是否为叶子节点
     * @param sysDeptTreeVo 部门树节点
     * @return 是否为叶子节点
     */
    private boolean isLeaf(SysDeptTreeVo sysDeptTreeVo) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDept::getParentId, sysDeptTreeVo.getDeptId());
        Integer count = sysDeptMapper.selectCount(wrapper);
        return count > 0 ? false : true;
    }

    /**
     * 通过角色id获取部门树
     *
     * @param deptId 部门id
     * @return 部门信息
     */
    @Override
    public SysDeptAllTreeVo allTree(Long deptId) {
        //当前顶级节点
        SysDept sysDept = getTopNode(deptId);
        if(null == sysDept){
            throw new BusinessException(ResponseEnum.NO_DATA);
        }

        //获取所有下级节点
        List<SysDeptAllTreeVo> treeList = sysDeptMapper.selectAllTreeNode(sysDept.getDeptId());

        SysDeptAllTreeVo topNode = new SysDeptAllTreeVo();
        BeanUtil.copyProperties(sysDept, topNode);

        //树结构组装
        if(CollectionUtil.isNotEmpty(treeList)){
            List<SysDeptAllTreeVo> tree = new ArrayList<>();
            tree.add(topNode);
            assembly(tree, treeList);
            return topNode;
        }
        return topNode;
    }

    private SysDept getTopNode(Long deptId) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        if(null != deptId) {
            wrapper.eq(SysDept::getDeptId, deptId);
        }
        wrapper.last(" limit 0,1");
        SysDept sysDept = sysDeptMapper.selectOne(wrapper);
        return sysDept;
    }

    private void assembly(List<SysDeptAllTreeVo> tree, List<SysDeptAllTreeVo> treeList) {
        for (SysDeptAllTreeVo sysDeptAllTreeVo : tree) {
            List<SysDeptAllTreeVo> children = new ArrayList<>();
            Iterator<SysDeptAllTreeVo> iterator = treeList.iterator();
            SysDeptAllTreeVo next;
            while (iterator.hasNext()) {
                next = iterator.next();
                if(next.getParentId().longValue() == sysDeptAllTreeVo.getDeptId().longValue()){
                    children.add(next);
                    iterator.remove();
                }
            }
            sysDeptAllTreeVo.setChildren(children);
            if(CollectionUtil.isNotEmpty(treeList)){
                assembly(sysDeptAllTreeVo.getChildren(), treeList);
            }
        }
    }
}
