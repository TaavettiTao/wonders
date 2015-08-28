package com.wonders.frame.complex.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;



/**
 * AfGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "af_group_old", catalog = "wsaf")
public class GroupOld implements java.io.Serializable {

	// Fields
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	private String name;
	private String type;
	private String description;
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;
//	private Set<UserOld> users;
	
	// Constructors

	/** default constructor */
	public GroupOld() {
		this.removed=0;
	}

	/** full constructor */
	public GroupOld(String name, String type, Integer removed) {
		this.name = name;
		this.type = type;
		this.removed = removed;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_group_old",allocationSize=1)
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

	@Column(name = "TYPE", length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
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

//	@OneToMany(mappedBy = "groupOld")
//	public Set<UserOld> getUsers() {
//		return users;
//	}
//
//	public void setUsers(Set<UserOld> users) {
//		this.users = users;
//	}

}