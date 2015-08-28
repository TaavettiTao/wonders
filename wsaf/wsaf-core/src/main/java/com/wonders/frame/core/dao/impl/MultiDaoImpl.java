package com.wonders.frame.core.dao.impl;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;


import com.wonders.frame.core.dao.MultiDao;
import com.wonders.frame.core.model.vo.PlaceholderParam;
import com.wonders.frame.core.model.vo.PageInfo;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.MultiQuerySqlElement;
import com.wonders.frame.core.utils.JacksonMapper;

@Repository("multipleDao")
public class MultiDaoImpl implements MultiDao{
	@PersistenceContext
	private EntityManager em;
	
	public SimplePage<HashMap<String,String>> findByPage(MultiQuerySqlElement se,Integer pageNum,Integer pageSize) throws Exception{
		PageInfo pageInfo = getPageInfo("select count(*) from ("
				+ se.getSql() + ")", se.getParamList(), pageNum, pageSize);

		List<HashMap<String,String>> rs = getQueryResult(se, pageInfo
				.getStartRecord(), pageInfo.getPageSize());

		return new SimplePage<HashMap<String,String>>(pageInfo, rs);
	}
	
	public List<HashMap<String,String>> findAll(MultiQuerySqlElement se, Integer rowNum, Integer rowSize) throws Exception{
		List<HashMap<String,String>> rs = getQueryResult(se, rowNum, rowSize);
		return rs;
	}
	
	private PageInfo getPageInfo(String ql,
			List<PlaceholderParam> queryParams, Integer pageNum,
			Integer pageSize)  throws Exception{
		
		Query query = em.createNativeQuery(ql);

		setQueryParams(query, queryParams);
		
		PageInfo pageInfo = new PageInfo(((BigDecimal) query.getSingleResult())
				.intValue(), pageSize);
		pageInfo.refresh(pageNum);
		return pageInfo;
	}

	private List<HashMap<String,String>> getQueryResult(MultiQuerySqlElement se, Integer first, Integer size)  throws Exception{
		
		Query query = em.createNativeQuery(se.getSql());
		
		setQueryParams(query, se.getParamList());
		
		if (first != null && size != null && size != 0) {
			query.setFirstResult(first).setMaxResults(size);
		}
		SQLQuery nativeQuery=query.unwrap(SQLQuery.class);

		setSelectFields(nativeQuery,se.getFieldList());
		
		return nativeQuery.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		//return query.getResultList();
	}
	private void setSelectFields(SQLQuery query, List<String> selectFields) throws Exception{
		if (selectFields != null && selectFields.size() > 0) {
			for (int i=0;i<selectFields.size();i++) {
				String fieldName=selectFields.get(i);
				String aliasNum="_"+i;
				query.addScalar(fieldName.concat(aliasNum),StandardBasicTypes.STRING);
			}
		}
	}
	private void setQueryParams(Query query, List<PlaceholderParam> queryParams) throws Exception{
		if (queryParams != null && queryParams.size() > 0) {
			for (int i=0;i<queryParams.size();i++) {
				PlaceholderParam mqp=queryParams.get(i);

				Object value=JacksonMapper.convert(mqp.getFieldValue(), mqp.getFieldType());
		
				query.setParameter(i+1, value);
			}
		}
	}
	
	private Type getStandardBasicTypes(Class<?> clazz){
		if(clazz.equals(java.util.Date.class)||clazz.equals(java.sql.Date.class)){
			return StandardBasicTypes.DATE;
		}else if(clazz.equals(Time.class)){
			return StandardBasicTypes.TIME;
		}else if(clazz.equals(Timestamp.class)){
			return StandardBasicTypes.TIMESTAMP;
		}else if(clazz.equals(Integer.class)){
			return StandardBasicTypes.INTEGER;
		}else if(clazz.equals(BigDecimal.class)){
			return StandardBasicTypes.BIG_DECIMAL;
		}else if(clazz.equals(Long.class)){
			return StandardBasicTypes.LONG;
		}else if(clazz.equals(Short.class)){
			return StandardBasicTypes.SHORT;
		}else if(clazz.equals(Double.class)){
			return StandardBasicTypes.DOUBLE;
		}else if(clazz.equals(Float.class)){
			return StandardBasicTypes.FLOAT;
		}else if(clazz.equals(Boolean.class)){
			return StandardBasicTypes.BOOLEAN;
		}else if(clazz.equals(Clob.class)){
			return StandardBasicTypes.CLOB;
		}else if(clazz.equals(Blob.class)){
			return StandardBasicTypes.BLOB;
		}else{
			return StandardBasicTypes.STRING;
		}
	}
}
