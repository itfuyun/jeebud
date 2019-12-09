package com.jeebud.module.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeebud.core.data.jpa.domain.AbstractDO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Data
@Entity
@Table(name = "cms_article")
public class Article extends AbstractDO {
	
	/**
	* 标题
	*/
	@Column(name = "title")
	private String title;

	/**
	* 内容
	*/
	@Column(name = "content")
	private String content;

	/**
	* 封面
	*/
	@Column(name = "image")
	private String image;

	/**
	* 栏目ID
	*/
	@Column(name = "column_id")
	private Integer columnId;

	/**
	* 创建时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Column(name = "create_time",updatable = false)
	private Date createTime;


	/**
	* 更新时间
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Column(name = "update_time",insertable = false)
	private Date updateTime;

	/**
	* 摘要
	*/
	@Column(name = "summary")
	private String summary;

}
