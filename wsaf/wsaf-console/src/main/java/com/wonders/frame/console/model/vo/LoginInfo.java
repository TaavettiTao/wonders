package com.wonders.frame.console.model.vo;

import java.util.List;

import com.wonders.frame.console.model.bo.User;

public class LoginInfo {
	private String ticket;
	private User user;
	private List<Permission> permission;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Permission> getPermission() {
		return permission;
	}
	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}
}
