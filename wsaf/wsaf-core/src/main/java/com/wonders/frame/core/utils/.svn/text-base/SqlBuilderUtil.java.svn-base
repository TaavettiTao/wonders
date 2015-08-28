package com.wonders.frame.core.utils;

import org.springframework.stereotype.Component;


import com.wonders.frame.core.service.MultiSqlBuilderService;
import com.wonders.frame.core.service.SingleHqlBuilderService;
@Component
public class SqlBuilderUtil {
	
	private static SingleHqlBuilderService hqlBuilderService;
	
	static{
		hqlBuilderService=(SingleHqlBuilderService)SpringBeanUtil.getBean("singleHqlBuilderService");
	}

	
	public static SingleHqlBuilderService getSingleHqlBuilder(){
		return hqlBuilderService;
	}
	
	public static MultiSqlBuilderService getMultiSqlBuilder(){
		return 	(MultiSqlBuilderService)SpringBeanUtil.getBean("multiSqlBuilderService");
	}
}
