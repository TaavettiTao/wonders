package com.wonders.frame.complex.model.bo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;


/**
 * AfRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_role_old", catalog = "wsaf")
@Access(AccessType.PROPERTY)
@ShowInView(name="角色信息")
public class RoleOld implements java.io.Serializable {

	// Fields
	@ShowInView(noUse=true)
	private static final long serialVersionUID = -2817970472471260674L;

	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@NotEmpty(message="名称不能为空")
	@ShowInView(name="角色名")
	private String name;
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;

	private Date operateDate;
	private Integer groupOldID;
	// Constructors

	/** default constructor */
	public RoleOld() {
		this.removed=0;
	}

	/** full constructor */
	public RoleOld(String name, Integer removed) {
		this.name = name;
		this.removed = removed;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_role_old",allocationSize=1)
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

	@Column(name = "OPERATE_DATE", nullable = false)
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "GROUPOLD_ID")
	public Integer getGroupOldID() {
		return groupOldID;
	}

	public void setGroupOldID(Integer groupOldID) {
		this.groupOldID = groupOldID;
	}

}