package com.rongji.rjsoft.web.aop;

import com.rongji.rjsoft.common.annotation.RateAnnotation;
import com.rongji.rjsoft.common.util.RedisCache;
import com.rongji.rjsoft.common.util.http.IpUtils;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Description 限流切面
 * @Author JohnYehyo
 * @Date 2020-10-29 10:35
 * @Version 1.0
 */
@Aspect
@Component
public class RateAspect {

    private static final Logger logger = LoggerFactory.getLogger(RateAspect.class);
    @Autowired
    private RedisCache redisCache;

    @Pointcut("@annotation(com.rongji.rjsoft.common.annotation.RateAnnotation)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object handle(ProceedingJoinPoint point) throws Exception {
        HttpServletRequest request = getHttpServletRequest();

        String ip = IpUtils.getIpAddr(request);
        if (StringUtils.isEmpty(ip)) {
            throw new Exception("非法访问！ip不能为空");
        }

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        RateAnnotation rateLimit = methodSignature.getMethod().getAnnotation(RateAnnotation.class);
        // 限制访问次数
        int maxCount = rateLimit.maxCount();
        int rangeTime = rateLimit.time();

        String url = request.getRequestURI();
        String key = url + ":" + ip;

        Long count = redisCache.incr(key, 1);
        if(count <= maxCount){
            redisCache.expire(key, rangeTime, TimeUnit.SECONDS);
            return action(point);
        }
        throw new BusinessException(ResponseEnum.BUSY);
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    private Object action(ProceedingJoinPoint point) {
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }
}
