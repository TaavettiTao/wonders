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
// 判断新增的数据是否已存在
function isExistsRule(ruleTypeId) {
	var isExists = false;
	var pobjId = $("#pobjType").val();
	var nobjId = $("#nobjType").val();
	$.ajax({
		type : 'GET',
		url : getBasePath() + '/api/rule',
		data : {
			'pobjId' : pobjId,
			'nobjId' : nobjId,
			'ruleTypeId' : ruleTypeId,
			'removed' : '0'
		},
		dataType : 'json',
		async : false,
		error : function() {
			alert('系统连接失败，请稍后再试！')
		},
		success : function(result) {
			if (result.data.length != 0) {
				isExists = true;
				alert("该规则rule已存在！");
			}
		}
	});
	return isExists;
}

// 根据规则名查找相关实体id
// function getRelationObjIds(){
// var $name = $("#type").val();
// var objIds;
// $.ajax({
// type: 'GET',
// async: false,
// url: getBasePath()+'/ruleType',
// data:{
// 'name':$name,
// 'removed':0
// },
// dataType:'json',
// error:function(){alert('系统连接失败，请稍后再试！');},
// success: function(result){
// if(result.data.length!=0){
// ids = result.data.objIds;
// }
// }
// });
// return objIds;
// }

// 初始化上级联、下级联对象下拉框,按规则类型查找
function initRelationObjInfoSelect(ruleTypeId) {
	$.ajax({
		type : 'GET',
		async : false,
		url : getBasePath() + '/api/ruleType',
		data : {
			// 'id':'${ruleTypeId}',
			'id' : ruleTypeId,
			'removed' : 0
		},
		dataType : 'json',
		error : function() {
			alert('系统连接失败，请稍后再试！');
		},
		success : function(result) {
			if (result.data.length != 0) {
				var objIds = result.data[0].objIds.split(",");
				var objTypes = result.data[0].objTypes.split(",");

				$("#pobjType").append(
						"<option value=''select='selected'>请选择</option>");
				$("#nobjType").append(
						"<option value=''select='selected'>请选择</option>");
				for (var i = 0; i < objIds.length; i++) {
					// $("#pobjType").append("<option
					// value='"+obj.result[i].id+"_"+obj.result[i].type+"'>"+obj.result[i].name+"</option>");
					$("#pobjType").append(
							"<option value='" + objIds[i] + "'>" + objTypes[i]
									+ "</option>");
					$("#nobjType").append(
							"<option value='" + objIds[i] + "'>" + objTypes[i]
									+ "</option>");
				}
			}
		}
	});
}

// 初始化上级联、下级联对象下拉框
function initObjectInfoSelect() {
	$.ajax({
		type : 'POST',
		url : getBasePath() + '/objInfo/api/all',
		dataType : 'json',
		async : false,
		error : function() {
			alert('系统连接失败，请稍后再试！')
		},
		success : function(obj) {
			if (obj.success) {
				$("#pobjType").append(
						"<option value=''select='selected'>请选择</option>");
				$("#nobjType").append(
						"<option value=''select='selected'>请选择</option>");
				for (var i = 0; i < obj.result.length; i++) {
					$("#pobjType").append(
							"<option value='" + obj.result[i].type + "'>"
									+ obj.result[i].name + "</option>");
					$("#nobjType").append(
							"<option value='" + obj.result[i].type + "'>"
									+ obj.result[i].name + "</option>");
				}
			}
		}
	});
}

