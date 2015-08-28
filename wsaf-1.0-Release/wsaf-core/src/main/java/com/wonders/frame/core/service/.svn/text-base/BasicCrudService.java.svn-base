package com.wonders.frame.core.service;


import java.util.List;

import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.RelatedNode;
import com.wonders.frame.core.model.vo.TreeNode;


public interface BasicCrudService extends SingleCrudService<IDefaultModel,Integer>{

	public List<TreeNode<IDefaultModel,Integer>> bindTreeObjInfo(List<Relation> relations);
	
	public List<RelatedNode<IDefaultModel,Integer>> bindParentObjInfo(List<Relation> relations);
	
	public List<RelatedNode<IDefaultModel,Integer>> bindChildObjInfo(List<Relation> relations);
}
