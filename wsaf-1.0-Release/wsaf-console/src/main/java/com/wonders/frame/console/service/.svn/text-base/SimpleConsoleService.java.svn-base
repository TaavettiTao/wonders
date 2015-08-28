package com.wonders.frame.console.service;


import java.util.List;

import com.wonders.frame.console.model.bo.Organ;
import com.wonders.frame.console.model.bo.Resource;
import com.wonders.frame.console.model.bo.Role;
import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.LoginInfo;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.ReturnObj;

public interface SimpleConsoleService {
	public final static int default_rule_type_id=1;
	
	public ReturnObj<LoginInfo> login(String loginName, String password);
	
	
	public User saveUser(User user);
	
	public List<User> saveUser(List<User> users);
	
	public void deleteUser(Integer id);
	
	public void deleteUser(List<Integer> ids);
	
	
	public Organ saveOrgan(Organ organ);
	
	public List<Organ> saveOrgan(List<Organ> organs);
	
	public void deleteOrgan(Integer id);
	
	public void deleteOrgan(List<Integer> ids);
	
	
	public Role saveRole(Role role);
	
	public List<Role> saveRole(List<Role> roles);
	
	public void deleteRole(Integer id);
	
	public void deleteRole(List<Integer> ids);
	
	
	public Resource saveResource(Resource resource);
	
	public List<Resource> saveResource(List<Resource> resources);
	
	public void deleteResource(Integer id);
	
	public void deleteResource(List<Integer> ids);
	
	
	public void bindOrgan(Integer userId,Integer[] organIds) throws Exception;
	
	public void bindRole(Integer userId,Integer[] roleIds) throws Exception;
	
	public void bindResource(Integer userId,Integer[] resourceIds) throws Exception;
	
}
