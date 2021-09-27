package com.rongji.rjsoft.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @description: 时间处理工具类
 * @author: JohnYehyo
 * @create: 2021-09-15 10:38:07
 */
public class TimeUtils {

    /**
     * Long时间戳转LocalDateTime
     * @param time Long时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime long2LocaDateTime(Long time){
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * LocalDateTime转Long时间戳
     * @param time LocalDateTime
     * @return Long时间戳
     */
    public static Long locaDateTime2Long(LocalDateTime time){
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = time.atZone(zoneId).toInstant();
        return instant.toEpochMilli();
    }
}
