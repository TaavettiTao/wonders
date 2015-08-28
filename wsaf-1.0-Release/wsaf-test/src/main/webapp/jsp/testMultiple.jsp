<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>

<html>
<head>
    <base href="<%=basePath%>">

    <title>测试复杂对象</title>
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
        	$("#multiData").val('{"user":[{"id":"1","name":"张三","password":"xxxx","mobile":"134454666","child":{"test":[{"id":85,"str":"aaa85","num":"12.345","date":"2013-11-23","datetime":"2014-02-11 14:32:23","fk":"$user.id"},{"id":87,"str":"abc87","num":"190.389","date":"2013-05-11","datetime":"2019-12-11 13:45:21","fk":"0"}]},"parent":{"role":[{"id":161,"name":"角色161","child":{"test":[{"id":82,"str":"aaa","num":"12.345","date":"2013-11-23","datetime":"2014-02-11 14:32:23","fk":""},{"id":84,"str":"abc84","num":"190.389","date":"2013-05-11","datetime":"2019-12-11 13:45:21","fk":"$role.id"}]}}],"organ":[{"id":"1","name":"单位1"}]}}]}');
        	
        	$("#multiQuery").val('{"select":{"bb":"b.*","aid":"a.id","fk":"a.fk","astr":"a.str"},"from":  {"a":"test","b":"user"},"where": {"b.id":"a.fk","$or":{"a.id":81,"a.datetime": {"$gte":"2014-02-11 10:13:14","$lte":"2014-02-11 15:23:34"}}},"order": {"a.date":"asc","a.datetime": "desc"}}');
        			
			$("#add").click(function(){				
				add();
			});
			
			$("#query").click(function(){
           		query();
           	});  
           	
        });
        
       
//添加复杂对象
        function add(){
        	$.ajax({            
	            url: './api/multi/put/acdm',
	            type: 'post',
	           // async: false,
	            dataType:'json',
	            //contentType: "application/x-www-form-urlencoded; charset=utf-8",
	            //contentType: "application/json; charset=utf-8",
	            //contentType: "text/html;charset=UTF-8",
	            //data:'multipleData={"user":[{"id":"1","name":"张三","password":"xxxx","mobile":"134454666","child":{"test":[{"id":85,"str":"aaa85","num":"12.345","date":"2013-11-23","datetime":"2014-02-11 14:32:23","fk":"$user.id"},{"id":87,"str":"abc87","num":"190.389","date":"2013-05-11","datetime":"2019-12-11 13:45:21","fk":"0"}]},"parent":{"role":[{"id":161,"name":"角色161","child":{"test":[{"id":82,"str":"aaa","num":"12.345","date":"2013-11-23","datetime":"2014-02-11 14:32:23","fk":""},{"id":84,"str":"abc84","num":"190.389","date":"2013-05-11","datetime":"2019-12-11 13:45:21","fk":"$role.id"}]}}],"organ":[{"id":"1","name":"单位1"}]}}]}', 
	            //data:'{"fd":"123"}',
	            data:$("#form").serialize(),
	            success: function (rto) {
	            alert(JSON.stringify(rto));
	                if(rto.info.success==true){	   
	                             
	                	alert("操作成功");

	                }else{
	                	alert("the api has error:"+rto.info.error);
	                }
	
	            },
	            error: function () {
	                alert("sorry,addMultipleData fail");
	            }
	        });
        	
        }
        
      function query(){
        	$.ajax({            
	            url: './api/multi/query/acdm',
	            type: 'post',
	           // async: false,
	            dataType:'json',
	            //contentType: "application/x-www-form-urlencoded; charset=utf-8",
	            //contentType: "application/json; charset=utf-8",
	            //contentType: "text/html;charset=UTF-8",
	            //data:'multipleData={"user":[{"id":"1","name":"张三","password":"xxxx","mobile":"134454666","child":{"test":[{"id":85,"str":"aaa85","num":"12.345","date":"2013-11-23","datetime":"2014-02-11 14:32:23","fk":"$user.id"},{"id":87,"str":"abc87","num":"190.389","date":"2013-05-11","datetime":"2019-12-11 13:45:21","fk":"0"}]},"parent":{"role":[{"id":161,"name":"角色161","child":{"test":[{"id":82,"str":"aaa","num":"12.345","date":"2013-11-23","datetime":"2014-02-11 14:32:23","fk":""},{"id":84,"str":"abc84","num":"190.389","date":"2013-05-11","datetime":"2019-12-11 13:45:21","fk":"$role.id"}]}}],"organ":[{"id":"1","name":"单位1"}]}}]}', 
	            //data:'{"fd":"123"}',
	            data:$("#form1").serialize()+"&page=1,3",
	            success: function (rto) {
	            alert(JSON.stringify(rto));
	                if(rto.info.success==true){	   
	                             
	                	alert("操作成功");

	                }else{
	                	alert("the api has error:"+rto.info.error);
	                }
	
	            },
	            error: function () {
	                alert("sorry,queryMultiple fail");
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
                                    <td class="f1"><h5 class="fl">测试复杂录入</h5></td>
                                    </thead>
                                    <tr>
                                    <td><textarea rows="8" cols="20" id="multiData"  name="multiJson"></textarea></td>
                                    </tr>
                                    <tr>
                                    <td><input type="button" id="add" value="保存" /></td>
                                    </tr>
                                </table>
                            </form>
                           
                        </div>
                        <div class="mb10">
                        <form id="form1">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2">
                                    <thead>
                                    <td class="f1"><h5 class="fl">测试复杂查询</h5></td>
                                    </thead>
                                    <tr>
                                    <td><textarea rows="15" cols="20" id="multiQuery"  name="multiJson"></textarea></td>
                                    </tr>
                                    <tr>
                                    <td><input type="button" id="query" value="查询" /></td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <div class="footer"></div>
                    </div>

                </div>

            </div>

        </div>

    </div>
</div>
</body>
</html>
