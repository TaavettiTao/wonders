package com.wonders.frame.security.service;

import java.util.Map;
import java.util.Set;

import com.wonders.frame.console.model.bo.User;

public interface LoginService {

	public User findUserByUsername(String username);
	
	/**
	 * 加载系统权限资源
	 * @return
	 */
	public  Map<String,Set<String>> loadPrivilegeAndResource();
}
