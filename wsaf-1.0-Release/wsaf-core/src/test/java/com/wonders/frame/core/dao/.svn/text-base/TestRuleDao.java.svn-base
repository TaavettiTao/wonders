package com.wonders.frame.core.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;

import com.wonders.frame.core.model.bo.Rule;

public class TestRuleDao extends AbstractTestDao<Rule>{
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public RuleDao dao;
	
	@Override
	public void setInit() {
		// TODO Auto-generated method stub
		dao.deleteAll();
		
	}
	
	@Override
	public void setDao(){
		this.setDao(dao);
	}
	
	@Override
	public void setRecNum(){
		//this.setRecNum(10);
	}

	@Override
	public void testBusiness() {

		testFindByRuleTypeId();
		
		testFindByRuleTypeIdAndPobjId();
		
		testFindByRuleTypeIdAndPobjType();
		
	}

	public void testFindByRuleTypeId(){
		List<Rule> recList=dao.findByRuleTypeId(getFirstRec().getRuleTypeId());
		Assert.assertNotNull(recList);
		Assert.assertEquals(getRecs().size(), recList.size());
		
		Assert.assertEquals(getFirstId(), recList.get(0).getId());
	}
	
	public void testFindByRuleTypeIdAndPobjId(){
		Rule rec=getFirstRec();
		List<Rule> recList=dao.findByRuleTypeIdAndPobjId(rec.getRuleTypeId(), rec.getPobjId());
		Assert.assertNotNull(recList);
		Assert.assertEquals(1, recList.size());		
		Assert.assertEquals(getFirstId(), recList.get(0).getId());
	}
	
	public void testFindByRuleTypeIdAndPobjType(){
		Rule rec=getFirstRec();
		List<Rule> recList=dao.findByRuleTypeIdAndPobjType(rec.getRuleTypeId(), rec.getPobjType());
		Assert.assertNotNull(recList);
		Assert.assertEquals(1, recList.size());		
		Assert.assertEquals(getFirstId(), recList.get(0).getId());
	}
	
	@Override
	public Rule buildRec(String index) {
		return new Rule("测试pobjType"+index, "测试nobjType"+index, Integer.valueOf(index), Integer.valueOf(index), 1);
	}

	@Override
	public void modifyRec(Rule rec) {
		rec.setPobjType("测试修改");
		
	}

	@Override
	public void setSingleHqlElement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setConditionExpress() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setQueryParam() {
		this.setQueryParam("pobjType_sl", "测试pobjType");
		
	}

	@Override
	public void setSortParam() {
		this.setSortParam("pobjType", "desc");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSqlCondition() {
		this.setSqlCondition("o.removed=0 and o.P_OBJ_TYPE like :pobjType||'%' order by o.id desc");
		
	}
	
	@Override
	public void setHqlCondition() {
		this.setHqlCondition("o.removed=0 and o.pobjType like :pobjType||'%' order by o.id desc");
		
	}

	@Override
	public void setQlParam() {
		this.setQlParam("pobjType", "测试pobjType");
		// TODO Auto-generated method stub
		
	}
}
