package com.wonders.frame.console.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import com.wonders.frame.console.model.bo.Privilege;
import com.wonders.frame.core.dao.GenericRepository;


public interface PrivilegeDao extends GenericRepository<Privilege, Integer> {
	@Query("select a from Privilege a where a.removed = 0 and a.name =?")
	public Privilege findByName(String name);
	
}
