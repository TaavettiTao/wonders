package com.wonders.frame.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wonders.frame.core.model.vo.MultiQuery;
import com.wonders.frame.core.model.vo.MultiQueryEntity;
import com.wonders.frame.core.model.vo.MultiQueryField;
import com.wonders.frame.core.model.vo.PlaceholderParam;
import com.wonders.frame.core.model.vo.MultiQuerySelect;
import com.wonders.frame.core.model.vo.MultiQuerySelectField;
import com.wonders.frame.core.model.vo.ObjectAttributeVo;
import com.wonders.frame.core.model.vo.ObjectInfoVo;
import com.wonders.frame.core.model.vo.MultiQuerySqlElement;
import com.wonders.frame.core.service.ObjInfoCache;
import com.wonders.frame.core.service.MultiSqlBuilderService;
import com.wonders.frame.core.service.SingleHqlBuilderService.SqlDefinedSign;
import com.wonders.frame.core.utils.JacksonMapper;

/**
 * @author lushuaifeng
 * 当前对象用于解析前台传入的json格式的查询信息，生成对应的sql和Hql相关元素
 * 在使用buildMultiSql()进行多表复杂查询时，必须以原生形式调用，因为处理过程中会用到对象的状态信息（用于存储解析sql过程中的对象属性等临时信息）
 */
