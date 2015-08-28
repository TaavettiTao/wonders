<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 

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
	<!--
	<script type="text/javascript" src="js/simpla.jquery.configuration.js"></script>
	<script type="text/javascript" src="js/facebox.js"></script>
	<script type="text/javascript" src="js/jquery.wysiwyg.js"></script>
	-->
	<script type="text/javascript" src="js/me.js"></script>
	<script type="text/javascript">
	
	</script>
</head>

<body id="${entity.cnName}-form">
<div id="main-content">
	<div class="clear"></div>
	<div class="content-box">
		<div class="content-box-content">
			<div class="content-box-header">
				<h3>&nbsp;${entity.cnName}</h3>
				<div class="clear"></div>
			</div>
			<form:form commandName="${entity.name}" action="${entity.name}/save">
			<c:if test="${hasErrors}">
			<div class="notification error png_bg">
				<a class="close" style="cursor: pointer;"><img src="images/icons/cross_grey_small.png"/></a>
				<div>
					<form:errors path="*" />
				</div>
			</div>
			</c:if>
			
			<table id="table1" border="0">
				<tbody>
				<c:forEach items="${entity.fieldProperties}" var="pageField" varStatus="s">
					<c:if test="${pageField.operate=='hidden'}">	
						<form:hidden path="${pageField.path}"/>
					</c:if>
					<c:if test="${pageField.operate=='input'}">	
					<tr>
						<td>${pageField.name}</td>
						<td><form:input path="${pageField.path}" class="text-input small-input"/></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					</c:if>

					<c:if test="${fn:startsWith(pageField.operate,'range')}">		
					<tr>
						<td>${pageField.name}</td>
						<td><form:input path="${pageField.path}" class="text-input small-input"/></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					</c:if>
					<c:if test="${pageField.operate=='select'}">	
					<tr>
						<td>${pageField.name}</td>
						<td><form:select path="${pageField.path}" items="${pageField.option}"/></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					</c:if>	
				</c:forEach>
				

				</tbody>
			</table>
			<p>
				<br/>
				<input class="button" type="submit" value="提交" />
            </p>
			</form:form>
		</div>
	</div>
	<div class="clear"></div>
	<div id="footer">
		<small>
			Powered by <a href="#" target="_blank">lsf</a> | <a href="mailto:mengjie@wondersgroup.com">联系我</a>
		</small>
	</div>
</div>
</body>
</html>
