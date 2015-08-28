package com.wonders.frame.core.model.vo;

public class ValidError {
	private String rootBeanName;
	private String leafBeanName;
	private String propertyPath;
	private String fieldName;
	
	private Object invalidValue;
	private String message;
	
	
	public ValidError(){
	}
	
	public ValidError(String rootBeanName, String leafBeanName, String propertyPath,
			String fieldName,Object invalidValue, String message) {
		this.rootBeanName = rootBeanName;
		this.leafBeanName = leafBeanName;
		this.propertyPath = propertyPath;
		this.fieldName = fieldName;
		this.invalidValue = invalidValue;
		this.message = message;
	}
	
	public ValidError(String rootBeanName, String leafBeanName, String propertyPath,
			Object invalidValue, String message) {
		this.rootBeanName = rootBeanName;
		this.leafBeanName = leafBeanName;
		this.propertyPath = propertyPath;
		String[] paths=propertyPath.split("\\.");
		this.fieldName=paths[paths.length-1];
		this.invalidValue = invalidValue;
		this.message = message;
	}
	public String getRootBeanName() {
		return rootBeanName;
	}
	public void setRootBeanName(String rootBeanName) {
		this.rootBeanName = rootBeanName;
	}
	
	public String getLeafBeanName() {
		return leafBeanName;
	}
	public void setLeafBeanName(String leafBeanName) {
		this.leafBeanName = leafBeanName;
	}
	public String getPropertyPath() {
		return propertyPath;
	}
	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getInvalidValue() {
		return invalidValue;
	}
	public void setInvalidValue(Object invalidValue) {
		this.invalidValue = invalidValue;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
