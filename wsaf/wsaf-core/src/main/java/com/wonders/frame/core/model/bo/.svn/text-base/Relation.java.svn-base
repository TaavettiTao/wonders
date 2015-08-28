package com.wonders.frame.core.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;

/**
 * AfObjRelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_relation")
@ShowInView(name="关联信息")
public class Relation implements IDefaultModel{

	// Fields
	@ShowInView(noUse=true)
	private static final long serialVersionUID = -4049405238385014656L;
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@ShowInView(name="上级联对象")
	private String ptype;
	@ShowInView(name="上级联对象ID")
	private Integer pid;
	@ShowInView(name="下级联对象")
	private String ntype;
	@ShowInView(name="下级联对象ID")
	private Integer nid;
	
	/**2014-12-12:taoweiwei*/
	//配置规则
	@ShowInView(name="配置规则ID")
	private Integer ruleTypeId;
	
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;

	// Constructors

	/** default constructor */
	public Relation() {
		this.removed=0;
	}
	public Relation(String ptype, Integer pid, String ntype, Integer nid,Integer ruleTypeId) {
		this.ptype = ptype;
		this.pid = pid;
		this.ntype = ntype;
		this.nid = nid;
		this.ruleTypeId = ruleTypeId;
		this.removed = 0;
	}
	
	/** full constructor */
	public Relation(String ptype, Integer pid, String ntype, Integer nid,
			Integer ruleTypeId, Integer removed) {
		this.ptype = ptype;
		this.pid = pid;
		this.ntype = ntype;
		this.nid = nid;
		this.ruleTypeId = ruleTypeId;
		this.removed = removed;
	}
	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_relation",allocationSize=1)   
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "P_TYPE", length = 20, nullable = false)
	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	@Column(name = "P_ID", nullable = false)
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "N_TYPE", length = 20, nullable = false)
	public String getNtype() {
		return ntype;
	}

	public void setNtype(String ntype) {
		this.ntype = ntype;
	}

	@Column(name = "N_ID", nullable = false)
	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}
	
	@Column(name="RULE_TYPE_ID",nullable=false)
	public Integer getRuleTypeId() {
		return ruleTypeId;
	}
	
	public void setRuleTypeId(Integer ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}

	@Column(name = "REMOVED", nullable = false)
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

}