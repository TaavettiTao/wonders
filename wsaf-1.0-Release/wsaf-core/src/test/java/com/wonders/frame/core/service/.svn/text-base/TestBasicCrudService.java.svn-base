package com.wonders.frame.core.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.wonders.frame.core.dao.Ejb1Dao;
import com.wonders.frame.core.dao.Ejb2Dao;
import com.wonders.frame.core.dao.Ejb3Dao;
import com.wonders.frame.core.dao.Ejb4Dao;
import com.wonders.frame.core.dao.Ejb5Dao;
import com.wonders.frame.core.dao.RelationDao;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.Ejb1;
import com.wonders.frame.core.model.bo.Ejb2;
import com.wonders.frame.core.model.bo.Ejb3;
import com.wonders.frame.core.model.bo.Ejb4;
import com.wonders.frame.core.model.bo.Ejb5;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.EntityProperty;
import com.wonders.frame.core.model.vo.FieldProperty;
import com.wonders.frame.core.model.vo.PageInfo;
import com.wonders.frame.core.model.vo.RelatedNode;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.TreeNode;
import com.wonders.frame.core.utils.JacksonMapper;

@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestBasicCrudService extends AbstractTransactionalJUnit4SpringContextTests{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Resource 
	private BasicCrudService basicCrudService;
	
	@Resource 
	private RelationService relationService;
	
	@Resource
	public RelationDao dao;
	
	@Resource
	public Ejb1Dao ejb1Dao;
	
	@Resource
	public Ejb2Dao ejb2Dao;
	
	@Resource
	public Ejb3Dao ejb3Dao;
	
	@Resource
	public Ejb4Dao ejb4Dao;
	
	@Resource
	public Ejb5Dao ejb5Dao;
	
	private HashMap<String,Integer> testIdHm= new HashMap<String,Integer>();
	
	@Before
	public void initData(){
		logger.info("=============================================testIdHm Size:{}",testIdHm.size());
		logger.info("=============================================testIdHm:{}",JacksonMapper.toJson(testIdHm));
		ejb1Dao.deleteAll();
		ejb2Dao.deleteAll();
		ejb3Dao.deleteAll();
		ejb4Dao.deleteAll();
		ejb5Dao.deleteAll();
		
		ejb1Dao.save(new Ejb1(1,"role1"));
		ejb2Dao.save(new Ejb2(1,"user1"));
		Relation rec=new Relation("ejb1",1,"ejb2",1,1);			
		rec=dao.save(rec);
		
		testIdHm.put(new StringBuffer(rec.getPtype()).append("_").append(rec.getPid()).append("-").append(rec.getNtype()).append("_").append(rec.getNid()).toString(), rec.getId());
		for(int j=1;j<=2;j++){
			ejb3Dao.save(new Ejb3(j,"privilege"+j));
			
			Relation rec2=new Relation("ejb2",1,"ejb3",j,1);					
			rec2=dao.save(rec2);
			testIdHm.put(new StringBuffer(rec2.getPtype()).append("_").append(rec2.getPid()).append("-").append(rec2.getNtype()).append("_").append(rec2.getNid()).toString(), rec2.getId());
			for(int k=1;k<=3;k++){
				ejb4Dao.save(new Ejb4(k,"resource"+k));
				Relation rec3=new Relation("ejb3",j,"ejb4",k,1);			
				rec3=dao.save(rec3);
				testIdHm.put(new StringBuffer(rec3.getPtype()).append("_").append(rec3.getPid()).append("-").append(rec3.getNtype()).append("_").append(rec3.getNid()).toString(), rec3.getId());
			}
		}
		int p=3;
		for(int i=1,k=p+1;i<=12;){
			ejb5Dao.save(new Ejb5(i,"organ"+i));
			for(int n=0;n<p;n++){
				k++;
				ejb5Dao.save(new Ejb5(k,"organ"+k));
				Relation rec4=new Relation("ejb5",i,"ejb5",k,1);
				rec4=dao.save(rec4);
				testIdHm.put(new StringBuffer(rec4.getPtype()).append("_").append(rec4.getPid()).append("-").append(rec4.getNtype()).append("_").append(rec4.getNid()).toString(), rec4.getId());
			}
			i++;
		}

	}
	@Test
	public void testGetProperty(){
		/**case1**/
		ReturnObj<EntityProperty>  ro=basicCrudService.getProperty(Relation.class, "", true);		
		logger.info("getProperty(Relation.class,'',true):{}",JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		EntityProperty ep=ro.getData();
		Assert.assertEquals("relation", ep.getName());
		List<FieldProperty> fps=ep.getFieldProperties();
		Assert.assertNotNull(fps);
		int fieldNum=fps.size();
		Assert.assertNotEquals(0, fps.size());

		/**case2**/
		ro=basicCrudService.getProperty(Relation.class, "id,ptype", true);
		logger.info("getProperty(Relation.class,'id,ptype',true):{}",JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		ep=ro.getData();
		Assert.assertEquals("relation", ep.getName());
		fps=ep.getFieldProperties();
		Assert.assertNotNull(fps);
		Assert.assertEquals(2, fps.size());
		
		/**case3**/
		ro=basicCrudService.getProperty(Relation.class, "id,ptype", false);
		logger.info("getProperty(Relation.class,'id,ptype',false):{}",JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());
		ep=ro.getData();
		Assert.assertEquals("relation", ep.getName());
		fps=ep.getFieldProperties();
		Assert.assertNotNull(fps);
		Assert.assertEquals(fieldNum-2, fps.size());
		
	}
	
	
	
	@Test
	public void testFind(){
		/**case1**/
		MockHttpServletRequest request = new MockHttpServletRequest();
		HashMap<String,String> params=new HashMap<String,String>();
		params.put("ptype_el", "3");
		params.put("pid_in", "1,2");
		params.put("ntype_sl", "ejb");
		params.put("nid_nin", "2,4");
		params.put("sort", "nid desc,pid asc");
		params.put("ruleTypeId", "$notnull");
		params.put("row", "1,2");
		request.addParameters(params);
		ReturnObj<List<IDefaultModel>> ro=basicCrudService.find(Relation.class, request);
		logger.info("find(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());		
		List<IDefaultModel> rs=ro.getData();		
		Assert.assertEquals(2, rs.size());	
		for(int i=0;i<rs.size();i++){
			Relation rec=(Relation)rs.get(i);
			Assert.assertEquals("ejb3", rec.getPtype());
			Assert.assertEquals("ejb4", rec.getNtype());			
			if(i==0){
				Assert.assertSame(3, rec.getNid());
				Assert.assertSame(2, rec.getPid());
			}
			if(i==1){
				Assert.assertSame(1, rec.getNid());
				Assert.assertSame(1, rec.getPid());
			}

		}
		/**case2**/
		request = new MockHttpServletRequest();
		params=new HashMap<String,String>();
		params.put("ptype_l", "ejb");
		params.put("pid", "1");
		params.put("ntype_l", "4");
		params.put("nid_s", "2");
		params.put("nid_e", "4");
		request.addParameters(params);
		ro=basicCrudService.find(Relation.class, request);
		logger.info("find(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());		
		rs=ro.getData();		
		Assert.assertEquals(2, rs.size());	
		for(int i=0;i<rs.size();i++){
			Relation rec=(Relation)rs.get(i);
			Assert.assertEquals("ejb3", rec.getPtype());
			Assert.assertEquals("ejb4", rec.getNtype());
			Assert.assertSame(1, rec.getPid());
			if(i==0){				
				Assert.assertSame(2, rec.getNid());
			}
			if(i==1){
				Assert.assertSame(3, rec.getNid());
			}

		}
		
		/**case3**/
		request = new MockHttpServletRequest();
		params=new HashMap<String,String>();
		params.put("ptype_l", "ejb");
		params.put("ntype_nel", "5");
		params.put("ntype_nsl", "ejb4");
		request.addParameters(params);
		ro=basicCrudService.find(Relation.class, request);
		logger.info("find(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());		
		rs=ro.getData();		
		Assert.assertEquals(3, rs.size());	
		
		/**case4**/
		request = new MockHttpServletRequest();
		params=new HashMap<String,String>();
		params.put("ptype", "$null");
		request.addParameters(params);
		ro=basicCrudService.find(Relation.class, request);
		logger.info("find(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());		
		rs=ro.getData();		
		Assert.assertEquals(0, rs.size());	
		
		/**case5**/
		request = new MockHttpServletRequest();
		params=new HashMap<String,String>();
		params.put("ptype_nl", "5");
		params.put("ntype_l", "ejb");
		params.put("pid_ne", "1");
		params.put("nid_nin", "1,2");
		request.addParameters(params);
		ro=basicCrudService.find(Relation.class, request);
		logger.info("find(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());		
		rs=ro.getData();		
		Assert.assertEquals(1, rs.size());		
		
		/**case6**/
		request = new MockHttpServletRequest();
		params=new HashMap<String,String>();
		params.put("conditionExpress", "$or($and(ptype_l,nid),$and(ptype_el,pid_ne,nid_in))");
		params.put("ptype_l", "2");
		params.put("nid", "2");		
		params.put("ptype_el", "3");
		params.put("pid_ne", "1");
		params.put("nid_in", "2,3");
		request.addParameters(params);
		ro=basicCrudService.find(Relation.class, request);
		logger.info("find(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());		
		rs=ro.getData();		
		Assert.assertEquals(3, rs.size());		

		for(int i=0;i<rs.size();i++){
			Relation rec=(Relation)rs.get(i);

			if(i==0){
				Assert.assertEquals("ejb2", rec.getPtype());
				Assert.assertEquals("ejb3", rec.getNtype());
				Assert.assertSame(1, rec.getPid());
				Assert.assertSame(2, rec.getNid());
			}
			if(i==1){
				Assert.assertEquals("ejb3", rec.getPtype());
				Assert.assertEquals("ejb4", rec.getNtype());
				Assert.assertSame(2, rec.getPid());
				Assert.assertSame(2, rec.getNid());
			}
			
			if(i==2){
				Assert.assertEquals("ejb3", rec.getPtype());
				Assert.assertEquals("ejb4", rec.getNtype());
				Assert.assertSame(2, rec.getPid());
				Assert.assertSame(3, rec.getNid());
			}

		}
		
	}
	@Test
	public void testFindByPage(){
		/**case1**/
		MockHttpServletRequest request = new MockHttpServletRequest();
		HashMap<String,String> params=new HashMap<String,String>();
		params.put("ptype_el", "3");
		params.put("pid_in", "1,2");
		params.put("ntype_sl", "ejb");
		params.put("nid_nin", "2,4");
		params.put("sort", "nid desc,pid asc");
		params.put("ruleTypeId", "$notnull");
		params.put("page", "1,2");
		request.addParameters(params);
		ReturnObj<SimplePage<IDefaultModel>> ro=basicCrudService.findByPage(Relation.class, request);
		logger.info("findByPage(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());		
		SimplePage<IDefaultModel> sp=ro.getData();	
		List<IDefaultModel> rs=sp.getContent();
		Assert.assertEquals(2, rs.size());	
		for(int i=0;i<rs.size();i++){
			Relation rec=(Relation)rs.get(i);
			Assert.assertEquals("ejb3", rec.getPtype());
			Assert.assertEquals("ejb4", rec.getNtype());			
			if(i==0){
				Assert.assertSame(3, rec.getNid());
				Assert.assertSame(1, rec.getPid());

			}
			if(i==1){
				Assert.assertSame(3, rec.getNid());
				Assert.assertSame(2, rec.getPid());
			}

		}
		
		PageInfo page=sp.getPageInfo();
		Assert.assertSame(1, page.getCurrentPage());
		Assert.assertSame(2, page.getPageSize());
		Assert.assertSame(0, page.getStartRecord());
		Assert.assertSame(2, page.getTotalPages());
		Assert.assertSame(4, page.getTotalRecord());
		Assert.assertTrue(page.isHasNext());
		Assert.assertFalse(page.isHasPrevious());
		
	}
	
	@Test
	public void testCount(){
		MockHttpServletRequest request = new MockHttpServletRequest();
		HashMap<String,String> params=new HashMap<String,String>();
		params.put("ptype_el", "3");
		params.put("pid_in", "1,2");
		params.put("ntype_sl", "ejb");
		params.put("nid_nin", "2,4");
		params.put("sort", "nid desc,pid asc");
		params.put("ruleTypeId", "$notnull");
		params.put("count", "");
		request.addParameters(params);
		ReturnObj<Long> ro=basicCrudService.count(Relation.class, request);
		logger.info("count(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());		
		Assert.assertEquals(Long.valueOf(4), ro.getData());	
	}
	
	@Test
	public void testFindByIds(){
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(testIdHm.get("ejb1_1-ejb2_1"));
		ids.add(testIdHm.get("ejb2_1-ejb3_1"));
		ids.add(testIdHm.get("ejb2_1-ejb3_2"));
		ReturnObj<List<IDefaultModel>> ro=basicCrudService.findByIds(Relation.class, ids);
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());	
		List<IDefaultModel> rs=ro.getData();
		Assert.assertEquals(3,rs.size());
		
	}
	
	@Test
	public void testGet(){
		Integer id=testIdHm.get("ejb1_1-ejb2_1");
		ReturnObj<IDefaultModel> ro=basicCrudService.get(Relation.class, id);
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());	
		Relation rec=(Relation)ro.getData();
		Assert.assertEquals(id,rec.getId());
	}
	@Test
	public void testRemoveAll(){
		MockHttpServletRequest request = new MockHttpServletRequest();
		HashMap<String,String> params=new HashMap<String,String>();
		params.put("ptype", "ejb3");
		params.put("ntype", "ejb4");
		request.addParameters(params);
		ReturnObj<Integer> ro=basicCrudService.removeAll(Relation.class, request);
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());	
		Integer num=ro.getData();
		Assert.assertSame(6,num);
		
	}
	@Test
	public void testRemoveById(){
		Integer id=testIdHm.get("ejb1_1-ejb2_1");
		ReturnObj<Integer> ro=basicCrudService.removeById(Relation.class, id);
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());	
		Integer num=ro.getData();
		Assert.assertSame(1,num);
	}
	@Test
	public void testRemoveByIds(){
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(testIdHm.get("ejb1_1-ejb2_1"));
		ids.add(testIdHm.get("ejb2_1-ejb3_1"));
		ids.add(testIdHm.get("ejb2_1-ejb3_2"));
		ReturnObj<Integer> ro=basicCrudService.removeByIds(Relation.class, ids);
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());	
		Integer num=ro.getData();
		Assert.assertSame(3,num);
	}
	@Test
	public void testDeleteAll(){
		MockHttpServletRequest request = new MockHttpServletRequest();
		HashMap<String,String> params=new HashMap<String,String>();
		params.put("ptype", "ejb3");
		params.put("ntype", "ejb4");
		request.addParameters(params);
		ReturnObj<Integer> ro=basicCrudService.deleteAll(Relation.class, request);
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());	

		ReturnObj<List<IDefaultModel>> ro2=basicCrudService.find(Relation.class, request);
		logger.info("find(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro2));
		Assert.assertNotNull(ro2);
		Assert.assertTrue(ro2.getInfo().getSuccess());		
		List<IDefaultModel> rs=ro2.getData();		
		Assert.assertEquals(0, rs.size());	

	}
	@Test
	public void testDeleteById(){
		Integer id=testIdHm.get("ejb1_1-ejb2_1");
		ReturnObj<Integer> ro=basicCrudService.deleteById(Relation.class, id);
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());	
		
		ReturnObj<IDefaultModel> ro2=basicCrudService.get(Relation.class, id);
		logger.info("get(Relation.class,{}):{}",id,JacksonMapper.toJson(ro2));
		Assert.assertNotNull(ro2);
		Assert.assertFalse(ro2.getInfo().getSuccess());	
		
		Assert.assertNull(ro2.getData());
	}
	@Test
	public void testDeleteByIds(){
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(testIdHm.get("ejb1_1-ejb2_1"));
		ids.add(testIdHm.get("ejb2_1-ejb3_1"));
		ids.add(testIdHm.get("ejb2_1-ejb3_2"));
		ReturnObj<Integer> ro=basicCrudService.deleteByIds(Relation.class, ids);
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());	
		
		ReturnObj<List<IDefaultModel>> ro2=basicCrudService.findByIds(Relation.class, ids);
		Assert.assertNotNull(ro2);
		Assert.assertTrue(ro2.getInfo().getSuccess());	
		
		List<IDefaultModel> rs=ro2.getData();
		
		Assert.assertEquals(0,rs.size());
	}
	@Test
	public void testSaveOrUpdate(){
		MockHttpServletRequest request = new MockHttpServletRequest();
		HashMap<String,String> params=new HashMap<String,String>();
		params.put("ptype", "type6");
		params.put("ntype", "type7");
		params.put("pid", "1");
		params.put("nid", "2");
		params.put("ruleTypeId", "1");		
		request.addParameters(params);
		ReturnObj<IDefaultModel> ro=basicCrudService.saveOrUpdate(Relation.class, request);
		logger.info("saveOrUpdate(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro));
		Assert.assertNotNull(ro);
		Assert.assertTrue(ro.getInfo().getSuccess());	
		
		Relation rec=(Relation)ro.getData();
		Assert.assertEquals("type6", rec.getPtype());
		Assert.assertEquals("type7", rec.getNtype());
		Assert.assertSame(1, rec.getPid());
		Assert.assertSame(2, rec.getNid());
		Assert.assertSame(1, rec.getRuleTypeId());		
		
		request = new MockHttpServletRequest();
		params=new HashMap<String,String>();
		params.put("id", rec.getId().toString());
		params.put("ptype", "type7");
		params.put("ntype", "type6");
		params.put("pid", "2");
		params.put("nid", "1");
		request.addParameters(params);
		ReturnObj<IDefaultModel> ro2=basicCrudService.saveOrUpdate(Relation.class, request);
		logger.info("saveOrUpdate(Relation.class,request) with requestParams {}:{}",JacksonMapper.toJson(params),JacksonMapper.toJson(ro2));
		Assert.assertNotNull(ro2);
		Assert.assertTrue(ro2.getInfo().getSuccess());	
		Relation rec2=(Relation)ro2.getData();
		Assert.assertEquals("type7", rec2.getPtype());
		Assert.assertEquals("type6", rec2.getNtype());
		Assert.assertSame(2, rec2.getPid());
		Assert.assertSame(1, rec2.getNid());
		Assert.assertSame(1, rec2.getRuleTypeId());	
		Assert.assertSame(rec.getId(), rec2.getId());	
		
	}
	@Test	
	public void bindTreeObjInfo(){
		List<Relation> rs=relationService.findTree(1, "ejb5", 1);
		
		List<TreeNode<IDefaultModel,Integer>>  nodes=basicCrudService.bindTreeObjInfo(rs);
		logger.info("bindTreeObjInfo(rs) with rs={}:{}",JacksonMapper.toJson(rs),JacksonMapper.toJson(nodes));
		Assert.assertNotNull(nodes);
		Assert.assertEquals(12,nodes.size());
		
	}
	@Test
	public void bindParentObjInfo(){
		List<Relation> rs=relationService.findAllParentRelation(1, "ejb4", 3);
		
		List<RelatedNode<IDefaultModel,Integer>>  nodes=basicCrudService.bindParentObjInfo(rs);
		logger.info("bindParentObjInfo(rs) with rs={}:{}",JacksonMapper.toJson(rs),JacksonMapper.toJson(nodes));
		Assert.assertNotNull(nodes);
		Assert.assertEquals(6,nodes.size());
	}
	@Test
	public void bindChildObjInfo(){
		List<Relation> rs=relationService.findAllChildRelation(1, "ejb1", 1);
		
		List<RelatedNode<IDefaultModel,Integer>>  nodes=basicCrudService.bindChildObjInfo(rs);
		logger.info("bindChildObjInfo(rs) with rs={}:{}",JacksonMapper.toJson(rs),JacksonMapper.toJson(nodes));
		Assert.assertNotNull(nodes);
		Assert.assertEquals(9,nodes.size());
	}
}
