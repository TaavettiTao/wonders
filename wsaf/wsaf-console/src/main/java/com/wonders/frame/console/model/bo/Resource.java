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
 * AfResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_resource")
@ShowInView(name="资源信息")
public class Resource implements IDefaultModel{

	// Fields
	@ShowInView(noUse=true)
	private static final long serialVersionUID = -7136554851672514278L;
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@ShowInView(name="名称")
	private String name;
	@ShowInView(name="类型",operateType=OperateType.SELECT,ccateType="resourceType")
	private String type;
	@ShowInView(name="资源路径")
	private String path;
	@ShowInView(name="描述")
	private String description;	
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;

	// Constructors

	/** default constructor */
	public Resource() {
		this.removed=0;
	}

	/** full constructor */
	public Resource(String name, String type, String path, String description,Integer removed) {
		this.name = name;
		this.type = type;
		this.path = path;
		this.description = description;
		this.removed = removed;
	}

	// Property accessors
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
//	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_resource",allocationSize=1)
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

	@Column(name = "TYPE", length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "PATH", length = 200)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return this.description;
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