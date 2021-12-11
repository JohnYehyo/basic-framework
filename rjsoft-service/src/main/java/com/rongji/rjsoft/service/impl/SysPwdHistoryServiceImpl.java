package com.rongji.rjsoft.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rongji.rjsoft.entity.system.SysPwdHistory;
import com.rongji.rjsoft.mapper.SysPwdHistoryMapper;
import com.rongji.rjsoft.service.ISysPwdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-12-10
 */
@Service
public class SysPwdHistoryServiceImpl extends ServiceImpl<SysPwdHistoryMapper, SysPwdHistory> implements ISysPwdHistoryService {

    @Autowired
    private SysPwdHistoryMapper sysPwdHistoryMapper;

    @Value("${JohnYehyo.pwd_history}")
    private int PWD_HISTORY;

    /**
     * 通过账号获取历史密码
     *
     * @param account 账号
     * @return 历史密码
     */
    @Override
    public SysPwdHistory getHistoryByAccount(String account) {
        LambdaQueryWrapper<SysPwdHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPwdHistory::getAccount, account).last(" limit 0, 1");
        return sysPwdHistoryMapper.selectOne(wrapper);
    }

    /**
     * 保存密码
     * @param sysPwdHistory 密码
     * @return 保存结果
     */
    @Override
    public boolean saveHistory(SysPwdHistory sysPwdHistory) {
        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("history", sysPwdHistory.getHistory());
        ja.add(jo);
        sysPwdHistory.setHistory(ja.toString());
        return sysPwdHistoryMapper.insert(sysPwdHistory) > 0;
    }

    /**
     * 更新密码
     *
     * @param sysPwdHistory 密码
     * @return 编辑结果
     */
    @Override
    public boolean updateHistory(SysPwdHistory sysPwdHistory) {
        SysPwdHistory history = getHistoryByAccount(sysPwdHistory.getAccount());
        if (null == history) {
            return saveHistory(sysPwdHistory);
        }

        JSONArray ja = JSONArray.parseArray(history.getHistory());


        if (ja.size() >= PWD_HISTORY) {
            for (int i = 0; i <= (ja.size() - PWD_HISTORY); i++) {
                ja.remove(i);
            }
        }

        JSONObject jo = new JSONObject();
        jo.put("history", sysPwdHistory.getHistory());
        ja.add(jo);
        sysPwdHistory.setHistory(ja.toString());
        LambdaUpdateWrapper<SysPwdHistory> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysPwdHistory::getAccount, sysPwdHistory.getAccount());
        return sysPwdHistoryMapper.update(sysPwdHistory, wrapper) > 0;
    }

}
