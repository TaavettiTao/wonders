package com.wonders.frame.core.model.vo;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wonders.frame.core.model.vo.ReturnObjInfo.ErrorType;

public class ReturnObj<T> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	private ReturnObjInfo info;
	
	private T data;
	
	public ReturnObj() {
		
	}

	public ReturnObj(T data) {
		if(data!=null){
			this.info=new ReturnObjInfo(true);
			this.data=data;
		}else{
			this.info=new ReturnObjInfo(ErrorType.NULL);

		}
	}
	
	public ReturnObj(ReturnObjInfo info) {
			setInfo(info);
	}
	
	public ReturnObj(boolean isSuccess) {
		this.info=new ReturnObjInfo(isSuccess);
	}

	public ReturnObj(String error) {
		this.info=new ReturnObjInfo(error);
	}
	
	public ReturnObj(ErrorType errorType) {
		this.info=new ReturnObjInfo(errorType);
	}
	
	public ReturnObj(HashMap<String,String> errorMap) {
		this.info=new ReturnObjInfo(errorMap);
	}
	
	public ReturnObj(List<ValidError> errors) {
		HashMap<String,String> errorMap=new HashMap<String,String>();
		
		for(ValidError error:errors){
			errorMap.put(error.getFieldName(), error.getMessage());
		}
		
		this.info=new ReturnObjInfo(errorMap);
	}
		
	public ReturnObj(Throwable e) {
		this.info=new ReturnObjInfo(e);
	}

	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}

	public ReturnObjInfo getInfo() {
		return info;
	}

	public void setInfo(ReturnObjInfo info) {
		this.info = info;
	}
	
	
	


}
