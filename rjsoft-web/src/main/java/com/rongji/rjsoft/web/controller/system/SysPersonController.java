package com.rongji.rjsoft.web.controller.system;

import com.rongji.rjsoft.ao.system.PasswordAo;
import com.rongji.rjsoft.ao.system.PersonInfoAo;
import com.rongji.rjsoft.common.annotation.LogAction;
import com.rongji.rjsoft.common.security.util.TokenUtils;
import com.rongji.rjsoft.common.util.file.FileUploadUtils;
import com.rongji.rjsoft.enums.LogTypeEnum;
import com.rongji.rjsoft.enums.OperatorTypeEnum;
import com.rongji.rjsoft.enums.ResponseEnum;
import com.rongji.rjsoft.service.ISysPersonService;
import com.rongji.rjsoft.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @description: 个人信息
 * @author: JohnYehyo
 * @create: 2021-09-09 16:25:20
 */
@Api(tags = "系统管理-个人信息")
@RestController
@RequestMapping(value = "sysPerson")
public class SysPersonController {

    @Autowired
    private ISysPersonService sysPersonService;

    @Autowired
    private TokenUtils tokenUtils;

    @Value("${file.path}")
    private String path;

    @Value("${file.avator}")
    private String avator;

    /**
     * 个人信息
     *
     * @return 个人信息
     */
    @ApiOperation(value = "个人信息查询")
    @GetMapping(value = "person")
    public Object person() {
        return sysPersonService.getPerson();
    }

    /**
     * 修改个人信息
     *
     * @param personInfoAo 个人信息
     * @return 修改结果
     */
    @ApiOperation(value = "修改个人信息")
    @PutMapping(value = "person")
    @LogAction(module = "个人信息", method = "修改个人信息", logType = LogTypeEnum.UPDATE, operatorType = OperatorTypeEnum.WEB)
    public Object update(@Valid @RequestBody PersonInfoAo personInfoAo) {
        return sysPersonService.update(personInfoAo);
    }

    /**
     * 修改密码
     *
     * @param passwordAo 参数
     * @return 修改结果
     */
    @ApiOperation(value = "修改密码")
    @PutMapping(value = "password")
    @LogAction(module = "个人信息", method = "修改密码", logType = LogTypeEnum.UPDATE,
            operatorType = OperatorTypeEnum.WEB, isRecord = false)
    public Object updatePassword(@Valid @RequestBody PasswordAo passwordAo) {
        return sysPersonService.updatePassWord(passwordAo);
    }

    /**
     * 上传头像
     *
     * @param file 头像
     * @return 上传结果
     * @throws IOException
     */
    @ApiOperation(value = "上传头像")
    @ApiImplicitParam(name = "file", value = "头像", required = true)
    @PostMapping(value = "avatar")
    @LogAction(module = "个人信息", method = "上传头像", logType = LogTypeEnum.UPLOAD, operatorType = OperatorTypeEnum.WEB)
    public Object avatar(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String avatar = FileUploadUtils.upload(path + avator, file).getFileUrl();
            return ResponseVo.response(ResponseEnum.SUCCESS, avatar);
        }
        return ResponseVo.error("请选择要上传图片");
    }

}
