package com.wonders.frame.complex.model.bo;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.wonders.frame.core.model.vo.Dictionary.Gender;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;



/**
 * AfUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_user_old",catalog = "wsaf")
@Access(AccessType.PROPERTY)
public class UserOld implements Serializable {

	/**
	 * 
	 */
	@ShowInView(noUse=true)
	private static final long serialVersionUID = -5398255942794045747L;
	// Fields
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	
	@ShowInView(name="姓名")
	@NotEmpty(message="用户名不能为空")
	private String name;
	
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
	private String email;
	
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;	
	private Gender gender;
	
//	private GroupOld groupOld;
	private Integer groupOldID;
	
	// Constructors

	/** default constructor */
	public UserOld() {
		this.removed=0;
	}

	/** full constructor */
	public UserOld(String name, String password, Long mobile1,
			Long mobile2, String telephone, String email, Gender gender, Integer removed) {
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_user_old",allocationSize=1)
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

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "GENDER")
	@Enumerated(EnumType.STRING)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@Column(name = "REMOVED", nullable = false)
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}

	@Column(name = "GROUPOLD_ID")
	public Integer getGroupOldID() {
		return groupOldID;
	}

	public void setGroupOldID(Integer groupOldID) {
		this.groupOldID = groupOldID;
	}

	
	
//	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
//	@JoinColumn(name = "groupOld_id")
//	public GroupOld getGroupOld() {
//		return groupOld;
//	}
//
//	public void setGroupOld(GroupOld groupOld) {
//		this.groupOld = groupOld;
//	}


}