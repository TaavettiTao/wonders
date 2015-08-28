package com.wonders.frame.core.model.vo;

import java.util.LinkedHashMap;
import com.wonders.frame.core.service.SingleCrudService.QueryType;

public class SingleQueryParams extends SingleModelParams{

	private QueryType queryType;
	private Integer range1;
	private Integer range2;
	private String conditionExpress;
	private LinkedHashMap<String,String> sort;

	public SingleQueryParams(Class<?> clazz){
		super(clazz);
	}
	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}
	
	public Integer getRange1() {
		return range1;
	}

	public void setRange1(Integer range1) {
		this.range1 = range1;
	}

	public Integer getRange2() {
		return range2;
	}

	public void setRange2(Integer range2) {
		this.range2 = range2;
	}

	public String getConditionExpress() {
		return conditionExpress;
	}

	public void setConditionExpress(String conditionExpress) {
		this.conditionExpress = conditionExpress;
	}

	public LinkedHashMap<String, String> getSort() {
		return sort;
	}

	public void addSort(String fieldName, String sort) {
		if(this.sort==null){
			this.sort=new LinkedHashMap<String,String>();
		}

		this.sort.put(fieldName,sort);			
	}
	
	public void setSort(LinkedHashMap<String, String> sort) {
		this.sort = sort;
	}

}
