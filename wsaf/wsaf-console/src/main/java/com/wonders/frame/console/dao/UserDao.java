package com.wonders.frame.console.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.core.dao.GenericRepository;

public interface UserDao extends GenericRepository<User, Integer>{
	@Query("select a from User a where a.removed = 0 and a.loginName =?")
	public List<User> findByLoginName(String loginName);
	
	@Query("select a from User a where a.removed = 0 and a.name =?")
	public User findByName(String name);
	

	
 	public long count();

	public Page<User> findAll(Pageable pageable);

	
	@Query("select a from User a where a.removed=0 and a.name=?1 and a.password=?2")
	public User findUserByNameAndPassword(String name,String password);
	
	// spring-data-jpa默认继承实现的一些方法
    // 该类中的方法不能通过@QueryHint来实现查询缓存。  
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })  
    public List<User> findAll();  
      
    @Query("from User")  
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })  
    public List<User> findAllCached();
    
	@Query("select t from User t where t.removed=0 and t.name = ?1")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public User findUserByName(String name);
}
