package com.wonders.frame.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.wonders.frame.core.dao.RuleDao;
import com.wonders.frame.core.model.bo.Rule;
import com.wonders.frame.core.utils.JacksonMapper;
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestRuleService extends AbstractTransactionalJUnit4SpringContextTests{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Resource 
	private RuleService ruleService;
	
	@Resource 
	private RuleDao ruleDao;
	
	private List<Integer> ids=new ArrayList<Integer>();
	@Before
	public void initDate(){
		ruleDao.deleteAll();
		Rule rec=ruleDao.save(new Rule("ejb1","ejb2",1,2,1));
		ids.add(rec.getId());
		rec=ruleDao.save(new Rule("ejb2","ejb3",2,3,1));
		ids.add(rec.getId());
		
		rec=ruleDao.save(new Rule("ejb4","ejb4",4,4,1));
		ids.add(rec.getId());

		
	}
	@Test
	public void testFindByRuleTypeId(){
		List<Rule> rs=ruleService.findByRuleTypeId(1);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3,rs.size());
		for(Rule rec:rs){
			Assert.assertTrue(ids.contains(rec.getId()));
		}
	}
	@Test
	public void testFindByRuleTypeIdAndPobjId(){
		List<Rule> rs=ruleService.findByRuleTypeIdAndPobjId(1, 1);
		Assert.assertNotNull(rs);
		Assert.assertEquals(1,rs.size());
		Assert.assertSame(ids.get(0),rs.get(0).getId());
		
	}
	@Test
	public void testFindByRuleTypeIdAndPobjType(){
		List<Rule> rs=ruleService.findByRuleTypeIdAndPobjType(1, "ejb1");
		Assert.assertNotNull(rs);
		Assert.assertEquals(1,rs.size());
		Assert.assertSame(ids.get(0),rs.get(0).getId());
	}
	
	@Test
	public void testGetRulePath(){
		List<String> rs=ruleService.getRulePath(1);
		Assert.assertNotNull(rs);
		Assert.assertEquals(2,rs.size());
		logger.info("getRulePath:{}",JacksonMapper.toJson(rs));
		Assert.assertTrue(rs.contains("ejb1>ejb2>ejb3"));
		Assert.assertTrue(rs.contains("ejb4>ejb4"));
	}

	
	@Test
	public void testCount(){
		HashMap<String,String> queryParams=new HashMap<String,String>();
		queryParams.put("pobjType", "ejb1");
		queryParams.put("nobjId", "2");
		queryParams.put("ruleTypeId", "1");		
		Long num=ruleService.count(queryParams);
		Assert.assertEquals(Long.valueOf(1), num);
		
		queryParams.clear();
		queryParams.put("pobjType", "ejb1");
		queryParams.put("nobjId", "2");
		queryParams.put("ruleTypeId", "2");		
		num=ruleService.count(queryParams);
		Assert.assertEquals(Long.valueOf(0), num);
	}

}
