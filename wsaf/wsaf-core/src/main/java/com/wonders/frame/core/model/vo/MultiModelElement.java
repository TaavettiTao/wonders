package com.wonders.frame.core.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于记录复杂对象入库过程中所需要记录的各个元素参数值
 * mainObjFieldName：复杂对象中标注为主对象的字段值
 * mainObjClazz：复杂对象中标注为主对象的字段值
 * parentObj：复杂对象中标注为父对象的字段值，由于可能有多个父对象一起保存，所以为list
 * childObj：复杂对象中标注为子对象的字段值，由于可能有多个子对象一起保存，所以为list
 * @author lushuaifeng
 *
 */
public class MultiModelElement {
	private Object mainObj;
	private List<MultiModelRelatedObj> parentObj;
	private List<MultiModelRelatedObj> childObj;

	public Object getMainObj() {
		return mainObj;
	}
	public void setMainObj(Object mainObj) {
		this.mainObj = mainObj;
	}
	public List<MultiModelRelatedObj> getParentObj() {
		return parentObj;
	}
	public void setParentObj(List<MultiModelRelatedObj> parentObj) {
		this.parentObj = parentObj;
	}
	
	public void addParentObj(MultiModelRelatedObj parentObj) {
		if(this.parentObj == null){
			this.parentObj=new ArrayList<MultiModelRelatedObj>();			
		}
		this.parentObj.add(parentObj);
	}
	public List<MultiModelRelatedObj> getChildObj() {
		return childObj;
	}
	public void setChildObj(List<MultiModelRelatedObj> childObj) {
		this.childObj = childObj;
	}
	public void addChildObj(MultiModelRelatedObj childObj) {
		if(this.childObj == null){
			this.childObj=new ArrayList<MultiModelRelatedObj>();			
		}
		this.childObj.add(childObj);
	}
	
}
