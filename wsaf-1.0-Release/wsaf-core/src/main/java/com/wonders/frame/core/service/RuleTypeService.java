package com.wonders.frame.core.service;

import com.wonders.frame.core.model.bo.RuleType;

public interface RuleTypeService {
	
	public RuleType findById(Integer id);

	public RuleType findByName(String name);

	public RuleType findExistOne(String name);
}
