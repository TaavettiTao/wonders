package com.wonders.frame.console.model.bo;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;


/**
 * AfRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_role")
@Access(AccessType.PROPERTY)
@ShowInView(name="角色信息")
public class Role implements IDefaultModel{

	// Fields
	@ShowInView(noUse=true)
	private static final long serialVersionUID = 2066627045100603957L;
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@NotEmpty(message="名称不能为空")
	@ShowInView(name="角色名")
	private String name;
	@ShowInView(name="描述")
	private String description;	
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;

	// Constructors

	/** default constructor */
	public Role() {
		this.removed=0;
	}

	/** full constructor */
	public Role(String name, String description,Integer removed) {
		this.name = name;
		this.description = description;
		this.removed = removed;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_role",allocationSize=1)
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