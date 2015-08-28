<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<base href="<%=basePath%>" />
	<title>FRAME-2222</title>

	<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen" />

	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="js/me.js"></script>
<!-- <script type="text/javascript" src="../js/jquery-1.3.2.min.js"></script> -->
<script type="text/javascript">
		//增加<tr/>
		$(document).ready(function(){
			$("#addUser").click(function(){
				   var _len = $("#table2 tbody tr").length;  
		               $("#table2 tbody").append("<tr id="+_len+" >"
                          +"<td>"+_len+"</td>"
                          +"<td><input type='text' value='' class='text-input small-input' name='users["+_len+"].name' id='users"+_len+".name' /></td>"
                          +"<td><input type='text' value='' class='text-input small-input' name='users["+_len+"].mobile1' id='users"+_len+".mobile1' /></td>"
                          +"<td><input type='text' value='' class='text-input small-input' name='users["+_len+"].email' id='users"+_len+".email' /></td>"
                          +"<td><a href=\'javascript:void(0)\' onclick=\'deltr("+_len+")\'>删除</a></td>"
                 			+"</tr>");            
				           });  
			
			})	
			//删除<tr/>
	    var deltr =function(index)
	    {
	    	var _len = $("#table2 tbody tr").length;  
	        $("tr[id='"+index+"']").remove();//删除当前行
	       for(var i=index+1,j=_len;i<j;i++)
	        {
	            var nextTxtVal = $("#desc"+i).val();
	            $("tr[id=\'"+i+"\']")
	                .replaceWith("<tr id="+_len+" >"
	                          +"<td>"+(i-1)+"</td>"
	                          +"<td><input type='text' value='' class='text-input small-input' name='users["+(i-1)+"].name' id='users"+(i-1)+".name' /></td>"
	                          +"<td><input type='text' value='' class='text-input small-input' name='users["+(i-1)+"].mobile1' id='users"+(i-1)+".mobile1' /></td>"
	                          +"<td><input type='text' value='' class='text-input small-input' name='users["+(i-1)+"].email' id='users"+(i-1)+".email' /></td>"
	                          +"<td><a href=\'javascript:void(0)\' onclick=\'deltr("+(i-1)+")\'>删除</a></td>"
	                 			+"</tr>");       
	         
	       }    
	    }
	
</script>
</head>

<body>
<div id="main-content">
	<div class="clear"></div>
	<div class="content-box">
		<form:form id="form1" action="" method="post"
			modelAttribute="addRelationForm">
			<div class="content-box-content">
				<div class="content-box-header">
					<h3>&nbsp;</h3>
					<div class="clear"></div>
				</div>

				<c:if test="${hasErrors}">
					<div class="notification error png_bg">
						<a class="close" style="cursor: pointer;"><img
							src="images/icons/cross_grey_small.png" /></a>
						<div>
							<form:errors path="*" />
						</div>
					</div>
				</c:if>
				<table id="table1" border="0">
					
						<tr>
							<td>组名</td>
<%-- 							<td><form:input path="groupOld.name" --%>
<%-- 									class="text-input small-input" /></td> --%>
							<td>类型</td>
<%-- 							<td><form:input path="groupOld.type" --%>
<%-- 									class="text-input small-input" /></td> --%>
							<td>描述</td>
<%-- 							<td><form:input path="groupOld.description" --%>
<%-- 									class="text-input small-input" /></td> --%>
						</tr>
						<tr>
							<td><form:input path="groupOld.name"
									class="text-input small-input" /></td>
							<td><form:input path="groupOld.type"
									class="text-input small-input" /></td>
							<td><form:input path="groupOld.description"
									class="text-input small-input" /></td>
						</tr>
					</tbody>
				</table>
				<p>
					<br /> <input class="button" id="addUser"
						value="新增用户" /> <input
						class="button" id="removeUser" onclick="return removeRow()"
						value="删除用户" /><br>
					<br> <input class="button" id="addContact"
						onclick="return appendRow()" value="新增联系人" /> <input
						class="button" id="removeContact" onclick="return removeRow()"
						value="删除联系人" />
				</p>
			</div>

			<div class="content-box-content">
				<div class="content-box-header">
					<h3>&nbsp;</h3>
					<div class="clear"></div>
				</div>
				<table id="table2" border="0">
					<tbody  id="userList">
						<tr id="userHeadline">
							<td>序号</td>
							<td>姓名</td>
<%-- 							<td><form:input path="users[0].name" --%>
<%-- 									class="text-input small-input" /></td> --%>
							<td>手机号</td>
<%-- 							<td><form:input path="users[0].mobile1" --%>
<%-- 									class="text-input small-input" /></td> --%>
							<td>个人邮件</td>
<%-- 							<td><form:input path="users[0].email" --%>
<%-- 									class="text-input small-input" /></td> --%>
							<td>操作</td>
							<td></td>
						</tr>
						
						<%-- <tr>
							<td></td>
							<td><form:input path="users[0].name"
									class="text-input small-input" /></td>
							<td><form:input path="users[0].mobile1"
									class="text-input small-input" /></td>
							<td><form:input path="users[0].email"
									class="text-input small-input" /></td>
							<td></td>
						
						</tr> --%>
						<%-- <tr>
							<td>姓名</td>
							<td><form:input path="users[5].name"
									class="text-input small-input" /></td>
							<td>手机号</td>
							<td><form:input path="users[5].mobile1"
									class="text-input small-input" /></td>
							<td>个人邮件</td>
							<td><form:input path="users[5].email"
									class="text-input small-input" /></td>
							<td></td>
							<td></td>
						</tr> --%>
					</tbody>
				</table>
			</div>
			<div class="content-box-content">
				<div class="content-box-header">
					<h3>&nbsp;</h3>
					<div class="clear"></div>
				</div>
				<table id="table3" border="0">
					<tbody>
						<%-- <tr id="groupList">
							<td>姓名</td>
							<td><form:input path="users[0].name"
									class="text-input small-input" /></td>
							<td>手机号</td>
							<td><form:input path="users[0].mobile1"
									class="text-input small-input" /></td>
							<td>个人邮件</td>
							<td><form:input path="users[0].email"
									class="text-input small-input" /></td>
							<td></td>
							<td></td>
						</tr> --%>
					</tbody>

				</table>
				<p>
					<br /> <input class="button" type="submit" value="提交" />
				</p>
			</div>
		</form:form>

	</div>
	<div class="clear"></div>
	<div id="footer">
		<small> Powered by <a href="http://yingzhuo.iteye.com"
			target="_blank">孟杰</a> | <a href="mailto:yingzhor@163.com">联系我</a>
		</small>
	</div>
</div>
</body>
</html>