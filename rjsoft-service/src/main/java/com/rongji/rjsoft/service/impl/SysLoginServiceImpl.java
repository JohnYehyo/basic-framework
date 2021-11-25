package com.rongji.rjsoft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.rongji.rjsoft.ao.system.LoginAo;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.entity.SsoLoginUser;
import com.rongji.rjsoft.common.security.util.AESUtils;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.RedisCache;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.entity.system.SysUser;
import com.rongji.rjsoft.enums.LogStatusEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.service.ISysLoginInfoService;
import com.rongji.rjsoft.service.ISysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Value("${JohnYehyo.key}")
    private String KEY;

    /**
     * 令牌有效期（默认30秒钟）
     */
    @Value("${token.sso.expireTime}")
    private int expireTime;


    /**
     * 登录
     *
     * @param loginAo 请求参数体
     * @return
     */
    @Override
    public String login(LoginAo loginAo) {
//        String verifyKey = Constants.CAPTCHA_CODE_KEY + loginAo.getUuid();
//        String captcha = redisCache.getCacheObject(verifyKey);
//        redisCache.deleteObject(verifyKey);
//
//        if (null == captcha) {
//            sysLoginInfoService.saveLoginInfo(loginAo.getUserName(), LogStatusEnum.FAIL.getCode(), ResponseEnum.CAPTCHA_EXPIRED.getValue());
//            throw new BusinessException(ResponseEnum.CAPTCHA_EXPIRED);
//        }
//
//        if (!captcha.equals(loginAo.getCaptcha())) {
//            LogUtils.warn("用户：{}验证码输入错误", loginAo.getUserName());
//            sysLoginInfoService.saveLoginInfo(loginAo.getUserName(), LogStatusEnum.FAIL.getCode(), ResponseEnum.CAPTCHA_ERROR.getValue());
//            throw new BusinessException(ResponseEnum.CAPTCHA_ERROR);
//        }

        String username = loginAo.getUserName();
        String password = loginAo.getPassword();
        try {
            byte[] key = KEY.getBytes("utf-8");
            username = new String(AESUtils.decrypt(username, key), "utf-8");
            password = new String(AESUtils.decrypt(password, key), "utf-8");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.ENCRYPTION_TO_DECRYPT);
        }

        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            sysLoginInfoService.saveLoginInfo(username, LogStatusEnum.FAIL.getCode(), e.getMessage());
            if(e.getCause() instanceof BusinessException){
                throw (BusinessException) e.getCause();
            }
            throw new BusinessException(ResponseEnum.LOGIN_ERROR);
        }


        sysLoginInfoService.saveLoginInfo(username, LogStatusEnum.LOGIN_SUCCESS.getCode(),
                LogStatusEnum.LOGIN_SUCCESS.getValue());

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenUtils.createToken(loginUser);
    }

    /**
     * 创建SSO token
     * @return 创建SSO token
     */
    @Override
    public String creatSsoToken() {
        LoginUser loginUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        SsoLoginUser ssoLoginUser = new SsoLoginUser();
        BeanUtil.copyProperties(loginUser, ssoLoginUser);
        BeanUtil.copyProperties(user, ssoLoginUser);
        String key = UUID.randomUUID().toString().replace("-", "");
        redisCache.setCacheObject(Constants.SSO_KEY + key, ssoLoginUser, expireTime, TimeUnit.MINUTES);
        return key;
    }

    /**
     * token登录
     * @param token token
     * @return 登录结果
     */
    @Override
    public SsoLoginUser tokenLogin(String token) {
        return redisCache.getCacheObject(Constants.SSO_KEY + token);
    }
}
