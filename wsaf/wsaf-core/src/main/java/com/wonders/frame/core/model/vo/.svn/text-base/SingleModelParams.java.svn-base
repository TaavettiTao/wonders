package com.wonders.frame.core.model.vo;

import java.util.HashMap;

import org.springframework.util.StringUtils;

public class SingleModelParams {
	private String objName;
	private Class<?> objClazz;
	
	private HashMap<String,String> data;
	
	public SingleModelParams(Class<?> objClazz){
		this.objClazz = objClazz;
		this.objName=StringUtils.uncapitalize(objClazz.getSimpleName());
	}

	public Class<?> getObjClazz() {
		return objClazz;
	}

	public void setObjClazz(Class<?> objClazz) {
		this.objClazz = objClazz;
	}
	
	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}
	
	public HashMap<String, String> getData() {
		return data;
	}

	public void addData(String fieldName, String data) {
		if(this.data==null){
			this.data=new HashMap<String,String>();
		}

		this.data.put(fieldName,data);			
	}
	
	public void setData(HashMap<String, String> data) {
		this.data = data;
	}

	
}
