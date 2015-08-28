package com.wonders.frame.core.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;



/**
 * AfUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_user")
//@Access(AccessType.PROPERTY)
@ShowInView(name="用户信息")
//@Matches(field = "mobile1", verifyField = "mobile2",  message = "两次密码不一致")  
public class Ejb2 implements IDefaultModel{

	@ShowInView(noUse=true)
	private static final long serialVersionUID = -5398255942794045747L;
	// Fields
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	
	@ShowInView(name="姓名")
	@NotEmpty(message="用户名不能为空")
	private String name;
	
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;	
	public Ejb2(){
		this.removed=0;
	}

	/** full constructor */
	public Ejb2(Integer id,String name) {
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