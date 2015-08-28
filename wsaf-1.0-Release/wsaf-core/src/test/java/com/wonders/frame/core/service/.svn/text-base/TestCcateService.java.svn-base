/** 
* @Title: TestCcateService.java 
* @Package com.wonders.frame.core.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.wonders.frame.core.model.bo.Ccate;
import com.wonders.frame.core.utils.JacksonMapper;

@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestCcateService extends AbstractTransactionalJUnit4SpringContextTests{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Resource
	private CcateService ccateService;

	

	
	@Test
	public void testFindByType(){
		Ccate ccate=ccateService.findByType("gender");
		Assert.assertNotNull(ccate);
		logger.info(JacksonMapper.toJson(ccate));
		Assert.assertEquals("gender", ccate.getType());
		Assert.assertEquals(3,ccate.getCodes().size());
		
	}
}
