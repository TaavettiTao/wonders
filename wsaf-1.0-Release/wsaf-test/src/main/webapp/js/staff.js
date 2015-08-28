var deptCode="";
var loginName = "";
var role = "user";

$.getInitData=function(firUrl){
    $.ajax({
    	async: false, //必须等拿到用户loginName才能ajax其他请求
        url: firUrl + "/ca/getCookies",        
        type: 'post',
        dataType:'json',
        success: function (data) {
        	loginName = data.loginName;
        	if("G00100000074"==loginName){
        		role="admin";
        	}
        },
        error: function () {
            alert("对不起！你的请求出现异常，请联系服务人员！");
        }
    });
}

$.setFormData=function(firUrl,type){
    
    $.ajax({
    	async: false, //必须等拿到用户部门才能ajax其他请求
        url: firUrl + "/basicCrud/iasStaff/page?pageNum=1&pageSize=1&loginName="+loginName,
        type: 'post',
        dataType:'json',

        success: function (data) {
//            if (data.success == true && data.result.content && JSON.stringify(data.result.content) != "[]") {
                
                
                $.each(data.result.content, function (entryIndex, entry) {
           
                
            	if (entry.loginName == loginName){
            		
            		deptCode=entry.deptCode;
            		
            		$("option[value="+entry.deptCode.substring(0,4)+"]").attr("SELECTED","SELECTED");
            		
            		if(type=="edit"){
	            		//$("option[value="+entry.unitCode+"]").attr("SELECTED","SELECTED");
            			//$("option[value='00']").attr("SELECTED","SELECTED");
            			$("#unit_label").attr("value",entry.unit);
	            		//$("option[value="+entry.manualEntry+"]").attr("SELECTED","SELECTED");
	            		
	            		$("#manualEntry_select").attr("value","YES");	            		
	            		$("#manualEntry").val("YES");
	            		if("admin" != role){
	            			$("#manualEntry_select").attr("disabled","disabled");
	            		}
	            		$("#deptCode").val(entry.deptCode);
	            		$("#unitCode").val(entry.unitCode);
	            		$("#dept").val(entry.dept);
	            		$("#unit").val(entry.unit);
            		}else if(type="list"){
            			$("#addbtn").click(function () {
            				window.open(firUrl+"/staff/edit");
            			});
            			
            			if(role == "admin"){
            				$("#deptCode option:first").attr("selected","selected");
            			}else{
            				$("#deptCode").attr("disabled","disabled");            				
            				
            			}
            			
            			
            		}
            		 
            	}
            });
        	

        }
    ,
        error: function () {
            alert("对不起！你的请求出现异常，请联系服务人员！");
        }
    });
}
  
$.initEditPage = function (firUrl) {

     if (firUrl != "") {

        QueryString.Initial();

        if (QueryString.GetValue('id') != null) {
        	//$("#unitCode_select").hide();
        	$("#deptCode_select").hide();
            $.postMainDetail(firUrl, QueryString.GetValue('id'));

        }else{
        	$("#title").html("考勤人员新增");
        	//$("#unit_label").hide();
        	$("#dept_label").hide();
            $.setFormData(firUrl,"edit");
            if(role != "admin"){
               // $("#unitCode_select").attr("disabled","disabled");
                $("#deptCode_select").attr("disabled","disabled");
            }
        }
               
    }

};

$.analyzeSelMenu = function (dataname, _obj) {
    for (var sitem in _obj) {
        if ($("[name=" + dataname + "]").length > 0) {
            $("[name=" + dataname + "]").append("<option value='" + sitem + "'>" + _obj[sitem] + "</option>");
        }
    }
};


$.setBtn = function (firUrl) {
    $(".topage").click(function () {
        $.postForm(firUrl, "&pageSize=10&pageNum=" + $(this).text());
    });

    $(".prepage").click(function () {
        var nowpage = parseInt($(".selected a").text());

        if (nowpage > 1) {
            var topage = nowpage - 1;
            $.postForm(firUrl, "&pageSize=10&pageNum=" + topage);
        }
    });

    $(".nextpage").click(function () {
        var nextpage = parseInt($(".selected").text()) + 1;
        
        if (nextpage <= parseInt($("#gopage").attr("max")))
            $.postForm(firUrl, "&pageSize=10&pageNum=" + nextpage);

    });


    $("#gopagebtn").click(function () {
        var topage = parseInt($("#gopage").val());

        if (topage <= parseInt($("#gopage").attr("max")) && topage >= 1) {
            $.postForm(firUrl, "&pageSize=10&pageNum=" + topage);
        }
        else {
            alert("超出索引值");
        }
    });
};

