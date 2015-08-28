package com.wonders.frame.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.ObjInfo;
import com.wonders.frame.core.model.bo.Rule;
import com.wonders.frame.core.model.bo.RuleType;
import com.wonders.frame.core.model.vo.NodeObj;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.RulePathVo;
import com.wonders.frame.core.model.vo.ReturnObjInfo.ErrorType;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.ObjInfoCache;
import com.wonders.frame.core.service.ObjInfoService;
import com.wonders.frame.core.utils.RulePathUtil;
import com.wonders.frame.core.service.RelationService;
import com.wonders.frame.core.service.RuleService;
import com.wonders.frame.core.service.RuleTypeService;

@Controller
@RequestMapping("/api/ruleType")
public class RuleTypeApiController extends AbstractSingleCrudController<RuleType>{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	BasicCrudService basicCrudService;

	@Resource
	RuleTypeService ruleTypeService;

	@Resource
	ObjInfoService objInfoService;

	@Resource
	RuleService ruleService;

	@Resource
	RelationService relationService;

	@RequestMapping(value = "/{id}/refresh", method = { RequestMethod.GET })
	@ResponseBody
	public ReturnObj refreshCache(@PathVariable("id") Integer id) {
		try {
			RulePathUtil.clear(id);
			return new ReturnObj(true);
		} catch (Exception e) {
			return new ReturnObj(e);
		}
		
	}
	
	@RequestMapping(value = "/{id}/rule/objs", method = { RequestMethod.GET })
	@ResponseBody
	public ReturnObj<List<ObjInfo>> findObjs(@PathVariable("id") Integer id,
			@RequestParam(value = "pobjType", required = false,defaultValue="") String pobjType) {
		try {
			
			List<Integer> objIdList = new ArrayList<Integer>();
			if(!pobjType.equals("")){
				List<Rule> rules=ruleService.findByRuleTypeIdAndPobjType(id, pobjType);
				
				if (rules == null||rules.size()==0) {
					return new ReturnObj<List<ObjInfo>>("No rule found with ruleTypeId:"+id+" and pobjType:"+pobjType);
				}
				
				for (Rule rule : rules) {
					objIdList.add(rule.getNobjId());
				}
			
			}else{
				RuleType ruleType = ruleTypeService.findById(id);
	
				if (ruleType==null ||ruleType.getObjIds() == null) {
					return new ReturnObj<List<ObjInfo>>("No ruleType found with id:"+id);
				}
				
				String[] objIds = ruleType.getObjIds().split(",");
				for (String objId : objIds) {
					objIdList.add(Integer.valueOf(objId));
				}
			}
			
			List<ObjInfo> objs = objInfoService.findByIds(objIdList);
			return new ReturnObj<List<ObjInfo>>(objs);
			
			
		} catch (Exception e) {	
			return new ReturnObj<List<ObjInfo>>(e);
		}

	}

	@RequestMapping(value = "/{id}/rule/paths", method = { RequestMethod.GET })
	@ResponseBody
	public ReturnObj<List<String>> findRulePath(@PathVariable("id") Integer ruleTypeId,
			@RequestParam(value = "pobjType", required = false,defaultValue="") String pobjType) {

		try {
			
			List<String> rulePaths=	ruleService.getRulePath(ruleTypeId);
			if(rulePaths!=null&&rulePaths.size()>0){
				if(!pobjType.equals("")){
					List<String> subRulePaths=new ArrayList<String>();
					for(String rulePath:rulePaths){
						String subRulePath="";
						if(rulePath.indexOf(pobjType+">")>=0){
							subRulePath=rulePath.substring(rulePath.indexOf(pobjType+">"));
							if(subRulePaths.indexOf(subRulePath)<0){
								subRulePaths.add(subRulePath);
								
							}
						}
						
					}
					rulePaths=subRulePaths;
				
				}
				
				return new ReturnObj<List<String>>(rulePaths);
				
			}else{
				return new ReturnObj<List<String>>(ErrorType.NULL);
			}
					

			
		} catch (Exception e) {
			return new ReturnObj<List<String>>(e);
		}
	}

