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
	<title>新增规则类型</title>

	<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/jquery.multiSelect.css" type="text/css" media="screen" />

	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	<!--
	<script type="text/javascript" src="js/simpla.jquery.configuration.js"></script>
	<script type="text/javascript" src="js/facebox.js"></script>
	<script type="text/javascript" src="js/jquery.wysiwyg.js"></script>
	-->
	<script type="text/javascript" src="js/me.js"></script>
	<script type="text/javascript" src="js/ruleType.js"></script>
	<script type="text/javascript" src="js/jquery.multiSelect.js"></script>
	<script type="text/javascript">
	   $(function(){  
	     initObjectInfoSelect();
		
	     listHistoryRelationRuleType(1);
	     
	     //新增事件
	     $("#add").click(function(){
	    	 alert($("#relationObj").selectedValuesString());
	    	 alert($("#relationObj").text());
	         var name=$("#name").val();
	         var objIds=$("#relationObj").selectedValuesString();
	         var objTypes = $("#relationObj span").text();
	         var isExists=isExistsRuleType(name);
	           if(!isExists){
		         $.ajax({
			        type: 'POST',
					url: '<%=basePath%>api/ruleType',
					data:{
						'name':name,
						'objIds':objIds,
						'objTypes':objTypes,
						'removed':'0'
					},
					dataType:'json',
					async:false,
					error:function(){alert('系统连接失败，请稍后再试！')},
					success: function(result){
						if(result.info.success==true){
						   alert("新增成功！");
						   listHistoryRelationRuleType(1);
						}
					}	 
			      });
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
				<h3>&nbsp;规则新增</h3>
				<div class="clear"></div>
			</div>
	        
	        <form id="relationRuleType">
	        <input type="hidden" name="sort" id="sort" value="id desc"/>
			<table id="table1" border="0">
				<tbody>
					<tr>
						<td>类型名称<input type="text" name="name" id="name"/></td>
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
						   <input type="button" name="add" id="add" value="新增"/>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td>
						     <input type="hidden" id="type"  name="type"  value="${type}"/>
						</td>
					</tr>
				</tbody>
			</table>
			</form>
		    <div style="height: 20px"></div>
		     &nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="javascript:showQuery()">查询条件</a>
              <div style="height: 180px;display: none;" id="query">
                 <form id="query">
	                  <table >
	                    <thead></thead>
	                    <tbody>
	                      <tr>
	                          <td>上级联对象</td>
	                          <td><input type="text"  id="nType" class="text-input small-input"/></td>
	                          <td></td>
	                          <td></td>
	                      </tr>
	                       <tr>
	                          <td>下级联对象</td>
	                          <td><input type="text"  id="nType" class="text-input small-input"/></td>
	                          <td></td>
	                          <td></td>
	                      </tr>
	                      <tr>
	                          <td colspan="2">
	                              <input type="button" value="检索" name="retrieval" id="retrieval" onclick="retrievalList()"/>
	                              &nbsp;&nbsp;&nbsp;
	                              <input type="reset" value="重置"/>
	                          </td>
	                          <td></td>
	                          <td></td>
	                      </tr>
	                     </tbody>
	                     <tfoot></tfoot>
	                </table>
                </form>
              </div>   
             
		    <table id="relationRuleHistoryData">
		        <thead>
		           <tr>
		              <td colspan="6"><h3>&nbsp;&nbsp;&nbsp;规则列表</h3></td>
		           </tr>
		           <tr style="background-color: #D8C655">
		              <th><img src="images/icons2/flag_16.png"/></th>
		              <th>序号</th>
		              <th>名称</th>
		              <th>规则对象</th>
		              <th>操作</th>
		           </tr>
		        </thead>
		        <tbody></tbody>
		        <tfoot>
		           <tr>
		              <td>
		                 <input type="button" value="全选"  id="allSelect" onclick="allSelect(this)"/>
		                 <input type="button" value="确认删除" onclick="delAllSelect()"/>
		              </td>
		              <td colspan="5" align="right">
			                <div class="pagination" id="paginationHistory"></div>
		              </td>
		           </tr>
		        </tfoot>
		    </table>
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
