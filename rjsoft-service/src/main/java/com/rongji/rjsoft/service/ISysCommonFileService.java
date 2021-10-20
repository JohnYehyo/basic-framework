package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.entity.common.SysCommonFile;
import com.rongji.rjsoft.query.common.SysCommonFileQuery;
import com.rongji.rjsoft.vo.common.SysCommonFileVo;

import java.util.List;

/**
 * <p>
 * 公共附件表 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-10-19
 */
public interface ISysCommonFileService extends IService<SysCommonFile> {

    /**
     * 附件查询
     * @param sysCommonFileQuery 查询对象
     * @return 附件列表
     */
    List<SysCommonFileVo> getFiles(SysCommonFileQuery sysCommonFileQuery);

}
