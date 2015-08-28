package com.wonders.frame.login.dao;

import java.util.List;

public interface LoginAccessDao {
	
	
	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	public List<Object[]> findUserInfoByUsername(String username);
	
	/**
	 * 查询出所有可用权限
	 * @return
	 */
	public List<String> findAllPrililegeType();
	
	/**
	 * 根据权限查询出其可访问的资源
	 * @param privilege
	 * @return
	 */
	public List<String> findUrlsByPrililege(String privilege);

}
