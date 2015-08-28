/** 
* @Title: ObjProperty.java 
* @Package com.wonders.frame.core.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.model.vo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.persistence.Column;
import com.wonders.frame.core.tags.ShowInView;

/** 
 * @ClassName: ObjectAttributeVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class ObjectAttributeVo {
	private String name;

	private Class<?> clazz;
	private Column column;
	private ShowInView showInView;
	private LinkedHashMap<String,String> option;
	private HashMap<String,LinkedHashMap<String,String>> codes;
	
	
	public ObjectAttributeVo(String name,Class<?> clazz,Column column){
		this.name=name;
		this.clazz=clazz;
		this.column=column;

	}
	
	public ObjectAttributeVo(String name,Class<?> clazz,Column column,ShowInView showInView){
		this.name=name;
		this.clazz=clazz;
		this.column=column;
		this.showInView=showInView;

	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Column getColumn() {
		return column;
	}
	public void setColumn(Column column) {
		this.column = column;
	}
	public ShowInView getShowInView() {
		return showInView;
	}
	public void setShowInView(ShowInView showInView) {
		this.showInView = showInView;
	}
	public LinkedHashMap<String, String> getOption() {
		return option;
	}
	public void setOption(LinkedHashMap<String, String> option) {
		this.option = option;
	}
	public HashMap<String, LinkedHashMap<String, String>> getCodes() {
		return codes;
	}
	public void setCodes(HashMap<String, LinkedHashMap<String, String>> codes) {
		if(this.codes==null){
			this.codes=codes;
		}else{
			this.codes.putAll(codes);
		}
	}
}
