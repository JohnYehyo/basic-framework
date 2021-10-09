package com.rongji.rjsoft.web.config;

import com.rongji.rjsoft.common.security.util.RSAUtils;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;

/**
 * @description: Jasypt EncryptablePropertyResolver 自定义实现
 * @author: JohnYehyo
 * @create: 2021-10-09 10:46:56
 */
public class MyEncryptablePropertyResolver implements EncryptablePropertyResolver {

    private EncryptablePropertyDetector detector = null;

    public EncryptablePropertyDetector getDetector(){
        if(null == detector){
            detector = new MyEncryptablePropertyDetector();
        }
        return detector;
    }

    static String privateKey = "";

    static {
        privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALhXsKEb5vwFACFR/G0PrjeNytqO4ixhg2XgGKbKqPn+mcpLScVdIFtANKutXc860hF4LcjG3DbXVJcBHiQ8YNh/kVLDWPGkHZJdviiHNohRUS+6+fAiWZBpCCz+w7QFqZWX+MHX26ls3AobdD3b6uga7ba09VhkchvlYjm+hpsrAgMBAAECgYEAgk2Rz7/+XURXnDXsvVapKiGzMxxuqTpgfAGUy0lTeirKoKDpS3YRag8PbA7G0bXdSXXuvw46GRXKeFbU7H41YYA3y7pulHaXHwvaKhXwM48ly5He4dv3UBDFGeCWoX4mdftaGYBy4YkW8G871PAoFLBMlJ8bga7yNXSy9sdki+ECQQDgzGnGRK3xytzAxgFaOCHBWS71hWCm0ol9M3J6ZgFJFEvsIn45QDfvT62l5vQHk5YzJlI2hCXAtgdbCsCUCjLxAkEA0e3JmqxBxFoM2Xf+8dQ2CP1xPH9mF6KoYQSho9LehCZul7c5dDJ9hWSQdaOL//su0rkAueGI94uUj2xnYA932wJARfT3Od9nrmZuOoOkKhprzsrdRUTjpibA6LiY+KRD5CUv12WImHJ2tb+7D3UHHlB8nqBfV13ajdVYBqoa8jcWUQJADgr6EX60Z44eje2ZAFRRyQgrKwDas3oOCW9l4K+acvyfKUb4cO1vuByiDTyDgPwshawagFS19NlBdtapk6N1QQJAGv1fuXlpIuyrf4nA1hqNuoDnFiriWe/Wb1YCZHtOMuFGOo5gPDeH86y7mwny9jG3+5BqAHZ0cpmk3yQqEktI6w==";
    }

    @Override
    public String resolvePropertyValue(String s) {
        EncryptablePropertyDetector encryptablePropertyDetector = getDetector();
        try {
            if (encryptablePropertyDetector.isEncrypted(s)) {
                String s1 = encryptablePropertyDetector.unwrapEncryptedValue(s);
                String str = RSAUtils.decrypt(s1, privateKey);
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
