package com.wonders.frame.core.dao;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import com.wonders.frame.core.model.bo.ObjInfo;



public interface ObjInfoDao  extends GenericRepository<ObjInfo, Integer>{

	@Query("select a from ObjInfo a where a.removed=0 and type=?")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public ObjInfo findByType(String type);
			
}