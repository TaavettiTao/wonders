package com.wonders.frame.complex.model.vo;

import java.util.LinkedHashMap;

import com.wonders.frame.core.utils.JacksonMapper;

public class FromBody {



	private String byname;
	private Object value;
	public String getByname() {
		return byname;
	}
	public void setByname(String byname) {
		this.byname = byname;
	}
	
	public Object getValue() {
		return value;
	}
	
	/**
	 * 1、由于嵌套操作，value值有不同类型
	 * 2、若value为ComplexQuery类型时，jackson解析出来的value为LinkedHashMap，需再强转一次，才能将map反序列化为对象
	 * @param value
	 */
	public void setValue(Object value) {
		String className = value.getClass().getSimpleName();
		if(className.equals("LinkedHashMap")){
			LinkedHashMap ss = (LinkedHashMap)value;
			if(ss.containsKey("joinType")){
				this.value = JacksonMapper.convert(value, JoinOnBody.class);
			}else{
				this.value = JacksonMapper.convert(value, ComplexQuery.class);
			}
		}else{
			this.value = value;
		}
	}
}
