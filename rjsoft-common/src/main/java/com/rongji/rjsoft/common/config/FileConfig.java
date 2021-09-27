package com.rongji.rjsoft.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "file")
public class FileConfig {

    @Value("${file.path}")
    private void setPath(String path){
        FileConfig.path = path;
    }

    @Value("${file.avator}")
    private void setAvator(String avator){
        FileConfig.avator = avator;
    }

    @Value("${file.download}")
    private void setDownload(String download){
        FileConfig.download = download;
    }

    @Value("${file.upload}")
    private void setUpload(String upload){
        FileConfig.upload = upload;
    }



    /**
     * 上传路径
     */
    private static String path;

    /**
     * 头像上传
     */
    private static String avator;

    /**
     * 下载
     */
    private static String download;

    /**
     * 上传
     */
    private static String upload;

    public static String getPath() {
        return path;
    }

    public static String getAvator() {
        return avator;
    }

    public static String getDownload() {
        return download;
    }

    public static String getUpload() {
        return upload;
    }
}
