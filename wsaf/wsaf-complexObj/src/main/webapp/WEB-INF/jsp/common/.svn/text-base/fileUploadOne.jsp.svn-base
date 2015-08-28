<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.io.OutputStream"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="<%=basePath%>" />
<title>FRAME-2222</title>

<link rel="stylesheet" href="css/reset.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/style.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/invalid.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/uploadify.css" type="text/css" />

<script src="js/jquery-1.7.1.min.js"></script>
<script src="js/jquery-ui-1.8.18.custom.min.js"></script>
<script src="js/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="js/me.js"></script>
<script type="text/javascript">
	
	</script>

<script type="text/javascript">
//关闭窗口
function shut(){
		  window.opener=null;
		  window.open("","_self");
		  window.close();
		}

$(function(){
	$("#uploadify").uploadify({
		'auto' : false,
	       'method' : "post",
	       'height' : 20,
	       'width' : 100,
	       'swf' : 'js/uploadify.swf', 
	       'uploader' : '<%=basePath%>/basicCrud/attach/uploadify',
	     'fileTypeDesc' : '格式:txt,xls，xlsx,doc,docx,rar,zip,jpg,png,pdf',		//描述
	       'fileTypeExts' : '*.txt;*.xls;*.xlsx;*.doc;*.docx;*.rar;*.zip;*.jpg;*.png;*.pdf',			//文件类型
	       'fileSizeLimit' : '30000KB',			//文件大小
	       'buttonText' : '选择文件',			//按钮名称
	       'fileObjName'	:'uploadify',
	       'multi' :true,
	       'successTimeout' : 5,
	       'requeueErrors' : false,
	       'removeTimeout' : 1,
	       'removeCompleted' : true,
       'onSelectError' : function(file,errorCode,errorMsg) {
       		if(errorCode==-110){
       			this.queueData.errorMsg = "文件太大，无法上传！";
       		}
        }, 
	       'onUploadStart' : function(file) {
	            $("#uploadify").uploadify("settings", "formData",{'attachMemo' : '附件1'});
	        },
        'onUploadSuccess' : function(file, data, response){
    	  	var attach = jQuery.parseJSON(data);
    		alert(attach);
    	  	alert(attach.filename);
//	       		var attach = eval('(' + data + ')');
       		$("#fileTable").show();
       		var addHtml = "<tr style='word-break:break-all; word-wrap:break-word;'>"+
       						"<td class='t_l' style='width:200px;'>"+
       							"<a href='<%=basePath%>/basicCrud/attach/downloadAttach?attachId="+attach.id+"'>"+attach.filename+"</a>"+
       						"</td>"+
       						"<td class='t_r'>"+attach.filesize+"</td>"+
       						"<td class='t_c'>"+attach.uploaddate+"</td>"+
       						"<td class='t_c'><a href='<%=basePath%>/basicCrud/attach/downloadAttach?attachId="+attach.id+"'>下载</a></td>"+
       						"<td class='t_c'><a href='#' id="+attach.id+" name='attach_id' onclick='removeFile(this); return false;'>取消</a></td>"+
       					  "</tr>";
       		$("#fileBody").find("tr").each(function(){
       			if($.trim($(this).find("td:first").text())==(attach.filename)){
       				var $target = $(this).find("td:last a");
       				$.ajax({
       					url			: 	"<%=basePath%>/basicCrud/attach/deleteAttach",
       					async: false,
       					dataType	: 	"json",
       					type		:	'post',
       					data: {
       						id:$target.attr("id")
       					},
       					error:function(XMLHttpRequest, textStatus, errorThrown){
       						alert(XMLHttpRequest.status);
       		                alert(XMLHttpRequest.readyState);
       		                alert(textStatus);
//       		 				alert("系统连接失败请稍后再试！");
       					},
       					success: function( data ) {
       						$target.parent().parent().remove();
       						var trLength = $("#fileTable").find("tr").length;
       						if(trLength==1){
       							$("#fileTable").hide();
       						}
       					}
       				});
       			}
       		});
       		$("#fileBody").append(addHtml);
       		$("#fileBody").show();
    	}
	});
});

function checkForm(){
	// 	检查第一个附件表单
	var attachIds ="";
	$("#fileBody").find("a[name=attach_id]").each(function(){
		attachIds += $(this).attr("id")+",";
	});
	
	if(attachIds!=null && attachIds!="" && attachIds.length>1){
		attachIds = attachIds.substring(0,attachIds.length-1);
	}
	$("#attachIds").val(attachIds);
	
	// 	检查第二个附件表单
	return true;
}

//删除上传的附件
function removeFile(paramObj){
	if(confirm("是否删除")){	
		var $target = $(paramObj);
		$.ajax({
			url			: 	"<%=basePath%>/basicCrud/attach/deleteAttach",
			async: false,
			dataType	: 	"json",
			type		:	'post',
			data: {
				id:$target.attr("id")
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
// 				alert("系统连接失败请稍后再试！");
			},
			success: function( data ) {
				$target.parent().parent().remove();
				var trLength = $("#fileTable").find("tr").length;
				if(trLength==1){
					$("#fileTable").hide();
				}
			}
		});
	}
}
</script>
</head>

