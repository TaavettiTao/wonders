<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
System.out.println(basePath);
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<base href="<%=basePath%>" />
	<title>FRAME-2222</title>

<!-- 	<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script> -->
<!-- <script type="text/javascript" language="javascript" src="js/jquery-1.4.2.min.js"></script> -->
<!-- <script type="text/javascript" language="javascript" src="js/swfobject.js"></script> -->
<!-- <script type="text/javascript" language="javascript" src="js/jquery.uploadify.v2.1.4.min.js"></script> -->
	<script src="js/jquery-1.7.1.min.js"></script>
	<script src="js/jquery-ui-1.8.18.custom.min.js"></script>
	<script src="js/jquery.uploadify-3.1.js"></script>
	<script type="text/javascript" src="js/me.js"></script>
	<script type="text/javascript">
	//关闭窗口
	function shut(){
			  window.opener=null;
			  window.open("","_self");
			  window.close();
			}

	
	function ajaxTest(){  
		var _date = $("#form").serialize();
        $.ajax( {  
            type : 'POST',  
//             contentType : 'application/json',     
            url : '<%=basePath%>/userOld/saveObj',     
//             async: false,//禁止ajax的异步操作，使之顺序执行。  
            data: _date,
            dataType : 'json',  
            success : function(data){  
            	  if(data.success) {
                      alert("成功");
                      $("#divJson").html("");
                  }
                  else {
                      var text = "";
                      text += "<font color='red'>" + data.errorInfo + "</font><br/>";
                      $("#divJson").html(text);
                  }  
            },  
            error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
// 				alert("系统连接失败请稍后再试！");
			}  
        });  
    }
	
	function ajaxError(){  
        $.ajax( {  
            type : 'GET',  
            url : '<%=basePath%>/test/josontest',     
            dataType : 'json',  
            success : function(data,textStatus){  
                alert(JSON.stringify(data));  
            },  
            error : function(data,textstatus){  
                alert(data.responseText);  
            }  
        });  
    } 
	</script>
	
	<link rel="stylesheet" href="css/jquery-ui-1.8.18.custom.css" type="text/css" />
	<link rel="stylesheet" href="css/reset.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="css/uploadify.css" type="text/css" />
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
 		<form  id="form" >  
<!--  		 <b>Json:</b><hr/> -->
   			 <div id="divJson"></div>
        	<div class="gray_bg2">
            	<div class="w_bg">
                	<div>
                    	<div class="Top_fw">
                        	<h1 class="t_c">上海申通地铁集团有限公司<br>发文单2</h1>
                            <div class="mb10">
                           	   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_4">
                                  <tr>
                                    <td class="lableTd" width="15%">用户名</td>
                                    <td ><input type="text" name="name" id="name"/></td>
                                    <td class="lableTd" width="15%">密码</td>
                                    <td >
                                    <input type="text" name="password" id="password" />
                                    </td>
                                  </tr>
                                
                              </table>
                          </div>
                          <div class="mb10 t_c">
                         
                         
<!--                           <input type="submit" value="提交" id="btnSubmit" /> -->
                          <input class="button" type="button" value="ajax提交" onclick="ajaxTest();" />
                          <input class="button" type="button" value="测试ajax异常" onclick="ajaxError();" />
                          </div>
                            <div class="footer"></div>
                        </div>
                    </div>
                </div>
            </div>
            </form>
		</div>
	</div>
	
	<div class="clear"></div>
	<div id="footer">
		<small>
			Powered by <a href="#" target="_blank">lsf</a> | <a href="mailto:mengjie@wondersgroup.com">联系我</a>
		</small>
	</div>
</div>
</body>
</html>
