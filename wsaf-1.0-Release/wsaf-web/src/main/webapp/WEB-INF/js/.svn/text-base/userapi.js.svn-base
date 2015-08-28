/** author:tww**/
$(function(){
	setMainContent();
	
});


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

/**
 * 控制查询div的显示与隐藏
 */
function showQuery(){
	setQueryContent();
	if($("#query").css("display")=="block"){
		$("#query").css("display","none");
	}else{
		$("#query").css("display","block");
	}
}

/**
 * 获取User对象的EntityProperty,将其中显示数据显示到页面
 */
function setQueryContent(){
	//设置实体属性显示
	var entityProperty=getEntityProperty();
	if(entityProperty!=null){
		$("#entityFieldProperties").html("");
		var tableItem="";
		for(var i=0;i<entityProperty.fieldProperties.length;i++){
			var field=entityProperty.fieldProperties[i];
			if(field.operate=="input"){
				tableItem+="<tr>";
				tableItem+="<td>"+field.name+"</td>";
				tableItem+="<td><input name='"+field.path+"' type='text' class='text-input small-input'/></td>";
				tableItem+="</tr>";
			}
			if(field.operate.substring(0,5)=="range"){
				tableItem+="<tr>";
				tableItem+="<td>"+field.name+"</td>";
				tableItem+="<td><input name='"+field.path+"' type='text' class='text-input small-input'/></td>";
				tableItem+="</tr>";
			}
			if(field.operate=="select"){
				tableItem+="<tr>";
				tableItem+="<td>"+field.name+"</td>";
				tableItem+="<td>";
				tableItem+="<select name='"+field.path+"'>";
				for(var key in field.option){
					tableItem+="<option value='"+key+"'>"+field.option[key]+"</option>";	
				}
				tableItem+="</select>";
				tableItem+="</td>";
				tableItem+="</tr>";
			}
		}
		tableItem+="<tr><td colspan='2'><input type='button' value='检索' onclick='javascript:submit();'/></td></tr>"
		$("#entityFieldProperties").html(tableItem);
	}
	
	 //设置查询中的分页数据
	 var entityData= getEntityData();
	 if(entityData!=null){
		 //设置查询条件中的隐藏分页数据
		 $("#pageNum").val(entityData.pageInfo.currentPage);
		 $("#pageSize").val(entityData.pageInfo.pageSize);
	 }
}


/**
 * 初始化设置页面数据
 */
