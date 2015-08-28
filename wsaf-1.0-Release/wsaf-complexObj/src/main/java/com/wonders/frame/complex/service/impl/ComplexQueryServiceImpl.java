package com.wonders.frame.complex.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wonders.frame.complex.dao.ComplexQueryDao;
import com.wonders.frame.complex.model.GetJson;
import com.wonders.frame.complex.model.vo.ComplexQuery;
import com.wonders.frame.complex.model.vo.FromBody;
import com.wonders.frame.complex.model.vo.GeneralQuery;
import com.wonders.frame.complex.model.vo.GroupByBody;
import com.wonders.frame.complex.model.vo.HavingBody;
import com.wonders.frame.complex.model.vo.JoinBody;
import com.wonders.frame.complex.model.vo.JoinOnBody;
import com.wonders.frame.complex.model.vo.OnBody;
import com.wonders.frame.complex.model.vo.RemoveBody;
import com.wonders.frame.complex.model.vo.SelectBody;
import com.wonders.frame.complex.model.vo.WhereBody;
import com.wonders.frame.complex.model.vo.and.JointQuery;
import com.wonders.frame.complex.model.vo.and.JointQuerys;
import com.wonders.frame.complex.service.ComplexQueryService;
import com.wonders.frame.core.model.vo.EntityProperty;
import com.wonders.frame.core.model.vo.FieldProperty;
import com.wonders.frame.core.model.vo.GenericEnum;
import com.wonders.frame.core.model.vo.QueryParams;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.BasicCrudService.FindType;
import com.wonders.frame.core.service.ObjInfoService;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.utils.DateFormatUtil;
import com.wonders.frame.core.utils.JacksonMapper;
import com.wonders.frame.core.utils.DateFormatUtil.DataFormat;

