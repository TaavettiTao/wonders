package com.wonders.frame.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;

import com.wonders.frame.console.model.bo.Role;
import com.wonders.frame.core.model.bo.ObjInfo;
import com.wonders.frame.core.model.bo.Rule;
import com.wonders.frame.core.model.bo.RuleType;

public class TestRoleDao extends AbstractTestDao<Role>{
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public RoleDao dao;
	
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
		findByName();
	}
	
	public void findByName(){
		Role rec=dao.findByName(getFirstRec().getName());
		Assert.assertNotNull(rec);
		Assert.assertEquals(getFirstId(),rec.getId());
	}
	
	
	@Override
	public Role buildRec(String index) {
		return new Role("测试name"+index, "测试description"+index, 0);
	}

	@Override
	public void modifyRec(Role rec) {
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
