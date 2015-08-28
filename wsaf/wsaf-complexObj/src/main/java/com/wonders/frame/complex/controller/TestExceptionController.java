/** 
 * @Title: UserEditController.java 
 * @Package com.wonders.frame.core.controller 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author lushuaifeng
 * @version V1.0 
 */
package com.wonders.frame.complex.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.complex.model.bo.UserOld;
import com.wonders.frame.complex.service.TestService;
import com.wonders.frame.exception.BusinessException;
import com.wonders.frame.exception.SystemException;

@Controller
@RequestMapping("/test")
public class TestExceptionController {

	@Resource
	private TestService testService;

	@RequestMapping(value = "/josontest")
	@ResponseBody
	public Map<String, Object> getjson() throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("content", "123");
			map.put("result", true);
			map.put("account", 1);
			throw new Exception();
		} catch (Exception e) {
			throw new BusinessException("detail of ajax exception information");
		}
	}

	@RequestMapping("/null")
	public void testNullPointerException() throws Exception {
		try {
			UserOld blog = null;
			// 这里就会发生空指针异常，然后就会返回定义在SpringMVC配置文件中的null视图
			blog.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 throw  new Exception();
		}
	}

	@RequestMapping("/number")
	public void testNumberFormatException() {
		// 这里就会发生NumberFormatException，然后就会返回定义在SpringMVC配置文件中的number视图
		Integer.parseInt("abc");
	}

	@RequestMapping("/default")
	public void testDefaultException() {
		if (1 == 1)
			// 由于该异常类型在SpringMVC的配置文件中没有指定，所以就会返回默认的exception视图
			throw new RuntimeException("Error!");
	}

	@RequestMapping("/systemException")
	public void testSystemException() {
		if (1 == 1)
			// 由于该异常类型在SpringMVC的配置文件中没有指定，所以就会返回默认的exception视图
			throw new SystemException("Error!");
	}

	@RequestMapping("/businessException")
	public void testBusinessException() throws BusinessException {
		if (1 == 1)
			throw new BusinessException("Controller异常");
		// 由于该异常类型在SpringMVC的配置文件中没有指定，所以就会返回默认的exception视图

	}

	@RequestMapping("/controller")
	public void testBusinessControllerException() throws BusinessException {
		throw new BusinessException("Controller异常");
	}

	@RequestMapping(value = "/service")
	public void service(HttpServletResponse response) throws Exception {
		testService.exception();
	}

	@RequestMapping(value = "/dao", method = RequestMethod.GET)
	public void dao(HttpServletResponse response) throws Exception {
		testService.dao();
	}
}
