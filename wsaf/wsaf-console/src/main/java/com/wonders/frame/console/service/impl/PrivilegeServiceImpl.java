package com.wonders.frame.console.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wonders.frame.console.dao.PrivilegeDao;
import com.wonders.frame.console.model.bo.Privilege;
import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.service.PrivilegeService;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {
	@Resource
	private PrivilegeDao privilegeDao;
	

	@Override
	public List<Permission> bindPrivilegeType(List<Permission> permissions) {
		
		//保存各个资源的权限及优先级映射关系
		HashMap<Integer,HashMap<Integer,Integer>> hmRePeLv=new HashMap<Integer,HashMap<Integer,Integer>>();
		List<Integer> peIds=new ArrayList<Integer>();
		for(Permission pe:permissions){
//System.out.println("id"+pe.getResourceId()+",peId:"+pe.getPrivilegeId()+",lv:"+pe.getLevel());
			Integer reId=pe.getResourceId();
			Integer peId=pe.getPrivilegeId();
			Integer cLv=pe.getLevel();
			
			if(hmRePeLv.containsKey(reId)){
				HashMap<Integer,Integer> hmPeLv=hmRePeLv.get(reId);
				if(hmPeLv.containsKey(peId)){
					Integer oLv=hmPeLv.get(peId);
					if(cLv<oLv){
						hmPeLv.put(peId, cLv);
						hmRePeLv.put(reId, hmPeLv);
					}
				}else{
					hmPeLv.put(peId, cLv);
					hmRePeLv.put(reId, hmPeLv);
				}
			}else{
				HashMap<Integer,Integer> hmPeLv=new HashMap<Integer,Integer>();
				hmPeLv.put(peId, cLv);
				hmRePeLv.put(reId, hmPeLv);
			}	

			//获取相关权限ID
			if(peIds.indexOf(pe.getPrivilegeId())<0&&!pe.getPrivilegeId().equals(0)){
				peIds.add(pe.getPrivilegeId());
			}
						
		}
		
		
		List<Privilege> privileges=privilegeDao.findByIds(peIds);
		//保存权限ID与操作类型的映射关系
		HashMap<Integer,String> hmIdType=new HashMap<Integer,String>();
		
		for(Privilege pe: privileges){
			hmIdType.put(pe.getId(), pe.getType());
		}
		
		//获取资源ID对应的操作类型
		List<Permission> newPermissions=new ArrayList<Permission>();
		
		Set<Integer> key=hmRePeLv.keySet();
		for(Iterator it=key.iterator();it.hasNext();){

			Integer reId=(Integer)it.next();

			HashMap<Integer,Integer> hmPeLv=hmRePeLv.get(reId);
		
			//将资源所对应的权限ID转为权限操作类型，并对同样的权限操作类型进行优先级筛选
			HashMap<String,Integer> hmPeTypeLv=new HashMap<String,Integer>();
		
			Set<Integer> key2=hmPeLv.keySet();
			for(Iterator it2=key2.iterator();it2.hasNext();){
				Integer peId=(Integer)it2.next();

				Integer peLv=hmPeLv.get(peId);
				
				String peType=peId==0?"all":hmIdType.get(peId);
				
				if(peType==null){continue;}//未找到对应类型，则删除
				
				if(hmPeTypeLv.containsKey(peType)){
					
					Integer oLv=hmPeTypeLv.get(peType);
					if(peLv<oLv){
						hmPeTypeLv.put(peType, peLv);
					}
				}else{
					hmPeTypeLv.put(peType, peLv);
				}
				
			}
			//根据同一资源的权限操作类型和优先级进行权限操作类型的筛选合并			
			Integer noneLv=hmPeTypeLv.get("none");
			Integer allLv=hmPeTypeLv.get("all");
			
			List<String> peTypes=new ArrayList<String>();	
			
			Set<String> key3=hmPeTypeLv.keySet();
			
			if(noneLv!=null&&allLv!=null){//none和all权限都存在，则比较两个权限哪个优先级高（lv小的优先级高）
				
				if(allLv>=noneLv){//none权限优先级高时，只有优先级高于其的级别操作权限可以保留
					for(Iterator it3=key3.iterator();it3.hasNext();){
						String petype=(String)it3.next();
						Integer petypeLv=hmPeTypeLv.get(petype);
						if(petypeLv<noneLv){
							peTypes.add(petype);
						}
					}
					if(peTypes.size()==0){
						peTypes.add("none");
					}

				}else{	//all权限优先级高时，该资源拥有all权限	
					peTypes.add("all");
				}
			}else if(noneLv!=null){//all权限不存在，而none权限存在，则只有优先级高于其的级别操作权限可以保留
				
				for(Iterator it3=key3.iterator();it3.hasNext();){
					String petype=(String)it3.next();
					Integer petypeLv=hmPeTypeLv.get(petype);
					if(petypeLv<noneLv){
						peTypes.add(petype);
					}
				}
				if(peTypes.size()==0){
					peTypes.add("none");
				}
			}else if(allLv!=null){//all权限存在，而none权限不存在，则该资源拥有all权限
				peTypes.add("all");
				
			}else{//all和none权限都不存在，则保留所有操作权限
				for(Iterator it3=key3.iterator();it3.hasNext();){
					String petype=(String)it3.next();
					peTypes.add(petype);
					
				}
			}
			
			if(peTypes.size()==0){continue;}//若经过优先级筛选后，剩余操作类型为空，则该资源去除

			Permission pe=new Permission();
			pe.setResourceId(reId);
			pe.setPrivilegeType(peTypes);
			newPermissions.add(pe);
			
		}
		
		return newPermissions;
	}

}
