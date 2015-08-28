package com.wonders.frame.core.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.Attach;
import com.wonders.frame.core.model.vo.ObjectInfoVo;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.SingleHqlElement;
import com.wonders.frame.core.model.vo.SingleQueryParams;
import com.wonders.frame.core.utils.JacksonMapper;
import com.wonders.frame.core.utils.SqlBuilderUtil;

@ContextConfiguration({"classpath:applicationContext.xml"})
public abstract class AbstractTestDao<T extends IDefaultModel> extends AbstractTransactionalJUnit4SpringContextTests {
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	private Class<T> entityClass;
	private String tableName;
	public GenericRepository<T,Integer> dao;
	private ObjectInfoVo objInfo;
	private int recNum=5;
	
	private boolean onlyTestBuiness=false;
	
	private SingleHqlElement she;
	
	private String conditionExpress;
	
	private HashMap<String,String> queryParam;
	
	private LinkedHashMap<String,String> sortParam;
	

	private String hqlCondition;
	
	private String countSql;
	private String querySql;
	private String singleSql;
	private String sqlCondition;
	
	private HashMap<String,Object> qlParam;
		
	private List<T> recs=new ArrayList<T>();
		
	public AbstractTestDao(){
		Type t = getClass().getGenericSuperclass();
		this.entityClass = (Class<T>)((ParameterizedType)t).getActualTypeArguments()[0];    

		this.objInfo= new ObjectInfoVo(entityClass);
		this.tableName=objInfo.getTable().name();
		this.countSql="select count(*) from "+tableName+" o";
		this.querySql="select * from "+tableName+" o";
		this.singleSql="select max(id) from "+tableName+" o";

	}
	
	/****************************设置具体dao**************************************/
	public abstract void setDao();
	public void setDao(GenericRepository<T,Integer> dao){
		this.dao= dao;
	}
	public abstract void setRecNum();
	public void setRecNum(int i){
		this.recNum= i;
	}

	/****************************查询中用到的测试数据**************************************/
	//使用SingleHqlElement传参进行查询时用到的测试数据
	public abstract void setSingleHqlElement();
	public void setSingleHqlElement(SingleHqlElement she){
		this.she=she;
	}
	
	public SingleHqlElement getSingleHqlElement(){
		return she;
	}
	public abstract void setConditionExpress();
	public void setconditionExpress(String conditionExpress){
		this.conditionExpress=conditionExpress;
	}
	
	public String getConditionExpress(){
		return conditionExpress;
	}
	
	public SingleHqlElement getSingleHqlElement(boolean isSort){
		
		try{
			if(getSingleHqlElement()!=null){
				
				return getSingleHqlElement();
				
			}else{

				SingleQueryParams sqp=new SingleQueryParams(entityClass);
				sqp.setData(getQueryParams());
				if(isSort){
					sqp.setSort(getSortParam());
				}
				if(getConditionExpress()!=null &&!getConditionExpress().equals("")){
					sqp.setConditionExpress(conditionExpress);
				}
				SingleHqlElement she=SqlBuilderUtil.getSingleHqlBuilder().buildSingleHql(sqp);
				
				
				return she;
	
			}
		}catch(Exception e){
			return null;
		}
	}

	//使用HashMap传参进行查询时用到的测试数据
	public abstract void setQueryParam();
	public void setQueryParam(String key,String value){
		if(this.queryParam==null){
			this.queryParam= new HashMap<String,String>();
		}
		queryParam.put(key, value);
	}
	public HashMap<String,String> getQueryParams(){
		
		//rpm.put("name_sl", "测试name");
		return this.queryParam;
	}
	public abstract void setSortParam();	
	public void setSortParam(String key,String value){
		if(this.sortParam==null){
			this.sortParam= new LinkedHashMap<String,String>();
		}
		sortParam.put(key, value);
	}
	
	public LinkedHashMap<String,String> getSortParam(){
		//rpm.put("id", "desc");
		return sortParam;
	}

	public abstract void setHqlCondition();
	public void setHqlCondition(String hqlCondition){
		this.hqlCondition=hqlCondition;
	}
	
