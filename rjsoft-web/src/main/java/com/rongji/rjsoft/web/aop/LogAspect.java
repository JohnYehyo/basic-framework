package com.rongji.rjsoft.web.aop;

import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.common.security.entity.LoginUser;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.common.util.ServletUtils;
import com.rongji.rjsoft.common.util.http.IpUtils;
import com.rongji.rjsoft.entity.monitor.SysOperationLog;
import com.rongji.rjsoft.entity.system.SysDept;
import com.rongji.rjsoft.enums.LogStatusEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.service.ISysOperationLogService;
import com.rongji.rjsoft.vo.ResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 操作日志
 * @author: JohnYehyo
 * @create: 2021-09-08 09:53:43
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private ISysOperationLogService sysOperationLogService;

    @Autowired
    private TokenUtils tokenUtils;

    private static final int RECORD_LENGTH = 300;

    @Pointcut("@annotation(com.rongji.rjsoft.common.annotation.LogAction)")
    public void logPointCut() {
    }

    @AfterReturning(value = "logPointCut()", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        log(joinPoint, null, result);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void after(JoinPoint joinPoint, Exception e) {
        log(joinPoint, e, null);
    }

    public void log(JoinPoint joinPoint, final Exception e, Object result) {
        SysOperationLog log = new SysOperationLog();
        HttpServletRequest request = ServletUtils.getRequest();

        String ip = IpUtils.getIpAddr(request);
        log.setIp(ip);

        // 拦截当前正在执行的controller
        Object target = joinPoint.getTarget();
        // 拦截当前正在执行的方法
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        // 拦截参数类型
        Signature sig = joinPoint.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();

        // 获得被拦截的方法
        Method method = null;
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e1) {
            LogUtils.error("获取被拦截方法异常(NoSuchMethodException)", e1);
        } catch (SecurityException e1) {
            LogUtils.error("获取被拦截方法异常(SecurityException)", e1);
        }
        if (null == method) {
            //不需要拦截直接执行
            return;
        }
        // 判断是否包含自定义的注解
        if (!method.isAnnotationPresent(LogAction.class)) {
            return;
        }

        LogAction systemlog = method.getAnnotation(LogAction.class);

        LoginUser loginUser = tokenUtils.getLoginUser(request);
        String userName = loginUser.getUsername();
        if (null != e) {
            log.setStatus(LogStatusEnum.FAIL.getCode());
            log.setResult(JSON.toJSONString(ResponseVo.response(ResponseEnum.EXCEPTION)));
            log.setErrorMsg(substring(e.toString(), RECORD_LENGTH));
            LogUtils.error("方法执行异常:", e);
        } else {
            log.setStatus(LogStatusEnum.SUCCESS.getCode());
            log.setResult(substring(JSON.toJSONString(result), RECORD_LENGTH));
        }
        SysDept sysDept = loginUser.getSysDept();
        if (null != sysDept) {
            log.setDeptId(sysDept.getDeptId());
            log.setDeptName(sysDept.getDeptName());
            log.setBranchCode(sysDept.getBranchCode());
        }
        log.setUrl(ServletUtils.getRequest().getRequestURI());
        log.setModule(systemlog.module());
        log.setMethod(systemlog.method());
        log.setBusinessType(systemlog.logType().getCode());
        log.setOperatorType(systemlog.operatorType().getCode());
        log.setUserName(userName);
        log.setRequestMethod(ServletUtils.getRequest().getMethod());
        log.setMethodTarget(className + "." + methodName + "()");
        log.setTime(LocalDateTime.now());
        if (systemlog.isRecord()) {
            setRequestValue(joinPoint, log);
        }
        try {
            sysOperationLogService.save(log);
        } catch (Exception e1) {
            LogUtils.error("记录操作日志异常:", e1);
        }
    }

    private void setRequestValue(JoinPoint joinPoint, SysOperationLog sysOperationLog) {
        String requestMethod = sysOperationLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = printRequestParam(joinPoint);
            sysOperationLog.setParam(substring(params, RECORD_LENGTH));
            return;
        }
        Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        sysOperationLog.setParam(substring(JSON.toJSONString(paramsMap), RECORD_LENGTH));

    }

    private String printRequestParam(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<Object> argList = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof MultipartFile || arg instanceof MultipartFile[]) {
                continue;
            }
            argList.add(arg);
        }
        return JSON.toJSONString(argList);
    }

    private String substring(String str, int length) {
        if (StringUtils.length(str) > length) {
            return str.substring(0, length);
        }
        return str;
    }

}
