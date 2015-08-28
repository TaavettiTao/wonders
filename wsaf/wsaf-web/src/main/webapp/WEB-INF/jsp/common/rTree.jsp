<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
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

<link rel="stylesheet" type="text/css" href="css/screen.css">


<link rel="stylesheet" href="css/demo.css" type="text/css">
<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css"
	type="text/css">

<script type="text/javascript"
	src="js/jquery-ui-1.11.1.custom/external/jquery/jquery.js"></script>
<!-- <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script> -->
<script type="text/javascript" src="js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="js/tree_obj.js"></script>
<script type="text/javascript" src="js/tree_auto_correlation.js"></script>
<script type="text/javascript" src="js/tree_async.js"></script>


<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="js/jquery-ui-1.11.1.custom/jquery-ui.css" /> -->
<!-- <script type="text/javascript" -->
<!-- 	src="js/jquery-ui-1.11.1.custom/jquery-ui.js"></script> -->
<!-- <link rel="stylesheet" type="text/css" -->
<!-- 	href="js/jquery-ui-1.11.1.custom/jquery-ui.theme.min.css" /> -->
<script type="text/javascript" src="js/jquery.cookie.js"></script>


<script type="text/javascript">
    var objData="";
	$(function() {
	
	    $("#logout").click(function(){
	         if(confirm("您确认退出系统吗？")){
	           $.cookie('user', '', {expires:-1,path:'/'}); // 删除 cookie
	           $("#logout").attr("href","<%=basePath%>common/logout");
	         }
	    });
	  //实现一键清空功能
	    $("#oneKeyClear").click(function(){
	    	$("#dynamicBody").remove();
	    });
	    
	    initModule();
	    $(".v-menu ul li").click(function(e){
	    	
	    	$("#dynamicBody").remove();
		       //获取已存在的对象,首先将其恢复为初始态
		       var relationObjs=$(".v-menu ul li");
		       for(var i=0;i<relationObjs.length;i++){
		          var currentObj=$(relationObjs[i]).attr("class").replace("ui-draggable ui-draggable-handle","").trim();
		          
		          var content=$("#"+currentObj+" div div h1").text();
		          if(typeof(content)=="undefined"){
		            continue;
		          }else if(content.indexOf("-",0)!=-1){
		             content=content.substring(0,content.indexOf("-",0));
		          }
		         $("#"+currentObj+" div div h1").text(content);
		         $("#"+currentObj+" div div h1").removeAttr("style");
		       }
		       var a=$(".v-menu ul li a");
		       for(var i=0;i<a.length;i++){
		          $(a[i]).removeAttr("style");
		       }
		       var currentObj=$(this).attr("class").replace("ui-draggable ui-draggable-handle","").trim();
		       $(this).children("a").attr("style","color:black;font-weight:bold");
		       
// 		       var modname  = e.target.className;
		       var pmodname = $(this).attr("class");
		       if($("#"+pmodname+"Div").length>0){
		    	    alert("当前模块已存在");
		    		return false;
				}
		       var ruleTypeId = getUrlParam('ruleTypeId');
		       var rule = $.getRule("${pageContext.request.contextPath}/",ruleTypeId,pmodname);
		       commonTable(ruleTypeId,pmodname,rule);
	 	});
	    

		var height = $(window).height();
		$(".v-menu").css("height", height);
		$("#pveast").css("height", height - 104);

	});
	
	function commonTable(ruleTypeId,pmodname,rule){
		var autoCorrelation = false;
		var isRelationTree = false;
		var tmp = "";
		var nodes = [];
		var tmpNtypeIds = ",", tmpPtypeIds = ",";
		for ( var a in rule) {
			var ruleTmp = rule[a];
			var nextRule = ruleTmp.substring(ruleTmp.indexOf(">") + 1);
			// 判断自增长
			if (nextRule.indexOf(pmodname) >= 0) {
				nextRule = nextRule.substring(nextRule.indexOf(">") + 1);
				autoCorrelation=true;
			}
			// 判断是否尾节点
			if (nextRule.indexOf(">") == -1) {
				tmp += nextRule + ",";
			} else {
				var nextObj = nextRule.substring(0, nextRule.indexOf(">"));
				if (tmp.indexOf(nextObj) < 0) {// 判断是否有重复子节点
					tmp += nextObj + ",";
				}
			}
		}
	
		tmp = tmp.substring(0, tmp.lastIndexOf(","));
		var nextNodes =tmp.split(",");
		 var _strTable = "<div id=\"dynamicBody\" class='"+pmodname+"'>";
// 		 if(autoCorrelation){
// 		 	_strTable+= "<input type=\"button\" value=\"加载树\" onclick=\"loopTree('treeDemo','"+ruleTypeId+"',true,'"+pmodname+"');\"></input>";
// 		 }else{
// 			_strTable+= "<input type=\"button\" value=\"加载树\" onclick=\"loopTree('treeDemo','"+ruleTypeId+"',true,'"+pmodname+"');\"></input>";
// 		 }
		 if(autoCorrelation){
			 	_strTable+= "<input type=\"button\" value=\"加载树\" onclick=\"onloadZTree('treeDemo','"+ruleTypeId+"','"+pmodname+"','"+pmodname+"',"+autoCorrelation+",false);\"></input>";
			 }else{
				_strTable+= "<input type=\"button\" value=\"加载树\" onclick=\"onloadZTree('treeDemo','"+ruleTypeId+"','"+pmodname+"','',"+autoCorrelation+",false);\"></input>";
			 }
		_strTable+="<div  class=\"content_wrap\" id=\""+pmodname+"Div\"><div class=\"zTreeDemoBackground left\"><ul id=\"treeDemo\" class=\"ztree\"></ul></div>";
		_strTable+="<div class=\"right\"><ul class=\"list\">";
		
		_strTable+="<div id=\"pTypeModule\">";
		_strTable+="<li class=\"title\">&nbsp;&nbsp;<span class=\"highlight_red\">勾选 checkbox 或者 点击节点 进行选择</span></li>";
		_strTable+="<li class=\"title\">&nbsp;&nbsp;父模块: <input id=\"pType\" type=\"text\" readonly value=\"\" style=\"width:120px;\" /></li>";
		_strTable+="</div>";
		_strTable+="<div id=\"dynamicRelation\">";
		_strTable+="<div id=\"nTypeModule\">";
		for(var i=0;i<nextNodes.length;i++){
			_strTable+="<li class=\"title\">&nbsp;&nbsp;"+nextNodes[i]+": <input id='"+nextNodes[i]+"' type=\"text\" readonly value=\"\" style=\"width:120px;\" onclick=\"showMenu('"+nextNodes[i]+"');\" />";
			_strTable+="&nbsp;<a id=\"menuBtn\" href=\"#\" onclick=\"showMenu(); return false;\">select</a></li>";
		}
		_strTable+="</div>";
		_strTable+="<input type=\"button\" value=\"一键绑定\" onclick=\"bindRelation1('"+pmodname+"');\"></input>";
		_strTable+="</ul>";
		_strTable+="<ul class=\"info\">";
				_strTable+="<li class=\"title\"><h2>1、解绑关联</h2>";
				_strTable+="<ul class=\"list\">";
				if(autoCorrelation){
					_strTable+="<li class=\"title\">&nbsp;&nbsp;"+pmodname+": <input id=\""+pmodname+"_"+pmodname+"\" type=\"text\" readonly value=\"\" style=\"width:120px;\" onclick=\"showRelationMenu('tree_obj','"+ruleTypeId+"','"+pmodname+"','"+pmodname+"',"+autoCorrelation+",true);\" />&nbsp;</li>";
				 }
				for(var i=0;i<nextNodes.length;i++){
					_strTable+="<li class=\"title\">&nbsp;&nbsp;"+nextNodes[i]+": <input id=\""+pmodname+"_"+nextNodes[i]+"\" type=\"text\" readonly value=\"\" style=\"width:120px;\" onclick=\"showRelationMenu('tree_obj','"+ruleTypeId+"','"+pmodname+"','"+nextNodes[i]+"',false,true);\" />&nbsp;</li>";
				}
				
// 				_strTable+="<li class=\"title\">&nbsp;&nbsp;组织: <input id=\"organ_organ\" type=\"text\" readonly value=\"\" style=\"width:120px;\" onclick=\"showRelationMenu('organ','organ','tree_relation',true);\" />&nbsp;</li>";
// 				_strTable+="<li class=\"title\">&nbsp;&nbsp;用户: <input id=\"organ_user\" type=\"text\" readonly value=\"\" style=\"width:120px;\" onclick=\"showRelationMenu('organ','user','tree_relation',true);\" />&nbsp;</li>";
// 				_strTable+="<li class=\"title\">&nbsp;&nbsp;角色: <input id=\"organ_role\" type=\"text\" readonly value=\"\" style=\"width:120px;\" onclick=\"showRelationMenu('organ','role','tree_relation',true);\" />&nbsp;</li>";
				_strTable+="</ul>";
			_strTable+="</li>";
		_strTable+="</ul>";
	_strTable+="</div>";
_strTable+="</div>";

_strTable+="<div id=\"menuContent\" class=\"menuContent\" style=\"display:none; position: absolute;\">";
	_strTable+="<ul id=\"tree_obj\" class=\"ztree\" style=\"margin-top:0; width:220px; height: 300px;\"></ul>";
_strTable+="</div>";
// _strTable+="<div id=\"menuContent_relation\" class=\"menuContent\" style=\"display:none; position: absolute;\">";
// 	_strTable+="<ul id=\"tree_relation\" class=\"ztree\" style=\"margin-top:0; width:220px; height: 300px;\"></ul>";
// _strTable+="</div>";
_strTable+="</div>";
		
	 	 $("#veast").append(_strTable);
// 	 	onloadZTree("organ","organ","treeDemo",false);
	}
	
	//获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
	//初始化模块
	function initModule(){
	    var pobjType= getUrlParam('pobjType');
	    var ruleTypeId= getUrlParam('ruleTypeId');
	    getRuleObjs(ruleTypeId,pobjType);
	    
	}
	
	//获取指定type、ptype条件下的规则记录
	 function getRuleObjs(ruleTypeId,pobjType){
           $.ajax({
                  url:'<%=basePath%>api/ruleType/'+ruleTypeId+'/rule/objs',
                  type:'GET',
                  dataType:'json',
                  async:false, //同步
                  data:{ 
                          'pobjType':pobjType,
//                           'ruleTypeId':ruleTypeId,
                  },
                  success:function(result){
                      if(result.info.success){
                         if(result.data!=null && result.data.length>0){
                            $(".v-menu ul").html("");
                            var items="";
                            if(pobjType=="" || pobjType==null){
                                for(var i=0;i<result.data.length;i++){
                                   items+="<li class=\""+result.data[i].type+"\" value=\""+result.data[i].id+"\" draggable=\"true\" ><a href=\"javascript:void(0);\">"+result.data[i].name+"</a></li>";
                                }
                            }else{
	                            for(var i=0;i<result.data.length;i++){
	                                if(i==0){
	                                  items+="<li class=\""+pobjType+"\" value=\""+getPobjTypeInfo(pobjType)[0].id+"\" draggable=\"true\" ><a href=\"javascript:void(0);\">"+getPobjTypeInfo(pobjType)[0].name+"</a></li>";
	                                }
	                                items+="<li class=\""+result.data[i].type+"\" value=\""+result.data[i].id+"\" draggable=\"true\"><a href=\"javascript:void(0);\">"+result.data[i].name+"</a></li>";
	                            } 
                            }
                            $(".v-menu ul").html(items);
                         }
                      }
                  }
           });
        }
        
	 //得到系统所有的对象信息
     function getPobjTypeInfo(pobjType){
		 var result = null;
		    $.ajax({
				type: 'GET',
				url: '<%=basePath%>objInfo',
			data : {
				type : pobjType
			},
			dataType : 'json',
			cache : false,
			async : false,
			error : function() {
				alert('系统连接失败，请稍后再试！')
			},
			success : function(obj) {
				if (obj.info.success) {
					if (obj.data != null && obj.data.length > 0) {
						result = obj.data;
					}
				}
			}
		});
		return result;
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

	//根据规则类型和父节点获取规则
	$.getRule = function(url, ruleTypeId, pobjType) {
		var rule = "";
		$.ajax({
			type : 'GET',
			url : url + 'api/ruleType/' + ruleTypeId + '/rule/paths?pobjType='
					+ pobjType,
			dataType : 'json',
			cache : false,
			async : false,
			error : function() {
				alert('系统连接失败，请稍后再试！');
			},
			success : function(result) {
				if (result.info.success) {
					rule = result.data;
				}
			}
		});
		return rule;
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

	function showRelationMenu(treeId, ruleTypeId, ptype, ntype, autoCorrelation, isRelationTree) {
// 		onloadZTree(treeId, ruleTypeId, ptype, ntype, autoCorrelation, isRelationTree);
		onloadZTree_async(treeId, ruleTypeId, ptype, ntype, autoCorrelation,isRelationTree);
		var obj = $("#" + ptype + "_" + ntype);
		var objOffset = $("#" + ptype + "_" + ntype).offset();
		$("#menuContent").css({
			left : objOffset.left + "px",
			top : objOffset.top + obj.outerHeight() + "px"
		}).slideDown("fast");
// 		$("#menuContent_relation").css({
// 			left : objOffset.left + "px",
// 			top : objOffset.top + obj.outerHeight() + "px"
// 		}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
// 		$("#menuContent_relation").fadeOut("fast");
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
	<div id="dialog" style="display: none">
		<p style="margin-left: 5px;">是否绑定（解绑）数据？</p>
	</div>
	<form>
		<div class="v-center">
			<div class="v-menu">
				<ul>
					<!-- 					<li class="user" draggable="true"><a href="#">用户</a></li> -->
					<!-- 					<li class="role" draggable="true"><a href="#">角色</a></li> -->
					<!-- 					<li class="privilege" draggable="true"><a href="#">权限</a></li> -->
					<!-- 					<li class="organ" draggable="true"><a href="#">部门</a></li> -->
				</ul>
			</div>
			<div class="v-main">
				<div class="v-top">
					<div id="radio" class="fl">
						<!-- 						<input type="radio" id="rdoFir" name="radio" checked="checked"> -->
						<!-- 						<label for="rdoFir" style="color:#c7188b">Level 1</label> <input -->
						<!-- 							type="radio" id="rdoSen" name="radio"> <label -->
						<!-- 							for="rdoSen" style="color:#ff8b19">Level 2</label> <input -->
						<!-- 							type="radio" id="rdoThi" name="radio"> <label -->
						<!-- 							for="rdoThi" style="color:#885df3">Level 3</label> -->

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
				<div id="pveast">

					<div id="veast">
						<h1>Checkbox 勾选操作</h1>
						<h6></h6>
					</div>
				</div>

			</div>
		</div>
	</form>
</body>
</html>