	public String getHqlCondition(){
		if(this.hqlCondition==null){
			return "";
		}else{
			return hqlCondition;
		}
	}
	
	public String getHqlWhere(){

		int idx= getHqlCondition().indexOf("order by");
		if(idx>0){
			return getHqlCondition().substring(0,idx);
		}else{
			return getHqlCondition();
		}
	}
	
	public void setCountSql(String sql){
		this.countSql=sql;
	}
	public void setQuerySql(String sql){
		this.querySql=sql;
	}
	public void setSingleSql(String sql){
		this.singleSql=sql;
	}
	
	public abstract void setSqlCondition();
	public void setSqlCondition(String sql){
		this.sqlCondition=sql;
	}
	
	public String getSqlCondition(){
		if(this.sqlCondition==null){
			return "";
		}else{
			return sqlCondition;
		}
	}
	
	public String getSqlWhere(){
		int idx= getSqlCondition().indexOf("order by");
		if(idx>0){
			return getSqlCondition().substring(0,idx);
		}else{
			return getSqlCondition();
		}
	}

	
	public String getCountWhereSql(){
		String s=this.countSql;
		if(!getSqlWhere().equals("")){
			s= s.concat(" where ").concat(getSqlWhere());
		}
		
		return s;
	}	
	
	public String getQueryWhereSql(){
		String s=this.querySql;
		if(!getSqlWhere().equals("")){
			s= s.concat(" where ").concat(getSqlWhere());
		}
		
		return s;
	}

	public String getQueryWhereSortSql(){
		String s=this.querySql;
		if(!getSqlCondition().equals("")){
			s= s.concat(" where ").concat(getSqlCondition());
		}
		
		return s;
	}
	
	public String getSingleSql(){
		String s=this.singleSql;
		if(!getSqlWhere().equals("")){
			s= s.concat(" where ").concat(getSqlWhere());
		}
		
		return s;
	}
	public abstract void setQlParam();
	public void setQlParam(String key,Object value){
		if(this.qlParam==null){
			this.qlParam= new HashMap<String,Object>();
		}
		qlParam.put(key, value);
	}
	
	public HashMap<String,Object> getQlParam(){
		return qlParam;
	}


	/****************************测试用记录缓存相关方法**************************************/
	public List<T> getSubRecs(int i){
		List<T> subRecs=new ArrayList<T>();
		for(int j=0;j<i;j++){
			subRecs.add(recs.get(j));		
		}
		
		return subRecs;
	}
	public List<T> getRecs(){
		return this.recs;
	}
	public void removeResc(List<T> subList){
		this.recs.removeAll(subList);
		dao.removeByIds(getIds(subList));
	}
	
	public void removeResc(T rec){
		this.recs.remove(rec);
		dao.removeById(rec.getId());
	}
	
	
	public List<Integer> getIds(List<T> recList){
		List<Integer> ids=new ArrayList<Integer>();
		for(T rec:recList){
			ids.add(rec.getId());		
		}
		
		return ids;
	}
	public T getFirstRec(){
		return recs.get(0);
	}
	
	public T getLastRec(){
		return recs.get(recs.size()-1);
	}
	
	public Integer getFirstId(){
		return recs.get(0).getId();
	}
	
