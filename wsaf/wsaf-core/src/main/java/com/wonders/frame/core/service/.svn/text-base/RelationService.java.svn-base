package com.wonders.frame.core.service;

import java.util.HashMap;
import java.util.List;

import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.NodeObj;



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

	//从path参数中获取关联对象和关联符
	public HashMap<String,List<String>> getObjAndOptagFromPath(String path);
	
	//绑定对象到hashMap
	public void bindNode2ObjDataMap(String[] curNode,Object relatedNode,HashMap<String,List<NodeObj>> hmObjData);
	//根据路径结果集转化为hashMap
	public void convertPath2Map(String pathResult,String op,String nodeType,Object relatedNode,HashMap<String,Object> hmKeyOp);
	    
    //通过递归查询，获得与url入参path格式一致的结果集(重载方法，新增type参数)
	public List<String> getPathResult(HashMap<String,List<String>> hmPath,String ruleTypeId);
	
	List<Relation> findAllChildRelation(Integer ruleTypeId, String type,
			Integer rootId);
	List<Relation> findAllParentRelation(Integer ruleTypeId,
			String type, Integer rootId);
	
	List<Relation> findAllRelation(Integer ruleTypeId,
			String type, List<Integer> ids);

	List<Relation> findTree(Integer ruleTypeId, String treeType,
			Integer rootNodeId);

	List<Relation> findReverseTree(Integer ruleTypeId,
			String treeType, Integer leafNodeId);
	
	Long count(HashMap<String,String> queryParams);









	



	

}