package com.wonders.frame.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.wonders.frame.core.dao.RelationDao;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.NodeObj;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.RulePathVo;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.ObjInfoCache;
import com.wonders.frame.core.service.RelationService;
import com.wonders.frame.core.utils.RulePathUtil;


@Service("relationService")
public class RelationServiceImpl implements RelationService {
	
	@Resource
	private RelationDao relationDao;


	public Relation bind(HashMap<String,String> queryParams) {
		Relation relation=null;

		List<Relation> rs=this.relationDao.findAll(queryParams,null);
		if(rs!=null &&rs.size()>0){
			relation=rs.get(0);
		}

		if(relation==null){
			relation = new Relation();
			relation.setPid(Integer.valueOf(queryParams.get("pid").toString()));
			relation.setPtype(queryParams.get("ptype").toString());
			relation.setNid(Integer.valueOf(queryParams.get("nid").toString()));
			relation.setNtype(queryParams.get("ntype").toString());
			relation.setRuleTypeId(Integer.valueOf(queryParams.get("ruleTypeId").toString()));
			relation=relationDao.save(relation);
		}
		return relation;
	}
	
	public Relation rebind(HashMap<String,String> queryParams,HashMap<String,String> newParams) {
		Relation relation=null;

		List<Relation> rs=this.relationDao.findAll(queryParams, null);
		if(rs!=null &&rs.size()==1){
			relation=rs.get(0);
			if(newParams.containsKey("pidNew")&&newParams.containsKey("ptypeNew")){
				relation.setPtype(newParams.get("ptypeNew").toString());
				relation.setPid(Integer.valueOf(newParams.get("pidNew").toString()));
			}else if(newParams.containsKey("nidNew")&&newParams.containsKey("ntypeNew")){
				relation.setNtype(newParams.get("ntypeNew").toString());
				relation.setNid(Integer.valueOf(newParams.get("nidNew").toString()));
			}
			relation=relationDao.save(relation);
			
		}

		return relation;
	}
	
	public void unbind(HashMap<String,String> queryParams) throws Exception{

		List<Relation> rs=this.relationDao.findAll(queryParams, null);
		
		this.relationDao.deleteInBatch(rs);
	}
	
	public ReturnObj<List<String>> findRelationPaths(String ruleTypeId,String path) {
		try {
			if (path == null || path.equals("")
					|| (path.indexOf(">") < 0 && path.indexOf("<") < 0)) {
				return new ReturnObj<List<String>>(false);
			}
			RulePathVo rp = RulePathUtil.getRulePath(path);

			List<String> pathResults = getPathResult(rp,
					ruleTypeId);

			return new ReturnObj<List<String>>(pathResults);
		} catch (Exception e) {
			return new ReturnObj<List<String>>(e);
			
		}

	}
	
	
	public ReturnObj<HashMap<String, List<NodeObj>>> findRelationTree(String ruleTypeId,String path) {
		try {
			if (path == null || path.equals("")
					|| (path.indexOf(">") < 0 && path.indexOf("<") < 0)) {
				
				return new ReturnObj<HashMap<String, List<NodeObj>>>(false);
			}

			RulePathVo rp = RulePathUtil.getRulePath(path);

			List<String> opList = rp.getOpList();

			List<String> pathResults = getPathResult(rp,
					ruleTypeId);

			HashMap<String, Object> hmKeyOp = new HashMap<String, Object>();
			for (int i = opList.size() - 1; i >= 0; i--) {
				String op = opList.get(i);
				String nodeType = op.equals(">") ? "next" : "previous";

				HashMap<String, Object> hmKeyOpTmp = new HashMap<String, Object>();

				if (i == opList.size() - 1) {
					for (String pathResult : pathResults) {
						convertPath2Map(pathResult, op,
								nodeType, null, hmKeyOpTmp);
					}
				} else {
					for (String key : hmKeyOp.keySet()) {
						Object nextNodes = hmKeyOp.get(key);
						convertPath2Map(key, op, nodeType,
								nextNodes, hmKeyOpTmp);
					}

				}
				hmKeyOp = hmKeyOpTmp;

			}
			HashMap<String, List<NodeObj>> hm = new HashMap<String, List<NodeObj>>();

			for (String key : hmKeyOp.keySet()) {
				Object nextNodes = hmKeyOp.get(key);
				String[] curNode = key.split("_");
				bindNode2ObjDataMap(curNode, nextNodes, hm);
			}
			
			return new ReturnObj<HashMap<String, List<NodeObj>>>(hm);
		} catch (Exception e) {
			return new ReturnObj<HashMap<String, List<NodeObj>>>(e);
		}
	}
	


