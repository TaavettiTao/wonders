package com.wonders.frame.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.wonders.frame.core.dao.RuleDao;
import com.wonders.frame.core.model.bo.Rule;
import com.wonders.frame.core.service.RuleService;
/**
 * 
 * @author lushuaifeng
 *
 */
@Service("ruleService")
public class RuleServiceImpl implements RuleService{

	@Resource
	private RuleDao ruleDao;
	
	@Override
	public List<Rule> findByRuleTypeId(Integer ruleTypeId) {
		return ruleDao.findByRuleTypeId(ruleTypeId);
	}
	
	@Override
	public List<Rule> findByRuleTypeIdAndPobjId(Integer ruleTypeId,Integer pobjId) {
		return ruleDao.findByRuleTypeIdAndPobjId(ruleTypeId,pobjId);
	}
	
	@Override
	public List<Rule> findByRuleTypeIdAndPobjType(Integer ruleTypeId,String pobjType) {
		return ruleDao.findByRuleTypeIdAndPobjType(ruleTypeId,pobjType);
	}
	
	
	public List<String> getRulePath(List<Rule> rules){
		
		List<String> objs = getRuleObjs(rules);
		
		List<String[]> ruleList = getRuleList(rules);
	
		List<List<String[]>> rulePathList = new ArrayList<List<String[]>>();
	
		// 循环绑定对象从属关系
		for (String obj : objs) {
	
			findAndAddNextRule(obj, ruleList, rulePathList);// 查找并添加rule至rulePathList

		}
	
		// 将对象间从属关系从list链路转换为字符串序列
		return convertRulePath2String(rulePathList);
		
	}
	
	public List<String[]> getRuleList(List<Rule> rules){
		List<String[]> ruleList = new ArrayList<String[]>();
		// 获得对象间从属规则关系视图列表
		for (Rule rule : rules) {
			// rule:规则对象，存储于长度为3 的数组：0=ptype;1=">"or"<";2=ntype
			String[] ruleVo = new String[3];

			ruleVo[0] = rule.getPobjType();
			ruleVo[1] = ">";
			ruleVo[2] = rule.getNobjType();

			ruleList.add(ruleVo);


		}
		return ruleList;
	}
	
	public List<String> getRuleObjs(List<Rule> rules){
		List<String> objs = new ArrayList<String>();
		// 获得所有对象
		for (Rule rule : rules) {
			if (!objs.contains(rule.getPobjType())) {
				objs.add(rule.getPobjType());
			}
			if (!objs.contains(rule.getNobjType())) {
				objs.add(rule.getNobjType());
			}

		}
		return objs;
	}
	
	
	//根据ptype从规则列表中组合获得对应的规则链路
	public  void findAndAddNextRule(String pTypeObj,
			List<String[]> ruleList, List<List<String[]>> rulePathList) {
		//System.out.println("---------------start----"+pTypeObj+"---------------------------");
		for (String[] rule : ruleList) {
			//System.out.println("..................rule....."+rule[0]+rule[1]+rule[2]);
			if (pTypeObj.equals(rule[0])) {
				boolean isContinue = false;
				// 将找到的下级规则添加到rulePathList
				if (rulePathList.size() > 0) {// 判断rulePathList是否为空
					boolean buildNewRulePath = true;
					for (int i = 0; i < rulePathList.size(); i++) {// 轮询rulePathList
						List<String[]> rulePath = rulePathList.get(i);
						if (rulePath.contains(rule)) {// 若rulePath中已经有相同的rule，则不添加（防止出现回路）
							buildNewRulePath = false;
							continue;
						} else {// 判断当前规则是否可与规则链中的规则产生关联，若在头或尾则直接加载规则链上，否则新建一个链路
							if (addRule2RulePath(rule, rulePath, rulePathList)) {

								buildNewRulePath = false;
								isContinue = true;
							}
						}
					}

					if (buildNewRulePath) {// 否则建立新的rulePath
						List<String[]> newRulePath = new ArrayList<String[]>();
						newRulePath.add(rule);
						rulePathList.add(newRulePath);
						isContinue = true;
					}
				} else {// rulePathList为空，建立新的rulePath
					List<String[]> newRulePath = new ArrayList<String[]>();
					newRulePath.add(rule);
					rulePathList.add(newRulePath);
					isContinue = true;
				}

//				List<String> rulePaths=convertRulePath2String(rulePathList);
//
//				for(String s:rulePaths){
//					System.out.println(s);
//				}

				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				// 绑定下一级规则
				if (isContinue) {
					findAndAddNextRule(rule[2], ruleList, rulePathList);
				}
				//System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			}
		}
		//System.out.println("---------------end----"+pTypeObj+"---------------------------");
	}

	// 将rule加入rulePath
	private  boolean addRule2RulePath(String[] newRule,
			List<String[]> rulePath, List<List<String[]>> rulePathList) {
		boolean isDone = false;

		for (int i = 0; i < rulePath.size() - 1; i++) {
			String[] rule = rulePath.get(i);

			if (rule[2].equals(newRule[0])) {

				List<String[]> newRulePath = new ArrayList<String[]>();

				for (int j = 0; j <= i; j++) {
					newRulePath.add(rulePath.get(j));
				}
				newRulePath.add(newRule);

				if (!rulePathContains(newRulePath, rulePathList)) {
					rulePathList.add(newRulePath);
				}
				isDone = true;
			}
		}
		if (rulePath.get(rulePath.size() - 1)[2].equals(newRule[0])) {
			rulePath.add(newRule);
			isDone = true;
		}
		if (rulePath.get(0)[0].equals(newRule[2])) {
			rulePath.add(0,newRule);
			isDone = true;
		}
		return isDone;

	}

	// 检查rulePath是否包含在rulePathList里面
	private boolean rulePathContains(List<String[]> rulePath,
			List<List<String[]>> rulePathList) {
		boolean isContains = false;
		for (List<String[]> existRulePath : rulePathList) {
			if (existRulePath.containsAll(rulePath)) {
				isContains = true;
				break;
			}
		}
		return isContains;
	}
	
	public List<String> convertRulePath2String(List<List<String[]>> rulePathList){
		// 将对象间从属关系从list链路转换为字符串序列进行保存
		List<String> rulePaths = new ArrayList<String>();
		for (List<String[]> rulePath : rulePathList) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < rulePath.size(); i++) {
				String[] r = rulePath.get(i);
				if (i == 0) {
					sb.append(r[0]).append(r[1]).append(r[2]);
				} else {
					sb.append(r[1]).append(r[2]);
				}
			}
			rulePaths.add(sb.toString());
		}
		return rulePaths;
	}

	@Override
	public Long count(HashMap<String, String> queryParams) {
		return ruleDao.count(queryParams);		
	}

	
}
