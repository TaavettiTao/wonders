package com.wonders.frame.core.service;

import java.util.HashMap;
import java.util.List;

import com.wonders.frame.core.model.bo.Rule;

public interface RuleService {

	public List<Rule> findByRuleTypeId(Integer ruleTypeId);
	
	public List<Rule> findByRuleTypeIdAndPobjId(Integer ruleTypeId, Integer pobjId);

	public List<Rule> findByRuleTypeIdAndPobjType(Integer ruleTypeId, String pobjType);
	
	
	public List<String> getRulePath(List<Rule> rules);

	public List<String> getRuleObjs(List<Rule> rules);
	
	public List<String[]> getRuleList(List<Rule> rules);
	
	void findAndAddNextRule(String pTypeObj,
			List<String[]> ruleList, List<List<String[]>> rulePathList);

	public List<String> convertRulePath2String(List<List<String[]>> rulePathList);
	
	public Long count(HashMap<String,String> queryParams);

}
