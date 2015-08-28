package com.wonders.frame.core.model.vo;

import java.util.List;

public class RulePathVo {
	private List<String> objList;
	private List<String> opList;
	
	public RulePathVo(List<String> objList, List<String> opList) {
		this.objList = objList;
		this.opList = opList;
	}
	
	public List<String> getObjList() {
		return objList;
	}

	public void setObjList(List<String> objList) {
		this.objList = objList;
	}
	public List<String> getOpList() {
		return opList;
	}
	public void setOpList(List<String> opList) {
		this.opList = opList;
	}
}
