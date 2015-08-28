<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="taglib_includes.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="<%=basePath%>" />
<title>FRAME</title>

<link rel="stylesheet" href="css/reset.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/style.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/invalid.css" type="text/css"
	media="screen" />

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<!--
	<script type="text/javascript" src="js/simpla.jquery.configuration.js"></script>
	<script type="text/javascript" src="js/facebox.js"></script>
	<script type="text/javascript" src="js/jquery.wysiwyg.js"></script>
	-->
<script type="text/javascript" src="js/me.js"></script>
<script type="text/javascript" src="js/contacts.js"></script>

<style type="text/css">
select {
	width: 200px;
	float: right;
}
</style>
</head>

<!-- <body id="login" onload="timeFinish()" > -->
<body id="login">
	<div id="login-wrapper" class="png_bg">
		<div id="login-top">
			<h1>FRAME</h1>
			<a href="#" target="_blank"> <img id="logo" src="images/logo.png" />
			</a>
		</div>
		<div id="login-content">
			<form:form action="login" method="post" commandName="user"
				modelAttribute="user">
				<div style="padding-bottom: 5px;">
					<form:errors path="loginName" style="color:red;display:inline;"> </form:errors>
					<form:errors path="password" style="color:red;white-space: nowrap;"></form:errors>
					</br>
				</div>
				<p>
					<label>Username</label>
					<form:input path="loginName" />
					<%-- 					<form:errors path="loginName" style="color:red;display:inline;"></form:errors> --%>
				</p>
				<div class="clear"></div>
				<p>
					<label>Password</label>
					<form:password path="password" />
					<%-- 					<form:errors path="password" style="color:red;white-space: nowrap;"></form:errors> --%>
				</p>
				<div class="clear"></div>
				<p>
					<input class="button" style="float: left; margin-left: 120px"
						type="submit" value="Sign In" /> <input class="button"
						style="float: left; margin-left: 20px" type="reset" value="Reset" />
					<!-- 					<input class="button" type="submit" name="" value="Login">  -->
					<!-- 					<input class="button" type="button" value="Regist" onclick="javascript:go('saveContact.do');"> -->
				</p>

			</form:form>
		</div>
	</div>
</body>
</html>

