package com.jeebud.module.upms.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class TreeVO implements Serializable {
    private Long id;
    @JsonProperty("pId")
    private Long pid;
    private String name;
    private Boolean checked;
    private Boolean open;
}
