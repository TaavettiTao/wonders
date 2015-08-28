package com.wonders.frame.core.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.model.vo.SingleQueryParams;
import com.wonders.frame.core.service.SingleCrudService.KeyWord;
import com.wonders.frame.core.service.SingleCrudService.QueryType;
import com.wonders.frame.core.service.SingleHqlBuilderService.OrderSort;

/** 
 * @ClassName: SingleParamsConvertUtil 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class SingleParamsConvertUtil {
	private final static Logger logger=LoggerFactory.getLogger(SingleParamsConvertUtil.class);
	

	public static HashMap<String, String> getRequestParams(HttpServletRequest request) {
		HashMap<String, String> pm = new HashMap<String, String>();
		Map<String, String[]> rpm = request.getParameterMap();
		for (String key : rpm.keySet()) {
			pm.put(key, request.getParameter(key));
		}

		return pm;
	}
	
	/**
	 * 将request传参绑定至用于查询处理的SingleQueryParams对象
	 * @param clazz，对象class类型
	 * @param request
	 * @return SingleQueryParams 单个对象查询信息对象,继承SingleFormParams，包括对象类型、查询参数键值映射、查询类型、查询范围、排序及复杂where条件表达式
	 */
	public static SingleQueryParams getQueryParams(Class<?> clazz,
			HttpServletRequest request) {
		HashMap<String, String> rpm = getRequestParams(request);
		return getQueryParams(clazz,rpm);
	}
	
	public static SingleQueryParams getQueryParams(Class<?> clazz,
			HashMap<String, String> rpm) {		
		SingleQueryParams qp = new SingleQueryParams(clazz);		
		qp.setQueryType(QueryType.ALL);//默认查询类型为all
		for (KeyWord keyword : KeyWord.values()) {//查找关键字，确定查询类型
			String paramName = keyword.word();
			if (rpm.containsKey(paramName)) {
				String value=rpm.get(paramName);
				switch(keyword){
				case COUNT:
					qp.setQueryType(QueryType.COUNT);
					break;
				case PAGE:
					qp.setQueryType(QueryType.PAGE);
					setQueryRange(value,qp);
					break;
				case ROW:
					qp.setQueryType(QueryType.ROW);
					setQueryRange(value,qp);					
					break;
				case SORT:
					if (value != null) {
						String[] sorts = value.split(",");
						for (int i = 0; i < sorts.length; i++) {
							String[] order = sorts[i].split(" ");
							
							if(order.length<1) continue;
							
							String fieldName=order[0];
							
							String sort=OrderSort.asc.toString();
							
							if(order.length>1 &&OrderSort.valueOf(order[1])!=null){
								sort=OrderSort.valueOf(order[1]).toString();
								
							}
							qp.addSort(fieldName, sort);
						}
					}
					break;
				case CONDITION_EXPRESS:
					qp.setConditionExpress(value);
					break;
				default:	
					break;
				}

				rpm.remove(paramName);

			}
		}

		qp.setData(rpm);
		return qp;
	}
	
	/**
	 * 从page或row参数中，分离出使用逗号分隔的查询范围边界值（range1，range2）
	 * @param value
	 * @param qp
	 */
	private static void setQueryRange(String value,SingleQueryParams qp){
		int range1 = 0, range2 = 0;
		if (value != null && !value.equals("")) {

			String[] arr = value.split(",");

			if (arr[0] != null && arr[0].matches("[0-9]+")) {
				range1 = Integer.valueOf(arr[0]);
			}

			if (arr[1] != null && arr[1].matches("[0-9]+")) {
				range2 = Integer.valueOf(arr[1]);
			}

		}

		qp.setRange1(range1);
		qp.setRange2(range2);
	}
	
	
	
	/**
	 * 将request传参绑定至用于表单处理的SingleModelParams对象
	 * @param clazz，对象class类型
	 * @param request
	 * @return SingleModelParams 单个对象表单信息对象，包括对象类型及表单参数键值映射
	 */
	public static SingleModelParams getModelParams(Class<?> clazz,
			HttpServletRequest request) {
		SingleModelParams mp = new SingleModelParams(clazz);
		
		HashMap<String, String> rpm = getRequestParams(request);

		mp.setData(rpm);
		
		return mp;
	}

}
