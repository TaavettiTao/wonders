package com.wonders.frame.core.dao;

import javax.annotation.Resource;

import com.wonders.frame.core.dao.ObjInfoDao;
import com.wonders.frame.core.model.bo.Ccate;
import com.wonders.frame.core.model.bo.ObjInfo;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestObjInfoDao extends AbstractTestDao<ObjInfo>{
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public ObjInfoDao dao;
	
	@Override
	public void setInit() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		findByType();
		
	}
	public void findByType(){
		ObjInfo rec=dao.findByType(getFirstRec().getType());
		Assert.assertNotNull(rec);
		Assert.assertEquals(getFirstId(),rec.getId());
	}
	@Override
	public ObjInfo buildRec(String index) {
		return new ObjInfo("测试name"+index, "测试type"+index, "测试params"+index);
	}

	@Override
	public void modifyRec(ObjInfo rec) {
		rec.setName("测试修改");
		
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
		this.setQueryParam("name_sl", "测试name");
		
	}

	@Override
	public void setSortParam() {
		this.setSortParam("name", "desc");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSqlCondition() {
		this.setSqlCondition("o.removed=0 and o.Name like :name||'%' order by o.id desc");
		
	}
	
	@Override
	public void setHqlCondition() {
		this.setHqlCondition("o.removed=0 and o.name like :name||'%' order by o.id desc");
		
	}

	@Override
	public void setQlParam() {
		this.setQlParam("name", "测试name");
		// TODO Auto-generated method stub
		
	}
	
}
