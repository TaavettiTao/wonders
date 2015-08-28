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
<title><tiles:insertAttribute name="title" /></title>

<link rel="stylesheet" type="text/css" href="css/screen.css">
<!-- <script type="text/javascript" -->
<!-- 	src="js/jquery-ui-1.11.1.custom/external/jquery/jquery.js"></script> -->
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/defaultJs.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/jquery-ui-1.11.1.custom/jquery-ui.css" />
<script type="text/javascript"
	src="js/jquery-ui-1.11.1.custom/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/jquery-ui-1.11.1.custom/jquery-ui.theme.min.css" />
<script type="text/javascript" src="js/modernizr.custom.54158.js"></script>


<!-- <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen" /> -->
<!-- 	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" /> -->
<!-- 	<link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen" /> -->
<link rel="stylesheet" href="css/jquery.multiSelect.css" type="text/css"
	media="screen" />
<!-- <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script> -->
<script type="text/javascript" src="js/rule.js"></script>
<script type="text/javascript" src="js/jquery.multiSelect.js"></script>
<script type="text/javascript" src="js/editable.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<script type="text/javascript">
	   $(function(){  
		   var ruleTypeId = getUrlParam("ruleTypeId");
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
//		 	     initObjectInfoSelect();
			     initRelationObjInfoSelect(ruleTypeId);
// 			     listHistoryRelationRule(1);
			     getRuleTableInfo("rule",ruleTypeId);
			     //新增事件
			     $("#add").click(function(){
			         var pobjId=$("#pobjType").val();
			         var nobjId=$("#nobjType").val();
			         var pobjType = changeToEn(pobjId);
			         var nobjType = changeToEn(nobjId);
//		 	         var _date = $("#relationRule").serialize();
			         var warnNotice="";
			         if(pobjId==""){
			            warnNotice+="请选择上级联对象！\n";
			         }
			         if(nobjId==""){
			            warnNotice+="请选择下级联对象！\n";
			         }
			         if(warnNotice!=""){
			           alert(warnNotice);
			         }else{
			           var isExists=isExistsRule(ruleTypeId);
			           if(!isExists){
			                $.ajax({
					        type: 'POST',
							url: '<%=basePath%>/api/rule',
							data:{
								'pobjId':pobjId,
								'nobjId':nobjId,
								'pobjType':pobjType,
								'nobjType':nobjType,
								'ruleTypeId':ruleTypeId,
							},
							dataType:'json',
							error:function(){alert('系统连接失败，请稍后再试！')},
							success: function(result){
								if(result.info.success==true){
									var queryParams = {}; // Map map = new HashMap();
									queryParams['ruleTypeId'] = ruleTypeId;
									$.getTable("${pageContext.request.contextPath}/api/rule", "#rule", queryParams);
								   alert("新增成功！");
								}
							}	 
					      });
			           }
			         } 
			     });
			     
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
	<div id="dialog" style="display: none">
		<p style="margin-left: 5px;">是否绑定（解绑）数据？</p>
	</div>
	<form>
		<div class="v-center">
			<div class="v-main">
				<div class="v-top">
					<div id="radio" class="fl">
						<input type="radio" id="bind" name="bindRelation"
							checked="checked" /> <label for="bind" style="color: #885df3">绑定</label>
						<input type="radio" id="removeBind" name="bindRelation"> <label
							for="removeBind" style="color: #885df3">解绑</label> <input
							type="radio" id="oneKeyPut" name="oneKeyPut"> <label
							for="oneKeyPut" style="color: #885df3">一键拖放</label> <input
							type="radio" id="oneKeyClear" name="oneKeyClear"> <label
							for="oneKeyClear" style="color: #885df3">一键清空</label>
					</div>
					<div class="topmenu fr">
						<span class="searchbox"> <input type="text" name="s"
							value="" placeholder="Search...."/ >
						</span> <span class="icon"> <input type="button" class="msgicon"
							value="" />
						</span> <span class="icon"> <input type="button" class="favicon"
							value="" />
						</span> <span class="icon"> <input type="button" class="cfgicon"
							value="" />
						</span> <span class="userinfo">
							<div class="lb">
								<img src="css/icon/headimg.jpg" width="48" height="48"
									alt="HeadImg" />
							</div>
							<div class="userDepartment">
								<security:authentication property="principal"
									var="authentication" />
								<ul>
									<li>${authentication.dept}</li>
									<li><b class="clr_bwn">${authentication.username}</b>，欢迎登录
										&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:void();"
										id="logout">退出系统</a></li>
								</ul>

							</div>
						</span>
					</div>
				</div>

				<div id="main-content">
					<div class="clear"></div>
					<div class="content-box">
						<div class="content-box-content">
							<div class="content-box-header">
								<h3>&nbsp;关联规则配置</h3>
								<div class="clear"></div>
							</div>

							<form id="relationRule">
								<input type="hidden" name="sort" id="sort" value="id desc" />
								<table id="table1" border="0">
									<tbody>
										<tr>
											<td>上级联对象 <select id="pobjType" name="pobjType">
											</select>
											</td>
											<td>下级联对象 <select id="nobjType" name="nobjType">
											</select>
											</td>
											<td><input type="button" name="add" id="add" value="新增" />
											</td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</tbody>
								</table>
							</form>
						</div>
						<div id="tableArea"></div>
						<div style="height: 20px"></div>
					</div>

				</div>
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
