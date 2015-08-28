/** 
 * @Title: BasicCOntroller.java 
 * @Package com.wonders.frame.core.controller 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author lushuaifeng
 * @version V1.0 
 */
package com.wonders.frame.core.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JavaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.ImportConfigLog;
import com.wonders.frame.core.model.bo.ImportConfig;
import com.wonders.frame.core.model.vo.FieldProperty;
import com.wonders.frame.core.model.vo.ObjectInfoVo;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.ImportConfigService;
import com.wonders.frame.core.service.ObjInfoCache;
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.utils.CustomDateTimeFormat;
import com.wonders.frame.core.utils.DateFormatUtil;
import com.wonders.frame.core.utils.ExcelUtil;
import com.wonders.frame.core.utils.JacksonMapper;
import com.wonders.frame.core.utils.ReflectUtil;

/**
 * @ClassName: AbstractSingleCrudieController
 * @Description: 提供简单的增删改查及excel导入导出的抽象controller类
 */

public abstract class AbstractSingleCrudieController<T> extends AbstractSingleCrudController<T> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private BasicCrudService basicCrudService;
	
	@Resource
	private ImportConfigService importConfigService;
	

	// 导出excel
	@RequestMapping(value = "/excel/{type}", method = RequestMethod.GET)
	@ResponseBody
	public void exportExcel(@PathVariable("type") String type,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("export excel");
		ObjectInfoVo objectInfo=ObjInfoCache.getObjectInfo(getEntityClass());

		ReturnObj<List<IDefaultModel>> ro = basicCrudService.find(objectInfo.getClazz(), request);

		JavaType javaType = JacksonMapper.getCollectionType(ArrayList.class,
				HashMap.class);
		List<HashMap> records = (List<HashMap>) JacksonMapper.convert(ro.getData(), javaType);

		String exportFields = getExcelExportField(type);

		List<FieldProperty> FieldProperties = ReflectUtil.getFieldProperties(
				objectInfo.getClazz(), exportFields, true);
		
		LinkedHashMap<String, String> hmFieldCnName = new LinkedHashMap<String, String>();
		
		HashMap<String, LinkedHashMap<String, String>> hmFieldOption = new HashMap<String, LinkedHashMap<String, String>>();
		//获取FieldProperty的codes
		HashMap<String, HashMap<String,LinkedHashMap<String,String>>> hmFieldCodes = new HashMap<String, HashMap<String,LinkedHashMap<String,String>>>();
		
		for (FieldProperty fieldProperty : FieldProperties) {
			
			hmFieldCnName.put(fieldProperty.getPath(), fieldProperty.getName());
			hmFieldOption.put(fieldProperty.getPath(),fieldProperty.getOption());
			//将获取的codes设置到hmFieldCodes
			hmFieldCodes.put(fieldProperty.getPath(), fieldProperty.getCodes());
		}
		
		//根据要求的输出字段顺序重新排序
		sortExcelExportField(hmFieldCnName,exportFields);

		// 数据转换
		List<HashMap<String, String>> excelRecords = new ArrayList<HashMap<String, String>>();
		
		String[] keys = hmFieldCnName.keySet().toArray(new String[0]);
		
		for (int i = 1; i <= records.size(); i++) {
			
			HashMap<String, String> excelRowRecord = new HashMap<String, String>();

			for (int j = 0; j < keys.length; j++) {

				String fieldValue = records.get(i - 1).get(keys[j]) == null ? ""
						: records.get(i - 1).get(keys[j]).toString();
          
				fieldValue = convertExcelExportData(type, keys[j], fieldValue,hmFieldOption,hmFieldCodes);

				excelRowRecord.put(keys[j], fieldValue);

			}

			excelRecords.add(excelRowRecord);
		}

		ExcelUtil.createWorkBook("excel文件", excelRecords, hmFieldCnName,
				response);
	}
	//excel输出字段排序
	public void sortExcelExportField(LinkedHashMap<String, String> lhmFieldName,String sortStr){
		LinkedHashMap<String, String> tmp=new LinkedHashMap<String, String>();
		String[] sortFields=sortStr.split(",");
		for(int k=0;k<sortFields.length;k++){
			;
			if(lhmFieldName.containsKey(sortFields[k])){
				tmp.put(sortFields[k], lhmFieldName.get(lhmFieldName));
			}
		}
		lhmFieldName.clear();
		lhmFieldName=tmp;
		
	}
	//定义Excel输出字段
	public abstract String getExcelExportField(String type);
	
	//转化Excel输出内容（默认字典项转换）
	public String convertExcelExportData(String type,String fieldName, String cellValue,HashMap<String, LinkedHashMap<String, String>> hmFieldOption,HashMap<String, HashMap<String,LinkedHashMap<String,String>>> hmFieldCodes){
		LinkedHashMap<String,String> hmOption=hmFieldOption.get(fieldName);
		//取出hmCodes
		HashMap<String,LinkedHashMap<String,String>> hmCodes=hmFieldCodes.get(fieldName);
			
		if(hmOption!=null && !cellValue.equals("")){
			cellValue = hmOption.get(cellValue);
		}else if(hmCodes!=null && !cellValue.equals("")){//二级选择菜单
			//HashMap<String,LinkedHashMap<String,String>> hmCodes=hmFieldCodes.get(fieldName);
			boolean flag=false;//标志位
			for(String string:hmCodes.keySet()){
				for(String str:hmCodes.get(string).keySet()){
					if(cellValue.equals(str)){
						cellValue=hmCodes.get(string).get(str);
						flag=true;
						break;
					}
				}
				if(flag){
					break;
				}
			}
		}
		return cellValue;
	}


	// 导入Excel
	@RequestMapping(value = "/excel/{type}", method = RequestMethod.POST)
	@ResponseBody
	public ReturnObj<HashMap<String,Object>> importExcel(@PathVariable("type") String type,
			HttpServletRequest request) {
		logger.debug("import excel");

		HashMap<String,Object> importResult=new HashMap<String,Object>();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件保存路径
			String savePath = request.getSession().getServletContext().getRealPath("/")+ "uploadFiles";
			savePath += File.separator + DateFormatUtil.getCurrentDate()+ File.separator;
			// 创建文件夹
			File file = new File(savePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				// 上传文件
				MultipartFile mf = entity.getValue();

				// 获取文件后缀
				String ext = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
				// 重命名文件
				String newFileName = DateFormatUtil.getFileDateTime() + "_"+ new Random().nextInt(1000) + ext;
				File uploadFile = new File(savePath + newFileName);
				FileCopyUtils.copy(mf.getBytes(), uploadFile);

				// 创建导入Excel数据操作日志对象
				ImportConfigLog importLog = new ImportConfigLog();
				importLog.setOriginalFilename(mf.getOriginalFilename().substring(0,mf.getOriginalFilename().lastIndexOf(".")));
				importLog.setSaveFilename(newFileName.substring(0,newFileName.lastIndexOf(".")));
				importLog.setFileType(ext);
				importLog.setSavePath(savePath);
				importLog.setImportTime(new Date(System.currentTimeMillis()));
				if (mf != null) {
					
					List<Object> datas = importData(uploadFile,type,request);
					if (datas != null) {// 文件导入数据成功
						importLog.setFlag("true");
						importLog.setRecordNum(datas.size());
						importResult.put(mf.getOriginalFilename(),datas);
					} else {// 文件导入数据失败
						importLog.setFlag("false");
						importLog.setRecordNum(0);
						importResult.put(mf.getOriginalFilename(),null);
					}
					// 保存本次导入Excel数据操作日志
					importLog=importConfigService.saveLog(importLog);
					//ReflectUtil.executeMethod("importConfigLog", "save",new Class[] { Object.class },new Object[] { importLog });
				}
			}

				return new ReturnObj<HashMap<String,Object>>(importResult);

		} catch (IOException e) {
			return new ReturnObj<HashMap<String,Object>>(e);
		}
	}

	
	public List<Object> importData(File file, String type,HttpServletRequest request) {
		Long start = System.currentTimeMillis();
		List<Object> datas = new ArrayList<Object>();
		List<String[]> list = ExcelUtil.getExcelData(file);
		if (list == null || list.size() < 1) {
			return datas;
		}
		try {
			ObjectInfoVo objectInfo=ObjInfoCache.getObjectInfo(getEntityClass());
			Class<?> crudObjClazz = objectInfo.getClazz();
			// 根据type、实体对象查询Excel导入数据配置项
			List<ImportConfig> queryObj=importConfigService.findByEntityAndType(getEntityName(), type);
//			List<ImportConfig> queryObj = (List<ImportConfig>) ReflectUtil.executeMethod("importConfig", "findByEntityAndType",
//					new Class[] { String.class,String.class }, new Object[] { getEntityName(),type });
			if (queryObj == null) {
				return null;
			}
			
			// 根据type查询Excel导入数据配置，该配置下的Excel只能是存在标题、不存在标题其中一种
			// 判断是否存在标题
			Integer hasTitle = (queryObj.get(0)).getHasTitle();
			if (hasTitle == 1) {// 存在标题
				for (int i = 1; i < list.size(); i++) {
					
					boolean isEmpty=isNotEmpty(list.get(i));
					if(isEmpty){
						continue;
					}
					
					Object obj = crudObjClazz.newInstance();
					for (int j = 0; j < queryObj.size(); j++) {
						String entityField = (String) ReflectUtil.invokeGet(queryObj.get(j), "entityField");
						String excelField = (String) ReflectUtil.invokeGet(queryObj.get(j), "excelField");
						// 遍历标题集合
						int flag = 0;
						for (int k = 0; k < list.get(0).length; k++) {
							if (excelField.equals(list.get(0)[k])) {
								flag = k;
								break;
							}
						}
						if (!list.get(i)[flag].equals("")) {
							Field field = crudObjClazz.getDeclaredField(entityField);
							String fieldExcelData = list.get(i)[flag];
							setData(type,field ,fieldExcelData,entityField,crudObjClazz,obj);
						}
					}
					ReflectUtil.executeMethod(getEntityClass(), "save",new Class[] { Object.class }, new Object[] { obj });
					datas.add(obj);
				}
			} else {// 不存在标题,配置中列必须从1开始
				for (int i = 0; i < list.size(); i++) {
					
					boolean isEmpty=isNotEmpty(list.get(i));
					if(isEmpty){
						continue;
					}
					
					Object obj = crudObjClazz.newInstance();
					for (int j = 0; j < queryObj.size(); j++) {
						String entityField = (String) ReflectUtil.invokeGet(queryObj.get(j), "entityField");
						String excelField = (String) ReflectUtil.invokeGet(queryObj.get(j), "excelField");
                        Integer column=Integer.valueOf(excelField);
						if (!list.get(i)[column - 1].equals("")) {
							Field field = crudObjClazz.getDeclaredField(entityField);
							String fieldExcelData = list.get(i)[column - 1];
							setData(type,field ,fieldExcelData,entityField,crudObjClazz,obj);
						}
					}
					ReflectUtil.executeMethod(getEntityClass(), "save",new Class[] { Object.class }, new Object[] { obj });
					datas.add(obj);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("error", e);
		}
		logger.debug("[import]--Owner total：" + datas.size());
		logger.debug("[import]--parse excel use time:"+ (System.currentTimeMillis() - start) + " ms");
		return datas;
	}
	
	/**
	 * 判断Excel当前行是否是空行
	 * @param obj
	 * @return
	 */
	private boolean isNotEmpty(Object[] obj){
		int length=0;
		for(int m=0;m<obj.length;m++){
			if(obj[m]==null||"".equals(obj[m])){
				length++;
			}
		}
		if(length==obj.length){
			return true;//空行
		}else{
			return false;//非空行
		}
	}
	
	/**
	 * @author taoweiwei
	 * 给指定对象的指定字段设置Excel数据
	 * @param field
	 * @param fieldExcelData
	 * @param entityField
	 * @param crudObjClazz
	 * @param obj
	 */
	@SuppressWarnings("unused")
	private void setData(String type,Field field ,String fieldExcelData,String entityField,Class<?> crudObjClazz,Object obj){
		/**
		 * 判断该字段上是否存在ShowInView注解，
		 * 一、存在该注解，则判断该选项是否是字典项（即是否是一个选择项）；
		 * 1、若是一个字典项，则取出其字典项，找 出excel中数据对应的字典项code保存到数据库；
		 * 2、若不是一个字典项，则将excel中数据值保存到数据库
		 * 二、不存在该注解，则将excel中值存到数据库
		 */

		ShowInView showInView = field.getAnnotation(ShowInView.class);
		if (showInView != null) {// 存在ShowInView注解
			String ccateType = showInView.ccateType();
			if (!"".equals(ccateType)) {// 存在字典项
				List<FieldProperty> fieldProperties = ReflectUtil.getFieldProperties(crudObjClazz,field.getName(), true);
				FieldProperty fieldProperty = fieldProperties.get(0);
				LinkedHashMap<String,String> option = fieldProperty.getOption();
				HashMap<String,LinkedHashMap<String,String>> code=fieldProperty.getCodes();
				fieldExcelData=convertExcelImportData(type,entityField ,fieldExcelData,option,code);
			}else{
				fieldExcelData=convertExcelImportData(type,entityField ,fieldExcelData,null,null);
			}
		}
		
		String returnType = field.getType().getName().toString();
		if (returnType.contains("Long")) {
			ReflectUtil.invokeSet(obj, entityField,Long.valueOf(fieldExcelData));
		} else if (returnType.contains("Integer")) {
			ReflectUtil.invokeSet(obj, entityField,Integer.valueOf(fieldExcelData));
		} else if (returnType.contains("Date")) {
			ReflectUtil.invokeSet(obj, entityField,DateFormatUtil.timeParse("yyyy-MM-dd HH:mm:ss",fieldExcelData));
		} else if (returnType.contains("Double")) {
			ReflectUtil.invokeSet(obj, entityField,Double.valueOf(fieldExcelData));
		} else if (returnType.contains("Float")) {
			ReflectUtil.invokeSet(obj, entityField,Float.valueOf(fieldExcelData));
		} else if (returnType.contains("Boolean")) {
			ReflectUtil.invokeSet(obj, entityField,Boolean.valueOf(fieldExcelData));
		} else {
			ReflectUtil.invokeSet(obj, entityField,fieldExcelData);
		}
	}


	public String convertExcelImportData(String type,String fieldName ,String fieldValue,LinkedHashMap<String,String> option,HashMap<String,LinkedHashMap<String,String>> code){
		fieldValue=fieldValue.trim();

		if(option!=null && option.size()>0){
			fieldValue=getOptionKey(fieldValue,option);
		}else if(code!=null && code.size()>0){
			fieldValue=getCodeKey(fieldValue,code);
		}
		
		return fieldValue;
		
	}
	
	/**
	 * 根据传入的值，获取对应option中的key
	 * @param fieldValue
	 * @param option
	 * @return
	 */
	private String getOptionKey(String fieldValue,LinkedHashMap<String,String> option){
		for (String key : option.keySet()) {
			if (fieldValue.equals(option.get(key))) {
				fieldValue = key;
				break;
			}
		}
		return fieldValue;
	}
	
	/**
	 * 根据传入的值，获取对应code中的key
	 * @param fieldValue
	 * @param code
	 * @return
	 */
	private String getCodeKey(String fieldValue,HashMap<String,LinkedHashMap<String,String>> code){
		for(String key:code.keySet()){
			LinkedHashMap<String,String> lhMap=code.get(key);
			for(String key1:lhMap.keySet()){
				if(fieldValue.equalsIgnoreCase(lhMap.get(key1))){
					fieldValue=key1;
					break;
				}
			}
		}
		return fieldValue;
	}
	
}
