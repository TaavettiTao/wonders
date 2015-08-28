/** 
* @Title: GroupApiController.java 
* @Package com.wonders.frame.core.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.console.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wonders.frame.console.model.bo.Organ;



/** 
 * @ClassName: GroupApiController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */

@Controller
@RequestMapping("/api/group")
public class GroupApiController extends AbstractGenericRelationObjController<Organ>{
	
}


