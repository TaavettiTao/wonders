package com.wonders.frame.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import com.wonders.frame.core.model.bo.Rule;
/**
 * 
 * @author taoweiwei
 *
 */
public interface RuleDao extends GenericRepository<Rule, Integer>{

	@Query("select r from Rule r where r.removed=0 and r.ruleTypeId=?")
	public List<Rule> findByRuleTypeId(Integer ruleTypeId);
	
	@Query("select r from Rule r where r.removed=0 and r.ruleTypeId=?1 and r.pobjId=?2")
	public List<Rule> findByRuleTypeIdAndPobjId(Integer ruleTypeId,Integer pobjId);
	
	@Query("select r from Rule r where r.removed=0 and r.ruleTypeId=?1 and r.pobjType=?2")
	public List<Rule> findByRuleTypeIdAndPobjType(Integer ruleTypeId,String pobjType);
	
}
