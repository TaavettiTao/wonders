/**author:tww**/
//初始化页面
$(function(){
	initContent();
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


function initContent(){
	//获取实体属性
	var entityProperty=getEntityProperty();
	var tbodyContent="";
    for(var i=0;i<entityProperty.fieldProperties.length;i++){
    	var field=entityProperty.fieldProperties[i];
    	if(field.operate=="hidden"){
    		if(field.path=="id"){
    			$("table[id='table1']").before("<input type='hidden' name='"+field.path+"' />");
    		}else{
    			$("table[id='table1']").before("<input type='hidden' name='"+field.path+"' value='0'/>");
    		}
    	}
    	if(field.operate=="input"){
    		if(field.path=="password"){
    			tbodyContent+="<tr>";
        		tbodyContent+="<td>"+field.name+"</td>";
        		tbodyContent+="<td><input name='"+field.path+"' id='"+field.path+"' onfocus=\"emptyData('"+field.path+"')\" type='password' class='text-input small-input'/></td>";
        		tbodyContent+="<td></td><td></td><td></td><td></td><td></td><td></td>";
        		tbodyContent+="</tr>";
    		}else{
    			tbodyContent+="<tr>";
        		tbodyContent+="<td>"+field.name+"</td>";
        		tbodyContent+="<td><input name='"+field.path+"' id='"+field.path+"' onfocus=\"emptyData('"+field.path+"')\" type='text' class='text-input small-input'/></td>";
        		tbodyContent+="<td></td><td></td><td></td><td></td><td></td><td></td>";
        		tbodyContent+="</tr>";
    		}
    	}
    	if(field.operate.substring(0,5)=="range"){
    		tbodyContent+="<tr>";
    		tbodyContent+="<td>"+field.name+"</td>";
    		tbodyContent+="<td><input name='"+field.path+"' class='text-input small-input'/></td>";
    		tbodyContent+="<td></td><td></td><td></td><td></td><td></td><td></td>";
    		tbodyContent+="</tr>";
    	}
    	if(field.operate=="select"){
    		tbodyContent+="<tr>";
    		tbodyContent+="<td>"+field.name+"</td>";
    		tbodyContent+="<td>";
    		tbodyContent+="<select name='"+field.path+"'>";
			for(var key in field.option){
				tbodyContent+="<option value='"+key+"'>"+field.option[key]+"</option>";	
			}
			tbodyContent+="</select>";
			tbodyContent+="</td>";
			tbodyContent+="<td></td><td></td><td></td><td></td><td></td><td></td>";
			tbodyContent+="</tr>";
		}
    }
    $("table[id='table1']>tbody").html(tbodyContent);
	
}


function formSubmit(){
	$.ajax({  
		    url : getBasePath()+'/user/api/save',
			type : 'post',
			data:$("#user").serialize(),
			dataType:'json',
			success: function(data) {
				if(data.success==true ){
					alert("新增成功");
					$("input").val("");
					$("select option[value='']").attr("selected", "selected");
				}else{
					if(data.result!=null){
						for(var i=0;i<data.result.length;i++){
							switch(data.result[i].defaultMessage){
							   case "密码不能为空!": 
								        //获取当前节点的父节点的下一个兄弟节点
								        $("input[id='password']").parent().next().attr("align","left");
								        $("input[id='password']").parent().next().html("<font color='red'>*密码不能为空!</font>");
								     break;
							   case "用户名不能为空":
								        $("input[id='name']").parent().next().attr("align","left");
								        $("input[id='name']").parent().next().html("<font color='red'>*用户名不能为空!</font>");
								     break;
							   case "密码长度必须在6和12之间":
								         $("input[id='password']").parent().next().attr("align","left");
								         $("input[id='password']").parent().next().html("<font color='red'>*密码长度必须在6和12之间!</font>");
									 break;
							   case "请使用邮件格式,eg:1@163.com":
								         $("input[id='email']").parent().next().attr("align","left");
								         $("input[id='email']").parent().next().html("<font color='red'>*请使用邮件格式,eg:1@163.com!</font>");
								     break;
							   default:"";     
							}
						}
					}else{
						alert("系统异常，请联系管理员！");
					}
				}
			}	
		});
}

function emptyData(id){
	$("#"+id).parent().next().html("");
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