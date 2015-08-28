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
	       //提交表单
	       $("#submit").click(function(){
	           
	           var content=$("#errorMsg div").text();
	           var display=$("#errorMsg").css("display");
	           if(display=="block" && (content=="该用户不存在！" || content=="该用户未配置部门！")){
	             return ;
	           }
	       
	           $("#errorMsg div").html("");
	           $("#errorMsg").css("display","none");
	           var username=$("[name=username]").val();
	           var password=$("[name=password]").val();
	           var dept=$("[name=dept]").val();
	           var verificationCode=$("[name=verificationCode]").val();
	           var errorMsgs="";
	           var re=new RegExp("[a-zA-Z0-9]{4,12}");
	           if(!re.test(username)){
	              errorMsgs+="用户名不符合要求!<br/>";
	           }
	           if(!re.test(password)){
	              errorMsgs+="密码不符合要求!<br/>";
	           }
	           if(dept==""){
	              errorMsgs+="部门不能为空！<br/>";
	           }
// 	           if(verificationCode==""){
// 	              errorMsgs+="验证码不能为空！<br/>";
// 	           }

	           if(errorMsgs.length>0){
	             $("#errorMsg").css("display","block");
	             $("#errorMsg div").html(errorMsgs);
	             return;
	           }
	           $.ajax({
	               url:'<%=basePath%>login/api/access',
	               type:'POST',
	               data:$("form").serialize(),
	               dataType:'json',
	               async:false,
	               success:function(data){
	                 if(data.success){
	                    window.location.href="<%=basePath%>login/index";
	                 }else{
	                      $("#errorMsg div").html(data.errorMsg);
	                      $("#errorMsg").css("display","block");
	                 }
	               },
	               error:function(){
	                  alert("系统异常,请稍后再试！");
	               }
	           });
	       });
	       
	       $("#reset").click(function(){
	          $("[name=username]").val("");
	          $("[name=password]").val("");
	          $("[name=dept]").html("");
	          $("[name=verificationCode]").val("");
	          $("#errorMsg div").html("");
	          $("#errorMsg").css("display","none");
	       });
	        
	        
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
	           $(this).attr('src','<%=basePath%>verificationCode/createImage.jpeg?randNum='+randNum);
	       });
	    });
	    
	    function getDept(username){
	        $.ajax({
	             url:'<%=basePath%>login/api/getDepts?username='+username,
	             type:'post',
	             dataType:'json',
	             async:false, 
	             success:function(data){
	               if(data.success==true){
	                 if(data.result!=null && data.result.length>0){//用户名存在
	                    var options="";
	                    if(data.result.length==1){
	                       options+="<option value='"+data.result[0]+"'>"+data.result[0]+"</option>";
	                    }else{
	                        options+="<option value=\"\">请选择</option>";
	                        for(var i=0;i<data.result.length;i++){
	                           options+="<option value='"+data.result[i]+"'>"+data.result[i]+"</option>";
	                        }
	                    }
	                    $("#dept").html(options);
	                 }else{
	                    $("#errorMsg div").html("该用户未配置部门！");
	                    $("#errorMsg").css("display","block");
	                 }
	               }
	             },
	             error:function(){
	               alert("系统正忙，请稍后再试！");
	             }
	             
	          });
	    }
	    
	    //判断用户名是否存在
	    function isExist(username){
	       var flag;
	       $.ajax({
	             url:'<%=basePath%>login/api/isExist?username='+username,
	             type:'POST',
	             dataType:'json',
	             async:false, 
	             success:function(data){
	               if(data.success==true){
	                 if(data.result!=null){//用户名存在
	                    flag=true;
	                 }else{//用户名不存在
	                    flag=false;
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
	      setTimeout(addOption,500);
	   }
	</script>
	<style type="text/css">
	  select{
	     width: 200px;
         float: right;
	  }
	</style>
</head>

<body id="login" >
	<div id="login-wrapper" class="png_bg">
		<div id="login-top">
			<h1>FRAME</h1>
			<a href="#" target="_blank">
				<img id="logo" src="images/logo.png" />
			</a>
		</div>
		<div id="login-content">
			<form >
				<div class="notification error png_bg" id="errorMsg" style="display:none;">
					<div></div>
				</div>
				<p>
					<label>Username</label> <input class="text-input" type="text" name="username" />
				</p>
				<div class="clear"></div>
				<p>
					<label>Password</label> <input class="text-input" type="password" name="password"/>
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
				   <input class="text-input small-input" type="text" name="verificationCode" id="verificationCode"
				                                      style="float:left;margin-left:30px;"/>
				   <img id="imageCode" alt="换一张" title="换一张" src="<%=basePath%>verificationCode/createImage.jpeg" style="float:right"/>
			    </p>
				<p >
					<input class="button" style="float:left;margin-left:120px" type="button" id="submit" value="Sign In" />
					<input class="button" style="float:left;margin-left:20px" type="button" id="reset" value="Reset" />
				</p>
			</form>
		</div>
	</div>
</body>
</html>

