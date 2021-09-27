package com.rongji.rjsoft.common.util.file;

import com.rongji.rjsoft.common.util.file.enums.FileTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description: 文件类型判断工具
 * @author: JohnYehyo
 * @create: 2021-09-24 12:33:26
 */
public class FileTypeJudgeUtils {

    public static final String IMAGE_PNG = "image/png";

    public static final String IMAGE_JPG = "image/jpg";

    public static final String IMAGE_JPEG = "image/jpeg";

    public static final String IMAGE_BMP = "image/bmp";

    public static final String IMAGE_GIF = "image/gif";


    /**
     * 将文件头转换成16进制字符串
     *
     * @param src 原生byte
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src) {

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     *
     * @param is InputStream
     * @return String
     * @throws IOException
     */
    private static String getFileContent(InputStream is) {

        byte[] b = new byte[28];

        try {
            is.read(b, 0, 28);
        } catch (IOException e) {

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }

        return bytesToHexString(b);
    }

    /**
     * 获取文件类型
     *
     * @param is 文件流
     * @return 文件类型
     * @throws IOException
     */
    public static FileTypeEnum getType(InputStream is) {

        String fileHead = getFileContent(is);
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }
        fileHead = fileHead.toUpperCase();
        FileTypeEnum[] fileTypes = FileTypeEnum.values();

        for (FileTypeEnum type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }

    /**
     * 是否为允许上传类型
     * 允许类型: pdf word excel ppt txt jpg png zip
     *
     * @param is 文件流
     * @return 结果
     */
    public static boolean isPermit(InputStream is) {

        FileTypeEnum type = getType(is);

        FileTypeEnum[] permits = {FileTypeEnum.JPEG, FileTypeEnum.PNG, FileTypeEnum.ZIP, FileTypeEnum.PDF,
                FileTypeEnum.XLS_DOC, FileTypeEnum.XLSX_DOCX};

        for (FileTypeEnum fileType : permits) {
            if (fileType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否允许为上传类型  允许类型: pdf word excel ppt txt jpg png zip
     *
     * @param file 文件
     * @return 结果
     * @throws IOException
     */
    public static boolean isPermit(MultipartFile file) throws IOException {

        FileTypeEnum type = getType(file.getInputStream());

        FileTypeEnum[] permits = {FileTypeEnum.JPEG, FileTypeEnum.PNG, FileTypeEnum.ZIP, FileTypeEnum.RAR, FileTypeEnum.PDF,
                FileTypeEnum.XLS_DOC, FileTypeEnum.XLSX_DOCX, FileTypeEnum.WAV, FileTypeEnum.AVI, FileTypeEnum.MP4, FileTypeEnum.FLV};

        for (FileTypeEnum fileType : permits) {
            if (fileType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为允许上传类型
     *
     * @param file    文件
     * @param permits 限制类型
     * @return 结果
     * @throws IOException
     */
    public static boolean isPermit(MultipartFile file, FileTypeEnum[] permits) throws IOException {

        FileTypeEnum type = getType(file.getInputStream());

        if (null == permits || permits.length == 0) {
            return true;
        }

        for (FileTypeEnum fileType : permits) {
            if (fileType.equals(type)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取文件类型
     *
     * @param prefix 文件前缀
     * @return 文件类型
     */
    public static String getExtension(String prefix) {
        switch (prefix) {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            default:
                return "";
        }
    }

}
