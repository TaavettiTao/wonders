package com.wonders.frame.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wonders.frame.core.model.vo.PlaceholderParam;
import com.wonders.frame.core.model.vo.ObjectAttributeVo;
import com.wonders.frame.core.model.vo.SingleHqlElement;
import com.wonders.frame.core.model.vo.SingleQueryParams;
import com.wonders.frame.core.service.ObjInfoCache;
import com.wonders.frame.core.service.SingleHqlBuilderService;
import com.wonders.frame.core.utils.JacksonMapper;
import com.wonders.frame.core.utils.ReflectUtil;

/**
 * @author lushuaifeng
 * 当前对象用于解析前台传入的单个对象的查询信息，生成对应的Hql相关元素
 */
@Service("singleHqlBuilderService")
public class SingleHqlBuilderServiceImpl implements SingleHqlBuilderService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public SingleHqlElement buildSingleHql(SingleQueryParams sqp)throws Exception{
	
		StringBuffer hql=new StringBuffer(SqlComponent.FROM.componentExpress());
		hql.append(sqp.getObjClazz().getSimpleName()).append(entityAlias);
		
		StringBuffer whereHql=new StringBuffer();
		
		if(ReflectUtil.isLogicRemovedObj(sqp.getObjClazz())){
			whereHql.append(entityAlias).append(".").append(logicDelField).append("=0");
		}
		
		
		String conditionHql="";		
		List<PlaceholderParam> placeholderParamList=new ArrayList<PlaceholderParam>();		
		if(sqp.getConditionExpress()!=null &&!sqp.getConditionExpress().equals("")){
			
			conditionHql=buildQueryConditionExpress(sqp.getConditionExpress(),sqp,placeholderParamList);			
		}else{
			conditionHql=buildQueryConditionExpress(sqp,placeholderParamList);
		}
				
		if(!conditionHql.equals("")){
			if(whereHql.length()>0){
				whereHql.append(SqlDefinedSign.$and.signExpress());
			}
			whereHql.append("(").append(conditionHql).append(")");
		}
		
		if(whereHql.length()>0){
			hql.append(SqlComponent.WHERE.componentExpress()).append(whereHql);
		}
		
		StringBuffer orderHql=buildOrder(sqp);
		if(orderHql.length()>0){
			hql.append(SqlComponent.ORDER.componentExpress()).append(orderHql);
		}
		logger.debug("HQL queryCondtion:{}",hql.toString());
		logger.debug("HQL queryParams:{}",JacksonMapper.toJson(placeholderParamList));
		
