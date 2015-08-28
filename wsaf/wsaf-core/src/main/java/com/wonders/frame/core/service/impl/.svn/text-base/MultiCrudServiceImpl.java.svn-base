package com.wonders.frame.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.wonders.frame.core.dao.MultiDao;
import com.wonders.frame.core.model.IDefaultModel;
import com.wonders.frame.core.model.IMultiModel;
import com.wonders.frame.core.model.bo.RuleType;
import com.wonders.frame.core.model.vo.MultiModelElement;
import com.wonders.frame.core.model.vo.MultiModelMainObj;
import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.model.vo.MultiModelRelatedObj;
import com.wonders.frame.core.model.vo.ObjectInfoVo;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.MultiQuerySqlElement;
import com.wonders.frame.core.model.vo.ValidError;
import com.wonders.frame.core.service.MultiCrudService;
import com.wonders.frame.core.service.BasicCrudService;
import com.wonders.frame.core.service.ObjInfoCache;
import com.wonders.frame.core.service.RelationService;
import com.wonders.frame.core.service.RuleTypeService;
import com.wonders.frame.core.utils.JacksonMapper;
import com.wonders.frame.core.utils.SqlBuilderUtil;
import com.wonders.frame.core.utils.ReflectUtil;
import com.wonders.frame.core.utils.ValidUtil;

