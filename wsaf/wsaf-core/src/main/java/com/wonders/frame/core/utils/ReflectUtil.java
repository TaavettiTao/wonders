package com.wonders.frame.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.wonders.frame.core.model.IMultiModel;
import com.wonders.frame.core.model.IRemovedModel;
import com.wonders.frame.core.model.bo.Ccate;
import com.wonders.frame.core.model.bo.Codes;
import com.wonders.frame.core.model.vo.EntityProperty;
import com.wonders.frame.core.model.vo.FieldProperty;
import com.wonders.frame.core.model.vo.MultiModelElement;
import com.wonders.frame.core.model.vo.MultiModelRelatedObj;
import com.wonders.frame.core.model.vo.ObjectAttributeVo;
import com.wonders.frame.core.model.vo.ObjectInfoVo;
import com.wonders.frame.core.service.CcateService;
import com.wonders.frame.core.service.ObjInfoCache;
import com.wonders.frame.core.service.ObjInfoService;
import com.wonders.frame.core.tags.MultiRelate;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;

/**
 * @ClassName: ReflectUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class ReflectUtil {
	private final static Logger logger = LoggerFactory
			.getLogger(ReflectUtil.class);

	public static Class<?> getFieldType(Class<?> clazz, String fieldName){
		Class<?> fieldClass = null;
		try{
			Field field = getField(clazz, fieldName);
			if (field != null) {
				fieldClass = field.getType();
			}
		}catch(Exception e){
			logger.error("Exception Throwable", e);
		}
		return fieldClass;
		
	}

	public static Field getField(Class<?> clazz, String fieldName){
		try{
			return clazz.getDeclaredField(fieldName);
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
			return null;
		}

	}

	public static Object setUpdateValue2Object(Class<?> clazz,
		Object oldObject, Object newObject, Set<String> updateParams){
		try{
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equalsIgnoreCase("serialVersionUID")
						|| field.getName().equalsIgnoreCase("id")) {
					continue;
				}
	
				if (updateParams.contains(field.getName())) {
					Object fieldValue = invokeGet(newObject, field.getName());
					invokeSet(oldObject, field.getName(), fieldValue);
	
				} else {
					continue;
	
				}
	
			}
			return oldObject;
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
			return null;
		}
		
	}
/*
	public static HashMap<String, Object> formatQueryParam(SingleQueryParams qp) {
		//
		HashMap<String, String> hmParam = new HashMap<String, String>();
		HashMap<String, String> hmParamEnd = new HashMap<String, String>();
		List<String> rangeQueryFields = new ArrayList<String>();
		List<String> likeQueryFields = new ArrayList<String>();
		HashMap<String, String[]> inQueryCondition = new HashMap<String, String[]>();

		// 把查询参数存入2个hashmap，hmParam存每个查询属性的起始条件，hmParamEnd存结束条件，同时把是范围查询的属性名存入队列rangeQueryFields
		for (String key : qp.getData().keySet()) {
			String[] keys = key.split("_");

			if (keys.length < 1) {
				continue;
			}

			String fieldName = keys[0];
			boolean isToHashMap = true;
			if (keys.length > 1) {
				if (keys[1].equals("s") || keys[1].equals("e")) {
					rangeQueryFields.add(key);
					if (keys[1].equals("e")) {
						hmParamEnd.put(fieldName, qp.getData().get(key)
								.toString());
						isToHashMap = false;
					}
				} else if (keys[1].equals("in")) {
					if (qp.getData().get(key) != null
							&& !qp.getData().get(key).toString().equals("")) {
						inQueryCondition.put(key, qp.getData().get(key)
								.toString().split(","));
					}
					isToHashMap = false;
				} else if (qp.getObjClazz().equals(String.class)
						&& keys[1].endsWith("l")) {
					likeQueryFields.add(key);
				}
			}
			if (isToHashMap) {
				hmParam.put(fieldName, qp.getData().get(key).toString());
			}

		}
		// 把存储起始和结束条件值的hashmap变为实体对象queryObj和queryObjEnd
		Object queryObj = JacksonMapper.convert(hmParam, qp.getObjClazz());
		Object queryObjEnd = JacksonMapper
				.convert(hmParamEnd, qp.getObjClazz());


		HashMap<String, Object> queryParam = new HashMap<String, Object>();
		try {
			Field[] fields = qp.getObjClazz().getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equalsIgnoreCase("serialVersionUID")) {
					continue;
				}

				Method getMethod = getGetMethod(qp.getObjClazz(), field
						.getName());// 获得get方法

				if (rangeQueryFields.contains(field.getName() + "_s")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_s", o);

					}
				}
				if (rangeQueryFields.contains(field.getName() + "_e")) {
					Object o = getMethod.invoke(queryObjEnd);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_e", o);

					}
				}

				if (inQueryCondition.containsKey(field.getName() + "_in")) {
					String[] inConditions = inQueryCondition.get(field
							.getName()
							+ "_in");
					if (field.getType().equals(Integer.class)) {
						List<Integer> conditionObj = new ArrayList<Integer>();
						for (String inCondition : inConditions) {
							conditionObj.add(Integer.valueOf(inCondition));
						}

						queryParam.put(field.getName() + "_in", conditionObj);
					} else {
						queryParam.put(field.getName() + "_in", Arrays
								.asList(inConditions));
					}

				}

				if (likeQueryFields.contains(field.getName() + "_l")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_l", o);

					}
				} else if (likeQueryFields.contains(field.getName() + "_el")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_el", o);

					}
				} else if (likeQueryFields.contains(field.getName() + "_sl")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_sl", o);

					}
				}
				if (!rangeQueryFields.contains(field.getName() + "_e")
						&& !rangeQueryFields.contains(field.getName() + "_s")
						&& !inQueryCondition.containsKey(field.getName()
								+ "_in")
						&& !likeQueryFields.contains(field.getName() + "_el")
						&& !likeQueryFields.contains(field.getName() + "_sl")
						&& !likeQueryFields.contains(field.getName() + "_l")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName(), o);

					}
				}

			}

		} catch (Exception e) {
			logger.error("Exception Throwable", e);
			return queryParam;
		}

		return queryParam;
	}

	public static HashMap<String, Object> formatQueryParam(
			HttpServletRequest request, Class<?> clazz) {
		Map<String, String[]> paramMap = request.getParameterMap();
		HashMap<String, String> hmParam = new HashMap<String, String>();
		HashMap<String, String> hmParamEnd = new HashMap<String, String>();
		List<String> rangeQueryFields = new ArrayList<String>();
		List<String> likeQueryFields = new ArrayList<String>();
		HashMap<String, String[]> inQueryCondition = new HashMap<String, String[]>();
		// 把查询参数存入2个hashmap，hmParam存每个查询属性的起始条件，hmParamEnd存结束条件，同时把是范围查询的属性名存入队列rangeQueryFields
		for (String key : paramMap.keySet()) {
			if (key.endsWith("_s")) {// 范围查询开始条件
				String objPropertyName = key.substring(0, key.length() - 2);
				rangeQueryFields.add(key);
				hmParam.put(objPropertyName, request.getParameter(key));
			} else if (key.endsWith("_e")) {// 范围查询结束条件
				String objPropertyName = key.substring(0, key.length() - 2);
				rangeQueryFields.add(key);
				hmParamEnd.put(objPropertyName, request.getParameter(key));
			} else if (key.endsWith("_in")) {// in查询条件,按逗号划分为数组之后与查询属性建立映射关系
				String inCondition = request.getParameter(key);
				if (inCondition != null && !inCondition.equals("")) {
					inQueryCondition.put(key, inCondition.split(","));
				}

			} else if (key.endsWith("_sl") || key.endsWith("_el")
					|| key.endsWith("_l")) {// like查询条件
				String objPropertyName = "";
				if (key.endsWith("_l")) {
					objPropertyName = key.substring(0, key.length() - 2);
				} else {
					objPropertyName = key.substring(0, key.length() - 3);
				}
				likeQueryFields.add(key);
				hmParam.put(objPropertyName, request.getParameter(key));

			} else {
				hmParam.put(key, request.getParameter(key));
			}

		}
		// 把存储起始和结束条件值的hashmap变为实体对象queryObj和queryObjEnd
		Object queryObj = JacksonMapper.convert(hmParam, clazz);
		Object queryObjEnd = JacksonMapper.convert(hmParamEnd, clazz);


		HashMap<String, Object> queryParam = new HashMap<String, Object>();
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equalsIgnoreCase("serialVersionUID")) {
					continue;
				}

				Method getMethod = getGetMethod(clazz, field.getName());// 获得get方法

				if (rangeQueryFields.contains(field.getName() + "_s")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_s", o);

					}
				}
				if (rangeQueryFields.contains(field.getName() + "_e")) {
					Object o = getMethod.invoke(queryObjEnd);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_e", o);

					}
				}

				if (inQueryCondition.containsKey(field.getName() + "_in")) {
					String[] inConditions = inQueryCondition.get(field
							.getName()
							+ "_in");
					if (field.getType().equals(Integer.class)) {
						List<Integer> conditionObj = new ArrayList<Integer>();
						for (String inCondition : inConditions) {
							conditionObj.add(Integer.valueOf(inCondition));
						}

						queryParam.put(field.getName() + "_in", conditionObj);
					} else {
						queryParam.put(field.getName() + "_in", Arrays
								.asList(inConditions));
					}

				}

				if (likeQueryFields.contains(field.getName() + "_l")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_l", o);

					}
				} else if (likeQueryFields.contains(field.getName() + "_el")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_el", o);

					}
				} else if (likeQueryFields.contains(field.getName() + "_sl")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName() + "_sl", o);

					}
				}
				if (!rangeQueryFields.contains(field.getName() + "_e")
						&& !rangeQueryFields.contains(field.getName() + "_s")
						&& !inQueryCondition.containsKey(field.getName()
								+ "_in")
						&& !likeQueryFields.contains(field.getName() + "_el")
						&& !likeQueryFields.contains(field.getName() + "_sl")
						&& !likeQueryFields.contains(field.getName() + "_l")) {
					Object o = getMethod.invoke(queryObj);
					if (o != null && !o.toString().equals("")) {
						queryParam.put(field.getName(), o);

					}
				}

			}

		} catch (Exception e) {
			logger.error("Exception Throwable", e);
			return queryParam;
		}

		return queryParam;
	}
	*/

	/* 获取复杂对象的相关字段名与对象属性映射关系 */
	public static MultiModelElement getMultiModelElement(Object multiModel){
		try{
			MultiModelElement mme=new MultiModelElement();
			
			Field[] declaredfields = multiModel.getClass().getDeclaredFields();
			for (Field field : declaredfields) {
	
				MultiRelate multiRelate=getAnnotation(multiModel.getClass(),field.getName(),MultiRelate.class);
				
				if(multiRelate==null) return null;
				
				boolean isList=false;
				boolean isMultiModel=false;
				Class<?> relatedObjClass;
				
				switch(multiRelate.type()){
					case MAIN: 
						mme.setMainObj(invokeGet(multiModel,field.getName()));
						break;
					case PARENT: 				
	
						if(field.getDeclaringClass().equals(List.class)){
							isList=true;						
							relatedObjClass=field.getGenericType().getClass();					
						}else{
							relatedObjClass=field.getDeclaringClass();
						}	
						if(isMultiModelObj(relatedObjClass)){
							isMultiModel=true;
						}
						MultiModelRelatedObj parentMmro=new MultiModelRelatedObj(multiRelate.fk(),isMultiModel,isList);		
						
						if(isList){
							parentMmro.setObjList((List<Object>)invokeGet(multiModel,field.getName()));						
						}else{
							parentMmro.setObj(invokeGet(multiModel,field.getName()));						
						}
						mme.addParentObj(parentMmro);
						break;
					case CHILD:
						if(field.getDeclaringClass().equals(List.class)){
							isList=true;						
							relatedObjClass=field.getGenericType().getClass();					
						}else{
							relatedObjClass=field.getDeclaringClass();
						}	
						if(isMultiModelObj(relatedObjClass)){
							isMultiModel=true;
						}
						MultiModelRelatedObj childMmro=new MultiModelRelatedObj(multiRelate.fk(),isMultiModel,isList);		
						
						if(isList){
							childMmro.setObjList((List<Object>)invokeGet(multiModel,field.getName()));						
						}else{
							childMmro.setObj(invokeGet(multiModel,field.getName()));						
						}
						mme.addChildObj(childMmro);
						break;
					default:
						break;
				}
				
						
			}
			return mme;
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
			return null;
		}

	}
	
	/* 获取对象class */
	public static Class<?> getObjectClass(String entityName) throws Exception{
		ObjInfoService objInfoService = (ObjInfoService) SpringBeanUtil
				.getBean("objInfoService");
		
		return objInfoService.getEntityClassByType(entityName);

	}
	
	public static ObjectInfoVo getObjectInfo(Class<?> clazz) throws Exception{

		CcateService ccateService = (CcateService) SpringBeanUtil
		.getBean("ccateService");
		
		HashMap<String, ObjectAttributeVo> attributeMap = new HashMap<String, ObjectAttributeVo>();
		List<ObjectAttributeVo> attributeList=new ArrayList<ObjectAttributeVo>();
		
		Field[] declaredfields = clazz.getDeclaredFields();
		for (Field field : declaredfields) {
			if(field.getName().equals("serialVersionUID")){
				continue;
			}
			Column column = getAnnotation(clazz, field.getName(),Column.class);
			
			ObjectAttributeVo attribute = new ObjectAttributeVo(field.getName(),
					field.getType(), column);
			
			ShowInView showInView = getAnnotation(clazz, field.getName(),ShowInView.class);
			
			if(showInView!=null&&!showInView.noUse()){
			
				attribute.setShowInView(showInView);

			
				if (attribute.getClazz().isEnum()) {
					attribute.setOption(getEnumStringAndDescriptionMap(attribute
							.getClazz()));
			
				} else if (attribute.getShowInView() != null) {
			
					String ccateType = attribute.getShowInView().ccateType();
					boolean allowOptionNull = attribute.getShowInView()
							.allowOptionNull();
			
					if (ccateType != null && !ccateType.equals("")) {
			
						String[] ccateTypes = ccateType.split(",");
			
						for (String singleCcateType : ccateTypes) {
							HashMap<String, LinkedHashMap<String, String>> multiOption = new HashMap<String, LinkedHashMap<String, String>>();
			
							Ccate ccate = ccateService.findByType(singleCcateType);
							if (ccate != null) {
								List<Codes> codeses = ccate.getCodes();
								for (Codes codes : codeses) {
									if (codes.getRemoved() == 1)
										continue;
									String pCode = codes.getPcode();
									if (pCode == null || pCode.equals("")) {
										pCode = "0";
									}
									String code = codes.getCode();
									String description = codes.getDisplay();
									if (description == null
											|| description.equals("")) {
										description = codes.getDescription();
									}
			
									if (multiOption.containsKey(pCode)) {
										LinkedHashMap<String, String> singleOption = multiOption
												.get(pCode);
										singleOption.put(code, description);
									} else {
										LinkedHashMap<String, String> singleOption = new LinkedHashMap<String, String>();
										if (allowOptionNull) {
											singleOption.put("", "请选择");
										}
										singleOption.put(code, description);
										multiOption.put(pCode, singleOption);
									}
								}
								if (multiOption.keySet() != null) {
									if (multiOption.keySet().size() > 1) {
										attribute.setCodes(multiOption);
									}
									if (multiOption.keySet().size() == 1) {
										if (multiOption.containsKey("0")) {
											attribute.setOption(multiOption
													.get("0"));
										} else {
											attribute.setCodes(multiOption);
										}
									}
			
								}
							}
			
						}
					}
				}
			}
		
			attributeMap.put(field.getName(), attribute);
			attributeList.add(attribute);
		}
		ObjectInfoVo objectInfo = new ObjectInfoVo(clazz);
		objectInfo.setAttributeList(attributeList);
		objectInfo.setAttributeMap(attributeMap);
		return objectInfo;


	}


	/* 获取对象属性（包括其字段属性列表） */
	public static EntityProperty getEntityProperty(Class<?> clazz,
			String fields, boolean needed){
		EntityProperty entityProperty=null;
		try{
			ObjectInfoVo objectInfo = ObjInfoCache.getObjectInfo(clazz);
			String cnName = "";
			ShowInView clazzShow = objectInfo.getShowInView();
			if (clazzShow != null) {
				cnName = clazzShow.name();
			}
			entityProperty = new EntityProperty(objectInfo.getEntityName(), cnName);
	
			entityProperty.setFieldProperties(getFieldProperties(clazz, fields,
					needed));
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
		return entityProperty;
	}


	public static List<FieldProperty> getFieldProperties(Class<?> clazz,
			String fields, boolean isNeeded) {
		List<FieldProperty> fieldProperties = new ArrayList<FieldProperty>();
		try{	
			List<String> filterFieldList = null;
			if (fields != null && !fields.equals("")) {
				String[] filterFields = fields.split(",");
				filterFieldList = Arrays.asList(filterFields);
			}
	
	
			List<ObjectAttributeVo> attributes = ObjInfoCache.getObjectAttributeList(clazz);
	
			for (ObjectAttributeVo attribute:attributes) {
				
				String fieldName =attribute.getName();
	
				if (filterFieldList != null
						&& ((isNeeded && !filterFieldList.contains(fieldName)) || (!isNeeded && filterFieldList
								.contains(fieldName)))) {
	
					continue;
				}
	
				boolean noUse = false;
				OperateType operateType = null;
				String cnName = "";
	
				ShowInView meta = attribute.getShowInView();
	
				if (meta != null) {
					noUse = meta.noUse();
					operateType = meta.operateType();
					cnName = meta.name();
				}
	
				if (noUse) {
					continue;
				}
	
				FieldProperty fieldProperty = new FieldProperty(fieldName, cnName);
				fieldProperty.setReturnType(attribute.getClazz());
	
				fieldProperty.setColumnName(attribute.getColumn().name());
	
				if (attribute.getClazz().isEnum()) {
					fieldProperty.setOperate(OperateType.SELECT.toString()
							.toLowerCase());
				} else if (operateType == null) {
					fieldProperty.setOperate(OperateType.INPUT.toString()
							.toLowerCase());
				} else {
					fieldProperty.setOperate(operateType.toString().toLowerCase());
				}
				fieldProperty.setOption(attribute.getOption());
				fieldProperty.setCodes(attribute.getCodes());
				fieldProperties.add(fieldProperty);
	
			}
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
		return fieldProperties;
	}

//	public static Column getColumnAnnotation(Class<?> clazz, String fieldName){
//		try{
//			Field field = clazz.getDeclaredField(fieldName);
//			Column column = field.getAnnotation(Column.class);
//			if (column != null)
//				return column;
//	
//			Method method = getGetMethod(clazz, fieldName);
//			column = method.getAnnotation(Column.class);
//			return column;
//		} catch (Exception e) {
//			logger.error("Exception Throwable", e);
//			return null;
//		}
//
//	}
	
	public static <T extends Annotation> T getAnnotation(Class<?> clazz, String fieldName,Class<T> annotationClass){
		try{
			Field field = clazz.getDeclaredField(fieldName);
			T annotation = field.getAnnotation(annotationClass);
			if (annotation != null)
				return annotation;
	
			Method method = getGetMethod(clazz, fieldName);
			if(method==null){
				return null;
			}
			annotation = method.getAnnotation(annotationClass);
			return annotation;
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
			return null;
		}

	}

	public static LinkedHashMap<String, String> getEnumStringAndDescriptionMap(
			Class clazz){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		try{
			map.put("", "请选择");
	
			Method getDescription = clazz.getMethod("description");
			// 得到enum的所有实例
			Object[] objs = clazz.getEnumConstants();
			for (Object obj : objs) {
				map.put(obj.toString(), (String) getDescription.invoke(obj));
			}
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
		return map;
	}

	public static LinkedHashMap<String, String> getEnumCodeAndDescriptionMap(
			Class clazz){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		try{
			map.put("", "请选择");
			Method getCode = clazz.getMethod("code");
			Method getDescription = clazz.getMethod("description");
			// 得到enum的所有实例
			Object[] objs = clazz.getEnumConstants();
			for (Object obj : objs) {
				map.put((String) getDescription.invoke(obj),
						(String) getDescription.invoke(obj));
			}
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
		return map;
	}

	public static Object executeMethod(String entityName, String methodName,
			Class[] inputParamTypes, Object[] inputParams){

		Object returnObj = null;
		try {
			Object dao = SpringBeanUtil.getBean(entityName + "Dao");
			Method method = dao.getClass().getMethod(methodName,
					inputParamTypes);

			returnObj = method.invoke(dao, inputParams);

		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
		return returnObj;

	}

	public static Object executeMethod(Class<?> clazz, String methodName,
			Class[] inputParamTypes, Object[] inputParams){
		
		String entityName=StringUtils.uncapitalize(clazz.getSimpleName());
		
		return executeMethod(entityName,methodName,inputParamTypes,inputParams);

	}

	/**
	 * 
	 * java反射bean的get方法
	 * 
	 * 
	 * 
	 * @param objectClass
	 * 
	 * @param fieldName
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static Method getGetMethod(Class objectClass, String fieldName) {
		Method getMethod=null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName,
					objectClass);

			getMethod = pd.getReadMethod();// 获得get方法

			return getMethod;
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}

		return getMethod;
	}

	/**
	 * java反射bean的set方法
	 * 
	 * @param objectClass
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Method getSetMethod(Class objectClass, String fieldName) {
		Method setMethod=null;
		try {
			Field field = objectClass.getDeclaredField(fieldName);

			StringBuffer sb = new StringBuffer();
			sb.append("set");
			sb.append(StringUtils.capitalize(fieldName));

			setMethod = objectClass.getMethod(sb.toString(),
					new Class[] { field.getType() });
			
//			PropertyDescriptor pd = new PropertyDescriptor(fieldName,
//					objectClass);
//
//			setMethod = pd.getWriteMethod();// 获得set方法

		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
		return setMethod;

	}

	/**
	 * 执行set方法
	 * 
	 * @param o执行对象
	 * @param fieldName属性
	 * @param value值
	 */
	public static void invokeSet(Object o, String fieldName, Object value) {		
		try {
			Method method = getSetMethod(o.getClass(), fieldName);
			method.invoke(o, new Object[] { value });

		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}
	}

	/**
	 * 执行get方法
	 * 
	 * @param o执行对象
	 * @param fieldName属性
	 */

	public static Object invokeGet(Object o, String fieldName) {		
		try {
			Method method = getGetMethod(o.getClass(), fieldName);
			return method.invoke(o);
		} catch (Exception e) {
			logger.error("Exception Throwable", e);
		}

		return null;

	}
	
	public static boolean isLogicRemovedObj(Class<?> clazz){
		boolean isTrue=false;
		try{
			if(clazz.newInstance() instanceof IRemovedModel){
				isTrue = true;			
			}
		}catch(Exception e){
			logger.error("Exception Throwable", e);
		}
		
		return isTrue;
	}

	public static boolean isMultiModelObj(Class<?> clazz){
		boolean isTrue=false;
		try{
			if(clazz.newInstance() instanceof IMultiModel){
				isTrue = true;			
			}
		}catch(Exception e){
			logger.error("Exception Throwable", e);
		}
		
		return isTrue;
	}
}
