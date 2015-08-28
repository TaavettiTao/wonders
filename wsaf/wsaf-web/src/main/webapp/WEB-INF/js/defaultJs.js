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
/*
json表格颜色
*/
$.setRow = function(){
  var rows = $(".commtable").find("tr");

  for ( i = 0; i < rows.length; i++) 
	{
	  if (i % 2 == 0) 
	 {
	   $(rows[i]).addClass("evenrowcolor").removeClass("oddrowcolor").removeClass("importcolor");
	 }
	 else 
	 {
	   $(rows[i]).addClass("oddrowcolor").removeClass("evenrowcolor").removeClass("importcolor");
	 }
   }
}

$.getid = function(modname){
	var _Id="";
	var count=$(modname).find("input[name='cbitem']:checked");
	for (var i = 0; i < count.length; i++) 
	{
		var _mid=$(count[i]).val();
		if(_mid!=""&&_mid!=null)
		{
			_Id=_Id+_mid+",";	
		}
	}
	_Id=_Id.substring(0,_Id.length-1);
	return _Id;
	};

//解析Table
	$.analyzeSingleTableData= function(result, status, xhr,modname,menuUrl,queryParams)
	{
		if (status == "success" && result != null)
		{
			
			$(modname + "  tr.tableItem").remove();
			
			if (result.info.success == true&&result.data.content &&JSON.stringify(result.data.content) != "[]") 
			{
				$(modname + " .tableItem").remove();
				var keycount = 1;
				var tableTitle = "<th class=\"tableItem\" style=\"width:8%\">序号</th>";
				
				$.ajax({
					cache : true, // 是否使用缓存
					async : false, // 是否异步
					type : 'get', // 请求方式,post
					dataType : "json", // 数据传输格式
					url : menuUrl,
					error : function() {
						alert('亲，网络有点不给力呀！');
					},
					success : function(dataContent) {
						
						$(modname+" h1").text(dataContent.data.cnName);
						$(modname+" .modName").text(dataContent.data.name);
						
						var fieldProperties = dataContent.data.fieldProperties;
						for(var a in fieldProperties){
							if(fieldProperties[a].operate != "hidden"){
								tableTitle += "<th class=\"tableItem\">"+ fieldProperties[a].name+ "</th>";
								keycount++;
							}
						}
//					tableTitle = "<th class=\"tableItem\">操作</th>";
						
						$.each(result.data.content,function(entryIndex,entry) 
								{
//							var tableItem = "<tr  class=\"tableItem\"><td><input type=\"checkbox\" name=\"cbitem\" value=\""+entry.id+"\"></td>";
							var tableItem = "<tr  class=\"tableItem\">";
							tableItem += "<td>"+(entryIndex+1)+ "</td>";
							for(var a in fieldProperties){
								if(fieldProperties[a].operate != "hidden"){
									tableItem += "<td>"+ entry[fieldProperties[a].path]+ "</td>";
								}
							}
//							var tmpModname = modname.substring(modname.indexOf("#")+1);
//							var tableTr = tableItem+"<td class=\"lvitem\"><div style=\"display:none\" >" +
//							"<a href=\"javascript:void(0);\" onclick=\"javascript:$.postOperForm('"+tmpModname+"','','add');\"  title=\"新增\"><img src=\"images/icons2/document_16.png\"/></a>&nbsp;&nbsp;" +
//							"<a href=\"javascript:void(0);\" onclick=\"javascript:$.postOperForm('"+tmpModname+"','"+entry.id+"','edit');\"  title=\"编辑\"><img src=\"images/icons2/pencil_16.png\"/></a>&nbsp;&nbsp;" +
//							"<a href=\"javascript:void(0);\" onclick=\"javascript:$.delAll('"+tmpModname+"','"+entry.id+"');\" title=\"删除\"><img src=\"images/icons2/delete_16.png\"/></a>&nbsp;&nbsp;";
							$(modname+" tbody").append(tableItem);
						});
					}
				});
				
//				$(modname+" thead > tr").append(tableTitle+"<th class=\"tableItem\" style=\"width:100px;\"></th>");
				$(modname+" thead > tr").append(tableTitle);
				$.setRow();
				$(".v-menu").css("height", $(document).height());
				
			}
		}
	};
	
