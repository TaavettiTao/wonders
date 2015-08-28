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
<title>规则绑定</title>


<link rel="stylesheet" href="css/demo.css" type="text/css">
<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" type="text/css" href="css/screen.css">

<!-- <script type="text/javascript" src="js/jquery-ui-1.11.1.custom/external/jquery/jquery.js"></script> -->
<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<link rel="stylesheet" type="text/css" href="js/jquery-ui-1.11.1.custom/jquery-ui.css" />

<link rel="stylesheet" type="text/css" href="js/jquery-ui-1.11.1.custom/jquery-ui.theme.min.css" />

<script type="text/javascript" src="js/jquery-ui-1.11.1.custom/jquery-ui.js"></script>	
<link rel="stylesheet" href="css/jquery.multiSelect.css" type="text/css"
	media="screen" />
<script type="text/javascript" src="js/modernizr.custom.54158.js"></script>

<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/defaultJs.js"></script>
<!-- <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script> -->
<script type="text/javascript" src="js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="js/tree_obj.js"></script>
<script type="text/javascript" src="js/tree_auto_correlation.js"></script>
<script type="text/javascript" src="js/tree_async.js"></script>
<script type="text/javascript" src="js/jquery.multiSelect.js"></script>
<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="js/jquery-ui-1.11.1.custom/jquery-ui.css" /> -->
<!-- <script type="text/javascript" -->
<!-- 	src="js/jquery-ui-1.11.1.custom/jquery-ui.js"></script> -->
<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="js/jquery-ui-1.11.1.custom/jquery-ui.theme.min.css" /> -->
<script type="text/javascript" src="js/jquery.cookie.js"></script>


