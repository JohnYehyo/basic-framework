package com.rongji.rjsoft.common.security.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @description: AES加解密
 * @author: JohnYehyo
 * @create: 2021-10-08 14:49:47
 */
public class AESUtils {

    private static String iv = "0123456789ABCDEF";
    private static String Algorithm = "AES";
    private static String AlgorithmProvider = "AES/ECB/PKCS5Padding";

    public AESUtils() {
    }

    public static byte[] generatorKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Algorithm);
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    public static IvParameterSpec getIv() throws UnsupportedEncodingException {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));
        return ivParameterSpec;
    }

    public static byte[] encrypt(String src, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        SecretKey secretKey = new SecretKeySpec(key, Algorithm);
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(1, secretKey);
        byte[] cipherBytes = cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
        return cipherBytes;
    }

    public static byte[] decrypt(String src, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, Algorithm);
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(2, secretKey);
        byte[] hexBytes = hexStringToBytes(src);
        byte[] plainBytes = cipher.doFinal(hexBytes);
        return plainBytes;
    }

    public static String byteToHexString(byte[] src) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < src.length; ++i) {
            int v = src[i] & 255;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append("0");
            }

            sb.append(hv);
        }

        return sb.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] b = new byte[length];

        for (int i = 0; i < length; ++i) {
            int pos = i * 2;
            b[i] = (byte) (charToByte(hexChars[pos]) << 4 | (charToByte(hexChars[pos + 1]) & 0xff));
        }

        return b;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) {
        try {
            byte[] key = "113813559663B1D1".getBytes("utf-8");
            String username = "admin";
            String password = "123456";
            System.out.println("密钥:" + byteToHexString(key));
            System.out.println("username:" + username);
            System.out.println("password:" + password);
            String enc1 = byteToHexString(encrypt(username, key));
            String enc2 = byteToHexString(encrypt(password, key));
            System.out.println("username加密：" + enc1);
            System.out.println("password加密：" + enc2);
            System.out.println("username解密：" + new String(decrypt(enc1, key), "utf-8"));
            System.out.println("password解密：" + new String(decrypt(enc2, key), "utf-8"));
        } catch (InvalidKeyException var4) {
            var4.printStackTrace();
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        } catch (NoSuchPaddingException var6) {
            var6.printStackTrace();
        } catch (IllegalBlockSizeException var7) {
            var7.printStackTrace();
        } catch (BadPaddingException var8) {
            var8.printStackTrace();
        } catch (UnsupportedEncodingException var9) {
            var9.printStackTrace();
        } catch (Exception var10) {
            var10.printStackTrace();
        }
    }

}