<body id="${entity.cnName}-form">
	<div id="main-content">
		<div class="clear"></div>
		<div class="content-box">
			<div class="content-box-content">
				<div class="content-box-header">
					<h3>&nbsp;${entity.cnName}</h3>
					<div class="clear"></div>
				</div>
				
				<form:form action="basicCrud/${crudObj}/addAttach">
				<input type="hidden" name="attachIds" id="attachIds">
				<input type="hidden" name="crudObjId" id="crudObjId" value="${crudObjId}" >
					<table id="table1" border="0">
						<thead>
							<th colspan="5" class="t_r"><input type="button" value="关 闭"
								onclick="shut()" /> &nbsp;</th>
						</thead>
						<tbody>
			<tr>
				<td class="t_r lableTd">附件<br/>(上传最大为10Mb)</td>
				<td colspan="3">
					<input type="file" name="uploadify" id="uploadify" />
					<input type="button" value="上传" onclick="$('#uploadify').uploadify('upload','*');">
					<input type="button" value="取消" onclick="$('#uploadify').uploadify('stop');">
					<table id="fileTable">
					<c:choose>
						<c:when test="${attachList !=null}">
							<tbody style="width: 550px;border: solid;border-color: #D0D0D3;" id="fileBody">
								<tr style="border: solid;border: #D0D0D3;">
									<td width="200px;" class="t_c">文件名</td>
									<td width="100px;" class="t_c">大小(KB)</td>
									<td width="150px;" class="t_c">上传时间</td>
									<td width="100px;" class="t_c" colspan="2">操作</td>
								</tr>
								<c:forEach var="attach" items="${attachList}">
								 <c:choose>
									<c:when test="${attach.status!=null} && ${attach.status=='eam'}">
										<tr style="word-break:break-all; word-wrap:break-word;">
											<td class="t_l" style="width:200px;">
												<a href="javascript:void(0);" onclick="window.open(${attach.path})">
													${attach.filename}
												</a>
											</td>
											<td class="t_r">
												<c:choose>
												<c:when test="(attach.filesize/1024.0+'').substring((attach.filesize/1024.0+'').indexOf('.'),(attach.filesize/1024.0+'').length()).length()<=3">
													${attach.filesize}
												</c:when>
												<c:otherwise>
													${attach.filesize}
												</c:otherwise>
											 </c:choose>
											</td>
											<td class="t_c">${attach.uploaddate}</td>
											<td class="t_c">
												<a href="<%=basePath%>/basicCrud/attach/downloadAttach?attachId=${attach.id}">下载</a>
											</td>
											<td class="t_c">
												<a href="#" onclick="removeFile(this)" id="${attach.id}" name="attach_id">删除</a>
											</td>
										</tr>
									</c:when >
									<c:otherwise>
										<tr style="word-break:break-all; word-wrap:break-word;">
											<td class="t_l" style="width:200px;">
												<a href="<%=basePath%>/basicCrud/attach/downloadAttach?attachId=${attach.id}">
													${attach.filename}
												</a>
											</td>
											<td class="t_r">
												<c:choose>
												<c:when test="(attach.filesize/1024.0+'').substring((attach.filesize/1024.0+'').indexOf('.'),(attach.filesize/1024.0+'').length()).length()<=3">
													${attach.filesize}
												</c:when>
												<c:otherwise>
													${attach.filesize}
												</c:otherwise>
											 </c:choose>
											</td>
											<td class="t_c">${attach.uploaddate}</td>
											<td class="t_c">
												<a href="<%=basePath%>/basicCrud/attach/downloadAttach?attachId=${attach.id}">下载</a>
											</td>
											<td class="t_c">
												<a href="#"  id="${attach.id}" name="attach_id" onclick="removeFile(this); return false">删除</a>
											</td>
										</tr>
									</c:otherwise>
									</c:choose>
								</c:forEach>
							</tbody>
						</c:when>
						<c:otherwise>
							<tbody style="width: 550px;border: solid;border-color: #D0D0D3;display: none;" id="fileBody">
								<tr style="border: solid;border: #D0D0D3;">
									<td width="200px;" class="t_c">文件名</td>
									<td width="100px;" class="t_c">大小(KB)</td>
									<td width="150px;" class="t_c">上传时间</td>
									<td width="100px;" class="t_c" colspan="2">操作</td>
								</tr>
							</tbody>
						</c:otherwise>
						 </c:choose>
					</table>
				</td>
			</tr>
			
			
						</tbody>
<!-- 						<tr class="tfoot"> -->
<!-- 							<td colspan="4" class="t_r"><input type="button" value="关 闭" -->
<!-- 								onclick="shut()" /></td> -->
<!-- 						</tr> -->
						<tr class="tfoot">
							<td colspan="6" class="t_r">
								<input type="submit" value="确 认" onclick="return checkForm();"/>&nbsp; 
								<input type="button" value="关 闭" onclick="shut()"/> &nbsp; 
							</td>
						</tr>
						</form:form>
					</table>
			</div>
		</div>
		<div class="clear"></div>
		<div id="footer">
			<small> Powered by <a href="#" target="_blank">lsf</a> | <a
				href="mailto:mengjie@wondersgroup.com">联系我</a>
			</small>
		</div>
	</div>
</body>

</html>
