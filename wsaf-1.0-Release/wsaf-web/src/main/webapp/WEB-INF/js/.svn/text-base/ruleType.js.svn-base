/**
 * js获取系统的根路径
 * @returns
 */
function getBasePath(){
	var obj=window.location;
	var contextPath=obj.pathname.split("/")[1];
	var basePath=obj.protocol+"//"+obj.host+"/"+contextPath;
	return basePath;
}; 
var basePath = getBasePath();
//判断id获取类型信息
function getRuleType(id){
	var ruleType = "";
	$.ajax({
		type: 'GET',
		url:  getBasePath()+'/api/ruleType',
		data:{
			'id':id,
			'removed':'0'
		},
		dataType:'json',
		async:false,
		error:function(){alert('系统连接失败，请稍后再试！');},
		success: function(result){
			if(result.info.success==true){
				ruleType = result.data[0];
			}
		}	 
	});
	return ruleType;
}
//判断id获取类型信息
function getAllRuleType(){
	var ruleType = "";
	$.ajax({
		type: 'GET',
		url:  getBasePath()+'/api/ruleType',
		data:{
			'removed':'0'
		},
		dataType:'json',
		async:false,
		error:function(){alert('系统连接失败，请稍后再试！');},
		success: function(result){
			if(result.info.success==true){
				ruleType = result.data;
			}
		}	 
	});
	return ruleType;
}

function getEdit(e){
	$tr = $(e.target).closest("tr");
	$tds = $tr.find("td");
	$model = $tr.parents(".userMods")[0].id;
	
	var $a = $tr.find("a.multiSelect");
	var $id = $($tds[0]).find("input").val();
	
	var ruleType = getRuleType($id);
    var relationObj = ruleType.objIds;
//        initObjectInfoSelectWithChecked(relationObj);
 	   $.ajax({
		   type: 'GET',
		   url: getBasePath()+'/api/objInfo',
		   dataType:'json',
		   async:false,
		   error:function(){alert('系统连接失败，请稍后再试！');},
		   success: function(result){
			   if(result.info.success){
				   var $input = $(td).find("input");
				   for(var i=0;i<result.data.length;i++){
					   if((','+relationObj+',').indexOf(','+result.data[i].id+',')>-1){
						   $($input).append("<option value= '"+result.data[i].id+"' selected=\"selected\">"+result.data[i].name+"</option>");	
					   }else{
						   $($input).append("<option value= '"+result.data[i].id+"'>"+result.data[i].name+"</option>");	
					   }
//					$("#relationObj").append("<option value= '{id:"+result.data[i].id+",entity:"+result.data[i].type+"}'>"+result.data[i].name+"</option>");	
				   }
				   $($input).multiSelect({
					   selectAllText: '全选',  
					   noneSelected: '请选择',
//					oneOrMoreSelected: '% options checked',
					   oneOrMoreSelected: '*',
				   });
				   
				   var $a = $(td).find("a");
				   $($a).css("height","21px");
				   
				   return true;
			   }
		   }	 
	   });
	return false; //返回false表示失败，dom元素不会有变化 
}

function postData(e){
	$tr = $(e.target).closest("tr");
	$tds = $tr.find("td");
	$model = $tr.parents(".userMods")[0].id;
	
	var $a = $tr.find("a.multiSelect");
   
	var $id = $($tds[0]).find("input").val();
 	var name=$($tds[2]).find("input").val();
 	var objIds=$a.selectedValuesString();
	var objTypes = $a.find("span").text();  
	
	var postParams = {};
	postParams['id'] = $id; 
	postParams['name'] = name; 
	postParams['objIds'] = objIds; 
	postParams['objTypes'] = objTypes; 
	postParams['removed'] = 0; 
	
	return saveRuleType(postParams);
//	var isExists=isExistsRuleType(name);
//    if(!isExists){
//    	return saveRuleType(postParams);
//	}
//	return false; //返回false表示失败，dom元素不会有变化 
}
//判断id获取类型信息
function saveRuleType(postParams){
	var data="";
	if(postParams !=null && postParams != ""){
		$.each(postParams, function(key, value) {
			data += key + "=" + value + "&";
		});
		data.substring(0,data.lastIndexOf("&"));
	}
	var isSuccess = false;
	 $.ajax({
	        type: 'POST',
			url: getBasePath()+'/api/ruleType',
			data : data,
			dataType : 'json',
			async : false,
			error : function() {
				alert('系统连接失败，请稍后再试！');
			},
			success : function(result) {
				if (result.info.success == true) {
					 // Map map = new HashMap();
					var queryParams = {};
					queryParams['tableType'] = "ruleType"; // map.put(key, value);
					//	 				queryParams[id] = getUrlParam('id'); // map.put(key, value);
					$.getTable(getBasePath()+'/api/ruleType', "#ruleType", queryParams);
					alert("修改成功！");
					isSuccess = true;
				}
			}
		});
	 return isSuccess;
}

