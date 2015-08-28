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
	<link rel="stylesheet" href="css/demo.css" type="text/css">
	<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.core-3.0.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.excheck-3.0.js"></script>
<!-- 	<script type="text/javascript" src="js/jquery.ztree.exedit-3.0.js"></script>	 -->
<SCRIPT type="text/javascript">
		<!--
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onNodeCreated: onNodeCreated
			}
		};

		var dataMaker = function(count) {
			var nodes = [], pId = -1,
			min = 10, max = 90, level = 0, curLevel = [], prevLevel = [], levelCount,
			i = 0,j,k,l,m;

			while (i<count) {
				if (level == 0) {
					pId = -1;
					levelCount = Math.round(Math.random() * max) + min;
					for (j=0; j<levelCount && i<count; j++, i++) {
						var n = {id:i, pId:pId, name:"Big-" +i};
						nodes.push(n);
						curLevel.push(n);
					}
				} else {
					for (l=0, m=prevLevel.length; l<m && i<count; l++) {
						pId = prevLevel[l].id;
						levelCount = Math.round(Math.random() * max) + min;
						for (j=0; j<levelCount && i<count; j++, i++) {
							var n = {id:i, pId:pId, name:"Big-" +i};
							nodes.push(n);
							curLevel.push(n);
						}
					}
				}
				prevLevel = curLevel;
				curLevel = [];
				level++;
			}
			return nodes;
		}

		var ruler = {
			doc: null,
			ruler: null,
			cursor: null,
			minCount: 5000,
			count: 5000,
			stepCount:500,
			minWidth: 30,
			maxWidth: 215,
			init: function() {
				ruler.doc = $(document);
				ruler.ruler = $("#ruler");
				ruler.cursor = $("#cursor");
				if (ruler.ruler) {
					ruler.ruler.bind("mousedown", ruler.onMouseDown);
					
				}
			},
			onMouseDown: function(e) {
				ruler.drawRuler(e, true);
				ruler.doc.bind("mousemove", ruler.onMouseMove);
				ruler.doc.bind("mouseup", ruler.onMouseUp);
				ruler.doc.bind("selectstart", ruler.onSelect);
				$("body").css("cursor", "pointer");
			},
			onMouseMove: function(e) {
				ruler.drawRuler(e);
				return false;
			},
			onMouseUp: function(e) {
				$("body").css("cursor", "auto");
				ruler.doc.unbind("mousemove", ruler.onMouseMove);
				ruler.doc.unbind("mouseup", ruler.onMouseUp);
				ruler.doc.unbind("selectstart", ruler.onSelect);
				ruler.drawRuler(e);
			},
			onSelect: function (e) {
				return false;
			},
			getCount: function(end) {
				var start = ruler.ruler.offset().left+1;
				var c = Math.max((end - start), ruler.minWidth);
				c = Math.min(c, ruler.maxWidth);
				return {width:c, count:(c - ruler.minWidth)*ruler.stepCount + ruler.minCount};
			},
			drawRuler: function(e, animate) {
				var c = ruler.getCount(e.clientX);
				ruler.cursor.stop();
				if ($.browser.msie || !animate) {
					ruler.cursor.css({width:c.width});
				} else {
					ruler.cursor.animate({width:c.width}, {duration: "fast",easing: "swing", complete:null});
				}
				ruler.count = c.count;
				ruler.cursor.text(c.count);
			}
		}
		var showNodeCount = 0;
		function onNodeCreated(event, treeId, treeNode) {
			showNodeCount++;
		}

		function createTree () {
			var zNodes = dataMaker(ruler.count);
			showNodeCount = 0;
			$("#treeDemo").empty();
			setting.check.enable = $("#showChk").attr("checked");
			var time1 = new Date();
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			var time2 = new Date();

			alert("节点共 " + zNodes.length + " 个, 初始化生成 DOM 的节点共 " + showNodeCount + " 个"
				+ "\n\n 初始化 zTree 共耗时: " + (time2.getTime() - time1.getTime()) + " ms");
		}


		$(document).ready(function(){
			ruler.init("ruler");
			$("#createTree").bind("click", createTree);

		});
		//-->
	</SCRIPT>
 </HEAD>

<BODY>
<h1>一次性加载大数据量</h1>
<h6>[ 文件路径: bigdata/common.html ]</h6>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul>
		<li><span>调整总节点数，测试加载速度：</span>
			<div id="ruler" class="ruler" title="拖拽可调整节点数">
				<div id="cursor" class="cursor">5000</div>
			</div>
			<div style="width:220px; text-align: center;">
				<span>checkbox</span><input type="checkbox" id="showChk" title="是否显示 checkbox" class="checkbox first" />&nbsp;&nbsp;&nbsp;&nbsp;
				[ <a id="createTree" href="#" title="初始化 zTree" onclick="return false;">初始化 zTree</a> ]
			</div>
		</li>
		</ul>
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="right">
		<ul class="info">
			<li class="title"><h2>1、大数据量加载说明</h2>
				<ul class="list">
				<li>1)、zTree v3.x 针对大数据量一次性加载进行了更深入的优化，实现了延迟加载功能，即不展开的节点不创建子节点的 DOM。</li>
				<li class="highlight_red">2)、对于每级节点最多一百左右，但总节点数几千甚至几万，且不是全部展开的数据，一次性加载的效果最明显，速度非常快。</li>
				<li class="highlight_red">3)、对于某一级节点数就多达几千的情况 延迟加载无效，这种情况建议考虑分页异步加载。</li>
				<li class="highlight_red">4)、对于全部节点都展开显示的情况，延迟加载无效，这种情况建议不要全部展开。</li>
				<li>5)、显示 checkbox / radio 会造成一定程度的性能下降。</li>
				<li>6)、利用 addDiyDom 功能增加自定义控件会影响速度，影响程度受节点数量而定。</li>
				<li>7)、利用 onNodeCreated 事件回调函数对节点 DOM 进行操作会影响速度，影响程度受节点数量而定。</li>
				</ul>
			</li>
			<li class="title"><h2>2、setting 配置信息说明</h2>
				<ul class="list">
				<li>不需要进行特殊的配置</li>
				</ul>
			</li>
			<li class="title"><h2>3、treeNode 节点数据说明</h2>
				<ul class="list">
				<li>对 节点数据 没有特殊要求，用户可以根据自己的需求添加自定义属性</li>
				</ul>
			</li>
		</ul>
	</div>
</div>
</BODY>
</html>
