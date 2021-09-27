package com.rongji.rjsoft.vo;

import com.rongji.rjsoft.enums.ResponseEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 统一返回
 * @author: JohnYehyo
 * @create: 2021-04-26 01:22:48
 */
@Data
@AllArgsConstructor
public class ResponseVo<T> implements Serializable {

    private static final long serialVersionUID = 2157815966898945209L;

    @ApiModelProperty(value = "响应码")
    private Integer code;
    @ApiModelProperty(value = "响应信息")
    private String msg;
    @ApiModelProperty(value = "响应结果")
    private T data;

    public ResponseVo(Integer code, String msg) {
        this(code, msg, null);
    }

    public static ResponseVo response(ResponseEnum responseEnum) {
        return new ResponseVo(responseEnum.getCode(), responseEnum.getValue());
    }

    public static ResponseVo response(ResponseEnum responseEnum, Object obj) {
        return new ResponseVo(responseEnum.getCode(), responseEnum.getValue(), obj);
    }

    public static ResponseVo success() {
        return new ResponseVo(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getValue());
    }

    public static ResponseVo success(String msg) {
        return new ResponseVo(ResponseEnum.SUCCESS.getCode(), msg);
    }

    public static ResponseVo error(Integer code, String msg) {
        return new ResponseVo(code, msg);
    }

    public static ResponseVo error(String msg) {
        return new ResponseVo(ResponseEnum.FAIL.getCode(), msg);
    }

    public static ResponseVo error() {
        return new ResponseVo(ResponseEnum.EXCEPTION.getCode(), ResponseEnum.EXCEPTION.getValue());
    }
}
