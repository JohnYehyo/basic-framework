package com.rongji.rjsoft.service;

import com.rongji.rjsoft.entity.system.SysBranch;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.vo.system.branch.BranchTreeVo;

import java.util.List;

/**
 * <p>
 * 行政区划 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-03
 */
public interface ISysBranchService extends IService<SysBranch> {

    /**
     * 行政区划异步树
     * @param branchCode branch_code
     * @return 行政区划异步树
     */
    List<BranchTreeVo> asynchTree(String branchCode);
}
