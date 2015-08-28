package com.wonders.frame.complex.model;

import java.util.ArrayList;
import java.util.List;

import com.wonders.frame.complex.model.vo.ComplexQuery;
import com.wonders.frame.complex.model.vo.FromBody;
import com.wonders.frame.complex.model.vo.GeneralQuery;
import com.wonders.frame.complex.model.vo.GroupByBody;
import com.wonders.frame.complex.model.vo.HavingBody;
import com.wonders.frame.complex.model.vo.JoinBody;
import com.wonders.frame.complex.model.vo.JoinOnBody;
import com.wonders.frame.complex.model.vo.OnBody;
import com.wonders.frame.complex.model.vo.RemoveBody;
import com.wonders.frame.complex.model.vo.SelectBody;
import com.wonders.frame.complex.model.vo.WhereBody;
import com.wonders.frame.complex.model.vo.and.JointQuery;
import com.wonders.frame.complex.model.vo.and.JointQuerys;
import com.wonders.frame.core.utils.JacksonMapper;

public class GetJson {
	public static String getJson() {
		List<SelectBody> selectBodies = new ArrayList<SelectBody>();
		List<FromBody> fromBodies = new ArrayList<FromBody>();
		List<WhereBody> whereBodies = new ArrayList<WhereBody>();
		List<GroupByBody> groupByBodies = new ArrayList<GroupByBody>();
		List<HavingBody> havingBodies = new ArrayList<HavingBody>();

//		SelectBody selectBody = new SelectBody("", "userOld");
		SelectBody selectBody = new SelectBody("", "a.name");
		SelectBody selectBody1 = new SelectBody("bbb", "b.id");
		SelectBody selectBody2 = new SelectBody("", "c.name");
		selectBodies.add(selectBody);
		selectBodies.add(selectBody1);
		selectBodies.add(selectBody2);

		
		JoinBody joinBody_nei = new JoinBody("b", "roleOld");
		List<GeneralQuery> generalQueries_on = new ArrayList<GeneralQuery>();
		GeneralQuery generalQuery_on = new GeneralQuery("and", "a.groupOldID", "=", "b.groupOldID", false, false);
				
		generalQueries_on.add(generalQuery_on);
		
		OnBody onBody_nei =new OnBody(generalQueries_on);
		JoinOnBody joinOnBody_nei = new JoinOnBody("userOld", "a", "join", joinBody_nei, onBody_nei);
		
		//wai
		JoinBody joinBody = new JoinBody("c", "groupOld");
		List<GeneralQuery> generalQueries_wai = new ArrayList<GeneralQuery>();
		GeneralQuery generalQuery_wai = new GeneralQuery("and", "a.removed", "=", "c.removed", false, false);
		generalQueries_wai.add(generalQuery_wai);
		OnBody onBody =new OnBody(generalQueries_wai);
		
//		JoinOnBody joinOnBody = new JoinOnBody("userOld", "a", "join", joinBody, onBody);
		
		JoinOnBody joinOnBody_wai = new JoinOnBody(joinOnBody_nei, "b1", "left join", joinBody, onBody);
		
		FromBody fromBody = new FromBody();
		fromBody.setValue(joinOnBody_wai);
//		fromBody.setValue("userOld");
		fromBody.setByname("a");
		fromBodies.add(fromBody);

		// in
		List<GeneralQuery> generalQueries = new ArrayList<GeneralQuery>();
		GeneralQuery generalQuery = new GeneralQuery("and", "a.id", "=", "262", true, false);
		GeneralQuery generalQuery1 = new GeneralQuery("or", "b.name", "=", "张三", false, false);
		GeneralQuery generalQuery2 = new GeneralQuery("and", "c.id", "<", "120", false, true);
		generalQueries.add(generalQuery);
		generalQueries.add(generalQuery1);
		generalQueries.add(generalQuery2);
		WhereBody whereBody = new WhereBody();
		whereBody.setGeneralQueries(generalQueries);

		whereBodies.add(whereBody);


		GroupByBody groupByBody =new GroupByBody("a.name");
		GroupByBody groupByBody1 =new GroupByBody("b.id");
		groupByBodies.add(groupByBody);
		groupByBodies.add(groupByBody1);
		
		HavingBody havingBody = new HavingBody("and", "sum(a.mobile1)", ">", 222);
		HavingBody havingBody1 = new HavingBody("and", "b.id", ">", 222);
		havingBodies.add(havingBody1);
		havingBodies.add(havingBody);
		
		ComplexQuery complexQuery = new ComplexQuery();
		complexQuery.setSelectBodies(selectBodies);
		complexQuery.setFromBodies(fromBodies);
		complexQuery.setWhereBodies(whereBodies);
//		complexQuery.setGroupByBodies(groupByBodies);
//		complexQuery.setHavingBodies(havingBodies);

		String s = JacksonMapper.toJson(complexQuery);
		System.out.println(s);
		return s;
	}
	
