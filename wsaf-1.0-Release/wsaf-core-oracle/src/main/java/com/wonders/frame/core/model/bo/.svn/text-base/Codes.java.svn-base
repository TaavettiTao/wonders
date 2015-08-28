package com.wonders.frame.core.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wonders.frame.core.model.IDefaultModel;

/**
 * Codes entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AF_CODES")
public class Codes implements IDefaultModel{

	// Fields

	private Integer id;
	private Integer ccateId;
	private String pcode;
	private String code;
	private String display;
	private String description;
	private Integer removed;
	private Integer orders;

	// Constructors

	/** default constructor */
	public Codes() {
		this.removed=0;
	}

	public Codes(Integer ccateId, String pcode, String code,
			String display, String description, Integer orders) {
		this.ccateId = ccateId;
		this.pcode = pcode;
		this.code = code;
		this.display = display;
		this.description = description;
		this.orders = orders;
		this.removed = 0;
	}
	

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_codes",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CCATE_ID", nullable = false)
	public Integer getCcateId() {
		return this.ccateId;
	}

	public void setCcateId(Integer ccateId) {
		this.ccateId = ccateId;
	}

	@Column(name = "PCODE", length = 20)
	public String getPcode() {
		return this.pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	@Column(name = "CODE", length = 20, nullable = false)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "DISPLAY", length = 200, nullable = false)
	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
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

	@Column(name = "ORDERS", nullable = false)
	public Integer getOrders() {
		return this.orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

}