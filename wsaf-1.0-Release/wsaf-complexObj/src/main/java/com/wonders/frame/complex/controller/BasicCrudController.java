/** 
* @Title: BasicCOntroller.java 
* @Package com.wonders.frame.core.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.complex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wonders.frame.core.controller.AbstractGeneralCrudController;

/** 
 * @ClassName: BasicCrudController 
 * @Description: 提供基本的增删改查的controller 
 */
@Controller
@RequestMapping("/basicCrud")
//public class BasicCrudController extends AbstractSimpleCrudJspController{
public class BasicCrudController extends AbstractGeneralCrudController{
	
//	@Resource
//	GenericService genericService;
//	@Resource
//	ObjInfoService objInfoService;
//	
//	@Override
//	public String convertExcelExportData(String type,String fieldName,String cellValue,HashMap<String, LinkedHashMap<String, String>> hmFieldOption){
//		LinkedHashMap<String,String> hmOption=hmFieldOption.get(fieldName);
//		
//		StringBuffer newValue=new StringBuffer("");
//		
//		if(fieldName.equalsIgnoreCase("line") && !cellValue.equals("")){				
//			
//			String[] values=cellValue.split(",");
//			for(String value:values){
//				newValue.append(hmOption.get(value)).append("/");
//			}
//			
//			cellValue=newValue.toString().substring(0,newValue.length()-1);
//			
//		}else if(fieldName.equalsIgnoreCase("resourceRequirement") && !cellValue.equals("")){
//
//			HashMap<String,String> cellValueMap  = (HashMap<String,String>)JacksonMapper.readValue(cellValue, HashMap.class);
//			
//			Set<String> keySet = cellValueMap.keySet();
//			
//			for(Iterator iterator = keySet.iterator();iterator.hasNext();){
//			
//				String key = (String)iterator.next();
//				String value = (String) cellValueMap.get(key);
//				
//				HashMap<String,String> subValueMap  = (HashMap<String,String>)JacksonMapper.readValue(value, HashMap.class);
//				
//				Set<String> subValuekeySet = subValueMap.keySet();
//				for(Iterator subValueIterator = subValuekeySet.iterator();subValueIterator.hasNext();){
//					String subKey = (String)subValueIterator.next();
//					String subValue = (String) subValueMap.get(subKey);
//					
//					newValue.append(hmOption.get(key)).append(":").append(subKey).append("[").append(subValue).append("]").append("/");
//				}
//			}
//			
//			cellValue=newValue.toString().substring(0,newValue.length()-1);
//
//
//		}else if(hmOption!=null && !cellValue.equals("")){
//			cellValue = hmOption.get(cellValue);
//		}
//		
//		return cellValue;
//	}
//
//	@Override
//	public String getExcelExportField(String type) {	
//			return "year,projectName,mainContent,yearTarget,totalInvestEstimate,projectType,resourceRequirement,securityLevel,planStartDate,planEndDate,remarks";
//		
//	}
	
	
}
	
