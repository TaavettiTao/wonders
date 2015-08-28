/** 
 * @Title: UserEditController.java 
 * @Package com.wonders.frame.core.controller 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author lushuaifeng
 * @version V1.0 
 */
package com.wonders.frame.core.controller;

import java.util.HashMap;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.service.RelationService;

@Controller
@RequestMapping("/api/relation")
public class RelationApiController extends
		AbstractSingleCrudController<Relation> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	RelationService relationService;

	@RequestMapping(value = "/bind", method = { RequestMethod.POST })
	@ResponseBody
	public ReturnObj saveBind(@RequestParam("pId") String pId,
			@RequestParam("pType") String pType,
			@RequestParam("nId") String nId,
			@RequestParam("nType") String nType,
			@RequestParam("ruleTypeId") String ruleTypeId) {
		try {
			String[] pIds = pId.split(",");
			String[] nIds = nId.split(",");
			for (String bindPid : pIds) {
				for (String bindNid : nIds) {
					HashMap<String, String> queryParams = new HashMap<String, String>();
					queryParams.put("ptype", pType);
					queryParams.put("pid", bindPid);
					queryParams.put("ntype", nType);
					queryParams.put("nid", bindNid);
					queryParams.put("ruleTypeId", ruleTypeId);
					relationService.bind(queryParams);
				}
			}
			return new ReturnObj(true);
		} catch (Exception e) {
			return new ReturnObj(e);
		}

	}
	
	@RequestMapping(value = "/rebind", method = { RequestMethod.POST })
	@ResponseBody
	public ReturnObj updateBind(@RequestParam("pId") String pId,
			@RequestParam("pType") String pType,
			@RequestParam("nId") String nId,
			@RequestParam("nType") String nType,
			@RequestParam("ruleTypeId") String ruleTypeId,
			@RequestParam(value = "pTypeNew", required = false, defaultValue = "") String pTypeNew,
			@RequestParam(value = "pIdNew", required = false, defaultValue = "") String pIdNew,
			@RequestParam(value = "nTypeNew", required = false, defaultValue = "") String nTypeNew,
			@RequestParam(value = "nIdNew", required = false, defaultValue = "") String nIdNew) {
		try {

			HashMap<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("ptype", pType);
			queryParams.put("pid", pId);
			queryParams.put("ntype", nType);
			queryParams.put("nid", nId);
			queryParams.put("ruleTypeId", ruleTypeId);
			
			HashMap<String, String> newParams = new HashMap<String, String>();
			
			if(!nTypeNew.equals("")&&!nIdNew.equals("")){
				
				newParams.put("ntypeNew", nTypeNew);
				newParams.put("nidNew", nIdNew);

				relationService.rebind(queryParams,newParams);
				
			}else if(!pTypeNew.equals("")&&!pIdNew.equals("")){
				
				newParams.put("ptypeNew", pTypeNew);
				newParams.put("pidNew", pIdNew);
				
				relationService.rebind(queryParams,newParams);
			}else{
				return new ReturnObj("Please provide new object values");
			}

			return new ReturnObj(true);
		} catch (Exception e) {
			return new ReturnObj(e);
		}

	}

	@RequestMapping(value = "/unbind", method = { RequestMethod.POST})
	@ResponseBody
	public ReturnObj removeBind(
			@RequestParam(value = "pId", required = false, defaultValue = "") String pId,
			@RequestParam(value = "pType", required = false, defaultValue = "") String pType,
			@RequestParam(value = "nId", required = false, defaultValue = "") String nId,
			@RequestParam(value = "nType", required = false, defaultValue = "") String nType,
			@RequestParam(value = "id", required = false, defaultValue = "") String id,
			@RequestParam(value = "type", required = false, defaultValue = "") String type,
			@RequestParam("ruleTypeId") String ruleTypeId) {

		try {
			Integer num = 0;
			
			Integer rs=0;
			
			HashMap<String, String> queryParams = new HashMap<String, String>();
			
			if (!id.equals("") && !type.equals("")) {
				String[] ids = id.split(",");
				
				// remove all relation which ptype and pid equals params			
				for (String bindId : ids) {
					queryParams.clear();
					queryParams.put("ptype", type);
					queryParams.put("pid", bindId);
					queryParams.put("ruleTypeId", ruleTypeId);
					relationService.unbind(queryParams);
				}
				

				// remove all relation which ntype and nid equals params
				
				for (String bindId : ids) {
					queryParams.clear();
					queryParams.put("ntype", type);
					queryParams.put("nid", bindId);
					queryParams.put("ruleTypeId", ruleTypeId);
					relationService.unbind(queryParams);
				}

			}else{
				String[] pIds = pId.split(",");
				String[] nIds = nId.split(",");
				for (String bindPid : pIds) {
					for (String bindNid : nIds) {
						queryParams.clear();
						queryParams.put("ptype", pType);
						queryParams.put("pid", bindPid);
						queryParams.put("ntype", nType);
						queryParams.put("nid", bindNid);
						queryParams.put("ruleTypeId", ruleTypeId);
						relationService.unbind(queryParams);
					}
				}
			}

			return new ReturnObj(true);
		} catch (Exception e) {
			return new ReturnObj(e);
		}
	}

	// //以下为绑定jsp，测试时使用，正式发布的时候可以删除
	// @RequestMapping(value="/bindRelation",method={RequestMethod.POST,RequestMethod.GET})
	// public String bindRelation(String type,String ptype,HttpServletRequest
	// request){
	// request.setAttribute("type", type);
	// request.setAttribute("ptype", ptype);
	// return "common/bind";
	// }
	//	
	// @RequestMapping(value="/removeBind",method={RequestMethod.POST,RequestMethod.GET})
	// public String removeBind(String type,String ptype,HttpServletRequest
	// request){
	// request.setAttribute("type", type);
	// request.setAttribute("ptype", ptype);
	// return "common/bindRemove";
	// }
	//	
	// @RequestMapping(value="/index",method={RequestMethod.POST,RequestMethod.GET})
	// public String bindRelation(HttpServletRequest request){
	// return "common/index";
	// }
}
