package com.rongji.rjsoft.web.aop;

import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.vo.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @description: 返回切面
 * @author: JohnYehyo
 * @create: 2021-09-01 13:07:47
 */
@Aspect
@Component
@Order(1)
public class ResponseBodyAspect {

    private static final int RECORD_LENGTH = 300;

    @Pointcut("(@within(org.springframework.web.bind.annotation.RestController) ) " +
            "|| @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public void responseBody() {
    }


    @Around("responseBody()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object result = null;
        String controllerName = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getMethod().getName();
        StopWatch sw = new StopWatch();
        sw.start();

        try {
            printRequestParam(controllerName, methodName, args);
        } catch (Exception e) {
            LogUtils.error("记录入参异常:{}||{}||{}", controllerName, methodName, args, e);
        }
        result = joinPoint.proceed(args);
        if (!(result instanceof ResponseEntity) && !(result instanceof ResponseVo)) {
            //空对象或空集合返回无相关数据
            boolean b = result instanceof Collection && CollectionUtils.isEmpty((Collection) result);
            if (null == result || b) {
                result = ResponseVo.response(ResponseEnum.NO_DATA);
            } else {
                result = ResponseVo.response(ResponseEnum.SUCCESS, result);
            }
        }
        sw.stop();
        LogUtils.info("[{}]-->[{}] 耗时：{} 毫秒，请求应答：{}", controllerName, methodName,
                sw.getTotalTimeMillis(), substring(JSON.toJSONString(result), RECORD_LENGTH));
        return result;
    }

    private void printRequestParam(String controllerName, String methodName, Object[] args) {
        List<Object> argList = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof MultipartFile || arg instanceof MultipartFile[]) {
                continue;
            }
            argList.add(arg);
        }
        LogUtils.info("[{}]-->[{}] 请求参数：{}", controllerName, methodName,
                argList.size() > 0 ? JSON.toJSONString(argList) : "");
    }

    private String substring(String str, int length) {
        if (StringUtils.length(str) > length) {
            return str.substring(0, length);
        }
        return str;
    }
}
