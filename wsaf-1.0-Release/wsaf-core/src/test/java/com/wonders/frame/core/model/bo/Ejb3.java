package com.wonders.frame.core.model.bo;

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
 * AfPrivilege entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_privilege")
@ShowInView(name = "权限信息")
public class Ejb3 implements IDefaultModel {

	// Fields
	@ShowInView(noUse = true)
	private static final long serialVersionUID = 2630531273755056854L;
	@ShowInView(name = "ID", operateType = OperateType.HIDDEN)
	private Integer id;
	@ShowInView(name = "名称")
	private String name;
	@ShowInView(name = "逻辑删除标志位", operateType = OperateType.HIDDEN)
	private Integer removed;
	public Ejb3(){
		this.removed=0;
	}

	/** full constructor */
	public Ejb3(Integer id,String name) {
		this.id=id;
		this.name = name;
		this.removed = 0;
	}

	// Property accessors
	@Id
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

	@Column(name = "REMOVED", nullable = false)
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

}