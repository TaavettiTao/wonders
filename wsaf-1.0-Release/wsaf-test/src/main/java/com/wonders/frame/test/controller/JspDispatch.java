package com.wonders.frame.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wonders.frame.core.controller.AbstractSingleCrudController;
import com.wonders.frame.test.model.bo.Test;

@Controller
@RequestMapping("/page")
public class JspDispatch extends AbstractSingleCrudController<Test>{
	
	@RequestMapping(value="/grid",method=RequestMethod.GET)
	public String listPage(Model model,HttpServletRequest request) {	
		 request.setAttribute("entity", request.getParameter("entity"));
		 return "list";
	}
	@RequestMapping(value="/form",method=RequestMethod.GET)
	public String editPage(Model model,HttpServletRequest request) {	
		 request.setAttribute("entity", request.getParameter("entity"));	 
		 return "edit";
	}
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String loginPage() {	 
		 return "login";
	}
	
	
	@RequestMapping(value="/multiple",method=RequestMethod.GET)
	public String testMultiple() {	 
		 return "testMultiple";
	}
	
	
}
