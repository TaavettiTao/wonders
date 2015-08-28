package com.wonders.frame.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.bo.Relation;
import com.wonders.frame.core.model.vo.ObjKey;
import com.wonders.frame.core.model.vo.RelatedNode;
import com.wonders.frame.core.model.vo.TreeNode;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.utils.ReflectUtil;

/**
 * @author lushuaifeng
 *
 */
@Service("basicCrudService")
public class BasicCrudServiceImpl extends SingleCrudServiceImpl<IDefaultModel,Integer> implements BasicCrudService{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings("unchecked")
	public List<RelatedNode<IDefaultModel,Integer>> bindChildObjInfo(
			List<Relation> relations) {
		List<RelatedNode<IDefaultModel,Integer>> childObjs = new ArrayList<RelatedNode<IDefaultModel,Integer>>();
		try{
			// 存储各个对象类型的所有对象ID
			HashMap<String, List<Integer>> hmObjIds = new HashMap<String, List<Integer>>();
			// 存储各个对象实例间的对应关系
			LinkedHashMap<ObjKey, List<ObjKey>> hmObjKeys = new LinkedHashMap<ObjKey, List<ObjKey>>();
			for (Relation r : relations) {
	
				// 对象类型与其ID列表的映射关系
				if (hmObjIds.containsKey(r.getNtype())) {
					List<Integer> ids = hmObjIds.get(r.getNtype());
					if (ids.indexOf(r.getNid()) < 0) {
						ids.add(r.getNid());
						hmObjIds.put(r.getNtype(), ids);
					}
				} else {
					List<Integer> ids = new ArrayList<Integer>();
					ids.add(r.getNid());
					hmObjIds.put(r.getNtype(), ids);
				}
	
				ObjKey pobjKey = new ObjKey(r.getPtype(), r.getPid());
				ObjKey nobjKey = new ObjKey(r.getNtype(), r.getNid());
	
				if (hmObjKeys.containsKey(pobjKey)) {
					List<ObjKey> objkeys = hmObjKeys.get(pobjKey);
					if (objkeys.indexOf(nobjKey) < 0) {
						objkeys.add(nobjKey);
						hmObjKeys.put(pobjKey, objkeys);
					}
				} else {
					List<ObjKey> objkeys = new ArrayList<ObjKey>();
					objkeys.add(nobjKey);
					hmObjKeys.put(pobjKey, objkeys);
				}
	
			}
	
			HashMap<String, IDefaultModel> hmObjKeyData = new HashMap<String, IDefaultModel>();
			Set<String> keys = hmObjIds.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				List<Integer> ids = hmObjIds.get(key);
				Object rs = ReflectUtil.executeMethod(key, "findByIds",
						new Class[] { Iterable.class }, new Object[] { ids });
	
				List<IDefaultModel> objs = (List<IDefaultModel>) rs;
				for (IDefaultModel obj : objs) {
					hmObjKeyData.put(new ObjKey(key, obj.getId()).getKeyNo(), obj);
				}
			}
	
			Set<ObjKey> keys2 = hmObjKeys.keySet();
			for (Iterator<ObjKey> it = keys2.iterator(); it.hasNext();) {
				ObjKey pobjKey = it.next();
				List<ObjKey> nobjKeys = hmObjKeys.get(pobjKey);
				for (ObjKey nobjKey : nobjKeys) {
					if (hmObjKeyData.get(nobjKey.getKeyNo()) == null) {
						continue;
					}
	
					RelatedNode<IDefaultModel,Integer> relatedNode = new RelatedNode<IDefaultModel,Integer>();
					relatedNode.setPobj(pobjKey.getType());
					relatedNode.setPid(pobjKey.getId());
					relatedNode.setNobj(nobjKey.getType());
					relatedNode.setNid(nobjKey.getId());
					relatedNode.setNode(hmObjKeyData.get(nobjKey.getKeyNo()));
					childObjs.add(relatedNode);
				}
			}
		}catch(Exception e){
			logger.error("Exception Throwable", e);
		}
		return childObjs;
	}

	@SuppressWarnings("unchecked")
	public List<RelatedNode<IDefaultModel,Integer>> bindParentObjInfo(
			List<Relation> relations) {
		List<RelatedNode<IDefaultModel,Integer>> parentObjs = new ArrayList<RelatedNode<IDefaultModel,Integer>>();
		try{
			// 存储各个对象类型的所有对象ID
			HashMap<String, List<Integer>> hmObjIds = new HashMap<String, List<Integer>>();
			// 存储各个对象实例间的对应关系
			LinkedHashMap<ObjKey, List<ObjKey>> hmObjKeys = new LinkedHashMap<ObjKey, List<ObjKey>>();
			for (Relation r : relations) {
	
				// 对象类型与其ID列表的映射关系
				if (hmObjIds.containsKey(r.getPtype())) {
					List<Integer> ids = hmObjIds.get(r.getPtype());
					if (ids.indexOf(r.getPid()) < 0) {
						ids.add(r.getPid());
						hmObjIds.put(r.getPtype(), ids);
					}
				} else {
					List<Integer> ids = new ArrayList<Integer>();
					ids.add(r.getPid());
					hmObjIds.put(r.getPtype(), ids);
				}
	
				ObjKey pobjKey = new ObjKey(r.getNtype(), r.getNid());
				ObjKey nobjKey = new ObjKey(r.getPtype(), r.getPid());
	
				if (hmObjKeys.containsKey(pobjKey)) {
					List<ObjKey> objkeys = hmObjKeys.get(pobjKey);
					if (objkeys.indexOf(nobjKey) < 0) {
						objkeys.add(nobjKey);
						hmObjKeys.put(pobjKey, objkeys);
					}
				} else {
					List<ObjKey> objkeys = new ArrayList<ObjKey>();
					objkeys.add(nobjKey);
					hmObjKeys.put(pobjKey, objkeys);
				}
	
			}
	
			HashMap<String, IDefaultModel> hmObjKeyData = new HashMap<String, IDefaultModel>();
			Set<String> keys = hmObjIds.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				List<Integer> ids = hmObjIds.get(key);
				Object rs = ReflectUtil.executeMethod(key, "findByIds",
						new Class[] { Iterable.class }, new Object[] { ids });
	
				List<IDefaultModel> objs = (List<IDefaultModel>) rs;
				for (IDefaultModel obj : objs) {
					hmObjKeyData.put(new ObjKey(key, obj.getId()).getKeyNo(), obj);
				}
			}
	
			Set<ObjKey> keys2 = hmObjKeys.keySet();
			for (Iterator<ObjKey> it = keys2.iterator(); it.hasNext();) {
				ObjKey pobjKey = it.next();
				List<ObjKey> nobjKeys = hmObjKeys.get(pobjKey);
				for (ObjKey nobjKey : nobjKeys) {
					if (hmObjKeyData.get(nobjKey.getKeyNo()) == null) {
						continue;
					}
	
					RelatedNode<IDefaultModel,Integer> relatedNode = new RelatedNode<IDefaultModel,Integer>();
					relatedNode.setPobj(nobjKey.getType());
					relatedNode.setPid(nobjKey.getId());
					relatedNode.setNobj(pobjKey.getType());
					relatedNode.setNid(pobjKey.getId());
					relatedNode.setNode(hmObjKeyData.get(nobjKey.getKeyNo()));
					parentObjs.add(relatedNode);
				}
			}
		}catch(Exception e){
			logger.error("Exception Throwable", e);
		}
		return parentObjs;
	}

	@SuppressWarnings("unchecked")
	public List<TreeNode<IDefaultModel,Integer>> bindTreeObjInfo(
			List<Relation> relations){
		List<TreeNode<IDefaultModel,Integer>> childObjs = new ArrayList<TreeNode<IDefaultModel,Integer>>();
		try{
			// 存储各个对象类型的所有对象ID
			HashMap<String, List<Integer>> hmObjIds = new HashMap<String, List<Integer>>();
			// 存储各个对象实例间的对应关系
			LinkedHashMap<ObjKey, List<ObjKey>> hmObjKeys = new LinkedHashMap<ObjKey, List<ObjKey>>();
			for (Relation r : relations) {
	
				// 对象类型与其ID列表的映射关系
				if (hmObjIds.containsKey(r.getNtype())) {
					List<Integer> ids = hmObjIds.get(r.getNtype());
					if (ids.indexOf(r.getNid()) < 0) {
						ids.add(r.getNid());
						hmObjIds.put(r.getNtype(), ids);
					}
				} else {
					List<Integer> ids = new ArrayList<Integer>();
					ids.add(r.getNid());
					hmObjIds.put(r.getNtype(), ids);
				}
	
				ObjKey pobjKey = new ObjKey(r.getPtype(), r.getPid());
				ObjKey nobjKey = new ObjKey(r.getNtype(), r.getNid());
	
				if (hmObjKeys.containsKey(pobjKey)) {
					List<ObjKey> objkeys = hmObjKeys.get(pobjKey);
					if (objkeys.indexOf(nobjKey) < 0) {
						objkeys.add(nobjKey);
						hmObjKeys.put(pobjKey, objkeys);
					}
				} else {
					List<ObjKey> objkeys = new ArrayList<ObjKey>();
					objkeys.add(nobjKey);
					hmObjKeys.put(pobjKey, objkeys);
				}
	
			}
	
			HashMap<String, IDefaultModel> hmObjKeyData = new HashMap<String, IDefaultModel>();
			Set<String> keys = hmObjIds.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				List<Integer> ids = hmObjIds.get(key);
				Object rs = ReflectUtil.executeMethod(key, "findByIds",
						new Class[] { Iterable.class }, new Object[] { ids });
	
				List<IDefaultModel> objs = (List<IDefaultModel>) rs;
				for (IDefaultModel obj : objs) {
					hmObjKeyData.put(new ObjKey(key, obj.getId()).getKeyNo(), obj);
				}
			}
	
			Set<ObjKey> keys2 = hmObjKeys.keySet();
			for (Iterator<ObjKey> it = keys2.iterator(); it.hasNext();) {
				ObjKey pobjKey = it.next();
				List<ObjKey> nobjKeys = hmObjKeys.get(pobjKey);
				for (ObjKey nobjKey : nobjKeys) {
					if (hmObjKeyData.get(nobjKey.getKeyNo()) == null) {
						continue;
					}
	
					TreeNode<IDefaultModel,Integer> treeNode = new TreeNode<IDefaultModel,Integer>();
					treeNode.setPid(pobjKey.getId());
					treeNode.setNid(nobjKey.getId());
					treeNode.setNode(hmObjKeyData.get(nobjKey.getKeyNo()));
					childObjs.add(treeNode);
				}
			}
		}catch(Exception e){
			logger.error("Exception Throwable", e);
		}
		return childObjs;
	}

}
