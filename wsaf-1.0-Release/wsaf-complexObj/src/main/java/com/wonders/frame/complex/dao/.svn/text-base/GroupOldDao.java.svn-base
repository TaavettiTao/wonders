package com.wonders.frame.complex.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.frame.complex.model.bo.GroupOld;
import com.wonders.frame.core.dao.GenericRepository;

public interface GroupOldDao extends GenericRepository<GroupOld, Integer> {
	public GroupOld findByName(String name);
	@Query("select a from GroupOld a where a.removed = 0 and a.id =?")
	public GroupOld findById(Integer id);
	
	@Transactional  
	@Modifying 
	@Query("update GroupOld a set a.removed = 1 where a.id =:id")
	public Integer logicDeleteById(@Param("id")Integer id);

	public List<GroupOld> findAll();
	
	public GroupOld findOne(Integer id);
//
//	@Transactional
//	public GroupOld save(GroupOld group);
//
//	public void delete(Integer id);

//	public Page<GroupOld> findByOwnerUsername(String username, Pageable pageable);
}
