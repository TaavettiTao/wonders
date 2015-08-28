<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>规则类型列表</title>

<link rel="stylesheet" type="text/css" href="css/screen.css">
<link rel="stylesheet" type="text/css" href="js/jquery-ui-1.11.1.custom/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-ui-1.11.1.custom/jquery-ui.theme.min.css" />

<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/rule.js"></script>
<script type="text/javascript" src="js/defaultJs.js"></script>
<script type="text/javascript" src="js/json2.js"></script>

<script type="text/javascript" src="js/jquery-ui-1.11.1.custom/jquery-ui.js"></script>

<script type="text/javascript" src="js/modernizr.custom.54158.js"></script>


<link rel="stylesheet" href="css/jquery.multiSelect.css" type="text/css"
	media="screen" />
<script type="text/javascript" src="js/ruleType.js"></script>

<script type="text/javascript" src="js/jquery.multiSelect.js"></script>
<script type="text/javascript" src="js/editable.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>
<script type="text/javascript">
	   $(function(){  
		   $(document).ajaxStart(function(){
			    $.blockUI({
		            message: $('#loading'),
		            css: {
		                top:  ($(window).height() - 400) /2 + 'px',
		                left: ($(window).width() - 400) /2 + 'px',
		                width: '400px',
		                border: 'none',
		                backgroundColor: 'none',
		            },
		            overlayCSS: { 
		            	backgroundColor: '#fff',
		            	opacity:'0.6' 
		            }
			    });
	 				setTimeout($.unblockUI, 2000);
			   });
	   $("#logout").click(function(){
	         if(confirm("您确认退出系统吗？")){
	           $.cookie('user', '', {expires:-1,path:'/'}); // 删除 cookie
	           $("#logout").attr("href","<%=basePath%>logout");
			}
		});
	     getTableInfo("ruleType");
// 	     var loginName = $("#loginName");
// 	     if(loginName!=null){
// 	    	 $.ajax({
// 	    			type : 'GET',
// 	    			url : getBasePath() + '/api/ruleType/' + ruleTypeId
// 	    					+ '/rule/paths?pobjType=' + pobjType,
// 	    			dataType : 'json',
// 	    			cache : false,
// 	    			async : false,
// 	    			error : function() {
// 	    				alert('系统连接失败，请稍后再试！');
// 	    			},
// 	    			success : function(result) {
// 	    				if (result.info.success) {
// 	    					rule = result.data;
// 	    				}
// 	    			}
// 	    		});
// 	     }
	});

</script>
<style type="text/css">
a#logout {
	color: #A9A487;
}

a#logout:hover {
	text-decoration: underline;
	color: #FF8B19;
}
</style>
</head>
<!-- <body id="default"  onclick="isLoginTimeout(event)" > -->
<body id="default">
<div id="loading" style="display: none"><img alt="" src="images/loading.gif"></div>
	<form>
		<div class="v-center">
			<div class="v-main">
				<div class="v-top logo">
<!-- 					<div id="radio" class="fl"> -->
<!-- 						<input type="radio" id="bind" name="bindRelation" -->
<!-- 							checked="checked" /> <label for="bind" style="color: #885df3">绑定</label> -->
<!-- 						<input type="radio" id="removeBind" name="bindRelation"> <label -->
<!-- 							for="removeBind" style="color: #885df3">解绑</label> <input -->
<!-- 							type="radio" id="oneKeyPut" name="oneKeyPut"> <label -->
<!-- 							for="oneKeyPut" style="color: #885df3">一键拖放</label> <input -->
<!-- 							type="radio" id="oneKeyClear" name="oneKeyClear"> <label -->
<!-- 							for="oneKeyClear" style="color: #885df3">一键清空</label> -->
<!-- 					</div> -->
					<div class="topmenu fr">
<!-- 						<span class="searchbox"> <input type="text" name="s" -->
<!-- 							value="" placeholder="Search...."/ > -->
<!-- 						</span>  -->
<!-- 						<span class="icon"> <input type="button" class="msgicon" -->
<!-- 							value="" /> -->
<!-- 						</span> <span class="icon"> <input type="button" class="favicon" -->
<!-- 							value="" /> -->
<!-- 						</span> <span class="icon"> <input type="button" class="cfgicon" -->
<!-- 							value="" /> -->
<!-- 						</span>  -->
						<span class="userinfo">
							<div class="lb">
								<img src="css/icon/headimg.jpg" width="48" height="48"
									alt="HeadImg" />
							</div>
							<div class="userDepartment">
								<security:authentication property="principal"
									var="authentication" />
								<ul>
								<input type="hidden" id="ruleTypeIds" value='${sessionScope.ruleTypeIds}'/>
								<input type="hidden" id="resPaths" value='${sessionScope.resPaths}'/>
<%-- 									<li>${authentication.dept}</li> --%>
									<li><b class="clr_bwn" id="loginName">${sessionScope.loginUser.name}</b>，欢迎登录
										&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:void();"
										id="logout">退出系统</a></li>
								</ul>

							</div>
						</span>
					</div>
				</div>
				<div id="main-content">
					<div class="clear"></div>

					<div id="tableArea"></div>
					<div class="clear"></div>
					<div id="footer">
						<small> Powered by <a href="#" target="_blank">lsf</a> | <a
							href="mailto:mengjie@wondersgroup.com">联系我</a>
						</small>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
