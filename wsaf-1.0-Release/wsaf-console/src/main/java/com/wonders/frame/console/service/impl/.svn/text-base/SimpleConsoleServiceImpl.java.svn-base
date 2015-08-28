package com.wonders.frame.console.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wonders.frame.console.dao.OrganDao;
import com.wonders.frame.console.dao.ResourceDao;
import com.wonders.frame.console.dao.RoleDao;
import com.wonders.frame.console.dao.UserDao;
import com.wonders.frame.console.model.bo.Organ;
import com.wonders.frame.console.model.bo.Role;
import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.LoginInfo;
import com.wonders.frame.console.service.SimpleConsoleService;
import com.wonders.frame.console.service.UserService;
import com.wonders.frame.core.dao.RelationDao;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.service.RelationService;

@Service("simpleConsoleService")
public class SimpleConsoleServiceImpl implements SimpleConsoleService{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private RelationDao relationDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private OrganDao organDao;
	
	@Resource
	private ResourceDao resourceDao;
	
	@Resource
	private RoleDao roleDao;
	
	@Resource
	private UserService userService;

	@Override
	public void bindOrgan(Integer userId, Integer[] organIds) throws Exception {
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("ntype", "user");
		hm.put("nid", userId.toString());
		hm.put("ptype", "organ");
		hm.put("ruleTypeId", String.valueOf(default_rule_type_id));
		List<Relation> rs=relationDao.findAll(hm,null);		
		
		relationDao.deleteInBatch(rs);
		if(organIds!=null && organIds.length>0){
			for(Integer id:organIds){
				Relation r=new Relation("organ",id,"user",userId,default_rule_type_id);
				relationDao.save(r);
			}
		}
		
	}

	@Override
	public void bindResource(Integer userId, Integer[] resourceIds) {
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("ptype", "user");
		hm.put("pid", userId.toString());
		hm.put("ntype", "resource");
		hm.put("ruleTypeId", String.valueOf(default_rule_type_id));
		List<Relation> rs=relationDao.findAll(hm,null);		
		
		relationDao.deleteInBatch(rs);
		
		if(resourceIds!=null && resourceIds.length>0){
			for(Integer id:resourceIds){
				Relation r=new Relation("user",userId,"resource",id,default_rule_type_id);
				relationDao.save(r);
			}
		}
	}

	@Override
	public void bindRole(Integer userId, Integer[] roleIds) {
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("ntype", "user");
		hm.put("nid", userId.toString());
		hm.put("ptype", "role");
		hm.put("ruleTypeId", String.valueOf(default_rule_type_id));
		List<Relation> rs=relationDao.findAll(hm,null);		
		
		relationDao.deleteInBatch(rs);
		if(roleIds!=null && roleIds.length>0){
			for(Integer id:roleIds){
				Relation r=new Relation("role",id,"user",userId,default_rule_type_id);
				relationDao.save(r);
			}
		}
	}

	@Override
	public void deleteOrgan(Integer id) {
		List<Integer> idList=new ArrayList<Integer>();
		idList.add(id);
		//delete all relation
		List<Relation> nextRs=relationDao.findNext(default_rule_type_id,"organ",idList);		
		List<Relation> previousRs=relationDao.findPrevious(default_rule_type_id,"organ",idList);		
		nextRs.addAll(previousRs);
		relationDao.deleteInBatch(nextRs);
		
		organDao.delete(id);
	}

	@Override
	public void deleteOrgan(List<Integer> ids){
		//delete all relation
		List<Relation> nextRs=relationDao.findNext(default_rule_type_id,"organ",ids);		
		List<Relation> previousRs=relationDao.findPrevious(default_rule_type_id,"organ",ids);		
		nextRs.addAll(previousRs);
		relationDao.deleteInBatch(nextRs);
		
		//delete All object
		List<Organ> rs=organDao.findByIds(ids);
		organDao.deleteInBatch(rs);

	}

