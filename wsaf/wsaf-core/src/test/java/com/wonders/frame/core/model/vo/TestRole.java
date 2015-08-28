package com.wonders.frame.core.model.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.wonders.frame.core.model.IDefaultModel;




/**
 * AfUser entity. @author MyEclipse Persistence Tools
 */

public class TestRole implements IDefaultModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5398255942794045747L;
	// Fields
	private Integer id;
	@NotNull
	private String name;
	@NotEmpty(message = "密码不能为空!")  
    @Size(min = 6, max = 12, message = "密码长度必须在{min}和{max}之间") 
	private String password;
	@NotNull
	private Long mobile1;
	@NotNull
	private Long mobile2;
	@NotNull
	@Length(max=12,min=5)
	private String telephone;
	@NotNull
	@Email
	private String email;

	private Integer removed;	
	
	// Constructors

	/** default constructor */
	public TestRole() {
		this.removed=0;
	}

	/** full constructor */
	public TestRole(String name, String password, Long mobile1,
			Long mobile2, String telephone, String email, Integer removed) {
		this.name = name;
		this.password = password;
		this.mobile1 = mobile1;
		this.mobile2 = mobile2;
		this.telephone = telephone;
		this.email = email;
		this.removed = removed;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getMobile1() {
		return this.mobile1;
	}

	public void setMobile1(Long mobile1) {
		this.mobile1 = mobile1;
	}

	public Long getMobile2() {
		return this.mobile2;
	}

	public void setMobile2(Long mobile2) {
		this.mobile2 = mobile2;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Min(1)
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}


}