package com.wonders.frame.core.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.wonders.frame.core.service.MultiSqlBuilderService.SqlComponent;
import com.wonders.frame.core.service.MultiSqlBuilderService.SqlDefinedSign;

public class MultiQuery {
	private MultiQuerySelect select;
	private StringBuilder from;
	private StringBuilder where;
	private String group;
	private StringBuilder having;
	private StringBuilder order;	
	private StringBuilder condition;		
	private List<PlaceholderParam> queryParams;	

	private String joinType;
	private StringBuilder joinEntityA;	
	private StringBuilder joinEntityB;
	private StringBuilder on;
	
	public MultiQuery(){
		this.from=new StringBuilder();
		this.where=new StringBuilder();
		this.group="";
		this.having=new StringBuilder();
		this.order=new StringBuilder();	
		this.condition=new StringBuilder();		
		this.on=new StringBuilder();		
	}
	public MultiQuerySelect getSelect() {
		return select;
	}
	public void setSelect(MultiQuerySelect select) {
		this.select = select;
	}
	public StringBuilder getFrom() {
		return from;
	}
	public void setFrom(StringBuilder from) {
		this.from = from;
	}
	public StringBuilder getWhere() {
		return where;
	}
	public void setWhere(StringBuilder where) {
		if(where.length()==0) return;
		if(this.where.length()>0){
			this.where.append(SqlDefinedSign.$and.signExpress());
		}
		this.where.append(where);
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public StringBuilder getHaving() {
		return having;
	}
	public void setHaving(StringBuilder having) {
		this.having = having;
	}
	public StringBuilder getOrder() {
		return order;
	}
	public void setOrder(StringBuilder order) {
		this.order = order;
	}
	public StringBuilder getCondition() {
		return condition;
	}
	public void setCondition(StringBuilder condition) {
		this.condition = condition;
	}
	

	public List<PlaceholderParam> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(List<PlaceholderParam> queryParams) {
		if(queryParams==null) return;
		if(this.queryParams==null){
			this.queryParams = queryParams;
		}else{
			this.queryParams.addAll(queryParams);
		}
	}
	
	public void setQueryParam(PlaceholderParam queryParam) {
		if(queryParam==null) return;
		if(this.queryParams==null){
			this.queryParams = new ArrayList<PlaceholderParam>();
		}
		this.queryParams.add(queryParam);
	}
	
	
	public StringBuilder getSql(){
		StringBuilder sb=new StringBuilder();
		if(select.getSql().equals("")||from.length()==0) return sb;
		
		sb.append(SqlComponent.SELECT.componentExpress())
		.append(select.getSql())
		.append(SqlComponent.FROM.componentExpress()).append(from);
		
		if(where.length()>0){
			sb.append(SqlComponent.WHERE.componentExpress()).append(where);
		}
		
		if(group.length()>0){
			sb.append(SqlComponent.GROUP.componentExpress()).append(group);
		}
		
		if(having.length()>0){
			sb.append(SqlComponent.HAVING.componentExpress()).append(having);
		}
		
		if(order.length()>0){
			sb.append(SqlComponent.ORDER.componentExpress()).append(order);
		}
		
		return sb;
	}
	
	public StringBuilder getSqlWithNoAliaNameRepeat(){
		StringBuilder sb=new StringBuilder();
		if(select.getSqlWithNoAliaNameRepeat().equals("")||from.length()==0) return sb;
		
		sb.append(SqlComponent.SELECT.componentExpress())
		.append(select.getSqlWithNoAliaNameRepeat())
		.append(SqlComponent.FROM.componentExpress()).append(from);
		
		if(where.length()>0){
			sb.append(SqlComponent.WHERE.componentExpress()).append(where);
		}
		
		if(group.length()>0){
			sb.append(SqlComponent.GROUP.componentExpress()).append(group);
		}
		
		if(having.length()>0){
			sb.append(SqlComponent.HAVING.componentExpress()).append(having);
		}
		
		if(order.length()>0){
			sb.append(SqlComponent.ORDER.componentExpress()).append(order);
		}
		
		return sb;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public StringBuilder getJoinEntityA() {
		return joinEntityA;
	}
	public void setJoinEntityA(StringBuilder joinEntityA) {
		this.joinEntityA = joinEntityA;
	}
	public StringBuilder getJoinEntityB() {
		return joinEntityB;
	}
	public void setJoinEntityB(StringBuilder joinEntityB) {
		this.joinEntityB = joinEntityB;
	}
	public StringBuilder getOn() {
		return on;
	}
	public void setOn(StringBuilder on) {
		if(on.length()==0) return;
		
		if(this.on.length()>0){
			this.on.append(SqlDefinedSign.$and.signExpress());
		}
		this.on.append(on);
	}

	public StringBuilder getJoinSql(){
		StringBuilder sb=new StringBuilder();
		
		if(joinType.equals("")||joinEntityA.length()==0||joinEntityB.length()==0) return sb;
		
		sb.append("(");
		
		sb.append(joinEntityA).append(joinType).append(joinEntityB);
		
		if(on.length()>0){
			sb.append(SqlDefinedSign.$on.signExpress()).append(on);
		}
		sb.append(")");		
		
		return sb;
	}
}
