/**
 * 
 */
package com.wonders.frame.login.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.LoginInfo;
import com.wonders.frame.console.model.vo.Permission;
import com.wonders.frame.console.service.UserService;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.login.service.LoginService;
import com.wonders.frame.login.util.RequestUtil;

/**
 * @author tfj 2014-8-1
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
	private final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);
	public static final String LAST_PAGE = "com.alibaba.lastPage";
	private static final String[] IGNORE_URI = { "/login", "/logout", "/api",
			"/accessDenied", "/viewAllUsers","/js/","/css/","/images/","/Login/",
			"backui/", "frontui/" };

	@Resource
	UserService userService;
	@Resource
	LoginService loginService;

	/*
	 * 利用正则映射到需要拦截的路径
	 * 
	 * private String mappingURL;
	 * 
	 * public void setMappingURL(String mappingURL) { this.mappingURL =
	 * mappingURL; }
	 */
	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			RequestUtil.saveRequest();
		}

//		log.info("==============执行顺序: 1、preHandle================");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());

		log.info("requestUri:" + requestUri);
		log.info("contextPath:" + contextPath);
		log.info("url:" + url);
		for (String s : IGNORE_URI) {
			if (url.contains(s)) {
				return true;
			}
		}

		// 获取用户
		User user = (User) request.getSession().getAttribute("user");
		String username = "";
		String password = "";
		String loginName = "";
		if (user != null) {
			username = user.getName();
			password = user.getPassword();
			loginName = user.getLoginName();
		}

		for (String s : IGNORE_URI) {
			if (url.contains(s)) {
				return true;
			}
		}
		// System.out.println("***************username:"+username);
		if (loginName == "" || loginName == null) {
			log.info("Interceptor：跳转到login页面！");
			response.sendRedirect(contextPath + "/login");
			// request.getRequestDispatcher("/login").forward(request,
			// response);
			return false;
		} else {
			if ((request.getHeader("accept").indexOf("application/json") > -1 || (request
					.getHeader("X-Requested-With") != null && request
					.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				// 如果不是异步请求
				// Apply HTTP status code for error views, if specified.
				// Only apply it if we're processing a top-level request.
				System.out.println("###################### ajax");
				return true;
			} else {
				List<String> resPaths = (List<String>) request.getSession().getAttribute("resPaths");
				System.out.println("权限-------------------resPaths:"+resPaths);
				if(resPaths!=null && resPaths.size()>0){
					if (resPaths.contains("/**")) {
						return true;
					} else if (resPaths.contains(url)) {
							return true;
					} else {
						log.info("Interceptor：跳转到没有权限页面！");
						response.sendRedirect(contextPath + "/accessDenied");
//						res.sendRedirect(req.getContextPath()+"/common/access-denied");
						return false;
					}
				}else{
					log.info("Interceptor：跳转到login页面！");
					response.sendRedirect(contextPath + "/login");
					return false;
				}
			}
		}
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
//	@Override
//	public void postHandle(HttpServletRequest request,
//			HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		log.info("==============执行顺序: 2、postHandle================");
//		if (modelAndView != null) { // 加入当前时间
//			modelAndView.addObject("var", "测试postHandle");
//		}
//	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
//	@Override
//	public void afterCompletion(HttpServletRequest request,
//			HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		log.info("==============执行顺序: 3、afterCompletion================");
//	}

}
