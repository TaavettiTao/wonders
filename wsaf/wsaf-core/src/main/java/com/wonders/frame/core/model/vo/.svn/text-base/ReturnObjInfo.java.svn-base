package com.wonders.frame.core.model.vo;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReturnObjInfo {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Boolean success;
	
	private String code;
	
	private HashMap<String,String> error;
	
	public ReturnObjInfo(Boolean success) {
		this.success = success;
	}
	
	public ReturnObjInfo(ErrorType type) {
		this.success = false;
		setError(type);
	}
	
	public ReturnObjInfo(String error) {
		this.success = false;
		setError(error);
	}
	
	public ReturnObjInfo(Throwable error) {
		this.success = false;
		setError(error);
	}
	
	public ReturnObjInfo(HashMap<String,String> error) {
		this.success = false;
		setError(error);
	}
	
	public ReturnObjInfo(Boolean success, String code,
			HashMap<String, String> error) {
		this.success = success;
		this.code = code;
		this.error = error;
	}

	public ReturnObjInfo(Boolean success, String code,
			String error) {
		this.success = success;
		this.code = code;
		setError(error);
	}


	public Boolean getSuccess() {
		return success;
	}



	public void setSuccess(Boolean success) {
		this.success = success;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public HashMap<String, String> getError() {
		return error;
	}

	public String getError(String key) {
		return error.get(key);
	}

	public void setError(HashMap<String, String> error) {
		if(this.error==null){
			this.error=new HashMap<String,String>();
		}
		this.error.putAll(error);		
	}

	public void setError(ErrorType type,String error) {
		if(this.error==null){
			this.error=new HashMap<String,String>();
		}
		this.error.put(type.toString().toLowerCase(), error);
		this.code=type.code;
	}

	
	public void setError(Throwable e) {
		outputError(e);
		setError(ErrorType.UNEXCEPTED);
	}
	
	public void setError(String error) {
		setError(ErrorType.MSG,error);
	}
	
	public void setError(ErrorType type) {
		if(this.error==null){
			this.error=new HashMap<String,String>();
		}
		this.error.put(type.toString().toLowerCase(), type.detail);
		this.code=type.code;
	}
	
	
	private void outputError(Throwable e){

		logger.error(buildMainError(e));
		
		logger.debug("error detail in ReturnObj",e);
	}
	
	private String buildMainError(Throwable e){

		StringBuffer error = new StringBuffer();
		
		error.append(ErrorType.UNEXCEPTED.detail)
		.append("(").append(ErrorType.UNEXCEPTED.code).append("): ")
		.append(e.toString()).append("\n");
				
		StackTraceElement[] messages = e.getStackTrace();
		int length = messages.length;
		for (int i = 0; i < length; i++) {
			if (messages[i].getClassName().indexOf("com.wonders") > -1 && messages[i].getFileName().endsWith(".java")) {

				error.append("             at ")
				.append(messages[i].getClassName()).append(".")
				.append(messages[i].getMethodName()).append(":")
				.append(messages[i].getLineNumber()).append("\n");
				

			}
			
		}
		
		return error.toString();
	}

	public enum ErrorType {
		MSG("000",""),//一般信息，返回给前台
		NULL("001","No matched record be found"),//查询失败，返回值为空
		ZERO("002","No matched record be operated"),//操作失败，0条记录被操作
		INVALID("004","Data is invalid"),//数据无效
		UNEXCEPTED("999","Unexcepted RuntimeException");//未预期的运行时异常
		
		private String code;
		private String detail;
		
		private ErrorType(String code,String detail){
			this.code=code;
			this.detail=detail;	

		}
		public String detail(){
			return detail;
		}
		
		public String code(){
			return code;
		}
	}
}
