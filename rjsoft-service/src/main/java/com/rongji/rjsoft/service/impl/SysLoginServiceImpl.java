package com.rongji.rjsoft.service.impl;

import com.rongji.rjsoft.ao.system.LoginAo;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.common.util.OsUtils;
import com.rongji.rjsoft.common.util.RedisCache;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.common.util.http.IpUtils;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.entity.monitor.SysLoginInfo;
import com.rongji.rjsoft.enums.LogStatusEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.service.ISysLoginInfoService;
import com.rongji.rjsoft.service.ISysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @description: 登录
 * @author: JohnYehyo
 * @create: 2021-04-26 17:28:59
 */
@Service
public class SysLoginServiceImpl implements ISysLoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ISysLoginInfoService sysLoginInfoService;


    /**
     * 登录
     *
     * @param loginAo 请求参数体
     * @return
     */
    @Override
    public String login(LoginAo loginAo) {
//        String verifyKey = Constants.CAPTCHA_CODE_KEY + loginAo.getAppId();
//        String captcha = redisCache.getCacheObject(verifyKey);
//        redisCache.deleteObject(verifyKey);
//
//        if (null == captcha) {
//            sysLoginInfoService.saveLoginInfo(loginAo.getUserName(), LogStatusEnum.FAIL.getCode(), ResponseEnum.CAPTCHA_EXPIRED.getValue());
//            throw new BusinessException(ResponseEnum.CAPTCHA_EXPIRED);
//        }
//
//        if (captcha.equalsIgnoreCase(loginAo.getCaptcha())) {
//            LogUtils.warn("用户：{}验证码输入错误", loginAo.getUserName());
//            sysLoginInfoService.saveLoginInfo(loginAo.getUserName(), LogStatusEnum.FAIL.getCode(), ResponseEnum.CAPTCHA_ERROR.getValue());
//            throw new BusinessException(ResponseEnum.CAPTCHA_ERROR);
//        }

        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginAo.getUserName(), loginAo.getPassword()));
        } catch (Exception e) {
            sysLoginInfoService.saveLoginInfo(loginAo.getUserName(), LogStatusEnum.FAIL.getCode(), e.getMessage());
            throw new BusinessException(ResponseEnum.LOGIN_ERROR);
        }


        sysLoginInfoService.saveLoginInfo(loginAo.getUserName(), LogStatusEnum.LOGIN_SUCCESS.getCode(),
                LogStatusEnum.LOGIN_SUCCESS.getValue());

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenUtils.createToken(loginUser);
    }
}
