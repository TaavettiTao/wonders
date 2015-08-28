package com.wonders.frame.console.service;

import java.util.List;

import com.wonders.frame.console.model.vo.Permission;

public interface PermissionService {

	List<Permission> findAllParentPermission(Integer ruleTypeId,
			String type, Integer id, Integer lv, List<Permission> permissions,
			List<String> objFilter);
	
	List<Permission> findAllChildPermission(Integer ruleTypeId,
			String type, Integer id, Integer lv, List<Permission> permissions,
			List<String> objFilter);

}
