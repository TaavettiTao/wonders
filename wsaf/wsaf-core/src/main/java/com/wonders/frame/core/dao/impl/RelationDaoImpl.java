package com.wonders.frame.core.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import com.wonders.frame.core.model.bo.Relation;

public class RelationDaoImpl {
	@PersistenceContext
	private EntityManager em;
	

	public List<Relation> findAllChild(Integer ruleTypeId, String type,Integer id){
		String sql="select t.* from (select a.* from af_relation a where a.removed = 0 and a.rule_type_id=:ruleTypeId) t start with t.p_type=:type and t.p_id=:id connect by prior t.n_type=t.p_type and prior t.n_id=t.p_id";
		Query query=em.createNativeQuery(sql, Relation.class);
		query.setParameter("ruleTypeId", ruleTypeId).setParameter("type", type).setParameter("id", id);
		List<Relation> relations=query.getResultList();
		return relations;
		
	}
	
	public List<Relation> findAllParent(Integer ruleTypeId, String type,Integer id){
		String sql="select t.* from (select a.* from af_relation a where a.removed = 0 and a.rule_type_id=:ruleTypeId) t start with t.n_type=:type and t.n_id=:id connect by t.n_type= prior t.p_type and t.n_id= prior t.p_id";
		Query query=em.createNativeQuery(sql, Relation.class);
		query.setParameter("ruleTypeId", ruleTypeId).setParameter("type", type).setParameter("id", id);
		List<Relation> relations=query.getResultList();
		return relations;
		
	}
	
	public List<Relation> findTree(Integer ruleTypeId, String treeType,Integer rootNodeId){
		String sql="select t.* from (select a.* from af_relation a where a.removed = 0 and a.rule_type_id=:ruleTypeId and a.p_type=a.n_type and a.p_type=:treeType) t start with t.p_type=:rootNodeType and t.p_id=:rootNodeId connect by prior t.n_type=t.p_type and prior t.n_id=t.p_id";
		Query query=em.createNativeQuery(sql, Relation.class);
		query.setParameter("ruleTypeId", ruleTypeId).setParameter("treeType", treeType).setParameter("rootNodeType", treeType).setParameter("rootNodeId", rootNodeId);
		List<Relation> relations=query.getResultList();
		return relations;
		
	}
	
	public List<Relation> findReverseTree(Integer ruleTypeId, String treeType,Integer leafNodeId){
		String sql="select t.* from (select a.* from af_relation a where a.removed = 0 and a.rule_type_id=:ruleTypeId and a.p_type=a.n_type and a.p_type=:treeType) t start with t.n_type=:leafNodeType and t.n_id=:leafNodeId connect by t.n_type= prior t.p_type and t.n_id= prior t.p_id";
		Query query=em.createNativeQuery(sql, Relation.class);
		query.setParameter("ruleTypeId", ruleTypeId).setParameter("treeType", treeType).setParameter("leafNodeType", treeType).setParameter("leafNodeId", leafNodeId);
		List<Relation> relations=query.getResultList();
		return relations;
		
	}
}
