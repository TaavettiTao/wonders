/** 
 * @Title: BasicCOntroller.java 
 * @Package com.wonders.frame.core.controller 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author lushuaifeng
 * @version V1.0 
 */
package com.wonders.frame.core.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.service.MultiCrudService;

/**
 * @ClassName: AbstractSimpleCrudController
 * @Description: 提供简单的增删改查的抽象controller类
 */

public abstract class AbstractlMultiCrudController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private MultiCrudService multiCrudService;

	// 查询复杂对象,保留关键字：multiJson,page,row
	@RequestMapping(value = "/query/{ruleType}", method = RequestMethod.POST)
	@ResponseBody
	public ReturnObj multipleQuery(@PathVariable("ruleType") String ruleType,HttpServletRequest request) {
		String dataJson=request.getParameter("multiJson");

		logger.debug("multiJson:{}",dataJson);
		
		String rangeValue=null;
		Integer range1=0,range2=0;
		Boolean isFindPage=true;
		rangeValue=request.getParameter("page");
		
		if(rangeValue==null){
			isFindPage=false;
			rangeValue=request.getParameter("row");
		}
				
		if (rangeValue != null && !rangeValue.equals("")) {

			String[] arr = rangeValue.split(",");

			if (arr[0] != null && arr[0].matches("[0-9]+")) {
				range1 = Integer.valueOf(arr[0]);
			}

			if (arr[1] != null && arr[1].matches("[0-9]+")) {
				range2 = Integer.valueOf(arr[1]);
			}

		}
		if(isFindPage){
			return  multiCrudService
				.findByPage(ruleType,dataJson,range1,range2);
		}else{
			return  multiCrudService
			.findAll(ruleType,dataJson,range1,range2);
		}
		

	}
	

	
	// 新建或更新复杂对象,保留关键字：multipleData
	@RequestMapping(value = "/put/{ruleType}", method = RequestMethod.POST)
	@ResponseBody
	public ReturnObj saveOrUpdateMultipleObj(@PathVariable("ruleType") String ruleType,HttpServletRequest request) {
		String dataJson=request.getParameter("multiJson");
		logger.debug("saveOrUpdateMultipleObj:{}",dataJson);
		return multiCrudService.saveOrUpdate(ruleType,dataJson);
	}

	


}
