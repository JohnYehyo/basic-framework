package com.rongji.rjsoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rongji.rjsoft.entity.monitor.SysLoginInfo;
import com.rongji.rjsoft.query.monitor.login.LoginInfoQuery;
import com.rongji.rjsoft.vo.monitor.login.LoginInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统访问记录 Mapper 接口
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-09-09
 */
public interface SysLoginInfoMapper extends BaseMapper<SysLoginInfo> {

    /**
     * 查询登录信息分页结果
     *
     * @param pages          分页对象
     * @param loginInfoQuery 查询对象
     * @return 分页结果
     */
    IPage<LoginInfoVo> getPages(IPage<LoginInfoVo> pages, @Param("param") LoginInfoQuery loginInfoQuery);
}
