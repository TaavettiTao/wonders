/** 
* @Title: ObjInfoService.java 
* @Package com.wonders.frame.core.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.service;

import java.util.List;

import com.wonders.frame.core.model.bo.ObjInfo;

/** 
 * @ClassName: ObjInfoService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public interface ObjInfoService {
	public Class<?> getEntityClassByType(String type) throws Exception;
	
	public List<ObjInfo> findByIds(List<Integer> ids);

}
