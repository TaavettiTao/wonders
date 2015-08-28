package com.wonders.frame.complex.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.wonders.frame.complex.model.vo.ComplexQuery;
import com.wonders.frame.complex.model.vo.and.JointQuery;
import com.wonders.frame.core.dao.GenericRepository;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.SimplePage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ComplexQueryDao {
//	@Query("select t from Relation t where t.removed = 0 and t.ruleTypeId=:ruleTypeId and t.ptype =:type and t.pid in:ids")
//	public List<Relation> findNext(@Param("ruleTypeId")Integer ruleTypeId, @Param("type")String type,@Param("ids")List<Integer> ids);
//	
//	@Query("select t from Relation t where t.removed = 0 and t.ruleTypeId=:ruleTypeId and t.ntype =:type and t.nid in:ids")
//	public List<Relation> findPrevious(@Param("ruleTypeId")Integer ruleTypeId, @Param("type")String type,@Param("ids")List<Integer> ids);
//	
//	public List<Relation> findAllChild(Integer ruleTypeId, String type,Integer id);
//	
//	
//	public List<Relation> findAllParent(Integer ruleTypeId, String type,Integer id);
//	
//	
//	public List<Relation> findTree(Integer ruleTypeId, String treeType,Integer rootNodeId);
//	
//	public List<Relation> findReverseTree(Integer ruleTypeId, String treeType,Integer leafNodeId);
	
	public SimplePage<Object> findByPage(String hql, LinkedHashMap<Integer, Object> valueMap, Integer pageNum,Integer pageSize);
	
	public List<Object> findAll(String hql, LinkedHashMap<Integer, Object> valueMap, Integer rowNum, Integer rowSize);
	
}   