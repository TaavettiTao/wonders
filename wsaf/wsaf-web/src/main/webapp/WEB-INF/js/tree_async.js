/**
 * js获取系统的根路径
 * 
 * @returns
 */
function getBasePath() {
	var obj = window.location;
	var contextPath = obj.pathname.split("/")[1];
	var basePath = obj.protocol + "//" + obj.host + "/" + contextPath;
	return basePath;
};
var basePath = getBasePath();
var demoMsg = {
	async : "正在进行异步加载，请等一会儿再点击...",
	expandAllOver : "全部展开完毕",
	asyncAllOver : "后台异步加载完毕",
	asyncAll : "已经异步加载完毕，不再重新加载",
	expandAll : "已经异步加载完毕，使用 expandAll 方法"
};

var ruleTypeId = getUrlParam('ruleTypeId');
// 不是关联树，异步加载
var setting_async = {
	check : {
		enable : true,
		chkboxType : {
			"Y" : "",
			"N" : ""
		}
	},
	async : {
		enable : true,
		url : getBasePath() + "/api/relation",
		autoParam : [ "realId=pid", "ruleTypeId", "ptype" ],// 自动为autoParam中参数赋值并传参
		dataFilter : filter,
		type : "get"
	},
	callback : {
		onClick : onClick,
		beforeAsync : beforeAsync,
		onAsyncSuccess : onAsyncSuccess,
		onAsyncError : onAsyncError
	}
};

// 是关联树，异步加载
var setting_relation_async = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
	},
	edit : {
		drag : {
			autoExpandTrigger : true,
			prev : dropPrev,
			inner : dropInner,
			next : dropNext
		},
		enable : true,
		showRemoveBtn : showRemoveBtn,
		removeTitle : "删除节点",
		showRenameBtn : false
	},
	check : {
		enable : true,
		chkboxType : {
			"Y" : "",
			"N" : ""
		},
		nocheckInherit : true
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		enable : true,
		url : getBasePath() + "/api/relation",
		autoParam : [ "realId=pid", "ruleTypeId", "ptype" ],
		dataFilter : filter,
		type : "get"
	},
	callback : {
//		onClick : onClick,
		onCheck : onCheck,
		beforeAsync : beforeAsync,
		onAsyncSuccess : onAsyncSuccess,
		onAsyncError : onAsyncError,
		// beforeDrag : beforeDrag,
		// beforeDrop : beforeDrop,
		// beforeDragOpen : beforeDragOpen,
		// onDrag : onDrag,
		// onDrop : onDrop,
		// onExpand : onExpand,
		beforeRemove : beforeRemove,
		onRemove : onRemove
	}
};
// 是反向关联树，异步加载
var setting_relation_reverse_async = {
	edit : {
		drag : {
			autoExpandTrigger : true,
			prev : dropPrev,
			inner : dropInner,
			next : dropNext
		},
		enable : true,
		showRemoveBtn : showRemoveBtn,
		showRenameBtn : false
	},
	check : {
		enable : false,
		chkboxType : {
			"Y" : "",
			"N" : ""
		},
		nocheckInherit : true
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	async : {
		enable : true,
		url : getBasePath() + "/api/relation",
		autoParam : [ "realId=nid", "ruleTypeId", "ntype" ],// realId都按nid进行传值
		dataFilter : reverseFilter,
		type : "get"
	},
	callback : {
		// onCheck : onCheck,
//		onClick : onClick,
		beforeAsync : beforeAsync,
		onAsyncSuccess : onAsyncSuccess,
		onAsyncError : onAsyncError,
		beforeRemove : beforeRemove,
		onRemove : onRemove
	}
};
function filter(treeId, parentNode, responseData) {
	var tmpNodes = [];
	var content = responseData.data;
	if (responseData.info.success) {
		if (content != null && content.length > 0) {
			for (var i = 0; i < content.length; i++) {
				// 将下级节点作为首节点进行查找，若count为0，说明没有下级节点了
				var isParents = isParent(ruleTypeId, content[i].ntype,
						content[i].nid, false);
				var n = {
					nocheck : true,
					realId : content[i].nid,
					id : content[i].ntype + "_" + content[i].nid,
					pId : content[i].ptype + "_" + content[i].pid,
					name : "[" + result[content[i].ntype] + "] "
							+ getObjName(content[i].ntype, content[i].nid),
					ptype : content[i].ntype,// 将父节点的ntype作为异步加载的下级ptype
					ruleTypeId : content[i].ruleTypeId,
					reverseTree : false,
					isParent : isParents
				};
				tmpNodes.push(n);
			}
		}
	}
	return tmpNodes;
}
function reverseFilter(treeId, parentNode, responseData) {
	var tmpNodes = [];
	var content = responseData.data;
	if (responseData.info.success) {
		if (content != null && content.length > 0) {
			for (var i = 0; i < content.length; i++) {
				var isParents = isParent(ruleTypeId, content[i].ptype,
						content[i].pid, true);
				var n = {
					nocheck : true,
					realId : content[i].pid,
					id : content[i].ptype + "_" + content[i].pid,
					pId : content[i].ntype + "_" + content[i].nid,
					name : "[" + result[content[i].ptype] + "] "
							+ getObjName(content[i].ptype, content[i].pid),
					ntype : content[i].ptype,// 将父节点的ptype作为异步加载的下级ntype
					ruleTypeId : content[i].ruleTypeId,
					reverseTree : true,
					isParent : isParents
				};
				tmpNodes.push(n);
			}
		}
	}
	return tmpNodes;
}

function isParent(ruleTypeId, type, id, isReverseTree) {
	var isParent = false;
	var _data;
	if (isReverseTree) {
		var _data = {
			ruleTypeId : ruleTypeId,
			ntype : type,
			nid : id
		};
	} else {
		var _data = {
			ruleTypeId : ruleTypeId,
			ptype : type,
			pid : id
		};
	}
	$.ajax({
		type : 'GET',
		url : getBasePath() + "/api/relation?count",
		dataType : 'json',
		async : false,
		data : _data,
		error : function() {
			alert('系统连接失败，请稍后再试！');
		},
		success : function(result) {
			if (result.data != 0 && result.data != null) {
				isParent = true;
			}
		}
	});
	return isParent;
}

function beforeAsync() {
	curAsyncCount++;
}

function onAsyncSuccess(event, treeId, treeNode, msg) {
	curAsyncCount--;
	if (curStatus == "expand") {
		expandNodes(treeNode.children);
	} else if (curStatus == "async") {
		asyncNodes(treeNode.children);
	}

	if (curAsyncCount <= 0) {
		if (curStatus != "init" && curStatus != "") {
			$("#demoMsg").text(
					(curStatus == "expand") ? demoMsg.expandAllOver
							: demoMsg.asyncAllOver);
			asyncForAll = true;
		}
		curStatus = "";
	}
}

function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
		errorThrown) {
	curAsyncCount--;

	if (curAsyncCount <= 0) {
		curStatus = "";
		if (treeNode != null)
			asyncForAll = true;
	}
}

