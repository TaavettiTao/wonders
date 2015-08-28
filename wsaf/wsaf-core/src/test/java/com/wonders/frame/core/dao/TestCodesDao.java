package com.wonders.frame.core.dao;

import javax.annotation.Resource;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wonders.frame.core.model.bo.Ccate;
import com.wonders.frame.core.model.bo.Codes;

public class TestCodesDao extends AbstractTestDao<Codes> {
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public CodesDao dao;
	@Resource
	public CcateDao dao2;
	
	private Integer ccateId;
	
	@Override
	public void setInit(){
		Ccate rec= new Ccate("测试Type","测试name","测试description");
		rec=dao2.save(rec);
		Assert.assertNotNull(rec.getId());
		logger.info("insert new rec,id={}",rec.getId());
		
		ccateId=rec.getId();
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
		//testFindByType();
		
	}
	

	@Override
	public Codes buildRec(String index) {
		return new Codes(ccateId, "测试pcode"+index,"测试code"+index,
				"测试display"+index, "测试description"+index, Integer.valueOf(index),0);		
	}

	@Override
	public void modifyRec(Codes rec) {
		rec.setDescription("测试修改");
		
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
		this.setQueryParam("description_sl", "测试description");
		this.setQueryParam("ccateId", ccateId.toString());
		
	}

	@Override
	public void setSortParam() {
		this.setSortParam("id", "desc");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHqlCondition() {
		this.setHqlCondition("o.removed=0 and o.ccateId=:ccateId and o.description like :description||'%' order by o.id desc");
		
	}
	@Override
	public void setSqlCondition() {
		this.setSqlCondition("o.removed=0 and o.CCATE_ID=:ccateId and o.DESCRIPTION like :description||'%' order by o.id desc");
		
	}
	
	@Override
	public void setQlParam() {
		this.setQlParam("description", "测试description");
		this.setQlParam("ccateId", ccateId);
		// TODO Auto-generated method stub
		
	}


		
}
