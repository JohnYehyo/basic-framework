package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.entity.system.SysDept;
import com.rongji.rjsoft.query.system.dept.DeptQuey;
import com.rongji.rjsoft.vo.system.dept.SysDeptAllTreeVo;
import com.rongji.rjsoft.vo.system.dept.SysDeptTreeVo;
import com.rongji.rjsoft.vo.system.dept.SysDeptVo;

import java.util.List;

/**
 * @description: 部门表 Mapper 接口
 * @author: JohnYehyo
 * @create: 2021-09-03 12:36:04
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 通过部门id查询所有下级的部门id
     * @param deptId 部门id
     * @return 部门id集合
     */
    List<Long> selectDeptIdSByDeptId(Long deptId);

    /**
     * 通过部门id集合查询部门信息
     * @param deptId 部门id
     * @return 部门信息
     */
    List<SysDeptTreeVo> getAsynchDeptTreeByIds(Long deptId);

    /**
     * 部门列表
     * @param deptQuey 查询条件
     * @return 部门列表
     */
    List<SysDeptVo> listByCondition(DeptQuey deptQuey);

    /**
     * 通过部门id查询所有下属部门
     * @param deptId 部门id
     * @return 下属部门
     */
    List<SysDept> selectChildrenByDeptId(Long deptId);

    /**
     * 更新子部门ancestor
     * @param list 子部门
     * @return 更新结果
     */
    int batchUpdateChildreAncestors(List<SysDept> list);

    /**
     * 部门异步树单节点
     * @param deptId 部门id
     * @return 部门树节点信息
     */
    SysDeptTreeVo getSimpleAsynchTreeById(Long deptId);

    /**
     * 获取当前部门下的所有部门节点
     * @param deptId 部门id
     * @return 下属部门信息
     */
    List<SysDeptAllTreeVo> selectAllTreeNode(Long deptId);
}