var curStatus = "init", curAsyncCount = 0, asyncForAll = false, goAsync = false;
function expandAll() {
	if (!check()) {
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	if (asyncForAll) {
		$("#demoMsg").text(demoMsg.expandAll);
		zTree.expandAll(true);
	} else {
		expandNodes(zTree.getNodes());
		if (!goAsync) {
			$("#demoMsg").text(demoMsg.expandAll);
			curStatus = "";
		}
	}
}
function expandNodes(nodes) {
	if (!nodes)
		return;
	curStatus = "expand";
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	for (var i = 0, l = nodes.length; i < l; i++) {
		zTree.expandNode(nodes[i], true, false, false);
		if (nodes[i].isParent && nodes[i].zAsync) {
			expandNodes(nodes[i].children);
		} else {
			goAsync = true;
		}
	}
}

function asyncAll() {
	if (!check()) {
		return;
	}
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	if (asyncForAll) {
		$("#demoMsg").text(demoMsg.asyncAll);
	} else {
		asyncNodes(zTree.getNodes());
		if (!goAsync) {
			$("#demoMsg").text(demoMsg.asyncAll);
			curStatus = "";
		}
	}
}
function asyncNodes(nodes) {
	if (!nodes)
		return;
	curStatus = "async";
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	for (var i = 0, l = nodes.length; i < l; i++) {
		if (nodes[i].isParent && nodes[i].zAsync) {
			asyncNodes(nodes[i].children);
		} else {
			goAsync = true;
			zTree.reAsyncChildNodes(nodes[i], "refresh", true);
		}
	}
}

function reset() {
	if (!check()) {
		return;
	}
	asyncForAll = false;
	goAsync = false;
	$("#demoMsg").text("");
	$.fn.zTree.init($("#treeDemo"), setting);
}

function check() {
	if (curAsyncCount > 0) {
		$("#demoMsg").text(demoMsg.async);
		return false;
	}
	return true;
}

function onClick(e, treeId, treeNode) {
	var reverseTree = treeNode.reverseTree;
	var id = treeNode.realId;
	// if (reverseTree) {
	// var ntype = treeNode.ntype;
	// getSingleInfo(ntype, ntype, id);
	// } else {
	// var ptype = treeNode.ptype;
	// getSingleInfo(ptype, ptype, id);
	// }
	var ptype = treeNode.ptype;
	$.postOperForm(basePath + '/', ptype, id, 'view');
	// var v = "";
	// var cityObj = $("#pType");
	// var value = treeNode.realId;
	// cityObj.attr("value", value);
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId), nodes = zTree
			.getCheckedNodes(true), v = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		var pids = nodes[i].id;
		v += pids.substring(pids.indexOf("_") + 1) + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	var cityObj = $("#pType");
	cityObj.attr("value", v);
}