	private void bindNode2ObjDataMap(String[] curNode,Object relatedNode,HashMap<String,List<NodeObj>> hmObjData){
		if(!hmObjData.containsKey(curNode[0])){
			List<NodeObj> dataList=new ArrayList<NodeObj>();//对象数据队列：存放对象数据队列
			NodeObj obj=new NodeObj<IDefaultModel>();
			obj.setData(curNode[1]);
			if(relatedNode!=null){
				obj.setRelatedNode(relatedNode);
			}
			dataList.add(obj);
			
			hmObjData.put(curNode[0], dataList);
		}else{
			List<NodeObj> dataListTmp=hmObjData.get(curNode[0]);
			NodeObj obj=new NodeObj();
			obj.setData(curNode[1]);
			if(relatedNode!=null){
				obj.setRelatedNode(relatedNode);
			}
			dataListTmp.add(obj);
		}
	}
	private void convertPath2Map(String pathResult,String op,String nodeType,Object relatedNode,HashMap<String,Object> hmKeyOp){
		
		int opIndex=pathResult.lastIndexOf(op);
		
		String key=pathResult.substring(0,opIndex);
		
		String[] curNode=pathResult.substring(opIndex+1).split("_");
		
		if(!hmKeyOp.containsKey(key)){
			List<NodeObj> dataList=new ArrayList<NodeObj>();//对象数据队列：存放对象数据队列
			NodeObj obj=new NodeObj();
			obj.setData(curNode[1]);
			if(relatedNode!=null){
				obj.setRelatedNode(relatedNode);
			}
			dataList.add(obj);

			HashMap<String,List<NodeObj>> hmObjData=new HashMap<String,List<NodeObj>>();//对象数据映射：存放对象类型与ID队列的映射关系		
			hmObjData.put(curNode[0], dataList);
			
			HashMap<String,Object> hmOpObj=new HashMap<String,Object>();//存放关系类型与对象数据映射的对应关系
			hmOpObj.put(nodeType, hmObjData);
			
			hmKeyOp.put(key, hmOpObj);
		}else{
			HashMap<String,Object> hmOpObjTmp=(HashMap<String,Object>)hmKeyOp.get(key);
			if(!hmOpObjTmp.containsKey(nodeType)){
				List<NodeObj> dataList=new ArrayList<NodeObj>();//对象数据队列：存放对象数据队列
				NodeObj obj=new NodeObj();
				obj.setData(curNode[1]);
				dataList.add(obj);
				if(relatedNode!=null){
					obj.setRelatedNode(relatedNode);
				}
				
				HashMap<String,List<NodeObj>> hmObjData=new HashMap<String,List<NodeObj>>();//对象数据映射：存放对象类型与ID队列的映射关系		
				hmObjData.put(curNode[0], dataList);
				
				HashMap<String,Object> hmOpObj=new HashMap<String,Object>();//存放关系类型与对象数据映射的对应关系
				hmOpObjTmp.put(nodeType, hmObjData);

			}else{
				HashMap<String,List<NodeObj>> hmObjDataTmp=(HashMap<String,List<NodeObj>>)hmOpObjTmp.get(nodeType);
				bindNode2ObjDataMap(curNode,relatedNode,hmObjDataTmp);
				
			}
		}
	}
	
	

	
	//通过递归查询，获得与url入参path格式一致的结果集
	private List<String> getPathResult(RulePathVo rp,String ruleTypeId){
		List<String> queryResult=new ArrayList<String>();	

		String[] beginObjs = rp.getObjList().get(0).split(",");//获取关联符之前的多个对象
		
		for(String sBeginObj:beginObjs){
			String[] beginObj = sBeginObj.split("_");//获取关联符之前的对象
			loopQuery(beginObj,rp.getObjList(),rp.getOpList(),"",0,queryResult,ruleTypeId);//递归查询
		}
		return queryResult;
	}
	
	//递归查询，以一个前置对象，一个后置对象进行一次查询，前置后置对象的前后关系由关联符号决定
	private void loopQuery(String[] beginObj,List<String> pathObjList,List<String> pathOpList,String prefixValue,int index,List<String> queryResult,String ruleTypeId){
		
		String op=pathOpList.get(index);
		
		String[] endObjs=pathObjList.get(index+1).split(",");//得到关联符之前用逗号分割的多个对象
		for(String sEndObj:endObjs){
			
			String[] endObj = sEndObj.split("_");//获取关联符之后的对象
					
			String[] pObj=op.equals(">")?beginObj:endObj;//根据关联符获得上级节点对象
			String[] nObj=op.equals(">")?endObj:beginObj;//根据关联符获得下级节点对象		
	
			List<Relation> relationList=findListByPObjAndNObj(pObj,nObj,ruleTypeId);//根据前置和后置对象查询关连表
	
			for(Relation r:relationList){
				StringBuffer curPreFixValue=new StringBuffer("");
				if(index<pathOpList.size()-1){//非最后一次递归（操作到最后一组对象之前）时，取path中位于前面的对象数据拼接到返回的字符串上
					String[] nextBeginObj=new String[2];				
					if(op.equals(">")){
						curPreFixValue=curPreFixValue.append(prefixValue).append(r.getPtype()).append("_").append(r.getPid().toString()).append(op);
						nextBeginObj[0]=r.getNtype();
						nextBeginObj[1]=r.getNid().toString();
						
					}else{
						curPreFixValue=curPreFixValue.append(prefixValue).append(r.getNtype()).append("_").append(r.getNid().toString()).append(op);
						nextBeginObj[0]=r.getPtype();
						nextBeginObj[1]=r.getPid().toString();
					}
					
					loopQuery(nextBeginObj,pathObjList,pathOpList,curPreFixValue.toString(),index+1,queryResult,ruleTypeId);
					
				}else{//最后一次递归时，将查询结果中的前置和后置数据都拼接到要返回的字符串上
					if(op.equals(">")){
						curPreFixValue=curPreFixValue.append(prefixValue)
						.append(r.getPtype()).append("_").append(r.getPid().toString()).append(op)
						.append(r.getNtype()).append("_").append(r.getNid().toString());
						
					}else{
						curPreFixValue=curPreFixValue.append(prefixValue)
						.append(r.getNtype()).append("_").append(r.getNid().toString()).append(op)
						.append(r.getPtype()).append("_").append(r.getPid().toString());
					}
					queryResult.add(curPreFixValue.toString());
				}
				
			}
		}
	}
			
