package com.rongji.rjsoft.common.util.file;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.rongji.rjsoft.common.config.FileConfig;
import com.rongji.rjsoft.common.util.file.entity.FileVo;
import com.rongji.rjsoft.constants.Constants;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.exception.BusinessException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @description: 文件上传工具类
 * @author: JohnYehyo
 * @create: 2021-09-24 12:33:26
 */
public class FileUploadUtils {

    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = FileConfig.getPath();

    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final FileVo upload(MultipartFile file) throws IOException {
        return upload(FileConfig.getPath() + Constants.DEFAULT_PATH, file);
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final FileVo upload(String baseDir, MultipartFile file) throws IOException {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new BusinessException(ResponseEnum.SUPER_LONG_FILE);
        }

        //文件大小及类型校验
        assertAllowed(file);

        String fileName = extractFilename(file);
        String originFileName = file.getOriginalFilename();

        File desc = getAbsoluteFile(baseDir, fileName);
        file.transferTo(desc);
        String pathFileName = getPathFileName(baseDir, fileName);
        return new FileVo(originFileName, pathFileName);
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        fileName = DateUtil.format(new Date(), "yyyy/MM/dd") + "/" + UUID.fastUUID() + "." + extension;
        return fileName;
    }

    /**
     * 文件绝对地址
     *
     * @param uploadDir 上传路径
     * @param fileName  文件名
     * @return 文件绝对地址
     * @throws IOException
     */
    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 获取完整路径
     *
     * @param uploadDir 上传路径
     * @param fileName  文件名
     * @return 完整路径
     * @throws IOException
     */
    private static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = FileConfig.getPath().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     */
    public static final void assertAllowed(MultipartFile file) throws IOException {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
            throw new BusinessException(ResponseEnum.SUPER_LARGE_FILE);
        }

        if (!FileTypeJudgeUtils.isPermit(file)) {
            throw new BusinessException(ResponseEnum.NO_ALLOW_FILE);
        }

    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = FileTypeJudgeUtils.getExtension(file.getContentType());
        }
        return extension;
    }

}
