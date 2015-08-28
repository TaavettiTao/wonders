package com.wonders.frame.console.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.service.PermissionService;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.service.RelationService;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	@Resource
	private RelationService relationService;
	@Override
	public List<Permission> findAllChildPermission(Integer ruleTypeId,String type,Integer id,Integer lv,List<Permission> permissions,List<String> objFilter) 
	{

		for(Permission pe:permissions){
			System.out.println("id"+pe.getResourceId()+",peId:"+pe.getPrivilegeId()+",lv:"+pe.getLevel());
		}
		System.out.println("------------"+type+":"+id+":"+lv+"--------------");

		List<Relation> nextRelations=relationService.findAllChildRelation(ruleTypeId, type, id);
		if(nextRelations!=null && nextRelations.size()>0){
			
			List<Relation> childRelationList=new ArrayList<Relation>();
			boolean isIgnore=false;
			for(Relation nr:nextRelations){
				
				if(nr.getPtype().equals(type)&&nr.getPid().equals(id)){//遍历到一个新的树形首节点
					if(objFilter!=null){//有对象过滤要求
						if(objFilter.indexOf(nr.getNtype())<0){//第一层子节点不是要求的对象
							isIgnore=true;
						}else{
							isIgnore=false;
						}
					}
				}
				
				
				if(isIgnore){
					continue;
				}
				if(addRelation2ChildRelationList(type,id,nr, childRelationList)){
				
					if(nr.getNtype().equals("resource")){
						Integer peId=0;//对应权限ID	
						Integer currentLv =0;//优先级

						//查找最靠近的上级权限
						for(int i=childRelationList.size()-1;i>=0;i--){
							Relation linkedRelation =childRelationList.get(i);
							if(linkedRelation.getPtype().equals("privilege")){
								peId=linkedRelation.getPid();
								currentLv=i;
								break;
							}
						}
						//未找到上级权限
						if(peId==0){
							//查找上级资源
							for(int i=childRelationList.size()-1;i>=0;i--){
								Relation linkedRelation =childRelationList.get(i);
								if(!linkedRelation.getPtype().equals("resource")){
									peId=0;
									currentLv=i+1;
									break;
								}
							}
						}
						
						
						Permission pe=new Permission();
						pe.setPrivilegeId(peId);
						pe.setResourceId(nr.getNid());
						pe.setLevel(currentLv+lv);
						permissions.add(pe);
					}
				}

			}
		}
		System.out.println("--------------------------");
		return permissions;
	}
	
	
	private boolean addRelation2ChildRelationList(String pType,Integer id,Relation currentRelation,List<Relation> linkedRelationList){
		boolean isSuccess=false;
		//获取最后一条记录
		if(linkedRelationList.size()==0){
			if(currentRelation.getPtype().equals(pType)&& currentRelation.getPid().equals(id)){
				linkedRelationList.add(currentRelation);
				isSuccess=true;
			}
		}else{
			Relation lastLinkedRelation =linkedRelationList.get(linkedRelationList.size()-1);
			//当前记录的前置对象与最近一条记录的后置对象一致
			if(currentRelation.getPtype().equals(lastLinkedRelation.getNtype())&&currentRelation.getPid().equals(lastLinkedRelation.getNid())){
				linkedRelationList.add(currentRelation);
				isSuccess=true;
			}else{
				linkedRelationList.remove(linkedRelationList.size()-1);
				
				isSuccess=addRelation2ChildRelationList(pType,id,currentRelation,linkedRelationList);
			}
		}
		return isSuccess;
	}
	
	
	@Override
	public List<Permission> findAllParentPermission(Integer ruleTypeId,String type,Integer id,Integer lv,List<Permission> permissions,List<String> objFilter) 
	{
		List<Relation> nextRelations=relationService.findAllParentRelation(ruleTypeId, type, id);
		if(nextRelations!=null && nextRelations.size()>0){
			
			List<Relation> parentRelationList=new ArrayList<Relation>();
			
			for(Relation nr:nextRelations){
				
				if(addRelation2ParentRelationList(type,id,nr, parentRelationList)){

					findAllChildPermission(ruleTypeId,nr.getPtype(),nr.getPid(),parentRelationList.size(),permissions,objFilter);
				}

			}
		}
		
		return permissions;
	}
	
	
	private boolean addRelation2ParentRelationList(String nType,Integer id,Relation currentRelation,List<Relation> linkedRelationList){
		boolean isSuccess=false;
		//获取最后一条记录
		if(linkedRelationList.size()==0){
			if(currentRelation.getNtype().equals(nType)&&currentRelation.getNid().equals(id)){
				linkedRelationList.add(currentRelation);
				isSuccess=true;
			}
		}else{
			Relation lastLinkedRelation =linkedRelationList.get(linkedRelationList.size()-1);
			//当前记录的后置对象与最近一条记录的前置对象一致
			if(currentRelation.getNtype().equals(lastLinkedRelation.getPtype())&&currentRelation.getNid().equals(lastLinkedRelation.getPid())){
				linkedRelationList.add(currentRelation);
				isSuccess=true;
			}else{
				linkedRelationList.remove(linkedRelationList.size()-1);
				
				isSuccess=addRelation2ParentRelationList(nType,id,currentRelation,linkedRelationList);
			}
		}
		
		return isSuccess;
	}
}
