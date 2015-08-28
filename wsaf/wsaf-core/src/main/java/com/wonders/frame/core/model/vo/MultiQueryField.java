package com.wonders.frame.core.model.vo;

/**
 * 用于标记对象属性所对应的列名和数据类型.当属性是复合对象（如函数等）时，保存对应的复合对象sql语法名及最终数据类型
 * @author lushuaifeng
 *
 */
public class MultiQueryField{
	private String colName;
	private Class<?> fieldType;
	
	public MultiQueryField(String colName,Class<?> fieldType){
		this.colName=colName;
		this.fieldType=fieldType;

	}
	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}
	
}
