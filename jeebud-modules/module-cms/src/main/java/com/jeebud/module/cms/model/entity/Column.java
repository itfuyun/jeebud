package com.jeebud.module.cms.model.entity;

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
@Table(name = "cms_column")
public class Column extends AbstractDO {
	
	/**
	* 栏目名称
	*/
	@javax.persistence.Column(name = "name")
	private String name;

	/**
	* 备注
	*/
	@javax.persistence.Column(name = "remarks")
	private String remarks;

}