@Service("complexQueryService")
public class ComplexQueryServiceImpl implements ComplexQueryService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	ComplexQueryDao complexQueryDao;

	@Resource
	BasicCrudService basicCrudService;

	@Resource
	ObjInfoService objInfoService;
	
	List<Object> valueList = new ArrayList<Object>();
	@Override
	public ReturnObj find(Class<?> crudObjClazz, HttpServletRequest request) {
		ReturnObj returnObj = new ReturnObj();

		try {
			QueryParams qp = basicCrudService.getQueryParams(crudObjClazz,
					request);
			Object obj = queryComplexData(qp);
			setReturnObj(returnObj, obj);

		} catch (Exception e) {
			returnObj.addInfo("success", false);
			returnObj.addInfo("error", e);
		}
		return returnObj;
	}

	public Object queryComplexData(QueryParams qp) {
		ComplexQuery complexQuery = JacksonMapper.readValue((GetJson.getJson()),
				ComplexQuery.class);

		String sql = getData(complexQuery).toString();

		Integer valueNo = this.valueNo;
		LinkedHashMap<Integer, Object> valueMap = (LinkedHashMap<Integer, Object>) this.valueMap.clone();
		this.valueNo = 0;
		this.valueMap.clear();
		
		Object obj = null;
		switch (qp.getPtype()) {
		case SERIAL:

			if (qp.getFtype().equals(FindType.COUNT)) {
				String method = "count";
			} else {
				if (qp.getFtype().equals(FindType.PAGE)) {
					obj = complexQueryDao.findByPage(sql,valueMap, qp.getRange1(),
							qp.getRange2());
				} else {
					obj = complexQueryDao.findAll(sql, valueMap,null, null);
				}
			}
			break;
		case JSON:
		default:
			break;
		}

		return obj;
	}

	private HashMap<String, Object> getJoinData(JoinOnBody joinOnBody,HashMap<String, Object> maps) {
		StringBuffer fromBodyHql = new StringBuffer("");
		StringBuffer fromValueSql = new StringBuffer("");
		StringBuffer fromByname = new StringBuffer("");
		
		StringBuffer joinBodySql = new StringBuffer("");
		StringBuffer onBodySql = new StringBuffer("");
		
		String joinType = joinOnBody.getJoinType();
		Object fromValue = joinOnBody.getFromValue();
		LinkedHashMap<String, Object> propertyMap = new LinkedHashMap<String, Object>();
//		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String className = fromValue.getClass().getSimpleName();
		if(className.equals("LinkedHashMap")){
			JoinOnBody tmp = JacksonMapper.convert(fromValue, JoinOnBody.class);
			
			fromValue = getJoinData(tmp,maps).get("fromBodySql");
		}else{
			fromValue = (String) fromValue;
			//由于join on特殊性，只有最里层的fromValue才能有别名，否则报错
			fromByname.append(joinOnBody.getFromByname()); 
			
			ReturnObj property = getPropertyMap(fromValue);
			EntityProperty entityProperty = (EntityProperty) property.getData();
			fromValue = entityProperty.getTableName();
			
			propertyMap.put(joinOnBody.getFromByname(), property);
			maps.put("propertyMap", propertyMap);
			
		}
		fromValueSql.append(" (").append(fromValue).append(") ");
		
		JoinBody joinBody = joinOnBody.getJoinBody();
		String para = joinBody.getPara();
		String byname = joinBody.getByname();
		
		ReturnObj property = getPropertyMap(para);
		
		EntityProperty entityProperty = (EntityProperty) property.getData();
		para = entityProperty.getTableName();
		
		//拿到嵌套循环的propertyMap
		propertyMap = (LinkedHashMap<String, Object>) maps.get("propertyMap");
		propertyMap.put(joinBody.getByname(), property);
//		propertyMap.put("c", property);
		maps.put("propertyMap", propertyMap);
		
		joinBodySql.append(para).append(" ").append(byname);
		
		OnBody onBody = joinOnBody.getOnBody();
		List<GeneralQuery> generalQueries = onBody.getGeneralQueries();
		
		StringBuffer generalQueriesSql = (StringBuffer) getGeneralQueries(generalQueries,propertyMap).get("result");
//		StringBuffer generalQueriesSql = getGeneralQueries(generalQueries, propertyMap);
		generalQueriesSql.delete(0,4);
		onBodySql.append(" on ").append(generalQueriesSql);
		
		fromBodyHql = fromBodyHql.append(fromValueSql).append(fromByname).append(" ").append(joinType).append(" ").append(joinBodySql);
		fromBodyHql = (joinType.equalsIgnoreCase("cross join") ? fromBodyHql : fromBodyHql.append(onBodySql));
		maps.put("fromBodySql", fromBodyHql);
		return maps;
	}
	
	private ReturnObj getPropertyMap(Object fromValue) {
		Class<?> crudObjClazz = objInfoService
				.getEntityClassByType((String) fromValue);
		ReturnObj property = basicCrudService.getProperty(crudObjClazz,
				null, false);
		return property;
	}

	private StringBuffer getData(ComplexQuery complexQuery) {
		StringBuffer hql = new StringBuffer("");

		HashMap<String, Object> map = getFromBody(complexQuery.getFromBodies());
		LinkedHashMap<String, Object> propertyMap = (LinkedHashMap<String, Object>) map.get("propertyMap");
		StringBuffer fromBody= (StringBuffer) map.get("fromBody");
		StringBuffer removedBody = (StringBuffer) map.get("removedBody");
		
		StringBuffer selectBody = getSelectBody(complexQuery.getSelectBodies(), propertyMap);
		
		StringBuffer whereBody = new StringBuffer(" where ");
		whereBody.append(removedBody).append(getWhereBody(complexQuery.getWhereBodies(), propertyMap));
		
		StringBuffer groupByBody = getGroupByBody(complexQuery.getGroupByBodies(), propertyMap);
		StringBuffer havingBody = getHavingBody(complexQuery.getHavingBodies(), propertyMap);

		hql = hql.append(selectBody).append(fromBody).append(whereBody).append(groupByBody).append(havingBody);

		System.out.println(hql);
		return hql;
	}
	
	private HashMap<String, Object> getFromBody(List<FromBody> fromBodies) {
		StringBuffer fromBody = new StringBuffer("");
		StringBuffer removedBody = new StringBuffer("");
		LinkedHashMap<String, Object> propertyMap = new LinkedHashMap<String, Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> maps = new HashMap<String, Object>();

		if(fromBodies != null){
			fromBody.append(" from ");
			for (int i = 0; i < fromBodies.size(); i++) {
				String byname = fromBodies.get(i).getByname();
				Object value = fromBodies.get(i).getValue();
				boolean isJoinOnBody = false;
				String className = value.getClass().getSimpleName();
				if (className.equals("ComplexQuery")) {
					value = getData((ComplexQuery) value);// 遍历获取hql
				}else if(className.equals("JoinOnBody")){
					
					HashMap<String, Object> joinOnMap = getJoinData((JoinOnBody) value,maps); 
					value = joinOnMap.get("fromBodySql");
					map.put("propertyMap", joinOnMap.get("propertyMap"));
					isJoinOnBody = true;
				}else {
					ReturnObj property = getPropertyMap(value);
					EntityProperty entityProperty = (EntityProperty) property.getData();
					value = entityProperty.getTableName();
					propertyMap.put(byname, property);
					map.put("propertyMap", propertyMap);
				}
				if(isJoinOnBody){
					fromBody.append(value);
				}else{
					fromBody.append(" ( ").append(value).append(" ) ").append(" ")
					.append(byname);
				}
				removedBody.append(" ").append(byname).append(".removed=0")
				.append(" ");
				if (i != fromBodies.size() - 1) {
					fromBody.append(" , ");
					removedBody.append(" and ");
				}
				map.put("fromBody", fromBody);
				map.put("removedBody", removedBody);
			}
//			whereBody.append(" where ").append(removedBody);
		}
		return map;
	}

	private StringBuffer getSelectBody(List<SelectBody> selectBodies, LinkedHashMap<String, Object> propertyMap) {
		StringBuffer result = new StringBuffer("");
		if(selectBodies != null){
			result.append(" select ");
			for (int i = 0; i < selectBodies.size(); i++) {
				String byname = selectBodies.get(i).getByname();
				String para = selectBodies.get(i).getPara();
				
				if (!para.contains(".")) {
					para += ".*";
					result.append(para);
				}else{
					HashMap<String, Object> datasMap = castData(para, null, propertyMap);
					result.append(datasMap.get("para"));
					if (byname != null  && byname.length() != 0){
						result.append(" as ").append(byname);
					}else{
						byname = para.replace(".", "");
						result.append(" as ").append(byname);
					}
				}
				if (i != selectBodies.size() - 1) {
					result.append(" , ");
				}
			}
		}
		return result;
	}

	private StringBuffer getHavingBody(List<HavingBody> havingBodies,LinkedHashMap<String, Object> propertyMap) {
		StringBuffer result = new StringBuffer("");
		if (havingBodies != null) {
			result.append(" having ");
			for (int i = 0; i < havingBodies.size(); i++) {
				String logicalOper = havingBodies.get(i).getLogicalOper();
				String para = havingBodies.get(i).getPara();
				String relationalOper = havingBodies.get(i).getRelationalOper();
				Object value = havingBodies.get(i).getValue();

				String className = value.getClass().getSimpleName();
				if (className.equals("ComplexQuery")) {
					value = getData((ComplexQuery) value);// 遍历获取hql
				} else {
					HashMap<String, Object> datasMap = castData(para, value, propertyMap);
					para = (String) datasMap.get("para");
					value = datasMap.get("value");
				}
				result = ((i == 0) ? result : result.append(logicalOper));
				result.append(" ").append(para).append(" ")
						.append(relationalOper).append(" (").append(value)
						.append(") ");
			}
		}
		return result;
	}

	private StringBuffer getGroupByBody(List<GroupByBody> groupByBodies, LinkedHashMap<String, Object> propertyMap) {
		StringBuffer result = new StringBuffer("");
		if (groupByBodies != null) {
			result.append(" group by ");
			for (int i = 0; i < groupByBodies.size(); i++) {
				String para = groupByBodies.get(i).getPara();

				HashMap<String, Object> datasMap = castData(para, null, propertyMap);
				result.append(datasMap.get("para"));
				if (i != groupByBodies.size() - 1) {
					result.append(" , ");
				}
			}
		}
		return result;
	}

	int valueNo = 0;
	LinkedHashMap<Integer, Object> valueMap = new LinkedHashMap<Integer, Object>();
	private StringBuffer getWhereBody(List<WhereBody> whereBodies, LinkedHashMap<String, Object> propertyMap) {
		StringBuffer result = new StringBuffer("");
		if (whereBodies != null) {
			for (int i = 0; i < whereBodies.size(); i++) {
				List<GeneralQuery> generalQueries = whereBodies.get(i).getGeneralQueries();
				StringBuffer generalQueriesSql = (StringBuffer) getGeneralQueries(generalQueries,propertyMap).get("result");
				result.append(generalQueriesSql);
			}
		}
		return result;
	}
	
	private HashMap<String, Object> getGeneralQueries(List<GeneralQuery> generalQueries, LinkedHashMap<String, Object> propertyMap) {
		HashMap<String, Object> generalQueriesMap = new HashMap<String, Object>();
		StringBuffer result = new StringBuffer("");
		for (int j = 0; j < generalQueries.size(); j++) {
			Boolean leftBracket = generalQueries.get(j).getLeftBracket();
			Boolean rightBracket = generalQueries.get(j).getRightBracket();
			String logicalOper = generalQueries.get(j).getLogicalOper();
			String para = generalQueries.get(j).getPara();
			String relationalOper = generalQueries.get(j)
					.getRelationalOper();
			Object value = generalQueries.get(j).getValue();
			String className = value.getClass().getSimpleName();
			if (className.equals("ComplexQuery")) {
				value = getData((ComplexQuery) value);// 遍历获取hql
			} else {
				HashMap<String, Object> datasMap = castData(para, value, propertyMap);
				para = (String) datasMap.get("para");
				value = datasMap.get("value");
				
				//针对a.id=b.id这种不需要动态赋值的情况
				if(!value.toString().contains(".") || value.toString().matches("[0-9]+")){
					valueNo++;
					valueMap.put(valueNo, value);
					value = "?"+valueNo;
				}				
			}
			result.append(" ").append(logicalOper);
				if(leftBracket != null && rightBracket != null){
				result = ((leftBracket) ? result.append(" ("):result);
			result.append(" ").append(para).append(" ").append(relationalOper)
					.append(" (").append(value).append(") ");
//			result.append(" ").append(para).append(" ").append(relationalOper)
//					.append(" (").append("?"+valueNo).append(") ");
			result = ((rightBracket) ? result.append(") "):result);
			}
		}
		generalQueriesMap.put("valueMap", valueMap);
		generalQueriesMap.put("result", result);
		return generalQueriesMap;
	}

	private StringBuffer getJointQuery(List<JointQuerys> jointQueryVo) {
		StringBuffer jointQuerySql = new StringBuffer("");
		if (jointQueryVo != null) {
			for (int j = 0; j < jointQueryVo.size(); j++) {
				String operator = jointQueryVo.get(j).getOperator();
				List<JointQuery> jointQueries = jointQueryVo.get(j)
						.getJointQuery();
				StringBuffer wb = new StringBuffer("");
				for (int k = 0; k < jointQueries.size(); k++) {
					String jointQueryOper = jointQueries.get(k).getOperator();
					List<String> values = jointQueries.get(k).getValue();
					for (int l = 0; l < values.size(); l++) {
						String value = values.get(l);
						wb.append(value);
						if (l != values.size() - 1) {
							wb.append(jointQueryOper);
						}
					}
				}
				jointQuerySql.append(" on ").append(operator).append(" ").append(wb)
						.append(" ");
			}
		}
		return jointQuerySql;
	}
	private HashMap<String, Object> castData(String para, Object value,
			LinkedHashMap<String, Object> propertyMap) {
		HashMap<String, Object> datasMap = new HashMap<String, Object>();
		
		String paraTmp = para;
		boolean isFunction = false;
		if(para.contains("(") || para.contains(")")){
			int first = para.indexOf("(");
		    int last = para.lastIndexOf(")");
		    para = para.substring(first + 1, last);
		    isFunction = true;
		}
		
		String[] paras = para.trim().split("\\.");
		ReturnObj property = (ReturnObj) propertyMap.get(paras[0]);
		if ((Boolean) property.getInfo().get("success")) {
			EntityProperty entityProperty = (EntityProperty) property.getData();
			for (FieldProperty fieldProperty : entityProperty
					.getFieldProperties()) {
				if (paras[1].equals(fieldProperty.getPath())) {
					String columnName = fieldProperty.getColumnName();
					Class<?> returnType = fieldProperty.getReturnType();
					String returnTypeName = returnType.getSimpleName();
					para = paras[0] + "." + columnName;

					//根据字段返回类型，将value转为对应的值
					if (value != null && value.toString().length() != 0 ){
						if(value.toString().contains(".")){
							HashMap<String, Object> joinValueMap = castData(value.toString(), "", propertyMap);
							value = joinValueMap.get("para");
						}else{
							value = castValue(returnTypeName, value + "");
						}
					}
					para = ((isFunction) ? paraTmp.replace(paras[1], columnName) : para);
					datasMap.put("para", para);
					datasMap.put("value", value);

					continue;
				}
			}
		}
		return datasMap;
	}

	private Object castValue(String returnTypeName, String value) {
		if (returnTypeName.equals("String")) {
			return String.valueOf(value);
		} else if (returnTypeName.equals("Long")) {
			return getLong(value);
		} else if (returnTypeName.equals("Double")) {
			return getDouble(value);
		} else if (returnTypeName.equals("Float")) {
			return getFloat(value);
		} else if (returnTypeName.equals("Integer")) {
			return getInteger(value);
		} else if (returnTypeName.equals("Byte")) {
			return getByte(value);
		} else if (returnTypeName.equals("Date") || returnTypeName.equals("Timestamp")) {
//			value = "to_date('"+value+"','"+DateFormatUtil.ORACLE_DATETIME_FORMAT+"')";
//			return value;
			return getDate(value);
		}
//		else if (returnTypeName.equals("Timestamp")) {
//			return getTimestamp(value);
//		}
		return value;
	}

	public Long getLong(String value) {
		if (value == null || false == NumberUtils.isNumber(value + ""))
			return 0L;
		return NumberUtils.toLong(value + "");
	}

	public Double getDouble(String value) {
		if (value == null || false == NumberUtils.isNumber(value + ""))
			return 0d;
		return NumberUtils.toDouble(value + "");
	}

	public Float getFloat(String value) {
		if (value == null || false == NumberUtils.isNumber(value + ""))
			return 0f;
		return NumberUtils.toFloat(value + "");
	}

	public Integer getInteger(String value) {
		if (value == null || false == NumberUtils.isNumber(value + ""))
			return 0;
		return Integer.valueOf(value + "");
	}

	public Byte getByte(String value) {
		if (value == null || false == NumberUtils.isNumber(value + ""))
			return 0;
		return NumberUtils.toByte(value + "");
	}

	public Date getDate(String value) {

		for (DataFormat df : DataFormat.values()) {
			System.out.println(df.code() + "," + df.description());
			Boolean isValidDate = DateFormatUtil.isValidDate(df.description(),
					value);
			if (isValidDate) {
				return DateFormatUtil.timeParse(df.description(), value);
			}
		}
		return null;
	}

	public Timestamp getTimestamp(String value) {

		for (DataFormat df : DataFormat.values()) {
			System.out.println(df.code() + "," + df.description());
			Boolean isValidDate = DateFormatUtil.isValidDate(df.description(),
					value);
			if (isValidDate) {
				return DateFormatUtil
						.stringToTimestamp(df.description(), value);
			}
		}
		return null;
	}

	private void setReturnObj(ReturnObj returnObj, Object obj) {
		if (obj != null) {
			returnObj.addInfo("success", true);
			returnObj.setData(obj);
		} else {
			returnObj.addInfo("success", false);
			returnObj.addInfo("error", "No matched record");
		}
	}

}