package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rongji.rjsoft.entity.common.SysCommonFile;
import com.rongji.rjsoft.query.common.SysCommonFileQuery;
import com.rongji.rjsoft.vo.common.SysCommonFileVo;

import java.util.List;

/**
 * <p>
 * 公共附件表 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-10-19
 */
public interface SysCommonFileMapper extends BaseMapper<SysCommonFile> {

    /**
     * 附件查询
     * @param sysCommonFileQuery 查询对象
     * @return 附件列表
     */
    List<SysCommonFileVo> getFiles(SysCommonFileQuery sysCommonFileQuery);
}
