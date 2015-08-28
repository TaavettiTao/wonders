package com.wonders.frame.core.dao;

import java.sql.Timestamp;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wonders.frame.core.model.bo.ImportConfigLog;

public class TestImportConfigLogDao extends AbstractTestDao<ImportConfigLog>{
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public ImportConfigLogDao dao;
	
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
		
	}

	@Override
	public ImportConfigLog buildRec(String index) {
		return new ImportConfigLog("测试originalFilename"+index, "测试saveFilename"+index,
				"测试fT"+index, "测试savePath"+index, 10, "测试flag"+index,
				new Timestamp(System.currentTimeMillis()), 0);
	}

	@Override
	public void modifyRec(ImportConfigLog rec) {
		rec.setOriginalFilename("测试修改");
		
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
		this.setQueryParam("originalFilename_sl", "测试originalFilename");
		
	}

	@Override
	public void setSortParam() {
		this.setSortParam("originalFilename", "desc");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSqlCondition() {
		this.setSqlCondition("o.removed=0 and o.ORIGINAL_FILENAME like :originalFilename||'%' order by o.id desc");
		
	}
	
	@Override
	public void setHqlCondition() {
		this.setHqlCondition("o.removed=0 and o.originalFilename like :originalFilename||'%' order by o.id desc");
		
	}

	@Override
	public void setQlParam() {
		this.setQlParam("originalFilename", "测试originalFilename");
		// TODO Auto-generated method stub
		
	}
}
