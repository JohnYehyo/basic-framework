package com.rongji.rjsoft.mapper;

import com.rongji.rjsoft.entity.system.SysBranch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.vo.system.branch.BranchTreeVo;

import java.util.List;

/**
 * <p>
 * 行政区划 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-03
 */
public interface SysBranchMapper extends BaseMapper<SysBranch> {

    /**
     * 获取简单异步树(单层级)
     * @param branchCode branch_code
     * @return 异步树
     */
    BranchTreeVo getSimpleAsynchTreeByCode(String branchCode);

    /**
     * 通过branchcode获取异步树
     * @param branchCode branch_code
     * @return 异步树
     */
    List<BranchTreeVo> getAsynchTreeByCode(String branchCode);
}
