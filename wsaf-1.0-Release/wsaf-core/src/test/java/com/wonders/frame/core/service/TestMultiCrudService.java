package com.wonders.frame.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestMultiCrudService extends AbstractTransactionalJUnit4SpringContextTests{	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());

	public void testFindByPageWithJson(){
		
	}
	public void testFindByPageWithRuleTypeAndJson(){
		
	}
	public void testFindByPageWithMultiQuerySqlElement(){
	}
	
	public void testFindAllWithJson(){
		
	}
	public void testFindAllWithRuleTypeAndJson(){
		
	}
	public void testFindAllWithMultiQuerySqlElement(){

	}
	public void testSaveOrUpdateWithJson(){
		
	}
	public void testSaveOrUpdateWithRuleTypeAndJson(){
		
	}
	public void testSaveOrUpdateWithRuleTypeAndMultiModelList(){
		
	}
	public void testSaveOrUpdateWithMultiModelList(){
		
	}
	public void testSaveOrUpdateWithRuleTypeAndMultiModel(){
		
	}
	public void testSaveOrUpdateWithMultiModel(){
		
	}

}
