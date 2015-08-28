package com.wonders.frame.console.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import com.wonders.frame.console.model.bo.Group;
import com.wonders.frame.core.dao.GenericRepository;

public interface GroupDao extends GenericRepository<Group, Integer> {
	@Query("select a from Group a where a.removed = 0 and a.name =?")
	public Group findByName(String name);

}