@Service("multiCrudService")
public class MultiCrudServiceImpl implements MultiCrudService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private MultiDao multipleDao;
	@Resource
	private BasicCrudService basicCrudService;

	@Resource
	private RuleTypeService ruleTypeService;

	@Resource
	private RelationService relationService;
	
	/**
	 * 此处不可以使用注解注入的方式使用SqlBuilderService，因为SqlBuilderService是有状态的，必须使用原生模式
	 * 而当前对象默认为单例模式，所有相关bean均被一次性加载
	 * 若采用@Resource或@autowired，则SqlBuilderService进行解析过程中，会出现成员变量出错的问题
	 */
	//@Resource 
	//private SqlBuilderService sqlBuilderService;
	
	public ReturnObj<SimplePage<HashMap<String,String>>> findByPage(String queryJson, Integer pageNum,Integer pageSize) {
		return findByPage(null,queryJson, pageNum,pageSize);
	}
	public ReturnObj<SimplePage<HashMap<String,String>>> findByPage(String ruleType,String queryJson, Integer pageNum,Integer pageSize) {
		try {
			RuleType rt=ruleTypeService.findExistOne(ruleType);
			
			if(rt==null){
				return new ReturnObj<SimplePage<HashMap<String,String>>>("Can not find ruleType record");
			}
			
			
			MultiQuerySqlElement mse = SqlBuilderUtil.getMultiSqlBuilder().buildMultiSql(rt.getId(),queryJson);
			
			return findByPage(mse,pageNum,pageSize);

		} catch (Exception e) {
			return new ReturnObj<SimplePage<HashMap<String,String>>>(e);
		}
	}
	
	public ReturnObj<SimplePage<HashMap<String,String>>> findByPage(MultiQuerySqlElement mse, Integer pageNum,Integer pageSize) {
		try {
			
			SimplePage<HashMap<String,String>> rs=multipleDao.findByPage(mse,pageNum,pageSize);

			return new ReturnObj<SimplePage<HashMap<String,String>>>(rs);

		} catch (Exception e) {
			return new ReturnObj<SimplePage<HashMap<String,String>>>(e);
		}
	}
	
	public ReturnObj<List<HashMap<String,String>>> findAll(String queryJson, Integer rowNum,Integer rowSize) {
		return findAll(null,queryJson, rowNum,rowSize);
	}
	public ReturnObj<List<HashMap<String,String>>> findAll(String ruleType,String queryJson, Integer rowNum,Integer rowSize) {
		try {
			RuleType rt=ruleTypeService.findExistOne(ruleType);
			
			if(rt==null){				
				return new ReturnObj<List<HashMap<String,String>>>("Can not find ruleType record");
			}
			
			MultiQuerySqlElement se = SqlBuilderUtil.getMultiSqlBuilder().buildMultiSql(rt.getId(),queryJson);

			return findAll(se,rowNum,rowSize); 

		} catch (Exception e) {
			
			return new ReturnObj<List<HashMap<String,String>>>(e);
		}

	}
	public ReturnObj<List<HashMap<String,String>>> findAll(MultiQuerySqlElement mse, Integer rowNum,Integer rowSize) {
		try {
			List<HashMap<String,String>> rs=multipleDao.findAll(mse,rowNum,rowSize); 
			
			logger.debug("SQL Result:{}",JacksonMapper.toJson(rs));
			
			return new ReturnObj<List<HashMap<String,String>>>(rs);

		} catch (Exception e) {
			
			return new ReturnObj<List<HashMap<String,String>>>(e);
		}

	}
	
	/**
	 *****************************************数据入库操作******************************************************
	 *传入参数格式如下：{"obj":[{"att":"val","child":{...},"parent":{...}}],obj2:[{}]}
	 * 其中obj为对象名（需要在数据库表af_obj_info中注册）,
	 * att为对象属性名,当对象的主键属性缺失时，为新增；
	 * 当某个att的value为"$obj.id"时，该属性值会以对应主对象的id值填充；
	 * child和parent对应value格式同主格式相同，
	 * {
	    "user": [
	        {
	            "id": "1",
	            "name": "张三",
	            "password": "xxxx",
	            "mobile": "134454666",
	            "child": {
	                "test": [
	                    {
	                        "id": 85,
	                        "str": "aaa85",
	                        "num": "12.345",
	                        "date": "2013-11-23",
	                        "datetime": "2014-02-11 14:32:23",
	                        "fk": "$user.id"
	                    },
	                    {
	                        "id": 87,
	                        "str": "abc87",
	                        "num": "190.389",
	                        "date": "2013-05-11",
	                        "datetime": "2019-12-11 13:45:21",
	                        "fk": "0"
	                    }
	                ]
	            },
	            "parent": {
	                "role": [
	                    {
	                        "id": 161,
	                        "name": "角色161",
	                        "child": {
	                            "test": [
	                                {
	                                    "id": 82,
	                                    "str": "aaa",
	                                    "num": "12.345",
	                                    "date": "2013-11-23",
	                                    "datetime": "2014-02-11 14:32:23",
	                                    "fk": ""
	                                },
	                                {
	                                    "id": 84,
	                                    "str": "abc84",
	                                    "num": "190.389",
	                                    "date": "2013-05-11",
	                                    "datetime": "2019-12-11 13:45:21",
	                                    "fk": "$role.id"
	                                }
	                            ]
	                        }
	                    }
	                ],
	                "organ": [
	                    {
	                        "id": "1",
	                        "name": "单位1"
	                    }
	                ]
	            }
	        }
	    ]
	}
	*/
	
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(String ruleType,List<IMultiModel> mms){
		try{
			HashMap<String,List<Object>> ro= new HashMap<String,List<Object>>();
			List<ValidError> errors=new ArrayList<ValidError>();
			for(Object obj:mms){
				List<ValidError> validErrors=ValidUtil.check(obj);
				if(validErrors.size()>0){
					errors.addAll(validErrors);
					break;
				}
				MultiModelMainObj mmmo=buildMultiModel(obj,null,null);
				if(ro.containsKey(mmmo.getEntityName())){
					ro.get(mmmo.getEntityName()).add(mmmo.getObjValue());
				}else{
					List<Object> objList=new ArrayList<Object>();
					objList.add(mmmo.getObjValue());
					ro.put(mmmo.getEntityName(), objList);
				}
				
			}
			if(errors.size()>0){
				return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>(errors);
			}
			
			return saveOrUpdate(ruleType,JacksonMapper.toJson(ro));
			
		}catch(Exception e){
			return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>(e);
		}
	}
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(List<IMultiModel> mms){
		
		return saveOrUpdate(null,mms);
		
	}
	
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(String ruleType,IMultiModel mm){
		try{
			HashMap<String,List<Object>> ro= new HashMap<String,List<Object>>();
			List<ValidError> errors=ValidUtil.check(mm);
			if(errors.size()>0){
				return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>(errors);
			}
			
			MultiModelMainObj mmmo=buildMultiModel(mm,null,null);
	
			List<Object> objList=new ArrayList<Object>();
			objList.add(mmmo.getObjValue());
			ro.put(mmmo.getEntityName(), objList);
			return saveOrUpdate(ruleType,JacksonMapper.toJson(ro));
		}catch(Exception e){
			return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>(e);
		}
	}
	
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(IMultiModel mm){
		return saveOrUpdate(null,mm);
	}
	

	private MultiModelMainObj buildMultiModel(Object multiModel,String[] fks,String mainObjPk) throws Exception{

		//获得复杂对象的各个元素信息
		MultiModelElement mme=ReflectUtil.getMultiModelElement(multiModel);
		
		
		//通过主对象字段名获取的主对象
		Object mainObj=mme.getMainObj();
		ObjectInfoVo objInfo=ObjInfoCache.getObjectInfo(mainObj.getClass());
		String entityName=objInfo.getEntityName();
		//构造主对象的主键字段
		String mainObjPK="$".concat(entityName).concat(".id");
		
		//将主对象转化为hashmap		
		HashMap<String, Object> hmMainObj = (HashMap<String, Object>) JacksonMapper
		.convert(mainObj, JacksonMapper.hmObj);
		
		if(fks!=null){//当前复杂对象的外键不为空，则为主对象添加外键关联
			for(String fk:fks){
				hmMainObj.put(fk, mainObjPk);
			}
		}
		
		//若父对象不为空，则在主对象下添加父对象节点
		if(!mme.getParentObj().isEmpty()){
			
			hmMainObj.put(RelateType.PARENT.nodeName().toLowerCase(), buildRelatedObj(mme.getParentObj(),mainObjPK));
		}
		//若子对象不为空，则在主对象下添加子对象节点
		if(!mme.getChildObj().isEmpty()){
			
			hmMainObj.put(RelateType.CHILD.nodeName().toLowerCase(), buildRelatedObj(mme.getChildObj(),mainObjPK));
		}
		
		return new MultiModelMainObj(entityName,hmMainObj);
	}
	
	
	private MultiModelMainObj buildSingleModel(Object singleModel,String[] fks,String mainObjPk) throws Exception{

		ObjectInfoVo objInfo=ObjInfoCache.getObjectInfo(singleModel.getClass());
		String entityName=objInfo.getEntityName();
		
		//将主对象转化为hashmap		
		HashMap<String, Object> hmMainObj = (HashMap<String, Object>) JacksonMapper
		.convert(singleModel, JacksonMapper.hmObj);
		
		if(fks!=null){//当前复杂对象的外键不为空，则为主对象添加外键关联
			for(String fk:fks){
				hmMainObj.put(fk, mainObjPk);
			}
		}

		
		return new MultiModelMainObj(entityName,hmMainObj);
	}
	
	
	private HashMap<String,List<Object>> buildRelatedObj(List<MultiModelRelatedObj> relatedObjs, String mainObjPK) throws Exception{
		HashMap<String,List<Object>> ro= new HashMap<String,List<Object>>();
		//遍历关联对象
		for(MultiModelRelatedObj mmro:relatedObjs){			
						
			if(mmro.isList()){
				for(Object obj:mmro.getObjList()){
					MultiModelMainObj mmmo=null;
					if(mmro.isMultiModel()){
						mmmo=buildMultiModel(obj,mmro.getFk(),mainObjPK);
					}else{
						mmmo=buildSingleModel(obj,mmro.getFk(),mainObjPK);
					}
					if(ro.containsKey(mmmo.getEntityName())){
						ro.get(mmmo.getEntityName()).add(mmmo.getObjValue());
					}else{
						List<Object> objList=new ArrayList<Object>();
						objList.add(mmmo.getObjValue());
						ro.put(mmmo.getEntityName(), objList);
					}
					
				}
			}else{
				MultiModelMainObj mmmo=null;
				if(mmro.isMultiModel()){
					mmmo=buildMultiModel(mmro.getObj(),mmro.getFk(),mainObjPK);
				}else{
					mmmo=buildSingleModel(mmro.getObj(),mmro.getFk(),mainObjPK);
				}
				if(ro.containsKey(mmmo.getEntityName())){
					ro.get(mmmo.getEntityName()).add(mmmo.getObjValue());
				}else{
					List<Object> objList=new ArrayList<Object>();
					objList.add(mmmo.getObjValue());
					ro.put(mmmo.getEntityName(), objList);
				}
			}
			
					
		}
		
		return ro;
	}
	
	/**
	 * 数据入库操作，由于关联关系可能保存在relation表中，所以需要传入规则类型ID
	 */
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(String dataJson) {
		return saveOrUpdate(null,dataJson);
	}
	
	public ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdate(String ruleType,String dataJson) {
		logger.debug("ruleType:{},dataJson:{}",ruleType,dataJson);
		try {
			// 查找对应规则类型
			RuleType rt=ruleTypeService.findExistOne(ruleType);
			
			if(rt==null){
				return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>("Can not find ruleType record");
			}
			
			return saveOrUpdateSingleObj(
					dataJson, String.valueOf(rt.getId()), null, null, null);

		} catch (Exception e) {			
			return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>(e);
		}

	}

	/**
	 * 分解json，对单个对象依次进行入库，并保存对象间关系（如无外键标示，则根据child/parent将关联关系存入relation表）
	 * 
	 * @param dataJson
	 * @param ruleTypeId
	 * @param relateType
	 * @param mainObj
	 * @param mainObjId
	 * @return
	 * @throws Exception
	 *             ：将异常抛出至顶层，封装至返回对象；由于采用AOP的方式加入事务管理，所以必须采用异常抛出机制以保证事务回滚
	 */
	private ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> saveOrUpdateSingleObj(
			String dataJson, String ruleTypeId, RelateType relateType,
			String mainObj, String mainObjId) throws Exception {

		LinkedHashMap<String, List<HashMap<String, Object>>> returnMultipleObj = new LinkedHashMap<String, List<HashMap<String, Object>>>();

		LinkedHashMap<String, Object[]> multipleObj = (LinkedHashMap<String, Object[]>) JacksonMapper
				.readValue(dataJson, JacksonMapper.linkedHmObjArr);

		Set<String> keySet = multipleObj.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {// 轮询每个对象

			String key = it.next();

			Object[] recObjs = multipleObj.get(key);
			Class<?> clazz = ObjInfoCache.getObjectClass(key);

			ObjectInfoVo objectInfo = ObjInfoCache.getObjectInfo(clazz);

			List<HashMap<String, Object>> returnRecs = new ArrayList<HashMap<String, Object>>();
			for (Object recObj : recObjs) {// 轮询每条记录
				boolean addRelation = true;
				String rec = JacksonMapper.toJson(recObj);
				if (mainObj == null || mainObjId == null) {
					addRelation = false;
				} else if (rec.contains("$".concat(mainObj).concat(".id"))) {// 若传递数据的JSON中指定了外键关系，则用主对象的ID值替换进去,并标记其含有外键
					rec = rec.replace("$".concat(mainObj).concat(".id"),
							mainObjId.toString());
					addRelation = false;
				}
				HashMap<String, String> hmData = (HashMap<String, String>) JacksonMapper
						.readValue(rec, JacksonMapper.hmStr);

				SingleModelParams mp = new SingleModelParams(objectInfo.getClazz());

				mp.setData(hmData);

				ReturnObj<IDefaultModel> ro =  basicCrudService
						.saveOrUpdate(mp);
				
				if(!ro.getInfo().getSuccess()){
					return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>(ro.getInfo());
				}
				IDefaultModel obj=ro.getData();
				if (addRelation) {// 如果记录不含有外键定义，则将关系记录到relation表中
					HashMap<String, String> queryParams = new HashMap<String, String>();
					switch (relateType) {
					case PARENT:
						queryParams.put("ruleTypeId", ruleTypeId);
						queryParams.put("ptype", key);
						queryParams.put("pid", String.valueOf(obj.getId()));
						queryParams.put("ntype", mainObj);
						queryParams.put("nid", mainObjId);
						// 绑定关联关系
						relationService.bind(queryParams);

						break;
					default:
						queryParams.clear();
						queryParams.put("ruleTypeId", ruleTypeId);
						queryParams.put("ptype", mainObj);
						queryParams.put("pid", mainObjId);
						queryParams.put("ntype", key);
						queryParams.put("nid", String.valueOf(obj.getId()));
						// 绑定关联关系
						relationService.bind(queryParams);
						break;
					}
				}
				LinkedHashMap<String, Object> returnRec = (LinkedHashMap<String, Object>) JacksonMapper
						.convert(obj, JacksonMapper.linkedHmObj);

				if (hmData.containsKey(RelateType.CHILD.nodeName())) {
					Object childDataObj = hmData.get(RelateType.CHILD
							.nodeName());
					String childDataJson = JacksonMapper.toJson(childDataObj);
					ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> childReturnObjs = saveOrUpdateSingleObj(
							childDataJson, ruleTypeId, RelateType.CHILD, key,
							String.valueOf(obj.getId()));
					if(!childReturnObjs.getInfo().getSuccess()){
						return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>(childReturnObjs.getInfo());
					}
					returnRec.put(RelateType.CHILD.nodeName(), childReturnObjs.getData());
					// hmData.remove("child");
				}
				if (hmData.containsKey(RelateType.PARENT.nodeName())) {
					Object parentDataObj = hmData.get(RelateType.PARENT
							.nodeName());
					String parentDataJson = JacksonMapper.toJson(parentDataObj);
					ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>> parentReturnObjs = saveOrUpdateSingleObj(
							parentDataJson, ruleTypeId, RelateType.PARENT, key,
							String.valueOf(obj.getId()));
					if(!parentReturnObjs.getInfo().getSuccess()){
						return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>(parentReturnObjs.getInfo());
					}
					returnRec.put(RelateType.PARENT.nodeName(),
							parentReturnObjs.getData());
					// hmData.remove("parent");
				}

				returnRecs.add(returnRec);

			}
			returnMultipleObj.put(key, returnRecs);
		}

		return new ReturnObj<LinkedHashMap<String, List<HashMap<String, Object>>>>(returnMultipleObj);
	}

	public static void main(String[] args) {
		try{


		}catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println("13344?ddf".replaceAll("?", ""));
		//long i=System.currentTimeMillis();
		//Object obj=JacksonMapper.convert("2014-10-12 11:12:23", Date.class);
		//Object obj=DateFormatUtil.timeParse("yyyy-MM-dd HH:mm:ss", "2013-01-12 12:43:32");
		//long j=System.currentTimeMillis();
		//System.out.println(obj);
		
		//System.out.println(JacksonMapper.readValue("1234", String.class));


	}
}
