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
 * AfOrgan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_organ")
@ShowInView(name="组织机构信息")
public class Organ implements IDefaultModel{

	// Fields

	@ShowInView(noUse=true)
	private static final long serialVersionUID = 4219037048094299330L;
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@ShowInView(name="名称")
	private String name;
	@ShowInView(name="类型",operateType=OperateType.SELECT,ccateType="organType")
	private String type;
	@ShowInView(name="电话")
	private String telephone;
	@ShowInView(name="描述")
	private String description;
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;
	@ShowInView(name="联系人")
	private String contacts;	
	@ShowInView(name="移动电话")
	private String mobile;
	@ShowInView(name="地址")
	private String address;		
	// Constructors

	/** default constructor */
	public Organ() {
		this.removed=0;
	}

	/** full constructor */
	public Organ(String name,String type,String contacts,String telephone,String mobile,String address, String description, Integer removed) {
		this.name = name;
		this.type = type;
		this.contacts = contacts;
		this.telephone = telephone;
		this.mobile = mobile;
		this.address = address;
		this.description = description;
		this.removed = removed;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_organ",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TYPE", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "NAME", length = 50, nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "TELEPHONE", length = 20)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	@Column(name = "CONTACTS", length = 30)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	@Column(name = "MOBILE", length = 20)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name = "ADDRESS", length = 200)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}