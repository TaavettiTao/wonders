package com.wonders.frame.core.service.impl;

import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wonders.frame.core.model.vo.EntityProperty;
import com.wonders.frame.core.model.vo.SimplePage;
import com.wonders.frame.core.model.vo.SingleHqlElement;
import com.wonders.frame.core.model.vo.SingleModelParams;
import com.wonders.frame.core.model.vo.SingleQueryParams;
import com.wonders.frame.core.model.vo.ReturnObj;
import com.wonders.frame.core.model.vo.ValidError;
import com.wonders.frame.core.model.vo.ReturnObjInfo.ErrorType;
import com.wonders.frame.core.service.SingleCrudService;
import com.wonders.frame.core.utils.JacksonMapper;
import com.wonders.frame.core.utils.ReflectUtil;
import com.wonders.frame.core.utils.SingleParamsConvertUtil;
import com.wonders.frame.core.utils.SqlBuilderUtil;
import com.wonders.frame.core.utils.ValidUtil;

/**
 * @author lushuaifeng
 *
 * @param <T>
 * @param <ID>
 */
public class SingleCrudServiceImpl<T,ID extends Serializable> implements SingleCrudService<T,ID> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	

	
	/**
	 * 将request传参绑定至用于表单处理的SingleModelParams对象
	 * @param clazz，对象class类型
	 * @param fields，需要展示或者隐藏的对象属性名，用逗号分隔
	 * @param show，展示（true）或隐藏（false）fields所指定的对象参数,默认为true
	 * @return ReturnObj<EntityProperty> data值为EntityProperty的返回对象
	 */
	public ReturnObj<EntityProperty> getProperty(Class<?> clazz, String fields,
			boolean show) {
		try {
			EntityProperty entityProperty = ReflectUtil.getEntityProperty(
					clazz, fields, show);
			return new ReturnObj<EntityProperty>(entityProperty);

		} catch (Exception e) {

			return new ReturnObj<EntityProperty>(e);

		}

	}

	
	public ReturnObj<List<T>> find(Class<?> clazz, HttpServletRequest request) {
		try {
			SingleQueryParams sqp = SingleParamsConvertUtil.getQueryParams(clazz, request);
			
			return find(sqp);
			
		} catch (Exception e) {
			return new ReturnObj<List<T>>(e);
		}
	}

	public ReturnObj<List<T>> find(SingleQueryParams sqp) {
		try {
			String method = "findAll";
			
			SingleHqlElement she=SqlBuilderUtil.getSingleHqlBuilder().buildSingleHql(sqp);
			
			Object obj = ReflectUtil.executeMethod(sqp.getObjName(), method, new Class[] {
					SingleHqlElement.class, Integer.class, Integer.class },
					new Object[] { she, sqp.getRange1(), sqp.getRange2()});
				
			List<T> rs=(List<T>)obj;
			
			return new ReturnObj<List<T>>(rs);
		} catch (Exception e) {
			return new ReturnObj<List<T>>(e);
		}
	}
	

	
	public ReturnObj<SimplePage<T>> findByPage(Class<?> clazz, HttpServletRequest request) {
		try {
			SingleQueryParams qp = SingleParamsConvertUtil.getQueryParams(clazz, request);
			
			return findByPage(qp);
			
		} catch (Exception e) {
			return new ReturnObj<SimplePage<T>>(e);
		}
	}

	public ReturnObj<SimplePage<T>> findByPage(SingleQueryParams sqp) {
		try {
			String method = "findByPage";
			SingleHqlElement she=SqlBuilderUtil.getSingleHqlBuilder().buildSingleHql(sqp);
			Object obj = ReflectUtil.executeMethod(sqp.getObjName(), method, new Class[] {
					SingleHqlElement.class, Integer.class, Integer.class },
					new Object[] { she, sqp.getRange1(), sqp.getRange2() });
				
			SimplePage<T> rs=(SimplePage<T>)obj;
			
			return new ReturnObj<SimplePage<T>>(rs);
		} catch (Exception e) {
			return new ReturnObj<SimplePage<T>>(e);
		}
	}
	

	public ReturnObj<Long> count(Class<?> clazz, HttpServletRequest request) {
		try {
			SingleQueryParams sqp = SingleParamsConvertUtil.getQueryParams(clazz, request);
			
			return count(sqp);
		} catch (Exception e) {
			return new ReturnObj<Long>(e);
		}
		
	}
	
	public ReturnObj<Long> count(SingleQueryParams sqp) {
		try{
			String method = "count";
			SingleHqlElement she=SqlBuilderUtil.getSingleHqlBuilder().buildSingleHql(sqp);
			Object obj = ReflectUtil.executeMethod(sqp.getObjName(), method, new Class[] {
					SingleHqlElement.class },
					new Object[] { she });
				
			Long rs=(Long)obj;
			
			return new ReturnObj<Long>(rs);
		} catch (Exception e) {
			return new ReturnObj<Long>(e);
		}
	}
	

	public ReturnObj<List<T>> findByIds(Class<?> clazz, List<ID> ids) {
		try {
			List<T> obj = (List<T>)ReflectUtil.executeMethod(clazz, "findByIds",
					new Class[] { Iterable.class }, new Object[] { ids });

			return new ReturnObj<List<T>>(obj); 
		} catch (Exception e) {
			return new ReturnObj<List<T>>(e); 
		}

	}

	public ReturnObj<T> get(Class<?> clazz, ID id) {
		try {
			Object obj = ReflectUtil.executeMethod(clazz, "findById",
					new Class[] { Serializable.class }, new Object[] { id });

			return new ReturnObj<T>((T)obj);

		} catch (Exception e) {
			return new ReturnObj<T>(e);
		}
	}


	public ReturnObj<Integer> removeAll(Class<?> clazz, HttpServletRequest request) {
		try {
			SingleQueryParams sqp = SingleParamsConvertUtil.getQueryParams(clazz, request);
			
			return removeAll(sqp);

		} catch (Exception e) {
			return new ReturnObj<Integer>(e);

		}

	}
	
	public ReturnObj<Integer> removeAll(SingleQueryParams sqp) {
		try {

			ReturnObj<List<T>> rsObj = find(sqp);
			
			if(rsObj.getData()==null){
				ReturnObj<Integer> ro=new ReturnObj<Integer>();
				ro.setInfo(rsObj.getInfo());
				return ro;
			}else{
				String method = "remove";
				
				Object obj = ReflectUtil.executeMethod(sqp.getObjName(), method, new Class[] {
					Iterable.class },
						new Object[] { rsObj.getData() });
				
				Integer successNum = (Integer) obj;
	
				if (successNum != null && successNum > 0) {
					return new ReturnObj<Integer>(successNum);
				} else {
					return new ReturnObj<Integer>(ErrorType.ZERO);
				}
			}

		} catch (Exception e) {
			return new ReturnObj<Integer>(e);

		}

	}

	public ReturnObj<Integer> removeById(Class<?> clazz, ID id) {
		try {

			Integer successNum = (Integer) ReflectUtil.executeMethod(clazz,
					"removeById", new Class[] { Serializable.class },
					new Object[] { id });

			if (successNum != null && successNum > 0) {
				return new ReturnObj<Integer>(successNum);
			} else {
				return new ReturnObj<Integer>(ErrorType.ZERO);

			}

		} catch (Exception e) {
			return new ReturnObj<Integer>(e);

		}

	}

	
	public ReturnObj<Integer> removeByIds(Class<?> clazz, List<ID> ids) {
		try {
			ReturnObj<List<T>> rsObj=findByIds(clazz,ids);
			Integer successNum = (Integer) ReflectUtil.executeMethod(clazz,
					"removeByIds", new Class[] { Iterable.class },
					new Object[] { ids });

			if (successNum != null && successNum > 0) {
				return new ReturnObj<Integer>(successNum);
			} else {
				return new ReturnObj<Integer>(ErrorType.ZERO);
			}

		} catch (Exception e) {
			return new ReturnObj<Integer>(e);

		}

	}
	

	
	public ReturnObj<Integer> deleteAll(Class<?> clazz, HttpServletRequest request) {
		try {
			SingleQueryParams sqp = SingleParamsConvertUtil.getQueryParams(clazz, request);
			
			return deleteAll(sqp);

		} catch (Exception e) {
			return new ReturnObj<Integer>(e);

		}

	}
	
	public ReturnObj<Integer> deleteAll(SingleQueryParams sqp) {
		try {

			ReturnObj<List<T>> rsObj = find(sqp);
			
			if(rsObj.getData()==null){
				ReturnObj<Integer> ro=new ReturnObj<Integer>();
				ro.setInfo(rsObj.getInfo());
				return ro;
			}else{
				String method = "deleteInBatch";
				
				ReflectUtil.executeMethod(sqp.getObjName(), method, new Class[] {
					Iterable.class },
						new Object[] { rsObj.getData() });
				
				return new ReturnObj<Integer>(true);

			}

		} catch (Exception e) {
			return new ReturnObj<Integer>(e);

		}

	}

	public ReturnObj<Integer> deleteById(Class<?> clazz, ID id) {
		try {

			ReflectUtil.executeMethod(clazz,
					"delete", new Class[] { Serializable.class },
					new Object[] { id });

			return new ReturnObj<Integer>(true);

		} catch (Exception e) {
			return new ReturnObj<Integer>(e);

		}

	}

	
	public ReturnObj<Integer> deleteByIds(Class<?> clazz, List<ID> ids) {
		try {
			ReturnObj<List<T>> rsObj = findByIds(clazz,ids);
			
			if(rsObj.getData()==null){
				ReturnObj<Integer> ro=new ReturnObj<Integer>();
				ro.setInfo(rsObj.getInfo());
				return ro;
			}else{
				String method = "deleteInBatch";
				
				ReflectUtil.executeMethod(clazz, method, new Class[] {
					Iterable.class },
						new Object[] { rsObj.getData() });
				
				return new ReturnObj<Integer>(true);

			}

		} catch (Exception e) {
			return new ReturnObj<Integer>(e);

		}

	}

	
	@Override
	public ReturnObj<T> saveOrUpdate(Class<?> clazz,
			HttpServletRequest request) {

		try {
			// 将前台传入参数组合为ModelParams对象
			SingleModelParams smp = SingleParamsConvertUtil.getModelParams(clazz, request);

			return saveOrUpdate(smp);

		} catch (Exception e) {
			
			return new ReturnObj<T>(e);
		}
	}

	public ReturnObj<T> saveOrUpdate(SingleModelParams smp) {
		try{
		// 转化查询参数至对象
			Object updateObj = JacksonMapper.convert(smp.getData(), smp.getObjClazz());
			
			// 获取原记录
			Object existObj = null;
	
			if (smp.getData().containsKey("id") &&!smp.getData().get("id").equals("")) {
				existObj = ReflectUtil.executeMethod(smp.getObjName(), "findById",
						new Class[] { Serializable.class }, new Object[] { smp.getData().get("id") });
			}
	
			// 更新原记录字段值
			if (existObj != null) {
				updateObj = ReflectUtil.setUpdateValue2Object(smp.getObjClazz(), existObj,
						updateObj, smp.getData().keySet());
			}
			List<ValidError> errors=ValidUtil.check(updateObj);
			if(errors.size()>0){
				return new ReturnObj<T>(errors);
			}else{
				updateObj = ReflectUtil.executeMethod(smp.getObjName(), "save",
					new Class[] { Object.class }, new Object[] { updateObj });
				return new ReturnObj<T>((T)updateObj);
			}
						
		}catch(Exception e){
			return new ReturnObj<T>(e);
		}
	}
	
	
	
	public static void main(String[] args) {

	}



}
