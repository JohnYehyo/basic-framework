package com.rongji.rjsoft.exception;

import com.rongji.rjsoft.enums.ResponseEnum;

/**
 * @description: 业务异常
 * @author: JohnYehyo
 * @create: 2021-04-26 16:10:13
 */
public class BusinessException extends RuntimeException{

    private Integer code;
    private String msg;

    public BusinessException(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getValue();
    }

    public BusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
