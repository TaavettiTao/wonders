<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>[<%String pId = request.getParameter("id");
			if (pId == null)
				pId = "0";
			String pName = request.getParameter("n");
			if (pName == null) {
				pName = "";
			} else {
				pName += ".";
			}
			String pLevel = request.getParameter("lv");
			if (pLevel == null)
				pLevel = "0";
			String pCheck = request.getParameter("chk");
			if (pCheck == null)
				pCheck = "";
			for (int i = 1; i < 1000; i++) {
				String nId = pId + "_" + i;
				String nName = "tree" + nId;%>
				{
				 "id":"<%=nId%>", 
				 "name":"<%=nName%>",
				isParent:<%=((Integer.parseInt(pLevel) < 2 && (i % 2) != 0) ? "true"
						: "false")%><%=(pCheck == "" ? ""
						: (((Integer.parseInt(pLevel) < 2 && (i % 2) != 0) ? ", halfCheck:true"
								: "") + (i == 3 ? ",checked:true" : "")))%>}<%if (i < 999) {%>,<%}
			}%>]
