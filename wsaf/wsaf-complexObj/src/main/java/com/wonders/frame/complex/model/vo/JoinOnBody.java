package com.wonders.frame.complex.model.vo;

import java.util.List;

import com.wonders.frame.complex.model.vo.and.JointQuerys;

/**
 * @author mengjie select a.*,b.* ,c.* from (af_user_old a join af_role_old b on
 *         a.groupold_id=b.groupold_id ) join af_group_old c on
 *         a.removed=c.removed
 * 
 */
public class JoinOnBody {

	private Object fromValue;
	private String fromByname;
	private String joinType;
	private JoinBody joinBody;
	private OnBody onBody;

	public Object getFromValue() {
		return fromValue;
	}

	public void setFromValue(Object fromValue) {
		this.fromValue = fromValue;
	}

	public String getFromByname() {
		return fromByname;
	}

	public void setFromByname(String fromByname) {
		this.fromByname = fromByname;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public JoinBody getJoinBody() {
		return joinBody;
	}

	public void setJoinBody(JoinBody joinBody) {
		this.joinBody = joinBody;
	}

	public OnBody getOnBody() {
		return onBody;
	}

	public void setOnBody(OnBody onBody) {
		this.onBody = onBody;
	}

	public JoinOnBody(Object fromValue, String fromByname, String joinType,
			JoinBody joinBody, OnBody onBody) {
		this.fromValue = fromValue;
		this.fromByname = fromByname;
		this.joinType = joinType;
		this.joinBody = joinBody;
		this.onBody = onBody;
	}

	public JoinOnBody() {
	}

}
