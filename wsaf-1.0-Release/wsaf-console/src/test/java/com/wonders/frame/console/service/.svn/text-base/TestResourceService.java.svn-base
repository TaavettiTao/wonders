package com.wonders.frame.console.service;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.wonders.frame.console.dao.ResourceDao;
import com.wonders.frame.console.model.bo.Resource;
import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.model.vo.Ticket;
import com.wonders.frame.core.utils.JacksonMapper;

@ContextConfiguration({"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TestResourceService extends AbstractTransactionalJUnit4SpringContextTests {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@javax.annotation.Resource
	private ResourceService resourceService;
	@javax.annotation.Resource
	private PermissionService permissionService;
	@javax.annotation.Resource
	private PrivilegeService privilegeService;
	@javax.annotation.Resource
	private ResourceDao resourceDao;
	@Test
	public void testBindResourceInfo2Permission(){
		//获取permissions数据
		String type = "user";
		Integer id = 241;
		String ticket="241_42";
		Ticket t=new Ticket(ticket);
		List<Permission> permissions=new ArrayList<Permission>();
		List<String> objFilter=new ArrayList<String>();
		
		objFilter.add("privilege");
		objFilter.add("resource");
		
		permissions=permissionService.findAllChildPermission(t.getRuleTypeId(), type, id,0,permissions,objFilter);	
		permissions=privilegeService.bindPrivilegeType(permissions);
		logger.info(JacksonMapper.toJson(permissions));
		
		assertNotNull(permissions);
		for (Permission permission : permissions) {
			assertNotNull(permission.getResourceId());
			assertNull(permission.getResourceName());
			assertNull(permission.getResourceType());
			assertNull(permission.getResourcePath());
			assertNotNull(permission.getPrivilegeType());
		}
		
		resourceService.bindResourceInfo2Permission(permissions);
		
		logger.info(JacksonMapper.toJson(permissions));
		assertNotNull(permissions);
		for (Permission permission : permissions) {
			assertNotNull(permission.getResourceId());
			assertNotNull(permission.getResourceName());
			assertNotNull(permission.getResourceType());
			assertNotNull(permission.getResourcePath());
			assertNotNull(permission.getPrivilegeType());
		}
	}

}