function onCheckNTypes(e, tableName) {
	var checkModule = $("#" + tableName).find("input[name='cbitem']:checked");

	var v = "";
	for (var i = 0, l = checkModule.length; i < l; i++) {
		var pids = checkModule[i].value;
		v += pids.substring(pids.indexOf("_") + 1) + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	var cityObj = $("#" + tableName + "Hidden");

	var tfootButton = $("#" + tableName + " tfoot button");
	$("#" + tableName + " tfoot button").click(
			function() {
				var inputModule = $("#" + tableName
						+ " table>tbody input[name='cbitem']");
				for (var i = 0, l = inputModule.length; i < l; i++) {
					var id = inputModule[i].value;
					$(this);
					if (v.indexOf(id) > 0) {
						inputModule[i].attr("checked", true);
					}
				}
			});
	cityObj.attr("value", v);
}

function getRuleInfo(ruleTypeId, pmodname, rule, treeNode) {

	var autoCorrelation = false;
	var isRelationTree = false;
	var tmp = "";
	for ( var a in rule) {
		var ruleTmp = rule[a];
		var nextRule = ruleTmp.substring(ruleTmp.indexOf(">") + 1);
		// 判断自增长
		if (nextRule.indexOf(pmodname) >= 0) {
			nextRule = nextRule.substring(nextRule.indexOf(">") + 1);
			autoCorrelation = true;
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
	var nextNodes = tmp.split(",");
	var _strTable = "";
	// _strTable+="<div class=\"right\"><ul class=\"list\">";
	$("#pTypeModule").remove();
	$("#dynamicRelation").remove();

	_strTable += "<div id=\"pTypeModule\">";
	_strTable += "<li class=\"title\">&nbsp;&nbsp;<span class=\"highlight_red\">勾选 checkbox 或者 点击节点 进行选择</span></li>";
	_strTable += "<li class=\"title\">&nbsp;&nbsp;父模块: <input id=\"pType\" type=\"text\" readonly value=\"\" style=\"width:120px;\" /></li>";
	_strTable += "</div>";
	_strTable += "<div id=\"dynamicRelation\">";
	_strTable += "<div id=\"nTypeModule\">";
	for (var i = 0; i < nextNodes.length; i++) {
		_strTable += "<li class=\"title\">&nbsp;&nbsp;"
				+ nextNodes[i]
				+ ": <input id='"
				+ nextNodes[i]
				+ "' type=\"text\" readonly value=\"\" style=\"width:120px;\" onclick=\"showMenu('"
				+ nextNodes[i] + "');\" />";
		_strTable += "&nbsp;<a id=\"menuBtn\" href=\"#\" onclick=\"showMenu(); return false;\">select</a></li>";
	}
	_strTable += "</div>";
	_strTable += "<input type=\"button\" value=\"一键绑定\" onclick=\"bind('"
			+ pmodname + "');\"></input>";
	_strTable += "</ul>";
	_strTable += "<ul class=\"info\">";
	// _strTable+="<li class=\"title\"><h2>1、多选配置</h2>";
	// _strTable+="<ul class=\"list\">";
	// _strTable+="<li><p>父子关联关系：<br/>";
	// _strTable+="被勾选时：<input type=\"checkbox\" id=\"py\" class=\"checkbox
	// first\" checked /><span>关联父</span>";
	// _strTable+="<input type=\"checkbox\" id=\"sy\" class=\"checkbox first\"
	// checked /><span>关联子</span><br/>";
	// _strTable+="取消勾选时：<input type=\"checkbox\" id=\"pn\" class=\"checkbox
	// first\" checked /><span>关联父</span>";
	// _strTable+="<input type=\"checkbox\" id=\"sn\" class=\"checkbox first\"
	// checked /><span>关联子</span><br/>";
	// _strTable+="<ul id=\"code\" class=\"log\"
	// style=\"height:20px;\"></ul></p>";
	// _strTable+="</li>";
	// _strTable+="</ul>";
	// _strTable+="</li>";
	_strTable += "<li class=\"title\"><h2>1、解绑关联</h2>";
	_strTable += "<ul class=\"list\">";
	if (autoCorrelation) {
		_strTable += "<li class=\"title\">&nbsp;&nbsp;"
				+ pmodname
				+ ": <input id=\""
				+ pmodname
				+ "_"
				+ pmodname
				+ "\" type=\"text\" readonly value=\"\" style=\"width:120px;\" onclick=\"showRelationMenu('tree_relation','"
				+ ruleTypeId + "','" + pmodname + "','" + pmodname + "',"
				+ autoCorrelation + ",true);\" />&nbsp;</li>";
	}
	for (var i = 0; i < nextNodes.length; i++) {
		_strTable += "<li class=\"title\">&nbsp;&nbsp;"
				+ nextNodes[i]
				+ ": <input id=\""
				+ pmodname
				+ "_"
				+ nextNodes[i]
				+ "\" type=\"text\" readonly value=\"\" style=\"width:120px;\" onclick=\"showRelationMenu('tree_relation','"
				+ ruleTypeId + "','" + pmodname + "','" + nextNodes[i]
				+ "',false,true);\" />&nbsp;</li>";
	}

	_strTable += "</ul>";
	_strTable += "</li>";
	_strTable += "</ul>";
	_strTable += "</div>";

	_strTable += "</div>";

	$(".content_wrap").append(_strTable);
	// onloadZTree("organ","organ","treeDemo",false);
}

function bind1(ptype) {
	var _ruleTypeId = getUrlParam('ruleTypeId');
	var pid = $("#pType").val();
	if (pid == null || pid == "") {
		alert("请勾选父栏目树！");
		return false;
	}

	var nTypeModule = $("#tableArea .hidden");
	var checkedCount = $("#tableArea .userMods").find(
			"input[name='cbitem']:checked");
	if (checkedCount.length == 0) {
		alert("请勾选数据！");
		return false;
	}
	var info = "";
	for (var i = 0; i < nTypeModule.length; i++) {
		var id = nTypeModule[i].id;
		var ntype = id.substring(0, id.indexOf("_"));
		var nid = nTypeModule[i].value;
		// var nid = $.getid("#"+ntype);
		if (ptype != "" && ntype != "" && pid != null && nid != ""
				&& _ruleTypeId != "") {
			$.ajax({
				url : getBasePath() + '/api/relation/bind',
				data : {
					pType : ptype,
					nType : ntype,
					pId : pid,
					nId : nid,
					ruleTypeId : _ruleTypeId,
					removed : 0
				},
				type : 'POST',
				async : false,
				error : function() {
					alert('系统连接失败，请稍后再试！');
				},
				success : function(result) {
					if (result.info.success) {
						info = "绑定成功";
					}
				}
			});
		}
	}
	if (info != "") {
		alert(info);
	}
}

function dropPrev(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode()
					&& curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
function dropInner(treeId, nodes, targetNode) {
	if (targetNode && targetNode.dropInner === false) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			if (!targetNode && curDragNodes[i].dropRoot === false) {
				return false;
			} else if (curDragNodes[i].parentTId
					&& curDragNodes[i].getParentNode() !== targetNode
					&& curDragNodes[i].getParentNode().childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
function dropNext(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode()
					&& curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}

var log, className = "dark", curDragNodes, autoExpandNode;
function beforeDrag(treeId, treeNodes) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: "
			+ treeNodes.length + " nodes.");
	for (var i = 0, l = treeNodes.length; i < l; i++) {
		if (treeNodes[i].drag === false) {
			curDragNodes = null;
			return false;
		} else if (treeNodes[i].parentTId
				&& treeNodes[i].getParentNode().childDrag === false) {
			curDragNodes = null;
			return false;
		}
	}
	curDragNodes = treeNodes;
	return true;
}
function beforeDragOpen(treeId, treeNode) {
	autoExpandNode = treeNode;
	return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime()
			+ " beforeDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
	showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "
			+ (isCopy == null ? "cancel" : isCopy ? "copy" : "move"));

	var _pId = treeNodes[0].pId;
	var _nId = treeNodes[0].id;
	var _ruleTypeId = getUrlParam('ruleTypeId');
	var _obj = getUrlParam('pobjType');
	if (_obj != "" && _pId != "" && _pId != null && _nId != ""
			&& _ruleTypeId != "") {
		$.ajax({
			url : getBasePath() + '/api/relation/unbind',
			data : {
				pType : _obj,
				nType : _obj,
				pId : _pId,
				nId : _nId,
				ruleTypeId : _ruleTypeId,
				removed : 0
			},
			type : 'POST',
			async : false,
			error : function() {
				alert('系统连接失败，请稍后再试！');
			},
			success : function(result) {
				// if(result.info.success){
				// alert("解绑成功");
				// }
			}
		});
	}
	return true;
}
function onDrag(event, treeId, treeNodes) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " onDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: "
			+ treeNodes.length + " nodes.");
}
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " onDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:"
			+ moveType);
	showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is "
			+ (isCopy == null ? "cancel" : isCopy ? "copy" : "move"))
	var _pId = treeNodes[0].pId;
	var _nId = treeNodes[0].id;
	var _ruleTypeId = getUrlParam('ruleTypeId');
	var _obj = getUrlParam('pobjType');
	if (_obj != "" && _pId != "" && _pId != null && _nId != ""
			&& _ruleTypeId != "") {
		$.ajax({
			url : getBasePath() + '/api/relation/bind',
			data : {
				pType : _obj,
				nType : _obj,
				pId : _pId,
				nId : _nId,
				ruleTypeId : _ruleTypeId,
				removed : 0
			},
			type : 'POST',
			async : false,
			error : function() {
				alert('系统连接失败，请稍后再试！');
			},
			success : function(result) {
				if (result.info.success) {
					alert("绑定成功");
				}
			}
		});
	}
}
function onExpand(event, treeId, treeNode) {
	if (treeNode === autoExpandNode) {
		className = (className === "dark" ? "" : "dark");
		showLog("[ " + getTime() + " onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;"
				+ treeNode.name);
	}
}

