package com.wonders.frame.core.dao;

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

import com.wonders.frame.core.model.vo.MultiQuerySqlElement;
import com.wonders.frame.core.model.vo.PlaceholderParam;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.utils.JacksonMapper;

@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestMultiDao extends AbstractTransactionalJUnit4SpringContextTests {
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public MultiDao dao;
	
	private MultiQuerySqlElement se;
	
	@Before
	public void initData(){
		String sql="select a.id id, a.name name,b.id id2,b.p_type ptype,b.p_id pid,b.n_type ntype,b.n_id nid from af_rule_type a,af_relation b where a.id=b.rule_type_id and a.name=? and b.p_type=?";
//		List<String> fieldList=new ArrayList<String>();
//		fieldList.add("id");
//		fieldList.add("name");
//		fieldList.add("id");
//		fieldList.add("ptype");
//		fieldList.add("pid");
//		fieldList.add("ntype");
//		fieldList.add("nid");
//		
		List<PlaceholderParam> paramList = new ArrayList<PlaceholderParam>();
		paramList.add(new PlaceholderParam("test",String.class));
		paramList.add(new PlaceholderParam("organ",String.class));
		
		se=new MultiQuerySqlElement(sql,paramList);
	}
	
	@Test
	public void testFindByPage(){
		try{
			
			SimplePage<HashMap<String,String>> rs=dao.findByPage(se, 1, 3);
			Assert.assertNotNull(rs);
			Assert.assertEquals(3, rs.getContent().size());
			logger.info("result of findByPage：{}",JacksonMapper.toJson(rs));
			
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}

	}
	
	@Test
	public void testFindAll(){
		try{
		List<HashMap<String,String>>  rs=dao.findAll(se, 1, 3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		logger.info("result of findByPage：{}",JacksonMapper.toJson(rs));
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
}
