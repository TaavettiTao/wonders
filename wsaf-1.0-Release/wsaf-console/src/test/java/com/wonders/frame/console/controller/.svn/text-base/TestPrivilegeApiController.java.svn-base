package com.wonders.frame.console.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.wonders.frame.console.model.bo.Privilege;

public class TestPrivilegeApiController extends AbstractTestController<Privilege>{

	@Override
	public MockHttpServletRequestBuilder buildPermissionRec() {
		return get("/api/privilege/1/permission").param("ticket", "241_42");
	} 
	
	@Override
	public MockHttpServletRequestBuilder buildChildNodeRec() {
		return get("/api/privilege/1/child").param("ticket", "241_42");
	} 
	
	@Override
	public MockHttpServletRequestBuilder buildParentNodeRec() {
		return get("/api/privilege/1/parent").param("ticket", "241_42");
	} 
    
	@Override
	public MockHttpServletRequestBuilder buildGetPropertyRec() {
		//此方法有两个非必须参数：fields,show
		return get("/api/privilege/info");
	}

	@Override
	public MockHttpServletRequestBuilder buildFindRec() {
		return get("/api/privilege").param("page", "1,5");
	}

	@Override
	public MockHttpServletRequestBuilder buildGetRec() {
		return get("/api/privilege/241");
	}

	@Override
	public MockHttpServletRequestBuilder buildSaveOrUpdateRec() {
		return post("/api/privilege").param("id", "373").param("name", "接口测试用户test1").param("loginName", "junitTest").param("password", "111111");
	}

	@Override
	public MockHttpServletRequestBuilder buildRemoveRec() {
		return delete("/api/privilege/373");
	}

	@Override
	public MockHttpServletRequestBuilder buildDeleteRec() {
		return delete("/api/privilege/del/373");
	} 
}
