/** 
 * @Title: UserEditController.java 
 * @Package com.wonders.frame.core.controller 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author lushuaifeng
 * @version V1.0 
 */
package com.wonders.frame.complex.controller;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wonders.frame.complex.model.GetJson;
import com.wonders.frame.complex.model.vo.ComplexQuery;
import com.wonders.frame.complex.model.vo.FromBody;
import com.wonders.frame.complex.model.vo.GeneralQuery;
import com.wonders.frame.complex.model.vo.GroupByBody;
import com.wonders.frame.complex.model.vo.HavingBody;
import com.wonders.frame.complex.model.vo.JoinBody;
import com.wonders.frame.complex.model.vo.JoinOnBody;
import com.wonders.frame.complex.model.vo.OnBody;
import com.wonders.frame.complex.model.vo.OrderBody;
import com.wonders.frame.complex.model.vo.RemoveBody;
import com.wonders.frame.complex.model.vo.SelectBody;
import com.wonders.frame.complex.model.vo.WhereBody;
import com.wonders.frame.complex.model.vo.and.JointQuery;
import com.wonders.frame.complex.model.vo.and.JointQuerys;
import com.wonders.frame.core.utils.JacksonMapper;

@Controller
@RequestMapping("/complexRelation")
public class ComplexRelationController extends
		AbstractComplexRelationController<ComplexQuery> {// RelationForm,RelationVoImpl

	@PersistenceContext
	private EntityManager em;

	public static void main(String[] args) throws IOException,
			IntrospectionException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		ComplexQuery complexQuery = JacksonMapper.readValue(GetJson.getJson(),
				ComplexQuery.class);
		StringBuffer a = getData(complexQuery);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println(getMap(2,map));
		// Class clazz = ComplexQuery.class;
		// Field[] declaredfields=ComplexQuery.class.getDeclaredFields();
		// for (Field field : declaredfields) {
		// System.out.println( field.getType().getSimpleName());
		// PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
		// clazz);
		// Method getMethod = pd.getReadMethod();//获得get方法
		// System.out.println(getMethod.getName());
		// }
	}
	public static HashMap<String, Object> getMap(Integer no,HashMap<String, Object> map){
		
		
		map.put("a", "aaaa");
		map.put(no.toString(), "bbbb");
		for (int i = no; i >0; i--) {
			getMap(no-1,map);
		}
		
		return map;
	}
	
	private static StringBuffer getJoinData(JoinOnBody joinOnBody) {
		StringBuffer fromBodyHql = new StringBuffer("");
		StringBuffer fromValueSql = new StringBuffer("");
		StringBuffer fromByname = new StringBuffer("");
		StringBuffer joinBodySql = new StringBuffer("");
		StringBuffer onBodySql = new StringBuffer("");
		
		String joinType = joinOnBody.getJoinType();
		Object fromValue = joinOnBody.getFromValue();

		String className = fromValue.getClass().getSimpleName();
		if(className.equals("LinkedHashMap")){
			JoinOnBody tmp = JacksonMapper.convert(fromValue, JoinOnBody.class);
			fromValue = getJoinData(tmp);
//			if(!tmp.getFromValue().getClass().getSimpleName().equals("LinkedHashMap")){
//				fromByname = tmp.getFromByname();
//			}
			
		}else{
			fromValue = (String) fromValue;
			//由于join on特殊性，只有最里层的fromValue才能有别名，否则报错
			fromByname.append(joinOnBody.getFromByname()); 
		}
		fromValueSql.append(" (").append(fromValue).append(") ");
		
		JoinBody joinBody = joinOnBody.getJoinBody();
		String para = joinBody.getPara();
		String byname = joinBody.getByname();
		joinBodySql.append(para).append(" ").append(byname);
		
		OnBody onBody = joinOnBody.getOnBody();
		List<GeneralQuery> generalQueries = onBody.getGeneralQueries();
		
//		onBodySql.append(getJointQuery(jointQueryVo));
		onBodySql.append(" on ").append(getGeneralQuery(generalQueries));
		fromBodyHql = fromBodyHql.append(fromValueSql).append(fromByname).append(" ").append(joinType).append(" ").append(joinBodySql);
		return fromBodyHql = (joinType.equalsIgnoreCase("cross join") ? fromBodyHql : fromBodyHql.append(onBodySql));
//		return fromBodyHql.append(fromValueSql).append(fromByname).append(" ").append(joinType).append(" ").append(joinBodySql).append(onBodySql);
	}

	
	private static Object getGeneralQuery(List<GeneralQuery> generalQueries) {
		StringBuffer result = new StringBuffer("");
		for (int j = 0; j < generalQueries.size(); j++) {
			Boolean leftBracket = generalQueries.get(j).getLeftBracket();
			Boolean rightBracket = generalQueries.get(j).getRightBracket();
			 
			String logicalOper = generalQueries.get(j).getLogicalOper();
			String para = generalQueries.get(j).getPara();
			String relationalOper = generalQueries.get(j)
					.getRelationalOper();
			Object value = generalQueries.get(j).getValue();
			String className = value.getClass().getSimpleName();
			if (className.equals("ComplexQuery")) {
				value = getData((ComplexQuery) value);// 遍历获取hql
			} else {
				value = (String) value;
			}
			result.append(" ").append(logicalOper);
			result = ((leftBracket) ? result.append(" ("):result);
			result.append(" ").append(para).append(" ").append(relationalOper)
					.append(" (").append(value).append(") ");
			result = ((rightBracket) ? result.append(") "):result);
		}
		return result;
	}

	private static StringBuffer getJointQuery(List<JointQuerys> jointQueryVo) {
		StringBuffer jointQuerySql = new StringBuffer("");
		if (jointQueryVo != null) {
			for (int j = 0; j < jointQueryVo.size(); j++) {
				String operator = jointQueryVo.get(j).getOperator();
				List<JointQuery> jointQueries = jointQueryVo.get(j)
						.getJointQuery();
				StringBuffer wb = new StringBuffer("");
				for (int k = 0; k < jointQueries.size(); k++) {
					String jointQueryOper = jointQueries.get(k).getOperator();
					List<String> values = jointQueries.get(k).getValue();
					for (int l = 0; l < values.size(); l++) {
						String value = values.get(l);
						wb.append(value);
						if (l != values.size() - 1) {
							wb.append(jointQueryOper);
						}
					}
				}
				jointQuerySql.append(" on ").append(operator).append(" ").append(wb)
						.append(" ");
			}
		}
		return jointQuerySql;
	}

	private static StringBuffer getData(ComplexQuery complexQuery) {
		StringBuffer hql = new StringBuffer(""), removedBody = new StringBuffer(
				"");
		StringBuffer whereBody = new StringBuffer(" where "), fromBody = new StringBuffer(
				" from "), selectBody = new StringBuffer(" select ");
		List<SelectBody> selectBodies = complexQuery.getSelectBodies();
		List<FromBody> fromBodies = complexQuery.getFromBodies();
		List<WhereBody> whereBodies = complexQuery.getWhereBodies();

		for (int i = 0; i < selectBodies.size(); i++) {
			String byname = selectBodies.get(i).getByname();
			String para = selectBodies.get(i).getPara();

			if (!para.contains(".")) {
				para += ".*";
				selectBody.append(para);
			} else {
				selectBody.append(para);
				if (byname != null && byname.length() != 0) {
					selectBody.append(" as ").append(byname);
				}
			}

			if (i != selectBodies.size() - 1) {
				selectBody.append(" , ");

			}
		}

		for (int i = 0; i < fromBodies.size(); i++) {
			String byname = fromBodies.get(i).getByname();
			Object value = fromBodies.get(i).getValue();

			boolean isJoinOnBody = false;
			String className = value.getClass().getSimpleName();
			if (className.equals("ComplexQuery")) {
				value = getData((ComplexQuery) value);// 遍历获取hql
			} else if (className.equals("JoinOnBody")) {
				value = getJoinData((JoinOnBody) value);
				isJoinOnBody = true;
			} else {
				value = StringUtils.capitalize((String) value);
			}

			if(isJoinOnBody){
				fromBody.append(value);
			}else{
				fromBody.append(" ( ").append(value).append(" ) ").append(" as ")
				.append(byname);
			}
			removedBody.append(" ").append(byname).append(".removed=0")
					.append(" ");
			if (i != fromBodies.size() - 1) {
				fromBody.append(" , ");
				removedBody.append(" and ");
			}
		}
		whereBody.append(removedBody);
		if (whereBodies != null) {
			for (int i = 0; i < whereBodies.size(); i++) {
//				List<JointQuerys> jointQueryVo = whereBodies.get(i)
//						.getJointQuerys();
				List<GeneralQuery> generalQueries = whereBodies.get(i)
						.getGeneralQueries();
				// List<RemoveBody> removeBodies =
				// whereBodies.get(i).getRemoveBodies();
				// if(removeBodies!=null){
				// for (int j = 0; j < removeBodies.size(); j++) {
				// String para = removeBodies.get(j).getPara();
				// String relationalOper =
				// removeBodies.get(j).getRelationalOper();
				// Object value = removeBodies.get(j).getValue();
				// String className = value.getClass().getSimpleName();
				// if (className.equals("ComplexQuery")) {
				// value = getData((ComplexQuery) value);//遍历获取hql
				// } else {
				// value = (String)value;
				// }
				// removedBody.append(" ").append(para).append(" ").append(relationalOper)
				// .append(" (").append(value).append(") ");
				// whereBody.append(removedBody);
				// }
				// }else{
				// whereBody.append(" 1=1 ");
				// }

				whereBody.append(getGeneralQuery(generalQueries));
			}
		}

		hql = hql.append(selectBody).append(fromBody).append(whereBody);

		String tmp = hql.substring(hql.indexOf("select"), hql.indexOf("from"));
		String hqlCount = hql.toString().replace(tmp, "select count(*) ");

		System.out.println(hql);
		return hql;
	}

}
