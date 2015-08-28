/** 
* @Title: UserEditController.java 
* @Package com.wonders.frame.core.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.complex.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wonders.frame.complex.model.bo.UserOld;
import com.wonders.frame.core.controller.AbstractGenericController;
import com.wonders.frame.core.controller.AbstractGenericCrudController;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.ObjInfoService;

@Controller
@RequestMapping("/userOld")
//public class UserOldController extends AbstractGenericController<UserOld>{
public class UserOldController extends AbstractGenericCrudController<UserOld>{
	@Resource
	BasicCrudService basicCrudService;

	@Resource
	ObjInfoService objInfoService;
	
	@RequestMapping(value="/userOldAdd",method=RequestMethod.GET)
	public String meetingManageAdd(ModelMap model,HttpServletRequest request) {	
			 
		 return "common/userOldAdd";
	}
}
