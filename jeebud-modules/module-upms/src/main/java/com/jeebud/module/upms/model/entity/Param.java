package com.jeebud.module.upms.model.entity;

import com.jeebud.core.data.jpa.domain.AbstractDO;
import lombok.Data;

import javax.persistence.*;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
@Entity
@Table(name = "sys_param")
public class Param extends AbstractDO {
	
	/**
	* 参数名称
	*/
	@Column(name = "param_name")
	private String paramName;

	/**
	* 参数键
	*/
	@Column(name = "param_key")
	private String paramKey;

	/**
	* 参数值
	*/
	@Column(name = "param_value")
	private String paramValue;

	/**
	* 参数组
	*/
	@Column(name = "param_group")
	private String paramGroup;

	/**
	* 类型
	*/
	@Column(name = "type")
	private Integer type;

	/**
	 * 备注
	 */
	@Column(name = "remarks")
	private String remarks;
}
