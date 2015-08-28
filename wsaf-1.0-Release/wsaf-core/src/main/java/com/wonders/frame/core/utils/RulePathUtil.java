/** 
* @Title: ConfigProperties.java 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com 
* @date 2013-7-2 下午05:01:35 
* @version V1.0 
*/
package com.wonders.frame.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wonders.frame.core.model.vo.RulePathVo;


@Component
public class RulePathUtil {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());

	private static HashMap<Integer,List<String>> rulePath=new HashMap<Integer,List<String>>();

//	private static HashMap<String,List<Object>> nextRelationNodes=new HashMap<String,List<Object>>();
//	
//	private static HashMap<String,List<Object>> previousRelationNodes=new HashMap<String,List<Object>>();
//	
//	private static HashMap<String,List<Object>> objNodes =new HashMap<String,List<Object>>();
	
	private static void init() {	

	}
	
	static {
		init();
	}


	public static void clear(Integer ruleTypeId) {
		if(rulePath.containsKey(ruleTypeId)){
			rulePath.remove(ruleTypeId);
		}
	}

	public static List<String> getRulePath(Integer ruleTypeId) {
		return rulePath.get(ruleTypeId);
	}

	public static void setRulePath(Integer ruleTypeId, List<String> paths) {
		RulePathUtil.rulePath.put(ruleTypeId,paths);
	}

	//从path参数中获取关联对象和关联符
	public static RulePathVo getRulePath(String path){
		List<String> objList=new ArrayList<String>();//存放关联对象
		List<String> opList=new ArrayList<String>();//存放关联符
		
		StringBuffer sb=new StringBuffer("");	
		
		for(int i=0;i<path.length();i++){//按字符遍历查询路径，
			String subStr=path.substring(i, i+1);
			if(subStr.equals(">")||subStr.equals("<")){//当前字符为关联符>或<时
				objList.add(sb.toString());
				opList.add(subStr);
				sb.setLength(0);
			}else{					
				sb.append(subStr);					
			}
		}
		objList.add(sb.toString());

		return new RulePathVo(objList,opList);
	}
	
//	public static void clear() {
//		rulePath.clear();
//	}
//	public static HashMap<Integer, List<String>> getRulePath() {
//		return rulePath;
//	}
//	
//	public static void setRulePath(HashMap<Integer, List<String>> rulePath) {
//		RulePathUtil.rulePath = rulePath;
//	}

//	public static HashMap<String, List<Object>> getNextRelationNodes() {
//		return nextRelationNodes;
//	}
//
//
//	public static void setNextRelationNodes(
//			HashMap<String, List<Object>> nextRelationNodes) {
//		RulePathUtil.nextRelationNodes = nextRelationNodes;
//	}
//
//	public static HashMap<String, List<Object>> getPreviousRelationNodes() {
//		return previousRelationNodes;
//	}
//
//	public static void setPreviousRelationNodes(
//			HashMap<String, List<Object>> previousRelationNodes) {
//		RulePathUtil.previousRelationNodes = previousRelationNodes;
//	}
//
//
//	public static HashMap<String, List<Object>> getObjNodes() {
//		return objNodes;
//	}
//
//	public static void setObjNodes(HashMap<String, List<Object>> objNodes) {
//		RulePathUtil.objNodes = objNodes;
//	}

	

//	public static void main(String[] args)
//	   {
//	      // Obtain a new CDMDS Client
////	      HashMap<String,String> test = new HashMap<String,String>();
////	      test.put("A", "111");
////	      test.put("B", "222");
////	      String s=test.get("A");
////	      test.remove("A");
//			String [] s="Lost/CDMDS.Data/1244".replace("Lost/", "").split("/");
//	      System.out.println(s[0]+","+s[1]);
//	   }

}
