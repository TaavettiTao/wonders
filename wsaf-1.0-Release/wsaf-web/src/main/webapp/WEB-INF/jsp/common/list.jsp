<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 


<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<base href="<%=basePath%>" />
	<title>FRAME</title>

	<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen" />

	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
	<!--
	<script type="text/javascript" src="js/simpla.jquery.configuration.js"></script>
	<script type="text/javascript" src="js/facebox.js"></script>
	<script type="text/javascript" src="js/jquery.wysiwyg.js"></script>
	-->
	<script type="text/javascript" src="js/me.js"></script>
	<script type="text/javascript">
	    $(document).ready(function(){
        	showObjectOption();
        })
        //全选，全不选
        function allSelect(e) {
            if (e.value=="全选") {
                $(":checkbox").attr("checked", true);
                e.value="全不选";
            }
            else {
                $(":checkbox").attr("checked",false);
                e.value="全选";
            }
        }
        //反选
        function otherSelect() {
            $(":checkbox").each(function () {
                if ($(this).attr("checked") == true) {
                    $(this).attr("checked",false);                    
                }
                else {
                    $(this).attr("checked", true);
                }
            });
        }
        
        function onSubmit(page) {
            $("#pageNum").val(page);
            $("#pageSize").val($("#pageSizeSelector").val());
            $("form[name='query']").submit();
        }

        function getRelationRules(){
           //保存可绑定的规则类型
           var objectTypes="";
           var ptype=$("#pType").val();
           var ruleType=$("#ruleType").val();
           $.ajax({
                  url:'<%=basePath%>relationRule/api/getRelationRules',
                  type:'POST',
                  dataType:'json',
                  async:false, //同步
                  data:{ 
                          'ptype':ptype,
                          'type':ruleType
                  },
                  success:function(data){
                      if(data.success){
                         if(data.result!=null && data.result.length>0){
                            for(var i=0;i<data.result.length;i++){
                                objectTypes+=data.result[i].ntype+",";
                            }
                         }
                      }
                  }
           });
           return objectTypes;
        }

        //初始化绑定选择框
        function showObjectOption(){
            var objectTypes=getRelationRules();
		    $.ajax({
				type: 'POST',
				url: '<%=basePath%>objInfo/api/getObjInfos?objTypes='+objectTypes,
				dataType:'json',
				cache : false,
				async:false,
				error:function(){alert('系统连接失败，请稍后再试！')},
				success: function(obj){
					if(obj.success){
						$("#nTypeSelector").append("<option value=''>请选择</option>");
						for(var i=0;i<obj.result.length;i++){
							$("#nTypeSelector").append("<option value='"+obj.result[i].type+"'>"+obj.result[i].name+"</option>");	
						}
					}
				}	  
			});	
		}
		
		function bind(){		 	
			var pId="";
			$(":[name='id']checkbox").each(function () {
                if ($(this).attr("checked") == true) {                   
                    if(pId!=""){
                    	pId+=","
                    };
                    pId+=$(this).val();               
                }
                
            });
            if(pId==""){
		 		alert("请先选择要被绑定的记录");
		 		return;
		 	}else{
		 		$("#pId").val(pId);
		 	}
		 	
		 	var nType=$("#nTypeSelector").val();
            if(nType==""){
		 		alert("请先选择要绑定的对象类型");
		 		$("#nTypeSelector").focus();
		 		return;
		 	}
		 	
	 	    var ruleType=$("#ruleType").val();
	 	    $("form[name='binding']").attr("action",nType+"/list?ruleType="+ruleType);
		    $("form[name='binding']").submit();
		}
			
		function confirmBind(nType){		 	
			var nId="";
			$(":[name='id']checkbox").each(function () {
                if ($(this).attr("checked") == true) {                   
                    if(nId!=""){
                    	nId+=","
                    };
                    nId+=$(this).val();
                                   
                }
                
            });
            if(nId==""){
		 		alert("请先选择绑定的记录");
		 		return;
		 	}
		 	
			$.ajax({
				type: 'POST',
				url: '<%=basePath%>relation/api/saveBind',
				data:{
						"pId":$("#pId").val(),
						"pType":$("#pType").val(),
						"nId":nId,
						"nType":nType,
						"type":$("#ruleType").val() //配置规则类型
					},
				dataType:'json',
				cache : false,
				error:function(){alert('系统连接失败，请稍后再试！')},
				success: function(obj){
					if(obj.success){
					      window.location.href=$("#pType").val()+"/list?ruleType="+$("#ruleType").val();
					}
				}	  
			});	

		}
		
		function getLv1PreviousObjNum(e,nId,nType){
			$.ajax({
				type: 'POST',
				url: '<%=basePath%>relation/api/findPrevious',
				data:{
						"nId":nId,
						"nType":nType
					},
				dataType:'json',
				cache : false,
				error:function(){alert('系统连接失败，请稍后再试！')},
				success: function(obj){
					if(obj.success){
						e.innerText=obj.result.length;
					}
					
				}	  
			});	
		}
		function showQuery(){
			if($("#query").css("display")=="block"){
				$("#query").css("display","none");
			}else{
				$("#query").css("display","block");
			}
		}
		function downloadAll(){
		    var url=$("#pType").val()+"/excelAll";
		    window.open(url);
		}
		function downloadPage(){
		    var url=$("#pType").val()+"/excelPage";
		    window.open(url);
		}
		function toUpload(){
		    var url="http://localhost:8080/wsaf-web/basicCrud/projectPlan/toUpload";
		    window.open(url);
		}
    </script>
