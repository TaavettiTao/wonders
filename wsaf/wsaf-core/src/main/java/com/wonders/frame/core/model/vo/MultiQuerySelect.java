package com.wonders.frame.core.model.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiQuerySelect {
	private List<MultiQuerySelectField> selectFields;
	private List<PlaceholderParam> queryParams;		

	public String getSql(){		
		if(selectFields==null) return "";
		
		StringBuilder sql=new StringBuilder();
		//for(MultipleSelectField msf:selectFields){
		for(int i=0;i<selectFields.size();i++){
			MultiQuerySelectField msf=selectFields.get(i);
			
			if(sql.length()>0){
				
				sql.append(",");
			}
			if(msf.getEntityAlias()!=null&&!msf.getEntityAlias().equals("")){
				sql.append(msf.getEntityAlias()).append(".");
			}
			sql.append(msf.getColName());
			
			if(msf.getFieldName()!=null&&!msf.getFieldName().equals("")){
				//sql.append(" ").append(msf.getFieldName());
				//为sql中select的字段别名添加编号，编号规则：alias+index+"_"+count+"_"
				String aliasNum="_"+i;
				sql.append(" ").append(msf.getFieldName().concat(aliasNum));
			}
			
		}
		
		return sql.toString();
	}
	//构造当前select所在sql作为子查询时，子查询对象的属性
	public HashMap<String,MultiQueryField> getSubQueryEntityAttributeMap(){
		HashMap<String,MultiQueryField> hmAttribute=new HashMap<String,MultiQueryField>();
		if(selectFields==null) return hmAttribute;
		//for(MultipleSelectField msf:selectFields){
		for(int i=0;i<selectFields.size();i++){
			MultiQuerySelectField msf=selectFields.get(i);
			String aliasNum="_"+i;
			//hmAttribute.put(msf.getFieldName(),new MultipleQueryField(msf.getFieldName(),msf.getFieldType()));
			hmAttribute.put(msf.getFieldName(),new MultiQueryField(msf.getFieldName().concat(aliasNum),msf.getFieldType()));
		}
		return hmAttribute;
	}
	
	public List<MultiQuerySelectField> getSelectFields() {
		return selectFields;
	}
	public void setSelectFields(List<MultiQuerySelectField> selectFields) {
		if(selectFields==null)return;
		if(this.selectFields==null){
			this.selectFields = selectFields;
		}else{
			this.selectFields.addAll(selectFields);
		}
	}
	
	
	public void setSelectField(MultiQuerySelectField selectField) {
		if(selectField==null)return;
		if(this.selectFields==null){
			this.selectFields = new ArrayList<MultiQuerySelectField>();
		}
		this.selectFields.add(selectField);
	}
	
	public List<PlaceholderParam> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(List<PlaceholderParam> queryParams) {
		if(queryParams==null)return;
		if(this.queryParams==null){
			this.queryParams = queryParams;
		}else{
			this.queryParams.addAll(queryParams);
		}
	}
	
	public void setQueryParam(PlaceholderParam queryParam) {
		if(queryParam==null)return;
		if(this.queryParams==null){
			this.queryParams = new ArrayList<PlaceholderParam>();
		}
		this.queryParams.add(queryParam);
	}
	
}
