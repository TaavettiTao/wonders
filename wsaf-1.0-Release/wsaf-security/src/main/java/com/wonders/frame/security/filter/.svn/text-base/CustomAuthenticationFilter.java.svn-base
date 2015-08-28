package com.wonders.frame.security.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wonders.frame.security.model.bo.UserDetail;
import com.wonders.frame.security.service.LoginService;
import com.wonders.frame.security.utils.CurrentUserHolder;

public class CustomAuthenticationFilter implements Filter {
	
	@Resource
	private LoginService loginService;
	
    //忽略过滤的url	
	private static final String[] ignoreUrls={"/common/login","/common/getDept",
		                                      "/validateCode/createCode.jpeg","/css/",
		                                      "/js/","/images/","/static/","/common/isExist",
		                                      "/relationRule/api/findObjByRule"};
	//将用户未登录之前访问的url保存下来，登录成功后继续跳转该url
	private static String firstVisitUrl="";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		
		//将忽略的路径放行
        if(isIgnoreUrl(req)){
         	chain.doFilter(request, response);
         	return ;
        }
		
        //第二步：在用户登录的情况下，对访问的url的进行过滤
        UserDetail user=CurrentUserHolder.get();
        if(user==null){
        	boolean flag=isIgnoreUrl(req);
        	if(!flag){
        		firstVisitUrl=getCurrentUrl(req);
        	}
        	res.sendRedirect(req.getContextPath()+"/common/login");
        	return;
        }else{
        	
        	setCookie(user.getUsername(),req,res);
        	
            if(!"".equals(firstVisitUrl)){
            	res.sendRedirect(req.getContextPath()+firstVisitUrl);
            	firstVisitUrl="";
            	return;
            }
        	//拒绝访问路劲放行
        	if(getCurrentUrl(req).indexOf("/common/access-denied")!=-1){
        		chain.doFilter(request, response);
				return;
        	}
        	
        	//第一步：先匹配当前访问url
        	Map<String,Set<String>> resourceMap=loginService.loadPrivilegeAndResource();
        	for (String url : resourceMap.keySet()) {
				if(getCurrentUrl(req).indexOf(getDealingUrl(url).trim())!=-1){
					//有权限访问
					Set<String> privileges=resourceMap.get(url);
					for (String privilege : privileges) {
						if(user.getRoles().contains(privilege)){
							chain.doFilter(request, response);
							return;
						}
					}
					//无权限访问：此处应该跳转访问拒绝页面，不应是跳转登陆页
					res.sendRedirect(req.getContextPath()+"/common/access-denied");
					return ;
				}
			}
        	//如果到达此处，只能说明用户访问的url在系统中不存在
			res.sendRedirect(req.getContextPath()+"/common/access-denied");
			return ;
        	
        }
	}
	
	/**
	 * 设置登录用户cookie
	 * @param username
	 * @param req
	 * @param res
	 */
	private void setCookie(String username, HttpServletRequest req,HttpServletResponse res) {
		Cookie[] cookies=req.getCookies();
		boolean flag=false;
		for(int i=0;i<cookies.length;i++){
			if(cookies[i].getName().equals("user") ){
				flag=true;
				break;
			}
		}
		try {
			if(!flag){
				Cookie cookie = new Cookie("user",URLEncoder.encode(username, "utf-8"));
				//设置cookie的生存时间，该时间与session超时时间保持一致
				int min=req.getSession().getMaxInactiveInterval()/60;
				cookie.setMaxAge(min*60);
				cookie.setPath("/");
			    res.addCookie(cookie);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {}

	private static String getCurrentUrl(HttpServletRequest request){
		String uri=request.getRequestURI();
		String contentPath=request.getContextPath();
		String queryParam=request.getQueryString();
		if(!"".equals(contentPath)){
			uri=uri.substring(contentPath.length());
		}
		if(queryParam!=null && !"".equals(queryParam)){
			uri+="?"+queryParam;
		}
		return uri;
	}
	
	/**
	 * 判断当前url是否是忽略的url
	 * @param request
	 * @return
	 */
	private static Boolean isIgnoreUrl(HttpServletRequest request){
		for(int i=0;i<ignoreUrls.length;i++){
			if(getCurrentUrl(request).indexOf(ignoreUrls[i].trim())!=-1){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 截取url，一直截取到为*停止
	 * @param url
	 * @return
	 */
	public String getDealingUrl(String url){
		if(url.contains("*")){
			return url.substring(0, url.indexOf("*"));
		}
		return url;	
	}
}
