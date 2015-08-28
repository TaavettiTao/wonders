/** 
* @Title: BasicCOntroller.java 
* @Package com.wonders.frame.core.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.complex.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.JavaType;
import com.wonders.frame.core.model.vo.EntityProperty;
import com.wonders.frame.core.model.vo.FieldProperty;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.ObjInfoService;
import com.wonders.frame.core.utils.DateFormatUtil;
import com.wonders.frame.core.utils.ExcelUtil;
import com.wonders.frame.core.utils.JacksonMapper;
import com.wonders.frame.core.utils.ReflectUtil;

/** 
 * @ClassName: AbstractSimpleCrudController 
 * @Description: 提供简单的增删改查的抽象controller类 
 */

public abstract class AbstractSimpleCrudJspController {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Resource
	BasicCrudService basicCrudService;

	@Resource
	ObjInfoService objInfoService;
	
	
	@InitBinder   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));      
    }  
	
//	获取字段定义
	@RequestMapping(value="/{crudObj}/getEntityProperty",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody 
	public HashMap<String,Object> getFieldList(@PathVariable("crudObj") String crudObj,HttpServletRequest request) {	
		logger.debug("Entering getFieldList(crudObj={},request={})",new Object[]{crudObj,
				request.getRequestURL().append(request.getQueryString()==null?"":"?"+request.getQueryString()),});
		HashMap<String,Object> result=new HashMap<String,Object>();
		try{
			Class crudObjClazz=objInfoService.getEntityClassByType(crudObj);
		
			EntityProperty entityProperty=ReflectUtil.getEntityProperty(crudObjClazz,
					request.getParameter("fieldFilter"),Boolean.valueOf(request.getParameter("isNeeded")));
			if(entityProperty!=null){
				result.put("success", true);
				result.put("result", entityProperty);
			}else{
				result.put("success", false);
			}
		}catch(Exception e){
			logger.error("Exception Throwable", e);
			result.put("success", false);

		}
		logger.debug("Leaving  getFieldList(): {\"success\":{}}",result.get("success"));
		return result;
	}
	
//	保存
	@RequestMapping(value="/{crudObj}/save",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody 
	public HashMap<String,Object> saveObject(@PathVariable("crudObj") String crudObj,HttpServletRequest request) {
		HashMap<String,Object> result=new HashMap<String,Object>();

		try{
			Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
			Map<String,String> paramMap=request.getParameterMap();
			HashMap<String,String> hmParam=new HashMap<String,String>();
			for(String key:paramMap.keySet()){
				hmParam.put(key, request.getParameter(key));
			}
			Object obj=JacksonMapper.convert(hmParam, crudObjClazz);
			if(hmParam.containsKey("id")){
				Object queryObj = basicCrudService.executeMethod(crudObj,
						"findById", new Class[] {Integer.class}, new Object[] {Integer.valueOf(hmParam.get("id"))});
				obj=ReflectUtil.setUpdateValue2Object(crudObjClazz, queryObj, obj, hmParam.keySet());
			}
			
			
			obj= basicCrudService.executeMethod(crudObj,
					"save", new Class[] {Object.class}, new Object[] {obj});
			result.put("success", true);
			result.put("result", obj);
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception Throwable", e);
			result.put("success", false);
		}
		return result;
	}
	
	
//	保存复杂关系
	@RequestMapping(value="/{crudObj}/saveObj",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody 
	public HashMap<String,Object> saveObj(@PathVariable("crudObj") String crudObj,HttpServletRequest request) {
		HashMap<String,Object> result=new HashMap<String,Object>();

		try{
			Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
			Map<String,String> paramMap=request.getParameterMap();
			HashMap<String,String> hmParam=new HashMap<String,String>();
			for(String key:paramMap.keySet()){
				hmParam.put(key, request.getParameter(key));
			}
			Object obj=JacksonMapper.convert(hmParam, crudObjClazz);
			if(hmParam.containsKey("id")){
				Object queryObj = basicCrudService.executeMethod(crudObj,
						"findById", new Class[] {Integer.class}, new Object[] {Integer.valueOf(hmParam.get("id"))});
				obj=ReflectUtil.setUpdateValue2Object(crudObjClazz, queryObj, obj, hmParam.keySet());
			}
			
			
			obj= basicCrudService.executeMethod(crudObj,
					"save", new Class[] {Object.class}, new Object[] {obj});
			result.put("success", true);
			result.put("result", obj);
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception Throwable", e);
			result.put("success", false);
		}
		return result;
	}
	
	
//	逻辑删除接口
	@RequestMapping(value="/{crudObj}/del/{id}",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public HashMap<String,Object> delObject(@PathVariable("crudObj") String crudObj,@PathVariable("id") Integer id) {
		HashMap<String,Object> result=new HashMap<String,Object>();
		try{
			
			Integer successNum= (Integer)basicCrudService.executeMethod(crudObj,
					"removeById", new Class[] {Integer.class}, new Object[] {id});
			if(successNum>0){
				result.put("success", true);			
			}else{
				result.put("success", false);
			}
		}catch(Exception e){
			logger.error("Exception Throwable", e);
			//e.printStackTrace();
			result.put("success", false);
		}
		
		return result;
	}
	
//	查询单个记录
	@RequestMapping(value="/{crudObj}/get/{id}",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody 
	public HashMap<String,Object> findById(@PathVariable("crudObj") String crudObj,@PathVariable("id") Integer id ){
		HashMap<String,Object> result=new HashMap<String,Object>();
		try{
			
			Object obj = basicCrudService.executeMethod(crudObj,
					"findById", new Class[] {Integer.class}, new Object[] {id});
			result.put("success", true);
			result.put("result", obj);
		}catch(Exception e){
			//e.printStackTrace();
			logger.error("Exception Throwable", e);
			result.put("success", false);
		}
		
		return result;
	}
	
	@RequestMapping(value="/{crudObj}/all",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody 
	public HashMap<String,Object> findAll(@PathVariable("crudObj") String crudObj,HttpServletRequest request) {
		HashMap<String,Object> result=new HashMap<String,Object>();
		Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
		Object qr =queryData("findAll",crudObjClazz,request);

		if(qr==null){
			result.put("success", false);
		}else{
			result.put("success", true);
			result.put("result", qr);
		}
		return result;
	}

	
//	按条件查询（分页，排序）
	@RequestMapping(value="/{crudObj}/page",method={RequestMethod.POST,RequestMethod.GET}) 
	@ResponseBody 
	public HashMap<String,Object> findByPage(@PathVariable("crudObj") String crudObj,HttpServletRequest request) {
		HashMap<String,Object> result=new HashMap<String,Object>();
		Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
		Object qr =queryData("findByPage",crudObjClazz,request);

		if(qr==null){
			result.put("success", false);
		}else{
			result.put("success", true);
			result.put("result", qr);
		}
		return result;
	}

	
	public Object queryData(String method,Class<?> crudObjClazz,HttpServletRequest request) {
		Object returnObj=null;
		try{
	        String sort=request.getParameter("sort");
	        LinkedHashMap<String,String> hmSort=new LinkedHashMap<String,String>();
	        if(sort!=null){
		        String[] sorts=sort.split(",");        
		
		        for(int i=0;i<sorts.length;i++){
		        	String[] order=sorts[i].split(" ");
		        	hmSort.put(order[0], order[1]);
		        }
	        }
			if("findAll".equalsIgnoreCase(method)){
				returnObj=basicCrudService.executeMethod(StringUtils.uncapitalize(crudObjClazz.getSimpleName()), "findAll", 
						new Class[] {HashMap.class,LinkedHashMap.class}, 
						new Object[] {ReflectUtil.formatQueryParam(request,crudObjClazz),hmSort});
				
			}else if("findByPage".equalsIgnoreCase(method)){
				int pageNum=0,pageSize=0;
				
		        String sPageSize=request.getParameter("pageSize");
		        if(sPageSize!=null &&sPageSize.matches("[0-9]+")){
		        	pageSize=Integer.valueOf(sPageSize);
		        }
		        String sPageNum=request.getParameter("pageNum");
		        if(sPageNum!=null &&sPageNum.matches("[0-9]+")){
		        	pageNum=Integer.valueOf(sPageNum);
		        }
		        
		        returnObj =basicCrudService.executeMethod(StringUtils.uncapitalize(crudObjClazz.getSimpleName()), "findByPage",
						new Class[] {HashMap.class,LinkedHashMap.class,Integer.class,Integer.class}, 
						new Object[] {ReflectUtil.formatQueryParam(request,crudObjClazz),hmSort,pageSize,pageNum});
			}

		}catch(Exception e){
			logger.error("Exception Throwable", e);
		}
		return returnObj;
	}
	/**
	 * @author mengjie
	 * 
	 * 导出数据Excel
	 * @param projectPlan
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{crudObj}/export2Excel",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void export2Excel(@PathVariable("crudObj") String crudObj,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String type=request.getParameter("type");
		Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
		
		Object dataContent=queryData("findAll",crudObjClazz,request);
		
		JavaType javaType = JacksonMapper.getCollectionType(ArrayList.class, HashMap.class); 		
		List<HashMap> records=(List<HashMap>)JacksonMapper.convert(dataContent, javaType);

		String exportFields=getExcelExportField(type);
		
		List<FieldProperty> FieldProperties=ReflectUtil.getFieldProperties(crudObjClazz, exportFields, true);
		
		LinkedHashMap<String,String> hmFieldCnName=new LinkedHashMap<String,String>();
		HashMap<String,LinkedHashMap<String,String>> hmFieldOption=new HashMap<String,LinkedHashMap<String,String>>();
		for(FieldProperty fieldProperty:FieldProperties){
			hmFieldCnName.put(fieldProperty.getPath(), fieldProperty.getName());
			hmFieldOption.put(fieldProperty.getPath(), fieldProperty.getOption());
		}
		
		//数据转换
		List<HashMap<String,String>> excelRecords=new ArrayList<HashMap<String,String>>();
		String[] keys=hmFieldCnName.keySet().toArray(new String[0]);
		for (int i = 1; i <= records.size(); i++) {
			HashMap<String,String> excelRowRecord= new HashMap<String,String>();
            
            for(int j=0;j<keys.length;j++){
            	
                String fieldValue=records.get(i-1).get(keys[j]) == null?"": records.get(i-1).get(keys[j]).toString();
                
                fieldValue=convertExcelExportData(type,keys[j],fieldValue,hmFieldOption);
                
                excelRowRecord.put(keys[j], fieldValue);
                
            }
            
            excelRecords.add(excelRowRecord);
        }
		
		ExcelUtil.createWorkBook("excel文件",excelRecords, hmFieldCnName,response);
		
	}
	
	public abstract String getExcelExportField(String type);
	
	public abstract String convertExcelExportData(String type,String fieldName,String cellValue,HashMap<String, LinkedHashMap<String, String>> hmFieldOption);
	
	/**
	 * 附件新增界面，此界面需要获取当前实体的附件列表
	 * @param crudObj
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{crudObj}/toExcelUpload",method={RequestMethod.GET})
	public String toExcelUpload(HttpServletRequest request) throws Exception {
		return "common/excelUpload";
	}
	
	/**
	 * 附件新增界面，此界面需要获取当前实体的附件列表（单）
	 * @param crudObj
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{crudObj}/toUpload/{id}",method={RequestMethod.GET})
	public String toUpload(@PathVariable("crudObj") String crudObj, @PathVariable("id") Integer id, ModelMap model, HttpServletRequest request) throws Exception {
		Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
		List objList = (List)basicCrudService.executeMethod("attach",
				"findByModelNameAndModelId", new Class[] {String.class,Integer.class}, new Object[] {crudObj,id});
		model.addAttribute("attachList", objList);
		model.addAttribute("crudObj", crudObj);
		model.addAttribute("crudObjId", id);
		return "common/fileUploadOne";
	}
	
	
	
	/**
	 * 上传文件,支持多附件
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value = "/{crudObj}/uploadify", method ={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String upload(@PathVariable("crudObj") String crudObj,HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IllegalAccessException{
		Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
		String attachMemo = request.getParameter("attachMemo");
		String subordinateModule = request.getParameter("subordinateModule");
		String jsonData = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  

	    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();  
	    // 文件保存路径
//	    String ctxPath=request.getSession().getServletContext().getRealPath("/")+File.separator+"uploadFiles"; 
	    String savePath=request.getSession().getServletContext().getRealPath("/")+"uploadFiles"; 
		savePath += File.separator + DateFormatUtil.getCurrentDate() + File.separator;
		// 创建文件夹
	    File file = new File(savePath);  
	    if (!file.exists()) {  
	        file.mkdirs();  
	    }  
	   
	    for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {  
	    	String responseStr="";
	    	// 上传文件 
	    	MultipartFile mf = entity.getValue();  
	    	String fileName = mf.getOriginalFilename();
	    	long fileSize = mf.getSize();
	    	String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	    	// 重命名文件
			String newFileName = DateFormatUtil.getFileDateTime() + "_" + new Random().nextInt(1000);
			File uploadFile = new File(savePath + newFileName); 
			
			Object obj = crudObjClazz.newInstance();
			ReflectUtil.invokeSet(obj, "fileName", fileName);
			ReflectUtil.invokeSet(obj, "extName", fileExt);
			
			ReflectUtil.invokeSet(obj, "fileSize", fileSize/1024);
			ReflectUtil.invokeSet(obj, "fielPath", savePath+ newFileName);
			ReflectUtil.invokeSet(obj, "memo", attachMemo);
			
			ReflectUtil.invokeSet(obj, "uploadDate", new Date());
			if(subordinateModule!=null && !subordinateModule.equals("")){
				ReflectUtil.invokeSet(obj, "subordinateModule", subordinateModule);
			}else{
				ReflectUtil.invokeSet(obj, "subordinateModule", "only");
			}
			basicCrudService.executeMethod("attach",
					"save", new Class[] {Object.class}, new Object[] {obj});
			
	     	try {
		    	FileCopyUtils.copy(mf.getBytes(), uploadFile);
				responseStr="上传成功";
		    } catch (IOException e) {
				responseStr="上传失败";
				e.printStackTrace();
		    }  
	     	jsonData = JacksonMapper.toJson(obj);
	  	} 
	    return jsonData;
	}
	
	/**
	 * 保存附件（单）
	 * @param crudObj e.g:user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{crudObj}/addAttach", method ={RequestMethod.POST})
	public String addAttach(@PathVariable("crudObj") String crudObj, HttpServletRequest request){
		Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
		String attachIds = request.getParameter("attachIds");
		String[] ids = attachIds.split(",");
		List<Integer> attachIdslist = new ArrayList<Integer>();
		for (int i=0;i<ids.length;i++){
			attachIdslist.add(Integer.parseInt(ids[i]));
		}
		String crudObjId = request.getParameter("crudObjId");
		//更新附件表中的关联实体名称和id
		Object objClazz = basicCrudService.executeMethod("attach",
				"updateModelNameAndModelId", new Class[] {String.class,Integer.class,List.class}, new Object[] {crudObj,Integer.parseInt(crudObjId),attachIdslist});
		
		return "redirect:/basicCrud/" + crudObj + "/toUpload/" + crudObjId;
	}
	
	
	/**
	 * 下载附件
	 * @param crudObj 必须为attach
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{crudObj}/downloadAttach")
	public String downloadAttach(@PathVariable("crudObj") String crudObj, HttpServletRequest request, HttpServletResponse response){
		String attachId = request.getParameter("attachId");
		Object objClazz = basicCrudService.executeMethod(crudObj,
				"findById", new Class[] {Integer.class}, new Object[] {Integer.parseInt(attachId)});
		if(objClazz==null){
			return null;
		}
		String fileName = (String) ReflectUtil.invokeGet(objClazz, "filename");
		String fullPath = (String) ReflectUtil.invokeGet(objClazz, "savefilename");
		try {
			downLoadData(fileName, fullPath, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除附件(逻辑)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{crudObj}/deleteAttach")
	public String deleteAttach(@PathVariable("crudObj") String crudObj, HttpServletRequest request){
		HashMap<String,Object> result=new HashMap<String,Object>();	
		String id = request.getParameter("id");
		Object objClazz = basicCrudService.executeMethod(crudObj,
				"removeById", new Class[] {Integer.class}, new Object[] {Integer.parseInt(id)});
		result.put("success", true);
		String jsonData = JacksonMapper.toJson(result);
		return jsonData;
	}
	
	/**
	 * 删除附件组(逻辑
	 * @param crudObj attach
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{crudObj}/deleteAttachGroup")
	public String deleteAttachGroup(@PathVariable("crudObj") String crudObj, HttpServletRequest request){
		HashMap<String,Object> result=new HashMap<String,Object>();	
		String subordinateModule = request.getParameter("subordinateModule");
		Object objClazz = basicCrudService.executeMethod(crudObj,
				"removeBySubordinateModule", new Class[] {String.class}, new Object[] {subordinateModule});
		result.put("success", true);
		String jsonData = JacksonMapper.toJson(result);
		return jsonData;
	}
	
	/**
	 * 附件新增界面，此界面需要获取当前实体的附件列表(多附件)
	 * @param crudObj
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{crudObj}/toUploadMore/{id}",method={RequestMethod.GET})
	public String toUploadMore(@PathVariable("crudObj") String crudObj, @PathVariable("id") String id, ModelMap model, HttpServletRequest request) throws Exception {
		Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
		List objList_one = (List)basicCrudService.executeMethod("attach",
				"findByModelNameAndModelIdAndSubordinateModuleAndRemoved", new Class[] {String.class,String.class,String.class,Integer.class}, new Object[] {crudObj,id,"before",0});
		List objList_two = (List)basicCrudService.executeMethod("attach",
				"findByModelNameAndModelIdAndSubordinateModuleAndRemoved", new Class[] {String.class,String.class,String.class,Integer.class}, new Object[] {crudObj,id,"after",0});
		model.addAttribute("attachList_one", objList_one);
		model.addAttribute("attachList_two", objList_two);
		model.addAttribute("crudObj", crudObj);
		model.addAttribute("crudObjId", id);
		return "common/fileUpload";
	}
	
	
	/**
	 * 保存附件（双）
	 * @param crudObj e.g:user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{crudObj}/addMoreAttach", method ={RequestMethod.POST})
	public String addMoreAttach(@PathVariable("crudObj") String crudObj, HttpServletRequest request){
		Class<?> crudObjClazz=objInfoService.getEntityClassByType(crudObj);
		
		//TODO 此处待修改，须与前台商量之后再写 ,该id为实体id
		String entityId = request.getParameter("entityId");
		HashMap<String,String> attachMap  = (HashMap<String,String>)JacksonMapper.readValue(entityId, HashMap.class);
		
		Set<String> keySet = attachMap.keySet();
		for(Iterator iterator = keySet.iterator();iterator.hasNext();){
			String key = (String)iterator.next();
			String value = (String) attachMap.get(key);
		}
		
		
		String attachIds_one = request.getParameter("attachIds_one");
		String attachIds_two = request.getParameter("attachIds_two");
		String[] ids_one = attachIds_one.split(",");
		List<Integer> attachIdslist_one = new ArrayList<Integer>();
		for (int i=0;i<ids_one.length;i++){
			attachIdslist_one.add(Integer.parseInt(ids_one[i]));
		}
		String[] ids_two = attachIds_two.split(",");
		List<Integer> attachIdslist_two = new ArrayList<Integer>();
		for (int i=0;i<ids_two.length;i++){
			attachIdslist_two.add(Integer.parseInt(ids_two[i]));
		}
		String crudObjId = request.getParameter("crudObjId");
		//更新附件表1中的关联实体名称和id
		Object objClazz = basicCrudService.executeMethod("attach",
				"updateModelNameAndModelIdAndModule", new Class[] {String.class,Integer.class,String.class,List.class}, new Object[] {crudObj,Integer.parseInt(crudObjId),"before",attachIdslist_one});
		//更新附件表2中的关联实体名称和id
		Object objClazz_two = basicCrudService.executeMethod("attach",
				"updateModelNameAndModelIdAndModule", new Class[] {String.class,Integer.class,String.class,List.class}, new Object[] {crudObj,Integer.parseInt(crudObjId),"after",attachIdslist_two});
		
		return "redirect:/basicCrud/" + crudObj + "/toUploadMore/" + crudObjId;
	}
	
	/**
	 * 从服务器获取文件，并下载
	 * @param fileName
	 * @param fullPath
	 * @param response
	 * @throws IOException
	 */
	private void downLoadData(String fileName, String fullPath, HttpServletResponse response)throws IOException{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
//			ByteArrayOutputStream os = new ByteArrayOutputStream();
//			byte[] content = os.toByteArray();
			InputStream is = new  FileInputStream(fullPath);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/x-msdownload;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
		
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			logger.error("Exception Throwable", e);
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
}
	
