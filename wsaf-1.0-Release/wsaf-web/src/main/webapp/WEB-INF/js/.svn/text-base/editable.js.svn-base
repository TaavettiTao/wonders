/*
code:    editable.js  
version: v1.0
date:    2011/10/21
author:  lyroge@foxmail.com
usage:
$("table").editable({   selector 可以选择table或者tr
head: true,         是否有标题
headcss:标题样式，在head: true下有效
noeditcol: [1, 0],  哪些列不能编辑

编辑列配置：colindex：列索引
edittype：编辑时显示的元素 0：input 1：checkbox 2：select
multiSelect:多选下拉input，必须指定edittype为0: [{ colindex: 3, edittype: 0,multiSelect:true}]
ctrid：关联元素的id  如edittype=2， 那么需要设置select的元素
css:元素的样式
editcol: [{ colindex: 2, edittype: 2, ctrid: "sel",css:""}],
onok: function () {                                              
return true;    根据结果返回true or false                     
},
ondel: function () {
return true;    根据结果返回true or false
}
});
*/

(function ($) {
    $.fn.editable = function (options) {
        options = options || {};
        opt = $.extend({}, $.fn.editable.defaults, options);

        trs = [];
        $.each(this, function () {
            if (this.tagName.toString().toLowerCase() == "table") {
                $(this).find("tr").each(function () {
                    trs.push(this);
                });
            }
            else if (this.tagName.toString().toLowerCase() == "tr") {
                trs.push(this);
            }
        });

        $trs = $(trs);
        $modname = $trs.parents(".userMods").attr("id");
        if ($trs.size() == 0 || (opt.head && $trs.size() == 1))
            return false;


        $trs.each(function (i, tr) {
            $tds = $(tr).find("td");
            var $id = $($tds[0]).find("input").val();
            if (opt.head && i == 0) {
                $(tr).append("<th class='" + opt.headcss + "'></th>");
                return true;
            }
            else if (opt.foot && i == $trs.length-1) {
            	$(tr).append("<th></th>");
            	return true;
            }
            var a = "<a href='#' class='" + opt.editcss + "'>编辑</a> <a href='#' class='" + opt.delcss + "'>删除</a><a href='#' class='" + opt.onokcss + "'>确定</a> <a href='#' class='" + opt.canclcss + "'>取消</a>&nbsp;";
            if(opt.opercol != undefined){
         	   $.each(opt.opercol, function (i, obj) {
         		   if(obj.onclick != undefined){
         			   a+=("<a href=\"javascript:void(0);\" onclick=\"javascript:" + obj.onclick + "('rule','" + $id + "');\" class='" + obj.operclass + "'>" + obj.opername + "</a>&nbsp;");
         		   }else{
         			   a+=("<a href='" + obj.href + $id+ "' target=\"_blank\" class='" + obj.operclass + "'>" + obj.opername + "</a>&nbsp;");
         		   }
         	   });
	         }
	         var button = "<td>"+a+"</td>";
         
            $(tr).append(button);
        });

        $trs.find(".onok, .cancl").hide();
        $trs.find(".edit").click(function (e) {
            $tr = $(this).closest("tr");
            $model = $tr.parents(".userMods")[0].id;
            $tds = $tr.find("td");
            
        	var $id = $($tds[0]).find("input").val();
            
            
            $.each($tds.filter(":lt(" + ($tds.size() - 1) + ")"), function (i, td) {
                if ($.inArray(i, opt.noeditcol) != -1)
                    return true;
                var t = $.trim($(td).text());
                if (opt.editcol != undefined) {
                    $.each(opt.editcol, function (j, obj) {
                        if (obj.colindex == i) {
                            css = obj.css ? "class='" + obj.css + "'" : "";
                            if (obj.edittype == undefined || obj.edittype == 0) {
                                $(td).data("v", t);
                                $(td).html("<input type='text' value='" + t + "' " + css + " />");
                                
                                if(obj.edittype == 0 && obj.multiSelect==true){
                                var $id = $($tds[0]).find("input").val();
                              	var ruleType = getRuleType($id);
                                var relationObj = ruleType.objIds;
//                                    initObjectInfoSelectWithChecked(relationObj);
                             	   $.ajax({
                            		   type: 'GET',
                            		   url: getBasePath()+'/api/objInfo',
                            		   dataType:'json',
                            		   async:false,
                            		   error:function(){alert('系统连接失败，请稍后再试！');},
                            		   success: function(result){
                            			   if(result.info.success){
                            				   var $input = $(td).find("input");
                            				   for(var i=0;i<result.data.length;i++){
                            					   if((','+relationObj+',').indexOf(','+result.data[i].id+',')>-1){
                            						   $($input).append("<option value= '"+result.data[i].id+"' selected=\"selected\">"+result.data[i].name+"</option>");	
                            					   }else{
                            						   $($input).append("<option value= '"+result.data[i].id+"'>"+result.data[i].name+"</option>");	
                            					   }
//                            					$("#relationObj").append("<option value= '{id:"+result.data[i].id+",entity:"+result.data[i].type+"}'>"+result.data[i].name+"</option>");	
                            				   }
                            				   $($input).multiSelect({
                            					   selectAllText: '全选',  
                            					   noneSelected: '请选择',
//                            					oneOrMoreSelected: '% options checked',
                            					   oneOrMoreSelected: '*',
                            				   });
                            				   
                            				   var $a = $(td).find("a");
                            				   $($a).css("height","21px");
                            			   }
                            		   }	 
                            	   });
                              }
                            }
                            else if (obj.edittype == 2) {    //select
                                if (obj.ctrid == undefined) {
                                    alert('请指定select元素id ctrid');
                                    return;
                                }
                                $(td).empty().append($("#" + obj.ctrid).clone().show());
                                $(td).find("option").filter(":contains('" + t + "')").attr("selected", true);
                            }
                            /* 可以在此处扩展input、select以外的元素编辑行为 */
                        }
                    });
                }
                else {
                    $(td).data("v", t);
                    $(td).html("<input type='text' value='" + t + "' />");
                }
            });
            
            $tr.find(".onok, .cancl, .edit, .del").toggle();
            return false;
        }); 

        $trs.find(".del").click(function () {
            $tr = $(this).closest("tr");
            var model = $tr.parents(".userMods")[0].id;
        	$tds = $tr.find("td");
        	var $id = $($tds[0]).find("input").val();
            if (opt.ondel(model,$id)) {
                $tr.remove();
            }
            return false;
        });

        $trs.find(".onok").click(function (e) {
        	if (opt.onok(e)) {
        		$.each($tds.filter(":lt(" + ($tds.size() - 1) + ")"), function (i, td) {
        			if ($.inArray(i, opt.noeditcol) != -1)
                          return true;
        			
        			var c = $(td).children().get(0);
        			if (c != null)
        				if (c.tagName.toLowerCase() == "select") {
        					$(td).html(c.options[c.selectedIndex].text);
        				}
        				else if (c.tagName.toLowerCase() == "input" && c.type != "checkbox") {
        					$(td).html(c.value);
        				}else if (c.tagName.toLowerCase() == "a") {//针对下拉多选
                        	if($(c).find("span").length != 0){
                        		$(td).html($(c).find("span").text());
                        	}
                        }
        			/* 可以在此处扩展input、select以外的元素确认行为 */
        		});
        		$tr.find(".onok, .cancl, .edit, .del").toggle();
        	}
        	return false;
        });

        $trs.find(".cancl").click(function () {
            $tr = $(this).closest("tr");
            $tds = $tr.find("td");
        	var $a = $tr.find("a.multiSelect");
        	var objTypes = $a.find("span").text(); 
            $.each($tds.filter(":lt(" + ($tds.size() - 1) + ")"), function (i, td) {
                var c = $(td).children().get(0);
                if (c != null)
                    if (c.tagName.toLowerCase() == "select") {
                        $(td).html(c.options[c.selectedIndex].text);
                    }
                    else if (c.tagName.toLowerCase() == "input" && c.type != "checkbox") {
                        $(td).html(c.value);
                    }else if (c.tagName.toLowerCase() == "a") {//针对下拉多选
                    	if($(c).find("span").length != 0){
                    		$(td).html(objTypes);
                    	}
                    }
                /* 可以在此处扩展input、select以外的元素取消行为 */
            });
            $tr.find(".onok, .cancl, .edit, .del").toggle();
            return false;
        });
    };

    $.fn.editable.defaults = {
        head: false,
        /* 
        如果为空那么所有的列都可以编辑，并且默认为文本框的方式编辑 
        如下形式：
        {{colindex:'', edittype:'', ctrid:'', css:''}, ...}
        edittype 0:input 1:checkbox 2:select
        */
        //editcol:{},    
        /* 
        设置不可以编辑的列，默认为空 
        如下形式：
        [0,2,3,...]
        */
        noeditcol: [],
        onok: function () {
            alert("this's default onok click event");
            return true;
        },
        ondel: function () {
            alert("this's default on del click event");
            return true;
        },
        editcss: "edit",
        delcss: "del",
        onokcss: "onok",
        canclcss: "cancl"
    };
})(jQuery);