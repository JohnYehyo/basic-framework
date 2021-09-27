package com.rongji.rjsoft.web.controller.commom;

import com.rongji.rjsoft.ao.common.FileUploadAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.common.util.file.FileUploadUtils;
import com.rongji.rjsoft.common.util.file.entity.FileVo;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 文件上传
 * @author: JohnYehyo
 * @create: 2021-09-24 12:59:19
 */
@Api(tags = "系统管理-文件上传")
@RestController
@RequestMapping(value = "upload")
public class FileUploadController {

    @Value("${file.path}")
    private String path;


    /**
     * 文件上传
     * @param file 文件
     * @param fileUploadAo 上传目标信息
     * @return 上传结果
     */
    @ApiOperation(value = "文件上传")
    @PostMapping
    @LogAction(module = "文件上传", method = "上传文件", logType = LogTypeEnum.UPLOAD, operatorType = OperatorTypeEnum.WEB)
    public Object upload(@RequestParam("file") MultipartFile file, @Valid FileUploadAo fileUploadAo) throws IOException {
        FileVo fileVo = FileUploadUtils.upload(path + File.separator +fileUploadAo.getBusinessType().toLowerCase(), file);
        return ResponseVo.response(ResponseEnum.SUCCESS, fileVo);
    }


    /**
     * 多文件上传
     * @param files 文件
     * @param fileUploadAo 上传目标信息
     * @return 上传结果
     */
    @ApiOperation(value = "多文件上传")
    @PostMapping(value = "batch")
    @LogAction(module = "文件上传", method = "多上传文件", logType = LogTypeEnum.UPLOAD, operatorType = OperatorTypeEnum.WEB)
    public Object upload(@RequestParam("files") MultipartFile[] files, @Valid FileUploadAo fileUploadAo) throws IOException {
        List<FileVo> list = new ArrayList<>();
        FileVo fileVo;
        for (int i = 0; i < files.length; i++) {
            fileVo = FileUploadUtils.upload(path + File.separator +fileUploadAo.getBusinessType().toLowerCase(), files[i]);
            list.add(fileVo);
        }
        return ResponseVo.response(ResponseEnum.SUCCESS, list);
    }
}
