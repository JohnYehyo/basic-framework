package com.rongji.rjsoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.entity.system.SysBranch;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.mapper.SysBranchMapper;
import com.rongji.rjsoft.service.ISysBranchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.vo.system.branch.BranchTreeVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 行政区划 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-03
 */
@Service
@AllArgsConstructor
public class SysBranchServiceImpl extends ServiceImpl<SysBranchMapper, SysBranch> implements ISysBranchService {

    private final SysBranchMapper sysBranchMapper;

    private final TokenUtils tokenUtils;


    /**
     * 行政区划异步树
     *
     * @param branchCode
     * @return
     */
    @Override
    public List<BranchTreeVo> asynchTree(String branchCode) {
        List<BranchTreeVo> list = new ArrayList<>();
        if (StringUtils.isEmpty(branchCode)) {
            LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
            branchCode = loginUser.getSysDept().getBranchCode();
            if(StringUtils.isEmpty(branchCode)){
                throw new BusinessException(ResponseEnum.NO_DEPT);
            }
            BranchTreeVo branchTreeVo = sysBranchMapper.getSimpleAsynchTreeByCode(branchCode);
            branchTreeVo.setParentNode(!isLeaf(branchTreeVo));
            list.add(branchTreeVo);
            return list;
        }
        list = sysBranchMapper.getAsynchTreeByCode(branchCode);
        for (BranchTreeVo branchTreeVo : list) {
            branchTreeVo.setParentNode(!isLeaf(branchTreeVo));
        }
        return list;
    }

    private boolean isLeaf(BranchTreeVo branchTreeVo) {
        LambdaQueryWrapper<SysBranch> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysBranch::getParentCode, branchTreeVo.getBranchCode());
        Integer count = sysBranchMapper.selectCount(wrapper);
        return count > 0 ? false : true;
    }
}
