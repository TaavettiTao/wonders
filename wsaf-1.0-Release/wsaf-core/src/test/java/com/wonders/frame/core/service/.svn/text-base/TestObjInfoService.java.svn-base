/** 
* @Title: TestObjInfoService.java 
* @Package com.wonders.frame.core.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.wonders.frame.core.dao.ObjInfoDao;
import com.wonders.frame.core.model.bo.Ejb1;
import com.wonders.frame.core.model.bo.ObjInfo;
import com.wonders.frame.core.utils.JacksonMapper;


@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestObjInfoService extends AbstractTransactionalJUnit4SpringContextTests{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ObjInfoService objInfoService; 
	
	@Resource
	private ObjInfoDao objInfoDao;
	
	private List<Integer> ids=new ArrayList<Integer>();
	
	@Before
	public void initData(){
		objInfoDao.deleteAll();
		ObjInfo rec=new ObjInfo("测试1","ejb1","{\"entity\":\"com.wonders.frame.core.model.bo.Ejb1\"}");
		rec=objInfoDao.save(rec);
		logger.debug(JacksonMapper.toJson(rec));
		
		ids.add(rec.getId());
		
		rec=new ObjInfo("测试2","ejb2","{\"entity\":\"com.wonders.frame.core.model.bo.Ejb2\"}");
		rec=objInfoDao.save(rec);
		logger.debug(JacksonMapper.toJson(rec));
		
		ids.add(rec.getId());
		
		
		
	}
	@Test
	public void testGetEntityClassByType() throws Exception{
		Class<?> clazz=objInfoService.getEntityClassByType("ejb1");
		
		Assert.assertEquals(Ejb1.class, clazz);
	}
	
	@Test
	public void testFindByIds(){
		List<ObjInfo> rs=objInfoService.findByIds(ids);
		Assert.assertNotNull(rs);
		logger.info(JacksonMapper.toJson(rs));
		Assert.assertEquals(2, rs.size());
		
	}

}