//解析Table
$.analyzeTableData= function(result, status, xhr,modname,menuUrl,queryParams){
	var tmpModname = modname.substring(modname.indexOf("#")+1);
	if (status == "success" && result != null)
	{

		$(modname + "  tr.tableItem").remove();
		
		if (result.info.success == true&&result.data.content &&JSON.stringify(result.data.content) != "[]") 
		{
			$(modname + " .tableItem").remove();
			var keycount = 1;
			var tableTitle = "<th class=\"tableItem\" style=\"width:8%\">序号</th>";
			var allTitle = {};
			$.ajax({
				cache : true, // 是否使用缓存
				async : false, // 是否异步
				type : 'get', // 请求方式,post
				dataType : "json", // 数据传输格式
				url : menuUrl,
				error : function() {
					alert('亲，网络有点不给力呀！');
				},
				success : function(dataContent) {
					
					$(modname+" h1").text(dataContent.data.cnName);
					$(modname+" .modName").text(dataContent.data.name);
					
					var fieldProperties = dataContent.data.fieldProperties;
					for(var a in fieldProperties){
						if(fieldProperties[a].operate != "hidden"){
							tableTitle += "<th class=\"tableItem\" name="+ fieldProperties[a].path+ " value="+ a + ">"+ fieldProperties[a].name+ "</th>";
//							if(a<5){
//								tableTitle += "<th class=\"tableItem\" name="+ fieldProperties[a].path+ " value="+ a + ">"+ fieldProperties[a].name+ "</th>";
//							}else{
//								tableTitle += "<th class=\"tableItem\"  style=\"display:none\" name="+ fieldProperties[a].path+ " value="+ a + ">"+ fieldProperties[a].name+ "</th>";
//							}
							allTitle[fieldProperties[a].path]=fieldProperties[a].name;
//							allTitle.push(fieldProperties[a].name);
							keycount++;
						}
					}
//					tableTitle = "<th class=\"tableItem\">操作</th>";
					
					$.each(result.data.content,function(entryIndex,entry) 
					{
						var tableItem = "<tr  class=\"tableItem\"><td><input type=\"checkbox\" name=\"cbitem\" value=\""+entry.id+"\"></td>";
						tableItem += "<td>"+(entryIndex+1)+ "</td>";
					   for(var a in fieldProperties){
							if(fieldProperties[a].operate != "hidden"){
								if(entry[fieldProperties[a].path] == null){
									tableItem += "<td>暂无</td>";
								}else{
									tableItem += "<td>"+ entry[fieldProperties[a].path]+ "</td>";
								}
							}
						}
					
//						$(modname+" tbody").append(tableItem+"<td class=\"lvitem\"><div style=\"display:none\" ><input type=\"button\" class=\"lv1\"  value=\"0\"><input type=\"button\" class=\"lv2\"  value=\"0\"><input type=\"button\" class=\"lv3\" value=\"0\"></div></td>");
						
						if(tmpModname == 'ruleType'){
							var tableTr = tableItem;
//							var tableTr = tableItem+"<td><a href=\"http://localhost:8080/wsaf-web/jsp/ruleType/edit?id="+entry.id+"\" target=\"_blank\" title=\"编辑\"><img src=\"images/icons2/pencil_16.png\"/></a>&nbsp;&nbsp;" +
//				        		"<a href=\"javascript:void(0);\" onclick=\"javascript:del('"+entry.id+"');\" title=\"删除\"><img src=\"images/icons2/delete_16.png\"/></a>&nbsp;&nbsp;" +
//				        		"<a href=\"http://localhost:8080/wsaf-web/jsp/rule/list/"+entry.id+"\" target=\"_blank\" title=\"配置规则\">配置规则</a></td>";
						}else if(tmpModname == 'rule'){
							var tableTr = tableItem+"<td class=\"lvitem\"><div style=\"display:none\" >" +
							"<a href=\"javascript:void(0);\" onclick=\"javascript:$.del('"+tmpModname+"','"+entry.id+"');\" title=\"删除\"><img src=\"images/icons2/delete_16.png\"/></a>&nbsp;&nbsp;";
						}else{
							var tableTr = tableItem+"<td class=\"lvitem\"><div style=\"display:none\" >" +
							"<a href=\"javascript:void(0);\" onclick=\"javascript:$.postOperForm('"+basePath+"/','"+tmpModname+"','','add');\"  title=\"新增\"><img src=\"images/icons2/document_16.png\"/></a>&nbsp;&nbsp;" +
							"<a href=\"javascript:void(0);\" onclick=\"javascript:$.postOperForm('"+basePath+"/','"+tmpModname+"','"+entry.id+"','edit');\"  title=\"编辑\"><img src=\"images/icons2/pencil_16.png\"/></a>&nbsp;&nbsp;" +
							"<a href=\"javascript:void(0);\" onclick=\"javascript:$.delAll('"+tmpModname+"','"+entry.id+"',"+getUrlParam('ruleTypeId')+");\" title=\"删除\"><img src=\"images/icons2/delete_16.png\"/></a>&nbsp;&nbsp;";
//							"<input type=\"button\" class=\"lv3\" value=\"0\"></div></td>";
						}
					   $(modname+" tbody").append(tableTr);
					});
//					$(modname).find('tr').find("td:eq(" + colnum.toString() + ")").hide(); 
				}
			});
			
			$(modname+" thead > tr").append(tableTitle+"<th class=\"tableItem\" style=\"width:12%;\"><img src=\"images/icons2/delete_16.png\"/>操作</th>");
//			$.menuChina(menuUrl,modname);
			$.setRow();
			
			var hiddenVal = $(modname+"_Hidden").val();
			var hiddenValTmp = ","+hiddenVal+",";
			if(hiddenVal != "" || hiddenVal != null){
				var inputModule = $(modname+" table>tbody input[name='cbitem']");
				for(var i in inputModule){
					if(hiddenValTmp.indexOf(","+inputModule[i].value+",")>=0){
						inputModule[i].checked=true;
					}
				}
			}
			$(modname+" table input[type='checkbox']").click(function(){
			
				var checkModule= $(modname).find("input[name='cbitem']:checked");
				
				var v = "";
				for (var i = 0, l = checkModule.length; i < l; i++) {
					var pids= checkModule[i].value;
					v += pids.substring(pids.indexOf("_")+1) + ",";
				}
				
				if(hiddenVal !=""){
					v = hiddenVal+","+v;
				}
				
				if (v.length > 0)
					v = v.substring(0, v.length - 1);
				v= $.unique(v.split(","));
				var obj = $(modname+"_Hidden");
				obj.attr("value", v);
			});
			
			$(".v-menu").css("height", $(document).height());
			
			var tableColspan = Math.floor(keycount / 3);
			if(tableColspan<=1)
				{
				tableColspan=2;
				}
			tableColspan=2;
			
			var hideField = queryParams["hideField"];
			var $th = $(modname +" thead th");
			if(hideField==undefined){
				//默认显示6列
				$(modname +" thead th:gt(5)").hide();
				$(modname).find('tr').find("td:gt(5)").hide();
				$(modname +" thead th:last").show();
				$(modname).find('tr').find("td:last").show();
			}else{

				$.each($th,function(key, value){
					if($(value).attr("name") != undefined){
						if($.inArray($(value).attr("name"),hideField)>=0){
							$(modname).find('tr').find("td:eq("+key+")").hide();
							$(this).hide();
						}
					}
				});
			}
			
			//遍历所有th,如果是隐藏字段，则隐藏
		
			var $thv = $(modname +" thead th:visible");
//			var otherColspan = keycount- tableColspan+2;
			var otherColspan = $thv.length-2;
			$(modname+" .isShow").click(function(){
//				$(modname+"operForm").empty();
				var $tr = $(modname +" thead th:hidden");
				var hideField =[];
				$.each($tr,function(key, value){
					hideField.push($(value).attr("name"));
				});
				$.getShowForm(tmpModname,allTitle,hideField);
				$(modname + "FilterDialog").dialog({
						width : 300,
						height:300,
						title : "过滤字段",
						modal : true,
						buttons : {
							"确定" : function() {
								var sv = $(modname + "FilterDialog .multiSelect").selectedValuesString();
								sv = sv.split(",");
								var $th = $(modname +" thead th");
								$.each($th,function(key, value){
									if($(value).attr("name")!=undefined){
										if($.inArray($(value).attr("name"),sv)<0){//显示
//											$(modname).find('tr').find("td").show();
											var cellIndex = $(this)[0].cellIndex;
											$(modname).find('tr').find("td:eq("+cellIndex+")").show();
											$(this).show();
										}else{
											var cellIndex = $(this)[0].cellIndex;
											$(modname).find('tr').find("td:eq("+cellIndex+")").hide();
											$(this).hide();
										};
									}
								});
								
//								$.each(sv,function(key, value){
//									$(modname).find('tr').find("th[name="+value+"]").hide(); 
//									var cellIndex = $(modname+" thead th[name="+value+"]")[0].cellIndex;
//									$(modname).find('tr').find("td:eq("+cellIndex+")").hide();
//									
//								});
								
//								$(modname +".userMods").css("width", "100%");
								var $thv = $(modname +" thead th:visible");
								$(modname + " .nexth").attr("colspan", $thv.length-2);
								$(this).dialog("close");
							},
							"取消" : function() {
								$(this).dialog("close");
							}
						}
					});
			});
			
			if (result.data.pageInfo != null) 
			{
				$(modname + " .firth").attr("colspan", tableColspan);
				$(modname + " .nexth").attr("colspan", otherColspan);
				$(modname + " .pageNow").text(result.data.pageInfo.currentPage+ "/"+ result.data.pageInfo.totalPages+ "("+ result.data.pageInfo.totalRecord+ " items)");

				if (result.data.pageInfo.hasNext == false) 
				{
					$(modname + " .btnNext").removeClass("btn-just-Icon").addClass("btn-just-Icon-b");
					$(modname + " .btnTolast").removeClass("btn-just-Icon").addClass("btn-just-Icon-b");
				}
				else
				{
					$(modname + " .btnNext").addClass("btn-just-Icon").removeClass("btn-just-Icon-b");
					$(modname + " .btnTolast").addClass("btn-just-Icon").removeClass("btn-just-Icon-b");
			
				}

				if (result.data.pageInfo.hasPrevious == false) 
				{
					$(modname + " .btnPrev").removeClass("btn-just-Icon").addClass("btn-just-Icon-b");
					$(modname + " .btnTotop").removeClass("btn-just-Icon").addClass("btn-just-Icon-b");
                }
				else
				{
					$(modname + " .btnPrev").addClass("btn-just-Icon").removeClass("btn-just-Icon-b");
					$(modname + " .btnTotop").addClass("btn-just-Icon").removeClass("btn-just-Icon-b");
					
				}
				$(modname + " .slider").slider('option', 'max',  result.data.pageInfo.totalPages);  
				
				$(modname +" table>tbody tr").click(function()
				   {
					    $(".lvitem").find("div").css("display","none");
						$(this).find(".lvitem").find("div").css("display","block");
						
						var thismod=$(this).parents(".userMod").find(".modName"),othermod=$(".modName").not(thismod);
						var _othermod="",this_id=$(this).children("td").eq(0).find("input").val(),urlopen=menuUrl.split("/");
					    
						for (var i = 0; i < othermod.length; i++) 
						{
					       
							if($(othermod[i]).text()!=""&&$(othermod[i]).text()!=null)
							{
								_othermod=_othermod+$(othermod[i]).text()+",";	
							}
						}
						_othermod=_othermod.substring(0,_othermod.length-1);

//						 var getbindurl="/"+urlopen[1]+"/api/ruleType/"+ruleTypeId+"/relation/paths/"+thismod.text()+"_"+this_id+"<"+_othermod;
//						 var getNextBindurl="/"+urlopen[1]+"/api/ruleType/"+ruleTypeId+"/relation/tree/"+thismod.text()+"_"+this_id+">"+_othermod;
//				
//						 $.getBind(getbindurl,modname+" .lv1");
//						 $.getBind(getbindurl+"<"+_othermod,modname+" .lv2");
//						 $.getBind(getbindurl+"<"+_othermod+"<"+_othermod,modname+" .lv3");
//
//						 switch($("#radio input:radio:checked").attr('id'))
//						 {
//						 case "rdoFir":
//							 $.getNextBind(getNextBindurl,thismod.text());
//							 break;
//						 case "rdoSen":
//							 $.getNextBind(getNextBindurl+">"+_othermod,thismod.text());
//							 break;
//						 case "rdoThi":
//							 $.getNextBind(getNextBindurl+">"+_othermod+">"+_othermod,thismod.text());	
//							 break;
//						 }
						});
			}
			
			if(tmpModname == 'ruleType'){
				$("#alternatecolor tbody tr").editable({ 
//					head: true, //有表头 
//					headcss:"tableItem",
//					foot: true, //有表头 
					noeditcol: [0,1], //第一列不可编辑 
					editcol: [{ colindex: 2 }, { colindex: 3, edittype: 0,multiSelect:true}], //配置表格的编辑列 ctrid:为关联的dom元素id 
//					opercol: [{ opername: "配置规则",operclass:"ssa",href:basePath+"/jsp/rule/list?ruleTypeId="}], //配置表格的编辑列 ctrid:为关联的dom元素id 
					opercol: [{ opername: "配置规则",operclass:"",onclick:"generRuleTable"}], //配置表格的编辑列 ctrid:为关联的dom元素id 
					onok: function (e) { 
						return postData(e);
					}, 
					ondel: function (modname,id) { 
						return $.del(modname,id); //返回false表示成功，dom元素相应有变化 
					}
				}); 
			}
			
//			$("#" + tmpModname + " .tableItem").click(function(){
//				alert(1); 
//			 });
		  }
		}
};