</head>

<body>
<div id="main-content">
	<div class="clear"></div>
	<div class="content-box">
		<div class="content-box-content">
			<form:form name="query" commandName="${entity.name}">
			<input type="hidden" name="pageNum" id="pageNum" value="${page.pageInfo.currentPage}"/>
			<input type="hidden" name="pageSize" id="pageSize" value="${page.pageInfo.pageSize}"/>
			<input type="hidden" name="sort" id="sort" value="id desc"/>
			<a href="javascript:void(0);" onclick="javascript:showQuery()">查询条件</a>
			<div id="query" style="display:none">
				<table>
				<c:forEach items="${entity.fieldProperties}" var="searchField" varStatus="s">
					<c:if test="${searchField.operate=='input'}">	
					<tr>
						<td>${searchField.name}:</td>
						<td><form:input path="${searchField.path}" class="text-input small-input"/></td>
					</tr>
					</c:if>
					<c:if test="${fn:startsWith(searchField.operate,'range')}">		
					<tr>
						<td>${searchField.name}:</td>
						<td><form:input path="${searchField.path}" class="text-input small-input"/></td>
					</tr>
					</c:if>
					
					<c:if test="${searchField.operate=='select'}">	
					<tr>
						<td>${searchField.name}:</td>
						<td><form:select path="${searchField.path}" items="${searchField.option}"/></td>
					</tr>
					</c:if>	
				</c:forEach>
				<tr>
					<td colspan="2"><input type="button" value="检索" onclick="javascript:submit();"/></td>
				</tr>
				
				</table>				
			</div>
			</form:form>
			<div class="content-box-header">
				<h3> ${entity.cnName}列表</h3>
