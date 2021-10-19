package com.rongji.rjsoft.entity.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 公共附件表
 * </p>
 *
 * @author JohnYehyo
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysCommonFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业务表id
     */
    private Long tableId;

    /**
     * 业务类型
     */
    private String tableType;

    /**
     * 附件类型(区分同一记录中的多类型附件)
     */
    private Boolean fileType;

    /**
     * 附件路径
     */
    private String fileUrl;

    /**
     * 附件名称
     */
    private String fileName;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
