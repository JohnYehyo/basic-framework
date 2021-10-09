package com.rongji.rjsoft;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description: Jasypt加解密
 * @author: JohnYehyo
 * @create: 2021-10-09 09:42:25
 */
@SpringBootTest
public class JasyptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    private static final String ALGORITHM_INFO = "PBEWithMD5AndDES";

    private static final String PASSWORD_INFO = "JohnYehyo";

    @Test
    public void encryptPwd() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        //配置文件中配置如下的算法
        standardPBEStringEncryptor.setAlgorithm(ALGORITHM_INFO);
        //配置文件中配置的password
        standardPBEStringEncryptor.setPassword(PASSWORD_INFO);
        //要加密的文本
        String name = standardPBEStringEncryptor.encrypt("root");
        String password = standardPBEStringEncryptor.encrypt("123456");
        String redisPassword = standardPBEStringEncryptor.encrypt("123456");
        //将加密的文本写到配置文件中
        System.out.println("name=" + name);
        System.out.println("password=" + password);
        System.out.println("redisPassword=" + redisPassword);

//        //要解密的文本
//        String name2 = standardPBEStringEncryptor.decrypt("FarrmxSQX5uwtH/NZRxy+g==");
        String password2 = standardPBEStringEncryptor.decrypt("8eznrLiq/wsswrcDscdmR/TRF/Mvo7Cv");
//        String redisPassword2 = standardPBEStringEncryptor.decrypt("ZII7UphhbVuJ8c3oxPUeyw==");
//        //解密后的文本
//        System.out.println("name2=" + name2);
        System.out.println("password2=" + password2);
//        System.out.println("redisPassword2=" + redisPassword2);

    }
}
