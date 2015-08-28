package com.wonders.frame.console.model.bo;

import java.io.Serializable;
import java.util.Set;

public class UserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String dept;
	private Set<String> roles;//保证唯一，不重复
	
	public UserInfo(){}

	public UserInfo(String username, String password, String dept,
			Set<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.dept = dept;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserInfo [username=" + username + ", password=" + password
				+ ", dept=" + dept + ", roles=" + roles + "]";
	}	
}
