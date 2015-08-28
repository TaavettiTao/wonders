package com.wonders.frame.complex.model.vo.and;

import java.util.List;

public class JointQuery {
	private String operator;//eg:=,<,>
	private List<String> value;

	/**
	 * @return
	 * eg:=,<,>
	 */
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}

}