	//测试转型 date等
	public static String getJson3() {
		List<SelectBody> selectBodies = new ArrayList<SelectBody>();
		List<FromBody> fromBodies = new ArrayList<FromBody>();
		List<WhereBody> whereBodies = new ArrayList<WhereBody>();

		SelectBody selectBody = new SelectBody("rid", "r.id");
		selectBodies.add(selectBody);

		FromBody fromBody1 = new FromBody();
		fromBody1.setValue("roleOld");
		fromBody1.setByname("r");
		fromBodies.add(fromBody1);

		// in
		List<GeneralQuery> generalQueries = new ArrayList<GeneralQuery>();
		GeneralQuery generalQuery = new GeneralQuery("and", "r.operateDate", "=", "2015/5/1 12:00:00", false, false);
//		GeneralQuery generalQuery = new GeneralQuery("and", "r.operateDate", "=", "1", false, false);
		
		generalQueries.add(generalQuery);
		WhereBody whereBody = new WhereBody();
		whereBody.setGeneralQueries(generalQueries);
		whereBodies.add(whereBody);

		ComplexQuery complexQuery = new ComplexQuery();
		complexQuery.setSelectBodies(selectBodies);
		complexQuery.setFromBodies(fromBodies);
		complexQuery.setWhereBodies(whereBodies);

		String s = JacksonMapper.toJson(complexQuery);
		System.out.println(s);
		return s;
	}
	
	//group by
	public static String getJson2() {
		List<SelectBody> selectBodies = new ArrayList<SelectBody>();
		List<FromBody> fromBodies = new ArrayList<FromBody>();
		List<WhereBody> whereBodies = new ArrayList<WhereBody>();
		List<GroupByBody> groupByBodies = new ArrayList<GroupByBody>();
		List<HavingBody> havingBodies = new ArrayList<HavingBody>();

		SelectBody selectBody = new SelectBody("u", "userOld.id");
		SelectBody selectBody1 = new SelectBody("r", "roleOld.name");
		SelectBody selectBody2 = new SelectBody("", "sum(userOld.mobile1)");
		selectBodies.add(selectBody);
		selectBodies.add(selectBody1);
		selectBodies.add(selectBody2);

		FromBody fromBody1 = new FromBody();
		FromBody fromBody2 = new FromBody();
		fromBody1.setValue("userOld");
		fromBody1.setByname("userOld");
		fromBody2.setValue("roleOld");
		fromBody2.setByname("roleOld");
		fromBodies.add(fromBody1);
		fromBodies.add(fromBody2);

		// in
		List<GeneralQuery> generalQueries = new ArrayList<GeneralQuery>();
		GeneralQuery generalQuery = new GeneralQuery();
		generalQuery.setLogicalOper("and");
		generalQuery.setPara("userOld.id");
		generalQuery.setRelationalOper("=");
		generalQuery.setValue("262");

		generalQueries.add(generalQuery);
		WhereBody whereBody = new WhereBody();
		whereBody.setGeneralQueries(generalQueries);

//		whereBodies.add(whereBody);

		GroupByBody groupByBody =new GroupByBody("userOld.name");
		GroupByBody groupByBody1 =new GroupByBody("userOld.id");
		groupByBodies.add(groupByBody);
		groupByBodies.add(groupByBody1);
		
		HavingBody havingBody = new HavingBody("and", "sum(userOld.mobile1)", ">", 222);
		HavingBody havingBody1 = new HavingBody("and", "userOld.id", ">", 222);
		havingBodies.add(havingBody1);
		havingBodies.add(havingBody);
		
		ComplexQuery complexQuery = new ComplexQuery();
		complexQuery.setSelectBodies(selectBodies);
		complexQuery.setFromBodies(fromBodies);
		complexQuery.setWhereBodies(whereBodies);
		complexQuery.setGroupByBodies(groupByBodies);
		complexQuery.setHavingBodies(havingBodies);

		String s = JacksonMapper.toJson(complexQuery);
		System.out.println(s);
		return s;
	}
	
