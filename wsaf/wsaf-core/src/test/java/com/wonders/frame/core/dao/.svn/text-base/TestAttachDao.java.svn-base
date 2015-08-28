package com.wonders.frame.core.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;


import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wonders.frame.core.model.bo.Attach;
import com.wonders.frame.core.utils.JacksonMapper;


public class TestAttachDao extends AbstractTestDao<Attach>{
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public AttachDao dao;
	
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
		this.setRecNum(10);
	}

	@Override
	public void testBusiness() {
		//setOnlyTestBuiness();
		testUpdateAttachRelation();
		testRemoveByModelNameAndModelIdAndGroupName();
	}

	public void testUpdateAttachRelation(){
		List<Attach> subRecs=getSubRecs(3);
		List<Integer> ids=getIds(subRecs);
		int num=dao.updateAttachRelation("测试修改modelName", "测试修改modelId", ids);
		Assert.assertEquals(ids.size(), num);
		List<Attach> updateRecs=dao.findByIds(ids);
		Assert.assertNotNull(updateRecs);
		Assert.assertEquals(ids.size(), updateRecs.size());
		for(Attach rec:updateRecs){
			Assert.assertEquals("测试修改modelName", rec.getModelName());
			Assert.assertEquals("测试修改modelId", rec.getModelId());
		}
		
		removeResc(subRecs);
		
	}

	public void testRemoveByModelNameAndModelIdAndGroupName(){
		Attach rec=getFirstRec();
		int num=dao.removeByModelNameAndModelIdAndGroupName(rec.getModelName(), rec.getModelId(), rec.getGroupName());
		Assert.assertEquals(1, num);
		Attach rec2=dao.findById(rec.getId());
		Assert.assertNull(rec2);
		removeResc(rec);
	}
	
	@Override
	public Attach buildRec(String index) {
		return new Attach("测试fileName"+index, "测试ext"+index,"测试filePath"+index,  "测试Size"+index, 
				"测试uploader"+index, new Timestamp(System.currentTimeMillis()),"测试Type"+index,"测试memo"+index,
				"测试ver"+index, "测试keyWord"+index, "测试modelName"+index,"测试modelId"+index,
				"测试groupName"+index,0);
	}

	@Override
	public void modifyRec(Attach rec) {
		rec.setFileName("测试修改");
		
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
		this.setQueryParam("fileName_sl", "测试fileName");
		
	}

	@Override
	public void setSortParam() {
		this.setSortParam("fileName", "desc");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSqlCondition() {
		this.setSqlCondition("o.removed=0 and o.FILE_NAME like :fileName||'%' order by o.id desc");
		
	}
	
	@Override
	public void setHqlCondition() {
		this.setHqlCondition("o.removed=0 and o.fileName like :fileName||'%' order by o.id desc");
		
	}

	@Override
	public void setQlParam() {
		this.setQlParam("fileName", "测试fileName");
		// TODO Auto-generated method stub
		
	}


}
