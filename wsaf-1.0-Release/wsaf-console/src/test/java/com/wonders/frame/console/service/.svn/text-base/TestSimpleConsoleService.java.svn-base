package com.wonders.frame.console.service;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.wonders.frame.console.dao.UserDao;
import com.wonders.frame.console.model.bo.Organ;
import com.wonders.frame.console.model.bo.Resource;
import com.wonders.frame.console.model.bo.Role;
import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.core.dao.RelationDao;
import com.wonders.frame.core.model.bo.Relation;

@ContextConfiguration({"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TestSimpleConsoleService extends AbstractTransactionalJUnit4SpringContextTests {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	private User user;
	private Organ organ;
	private Role role;
	private Resource resource;
	
	@javax.annotation.Resource
	SimpleConsoleService simpleConsoleService;
	@javax.annotation.Resource
	UserDao userDao;
	@javax.annotation.Resource
	RelationDao relationDao;
	
	@Before
	public void setUp(){
		//初始化
		user = new User("user_name", "user_loginName_1", "user_password",123l, 123l,
				"user_telephone", "user_email", "gender");
		role = new Role("role_name", "role_description", 0);
		resource = new Resource("resource_name", "resource_type", "resource_path", "resource_description", 0);
		organ = new Organ("organ_type", "organ_name", "organ_description", 0);
	}
	
	@Test
	public void testHamcrest(){
		assertThat("test",equalToIgnoringCase("TEST"));
	}
	@Test
	public void testSaveUser(){
		User ou = user;
		User nu = simpleConsoleService.saveUser(ou);
		assertNotNull("user is null", nu);
		assertEquals("user_name",nu.getName());
	}
	
	@Test
	public void testSaveUserList(){
		User ou = user;
		List<User> users = new ArrayList<User>();
		users.add(ou);
		users.add(ou);
		List<User> nu = simpleConsoleService.saveUser(users);
		assertNotNull("user is null", nu);
		assertEquals(2,nu.size());
		assertEquals("user_name",nu.get(0).getName());
	}
	
	@Test
	public void testDeleteUser(){
		User ou = user;
		ou = simpleConsoleService.saveUser(ou);
		assertNotNull("user is null", ou);
		Relation relationNext= new Relation("user", ou.getId(), "resource", 1, simpleConsoleService.default_rule_type_id);
		Relation relationPrevious= new Relation("resource", 1,"user", ou.getId(), simpleConsoleService.default_rule_type_id);
		relationDao.save(relationNext);
		relationDao.save(relationPrevious);
		
		simpleConsoleService.deleteUser(ou.getId());
		
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(ou.getId());
		List<Relation> nextRs=relationDao.findNext(simpleConsoleService.default_rule_type_id,"user",idList );		
		List<Relation> previousRs=relationDao.findPrevious(simpleConsoleService.default_rule_type_id,"user",idList);	
		assertEquals(0,nextRs.size());
		assertEquals(0,previousRs.size());
		
		ou = userDao.findById(ou.getId());
		assertNull(ou);
	}
	
	@Test
	public void testDeleteUserList(){
		User user1 = new User("user_name_1", "user_loginName_1", "user_password",123l, 123l,
				"user_telephone", "user_email", "gender");
		User user2 = new User("user_name_2", "user_loginName_2", "user_password",123l, 123l,
				"user_telephone", "user_email", "gender");
		
		user1 = userDao.save(user1);
		user2 = userDao.save(user2);
		
		Relation relationNext= new Relation("user", user1.getId(), "resource", 1, simpleConsoleService.default_rule_type_id);
		Relation relationPrevious= new Relation("resource", 1,"user", user1.getId(), simpleConsoleService.default_rule_type_id);
		relationDao.save(relationNext);
		relationDao.save(relationPrevious);
		
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(user1.getId());
		idList.add(user2.getId());
		assertNotNull(idList);
		assertEquals(2,idList.size());
		
		simpleConsoleService.deleteUser(idList);
		//relation
		List<Relation> nextRs=relationDao.findNext(simpleConsoleService.default_rule_type_id,"user",idList );		
		List<Relation> previousRs=relationDao.findPrevious(simpleConsoleService.default_rule_type_id,"user",idList);	
		assertEquals(0,nextRs.size());
		assertEquals(0,previousRs.size());
		
		//obj
		List<User> rs=userDao.findByIds(idList);
		assertEquals(0,rs.size());
	}
	
	public void testSaveOrgan(Organ organ){}
	
	public void testSaveOrgan(List<Organ> organs){}
	
	public void testDeleteOrgan(Integer id){}
	
	public void testDeleteOrgan(List<Integer> ids){}
	
	
	public void testSaveRole(Role role){}
	
	public void testSaveRole(List<Role> roles){}
	
	public void testDeleteRole(Integer id){}
	
	public void testDeleteRole(List<Integer> ids){}
	
	
	public void testSaveResource(Resource resource){}
	
	public void testSaveResource(List<Resource> resources){}
	
	public void testDeleteResource(Integer id){}
	
	public void testDeleteResource(List<Integer> ids){}
	
	
	@Test
	public void testBindOrgan() throws Exception{
		Integer[] organIds = {1,2};
		User ou = user;
		ou = userDao.save(ou);
		
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("ntype", "user");
		hm.put("nid", ou.getId().toString());
		hm.put("ptype", "organ");
		hm.put("ruleTypeId", String.valueOf(simpleConsoleService.default_rule_type_id));
		
		List<Relation> rs=relationDao.findAll(hm,null);	
		simpleConsoleService.bindOrgan(ou.getId(), organIds);
		assertEquals(0,rs.size());
		
		rs=relationDao.findAll(hm,null);	
		assertNotNull(rs);
		assertEquals(organIds.length,rs.size());
		for (Relation relation : rs) {
			assertEquals("user",relation.getNtype());
			assertEquals(ou.getId(),relation.getNid());
			assertEquals("organ",relation.getPtype());
			assertEquals(Integer.valueOf(simpleConsoleService.default_rule_type_id),relation.getRuleTypeId());
			assertThat(relation.getPid(), isOneOf(organIds));
		}
	}
	
	public void testBindRole() throws Exception{
		Integer[] roleIds = {1,2};
		User ou = user;
		ou = userDao.save(ou);
		
		HashMap<String,String> hm=new HashMap<String,String>();
		hm.put("ntype", "user");
		hm.put("nid", ou.getId().toString());
		hm.put("ptype", "role");
		hm.put("ruleTypeId", String.valueOf(simpleConsoleService.default_rule_type_id));
		
		List<Relation> rs=relationDao.findAll(hm,null);	
		simpleConsoleService.bindOrgan(ou.getId(), roleIds);
		assertEquals(0,rs.size());
		
		rs=relationDao.findAll(hm,null);	
		assertNotNull(rs);
		assertEquals(roleIds.length,rs.size());
		for (Relation relation : rs) {
			assertEquals("user",relation.getNtype());
			assertEquals(ou.getId(),relation.getNid());
			assertEquals("role",relation.getPtype());
			assertEquals(Integer.valueOf(simpleConsoleService.default_rule_type_id),relation.getRuleTypeId());
			assertThat(relation.getPid(), isOneOf(roleIds));
		}
	}
	
	public void testBindResource(Integer userId,Integer[] resourceIds) throws Exception{}
	
}