<script type="text/javascript">
	$(function() {
// 		$("#pveast").width($(".v-top").width()-90);
// 		alert($(window).width()-90);
	    $("#logout").click(function(){
	         if(confirm("您确认退出系统吗？")){
	           $.cookie('user', '', {expires:-1,path:'/'}); // 删除 cookie
	           $("#logout").attr("href","<%=basePath%>logout");
			}
		});
		//实现一键清空功能
		$("#oneKeyClear").click(function() {
			$("#dynamicBody").remove();
		});

		initModule();
		$(".v-menu ul li").click(function(e) {
							$("#tableArea").empty();
							$("#bindDiv").empty();

							$("#dynamicBody").remove();
							// 	    	$("#pveast").remove();
							//获取已存在的对象,首先将其恢复为初始态
							var relationObjs = $(".v-menu ul li");
							for (var i = 0; i < relationObjs.length; i++) {
								var currentObj = $(relationObjs[i]).attr("class").replace(
										"ui-draggable ui-draggable-handle", "").trim();

								var content = $(
										"#" + currentObj + " div div h1").text();
								if (typeof (content) == "undefined") {
									continue;
								} else if (content.indexOf("-", 0) != -1) {
									content = content.substring(0, content
											.indexOf("-", 0));
								}
								$("#" + currentObj + " div div h1").text(
										content);
								$("#" + currentObj + " div div h1").removeAttr(
										"style");
							}
							var a = $(".v-menu ul li a");
							for (var i = 0; i < a.length; i++) {
								$(a[i]).removeAttr("style");
							}
							var currentObj = $(this).attr("class").replace(
									"ui-draggable ui-draggable-handle", "")
									.trim();
							$(this).children("a").attr("style",
									"color:black;font-weight:bold");

							// 		       var modname  = e.target.className;
							var pmodname = $(this).attr("class");
							if ($("#" + pmodname + "Div").length > 0) {
								alert("当前模块已存在");
								return false;
							}
							var ruleTypeId = getUrlParam('ruleTypeId');
							var rule = getRule(ruleTypeId, pmodname);
							
							//获取所有存在的对象
							var nextNodes = getRuleNodes(true,rule,pmodname);
							for (var i = 0; i < nextNodes.length; i++) {
								var modname = nextNodes[i];
								var modCnName = nextNodes[i];
								if ($("#" + modname).length == 0) {
// 									对该div启用拖放功能
// 									$("#" + modname).draggable({
// 										revert : true,
// 									});
									getTableInfo(modname);
									
								}
							}
							var height = $(window).height()-75;
							getTree(ruleTypeId, pmodname, rule);
							$("div.zTreeDemoBackground").css("height", (height - 18)/2);
	
				
// 							var _strTable = "<input type=\"hidden\" id=\"pType\"\>";
// 							_strTable += "<input type=\"button\" value=\"绑定\" onclick=\"bind1('"+ pmodname + "');\"\>";
									var _strTable =	"<div class=\"ui-tools-bottom\">";
									_strTable += 	"<div class=\"ui-tools-app\">";
									_strTable += 		"<a class=\"item-tools\" href=\"http://www.baidu.com\"";
										_strTable += 		"target=\"_blank\"><span class=\"item-ico\"><i";
										_strTable += 					"class=\"ui-ico ui-ico-mobile\"></i></span><span class=\"item-txt\">移动应用</span>";
										_strTable += 				"<div class=\"ui-poptipnoc ui-poptipnoc-left\">";
										_strTable += 					"<div class=\"ui-poptipnoc-arrow\">";
										_strTable += 						"<i></i>";
										_strTable += 					"</div>";
										_strTable += 				"<div class=\"ui-poptipnoc-bd\">";
										_strTable += 			"<div>";
										_strTable += 			"<div class=\"item-weixin\"></div>";
										_strTable += 			"<p>";
										_strTable += 				"官方微信<br>轻松找人做事";
										_strTable += 			"</p>";
										_strTable += 		"</div>";
										_strTable += 		"<div class=\"ui-vline\"></div>";
										_strTable += 		"<div>";
										_strTable += 			"<div class=\"item-mobile\"></div>";
										_strTable += 			"<p>";
										_strTable += 			"下载新版APP<br>";
										_strTable += 			"<span style=\"color: #ff0000\"> 超底抢购优质服务</span>";
										_strTable += 		"</p>";
										_strTable += 	"</div>";
										_strTable += 	"</div>";
										_strTable += "</div></a>";
										_strTable += "</div>";
										_strTable += "<div class=\"ui-tools-feed\">";
											_strTable += 	"<a class=\"item-tools \" id=\"j-feedback-btn\"><span";
											_strTable += 	"class=\"item-ico\"><i class=\"ui-ico ui-ico-feed\"></i></span>";
											_strTable += 	"<span class=\"item-txt\" id=\"pType\"  onclick=\"bind1('"+ pmodname + "');\">绑定数据</span></a>";
											_strTable += "</div>";
										_strTable += "<div class=\"ui-tools-feed\">";
// 											_strTable += 	"<a href=\"${pageContext.request.contextPath}/jsp/ruleType/list/\" target=\"_blank\" ";
											_strTable += 	"<a class=\"item-tools \" id=\"j-feedback-btn\"><span";
											_strTable += 	"class=\"item-ico\"><i class=\"ui-ico ui-ico-feed\"></i></span>";
											_strTable += 	"<span class=\"item-txt\" onclick=\"explain();\">绑定说明</span></a>";
											_strTable += "</div>";
// 												_strTable += "<div class=\"ui-tools-gotop\" style=\"visibility: visible;\">";
// 												_strTable += 	"<a class=\"item-tools\" href=\"javascript:;\" title=\"返回顶部\"";
// 													_strTable += 		"id=\"j-goTop\"><i class=\"icon-font\"></i></a>";
// 													_strTable += "</div>";
													_strTable += "</div>";
							$("#bindDiv").append(_strTable);

						});

		var height = $(window).height();
		$(".v-menu").css("height", height-75);
		$("#pveast").css("height", height - 75);
		$("#tableArea").css("height", height - 75);
		$("#tableArea").css("overflow", "auto");
	});

	//初始化模块
	function initModule() {
		var pobjType = getUrlParam('pobjType');
		var ruleTypeId = getUrlParam('ruleTypeId');
		getRuleObjs(ruleTypeId, pobjType);
		getObjInfo();
	}

	function isLoginTimeout(e) {
		var event = window.event;
		var obj = event.srcElement ? event.srcElement : event.target; //事件对象：事件对象实例
		var id = $(obj).attr("id");
		if (id == "logout") {
			e.stopPropagation();
		} else {
			var username = $.cookie('user');
			if (username == null || username == "") {
				alert("您已登录超时，请重新登录！");
				//跳转当前页地址栏完整URL
				window.location.href = window.top.document.URL;
			}
		}
	}

	function showMenu(objName) {
		onloadZTree_obj(objName);
		var obj = $("#" + objName);
		var objOffset = $("#" + objName).offset();
		$("#menuContent").css({
			left : objOffset.left + "px",
			top : objOffset.top + obj.outerHeight() + "px"
		}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}

	function showRelationMenu(treeId, ruleTypeId, ptype, ntype,
			autoCorrelation, isRelationTree) {
		// 		onloadZTree(treeId, ruleTypeId, ptype, ntype, autoCorrelation, isRelationTree);
		onloadZTree_async(treeId, ruleTypeId, ptype, ntype, autoCorrelation,
				isRelationTree);
		var obj = $("#" + ptype + "_" + ntype);
		var objOffset = $("#" + ptype + "_" + ntype).offset();
		$("#menuContent").css({
			left : objOffset.left + "px",
			top : objOffset.top + obj.outerHeight() + "px"
		}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("#tree_obj").empty();

		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "citySel"
				|| event.target.id == "menuContent"
				|| $(event.target).parents("#menuContent").length > 0
				|| event.target.id == "menuContent_relation" || $(event.target)
				.parents("#menuContent_relation").length > 0)) {
			hideMenu();
		}
	}

	function changeHeight() {
		var height = $(window).height();
		$(".v-menu").css("height", height);
		$("#pveast").css("height", height - 75);
		$("#tableArea").css("height", $("#pveast").height() - 27-39);
	}
	//解决jQuery的resize事件执行两次的方法
	var resizeTimer = null;
	$(window).resize(function() {
		if (resizeTimer)
			clearTimeout(resizeTimer);
		resizeTimer = setTimeout("changeHeight()", 500);
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
<style type="text/css">
div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	background-color: #555;
	text-align: left;
/* 	padding: 2px; */
}

div#rMenu #m_add {
	margin: 1px;
	padding: 1px 5px;
	cursor: pointer;
	list-style: none outside none;
	background-color: #DFDFDF;
/* 	margin-right:2px;  */
	background-position:-144px 0; vertical-align:top; *vertical-align:middle
}
</style>
</head>
<!-- <body id="default"  onclick="isLoginTimeout(event)" > -->
<body id="default">
	<div id="dialog" style="display: none">
		<p style="margin-left: 5px;">是否绑定（解绑）数据？</p>
	</div>
	<form>
		<div class="v-center">
			<div class="v-menu">
				<ul>
				</ul>
			</div>
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
<%-- 									<li>${authentication.dept}</li> --%>
									<li><b class="clr_bwn">${sessionScope.loginUser.name}</b>，欢迎登录
										&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:void();"
										id="logout">退出系统</a></li>
								</ul>

							</div>
						</span>
					</div>
				</div>
				<div id="pveast" class="fl" style="margin-left: 90px">