// 加载历史的配置规则到列表显示
function listHistoryRelationRule(page) {
	$("#relationRuleHistoryData tbody").html("");
	var pageSize = $("#pageSizeSelectorHistory").val();
	if (pageSize == undefined) {
		pageSize = 20;
	}
	var ruleTypeId = $("#ruleTypeId").val();
	var sort = $("#sort").val();
	var pobjType = changeToValue($("#pType").val());
	var nobjType = changeToValue($("#nType").val());
	$.ajax({
				type : 'GET',
				url : getBasePath() + '/api/rule',
				data : {
					// 'pageSize':pageSize,
					// 'pageNum':page,
					'page' : page + ',' + pageSize,
					'ruleTypeId' : ruleTypeId,
					'sort' : sort,
				},
				dataType : 'json',
				async : false,
				error : function() {
					alert('系统连接失败，请稍后再试！');
				},
				success : function(result) {
					if (result.info.success == true) {
						if (result.data.content != null
								&& result.data.content.length > 0) {
							for (var i = 0; i < result.data.content.length; i++) {
								var dataContent = result.data.content[i];
								var trItem = "<tr>";
								trItem += "<td><input type=\"checkBox\" name=\"id\" value=\""
										+ dataContent.id + "\"/></td>";
								trItem += "<td>" + (i + 1) + "</td>";
								trItem += "<td>" + change(dataContent.pobjType)
										+ "</td>";
								trItem += "<td>" + change(dataContent.nobjType)
										+ "</td>";
								trItem += "<td>" + dataContent.ruleTypeId
										+ "</td>";
								trItem += "<td><a href=\"javascript:void(0);\" onclick=\"javascript:del('"
										+ dataContent.id
										+ "');\" title=\"删除\"><img src=\"images/icons2/delete_16.png\"/></a></td>";
								trItem += "</tr>";
								$("#relationRuleHistoryData tbody").append(
										trItem);
							}
						}
						setPage(result.data.pageInfo);
					}
				}
			});
}

// 全选
function allSelect(e) {
	if (e.value == "全选") {
		$(":checkbox").attr("checked", true);
		e.value = "全不选";
	} else {
		$(":checkbox").attr("checked", false);
		e.value = "全选";
	}
}

// 删除所有被选中的选项
function delAllSelect() {
	if ($("#allSelect").val() == "全不选") {
		$("#allSelect").val("全选");
	}
	var checkboxs = $("input:checked");
	for (var i = 0; i < checkboxs.length; i++) {
		$.ajax({
			url : getBasePath() + '/relationRule/api/del/'
					+ $(checkboxs[i]).val(),
			type : 'post',
			dataType : 'json',
			async : false,
			error : function() {
				alert('系统连接失败，请稍后再试！')
			},
			success : function(data) {
				if (data.success == false) {
					alert('系统异常，删除失败！');
				}
			}
		});
	}
	listHistoryRelationRule(1);
}

// 删除指定id的数据
function del(id) {
	var pageStr = $(".current").text();
	if (pageStr.substring(0, 3) == "上一页") {
		pageStr = pageStr.substring(3, 4);
	} else {
		pageStr = pageStr.substring(0, 1);
	}
	$.ajax({
		url : getBasePath() + '/rule/' + id,
		type : 'DELETE',
		dataType : 'json',
		async : false,
		error : function() {
			alert('系统连接失败，请稍后再试！')
		},
		success : function(result) {
			if (result.info.success == false) {
				alert('系统异常，删除失败！');
			} else {
				alert('删除成功！');
			}
		}
	});
	listHistoryRelationRule(pageStr);
}

// 转换显示信息为文本消息
function change(content) {
	var options = $("#pobjType option");
	for (var i = 0; i < options.length; i++) {
		if ($(options[i]).val() == content) {
			content = $(options[i]).text();
			break;
		}
	}
	return content;
}

// 转换查询数据为value
function changeToValue(content) {
	var options = $("#pobjType option");
	for (var i = 0; i < options.length; i++) {
		if (content == $(options[i]).text()) {
			content = $(options[i]).val();
			break;
		}
	}
	return content;
}

// 根据id 获取 info
function changeToEn(id) {
	var value = "";
	$.ajax({
		type : 'GET',
		url : getBasePath() + '/api/objInfo',
		data : {
			'id' : id,
			'removed' : '0'
		},
		dataType : 'json',
		async : false,
		error : function() {
			alert('系统连接失败，请稍后再试！');
		},
		success : function(result) {
			if (result.data.length != 0) {
				value = result.data[0].type;
			}
		}
	});
	return value;
}

