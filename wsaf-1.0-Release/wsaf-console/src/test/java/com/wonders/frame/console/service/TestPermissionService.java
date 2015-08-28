package com.wonders.frame.console.service;

import java.util.ArrayList;
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

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.core.service.RelationService;

@ContextConfiguration({"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TestPermissionService extends AbstractTransactionalJUnit4SpringContextTests {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private RelationService relationService;
	
	@Test
	public void testFindAllParentPermission(){
		
	}
	
	public void testFindAllChildPermission(Integer ruleTypeId,
			String type, Integer id, Integer lv, List<Permission> permissions,
			List<String> objFilter){}

}