$.getShowForm = function(modname,allTitle,hideField){
	$("#"+modname+"FilterForm input").empty();
	
	$.each(allTitle,function(key, value){
		if($.inArray(key,hideField)>=0){
			$("#" + modname + "FilterDialog input[name='filterCol']").append("<option value='" +key+ "' selected='selected'>" + value+ "</option>");
		}else{
			$("#" + modname + "FilterDialog input[name='filterCol']").append("<option value='" +key+ "'>" + value+ "</option>");
		}
	});
	
	//针对下拉多选，无法识别隐藏域
	$("#" + modname + "FilterDialog").css("display", "block");
	$("#" + modname + "FilterDialog input[name='filterCol']").multiSelect({
		   selectAllText: '全选',  
		   noneSelected: '请选择',
		   //oneOrMoreSelected: '% options checked',
		   oneOrMoreSelected: '*',
	   });;
	$("#" + modname + "FilterDialog").css("display", "none");
	
};
//提交查询单行表单
$.postSingleSearchForm = function(firUrl,objname,pageNum,pageSize,queryParams){
	var url =  firUrl+"?page="+pageNum+","+pageSize;
	var id = queryParams['id'];
	if(id != null){
		url = firUrl+"?page="+pageNum+","+pageSize+"&id="+id;
	}
	if(firUrl!=""&&objname!="")
	{
		var menuUrl=firUrl+"/info";
		
		$.ajax({
			url : url,
			type : 'get',
			data: $(objname+"form").serialize(),
			success: function(result, status, xhr) {
				//alert(data);
				$.analyzeSingleTableData(result, status, xhr,objname,menuUrl,queryParams);
			}, 
			error: function() {
				hiAlert("对不起！你的请求出现异常，请联系服务人员！","重要提示");
			}
		});
	}
};

