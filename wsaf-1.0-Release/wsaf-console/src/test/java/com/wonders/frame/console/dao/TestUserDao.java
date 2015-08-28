package com.wonders.frame.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.core.model.bo.ObjInfo;
import com.wonders.frame.core.model.bo.Rule;
import com.wonders.frame.core.model.bo.RuleType;

public class TestUserDao extends AbstractTestDao<User>{
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public UserDao dao;
	
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
		findByLoginName();
		findByName();
		findUserByNameAndPassword();
		findUserByName();
	}
	
	public void findByLoginName(){
		User rec=getFirstRec();
		List<User> recList=dao.findByLoginName(rec.getLoginName());
		Assert.assertNotNull(rec);
		Assert.assertEquals(1, recList.size());		
		Assert.assertEquals(getFirstId(),recList.get(0).getId());
	}
	
	public void findByName(){
		User rec=getFirstRec();
		List<User> recList=dao.findByName(rec.getName());
		Assert.assertNotNull(rec);
		Assert.assertEquals(1, recList.size());		
		Assert.assertEquals(getFirstId(),recList.get(0).getId());
	}
	
	public void findUserByNameAndPassword(){
		User rec=dao.findUserByNameAndPassword(getFirstRec().getName(), getFirstRec().getPassword());
		Assert.assertNotNull(rec);
		Assert.assertEquals(getFirstId(),rec.getId());
	}
	
	public void findUserByName(){
		User rec=dao.findUserByName(getFirstRec().getName());
		Assert.assertNotNull(rec);
		Assert.assertEquals(getFirstId(),rec.getId());
	}

	
	@Override
	public User buildRec(String index) {
		return new User("测试name"+index, "测试loginName"+index, "测试password"+index,
				Long.valueOf(index), Long.valueOf(index),"测试telephone"+index, "测试email"+index, "gender"+index);
	}

	@Override
	public void modifyRec(User rec) {
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
