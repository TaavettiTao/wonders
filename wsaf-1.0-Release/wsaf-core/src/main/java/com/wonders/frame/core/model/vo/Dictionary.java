/** 
* @Title: Dictionary.java 
* @Package com.wonders.frame.core.model.vo.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.model.vo;


/** 
 * @ClassName: Dictionary 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class Dictionary {
//	public static enum FieldOperate {
//		HIDDEN("hidden"),
//		DATE("range_date"),
//		LONG("range_long"),
//		INTEGER("range_int"),
//		STRING("input"), 
//		ENUM("select");
//	
//		private String operate;
//		
//		private FieldOperate(String operate){
//			this.operate=operate;	
//		}
//		public String operate(){
//			return operate;
//		}
//	}

	
	public static enum Gender implements GenericEnum{
		MALE(1,"男"), FEMALE(2,"女");

		private int code;
		private String description;
		
		private Gender(int code,String description){
			this.code=code;	
			this.description=description;	
		}
		public int code(){
			return code;
		}
		public String description(){
			return description;
		}
		
	}
	
	public static enum YesOrNo implements GenericEnum{
		YES(1,"是"), NO(0,"否");

		private int code;
		private String description;
		
		private YesOrNo(int code,String description){
			this.code=code;	
			this.description=description;	
		}
		public int code(){
			return code;
		}
		public String description(){
			return description;
		}
	}
	
}
