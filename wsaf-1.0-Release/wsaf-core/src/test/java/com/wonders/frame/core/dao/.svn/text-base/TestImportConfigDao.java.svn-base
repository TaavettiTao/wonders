package com.wonders.frame.core.dao;

import java.util.List;

import javax.annotation.Resource;


import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wonders.frame.core.model.bo.ImportConfig;

public class TestImportConfigDao extends AbstractTestDao<ImportConfig>{
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public ImportConfigDao dao;
	
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
		testFindByEntityAndType();
		
	}

	public void testFindByEntityAndType(){
		ImportConfig rec=getFirstRec();
		List<ImportConfig> recList=dao.findByEntityAndType(rec.getEntity(), rec.getType());
		Assert.assertNotNull(recList);
		Assert.assertEquals(1, recList.size());
		Assert.assertEquals(rec.getId(), recList.get(0).getId());		
	}


	@Override
	public ImportConfig buildRec(String index) {
		return new ImportConfig("测试entity"+index, "测试entityField"+index,
				"测试excelField"+index, "测试type"+index, 1);
	}

	@Override
	public void modifyRec(ImportConfig rec) {
		rec.setExcelField("测试修改");
		
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
		this.setQueryParam("excelField_sl", "测试excelField");
		
	}

	@Override
	public void setSortParam() {
		this.setSortParam("excelField", "desc");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSqlCondition() {
		this.setSqlCondition("o.removed=0 and o.EXCEL_FIELD like :excelField||'%' order by o.id desc");
		
	}
	
	@Override
	public void setHqlCondition() {
		this.setHqlCondition("o.removed=0 and o.excelField like :excelField||'%' order by o.id desc");
		
	}

	@Override
	public void setQlParam() {
		this.setQlParam("excelField", "测试excelField");
		// TODO Auto-generated method stub
		
	}
}
