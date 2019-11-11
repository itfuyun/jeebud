package com.jeebud.module.upms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
public class MenuVO implements Serializable {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Long pid;
    private String name;
    private String icon;
    private String url;
    private List<MenuVO> subMenus;
}