// 设置分页
function setPage(pageInfo) {
	$("#paginationHistory").html("");
	var pageContent = "";
	pageContent += "<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit(1)\">&laquo; 第一页</a>";
	if (pageInfo.hasPrevious == true) {
		pageContent += "<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit("
				+ (pageInfo.currentPage - 1)
				+ ")\" class=\"number current\">上一页</a>";
	}
	for (var i = 1; i <= pageInfo.totalPages; i++) {
		if (i == pageInfo.currentPage) {
			pageContent += "<a href=\"javascript:void(0)\" class=\"number current\">"
					+ i + "</a>";
		} else {
			pageContent += "<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit("
					+ i + ")\" class=\"number\">" + i + "</a>";
		}
	}
	if (pageInfo.hasNext == true) {
		pageContent += "<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit("
				+ (pageInfo.currentPage + 1)
				+ ")\" class=\"number current\">下一页</a>";
	}
	pageContent += "<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit("
			+ pageInfo.totalPages + ")\" title=\"Last Page\">最后一页 &raquo;</a>";
	pageContent += "<span> 共" + pageInfo.totalPages + "页/"
			+ pageInfo.totalRecord + "条 </span>";
	pageContent += "<span> 每页</span>";
	pageContent += "<select id=\"pageSizeSelectorHistory\" onchange=\"javascript:onSubmit(1)\">";
	if (pageInfo.pageSize == 1) {
		pageContent += "<option value=\"1\" selected=\"selected\">1</option>";
	} else {
		pageContent += "<option value=\"1\">1</option>";
	}
	if (pageInfo.pageSize == 5) {
		pageContent += "<option value=\"5\" selected=\"selected\">5</option>";
	} else {
		pageContent += "<option value=\"5\">5</option>";
	}
	if (pageInfo.pageSize == 10) {
		pageContent += "<option value=\"10\" selected=\"selected\">10</option>";
	} else {
		pageContent += "<option value=\"10\">10</option>";
	}
	if (pageInfo.pageSize == 20) {
		pageContent += "<option value=\"20\" selected=\"selected\">20</option>";
	} else {
		pageContent += "<option value=\"20\">20</option>";
	}
	pageContent += "</select><span>条</span>";
	$("#paginationHistory").html(pageContent);
}

// 分页查看
function onSubmit(page) {
	listHistoryRelationRule(page);
}

// 显示与隐藏查询界面
function showQuery() {
	if ($("#query").css("display") == "none") {
		$("#query").css("display", "block");
	} else {
		$("#query").css("display", "none");
	}
}

// 查询
function retrievalList() {
	listHistoryRelationRule(1);
}

function generRuleTable(modname,ruleTypeId){
	$("#"+modname).remove();
	getRuleTableInfo(modname,ruleTypeId);
}