function showLog(str) {
	if (!log)
		log = $("#log");
	log.append("<li class='" + className + "'>" + str + "</li>");
	if (log.children("li").length > 8) {
		log.get(0).removeChild(log.children("li")[0]);
	}
}
function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
			.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}

function setTrigger() {
	var zTree = $.fn.zTree.getZTreeObj(tree);
	zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr(
			"checked");
}

function showRemoveBtn(treeId, treeNode) {
	// return !(treeNode.level == 0);
	return true;
}

function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.selectNode(treeNode);
	return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
}
function onRemove(e, treeId, treeNode) {
	showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);

	var _pType = treeNode.ptype;
	var _pId = treeNode.realId;
	var _ruleTypeId = treeNode.ruleTypeId;

	var pids = $.getRelationId(_pType, _pId, false, _ruleTypeId);
	var nids = $.getRelationId(_pType, _pId, true, _ruleTypeId);
	var ids = (pids == "") ? nids : ((nids == "") ? pids : pids + "," + nids);
	if ($.dele(_pType, _pId, _ruleTypeId)) {
		if (ids != "") {
			$.dele("relation", ids, _ruleTypeId);
		}
		alert("删除成功！");
	}
	var queryParams = {};
	queryParams['ruleTypeId'] = _ruleTypeId; // map.put(key, value);
	var seleBtns = $("#" + _pType).find("[name='number']");
	// 重新获取表格-刷新
	$.postSearchForm(basePath + "/" + _pType, "#" + _pType, 1, seleBtns.val(),
			queryParams);

}

//function addEditDom(treeId, treeNode) {
//	var sObj = $("#" + treeNode.tId + "_span");
//	if (treeNode.editNameFlag || $("#editBtn_" + treeNode.tId).length > 0)
//		return;
//	var addStr = "<span class='button edit' id='editBtn__" + treeNode.tId
//			+ "' title='edit node' onfocus='this.blur();'></span>";
//	sObj.after(addStr);
//	var btn = $("#editBtn__" + treeNode.tId);
//	if (btn)
//		btn.bind("click", function() {
//			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//			zTree.addNodes(treeNode, {
//				id : (100 + newCount),
//				pId : treeNode.id,
//				name : "new node" + (newCount++)
//			});
//			return false;
//		});
//}

