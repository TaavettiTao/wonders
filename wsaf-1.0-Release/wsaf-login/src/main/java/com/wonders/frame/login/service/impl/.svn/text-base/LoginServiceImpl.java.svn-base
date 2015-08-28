package com.wonders.frame.login.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.frame.console.dao.UserDao;
import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.login.dao.LoginDao;
import com.wonders.frame.login.service.LoginService;

@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService{
	
	@Resource
	private LoginDao loginDao;
	
	@Resource 
	private UserDao userDao;
	
	@Transactional(readOnly=true)
	public User findUserByUsername(String username){
		User user=userDao.findUserByName(username);
		return user;
	}

	@Override
	public Map<String, Set<String>> loadPrivilegeAndResource() {
		
		//加载所有权限
		List<String> privileges=loginDao.findAllPrililegeType();
		
		//应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问
		Map<String,Set<String>> resourceMap =new HashMap<String,Set<String>>();

		for(String privilege:privileges){
			 List<String> urls =loginDao.findUrlByPrililege(privilege);
			 for(String url:urls){
				 //判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
				 if(resourceMap.containsKey(url)){//包含该url，则将权限添加到该url为key的map集合
					 Set<String> value= resourceMap.get(url);
					 value.add(privilege.trim());
					resourceMap.put(url, value);
				 }else{
					 Set<String> value=new HashSet<String>();
					 value.add(privilege.trim());
					 resourceMap.put(url, value);
				 }
			 }
		}	
		return resourceMap;
	}

	@Override
	public User getById(int id) {
		// TODO Auto-generated method stub
		return loginDao.getById(id);
	}

	@Override
	public User getByName(String name) {
		// TODO Auto-generated method stub
		return loginDao.getByName(name);
	}

	@Override
	public boolean loginUsers(String name, String password) {
		// TODO Auto-generated method stub
		return loginDao.loginUsers(name, password);
	}

	@Override
	public List<User> searchUsers(String name) {
		// TODO Auto-generated method stub
		return loginDao.searchUsers(name);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return loginDao.getAllUsers();
	}

	@Override
	public int checkName(String name) {
		// TODO Auto-generated method stub
		return loginDao.checkName(name);
	}

	public List<Integer> getRuleTypeFromUserId(Integer userId){
		return loginDao.getRuleTypeFromUserId(userId);
	}
	
	@Override
	public int save(User user) {
		// TODO Auto-generated method stub
		return loginDao.save(user);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}
}