//提交查询表单
$.postSearchForm = function(firUrl, objname, pageNum, pageSize, queryParams) {
	var url = firUrl + "?page=" + pageNum + "," + pageSize;
	$.each(queryParams, function(key, value) {
		url += "&" + key + "=" + value;
	});
	if (firUrl != "" && objname != "") {
		var menuUrl = firUrl + "/info";
		$.ajax({
			url : url,
			type : 'get',
			asysc:false,
			data : $(objname + "form").serialize(),
			success : function(result, status, xhr) {
				$.analyzeTableData(result, status, xhr, objname, menuUrl,
						queryParams);
			},
			error : function() {
				hiAlert("对不起！你的请求出现异常，请联系服务人员！", "重要提示");
			}
		});
	}
};


// 得到查询表单
$.getSearchForm = function(firUrl,objname){
    if(firUrl!=""&&objname!=""){
   $.getJSON(firUrl+objname+"/info", function(data, status, xhr) {
		if (status == "success" && data != null)
		{
			if (data.info.success == true&&data.data.fieldProperties&&JSON.stringify(data.data.fieldProperties) != "[]") 
			{	
				//$("#searchForm>div").remove();
				var _items=data.data.fieldProperties;
				var formContent="";
				for ( var i = 0; i < _items.length; i++){
		
					switch(_items[i].operate)
					{
					case "input":
						formContent+="<div>"+_items[i].name+":<input name=\""+_items[i].path+"\" type=\"text\"/> </div>";
						break;
					case "range_long":
						formContent+="<div>"+_items[i].name+":<input name=\""+_items[i].path+"\" type=\"text\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onafterpaste=\"this.value=this.value.replace(/[^\\d]/g,'')\" /> </div>";
						break;
					case "select":
						formContent+="<div>"+_items[i].name+":<select name=\""+_items[i].path+"\">";
						for ( var item in _items[i].option)
					     {
							formContent+="<option value=\""+item+"\">"+ _items[i].option[item]+"</option>";
					     }
						formContent+="</select></div>"
						break;
					}
					}
			     
				$("#"+objname+"form").append(formContent);
			}
		}

		});
    }
    }

