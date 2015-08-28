/** 
* @Title: JacksonTool.java 
* @Package com.wonders.frame.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.utils;


import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;


/** 
 * @ClassName: JacksonTool 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class TestJacksonMapper {
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	

	public void testGetInstance(){
		ObjectMapper rs=JacksonMapper.getInstance();
		Assert.assertNotNull(rs);
	}
	
	public void testReadValueWithClass() {
    }
	
	public void testReadValueWithJavaType() {
    }
	
	public void testGetCollectionType() {
     }
	
	public void testToJson() {
    }
	
	public void testConvertWithClass() {
    }
	
	public void testConvertWithJavaType() {
    }
}
