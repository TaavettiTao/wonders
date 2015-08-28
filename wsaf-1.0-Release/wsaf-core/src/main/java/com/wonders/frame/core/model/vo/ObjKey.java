package com.wonders.frame.core.model.vo;

public class ObjKey {
	private String type;
	private Integer id;
	
	public ObjKey(String type,Integer id){
		this.type=type;
		this.id=id;
	}
	
	public String getKeyNo(){
		return this.type+"_"+this.id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
