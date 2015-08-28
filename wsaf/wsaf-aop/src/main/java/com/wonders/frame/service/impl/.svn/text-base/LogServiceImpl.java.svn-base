package com.wonders.frame.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wonders.frame.dao.LogDao;
import com.wonders.frame.model.Log;
import com.wonders.frame.service.LogService;


@Service("logService")
@Transactional
public class LogServiceImpl implements LogService {

	private LogDao logDao;

	@Autowired
	public void setDao(LogDao dao) {
		this.logDao = dao;
	}
	

	@Override
	@Transactional
	public void saveLog(Log log) {
		System.out.println("*******save开始**********");
		logDao.save(log);
		 System.out.println("SpringAOP Test!");
	}
	
/*	*//**
	 * 获取登录管理员ID
	 * 
	 * @return
	 *//*
	public TestUser loginUser() {

		if(SecurityContextHolder.getContext() == null){
			return null;
		}
		
		if(SecurityContextHolder.getContext().getAuthentication() == null){
			return null;
		}
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		if(userDetails == null){
			return null;
		}
		
		//获取登录管理员帐号名
		String userName = userDetails.getUsername();
		
		if(userName == null || userName.equals("")){
			return null;
		}
		
		// 根据管理员帐号名获取帐号ID
		TestUser user = this.userService.findByName(userName);
		
		if(user == null){
			return null;
		}
		
//		return user.getId();
		return user;
	}*/
}