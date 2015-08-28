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

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
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
	       'multi' :false,
	       'successTimeout' : 5,
	       'requeueErrors' : false,
	       'removeTimeout' : 1,
	       'removeCompleted' : true,
       'onSelectError' : function(file,errorCode,errorMsg) {
       		if(errorCode==-110){
       			this.queueData.errorMsg = "文件太大，无法上传！";
       		}
        }, 
       'onUploadSuccess' : function(file, data, response){
    	   var attach = jQuery.parseJSON(data);
       		$("#fileTable").show();
       		var addHtml = "<a href='<%=basePath%>/attach/downloadAttach.action?attachId="+attach.id+"'>"+attach.filename+"."+attach.fileextname+"</a>";				
       		$("#showTR").show();
       		$("#showId").html(addHtml);
    	}
	});
});

function downloadTemplete(){
	window.location.href='<%=basePath%>/contract/downloadFile.action?fileName=ContractImportTemplate.xls';
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

					<table id="table1" border="0">
						<thead>
							<th colspan="5" class="t_r"><input type="button" value="关 闭"
								onclick="shut()" /> &nbsp;</th>
						</thead>
						<tbody>
							<tr>
								<td class="t_r lableTd" width="200px;">上传文件<br /> 
<!-- 								<a href='javascript:void(0);' onclick="downloadTemplete();">下载模板</a> -->
								</td>
								<td colspan="2"><input type="file" name="uploadify"
									id="uploadify" /> <input type="button" value="上传"
									onclick="$('#uploadify').uploadify('upload','*');"> <input
									type="button" value="取消"
									onclick="$('#uploadify').uploadify('stop');"></td>
							</tr>
							<tr id="showTR" style="display: none;">
								<td class="t_r">下载反馈文件</td>
								<td id="showId"></td>
							</tr>
						</tbody>
						<tr class="tfoot">
							<td colspan="4" class="t_r"><input type="button" value="关 闭"
								onclick="shut()" /></td>
						</tr>
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
