/** 
* @Title: PageField.java 
* @Package com.wonders.frame.core.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.model.vo;

import java.util.HashMap;
import java.util.LinkedHashMap;


/** 
 * @ClassName: FieldProperty 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class FieldProperty {
	private String name;
	private String path;
	private String operate;
	private Class<?> returnType;
	private String columnName;
	//the select options for field
	private LinkedHashMap<String,String> option;
	//the select options which has parent code
	private HashMap<String,LinkedHashMap<String,String>> codes;
	
	public FieldProperty(){		
	}

	public FieldProperty(String path){
		this.path=path;
		this.name=path;

	}
	
	public FieldProperty(String path,String name){
		this.path=path;
		if(name!=null && !name.equals("")){
			this.name=name;
		}else{
			this.name=path;
		}

	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		
		this.operate = operate;
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

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	
	
	
//	public static void main(String[] args){
//		 Field[] fields=User.class.getDeclaredFields();		
//		 
//		 for(int i=0;i<fields.length;i++){		
//			 if(fields[i].getName().equalsIgnoreCase("serialVersionUID")){
//				 continue;
//			 }
//
//			 System.out.println(fields[i].getName()+":"+fields[i].getType().getSimpleName()+":"+fields[i].getType().isEnum());
//			 if(fields[i].getType().isEnum()){
//				 LinkedHashMap<String, String> lhm=ReflectUtil.getEnumStringAndDescriptionMap(fields[i].getType());
//				 for(String key:lhm.keySet()){
//					 System.out.println(key+":"+lhm.get(key));
//				 }
//	            
//			 }
//			
//		 }
//	}
}
