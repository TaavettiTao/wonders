package com.wonders.frame.test.service;

import javax.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.test.model.bo.Test;
import com.wonders.frame.test.service.TestAspect;

/**
 * 系统日志AOP
 * @author Administrator
 *
 */
@Component
@Aspect
public class TestAspect{
	private static Logger logger=LoggerFactory.getLogger(Test.class);
	@Resource
	private BasicCrudService basicCrudService;

	/**
	 * 指定切入点 
	 */
	@Pointcut("execution(* com.wonders.frame.test.dao.TestDao.save(..))")
	public void saveOrUpdateTest(){}
	
	/**
	 * 添加操作日志(后置通知) 
	 * @param joinPoint
	 * @param rtv
	 */
	@AfterReturning(value="saveOrUpdateTest()",argNames="rtv", returning="rtv")
	public void insertTestLog(JoinPoint joinPoint, Object rtv) throws Throwable{
		logger.debug("start aop....... \n");
		

	}

	
}
