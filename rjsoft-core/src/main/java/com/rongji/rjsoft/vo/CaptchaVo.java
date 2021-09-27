package com.rongji.rjsoft.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 验证码
 * @author: JohnYehyo
 * @create: 2021-08-31 18:43:30
 */
@Data
@AllArgsConstructor
public class CaptchaVo {

    /**
     * 唯一码
     */
    private String uuid;

    /**
     * 验证码图片
     */
    private String image;
}