function setMainContent(){
	
	//获取实体属性对象，用于显示
	var entityProperty=getEntityProperty();
	
	 //获取实体数据
	 var entityData=getEntityData();
	 
     //设置header部分信息
	$("#entityCnName").text(entityProperty.cnName+"列表");
	
	$("table[id='contentTable']>thead>tr").html("");
	 //设置显示列名
	 var thItems="<th><img src=\"images/icons2/flag_16.png\"/></th>";
	 thItems+="<th>序号</th>";
	 for(var i=0;i<entityProperty.fieldProperties.length;i++){
		  var field=entityProperty.fieldProperties[i];
		    if(field.operate!="hidden"){
		    	var fieldNum=$("#fieldNum").val();
		    	fieldNum++;
		    	$("#fieldNum").val(fieldNum);
		    	thItems+="<th>"+field.name+"</th>";
		    }
	 }	
	 thItems+="<th>操作</th><th>关联</th>";
	 $("table[id='contentTable']>thead>tr").html(thItems);
	 
	 $("table[id='contentTable']>tbody").html(""); 
	 //设置数据部分
	 var trData="";
	 for(var i=0;i<entityData.content.length;i++){
		 var j=i;
		 var fieldData=entityData.content[i];
		 trData+="<tr>";
		 trData+="<td><input type='checkBox' name='id' value='"+fieldData.id+"'/></td>";
		 trData+="<td>"+((++j)+entityData.pageInfo.startRecord)+"</td>";
		 for(var m=0;m<entityProperty.fieldProperties.length;m++){
			 var field=entityProperty.fieldProperties[m];
			 if(field.operate=="input"){
				 trData+="<td>"+fieldData[field.path]+"</td>";
			 }
			 if(field.operate.substring(0,5)=="range"){
				 trData+="<td>"+fieldData[field.path]+"</td>";
			 }
			 if(field.operate=="select"){
				 trData+="<td>";
				 for(var key in field.option){
					 if(key==fieldData[field.path]){
						 trData+=field.option[key];
					 }
				 }
				 trData+="</td>";
			 }
		 }
		 trData+="<td>";
		 trData+="<a href='"+entityProperty.name+"/edit/"+fieldData.id+"' title='编辑'><img src='images/icons2/pencil_16.png'/></a>";
		 trData+="<a href='javascript:void(0)' onclick='javascript:delUser("+fieldData.id+")' title='删除'><img src='images/icons2/delete_16.png'/></a>";
		 trData+="</td>";
		 trData+="<td>";
		 trData+="<span style='background-color:red'  onMouseOver='javascript:getLv1PreviousObjNum(this,'"+fieldData.id+"','"+entityProperty.name+"');'>0</span>";
		 trData+="<span style='background-color:green'>0</span><span style='background-color:blue'>0</span>";
		 trData+="</td>";
		 trData+="</tr>";
	 }
	 $("table[id='contentTable']>tbody").append(trData); 
	 
	 //页面下导航操作栏：tfoot
	 $("table[id='contentTable']>tfoot>tr>td:first-child").html("");
	 var tdItem="";
	 tdItem+="<input type='button' value='新增' onclick='toAdd()'/>";
	 tdItem+="<input type=\"button\" value=\"全选\" onclick=\"allSelect(this)\"/>";
	 tdItem+="<input type=\"button\" value=\"反选\" onclick=\"otherSelect()\"/>";
	 $("table[id='contentTable']>tfoot>tr>td:first-child").append(tdItem); 

	 //设置分页td数据
	 $("table[id='contentTable']>tfoot>tr>td:nth-child(2)").attr("colspan",$("#fieldNum").val());
	 
	 //设置id为pagination的内容
	 var paginationContent="";
	 paginationContent+="<a href='javascript:void(0)' onclick='javascript:onSubmit(1)'>&laquo; 第一页</a>";
     if(entityData.pageInfo.hasPrevious){
    	 paginationContent+="<a href='javascript:void(0)' onclick='javascript:onSubmit("+(entityData.pageInfo.currentPage-1)+")' class='number current'>上一页</a>";
     }
     for(var i=1;i<=entityData.pageInfo.totalPages;i++){
    	 if(entityData.pageInfo.currentPage==i){
    		 paginationContent+="<a href='javascript:void(0)' class='number current'>"+i+"</a>";
    	 }else{
    		 paginationContent+="<a href='javascript:void(0)' onclick='javascript:onSubmit("+i+")' class='number'>"+i+"</a>";
    	 } 
     }
     if(entityData.pageInfo.hasNext){
    	 paginationContent+="<a href='javascript:void(0)' onclick='javascript:onSubmit("+(entityData.pageInfo.currentPage+1)+")' class='number current'>下一页</a>";
     }
     paginationContent+="<a href='javascript:void(0)' onclick='javascript:onSubmit("+entityData.pageInfo.totalPages+")' title='Last Page'>最后一页 &raquo;</a>";
     paginationContent+="<span> 共"+entityData.pageInfo.totalPages+"页/"+entityData.pageInfo.totalRecord+"条 </span>";
     paginationContent+="<span> 每页</span>";
     paginationContent+="<select id='pageSizeSelector' onchange='javascript:onSubmit(1)'>";
     paginationContent+="<option value='1'";
     if(entityData.pageInfo.pageSize==1){
    	 paginationContent+=" selected='selected'";
     }
     paginationContent+=">1</option>";
     
     paginationContent+="<option value='5'";
     if(entityData.pageInfo.pageSize==5){
    	 paginationContent+=" selected='selected'";
     }
     paginationContent+=">5</option>";
     
     paginationContent+="<option value='10'";
     if(entityData.pageInfo.pageSize==10){
    	 paginationContent+=" selected='selected'";
     }
     paginationContent+=">10</option>";
     
     paginationContent+="<option value='20'";
     if(entityData.pageInfo.pageSize==20){
    	 paginationContent+=" selected='selected'";
     }
     paginationContent+=">20</option>";
     paginationContent+="</select><span>条</span>";
     
     $("#pagination").html(paginationContent);  
}


function delUser(id){
	if(confirm("确定要删除该条数据吗？")){
		$.ajax({
		    url : getBasePath()+'/user/api/del/'+id,
			type : 'post',
			dataType:'json',
			success: function(data) {
				if(data.success==true){
					window.location.href="user/api/toListApi";
				}
			}	
		});	
	}
}

function toAdd(){
	window.location.href="user/api/toEditApi";
}

//获取实体属性对象
function getEntityProperty(){
	var entityProperty=null;
	 $.ajax({
		   async:false,  //将ajax异步变为同步
		    url : getBasePath()+'/user/api/getEntityProperty',
			type : 'post',
			dataType:'json',
			success: function(data) {
				if(data.success==true && data.result.fieldProperties.length!=0){
					entityProperty=data.result;
				}
			}	
		});
	 return entityProperty;
}

//获取实体数据
function getEntityData(){
	var entityData=null;
	var pageNum=$("#pageNum").val();
	var pageSize=$("#pageSizeSelector").val()==null?20:$("#pageSizeSelector").val();
	var sort=$("#sort").val();
	$.ajax({
		async:false,//将ajax异步变为同步
	    url : getBasePath()+'/user/api/page',
		type : 'post',
		data:"pageNum="+pageNum+"&pageSize="+pageSize+"&sort="+sort,
		dataType:'json',
		success: function(data) {
			if(data.success==true && data.result.content.length!=0){
				entityData=data.result;
			}
		}	
	});
	return entityData;
}








