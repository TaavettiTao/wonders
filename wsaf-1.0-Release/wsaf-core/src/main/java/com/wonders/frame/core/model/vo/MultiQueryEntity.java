package com.wonders.frame.core.model.vo;

import java.util.HashMap;

/**
 * 用于暂存查询过程中涉及的各对象的属性所对应的列名及数据类型
 * @author lushuaifeng
 *
 */
public class MultiQueryEntity {
	private String entityName;
//	private String tableName;
	private HashMap<String,MultiQueryField> fieldAttribute;

	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

//	public String getTableName() {
//		return tableName;
//	}
//
//	public void setTableName(String tableName) {
//		this.tableName = tableName;
//	}

	public MultiQueryEntity(String entityName,
			HashMap<String, MultiQueryField> fieldAttribute) {
		this.entityName=entityName;
		this.fieldAttribute = fieldAttribute;
	}
	
	public MultiQueryEntity(
			HashMap<String, MultiQueryField> fieldAttribute) {
		this.fieldAttribute = fieldAttribute;
	}

	public HashMap<String, MultiQueryField> getFieldAttribute() {
		return fieldAttribute;
	}
	
	public MultiQueryField getFieldAttribute(String FieldName) {
		return fieldAttribute.get(FieldName);
	}
	
	public void setFieldAttribute(HashMap<String, MultiQueryField> fieldAttribute) {
		if(this.fieldAttribute==null){
			this.fieldAttribute = fieldAttribute;
		}else{
			this.fieldAttribute.putAll(fieldAttribute);
		}
	}	

	
}
