/** 
* @Title: CcateService.java 
* @Package com.wonders.frame.core.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.service;

import com.wonders.frame.core.model.bo.Ccate;

/** 
 * @ClassName: CcateService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public interface CcateService {
	public Ccate findByType(String type);
}
