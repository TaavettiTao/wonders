package com.wonders.frame.console.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
//import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.wonders.frame.console.dao.UserDao;
import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.LoginInfo;
import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.model.vo.Ticket;
import com.wonders.frame.console.utils.EmptyUtils;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.bo.RuleType;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.RelationService;
import com.wonders.frame.core.service.RuleTypeService;
import com.wonders.frame.core.utils.JacksonMapper;

@ContextConfiguration({"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TestUserService extends AbstractTransactionalJUnit4SpringContextTests {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	private User user;
	private MockMultipartHttpServletRequest request;
	@Resource
	UserService userService;
	
	@Resource
	RuleTypeService ruleTypeService;
	
	@Resource
	private RelationService relationService;
	
	@Resource
	UserDao userDao;
	
	@Resource
	BasicCrudService basicCrudService;

	@Resource
	private PermissionService permissionService;

	@Resource
	private PrivilegeService privilegeService;

	@Resource
	private ResourceService resourceService;
	
	@Before
	public void setUp(){
		//初始化
		user = new User("接口测试", "test111", "111111", 123l, 123l, "131", "1@163.com", "MALE");
		request = new MockMultipartHttpServletRequest();
		request.setAttribute("ruleTypeId", 42);
		request.setAttribute("loginName", "test");
		request.setAttribute("password", "test");
	}
	@Test
	public void testHamcrest(){
		assertThat(50, allOf(greaterThan(40), lessThan(60)));
	}
	
	@Test
	public void testfindByLoginName(){
		User ou = user;
		userDao.save(ou);
		List<User> users = userService.findByLoginName("test111");
		assertNotNull(users);
		assertEquals(1,users.size());
		for (User user : users) {
			assertUserEquals(ou, user);
		}
	}
	
	@Test
	public void testFindByName(){
		User ou = user;
		userDao.save(ou);
		List<User> users = userService.findByName("接口测试");
		assertEquals(1,users.size());
		for (User user : users) {
			assertUserEquals(ou, user);
		}
	}
//	@Test
//	public void testFindByNameMock(){
//		User ou = user;
//		List<User> usersMock = new ArrayList<User>();
//		usersMock.add(ou);
//		UserDao userDao = createMock(UserDao.class);
////		expect(userDao.save(user)).andReturn(ou);
//		expect(userDao.findByName("接口测试")).andReturn(usersMock);
//		replay(userDao);
//		List<User> users = userService.findByName("接口测试");
//		assertEquals(1,users.size());
//		for (User user : users) {
//			assertUserEquals(ou, user);
//		}
//		
//		verify(userDao);
//	}
	
	@Test
	public void testFindUserByNameAndPassword(){
		User ou = user;
		userDao.save(ou);
		User user = userService.findUserByNameAndPassword("接口测试","111111");
		assertUserEquals(ou, user);
	}

	@Test
	public void testLogin(){
		testFindByName();
//		User ou = user;
//		userDao.save(ou);
		Integer ruleTypeId = (Integer) request.getAttribute("ruleTypeId");
		String loginName = (String) request.getAttribute("loginName");
		String password = (String) request.getAttribute("password");
		
		assertNotNull("loginName is null!",loginName);
		assertNotNull("ruleType is null!",password);
		assertNotNull("ruleType is not exist!",ruleTypeId);
		//根据登陆名查找用户
		List<Integer> matchedId=new ArrayList<Integer>();
		//查找对应规则类型
		RuleType rt=ruleTypeService.findById(ruleTypeId);
		assertNotNull("Has no matched ruleType with id:"+ruleTypeId, rt);
		
		List<User> users = userDao.findByLoginName(loginName);
		assertFalse("Has no matched user with loginName:"+loginName, EmptyUtils.isEmpty(users));
		
		//验证密码，获取用户ID
		for (User user : users) {
			assertEquals("password is not matched",user.getPassword(),password);
			matchedId.add(user.getId());
		}
				
		assertNotEquals("Has no matched user with loginName:"+loginName, 0, matchedId.size());

		//效验通过验证用户在制定规则类型下是否有关联对象存在
		List<Relation> userRelations=relationService.findAllRelation(rt.getId(), "user", matchedId);
		assertFalse("the user with loginName:"+loginName+" has no related objs in ruleType:"+rt.getName(), 
				EmptyUtils.isEmpty(userRelations));
		
		//校验是否有多个相同用户存在
		boolean hasDoubleUser = false;
		Integer loginUserId = -1;
		for (Relation userRelation : userRelations) {
			Integer tmpUserId = null;
			if (userRelation.getPtype().equals("user")) {
				tmpUserId = userRelation.getPid();
			} else {
				tmpUserId = userRelation.getNid();
			}

			if (loginUserId == -1) {
				loginUserId = tmpUserId;
			} else {
				if (!tmpUserId.equals(loginUserId)) {
					hasDoubleUser = true;
					break;
				}
			}
		}
		
		//有多个用户，则返回异常信息
		assertFalse("Find more than one user with loginName:"+loginName+" in ruleType:"+rt.getName(),hasDoubleUser);
		List<Permission> permissions=new ArrayList<Permission>();
		
		permissions=permissionService.findAllChildPermission(rt.getId(), "user", loginUserId,0,permissions,null);	
		List<String> objFilter=new ArrayList<String>();
		objFilter.add("privilege");
		objFilter.add("resource");		
		permissions=permissionService.findAllParentPermission(rt.getId(), "user", loginUserId,0,permissions,objFilter);
		
		permissions=privilegeService.bindPrivilegeType(permissions);
		
		permissions=resourceService.bindResourceInfo2Permission(permissions);

		LoginInfo loginUser=new LoginInfo();
		
		loginUser.setPermission(permissions);
		User user = userDao.findById(loginUserId);
		assertNotNull("Has no matched user with loginUserId:"+loginUserId, user);
		loginUser.setUser(user);
		loginUser.setTicket(new Ticket(rt.getId(),loginUserId).getTicketNo());
		ReturnObj<LoginInfo> ro = new ReturnObj<LoginInfo>(loginUser);
		logger.info(JacksonMapper.toJson(ro));
	}
	
	@Test
	public void testCollectionIsEmpty() {
		List<Integer> list = Arrays.asList(1, 2, 3);

		boolean listWithPositiveSize = EmptyUtils.isEmpty(list);
		assertFalse(listWithPositiveSize);

		List<Integer> nullList = null;
		boolean nullEmpty = EmptyUtils.isEmpty(nullList);
		assertTrue(nullEmpty);
	}
	
	private void assertUserEquals(User u, User testUser){
		assertNotNull(testUser);
		assertEquals(u.getName(),testUser.getName());
		assertEquals(u.getLoginName(),testUser.getLoginName());
		assertEquals(u.getPassword(),testUser.getPassword());
		assertEquals(u.getEmail(),testUser.getEmail());
		assertEquals(u.getMobile1(),testUser.getMobile1());
		assertEquals(u.getMobile2(),testUser.getMobile2());
		assertEquals(u.getTelephone(),testUser.getTelephone());
		assertEquals(u.getRemoved(),testUser.getRemoved());
	}
}
