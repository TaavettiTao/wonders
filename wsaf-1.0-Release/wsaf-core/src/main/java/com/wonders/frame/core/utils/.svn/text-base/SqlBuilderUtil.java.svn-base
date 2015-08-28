package com.wonders.frame.core.utils;

import org.springframework.stereotype.Component;


import com.wonders.frame.core.service.MultiSqlBuilderService;
import com.wonders.frame.core.service.SingleHqlBuilderService;
import com.wonders.frame.core.service.impl.SingleHqlBuilderServiceImpl;
@Component
public class SqlBuilderUtil {
	
	private static SingleHqlBuilderService hqlBuilderService;
	
	static{
		hqlBuilderService=new SingleHqlBuilderServiceImpl();
	}

	
	public static SingleHqlBuilderService getSingleHqlBuilder(){
		return hqlBuilderService;
	}
	
	public static MultiSqlBuilderService getMultiSqlBuilder(){
		return 	(MultiSqlBuilderService)SpringBeanUtil.getBean("multiSqlBuilderService");
	}
}
