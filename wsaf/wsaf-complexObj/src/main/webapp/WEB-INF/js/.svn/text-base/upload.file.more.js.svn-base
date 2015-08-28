//关闭窗口
function shut(){
		  window.opener=null;
		  window.open("","_self");
		  window.close();
		}

$(function(){
	$("#uploadify_one").uploadify({
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
        'onUploadSuccess' : function(file, data, response){
    	  	var attach = jQuery.parseJSON(data);
    		alert(attach);
    	  	alert(attach.filename);
//	       		var attach = eval('(' + data + ')');
       		$("#fileTable_one").show();
       		var addHtml = "<tr style='word-break:break-all; word-wrap:break-word;'>"+
       						"<td class='t_l' style='width:200px;'>"+
       							"<a href='<%=basePath%>/basicCrud/attach/downloadAttach?attachId="+attach.id+"'>"+attach.filename+"</a>"+
       						"</td>"+
       						"<td class='t_r'>"+attach.filesize+"</td>"+
       						"<td class='t_c'>"+attach.uploaddate+"</td>"+
       						"<td class='t_c'><a href='<%=basePath%>/basicCrud/attach/downloadAttach?attachId="+attach.id+"'>下载</a></td>"+
       						"<td class='t_c'><a href='#' id="+attach.id+" name='attach_id_one' onclick='removeFile_one(this); return false;'>取消</a></td>"+
       					  "</tr>";
       		$("#fileBody_one").find("tr").each(function(){
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
       						var trLength = $("#fileTable_one").find("tr").length;
       						if(trLength==1){
       							$("#fileTable_one").hide();
       						}
       					}
       				});
       			}
       		});
       		$("#fileBody_one").append(addHtml);
       		$("#fileBody_one").show();
    	}
	});
});

$(function(){
	$("#uploadify_two").uploadify({
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
        'onUploadSuccess' : function(file, data, response){
    	  	var attach = jQuery.parseJSON(data);
    		alert(attach);
    	  	alert(attach.filename);
//	       		var attach = eval('(' + data + ')');
       		$("#fileTable_two").show();
       		var addHtml = "<tr style='word-break:break-all; word-wrap:break-word;'>"+
       						"<td class='t_l' style='width:200px;'>"+
       							"<a href='<%=basePath%>/basicCrud/attach/downloadAttach?attachId="+attach.id+"'>"+attach.filename+"</a>"+
       						"</td>"+
       						"<td class='t_r'>"+attach.filesize+"</td>"+
       						"<td class='t_c'>"+attach.uploaddate+"</td>"+
       						"<td class='t_c'><a href='<%=basePath%>/basicCrud/attach/downloadAttach?attachId="+attach.id+"'>下载</a></td>"+
       						"<td class='t_c'><a href='#' id="+attach.id+" name='attach_id_two' onclick='removeFile_two(this); return false;'>取消</a></td>"+
       					  "</tr>";
       		$("#fileBody_two").find("tr").each(function(){
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
       						var trLength = $("#fileTable_two").find("tr").length;
       						if(trLength==1){
       							$("#fileTable_two").hide();
       						}
       					}
       				});
       			}
       		});
       		$("#fileBody_two").append(addHtml);
       		$("#fileBody_two").show();
    	}
	});
});

function checkForm(){
	// 	检查第一个附件表单
	var attachIds_one ="";
	$("#fileBody_one").find("a[name=attach_id_one]").each(function(){
		attachIds_one += $(this).attr("id")+",";
	});
	
	if(attachIds_one!=null && attachIds_one!="" && attachIds_one.length>1){
		attachIds_one = attachIds_one.substring(0,attachIds_one.length-1);
	}
	$("#attachIds_one").val(attachIds_one);
	
	// 	检查第二个附件表单
	var attachIds_two ="";
	$("#fileBody_two").find("a[name=attach_id_two]").each(function(){
		attachIds_two += $(this).attr("id")+",";
	});
	
	if(attachIds_two!=null && attachIds_two!="" && attachIds_two.length>1){
		attachIds_two = attachIds_two.substring(0,attachIds_two.length-1);
	}
	$("#attachIds_two").val(attachIds_two);
	
	return true;
}

//删除上传的附件1
function removeFile_one(paramObj){
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
				var trLength = $("#fileTable_one").find("tr").length;
				if(trLength==1){
					$("#fileTable)_one").hide();
				}
			}
		});
	}
}
//删除上传的附件2
function removeFile_two(paramObj){
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
				var trLength = $("#fileTable_two").find("tr").length;
				if(trLength==1){
					$("#fileTable_two").hide();
				}
			}
		});
	}
}