package com.wonders.frame.console.model.bo;

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
public class User implements IDefaultModel{

	@ShowInView(noUse=true)
	private static final long serialVersionUID = -5398255942794045747L;
	// Fields
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	
	@ShowInView(name="姓名")
	@NotEmpty(message="用户名不能为空")
	private String name;
	
	@ShowInView(name="登录名")
	@NotEmpty(message="登录名不能为空")
	private String loginName;
	
	@ShowInView(name="密码") 
	@NotEmpty(message = "密码不能为空!")  
    @Size(min = 6, max = 12, message = "密码长度必须在{min}和{max}之间") 
	private String password;
	
	@ShowInView(name="手机1")
	private Long mobile1;
	
	@ShowInView(name="手机2")
	private Long mobile2;
	
	@ShowInView(name="座机")
	private String telephone;
	
	@ShowInView(name="邮箱")
	@Email(message = "请使用邮件格式,eg:1@163.com")
	private String email;
	
	@ShowInView(name="性别",operateType=OperateType.SELECT,ccateType="gender")
	private String gender;
	
	
	@ShowInView(name="IMOID")
	private String imoId;	
	
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;	
	
	
	
	// Constructors

	/** default constructor */
	public User() {
		this.removed=0;
	}

	/** full constructor */
	public User(String name, String password, Long mobile1,
			Long mobile2, String telephone, String email, String gender, Integer removed) {
		this.name = name;
		this.password = password;
		this.mobile1 = mobile1;
		this.mobile2 = mobile2;
		this.telephone = telephone;
		this.email = email;
		this.gender = gender;
		this.removed = removed;
	}

	// Property accessors
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
//	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_user",allocationSize=1)
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

	@Column(name = "PASSWORD", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "MOBILE1", precision = 11, scale = 0)
	public Long getMobile1() {
		return this.mobile1;
	}

	public void setMobile1(Long mobile1) {
		this.mobile1 = mobile1;
	}

	@Column(name = "MOBILE2", precision = 11, scale = 0)
	public Long getMobile2() {
		return this.mobile2;
	}

	public void setMobile2(Long mobile2) {
		this.mobile2 = mobile2;
	}

	@Column(name = "TELEPHONE", length = 20)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "EMAIL", length = 40)
	public String getEmail() {
		return email;
	}

	
	@Column(name = "GENDER")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Column(name = "REMOVED", nullable = false)
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

	@Column(name="LOGIN_NAME",length=50)
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	
	public void setImoId(String imoId) {
		this.imoId = imoId;
	}
	
	@Column(name = "IMO_ID", length = 50)
	public String getImoId(){
		return this.imoId;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}