@Service("multiSqlBuilderService")
@Scope("prototype")
public class MultiSqlBuilderServiceImpl implements MultiSqlBuilderService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Integer ruleTypeId=1;
	
	private HashMap<String, MultiQueryEntity> hmEntity=new HashMap<String, MultiQueryEntity>();
	
	private void setRuleTypeId(Integer ruleType){
		this.ruleTypeId=ruleType;
	}
		
	public MultiQuerySqlElement buildMultiSql(Integer ruleTypeId,String jsonData) throws Exception{

		if(ruleTypeId!=null){
			setRuleTypeId(ruleTypeId);
		}
		
		long i=System.currentTimeMillis();
		MultiQuery obj = buildMultipleQuery(jsonData);
		
		List<PlaceholderParam> mqp=new ArrayList<PlaceholderParam>();
		if(obj.getSelect().getQueryParams()!=null && obj.getSelect().getQueryParams().size()>0){
			mqp.addAll(obj.getSelect().getQueryParams());
		}
		if(obj.getQueryParams()!=null && obj.getQueryParams().size()>0){
			mqp.addAll(obj.getQueryParams());
		}
		
		logger.debug("SQL:{}",obj.getSql());
		logger.debug("SQL selectField:{}",JacksonMapper.toJson(obj.getSelect().getSelectFields()));
		logger.debug("SQL queryParams:{}",JacksonMapper.toJson(mqp));

		long j=System.currentTimeMillis();
		logger.debug("拼接sql耗时：{}",j-i);
		
//		List<String> selectFields=new ArrayList<String>();
//		for(MultiQuerySelectField msf:obj.getSelect().getSelectFields()){
//			selectFields.add(msf.getFieldName());
//		}
//		
		return new MultiQuerySqlElement(obj.getSqlWithNoAliaNameRepeat().toString(),mqp);

		
	}
	
	/**
	 * 多个对象复杂查询主方法，用于解析并分解queryJson，交给各个处理函数分别生成各部分sql，并最终组装sql预处理语句
	 * 
	 * @param queryJson
	 *            ：包括select\from\where\group\having\order五部分，分别在枚举类QlComponent中定义
	 * @return MultipleQuery：select(MultipleSelect),from(StringBuilder),where(
	 *         StringBuilder),
	 *         group(StringBuilder),having(StringBuilder),order(StringBuilder),
	 *         queryParams(List<MultipleQueryParam>)
	 * @throws Exception
	 */
	public MultiQuery buildMultipleQuery(String queryJson) throws Exception {

		MultiQuery query = new MultiQuery();

		HashMap<String, Object> hmQuery = (HashMap<String, Object>) JacksonMapper
				.readValue(queryJson, JacksonMapper.hmObj);

		for (SqlComponent e : SqlComponent.values()) {// 按照定义好的sql语句组成部分枚举类，依次解析各个部分的json
			String SqlComponentName = e.toString().toLowerCase();
			if (hmQuery.containsKey(SqlComponentName)) {

				Object SqlComponentObj = hmQuery.get(SqlComponentName);
				if (isJsonObject(SqlComponentObj)) {// 多个对象
					String sqlJson = JacksonMapper.toJson(SqlComponentObj);
					buildMultipleQueryAdapter(e, sqlJson, query);

				} else {// 单个对象
					buildSingleQueryAdapter(e, SqlComponentObj.toString(),
							query);
				}

			}
		}

		return query;
	}

	/**
	 * 判断对象是否是json对象
	 * 
	 * @param obj
	 * @return
	 */
	private boolean isJsonObject(Object obj) {
		if (obj.toString().startsWith("{")&&obj.toString().endsWith("}")) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 判断对象是否是json数组
	 * 
	 * @param obj
	 * @return
	 */
	private boolean isJsonArray(Object obj) {
		if (obj.toString().startsWith("[")&&obj.toString().endsWith("]")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 单个对象复杂查询适配器
	 * 
	 * @param SqlComponent
	 * @param valueJson
	 * @param query
	 * @throws Exception
	 */
	private void buildSingleQueryAdapter(SqlComponent SqlComponent,
			String valueJson, 
			MultiQuery query) throws Exception {
		switch (SqlComponent) {
		case SELECT:
			query.setSelect(buildSingleSelect(valueJson));
			break;
		case FROM:
			MultiQuery fromQuery = buildSingleFrom(valueJson);
			query.setFrom(fromQuery.getFrom());
			
			query.setWhere(fromQuery.getWhere());
			
			break;
		case WHERE:
			MultiQuery whereQuery = buildCondition(valueJson);

			query.setWhere(whereQuery.getCondition());
			
			// 将子查询的查询参数置入上层查询的参数列表
			if (whereQuery.getQueryParams() != null
					&& whereQuery.getQueryParams().size() > 0) {
				query.setQueryParams(whereQuery.getQueryParams());
			}
			break;
		case GROUP:
			query.setGroup(buildGroup(valueJson));
			break;
		case HAVING:
			MultiQuery havingQuery = buildCondition(valueJson);

			query.setHaving(havingQuery.getCondition());
			// 将子查询的查询参数置入上层查询的参数列表
			if (havingQuery.getQueryParams() != null
					&& havingQuery.getQueryParams().size() > 0) {
				query.setQueryParams(havingQuery.getQueryParams());
			}
			break;
		case ORDER:
			query.setOrder(buildSingleOrder(valueJson));
			break;
		default:
			break;
		}

	}

	/**
	 * 多个对象复杂查询适配器
	 * 
	 * @param SqlComponent
	 * @param valueJson
	 * @param query
	 * @throws Exception
	 */
	private void buildMultipleQueryAdapter(SqlComponent SqlComponent,
			String valueJson,
			MultiQuery query) throws Exception {
		switch (SqlComponent) {
		case SELECT:
			query.setSelect(buildMultipleSelect(valueJson));
			break;
		case FROM:
			MultiQuery fromQuery = buildMultipleFrom(valueJson);
			query.setFrom(fromQuery.getFrom());
			query.setWhere(fromQuery.getWhere());
			
			// 将子查询的查询参数置入上层查询的参数列表
			if (fromQuery.getQueryParams() != null
					&& fromQuery.getQueryParams().size() > 0) {
				query.setQueryParams(fromQuery.getQueryParams());
			}

			break;
		case WHERE:
			MultiQuery whereQuery = buildCondition(valueJson);
			
			query.setWhere(whereQuery.getCondition());
			// 将子查询的查询参数置入上层查询的参数列表
			if (whereQuery.getQueryParams() != null
					&& whereQuery.getQueryParams().size() > 0) {
				query.setQueryParams(whereQuery.getQueryParams());
			}

			break;
		case GROUP:
			query.setGroup(buildGroup(valueJson));
			break;
		case HAVING:
			MultiQuery havingQuery = buildCondition(valueJson);

			query.setHaving(havingQuery.getCondition());
			// 将子查询的查询参数置入上层查询的参数列表
			if (havingQuery.getQueryParams() != null
					&& havingQuery.getQueryParams().size() > 0) {
				query.setQueryParams(havingQuery.getQueryParams());
			}
			break;
		case ORDER:
			query.setOrder(buildMultipleOrder(valueJson));
			break;
		default:
			break;
		}
	}

	/**
	 ***************************************from start********************************************************
	 */
		
	/**
	 * 单个对象查询复杂查询（from部分）
	 * 
	 * @param fromJson
	 * @return
	 * @throws Exception
	 */
	private MultiQuery buildSingleFrom(String fromJson) throws Exception {
		MultiQuery mq=buildQueryEntity("o", fromJson);
		mq.setFrom(mq.getFrom().append(" o"));
		return mq;
	}

	/**
	 * 多个对象复杂查询（from部分）
	 * 
	 * @param fromJson
	 *            示例 简单多对象{"a":"organ","b"："user"
	 *            带子查询的对象{"aaa":{select:{},from:{},...}
	 *            join关联的对象"$ij":{"$lj":{"u"
	 *            :"user","o":"organ","$on":{"o.id":"r.id"
	 *            ,...},"t":"test","$on":{"t.xxx":"o.aaa",....})
	 * @return
	 * @throws Exception
	 */
	private MultiQuery buildMultipleFrom(String fromJson) throws Exception {
		MultiQuery fromQuery = new MultiQuery();// 由于可能存在子查询，所以使用复杂查询视图对象存放拼接的sql和子查询的参数
		StringBuilder sql = new StringBuilder();

		LinkedHashMap<String, Object> hmSelect = (LinkedHashMap<String, Object>) JacksonMapper
				.readValue(fromJson, JacksonMapper.linkedHmObj);

		Set<String> keySet = hmSelect.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object valueObj = hmSelect.get(key);

			StringBuilder sb = new StringBuilder();

			if (key.startsWith("$") && key.toLowerCase().endsWith("j")) {// 处理join对象

				MultiQuery joinQuery = buildQueryJoinEntity(JacksonMapper
						.toJson(valueObj));

				joinQuery.setJoinType(SqlDefinedSign.valueOf(key.toLowerCase()).signExpress());

				sb.append(joinQuery.getJoinSql());// 获取join的sql并绑定到from中

				fromQuery.setQueryParams(joinQuery.getQueryParams());// 将join中的条件参数传递给父查询

			} else if (isJsonObject(valueObj)) {// 处理带子查询的对象
				sb.append(buildSubQueryEntity(key, JacksonMapper
						.toJson(valueObj), fromQuery));

			} else {
				// 处理简单对象
				if(valueObj.toString().split("\\.").length>1){//自动通过relation表产生关联的特殊情况，"c":"user.organ"
					
					MultiQuery relatedSubQuery=buildRelationEntity(key,valueObj.toString());
					
					//使用构建的关联子查询的json字符串进行sql解析
					sb.append(buildSubQueryEntity(key, relatedSubQuery.getFrom().toString(), fromQuery));
										
					//在where条件中添加子查询对象与关联对象的关联条件
					fromQuery.setWhere(relatedSubQuery.getWhere());
					
				}else{//一般的情况
					MultiQuery singleFromQuery=buildQueryEntity(key, valueObj.toString());
					sb.append(singleFromQuery.getFrom())
							.append(" ").append(key);// 增加别名,a.xxxx ccc
	
					fromQuery.setWhere(singleFromQuery.getWhere());
				}
			}
			if (sql.length()>0) {
				sql.append(",");
			}
			sql.append(sb);
		}
		fromQuery.setFrom(sql);

		return fromQuery;
	}

	/**
	 * 对from中join下的实体进行转换
	 * 
	 * @param fromEntityJoin
	 *            ：{"$ij":{"u":"user","o":"organ","$on":{"o.id":"r.id",...}}|"u":
	 *            "user","t":"test","$on":{"t.id":"r.id"}}
	 * 
	 * @return MultipleQuery
	 * @throws Exception
	 */
	private MultiQuery buildQueryJoinEntity(String joinEntityJson) throws Exception {
		MultiQuery joinQuery = new MultiQuery();

		LinkedHashMap<String, Object> hmSelect = (LinkedHashMap<String, Object>) JacksonMapper
				.readValue(joinEntityJson, JacksonMapper.linkedHmObj);

		Set<String> keySet = hmSelect.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key =  it.next();
			Object valueObj = hmSelect.get(key);

			if (key.startsWith("$") && key.toLowerCase().endsWith("j")) {// 处理join对象

				MultiQuery subJoinQuery = buildQueryJoinEntity(JacksonMapper
						.toJson(valueObj));

				subJoinQuery.setJoinType(SqlDefinedSign.valueOf(key.toLowerCase()).signExpress());

				setQueryJoinEntity(joinQuery, subJoinQuery.getJoinSql());// 将子join作为一个父join的一个对象

				joinQuery.setQueryParams(subJoinQuery.getQueryParams());

			} else if (key.equalsIgnoreCase(SqlDefinedSign.$on.toString())) {// 处理on,处理方式同where

				MultiQuery onQuery = buildCondition(JacksonMapper
						.toJson(valueObj));

				joinQuery.setOn(onQuery.getCondition());
				
				joinQuery.setQueryParams(onQuery.getQueryParams());

			} else if (isJsonObject(valueObj)) {// 处理带子查询的对象

				StringBuilder subEntity = buildSubQueryEntity(key,
						JacksonMapper.toJson(valueObj), joinQuery);

				setQueryJoinEntity(joinQuery, subEntity);// 将子对象作为一个父join的一个对象

			} else {// 处理简单对象
				if(valueObj.toString().split("\\.").length>1){//自动通过relation表产生关联的特殊情况，"c":"user.organ"
					
					MultiQuery relatedSubQuery=buildRelationEntity(key,valueObj.toString());
					
					//使用构建的关联子查询的json字符串进行sql解析
					StringBuilder subEntity = buildSubQueryEntity(key,
							relatedSubQuery.getFrom().toString(), joinQuery);

					setQueryJoinEntity(joinQuery, subEntity);// 将子对象作为一个父join的一个对象
										
					//在on条件中添加子查询对象与关联对象的关联条件
					joinQuery.setOn(relatedSubQuery.getWhere());
					
				}else{//一般的情况							
					MultiQuery mq = buildQueryEntity(key,
							valueObj.toString());

					if (mq.getFrom().equals(""))
						continue;
					// 将对象放入joinQuery
					setQueryJoinEntity(joinQuery, mq.getFrom().append(" ").append(key));
					joinQuery.setOn(mq.getWhere());
				}


			}

		}

		return joinQuery;

	}

	/**
	 * //将别名依次放入entityA和entityB，因为一个join只能关联2个对象
	 * 
	 * @param joinQuery
	 * @param joinEntity
	 * @throws Exception
	 */
	private void setQueryJoinEntity(MultiQuery joinQuery,
			StringBuilder joinEntity) throws Exception {
		if (joinQuery.getJoinEntityA() == null
				|| joinQuery.getJoinEntityA().equals("")) {
			joinQuery.setJoinEntityA(joinEntity);// 别名,a.xxxx ccc
		} else if (joinQuery.getJoinEntityB() == null
				|| joinQuery.getJoinEntityB().equals("")) {
			joinQuery.setJoinEntityB(joinEntity);
		}
	}

	/**
	 * 将from中的实体名进行转换，并将实体别名与数据表列名+属性类型的映射关系存入hmEntity
	 * 
	 * @param alias
	 * @param entityName
	 * @return
	 * @throws Exception
	 */
	private MultiQuery buildQueryEntity(String alias, String entityName) throws Exception {
		StringBuilder sql = new StringBuilder();
		StringBuilder logicDel = new StringBuilder();
		MultiQuery mq=new MultiQuery();
		if (hmEntity.containsKey(alias) &&!entityName.equalsIgnoreCase("relation")&&!alias.equalsIgnoreCase("r")) {
			throw new Exception(
					"entity alias ("
							+ alias
							+ ") was found repeated when build 'from' component of multipleQuery jsonData");
		}

		Class<?> clazz=ObjInfoCache.getObjectClass(entityName);

		ObjectInfoVo objectInfo = ObjInfoCache.getObjectInfo(clazz);

		if (objectInfo == null || objectInfo.getTable() == null) {
			logger.debug("entity {} has not been defined in objInfo",
					entityName);
			throw new Exception("entity " + entityName
					+ " has not been defined in objInfo");

		}
		sql.append(objectInfo.getTable().name());

		// 存储字段名和列名+字段类型的映射
		HashMap<String, MultiQueryField> fieldHm = new HashMap<String, MultiQueryField>();
		for (ObjectAttributeVo oav : objectInfo.getAttributeList()) {
			if (oav.getColumn() == null)
				continue;
			if(oav.getName().equalsIgnoreCase(logicDelField)){
				logicDel.append(alias).append(".").append(oav.getColumn().name()).append("=0");
			}
			fieldHm.put(oav.getName(), new MultiQueryField(oav.getColumn()
					.name(), oav.getClazz()));
		}

		// 建立对象别名和查询对象信息映射关心
		hmEntity.put(alias, new MultiQueryEntity(entityName,fieldHm));
		mq.setFrom(sql);

		mq.setWhere(logicDel);
			
		return mq;
	}
	/**
	 * 遇到from中出现"c":"entityA.entityB"这种情况，则自动识别当前对象，并构建其与relation的子查询对象json，同时生成子查询对象与关联对象的where条件关联
	 * @param alias
	 * @param entitys
	 * @return
	 * @throws Exception
	 */
	public MultiQuery buildRelationEntity(String alias,String entitys) throws Exception{
		
		MultiQuery relatedSubQuery=new MultiQuery();
		//构建entityName与entityAlias的映射关系
		HashMap<String,String> hmEntityAlias = new HashMap<String,String>();
		Set<String> keySet=hmEntity.keySet();
		for(Iterator<String> it=keySet.iterator();it.hasNext();){
			String key=it.next();
			MultiQueryEntity mqe=hmEntity.get(key);
			if(mqe.getEntityName()!=null){//entityName为空的，是子查询的select Field
				hmEntityAlias.put(mqe.getEntityName(), key);
			}
		}
		//定义并为构建关联子查询的参数赋值
		String fid,entityA,entityB,entityCurrent,rid,entityRelatedAlias;
		String[] entityArr=entitys.split("\\.");
		entityA=entityArr[0];
		entityB=entityArr[1];
		if(hmEntityAlias.containsKey(entityA)){
			entityRelatedAlias=hmEntityAlias.get(entityA);
			entityCurrent=entityB;
			rid="nid";
			fid="pid";
		}else if(hmEntityAlias.containsKey(entityB)){
			entityRelatedAlias=hmEntityAlias.get(entityB);
			entityCurrent=entityA;
			rid="pid";
			fid="nid";
		}else{
			throw new Exception("None entity of "+entityA+",and "+entityB+" has been defined before");
		}
				
		relatedSubQuery.setFrom(new StringBuilder(String.format(relatedSubQueryJsonFormat,alias,fid,alias,entityCurrent,rid,alias,entityA,entityB,ruleTypeId)));		
		relatedSubQuery.setWhere(new StringBuilder(String.format(relatedSubQueryEntityWhereFormat,entityRelatedAlias,alias)));
		
		
		logger.debug("构建关联子查询json:{}",relatedSubQuery.getFrom());
		
		logger.debug("构建关联子查询与关联对象的where条件关联关系:{}",relatedSubQuery.getWhere());
		
		return relatedSubQuery;
	}
	/**
	 * 对from中的子查询实体名转换，并将实体别名与数据表列名+属性类型的映射关系存入hmEntity
	 * 
	 * @param alias
	 * @param entityName
	 * @return
	 * @throws Exception
	 */
	private StringBuilder buildSubQueryEntity(String alias,
			String subQueryJson, MultiQuery parentQuery) throws Exception {

		StringBuilder sb = new StringBuilder();

		MultiQuery submq = buildMultipleQuery(subQueryJson);// 子查询是一个完整的查询sql，所以调用queryMultiple

		//子查询的sql:(select * from ... where .. group by ... having ... order by ...) alias
		sb.append("(").append(submq.getSql()).append(") ").append(alias);// 
		
		//子查询的select field中的参数传递给父查询（如decode(a.xx,'1',"是","2","否","")会被转化为decode(a.XXX,?,?,?,?,?)，具体参数值通过查询参数形式填入）
		if(submq.getSelect().getQueryParams()!=null&&submq.getSelect().getQueryParams().size()>0){
			parentQuery.setQueryParams(submq.getSelect().getQueryParams());// 将子查询select部分，函数里面的参数传递给父查询
		}
		
		//子查询的条件查询参数传递给父查询
		parentQuery.setQueryParams(submq.getQueryParams());// 将子查询的条件参数传递给父查询

		// 将子查询获得的查询字段类型映射关系放到当前查询的缓存对象hmEntity中
		hmEntity.put(alias, new MultiQueryEntity(submq.getSelect().getSubQueryEntityAttributeMap()));

		return sb;
	}

	/**
	 *****************************************from end******************************************************
	 */
	/**
	 *****************************************select start******************************************************
	 */
	/**
	 * 用于单个对象复杂查询（Select部分）
	 * 
	 * @param selectFields
	 *            示例 aaa,bbb,ccc
	 * @return 
	 *         MultipleSelect
	 * @throws Exception
	 */
	private MultiQuerySelect buildSingleSelect(String selectFields) throws Exception {
		MultiQuerySelect ms = new MultiQuerySelect();
		String[] fieldArr = selectFields.split(",");

		for (String field : fieldArr) {
			
			MultiQuerySelectField msf=getSelectField(field);
			
			//遇到a.*或*的情况转化为展示当前对象所有的字段
			if(msf.getFieldName().equals("*")){
				
				ms.setSelectFields(buildAllQueryField(msf.getEntityAlias()));

			}else{
				msf = buildQueryField(msf);					

				ms.setSelectField(msf);

			}
		}

		return ms;
	}

	/**
	 * 用于多表复杂查询（Select部分）
	 * 
	 * @param selectJson
	 *            示例 简单多对象字段{"aaa":"a.dddd","bbb":"b.ssss"}
	 *            带函数的多对象字段{"aaa":{$fn:"a.ccc"},"bbb":{"$sum":"b.xxxx"}}
	 *            带distinct的多字段对象{"$dis":{"aaa":"b.aaaa","bbb":"c.xxxx"}}
	 * @return 
	 *         MultipleSelect:selectSql(Stringbuilder),fields(HashMap<String,Class
	 *         <?>>)[字段别名：字段类型]
	 * @throws Exception
	 */
	private MultiQuerySelect buildMultipleSelect(String selectJson) throws Exception {
		MultiQuerySelect ms = new MultiQuerySelect();

		LinkedHashMap<String, Object> hmSelect = (LinkedHashMap<String, Object>) JacksonMapper
				.readValue(selectJson, JacksonMapper.linkedHmObj);

		Set<String> keySet = hmSelect.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object valueObj = hmSelect.get(key);

			if(key.startsWith("$")){//函数:$fn:[xxxxx,{"aa":a.xxx}]
				if(SqlDefinedSign.valueOf(key.toLowerCase())==null) continue;//函数未定义，则跳出本次循环
				
				if(!isJsonArray(valueObj)) continue;//函数后面的value值必须是数组形式
				
				MultiQuerySelect msf=buildFunction(SqlDefinedSign.valueOf(key.toLowerCase()),JacksonMapper.toJson(valueObj));
				
				ms.setSelectFields(msf.getSelectFields());
				
				ms.setQueryParams(msf.getQueryParams());
				
			}else if(isJsonObject(valueObj)) {//查询字段的表达式复杂，如aaa:{$fn:[xxxx]}
				
				MultiQuerySelect subMs = buildMultipleSelect(JacksonMapper
						.toJson(valueObj));// 嵌套分解

				if(subMs.getSelectFields()!=null&&subMs.getSelectFields().size()>0){
					MultiQuerySelectField msf=subMs.getSelectFields().get(0);
					msf.setFieldName(key);
					ms.setSelectField(msf);
				}
				ms.setQueryParams(subMs.getQueryParams());

				
			}else{// 处理简单字段
				String value=valueObj.toString();
				
				MultiQuerySelectField msf= getSelectField(value);
				
				if(msf.getFieldName().equals("*")){//遇到a.*或*的情况将转化为展示当前对象所有的字段
					
					ms.setSelectFields(buildAllQueryField(msf.getEntityAlias()));

				}else{
					msf = buildQueryField(msf);
					if(msf.getColName()==null) continue;
					msf.setFieldName(key);//设置字段别名，若不设置，则别名默认为字段属性名

					ms.setSelectField(msf);												// ccc

				}
			}

		}
		return ms;
	}

	
	private MultiQuerySelect buildFunction(SqlDefinedSign functionSign,String functionParams) throws Exception{

		StringBuilder sql = new StringBuilder();
		MultiQuerySelect ms=new MultiQuerySelect();

		Object[] valueObjArr = JacksonMapper
				.readValue(functionParams, Object[].class);
		
		for(Object valueObj:valueObjArr){
			
			StringBuilder sb = new StringBuilder();
			
			if(!isJsonObject(valueObj)){//函数值不是json对象，则整个函数形如$fn:[xxx]，若该函数在上级sql中只能通过为其定义别名调用，默认以integer形式调用
				MultiQuerySelectField msf = getSelectField(valueObj.toString());
				
				msf=buildQueryField(msf);					
	
				if (msf.getColName()==null){//转换得到的select field没找到数据库表的列名，表示函数的参数时一个固定值

					sb.append("?");
					
					ms.setQueryParam(new PlaceholderParam(valueObj.toString(),String.class));//将函数中的参数值放到最终的预处理参数队列中
					
				}else{//转换得到的select field不为空，表示函数的参数是一个字段
												
					sb.append(msf.getEntityAlias()).append(".").append(msf.getColName());
				}
				
			}else{//函数值是json对象,则函数形如：$fn:[{},{}]。
				MultiQuerySelect subMs = buildMultipleSelect(JacksonMapper
						.toJson(valueObj));// 嵌套分解

				sb.append(subMs.getSql());// a.x 
				
				ms.setQueryParams(subMs.getQueryParams());

			}
			
			if(sql.length()>0){sql.append(",");}
			
			sql.append(sb);
			
		}
		String colName=functionSign.signExpress().replace("?", sql);
		MultiQuerySelectField msf=new MultiQuerySelectField();
		msf.setFieldName(functionSign.signExpress().replace("?","").replace("(", "").replace(")", "").toLowerCase());
		msf.setColName(colName);
		switch(functionSign){
		case $cnt:
		case $sum:
		case $avg:
			msf.setFieldType(Integer.class);
			break;
		default:
			msf.setFieldType(String.class);
			break;
		}

		ms.setSelectField(msf);

		return ms;

	}
	
	private MultiQuerySelectField getSelectField(String field) throws Exception{
		String[] attrs = field.split("\\.");// 分隔a.xxx
		String entityAlias;// 对象别名
		String attrName;// 属性名
		if (attrs.length > 1) {
			entityAlias = attrs[0];// 对象别名
			attrName = attrs[1];// 属性名
		} else {
			entityAlias = "o";
			attrName = field;// 属性名
		}
		
		return new MultiQuerySelectField(entityAlias,attrName);
	}
	/**
	 * 处理单个select字段，根据对象属性名，查找对象的column标签，获得对应的数据表字段名称以及字段类型（供上级查询作为运算子调用）
	 * 
	 * @param entityAttr
	 *            a.attrName
	 * @return 
	 *         MultipleSelectField:selectField(StringBuilder),fieldType(Class<?>)
	 *         ,示例：a.columnName,Integer
	 * @throws Exception
	 */
	private MultiQuerySelectField buildQueryField(MultiQuerySelectField msf) throws Exception {

		if (hmEntity.containsKey(msf.getEntityAlias())) {// 判断属性对应对象是否在from体中
			
			if(msf.getFieldName().equals("*")){//对于a.*，只验证其对象是否存在并构建sql
				
				msf.setColName(msf.getFieldName());// a.*
				
			}else{
				MultiQueryEntity mqe = hmEntity.get(msf.getEntityAlias());// 获取查询对象参数		
				
				MultiQueryField attribute = mqe.getFieldAttribute(msf.getFieldName());// 获取属性相关信息
				
				if (attribute != null) {// 得到属性对应的数据表字段名
					msf.setColName(attribute.getColName());// 返回属性对应的数据表字段名sql
					msf.setFieldType(attribute.getFieldType());// 返回属性对应的数据类型
				}
			}

		}
		return msf;
	}
	
	private List<MultiQuerySelectField> buildAllQueryField(String entityAlias) throws Exception {
			
			List<MultiQuerySelectField> msfList = new ArrayList<MultiQuerySelectField>();
					
			MultiQueryEntity mqe = hmEntity.get(entityAlias);// 获取查询对象		
			
			HashMap<String,MultiQueryField> attributes = mqe.getFieldAttribute();
			
			Set<String> keySet = attributes.keySet();
			
			for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
				
				String key = it.next();
				
				MultiQueryField attribute = attributes.get(key);
				
				msfList.add(new MultiQuerySelectField(entityAlias,attribute.getColName(),key,attribute.getFieldType()));

			}
					
			return msfList; 
	}
	/**
	 *****************************************select end******************************************************
	 */
	/**
	 *****************************************condition start******************************************************
	 */
	/**
	 *处理查询条件（包括where,having及join部分的on）
	 * @param whereJson
	 * 			示例：
	 * 				{"or":{"a.xxx":"b.bbb","a.ccc":"xxxcv","$and":{"a.ccc":{"$gt":"11"},"b.ccc":"$null"},"b.ddd":{"$ne":"xxxx"}}}以or作为第一层条件关联方式	 * 				
	 * 				{"a.xxx":"b.bbb","a.ccc":"xxxcv","$or":{"a.ccc":{"$gt":"11"},"b.ccc":"$null"},"b.ddd":{"$ne":"xxxx"}}以and进行条件关联
	 * @return
	 * @throws Exception
	 */
	private MultiQuery buildCondition(String conditionJson) throws Exception {

		HashMap<String, Object> hmWhere = (HashMap<String, Object>) JacksonMapper
				.readValue(conditionJson, JacksonMapper.hmObj);

		MultiQuery conditionQuery;
		
		if (hmWhere.keySet().size()==1 && hmWhere.containsKey(SqlDefinedSign.$or.toString())) {
			conditionQuery = buildsubCondition(SqlDefinedSign.$or,
					JacksonMapper.toJson(hmWhere.get(SqlDefinedSign.$or
							.toString())));
			//为or外面加括号
			conditionQuery.setCondition(new StringBuilder("(").append(conditionQuery.getCondition()).append(")"));

		}else {
			conditionQuery = buildsubCondition(SqlDefinedSign.$and,
					conditionJson);
		}

		return conditionQuery;
	}

	/**
	 * 处理各层次关联表达式，每一个层次嵌套做一次递归
	 * @param op：条件表达式的关联符or/and
	 * @param conditionJson
	 * @return
	 * @throws Exception
	 */
	private MultiQuery buildsubCondition(SqlDefinedSign op, String conditionJson) throws Exception {
		MultiQuery query = new MultiQuery();
		StringBuilder sql = new StringBuilder();

		HashMap<String, Object> hmSelect = (HashMap<String, Object>) JacksonMapper
				.readValue(conditionJson, JacksonMapper.hmObj);

		Set<String> keySet = hmSelect.keySet();
		
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object valueObj = hmSelect.get(key);
			
			StringBuilder sb = new StringBuilder();

			if (key.equalsIgnoreCase(SqlDefinedSign.$or.toString())
					|| key.equalsIgnoreCase(SqlDefinedSign.$and.toString())) {//下一层级关联表达式
				
				MultiQuery subConditionQuery = buildsubCondition(SqlDefinedSign.valueOf(key.toLowerCase()), JacksonMapper
						.toJson(valueObj));
				
				sb.append("(").append(subConditionQuery.getCondition()).append(")");//把下一层条件的关联表达式用括号放在一起
				
				query.setQueryParams(subConditionQuery.getQueryParams());
				
			} else {//一般条件表达式：Key +运算子 +Value
										
				if (isJsonObject(valueObj)) {// 运算子复杂情况，例如a.xxx:{"$gt":1}
					
					MultiQuery expressQuery= buildConditionExpression(key, JacksonMapper.toJson(valueObj));
					sb.append(expressQuery.getCondition());
					
					query.setQueryParams(expressQuery.getQueryParams());

				} else {// 运算子简单情况，例如 a.xxx：xxxx
					MultiQuerySelectField msf =getSelectField(key);
					
					msf = buildQueryField(msf);//转换条件表达式的key部分
					
					if(msf.getColName()==null) continue;				
					
					String value = valueObj.toString();
					
					sb.append(msf.getEntityAlias()).append(".").append(msf.getColName());//a.columnName
					
					if (value.equalsIgnoreCase(SqlDefinedSign.$null.toString())
							||value.equalsIgnoreCase(SqlDefinedSign.$notnull.toString())) {//null或not null
						//拼接is null 或is not null
						sb.append(SqlDefinedSign.valueOf(value.toLowerCase()).signExpress());

					}else{ 
						MultiQuerySelectField msf2 =getSelectField(value);
						msf2 = buildQueryField(msf2);//转换条件表达式的value部分
						
						if (msf2.getColName()!=null) {// 判断value部分内容是否是对象属性字段，
							//拼接=b.columnName2
							sb.append("=").append(msf2.getEntityAlias()).append(".").append(msf2.getColName());
							
						} else {
							//拼接带占位符的表达式：=?
							sb.append("=").append("?");
							
							// 设置查询参数（参数名:数据、字段类型）
							query.setQueryParam(new PlaceholderParam(value, msf
									.getFieldType()));
						}
					}
					
				}
			}
			//添加条件表达式间的连接符and/or
			if(sql.length()>0){
				sql.append(op.signExpress());
			}
			
			sql.append(sb);

		}
		
		query.setCondition(sql);
		return query;
	}

	/**
	 * 处理一个条件表达式，若一个条件表达式包含多个条件，用and连接，如a.xxx:{"$gt":1,"$lt":3}转为a.xxx>1 and a.xxx<3
	 * @param msf
	 * @param conditionJson
	 * @return
	 * @throws Exception
	 */
	private MultiQuery buildConditionExpression(String conditionKey,String conditionJson)
			throws Exception {		
		
		MultiQuerySelectField msf = getSelectField(conditionKey);
		msf=buildQueryField(msf);//转换条件表达式的key部分
		
		String field=conditionKey;
		Class<?> fieldType=String.class;
		
		if(msf.getColName()!=null){
		
			field = msf.getEntityAlias().concat(".").concat(msf.getColName());//转化后的key部分
		
			fieldType=msf.getFieldType();
		}
					
		StringBuilder sql = new StringBuilder();
		
		MultiQuery query = new MultiQuery();

		LinkedHashMap<String, Object> hmExpression = (LinkedHashMap<String, Object>) JacksonMapper
				.readValue(conditionJson, JacksonMapper.linkedHmObj);

		Set<String> keySet = hmExpression.keySet();
		
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			Object valueObj = hmExpression.get(key);			
			StringBuilder sb = new StringBuilder();
			
			if (SqlDefinedSign.valueOf(key.toLowerCase()) != null) {//是自定的关键字
				
				if(isJsonArray(valueObj)){//值是json数组，则当前处理的是函数
					
					MultiQuerySelect ms=buildFunction(SqlDefinedSign.valueOf(key.toLowerCase()),JacksonMapper.toJson(valueObj));
					
					sb.append(ms.getSql());//function SQL
											
					if(ms.getQueryParams()!=null){//当前表达式的key部分是函数
						query.setQueryParams(ms.getQueryParams());
					}
					
				}else{//不是json数组，则处理的是运算子及值
					sb.append(field);//加key部分

					if(isJsonObject(valueObj)){//值是json对象，针对 in(select distinct c from xxxx)这种查询条件里面带子查询的
							
						MultiQuery submq = buildMultipleQuery(JacksonMapper.toJson(valueObj));// 子查询是一个完整的查询sql，所以调用queryMultiple
						//加运算子及值部分	
						sb.append(SqlDefinedSign.valueOf(key.toLowerCase()).signExpress().replace("?", submq.getSql()));
						

						//将子查询select部分的参数传给父查询					
						query.setQueryParams(submq.getSelect().getQueryParams());
						// 将子查询的条件参数传递给父查询	
						query.setQueryParams(submq.getQueryParams());					
						
					}else{//值不是json格式字符串
												
						MultiQuerySelectField msf2 =getSelectField(valueObj.toString());
						msf2 = buildQueryField(msf2);//转换条件表达式的value部分
						// 判断value部分内容是否是对象属性字段，
						if (msf2.getColName()!=null) {//是对象属性
							//拼接 运算子+b.columnName2
							String field2=msf2.getEntityAlias().concat(".").concat(msf2.getColName());
							
							sb.append(SqlDefinedSign.valueOf(key.toLowerCase()).signExpress().replace("?", field2));
							
						} else {
							//对于in查询，根据in的参数个数，设置$in所对应的占位符?的个数，并将in的参数按照逗号分开作为占位符的传入参数
							if(SqlDefinedSign.valueOf(key).equals(SqlDefinedSign.$in)||SqlDefinedSign.valueOf(key).equals(SqlDefinedSign.$nin)){
								String[] values=valueObj.toString().split(",");
								StringBuffer prepareInSql=new StringBuffer();
								for(String inValue:values){
									if(prepareInSql.length()>0){
										prepareInSql.append(",");
									}
									prepareInSql.append("?");
									query.setQueryParam(new PlaceholderParam(inValue, fieldType));
								}
								String inExpress=SqlDefinedSign.valueOf(key.toLowerCase()).signExpress().replace("?", prepareInSql.toString());
								sb.append(inExpress);
							}else{							
								//加运算子及值部分	
								sb.append(SqlDefinedSign.valueOf(key.toLowerCase()).signExpress());
								
								//设置值对应的查询参数
								query.setQueryParam(new PlaceholderParam(valueObj.toString(), fieldType));
							}
						}
						

					}
				}
			}
			
			if(sql.length()>0){
				sql.append(SqlDefinedSign.$and.signExpress());
			}
			
			sql.append(sb);

		}
		if(sql.indexOf(SqlDefinedSign.$and.signExpress())>0){//单个字段的表达式中带and（如a.xx:{"$gt":1,"$lt":2}会转为a.xx>1 and a.xx<2）时，用括号包裹
			query.setCondition(new StringBuilder("(").append(sql).append(")"));
		}else{
			query.setCondition(sql);
		}
		
		return query;
	}

	/**
	 *****************************************where end******************************************************
	 */
	/**
	 *****************************************group start******************************************************
	 */
	/**
	 * 复杂查询group部分
	 * @param groupJson：a.xxx,b.xxx
	 * @return
	 * @throws Exception
	 */
	private String buildGroup(String groupJson) throws Exception {
		MultiQuerySelect ms = new MultiQuerySelect();
		String[] attrs = groupJson.split(",");// 分隔a.xxx
		for (String attr : attrs) {
			MultiQuerySelectField msf = getSelectField(attr);
			msf=buildQueryField(msf);
			msf.setFieldName("");
			ms.setSelectField(msf);
		}

		return ms.getSql();
	}

	/**
	 *****************************************group end******************************************************
	 */
	/**
	 *****************************************order start******************************************************
	 */
	/**
	 * 复杂查询order部分（简单排序，默认升序）
	 * @param OrderJson:a.xxx,b.xccc
	 * @return
	 * @throws Exception
	 */
	private StringBuilder buildSingleOrder(String OrderJson) throws Exception {
		StringBuilder sql = new StringBuilder();
		String[] attrs = OrderJson.split(",");// 分隔a.xxx
		for (String attr : attrs) {
			MultiQuerySelectField msf = getSelectField(attr);
			msf=buildQueryField(msf);
			if(msf.getColName()==null) continue;
			if (sql.length()>0) {
				sql.append(",");
			}
			sql.append(msf.getEntityAlias()).append(".").append(msf.getColName()).append(OrderSort.asc.sortExpress());
		}

		return sql;
	}

	/**复杂查询order部分（复杂排序：自定义排序）
	 * @param OrderJson：{"a.xxx":"asc","b.ccc":"desc"}
	 * @return
	 * @throws Exception
	 */
	private StringBuilder buildMultipleOrder(String OrderJson) throws Exception {
		StringBuilder sql = new StringBuilder();

		LinkedHashMap<String, Object> hmOrder = (LinkedHashMap<String, Object>) JacksonMapper
				.readValue(OrderJson, JacksonMapper.linkedHmObj);

		Set<String> keySet = hmOrder.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
			String key = it.next();
			String value = (String) hmOrder.get(key);

			MultiQuerySelectField msf = getSelectField(key);
			
			msf=buildQueryField(msf);
			
			if(msf.getColName()==null) continue;
			
			String sort;
			
			if(OrderSort.valueOf(value.toLowerCase())==null){
				sort=OrderSort.asc.sortExpress();
			}else{
				sort=OrderSort.valueOf(value.toLowerCase()).sortExpress();
			}
			
			if (sql.length()>0) {
				sql.append(",");
			}
			sql.append(msf.getEntityAlias()).append(".").append(msf.getColName()).append(sort);

		}

		return sql;
	}

	/**
	 *****************************************order end******************************************************
	 */
	
	public static void main(String[] args) {


			

	}
}
