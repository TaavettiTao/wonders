package com.wonders.frame.complex.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.frame.complex.model.bo.UserOld;
import com.wonders.frame.core.dao.GenericRepository;

public interface UserOldDao extends GenericRepository<UserOld, Integer>{
	public UserOld findByName(String name);
	
 	public long count();

	public Page<UserOld> findAll(Pageable pageable);
		
	@Query("select a from UserOld a where a.removed = 0 and a.id =?")
	public UserOld findById(Integer id);
	
	@Transactional  
	@Modifying 
	@Query("update UserOld a set a.removed = 1 where a.id =:id")
	public Integer logicDeleteById(@Param("id")Integer id);
	
	
	// spring-data-jpa默认继承实现的一些方法
    // 该类中的方法不能通过@QueryHint来实现查询缓存。  
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })  
    public List<UserOld> findAll();  
      
    @Query("from UserOld")  
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })  
    public List<UserOld> findAllCached();
    
	@Query("select t from UserOld t where t.name = ?1")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public UserOld findUserByName(String name);
	

	public UserOld findOne(Integer id);

	@Query("select t from UserOld t where t.id = ?1")  
	public List<UserOld> findByParentId(Integer id);
}
