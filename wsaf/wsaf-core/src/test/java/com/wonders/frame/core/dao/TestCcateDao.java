package com.wonders.frame.core.dao;

import javax.annotation.Resource;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wonders.frame.core.model.bo.Ccate;

public class TestCcateDao extends AbstractTestDao<Ccate> {
	private  Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public CcateDao dao;
	
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
		testFindByType();
		
	}
	
	public void testFindByType() {		
		Ccate rec=dao.findByType(getFirstRec().getType());
		Assert.assertNotNull(rec);
		Assert.assertEquals(getFirstId(),rec.getId());
	}

	@Override
	public Ccate buildRec(String index) {
		return new Ccate("测试type"+index,"测试name"+index,"测试description"+index);		
	}

	@Override
	public void modifyRec(Ccate rec) {
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
		this.setSortParam("id", "desc");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSqlCondition() {
		this.setSqlCondition("o.removed=0 and o.NAME like :name||'%' order by o.id desc");
		
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


/*
	private List<Ccate> recs=new ArrayList<Ccate>();
	
	public List<Ccate> getSubRecs(int i){
		List<Ccate> subRecs=new ArrayList<Ccate>();
		for(int j=0;j<i;j++){
			subRecs.add(recs.get(j));		
		}
		
		return subRecs;
	}
	
	
	public List<Integer> getIds(List<Ccate> recList){
		List<Integer> ids=new ArrayList<Integer>();
		for(Ccate rec:recList){
			ids.add(rec.getId());		
		}
		
		return ids;
	}
	public Ccate getFirstRec(){
		return recs.get(0);
	}
	
	public Ccate getLastRec(){
		return recs.get(recs.size()-1);
	}
	
	public Integer getFirstId(){
		return recs.get(0).getId();
	}
	
	public Integer getLastId(){
		return recs.get(recs.size()-1).getId();
	}
	//@Before
	public void setDao(){
		logger.info(this.dao.toString());
	}
	//@Test
	//@Rollback(false)
	public void testDao() {
//		dao2.deleteAll();
//		dao.deleteAll();
		
		testAddRecInBatch(5);//批量新增
		
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
		
		testFindAllByRowAndSortWithql();//查询一定范围内数据(带排序)
		
		testFindByPageWithSql();//查询分页数据(不带排序)	

		testFindByPageBySortWithSql();//查询分页数据(带排序)	

		testGetSingleResultBySql();//查询唯一值	
		

		testFindByType();//根据类型获取

		testUpdateRec();	//修改
		
		testFindById();	//按ID查找
		
		testFindExistOne();	//查存在的任意一个

		testFindByIds();	//按一组ID查

		testRemoveById();//按ID做逻辑删除
		
		testRemoveByIds();//按一组ID做逻辑删除
				
		testRemove();//按对象做逻辑删除
		
		testRemoveEntitys();//按一组对象做逻辑删除

	}

	public void testAddRecInBatch(int i){
		for(int x=1;x<=i;x++){
			testAddRec(x);
		}
	}
	//新增
	public Ccate buildRec(String index){
		return new Ccate("test"+index,"测试测试"+index,"测试测试字典类"+index);
	}
	
	public void testAddRec(int i) {//调用addRec实现具体序列数据的插入
		String index=i+"";
		if(i<10){index="0"+index;}
		Ccate rec=buildRec(index);		
		rec=dao.save(rec);
		Assert.assertNotNull(rec.getId());
		logger.info("insert new rec,id={}",rec.getId());
		recs.add(rec);

	}

	
	//根据类型获取
	public void testFindByType() {		
		Ccate rec=dao.findByType(getFirstRec().getType());
		Assert.assertNotNull(rec);
		Assert.assertEquals(getFirstId(),rec.getId());
	}
	
	//修改
	public void testUpdateRec() {
		
		Ccate rec=getFirstRec();
		
		modifyRec(rec);
		
		rec=dao.save(rec);	
		
		Assert.assertNotNull(rec);

		Ccate newRec=dao.findById(rec.getId());
		
		Assert.assertEquals(rec, newRec);
		
		recs.remove(0);

	}
	
	public void modifyRec (Ccate rec){
		rec.setName("测试修改");
	}
	
	//按ID查找
	public void testFindById() {		
		Ccate rec=dao.findById(getFirstId());	
		Assert.assertNotNull(rec);
		Assert.assertEquals(getFirstRec(),rec);
	}
	
	//查唯一的一个
	public void testFindExistOne(){
		
		Ccate rec= dao.findExistOne();
		
		Assert.assertNotNull(rec);
		
		logger.info("findExistOne:"+TestJacksonMapper.toJson(rec));
	}
	//按一组ID查
	public void testFindByIds(){
		List<Ccate> subList= getSubRecs(2);
		List<Ccate> rs=dao.findByIds(getIds(subList));
		Assert.assertEquals(subList.size(),rs.size());
		Assert.assertEquals(subList, rs);

	}
		
	public HashMap<String,String> getQueryParams(){
		HashMap<String,String> rpm=new HashMap<String,String>();
		rpm.put("name_sl", "测试测试");
		return rpm;
	}
	
	public HashMap<String,String> getSortQueryParams(){
		HashMap<String,String> rpm=getQueryParams();
		rpm.put("sort", "name desc");
		return rpm;
	}
	

	public LinkedHashMap<String,String> getSortMap(){
		LinkedHashMap<String,String> rpm=new LinkedHashMap<String,String>();
		rpm.put("name", "desc");
		return rpm;
	}
	public SingleHqlElement getSHE(HashMap<String,String> rpm){
		try{
			SingleQueryParams sqp=TestSingleParamsConvertUtil.getQueryParams(Ccate.class, rpm);
			SingleHqlElement she=TestSqlBuilderUtil.getSingleHqlBuilder().buildSingleHql(sqp);
			return she;
		}catch(Exception e){
			return null;
		}
	}

	
	public String getHql(){
		return "o.name like :name||'%'";
	}
	
	public String getHqlBySort(){
		return getHql().concat(" order by o.name desc");
	}
	
	
	public String getCountSql(){
		return "select count(*) from af_"+StringUtils.uncapitalize(Ccate.class.getSimpleName())+" o where o.name like :name||'%' and o.removed=0";
	}
	
	
	public String getSql(){
		return "select * from af_"+StringUtils.uncapitalize(Ccate.class.getSimpleName())+" o where o.name like :name||'%' and o.removed=0";
	}

	public String getSqlBySort(){
		return getSql().concat(" order by o.name desc");
	}
	public String getSingleSql(){
		return "select max(id) from af_"+StringUtils.uncapitalize(Ccate.class.getSimpleName())+" o where o.name like :name||'%' and o.removed=0";
	}
	
	public HashMap<String,Object> getQueryParams2(){
		HashMap<String,Object> rpm=new HashMap<String,Object>();
		rpm.put("name", "测试测试");
		return rpm;
	}
	
	//用自定义的简单hql元素对象查
	//查询总数
	public void countWithSHE(){		
		Long cnt=dao.count(getSHE(getQueryParams()));
		Assert.assertEquals(Long.valueOf(recs.size()), cnt);
	}
	//查询所有(不带排序)
	public void testFindAllWithSHE(){
		
		List<Ccate> rs=dao.findAll(getSHE(getQueryParams()));
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
	}
	//查询所有(带排序)
	public void testFindAllBySortWithSHE(){		
		List<Ccate> rs=dao.findAll(getSHE(getSortQueryParams()));

		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(rs.size()-1).getId());
	}

	
	//查询一定范围内数据(不带排序)
	public void testFindAllByRowWithSHE(){
		
		List<Ccate> rs=dao.findAll(getSHE(getQueryParams()),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(1).getId(), rs.get(0).getId());
	}
	
	//查询一定范围内数据(带排序)
	public void testFindAllByRowAndSortWithSHE(){
		List<Ccate> rs=dao.findAll(getSHE(getSortQueryParams()),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(recs.size()-2).getId(), rs.get(0).getId());
	}
	
	//查询分页数据(不带排序)	
	public void testFindByPageWithSHE(){
		SimplePage<Ccate> rs=dao.findByPage(getSHE(getQueryParams()),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(0).getId(), rs.getContent().get(0).getId());
	}

	
	//查询分页数据(带排序)	
	public void testFindByPageBySortWithSHE(){
		SimplePage<Ccate> rs=dao.findByPage(getSHE(getSortQueryParams()),1,3);
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
		List<Ccate> rs=dao.findAll(getQueryParams(),null);
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
	}
	//查询所有(带排序)
	public void testFindAllBySortWithMap(){
		List<Ccate> rs=dao.findAll(getQueryParams(),getSortMap());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(rs.size()-1).getId());
	}

	
	//查询一定范围内数据(不带排序)
	public void testFindAllByRowWithMap(){
		List<Ccate> rs=dao.findAll(getQueryParams(),null,1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(1).getId(), rs.get(0).getId());
	}
	
	//查询一定范围内数据(带排序)
	public void testFindAllByRowAndSortWithMap(){
		List<Ccate> rs=dao.findAll(getQueryParams(),getSortMap(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(recs.size()-2).getId(), rs.get(0).getId());
	}
	
	//查询分页数据(不带排序)	
	public void testFindByPageWithMap(){
		SimplePage<Ccate> rs=dao.findByPage(getQueryParams(),null,1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(0).getId(), rs.getContent().get(0).getId());
	}

	
	//查询分页数据(带排序)	
	public void testFindByPageBySortWithMap(){
		SimplePage<Ccate> rs=dao.findByPage(getQueryParams(),getSortMap(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(recs.size()-1).getId(), rs.getContent().get(0).getId());
	}
	
	//用hql加传参查
	//查询总数
	public void countWithHql(){

		Long cnt=dao.count(getHql(),getQueryParams2());
		Assert.assertEquals(Long.valueOf(recs.size()), cnt);
	}
	//查询所有(不带排序)
	public void testFindAllWithHql(){

		List<Ccate> rs=dao.findAll(getHql(),getQueryParams2());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
	}
	//查询所有(带排序)
	public void testFindAllBySortWithHql(){

		List<Ccate> rs=dao.findAll(getHqlBySort(),getQueryParams2());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(rs.size()-1).getId());
	}

	
	//查询一定范围内数据(不带排序)
	public void testFindAllByRowWithHql(){
		List<Ccate> rs=dao.findAll(getHql(),getQueryParams2(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(1).getId(), rs.get(0).getId());
	}
	
	//查询一定范围内数据(带排序)
	public void testFindAllByRowAndSortWithHql(){

		List<Ccate> rs=dao.findAll(getHqlBySort(),getQueryParams2(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(recs.size()-2).getId(), rs.get(0).getId());
	}
	
	//查询分页数据(不带排序)	
	public void testFindByPageWithHql(){
		SimplePage<Ccate> rs=dao.findByPage(getHql(),getQueryParams2(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(0).getId(), rs.getContent().get(0).getId());
	}

	
	//查询分页数据(带排序)	
	public void testFindByPageBySortWithHql(){
		SimplePage<Ccate> rs=dao.findByPage(getHqlBySort(),getQueryParams2(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(recs.size()-1).getId(), rs.getContent().get(0).getId());
	}
	
	//用sql加传参查
	//查询总数
	public void countWithSql(){
		Long cnt=dao.countWithSql(getCountSql(),getQueryParams2());
		Assert.assertEquals(Long.valueOf(recs.size()), cnt);
	}
	//查询所有(不带排序)
	public void testFindAllWithSql(){
		List<Ccate> rs=dao.findAllWithSql(getSql(),getQueryParams2());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
	}
	//查询所有(带排序)
	public void testFindAllBySortWithSql(){
		List<Ccate> rs=dao.findAllWithSql(getSqlBySort(),getQueryParams2());
		Assert.assertNotNull(rs);
		Assert.assertEquals(recs.size(), rs.size());
		Assert.assertEquals(getFirstRec().getId(), rs.get(rs.size()-1).getId());
	}
	
	//查询一定范围内数据(不带排序)
	public void testFindAllByRowWithSql(){
		List<Ccate> rs=dao.findAllWithSql(getSql(),getQueryParams2(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(1).getId(), rs.get(0).getId());
	}
	
	//查询一定范围内数据(带排序)
	public void testFindAllByRowAndSortWithql(){
		List<Ccate> rs=dao.findAllWithSql(getSqlBySort(),getQueryParams2(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertEquals(3, rs.size());
		Assert.assertEquals(recs.get(recs.size()-2).getId(), rs.get(0).getId());
	}
	
	//查询分页数据(不带排序)	
	public void testFindByPageWithSql(){
		SimplePage<Ccate> rs=dao.findByPageWithSql(getSql(),getQueryParams2(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(0).getId(), rs.getContent().get(0).getId());
	}

	
	//查询分页数据(带排序)	
	public void testFindByPageBySortWithSql(){
		SimplePage<Ccate> rs=dao.findByPageWithSql(getSqlBySort(),getQueryParams2(),1,3);
		Assert.assertNotNull(rs);
		Assert.assertNotNull(rs.getContent());
		Assert.assertEquals(3, rs.getContent().size());
		Assert.assertEquals(recs.get(recs.size()-1).getId(), rs.getContent().get(0).getId());
	}	

	
	//查询唯一值	
	public void testGetSingleResultBySql(){
		
		String rs=dao.getSingleResultBySql(getSingleSql(),getQueryParams2());
		Assert.assertEquals(getLastRec().getId().toString(), rs);
	}
	
	

	
	//按ID做逻辑删除
	public void testRemoveById(){		
		int num=dao.removeById(getFirstId());
		Assert.assertEquals(1,num);
		recs.remove(0);
	}
	

	//按一组ID做逻辑删除
	public void testRemoveByIds(){	
		List<Ccate> subList=getSubRecs(2);
		int num=dao.removeByIds(getIds(subList));
		Assert.assertEquals(subList.size(),num);
		recs.removeAll(subList);
	}

	//按对象做逻辑删除
	public void testRemove(){		
		int num=dao.remove(getFirstRec());
		Assert.assertEquals(1,num);
		recs.remove(0);
	}

	
	//按一组对象做逻辑删除
	public void testRemoveEntitys(){		
		int num=dao.remove(recs);
		Assert.assertEquals(recs.size(),num);
	}
	*/


}
