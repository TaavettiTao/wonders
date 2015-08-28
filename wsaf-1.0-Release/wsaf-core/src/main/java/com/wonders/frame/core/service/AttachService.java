/** 
* @Title: AttachService.java 
* @Package com.wonders.frame.core.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wonders.frame.core.model.bo.Attach;

/** 
 * @ClassName: AttachService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public interface AttachService {
	public List<Attach> uploadfy(HttpServletRequest request);
	
	public boolean saveBind(String modelName,String modelId,String attachIds);
	
	public void download(Integer id,HttpServletResponse response);

	public boolean removeByGroup(String modelName,String modelId,String groupName);

}