//判断修改的类型名称是否已存在
function isExistsRuleType(name){
	var isExists = false;
	if(name!="" && name!=null){
		$.ajax({
			type: 'GET',
			url:  getBasePath()+'/api/ruleType',
			data:{
				'name':name,
				'removed':'0'
			},
			dataType:'json',
			async:false,
			error:function(){alert('系统连接失败，请稍后再试！');},
			success: function(result){
				if(result.data.length!=0){
					isExists = true;
					alert("名称已存在！");
				}
			}	 
		});
	}
	return isExists;
}

   //初始化上级联、下级联对象下拉框
   function getMultiSelect(selectObj){
       $.ajax({
        type: 'GET',
		url: getBasePath()+'/api/objInfo',
		dataType:'json',
		async:false,
		error:function(){alert('系统连接失败，请稍后再试！');},
		success: function(result){
			if(result.info.success){
				for(var i=0;i<result.data.length;i++){
					$(selectObj).append("<option value= '"+result.data[i].id+"'>"+result.data[i].name+"</option>");	
//					$("#relationObj").append("<option value= '{id:"+result.data[i].id+",entity:"+result.data[i].type+"}'>"+result.data[i].name+"</option>");	
				}
				$(selectObj).multiSelect({
					selectAllText: '全选',  
					noneSelected: '请选择',
//					oneOrMoreSelected: '% options checked',
					oneOrMoreSelected: '*',
				});
//				$("#relationObj").css("height","21px");
			}
		}	 
       });
   }
   
   //初始化上级联、下级联对象下拉框（针对类型编辑）
   function initObjectInfoSelectWithChecked(relationObj){
	   $.ajax({
		   type: 'GET',
		   url: getBasePath()+'/api/objInfo',
		   dataType:'json',
		   async:false,
		   error:function(){alert('系统连接失败，请稍后再试！');},
		   success: function(result){
			   if(result.info.success){
				   for(var i=0;i<result.data.length;i++){
					   if((','+relationObj+',').indexOf(','+result.data[i].id+',')>-1){
						   $("#relationObj").append("<option value= '"+result.data[i].id+"' selected=\"selected\">"+result.data[i].name+"</option>");	
					   }else{
						   $("#relationObj").append("<option value= '"+result.data[i].id+"'>"+result.data[i].name+"</option>");	
					   }
//					$("#relationObj").append("<option value= '{id:"+result.data[i].id+",entity:"+result.data[i].type+"}'>"+result.data[i].name+"</option>");	
				   }
				   $("#relationObj").multiSelect({
					   selectAllText: '全选',  
					   noneSelected: '请选择',
//					oneOrMoreSelected: '% options checked',
					   oneOrMoreSelected: '*',
				   });
			   }
		   }	 
	   });
   }
 
   //加载历史的配置规则到列表显示
   function listHistoryRelationRuleType(page){
      $("#relationRuleHistoryData tbody").html("");
      var pageSize=$("#pageSizeSelectorHistory").val();
      if(pageSize==undefined){
    	  pageSize=20;
      }
      var sort=$("#sort").val();
      $.ajax({
		        type: 'GET',
		        url: getBasePath()+'/api/ruleType?page=1,10&sort='+sort,
//				data:{
//					   'pageSize':pageSize,
//					   'pageNum':page,
//						'page':'1,10',
//					   'sort':sort,
//				     },
				dataType:'json',
				async:false,
				error:function(){alert('系统连接失败，请稍后再试！');},
				success: function(result){
					if(result.info.success==true){
					   if(result.data.content!=null && result.data.content.length>0){
					      for(var i=0;i<result.data.content.length;i++){
					        var dataContent=result.data.content[i]; 
					        var trItem="<tr>";  
					        trItem+="<td><input type=\"checkBox\" name=\"id\" value=\""+dataContent.id+"\"/></td>";
					        trItem+="<td>"+(i+1)+"</td>";
					        trItem+="<td>"+dataContent.name+"</td>";
					        trItem+="<td>"+dataContent.objTypes+"</td>";
//					        trItem+="<td>"+dataContent.relationObj+"</td>";
					        trItem+="<td><a href=\"http://localhost:8080/wsaf-web/jsp/ruleType/edit?id="+dataContent.id+"\" target=\"_blank\" title=\"编辑\"><img src=\"images/icons2/pencil_16.png\"/></a>&nbsp;&nbsp;" +
					        		"<a href=\"javascript:void(0);\" onclick=\"javascript:del('"+dataContent.id+"');\" title=\"删除\"><img src=\"images/icons2/delete_16.png\"/></a>&nbsp;&nbsp;" +
					        		"<a href=\"http://localhost:8080/wsaf-web/jsp/rule/list/"+dataContent.id+"\" target=\"_blank\" title=\"配置规则\">配置规则</a></td>";
					        trItem+="</tr>";
					        $("#relationRuleHistoryData tbody").append(trItem);
					      }
					   }
					   setPage(result.data.pageInfo);
					}
				}	 
	  });
   }
  
   //全选
   function allSelect(e){
	   if(e.value=="全选"){
		   $(":checkbox").attr("checked",true);
		   e.value="全不选";
	   }else{
		   $(":checkbox").attr("checked",false);
		   e.value="全选";
	   }
   }
   
   //删除所有被选中的选项
   function delAllSelect(){
	   if($("#allSelect").val()=="全不选"){
		   $("#allSelect").val("全选");
	   }
	   var checkboxs=$("input:checked");
	   for(var i=0;i<checkboxs.length;i++){
		   $.ajax({
			   url:getBasePath()+'/api/ruleType/del/'+$(checkboxs[i]).val(),
			   type:'post',
			   dataType:'json',
			   async:false,
			   error:function(){alert('系统连接失败，请稍后再试！');},
			   success:function(data){
				   if(data.success==false){
					   alert('系统异常，删除失败！');
				   }
			   }
		   });
	   }
	   listHistoryRelationRule(1);
   }
   
   
   //删除指定id的数据
   function del(id){
	   var pageStr=$(".current").text();
	   if(pageStr.substring(0,3)=="上一页"){
		   pageStr=pageStr.substring(3,4);
	   }else{
		   pageStr=pageStr.substring(0,1);
	   }
	   $.ajax({
		   url:getBasePath()+'/api/ruleType/del/'+id,
		   type:'post',
		   dataType:'json',
		   async:false,
		   error:function(){alert('系统连接失败，请稍后再试！')},
		   success:function(data){
			   if(data.success==false){
				   alert('系统异常，删除失败！');
			   }
		   }
	   });
	   listHistoryRelationRuleType(pageStr);
   }
   
   //转换显示信息为文本消息