$.setPage = function (result) {
//	alert("result.pageInfo:"+result.pageInfo);
    if (result.pageInfo != null) {
        var _pageinfo = result.pageInfo;
        var nowcount = $(".table_1").find(".tableItem");

        if (_pageinfo.pageSize > nowcount.length) {
            var minMum = _pageinfo.pageSize - nowcount.length, len = $(".table_1 .tit").children("td").length;

            for (var i = 0; i < minMum; i++) {
                var con = "<tr class=\"tableItem\">";
                for (var j = 0; j < len; j++) {
                    con += "<td>&nbsp;</td>";
                }
                $(".table_1").append(con + "</tr>");
            }
        }

//        	alert(" _pageinfo.totalRecord:"+ _pageinfo.totalRecord);
        $(".tfoot span:first-child").text("共" + _pageinfo.totalRecord + "条记录，当前显示1-" + nowcount.length + "条");

        var _menu = "<li>Pages:" + _pageinfo.currentPage + "/" + _pageinfo.totalPages + "<input type=\"number\" id=\"gopage\" name=\"number\" min=\"0\" max=\"" + _pageinfo.totalPages + "\" step=\"1\" class=\"input_tiny\" />";
        _menu += " <input type=\"button\" name=\"button\" id=\"gopagebtn\" value=\"Go\"></li><li><a class=\"nextpage\">&gt;&gt;</a></li>";

        var pageStyle = "";
        if (_pageinfo.totalPages > 7) {
            if (_pageinfo.currentPage <= 1) {
//                pageStyle += "<li class=\"selected\"><a class=\"topage\">1</a></li> <li><a class=\"topage\">2</a></li> <li><a class=\"topage\">3</a></li> <li><a class=\"topage\">4</a></li> <li><a class=\"topage\">5</a></li> <li><a>...</a></li> <li><a>" + _pageinfo.totalPages + "</a></li>";
                pageStyle += "  <li><a>" + _pageinfo.totalPages + "</a></li> <li><a>...</a></li>  <li><a class=\"topage\">5</a></li> <li><a class=\"topage\">4</a></li>  <li><a class=\"topage\">3</a></li> <li><a class=\"topage\">2</a></li> <li class=\"selected\"><a class=\"topage\">1</a></li> ";

            }
            else {
                var pa = _pageinfo.currentPage - 1;
                pb = _pageinfo.currentPage + 1;
                pc = _pageinfo.currentPage + 2;
                pd = _pageinfo.currentPage + 3;
//                pageStyle += "<li ><a class=\"topage\">" + pa + "</a></li> <li><a class=\"selected\">" + _pageinfo.currentPage + "</a></li> <li><a class=\"topage\">" + pb + "</a></li> <li><a class=\"topage\">" + pc + "</a></li> <li><a class=\"topage\">" + pd + "</a></li> <li><a>...</a></li> <li><a>" + _pageinfo.totalPages + "</a></li>";
                pageStyle += "<li><a>" + _pageinfo.totalPages + "</a></li>" + 
                			 "</a></li> <li><a>...</a></li>"+
                			 "<li><a class=\"topage\">" + pd + "</a></li>" +
                			 "<li><a class=\"topage\">" + pc + "</a></li>" +
                			 "<li><a class=\"topage\">" + pb + "</a></li>" +
                			 "<li class=\"selected\" ><a class=\"topage\">" + _pageinfo.currentPage + "</a></li>" +
                			 "<li ><a class=\"topage\">" + pa + "</a></li>";
            }
        }
        else {
            for (var j = 1; j <= _pageinfo.totalPages; j++) {
                if (j == _pageinfo.currentPage) {
                    pageStyle = "<li class=\"selected\"><a>" + j + "</a></li>" + pageStyle;

                }
                else {
                    pageStyle = "<li><a class=\"topage\">" + j + "</a></li>" + pageStyle;

                }
            }
//            for (var j =  _pageinfo.totalPages; j >= 0; j--) {
//                if (j == _pageinfo.currentPage) {
//                    pageStyle = "<li class=\"selected\"><a>" + j + "</a></li>" + pageStyle;
//
//                }
//                else {
//                    pageStyle = "<li><a class=\"topage\">" + j + "</a></li>" + pageStyle;
//
//                }
//            }
           
        }

        _menu += pageStyle + "<li><a class=\"prepage\">&lt;&lt;</a></li>";
        $("ul.pager li").remove();
 
        $("ul.pager").append(_menu);
    }
};


