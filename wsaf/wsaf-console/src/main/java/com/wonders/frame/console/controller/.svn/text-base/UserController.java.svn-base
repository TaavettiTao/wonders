/** 
* @Title: UserEditController.java 
* @Package com.wonders.frame.core.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.console.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.core.controller.AbstractSingleCrudController;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractSingleCrudController<User>{
   
	@RequestMapping("/my-information")
	public String toMyInfomation(){
		return "user/my-information";
	}
	
	@RequestMapping("/edit-information")
	public String toEditInfomation(){
		return "user/edit-information";
	}
}
