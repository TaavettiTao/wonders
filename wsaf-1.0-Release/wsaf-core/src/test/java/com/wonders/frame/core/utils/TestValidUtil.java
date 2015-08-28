package com.wonders.frame.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestValidUtil extends AbstractTransactionalJUnit4SpringContextTests {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	public void testGetValidator(){
	}
	
	public void testCheckObj(){
		
		
		 
	}
	
	public void testCheckProperty(){
	}
	
	public void testCheckPropertyWithClass(){
	}
	
}
