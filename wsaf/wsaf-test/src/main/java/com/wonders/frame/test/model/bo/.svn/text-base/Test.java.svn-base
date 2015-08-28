package com.wonders.frame.test.model.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;

/**
 * AfTest entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AF_TEST")
@ShowInView(name="测试")
public class Test implements IDefaultModel {

	@ShowInView(noUse=true)
	private static final long serialVersionUID = -1226524428087036918L;
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@ShowInView(name="字符串")
	private String str;
	@ShowInView(name="大字段")
	private String clob;
	@ShowInView(name="字典项",operateType=OperateType.SELECT,ccateType="testCode")
	private String code;
	@ShowInView(name="数值/金额")
	private BigDecimal num;	
	@ShowInView(name="日期")
	private Date date;
	@ShowInView(name="日期时间")
	private Date datetime;
	@ShowInView(name="FK")
	private Integer fk;

	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;
	
	
	public Test() {
		this.removed=0;
	}
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_test",allocationSize=1)	
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return id;
	}
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "REMOVED", nullable = false, precision = 22, scale = 0)
	public Integer getRemoved() {
		return removed;
	}
	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
	
	@Column(name = "F_STR", length = 200)
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
	@Column(name = "F_CLOB", length = 4000)
	public String getClob() {
		return clob;
	}
	public void setClob(String clob) {
		this.clob = clob;
	}
	
	@Column(name = "F_CODE", length = 50)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "F_NUM", precision = 12, scale = 2)
	public BigDecimal getNum() {
		return num;
	}
	public void setNum(BigDecimal num) {
		this.num = num;
	}
	//@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "F_DATE")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "F_DATETIME")
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}	
	@Column(name = "F_FK")
	public Integer getFk() {
		return fk;
	}
	public void setFk(Integer fk) {
		this.fk = fk;
	}


}
