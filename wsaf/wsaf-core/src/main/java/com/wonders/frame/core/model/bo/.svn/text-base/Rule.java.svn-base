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
 * 
 * @author taoweiwei
 *
 */
@Entity
@Table(name="af_rule")
@ShowInView(name="关联规则配置")
public class Rule implements IDefaultModel{

	//Fields
	@ShowInView(noUse=true)
	private static final long serialVersionUID = 1L;
	
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@ShowInView(name="上级联对象")
	private String pobjType;
	@ShowInView(name="下级联对象")
	private String nobjType;
	@ShowInView(name="上级联对象ID")
	private Integer pobjId;
	@ShowInView(name="下级联对象ID")
	private Integer nobjId;
	@ShowInView(name="规则类型ID")
	private Integer ruleTypeId;
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;
	
	//Constructors
	/**default constructor*/
	public Rule() {
		this.removed=0;
	}

	/**full constructor*/
	public Rule(String pobjType, String nobjType, Integer pobjId, Integer nobjId, Integer ruleTypeId, Integer removed) {
		this.pobjType = pobjType;
		this.nobjType = nobjType;
		this.pobjId = pobjId;
		this.nobjId= nobjId;
		this.ruleTypeId = ruleTypeId;
		this.removed = removed;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Generator")
	@SequenceGenerator(name="Generator",sequenceName="seq_af_rule")
	@Column(name="ID",unique=true,nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="P_OBJ_TYPE",length=50,nullable=false)
	public String getPobjType() {
		return pobjType;
	}

	public void setPobjType(String pobjType) {
		this.pobjType = pobjType;
	}

	@Column(name="N_OBJ_TYPE",length=50,nullable=false)
	public String getNobjType() {
		return nobjType;
	}

	public void setNobjType(String nobjType) {
		this.nobjType = nobjType;
	}

	@Column(name="P_OBJ_ID",nullable=false)
	public Integer getPobjId() {
		return pobjId;
	}

	public void setPobjId(Integer pobjId) {
		this.pobjId = pobjId;
	}

	@Column(name="N_OBJ_ID",nullable=false)
	public Integer getNobjId() {
		return nobjId;
	}

	public void setNobjId(Integer nobjId) {
		this.nobjId = nobjId;
	}
	
	@Column(name="RULE_TYPE_ID",nullable=false)
	public Integer getRuleTypeId() {
		return ruleTypeId;
	}

	public void setRuleTypeId(Integer ruleTypeId) {
		this.ruleTypeId = ruleTypeId;
	}

	@Column(name="REMOVED",nullable=false)
	public Integer getRemoved() {
		return removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	
}
