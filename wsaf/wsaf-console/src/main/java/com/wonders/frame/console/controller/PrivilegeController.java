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

import com.wonders.frame.console.model.bo.Privilege;
import com.wonders.frame.core.controller.AbstractSingleCrudController;


@Controller
@RequestMapping("/privilege")
public class PrivilegeController extends AbstractSingleCrudController<Privilege>{
}
