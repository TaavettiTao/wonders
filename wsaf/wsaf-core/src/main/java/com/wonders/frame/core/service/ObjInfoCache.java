/** 
 * @Title: ConfigProperties.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author A18ccms A18ccms_gmail_com 
 * @date 2013-7-2 下午05:01:35 
 * @version V1.0 
 */
package com.wonders.frame.core.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.wonders.frame.core.model.vo.ObjectAttributeVo;
import com.wonders.frame.core.model.vo.ObjectInfoVo;
import com.wonders.frame.core.utils.ReflectUtil;

@Component
public class ObjInfoCache {
	private static final Logger logger = LoggerFactory
			.getLogger(ObjInfoCache.class);


	private static HashMap<Class<?>, ObjectInfoVo> hmObjectInfo = new HashMap<Class<?>, ObjectInfoVo>();
	
	private static HashMap<String, Class<?>> hmObjectClass = new HashMap<String, Class<?>>();
	
	private static void init() {

	}

	static {
		init();
	}
	
	public static ObjectInfoVo getObjectInfo(Class<?> clazz) throws Exception{
		
		if (hmObjectInfo.get(clazz) == null) {
				ObjectInfoVo objectInfo = ReflectUtil.getObjectInfo(clazz);

				hmObjectInfo.put(clazz, objectInfo);
				hmObjectClass.put(StringUtils.uncapitalize(clazz.getSimpleName()),clazz);
				return objectInfo;


		} else {
			return hmObjectInfo.get(clazz);
		}
	}

	public static Class<?> getObjectClass(String entityName) throws Exception{
		if (hmObjectClass.get(entityName) == null) {

			Class<?> clazz = ReflectUtil.getObjectClass(entityName);
			hmObjectClass.put(entityName,clazz);
			return clazz;

		} else {
			return hmObjectClass.get(entityName);
		}
		
	}
	
	public static HashMap<String,ObjectAttributeVo> getObjectAttributeMap(Class<?> clazz) throws Exception{
		
		return getObjectInfo(clazz).getAttributeMap();
		
	}
	
	public static List<ObjectAttributeVo> getObjectAttributeList(Class<?> clazz) throws Exception{
		
		return getObjectInfo(clazz).getAttributeList();
		
	}
	
	public static ObjectAttributeVo getObjectAttribute(Class<?> clazz,String attributeName) throws Exception{
		
		return getObjectInfo(clazz).getAttribute(attributeName);
		
	}


	public static void main(String[] args) {
	}

}