	public Integer getLastId(){
		return recs.get(recs.size()-1).getId();
	}
	public abstract void setInit();
	@Before
	public void getReady(){
		
		setInit();
		setDao();
		
		setRecNum();
		
		setSingleHqlElement();
		
		setConditionExpress();
		
		setQueryParam();

		setSortParam();
		
		setHqlCondition();

		setSqlCondition();
		setQlParam();

	}
	/****************************测试主方法**************************************/
	@Test
	//@Rollback(false)
	public void testDao() {		
		testAddRecInBatch(recNum);//批量新增

		testBusiness();		//具体业务
		
		if(!onlyTestBuiness){
			testQuery();
			
			testUpdateRec();	//修改
			
			testFindById();	//按ID查找
			
			testFindExistOne();	//查存在的任意一个
	
			testFindByIds();	//按一组ID查
	
			testRemoveById();//按ID做逻辑删除
			
			testRemoveByIds();//按一组ID做逻辑删除
					
			testRemove();//按对象做逻辑删除
			
			testRemoveEntitys();//按一组对象做逻辑删除
		}

	}
	/****************************查询相关方法**************************************/
	public void testQuery(){
		//用自定义的简单hql元素对象查
		countWithSHE();	//查询总数

		testFindAllWithSHE();	//查询所有(不带排序)
		
		testFindAllBySortWithSHE();	//查询所有(带排序)

		testFindAllByRowWithSHE();	//查询一定范围内数据(不带排序)
		
		testFindAllByRowAndSortWithSHE();	//查询一定范围内数据(带排序)

		testFindByPageWithSHE();	//查询分页数据(不带排序)	

		testFindByPageBySortWithSHE();	//查询分页数据(带排序)	
		
		//用hashMap传参查
		countWithMap();	//查询总数

		testFindAllWithMap();	//查询所有(不带排序)

		testFindAllBySortWithMap();	//查询所有(带排序)

		testFindAllByRowWithMap();	//查询一定范围内数据(不带排序)

		testFindAllByRowAndSortWithMap();	//查询一定范围内数据(带排序)
		
		testFindByPageWithMap();	//查询分页数据(不带排序)	

		testFindByPageBySortWithMap();	//查询分页数据(带排序)	
		
		
		//用hql加传参查
		countWithHql();	//查询总数

		testFindAllWithHql();	//查询所有(不带排序)

		testFindAllBySortWithHql();	//查询所有(带排序)

		testFindAllByRowWithHql();	//查询一定范围内数据(不带排序)

		testFindAllByRowAndSortWithHql();	//查询一定范围内数据(带排序)
		
		testFindByPageWithHql();	//查询分页数据(不带排序)	

		testFindByPageBySortWithHql();	//查询分页数据(带排序)	
		
		
		//用sql加传参查
		countWithSql();//查总数
		
		testFindAllWithSql();//查询所有(不带排序)
		
		testFindAllBySortWithSql();//查询所有(带排序)

		testFindAllByRowWithSql();//查询一定范围内数据(不带排序)
		
		testFindAllByRowAndSortWithSql();//查询一定范围内数据(带排序)
		
		testFindByPageWithSql();//查询分页数据(不带排序)	

		testFindByPageBySortWithSql();//查询分页数据(带排序)	

		testGetSingleResultBySql();//查询唯一值	
	}
	/****************************查询相关方法中用到的视图转换**************************************/

	//具体业务相关DAO方法测试
	
	public abstract void testBusiness();
	
	public void setOnlyTestBuiness(){
		this.onlyTestBuiness=true;
	}
	/****************************新增**************************************/
	public void testAddRecInBatch(int i){
		for(int x=1;x<=i;x++){
			testAddRec(x);
		}
	}
	
	//新增
	public abstract T buildRec(String index);//按照序列号生成具体测试数据
	
	public void testAddRec(int i) {//调用addRec实现具体序列数据的插入
		String index=i+"";
		if(i<10){index="0"+index;}
		T rec=buildRec(index);		
		addRec(rec);

	}
	
	public void addRec(T rec){
		rec=dao.save(rec);
		Assert.assertNotNull(rec.getId());
		logger.info("insert new rec,id={}",rec.getId());
		recs.add(rec);
	}
	/****************************修改**************************************/
	//修改
	public void testUpdateRec() {
		
		T rec=getFirstRec();
		
		modifyRec(rec);
		
		rec=dao.save(rec);	
		
		Assert.assertNotNull(rec);

		T newRec=dao.findById(rec.getId());
		
		Assert.assertEquals(rec, newRec);
		
		//recs.remove(0);
		removeResc(getFirstRec());

	}
	
	public abstract void modifyRec (T rec);//实现具体对象的属性修改操作
	
	
	//按ID查找
	public void testFindById() {		
		T rec=dao.findById(getFirstId());	
		Assert.assertNotNull(rec);
		Assert.assertEquals(getFirstId(),rec.getId());
	}
	
