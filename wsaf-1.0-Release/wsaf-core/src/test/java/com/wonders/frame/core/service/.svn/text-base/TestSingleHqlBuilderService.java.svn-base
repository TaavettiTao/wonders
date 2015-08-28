package com.wonders.frame.core.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.wonders.frame.core.dao.Ejb1Dao;
import com.wonders.frame.core.model.bo.Ejb1;
import com.wonders.frame.core.model.vo.PlaceholderParam;
import com.wonders.frame.core.model.vo.SingleHqlElement;
import com.wonders.frame.core.model.vo.SingleQueryParams;
import com.wonders.frame.core.service.SingleCrudService.QueryType;
import com.wonders.frame.core.utils.JacksonMapper;
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestSingleHqlBuilderService extends AbstractTransactionalJUnit4SpringContextTests{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());

	@Resource 
	SingleHqlBuilderService singleHqlBuilderService;
	
	@Test
	public void testBuildSingleHql() throws Exception{
		SingleQueryParams sqp=new SingleQueryParams(Ejb1.class);
		sqp.setQueryType(QueryType.ALL);
		HashMap<String,String> data=new HashMap<String,String>();
		data.put("id_in", "1,2,3,4");
		data.put("name_sl", "test");
		data.put("name_nsl", "test");
		sqp.setData(data);
		LinkedHashMap<String,String> sort=new LinkedHashMap<String,String>();
		sort.put("name", "asc");
		sort.put("id", "desc");
		sqp.setSort(sort);
		sqp.setConditionExpress("$or($and(id_in,name_sl),name_nsl)");
		SingleHqlElement she=singleHqlBuilderService.buildSingleHql(sqp);
		Assert.assertNotNull(she);
		logger.info("SingleHqlElement:{}",JacksonMapper.toJson(she));
		Assert.assertTrue(she.getHql().contains("from Ejb1 o"));
		Assert.assertTrue(she.getHql().contains("o.removed=0"));
		Assert.assertTrue(she.getHql().contains("(( o.id in (?,?,?,?)"));
		Assert.assertTrue(she.getHql().contains("and  o.name like ?||'%')"));		
		Assert.assertTrue(she.getHql().contains("or  o.name not like ?||'%')"));		
		Assert.assertTrue(she.getHql().contains("order by o.name asc ,o.id desc"));
		Assert.assertTrue(she.getQueryHql().contains("select  o from Ejb1 o"));
		Assert.assertTrue(she.getCountHql().contains("select count(*) from Ejb1 o"));
		List<PlaceholderParam> rs=she.getParamList();
		Assert.assertEquals(6, rs.size());
		Assert.assertEquals(Integer.class, rs.get(0).getFieldType());
		Assert.assertEquals("1", rs.get(0).getFieldValue());
		Assert.assertEquals(Integer.class, rs.get(1).getFieldType());
		Assert.assertEquals("2", rs.get(1).getFieldValue());
		Assert.assertEquals(Integer.class, rs.get(2).getFieldType());
		Assert.assertEquals("3", rs.get(2).getFieldValue());
		Assert.assertEquals(Integer.class, rs.get(3).getFieldType());
		Assert.assertEquals("4", rs.get(3).getFieldValue());
		Assert.assertEquals(String.class, rs.get(4).getFieldType());
		Assert.assertEquals("test", rs.get(4).getFieldValue());
		Assert.assertEquals(String.class, rs.get(5).getFieldType());
		Assert.assertEquals("test", rs.get(5).getFieldValue());
	}
}
