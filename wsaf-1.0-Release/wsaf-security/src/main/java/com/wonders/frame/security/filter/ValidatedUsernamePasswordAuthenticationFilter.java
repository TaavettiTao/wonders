package com.wonders.frame.security.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.LocaleUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;
/**
 * 在用户输完用户名、密码、验证码后，
 * 根据语言环境，初步验证用户名、密码规则是否符合要求,并且验证码是否一致
 * @author Administrator
 *
 */
public class ValidatedUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
     
	//验证码
	private String sessionvalidateCodeField="sessionCode";
	private String validateCodeParamter="validateCode";
	
	private String languageParameter = "lang";
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		Locale locale = obtainLocale(request);
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		String dept=obtainDept(request);
		
		String sessionCode=obtainSessionValidateCode(request);
		String validateCode=obtainValidateCode(request);
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("loginErrors", errors);
		request.setAttribute("hasErrors", false);
		String msg = null;
		
		if (! username.matches("[a-zA-Z0-9]{4,12}")) {
			msg = super.messages.getMessage("jsr303.login.username", locale);
			errors.add(msg);
		}
		
		if (! password.matches("[a-zA-Z0-9]{4,12}")) {
			msg = super.messages.getMessage("jsr303.login.password", locale);
			errors.add(msg);
		}
		
		if("".equals(dept)){
			msg=super.messages.getMessage("jsr303.login.dept.notEmpty", locale);
			errors.add(msg);
		}
		
//        if("".equals(validateCode)){
//        	msg=super.messages.getMessage("jsr303.login.validateCode.notempty", locale);
//			errors.add(msg);
//        }else if(!sessionCode.equalsIgnoreCase(validateCode)){
//        	msg=super.messages.getMessage("jsr303.login.validateCode", locale);
//			errors.add(msg);
//        }
		
		if (! errors.isEmpty()) {
			request.setAttribute("username",username);
			request.setAttribute("password",password);
			request.setAttribute("hasErrors", true);
			throw new AuthenticationServiceException("Data validation fail.");
		}
		
		Authentication authentication = null;
		try {
			authentication = super.attemptAuthentication(request, response);
		} catch (AuthenticationException e) {
			msg = super.messages.getMessage("jsr303.login.fail", locale);
			errors.add(msg);
			request.setAttribute("hasErrors", true);
			throw e;
		}
		return authentication;
	}

	//afterPropertiesSet 在初始化bean的时候执行
	@Override
	public void afterPropertiesSet() {
		Assert.hasLength(languageParameter);
		super.afterPropertiesSet();
	}

	
	//获取用户名
	protected String obtainUsername(HttpServletRequest request) {
		String username = super.obtainUsername(request);
		return username != null ? username.trim() : "";
	}

	//获取密码
	protected String obtainPassword(HttpServletRequest request) {
		String password = super.obtainPassword(request);
		return password != null ? password.trim() : "";
	}
	
	//获取部门
	protected String obtainDept(HttpServletRequest request) {
		String dept = request.getParameter("dept");
		return dept != null ? dept.trim() : "";
	}
	
	//获取前台验证码
	protected String obtainValidateCode(HttpServletRequest request){
		String validateCode=request.getParameter(validateCodeParamter);
		return validateCode!=null?validateCode.trim():"";
	}
	
	//获取session中的验证码
	protected String obtainSessionValidateCode(HttpServletRequest request){
		String sessionValidateCode=(String)request.getSession().getAttribute(sessionvalidateCodeField);
		return sessionValidateCode!=null?sessionValidateCode.trim():"";
	}
	
	
    
    
	//返回的首选区域，该客户端将接受的内容，基于Accept-Language头的。如果客户端请求不提供Accept-Language头，此方法将返回默认语言环境的服务器。
	protected Locale obtainLocale(HttpServletRequest request) {
		String language = request.getParameter(languageParameter);
		if (language == null) {
			return request.getLocale();
		} else {
			try {
				return LocaleUtils.toLocale(language);
			} catch (IllegalArgumentException e) {
				return request.getLocale();
			}
		}
	}
}
