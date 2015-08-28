package com.wonders.frame.console.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.model.vo.Ticket;
import com.wonders.frame.console.service.PermissionService;
import com.wonders.frame.console.service.PrivilegeService;
import com.wonders.frame.console.service.ResourceService;
import com.wonders.frame.core.controller.AbstractSingleCrudController;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.RelatedNode;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.ReturnObjInfo.ErrorType;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.RelationService;

public abstract class AbstractGenericRelationObjController<T> extends AbstractSingleCrudController<T>{
	@Resource
	private RelationService relationService;
	
	public RelationService getRelationService(){
		return relationService;
	}
	
	@Resource
	private PermissionService permissionService;

	public PermissionService getPermissionService(){
		return permissionService;
	}
	
	@Resource
	private PrivilegeService privilegeService;
	
	public PrivilegeService getPrivilegeService(){
		return privilegeService;
	}
	
	@Resource
	private ResourceService resourceService;
	
	public ResourceService getResourceService(){
		return resourceService;
	}
	
	@RequestMapping(value = "/{id}/child", method = RequestMethod.GET)
	@ResponseBody
	public ReturnObj<List<RelatedNode<IDefaultModel,Integer>>> getChildNode(
			@PathVariable("id") Integer id,
			@RequestParam(value = "ticket", required = true) String ticket) {
		
		Ticket t=new Ticket(ticket);
		if(t.getRuleTypeId()==null){
			return new ReturnObj<List<RelatedNode<IDefaultModel,Integer>>>("invalid ticket");
		}
		
		List<Relation> relations=relationService.findAllChildRelation(t.getRuleTypeId(), this.getEntityName(), id);
		
		List<RelatedNode<IDefaultModel,Integer>> relatedNodes=this.getBasicCrudService().bindChildObjInfo(relations);
		
		if(relatedNodes==null||relatedNodes.size()==0){
			return new ReturnObj<List<RelatedNode<IDefaultModel,Integer>>>(ErrorType.NULL);
		}
		
		return new ReturnObj<List<RelatedNode<IDefaultModel,Integer>>>(relatedNodes);

	}
	
	@RequestMapping(value = "/{id}/parent", method = RequestMethod.GET)
	@ResponseBody
	public ReturnObj<List<RelatedNode<IDefaultModel,Integer>>> getParentNode(
			@PathVariable("id") Integer id,
			@RequestParam(value = "ticket", required = true) String ticket) {
		Ticket t=new Ticket(ticket);
		if(t.getRuleTypeId()==null){
			return new ReturnObj<List<RelatedNode<IDefaultModel,Integer>>>("invalid ticket");
		}
		
		List<Relation> relations=relationService.findAllParentRelation(t.getRuleTypeId(), this.getEntityName(), id);
		
		List<RelatedNode<IDefaultModel,Integer>> relatedNodes=this.getBasicCrudService().bindParentObjInfo(relations);
		
		if(relatedNodes==null||relatedNodes.size()==0){

			return new ReturnObj<List<RelatedNode<IDefaultModel,Integer>>>(ErrorType.NULL);
		}
		
		return new ReturnObj<List<RelatedNode<IDefaultModel,Integer>>>(relatedNodes);


	}
	
	@RequestMapping(value = "/{id}/permission", method = RequestMethod.GET)
	@ResponseBody
	public ReturnObj<List<Permission>> getPermission(
			@PathVariable("id") Integer id,
			@RequestParam(value = "ticket", required = true) String ticket) {
		Ticket t=new Ticket(ticket);
		if(t.getRuleTypeId()==null){
			return new ReturnObj<List<Permission>>("invalid ticket");
		}
		
		List<Permission> permissions=new ArrayList<Permission>();
		
		List<String> objFilter=new ArrayList<String>();
		
		objFilter.add("privilege");
		objFilter.add("resource");
		
		permissions=permissionService.findAllChildPermission(t.getRuleTypeId(), this.getEntityName(), id,0,permissions,objFilter);	
		
		permissions=privilegeService.bindPrivilegeType(permissions);
		
		permissions=resourceService.bindResourceInfo2Permission(permissions);
		
		if(permissions==null||permissions.size()==0){
			return new ReturnObj<List<Permission>>(ErrorType.NULL);
		}
				
		return new ReturnObj<List<Permission>>(permissions);

	}
}