	//查唯一的一个
	public void testFindExistOne(){
		
		T rec= dao.findExistOne();
		
		Assert.assertNotNull(rec);
		
		logger.info("findExistOne:"+JacksonMapper.toJson(rec));
	}
	//按一组ID查
	public void testFindByIds(){
		List<T> subList= getSubRecs(2);
		List<T> rs=dao.findByIds(getIds(subList));
		Assert.assertEquals(subList.size(),rs.size());
		Assert.assertEquals(subList.get(0).getId(), rs.get(0).getId());

	}
	


	
	//按ID做逻辑删除
	public void testRemoveById(){		
		int num=dao.removeById(getFirstId());
		Assert.assertEquals(1,num);
		T rec=dao.findById(getFirstId());
		Assert.assertNull(rec);
		recs.remove(0);
	}
	

	//按一组ID做逻辑删除
	public void testRemoveByIds(){	
		List<T> subList=getSubRecs(2);
		int num=dao.removeByIds(getIds(subList));
		Assert.assertEquals(subList.size(),num);
		List<T> recList=dao.findByIds(getIds(subList));
		Assert.assertNotNull(recList);
		Assert.assertEquals(0, recList.size());
		recs.removeAll(subList);
	}

	//按对象做逻辑删除
	public void testRemove(){		
		int num=dao.remove(getFirstRec());
		Assert.assertEquals(1,num);
		T rec=dao.findById(getFirstRec().getId());
		Assert.assertNull(rec);
		recs.remove(0);
	}

	
	//按一组对象做逻辑删除
	public void testRemoveEntitys(){		
		int num=dao.remove(recs);
		Assert.assertEquals(recs.size(),num);
		List<T> recList=dao.findByIds(getIds(recs));
		Assert.assertNotNull(recList);
		Assert.assertEquals(0, recList.size());
	}
	
	
	//用自定义的简单hql元素对象查
	//查询总数
	public void countWithSHE(){		
		Long cnt=dao.count(getSingleHqlElement(false));
		Assert.assertEquals(Long.valueOf(recs.size()), cnt);
	}
	//查询所有(不带排序)
	public void testFindAllWithSHE(){
		
		List<T> rs=dao.findAll(getSingleHqlElement(false));
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(0).getId());
	}
	//查询所有(带排序)
	public void testFindAllBySortWithSHE(){		
		List<T> rs=dao.findAll(getSingleHqlElement(true));

		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(rs.size()-1).getId());
	}

	
	//查询一定范围内数据(不带排序)
	public void testFindAllByRowWithSHE(){
		List<T> rs=dao.findAll(getSingleHqlElement(false),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(1).getId(), rs.get(0).getId());
	}
	
	//查询一定范围内数据(带排序)
	public void testFindAllByRowAndSortWithSHE(){
		List<T> rs=dao.findAll(getSingleHqlElement(true),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(recs.size()-2).getId(), rs.get(0).getId());
	}
	
	//查询分页数据(不带排序)	
	public void testFindByPageWithSHE(){
		SimplePage<T> rs=dao.findByPage(getSingleHqlElement(false),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(0).getId(), rs.getContent().get(0).getId());
	}

	
	//查询分页数据(带排序)	
	public void testFindByPageBySortWithSHE(){
		SimplePage<T> rs=dao.findByPage(getSingleHqlElement(true),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(recs.size()-1).getId(), rs.getContent().get(0).getId());
	}
	
	//用hashMap传参查
	//查询总数
	public void countWithMap(){
		Long cnt=dao.count(getQueryParams());
		Assert.assertEquals(Long.valueOf(recs.size()), cnt);
	}
	//查询所有(不带排序)
	public void testFindAllWithMap(){
		List<T> rs=dao.findAll(getQueryParams(),null);
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(0).getId());
	}
	//查询所有(带排序)
	public void testFindAllBySortWithMap(){
		List<T> rs=dao.findAll(getQueryParams(),getSortParam());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(rs.size()-1).getId());
	}

	
	//查询一定范围内数据(不带排序)
	public void testFindAllByRowWithMap(){
		List<T> rs=dao.findAll(getQueryParams(),null,1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(1).getId(), rs.get(0).getId());
	}
	
	//查询一定范围内数据(带排序)
	public void testFindAllByRowAndSortWithMap(){
		List<T> rs=dao.findAll(getQueryParams(),getSortParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(recs.size()-2).getId(), rs.get(0).getId());
	}
	
	//查询分页数据(不带排序)	
	public void testFindByPageWithMap(){
		SimplePage<T> rs=dao.findByPage(getQueryParams(),null,1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(0).getId(), rs.getContent().get(0).getId());
	}

	
	//查询分页数据(带排序)	
	public void testFindByPageBySortWithMap(){
		SimplePage<T> rs=dao.findByPage(getQueryParams(),getSortParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(recs.size()-1).getId(), rs.getContent().get(0).getId());
	}
	
	//用hql加传参查
	//查询总数
	public void countWithHql(){

		Long cnt=dao.count(getHqlWhere(),getQlParam());
		Assert.assertEquals(Long.valueOf(recs.size()), cnt);
	}
	//查询所有(不带排序)
	public void testFindAllWithHql(){

		List<T> rs=dao.findAll(getHqlWhere(),getQlParam());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(0).getId());
	}
	//查询所有(带排序)
	public void testFindAllBySortWithHql(){

		List<T> rs=dao.findAll(getHqlCondition(),getQlParam());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(rs.size()-1).getId());
	}

	
	//查询一定范围内数据(不带排序)
	public void testFindAllByRowWithHql(){
		List<T> rs=dao.findAll(getHqlWhere(),getQlParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(1).getId(), rs.get(0).getId());
	}
	
	//查询一定范围内数据(带排序)
	public void testFindAllByRowAndSortWithHql(){

		List<T> rs=dao.findAll(getHqlCondition(),getQlParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(recs.size()-2).getId(), rs.get(0).getId());
	}
	
	//查询分页数据(不带排序)	
	public void testFindByPageWithHql(){
		SimplePage<T> rs=dao.findByPage(getHqlWhere(),getQlParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(0).getId(), rs.getContent().get(0).getId());
	}

	
	//查询分页数据(带排序)	
	public void testFindByPageBySortWithHql(){
		SimplePage<T> rs=dao.findByPage(getHqlCondition(),getQlParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(recs.size()-1).getId(), rs.getContent().get(0).getId());
	}
	
	//用sql加传参查
	//查询总数
	public void countWithSql(){
		Long cnt=dao.countWithSql(getCountWhereSql(),getQlParam());
		Assert.assertEquals(Long.valueOf(recs.size()), cnt);
	}
	//查询所有(不带排序)
	public void testFindAllWithSql(){
		List<T> rs=dao.findAllWithSql(getQueryWhereSql(),getQlParam());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(0).getId());
	}
	//查询所有(带排序)
	public void testFindAllBySortWithSql(){
		List<T> rs=dao.findAllWithSql(getQueryWhereSortSql(),getQlParam());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(rs.size()-1).getId());
	}
	
	//查询一定范围内数据(不带排序)
	public void testFindAllByRowWithSql(){
		List<T> rs=dao.findAllWithSql(getQueryWhereSql(),getQlParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(1).getId(), rs.get(0).getId());
	}
	
	//查询一定范围内数据(带排序)
	public void testFindAllByRowAndSortWithSql(){
		List<T> rs=dao.findAllWithSql(getQueryWhereSortSql(),getQlParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(recs.size()-2).getId(), rs.get(0).getId());
	}
	
	//查询分页数据(不带排序)	
	public void testFindByPageWithSql(){
		SimplePage<T> rs=dao.findByPageWithSql(getQueryWhereSql(),getQlParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(0).getId(), rs.getContent().get(0).getId());
	}

	
	//查询分页数据(带排序)	
	public void testFindByPageBySortWithSql(){
		SimplePage<T> rs=dao.findByPageWithSql(getQueryWhereSortSql(),getQlParam(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(recs.size()-1).getId(), rs.getContent().get(0).getId());
	}	

	
	//查询唯一值	
	public void testGetSingleResultBySql(){
		
		String rs=dao.getSingleResultBySql(getSingleSql(),getQlParam());
		Assert.assertEquals(getLastRec().getId().toString(), rs);
	}
	
	
}
