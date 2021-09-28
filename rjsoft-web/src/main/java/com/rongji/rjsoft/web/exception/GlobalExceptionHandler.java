package com.rongji.rjsoft.web.exception;

import com.alibaba.fastjson.JSON;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.vo.ResponseVo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 全局异常
 * @author: JohnYehyo
 * @create: 2021-04-26 16:01:22
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public ResponseVo handle(Exception e) {
        BusinessException e1 = (BusinessException) e;
        LogUtils.error(ResponseEnum.EXCEPTION.getValue(), e);
        return ResponseVo.error(e1.getCode(), e1.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseVo accessDeniedhandle(Exception e) {
        AccessDeniedException e1 = (AccessDeniedException) e;
        LogUtils.error(ResponseEnum.NO_PERMISSION.getValue(), e);
        return ResponseVo.response(ResponseEnum.NO_PERMISSION);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVo handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMesssages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssages.add(fieldError.getDefaultMessage());
        }
        Integer code = ResponseEnum.PARAM_ERROR.getCode();
        String error = errorMesssages.stream().collect(Collectors.joining(","));
        LogUtils.error(ResponseEnum.PARAM_ERROR.getValue(), e.getMessage(), e);
        return ResponseVo.error(code, error);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseVo handleMethodArgumentNotValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMesssages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssages.add(fieldError.getDefaultMessage());
        }
        Integer code = ResponseEnum.PARAM_ERROR.getCode();
        LogUtils.error(ResponseEnum.PARAM_ERROR.getValue(), e);
        return ResponseVo.error(code, JSON.toJSONString(errorMesssages));
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseVo maxUploadSizeExceededException(Exception e) {
        LogUtils.error(ResponseEnum.SUPER_LARGE_FILE.getValue(), e);
        return ResponseVo.error(ResponseEnum.SUPER_LARGE_FILE.getCode(), ResponseEnum.SUPER_LARGE_FILE.getValue());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVo otherException(Exception e) {
        LogUtils.error(ResponseEnum.EXCEPTION.getValue(), e);
        return ResponseVo.error(ResponseEnum.EXCEPTION.getValue());
    }
}
