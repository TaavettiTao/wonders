package com.wonders.frame.core.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.wonders.frame.core.service.MultiSqlBuilderService.SqlComponent;

public class MultiQuerySqlElement {
	private String sql;
	//private List<String> fieldList;
	private List<PlaceholderParam> paramList;

	public MultiQuerySqlElement(String sql,
			List<PlaceholderParam> paramList) {
		this.sql = sql;
		//this.fieldList = fieldList;
		this.paramList = paramList;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<String> getFieldList() {
		List<String> fl=new ArrayList<String>();
		int idx1=SqlComponent.SELECT.componentExpress().length();
		int idx2=sql.indexOf(SqlComponent.FROM.componentExpress());
		
		String sqlSelectField=sql.substring(idx1, idx2);
		String[] selectFields=sqlSelectField.split(",");
		for(String selectField:selectFields){
			String[] fields=selectField.trim().split(" ");
			fl.add(fields[1].trim());
		}
		
		
		return fl; 
	}
//	public void setFieldList(List<String> fieldList) {
//		this.fieldList = fieldList;
//	}
	public List<PlaceholderParam> getParamList() {
		return paramList;
	}
	public void setParamList(List<PlaceholderParam> paramList) {
		this.paramList = paramList;
	}
	
	
}
