package com.wonders.frame.core.controller;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
@WebAppConfiguration 
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestAttachApiController{
	@Resource
	private WebApplicationContext webApplicationContext;  
    private MockMvc mockMvc;  
    @Before  
    public void init(){  
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();  
    } 
    
    @Test
    public void testUpload() throws Exception{
    	MockMultipartFile file = new MockMultipartFile("file", "orig.txt", null, "bar".getBytes());  
        MvcResult mr=mockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/attach/upload").file(file).param("groupName", "groupTest")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    
    }
}
