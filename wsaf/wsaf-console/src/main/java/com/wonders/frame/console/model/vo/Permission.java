package com.wonders.frame.console.model.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Permission {
	private Integer resourceId;
	private String resourceName;
	private String resourceType;
	private String resourcePath;
	private Integer privilegeId;	
	private List<String> privilegeType;
	private Integer level;	
	@JsonIgnore
	public Integer getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}
	@JsonIgnore
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourcePath() {
		return resourcePath;
	}
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	public void setPrivilegeType(List<String> privilegeType) {
		this.privilegeType = privilegeType;
	}
	public List<String> getPrivilegeType() {
		return privilegeType;
	}

}
