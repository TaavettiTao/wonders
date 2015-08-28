package com.wonders.frame.core.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.wonders.frame.core.dao.RelationDao;
import com.wonders.frame.core.model.bo.Attach;
import com.wonders.frame.core.model.bo.ObjInfo;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.utils.JacksonMapper;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public class TestRelationDao extends AbstractTestDao<Relation>{
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public RelationDao dao;
	
	@Override
	public void setInit() {
		
		Relation rec=new Relation("type1",1,"type2",1,1);			
		dao.save(rec);
		for(int j=1;j<=2;j++){
			Relation rec2=new Relation("type2",1,"type3",j,1);			
			dao.save(rec2);
			for(int k=1;k<=3;k++){
				Relation rec3=new Relation("type3",j,"type4",k,1);			
				dao.save(rec3);
			}
		}

		int p=3;
		for(int i=1,k=p+1;i<=12;){
			for(int n=0;n<p;n++){				
				Relation rec4=new Relation("type5",i,"type5",k++,1);
				dao.save(rec4);
			}
			i++;
		}
		
	}
	
	@Override
	public void setDao(){
		this.setDao(dao);
	}
	
	@Override
	public void setRecNum(){
		//this.setRecNum(0);
	}

	@Override
	public void testBusiness() {
		//setOnlyTestBuiness();
		testFindNext();
		testFindPrevious();
		testFindAllChild();
		testFindAllParent();
		testFindTree();
		testFindReverseTree();
	}
	
	public void testFindNext(){
		List<Integer> pids=new ArrayList<Integer>();
		pids.add(1);
		pids.add(2);
		List<Relation> recList=dao.findNext(1, "type3",pids);
		Assert.assertNotNull(recList);
		Assert.assertEquals(6,recList.size());
		
	}
	
	public void testFindPrevious(){
		List<Integer> nids=new ArrayList<Integer>();
		nids.add(1);
		nids.add(2);
		List<Relation> recList=dao.findPrevious(1, "type4", nids);
		Assert.assertNotNull(recList);
		Assert.assertEquals(4,recList.size());
	}
	
	public void testFindAllChild(){
		List<Relation> recList=dao.findAllChild(1, "type1", 1);
		logger.info("allChild of type1-1:{}",JacksonMapper.toJson(recList));
		Assert.assertNotNull(recList);
		//type1-type2:1条，type2-type3:2条，type3-type4:2*3条
		Assert.assertEquals(9, recList.size());

	}
		
	public void testFindAllParent(){
		List<Relation> recList=dao.findAllParent(1, "type4", 1);
		logger.info("allParent of type4-1:{}",JacksonMapper.toJson(recList));
		Assert.assertNotNull(recList);
		//type4(1)-type3(1):1条，type3(1)-type2(1):1条，type2(1)-type1:1条
		//type4(1)-type3(2):1条，type3(2)-type2(1):1条，type2(1)-type1:1条
		//其中type2-type1会被关联到2次
		Assert.assertEquals(6, recList.size());

	}
		
	public void testFindTree(){
		List<Relation> recList=dao.findTree(1, "type5", 1);
		logger.info("tree of type5-1:{}",JacksonMapper.toJson(recList));
		Assert.assertNotNull(recList);
		//1-4,5,6:3条，4-13,14,15:3条，5-16,17,18:3条，5-19,20,21:3条
		Assert.assertEquals(12, recList.size());

	}
	
	public void testFindReverseTree(){
		List<Relation> recList=dao.findReverseTree(1, "type5", 13);
		logger.info("reverseTree of type5-13:{}",JacksonMapper.toJson(recList));
		Assert.assertNotNull(recList);
		//13-4条，4-1:1条
		Assert.assertEquals(2, recList.size());

	}

	@Override
	public Relation buildRec(String index) {
		return new Relation("测试ptype"+index, Integer.valueOf(index), "测试ntype"+index, Integer.valueOf(index),1);
	}

	@Override
	public void modifyRec(Relation rec) {
		rec.setPtype("测试修改");
		
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
		this.setQueryParam("ptype_sl", "测试ptype");
		
	}

	@Override
	public void setSortParam() {
		this.setSortParam("ptype", "desc");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSqlCondition() {
		this.setSqlCondition("o.removed=0 and o.P_TYPE like :ptype||'%' order by o.id desc");
		
	}
	
	@Override
	public void setHqlCondition() {
		this.setHqlCondition("o.removed=0 and o.ptype like :ptype||'%' order by o.id desc");
		
	}

	@Override
	public void setQlParam() {
		this.setQlParam("ptype", "测试ptype");
		// TODO Auto-generated method stub
		
	}
	
}
