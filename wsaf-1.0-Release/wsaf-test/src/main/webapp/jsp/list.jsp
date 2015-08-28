<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String entity=(String)request.getAttribute("entity");	
%>

<!DOCTYPE html>

<html>
	<head>
		<base href="<%=basePath%>">

		<title>列表</title>
		<meta charset="utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" href="css/formalize.css" />
		<link rel="stylesheet" href="css/page.css" />
		<link rel="stylesheet" href="css/default/imgs.css" />
		<link rel="stylesheet" href="css/reset.css" />
		<link type="text/css" href="css/flick/jquery-ui-1.8.18.custom.css"
			rel="stylesheet" />

		<script src="js/html5.js"></script>
		<script src="js/jquery-1.7.1.min.js"></script>
		<script src="js/jquery-ui-1.8.18.custom.min.js"></script>
		<script src="js/jquery.ui.datepicker-zh-CN.js"></script>
		<script src="js/jquery.formalize.js"></script>
		<style>
.ui-datepicker-title span {
	display: inline;
}

button.ui-datepicker-current {
	display: none;
}
</style>
		<script type="text/javascript">
		var fields=[];
		var pageSize=10;
		var	pageCurrent=1;
		var entity="<%=entity%>";
        $(function () {
        	$("#add").click(function(){
				window.open("./page/form?entity="+entity);
			});
			getProperty();
			bindData(pageCurrent);
        });
        
        //获取对象属性
        function getProperty(){
        	$.ajax({            
	            url: './api/'+entity+'/info' ,
	            type: 'GET',
	            async: false,
	            dataType:'json',
	            success: function (rto) {
	                if(rto.info.success==true){
	                
	                	setGridHead(rto.data);
	                	
	                	setForm(rto.data);
	                	
	                }else{
	                	alert("the api has error:"+rto.info.error);
	                }
	
	            },
	            error: function () {
	                alert("sorry,getProperty fail");
	            }
	        });
        }
        //绑定数据
        function bindData(num){
        	
        	$.ajax({            
	            url: './api/'+entity ,
	            type: 'GET',
	            //async: false,
	            cache:false,
	            dataType:'json',
	            //contentType: "application/json;charset=utf-8",
	            //data: {'page':num+','+pageSize},
	            data: 'page='+num+','+pageSize+'&'+$("#form").serialize(),
	            success: function (rto) {
	                if(rto.info.success==true){	   
	                             
	                	setGridData(rto.data.content,rto.data.pageInfo.startRecord);
	                	
	                	
	                	
	                	setPageInfo(rto.data.pageInfo,rto.data.content.length);
	                	
	                	setPageBtn(rto.data.pageInfo);

	                }else{
	                	alert("the api has error:"+rto.info.error);
	                }
	
	            },
	            error: function () {
	                alert("sorry,findBypage fail");
	            }
	        });
        	
        }
        
         function bindDataByHql(num){
        	
        	$.ajax({            
	            url: './api/'+entity ,
	            type: 'GET',
	            //async: false,
	            dataType:'json',
	            //contentType: "application/json;charset=utf-8",
	            //data: {'page':num+','+pageSize},
	            data: 'page='+num+','+pageSize+'&'+$("#form").serialize()+'&hql= and (o.str like:str_l or o.str is null) and (o.clob like:clob_el or o.clob is null) ',
	            success: function (rto) {
	                if(rto.info.success==true){	   
	                             
	                	setGridData(rto.data.content,rto.data.pageInfo.startRecord);
	                	
	                	
	                	
	                	setPageInfo(rto.data.pageInfo,rto.data.content.length);
	                	
	                	setPageBtn(rto.data.pageInfo);

	                }else{
	                	alert("the api has error:"+rto.info.error);
	                }
	
	            },
	            error: function () {
	                alert("sorry,findBypage fail");
	            }
	        });
        	
        }
        //打开编辑页面
        function edit(id){
			window.open("./page/form?entity="+entity+"&id="+id);
		};
        
        //逻辑删除数据
        function del(id){
        	$.ajax({            
	            url: './api/'+entity+'/'+id ,
	            type: 'delete',
	           // async: false,
	            dataType:'json',
	            //contentType: "application/json; charset=utf-8",
	            //data:'{}', 
	            success: function (rto) {
	                if(rto.info.success==true){	   
	                             
	                	alert("操作成功");
	                	
	                	bindData(pageCurrent);

	                }else{
	                	alert("the api has error:"+rto.info.error);
	                }
	
	            },
	            error: function () {
	                alert("sorry,findBypage fail");
	            }
	        });
        	
        }
        //添加复杂对象
        function multiplePage(){
        	window.open("./page/multiple");
        	
        }
        
        //设置表格头文字
        function setGridHead(entity){
           	$("a.fl").html(entity.cnName);

           	var h='<thead><tr class="tit"><td class="sort">序号</td>';
            	
           	$.each(entity.fieldProperties, function(i, field) {
           		if(field.operate=="hidden"){
           			return true;//相当于continue
           		}
           		h+='<td class="sort">'+field.name+'</td>';
           		fields.push(field.path);
           	});
           	h+='<td class="sort">操作</td></tr></thead>';
           	$(".table_1").append(h);                
        }
        
        //设置表格数据内容
		function setGridData(records,start){
           	var b='<tbody>'
           	$.each(records, function(i, record) {
	             		
           		b+='<tr><td>'+(i+1+start)+'</td>';
           		
           		$.each(fields,function(j,path){
           		
           			var fieldValue=record[path];
           			if(fieldValue==null){fieldValue="";}
           				b+='<td>'+fieldValue+'</td>';
           			});
           			                		
           		b+='<td><input type="button" id="edit" value="编辑" onclick="javascript:edit('+record.id+')">';
           		b+='<input type="button" id="del" value="删除" onclick="javascript:del('+record.id+')"></td></tr>';
           	});
           	b+='</tbody>';
           	$(".table_1").children("tbody").remove();
           	$(".table_1").append(b);
		}

		//设置分页信息
		function setPageInfo(pageInfo,rowCount){
			currentPage=pageInfo.currentPage;
            var rowStart=parseInt(pageInfo.startRecord)+1;
            var rowEnd=parseInt(pageInfo.startRecord)+rowCount;
            	
            var f1='共' + pageInfo.totalRecord + '条记录，当前显示'+rowStart+'-' + rowEnd + '条';
            	
            $(".tfoot span:first-child").text(f1);
            	
            var f2='<input type="button" value="|<" id="first">&nbsp;';
            if(pageInfo.hasPrevious){            
            	f2+='<input type="button" value="<" id="previous">&nbsp;';
            }
            if(pageInfo.hasNext){
            	f2+='<input type="button" value=">" id="next">&nbsp;';	
            }
			f2+='<input type="button" value=">|" id="last">&nbsp;';
            f2+='<input type="number" id="pageNum" step="1" min="1" max="'+pageInfo.totalPages+'" value="'+pageInfo.currentPage+'"><input type="button" value="go" id="goPage">&nbsp;&nbsp;';
            
            $(".tfoot span.pager").children().remove();
            $(".tfoot span.pager").append(f2);
            
		}
		//设置翻页按钮响应事件
		function setPageBtn(pageInfo){
			$("#first").click(function(){
				if(pageInfo.currentPage!=1){
					bindData(1);
				}else{
					alert("当前页面为首页");
				}
			});
			
			$("#previous").click(function(){
				bindData(parseInt(pageInfo.currentPage)-1);
			});
			
			$("#next").click(function(){
				bindData(parseInt(pageInfo.currentPage)+1);
			});
			
			$("#last").click(function(){
				if(pageInfo.currentPage!=pageInfo.totalPages){
					bindData(pageInfo.totalPages);
				}else{
					alert("当前页面为末页");
				}
			});
			
			$("#goPage").click(function(){
				if($("#pageNum").val()!=pageInfo.currentPage){
					bindData($("#pageNum").val());
				}else{
					alert("当前页面为第"+pageInfo.currentPage+"页");
				}
			});
		}
		
		 function setForm(entity){

           	var h='';
            	
           	$.each(entity.fieldProperties, function(i, field) {
           		
           		if(field.operate=="hidden"){
           			return true;
           		}else{
           			h+='<tr><td class="lableTd t_r">'+field.name+'</td>';
           			if(field.operate=="select"){
           				h+='<td><select name="'+field.path+'" class="input_large">';
           				for(var key in field.option){
           					h+='<option value="'+key+'">'+field.option[key]+'</option>';
           				}
           				h+='</select"></td>';
           			}else if(field.path=="num"||field.path=="date"||field.path=="datetime"){
           				h+='<td><input type="text" name="'+field.path+'_s" class="input_large" maxlength="30">至<input type="text" name="'+field.path+'_e" class="input_large" maxlength="30"></td>';
           			}else if(field.path=="str"){
           				h+='<td><input type="text" name="'+field.path+'_l" class="input_large" maxlength="30"></td>';
           			}else if(field.path=="clob"){
           				h+='<td><input type="text" name="'+field.path+'_el" class="input_large" maxlength="30"></td>';
           			}else{
           				h+='<td><input type="text" name="'+field.path+'" class="input_large" maxlength="30"></td>';
           			}
           			h+='</tr>';
           		}
           		
           	});
           	
           	
           	$("table.nwarp").append(h);    
           	
           	$("#searchbtn").click(function(){
           		bindData(1);
           	});  
           	
           	$("#searchHqlbtn").click(function(){
           		bindDataByHql(1);
           	});  
           	$("#searchJsonbtn").click(function(){
           		bindDataByJson(1);
           	});   
           	$("#removeAllbtn").click(function(){
           		removeAll();
           	});   
           	
           	$("#multiple").click(function(){
           		multiplePage();
           	});   
           	      
        }
    </script>
	</head>

	<body class="Flow">
		<div class="main">
			<div class="ctrl clearfix nwarp">
				<div class="fl">
					<img src="css/default/images/sideBar_arrow_left.jpg" width="46"
						height="30" alt="收起">
				</div>
				<div class="posi fl nwarp">
					<ul>
						<li>
							<a>列表</a>
						</li>

					</ul>
				</div>
			</div>

			<div class="pt45">

				<div class="filter">
					<div class="query">
						<div class="filter_search p8">
							<!--这里根据内容做样式调整。1、筛选：filter_sandglass 2、搜索filter_search 3、提示：.filter_tips 背景图会改变。-->
							<form id="form">
								<table id="table_2" class="nwarp" width="100%" border="0" cellspacing="0"
									cellpadding="0">


									

								</table>
								<div class="mb10 t_c">
									<input type="button" id="searchbtn" value="查 询" />
									<input type="button" id="searchHqlbtn" value="HQL查 询" />
									<input type="button" id="searchJsonbtn" value="JSON查 询" />
									<input type="button" id="multiple" value="复杂对象" />
<!--									<input type="button" id="removeAllbtn" value="批量删除" />-->
									
									&nbsp;
									<input type=button id="renewbtn" value="重 置" />
								</div>
							</form>
						</div>
					</div>
					<div class="fn clearfix">
						<h5 class="fl">
							<a class="fl"></a>
						</h5>
						<input class="fr" type="button" id="add" value="新 增">
					</div>
				</div>
				<!--Filter End-->
				<!--Table-->
				<div class="mb10">
					<table width="100%" class="table_1">
						<!--						<col width="4%">-->
						<!--						<col width="8%">-->
						<!--						<col width="8%">-->
						<!--						<col width="8%">-->
						<!--						<col width="15%">-->
						<!--						<col width="23%">-->
						<!--						<col width="5%">-->
						<!--						<col width="5%">-->
						<!--						<col width="12%">-->
						<!--						<col width="12%">-->

						<tfoot>
							<tr class="tfoot">
								<td colspan="10">
									<div class="clearfix">
										<span class="fl"></span>
										<span class="fr clearfix pager"></span>
									</div>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
				<!--Table End-->
			</div>
		</div>
	</body>
</html>

