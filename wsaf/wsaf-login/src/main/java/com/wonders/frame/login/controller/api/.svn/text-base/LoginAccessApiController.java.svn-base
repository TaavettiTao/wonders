package com.wonders.frame.login.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.bo.UserInfo;
import com.wonders.frame.console.service.UserService;
import com.wonders.frame.login.service.LoginAccessService;

@Controller
@RequestMapping("/login/api")
public class LoginAccessApiController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private LoginAccessService loginAccessService;
	
	@RequestMapping("/access")
	@ResponseBody
	public HashMap<String,Object> loginIn(HttpServletRequest request){
		HashMap<String,Object> result=new HashMap<String,Object>();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String dept=request.getParameter("dept");
		String verificationCode=request.getParameter("verificationCode");
		String sessionCode=(String)request.getSession().getAttribute("sessionCode");
//
//		if(!verificationCode.equals(sessionCode)){
//			result.put("errorMsg", "验证码不一致！");
//			result.put("success",false);
//			return result;
//		}
//		
		User user=userService.findUserByNameAndPassword(username, password);
		
		if(user==null){
			result.put("errorMsg", "用户名或密码错误！");
			result.put("success",false);
			return result;
		}
		
		Set<String> roles=loginAccessService.findUserInfoByUsername(username);
		
		UserInfo userInfo=new UserInfo(username,password,dept,roles);
		request.getSession().setAttribute("user", userInfo);
		result.put("success", true);
		return result;
	}
	
	
	@RequestMapping("/isExist")
	@ResponseBody
	public HashMap<String,Object> isExist(String username){
		HashMap<String,Object> result=new HashMap<String,Object>();
		try {
			User user =userService.findByName(username);
			result.put("success", true);
			result.put("result", user);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}
}
