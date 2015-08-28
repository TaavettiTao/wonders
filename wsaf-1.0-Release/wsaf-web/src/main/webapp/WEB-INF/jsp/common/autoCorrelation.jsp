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

<link rel="stylesheet" type="text/css" href="css/screen.css">
<script type="text/javascript"
	src="js/jquery-ui-1.11.1.custom/external/jquery/jquery.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/defaultJs.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/jquery-ui-1.11.1.custom/jquery-ui.css" />
<script type="text/javascript"
	src="js/jquery-ui-1.11.1.custom/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/jquery-ui-1.11.1.custom/jquery-ui.theme.min.css" />
<script type="text/javascript" src="js/modernizr.custom.54158.js"></script>
<script type="text/javascript" src="js/jquery.cookie.js"></script>

<link rel="stylesheet" href="css/demo.css" type="text/css">
	<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="js/jquery.ztree.exedit-3.5.js"></script>	
	<SCRIPT type="text/javascript">
		var dragId;var zTree_Menu;
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false,
				showLine: false,
				selectedMulti: false,
				showIcon: false
			},
			edit: {
				enable: true,
				showRemoveBtn: setRemoveBtn,
				removeTitle:"删除分类",
				renameTitle:"编辑分类",
				drag: {
			          prev: true,
			          next: true,
			          inner: false
		        },
				editNameSelectAll: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeClick: beforeClick,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename,
				beforeDrop: beforeDrop
			}
		};
                //采用简单数据模式 (Array)
		var zNodes =[
			{ id:1, pId:0, name:"拖拽 1"},
			{ id:11, pId:1, name:"拖拽 1-1"},
			{ id:111, pId:11, name:"拖拽 1-1-1"},
			{ id:12, pId:1, name:"拖拽 1-2"},
			{ id:121, pId:12, name:"拖拽 1-2-1"},
			
			{ id:122, pId:12, name:"拖拽 1-2-2"},
			{ id:1221, pId:121, name:"拖拽 1-2-2-1"},
			{ id:123, pId:12, name:"拖拽 1-2-3"},
			{ id:1231, pId:123, name:"拖拽 1-2-3-1"},
			{ id:1232, pId:123, name:"拖拽 1-2-3-2"},
			{ id:1233, pId:123, name:"拖拽 1-2-3-3"},
			{ id:2, pId:0, name:"拖拽 2"},
			{ id:21, pId:2, name:"拖拽 2-1"},
			{ id:22, pId:2, name:"拖拽 2-2"},
			{ id:23, pId:2, name:"拖拽 2-3"},
			{ id:3, pId:0, name:"拖拽 3"},
			{ id:31, pId:3, name:"拖拽 3-1"},
			{ id:32, pId:3, name:"拖拽 3-2"},
			{ id:33, pId:3, name:"拖拽 3-3"}
		];
 
		function beforeDrag(treeId, treeNodes) {//用于捕获节点被拖拽之前的事件回调函数
			return false;
		}
		function beforeEditName(treeId, treeNode) {//用于捕获节点编辑按钮的 click 事件，并且根据返回值确定是否允许进入名称编辑状态
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return true;
		}
		function beforeRemove(treeId, treeNode) {//用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return confirm("确认删除 分类 -- " + treeNode.name + " 吗？");
		}
		function onRemove(e, treeId, treeNode) {//用于捕获删除节点之后的事件回调函数
			
		}
		function beforeRename(treeId, treeNode, newName) {//更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
			if (newName.length == 0) {
				alert("分类名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		function onRename(e, treeId, treeNode) {//用于捕获节点编辑名称结束之后的事件回调函数
			
		}
 
		var newCount = 1;
		function addHoverDom(treeId, treeNode) {//用于当鼠标移动到节点上时，显示用户自定义控件
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='添加分类' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
				return false;
			});
		}
		function setRemoveBtn(treeId, treeNode) {//父分类去掉删除功能
		   return !treeNode.isParent;
               
       <span style="white-space:pre">		</span>}
       
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		
		function beforeDrag(treeId, treeNodes) {//拖拽时执行
			for (var i=0,l=treeNodes.length; i<l; i++) {
				   dragId = treeNodes[i].pId;
				if (treeNodes[i].drag === false) {
					return false;
				}
			}
			return true;
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType) {//拖拽释放后执行
				  if(targetNode.pId == dragId){
				    return true;
				  }else{
			        return false;
			      }
		}
		
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		};
		
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);//初始化ztree
			zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
			$("#selectAll").bind("click", selectAll);
		});
		
	</SCRIPT>
</head>
<body>
    <div class="widget-box">
        <div class="widget-title"> <span class="icon"> <i class="icon-th"></i> </span>
            <h5>分类管理</h5>
        </div>
        <div class="widget-content tab-content" >
              <!--分类管理开始-->
             <div class="content_wrap" >
                  <div class="zTreeDemoBackground ">
                      <ul id="treeDemo" class="ztree"></ul>
                  </div>
             </div>
              <!--分类管理结束-->
        </div>
    </div>
</body>
</html>
