/** 
* @Title: ObjProperty.java 
* @Package com.wonders.frame.core.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.model.vo;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.wonders.frame.core.tags.ShowInView;

/** 
 * @ClassName: ObjectInfoVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class ObjectInfoVo {
	private Table table;
	private ShowInView showInView;
	private Class<?> clazz;
	private String entityName;
	private HashMap<String,ObjectAttributeVo> attributeMap;
	private List<ObjectAttributeVo> attributeList;
	
	public ObjectInfoVo(Class<?> clazz){
		this.clazz=clazz;
		this.entityName=StringUtils.uncapitalize(clazz.getSimpleName());
		this.table=clazz.getAnnotation(Table.class);
		this.showInView=clazz.getAnnotation(ShowInView.class);
	}

	
	
	public String getEntityName() {
		return entityName;
	}



	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}



	public Table getTable() {
		return table;
	}



	public void setTable(Table table) {
		this.table = table;
	}



	public ShowInView getShowInView() {
		return showInView;
	}



	public void setShowInView(ShowInView showInView) {
		this.showInView = showInView;
	}



	public HashMap<String, ObjectAttributeVo> getAttributeMap() {
		return attributeMap;
	}
	public ObjectAttributeVo getAttribute(String attributeName) {
		return attributeMap.get(attributeName);
	}
	public void setAttributeMap(HashMap<String, ObjectAttributeVo> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public List<ObjectAttributeVo> getAttributeList() {
		return attributeList;
	}



	public void setAttributeList(List<ObjectAttributeVo> attributeList) {
		this.attributeList = attributeList;
	}



	public Class<?> getClazz() {
		return clazz;
	}



	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}	

	
}
