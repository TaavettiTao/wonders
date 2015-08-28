package com.wonders.frame.security.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wonders.frame.security.dao.LoginDao;
import com.wonders.frame.security.model.bo.UserDetail;
import com.wonders.frame.security.service.UserDetailService;

@Service("userDetailService")
@Transactional
public class UserDetailServiceImpl implements UserDetailService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private LoginDao loginDao;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String dept=request.getParameter("dept");
		UserDetail result = null;
		try {
			List<Object[]> objs= loginDao.findUserInfoByUsername(username);
			
			Set<String> roles=new HashSet<String>();
			if(objs!=null && objs.size()>0){
				result=new UserDetail();
				result.setDept(dept);//设置部门
				for(int i=0;i<objs.size();i++){
					if(i==0){//设置用户名、密码
						result.setUsername(objs.get(0)[0].toString());
						result.setPassword(objs.get(0)[1].toString());
					}
					roles.add(objs.get(i)[2].toString().trim());
				}
				result.setRoles(roles);//设置角色
			}
		} catch (NoResultException e) {
			logger.debug("username {} not found.", username);
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
		return result;  
	}
	
}
