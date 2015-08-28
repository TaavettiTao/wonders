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

//不是自关联，且不是关联树
var setting_common  = {
	check : {
		enable : true
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onCheck : onCheck,
	}
};
//是自关联
var setting_auto_correlation = {
	edit : {
		drag : {
			autoExpandTrigger : true,
			prev : dropPrev,
			inner : dropInner,
			next : dropNext
		},
		enable : true,
		showRemoveBtn : false,
		showRenameBtn : false
	},
	check : {
		enable : true
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onCheck : onCheck,
		beforeDrag : beforeDrag,
		beforeDrop : beforeDrop,
		beforeDragOpen : beforeDragOpen,
		onDrag : onDrag,
		onDrop : onDrop,
		onExpand : onExpand,
	}
};
//是关联树
var setting_relation = {
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
		enable : true
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		// onCheck : onCheck_relation,
		beforeDrag : beforeDrag,
		beforeDrop : beforeDrop,
		beforeDragOpen : beforeDragOpen,
		onDrag : onDrag,
		onDrop : onDrop,
		onExpand : onExpand,
		beforeRemove : beforeRemove,
		onRemove : onRemove
	}
};

var code;

function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj(tree), py = $("#py").attr("checked") ? "p"
			: "", sy = $("#sy").attr("checked") ? "s" : "", pn = $("#pn").attr(
			"checked") ? "p" : "", sn = $("#sn").attr("checked") ? "s" : "", type = {
		"Y" : py + sy,
		"N" : pn + sn
	};
	zTree.setting.check.chkboxType = type;
	showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "'
			+ type.N + '" };');
}
function showCode(str) {
	if (!code)
		code = $("#code");
	code.empty();
	code.append("<li>" + str + "</li>");
}

// 加载ztree

// var zTree_Menu="";

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

function getObjName(obj, id) {
	var objName;
	$.ajax({
		async : false, // 是否异步
		cache : true, // 是否使用缓存
		type : 'get', // 请求方式,post
		dataType : "json", // 数据传输格式
		url : getBasePath() + "/" + obj,
		data : {
			id : id
		},
		error : function() {
			alert('亲，网络有点不给力呀！');
		},
		success : function(result) {
			if(result.data.length != 0){
				objName = result.data[0].name;
			}
		}
	});
	return objName;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId), nodes = zTree
			.getCheckedNodes(true), v = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		var pids= nodes[i].id;
		v += pids.substring(pids.indexOf("_")+1) + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	var cityObj = $("#pType");
	cityObj.attr("value", v);
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
	return !(treeNode.level==0);
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
}

function onloadZTree(treeId, ruleTypeId, ptype, ntype, autoCorrelation, isRelationTree) {
	var nodes = [];
	var tmpNtypeIds = ",", tmpPtypeIds = ",";
	
	// 若不是自关联，且不是关联树，则直接获取pType的所有数据
	if(isRelationTree==false && autoCorrelation==false){
		var obj = getObj(ptype);
		var nodes_pobjType = [];
		for (var i = 0; i < obj.length; i++) {
			if (tmpNtypeIds
					.indexOf("," + ptype + "_" + obj[i].id + ",") < 0) {
				var n = {
					id : ptype + "_" + obj[i].id,
					name : obj[i].name,
					pId : 0
				// isParent:true
				};
				nodes_pobjType.push(n);
			}
		}
		$.fn.zTree.init($("#" + treeId), setting_common, nodes_pobjType);
	}else{
		$.ajax({
			async : false, // 是否异步
			type : 'get', // 请求方式,post
			dataType : "json", // 数据传输格式
			url : getBasePath() + '/api/relation',
			data : {
				ptype : ptype,
				ntype : ntype,
				ruleTypeId:ruleTypeId
			},
			error : function() {
				alert('亲，网络有点不给力呀！');
			},
			success : function(result) {
				var nodes = [];
				var tmpNtypeIds = ",", tmpPtypeIds = ",";
				for (var i = 0; i < result.data.length; i++) {
					var nTypes = {
						id : result.data[i].ntype + "_" + result.data[i].nid,
						pId : result.data[i].ptype + "_" + result.data[i].pid,
						name : getObjName(ntype, result.data[i].nid)
					};
					nodes.push(nTypes);

					//获取所有ntype
					tmpNtypeIds += result.data[i].ntype + "_" + result.data[i].nid
							+ ',';

					//获取ptype,不包含与ntype重复的数据,这样ptype均为顶级节点
					if (tmpPtypeIds.indexOf(result.data[i].ptype + "_" +result.data[i].pid) < 0) {

						tmpPtypeIds += result.data[i].ptype + "_"
								+ result.data[i].pid + ',';
					}
				}

				// 如果是关联树，则只取relaition关系，不取没关联的数据，以供解绑使用
				if (isRelationTree) {
					// 去除首尾“，”，生成父节点
					tmpPtypeIds = tmpPtypeIds.substring(
							tmpPtypeIds.indexOf(",") + 1).substring(0,
							tmpPtypeIds.lastIndexOf(",") - 1);
					tmpPtypeIds = tmpPtypeIds.split(",");
					for ( var i in tmpPtypeIds) {
						if (tmpNtypeIds.indexOf(tmpPtypeIds[i]) < 0) {
							var pTypes = {
								id : tmpPtypeIds[i],
								name : getObjName(ptype,
										tmpPtypeIds[i].substring(tmpPtypeIds[i]
												.lastIndexOf("_") + 1)),
								pId : 0
							// isParent:true
							};
							nodes.push(pTypes);
						}
					}
					$.fn.zTree.init($("#" + treeId), setting_relation, nodes);
				} else{
					if (autoCorrelation) {// 不是关联树，但，是自关联,则要获取relation之外的数据
						var obj = getObj(ptype);
						for (var i = 0; i < obj.length; i++) {
							if (tmpNtypeIds
									.indexOf("," + ptype + "_" + obj[i].id + ",") < 0) {
								var n = {
										id : ptype + "_" + obj[i].id,
										name : obj[i].name,
										pId : 0
										// isParent:true
								};
								nodes.push(n);
							}
						}
						$.fn.zTree.init($("#" + treeId), setting_auto_correlation,nodes);
					}
				} 
			}
		});
	}
	
}

// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}
