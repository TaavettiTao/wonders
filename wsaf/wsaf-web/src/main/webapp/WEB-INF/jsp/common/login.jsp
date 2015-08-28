<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
	    $(function(){
	        
	       //绑定失去焦点事件，获取用户部门
	       $("input[name=username]").blur(function(){
	          var username=$(this).val();
	          $("#dept").html("");
	          var flag=isExist(username);
	          if(flag){
	            $("#errorMsg div").html();
	            $("#errorMsg").css("display","none");
	            getDept(username);
	          }else{
	             $("#errorMsg div").html("该用户不存在！");
	             $("#errorMsg").css("display","block");
	          }
	          
	       });
	       
	       $("#imageCode").click(function(){
	           var randNum=new Date().getMilliseconds();
	           $(this).attr('src','<%=basePath%>validateCode/createCode.jpeg?randNum='+randNum);
	       });
	    });
	    
	    function getDept(username){
	        $.ajax({
<%-- 	             url:'<%=basePath%>common/getDept?username='+username, --%>
	             url:'<%=basePath%>relationRule/api/findObjByRule/user_'+username+'>role<organ?type=type20141216',
	             type:'post',
	             dataType:'json',
	             async:false, 
	             success:function(data){
	               if(data.success==true){
	                 if(data.result!=null && data.result.length>0){//用户名存在
	                    var options="";
	                    if(data.result.length==1){
	                       options+="<option value='"+data.result[0].name+"'>"+data.result[0].name+"</option>";
	                    }else{
	                        options+="<option value=\"\">请选择</option>";
	                        for(var i=0;i<data.result.length;i++){
	                           options+="<option value='"+data.result[i].name+"'>"+data.result[i].name+"</option>";
	                        }
	                    }
	                    $("#dept").html(options);
	                 }else{
	                    $("#errorMsg div").html("该用户未配置部门！");
	                    $("#errorMsg").css("display","block");
	                 }
	               }
	             },
	             error:function(XMLHttpRequest, textStatus, errorThrown){
						alert(XMLHttpRequest.status);
		                alert(XMLHttpRequest.readyState);
		                alert(textStatus);
//		 				alert("系统连接失败请稍后再试！");
					}
	             
	          });
	    }
	    
	    //判断用户名是否存在
	    function isExist(username){
	       var flag;
	       $.ajax({
	             url:'<%=basePath%>common/isExist?username='+username,
	             type:'POST',
	             dataType:'json',
	             async:false, 
	             success:function(data){
	               if(data.success==true){
	                 if(data.result!=null){//用户名存在
	                    flag=true;
	                    return flag;
	                 }else{//用户名不存在
	                    flag=false;
	                    return flag;
	                 }
	              }
	             }
	        });
	        return flag;
	    }
	    
	   function addOption(){
         var name=$("input[name=username]").val();
         if(name!=null && name!=""){
            $("#dept").html("");
            getDept(name);
         }
	   }
	   
	   function timeFinish(){
	      setTimeout(addOption,1000);
	   }
	   
	   //当用户不存在或用户未配置部门，不允许提交表单
	   function check(){
	      var display=$("#errorMsg").css("display");
	      if(display=="block"){
	         return false;
	      }else{
	        return true;
	      }
	   }
	</script>
	<style type="text/css">
	  select{
	     width: 200px;
         float: right;
	  }
	</style>
</head>

<body id="login" onload="timeFinish()" >
	<div id="login-wrapper" class="png_bg">
		<div id="login-top">
			<h1>FRAME</h1>
			<a href="#" target="_blank">
				<img id="logo" src="images/logo.png" />
			</a>
		</div>
		<div id="login-content">
			<form action="common/login-process" method="post" onsubmit="return check();">
				<c:if test="${hasErrors}">
				<div class="notification error png_bg" id="errors">
					<div>
						<c:forEach items="${loginErrors}" var="msg">
							<c:out value="${msg}" /> <br/>
						</c:forEach>
					</div>
				</div>
				</c:if>
				<div class="notification error png_bg" id="errorMsg" style="display:none;">
					<div></div>
				</div>
				<p>
					<label>Username</label> <input class="text-input" type="text" name="username" value="${username}" />
				</p>
				<div class="clear"></div>
				<p>
					<label>Password</label> <input class="text-input" type="password" name="password" value="${password}"/>
				</p>
				<div class="clear"></div>
				<p>
					<label>Dept</label>
					<select id="dept" name="dept" >
					  
					</select>
				</p>
				<div class="clear"></div>
				<p style="padding-top: 20px;">
				   <label>Verifycode</label>
				   <input class="text-input small-input" type="text" name="validateCode" id="validateCode"
				                                      style="float:left;margin-left:30px;"/>
				   <img id="imageCode" alt="换一张" title="换一张" src="<%=basePath%>validateCode/createCode.jpeg" style="float:right"/>
			    </p>
<!-- 			    <div class="clear"></div>	 -->
<!-- 				<p id="remember-password" style="margin-top: 10px;margin-left:100px;float:left"> -->
<!-- 					<input type="checkbox" name="_remember_me"  checked="checked" /> Remember me -->
<!-- 				</p> -->
<!-- 				<div class="clear"></div> -->
				<p >
					<input class="button" style="float:left;margin-left:120px" type="submit" value="Sign In" />
					<input class="button" style="float:left;margin-left:20px" type="reset" value="Reset" />
				</p>
			</form>
		</div>
	</div>
</body>
</html>

