package com.rongji.rjsoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rongji.rjsoft.entity.system.SysPwdHistory;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-12-10
 */
public interface ISysPwdHistoryService extends IService<SysPwdHistory> {


    /**
     * 通过账号获取历史密码
     * @param account 账号
     * @return 历史密码
     */
    SysPwdHistory getHistoryByAccount(String account);

    /**
     * 保存密码
     * @param sysPwdHistory 密码
     * @return 保存结果
     */
    boolean saveHistory(SysPwdHistory sysPwdHistory);

    /**
     * 更新密码
     * @param sysPwdHistory 密码
     * @return 编辑结果
     */
    boolean updateHistory(SysPwdHistory sysPwdHistory);

}
