<%@ page contentType="text/html; charset=utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>欢迎</title>
		<%
			String basePath = request.getContextPath();
		 %>
	</head>
	<body>
		<div>
		<ul >
			<li>合同管理</li>
			<li>开发人：姚成龙</li>
			<li>版本号：</li>
			<li>发布时间：</li>
			<li>合同封面：<a href="<%=basePath %>/contract/showContractCover.action" target="_blank">[合同封面]</a><%=basePath %>/contract/showContractCover.action</li>
			<li>列表页面：<a href="<%=basePath %>/contract/findContractByPage.action" target="_blank">[合同管理]</a><%=basePath %>/contract/findContractByPage.action</li>
			<li>合同导入-客户定制版，合同编号相同不更新数据：<a href="<%=basePath %>/contract/showBatchUploadCustomized.action" target="_blank">[批量上传合同]</a><%=basePath %>/contract/findContractByPage.action</li>
			<li>合同实际支付情况批量导入：<a href="<%=basePath %>/contractStatus/showBatchUpload.action" target="_blank">[批量上传实际支付情况]</a><%=basePath %>/contract/findContractByPage.action</li>
		</ul>
	</div>
	</body>
</html>