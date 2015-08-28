<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String entity=(String)request.getAttribute("entity");
    String id=(String)request.getParameter("id");   
    if(id==null){id="";} 
    String title="编辑";
    if(id.equals("")){
    	title="新增";
    }
%>

<!DOCTYPE html>

<html>
<head>
    <base href="<%=basePath%>">

    <title><%=title%></title>
    <meta charset="utf-8"/>
    <meta http-equiv="x-ua-compatible" content="IE=8">

    <link rel="stylesheet" href="css/formalize.css"/>
    <link rel="stylesheet" href="css/page.css"/>
    <link rel="stylesheet" href="css/default/imgs.css"/>
    <link rel="stylesheet" href="css/reset.css"/>
    <link type="text/css" href="css/flick/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>
    <script src="js/html5.js"></script>
    <script src="js/jquery-1.7.1.min.js"></script>
    <script src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <script src="js/jquery.ui.datepicker-zh-CN.js"></script>
    <script src="js/jquery.formalize.js"></script>
    <style>
        .red {
            display: inline;
            color: red;
        }

        .w90 {
            width: 90%;
        }
    </style>
    <script type="text/javascript">
		var entity="<%=entity%>";
		var id="<%=id%>";
        $(function () {
			buildForm();
			if(id!=""){
				initData();
			}
			
			$("#btnSubmit").click(function(){
				
				saveOrUpdate();

			});
			//bindData(pageCurrent);
        });
        
        //获取对象属性
        function buildForm(){
        	$.ajax({            
	            url: './api/'+entity+'/info' ,
	            type: 'GET',
	            async: false,
	            dataType:'json',
	            success: function (rto) {
	                if(rto.info.success==true){
	                
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
        
        function initData(){
        	$.ajax({            
	            url: './api/'+entity+'/'+id ,
	            type: 'GET',
	            async: false,
	            dataType:'json',
	            success: function (rto) {
	                if(rto.info.success==true){
	                
	                	for(var path in rto.data){
	                		var v=rto.data[path];
	                		if(v==null){v="";}
	                		$("#"+path).val(v);
	                	}
	                	
	                }else{
	                	alert("the api has error:"+rto.info.error);
	                }
	
	            },
	            error: function () {
	                alert("sorry,getProperty fail");
	            }
	        });
        }
        function saveOrUpdate(){
        //alert($("#form").serialize());
        	$.ajax({  
        		type: 'POST',        
	            url: './api/'+entity ,
	            //url: './'+entity ,
	            //async: false,
	            data:$("#form").serialize(),
	            dataType:'json',
	            success: function (rto) {
	            alert(JSON.stringify(rto));
	                if(rto.info.success==true){
	                
	                	alert("操作成功");
	                	
	                	location.href = './page/form?entity='+entity+'&id=' + rto.data.id;
	                }else{
	                	alert("the api has error:"+rto.info.error);
	                }
	
	            },
	            error: function () {
	                alert("sorry,getProperty fail");
	            }
	        });
        }
        
        
         function setForm(entity){
           	$("h5.fl").html(entity.cnName+"基本信息"+"<%=title%>");

           	var h='';
            	
           	$.each(entity.fieldProperties, function(i, field) {
           		if(field.operate=="hidden"){
           			if(field.path=="removed"){
           				$("#form").append('<input type="hidden" id="'+field.path+'" name="'+field.path+'" value="0">');
           			}else{
           				$("#form").append('<input type="hidden" id="'+field.path+'" name="'+field.path+'">');
           			}
           			return true;
           		}else{
           			h+='<tr><td class="lableTd t_r">'+field.name+'</td>';
           			if(field.operate=="select"){
           				h+='<td><select id="'+field.path+'"  name="'+field.path+'" class="input_large">';
           				for(var key in field.option){
           					h+='<option value="'+key+'">'+field.option[key]+'</option>';
           				}
           				h+='</select"></td>';
           			}else{
           				h+='<td><input type="text" id="'+field.path+'"  name="'+field.path+'" class="input_large" maxlength="30"></td>';
           			}
           			h+='</tr>';
           		}
           		
           	});
           	$(".table_2").append(h);                
        }
        
    </script>
</head>

<body class="Flow">

<div class="f_bg">

    <div class="logo_1"></div>

    <div class="gray_bg">

        <div class="gray_bg2">

            <div class="w_bg">

                <div class="Bottom">

                    <div class="Top" style="min-height: 0px;">

                        <h1 class="t_c" id="title"></h1>

                        <div class="mb10">

                            <form id="form">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
                                    <thead>
                                    <td colspan="2" class="f1"><h5 class="fl">基本信息</h5></td>
                                    </thead>
                                </table>
                            </form>
                        </div>
                        <div class="mb10 t_c">
                            <input type="button" id="btnSubmit" value="保存"/>
                            &nbsp;
                            <input type="button" id="btnClose" value="关闭"/>
                            &nbsp; </div>
                        <div class="footer"></div>
                    </div>

                </div>

            </div>

        </div>

    </div>
</div>
</body>
</html>
