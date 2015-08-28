package com.wonders.frame.complex.model.vo;

import com.wonders.frame.core.utils.JacksonMapper;

public class GeneralQuery {
	private String logicalOper;//and or
	private String para;
	private String relationalOper;//< > <= >= 
	private Object value;

	private Boolean leftBracket;
	private Boolean rightBracket;
	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getLogicalOper() {
		return logicalOper;
	}

	public void setLogicalOper(String logicalOper) {
		this.logicalOper = logicalOper;
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

	public Boolean getLeftBracket() {
		return leftBracket;
	}

	public void setLeftBracket(Boolean leftBracket) {
		this.leftBracket = leftBracket;
	}

	public Boolean getRightBracket() {
		return rightBracket;
	}

	public void setRightBracket(Boolean rightBracket) {
		this.rightBracket = rightBracket;
	}

	public GeneralQuery(String logicalOper, String para, String relationalOper,
			Object value, Boolean leftBracket, Boolean rightBracket) {
		this.logicalOper = logicalOper;
		this.para = para;
		this.relationalOper = relationalOper;
		this.value = value;
		this.leftBracket = leftBracket;
		this.rightBracket = rightBracket;
	}

	public GeneralQuery() {
	}

}
