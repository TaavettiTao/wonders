package com.wonders.frame.core.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import com.wonders.frame.core.model.bo.ImportConfig;

public interface ImportConfigDao extends GenericRepository<ImportConfig, Integer> {


	//public Page<ImportConfig> findAll(Pageable pageable);

//	@Query("select a from ImportConfig a where a.removed = 0 and a.id =?")
//	public ImportConfig findById(Integer id);
	

	@Query("select a from ImportConfig a where a.removed = 0 and a.entity =:entity and a.type =:type")
	public List<ImportConfig> findByEntityAndType(@Param("entity") String entity,@Param("type")String type);
	
//	@Transactional
//	@Modifying
//	@Query("update ImportConfig a set a.removed = 1 where a.id =:id")
//	public Integer removeById(@Param("id") Integer id);
	

	// spring-data-jpa默认继承实现的一些方法
	// 该类中的方法不能通过@QueryHint来实现查询缓存。
//	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
//	public List<ImportConfig> findAll();

//	@Query("select a from ImportConfig a where a.removed = 0 and a.entity =:entity and a.type=:type")
//	public List<ImportConfig> findImportRelation(@Param("entity") String entity,@Param("type")String type);

}
