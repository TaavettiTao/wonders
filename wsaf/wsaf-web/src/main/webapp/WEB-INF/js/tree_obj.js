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

//分页树
var setting_page = {
	async : {
		enable : true,
		type : "get",
		url : getUrl,
		dataFilter : dataFilter
	},
	check : {
		enable : true
	},
	view : {
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	view : {
		addDiyDom : addDiyDom
	},
	callback : {
//		beforeClick: beforeClick,
		onCheck : onCheck,
		beforeExpand : beforeExpand,
		onAsyncSuccess : onAsyncSuccess,
		onAsyncError : onAsyncError
	}
};

// var nodes =[{name:"分页测试", t:"请点击分页按钮", id:"0",count:22 ,page:0, pageSize:10,
// isParent:true}];
var curPage = 0;

function dataFilter(treeId, parentNode, responseData) {
	var tmpNodes = [];
	var content = responseData.data.content;
	var totalPages = responseData.data.pageInfo.totalPages;
	if (responseData.info.success) {
		if (content != null && content.length > 0) {
			for (var i = 0; i < content.length; i++) {
				var n = {
					id : content[i].id,
					pId : 0,
					name : content[i].name,
					objName:parentNode.objName,
					isParent : false
				};
				tmpNodes.push(n);
			}
		}
	}
	return tmpNodes;
}

function getUrl(treeId, treeNode) {
	// var param = "id="+ treeNode.id +"_"+treeNode.page
	// +"&count="+treeNode.pageSize,
	aObj = $("#" + treeNode.tId + "_a");
	aObj.attr("title", "当前第 " + treeNode.page + " 页 / 共 " + treeNode.maxPage
			+ " 页");
	return "../wsaf-web/"+treeNode.objName+"?page=" + treeNode.page + "," + treeNode.pageSize;
}
function goPage(treeNode, page) {
	treeNode.page = page;
	if (treeNode.page < 1)
		treeNode.page = 1;
	if (treeNode.page > treeNode.maxPage)
		treeNode.page = treeNode.maxPage;
	if (curPage == treeNode.page)
		return;
	curPage = treeNode.page;
	var zTree = $.fn.zTree.getZTreeObj("tree_obj");
	zTree.reAsyncChildNodes(treeNode, "refresh");
}
function beforeExpand(treeId, treeNode) {
	if (treeNode.page == 0)
		treeNode.page = 1;
	return !treeNode.isAjaxing;
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
		errorThrown) {
	var zTree = $.fn.zTree.getZTreeObj("tree_obj");
	alert("异步获取数据出现异常。");
	treeNode.icon = "";
	zTree.updateNode(treeNode);
}
function addDiyDom(treeId, treeNode) {
	if (treeNode.level > 0)
		return;
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#addBtn_" + treeNode.id).length > 0)
		return;
	var addStr = "<span class='button lastPage' id='lastBtn_"
			+ treeNode.id
			+ "' title='last page' onfocus='this.blur();'></span><span class='button nextPage' id='nextBtn_"
			+ treeNode.id
			+ "' title='next page' onfocus='this.blur();'></span><span class='button prevPage' id='prevBtn_"
			+ treeNode.id
			+ "' title='prev page' onfocus='this.blur();'></span><span class='button firstPage' id='firstBtn_"
			+ treeNode.id
			+ "' title='first page' onfocus='this.blur();'></span>";
	aObj.after(addStr);
	var first = $("#firstBtn_" + treeNode.id);
	var prev = $("#prevBtn_" + treeNode.id);
	var next = $("#nextBtn_" + treeNode.id);
	var last = $("#lastBtn_" + treeNode.id);
	treeNode.maxPage = Math.round(treeNode.count / treeNode.pageSize - .5)
			+ (treeNode.count % treeNode.pageSize == 0 ? 0 : 1);
	first.bind("click", function() {
		if (!treeNode.isAjaxing) {
			goPage(treeNode, 1);
		}
	});
	last.bind("click", function() {
		if (!treeNode.isAjaxing) {
			goPage(treeNode, treeNode.maxPage);
		}
	});
	prev.bind("click", function() {
		if (!treeNode.isAjaxing) {
			goPage(treeNode, treeNode.page - 1);
		}
	});
	next.bind("click", function() {
		if (!treeNode.isAjaxing) {
			goPage(treeNode, treeNode.page + 1);
		}
	});
};

function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("tree_obj");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onloadZTree_obj(objName) {
	$.ajax({
		async : false, // 是否异步
		cache : true, // 是否使用缓存
		type : 'get', // 请求方式,post
		dataType : "json", // 数据传输格式
		url : getBasePath()+'/'+objName+'?count',
		error : function() {
			alert('亲，网络有点不给力呀！');
		},
		success : function(result) {
			ztreeNodes = result.data; // 将string类型转换成json对象
			if (result.info.success) {
				var nodes1 = [ {
					name : objName,
					t : "请点击分页按钮",
					objName:objName,
					id : "0",
					count : result.data,
					page : 0,
					pageSize : 20,
					isParent : true
				} ];
				$.fn.zTree.init($("#tree_obj"), setting_page, nodes1);
			}
		}
	});
}
function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("tree_obj"), nodes = zTree
			.getCheckedNodes(true), v = "";
	for (var i = 1, l = nodes.length; i < l; i++) {
		v += nodes[i].id + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	var cityObj = $("#"+treeNode.objName);
	cityObj.attr("value", v);
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; // 返回参数值
}