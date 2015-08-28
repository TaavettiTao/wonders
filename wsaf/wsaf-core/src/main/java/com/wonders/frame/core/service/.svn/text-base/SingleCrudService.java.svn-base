package com.wonders.frame.core.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wonders.frame.core.model.vo.EntityProperty;
import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.model.vo.SingleQueryParams;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;

public interface SingleCrudService<T,ID extends Serializable> {
	
	public enum KeyWord {
		PAGE("page"), ROW("row"),COUNT("count"),SORT("sort"),CONDITION_EXPRESS("conditionExpress");
		
		//函数及运算子表达式
		private String word;
		
		private KeyWord(String word){
			this.word=word;	
		}
		public String word(){
			return word;
		}
	}
	
	public enum QueryType {
		COUNT, PAGE,ROW,ALL;
	}
	
	
	public ReturnObj<EntityProperty> getProperty(Class<?> clazz, String fields, boolean show);
	
	public ReturnObj<List<T>> find(Class<?> clazz, HttpServletRequest request);
	
	public ReturnObj<List<T>> find(SingleQueryParams sqp);
	
	public ReturnObj<SimplePage<T>> findByPage(Class<?> clazz, HttpServletRequest request);
	
	public ReturnObj<SimplePage<T>> findByPage(SingleQueryParams sqp);
	
	public ReturnObj<Long> count(Class<?> clazz, HttpServletRequest request);
	
	public ReturnObj<Long> count(SingleQueryParams sqp);
	
	public ReturnObj<List<T>> findByIds(Class<?> clazz, List<ID> ids);
	
	public ReturnObj<T> get(Class<?> clazz, ID id);
	
	public ReturnObj<Integer> removeAll(Class<?> clazz, HttpServletRequest request);
	
	public ReturnObj<Integer> removeAll(SingleQueryParams sqp);
	
	public ReturnObj<Integer> removeById(Class<?> clazz, ID id);
	
	public ReturnObj<Integer> removeByIds(Class<?> clazz, List<ID> ids);
	
	public ReturnObj<Integer> deleteAll(Class<?> clazz, HttpServletRequest request);
	
	public ReturnObj<Integer> deleteAll(SingleQueryParams qp);
	
	public ReturnObj<Integer> deleteById(Class<?> clazz, ID id);
	
	public ReturnObj<Integer> deleteByIds(Class<?> clazz, List<ID> ids);
	
	public ReturnObj<T> saveOrUpdate(Class<?> clazz, HttpServletRequest request);
		
	public ReturnObj<T> saveOrUpdate(SingleModelParams mp);
	

}