	//2015-1-6:根据前置和后置对象所包含的数据，确定调用那一个接口
	private List<Relation> findListByPObjAndNObj(String[] pObj,String[] nObj,String ruleTypeId){
		List<Relation> relationList=new ArrayList<Relation>();
		HashMap<String,String> queryParams=new HashMap<String,String>();
		switch(pObj.length){//判断上级对象长度
					case 1://长度为1，则上级对象不包含具体ID
						switch(nObj.length){//判断下级对象长度
							case 1://长度为1，则下级对象不包含具体ID
								queryParams.put("ptype", pObj[0]);
								queryParams.put("ntype", nObj[0]);
								queryParams.put("ruleTypeId", ruleTypeId);
								relationList=this.relationDao.findAll(queryParams, null);
								//relationList=relationDao.findByPTypeAndNType(pObj[0], nObj[0],type);
								break;
							case 2://长度为2，则下级对象包含具体ID
								queryParams.put("ptype", pObj[0]);
								queryParams.put("ntype", nObj[0]);
								queryParams.put("nid", nObj[1]);
								queryParams.put("ruleTypeId", ruleTypeId);
								relationList=this.relationDao.findAll(queryParams, null);
								//relationList=relationDao.findByPTypeAndNTypeAndNId(pObj[0], nObj[0], Integer.valueOf(nObj[1]),type);
								break;
							default:break;
						};
						break;
					case 2://长度为2，则上级对象包含具体ID
						switch(nObj.length){//判断下级对象长度
							case 1://长度为1，则下级对象不包含具体ID
								queryParams.put("ptype", pObj[0]);
								queryParams.put("pid",  pObj[1]);
								queryParams.put("ntype", nObj[0]);
								queryParams.put("ruleTypeId", ruleTypeId);
								relationList=this.relationDao.findAll(queryParams, null);
								//relationList=relationDao.findByPTypeAndPIdAndNType(pObj[0], Integer.valueOf(pObj[1]), nObj[0],type);
								break;
							case 2://长度为2，则下级对象包含具体ID
								queryParams.put("ptype", pObj[0]);
								queryParams.put("pid",  pObj[1]);
								queryParams.put("ntype", nObj[0]);
								queryParams.put("nid", nObj[1]);
								queryParams.put("ruleTypeId", ruleTypeId);
								relationList=this.relationDao.findAll(queryParams, null);
								//relationList=relationDao.findByPTypeAndPIdAndNTypeAndNId(pObj[0], Integer.valueOf(pObj[1]), nObj[0], Integer.valueOf(nObj[1]),type);
								break;
							default:break;
						};
						break;
					default:break;
				}
		return relationList;	
	}

	@Override
	public List<Relation> findAllRelation(Integer ruleTypeId,
			String type, List<Integer> ids) {
		List<Relation> relations=relationDao.findNext(ruleTypeId, type, ids);
		relations.addAll(relationDao.findPrevious(ruleTypeId, type, ids));
		return relations;
	}
	
	@Override
	public List<Relation> findAllChildRelation(Integer ruleTypeId,String type,Integer rootId) {
		return relationDao.findAllChild(ruleTypeId, type, rootId);
	}
	
	@Override
	public List<Relation> findAllParentRelation(Integer ruleTypeId,String type,Integer rootId) {
		return relationDao.findAllParent(ruleTypeId, type, rootId);
	}
	@Override
	public List<Relation> findTree(Integer ruleTypeId, String treeType,Integer rootNodeId){
		return relationDao.findTree(ruleTypeId, treeType, rootNodeId);
		
	}
	@Override
	public List<Relation> findReverseTree(Integer ruleTypeId, String treeType,Integer leafNodeId){
		return relationDao.findReverseTree(ruleTypeId, treeType, leafNodeId);
	}

	@Override
	public Long count(HashMap<String, String> queryParams) {
		return relationDao.count(queryParams);
	}


}