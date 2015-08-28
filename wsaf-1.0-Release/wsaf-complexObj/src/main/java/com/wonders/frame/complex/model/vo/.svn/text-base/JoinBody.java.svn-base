package com.wonders.frame.complex.model.vo;

import java.util.List;

import com.wonders.frame.complex.model.vo.and.JointQuerys;

/**
 * @author mengjie 
 * select a.*,b.* ,c.* from (af_user_old a join af_role_old b on a.groupold_id=b.groupold_id ) join af_group_old c on
 *         a.removed=c.removed
 * 
 */
public class JoinBody {

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

	public JoinBody(String byname, String para) {
		this.byname = byname;
		this.para = para;
	}

	public JoinBody() {
	}

}
