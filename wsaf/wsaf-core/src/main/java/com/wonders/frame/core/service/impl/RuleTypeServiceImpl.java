package com.wonders.frame.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.wonders.frame.core.dao.RuleTypeDao;
import com.wonders.frame.core.model.bo.RuleType;
import com.wonders.frame.core.service.RuleTypeService;
/**
 * 
 * @author taoweiwei
 *
 */
@Service("ruleTypeService")
public class RuleTypeServiceImpl implements RuleTypeService{
	
	@Resource
	private RuleTypeDao ruleTypeDao;
	
	public RuleType findById(Integer id) {
		return ruleTypeDao.findById(id);
	}
	public RuleType findByName(String name){
		return ruleTypeDao.findByName(name);		
	}

	public RuleType findExistOne(String name) {
		if(name==null ||name.equals("")){
			return ruleTypeDao.findExistOne();
		}else{
			RuleType rt= ruleTypeDao.findByName(name);	
			if(rt==null){
				rt=ruleTypeDao.findExistOne();
			}
			
			return rt;
		}
	}
	
//	public Integer removeById(Integer id) {
//		return this.relationRuleTypeDao.removeById(id);
//	}
//
//	public Integer removeByIds(List<Integer> ids) {
//		return this.relationRuleTypeDao.removeByIds(ids);
//	}


}