function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");

	if (treeNode.editNameFlag || $("#unbindBtn_" + treeNode.tId).length > 0
			|| $("#editBtn_" + treeNode.tId).length > 0 || $("#viewBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "";
	if (treeNode.level != 0) {
		addStr = "<span class='button unbind' id='unbindBtn_" + treeNode.tId
				+ "' title='unbind nodes' onfocus='this.blur();'></span>";
	}
	addStr += "<span class='button edit' id='editBtn_" + treeNode.tId
			+ "' title='edit nodes' onfocus='this.blur();'></span>";
	addStr += "<span class='button view' id='viewBtn_" + treeNode.tId
			+ "' title='view nodes' onfocus='this.blur();'></span>";
	sObj.after(addStr);

	var unbindBtn = $("#unbindBtn_" + treeNode.tId);
	if (unbindBtn)
		unbindBtn.bind("click", function() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			// zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id,
			// name:"new node" + (newCount++)});
			if (confirm("确认解绑-- " + treeNode.name + "--所有关联吗?")) {
				className = (className === "dark" ? "" : "dark");
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.selectNode(treeNode);

				// unbind
				showLog("[ " + getTime()
						+ " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
						+ treeNode.name);
				var pidInfos = treeNode.pId.split("_");

				var _pType = pidInfos[0];
				var _pId = pidInfos[1];
				var _nType = treeNode.ptype;
				var _nId = treeNode.realId;
				var _ruleTypeId = treeNode.ruleTypeId;
				$.ajax({
					url : getBasePath() + '/api/relation/unbind',
					data : {
						pType : _pType,
						nType : _nType,
						pId : _pId,
						nId : _nId,
						ruleTypeId : _ruleTypeId,
						removed : 0
					},
					type : 'POST',
					async : false,
					error : function() {
						alert('系统连接失败，请稍后再试！');
					},
					success : function(result) {
						if (result.info.success) {
							$("#" + treeNode.tId).hide();
							alert("解绑成功");
						}
					}
				});
			}
			return false;
		});

	var editBtn = $("#editBtn_" + treeNode.tId);
	if (editBtn)
		editBtn.bind("click", function() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			className = (className === "dark" ? "" : "dark");
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.selectNode(treeNode);

			// editBtn
			showLog("[ " + getTime() + " onEdit ]&nbsp;&nbsp;&nbsp;&nbsp; "
					+ treeNode.name);

			var reverseTree = treeNode.reverseTree;
			var id = treeNode.realId;
			if (reverseTree) {
				var ntype = treeNode.ntype;
				// getSingleInfo(ntype, ntype, id);
				$.postOperForm(basePath + '/', ntype, id, 'edit');
			} else {
				var ptype = treeNode.ptype;
				// getSingleInfo(ptype, ptype, id);
				$.postOperForm(basePath + '/', ptype, id, 'edit');
			}
			return false;
		});
	
	var viewBtn = $("#viewBtn_" + treeNode.tId);
	if (viewBtn)
		viewBtn.bind("click", function() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			className = (className === "dark" ? "" : "dark");
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.selectNode(treeNode);

			// viewBtn
			showLog("[ " + getTime() + " onView ]&nbsp;&nbsp;&nbsp;&nbsp; "
					+ treeNode.name);

			var reverseTree = treeNode.reverseTree;
			var id = treeNode.realId;
			if (reverseTree) {
				var ntype = treeNode.ntype;
				// getSingleInfo(ntype, ntype, id);
				$.postOperForm(basePath + '/', ntype, id, 'view');
			} else {
				var ptype = treeNode.ptype;
				// getSingleInfo(ptype, ptype, id);
				$.postOperForm(basePath + '/', ptype, id, 'view');
			}
			return false;
		});

};
function removeHoverDom(treeId, treeNode) {
	$("#unbindBtn_" + treeNode.tId).unbind().remove();
	$("#editBtn_" + treeNode.tId).unbind().remove();
	$("#viewBtn_" + treeNode.tId).unbind().remove();
};
function onloadZTree_async(treeId, ruleTypeId, ptype, ntype, autoCorrelation,
		isRelationTree) {
	// var ptype = $("#dynamicBody").attr("class");
	// alert(ptype);
	$.get(getBasePath() + "/api/relation", {
		ruleTypeId : 42,
		ptype : ptype
	}, function(responseData) {
		var tmpNodes = [];
		var tmpPtypeIds = [];
		var content = responseData.data;
		var tmpNtypeIds = ","
		if (responseData.info.success) {
			if (content != null && content.length > 0) {
				for (var i = 0; i < content.length; i++) {
					// 获取所有ntype
					tmpNtypeIds += content[i].ntype + "_" + content[i].nid
							+ ',';

					// 获取ptype,不包含与ntype重复的数据
					if (tmpPtypeIds.indexOf(content[i].ptype + "_"
							+ content[i].pid) < 0) {

						tmpPtypeIds.push(content[i].ptype + "_"
								+ content[i].pid);
					}
				}
				// 去除首尾“，”，生成父节点
				for ( var j in tmpPtypeIds) {
					if (tmpNtypeIds.indexOf(tmpPtypeIds[j]) < 0) {
						var ptype = tmpPtypeIds[j].substring(0, tmpPtypeIds[j]
								.indexOf("_"));
						var realId = tmpPtypeIds[j].substring(tmpPtypeIds[j]
								.indexOf("_") + 1);
						var pTypes = {
							// name : getObjName(ptype, tmpPtypeIds[i]
							// .substring(tmpPtypeIds[i]
							// .lastIndexOf("_") + 1)).name,
							realId : realId,
							id : tmpPtypeIds[j],
							pId : 0,
							// name : content[i].ptype + "_" + content[i].pid,
							name : "[" + result[ptype] + "] "
									+ getObjName(ptype, realId),
							ptype : ptype,
							ruleTypeId : ruleTypeId,
							isParent : true
						};
						tmpNodes.push(pTypes);
					}
				}

				var obj = getObj(ptype);
				for (var i = 0; i < obj.length; i++) {
					if ($.inArray(ptype + "_" + obj[i].id, tmpPtypeIds) < 0) {
						var n = {
							realId : obj[i].id,
							id : ptype + "_" + obj[i].id,
							name : "[" + result[ptype] + "] " + obj[i].name,
							pId : 0,
							ptype : ptype,
							ruleTypeId : ruleTypeId,
							reverseTree : false
						// isParent:true
						};
						tmpNodes.push(n);
					}
				}

				if (!isRelationTree) {
					$.fn.zTree.init($("#" + treeId), setting_async, tmpNodes);
				} else {
					$.fn.zTree.init($("#" + treeId), setting_relation_async,
							tmpNodes);
				}
			}
		}
		$("#expandAllBtn").bind("click", expandAll);
		$("#asyncAllBtn").bind("click", asyncAll);
		$("#resetBtn").bind("click", reset);
	});
}

function onloadZTree_reverse_async(treeId, ruleTypeId, ptype, ntype,
		autoCorrelation, isRelationTree) {
	// var ptype = $("#dynamicBody").attr("class");
	// alert(ptype);
	$.get(getBasePath() + "/api/relation", {
		ruleTypeId : 42,
		ntype : ntype
	}, function(responseData) {
		var tmpNodes = [];
		var ntypeIds = [];
		var content = responseData.data;
		var ptypeIds = ",";
		if (responseData.info.success) {
			if (content != null && content.length > 0) {
				for (var i = 0; i < content.length; i++) {
					// 获取所有ptype
					ptypeIds += content[i].ptype + "_" + content[i].pid + ',';

					// 获取ntype,不包含与ptype重复的数据
					if (ntypeIds.indexOf(content[i].ntype + "_"
							+ content[i].nid) < 0) {

						ntypeIds.push(content[i].ntype + "_" + content[i].nid);
					}
				}
				// 去除首尾“，”，生成父节点
				for ( var j in ntypeIds) {
					if (ptypeIds.indexOf(ntypeIds[j]) < 0) {
						var ntype = ntypeIds[j].substring(0, ntypeIds[j]
								.indexOf("_"));
						var realId = ntypeIds[j].substring(ntypeIds[j]
								.indexOf("_") + 1);
						var nTypes = {
							// name : getObjName(ptype, tmpPtypeIds[i]
							// .substring(tmpPtypeIds[i]
							// .lastIndexOf("_") + 1)).name,
							realId : realId,
							id : ntypeIds[j],
							pId : 0,
							// name : content[i].ptype + "_" + content[i].pid,
							name : "[" + result[ntype] + "] "
									+ getObjName(ntype, realId),
							ntype : ntype,
							ruleTypeId : ruleTypeId,
							reverseTree : true,
							isParent : true
						};
						tmpNodes.push(nTypes);
					}
				}

				// var obj = getObj(ntype);
				// for (var i = 0; i < obj.length; i++) {
				// if ($.inArray(ntype + "_" + obj[i].id, ntypeIds) < 0) {
				// var n = {
				// realId : obj[i].id,
				// id : ntype + "_" + obj[i].id,
				// name : "[" + ntype + "]" + obj[i].name,
				// pId : 0,
				// ntype : ntype,
				// ruleTypeId : ruleTypeId,
				// // isParent:true
				// };
				// tmpNodes.push(n);
				// }
				// }
				if (!isRelationTree) {
					$.fn.zTree.init($("#" + treeId), setting_async, tmpNodes);
				} else {
					$.fn.zTree.init($("#" + treeId),
							setting_relation_reverse_async, tmpNodes);
				}
			}
		}
		$("#expandAllBtn").bind("click", expandAll);
		$("#asyncAllBtn").bind("click", asyncAll);
		$("#resetBtn").bind("click", reset);
	});
}

