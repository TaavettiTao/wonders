/** 
* @Title: ObjProperty.java 
* @Package com.wonders.frame.core.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.model.vo;

import java.util.List;

/** 
 * @ClassName: ObjProperty 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class EntityProperty {
	private String name;
	private String cnName;
	private List<FieldProperty> fieldProperties;
	
	public EntityProperty(String name,String cnName){
		this.name=name;
		if(cnName!=null&&!cnName.equals("")){
			this.cnName=cnName;
		}else{
			this.cnName=name;
		}
	}

	public List<FieldProperty> getFieldProperties() {
		return fieldProperties;
	}
	public void setFieldProperties(List<FieldProperty> fieldProperties) {
		this.fieldProperties = fieldProperties;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
}
