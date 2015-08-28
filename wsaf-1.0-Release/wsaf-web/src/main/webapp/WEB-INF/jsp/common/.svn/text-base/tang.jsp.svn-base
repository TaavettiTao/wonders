<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><tiles:insertAttribute name="title" /></title>

<link rel="stylesheet" type="text/css" href="css/screen.css">
<script type="text/javascript"
	src="js/jquery-ui-1.11.1.custom/external/jquery/jquery.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/defaultJs.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/jquery-ui-1.11.1.custom/jquery-ui.css" />
<script type="text/javascript"
	src="js/jquery-ui-1.11.1.custom/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/jquery-ui-1.11.1.custom/jquery-ui.theme.min.css" />
<script type="text/javascript" src="js/modernizr.custom.54158.js"></script>
<script type="text/javascript">
	$(function() {
		$(".v-menu li").draggable({
			revert : true
		});

		$("#pveast").droppable({
			  accept: ".v-menu li",
		      drop: function( event, ui ) 
			  {
					
		    	var modname=$.trim(ui.draggable.attr("class").replace("ui-draggable ui-draggable-handle ui-draggable-dragging",""));

		    	if($("#"+modname).length>0)
		    	{	alert("当前模块已存在");
		    		return false;
		    	}
		    	
				var _strTable="<div class=\"userMods fl\" id=\""+modname+"\"><div class=\"userMod\"><div id=\""+modname+"Dialog\" style=\"display:none\"><form id=\""+modname+"form\"></form></div><div><ul style=\"display:none\" class=\"s_menu\"><li class=\"delmod\">删除</li></ul><h1 class=\"fl\"></h1>";
			         _strTable+="<div class=\"modName\" style=\"display:none\"></div><a class=\"size fr\">展开</a><span class=\"fr\">";
					_strTable+="<input class=\"searchs\" type=\"text\" name=\"s3\" placeholder=\"Search....\" onClick=\"this.focus();this.select();\" />";
					_strTable+="</span> <span class=\"rdi fr\"> <input type=\"radio\" id=\"rdoTable"+modname+"\" name=\"rdoRole\" checked=\"checked\">"; 
					_strTable+="<label  for=\"rdoTable"+modname+"\" class=\"rdoTable\"></label>"; 
					_strTable+="<input type=\"radio\" id=\"rdoThree"+modname+"\" name=\"rdoRole\"><label for=\"rdoThree"+modname+"\" class=\"rdoThree iconShow\">";
					_strTable+="</label></span></div><div class=\"clearfix\"></div>";
				    _strTable+="<table class=\"commtable\" id=\"alternatecolor\" ><thead>";
					_strTable+="<tr style=\"background-color:#e4e2d7;\"> <th style=\"width:4%\"><input type=\"checkbox\" value=\"checkbox1\" name=\"checkAll\"></th>";
					_strTable+="<th class=\"tableItem\">noitem</th><th class=\"tableItem\"></th></tr></thead><tbody></tbody><tfoot>";
					_strTable+="<tr style=\"background-color:#e4e2d7\";><th colspan=\"2\" class=\"firth\" style=\"border:none\"><button class=\"btnIcon btnPlus\"> ";
					_strTable+="</button><button class=\"btnIconb btnMin\"></button></th><th class=\"nexth\" style=\"border:none\" class=\"table-bottom\">";
					_strTable+="<span class=\"fr mr5\"><select name=\"number\" id=\"number\"><option selected=\"selected\">5</option><option>10</option>";
					_strTable+="<option>15</option></select></span><button class=\"btn-just-Icon-b btnTolast fr\"></button>";
					_strTable+="<button class=\"btn-just-Icon-b btnNext fr\"></button> <span class=\"fr pt5\"><div class=\"slider\"></div></span>";
					_strTable+="<button class=\"btn-just-Icon-b btnPrev fr\"></button><button class=\"btn-just-Icon-b btnTotop fr\"></button>"; 
					_strTable+="<span class=\"fr pt5 pageNow\">1/2000(1000items)</span></th></tr></tfoot></table></div></div>";
					
					$("#veast").append(_strTable);
					
					 $( "table>tbody" ).draggable({ 
						 revert : true,
						 opacity: 0.7, helper: "clone",
						 handle: "td" });
					 
					 $( "table>tbody" ).droppable({
						  accept: "table>tbody",
						  over :function( event, ui ) {
					        $( this ).css("border","2px solid #ffac7f");
					      },
					      out: function( event, ui ) {
							        $( this ).css("border","1px solid #c4c1ac");
							      },
					      drop: function( event, ui ) {
					        $( this ).css("border","1px solid #c4c1ac");
					        var $thismod= $( this );
					        $( "#dialog" ).dialog({
					            resizable: false,
					            height:140,
					            width:100,
					            modal: true,
					            buttons: {
					              "确定": function() {
					            	  var nmodname=ui.draggable.parents(".userMods").attr("id"); 
					            	  var pmodname=$thismod.parents(".userMods").attr("id"); 
					            	$.saveBind("${pageContext.request.contextPath}/relation/api/saveBind","#"+pmodname,"#"+nmodname);
					            		
					                $( this ).dialog( "close" );
					              },
					              "取消": function() {
					                $( this ).dialog( "close" );
					              }
					            }
					          });
					        
					      }
					    });
										
					$("#"+modname+" .rdi").buttonset();
					$("#"+modname+" .rdoThree").removeClass("ui-widget").removeClass("ui-state-default");

					$("#"+modname+" .rdoTable").click(
							function() {
								$(this).addClass("ui-widget").addClass("ui-state-default");
								$(this).next().next().removeClass("ui-widget").removeClass(
										"ui-state-default").addClass("iconShow");
							});

					$("#"+modname+" .rdoThree").click(
							function() {
								$(this).addClass("ui-widget").addClass("ui-state-default");
								$(this).prev().prev().removeClass("ui-widget").removeClass(
										"ui-state-default").addClass("iconShow");
							});
					
					 $(".userMods").css("width","50%");
					 $(".size").text("展开");
					 
					 if ($("#veast>div").length % 2 != 0) 
					 {
						 $("#"+modname).css("width","100%");
						 $("#"+modname+" .size").text("收缩");
					 }
				
						$("#"+modname+" .size").click(function(){

							var _thismod=$(this).parents(".userMods").attr("id");

							if($(this).text()=="展开")
							{
							   $("#"+_thismod).css("width","100%");
							   $(this).text("收缩");
							}
							else
							{
								 $("#"+_thismod).css("width","50%");
								 $(this).text("展开");
							}
			
							$(".v-menu").css("height", $(document).height());
							
						});
					 
					$.getTable("${pageContext.request.contextPath}/"+modname,"#"+modname);
					
					$("#veast").sortable({
						stop: function(event, ui) { 
							$(".v-menu").css("height", $(document).height());
						}
					}).disableSelection();
					
					$.getSearchForm("${pageContext.request.contextPath}/",modname);
					
                    $("#"+modname+" .searchs").click(function(){
                    	$("#"+modname+"Dialog" ).dialog({
                    		 width:270,
                    		title:"查询",
                    		 modal: true,
                    		 buttons: {
					              "查询": function() {
					            	  var seleBtns=$("#"+modname).find("[name='number']");
					            		$.postSearchForm("${pageContext.request.contextPath}/"+modname,"#"+modname,"&pageSize="+seleBtns.val()+"&pageNum=1");
					            	  $( this ).dialog( "close" );
					              },
					              "取消": function() {
					                $( this ).dialog( "close" );
					              }
					            }
                    	     });
                          });
                    $("#"+modname+" h1").click(function(){ 
                    	$("#"+modname+" .s_menu").css("display","block");
                    });
                    $("#"+modname+" .s_menu").mouseleave(function(){ 
                    	$("#"+modname+" .s_menu").css("display","none");
                    });
                    
                    $("#"+modname+" .delmod").click(function(){ 
                    	$("#"+modname).remove();
                    });
		      } 
		    });

		var height = $(window).height();
		$(".v-menu").css("height", height);
		$("#pveast").css("height", height - 104);

		$(".slider").slider();

		$("#radio").buttonset();
		$("#radio input[name='radio']").click(function(){
			$.setRow();
		});
	});
