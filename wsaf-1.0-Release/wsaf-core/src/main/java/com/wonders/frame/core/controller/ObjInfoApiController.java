/** 
* @Title: UserEditController.java 
* @Package com.wonders.frame.core.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wonders.frame.core.model.bo.ObjInfo;

@Controller
@RequestMapping("/api/objInfo")
public class ObjInfoApiController extends AbstractSingleCrudController<ObjInfo>{
	
}
