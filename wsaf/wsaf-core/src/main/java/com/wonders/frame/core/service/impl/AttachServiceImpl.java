/** 
* @Title: AttachServiceImpl.java 
* @Package com.wonders.frame.core.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wonders.frame.core.dao.AttachDao;
import com.wonders.frame.core.model.bo.Attach;
import com.wonders.frame.core.service.AttachService;
import com.wonders.frame.core.utils.DateFormatUtil;
import com.wonders.frame.core.utils.FileUtil;

/** 
 * @ClassName: AttachServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
@Service("attachService")
public class AttachServiceImpl implements AttachService{
	private final Logger logger=LoggerFactory.getLogger(this.getClass());	
	@Resource
	private AttachDao attachDao;

	@Override
	public List<Attach> uploadfy(HttpServletRequest request) {		
		List<Attach> attachList=new ArrayList<Attach>();
		try{
			String groupName = request.getParameter("groupName");
			String uploader = request.getParameter("uploader");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();  
		    // 文件保存路径
		    String savePath=request.getSession().getServletContext().getRealPath("/")+"uploadFiles"; 
			savePath += File.separator + DateFormatUtil.getCurrentDate() + File.separator;
			// 创建文件夹
		    File file = new File(savePath);  
		    if (!file.exists()) {  
		        file.mkdirs();  
		    }  
		   
		    for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {  
		    	// 上传文件 
		    	MultipartFile mf = entity.getValue();  
		    	String fileName = mf.getOriginalFilename();
		    	long fileSize = mf.getSize();
		    	String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		    	// 重命名文件
				String newFileName = DateFormatUtil.getFileDateTime() + "_" + new Random().nextInt(1000);
				File uploadFile = new File(savePath + newFileName); 
				
				Attach attach = new Attach();
				attach.setFileName(fileName);
				attach.setExtName(fileExt);
				attach.setFileSize(fileSize/1024+"k");
				attach.setFilePath(savePath + newFileName);
				attach.setUploadDate(new Date());
				attach.setUploader(uploader);
				attach.setGroupName(groupName);		
				attachDao.save(attach);		
				
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
				
				attachList.add(attach);
		    }	
			}catch(IOException e){
				logger.error("Exception Throwable", e);
			}
		    
		return attachList;
	}



	@Override
	public boolean saveBind(HttpServletRequest request) {
		boolean success=false;
		String modelName=request.getParameter("modelName");
		String modelId=request.getParameter("modelId");
		String attachIds=request.getParameter("attachIds");
		
		if(modelName==null || modelName.equals("")||modelId==null || modelId.equals("")||attachIds==null || attachIds.equals("")){
			return success;			
		}
		
		String[] ids = attachIds.split(",");
		List<Integer> attachIdslist = new ArrayList<Integer>();
		for (int i=0;i<ids.length;i++){
			attachIdslist.add(Integer.parseInt(ids[i]));
		}
		attachDao.updateAttachRelation(modelName, modelId, attachIdslist);
		success=true;
		return success;
	}
	

	@Override
	public boolean removeByGroup(HttpServletRequest request) {
		boolean success=false;
		String modelName=request.getParameter("modelName");
		String modelId=request.getParameter("modelId");
		String groupName=request.getParameter("groupName");
		
		if(modelName==null || modelName.equals("")||modelId==null || modelId.equals("")||groupName==null || groupName.equals("")){
			return success;			
		}

		attachDao.removeByModelNameAndModelIdAndGroupName(modelName, modelId, groupName);
		success=true;
		return success;
	}
	
	@Override
	public void download(Integer id,
			HttpServletResponse response) {
		Attach attach=attachDao.findById(id);
		if(attach!=null){
			FileUtil.downLoadFile(attach.getFileName(), attach.getFilePath(), response);
		}
	}
	

}