QueryString = {
    data: {},
    Initial: function () {
        var aPairs, aTmp;
        var queryString = new String(window.location.search);
        queryString = queryString.substr(1, queryString.length); //remove   "?"
        aPairs = queryString.split("&");
        for (var i = 0; i < aPairs.length; i++) {
            aTmp = aPairs[i].split("=");
            this.data[aTmp[0]] = aTmp[1];
        }
    },
    GetValue: function (key) {
        if (this.data[key] != null)
            return decodeURIComponent(this.data[key]);
        else
            return null;
    }
};

$.postForm = function (firUrl, paging) {
    var otherData = "";
    if (firUrl != "") {
        $.ajax({
            
            url: firUrl + "/basicCrud/iasStaff/page" ,
            type: 'post',
            dataType:'json',
            data: $("#staffForm").serialize()+ paging+  "&sort=staffCode asc" +"&deptCode_sl="+deptCode.substring(0,4),
            success: function (data, status, xhr) {
                $.analyzeTableData(data, status, xhr, firUrl);
                $.setBtn(firUrl);

            },
            error: function () {
                alert("对不起！你的请求出现异常，请联系服务人员！");
            }
        });
    }
};


//考勤模块
/**
 * ajax请求登录人员所在部门员工分页数据
 * 1.从cooki里获取登录人loginName 
 * 2.获取所有人员并从中筛选出loginName一致的记录，取出部门id
 * 3.获取所有人员并从中筛选出部门id一致的记录
 */






function nullString(str,length){
	var newStr=""
	if(null!=str){
		if(str.length>length){
			newStr=str.substr(0,length)+"...";
		}else{
			newStr=str;
		}
	}
	return newStr;
}

//得到员工性质
function getStaffTypeName(type){
	
//		<select >
//        <option value="">请选择</option>
//		  <option value="official">正式员工</option>
//		  <option value="loanedPersonnel">派遣工</option>
//		  <option value="dispatchingWorker">借调人员</option>
//         <option value="labor"> 劳务工</option>
//      </select>
	if ( null ==type ){
		return "";
	}
	else
	if ( "official" ==type ){
		return "正式员工";
	}
	else
		if ( "loanedPersonnel" ==type ){
			return "派遣工";
		}
		else
			if ( "dispatchingWorker" ==type ){
				return "借调人员";
			}else if ( "labor" == type ){
				return "劳务工";
			}else if ( "retire" == type ){
				return "退聘人员";
			}
	
}

function getManualEntryName(manualEntry){
	if ("YES" == manualEntry){
		return "手动";
	}else if ("NO" == manualEntry){
		return "";
	}
}
//解析Table
$.analyzeTableData = function (data, status, xhr, firUrl) {
    if (status == "success" && data != null) {
        $("tr.tableItem").remove();

        if (data.success == true && data.result.content) {

            $("tr.tableItem").remove();
            var tableItem = "";
            $.each(data.result.content, function (entryIndex, entry) {
            	
                tableItem = "<tr class=\"tableItem\">";
//                tableItem+="<td class=\"t_c\"><input type=\"checkbox\" id=\"test_checkbox_1\" name=\"test_checkbox_1\" /></td>";
//                tableItem += "<td>" + (entryIndex + 1) + "</td><td>" + entry.cpqName +
//                    "</td><td>" + entry.creator + "</td><td>" + (entry.opened=="yes"?"开放":"不开放") + "</td>" +
//                    "<td>" + (entry.status=="draft"?"草稿":(entry.status=="doing"?"测评中":"完成")) + "</td><td>" + entry.startTime + "</td><td>" + entry.endTime+"</td><td>";
                tableItem += "<td>" + (entryIndex + 1) + "</td>" +
     			 "<td>" +	nullString(entry.staffCode,11) +"</td>"+  //人员编码
     			 "<td>" +	nullString(entry.loginName,12) +"</td>"+  //工号
     			 "<td>" +	nullString(entry.staffName,3) +"</td>"+  //姓名
     			"<td>"+ entry.unit +"</td>"+  //单位
     			 "<td>" + entry.dept + "</td>" + //部门
     			 
         "<td>" + getStaffTypeName(entry.staffType) + "</td>"+ //员工性质
//         "<td>" + entry. + "</td>"+  //
         "<td>" + getManualEntryName(entry.manualEntry) + "</td>"+  // 人工录入
         "<td>" + nullString(entry.remark,10) + "</td>"+  //备注
         "<td>";

                tableItem += "&nbsp;<input type=\"button\" name=\"button\" id=\"edit\" value=\"编辑\" onclick=\"window.open('" + firUrl + "/staff/edit?id=" + entry.id + "');\" \>";
                
                //tableItem += "&nbsp;<input type=\"button\" name=\"button\" id=\"edit\" value=\"查看\" onclick=\"window.open('" + firUrl + "/cpqMain/cpqMainDetail?id=" + entry.id + "');\" \>";
                if(entry.manualEntry=="YES"){
                    tableItem += "&nbsp;<input mainId='"+entry.id+"'  type=\"button\" name=\"button\" class=\"del\" value=\"删除\"');\" \>";
                }

                tableItem += "</td>";
                $(".table_1").append(tableItem);
            });


           
            
        }
        $.setPage(data.result);
    }

    
};


