package com.wonders.frame.core.dao;

import java.util.List;

import com.wonders.frame.core.model.bo.Relation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RelationDao  extends GenericRepository<Relation, Integer>{
	@Query("select t from Relation t where t.removed = 0 and t.ruleTypeId=:ruleTypeId and t.ptype =:type and t.pid in:ids")
	public List<Relation> findNext(@Param("ruleTypeId")Integer ruleTypeId, @Param("type")String type,@Param("ids")List<Integer> ids);
	
	@Query("select t from Relation t where t.removed = 0 and t.ruleTypeId=:ruleTypeId and t.ntype =:type and t.nid in:ids")
	public List<Relation> findPrevious(@Param("ruleTypeId")Integer ruleTypeId, @Param("type")String type,@Param("ids")List<Integer> ids);
	
	public List<Relation> findAllChild(Integer ruleTypeId, String type,Integer id);
	
	
	public List<Relation> findAllParent(Integer ruleTypeId, String type,Integer id);
	
	
	public List<Relation> findTree(Integer ruleTypeId, String treeType,Integer rootNodeId);
	
	public List<Relation> findReverseTree(Integer ruleTypeId, String treeType,Integer leafNodeId);
	

}