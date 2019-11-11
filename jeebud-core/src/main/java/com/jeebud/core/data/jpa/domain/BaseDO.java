package com.jeebud.core.data.jpa.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
@MappedSuperclass
public class BaseDO extends AbstractDO {
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time",updatable = false)
    private Date createTime;
    /**
     * 创建人
     */
    @Column(name = "create_by",updatable = false)
    private String createBy;
    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "update_time",insertable = false)
    private Date updateTime;
    /**
     * 修改人
     */
    @Column(name = "update_by",insertable = false)
    private String updateBy;


}
