/** 
* @Title: GenericController.java 
* @Package com.wonders.frame.core.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.core.model.vo.EntityProperty;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SingleQueryParams;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.SingleCrudService.QueryType;
import com.wonders.frame.core.utils.CustomDateTimeFormat;
import com.wonders.frame.core.utils.SingleParamsConvertUtil;
/** 
 * @ClassName: GenericController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */

public abstract class AbstractSingleCrudController<T>{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private BasicCrudService basicCrudService;
	
	public BasicCrudService getBasicCrudService(){
		return basicCrudService;
	}
	
	private Class<T> entityClass;
	
	public Class<T> getEntityClass(){
		return entityClass;
	}
	
	private String entityName;
	
	public String getEntityName(){
		return entityName;
	}

	public AbstractSingleCrudController() {
		Type t = getClass().getGenericSuperclass();
        this.entityClass = (Class<T>)((ParameterizedType)t).getActualTypeArguments()[0];    
		this.entityName=StringUtils.uncapitalize(this.entityClass.getSimpleName());
	}
	@InitBinder   
    public void initBinder(WebDataBinder binder) {   
		
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new CustomDateTimeFormat(), true));      
    }  
	
	// 获得对象属性,保留关键字：fields,show
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public ReturnObj<EntityProperty> getProperty(
			@RequestParam(value = "fields", required = false) String fields,
			@RequestParam(value = "show", required = false, defaultValue = "true") boolean show) {
		logger.debug("getProperty");
		return basicCrudService.getProperty(this.entityClass, fields, show);

	}

	// 查询一组对象,保留关键字：json,hql,row,page,sort
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ReturnObj find(
			HttpServletRequest request) {
		logger.debug("find");
		SingleQueryParams sqp=SingleParamsConvertUtil.getQueryParams(this.entityClass, request);
		if(sqp.getQueryType().equals(QueryType.COUNT)){
			return basicCrudService.count(sqp);
		}else if(sqp.getQueryType().equals(QueryType.PAGE)){
			return basicCrudService.findByPage(sqp);
		}else{
			return basicCrudService
				.find(this.entityClass,request);
		}

	}
	

	// 查询单个记录
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ReturnObj get(
			@PathVariable("id") Integer id) {
		logger.debug("get");
		return basicCrudService.get(this.entityClass, id);

	}

	// 新建或更新,id为空为新建，不为空则为更新
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnObj saveOrUpdate(HttpServletRequest request) {		
		logger.debug("saveOrUpdate");
		return basicCrudService.saveOrUpdate(this.entityClass, request);

	}
	/*
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnObj<T> saveOrUpdate(@Valid @ModelAttribute T obj,BindingResult bindingResult,
			HttpServletRequest request) {
		
		logger.debug("saveOrUpdate");
		//return basicCrudService.saveOrUpdate(this.entityClass, request);
		
		try{
			HashMap<String, String> mapErrors = new LinkedHashMap<String, String>();
			if (bindingResult.hasErrors()) {
				for(FieldError error:bindingResult.getFieldErrors()){
					mapErrors.put(error.getField(), error.getDefaultMessage());
					
				}
			}
			if(!mapErrors.isEmpty()){			
				return new ReturnObj<T>(false,mapErrors);
			}else{
	
				obj= (T)ReflectUtil.executeMethod(this.entityClass,
					"save", new Class[] {Object.class}, new Object[] {obj});
				
				return new ReturnObj<T>(obj);
			}
		}catch(Exception e){
			return new ReturnObj<T>(false,e);
		}
		

	}
	*/

	// 逻辑删除
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ReturnObj<Integer> remove(
			@PathVariable("id") String id) {
			logger.debug("remove:id="+id);
			String[] ids=id.split(",");
			if(ids.length>1){
				List<Integer> idList=new ArrayList<Integer>();
				for(String i:ids){
					idList.add(Integer.valueOf(i));
				}
				return basicCrudService.removeByIds(this.entityClass,idList);
			}else{
				return basicCrudService.removeById(this.entityClass, Integer.valueOf(id));
			}

	}
	
	// 物理删除
	@RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ReturnObj<Integer> delete(
			@PathVariable("id") String id) {
			logger.debug("remove:id="+id);
			String[] ids=id.split(",");
			if(ids.length>1){
				List<Integer> idList=new ArrayList<Integer>();
				for(String i:ids){
					idList.add(Integer.valueOf(i));
				}
				return basicCrudService.deleteByIds(this.entityClass,idList);
			}else{
				return basicCrudService.deleteById(this.entityClass, Integer.valueOf(id));
			}

	}
		
	
	// 页面跳转
	@RequestMapping(value = "/page/{url}", method = RequestMethod.GET)
	public String page(@PathVariable("url") String url) {
		
		return "common/"+this.entityName+"/"+url;

	}
}
