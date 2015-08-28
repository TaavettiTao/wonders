package com.wonders.frame.console.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wonders.frame.console.dao.ResourceDao;
import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	@Resource
	private ResourceDao resourceDao;

	
	@Override
	public List<Permission> bindResourceInfo2Permission(List<Permission> permissions) {
		List<Integer> ids=new ArrayList<Integer>();
		for(Permission pe:permissions){

			//获取相关资源ID
			if(ids.indexOf(pe.getResourceId())<0){
				ids.add(pe.getResourceId());
			}
						
		}
		
		
		List<com.wonders.frame.console.model.bo.Resource> resources=resourceDao.findByIds(ids);
		if(resources==null ||resources.size()==0){
			return null;
		}
		//保存权限ID与操作类型的映射关系
		HashMap<Integer,com.wonders.frame.console.model.bo.Resource> hmIdResource=new HashMap<Integer,com.wonders.frame.console.model.bo.Resource>();
		
		for(com.wonders.frame.console.model.bo.Resource re: resources){
			hmIdResource.put(re.getId(), re);
		}
		
		for(Permission pe:permissions){

			Integer reId=pe.getResourceId();
			com.wonders.frame.console.model.bo.Resource re=hmIdResource.get(reId);
			
			pe.setResourceName(re.getName());
			pe.setResourcePath(re.getPath());
			pe.setResourceType(re.getType());			
						
		}
		return permissions;
	}

	

}
