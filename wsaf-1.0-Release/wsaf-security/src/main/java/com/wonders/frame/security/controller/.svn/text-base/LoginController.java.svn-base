package com.wonders.frame.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.security.service.LoginService;

/**
 * 
 * @author taoweiwei
 *
 */
@Controller
@RequestMapping("/common")
public class LoginController {
	
	@Resource
	private LoginService loginService;
	
	@RequestMapping("/login")
	public String toLogin(){
		return "common/login";
	}
	
	@RequestMapping("/access-denied")
	public String toDenyAccess(){
		return "error/access-denied";
	}
	

	@RequestMapping("/isExist")
	@ResponseBody
	public HashMap<String,Object> isExist(String username){
		HashMap<String,Object> result=new HashMap<String,Object>();
		try {
			User user =loginService.findUserByUsername(username);
			result.put("success", true);
			result.put("result", user);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}
}
