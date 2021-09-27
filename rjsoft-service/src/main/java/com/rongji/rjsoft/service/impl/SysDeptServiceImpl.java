package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.ao.system.SysDeptAo;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.entity.system.SysDept;
import com.rongji.rjsoft.enums.EnableEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.mapper.SysDeptMapper;
import com.rongji.rjsoft.query.system.dept.DeptQuey;
import com.rongji.rjsoft.service.ISysDeptService;
import com.rongji.rjsoft.vo.system.dept.SysDeptTreeVo;
import com.rongji.rjsoft.vo.system.dept.SysDeptVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
        if (EnableEnum.DISABLE.getCode() == parentDept.getStatus()) {
            throw new BusinessException(ResponseEnum.FAIL.getCode(), "父级部门已停用");
        }
        sysDept.setAncestors(parentDept.getAncestors() + "," + parentDept.getDeptId());
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
        if (null != parentDept && null != dept) {
            String newAncestors = parentDept.getAncestors() + "," + parentDept.getDeptId();
            String oldAncestors = dept.getAncestors();
            dept.setAncestors(newAncestors);
            sysDept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
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
     * @param roleId
     * @return
     */
    @Override
    public Object listByRoleId(Long roleId) {
        return null;
    }
}