$.postMainDetail = function (firUrl, id) {

    if (firUrl != "") {
        $.ajax({
            
            url: firUrl + "/basicCrud/iasStaff/get/" + id,
            dataType :'json',
            type: 'post',
            success: function (data, status, xhr) {

                if (status == "success" && data != null) {
                    if (data.success == true && data.result && JSON.stringify(data.result) != "[]") {
                        var entry = data.result;
           				
                        $('#id').attr("value",entry.id);
                        $("#title").html("考勤人员修改");
                        $('#removed').attr("value",entry.removed);
                        $('#idCard').attr("value",entry.idCard);
           	            $('#staffName').attr("value",entry.staffName);
                        $('#staffCode').attr("value",entry.staffCode);

           				$('#loginName').attr("value",entry.loginName);
           				
           				if ( "male" ==entry.gender){
           					$("input[name=gender]:eq(0)").attr("checked",'checked');
           				}else{
           					$("input[name=gender]:eq(1)").attr("checked",'checked');
           				}

           				$('#unit_label').attr("value",entry.unit);
                        $('#dept_label').attr("value",entry.dept);
           				$("option[value="+entry.staffType+"]").attr("SELECTED","SELECTED");
           				//$("option[value="+entry.unitCode+"]").attr("SELECTED","SELECTED");
           				//$("option[value="+entry.deptCode.substring(0,4)+"]").attr("SELECTED","SELECTED");         					
           				$("option[value="+entry.manualEntry+"]").attr("SELECTED","SELECTED"); 
           				if("admin" != role){           				
           					$("#manualEntry_select").attr("disabled","disabled");
           				}
           				$('#manualEntry').attr("value",entry.manualEntry);
           				$('#unit').attr("value",entry.unit);
           				$('#dept').attr("value",entry.dept);
           				$('#unitCode').attr("value",entry.unitCode);
           				$('#deptCode').attr("value",entry.deptCode);
				           
           				$('#inTime').attr("value",entry.inTime);
           				//$('#outTime').attr("value",entry.outTime);
           				$('#remark').attr("value",entry.remark);
                      
                    }
                }
            },
            error: function () {
                alert("对不起！你的请求出现异常，请联系服务人员！");
            }
        });
    }
    
    
};


$.postSaveMain = function (Url, formName, otherData, toUrl) {
    var _data = valueConvert($("#" + formName).serialize());
    //alert(_data + otherData);
    $(":button").attr("disabled",true);
    if (Url != "") {
        $.ajax({
        	async:false,
            url: Url + "/basicCrud/iasStaff/save",
            type: 'post',
            dataType : 'json',
            data: _data + otherData,
            success: function (data, status, xhr) {
                if (status == "success" && data != null) {
                    if (data.success == true && data.result!=null) {
                        	alert("操作成功！");
                        if (toUrl != ""){
                            location.href = Url + toUrl + "?id=" + data.result.id;
                        }
                    	
                    }
                    else {
                        alert("操作失败");
                        $(":button").attr("disabled",false);
                       
                    }
                }
                else {
                    alert("操作失败");
                    $(":button").attr("disabled",false);
                }

            }
        });
    }

};

