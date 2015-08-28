package com.wonders.frame.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.fasterxml.jackson.databind.JavaType;
import com.wonders.frame.core.dao.RelationDao;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.NodeObj;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.utils.JacksonMapper;
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestRelationService extends AbstractTransactionalJUnit4SpringContextTests{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());	
	@Resource 
	private RelationService relationService;
	
	@Resource
	public RelationDao dao;
	
	private HashMap<String,Integer> testIdHm= new HashMap<String,Integer>();
	
	@Before
	public void initData(){
		Relation rec=new Relation("type1",1,"type2",1,1);			
		rec=dao.save(rec);
		testIdHm.put(new StringBuffer(rec.getPtype()).append("_").append(rec.getPid()).append("-").append(rec.getNtype()).append("_").append(rec.getNid()).toString(), rec.getId());
		
		for(int i=1;i<=2;i++){
			rec=dao.save(new Relation("type1",1,"type3",i,1));
			testIdHm.put(new StringBuffer(rec.getPtype()).append("_").append(rec.getPid()).append("-").append(rec.getNtype()).append("_").append(rec.getNid()).toString(), rec.getId());
		}
		
		for(int i=1;i<=3;i++){
			rec=dao.save(new Relation("type1",1,"type4",i,1));
			testIdHm.put(new StringBuffer(rec.getPtype()).append("_").append(rec.getPid()).append("-").append(rec.getNtype()).append("_").append(rec.getNid()).toString(), rec.getId());
		}
		

		
		
		
		for(int j=1;j<=2;j++){
			Relation rec2=new Relation("type2",1,"type3",j,1);			
			rec2=dao.save(rec2);
			testIdHm.put(new StringBuffer(rec2.getPtype()).append("_").append(rec2.getPid()).append("-").append(rec2.getNtype()).append("_").append(rec2.getNid()).toString(), rec2.getId());
			for(int k=1;k<=3;k++){
				Relation rec3=new Relation("type3",j,"type4",k,1);			
				rec3=dao.save(rec3);
				testIdHm.put(new StringBuffer(rec3.getPtype()).append("_").append(rec3.getPid()).append("-").append(rec3.getNtype()).append("_").append(rec3.getNid()).toString(), rec3.getId());
			}
		}
		
		

		int p=3;
		for(int i=1,k=p+1;i<=12;){
			for(int n=0;n<p;n++){				
				Relation rec4=new Relation("type5",i,"type5",k++,1);
				rec4=dao.save(rec4);
				testIdHm.put(new StringBuffer(rec4.getPtype()).append("_").append(rec4.getPid()).append("-").append(rec4.getNtype()).append("_").append(rec4.getNid()).toString(), rec4.getId());
			}
			i++;
		}
	}
	
	@Test
	public void testBind(){
		HashMap<String,String> queryParams=new HashMap<String,String>();
		queryParams.put("ptype", "type1");
		queryParams.put("pid", "1");
		queryParams.put("ntype", "type2");
		queryParams.put("nid", "1");
		queryParams.put("ruleTypeId", "2");
		
		Relation rec=relationService.bind(queryParams);
		
		Assert.assertNotNull(rec);
		
		Assert.assertSame(2, rec.getRuleTypeId());
	}
	@Test
	public void testRebind(){
		HashMap<String,String> queryParams=new HashMap<String,String>();
		queryParams.put("ptype", "type1");
		queryParams.put("pid", "1");
		queryParams.put("ntype", "type2");
		queryParams.put("nid", "1");
		queryParams.put("ruleTypeId", "1");
		HashMap<String,String> newParams=new HashMap<String,String>();
		newParams.put("ptypeNew", "type0");
		newParams.put("pidNew", "0");
		Relation rec=relationService.rebind(queryParams, newParams);
		
		Assert.assertNotNull(rec);
		Assert.assertEquals("type0", rec.getPtype());
		Assert.assertSame(0, rec.getPid());
		
		queryParams.clear();
		queryParams.put("ptype", "type0");
		queryParams.put("pid", "0");
		queryParams.put("ntype", "type2");
		queryParams.put("nid", "1");
		queryParams.put("ruleTypeId", "1");
		
		newParams.clear();
		newParams.put("ntypeNew", "type0");
		newParams.put("nidNew", "0");
		
		rec=relationService.rebind(queryParams, newParams);
		
		Assert.assertNotNull(rec);
		Assert.assertEquals("type0", rec.getNtype());
		Assert.assertSame(0, rec.getNid());
	}
	@Test
	public void testUnbind() throws Exception{
		HashMap<String,String> queryParams=new HashMap<String,String>();
		queryParams.put("ptype", "type1");
		queryParams.put("pid", "1");
		queryParams.put("ntype", "type2");
		queryParams.put("nid", "1");
		queryParams.put("ruleTypeId", "1");
		relationService.unbind(queryParams);
		
		List<Relation> rs=dao.findAll(queryParams, null);
		
		Assert.assertNotNull(rs);
		
		Assert.assertSame(0, rs.size());
	}
	@Test
	public void testFindRelationPaths(){
		ReturnObj<List<String>> ro=relationService.findRelationPaths("1", "type1>type2>type3");		
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		logger.info("type1>type2>type3:{}",JacksonMapper.toJson(ro.getData()));
		List<String> paths=ro.getData();
		Assert.assertSame(2, paths.size());
		Assert.assertTrue(paths.contains("type1_1>type2_1>type3_1"));
		Assert.assertTrue(paths.contains("type1_1>type2_1>type3_2"));
		
		ro=relationService.findRelationPaths("1", "type3<type2<type1");
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		logger.info("type3<type2<type1:{}",JacksonMapper.toJson(ro.getData()));
		paths=ro.getData();
		Assert.assertSame(2, paths.size());
		Assert.assertTrue(paths.contains("type3_1<type2_1<type1_1"));
		Assert.assertTrue(paths.contains("type3_2<type2_1<type1_1"));
		
		ro=relationService.findRelationPaths("1", "type3,type4<type1>type2");
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		logger.info("type3,type4<type1>type2:{}",JacksonMapper.toJson(ro.getData()));
		paths=ro.getData();
		Assert.assertSame(5, paths.size());
		Assert.assertTrue(paths.contains("type3_1<type1_1>type2_1"));
		Assert.assertTrue(paths.contains("type3_2<type1_1>type2_1"));
		Assert.assertTrue(paths.contains("type4_1<type1_1>type2_1"));
		Assert.assertTrue(paths.contains("type4_2<type1_1>type2_1"));
		Assert.assertTrue(paths.contains("type4_3<type1_1>type2_1"));
		
		ro=relationService.findRelationPaths("1", "type1>type2,type4");
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		logger.info("type1>type2,type4:{}",JacksonMapper.toJson(ro.getData()));
		paths=ro.getData();
		Assert.assertSame(4, paths.size());
		Assert.assertTrue(paths.contains("type1_1>type2_1"));
		Assert.assertTrue(paths.contains("type1_1>type4_1"));
		Assert.assertTrue(paths.contains("type1_1>type4_2"));
		Assert.assertTrue(paths.contains("type1_1>type4_3"));

		
		ro=relationService.findRelationPaths("1", "type1,type3>type4");
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		logger.info("type1,type3>type4:{}",JacksonMapper.toJson(ro.getData()));
		paths=ro.getData();
		Assert.assertSame(9, paths.size());
		Assert.assertTrue(paths.contains("type1_1>type4_1"));
		Assert.assertTrue(paths.contains("type1_1>type4_2"));
		Assert.assertTrue(paths.contains("type1_1>type4_3"));
		Assert.assertTrue(paths.contains("type3_1>type4_1"));
		Assert.assertTrue(paths.contains("type3_1>type4_2"));
		Assert.assertTrue(paths.contains("type3_1>type4_3"));
		Assert.assertTrue(paths.contains("type3_2>type4_1"));
		Assert.assertTrue(paths.contains("type3_2>type4_2"));
		Assert.assertTrue(paths.contains("type3_2>type4_3"));

		
		ro=relationService.findRelationPaths("1", "type2>type3_1,type3_2>type4_1");
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		logger.info("type2>type3_1,type3_2>type4_1:{}",JacksonMapper.toJson(ro.getData()));
		paths=ro.getData();
		Assert.assertSame(2, paths.size());
		Assert.assertTrue(paths.contains("type2_1>type3_1>type4_1"));
		Assert.assertTrue(paths.contains("type2_1>type3_2>type4_1"));
		
		ro=relationService.findRelationPaths("1", "type1_1>type2,type4_3");
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		logger.info("type1_1>type2,type4_3:{}",JacksonMapper.toJson(ro.getData()));
		paths=ro.getData();
		Assert.assertSame(2, paths.size());
		Assert.assertTrue(paths.contains("type1_1>type2_1"));
		Assert.assertTrue(paths.contains("type1_1>type4_3"));
	}
	
	@Test
	public void testFindRelationTree(){

		ReturnObj<HashMap<String,List<NodeObj>>> ro=relationService.findRelationTree("1", "type1>type2>type3");		
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		logger.info("type1>type2>type3:{}",JacksonMapper.toJson(ro.getData()));
		
		HashMap<String,List<NodeObj>> nodesHm=ro.getData();
		
		Assert.assertSame(1, nodesHm.keySet().size());
		Assert.assertTrue(nodesHm.containsKey("type1"));
		List<NodeObj> nodes=nodesHm.get("type1");
		Assert.assertSame(1, nodes.size());
		
		NodeObj nodeObj=nodes.get(0);
		Assert.assertEquals("1",nodeObj.getData().toString());
		
		
		HashMap<String,Object> nextNodeHm=(HashMap<String,Object>)JacksonMapper.convert(nodeObj.getRelatedNode(), JacksonMapper.hmObj);
		
		Assert.assertTrue(nextNodeHm.containsKey("next"));
		nextNodeHm=(HashMap<String,Object>)JacksonMapper.convert(nextNodeHm.get("next"), JacksonMapper.hmObj);
		
		Assert.assertTrue(nextNodeHm.containsKey("type2"));
		JavaType javaType=JacksonMapper.getCollectionType(ArrayList.class, NodeObj.class);
		nodes=(List<NodeObj>)JacksonMapper.convert(nextNodeHm.get("type2"), javaType);
		Assert.assertSame(1, nodes.size());
		
		nodeObj=nodes.get(0);
		Assert.assertEquals("1", nodeObj.getData().toString());
		
		
		nextNodeHm=(HashMap<String,Object>)JacksonMapper.convert(nodeObj.getRelatedNode(), JacksonMapper.hmObj);

		Assert.assertTrue(nextNodeHm.containsKey("next"));
		nextNodeHm=(HashMap<String,Object>)JacksonMapper.convert(nextNodeHm.get("next"), JacksonMapper.hmObj);
		
		Assert.assertTrue(nextNodeHm.containsKey("type3"));
		nodes=(List<NodeObj>)JacksonMapper.convert(nextNodeHm.get("type3"), javaType);
		Assert.assertSame(2, nodes.size());
		
		nodeObj=nodes.get(0);
		Assert.assertEquals("1", nodeObj.getData().toString());
		Assert.assertNull(nodeObj.getRelatedNode());
		
		nodeObj=nodes.get(1);
		Assert.assertEquals("2", nodeObj.getData().toString());
		Assert.assertNull(nodeObj.getRelatedNode());
	}
	@Test
	public void testFindAllChildRelation(){
		List<Relation> rs=relationService.findAllChildRelation(1, "type1", 1);
		
		Assert.assertEquals(20, rs.size());
	}
	@Test
	public void testFindAllParentRelation(){
		List<Relation> rs=relationService.findAllParentRelation(1, "type4", 1);
		
		Assert.assertEquals(9, rs.size());
	}
	@Test
	public void testFindAllRelation(){
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		List<Relation> rs=relationService.findAllRelation(1, "type3", ids);
		
		Assert.assertEquals(10, rs.size());
	}
	@Test
	public void testFindTree(){
		List<Relation> rs=relationService.findTree(1, "type5", 1);
		Assert.assertEquals(12, rs.size());
	}
	@Test
	public void testFindReverseTree(){
		List<Relation> rs=relationService.findReverseTree(1, "type5", 13);
		Assert.assertEquals(2, rs.size());
	}
	@Test
	public void testCount(){
		HashMap<String,String> queryParams=new HashMap<String,String>();
		queryParams.put("ptype", "type1");
		queryParams.put("pid", "1");
		Long cnt=relationService.count(queryParams);
		Assert.assertEquals(Long.valueOf(6), cnt);
	}


}