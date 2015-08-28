/** 
* @Title: UserEditController.java 
* @Package com.wonders.frame.core.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wonders.frame.core.model.bo.Attach;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.service.AttachService;

@Controller
@RequestMapping("/api/attach")
public class AttachApiController extends AbstractSingleCrudController<Attach>{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());	
	@Resource 
	AttachService attachService;
	/**
	 * 上传文件,支持多附件(未与业务绑定)
	 * @param request.groupName
	 * @param request.uploader
	 * @return list<attach>
	 */
	@RequestMapping(value = "/upload", method ={RequestMethod.POST})
	@ResponseBody
	public ReturnObj<List<Attach>> upload(HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IllegalAccessException{
		ReturnObj<List<Attach>> returnObj=new ReturnObj<List<Attach>>();
		List<Attach> attachList=attachService.uploadfy(request);
		if(attachList.size()>0){
			return new ReturnObj<List<Attach>>(attachList);
		}else{	
			return new ReturnObj<List<Attach>>("Attach files upload failed");
		}

	}
	
	/**
	 * 绑定附件至业务(批量)
	 * @param request.modelName
	 * @param request.modelId
	 * @param request.attachIds
	 * @return
	 */
	@RequestMapping(value = "/bind", method ={RequestMethod.POST})
	@ResponseBody
	public ReturnObj bindAttachs(HttpServletRequest request){
		String modelName=request.getParameter("modelName");
		String modelId=request.getParameter("modelId");
		String attachIds=request.getParameter("attachIds");
		
		boolean success=attachService.saveBind(modelName,modelId,attachIds);
		return new ReturnObj(success);

	}
	
	
	/**
	 * 下载附件
	 * @param id
	 * @return
	 */
	
	@RequestMapping(value="/download/{id}", method ={RequestMethod.GET})
	@ResponseBody
	public void downloadById(@PathVariable("id") Integer id,HttpServletResponse response){
		attachService.download(id, response);
	}
	
	/**
	 * 删除一组附件(URL:/attach,method=DELETE)
	 * @param request.modelName		
	 * @param request.modelId
	 * @param request.groupName
	 * @return
	 */
//	@ResponseBody
//	@RequestMapping(value="/batchRemove",method={RequestMethod.POST})
//	public HashMap<String,Object> removeByGroup(HttpServletRequest request){
//		HashMap<String,Object> result=new HashMap<String,Object>();
//		
//		boolean success=attachService.removeByGroup(request);
//		if(success){
//			result.put("success", true);
//		}else{
//			result.put("success", false);
//		}
//		return result;
//	}
}
