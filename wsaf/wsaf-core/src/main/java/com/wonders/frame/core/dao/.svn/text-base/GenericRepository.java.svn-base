package com.wonders.frame.core.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.SingleHqlElement;



/**

 * @author mengjie

 * 针对spring data jpa所提供的接口{@link JpaRepository}再次扩展

 * @NoRepositoryBean是必须的

 */

@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> 
	extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {
	
	public T findById(ID id);
	
	public T findExistOne();
	
	public List<T> findByIds(Iterable<ID> ids);
	
	public Integer remove(T entity);
	
	public Integer remove(Iterable<? extends T> entities);

	public Integer removeById(ID id);

	public Integer removeByIds(Iterable<ID> ids);
	
	
	public Long count(SingleHqlElement she);
	
	public List<T> findAll(SingleHqlElement she);
	
	public List<T> findAll(SingleHqlElement she,Integer rowStartNum,
			Integer rowSize);
		
	public SimplePage<T> findByPage(SingleHqlElement she,Integer pageNum,
			Integer pageSize);
	
	
	public Long count(HashMap<String, String> queryParams);
	
	public List<T> findAll(HashMap<String, String> queryParams,
			LinkedHashMap<String, String> orderby);
	
	public List<T> findAll(HashMap<String, String> queryParams,
			LinkedHashMap<String, String> orderby, Integer rowStartNum,
			Integer rowSize);
	
	public SimplePage<T> findByPage(HashMap<String,String> queryParams,
			LinkedHashMap<String, String> orderby, Integer pageNum,Integer pageSize);
	

	public Long count(String whereHql,HashMap<String, Object> queryParams);
	
	public List<T> findAll(String whereHql,
			HashMap<String, Object> queryParams);
	
	public List<T> findAll(String whereHql,
			HashMap<String, Object> queryParams, Integer rowStartNum,
			Integer rowSize);
	
	public SimplePage<T> findByPage(String whereHql,
			HashMap<String, Object> queryParams, Integer pageNum,
			Integer pageSize);
	
	public Long countWithSql(String sql,HashMap<String,Object> queryParams);	
	
	public List<T> findAllWithSql(String sql,HashMap<String,Object> queryParams);	
	
	public List<T> findAllWithSql(String sql, HashMap<String, Object> queryParams,
			Integer rowStartNum, Integer rowSize);
	

	
	public SimplePage<T> findByPageWithSql(String sql,
			HashMap<String, Object> queryParams, Integer pageNum,
			Integer pageSize);
		
	public String getSingleResultBySql(String sql,HashMap<String,Object> queryParams);



	











}
