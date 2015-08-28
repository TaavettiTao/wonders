package com.wonders.frame.core.model.vo;

/**
 * 存数select Field的对象别名、列名、字段名(别名)及字段类型
 * @author lushuaifeng
 *
 */
public class MultiQuerySelectField {

	private String entityAlias;
	private String colName;
	private String fieldName;	
	private Class<?> fieldType;
	public MultiQuerySelectField(){
		
	}
	public MultiQuerySelectField(String entityAlias,String fieldName) {
		this.entityAlias = entityAlias;
		this.fieldName = fieldName;
	}
	
	public MultiQuerySelectField(String entityAlias,String colName, String fieldName,Class<?> fieldType) {
		this.entityAlias = entityAlias;
		this.colName = colName;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
	}
	
	public String getEntityAlias() {
		return entityAlias;
	}
	public void setEntityAlias(String entityAlias) {
		this.entityAlias = entityAlias;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Class<?> getFieldType() {
		return fieldType;
	}
	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	
	
	
	
}