</script>
</head>
<body id="default">
<div id="dialog" style="display:none">
  <p style="margin-left:5px;">是否绑定数据.</p>
</div>
	<form>
		<div class="v-center">
			<div class="v-menu">
				<ul>
					<li class="user" draggable="true"><a href="#">用户</a></li>
					<li class="role" draggable="true"><a href="#">角色</a></li>
					<li class="privilege" draggable="true"><a href="#">权限</a></li>
					<li class="organ" draggable="true"><a href="#">部门</a></li>
				</ul>
			</div>
			<div class="v-main">
				<div class="v-top">
					<div id="radio" class="fl">
						<input type="radio" id="rdoFir" name="radio" checked="checked">
						<label for="rdoFir" style="color:#c7188b">Level 1</label> <input
							type="radio" id="rdoSen" name="radio"> <label
							for="rdoSen" style="color:#ff8b19">Level 2</label> <input
							type="radio" id="rdoThi" name="radio"> <label
							for="rdoThi" style="color:#885df3">Level 3</label>
					</div>
					<div class="topmenu fr">
						<span class="searchbox"> <input type="text" name="s"
							value="" placeholder="Search...."/ >
						</span> <span class="icon"> <input type="button" class="msgicon"
							value="" />
						</span> <span class="icon"> <input type="button" class="favicon"
							value="" />
						</span> <span class="icon"> <input type="button" class="cfgicon"
							value="" />
						</span> <span class="userinfo">
							<div class="lb">
								<img src="css/icon/headimg.jpg" width="48" height="48"
									alt="HeadImg" />
							</div>
							<div class="userDepartment">
								<ul>
									<li>某某某部门</li>
									<li><b class="clr_bwn">某某某</b>，欢迎登录</li>
								</ul>
							</div>
						</span>
					</div>
				</div>
				<div id="pveast">
					<div id="veast">	
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
