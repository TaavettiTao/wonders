package com.wonders.frame.complex.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;































import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.wonders.frame.complex.dao.ComplexQueryDao;
import com.wonders.frame.complex.model.vo.ComplexQuery;
import com.wonders.frame.complex.model.vo.and.JointQuery;
import com.wonders.frame.core.dao.GenericRepository.QueryType;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.PageInfo;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.utils.DateFormatUtil;
import com.wonders.frame.core.utils.ReflectUtil;
import com.wonders.frame.core.utils.DateFormatUtil.DataFormat;

@Repository("complexQueryDao")
public class ComplexQueryDaoImpl implements ComplexQueryDao{
	@PersistenceContext
	private EntityManager em;
	
	
	// find by seial
	public List<Object> findAll(String sql, LinkedHashMap<Integer, Object> queryParams,Integer rowNum, Integer rowSize) {

		return findAllWithHql(sql,queryParams, rowNum, rowSize);

	}

	public List<Object> findAllWithHql(String sql, LinkedHashMap<Integer, Object> queryParams, Integer rowNum, Integer rowSize) {

		List<Object> rs = getQueryResult(QueryType.SQL, sql, queryParams, rowNum, rowSize);
		return rs;
	}
	
	//
	public SimplePage<Object> findByPage(String hql, LinkedHashMap<Integer, Object> queryParams,Integer pageNum,Integer pageSize) {

		return findByPageWithHql(hql, queryParams,pageNum,pageSize);

	}
	
	public SimplePage<Object> findByPageWithHql(String sql,LinkedHashMap<Integer, Object> queryParams, Integer pageNum,Integer pageSize) {

		String tmp = sql.substring(sql.indexOf("select"), sql.indexOf("from"));
		String hqlCount = sql.toString().replace(tmp, "select count(*) ");

		PageInfo pageInfo = getPageInfo(QueryType.SQL, hqlCount,pageNum, pageSize);

		List<Object> rs = getQueryResult(QueryType.SQL,sql,queryParams,pageInfo.getStartRecord(), pageInfo.getPageSize());

		return new SimplePage<Object>(pageInfo, rs);
	}
	
	private List<Object> getQueryResult(QueryType type,String ql, LinkedHashMap<Integer, Object> queryParams, Integer first, Integer size) {
		Query query;
		switch (type) {
		case SQL:
//			ql = "select * from af_role_old t where t.OPERATE_DATE = ?1";
			query = em.createNativeQuery(ql);
//			Date ss = DateFormatUtil.timeParse(DateFormatUtil.DATETIME_FORMAT, "2015-5-1 12:00:00");
			if (queryParams != null && queryParams.size() > 0) {
				
				for (Integer key : queryParams.keySet()) {
					query.setParameter(key, queryParams.get(key));
				}
			}
			
			break;
		default:
			query = em.createQuery(ql);
			break;
		}
		
		if (first != null && size != null && size != 0) {
			query.setFirstResult(first).setMaxResults(size);
		}
		return query.getResultList();
	}
	
	
	private PageInfo getPageInfo(QueryType type,String ql,Integer pageNum,
			Integer pageSize) {
		Query query;
//		String sql = "select r.ID as rid from  ( af_role_old )  r where  r.removed=0  and r.OPERATE_DATE = (to_date('2015/5/1 12:00:00','yyyy-MM-dd HH24:mi:ss')) ";
		switch (type) {
		case SQL:
			query = em.createNativeQuery(ql);
			break;
		default:
			query = em.createQuery(ql);
			break;
		}
		int totalRecord = Integer.parseInt(query.getSingleResult().toString());
		PageInfo pageInfo = new PageInfo(totalRecord, pageSize);
		pageInfo.refresh(pageNum);
		return pageInfo;
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
	
}
