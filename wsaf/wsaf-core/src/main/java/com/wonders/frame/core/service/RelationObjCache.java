/** 
* @Title: ConfigProperties.java 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com 
* @date 2013-7-2 下午05:01:35 
* @version V1.0 
*/
package com.wonders.frame.core.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class RelationObjCache {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());

	private static HashMap<Integer,List<String>> rulePath=new HashMap<Integer,List<String>>();

	private static HashMap<String,List<Object>> nextRelationNodes=new HashMap<String,List<Object>>();
	
	private static HashMap<String,List<Object>> previousRelationNodes=new HashMap<String,List<Object>>();
	
	private static HashMap<String,List<Object>> objNodes =new HashMap<String,List<Object>>();
	
	private static void init() {	

	}
	
	static {
		init();
	}


	public static HashMap<Integer, List<String>> getRulePath() {
		return rulePath;
	}
	
	public static List<String> getRulePath(Integer ruleTypeId) {
		return rulePath.get(ruleTypeId);
	}


	public static void setRulePath(HashMap<Integer, List<String>> rulePath) {
		RelationObjCache.rulePath = rulePath;
	}
	
	public static void setRulePath(Integer ruleTypeId, List<String> paths) {
		RelationObjCache.rulePath.put(ruleTypeId,paths);
	}


	public static HashMap<String, List<Object>> getNextRelationNodes() {
		return nextRelationNodes;
	}


	public static void setNextRelationNodes(
			HashMap<String, List<Object>> nextRelationNodes) {
		RelationObjCache.nextRelationNodes = nextRelationNodes;
	}

	public static HashMap<String, List<Object>> getPreviousRelationNodes() {
		return previousRelationNodes;
	}

	public static void setPreviousRelationNodes(
			HashMap<String, List<Object>> previousRelationNodes) {
		RelationObjCache.previousRelationNodes = previousRelationNodes;
	}


	public static HashMap<String, List<Object>> getObjNodes() {
		return objNodes;
	}

	public static void setObjNodes(HashMap<String, List<Object>> objNodes) {
		RelationObjCache.objNodes = objNodes;
	}

	

	public static void main(String[] args)
	   {
	      // Obtain a new CDMDS Client
//	      HashMap<String,String> test = new HashMap<String,String>();
//	      test.put("A", "111");
//	      test.put("B", "222");
//	      String s=test.get("A");
//	      test.remove("A");
			String [] s="Lost/CDMDS.Data/1244".replace("Lost/", "").split("/");
	      System.out.println(s[0]+","+s[1]);
	   }

}
