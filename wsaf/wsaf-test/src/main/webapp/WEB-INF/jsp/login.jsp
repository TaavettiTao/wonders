<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>

<html>
<head>
    <base href="<%=basePath%>">

    <title>Login</title>
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

        $(function () {			
			$("#btnSubmit").click(function(){				
				login();

			});
        });
        
       
        function login(){
        	$.ajax({  
        		type: 'POST',        
	            url: './api/user/login/acdm',	           
	            //async: false,
	            data:$("#form").serialize(),
	            dataType:'json',
	            success: function (rto) {
	                if(rto.info.success==true){
	                
	                	alert("操作成功");
	                	
	                	alert(rto.data);
	                }else{
	                	alert(rto.info.error);
	                }
	
	            },
	            error: function () {
	                alert("sorry,login failure");
	            }
	        });
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
                                    <tr>
                                    <td class="lableTd t_r">用户名</td>
                                    <td><input type="text" id="loginName"  name="loginName" class="input_large" maxlength="30"></td>
                                    </tr>
                                                                        <tr>
                                    <td class="lableTd t_r">密码</td>
                                    <td><input type="password" id="password"  name="password" class="input_large" maxlength="30"></td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <div class="mb10 t_c">
                            <input type="button" id="btnSubmit" value="登录"/>
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
