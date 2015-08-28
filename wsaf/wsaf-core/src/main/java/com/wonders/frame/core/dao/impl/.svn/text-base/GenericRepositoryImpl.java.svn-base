package com.wonders.frame.core.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.frame.core.dao.GenericRepository;
import com.wonders.frame.core.model.IRemovedModel;
import com.wonders.frame.core.model.vo.PageInfo;
import com.wonders.frame.core.model.vo.PlaceholderParam;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.SingleHqlElement;
import com.wonders.frame.core.model.vo.SingleQueryParams;
import com.wonders.frame.core.service.SingleHqlBuilderService;
import com.wonders.frame.core.service.SingleHqlBuilderService.SqlComponent;
import com.wonders.frame.core.service.SingleHqlBuilderService.SqlDefinedSign;
import com.wonders.frame.core.utils.JacksonMapper;
import com.wonders.frame.core.utils.ReflectUtil;
import com.wonders.frame.core.utils.SqlBuilderUtil;

/**
 * {@link GenericRepository}接口实现类，并在{@link SimpleJpaRepository}基础上扩展。
 * 
 * @param <T>
 *            ORM对象
 * @param <ID>
 *            主键ID
 */
@NoRepositoryBean
// 必须的
public class GenericRepositoryImpl<T, ID extends Serializable> extends
		SimpleJpaRepository<T, ID> implements GenericRepository<T, ID> {

	static Logger logger = Logger.getLogger(GenericRepositoryImpl.class);
	private final EntityManager em;
	private final Class<T> entityClass;
	private final String entityName;
	private String queryHql;
	private String countHql;

	/**
	 * 构造函数
	 * 
	 * @param domainClass
	 * @param em
	 */
	public GenericRepositoryImpl(
			final JpaEntityInformation<T, ?> entityInformation,
			EntityManager entityManager) {

		super(entityInformation, entityManager);
		this.em = entityManager;
		this.entityClass = entityInformation.getJavaType();
		this.entityName = entityInformation.getEntityName();
		
		initHql();
	}

	/**
	 * 构造函数
	 * 
	 * @param domainClass
	 * @param em
	 */
	public GenericRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getMetadata(domainClass, em), em);

	}
	
	private void initHql(){
		StringBuffer querySb=new StringBuffer(SqlComponent.SELECT.componentExpress()).append(SingleHqlBuilderService.entityAlias);
		StringBuffer countSb=new StringBuffer(SqlComponent.SELECT.componentExpress()).append(SingleHqlBuilderService.COUNT);
		StringBuffer fromHql=new StringBuffer(SqlComponent.FROM.componentExpress()).append(entityName).append(SingleHqlBuilderService.entityAlias);
		StringBuffer whereHql=new StringBuffer(SqlComponent.WHERE.componentExpress());
		if(ReflectUtil.isLogicRemovedObj(entityClass)){
			whereHql.append(SingleHqlBuilderService.entityAlias).append(".").append(SingleHqlBuilderService.logicDelField).append("=0");
		}
		this.queryHql=querySb.append(fromHql).append(whereHql).toString();
		this.countHql=countSb.append(fromHql).append(whereHql).toString();
	}

	@Override
	public T findById(final ID id) {
		return this.findOne(new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				query.where(cb.equal(root.get("id"), id), cb.equal(root
						.get("removed"), 0));

				return null;
			}
		});

	}
	
	@Override
	public T findExistOne() {
		List<T> rs=this.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				query.where(cb.equal(root
						.get("removed"), 0));

				return null;
			}
		});
		
		if(rs.size()>0){
			return rs.get(0);
		}else{
			return null;
		}

	}
	
	@Override
	public List<T> findByIds(Iterable<ID> ids) {
		List<T> rs=new ArrayList<T>();
		if (ids == null) {
			return null;
		}

		for (ID id : ids) {
			T obj=findById(id);
			if(obj!=null){
				rs.add(obj);
			}
		}
		return rs;

	}
	
	@Transactional
	public Integer remove(T entity) {
		if (entity != null) {
			if(entity instanceof IRemovedModel){
				IRemovedModel dm = (IRemovedModel) entity;
				dm.setRemoved(1);
				// this.em.merge(dm);
				this.save(entity);
				return 1;
			}else{
				this.delete(entity);
				return 1;
			}
		} else {
			return 0;
		}
	}
	
	
	@Transactional
	public Integer remove(Iterable<? extends T> entities) {
		Integer n=0;
		if (entities == null) {
			return n;
		}

		for (T entity : entities) {
			Integer r=remove(entity);
			n+=r;
		}
		return n;
	}
	
	

	@Override
	@Transactional
	public Integer removeById(ID id) {
		T entity = this.findById(id);
		return remove(entity);

	}
	
	@Override
	public Integer removeByIds(Iterable<ID> ids) {
		Integer n=0;
		if (ids == null) {
			return n;
		}

		for (ID id : ids) {
			Integer r=removeById(id);
			n+=r;
		}
		return n;

	}
	
	private SingleHqlElement getSingleHqlElement(HashMap<String, String> queryParams,LinkedHashMap<String, String> orderby){
		try{
			SingleQueryParams sqp= new SingleQueryParams(entityClass);
			sqp.setData(queryParams);
			if(orderby!=null){
				sqp.setSort(orderby);
			}
			return SqlBuilderUtil.getSingleHqlBuilder().buildSingleHql(sqp);
		}catch(Exception e){
			logger.error("Exception Throwable", e);
			return null;
		}
	}
	@Override
	public Long count(SingleHqlElement she) {
		Query query = em.createQuery(she.getCountHql());
		setQueryParams(query,she.getParamList());
		return (Long)query.getSingleResult();
	}

	@Override
	public Long count(HashMap<String, String> queryParams) {		
		return count(getSingleHqlElement(queryParams,null));
	}


	@Override
	public List<T> findAll(SingleHqlElement she, Integer rowStartNum,
			Integer rowSize) {
		Query query = em.createQuery(she.getQueryHql());
		setQueryParams(query,she.getParamList());
		
		if (rowStartNum != null && rowSize != null && rowSize != 0) {
			query.setFirstResult(rowStartNum).setMaxResults(rowSize);
		}
		
		return (List<T>) query.getResultList();
	}

	@Override
	public List<T> findAll(SingleHqlElement she) {
		return findAll(she,0,0);
	}


	@Override
	public List<T> findAll(HashMap<String, String> queryParams,
			LinkedHashMap<String, String> orderby, Integer rowStartNum,
			Integer rowSize) {
		return findAll(getSingleHqlElement(queryParams,orderby),rowStartNum,rowSize);
	}

	@Override
	public List<T> findAll(HashMap<String, String> queryParams,
			LinkedHashMap<String, String> orderby) {
		return findAll(getSingleHqlElement(queryParams,orderby));
	}
	




	@Override
	public SimplePage<T> findByPage(SingleHqlElement she, Integer pageNum,
			Integer pageSize) {
		PageInfo pageInfo = getPageInfo(she, pageNum, pageSize);

		List<T> rs = findAll(she, pageInfo
				.getStartRecord(), pageInfo.getPageSize());

		return new SimplePage<T>(pageInfo, rs);
	}

	@Override
	public SimplePage<T> findByPage(HashMap<String, String> queryParams,
			LinkedHashMap<String, String> orderby, Integer pageNum,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return findByPage(getSingleHqlElement(queryParams,orderby),pageNum,pageSize);
	}
	
	private void setQueryParams(Query query, List<PlaceholderParam> queryParams){
		if (queryParams != null && queryParams.size() > 0) {
			for (int i=0;i<queryParams.size();i++) {
				
				PlaceholderParam mqp=queryParams.get(i);
				
				Object value=JacksonMapper.convert(mqp.getFieldValue(), mqp.getFieldType());
				
				query.setParameter(i+1, value);
			}
		}
	}
	
	private PageInfo getPageInfo(SingleHqlElement she, Integer pageNum,
			Integer pageSize){
		
		return getPageInfo(she.getCountHql(),she.getParamList(),pageNum,pageSize);
	}
	
	
	private PageInfo getPageInfo(String hql,List<PlaceholderParam> placeholderParamList, Integer pageNum,
			Integer pageSize){
		
		Query query = em.createQuery(hql);

		setQueryParams(query, placeholderParamList);
		
		PageInfo pageInfo = new PageInfo(((Long) query.getSingleResult())
				.intValue(), pageSize);
		
		pageInfo.refresh(pageNum);
		
		return pageInfo;
	}
	
	/*
	
	@Override
	public Long count(HashMap<String, String> queryParams) {
		String whereHql = buildWhereQuery(queryParams);
		String hql = "select count(*) from " + entityName + " o where o.removed=0 ";
		
		Query query = em.createQuery(hql);

		setQueryParams(query, queryParams);
		return (Long)query.getSingleResult();
	}

	
	@Override
	public List<T> findAll(HashMap<String, String> queryParams,
			LinkedHashMap<String, String> orderby) {

		String whereHql = buildWhereQuery(queryParams);

		String orderHql = buildOrderby(orderby);

		return findAllWithHql(whereHql + orderHql, queryParams, null, null);

	}

	@Override
	public List<T> findAll(HashMap<String, String> queryParams,
			LinkedHashMap<String, String> orderby, Integer rowStartNum,
			Integer rowSize) {

		String whereHql = buildWhereQuery(queryParams);

		String orderHql = buildOrderby(orderby);

		return findAllWithHql(whereHql + orderHql, queryParams, rowStartNum, rowSize);

	}

	@Override
	public List<T> findAllWithHql(String whereHql,
			HashMap<String, String> queryParams, Integer rowNum, Integer rowSize) {

		String hql = "select o from " + entityName + " o where o.removed=0 ";

		return this.getQueryResult(QueryType.HQL, hql + whereHql, queryParams,
				rowNum, rowSize);
	}

	@Override
	public List<T> findAllWithSql(String sql,
			HashMap<String, String> queryParams, Integer rowNum, Integer rowSize) {

		List<T> rs = getQueryResult(QueryType.SQL, sql, queryParams, rowNum,
				rowSize);

		return rs;
	}

	//
	@Override
	public SimplePage<T> findByPage(HashMap<String, String> queryParams,
			LinkedHashMap<String, String> orderby, Integer pageNum,
			Integer pageSize) {

		String whereHql = buildWhereQuery(queryParams);
		String orderHql = buildOrderby(orderby);

		return findByPageWithHql(whereHql + orderHql, queryParams, pageNum,
				pageSize);

	}

	@Override
	public SimplePage<T> findByPageWithHql(String whereHql,
			HashMap<String, String> queryParams, Integer pageNum,
			Integer pageSize) {

		String hql = "select count(*) from " + entityName
				+ " o where o.removed=0 ";

		PageInfo pageInfo = getPageInfo(QueryType.HQL, hql + whereHql,
				queryParams, pageNum, pageSize);

		hql = "select o from " + entityName + " o where o.removed=0 ";

		List<T> rs = getQueryResult(QueryType.HQL, hql + whereHql, queryParams,
				pageInfo.getStartRecord(), pageInfo.getPageSize());

		return new SimplePage<T>(pageInfo, rs);
	}

	@Override
	public SimplePage<T> findByPageWithSql(String sql,
			HashMap<String, String> queryParams, Integer pageNum,
			Integer pageSize) {

		PageInfo pageInfo = getPageInfo(QueryType.SQL, "select count(*) from ("
				+ sql + ")", queryParams, pageNum, pageSize);

		List<T> rs = getQueryResult(QueryType.SQL, sql, queryParams, pageInfo
				.getStartRecord(), pageInfo.getPageSize());

		return new SimplePage<T>(pageInfo, rs);
	}



	private List<T> getQueryResult(QueryType type, String ql,
			HashMap<String, String> queryParams, Integer first, Integer size) {
		Query query;
		switch (type) {
		case SQL:
			query = em.createNativeQuery(ql);
			break;
		default:
			query = em.createQuery(ql);
			break;
		}
		setQueryParams(query, queryParams);
		if (first != null && size != null && size != 0) {
			query.setFirstResult(first).setMaxResults(size);
		}
		return query.getResultList();
	}
	
	private Object getSingleResult(QueryType type, String ql,
			HashMap<String, String> queryParams) {
		Query query;
		switch (type) {
		case SQL:
			query = em.createNativeQuery(ql);
			break;
		default:
			query = em.createQuery(ql);
			break;
		}
		setQueryParams(query, queryParams);
		return query.getSingleResult();
	}

	private String buildWhereQuery(HashMap<String, String> queryParams) {

		StringBuffer whereQueryHql = new StringBuffer("");
		
		if (queryParams != null && queryParams.size() > 0) {
			
			for (String key : queryParams.keySet()) {
				
				String[] keyArr=key.split("_");
				
				Class<?> clazz = ReflectUtil.getFieldType(this.entityClass, keyArr[0]);
				
				if (clazz == null ){continue;}
				
				if(key.equals("removed")){continue;}
											
				String operateTag=" = ";
				
				if(keyArr.length==2){
					if (keyArr[1].equals("s")) {
						operateTag=" >= ";
					} else if (keyArr[1].equals("e")) {
						operateTag=" <= ";
					} else if (keyArr[1].equals("in")) {
						operateTag=" in ";
					} else if (clazz.equals(String.class) && keyArr[1].endsWith("l")) {
						operateTag=" like ";							
					}
				}
				whereQueryHql.append(" and ").append("o.").append(keyArr[0]).append(operateTag).append(":").append(key);
			}
		}

		return whereQueryHql.toString();
	}

	private String buildOrderby(LinkedHashMap<String, String> orderby) {
		// TODO Auto-generated method stub
		StringBuffer orderbyql = new StringBuffer("");
		if (orderby != null && orderby.size() > 0) {
			orderbyql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyql.append("o.").append(key).append(" ").append(
						orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}

		return orderbyql.toString();
	}

	private void setQueryParams(Query query, HashMap<String, String> queryParams) {
		if (queryParams != null && queryParams.size() > 0) {
			for (String key : queryParams.keySet()) {
				
				String[] keyArr=key.split("_");
				
				Class<?> clazz = ReflectUtil.getFieldType(this.entityClass, keyArr[0]);
				
				if (clazz == null ){continue;}
				
				if(key.equals("removed")){continue;}

				Object queryParam = queryParams.get(key);
				
				if(clazz.equals(String.class)) {
					
					if (key.endsWith("_el")) {
						queryParam = '%' + queryParam.toString();
						
					} else if (key.endsWith("_sl")) {
						queryParam = queryParam.toString() + '%';
						
					} else if (key.endsWith("_l")) {
						queryParam = '%' + queryParam.toString() + '%';
						
					} 
				} 
								
				try{
					query.setParameter(key, queryParam);
				}catch(QueryParameterException e){
					continue;
				}

			}
		}
	}
*/
	@Override
	public Long count(String whereHql, HashMap<String, Object> queryParams) {
		String sql=countHql;
		if(whereHql!=null &&!whereHql.equals("")){
			 sql=countHql.concat(SqlDefinedSign.$and.signExpress()).concat(whereHql);
		}
		Query query = em.createQuery(sql);
		for (String key : queryParams.keySet()) {
			query.setParameter(key, queryParams.get(key));
		}
		
		return (Long)query.getSingleResult();
	}

	@Override
	public List<T> findAll(String whereHql, HashMap<String, Object> queryParams) {
		// TODO Auto-generated method stub
		return findAll(whereHql,queryParams,0,0);
	}
	
	@Override
	public List<T> findAll(String whereHql,
			HashMap<String, Object> queryParams, Integer rowStartNum,
			Integer rowSize) {
		String sql=queryHql;
		if(whereHql!=null &&!whereHql.equals("")){
			 sql=queryHql.concat(SqlDefinedSign.$and.signExpress()).concat(whereHql);
		}
		Query query = em.createQuery(sql, entityClass);
		for (String key : queryParams.keySet()) {
			query.setParameter(key, queryParams.get(key));
		}
		
		if (rowStartNum != null && rowSize != null && rowSize != 0) {
			query.setFirstResult(rowStartNum).setMaxResults(rowSize);
		}
		
		return query.getResultList();
	}
	
	@Override
	public SimplePage<T> findByPage(String whereHql,
			HashMap<String, Object> queryParams, Integer pageNum,
			Integer pageSize) {
		String countSql=countHql;
		String querySql=queryHql;
		if(whereHql!=null &&!whereHql.equals("")){
			countSql=countHql.concat(SqlDefinedSign.$and.signExpress()).concat(whereHql);
			querySql=queryHql.concat(SqlDefinedSign.$and.signExpress()).concat(whereHql);
		}
		
		PageInfo pageInfo = getPageInfo(countSql, queryParams, pageNum, pageSize);
		
		List<T> rs=findAll(whereHql,queryParams,pageInfo.getStartRecord(),pageInfo.getPageSize());
		
		return new SimplePage<T>(pageInfo, rs);
	}

	private PageInfo getPageInfo(String hql,HashMap<String,Object> queryParams, Integer pageNum,
			Integer pageSize){
		
		Query query = em.createQuery(hql);

		for (String key : queryParams.keySet()) {
			query.setParameter(key, queryParams.get(key));
		}
		
		PageInfo pageInfo = new PageInfo(((Long) query.getSingleResult())
				.intValue(), pageSize);
		
		pageInfo.refresh(pageNum);
		
		return pageInfo;
	}
	
	/*********************************sql查询******************************************************/
	
	@Override
	public Long countWithSql(String sql,
			HashMap<String, Object> queryParams) {
		Query query = em.createNativeQuery(sql);
		for (String key : queryParams.keySet()) {
			query.setParameter(key, queryParams.get(key));
		}
		return ((BigDecimal) query.getSingleResult()).longValue();

	}
	
	@Override
	public SimplePage<T> findByPageWithSql(String sql,
			HashMap<String, Object> queryParams, Integer pageNum,
			Integer pageSize) {
		PageInfo pageInfo = getPageInfoWithSql("select count(*) from ("
				+ sql + ")", queryParams, pageNum, pageSize);

		
		List<T> rs=findAllWithSql(sql,queryParams,pageInfo.getStartRecord(),pageInfo.getPageSize());
		
		return new SimplePage<T>(pageInfo, rs);

	}
	
	private PageInfo getPageInfoWithSql(String sql,
			HashMap<String,Object> queryParams, Integer pageNum,
			Integer pageSize){
		
		Query query = em.createNativeQuery(sql);

		for (String key : queryParams.keySet()) {
			query.setParameter(key, queryParams.get(key));
		}
		
		PageInfo pageInfo = new PageInfo(((BigDecimal) query.getSingleResult())
				.intValue(), pageSize);
		pageInfo.refresh(pageNum);
		return pageInfo;
	}
	@Override
	public List<T> findAllWithSql(String sql,HashMap<String,Object> queryParams) {
		
		return findAllWithSql(sql,queryParams,0,0);
	}
	
	@Override
	public List<T> findAllWithSql(String sql,
			HashMap<String, Object> queryParams, Integer rowStartNum,
			Integer rowSize) {
		
		Query query = em.createNativeQuery(sql, entityClass);
		for (String key : queryParams.keySet()) {
			query.setParameter(key, queryParams.get(key));
		}
		
		if (rowStartNum != null && rowSize != null && rowSize != 0) {
			query.setFirstResult(rowStartNum).setMaxResults(rowSize);
		}
		
		return query.getResultList();
		//SQLQuery nativeQuery=query.unwrap(SQLQuery.class); 
		
		//return (List<T>)nativeQuery.setResultTransformer(Transformers.aliasToBean(entityClass)).list();
	}

	public String getSingleResultBySql(String sql,
			HashMap<String, Object> queryParams) {
		Query query = em.createNativeQuery(sql);
		for (String key : queryParams.keySet()) {
			query.setParameter(key, queryParams.get(key));
		}
		return query.getSingleResult().toString();
	}







}