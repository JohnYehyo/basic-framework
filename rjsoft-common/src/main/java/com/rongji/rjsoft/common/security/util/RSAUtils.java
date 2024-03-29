package com.rongji.rjsoft.common.security.util;

import cn.hutool.core.codec.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: RSA加解密
 * @author: JohnYehyo
 * @create: 2021-10-09 10:40:26
 */
public class RSAUtils {

    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();

    // 用于封装随机产生的公钥与私钥
    public static void main(String[] args) throws Exception {
        // 生成公钥和私钥
        genKeyPair();
        // 加密字符串
        String username = "root";
        String password = "hky4yhl9t";
        String redisPassword = "123456";
        String rabbitmqUsername = "admin";
        String rabbitmqPassword = "Rjsoft@2022";
        System.out.println("随机生成的公钥为:" + keyMap.get(0));
        System.out.println("随机生成的私钥为:" + keyMap.get(1));
        System.out.println();
        String messageUsername = encrypt(username, keyMap.get(0));
        String messagePassword = encrypt(password, keyMap.get(0));
        String messageRedisPassword = encrypt(redisPassword, keyMap.get(0));
        String messageRabbitmqUsername = encrypt(rabbitmqUsername, keyMap.get(0));
        String messageRabbitmqPassword = encrypt(rabbitmqPassword, keyMap.get(0));
        System.out.println(username + "\t加密后的字符串为:" + messageUsername);
        System.out.println(password + "\t加密后的字符串为:" + messagePassword);
        System.out.println(redisPassword + "\t加密后的字符串为:" + messageRedisPassword);
        System.out.println(rabbitmqUsername + "\t加密后的字符串为:" + messageRabbitmqUsername);
        System.out.println(rabbitmqPassword + "\t加密后的字符串为:" + messageRabbitmqPassword);
        String messageDeUsername = decrypt(messageUsername, keyMap.get(1));
        String messageDePassword = decrypt(messagePassword, keyMap.get(1));
        String messageDeRedisPassword = decrypt(messageRedisPassword, keyMap.get(1));
        String messageDeRabbitmqUsername = decrypt(messageRabbitmqUsername, keyMap.get(1));
        String messageDeRabbitmqPassword = decrypt(messageRabbitmqPassword, keyMap.get(1));
        System.out.println("还原后的字符串为:" + messageDeUsername);
        System.out.println("还原后的字符串为:" + messageDePassword);
        System.out.println("还原后的字符串为:" + messageDeRedisPassword);
        System.out.println("还原后的字符串为:" + messageDeRabbitmqUsername);
        System.out.println("还原后的字符串为:" + messageDeRabbitmqPassword);
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(2048, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 得到公钥
        String publicKeyString = Base64.encode(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.encode((privateKey.getEncoded()));
        // 将公钥和私钥保存到Map
        // 0表示公钥
        keyMap.put(0, publicKeyString);
        // 1表示私钥
        keyMap.put(1, privateKeyString);
    }

    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = Base64.decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA-1AndMGF1Padding", new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encode(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.decode(str.getBytes("UTF-8"));
        // base64编码的私钥
        byte[] decoded = Base64.decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA-1AndMGF1Padding", new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