//   function change(content){
//	   var name="";
//	   $.ajax({
//		   url:getBasePath()+'/objInfo/api/findObjInfosByIds',
//		   type:'post',
//		   data:{'ids':content},
//		   dataType:'json',
//		   async:false,
//		   error:function(){alert('系统连接失败，请稍后再试！');},
//		   success:function(data){
//			   if(data.success){
//				   for(var i=0;i<result.data.length;i++){
//					   name += result.data[i].name + ",";
//					}
//			   }
//		   }
//	   });
//	   name = name.substring(0,name.lastIndexOf(","));
//	   return name;
//   }
   
   //转换查询数据为value
   function changeToValue(content){
	   var options=$("#PType option");
	   for(var i=0;i<options.length;i++){
		   if(content==$(options[i]).text()){
			   content=$(options[i]).val();
			   break;
		   }
	   }
	   return content;
   }
   
   
   //设置分页
   function setPage(pageInfo){
      $("#paginationHistory").html("");
      var pageContent="";
      pageContent+="<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit(1)\">&laquo; 第一页</a>";
      if(pageInfo.hasPrevious==true){
         pageContent+="<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit("+(pageInfo.currentPage-1)+")\" class=\"number current\">上一页</a>";
      }
      for(var i=1;i<=pageInfo.totalPages;i++){
         if(i==pageInfo.currentPage){
            pageContent+="<a href=\"javascript:void(0)\" class=\"number current\">"+i+"</a>";
         }else{
            pageContent+="<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit("+i+")\" class=\"number\">"+i+"</a>";
         }
      }
      if(pageInfo.hasNext==true){
          pageContent+="<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit("+(pageInfo.currentPage+1)+")\" class=\"number current\">下一页</a>";
      }
      pageContent+="<a href=\"javascript:void(0)\" onclick=\"javascript:onSubmit("+pageInfo.totalPages+")\" title=\"Last Page\">最后一页 &raquo;</a>";
	  pageContent+="<span> 共"+pageInfo.totalPages+"页/"+pageInfo.totalRecord+"条 </span>";
	  pageContent+="<span> 每页</span>";
	  pageContent+="<select id=\"pageSizeSelectorHistory\" onchange=\"javascript:onSubmit(1)\">";
	  if(pageInfo.pageSize==1){
	    pageContent+="<option value=\"1\" selected=\"selected\">1</option>";
	  }else{
	    pageContent+="<option value=\"1\">1</option>";
	  }
	  if(pageInfo.pageSize==5){
	    pageContent+="<option value=\"5\" selected=\"selected\">5</option>";
	  }else{
	    pageContent+="<option value=\"5\">5</option>";
	  }
	  if(pageInfo.pageSize==10){
	    pageContent+="<option value=\"10\" selected=\"selected\">10</option>";
	  }else{
	    pageContent+="<option value=\"10\">10</option>";
	  }
	  if(pageInfo.pageSize==20){
	    pageContent+="<option value=\"20\" selected=\"selected\">20</option>";
	  }else{
	    pageContent+="<option value=\"20\">20</option>";
	  }
	  pageContent+="</select><span>条</span>";
	  $("#paginationHistory").html(pageContent);
   }
   
   //分页查看
   function onSubmit(page){  
	    listHistoryRelationRule(page);  
   }
   
   
   //显示与隐藏查询界面
   function showQuery(){
	   if($("#query").css("display")=="none"){ 
		   $("#query").css("display","block");
	   }else{
		   $("#query").css("display","none");
	   }
   }
   
   //查询
   function retrievalList(){
	   listHistoryRelationRule(1);
   }
   
	//表格通用部分
	function getTableInfo(modname) {
		var _strTable = "<div class=\"userMods\" id=\""+modname+"\"><div class=\"userMod\"><div id=\""+modname+"Dialog\" style=\"display:none\"><form id=\""+modname+"form\"></form></div><div><ul style=\"display:none\" class=\"s_menu\"><li class=\"delmod\">删除</li></ul><h1 class=\"fl\"></h1>";
		_strTable += "<div class=\"modName\" style=\"display:none\">" + modname+"</div>";
//		_strTable +="<a class=\"size fr\">展开</a>";

//		_strTable += "<span class=\"fr\"><input class=\"searchs\" type=\"text\" name=\"s3\" placeholder=\"Search....\" onClick=\"this.focus();this.select();\" /></span> ";
//		_strTable += "<a class=\"fr\"><img src=\"images/filter.png\" class=\"isShow\" title=\"显示/隐藏字段\" name=\"s3\" />&nbsp;&nbsp;&nbsp;";
		_strTable += "<a class=\"fr\"><img src=\"images/icons2/document_16.png\" class=\"adds\" title=\"新增\" name=\"s3\" /></a>";
		_strTable += "<a class=\"fr\"><img src=\"css/icon/search-icon.png\" class=\"searchs\" title=\"搜索\" name=\"s3\"/></a> </div>";

//		_strTable += "<div class=\"clearfix\	"></div>";
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

		_strTable += "<div id=\""+modname+"OperDialog\" style=\"display:none\"><form id=\""+modname+"operForm\"></form></div>";
		_strTable += "<input type=\"text\" id=\""+modname+"_Hidden\" class=\"hidden\">";
		
//		_strTable += "<div id=\""+modname+"AddDialog\" style=\"display:none\"><form id=\""+modname+"operForm\"></form></div>";
//		<div id="addDialog" >
//		<form id="addForm">
//			<div>类型名称：<input type="text" name="name" id="name" /></div>
//			<div>关联对象 ：<input type="text" name="relationObj" id="relationObj" /></div>
//		</form>
//		</div>
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
//		
//		if(modname == 'ruleType'){
//			var ruleTypeIds = $("#ruleTypeIds").val();
//			ruleTypeIds = ruleTypeIds.substring(1);
//			ruleTypeIds = ruleTypeIds.substring(0,ruleTypeIds.length-1).split(",");
//			var resPaths = $("#resPaths").val();
//			if(resPaths.indexOf("/**")>=0){
//				queryParams = {};
//			}else{
//				queryParams["id_in"]=ruleTypeIds;
//			}
//		}
		$.getTable(basePath+"/api/" + modname, "#"
				+ modname, queryParams);

		$("#veast").sortable({
			stop : function(event, ui) {
				$(".v-menu").css("height", $(document).height());
			}
		}).disableSelection();

		$.getSearchForm(basePath+"/api/", modname);
		$("#" + modname + " .searchs").click(function() {
			$("#" + modname + "Dialog").dialog({
						width : 270,
						title : "查询",
						modal : true,
						buttons : {
							"查询" : function() {
								var seleBtns = $("#" + modname).find("[name='number']");
								$.postSearchForm(basePath+"/api/"+ modname, "#"+ modname, 1, seleBtns.val(), queryParams);
								$(this).dialog("close");
							},
							"取消" : function() {
								$(this).dialog("close");
							}
						}
					});
				});
		$.getOperForm(basePath+"/api/",modname,'','add');
//		$("#" + modname + "OperDialog").css("display", "block");
//		getMultiSelect("#" + modname + "OperDialog input[name='objTypes']");
		$("#" + modname + " .adds").click(function() {
			$("#" + modname + "OperDialog").dialog({
						width : 350,
						height:250,
						title : "规则类型新增",
						modal : true,
						buttons : {
							"新增" : function() {
						         var name=$("#" + modname + "OperDialog input[name='name']").val();
						         var objIds=$("#" + modname + "OperDialog .multiSelect").selectedValuesString();
						         var objTypes = $("#" + modname + "OperDialog .multiSelect span").text();
						         var isExists=isExistsRuleType(name);
						         if(isExists == false && objTypes == "请选择"){
						        	 alert("请选择关联对象");
						         }else if(!isExists){
							         $.ajax({
								        type: 'POST',
										url: basePath+'/api/ruleType',
										data : {
											'name' : name,
											'objIds' : objIds,
											'objTypes' : objTypes,
											'removed' : '0'
										},
										dataType : 'json',
										async : false,
										error : function() {
											alert('系统连接失败，请稍后再试！');
										},
										success : function(result) {
											if (result.info.success == true) {
												$.getTable(basePath+"/api/ruleType", "#ruleType", {});
												alert("新增成功！");
											}
										}
									});
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
//		$("#" + modname + " .tableItem").click(function(){
//			alert(1); 
//		 })
	}
	
// 获取url中的参数
   function getUrlParam(name) {
   	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
   	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
   	if (r != null)
   		return unescape(r[2]);
   	return null; // 返回参数值
   }
   