//得到修改表单
$.getOperForm = function(firUrl,objname,id,oper){
	if(firUrl!=""&&objname!="")
	{
		$.ajax({
			url : firUrl+objname+"/info",
			type : 'get',
//			asysc:false,
			success : function(data, status, xhr) {
				if (status == "success" && data != null)
				{
					if (data.info.success == true&&data.data.fieldProperties&&JSON.stringify(data.data.fieldProperties) != "[]") 
					{	
						$("#"+objname+"operForm").empty();
						var _items=data.data.fieldProperties;
						var formContent="";
						
						if(oper == 'add'){
							for ( var i = 0; i < _items.length; i++){
								switch(_items[i].operate)
								{
								case "input":
									formContent+="<div>"+_items[i].name+":<input name=\""+_items[i].path+"\" type=\"text\"/> </div>";
									break;
								case "range_long":
									formContent+="<div>"+_items[i].name+":<input name=\""+_items[i].path+"\" type=\"text\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onafterpaste=\"this.value=this.value.replace(/[^\\d]/g,'')\" /> </div>";
									break;
								case "select":
									formContent+="<div>"+_items[i].name+":<select name=\""+_items[i].path+"\">";
									for ( var item in _items[i].option)
									{
										formContent+="<option value=\""+item+"\">"+ _items[i].option[item]+"</option>";
									}
									formContent+="</select></div>"
										break;
								}
							}
						}else if(oper == 'edit'){
							var editInfo = $.getOperInfo(objname,id);
							for ( var i = 0; i < _items.length; i++){
								switch(_items[i].operate)
								{
								case "input":
									formContent+="<div>"+_items[i].name+":<input name=\""+_items[i].path+"\" type=\"text\"  value=\""+editInfo.data[0][_items[i].path]+"\"/> </div>";
									break;
								case "range_long":
									formContent+="<div>"+_items[i].name+":<input name=\""+_items[i].path+"\" type=\"text\" value=\""+editInfo.data[0][_items[i].path]+"\" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onafterpaste=\"this.value=this.value.replace(/[^\\d]/g,'')\" /> </div>";
									break;
								case "select":
									formContent+="<div>"+_items[i].name+":<select name=\""+_items[i].path+"\">";
									for ( var item in _items[i].option)
									{
										if(editInfo.data[0][_items[i].path] == item){
											formContent+="<option value=\""+item+"\" selected =\"selected\">"+_items[i].option[item]+"</option>";
										}else{
											formContent+="<option value=\""+item+"\">"+_items[i].option[item]+"</option>";
										}
									}
									formContent+="</select></div>";
										break;
								}
							}
						}else if(oper == 'view'){
							var editInfo = $.getOperInfo(objname,id);
							for ( var i = 0; i < _items.length; i++){
								switch(_items[i].operate)
								{
								case "input":
									formContent+="<div style='padding:3px'>"+_items[i].name+" : "+editInfo.data[0][_items[i].path]+"</div>";
									break;
								case "range_long":
									formContent+="<div style='padding:3px'>"+_items[i].name+" : "+editInfo.data[0][_items[i].path]+"</div>";
									break;
								case "select":
									for ( var item in _items[i].option)
									{
										if(editInfo.data[0][_items[i].path] == item){
											formContent+="<div style='padding:3px'>"+_items[i].name+" : "+_items[i].option[item]+"</div>";
										}
									}
									break;
								}
							}
						}
						//针对下拉多选，无法识别隐藏域
						$("#"+objname+"operForm").append(formContent);
						var ss = $("#" + objname + "OperDialog input[name='objTypes']");
						$("#" + objname + "OperDialog").css("display", "block");
						getMultiSelect("#" + objname + "OperDialog input[name='objTypes']");
						$("#" + objname + "OperDialog").css("display", "none");
					}
				}
			},
			error : function() {
				alert("对不起！你的请求出现异常，请联系服务人员！", "重要提示");
			}
		});
	}
};


function searchNode(treeId,nodeId,newName,modename){
	var nodeList=[];
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	nodeList = zTree.getNodesByParam("id", modename+"_"+nodeId);
	
	editTreeNodes(treeId,nodeList,newName);
}
function editTreeNodes(treeId,nodeList,newName) {
	for( var i=0, l=nodeList.length; i<l; i++) {
//		nodeList[i].highlight = true;
//		zTree.updateNode(nodeList[i]);s
		var tId = nodeList[i].tId;
		
		var treeSelectedNode = $("#"+treeId+" span[id="+tId+"_span]").text();
		var subtsNode = treeSelectedNode.substring(0,treeSelectedNode.lastIndexOf("]")+1);
		var newNodes = subtsNode+" "+newName;
		$("#"+treeId+" a[id="+tId+"_a]").attr("title",newNodes);
		$("#"+treeId+" span[id="+tId+"_span]").text(newNodes);
	}
}
function delTreeNodes(treeId,nodeId,modename) {
	var nodeList=[];
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	nodeList = zTree.getNodesByParam("id", modename+"_"+nodeId);
	for( var i=0, l=nodeList.length; i<l; i++) {
		var tId = nodeList[i].tId;
		
		$("#"+tId).hide();
	}
}
//提交修改表单，先得到修改表单
$.postOperForm = function(firUrl, objname, id, oper) {
	var url = firUrl + '/' + objname;
	var title = "新增";
	var buttons = {
		"确定" : function() {
			$.ajax({
				type : 'POST',
				url : url,
				dataType : 'json',
				data : $("#" + objname + "operForm").serialize(),
				cache : false,
				async : false,
				error : function() {
					alert('系统连接失败，请稍后再试！');
				},
				success : function(result) {
					if (result.info.success) {
						alert("操作成功!");
					}
				}
			});
			var newName = $("#" + objname + "operForm input[name='name']").val();
			searchNode("treeDemo",id,newName,objname);
			searchNode("treeReverseDemo",id,newName,objname);
			
			var seleBtns = $("#" + objname).find("[name='number']");
			// 重新获取表格-刷新
			$.postSearchForm(basePath + "/" + objname, "#" + objname, 1,
					seleBtns.val(), queryParams);
			$(this).dialog("close");
		},
		"关闭" : function() {
			$(this).dialog("close");
		}
	};
	if (oper == "edit") {
		url = firUrl + '/' + objname + "?id=" + id;
		title = "修改";
	} else if (oper == "view") {
		title = "查看";
		buttons = {
			"关闭" : function() {
				$(this).dialog("close");
			}
		};
	}
	var queryParams = {}; // Map map = new HashMap();
	queryParams['ruleTypeId'] = getUrlParam('ruleTypeId'); // map.put(key,
															// value);
	$.getOperForm(firUrl, objname, id, oper);
	$("#" + objname + "OperDialog").dialog({
		width : 270,
		title : title,
		modal : true,
		buttons : buttons
	});

}