	@Override
	public void deleteResource(Integer id) {
		List<Integer> idList=new ArrayList<Integer>();
		idList.add(Integer.valueOf(id));
		//delete all relation
		List<Relation> nextRs=relationDao.findNext(default_rule_type_id,"resource",idList);		
		List<Relation> previousRs=relationDao.findPrevious(default_rule_type_id,"resource",idList);		
		nextRs.addAll(previousRs);
		relationDao.deleteInBatch(nextRs);
		
		resourceDao.delete(id);
	}

	@Override
	public void deleteResource(List<Integer> ids) {
		//delete all relation
		List<Relation> nextRs=relationDao.findNext(default_rule_type_id,"resource",ids);		
		List<Relation> previousRs=relationDao.findPrevious(default_rule_type_id,"resource",ids);		
		nextRs.addAll(previousRs);
		relationDao.deleteInBatch(nextRs);
		
		//delete All object
		List<com.wonders.frame.console.model.bo.Resource> rs=resourceDao.findByIds(ids);
		resourceDao.deleteInBatch(rs);

	}

	@Override
	public void deleteRole(Integer id) {
		List<Integer> idList=new ArrayList<Integer>();
		idList.add(Integer.valueOf(id));
		//delete all relation
		List<Relation> nextRs=relationDao.findNext(default_rule_type_id,"role",idList);		
		List<Relation> previousRs=relationDao.findPrevious(default_rule_type_id,"role",idList);		
		nextRs.addAll(previousRs);
		relationDao.deleteInBatch(nextRs);
		
		roleDao.delete(Integer.valueOf(id));
	}

	@Override
	public void deleteRole(List<Integer> ids) {
		//delete all relation
		List<Relation> nextRs=relationDao.findNext(default_rule_type_id,"role",ids);		
		List<Relation> previousRs=relationDao.findPrevious(default_rule_type_id,"role",ids);		
		nextRs.addAll(previousRs);
		relationDao.deleteInBatch(nextRs);
		
		//delete All object
		List<Role> rs=roleDao.findByIds(ids);
		roleDao.deleteInBatch(rs);

	}

	@Override
	public void deleteUser(Integer id) {
		List<Integer> idList=new ArrayList<Integer>();
		idList.add(Integer.valueOf(id));
		//delete all relation
		List<Relation> nextRs=relationDao.findNext(default_rule_type_id,"user",idList);		
		List<Relation> previousRs=relationDao.findPrevious(default_rule_type_id,"user",idList);		
		nextRs.addAll(previousRs);
		relationDao.deleteInBatch(nextRs);
		
		userDao.delete(Integer.valueOf(id));
	}

	@Override
	public void deleteUser(List<Integer> ids) {
		//delete all relation
		List<Relation> nextRs=relationDao.findNext(default_rule_type_id,"user",ids);		
		List<Relation> previousRs=relationDao.findPrevious(default_rule_type_id,"user",ids);		
		nextRs.addAll(previousRs);
		relationDao.deleteInBatch(nextRs);
		
		//delete All object
		List<User> rs=userDao.findByIds(ids);
		userDao.deleteInBatch(rs);
	}

	@Override
	public ReturnObj<LoginInfo> login(String loginName, String password) {
		return userService.login(loginName, password, default_rule_type_id);
	}

	@Override
	public Organ saveOrgan(Organ organ) {
		return organDao.save(organ);
	}

	@Override
	public List<Organ> saveOrgan(List<Organ> organs) {
		// TODO Auto-generated method stub
		return organDao.save(organs);
	}

	@Override
	public com.wonders.frame.console.model.bo.Resource saveResource(
			com.wonders.frame.console.model.bo.Resource resource) {
		return resourceDao.save(resource);
	}

	@Override
	public List<com.wonders.frame.console.model.bo.Resource> saveResource(
			List<com.wonders.frame.console.model.bo.Resource> resources) {
		return resourceDao.save(resources);
	}

	@Override
	public Role saveRole(Role role) {
		return roleDao.save(role);
	}

	@Override
	public List<Role> saveRole(List<Role> roles) {
		return roleDao.save(roles);
	}

	@Override
	public User saveUser(User user) {
		return userDao.save(user);
	}

	@Override
	public List<User> saveUser(List<User> users) {
		return userDao.save(users);
	}

}
