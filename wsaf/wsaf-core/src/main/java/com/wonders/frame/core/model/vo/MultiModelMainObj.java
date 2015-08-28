package com.wonders.frame.core.model.vo;

/**
 * @author lushuaifeng
 *
 */
public class MultiModelMainObj {
	private String entityName;
	private Object objValue;

	public MultiModelMainObj(String entityName, Object objValue) {
		this.entityName = entityName;
		this.objValue = objValue;
	}

	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Object getObjValue() {
		return objValue;
	}
	public void setObjValue(Object objValue) {
		this.objValue = objValue;
	}
	
	
}
