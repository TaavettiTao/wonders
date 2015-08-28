<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<base href="<%=basePath%>" />
	<title>编辑规则类型</title>

	<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/jquery.multiSelect.css" type="text/css" media="screen" />

	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	
	<script type="text/javascript" src="js/me.js"></script>
	<script type="text/javascript" src="js/ruleType.js"></script>
	<script type="text/javascript" src="js/jquery.multiSelect.js"></script>
	<script type="text/javascript">
	   $(function(){ 
		 var ruleType = getRuleType(getUrlParam("id"));
	   	 var relationObj = ruleType.objIds;
	   	 $("#name").val(ruleType.name);
		 initObjectInfoSelectWithChecked(relationObj);
// 	     listHistoryRelationRuleType(1);
	     //编辑事件
	     $("#edit").click(function(){
	    	 //alert($("#relationObj").selectedValuesString());
	         var name=$("#name").val();
	         var relationObj=$("#relationObj").selectedValuesString();
	         var objTypes = $("#relationObj span").text();
	         var warnNotice="";
	         if(name==""){
	            warnNotice+="请填写类型名称！\n";
	         }
	         if(relationObj==""){
	            warnNotice+="请选择关联对象！\n";
	         }
	         if(warnNotice!=""){
	           alert(warnNotice);
	         }else{
	        	var newName=$("#name").val();//修改后类型的名称
	    	    var oldName = ruleType.name;//待修改类型的名称
	    	    var isExist = false;
	    	    if(newName != oldName){
	        		isExist=isExistsRuleType(newName);
	    	    }
           		if(!isExist){
           		 $.ajax({
				        type: 'POST',
						url: '<%=basePath%>api/ruleType',
						data:{
							'id':ruleType.id,
							'name':name,
							'objIds':relationObj,
							'objTypes':objTypes,
							'removed':'0'
						},
						dataType:'json',
						error:function(){alert('系统连接失败，请稍后再试！')},
						success: function(data){
							if(data.info.success==true){
							   alert("修改成功！");
							   if(window.opener){
									window.opener.location.reload(true);
									window.close();
								}
							   //listHistoryRelationRuleType(1);
							}
						}	 
				    }); 
           		}
	       	  }
	     	});
	   });
	</script>
</head>

<body id="关联规则配置-form">
<div id="main-content">
	<div class="clear"></div>
	<div class="content-box">
		<div class="content-box-content">
			<div class="content-box-header">
				<h3>&nbsp;规则编辑</h3>
				<div class="clear"></div>
			</div>
	        
	        <form id="relationRuleType">
	        <input type="hidden" name="sort" id="sort" value="id desc"/>
			<table id="table1" border="0">
				<tbody>
					<tr>
						<td>类型名称<input type="text" name="name" id="name" value/></td>
<!-- 						<td>上级联对象 -->
<!-- 						 <select id="PType" name="ntype" multiple="multiple"> -->
<!-- 						    </select> -->
<!-- 						</td> -->
						<td>
						           关联对象 
						    <select id="relationObj" name="relationObj" multiple="multiple">
						    </select>
						</td>
						<td>
						   <input type="button" name="edit" id="edit" value="编辑保存"/>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>
		    
	</div>
	<div class="clear"></div>
	<div id="footer">
		<small>
			Powered by <a href="#" target="_blank">lsf</a> | <a href="mailto:mengjie@wondersgroup.com">联系我</a>
		</small>
	</div>
</div>
</body>
</html>
