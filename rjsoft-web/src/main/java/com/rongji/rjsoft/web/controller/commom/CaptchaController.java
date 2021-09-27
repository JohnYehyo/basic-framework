package com.rongji.rjsoft.web.controller.commom;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import com.google.code.kaptcha.Producer;
import com.rongji.rjsoft.common.util.LogUtils;
import com.rongji.rjsoft.common.util.RedisCache;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import com.rongji.rjsoft.vo.CaptchaVo;
import com.rongji.rjsoft.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @description: 验证码处理
 * @author: JohnYehyo
 * @create: 2021-08-31 18:31:41
 */
@Api(tags = "系统管理-验证码处理")
@RestController
@RequestMapping(value = "captcha")
public class CaptchaController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    /**
     * 验证码类型-数字
     */
    private static final String MATH = "math";

    /**
     * 验证码类型-字符
     */
    private static final String CHAR = "char";

    /**
     * 验证码类型
     */
    @Value("${JohnYehyo.captchaType}")
    private String captchaType;

    @ApiOperation(value = "获取验证码")
    @GetMapping
    public Object creat() {
        // 保存验证码信息
        String uuid = UUID.fastUUID().toString();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        if (MATH.equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if (CHAR.equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            LogUtils.error("写出验证码图片失败:{}", e.getMessage(), e);
            throw new BusinessException(ResponseEnum.CAPTCHA_FAIL);
        }

        CaptchaVo captchaVo = new CaptchaVo(uuid, Base64.encode(os.toByteArray()));

        return ResponseVo.response(ResponseEnum.SUCCESS, captchaVo);
    }
}
