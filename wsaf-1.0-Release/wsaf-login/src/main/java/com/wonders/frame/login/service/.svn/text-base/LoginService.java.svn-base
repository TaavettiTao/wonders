package com.wonders.frame.login.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wonders.frame.console.model.bo.User;

public interface LoginService {

	public User getById(int id) ;
	public User getByName(String name);

	public boolean loginUsers(String name, String password) ;
	public List<User> searchUsers(String name) ;
	public List<User> getAllUsers() ;
	public int checkName(String name) ;
	public int save(User user) ;
	public void update(User user) ;
	public void delete(int id) ;
	public User findUserByUsername(String username);
	public List<Integer> getRuleTypeFromUserId(Integer userId);
	/**
	 * 加载系统权限资源
	 * @return
	 */
	public  Map<String,Set<String>> loadPrivilegeAndResource();
}
