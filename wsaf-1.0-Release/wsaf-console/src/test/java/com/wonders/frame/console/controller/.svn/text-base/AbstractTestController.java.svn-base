package com.wonders.frame.console.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-mvc.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class AbstractTestController<T> extends AbstractTransactionalJUnit4SpringContextTests{
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	private Class<T> entityClass;
	private String entityName;
	
	@Resource
	private WebApplicationContext webApplicationContext;  
    protected MockMvc mockMvc;  
      
    public AbstractTestController() {
    	Type t = getClass().getGenericSuperclass();
		this.entityClass = (Class<T>)((ParameterizedType)t).getActualTypeArguments()[0];   
		this.entityName=StringUtils.uncapitalize(this.entityClass.getSimpleName());
	}

	@Before    
    public void setUp(){    
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }  
    
	public abstract MockHttpServletRequestBuilder buildPermissionRec();
	public abstract MockHttpServletRequestBuilder buildChildNodeRec();
	public abstract MockHttpServletRequestBuilder buildParentNodeRec();
	public abstract MockHttpServletRequestBuilder buildGetPropertyRec();// 获得对象属性,保留关键字：fields,show
	public abstract MockHttpServletRequestBuilder buildFindRec();// 查询一组对象,保留关键字：json,hql,row,page,sort
	public abstract MockHttpServletRequestBuilder buildGetRec();// 查询单个记录
	public abstract MockHttpServletRequestBuilder buildSaveOrUpdateRec();// 新建或更新,id为空为新建，不为空则为更新
	public abstract MockHttpServletRequestBuilder buildRemoveRec();// 逻辑删除
	public abstract MockHttpServletRequestBuilder buildDeleteRec();// 物理删除
	
//	@Test
	public void testGetPermission() throws Exception {
		MockHttpServletRequestBuilder builder = buildPermissionRec();
		result(builder);
	}

//	@Test
	public void testGetChildNode() throws Exception {
		MockHttpServletRequestBuilder builder = buildChildNodeRec();
		result(builder);
	}

//	@Test
	public void testGetParentNode() throws Exception {
		MockHttpServletRequestBuilder builder = buildParentNodeRec();
		result(builder);
	}
	
	@Test
	public void testGetProperty() throws Exception {
		MockHttpServletRequestBuilder builder = buildGetPropertyRec();
		result(builder);
	}
	
	@Test
	public void testFind() throws Exception {
		MockHttpServletRequestBuilder builder = buildFindRec();
		result(builder);
	}
	
	@Test
	public void testGet() throws Exception {
		MockHttpServletRequestBuilder builder = buildGetRec();
		result(builder);
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		MockHttpServletRequestBuilder builder = buildSaveOrUpdateRec();
		result(builder);
	}
	
	@Test
	public void testRemove() throws Exception {
		MockHttpServletRequestBuilder builder = buildRemoveRec();
		result(builder);
	}
	
	@Test
	public void testDelete() throws Exception {
		MockHttpServletRequestBuilder builder = buildDeleteRec();
		result(builder);
	}
	
	public void result(MockHttpServletRequestBuilder builder){
		try {
			mockMvc.perform(builder).andExpect(status().isOk()).andDo(print())
			.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
