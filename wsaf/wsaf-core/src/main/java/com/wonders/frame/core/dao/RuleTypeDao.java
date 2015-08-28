package com.wonders.frame.core.dao;

import com.wonders.frame.core.model.bo.RuleType;
import org.springframework.data.jpa.repository.Query;
/**
 * 
 * @author taoweiwei
 *
 */
public interface RuleTypeDao extends GenericRepository<RuleType, Integer>{
	
//	@Query("select r from RuleType r where r.removed=0 and r.id=?")
//	public RuleType findById(Integer id);
	
	@Query("select r from RuleType r where r.removed=0 and r.name=?")
	public RuleType findByName(String name);
	
//	@Transactional
//	@Modifying
//	@Query("update RuleType r set r.removed=1 where r.id=?")
//	public Integer removeById(Integer id);

//	@Transactional
//	@Modifying
//	@Query("update RuleType r set r.removed=1 where r.id in (?)")
//	public Integer removeByIds(Integer[] ids);
	
}
