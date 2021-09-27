package com.rongji.rjsoft.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.rongji.rjsoft.common.security.util.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @description: mybaitsplus自动填充
 * @author: JohnYehyo
 * @create: 2021-09-01 14:14:50
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getUserName());
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateBy", String.class, SecurityUtils.getUserName());
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
