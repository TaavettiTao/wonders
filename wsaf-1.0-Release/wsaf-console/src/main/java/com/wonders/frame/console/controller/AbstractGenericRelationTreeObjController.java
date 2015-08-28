package com.wonders.frame.console.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.console.model.vo.Ticket;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.TreeNode;
import com.wonders.frame.core.model.vo.ReturnObjInfo.ErrorType;
import com.wonders.frame.core.service.RelationService;
import com.wonders.frame.core.utils.ReflectUtil;

public abstract class AbstractGenericRelationTreeObjController<T> extends AbstractGenericRelationObjController<T>{

	@RequestMapping(value = "/{id}/tree", method = RequestMethod.GET)
	@ResponseBody
	public ReturnObj<List<TreeNode<IDefaultModel,Integer>>> getTree(
			@PathVariable("id") Integer id,@RequestParam(value = "whole", required = false, defaultValue = "") String whole,
			@RequestParam(value = "ticket", required = true) String ticket) {
		Ticket t=new Ticket(ticket);
		if(t.getRuleTypeId()==null){
			return new ReturnObj<List<TreeNode<IDefaultModel,Integer>>>("invalid ticket");
		}
		
		
		List<Relation> relations=getRelationService().findTree(t.getRuleTypeId(), this.getEntityName(), id);

		List<TreeNode<IDefaultModel,Integer>> tree=this.getBasicCrudService().bindTreeObjInfo(relations);
		if(!whole.equals("")){
			Object obj = ReflectUtil.executeMethod(this.getEntityClass(), "findById",
					new Class[] { Serializable.class }, new Object[] { id });
			
			TreeNode<IDefaultModel,Integer> self=new TreeNode<IDefaultModel,Integer>();
			self.setPid(0);
			self.setNid(id);
			self.setNode((IDefaultModel)obj);
			tree.add(0,self);
		}
		
		
		if(tree==null||tree.size()==0){

			return new ReturnObj<List<TreeNode<IDefaultModel,Integer>>>(ErrorType.NULL);
		}
		return new ReturnObj<List<TreeNode<IDefaultModel,Integer>>>(tree);		

	}
	@RequestMapping(value = "/{id}/reverseTree", method = RequestMethod.GET)
	@ResponseBody
	public ReturnObj<List<TreeNode<IDefaultModel,Integer>>> getReverseTree(
			@PathVariable("id") Integer id,
			@RequestParam(value = "ticket", required = true) String ticket) {

		Ticket t=new Ticket(ticket);
		if(t.getRuleTypeId()==null){
			return new ReturnObj<List<TreeNode<IDefaultModel,Integer>>>("invalid ticket");
		}
		List<Relation> relations=getRelationService().findReverseTree(t.getRuleTypeId(), this.getEntityName(), id);
		
		List<TreeNode<IDefaultModel,Integer>> tree=this.getBasicCrudService().bindTreeObjInfo(relations);
		if(tree==null||tree.size()==0){
			return new ReturnObj<List<TreeNode<IDefaultModel,Integer>>>(ErrorType.NULL);
		}

		return new ReturnObj<List<TreeNode<IDefaultModel,Integer>>>(tree);

	}
}