<!-- 				<div class="clear"></div> -->
				<input type="button" value="导出数据" onclick="downloadPage()"/>
				<input type="button" value="导入Excel" onclick="toUpload()"/>
			</div>

			<c:if test="${hasErrors}">
			<div class="notification error png_bg">
				<a class="close" style="cursor: pointer;"><img src="images/icons/cross_grey_small.png"/></a>
				<div>
					<form:errors path="*" />
				</div>
			</div>
			</c:if>
			<c:set value="0" var="fieldNum"/>
			<table border="0">
				<thead>
				<tr>
					<th><img src="images/icons2/flag_16.png"/></th>
					<th>序号</th>
					<c:forEach items="${entity.fieldProperties}" var="recField" varStatus="st">
						<c:if test="${recField.operate!='hidden'}">	
							<c:set value="${fieldNum+1}" var="fieldNum"/>
							<th>${recField.name}</th>
						</c:if>
					</c:forEach>					
					<th>操作</th>
					<th>关联</th>
				</tr>
				</thead>
				<tbody>

				<c:forEach items="${page.content}" var="rec" varStatus="s">
				<tr>
					<td><input type="checkBox" name="id" value="${rec.id}"/></td>
					<td>${s.index+1+page.pageInfo.startRecord}</td>
					<c:forEach items="${entity.fieldProperties}" var="recField" varStatus="st">
				
					<c:if test="${recField.operate=='input'}">						
						<td>${rec[recField.path]}</td>											
					</c:if>		
					<c:if test="${fn:startsWith(recField.operate,'range')}">						
						<td>${rec[recField.path]}</td>											
					</c:if>	
					<c:if test="${recField.operate=='select'}">	
						<td>
						<c:forEach items="${recField.option}" var="op" varStatus="stt">
						
							<c:if test="${op.key==rec[recField.path]}">${ op.value}</c:if>
						</c:forEach>
						</td>
					</c:if>
					</c:forEach>
					<td>
						<a href="${entity.name}/edit/${rec.id}" title="编辑"><img src="images/icons2/pencil_16.png"/></a>
						<a href="${entity.name}/del/${rec.id}" title="删除"><img src="images/icons2/delete_16.png"/></a>
					</td>
					<td><span style="background-color:red"  onMouseOver="javascript:getLv1PreviousObjNum(this,'${rec.id}','${entity.name}');">0</span>
					<span style="background-color:green">0</span>
					<span style="background-color:blue">0</span></td>
				</tr>
				</c:forEach>
				</tbody>
				<tfoot>
					<tr>
					<td colspan="3">
					<!-- a href="${entityName}/new"><img src="images/icons2/plus_16.png"/></a-->
					<input type="button" value="新增" onclick="javascript:window.location.href='${entity.name}/new'"/>
					<input type="button" value="全选" onclick="allSelect(this)"/>
					<input type="button" value="反选" onclick="otherSelect()"/>
					<c:if test="${empty pId}">					
						<select id="nTypeSelector"></select><input type="button" value="绑定" onclick="bind()"/>
					</c:if>
					<c:if test="${!empty pId}">					
						<input type="button" value="确认绑定" onclick="confirmBind('${entity.name}')"/>
					</c:if>
					</td>
					<td colspan="${fieldNum+4-3}">					
					<div class="pagination">						
						<a href="javascript:void(0)" onclick="javascript:onSubmit(1)">&laquo; 第一页</a>
						<c:if test="${page.pageInfo.hasPrevious}">
								<a href="javascript:void(0)" onclick="javascript:onSubmit(${page.pageInfo.currentPage-1})" class="number current">上一页</a>
						</c:if>
						<c:forEach begin="1" end="${page.pageInfo.totalPages}" var="pageNumber">
							<c:if test="${page.pageInfo.currentPage == pageNumber}">
								<a href="javascript:void(0)" class="number current">${pageNumber}</a>
							</c:if>
							<c:if test="${page.pageInfo.currentPage != pageNumber}">
								<a href="javascript:void(0)" onclick="javascript:onSubmit(${pageNumber})" class="number">${pageNumber}</a>
							</c:if>
						</c:forEach>
						<c:if test="${page.pageInfo.hasNext}">
								<a href="javascript:void(0)" onclick="javascript:onSubmit(${page.pageInfo.currentPage+1})" class="number current">下一页</a>
						</c:if>
						<a href="javascript:void(0)" onclick="javascript:onSubmit(${page.pageInfo.totalPages})" title="Last Page">最后一页 &raquo;</a>
						<span> 共${page.pageInfo.totalPages}页/${page.pageInfo.totalRecord}条 </span>
						<span> 每页</span>
						<select id="pageSizeSelector" onchange="javascript:onSubmit(1)">
							<option value="1" <c:if test="${page.pageInfo.pageSize==1}">selected="selected"</c:if>>1</option>
							<option value="5" <c:if test="${page.pageInfo.pageSize==5}">selected="selected"</c:if>>5</option>
							<option value="10" <c:if test="${page.pageInfo.pageSize==10}">selected="selected"</c:if>>10</option>
							<option value="20" <c:if test="${page.pageInfo.pageSize==20}">selected="selected"</c:if>>20</option>
						</select><span>条</span>
					</div>
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
	<form name="binding" method="post" >
		<input type="hidden" name="pId" id="pId" value="${pId}"/>
		<input type="hidden" name="pType" id="pType" value="${pType}"/>
		<input type="hidden" name="ruleType" id="ruleType" value="${ruleType}"/>
	</form>
	
</div>
</body>
</html>
