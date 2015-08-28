package com.wonders.frame.login.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LoginAccessService {
	
	public Set<String> findUserInfoByUsername(String username);
	
	/**
	 * 加载系统权限资源
	 * @return
	 */
	public  Map<String,Set<String>> loadPrivilegeAndResource();
}