$.getOperInfo = function(objname,id,oper){
    var info="";
    $.ajax({
		type: 'GET',
		url: getBasePath()+'/'+objname+'?id='+id,
		dataType:'json',
		cache : false,
		async:false,
		error:function(){alert('系统连接失败，请稍后再试！');},
		success: function(result){
			if(result.info.success){
				info = result;
			}
		}	  
	});	
    return info;
}
//得到子节点
$.getNextBind = function(url,objname){
	 $.setRow();
    if(url!=""&&objname!="")
   {
	$.getJSON(url, function(data, status, xhr) {
	   if (status == "success" && data != null)
	  {
	    if (data.success == true&&data.result[objname]&&JSON.stringify(data.result[objname]) != "[]") 
		{
	       var _obj=data.result[objname],_objn=_obj[0].relatedNode.next;

	     	for ( var item in _objn)
	     {
			var _item=_objn[item],changeitem=$("#"+item+" tr");
			
			for ( var i = 0; i < _item.length; i++){
			
			//如果ID表格中不确定可修改此逻辑
			for ( var j = 1; j < changeitem.length-1; j++) 
			{
				var getTd=$(changeitem[j]).children('td').eq(1)
				if(_item[i].data==getTd.text())
					{
					//alert(_item[i].data+"有");
					getTd.parent("tr").addClass("importcolor").removeClass("evenrowcolor").removeClass("oddrowcolor");
					}
			  }
		    }
		 }			
	    }
	  }
	});
   }
    else
   {
    alert("数据不正确");	
   }
}

//得到父节点
$.getBind = function(url,objname){
    if(url!=""&&objname!=""){
	$.getJSON(url, function(result, status, xhr) {
		if (status == "success" && result != null)
		{
			if (result.info.success == true&&result.data&&JSON.stringify(result.data) != "[]") 
			{
				$(objname).val( result.data.length);
				 var _getBind = "";
				    for(var i=0;i<result.data.length ;i++)
				    {
				    	_getBind +=result.data[i]+",\r\n";
				    }
				$(objname).attr("title",_getBind.substring(0,_getBind.length-3));

				$(objname).mousemove(function(){
				    
		        	$(objname).tooltip({track : true});
				
					});
			}
			else
			{
				$(objname).val(0);
				$(objname).attr("title","");
				
			}
		}
	});
   }
    else
   {
    alert("数据不正确");	
   }
}

//保存子节点
$.saveBind = function(url,pmodname,nmodname,ruleTypeId){
	var _pType=$(pmodname+" .modName").text();
	var _nType=$(nmodname+" .modName").text();
	var pidcount=$(pmodname).find("input[name='cbitem']:checked");
	var _pId=$.getid(pmodname);
	var _nId=$.getid(nmodname);
	var _ruleTypeId = ruleTypeId;
    if(_pType!=""&&_pId!=""&&_nType!=""&&_nId!="")
   {
	$.post(url,{ pType: _pType, pId: _pId, nType: _nType,nId: _nId ,ruleTypeId:_ruleTypeId}, function(data, status, xhr) {
		if (status == "success" && data != null)
		{
			if (data.info.success == true) 
			{
				alert("绑定成功");
			 }
			else
			{				
				alert("绑定失败");
			}
		}
	});
   }
    else
   {
    alert("数据不正确");	
   }
}
//解除绑定
$.removeBind = function(url,pmodname,nmodname,ruleTypeId){
	var _pType=$(pmodname+" .modName").text();
	var _nType=$(nmodname+" .modName").text();
	var pidcount=$(pmodname).find("input[name='cbitem']:checked");
	var _pId=$.getid(pmodname);
	var _nId=$.getid(nmodname);
	var _ruleTypeId = ruleTypeId;
    if(_pType!=""&&_pId!=""&&_nType!=""&&_nId!="")
   {
	$.post(url,{ pType: _pType, pId: _pId, nType: _nType,nId: _nId ,ruleTypeId:_ruleTypeId}, function(data, status, xhr) {
		if (status == "success" && data != null)
		{
			if (data.info.success == true) 
			{
				alert("解绑成功");
			 }
			else
			{				
				alert("解绑失败");
			}
		}
	});
   }
    else
   {
    alert("数据不正确");	
   }
}

//中文菜单
$.menuChina = function(url,modname){
	$.getJSON(url,function(data, status, xhr) {
		if (status == "success" && data != null)
		{
			if (data.info.success == true&&data.data&&JSON.stringify(data.data) != "[]") 
			{
				$(modname+" h1").text(data.data.cnName);
				$(modname+" .modName").text(data.data.name);
				
				if(data.data.fieldProperties&&JSON.stringify(data.data.fieldProperties) != "[]")
					{
					$.each(data.data.fieldProperties,function(entryIndex,entry) 
							{
						$(modname+" th.tableItem").each(function(){
						     if(entry.path==$(this).text())
						    	 {
						    	 $(this).text(entry.name);
						    	 return false;
						    	 }
						});
							});
					}
				
			}
		
		}

	});
	}