$.postSaveWorkflow = function (base,url1, url2,otherData) {
    $("#status").val("doing");
    var _data = $("#staffForm").serialize();
    $(":button").attr("disabled",true);
    if (url1 != null) {
        $.ajax({
            url: url1,
            type: 'post',
            dataType : 'json',
            data: _data + otherData,
            success: function (data, status, xhr) {
                if (status == "success" && data != null) {
                    if (data.success == true) {
                        $.getDeptLeader(base,data.result);
                    }
                    else {
                        alert("操作失败");
                        $(":button").attr("disabled",false);
                    }
                }
                else {
                    alert("操作失败");
                    $(":button").attr("disabled",false);
                }

            }
        });
    }
};

$.getDeptLeader = function(base,mainBo){
    var deptId = "";
    var loginName = "";
    var userName = "";
    $.ajax({
        url: base + "/ca/getDeptSingleLeader",
        type: 'post',
        dataType : 'json',
        data: {"deptId":mainBo.testDept},
        success: function (data, status, xhr) {
            if (status == "success" && data != null) {
               count = mainBo.testDept.split(",").length;
               for(var key in data){
                   deptId = key;
                   if(deptId == "2959"){
                       loginName = "G060000000022959";
                       userName = "苏致雄";
                   }else if(deptId == "2921"){
                       loginName = "G010010000052921";
                       userName = "王伟雯";
                   }else{
                       loginName = (data[key].split(","))[0];
                       userName = (data[key].split(","))[1];
                   }

                   $.addWorkflow(base,mainBo,deptId,loginName,userName);
               }
            }
            else {
                alert("操作失败");
                $(":button").attr("disabled",false);
            }

        }
    });
}
var count = 0;
var counts = 0;
$.addWorkflow = function(base,mainBo,deptId,loginName,userName){
    $.ajax({
        url: base + "/basicCrud/cpqWorkflow/save",
        type: 'post',
        dataType : 'json',
        data: {
            "cpqId":mainBo.id,
            "testDept":deptId,
            "initiator":mainBo.creator,
            "initiatorLoginname":mainBo.creatorLoginName,
            "initiateTime":new Date().getTime(),
            "handler":userName,
            "handlerLoginname":loginName,
            "status":"assign"
        },
        success: function (data, status, xhr) {
            if (status == "success" && data != null) {
                if(data.success == true){
                    counts ++;
                    if(count == counts){
                        alert("操作成功！");
                        window.opener = null;
                        window.open("","_self");
                        window.close();
                    }
                }
            }
            else {
                alert("操作失败");
                $(":button").attr("disabled",false);
            }

        }
    });
}

$.validateForm = function () {
    var _return = "";
    if ($("#staffName").val() == "" || $("#staffName").val() == null) {
        _return += "\r\n姓名不能为空";
    }

    if ($("#staffCode").val() == "" || $("#staffCode").val() == null) {

        _return += "\r\n人员编码不能为空";
    }
    
    if ($("#inTime").val() == "" || $("#inTime").val() == null) {

        _return += "\r\n调入时间不能为空";
    }


//    if ($("#inTime").val() != "" && $("#inTime").val() != null
//        && $("#outTime").val() != "" && $("#outTime").val() != null
//        && $("#outTime").val() <= $("#inTime").val()) {
//
//        _return += "\r\n调出时间不能先于调入时间";
//    }

    if ($("#unit").val() == "" || $("#unit").val() == null) {

        _return += "\r\n请选择单位";
    }

    if ($("#dept").val() == "" || $("#dept").val() == null) {

        _return += "\r\n请选择部门";
    }

    if ($("#staffType").val() == "" || $("#staffType").val() == null) {

        _return += "\r\n请选择员工性质";
    }

    if ($("input[name='gender']:checked").val() == "" || $("input[name='gender']:checked").val() == null) {

        _return += "\r\n请选择性别";
    }
    
    return _return;
};
	
function valueConvert(str){
	var strs= str.split("&");
	var newStr="";
	$.each(strs,function(index,infs){
		var inf = infs.split("=");		
		if(inf[0]!="id"||inf[1]!=""){				
			if(newStr!=""){
				newStr+="&";
			}
			var value=inf[1];
			if(inf[0]=="inTime"||inf[0]=="outTime"){
				var time=$("#"+inf[0]).val();
				
				time=time.replace(new RegExp("-","gm"),"/");
				value=new Date(time).getTime();
				
			}
			newStr+=inf[0]+"="+value;
		}
		  
		
	});
	
	return newStr;
	
}
