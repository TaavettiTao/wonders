package com.wonders.frame.core.service;

import com.wonders.frame.core.model.vo.MultiQuerySqlElement;

public interface MultiSqlBuilderService {
public final static String logicDelField="removed";
	
	//定义关联子查询的json模板
	public final static String relatedSubQueryJsonFormat="{\"select\":{\"all\":\"%s.*\",\"fid\":\"r.%s\"},\"from\":{\"%s\":\"%s\",\"r\":\"relation\"},\"where\":{\"r.%s\":\"%s.id\",\"r.ptype\":\"%s\",\"r.ntype\":\"%s\",\"r.ruleTypeId\":\"%s\"}}";
	
	//定义关联子查询对象与关联关系的where条件模板
	public final static String relatedSubQueryEntityWhereFormat="%s.id=%s.fid";
	
	/**
	 * 定义复杂对象出库（查询）操作中，前台json格式中用于组装sql的组成部分
	 *
	 */
	public enum SqlComponent {
		FROM(" from "),SELECT("select "), WHERE(" where "),GROUP(" group by "),HAVING(" having "),ORDER(" order by ");
		//sql语句组成部分
		private String componentExpress;
		
		private SqlComponent(String componentExpress){
			this.componentExpress=componentExpress;	
		}
		public String componentExpress(){
			return componentExpress;
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
	
	/**
	 * 定义在复杂对象出库（查询）操作中，定义的与sql函数及运算子相关的关键字
	 *
	 */
	public static enum SqlDefinedSign {
	$cnt("COUNT(?)"),
	$sum("SUM(?)"),
	$avg("AVG(?)"),
	$dis("distinct ?"),
	$decode("decode(?)"),
	$lj(" left join "), 
	$rj(" right join "),
	$ij(" inner join "),
	$oj(" outer join "),
	$on(" on "),
	$or(" or  "),
	$and(" and "),
	$null(" is null"),	
	$notnull(" is not null"),
	$ne(" != ?"),
	$in(" in (?)"),
	$nin(" not in (?)"),
	$l(" like '%'||?||'%'"),
	$sl(" like '%'||?"),
	$el(" like ?||'%'"),
	$nl(" not like '%'||?||'%'"),
	$nsl(" not like '%'||?"),
	$nel(" not like ?||'%'"),
	$gt(" > ?"),
	$lt(" < ?"),
	$gte(" >= ?"),
	$lte(" <= ?");
	//函数及运算子表达式
	private String signExpress;
	
	private SqlDefinedSign(String signExpress){
		this.signExpress=signExpress;	
	}
	public String signExpress(){
		return signExpress;
	}
}
	public MultiQuerySqlElement buildMultiSql(Integer ruleTypeId,String jsonData) throws Exception;
}