<!-- 					<div style="width: 49%" class="fl w250"> -->
					<div class="fl w250">
<!-- 						<h1>树形操作</h1> -->
						<div id="veast">
<!-- 						<li>操作 [ <a id="expandAllBtn" href="#" -->
<!-- 							onclick="return false;">全部展开</a> ] -->
<!-- 							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [ <a id="asyncAllBtn" -->
<!-- 							href="#" onclick="return false;">后台自动全部加载</a> ] -->
<!-- 							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [ <a id="resetBtn" href="#" -->
<!-- 							onclick="return false;">Reset zTree</a> ] -->
<!-- 							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 							<p class="highlight_red" id="demoMsg"></p> -->
<!-- 						</li> -->
						
						</div>
						<div class="clearfix"></div>
						<div id="detailInfo" style="width: 100%; overflow: auto;";>
<!-- 							<h1>详细信息</h1> -->
						</div>
					</div>

					<div class="fr right" style="width: 72%;padding-right: 70px">
<!-- 						<h1>列表展示</h1> -->
						<div id="tableArea"></div>
						<div id="bindDiv"></div>
					</div>
					<div id="rMenu">
						<ul>
							<li id="m_add">增加节点</li>
<!-- 							<li id="m_del" onclick="removeTreeNode();">删除节点</li> -->
<!-- 							<li id="m_check" onclick="checkTreeNode(true);">Check节点</li> -->
<!-- 							<li id="m_unCheck" onclick="checkTreeNode(false);">unCheck节点</li> -->
<!-- 							<li id="m_reset" onclick="resetTree();">恢复zTree</li> -->
						</ul>
					</div>
					 <!--效果html开始-->
<!-- 					<div class="box_r"> -->
<!-- 						<div class="top"></div> -->
<!-- 					    <div class="main"> -->
<!-- 					    	<img src="images/kf.gif" style="margin-bottom:10px;" /> -->
<!-- 					        <p>在线客服1：<a href="http://www.internetke.com/" target="_blank">网页设计</a></p> -->
<!-- 					        <p>在线客服2：<a href="http://www.internetke.com/" target="_blank">网页制作</a></p> -->
<!-- 					        <p>在线客服3：<a href="http://www.internetke.com/" target="_blank">UI设计</a></p> -->
<!-- 					        <p>在线客服4：<a href="http://www.internetke.com/" target="_blank">网页特效</a></p> -->
<!-- 					        <img src="images/zx.gif" style="margin-top:10px;" /> -->
<!-- 					    </div> -->
<!-- 					    <div class="bottom"></div> -->
<!-- 					</div> -->				
				</div>
			</div>
		</div>
	</form>
</body>
</html>
