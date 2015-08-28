/** 
* @Title: TestAttachService.java 
* @Package com.wonders.frame.core.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.google.common.base.Joiner;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.Attach;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.utils.JacksonMapper;

@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestAttachService extends AbstractTransactionalJUnit4SpringContextTests {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	private List<Integer> attachIds=new ArrayList<Integer>();
	
	private List<String> attachPaths=new ArrayList<String>();
	
	@Resource 
	AttachService attachService;
	
	@Resource 
	BasicCrudService basicCrudService;
	
	@Test
	public void testAttachMethod(){
		testUploadfy();
		testSaveBind();
		testDownload();
		testRemoveByGroup();
		for(String filePaht:attachPaths){
			File file=new File(filePaht);
			file.delete();
		}
		
	}
	public void testUploadfy(){
		//MockHttpServletRequest request = new MockHttpServletRequest();
		MockMultipartHttpServletRequest request=new MockMultipartHttpServletRequest();
		
		request.addParameter("groupName", "groupNameTest");
		request.addParameter("uploader", "tester");
		MockMultipartFile testFile= new MockMultipartFile("file", "测试文件.txt", null, "测试数据".getBytes());
		request.addFile(testFile);
		MockMultipartFile testFile2= new MockMultipartFile("file2", "测试文件2.doc", null, "测试数据2".getBytes());
		request.addFile(testFile2);
		List<Attach> rs=attachService.uploadfy(request);
		
		Assert.assertNotNull(rs);
		
		Assert.assertEquals(2,rs.size());
		logger.info(JacksonMapper.toJson(rs));
		for(int i=0;i<rs.size();i++){
			Attach attach=rs.get(i);
			Assert.assertEquals("groupNameTest", attach.getGroupName());
			Assert.assertEquals("tester", attach.getUploader());
			
			if(i==0){
				Assert.assertEquals("测试文件.txt", attach.getFileName());
			}else{
				Assert.assertEquals("测试文件2.doc", attach.getFileName());
			}
			String fileContent="";
			File file=new File(attach.getFilePath());
			try {
				FileReader fr = new FileReader(file);
				int j = 0;
				while ((j = fr.read()) != -1) {
					fileContent +=  (char) j;
				}
			} catch (Exception e) {

				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
			
			if(i==0){
				Assert.assertEquals("测试数据", fileContent);
			}else{
				Assert.assertEquals("测试数据2", fileContent);
			}
			
			attachIds.add(attach.getId());
			attachPaths.add(attach.getFilePath());
		}

		
		
		
	}
	
	public void testSaveBind(){
		boolean success=attachService.saveBind("modelNameTest", "modelIdTest",Joiner.on(",").join(attachIds));
		Assert.assertTrue(success);
		ReturnObj<List<IDefaultModel>> ro=basicCrudService.findByIds(Attach.class, attachIds);
		
		List<IDefaultModel> rs=ro.getData();
		Assert.assertTrue(ro.getInfo().getSuccess());
		Assert.assertEquals(2, rs.size());
		for(IDefaultModel attach:rs){
			
			Assert.assertEquals("modelNameTest", ((Attach)attach).getModelName());
			Assert.assertEquals("modelIdTest", ((Attach)attach).getModelId());
		}
		
		
	}
//	附件下载的方法尚无法测试
	public void testDownload(){				
		MockHttpServletResponse response = new MockHttpServletResponse();
		attachService.download(attachIds.get(0), response);
		
	}

	public void testRemoveByGroup(){
		boolean success =attachService.removeByGroup("modelNameTest", "modelIdTest", "groupNameTest");
		Assert.assertTrue(success);
		ReturnObj<List<IDefaultModel>> ro=basicCrudService.findByIds(Attach.class, attachIds);
		
		List<IDefaultModel> rs=ro.getData();
		Assert.assertTrue(ro.getInfo().getSuccess());
		Assert.assertEquals(0, rs.size());
	}
	
	

}
