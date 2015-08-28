package com.wonders.frame.complex.model.vo;

import com.wonders.frame.core.utils.JacksonMapper;


public class RemoveBody {

	private String para;
	private String relationalOper;//< > <= >= 
	private Object value;

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getRelationalOper() {
		return relationalOper;
	}

	public void setRelationalOper(String relationalOper) {
		this.relationalOper = relationalOper;
	}

	public Object getValue() {
		return value;
	}

	/**
	 * 1、由于嵌套操作，value值有不同类型
	 * 2、若value为ComplexQuery类型时，jackson解析出来的value为LinkedHashMap
	 * ，需再强转一次，才能将map反序列化为对象
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		String className = value.getClass().getSimpleName();
		if (className.equals("LinkedHashMap")) {
			this.value = JacksonMapper.convert(value, ComplexQuery.class);
		} else {
			this.value = value;
		}
	}

	public RemoveBody(String para, String relationalOper, Object value) {
		this.para = para;
		this.relationalOper = relationalOper;
		this.value = value;
	}

	public RemoveBody() {
	}


}
