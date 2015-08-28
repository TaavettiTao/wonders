/** 
 * @Title: ConfigProperties.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author A18ccms A18ccms_gmail_com 
 * @date 2013-7-2 下午05:01:35 
 * @version V1.0 
 */
package com.wonders.frame.core.service;


import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.bo.Rule;
import com.wonders.frame.core.model.vo.ObjectAttributeVo;
import com.wonders.frame.core.model.vo.ObjectInfoVo;
import com.wonders.frame.core.utils.JacksonMapper;

@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestObjInfoCache extends AbstractTransactionalJUnit4SpringContextTests{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());	
	
	
	@Test
	public void testGetObjectInfo() throws Exception{
	
		ObjectInfoVo obj=ObjInfoCache.getObjectInfo(Rule.class);
		
		Assert.assertNotNull(obj);
		
		Assert.assertEquals(Rule.class,obj.getClazz());
	}
	@Test
	public void testGetObjectClass() throws Exception{
		
		Class<?> clazz=ObjInfoCache.getObjectClass("relation");
		
		Assert.assertNotNull(clazz);
		
		Assert.assertEquals(Relation.class,clazz);
	}
	@Test
	public void testGetObjectAttributeMap() throws Exception{
		HashMap<String,ObjectAttributeVo> fieldHm=ObjInfoCache.getObjectAttributeMap(Rule.class);
		logger.info("getObjectAttributeMap(Rule.class):{}",JacksonMapper.toJson(fieldHm));
		Assert.assertNotNull(fieldHm);
		Assert.assertFalse(fieldHm.isEmpty());
		ObjectAttributeVo fieldVo=fieldHm.get("id");
		Assert.assertNotNull(fieldVo.getColumn());		
		Assert.assertEquals("ID",fieldVo.getColumn().name());
	}
	@Test
	public void testGetObjectAttributeList() throws Exception{
		List<ObjectAttributeVo> fields=ObjInfoCache.getObjectAttributeList(Rule.class);
		logger.info("getObjectAttributeList(Rule.class):{}",JacksonMapper.toJson(fields));
		Assert.assertNotNull(fields);
		Assert.assertFalse(fields.isEmpty());
		for(ObjectAttributeVo field:fields){
			if(field.getName().equals("id")){
				Assert.assertNotNull(field.getColumn());		
				Assert.assertEquals("ID",field.getColumn().name());
				break;
			}
		}
	}
	@Test
	public void testGetObjectAttribute() throws Exception{
		ObjectAttributeVo fieldVo=ObjInfoCache.getObjectAttribute(Rule.class,"id");
		logger.info("getObjectAttribute(Rule.class,'id'):{}",JacksonMapper.toJson(fieldVo));
		Assert.assertNotNull(fieldVo.getColumn());		
		Assert.assertEquals("ID",fieldVo.getColumn().name());
		
	}

}