//全选
$.selAll = function(){
	$("input[name='checkAll']").click(function(){
		  
		if(this.checked)
		{  
		   $(this).parents("table").find("input[name='cbitem']").each(function(){this.checked=true;});
		 }
		  else
		 {
		    $(this).parents("table").find("input[name='cbitem']").each(function(){this.checked=false;});
		 }
	});
	}
$.getTable = function(firUrl, modname, queryParams){
	var menuUrl=firUrl+"/info";
	var seleBtns=$(modname).find("[name='number']");

	$.postSearchForm(firUrl,modname,1,seleBtns.val(),queryParams);
	//表格样式
	$.setRow();
	
	//全选
	$.selAll();
	
	//下拉表选页
	$(modname+ " #number").change(function(){ 
//     var paging ="&pageSize="+ $(this).find("option:selected").text() +"&pageNum=1";
     var pageNum = 1;
     var pageSize =  $(this).find("option:selected").text();
  	 $.postSearchForm(firUrl,modname,pageNum,pageSize,queryParams);
	});

	//滚动翻页
	$(modname + " .slider").slider({
		range : "max",
		min : 1,	
		value : 1,
		slide : function(event,ui) {
			if(!alertSave(modname)){//如果返回值为false,说明取消点击了取消
				return false;
			};
			$(modname+ " .slider").attr("title","Page "+ ui.value+ " of "+$(this).slider('option', 'max'));
			 var  pageSize=$(this).parents("table").find("[name='number']").val();	
			 var pageNum = ui.value;
//		     var paging = "&pageSize="+ pageSize +"&pageNum="+ui.value;
        	$(document).tooltip({track : true});
        	queryParams["hideField"]=getHideField();
            $.postSearchForm(firUrl,modname,pageNum,pageSize,queryParams);
        	}
});

	//上 一页	
	$(modname+" .btnPrev").click(function(){
		if(!alertSave(modname)){//如果返回值为false,说明取消点击了取消
			return false;
		};
		if($(this).hasClass("btn-just-Icon-b"))
		return false

		var str = $(this).siblings(".pageNow").text().split("/"),nowPage=parseInt(str[0])-1, pageSize=$(this).parents("table").find("[name='number']").val();		
//        var paging = "&pageSize="+ pageSize +"&pageNum="+nowPage;
        var pageNum=nowPage;
    	queryParams["hideField"]=getHideField();
        $.postSearchForm(firUrl,modname,pageNum,pageSize,queryParams);
        
        $(modname + " .slider").slider("option","value",nowPage);
		return false
	});
	//首页	
	$(modname+" .btnTotop").click(function(){
		if(!alertSave(modname)){//如果返回值为false,说明取消点击了取消
			return false;
		};
		if($(this).hasClass("btn-just-Icon-b"))
		return false
		var pageSize=$(this).parents("table").find("[name='number']").val();
		var pageNum=1;
//		var paging = "&pageSize="+pageSize +"&pageNum=1";
		queryParams["hideField"]=getHideField();
		 $.postSearchForm(firUrl,modname,pageNum,pageSize,queryParams);
	     $(modname + " .slider").slider("option","value",1);
		return false
	});
	//下一页 
	$(modname+" .btnNext").click(function(){
		if(!alertSave(modname)){//如果返回值为false,说明取消点击了取消
			return false;
		};
		
		if($(this).hasClass("btn-just-Icon-b"))
		return false;
		
		var str = $(this).siblings(".pageNow").text().split("/"),pageSize=$(this).parents("table").find("[name='number']").val();
		var nowPage=parseInt(str[0])+1;
//		var paging ="&pageSize="+pageSize +"&pageNum="+nowPage;
		var pageNum = nowPage;
		
		queryParams["hideField"]=getHideField();
		
		$.postSearchForm(firUrl,modname,pageNum,pageSize,queryParams);

        $(modname + " .slider").slider("option","value",nowPage);
		return false;
	});
	//最后一页
	$(modname+" .btnTolast").click(function(){
		if(!alertSave(modname)){//如果返回值为false,说明取消点击了取消
			return false;
		};
		if($(this).hasClass("btn-just-Icon-b"))
		return false;

		var str = $(this).siblings(".pageNow").text().split("/") ,str2=str[1].split("("),nowPage=parseInt(str2[0]);
		var pageSize=$(this).parents("table").find("[name='number']").val();
//		var paging = "&pageSize="+pageSize+"&pageNum="+nowPage;
		var pageNum = nowPage;
		queryParams["hideField"]=getHideField();
		$.postSearchForm(firUrl,modname,pageNum,pageSize,queryParams);
		 $(modname + " .slider").slider("option","value",nowPage);
		return false
	});
	//增加记录
	$(modname+" .btnPlus").click(function(){
		if(!alertSave(modname)){//如果返回值为false,说明取消点击了取消
			return false;
		};
		 var seleBtn=$(this).parents("table").find("[name='number']"),itemCount = parseInt(seleBtn.val()) + 5;

		if(parseInt(seleBtn.val())>=15)
         return false;

		 if(itemCount>=15)
		 $(this).removeClass("btnIcon").addClass("btnIconb");
//		 var paging="&pageSize="+ itemCount + "&pageNum=1";
		 var pageSize = itemCount,pageNum=1;
		  $(this).siblings(".btnMin").removeClass("btnIconb").addClass("btnIcon");
			seleBtn.val(itemCount);
			queryParams["hideField"]=getHideField();
			$.postSearchForm(firUrl,modname,pageNum,pageSize,queryParams);
			return false;
	});
	//减少记录
	$(modname+" .btnMin").click(function(){	
		if(!alertSave(modname)){//如果返回值为false,说明取消点击了取消
			return false;
		};
		var seleBtn=$(this).parents("table").find("[name='number']"),itemCount = parseInt(seleBtn.val())-5;

		if(parseInt(seleBtn.val())<=5)
        return false;

		 if(itemCount<=5)
			 $(this).removeClass("btnIcon").addClass("btnIconb");

//		 var paging="&pageSize="+ itemCount + "&pageNum=1";
		 var pageSize = itemCount,pageNum=1;
		 $(this).siblings(".btnPlus").removeClass("btnIconb").addClass("btnIcon");
		 seleBtn.val(itemCount);
		queryParams["hideField"]=getHideField();
		 $.postSearchForm(firUrl,modname,pageNum,pageSize,queryParams);
		 return false;
	});
	
	function alertSave(modname){
		var textNo = $(modname+" tbody>tr input[type='text']").length;
		var multiSelectNo = $(modname+" tbody>tr .multiSelect").length;
		if(textNo>0 || multiSelectNo>0){
			  if(!confirm("表格中有未保存数据，你确认要放弃保存么？")){
				   return false;
			   }
		}
		return true;
	}
	
	function getHideField(){
		var $tr = $(modname +" thead th:hidden");
		var hideField =[];
		$.each($tr,function(key, value){
			hideField.push($(value).attr("name"));
		});
		return hideField;
	}
	//根据规则类型和父节点获取规则
	$.getRule = function(url,ruleTypeId,pobjType){
	    var rule="";
	    $.ajax({
			type: 'GET',
			url: url+'api/ruleType/'+ruleTypeId+'/rule/paths?pobjType='+pobjType,
			dataType:'json',
			cache : false,
			async:false,
			error:function(){alert('系统连接失败，请稍后再试！');},
			success: function(result){
				if(result.info.success){
				    rule = result.data;
				}
			}	  
		});	
	    return rule;
	}
	
	$.getNext = function(url,pId,pType){
	    var result="";
	    $.ajax({
			type: 'POST',
			url: url,
			data:{
				'pId':pId,
				'pType':pType
			},
			dataType:'json',
			cache : false,
			async:false,
			error:function(){alert('系统连接失败，请稍后再试！');},
			success: function(data){
				if(data.success){
					result = data.result;
				}
			}	  
		});	
	    return result;
	};
	
	$.del = function(objname,id,ruleTypeId){
		if(confirm("确定删除？")){
			if($.dele(objname,id,ruleTypeId)){
				 if(objname!="relation"){
					 alert('删除成功！');
				 }
			};
		}
	};
	
	$.dele = function(objname,id,ruleTypeId){
		var isSuccess = false;
		var url = getBasePath()+'/'+objname+'/'+id;
		if(objname=="relation"){
			url = getBasePath()+'/api/'+objname+'/'+id+'?ruleTypeId='+ruleTypeId;
		}else if(objname=="rule" || objname=="ruleType"){
			url = getBasePath()+'/api/'+objname+'/'+id;
		}
//		if(confirm("确定删除？")){
			$.ajax({
				   url:url,
				   type:'DELETE',
				   dataType:'json',
				   async:false,
				   error:function(){alert('系统连接失败，请稍后再试！');},
				   success:function(data){
					   if(data.info.success==true){
						   $trs = $("#"+objname+" tbody tr");
						   $trs.each(function (i, tr) {
							   $tds = $(tr).find("td");
							   var $id = $($tds[0]).find("input").val();
							   if(id == $id){
								   $(tr).remove();
							   }
						   });
						   delTreeNodes("treeDemo",id,objname);
						   delTreeNodes("treeReverseDemo",id,objname);
						   isSuccess = true;
					   }else{
						   alert('系统异常，删除失败！');
					   }
				   }
			   });
//		}
		return isSuccess;
	};
	
	$.delAll = function(objname,id,ruleTypeId){
		var pids = $.getRelationId(objname,id,false,ruleTypeId);
		var nids = $.getRelationId(objname,id,true,ruleTypeId);
		var ids = (pids=="") ? nids : ((nids=="") ? pids : pids+","+nids);
		if($.del(objname,id,ruleTypeId) && ids!=""){
			$.del("relation",ids,ruleTypeId);
		}
		
		queryParams['ruleTypeId'] = getUrlParam('ruleTypeId'); // map.put(key, value);
		var seleBtns=$("#"+objname).find("[name='number']");
     	//重新获取表格-刷新
     	$.postSearchForm(basePath+"/"+objname,"#"+objname,1,seleBtns.val(),queryParams);
	};	
	
	$.getRelationId = function(objname,id,isReverse,ruleTypeId){
	   var ids = "";
	   var data = {pid:id, ptype:objname,ruleTypeId:ruleTypeId};
	   if(isReverse){
		   data = {nid:id, ntype:objname,ruleTypeId:ruleTypeId};
	   }

	   $.ajax({
		   url:getBasePath()+'/api/relation',
		   data:data,
		   type:'get',
		   dataType:'json',
		   async:false,
		   error:function(){alert('系统连接失败，请稍后再试！')},
		   success:function(data){
			   if(data.success==false){
				   alert('系统异常，删除失败！');
			   }else{
				   for(var i in data.data){
					   ids += data.data[i].id;
					   if(i<data.data.length-1){
						   ids += ",";
					   }
				   }
			   }
		   }
	   });
	   return ids;
	};
	
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
// 					$("#relationObj").append("<option value= '{id:"+result.data[i].id+",entity:"+result.data[i].type+"}'>"+result.data[i].name+"</option>");	
 				}
 				$(selectObj).multiSelect({
 					selectAllText: '全选',  
 					noneSelected: '请选择',
// 					oneOrMoreSelected: '% options checked',
 					oneOrMoreSelected: '*',
 				});
// 				$("#relationObj").css("height","21px");
 			}
 		}	 
        });
    }
    
}



