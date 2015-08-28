package com.wonders.frame.complex.model.vo;

import java.util.List;

import com.wonders.frame.core.model.vo.GenericEnum;

public class ComplexQuery {

	private List<SelectBody> selectBodies;
	private List<FromBody> fromBodies;
	private List<WhereBody> whereBodies;
	private OrderBody orderBody;
	private List<GroupByBody> groupByBodies;
	private List<HavingBody> havingBodies;

	public List<SelectBody> getSelectBodies() {
		return selectBodies;
	}

	public void setSelectBodies(List<SelectBody> selectBodies) {
		this.selectBodies = selectBodies;
	}

	public List<FromBody> getFromBodies() {
		return fromBodies;
	}

	public void setFromBodies(List<FromBody> fromBodies) {
		this.fromBodies = fromBodies;
	}

	public List<WhereBody> getWhereBodies() {
		return whereBodies;
	}

	public void setWhereBodies(List<WhereBody> whereBodies) {
		this.whereBodies = whereBodies;
	}

	public OrderBody getOrderBody() {
		return orderBody;
	}

	public void setOrderBody(OrderBody orderBody) {
		this.orderBody = orderBody;
	}

	public List<GroupByBody> getGroupByBodies() {
		return groupByBodies;
	}

	public void setGroupByBodies(List<GroupByBody> groupByBodies) {
		this.groupByBodies = groupByBodies;
	}

	public List<HavingBody> getHavingBodies() {
		return havingBodies;
	}

	public void setHavingBodies(List<HavingBody> havingBodies) {
		this.havingBodies = havingBodies;
	}

	public ComplexQuery() {
	}

//	public static enum Func {
//		EQUAL("$e", "=");
//
//		private String sign;
//		private String description;
//
//		private Func(int code, String description) {
//			this.code = code;
//			this.description = description;
//		}
//
//		public void setCode(int code) {
//			this.code = code;
//		}
//
//		public void setDescription(String description) {
//			this.description = description;
//		}
//
//		public int code() {
//			return code;
//		}
//
//		public String description() {
//			return description;
//		}
//	}
}
