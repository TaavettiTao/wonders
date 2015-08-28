package com.wonders.frame.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wonders.frame.core.model.bo.Rule;

@Controller
@RequestMapping("/api/rule")
public class RuleApiController extends AbstractSingleCrudController<Rule>{
	
//	@Resource
//	RuleService ruleService;
//	   
//	@Resource
//	private RelationService relationService;
//	
//	@Resource
//	private RelationObjectService relationObjectService;
//	
//	
//	@Resource
//	RuleTypeService ruleTypeService;
//	/**
//	 * @param rulePath 规则
//	 * @param type
//	 * @return
//	 */
////	@RequestMapping(value="/findRule/{type}/{pid}",method={RequestMethod.POST,RequestMethod.GET})
//	@RequestMapping(value="/{type}/{pid}",method={RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public ReturnObj findRule(@PathVariable("type") String type, @PathVariable("pid") Integer pid){
//		ReturnObj returnObj=new ReturnObj();
//		try {
//			List<String> rules = ruleService.findRuleByPIdAndType(pid, type);
//			returnObj.addInfo("success", true);
//			returnObj.setData(getRules(rules));
//		} catch (Exception e) {
//			e.printStackTrace();
//			returnObj.addInfo("success", false);
//		}
//		return returnObj;
//	}	
//
//	public  HashMap<Integer, String> getRules(List<String> list){
//        Collections.sort (list);
//        HashMap<Integer, String> map = new HashMap<Integer, String> ();
//        int key = 0;
//        for ( int i = 0; i < list.size (); i++ ){
//            String temp = list.get (i);
//            if (null == map.get (key)) {
//                map.put (key, temp);
//            }else if (temp.indexOf (map.get (key)) != -1){
//                map.put (key, temp);
//            }else{
//                key++;
//                map.put (key, temp);
//            }
//        }
//        System.out.println (map.get(key));
//        return map;
//	}
//	
//	/**
//	 * 根据规则类型id和父节点来获取相关objs
//	 * @param pobjType
//	 * @param ruleTypeId
//	 * @return
//	 */
//	@RequestMapping("/getRuleObjs")
//	@ResponseBody
//	public ReturnObj getRuleObjs(String pobjType,@RequestParam String ruleTypeId){
//		ReturnObj returnObj=new ReturnObj();
//		List<ObjInfo> objInfos=new ArrayList<ObjInfo>();
//		Integer[] intIds = null;
//		try {
//			if(pobjType==null || "".equals(pobjType)){
//				
//				RuleType ruleType =ruleTypeService.findById(Integer.valueOf(ruleTypeId));
//				String[] tempObjIds = ruleType.getObjIds().split(",");
//				intIds = new Integer[tempObjIds.length];
//				for(int i=0;i<tempObjIds.length;i++)
//				{
//					intIds[i] = Integer.parseInt(tempObjIds[i]);
//				}
//				
//			}else{
//				List<Rule> relationRules=this.ruleService.findByPtypeAndRuleTypeId(pobjType, Integer.valueOf(ruleTypeId));
//				intIds =  new Integer[relationRules.size()];
//				for(int i=0; i<relationRules.size(); i++){
//					intIds[i] = relationRules.get(i).getNobjId();
//				}
//			}
//			objInfos=this.objInfoService.findByIdIn(intIds);
//			returnObj.addInfo("success", true);
//			returnObj.setData(objInfos);
//		} catch (Exception e) {
//			e.printStackTrace();
//			returnObj.addInfo("success", false);
//		}
//		return returnObj;
//	}
//	   
//	   
//		//@RequestMapping(value="/findObjectsByPath/{path}",method={RequestMethod.POST,RequestMethod.GET})
//	   @RequestMapping(value="/objects/{path}",method={RequestMethod.GET})
//		@ResponseBody
//		public ReturnObj findObjectsByPath(@PathVariable("path") String path,Integer ruleTypeId){
//			ReturnObj returnObj=new ReturnObj();
//			try {
//				if(path==null || "".equals(path) || (path.indexOf("<")<0 && path.indexOf(">")<0)){
//					returnObj.addInfo("success", false);
//					return returnObj;
//				}
//				
//				HashMap<String,List<String>> hmPath=relationService.convertPath(path);
//				List<String> pathResults=relationService.getPathResult(hmPath,ruleTypeId);
//				
//				if(pathResults==null || pathResults.size()<=0){
//					returnObj.addInfo("success", true);
//					returnObj.setData("");
//					return returnObj;
//				}
//				
//				String nodeObj="";//记录查询的节点对象
//				Set<String> ids=new HashSet<String>();//记录查询节点对象的id集合
//				
//	           if(pathResults.get(0).indexOf("<")>0 && pathResults.get(0).indexOf(">")>0){
//	           	//"organ_1>role_101<user_189"
//	           	//opList=[>, <], objList=[organ_1, role_101, user_189]
//	           	for(String pathStr:pathResults){
//	           		HashMap<String,List<String>> hmPathStr=relationService.convertPath(pathStr);
//	                   List<String> opList=hmPathStr.get("opList");
//	                   for(int i=0;i<opList.size()-1;i++){
//	                   	if((opList.get(i).equals(">")&&opList.get(i+1).equals("<"))
//	                   			||(opList.get(i).equals("<")&&opList.get(i+1).equals(">"))){
//	                   		String[] nodeData=hmPathStr.get("objList").get(i+1).split("_");
//	                   		if("".equals(nodeObj)){
//	                   			nodeObj=nodeData[0];
//	                   		}
//	                   		ids.add(nodeData[1]);
//	                   	}
//	                   }
//	           	}
//	           }else if(pathResults.get(0).indexOf("<")>0){
//	           	//"privilege_5<role_101<user_187",
//	           	for(String pathStr:pathResults){
//	           		String[] nodeData=pathStr.split("<")[0].split("_");
//	           		if("".equals(nodeObj)){
//	           			nodeObj=nodeData[0];
//	           		}
//	           		ids.add(nodeData[1]);
//	           	}
//	           }else if(pathResults.get(0).indexOf(">")>0){
//	           	//"user_187>role_101>privilege_5"
//	           	for(String pathStr:pathResults){
//	           		String[] pathResult=pathStr.split(">");
//	           		String[] nodeData=pathResult[pathResult.length-1].split("_");
//	           		if("".equals(nodeObj)){
//	           			nodeObj=nodeData[0];
//	           		}
//	           		ids.add(nodeData[1]);
//	           	}
//	           }
//	           
//	           String nodeIds = ids.toString().replaceFirst("^\\[", "").replaceAll("\\]$", "");
//	           List<Object> objs=relationObjectService.findObjectsByNextNodeIds(nodeObj, nodeIds);
//	           returnObj.addInfo("success", true);
//	           returnObj.setData(objs);
//			} catch (Exception e) {
//				e.printStackTrace();
//				returnObj.addInfo("success", false);
//			}
//			return returnObj;
//		}	
//		
//		/**
//		 * @param rulePath 规则
//		 * @param ruleTypeId
//		 * @return
//		 */
//		//@RequestMapping(value="/findObjByRule/{rulePath}",method={RequestMethod.POST,RequestMethod.GET})
//		@RequestMapping(value="/obj/{rulePath}",method={RequestMethod.GET})
//		@ResponseBody
//		public ReturnObj findObjByRule(@PathVariable("rulePath") String rulePath,Integer ruleTypeId){
//			ReturnObj returnObj=new ReturnObj();
//			try {
//				if(rulePath==null || "".equals(rulePath) || (rulePath.indexOf("<")<0 && rulePath.indexOf(">")<0)){
//					returnObj.addInfo("success", false);
//					return returnObj;
//				}
//				//对rulePath进行切割
//				String firstOper;
//				if((rulePath.indexOf("<")<0 || rulePath.indexOf(">")<0)){
//					firstOper = (rulePath.indexOf("<")<rulePath.indexOf(">"))?">":"<";	
//				}else{
//					firstOper = (rulePath.indexOf("<")<rulePath.indexOf(">"))?"<":">";	
//				}
//				String[] firstNodeData=rulePath.substring(0,rulePath.indexOf(firstOper)).split("_");
//				
//				if(firstNodeData.length>1){
//					boolean isNum = firstNodeData[1].matches("[0-9]+");
//					if(!isNum){
//						List firtObj = relationObjectService.findObjsByFieldName(firstNodeData[0], firstNodeData[1]);
//						if(firtObj.size()!=0){
//							int id = (Integer) ReflectUtil.invokeGet(firtObj.get(0), "id");
//							rulePath = rulePath.replace(firstNodeData[1], String.valueOf(id));
//						}else{
//							returnObj.addInfo("success", false);
//							return returnObj;
//						}
//						
//					}
//				}
//				
//				HashMap<String,List<String>> hmPath=relationService.convertPath(rulePath);
//				List<String> pathResults=relationService.getPathResult(hmPath,ruleTypeId);
//				
//				if(pathResults==null || pathResults.size()<=0){
//					returnObj.addInfo("success", true);
//					returnObj.setData("");
//					return returnObj;
//				}
//				
//				String nodeObj="";//记录查询的节点对象
//				Set<String> ids=new HashSet<String>();//记录查询节点对象的id集合
//				
//				for(String pathStr:pathResults){
//					String lastOper = (pathStr.lastIndexOf("<")<pathStr.lastIndexOf(">"))?">":"<";
//					String[] nodeData=pathStr.substring(pathStr.lastIndexOf(lastOper)+1).split("_");
//					if("".equals(nodeObj)){
//						nodeObj=nodeData[0];
//					}
//					ids.add(nodeData[1]);
//				}
//				String nodeIds = ids.toString().replaceFirst("^\\[", "").replaceAll("\\]$", "");
//				List<Object> objs=relationObjectService.findObjectsByNextNodeIds(nodeObj, nodeIds);
//				returnObj.addInfo("success", true);
//				returnObj.setData(objs);
//			} catch (Exception e) {
//				e.printStackTrace();
//				returnObj.addInfo("success", false);
//			}
//			return returnObj;
//		}	
//	/*
//		@RequestMapping("/findDepts")
//		@ResponseBody 
//		public ReturnObj findDeptsByUser(String username){
//			ReturnObj returnObj=new ReturnObj();
//			try {
//				List<Object> depts=relationObjectService.findDeptsByUsername(username);
//				returnObj.addInfo("success", true);
//				result.put("result", depts);
//			} catch (Exception e) {
//				e.printStackTrace();
//				returnObj.addInfo("success", false);
//			}
//			return returnObj;
//		}
//		
//		@RequestMapping("/findPrivileges")
//		@ResponseBody
//		public ReturnObj findPrililegesByUser(String username){
//			ReturnObj returnObj=new ReturnObj();
//			try {
//				List<Object> prilileges=relationObjectService.findPrivilegesByUsername(username);
//				returnObj.addInfo("success", true);
//				result.put("result", prilileges);
//			} catch (Exception e) {
//				e.printStackTrace();
//				returnObj.addInfo("success", false);
//			}
//			return returnObj;
//		}
//		*/
//		
}