	@RequestMapping(value = "/{id}/rule/paths/{path}", method = { RequestMethod.GET })
	@ResponseBody
	public ReturnObj matchRulePath(@PathVariable("id") Integer ruleTypeId,
			@PathVariable("path") String path) {
	
		try {
			if (path == null || path.equals("")
					|| (path.indexOf(">") < 0 && path.indexOf("<") < 0)) {
				return new ReturnObj(false);
			}
			RulePathVo rp = RulePathUtil.getRulePath(path);
			List<String> objList = rp.getObjList();
			List<String> opList = rp.getOpList();
			
			List<String> rulePaths=ruleService.getRulePath(ruleTypeId);
			
			if(rulePaths==null){
				return new ReturnObj("no rule exist");
			}
			boolean isMatched=true;
			String beCheckedPath="";
			for(int i=0;i<opList.size()&&isMatched;i++){
				StringBuffer rule=new StringBuffer();
				
				if(opList.get(i).equals(">")){
					rule.append(objList.get(i)).append(opList.get(i)).append(objList.get(i+1));
				}else{
					rule.append(objList.get(i+1)).append(">").append(objList.get(i));
				}
				beCheckedPath=rule.toString();
				boolean isExist=false;
				for(String s:rulePaths){
					if(s.indexOf(beCheckedPath)>=0){
						isExist=true;
						break;
					}
				}
				isMatched=isExist;
										
			}
			
			if(isMatched){			
				return new ReturnObj(true);
			}else{				
				return new ReturnObj("the path ("+beCheckedPath+") not matched the rule");
			}
			//returnObj.setData(ro.getData());
		} catch (Exception e) {
			return new ReturnObj("has error when get relation obj by path "+path);
		}
	}

	

	/**
	 * "objType_objId>(<)objType_objId...，其中关联符“>”表示前面的对象是后面对象的上级节点；关联符“<”表示前面的对象是后面对象的下级节
	 * 点
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}/relation/paths/{path}", method = { RequestMethod.GET })
	@ResponseBody
	public ReturnObj<List<String>> findRelationPaths(@PathVariable("id") String ruleTypeId,
			@PathVariable("path") String path) {
			return relationService.findRelationPaths(ruleTypeId, path);

	}

	@RequestMapping(value = "/{id}/relation/tree/{path}", method = { RequestMethod.GET })
	@ResponseBody
	public ReturnObj<HashMap<String, List<NodeObj>>> findRelationTree(@PathVariable("id") String ruleTypeId,
			@PathVariable("path") String path) {
		return relationService.findRelationTree(ruleTypeId, path);		
	}
	
	@RequestMapping(value = "/{id}/relation/objs/{path}", method = { RequestMethod.GET })
	@ResponseBody
	public ReturnObj<List<IDefaultModel>> findRelationObjs(@PathVariable("id") String ruleTypeId,
			@PathVariable("path") String path) {
		try {
			ReturnObj<List<String>> ro=relationService.findRelationPaths(ruleTypeId, path);
			if(!ro.getInfo().getSuccess()){
				return new ReturnObj<List<IDefaultModel>>(ro.getInfo().getError());
			}
			List<String> pathResults =ro.getData();
			
			List<Integer> objIds = new ArrayList<Integer>();

			if (pathResults != null && pathResults.size() > 0) {
				for (String pathResult : pathResults) {
					String[] tmp = pathResult.split("_");
					objIds.add(Integer.valueOf(tmp[tmp.length - 1]));
				}
			}
			RulePathVo rp = RulePathUtil.getRulePath(path);
			List<String> objList = rp.getObjList();
			String objName = objList.get(objList.size() - 1);

			Class<?> clazz =ObjInfoCache.getObjectClass(objName);
			return basicCrudService.findByIds(clazz, objIds);
			
		} catch (Exception e) {
			return new ReturnObj<List<IDefaultModel>>(e);
		}
	}

}
