/** 
* @Title: JacksonTool.java 
* @Package com.wonders.frame.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.utils;


import java.util.HashMap;
import java.util.LinkedHashMap;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/** 
 * @ClassName: JacksonTool 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class JacksonMapper {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static final JavaType hmStr;
	
	public static final JavaType hmObj;
	
	public static final JavaType linkedHmObj;
	
	public static final JavaType linkedHmObjArr;
	
	
	static{
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				 
		mapper.setDateFormat(new CustomDateTimeFormat());
		
		hmStr=getCollectionType(HashMap.class, String.class, String.class);		
		 
		hmObj=getCollectionType(HashMap.class, String.class, Object.class);		
		
		linkedHmObj = getCollectionType(LinkedHashMap.class,String.class,Object.class);
		
		linkedHmObjArr = getCollectionType(LinkedHashMap.class, String.class, Object[].class);
	}
 
	
	public static ObjectMapper getInstance(){
		return mapper;
	}
	
	public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return null;
    }
	
	public static Object readValue(String content, JavaType javaType) {
        try {
            return mapper.readValue(content, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return null;
    }
	
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
     }
	
	public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return null;
    }
	
	public static <T> T convert(Object fromValue,Class<T> toValueType) {
        try {
            return mapper.convertValue(fromValue, toValueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return null;
    }
	
	public static Object convert(Object fromValue, JavaType javaType) {
        try {
            return mapper.convertValue(fromValue, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return null;
    }
}
