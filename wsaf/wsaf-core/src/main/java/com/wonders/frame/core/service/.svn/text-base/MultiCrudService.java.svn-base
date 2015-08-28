package com.wonders.frame.core.service;

import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.List;

import com.wonders.frame.core.model.IMultiModel;
import com.wonders.frame.core.model.vo.MultiQuerySqlElement;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;
/**
 * @author lushuaifeng
 *
 */
public interface MultiCrudService{	
	/**
	 * 定义复杂对象入库操作中，对象间的从属关系
	 *
	 */
	public enum RelateType {
		MAIN("main"),CHILD("child"), PARENT("parent");
		
		private String nodeName;
		
		private RelateType(String nodeName){
			this.nodeName=nodeName;	
		}
		public String nodeName(){
			return nodeName;
		}
	}
	public ReturnObj<SimplePage<HashMap<String,String>>> findByPage(String queryJson, Integer pageNum,Integer pageSize);
	public ReturnObj<SimplePage<HashMap<String,String>>> findByPage(String ruleType,String queryJson, Integer pageNum,Integer pageSize);
	public ReturnObj<SimplePage<HashMap<String,String>>> findByPage(MultiQuerySqlElement mse, Integer pageNum,Integer pageSize) ;
	
	public ReturnObj<List<HashMap<String,String>>> findAll(String queryJson, Integer rowNum,Integer rowSize);
	public ReturnObj<List<HashMap<String,String>>> findAll(String ruleType,String queryJson, Integer rowNum,Integer rowSize);
	public ReturnObj<List<HashMap<String,String>>> findAll(MultiQuerySqlElement mse, Integer rowNum,Integer rowSize) ;
	
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(String ruleType,List<IMultiModel> mms);
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(List<IMultiModel> objs);
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(String ruleType,IMultiModel mm);
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(IMultiModel mm);
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(String dataJson);
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(String ruleType,String dataJson);
}
