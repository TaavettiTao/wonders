package com.wonders.frame.login.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.LoginInfo;
import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.service.UserService;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.login.service.LoginService;
import com.wonders.frame.login.util.UserFormValidator;

@Controller
@SessionAttributes("user")
public class LoginControllers {
	private Logger logger = LoggerFactory.getLogger(LoginControllers.class);

	@Resource
	private LoginService loginService;

	@Resource
	UserService userService;

	@Autowired
	private UserFormValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	// @ModelAttribute("logUser")
	// public User getUser(){
	// User logUser = new User();
	// return logUser;
	// }

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginForm() {
		ModelAndView mav = new ModelAndView("login");
		User user = new User();
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(
			@RequestParam(required = true, defaultValue = "") String loginName,
			@RequestParam(required = true, defaultValue = "") String password,
			@ModelAttribute("user") User user, BindingResult result,
			HttpSession httpSession,SessionStatus status,HttpServletRequest request) {
		validator.loginValidate(user, result);
//		List<User> users = userService.findByLoginName(loginName);
//		validator.isExistCheck(users,result);
	
		if (result.hasErrors()) {
			return "login";
		} else {
			List<User> users = userService.findByLoginName(loginName);
			if (users.size() == 0) {
//				if (loginService.checkName(loginName) == 0) {
				result.rejectValue("loginName", "filed.loginName",
						"loginName Does not exist.");
				return "login";
			} else {
				boolean flag = loginService.loginUsers(loginName, password);
				if (flag) {
					List<Integer> ruleTypeIds = loginService.getRuleTypeFromUserId(users.get(0).getId());
					List<String> resPaths = new ArrayList<String>();
					for(int i=0;i<ruleTypeIds.size();i++){
						ruleTypeIds.get(i);
			    		ReturnObj<LoginInfo> ro = userService.login(loginName, password, 42);
			    		if(ro.getInfo().getSuccess()==true){
			    			List<Permission> permissions = ro.getData().getPermission();
			    			for(int j=0;j<permissions.size();j++){
			    				resPaths.add(permissions.get(j).getResourcePath());
			    			}
			    		}
					}
					User loginUser = users.get(0);
					
					httpSession.setAttribute("ruleTypeIds", ruleTypeIds);
					httpSession.setAttribute("resPaths", resPaths);
					httpSession.setAttribute("loginUser", loginUser);
					request.getSession().setAttribute("ruleTypeIds1", ruleTypeIds);
					logger.info("login sucesss!");
					// return "redirect:viewAllUsers";
					return "redirect:jsp/ruleType/list";
				} else {
					result.rejectValue("password", "filed.password",
							"Password is Error!");
					return "login";
				}
			}
		}
	}

	@RequestMapping("/loginUsers")
	public String loginUsers(
			@RequestParam(required = true, defaultValue = "") String name,
			@RequestParam(required = true, defaultValue = "") String password,
			@ModelAttribute("logUser") User logUser, BindingResult result) {
		boolean flag = loginService.loginUsers(name, password);
		// validator.loginValidate(logUser, result);
		if (flag) {
			logUser.setName(name);
			logger.info("login sucesss!");
			return "redirect:viewAllUsers";
		} else {
			logUser.setName(null);
			logger.info("login excess or exit");
			return "loginUsers";
		}
	}

	@RequestMapping("/searchUsers")
	public ModelAndView searchUsers(
			@RequestParam(required = false, defaultValue = "") String name) {
		ModelAndView mav = new ModelAndView("showUsers");
		List<User> users = loginService.searchUsers(name.trim());
		mav.addObject("SEARCH_CONTACTS_RESULTS_KEY", users);
		return mav;
	}

	@RequestMapping("/viewAllUsers")
	public ModelAndView getAllUsers() {
		logger.info("excute search...");
		ModelAndView mav = new ModelAndView("showUsers");
		List<User> users = loginService.getAllUsers();
		mav.addObject("SEARCH_CONTACTS_RESULTS_KEY", users);
		return mav;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.GET)
	public ModelAndView newuserForm() {
		ModelAndView mav = new ModelAndView("newUser");
		User user = new User();
		mav.getModelMap().put("newUser", user);
		return mav;
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String create(@ModelAttribute("newUser") User user,
			BindingResult result, SessionStatus status) {
		if (loginService.checkName(user.getName()) > 0) {
			result.rejectValue("name", "filed.name", "Name is exists.");
		}
		validator.validate(user, result);
		if (result.hasErrors()) {
			return "newUser";
		}
		loginService.save(user);
		status.setComplete();
		return "login";
	}

	@RequestMapping(value = "/saveUser2", method = RequestMethod.GET)
	public ModelAndView newuserForm2() {
		ModelAndView mav = new ModelAndView("newUser2");
		User user = new User();
		mav.getModelMap().put("newUser2", user);
		return mav;
	}

	@RequestMapping(value = "/saveUser2", method = RequestMethod.POST)
	public String create2(@ModelAttribute("newUser2") User user,
			BindingResult result, SessionStatus status) {
		validator.validate(user, result);
		if (result.hasErrors()) {
			return "newUser2";
		}
		loginService.save(user);
		// status.setComplete();
		return "redirect:viewAllUsers";
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("id") Integer id) {
		ModelAndView mav = new ModelAndView("editUser");
		User user = loginService.getById(id);
		mav.addObject("editUser", user);
		return mav;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String update(@ModelAttribute("editUser") User user,
			BindingResult result, SessionStatus status) {
		validator.validate(user, result);
		if (result.hasErrors()) {
			return "editUser";
		}
		loginService.update(user);
		// status.setComplete();
		return "redirect:viewAllUsers";
	}

	@RequestMapping("deleteUser")
	public ModelAndView delete(@RequestParam("id") Integer id) {
		ModelAndView mav = new ModelAndView("redirect:viewAllUsers");
		loginService.delete(id);
		return mav;
	}

	@RequestMapping("logout")
	public ModelAndView exit(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
//		// 清除session
//		        Enumeration<String> em = request.getSession().getAttributeNames();
//		        while (em.hasMoreElements()) {
//		            request.getSession().removeAttribute(em.nextElement().toString());
//		        }

		if (session != null) {
			session.invalidate();
		}
		ModelAndView mav = new ModelAndView("login");
		// status.setComplete();
		logger.info("logout success!");
		return mav;
	}

	@RequestMapping("accessDenied")
	public ModelAndView toDenyAccess() {
		ModelAndView mav = new ModelAndView("error/access-denied");
		// status.setComplete();
		logger.info("accessDenied!");
		return mav;
//		return "error/access-denied";
	}

}