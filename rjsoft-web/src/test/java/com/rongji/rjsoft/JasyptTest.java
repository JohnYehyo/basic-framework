package com.rongji.rjsoft;

import com.rongji.rjsoft.common.security.util.RSAUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Assert;
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
        String originName = "root";
        String originPassword = "root";
        String originRedisPassword = "root";
        String name = standardPBEStringEncryptor.encrypt(originName);
        String password = standardPBEStringEncryptor.encrypt(originPassword);
        String redisPassword = standardPBEStringEncryptor.encrypt(originRedisPassword);
        //将加密的文本写到配置文件中
        System.out.println("name:" + name);
        System.out.println("password:" + password);
        System.out.println("redisPassword:" + redisPassword);

        //要解密的文本
        String name2 = standardPBEStringEncryptor.decrypt(name);
        String password2 = standardPBEStringEncryptor.decrypt(password);
        String redisPassword2 = standardPBEStringEncryptor.decrypt(redisPassword);
        //解密后的文本
        System.out.println("name2:" + name2);
        System.out.println("password2:" + password2);
        System.out.println("redisPassword2:" + redisPassword2);

        Assert.assertEquals(originName, name2);
        Assert.assertEquals(originPassword, password2);
        Assert.assertEquals(originRedisPassword, redisPassword2);

    }

    @Test
    public void RSAencryptPwd() throws Exception {
        // 生成公钥和私钥
        RSAUtils.genKeyPair();
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwfSaV9T9ua17a7I/vgO2pfJ/Bk4wZI/PRhXib7swfAe4g+TEMGg1ct1f5UPjAiu5WF3s23AuocoWFM7DEiqG/T5ZahyArEX99aGttBGYMNBZHI0m2IdIJHKIB1zg3MI+KuXEKJU4cEnE2Vi5Wu+Xl6xzmsr2vGWzEqA/hdFQuowIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALB9JpX1P25rXtrsj++A7al8n8GTjBkj89GFeJvuzB8B7iD5MQwaDVy3V/lQ+MCK7lYXezbcC6hyhYUzsMSKob9PllqHICsRf31oa20EZgw0FkcjSbYh0gkcogHXODcwj4q5cQolThwScTZWLla75eXrHOayva8ZbMSoD+F0VC6jAgMBAAECgYAwBg3+1hopiKvp9F8CM9n3uYKbIPQJT0AkLiZwf90pPtQ2Qrh264vhQ757h+34LpXF9WPAvMWrU2ZDpZofEP4L5QkHiSpJdfxg1j6pT5IwXy56TtC+KvsZrrQV1FWngVB4LQILywe81DG+L6ARBVdhfUlrXIOqRgYA3nk64nBKYQJBAPIulFK+ofOLo0YnhNnzeuH/vtIExLwnqkqm9vZ0m4joKd/JbKH3NBrR/jRofuZP721PJI91L2YnSkqmOG+q0DECQQC6jwcwUiqVLTCsdSb/ADpHS382QG+Da6CzNLquwdvlUzSdRdCS+rBiVPQJ98pn+fq7JU18mifKz29cnywLdqsTAkEAm6bWS4wcOJSRvsAdgZscam5drUvFSgzQRSSka2gsosRcTWl++WYRRf+bOwHCNbJAdue73rv5V9V+yRWmBhcpsQJAFTf7W1WdHkZ7iMHyY4KFHmLJ30PI6yuspXVT3liSSC0EiPbN2EnHEggTkaarUNQv3mVJwOhPqlOZmPFJsywWHQJACKjD9GP0ZuU0SMiIdH9uQ6q1Jc9N+2DE4tbQu78qfHeBhfNrflgKUb/UIxQccN/GcG0zth14nnARYg84+XdkOA==";
        // 加密字符串
        String username = "root";
        String password = "hky4yhl9t";
        String redisPassword = "123456";
        String messageUsername = RSAUtils.encrypt(username, publicKey);
        String messagePassword = RSAUtils.encrypt(password, publicKey);
        String messageRedisPassword = RSAUtils.encrypt(redisPassword, publicKey);
        System.out.println(username + "\t加密后的字符串为:" + messageUsername);
        System.out.println(password + "\t加密后的字符串为:" + messagePassword);
        System.out.println(redisPassword + "\t加密后的字符串为:" + messageRedisPassword);
        String messageDeUsername = RSAUtils.decrypt(messageUsername, privateKey);
        String messageDePassword = RSAUtils.decrypt(messagePassword, privateKey);
        String messageDeRedisPassword = RSAUtils.decrypt(messageRedisPassword, privateKey);
        System.out.println("还原后的字符串为:" + messageDeUsername);
        System.out.println("还原后的字符串为:" + messageDePassword);
        System.out.println("还原后的字符串为:" + messageDeRedisPassword);

        Assert.assertEquals(username, messageDeUsername);
        Assert.assertEquals(password, messageDePassword);
        Assert.assertEquals(redisPassword, messageDeRedisPassword);
    }
}
