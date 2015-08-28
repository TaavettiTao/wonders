package com.wonders.frame.core.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.wonders.frame.core.dao.RuleTypeDao;
import com.wonders.frame.core.model.bo.RuleType;
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestRuleTypeService extends AbstractTransactionalJUnit4SpringContextTests{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Resource 
	RuleTypeService ruleTypeService;
	
	@Resource 
	RuleTypeDao ruleTypeDao;
	
	private Integer id;
	
	@Before
	public void initData(){
		ruleTypeDao.deleteAll();
		RuleType rec=ruleTypeDao.save(new RuleType("test","1,2,3,4","ejb1,ejb2,ejb3,ejb4"));
		id=rec.getId();
	}
	@Test
	public void testFindById(){
		RuleType rec=ruleTypeService.findById(id);
		Assert.assertNotNull(rec);
		Assert.assertEquals("test", rec.getName());
		Assert.assertEquals("1,2,3,4", rec.getObjIds());
		Assert.assertEquals("ejb1,ejb2,ejb3,ejb4", rec.getObjTypes());
	}
	@Test
	public void testFindByName(){
		RuleType rec=ruleTypeService.findByName("test");
		Assert.assertNotNull(rec);
		Assert.assertEquals(id, rec.getId());
		Assert.assertEquals("1,2,3,4", rec.getObjIds());
		Assert.assertEquals("ejb1,ejb2,ejb3,ejb4", rec.getObjTypes());
	}
	@Test
	public void testFindExistOne(){
		RuleType rec=ruleTypeService.findExistOne(null);
		Assert.assertNotNull(rec);
		Assert.assertEquals(id, rec.getId());
		Assert.assertEquals("test", rec.getName());
		Assert.assertEquals("1,2,3,4", rec.getObjIds());
		Assert.assertEquals("ejb1,ejb2,ejb3,ejb4", rec.getObjTypes());
		
		rec=ruleTypeService.findExistOne("");
		Assert.assertNotNull(rec);
		Assert.assertEquals(id, rec.getId());
		Assert.assertEquals("test", rec.getName());
		Assert.assertEquals("1,2,3,4", rec.getObjIds());
		Assert.assertEquals("ejb1,ejb2,ejb3,ejb4", rec.getObjTypes());
		
		rec=ruleTypeService.findExistOne("test111");
		Assert.assertNotNull(rec);
		Assert.assertEquals(id, rec.getId());
		Assert.assertEquals("test", rec.getName());
		Assert.assertEquals("1,2,3,4", rec.getObjIds());
		Assert.assertEquals("ejb1,ejb2,ejb3,ejb4", rec.getObjTypes());
		
		rec=ruleTypeService.findExistOne("test");
		Assert.assertNotNull(rec);
		Assert.assertEquals(id, rec.getId());
		Assert.assertEquals("test", rec.getName());
		Assert.assertEquals("1,2,3,4", rec.getObjIds());
		Assert.assertEquals("ejb1,ejb2,ejb3,ejb4", rec.getObjTypes());
	}
}
