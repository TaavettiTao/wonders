/** 
* @Title: CustomObjectMapper.java 
* @Package com.wonders.frame.core.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/** 
 * @ClassName: CustomObjectMapper 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
@Component("customObjectMapper") 
public class CustomObjectMapper extends ObjectMapper {  
  
    public CustomObjectMapper() {  
        this.configure(SerializationFeature.INDENT_OUTPUT, true);
    }  
} 
