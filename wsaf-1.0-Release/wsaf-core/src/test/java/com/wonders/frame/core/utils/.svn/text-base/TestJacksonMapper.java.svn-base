/** 
* @Title: JacksonTool.java 
* @Package com.wonders.frame.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.utils;


import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.fasterxml.jackson.databind.ObjectMapper;


@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestJacksonMapper extends AbstractTransactionalJUnit4SpringContextTests {
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	

	public void testGetInstance(){
		ObjectMapper rs=JacksonMapper.getInstance();
		Assert.assertNotNull(rs);
	}
	
	public void testReadValueWithClass() {
    }
	
	public void testReadValueWithJavaType() {
    }
	
	public void testGetCollectionType() {
     }
	
	public void testToJson() {
    }
	
	public void testConvertWithClass() {
    }
	
	public void testConvertWithJavaType() {
    }
}
