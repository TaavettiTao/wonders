package com.wonders.frame.core.service;

import java.util.HashMap;
import java.util.List;

import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.NodeObj;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.RulePathVo;
import com.wonders.frame.core.utils.RulePathUtil;



/**
 * @author mengjie
 * log管理
 *
 * 2013-3-30
 */
public interface RelationService {

	public Relation bind(HashMap<String,String> queryParams);
	
	public Relation rebind(HashMap<String,String> queryParams,HashMap<String,String> newParams);
	
	public void unbind(HashMap<String,String> queryParams) throws Exception;
	
	public ReturnObj<List<String>> findRelationPaths(String ruleTypeId,String path);
	
	public ReturnObj<HashMap<String, List<NodeObj>>> findRelationTree(String ruleTypeId,String path);
	
	public List<Relation> findAllChildRelation(Integer ruleTypeId, String type,
			Integer rootId);
	public List<Relation> findAllParentRelation(Integer ruleTypeId,
			String type, Integer rootId);
	
	public List<Relation> findAllRelation(Integer ruleTypeId,
			String type, List<Integer> ids);

	public List<Relation> findTree(Integer ruleTypeId, String treeType,
			Integer rootNodeId);

	public List<Relation> findReverseTree(Integer ruleTypeId,
			String treeType, Integer leafNodeId);
	
	public Long count(HashMap<String,String> queryParams);









	



	

}