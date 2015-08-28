package com.wonders.frame.core.service;

import com.wonders.frame.core.model.vo.SingleHqlElement;
import com.wonders.frame.core.model.vo.SingleQueryParams;

public interface SingleHqlBuilderService {
public final static String logicDelField="removed";
	
	public final static String entityAlias=" o";
	
	public final static String COUNT="count(*)";
	
	public enum SqlComponent {
		SELECT("select "),FROM(" from "), WHERE(" where "),ORDER(" order by ");
		//sql语句组成部分
		private String componentExpress;
		
		private SqlComponent(String componentExpress){
			this.componentExpress=componentExpress;	
		}
		public String componentExpress(){
			return componentExpress;
		}
	}
	
	public static enum SqlDefinedSign {
		$or(" or  "),
		$and(" and "),
		$null(" is null"),	
		$notnull(" is not null"),
		ne(" != ?"),
		in(" in (?)"),
		nin(" not in (?)"),
		l(" like '%'||?||'%'"),
		sl(" like ?||'%'"),
		el(" like '%'||?"),
		nl(" not like '%'||?||'%'"),
		nsl(" not like ?||'%'"),
		nel(" not like '%'||?"),
		s(" >= ?"),
		e(" <= ?");
		//函数及运算子表达式
		private String signExpress;
		
		private SqlDefinedSign(String signExpress){
			this.signExpress=signExpress;	
		}
		public String signExpress(){
			return signExpress;
		}
	}
	public enum OrderSort {
		asc(" asc "),desc(" desc");
		//order排序
		private String sortExpress;
		
		private OrderSort(String sortExpress){
			this.sortExpress=sortExpress;	
		}
		public String sortExpress(){
			return sortExpress;
		}
	}
	
	public SingleHqlElement buildSingleHql(SingleQueryParams sqp) throws Exception;
}
