package com.wonders.frame.core.model.vo;

import java.util.List;

import com.wonders.frame.core.service.SingleHqlBuilderService;
import com.wonders.frame.core.service.SingleHqlBuilderService.SqlComponent;

public class SingleHqlElement {
	/*hql:from User o where o.name like ?||'%' and id in(?)
	 * */
	private String hql;
	private List<PlaceholderParam> paramList;

	public SingleHqlElement(String hql, 
			List<PlaceholderParam> paramList) {
		this.hql = hql;
		this.paramList = paramList;
	}
	public String getHql() {
		return hql;
	}
	public String getQueryHql() {
		StringBuffer sb=new StringBuffer(SqlComponent.SELECT.componentExpress());
		sb.append(SingleHqlBuilderService.entityAlias).append(hql);
		return sb.toString();
	}
	public String getCountHql() {
		StringBuffer sb=new StringBuffer(SqlComponent.SELECT.componentExpress());
		sb.append(SingleHqlBuilderService.COUNT).append(hql);
		return sb.toString();
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public List<PlaceholderParam> getParamList() {
		return paramList;
	}
	public void setParamList(List<PlaceholderParam> paramList) {
		this.paramList = paramList;
	}
	
	
}
