package com.wonders.frame.core.model.bo;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;

/**
 * RelationRuleType entity. @author MyEclipse Persistence Tools
 */
@Entity
@ShowInView(name="规则类型")
@Table(name = "AF_RULE_TYPE")
public class RuleType implements IDefaultModel{

	/**
	 * 
	 */
	//Fields
	@ShowInView(noUse=true)
	private static final long serialVersionUID = 1828291678489761322L;
	// Fields
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@NotEmpty(message="名称不能为空")
	@ShowInView(name="规则类型名称")
	private String name;
		@ShowInView(name="关联对象ID",operateType=OperateType.HIDDEN)
	private String objIds;
	@ShowInView(name="关联对象名称")
	private String objTypes;
		@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;

	// Constructors

	/** default constructor */
	public RuleType() {
		this.removed = 0;
	}

	/** minimal constructor */
	public RuleType(String name) {
		this.name = name;
		this.removed = 0;
	}

	/** full constructor */
	public RuleType(String name, String objIds,String objTypes) {
		this.name = name;
		this.objIds = objIds;
		this.objTypes = objTypes;		
		this.removed = 0;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Generator")
	@SequenceGenerator(name="Generator",sequenceName="seq_af_rule_type")
	@Column(name="ID",unique=true,nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "OBJ_IDS")
	public String getObjIds() {
		return this.objIds;
	}

	public void setObjIds(String objIds) {
		this.objIds = objIds;
	}
	
	@Column(name = "OBJ_TYPES")
	public String getObjTypes() {
		return this.objTypes;
	}

	public void setObjTypes(String objTypes) {
		this.objTypes = objTypes;
	}

	@Column(name = "REMOVED", nullable = false, precision = 22, scale = 0)
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

}