// 表格通用部分
function getRuleTableInfo(modname,ruleTypeId) {
	var _strTable = "<div class=\"userMods\" id=\""+ modname+ "\"><div class=\"userMod\"><div id=\""+ modname+ "Dialog\" style=\"display:none\"><form id=\""+ modname
			+ "form\"></form></div><div><ul style=\"display:none\" class=\"s_menu\"><li class=\"delmod\">删除</li></ul><h1 class=\"fl\"></h1>";
	_strTable += "<div class=\"modName\" style=\"display:none\">" + modname+"</div>";
//	_strTable +="<a class=\"size fr\">展开</a>";
//	_strTable += "<span class=\"fr\"><input class=\"searchs\" type=\"text\" name=\"s3\" placeholder=\"Search....\" onClick=\"this.focus();this.select();\" /></span> ";
	// _strTable+="<span class=\"rdi fr\"> <input type=\"radio\"
	// id=\"rdoTable"+modname+"\" name=\"rdoRole\" checked=\"checked\">";
	// _strTable+="<label for=\"rdoTable"+modname+"\"
	// class=\"rdoTable\"></label>";
	// _strTable+="<input type=\"radio\" id=\"rdoThree"+modname+"\"
	// name=\"rdoRole\"><label for=\"rdoThree"+modname+"\" class=\"rdoThree
	// iconShow\">";
	// _strTable+="</label></span>";
	_strTable += "<a class=\"fr\"><img src=\"images/icons2/document_16.png\" class=\"adds\" title=\"新增\" name=\"s3\" /></a>";
	_strTable += "<a class=\"fr\"><img src=\"css/icon/search-icon.png\" class=\"searchs\" title=\"搜索\" name=\"s3\"/></a> </div>";
//	_strTable += "<div class=\"clearfix\"></div>";
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
	queryParams['ruleTypeId'] = ruleTypeId; // map.put(key,
															// value);

	$.getTable(basePath + "/api/" + modname, "#" + modname, queryParams);

	$("#veast").sortable({
		stop : function(event, ui) {
			$(".v-menu").css("height", $(document).height());
		}
	}).disableSelection();

	$.getSearchForm(basePath + "/api/", modname);

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
									$.postSearchForm(basePath + "/api/"
											+ modname, "#" + modname, 1,
											seleBtns.val(), queryParams);
									$(this).dialog("close");
								},
								"取消" : function() {
									$(this).dialog("close");
								}
							}
						});
			});
	getAddForm(basePath+"/api/",modname,ruleTypeId);
	$("#" + modname + " .adds").click(function() {
		$("#" + modname + "OperDialog").dialog({
					width : 270,
					height:250,
					title : "新增",
					modal : true,
					buttons : {
						"新增" : function() {
							 var pobjId=$("#pobjType").val();
					         var nobjId=$("#nobjType").val();
					         var pobjType = changeToEn(pobjId);
					         var nobjType = changeToEn(nobjId);
//				 	         var _date = $("#relationRule").serialize();
					         var warnNotice="";
					         if(pobjId==""){
					            warnNotice+="请选择上级联对象！\n";
					         }
					         if(nobjId==""){
					            warnNotice+="请选择下级联对象！\n";
					         }
					         if(warnNotice!=""){
					           alert(warnNotice);
					           return false;
					         }else{
					           var isExists=isExistsRule(ruleTypeId);
					           if(!isExists){
					                $.ajax({
							        type: 'POST',
									url: basePath+'/api/rule',
									data:{'pobjId':pobjId,'nobjId':nobjId,'pobjType':pobjType,'nobjType':nobjType,'ruleTypeId':ruleTypeId},
									dataType:'json',
									error:function(){alert('系统连接失败，请稍后再试！');},
									success: function(result){
										if(result.info.success==true){
											var queryParams = {}; // Map map = new HashMap();
											queryParams['ruleTypeId'] = ruleTypeId;
											$.getTable(basePath+"/api/rule", "#"+modname, queryParams);
										   alert("新增成功！");
										}
									}	 
							      });
					           }
					         } 
							$(this).dialog("close");
						},
						"取消" : function() {
							$(this).dialog("close");
						}
					}
				});
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

//获取新增界面
function getAddForm(firUrl,objname,ruleTypeId){
	if(firUrl!=""&&objname!=""){
		$.ajax({
			type : 'GET',
//			async : false,
			url : firUrl + 'ruleType',
			data : {'id' : ruleTypeId,'removed' : 0},
			dataType : 'json',
			error : function() {
				alert('系统连接失败，请稍后再试！');
			},
			success : function(result) {
				if (result.data.length != 0) {
					$("#"+objname+"operForm").empty();
					var objIds = result.data[0].objIds.split(",");
					var objTypes = result.data[0].objTypes.split(",");
					var sp = "<div>上级联对象:<select name='pobjType' id='pobjType'>";
					var sn = "<div>下级联对象:<select name='nobjType' id='nobjType'>";
					var selectBody = "";
					selectBody += "<option value=''select='selected'>请选择</option>";
					for (var i = 0; i < objIds.length; i++) {
						selectBody += "<option value='" + objIds[i] + "'>" + objTypes[i]+ "</option>";
					}
					selectBody+="</select></div>";
					
					sp += selectBody;
					sn += selectBody;
					
					$("#"+objname+"operForm").append(sp).append(sn);
					
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
