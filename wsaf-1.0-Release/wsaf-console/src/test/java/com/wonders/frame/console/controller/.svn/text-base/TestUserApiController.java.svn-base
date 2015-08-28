package com.wonders.frame.console.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;  
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; 

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.wonders.frame.console.model.bo.User;
import com.wonders.frame.console.model.vo.LoginInfo;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.utils.JacksonMapper;

public class TestUserApiController extends AbstractTestController<User>{

    @Test  
    public void testLogin() throws Exception {  
    	MockHttpServletRequestBuilder builder = get("/api/user/login/acdm").param("loginName", "test").param("password", "test");
        this.mockMvc.perform(builder).andExpect(status().isOk()) 
                .andDo(print()).andReturn(); ;  
    } 
    
	@Override
	public MockHttpServletRequestBuilder buildPermissionRec() {
		return get("/api/user/241/permission").param("ticket", "241_42");
	} 
	
	@Override
	public MockHttpServletRequestBuilder buildChildNodeRec() {
		return get("/api/user/241/child").param("ticket", "241_42");
	} 
	
	@Override
	public MockHttpServletRequestBuilder buildParentNodeRec() {
		return get("/api/user/241/parent").param("ticket", "241_42");
	}

	@Override
	public MockHttpServletRequestBuilder buildGetPropertyRec() {
		//此方法有两个非必须参数：fields,show
		return get("/api/user/info");
	}

	@Override
	public MockHttpServletRequestBuilder buildFindRec() {
		return get("/api/user").param("page", "1,5");
	}

	@Override
	public MockHttpServletRequestBuilder buildGetRec() {
		return get("/api/user/241");
	}

	@Override
	public MockHttpServletRequestBuilder buildSaveOrUpdateRec() {
		return post("/api/user").param("id", "373").param("name", "接口测试用户test1").param("loginName", "junitTest").param("password", "111111");
	}

	@Override
	public MockHttpServletRequestBuilder buildRemoveRec() {
		return delete("/api/user/373");
	}

	@Override
	public MockHttpServletRequestBuilder buildDeleteRec() {
		return delete("/api/user/del/373");
	} 
}
