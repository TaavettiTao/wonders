package com.wonders.frame.login.dao;

import java.util.List;

import com.wonders.frame.console.model.bo.User;

public interface LoginDao {
	
	public User getById(int id) ;
	public User getByName(String name);

	public boolean loginUsers(String name, String password) ;
	public List<User> searchUsers(String name) ;
	public List<User> getAllUsers() ;
	public int checkName(String name) ;
	public int save(User user) ;
	public void update(User user) ;
	public void delete(int id) ;
	public List<Integer> getRuleTypeFromUserId(Integer userId);
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
	public List<String> findUrlByPrililege(String privilege);
	
	
}
