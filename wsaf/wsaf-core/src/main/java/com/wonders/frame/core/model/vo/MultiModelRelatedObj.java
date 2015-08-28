package com.wonders.frame.core.model.vo;

import java.util.List;

/**
 * 该类用于记录复杂对象中关联对象（父对象、子对象）的外键字段、对象值（list和单个对象分别保存）、是否list及是否复杂对象等信息
 * @author lushuaifeng
 *
 */
public class MultiModelRelatedObj {
	private String[] fk;
	private List<Object> objList;
	private Object obj;
	private boolean isMultiModel;
	private boolean isList;
	
	public MultiModelRelatedObj(){
		
	}
	
	public MultiModelRelatedObj(String[] fk,
			boolean isMultiModel, boolean isList) {
		this.fk = fk;
		this.isMultiModel = isMultiModel;
		this.isList = isList;
	}
	public String[] getFk() {
		return fk;
	}
	public void setFk(String[] fk) {
		this.fk = fk;
	}

	public List<Object> getObjList() {
		return objList;
	}

	public void setObjList(List<Object> objList) {
		this.objList = objList;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isMultiModel() {
		return isMultiModel;
	}
	public void setMultiModel(boolean isMultiModel) {
		this.isMultiModel = isMultiModel;
	}
	public boolean isList() {
		return isList;
	}
	public void setList(boolean isList) {
		this.isList = isList;
	}


	

	
}
