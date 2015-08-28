<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><tiles:insertAttribute name="title" /></title>

<!-- <link rel="stylesheet" type="text/css" href="css/screen.css"> -->
<!-- <script type="text/javascript" -->
<!-- 	src="js/jquery-ui-1.11.1.custom/external/jquery/jquery.js"></script> -->
<!-- <script type="text/javascript" src="js/json2.js"></script> -->
<!-- <script type="text/javascript" src="js/defaultJs.js"></script> -->
<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="js/jquery-ui-1.11.1.custom/jquery-ui.css" /> -->
<!-- <script type="text/javascript" -->
<!-- 	src="js/jquery-ui-1.11.1.custom/jquery-ui.js"></script> -->
<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="js/jquery-ui-1.11.1.custom/jquery-ui.theme.min.css" /> -->
<!-- <script type="text/javascript" src="js/modernizr.custom.54158.js"></script> -->
<!-- <script type="text/javascript" src="js/jquery.cookie.js"></script> -->

	<link rel="stylesheet" href="css/demo.css" type="text/css">
	<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.exedit-3.5.js"></script>	
	<script type="text/javascript" src="js/tree_obj.js"></script>	
	<script type="text/javascript" src="js/tree_auto_correlation.js"></script>	
<!-- 	<script type="text/javascript" src="js/jquery.ztree.all-3.5.js"></script>	 -->
	<script type="text/javascript">
		
	 		   //初始化操作
	 	     $(document).ready( function(){
// 	 	           onloadZTree("organ","organ","treeDemo",false);
	 	           
	 	     });
// 	 		 function(){
// 	 			onloadZTree_obj("user");
// 	 		 }
			function bind(){
				var nodes = $("#pType").val();
				var nodes1 = $("#citySel").val();
			}
			
			function showMenu(objName) {
				onloadZTree_obj(objName);
				var obj = $("#"+objName);
				var objOffset = $("#"+objName).offset();
				$("#menuContent").css({
					left : objOffset.left + "px",
					top : objOffset.top + obj.outerHeight() + "px"
				}).slideDown("fast");

				$("body").bind("mousedown", onBodyDown);
			}
			
			function showRelationMenu(treeId, ruleTypeId, ptype, ntype, autoCorrelation, isRelationTree) {
				onloadZTree(treeId, ruleTypeId, ptype, ntype, autoCorrelation, isRelationTree);
				var obj = $("#" + ptype + "_" + ntype);
				var objOffset = $("#" + ptype + "_" + ntype).offset();
				$("#menuContent").css({
					left : objOffset.left + "px",
					top : objOffset.top + obj.outerHeight() + "px"
				}).slideDown("fast");
				$("#menuContent_relation").css({
					left : objOffset.left + "px",
					top : objOffset.top + obj.outerHeight() + "px"
				}).slideDown("fast");

				$("body").bind("mousedown", onBodyDown);
			}
			function hideMenu() {
				$("#menuContent").fadeOut("fast");
				$("#menuContent_relation").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
			function onBodyDown(event) {
				if (!(event.target.id == "menuBtn" || event.target.id == "citySel"
						|| event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0 || event.target.id == "menuContent_relation" 
						|| $(event.target).parents("#menuContent_relation").length > 0)) {
					hideMenu();
				}
			}
	</script>
</head>

<body>
<h1>Checkbox 勾选操作</h1>
<h6></h6>
<input type="button" value="加载树" onclick="onloadZTree('treeDemo','42','organ','organ',true,false);"></input>
<div  class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="right">
		
		<ul class="list">
			<li class="title">&nbsp;&nbsp;<span class="highlight_red">勾选 checkbox 或者 点击节点 进行选择</span></li>
			<li class="title">&nbsp;&nbsp;父模块: <input id="pType" type="text" readonly value="" style="width:120px;" /></li>
		
			<li class="title">&nbsp;&nbsp;用户: <input id="user" type="text" readonly value="" style="width:120px;" onclick="showMenu('user');" />
		&nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">select</a></li>
			<li class="title">&nbsp;&nbsp;角色: <input id="role" type="text" readonly value="" style="width:120px;" onclick="showMenu('role');" />
		&nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">select</a></li>
			<li class="title">&nbsp;&nbsp;组织机构: <input id="organ" type="text" readonly value="" style="width:120px;" onclick="showMenu('organ');" />
		&nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">select</a></li>
		<input type="button" value="一键绑定" onclick="bind();"></input>
		</ul>
		<ul class="info">
			<li class="title"><h2>1、多选配置</h2>
				<ul class="list">
				<li><p>父子关联关系：<br/>
						被勾选时：<input type="checkbox" id="py" class="checkbox first" checked /><span>关联父</span>
						<input type="checkbox" id="sy" class="checkbox first" checked /><span>关联子</span><br/>
						取消勾选时：<input type="checkbox" id="pn" class="checkbox first" checked /><span>关联父</span>
						<input type="checkbox" id="sn" class="checkbox first" checked /><span>关联子</span><br/>
						<ul id="code" class="log" style="height:20px;"></ul></p>
				</li>
				</ul>
			</li>
			<li class="title"><h2>2、解绑关联</h2>
				<ul class="list">
			<li class="title">&nbsp;&nbsp;组织: <input id="organ_organ" type="text" readonly value="" style="width:120px;" onclick="showRelationMenu('tree_relation','42','organ','organ',true,true);" />&nbsp;</li>
			<li class="title">&nbsp;&nbsp;用户: <input id="organ_user" type="text" readonly value="" style="width:120px;" onclick="showRelationMenu('tree_relation','42','organ','user',false,true);" />&nbsp;</li>
			<li class="title">&nbsp;&nbsp;角色: <input id="organ_role" type="text" readonly value="" style="width:120px;" onclick="showRelationMenu('tree_relation',42,'organ','role',false,true);" />&nbsp;</li>
				</ul>
			</li>
		</ul>
	</div>
</div>

<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="tree_obj" class="ztree" style="margin-top:0; width:220px; height: 300px;"></ul>
</div>
<div id="menuContent_relation" class="menuContent" style="display:none; position: absolute;">
	<ul id="tree_relation" class="ztree" style="margin-top:0; width:220px; height: 300px;"></ul>
</div>
</body>
</html>
