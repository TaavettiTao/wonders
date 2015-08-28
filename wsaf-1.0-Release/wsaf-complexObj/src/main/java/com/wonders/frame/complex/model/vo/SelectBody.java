package com.wonders.frame.complex.model.vo;


public class SelectBody {


	private String byname;
	private String para;
	
	public String getByname() {
		return byname;
	}
	public void setByname(String byname) {
		this.byname = byname;
	}
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}
	public SelectBody(String byname, String para) {
		this.byname = byname;
		this.para = para;
	}
	public SelectBody() {
		super();
	}

}
