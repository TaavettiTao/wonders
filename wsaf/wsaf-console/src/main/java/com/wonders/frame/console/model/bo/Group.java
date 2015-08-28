package com.wonders.frame.console.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import javax.persistence.Id;
import javax.persistence.Table;

import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;



/**
 * AfGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_group")
@ShowInView(name="分组信息")
public class Group implements IDefaultModel{

	@ShowInView(noUse=true)
	private static final long serialVersionUID = 5870113644983432612L;
	// Fields
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@ShowInView(name="组名")
	private String name;
	@ShowInView(name="类型",operateType=OperateType.SELECT,ccateType="groupType")
	private String type;
	@ShowInView(name="描述")
	private String description;
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;

	
	// Constructors

	/** default constructor */
	public Group() {
		this.removed=0;
	}

	/** full constructor */
	public Group(String name, String type, Integer removed) {
		this.name = name;
		this.type = type;
		this.removed = removed;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_group",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 50, nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "TYPE", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "REMOVED", nullable = false)
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

}