		return new SingleHqlElement(hql.toString(),placeholderParamList);

	}


	
	/**
	 * 根据参数conditionExpress的值构建复杂的where条件
	 * @param value，参数值格式如下：
	 * 1、$or(field_sl,field_in,$and(field_in,field_s,field))等同于A or B or (C and D)
	 * 2、$and(....)等同于A and B and ....
	 * 3、field,field2,...等同于A and B and ....
	 * @param sqp，查询处理对象
	 * @param placeholderParamList，占位符参数列表
	 * @return String，构造完成的预处理sql
	 */
	private String buildQueryConditionExpress(String value,SingleQueryParams sqp,List<PlaceholderParam> placeholderParamList) throws Exception{
		
		HashMap<String,ObjectAttributeVo> attributeMap = ObjInfoCache.getObjectAttributeMap(sqp.getObjClazz());
		HashMap<String,String> queryParams=sqp.getData();
		StringBuffer sb=new StringBuffer();
		String sqlSign="";
		if(value.startsWith(SqlDefinedSign.$or.toString())){
			sqlSign=SqlDefinedSign.$or.signExpress();
			value=value.substring(SqlDefinedSign.$or.toString().length()+1,value.length()-1);
		}if(value.startsWith(SqlDefinedSign.$and.toString())){
			sqlSign=SqlDefinedSign.$and.signExpress();
			value=value.substring(SqlDefinedSign.$and.toString().length()+1,value.length()-1);
		}else{
			sqlSign=SqlDefinedSign.$and.signExpress();			
		}
		
		String[] paramNames=value.split(",");
		
		for(String paramName:paramNames){
			
			StringBuffer att =new StringBuffer();
			
			if(paramName.startsWith("$")){
				String subStr=buildQueryConditionExpress(paramName,sqp,placeholderParamList);
				if(!subStr.equals("")){
					att.append("(").append(subStr).append(")");
				}
			}else{
								
				String paramValue=queryParams.get(paramName);

				att.append(buildAttributeExpress(paramName,paramValue,attributeMap,placeholderParamList));
			}
			
			if(att.length()>0){
				if(sb.length()>0){
					sb.append(sqlSign);
				}
				
				sb.append(att);
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * 根据request传入参数的键值对构建简单的where条件
	 * @param sqp，查询处理对象
	 * @param placeholderParamList，占位符参数列表
	 * @return String，构造完成的预处理sql
	 */
	private String buildQueryConditionExpress(SingleQueryParams sqp,List<PlaceholderParam> placeholderParamList) throws Exception{
		
		HashMap<String,ObjectAttributeVo> attributeMap = ObjInfoCache.getObjectAttributeMap(sqp.getObjClazz());
		HashMap<String,String> queryParams=sqp.getData();
		StringBuffer sb=new StringBuffer();
		String sqlSign=SqlDefinedSign.$and.signExpress();			
			
		Set<String> keySet=queryParams.keySet();
		for(Iterator<String> it=keySet.iterator();it.hasNext();){
			String paramName=it.next();

			String paramValue=queryParams.get(paramName);
			
			StringBuffer att =buildAttributeExpress(paramName,paramValue,attributeMap,placeholderParamList);
			if(att.length()>0){
				if(sb.length()>0){
					sb.append(sqlSign);
				}
				sb.append(att);
			}
			
		}
		return sb.toString();
	}
	
	/**
	 * 根据每一个传入的查询参数键值对构造对象属性表达式对应的预处理sql部分及对应的占位符参数
	 * @param paramName，参数名
	 * @param paramValue，参数值
	 * @param attributeMap，对象属性表
	 * @param placeholderParamList，占位符参数列表
	 * @return StringBuilder，构造完成的属性表达式预处理sql
	 */
	private StringBuffer buildAttributeExpress(String paramName,String paramValue,HashMap<String,ObjectAttributeVo> attributeMap,List<PlaceholderParam> placeholderParamList) throws Exception{
		StringBuffer sb=new StringBuffer();
		if(paramValue==null || paramValue.equals("")){
			return sb;
		}
		String[] keys = paramName.split("_");
		
		if (keys.length < 1) {
			return sb;
		}

		String attributeName = keys[0];
		
		String express="";
		boolean hasPlaceholderParam=true;
		boolean isIn=false;
		if (keys.length > 1 && SqlDefinedSign.valueOf(keys[1])!=null) {

			express=SqlDefinedSign.valueOf(keys[1]).signExpress();
			//in查询时，按照参数值的个数，构造多个预处理入参位(?)
			if(SqlDefinedSign.valueOf(keys[1]).equals(SqlDefinedSign.in)){
				String[] values=paramValue.split(",");
				StringBuffer prepareInSql=new StringBuffer();
				for(int i=0;i<values.length;i++){
					if(i>0){
						prepareInSql.append(",");
					}
					prepareInSql.append("?");
				}
				express=express.replace("?", prepareInSql.toString());
				isIn=true;
			}
			
		}else if(paramValue.startsWith("$")&& SqlDefinedSign.valueOf(paramValue)!=null){
			
			express=SqlDefinedSign.valueOf(paramValue).signExpress();
			hasPlaceholderParam=false;
		}else{
			express=" =?";
		}
		
		if(attributeMap.containsKey(attributeName)){
			ObjectAttributeVo attributeVo =attributeMap.get(attributeName);
			sb.append(entityAlias).append(".").append(attributeName).append(express);
			if(hasPlaceholderParam){
				if(isIn){
					String[] values=paramValue.split(",");
					for(String value:values){
						placeholderParamList.add(new PlaceholderParam(value,attributeVo.getClazz()));
					}
				}else{
					placeholderParamList.add(new PlaceholderParam(paramValue,attributeVo.getClazz()));
				}
				
			}
		}
		return sb;
	}
	
	/**
	 * 查询order部分
	 * @param sqp 查询处理对象
	 * @return
	 * @throws Exception
	 */
	private StringBuffer buildOrder(SingleQueryParams sqp)throws Exception{
		HashMap<String,ObjectAttributeVo> attributeMap = ObjInfoCache.getObjectAttributeMap(sqp.getObjClazz());
		
		StringBuffer sb = new StringBuffer();
		LinkedHashMap<String,String> sortMap=sqp.getSort();
		if(sortMap==null) return sb;
		
		Set<String> keySet=sortMap.keySet();
		for(Iterator<String> it=keySet.iterator();it.hasNext();){
						
			String attributeName=it.next();
			
			StringBuffer att=new StringBuffer();
			if(attributeMap.containsKey(attributeName)){
				
				att.append("o.").append(attributeName);
				
				String sort=sortMap.get(attributeName);
				
				if(OrderSort.valueOf(sort)!=null){
					att.append(OrderSort.valueOf(sort).sortExpress());
				}else{
					att.append(OrderSort.asc.sortExpress());
				}
			}
			if(att.length()>0){
				if(sb.length()>0){sb.append(",");}
				
				sb.append(att);
			}

		}

		return sb;
	}

	
	public static void main(String[] args) {
		
		System.out.println(Arrays.asList("112,34".split(",")));
	}
}
