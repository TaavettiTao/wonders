package com.wonders.frame.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AfLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_log", catalog = "wsaf")
public class Log implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3221327105444124622L;
	private Integer id;
	private Integer operatorId;
	private String operatorName;
	private String content;
	private String operation;
	private String logIp;
	private String operateTime;
	private Integer removed;

	// Constructors

	/** default constructor */
	public Log() {
	}

	/** minimal constructor */
	public Log(Integer operatorId, Integer removed) {
		this.operatorId = operatorId;
		this.removed = removed;
	}

	/** full constructor */
	public Log(Integer operatorId, String operatorName, String content,
			String operation, String logIp, String operateTime, Integer removed) {
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.content = content;
		this.operation = operation;
		this.logIp = logIp;
		this.operateTime = operateTime;
		this.removed = removed;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_log",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "OPERATOR_ID", nullable = false)
	public Integer getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	@Column(name = "OPERATOR_NAME", length = 50)
	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Column(name = "CONTENT", length = 50)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "OPERATION", length = 50)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(name = "LOG_IP", length = 50)
	public String getLogIp() {
		return this.logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	@Column(name = "OPERATE_TIME", length = 50)
	public String getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "REMOVED", nullable = false)
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

}