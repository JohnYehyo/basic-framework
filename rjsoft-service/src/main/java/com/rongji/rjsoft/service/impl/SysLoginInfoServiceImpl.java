package com.rongji.rjsoft.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.common.util.CommonPageUtils;
import com.rongji.rjsoft.common.util.OsUtils;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.common.util.http.IpUtils;
import com.rongji.rjsoft.entity.monitor.SysLoginInfo;
import com.rongji.rjsoft.mapper.SysLoginInfoMapper;
import com.rongji.rjsoft.query.monitor.login.LoginInfoQuery;
import com.rongji.rjsoft.service.ISysLoginInfoService;
import com.rongji.rjsoft.vo.CommonPage;
import com.rongji.rjsoft.vo.monitor.login.LoginInfoVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统访问记录 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-09
 */
@Service
@AllArgsConstructor
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysLoginInfo> implements ISysLoginInfoService {

    private final SysLoginInfoMapper sysLoginInfoMapper;

    /**
     * 记录访问
     * @param userName 登录用户
     * @param result 登录结果
     * @param msg 登录提示
     */
    @Override
    public void saveLoginInfo(String userName, int result, String msg) {
        SysLoginInfo sysLoginInfo = new SysLoginInfo();
        sysLoginInfo.setUserName(userName);
        HttpServletRequest request = ServletUtils.getRequest();
        sysLoginInfo.setIp(IpUtils.getIpAddr(request));
        sysLoginInfo.setBrowser(OsUtils.getBrowserName(request));
        sysLoginInfo.setOs(OsUtils.getOsName(request));
        sysLoginInfo.setLoginTime(LocalDateTime.now());
        sysLoginInfo.setStatus(result);
        sysLoginInfo.setMsg(msg);
        sysLoginInfoMapper.insert(sysLoginInfo);
    }

    /**
     * 登录信息分页列表
     * @param loginInfoQuery 查询条件
     * @return 分页结果
     */
    @Override
    public CommonPage<LoginInfoVo> getPages(LoginInfoQuery loginInfoQuery) {
        if(null == loginInfoQuery.getCurrent()){
            loginInfoQuery.setCurrent(1);
        }
        if(null == loginInfoQuery.getPageSize()){
            loginInfoQuery.setPageSize(10);
        }
        IPage<LoginInfoVo> pages = new Page<>();
        pages = sysLoginInfoMapper.getPages(pages, loginInfoQuery);
        return CommonPageUtils.assemblyPage(pages);
    }
}