function getObj(modelName) {
	var modelObj;
	$.ajax({
		cache : true, // 是否使用缓存
		async : false, // 是否异步
		type : 'get', // 请求方式,post
		dataType : "json", // 数据传输格式
		url : getBasePath() + "/" + modelName,
		error : function() {
			alert('亲，网络有点不给力呀！');
		},
		success : function(result) {
			modelObj = result.data;
		}
	});
	return modelObj;
}

// 根据规则类型和父节点获取规则
function getRule(ruleTypeId, pobjType) {
	var rule = "";
	$.ajax({
		type : 'GET',
		url : getBasePath() + '/api/ruleType/' + ruleTypeId
				+ '/rule/paths?pobjType=' + pobjType,
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

// 获取指定type、ptype条件下的规则记录
function getRuleObjs(ruleTypeId, pobjType) {
	$
			.ajax({
				url : getBasePath() + '/api/ruleType/' + ruleTypeId
						+ '/rule/objs',
				type : 'GET',
				dataType : 'json',
				async : false, // 同步
				data : {
					'pobjType' : pobjType,
				// 'ruleTypeId':ruleTypeId,
				},
				success : function(result) {
					if (result.info.success) {
						if (result.data != null && result.data.length > 0) {
							$(".v-menu ul").html("");
							var items = "";
							if (pobjType == "" || pobjType == null) {
								for (var i = 0; i < result.data.length; i++) {
									items += "<li class=\""
											+ result.data[i].type
											+ "\" value=\""
											+ result.data[i].id
											+ "\" draggable=\"true\" ><a href=\"javascript:void(0);\">"
											+ result.data[i].name + "</a></li>";
								}
							} else {
								for (var i = 0; i < result.data.length; i++) {
									if (i == 0) {
										items += "<li class=\""
												+ pobjType
												+ "\" value=\""
												+ getPobjTypeInfo(pobjType)[0].id
												+ "\" draggable=\"true\" ><a href=\"javascript:void(0);\">"
												+ getPobjTypeInfo(pobjType)[0].name
												+ "</a></li>";
									}
									items += "<li class=\""
											+ result.data[i].type
											+ "\" value=\""
											+ result.data[i].id
											+ "\" draggable=\"true\"><a href=\"javascript:void(0);\">"
											+ result.data[i].name + "</a></li>";
								}
							}
							$(".v-menu ul").html(items);
						}
					}
				}
			});
}

function getPobjTypeInfo(pobjType) {
	var result = null;
	$.ajax({
		type : 'GET',
		url : getBasePath() + '/api/objInfo',
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

// 得到系统所有的对象信息
var result = {};
function getObjInfo() {
	$.ajax({
		type : 'GET',
		url : getBasePath() + '/api/objInfo',
		data : {
			removed : 0
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
					for (var i = 0; i < obj.data.length; i++) {
						result[obj.data[i].type] = obj.data[i].name;
					}
				}
			}
		}
	});
	return result;
}
// 获取树形部分
function getTree(ruleTypeId, pmodname, rule) {
	var autoCorrelation = false;
	var tmp = "";
	var nodes = [];
	for ( var a in rule) {
		var ruleTmp = rule[a];
		var nextRule = ruleTmp.substring(ruleTmp.indexOf(">") + 1);
		// 判断自增长
		if (nextRule.indexOf(pmodname) >= 0) {
			nextRule = nextRule.substring(nextRule.indexOf(">") + 1);
			autoCorrelation = true;
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
	var _strTable = "<div id=\"dynamicBody\" class='" + pmodname + "'>";
	onloadZTree_async('treeDemo', ruleTypeId, pmodname, pmodname,
			autoCorrelation, true);
	onloadZTree_reverse_async('treeReverseDemo', ruleTypeId, pmodname,
			pmodname, autoCorrelation, true);
	// if(autoCorrelation){//onloadZTree_async(treeId, ruleTypeId, ptype, ntype,
	// autoCorrelation,isRelationTree);
	// _strTable+= "<input type=\"button\" value=\"加载树\"
	// onclick=\"onloadZTree_async('treeDemo','"+ruleTypeId+"','"+pmodname+"','"+pmodname+"',"+autoCorrelation+",true);\"></input>";
	// _strTable+= "<input type=\"button\" value=\"加载反向树\"
	// onclick=\"onloadZTree_reverse_async('treeReverseDemo','"+ruleTypeId+"','"+pmodname+"','"+pmodname+"',"+autoCorrelation+",true);\"></input>";
	// }else{
	// _strTable+= "<input type=\"button\" value=\"加载树\"
	// onclick=\"onloadZTree_async('treeDemo','"+ruleTypeId+"','"+pmodname+"','"+pmodname+"',"+autoCorrelation+",true);\"></input>";
	// _strTable+= "<input type=\"button\" value=\"加载反向树\"
	// onclick=\"onloadZTree_reverse_async('treeReverseDemo','"+ruleTypeId+"','"+pmodname+"','"+pmodname+"',"+autoCorrelation+",true);\"></input>";
	// }
	_strTable += "<div  class=\"content_wrap\" id=\""
			+ pmodname
			+ "Div\"><div class=\"zTreeDemoBackground\"><ul id=\"treeDemo\" class=\"ztree\"></ul></div>";
	_strTable += "<div class=\"zTreeDemoBackground\"><ul id=\"treeReverseDemo\" class=\"ztree\"></ul></div></div>";

	_strTable += "</div>";

	// _strTable += "<div id=\"menuContent\" class=\"menuContent\"
	// style=\"display:none; position: absolute;\">";
	// _strTable += "<ul id=\"tree_obj\" class=\"ztree\" style=\"margin-top:0;
	// width:220px; height: 300px;\"></ul>";
	// _strTable += "</div>";
	_strTable += "</div>";

	$("#veast").append(_strTable);
	// $("#veast").draggable();
	$("#dynamicBody").draggable({
		axis : "y",
		containment : "parent"
	});
}

// 表格通用部分
function getTableInfo(modname) {
	var _strTable = "<div class=\"userMods\" id=\""
			+ modname
			+ "\"><div class=\"userMod\"><div id=\""
			+ modname
			+ "Dialog\" style=\"display:none\"><form id=\""
			+ modname
			+ "form\"></form></div><div><ul style=\"display:none\" class=\"s_menu\"><li class=\"delmod\">删除</li></ul><h1 class=\"fl\"></h1>";
	_strTable += "<div class=\"modName\" style=\"display:none\">" + modname
			+ "</div>";
	// _strTable +="<a class=\"size fr\">展开</a>";
	// _strTable+="<span class=\"fr\"><input class=\"searchs\" type=\"text\"
	// name=\"s3\" placeholder=\"Search....\"
	// onClick=\"this.focus();this.select();\" /></span> ";
	// _strTable+="<span class=\"rdi fr\"> <input type=\"radio\"
	// id=\"rdoTable"+modname+"\" name=\"rdoRole\" checked=\"checked\">";
	// _strTable+="<label for=\"rdoTable"+modname+"\"
	// class=\"rdoTable\"></label>";
	// _strTable+="<input type=\"radio\" id=\"rdoThree"+modname+"\"
	// name=\"rdoRole\"><label for=\"rdoThree"+modname+"\" class=\"rdoThree
	// iconShow\">";
	// _strTable+="</label></span>";
	// _strTable += "<a class=\"fr\"><img src=\"css/icon/search-icon.png\"
	// class=\"isShow\" title=\"显示/隐藏字段\" name=\"s3\" /></a>";
	_strTable += "<a class=\"fr\"><img src=\"css/icon/search-icon.png\" class=\"isShow\" title=\"显示/隐藏字段\" name=\"s3\" />&nbsp;&nbsp;&nbsp;"
			+ "<img src=\"images/icons2/document_16.png\" class=\"adds\" title=\"新增\" name=\"s3\" />&nbsp;"
			+ "<img src=\"css/icon/search-icon.png\" class=\"searchs\" title=\"搜索\" name=\"s3\"/></a></div>";
	// "<div class=\"clearfix\"></div>";
	_strTable += "<table class=\"commtable\" id=\"alternatecolor\" ><thead>";
	_strTable += "<tr style=\"background-color:#e4e2d7;\"> <th style=\"width:5%\"><input type=\"checkbox\" value=\"checkbox1\" name=\"checkAll\"></th>";
	_strTable += "<th class=\"tableItem\">noitem</th><th class=\"tableItem\"></th></tr></thead><tbody></tbody><tfoot>";
	_strTable += "<tr style=\"background-color:#e4e2d7\";><th colspan=\"2\" class=\"firth\" style=\"border:none\"><button class=\"btnIcon btnPlus\"> ";
	_strTable += "</button><button class=\"btnIconb btnMin\"></button></th><th class=\"nexth\" style=\"border:none\" class=\"table-bottom\">";
	_strTable += "<span class=\"fr mr5\"><select name=\"number\" id=\"number\"><option selected=\"selected\">5</option><option>10</option>";
	_strTable += "<option>15</option></select></span><button class=\"btn-just-Icon-b btnTolast fr\"></button>";
	_strTable += "<button class=\"btn-just-Icon-b btnNext fr\"></button> <span class=\"fr pt5\"><div class=\"slider\"></div></span>";
	_strTable += "<button class=\"btn-just-Icon-b btnPrev fr\"></button><button class=\"btn-just-Icon-b btnTotop fr\"></button>";
	_strTable += "<span class=\"fr pt5 pageNow\">1/2000(1000items)</span></th></tr></tfoot></table></div></div>";

	_strTable += "<div id=\"" + modname
			+ "OperDialog\" style=\"display:none\"><form id=\"" + modname
			+ "operForm\"></form></div>";
	_strTable += "<div id=\""
			+ modname
			+ "FilterDialog\" style=\"display:none\"><form id=\""
			+ modname
			+ "FilterForm\"><div>\"字段名称:\"<input name=\"filterCol\" id=\"filterCol\" type=\"text\"/> </div></form></div>";

	_strTable += "<input type=\"text\" id=\"" + modname
			+ "_Hidden\" class=\"hidden\">";
	$("#tableArea").append(_strTable);

	$("#" + modname + " .rdi").buttonset();
	$("#" + modname + " .rdoThree").removeClass("ui-widget").removeClass(
			"ui-state-default");

	$("#" + modname + " .rdoTable").click(
			function() {
				$(this).addClass("ui-widget").addClass("ui-state-default");
				$(this).next().next().removeClass("ui-widget").removeClass(
						"ui-state-default").addClass("iconShow");
			});

	$("#" + modname + " .rdoThree").click(
			function() {
				$(this).addClass("ui-widget").addClass("ui-state-default");
				$(this).prev().prev().removeClass("ui-widget").removeClass(
						"ui-state-default").addClass("iconShow");
			});

	$(".userMods").css("width", "100%");
	$(".size").text("展开");

	if ($("#veast>div").length % 2 != 0) {
		$("#" + modname).css("width", "100%");
		$("#" + modname + " .size").text("收缩");
	}

	$("#" + modname + " .size").click(function() {
		var _thismod = $(this).parents(".userMods").attr("id");
		if ($(this).text() == "展开") {
			$("#" + _thismod).css("width", "100%");
			$(this).text("收缩");
		} else {
			$("#" + _thismod).css("width", "50%");
			$(this).text("展开");
		}
		$(".v-menu").css("height", $(document).height());
	});

	var queryParams = {}; // Map map = new HashMap();
	queryParams['ruleTypeId'] = getUrlParam('ruleTypeId'); // map.put(key,
	// value);
	$.getTable(getBasePath() + "/" + modname, "#" + modname, queryParams);

	$("#veast").sortable({
		stop : function(event, ui) {
			$(".v-menu").css("height", $(document).height());
		}
	}).disableSelection();

	$.getSearchForm(getBasePath() + "/", modname);

	$("#" + modname + " .searchs").click(
			function() {
				$("#" + modname + "Dialog").dialog(
						{
							width : 270,
							title : "查询",
							modal : true,
							buttons : {
								"查询" : function() {
									var seleBtns = $("#" + modname).find(
											"[name='number']");
									$.postSearchForm(
											"${pageContext.request.contextPath}/"
													+ modname, "#" + modname,
											1, seleBtns.val(), queryParams);
									$(this).dialog("close");
								},
								"取消" : function() {
									$(this).dialog("close");
								}
							}
						});
			});
	var allTitle = $("#" + modname + " thead th .tableItem");
	$.each(allTitle, function(key, value) {
		var url = "&" + key + "=" + value;
		alert(url);
	});

	$("#" + modname + " h1").click(function() {
		$("#" + modname + " .s_menu").css("display", "block");
	});
	$("#" + modname + " .s_menu").mouseleave(function() {
		$("#" + modname + " .s_menu").css("display", "none");
	});

	$("#" + modname + " .delmod").click(function() {
		$("#" + modname).remove();
	});

}

// 获取单个列表信息
function getSingleInfo(modname, modCnName, id) {
	$("#detailInfo").empty();
	var _strTable = "<div class=\"userMods fl\" id=\"" + modname
			+ "Info\"><div class=\"userMod\"><div id=\"" + modname
			+ "Dialog\" style=\"display:none\"><form id=\"" + modname
			+ "form\"></form></div>";
	// _strTable += "<div><ul style=\"display:none\" class=\"s_menu\"><li
	// class=\"delmod\">删除</li></ul><h1 class=\"fl\">"+modCnName+"</h1>";
	// _strTable += "<div class=\"modName\" style=\"display:none\">" +
	// modname+"</div>"
	// _strTable +="<a class=\"size fr\">展开</a>";
	// _strTable+="<span class=\"fr\"><input class=\"searchs\" type=\"text\"
	// name=\"s3\" placeholder=\"Search....\"
	// onClick=\"this.focus();this.select();\" /></span> ";
	// _strTable+="<span class=\"rdi fr\"> <input type=\"radio\"
	// id=\"rdoTable"+modname+"\" name=\"rdoRole\" checked=\"checked\">";
	// _strTable+="<label for=\"rdoTable"+modname+"\"
	// class=\"rdoTable\"></label>";
	// _strTable+="<input type=\"radio\" id=\"rdoThree"+modname+"\"
	// name=\"rdoRole\"><label for=\"rdoThree"+modname+"\" class=\"rdoThree
	// iconShow\">";
	// _strTable+="</label></span>";

	_strTable += "</div><div class=\"clearfix\"></div>";
	_strTable += "<table class=\"commtable\" id=\"alternatecolor\" ><thead>";
	_strTable += "<tr style=\"background-color:#e4e2d7;\">";
	// _strTable+="<th style=\"width:4%\"><input type=\"checkbox\"
	// value=\"checkbox1\" name=\"checkAll\"></th>";
	_strTable += "<th class=\"tableItem\"></th><th class=\"tableItem\"></th></tr></thead><tbody></tbody>";

	// _strTable+="<tfoot><tr style=\"background-color:#e4e2d7\";><th
	// colspan=\"2\" class=\"firth\" style=\"border:none\"><button
	// class=\"btnIcon btnPlus\"> ";
	// _strTable+="</button><button class=\"btnIconb btnMin\"></button></th><th
	// class=\"nexth\" style=\"border:none\" class=\"table-bottom\">";
	// _strTable+="<span class=\"fr mr5\"><select name=\"number\"
	// id=\"number\"><option
	// selected=\"selected\">5</option><option>10</option>";
	// _strTable+="<option>15</option></select></span><button
	// class=\"btn-just-Icon-b btnTolast fr\"></button>";
	// _strTable+="<button class=\"btn-just-Icon-b btnNext fr\"></button> <span
	// class=\"fr pt5\"><div class=\"slider\"></div></span>";
	// _strTable+="<button class=\"btn-just-Icon-b btnPrev fr\"></button><button
	// class=\"btn-just-Icon-b btnTotop fr\"></button>";
	// _strTable+="<span class=\"fr pt5
	// pageNow\">1/2000(1000items)</span></th></tr></tfoot>";

	_strTable += "</table></div></div>";
	$("#detailInfo").append(_strTable);

	$("#" + modname + "Info .rdi").buttonset();
	$("#" + modname + "Info .rdoThree").removeClass("ui-widget").removeClass(
			"ui-state-default");

	$("#" + modname + "Info .rdoTable").click(
			function() {
				$(this).addClass("ui-widget").addClass("ui-state-default");
				$(this).next().next().removeClass("ui-widget").removeClass(
						"ui-state-default").addClass("iconShow");
			});

	$("#" + modname + "Info .rdoThree").click(
			function() {
				$(this).addClass("ui-widget").addClass("ui-state-default");
				$(this).prev().prev().removeClass("ui-widget").removeClass(
						"ui-state-default").addClass("iconShow");
			});

	$(".userMods").css("width", "100%");
	$(".size").text("展开");

	if ($("#veast>div").length % 2 != 0) {
		$("#" + modname + "Info").css("width", "100%");
		$("#" + modname + "Info .size").text("收缩");
	}

	$("#" + modname + "Info .size").click(function() {

		var _thismod = $(this).parents(".userMods").attr("id");

		if ($(this).text() == "展开") {
			$("#" + _thismod).css("width", "100%");
			$(this).text("收缩");
		} else {
			$("#" + _thismod).css("width", "50%");
			$(this).text("展开");
		}

		$(".v-menu").css("height", $(document).height());

	});

	var queryParams = {}; // Map map = new HashMap();
	queryParams['ruleTypeId'] = getUrlParam('ruleTypeId'); // map.put(key,
	// value);
	queryParams['id'] = id; // map.put(key, value);

	// $.getSingleTable("${pageContext.request.contextPath}/"+modname,"#"+modname+"Info",queryParams);
	$.postSingleSearchForm(getBasePath() + "/" + modname, "#" + modname
			+ "Info", 1, 5, queryParams);
	$("#veast").sortable({
		stop : function(event, ui) {
			$(".v-menu").css("height", $(document).height());
		}
	}).disableSelection();

	$("#" + modname + "Info h1").click(function() {
		$("#" + modname + " .s_menu").css("display", "block");
	});
	$("#" + modname + "Info .s_menu").mouseleave(function() {
		$("#" + modname + "Info .s_menu").css("display", "none");
	});

	$("#" + modname + "Info .delmod").click(function() {
		$("#" + modname + "Info").remove();
	});
}

// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值2
}