	//联查及其他
	public static String getJson1() {
		List<SelectBody> selectBodies = new ArrayList<SelectBody>();
		List<FromBody> fromBodies = new ArrayList<FromBody>();
		List<WhereBody> whereBodies = new ArrayList<WhereBody>();

		SelectBody selectBody1 = new SelectBody("u", "userOld.id");
		SelectBody selectBody2 = new SelectBody("", "roleOld.name");
		selectBodies.add(selectBody1);
//		selectBodies.add(selectBody2);

		ComplexQuery complexQuery1 = new ComplexQuery();
		List<SelectBody> selectBodies1 = new ArrayList<SelectBody>();
		List<FromBody> fromBodies1 = new ArrayList<FromBody>();
		SelectBody selectBody11 = new SelectBody();
		selectBody11.setPara("r");
		selectBody11.setByname("role1");
		selectBodies1.add(selectBody11);

		FromBody fromBody11 = new FromBody();
		fromBody11.setByname("r");
		fromBody11.setValue("roleOld");
		fromBodies1.add(fromBody11);
		complexQuery1.setFromBodies(fromBodies1);
		complexQuery1.setSelectBodies(selectBodies1);

		FromBody fromBody1 = new FromBody();
		fromBody1.setValue("userOld");
		fromBody1.setByname("userOld");
		FromBody fromBody2 = new FromBody();
		fromBody2.setByname("roleOld");
		fromBody2.setValue(complexQuery1);
		fromBodies.add(fromBody1);
		fromBodies.add(fromBody2);

		// whereBody
//		List<JointQuerys> jointQuerys = new ArrayList<JointQuerys>();
//		List<JointQuery> jointQueries = new ArrayList<JointQuery>();
//		List<String> jointQueryValues = new ArrayList<String>();
//		JointQuerys jointQueryss = new JointQuerys();
//
//		JointQuery jointQuery = new JointQuery();
//		jointQuery.setOperator("=");
//		jointQueryValues.add("userOld.GROUPOLD_ID");
//		jointQueryValues.add("roleOld.GROUPOLD_ID");
//		jointQuery.setValue(jointQueryValues);
//		jointQueries.add(jointQuery);
//
//		jointQueryss.setJointQuery(jointQueries);
//		jointQueryss.setOperator("and");
//		jointQuerys.add(jointQueryss);

		// in
		List<GeneralQuery> generalQueries = new ArrayList<GeneralQuery>();
		GeneralQuery generalQuery = new GeneralQuery();
		generalQuery.setLogicalOper("and");
		generalQuery.setPara("userOld.id");
		generalQuery.setRelationalOper("=");
		generalQuery.setValue("262");

		// generalQuery.setRelationalOper("in");

		// 内部嵌套
		// ComplexQuery complexQuery2 = new ComplexQuery();
		// List<SelectBody> selectBodies2 = new ArrayList<SelectBody>();
		// List<FromBody> fromBodies2 = new ArrayList<FromBody>();
		// SelectBody selectBody21 = new SelectBody();
		// selectBody21.setValue("userOld2");
		// selectBody21.setByname("u2");
		// selectBodies2.add(selectBody21);

		// ComplexQuery complexQuery3 = new ComplexQuery();
		// List<SelectBody> selectBodies3 = new ArrayList<SelectBody>();
		// List<FromBody> fromBodies3 = new ArrayList<FromBody>();
		// SelectBody selectBody31 = new SelectBody();
		// selectBody31.setValue("userOld1");
		// selectBody31.setByname("u1");
		// selectBodies3.add(selectBody31);
		//
		// FromBody fromBody31 = new FromBody();
		// fromBody31.setByname("userOld3");
		// fromBody31.setValue("UserOld3");
		// fromBodies3.add(fromBody31);
		// complexQuery3.setFromBodies(fromBodies3);
		// complexQuery3.setSelectBodies(selectBodies3);

		// FromBody fromBody21 = new FromBody();
		// fromBody21.setByname("userOld2");
		// fromBody21.setValue("UserOld2");
		// FromBody fromBody22 = new FromBody();
		// fromBody22.setByname("userOld3");
		// fromBody22.setValue(complexQuery3);
		// fromBodies2.add(fromBody21);
		// fromBodies2.add(fromBody22);
		//
		// complexQuery2.setFromBodies(fromBodies2);
		// complexQuery2.setSelectBodies(selectBodies2);
		//
		// //>,complexQuery2----generalQueries--whereBodies---complexQuery3
		// List<GeneralQuery> generalQueriesWhereBodies = new
		// ArrayList<GeneralQuery>();
		// GeneralQuery generalQuery_w = new GeneralQuery();
		// generalQuery_w.setLogicalOper("or");
		// generalQuery_w.setPara("roleOld.id");
		// generalQuery_w.setRelationalOper(" not in ");
		// generalQuery_w.setValue(complexQuery3);
		// generalQueriesWhereBodies.add(generalQuery_w);
		// WhereBody whereBody_w = new WhereBody();
		// whereBody_w.setGeneralQueries(generalQueriesWhereBodies);
		// // whereBody_w.setJointQuerys(jointQuerys);
		//
		// List<WhereBody> whereBodies_c2 = new ArrayList<WhereBody>();
		// whereBodies_c2.add(whereBody_w);
		// complexQuery2.setWhereBodies(whereBodies_c2);

		// generalQuery.setValue(complexQuery2);
		generalQueries.add(generalQuery);
		WhereBody whereBody = new WhereBody();
		whereBody.setGeneralQueries(generalQueries);
//		whereBody.setJointQuerys(jointQuerys);

		List<RemoveBody> removeBodies = new ArrayList<RemoveBody>();
		RemoveBody removeBody = new RemoveBody();
		removeBody.setPara("u.removed");
		removeBody.setRelationalOper("=");
		removeBody.setValue("0");
		removeBodies.add(removeBody);

		whereBodies.add(whereBody);

		// List<OrderBody> orderBodies = new ArrayList<OrderBody>();
		// List<RemoveBody> removeBodies = new ArrayList<RemoveBody>();

		ComplexQuery complexQuery = new ComplexQuery();
		complexQuery.setSelectBodies(selectBodies);
		complexQuery.setFromBodies(fromBodies);
		complexQuery.setWhereBodies(whereBodies);

		String s = JacksonMapper.toJson(complexQuery);
		System.out.println(s);
		return s;
	}
}
