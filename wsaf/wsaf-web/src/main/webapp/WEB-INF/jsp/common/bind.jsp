<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
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
<script type="text/javascript" src="js/jquery.cookie.js"></script>

<!-- <link rel="stylesheet" href="css/demo.css" type="text/css"> -->
<!-- <link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" -->
<!-- 	type="text/css"> -->

<!-- <script type="text/javascript" src="js/jquery.ztree.core-3.5.js"></script> -->
<!-- <script type="text/javascript" src="js/jquery.ztree.excheck-3.5.js"></script> -->
<!-- <script type="text/javascript" src="js/jquery.ztree.exedit-3.5.js"></script> -->
<!-- <script type="text/javascript" src="js/tree_obj.js"></script> -->
<!-- <script type="text/javascript" src="js/tree_auto_correlation.js"></script> -->
<!-- <script type="text/javascript" src="js/tree_async.js"></script> -->

<script type="text/javascript">
    var objData="";
	$(function() {
	
	    $("#logout").click(function(){
	         if(confirm("您确认退出系统吗？")){
	           $.cookie('user', '', {expires:-1,path:'/'}); // 删除 cookie
	           $("#logout").attr("href","<%=basePath%>common/logout");
	         }
	    });
	  //实现一键清空功能
	    $("#oneKeyClear").click(function(){
	    	$(".userMods").remove();
	    });
	    //实现一键拖放功能
	    $("#oneKeyPut").click(function(){
	        //获取所有存在的对象
	        var arrayObjs=$(".v-menu ul li");
	        for(var i=0;i<arrayObjs.length;i++){
	           var modname=$(arrayObjs[i]).attr("class").replace("ui-draggable ui-draggable-handle","").trim();
	           var modCnName=$(arrayObjs[i]).text();
	           var id=$(arrayObjs[i]).val();
	           if($("#"+modname).length==0){
	   
// 				  $.getTable("${pageContext.request.contextPath}/"+modname,"#"+modname);
				  //对该div启用拖放功能
				  $("#"+modname).draggable({
				      revert : true,
 			          delay: 100,
				  });
				  
// 				  here
		
				  commonTable(modname,modCnName,id);	
	           }
	        }
	    });
	    
// 	    getAllObjInfo();
	    initModule();
	    $(".v-menu ul li").click(function(){
	    	
	    	
		       //获取已存在的对象,首先将其恢复为初始态
		       var relationObjs=$(".v-menu ul li");
		       for(var i=0;i<relationObjs.length;i++){
		          var currentObj=$(relationObjs[i]).attr("class").replace("ui-draggable ui-draggable-handle","").trim();
		          
		          var content=$("#"+currentObj+" div div h1").text();
		          if(typeof(content)=="undefined"){
		            continue;
		          }else if(content.indexOf("-",0)!=-1){
		             content=content.substring(0,content.indexOf("-",0));
		          }
		         $("#"+currentObj+" div div h1").text(content);
		         $("#"+currentObj+" div div h1").removeAttr("style");
		       }
		       var a=$(".v-menu ul li a");
		       for(var i=0;i<a.length;i++){
		          $(a[i]).removeAttr("style");
		       }
		       
		       //设置当前选择节点以及其子节点
// 		       var currentObj=$(this).attr("class").replace("ui-draggable ui-draggable-handle","").trim();
// 		       $(this).children("a").attr("style","color:black;font-weight:bold");
// 		       var currentObj=$(this).attr("class").replace("ui-draggable ui-draggable-handle ui-draggable-dragging","").trim();
		       var currentObj=$(this).attr("class").replace("ui-draggable ui-draggable-handle","").trim();
		       $(this).children("a").attr("style","color:black;font-weight:bold");
		       var value = $(this).val();
		       var rule = $.getRule("${pageContext.request.contextPath}/",getUrlParam('ruleTypeId'),currentObj);
// 		       alert(rule[0]);
		       if(rule[0]!="undefined"){
		           for(var a in rule){
				       //organ,user>role>privilege>resource
				        var objTmp = rule[a].split(/>|,/g);
			            for(var i=0;i<objTmp.length;i++){
		           	  	 if(i==0){
		                      continue;
		                    }
			              var content=$("#"+objTmp[i]+" div div h1").text();
			              if(content.indexOf("级")==-1){
			            	  $("#"+objTmp[i]+" div div h1").text(content+"-"+(i)+"级");
			              }
			              
			            }
				       
				        //设置其他节点变淡
//		 		        for(var i=0;i<relationObjs.length;i++){
//		 		            var tmp=$(relationObjs[i]).attr("class").replace("ui-draggable ui-draggable-handle","").trim();
				            
//		 		            if(rule[a].indexOf(tmp)==-1){
//		 		            	 $("#"+tmp+" div div h1").attr("style","color:#DBDBDB");
//		 		            }
				            
//		 		        } 
		    		}
		       }
		
	    });
	    
	    $(".v-menu li").draggable({
 			revert : true,
 			delay: 300,
 		});

	    
		$("#pveast").droppable({
			  accept: ".v-menu li",
		      drop: function( event, ui ) 
			  {
					
		    	var modname=$.trim(ui.draggable.attr("class").replace("ui-draggable ui-draggable-handle ui-draggable-dragging",""));
		    	var modCnName=ui.draggable.context.innerText;
		    	var id=ui.draggable.attr("value");
		    	if($("#"+modname).length>0)
		    	{	alert("当前模块已存在");
		    		return false;
		    	}
		    	
// 					here
					commonTable(modname,modCnName,id);				
				
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
	
	//表格通用部分
	function commonTable(modname,modCnName,id){
        var _strTable="<div class=\"userMods fl\" id=\""+modname+"\" value=\""+id+"\"><div class=\"userMod\"><div id=\""+modname+"Dialog\" style=\"display:none\"><form id=\""+modname+"form\"></form></div><div><ul style=\"display:none\" class=\"s_menu\"><li class=\"delmod\">删除</li></ul><h1 class=\"fl\">"+modCnName+"</h1>";
        _strTable+="<div class=\"modName\" style=\"display:none\">"+modname+"</div><a class=\"size fr\">展开</a><span class=\"fr\">";
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
//			            	  var ruleObjs = new Array();
		            	  var nmodname=ui.draggable.parents(".userMods").attr("id"); 
		            	  var pmodname=$thismod.parents(".userMods").attr("id"); 
		            	  
		            	  var nid = ui.draggable.parents(".userMods").attr("value"); 
		            	  var pid = $thismod.parents(".userMods").attr("value"); 
		            	  
		            	  var rule = $.getRule("${pageContext.request.contextPath}/",getUrlParam('ruleTypeId'),pmodname);
		            	  var tmp="";
		            		  for(var a in rule){
		            			  var ruleTmp = rule[a];
									 var nextRule = ruleTmp.substring(ruleTmp.indexOf(">")+1);
									//判断自增长
									 if(nextRule.indexOf(pmodname)>=0){
										 nextRule = nextRule.substring(nextRule.indexOf(">")+1);
									 }
									//判断是否尾节点
									 if(nextRule.indexOf(">")==-1){
										 tmp += nextRule+",";
									 }else{
										 var nextObj = nextRule.substring(0,nextRule.indexOf(">"));
										 if(tmp.indexOf(nextObj)<0){//判断是否有重复子节点
											 tmp += nextObj+",";
										 }
									 }
			            		  }
	            		
	            		  tmp = tmp.substring(0,tmp.lastIndexOf(","));
	            		
		            	  if($('#bind').is(':checked')){
					   		     if(tmp.indexOf(nmodname)>=0){
									 $.saveBind("${pageContext.request.contextPath}/relation/bind","#"+pmodname,"#"+nmodname,getUrlParam('ruleTypeId'));
						              $( this ).dialog( "close" );
				            	  }else{
				            		  alert("绑定规则错误，请改变拖拽顺序！");
				            		  $( this ).dialog( "close" );
				            	  }
		            	  }else if($('#removeBind').is(':checked')){
		            		  if(tmp.indexOf(nmodname)>=0){
								  $.removeBind("${pageContext.request.contextPath}/relation/unbind","#"+pmodname,"#"+nmodname,getUrlParam('ruleTypeId'));
					              $( this ).dialog( "close" );
			            	  }else{
			            		  alert("解绑规则错误，请改变拖拽顺序！");
			            		  $( this ).dialog( "close" );
			            	  }
		            	  }
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
				$.getTable("${pageContext.request.contextPath}/"+modname,"#"+modname,getUrlParam('ruleTypeId'));
				
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
			 

                //点击变色，并寻找下级节点数据
//                 $("#"+modname+" table>tbody").click(function(e){
//                     if(!$(e.target).is("input[type=checkbox]")){
//                     	$(":checkbox").attr("checked",false);
//                 }
                	
// //                 	$("table>tbody tr td").find("input").attr("checked",false);
//                 	$(this).children("tr").css('background-color', '');
                	
//                  	var thismod=$(this).children("tr");
// 					$(e.target).parent("tr").css('background-color','#DBDBDB');
//                 	var this_id=$(e.target).parent("tr").find("td").eq(2).text();

//                 	var nextObj = $.getNext("${pageContext.request.contextPath}/ruleType/2/relation/objs/organ_1>role",this_id ,modname);
//                 	 for(var a in nextObj){
//                 		 nextObjINid = nextObj[a].nid;
//                 		 nextObjINType = nextObj[a].ntype;
//                 		 var nextObjTr = $("#"+nextObjINType+" table>tbody").children("tr");
//                 		 nextObjTr.html(function(index,oldcontent){
//                 			 if(oldcontent.indexOf(nextObjINid) != -1){
//                 				 nextObjTr.eq(index).find("td").eq(0).html("<input type=\"checkbox\" value=\"checkbox1\" name=\"cbitem\" checked=\"checked\">");
//                 			 };
//                 		 });
//                 	 }
// 			  	});
	}
	//获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
	//初始化模块
	function initModule(){
	    var pobjType= getUrlParam('pobjType');
	    var ruleTypeId= getUrlParam('ruleTypeId');
	    getRuleObjs(ruleTypeId,pobjType);
	    
	}
	
	//获取指定type、ptype条件下的规则记录
	 function getRuleObjs(ruleTypeId,pobjType){
           $.ajax({
                  url:'<%=basePath%>api/ruleType/'+ruleTypeId+'/rule/objs',
                  type:'GET',
                  dataType:'json',
                  async:false, //同步
                  data:{ 
                          'pobjType':pobjType,
//                           'ruleTypeId':ruleTypeId,
                  },
                  success:function(result){
                      if(result.info.success){
                         if(result.data!=null && result.data.length>0){
                            $(".v-menu ul").html("");
                            var items="";
                            if(pobjType=="" || pobjType==null){
                                for(var i=0;i<result.data.length;i++){
                                   items+="<li class=\""+result.data[i].type+"\" value=\""+result.data[i].id+"\" draggable=\"true\" ><a href=\"javascript:void(0);\">"+result.data[i].name+"</a></li>";
                                }
                            }else{
	                            for(var i=0;i<result.data.length;i++){
	                                if(i==0){
	                                  items+="<li class=\""+pobjType+"\" value=\""+getPobjTypeInfo(pobjType)[0].id+"\" draggable=\"true\" ><a href=\"javascript:void(0);\">"+getPobjTypeInfo(pobjType)[0].name+"</a></li>";
	                                }
	                                items+="<li class=\""+result.data[i].type+"\" value=\""+result.data[i].id+"\" draggable=\"true\"><a href=\"javascript:void(0);\">"+result.data[i].name+"</a></li>";
	                            } 
                            }
                            $(".v-menu ul").html(items);
                         }
                      }
                  }
           });
        }
        
	 //得到系统所有的对象信息
     function getPobjTypeInfo(pobjType){
		 var result = null;
		    $.ajax({
				type: 'GET',
				url: '<%=basePath%>api/objInfo',
				data:{
					type:pobjType
				},
				dataType:'json',
				cache:false,
				async:false,
				error:function(){alert('系统连接失败，请稍后再试！')},
				success: function(obj){
					if(obj.info.success){
					    if(obj.data!=null && obj.data.length>0){
					    	result=obj.data;
					    }
					}
				}	  
			});	
		    return result;
     }
	 
//         //得到系统所有的对象信息
//         function getAllObjInfo(){
// 		    $.ajax({
// 				type: 'GET',
<%-- 				url: '<%=basePath%>objInfo', --%>
// 				dataType:'json',
// 				//cache : false,
// 				async:false,
// 				error:function(){alert('系统连接失败，请稍后再试！')},
// 				success: function(obj){
// 					if(obj.info.success){
// 					    if(obj.data!=null && obj.data.length>0){
// 					       objData=obj.data;
// 					    }
// 					}
// 				}	  
// 			});	
//         }
        
        //转换value为显示的文本text
//         function convert(value){
//             var text;
//             for(var i=0;i<objData.length;i++){
//                if(objData[i].type==value){
//                   text=objData[i].name;
//                }
//             }
//            return text;
//         }
        
        function isLoginTimeout(e){
           var event=window.event;
           var obj = event.srcElement ? event.srcElement : event.target; //事件对象：事件对象实例
           var id=$(obj).attr("id");
           if(id=="logout"){
               e.stopPropagation();
           }else{
               var username=$.cookie('user');
	           if(username==null||username==""){
	             alert("您已登录超时，请重新登录！");
	             //跳转当前页地址栏完整URL
	             window.location.href=window.top.document.URL;
	           }
           }
        }
           
</script>
<style type="text/css">
   a#logout{
      color:#A9A487;
   }
   a#logout:hover {
      text-decoration: underline;
      color:#FF8B19;
   }
   
</style>
</head>
<!-- <body id="default"  onclick="isLoginTimeout(event)" > -->
<body id="default">
<div id="dialog" style="display:none">
  <p style="margin-left:5px;">是否绑定（解绑）数据？</p>
</div>
	<form>
		<div class="v-center" >
			<div class="v-menu">
				<ul>
<!-- 					<li class="user" draggable="true"><a href="#">用户</a></li> -->
<!-- 					<li class="role" draggable="true"><a href="#">角色</a></li> -->
<!-- 					<li class="privilege" draggable="true"><a href="#">权限</a></li> -->
<!-- 					<li class="organ" draggable="true"><a href="#">部门</a></li> -->
				</ul>
			</div>
			<div class="v-main">
				<div class="v-top">
					<div id="radio" class="fl">
<!-- 						<input type="radio" id="rdoFir" name="radio" checked="checked"> -->
<!-- 						<label for="rdoFir" style="color:#c7188b">Level 1</label> <input -->
<!-- 							type="radio" id="rdoSen" name="radio"> <label -->
<!-- 							for="rdoSen" style="color:#ff8b19">Level 2</label> <input -->
<!-- 							type="radio" id="rdoThi" name="radio"> <label -->
<!-- 							for="rdoThi" style="color:#885df3">Level 3</label> -->
							
							<input type="radio" id="bind" name="bindRelation"  checked="checked" /> 
							<label for="bind" style="color:#885df3">绑定</label>
							<input type="radio" id="removeBind" name="bindRelation"> 
							<label for="removeBind" style="color:#885df3">解绑</label>
							<input type="radio" id="oneKeyPut" name="oneKeyPut"> 
							<label for="oneKeyPut" style="color:#885df3">一键拖放</label>
							<input type="radio" id="oneKeyClear" name="oneKeyClear"> 
							<label for="oneKeyClear" style="color:#885df3">一键清空</label>
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
							    <security:authentication property="principal" var="authentication"/>
								<ul>
									<li>${authentication.dept}</li>
									<li><b class="clr_bwn">${authentication.username}</b>，欢迎登录 &nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:void();" id="logout">退出系统</a></li>
								</ul>
								
							</div>
						</span>
					</div>
				</div>
				<div id="pveast">
					<div id="treeBody">
						<h1> 树形操作</h1>
					</div>
					<div id="veast">	
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
