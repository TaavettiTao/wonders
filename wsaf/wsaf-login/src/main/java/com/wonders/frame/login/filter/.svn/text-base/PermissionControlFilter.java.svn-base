package com.wonders.frame.login.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wonders.frame.console.model.bo.UserInfo;
import com.wonders.frame.core.utils.SpringBeanUtil;
import com.wonders.frame.login.service.LoginAccessService;
/**
 * 
 * @author Administrator
 * 权限过滤器
 */
public class PermissionControlFilter implements Filter {
	
	public static final String IGNORE_URLS="ignoreUrls";
	
	private static String[] ignoreUrl;
	
	//将用户未登录之前访问的url保存下来，登录成功后继续跳转该url
    private static String firstVisitUrl=""; 

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String isIgnoreUrls=filterConfig.getInitParameter(IGNORE_URLS);
		ignoreUrl=isIgnoreUrls.split(",");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		//对忽略的路径放行
		if(isIgnoreUrl(req)){
			chain.doFilter(request, response);
			return;
		}
		
		UserInfo userInfo=(UserInfo)req.getSession().getAttribute("user");
		if(userInfo==null){
			if(!isIgnoreUrl(req)){
				firstVisitUrl=getCurrentUrl(req);
			}
			res.sendRedirect(req.getContextPath()+"/login/access");
			return;
		}else{
			if(!"".equals(firstVisitUrl)){
				 res.sendRedirect(firstVisitUrl);
				 firstVisitUrl="";
				 return ;
			}
			
			LoginAccessService loginAccessService=(LoginAccessService)SpringBeanUtil.getBean("loginAccessService");
			Map<String,Set<String>> resourceMap=loginAccessService.loadPrivilegeAndResource();
			for(String url:resourceMap.keySet()){
				if(getCurrentUrl(req).contains(getDealingUrl(url))){
					Set<String> prilileges=resourceMap.get(url);
					for(String prililege:prilileges){
						if(userInfo.getRoles().contains(prililege)){
							chain.doFilter(request, response);
							return ;
						}
					}
					//无权限访问：此处应该跳转访问拒绝页面，不应是跳转登陆页
					res.sendRedirect(req.getContextPath()+"/login/access-denied");
					return ;
				}
			}
			//如果到达此处，只能说明用户访问的url在系统中不存在
			res.sendRedirect(req.getContextPath()+"/login/access-denied");
			return ;
		}
	}

	@Override
	public void destroy() {}

	private String getCurrentUrl(HttpServletRequest request){
		String url=request.getRequestURL().toString();
		String urlParam=request.getQueryString();
		return url+"?"+urlParam;
	}
	
	private Boolean isIgnoreUrl(HttpServletRequest request){
		for(String url:ignoreUrl){
			if(getCurrentUrl(request).contains(url)){
				return true;
			}
		}
		return false;
	}
	
	public String getDealingUrl(String url){
		if(url.contains("*")){
			return url.substring(0, url.indexOf("*"));
		}
		return url;	
	}
}
