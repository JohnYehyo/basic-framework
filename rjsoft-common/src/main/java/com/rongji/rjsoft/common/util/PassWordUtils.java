package com.rongji.rjsoft.common.util;

/**
 * @description: 随机生成密码工具类
 * @author: JohnYehyo
 * @create: 2021-12-09 10:49:37
 */
public class PassWordUtils {

    private static String UPPERCASE_LETTER = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static String LOWER_LETTER = "qwertyuiopasdfghjklzxcvbnm";
    private static String NUMBER = "1234567890";
    private static String SPECIAL_SYMBOL = "~!@#$%^&_*-";

    public static String passRandom(int num) {
        String passWord = "";
        if (num < 8) {
            return passWord;
        }

        int length1 = (int) (Math.random() * 2 + 1);
        int length2 = (int) (Math.random() * 2 + 1);
        int length3 = (int) (Math.random() * 2 + 1);
        int length4 = num - length1 - length2 - length3;
        passWord = getString(passWord, length1, SPECIAL_SYMBOL);
        passWord = getString(passWord, length2, NUMBER);
        passWord = getString(passWord, length3, UPPERCASE_LETTER);
        passWord = getString(passWord, length4, LOWER_LETTER);
        return passWord;
    }

    private static String getString(String textString, int length, String characterPool) {
        StringBuffer buffer = new StringBuffer(characterPool);
        for (int i = 0; i < length; i++) {
            int ran = (int) (Math.random() * characterPool.length());
            textString += buffer.charAt(ran);
        }
        return textString;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(passRandom(8));
        }
    }

}
