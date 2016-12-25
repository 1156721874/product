$(function (){
	initPrivilege();
	$("#add").click("click", function() {
	    var dialogParent = $("#adduserdiv").parent();  
	    var dialogOwn = $("#adduserdiv").clone();  
	    dialogOwn.hide();  
		//此处二次注册弹出框的目的：经测试jquery的dialog插件存在问题，只能重新注册，
		$("#adduserdiv").dialog({
			autoOpen : false,// 设置对话框打开的方式 不是自动打开
			show : "bind",
			hide : "explode",
			modal : true,
			height : 500,
			width : 500,
			title: "添加",
			buttons : {
				'保存' : function() {
					if(saveUser()){
						$(this).dialog("close");
					}
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			},
			open : function(ev, ui) {
				
				// CommnUtil.cleanInputValue("addcargoname,org,irradtype,irradtime,timeorg");
			},
			close : function(ev, ui) {
	            dialogOwn.appendTo(dialogParent);  
	            $(this).dialog("destroy").remove(); 
				//CommnUtil.cleanInputValue("cargoname,cargocount,cargoweight,showcountorginfos,funguscount,reqreagent,irradtypes,irradtime,timeorgs,irradflags,irradflag");
			}
		});
		$('#adduserdiv').dialog('open');
	});
	$("#edit").click("click", function() {
		goedit();
	});
	$("#delete").click("click", function() {
		
	});
});

function initPrivilege(){
	var data = CommnUtil.normalAjax("/privilegemana/configprivilege/querygprivileges.do","","json");
	if(CommnUtil.notNull(data)){
		var html ="";
		for(var i=0;i<data.length;i++){
			html = html
			+"<tr>"
			+"<td style='text-align: center'><input type='checkbox' name='allusers' value='"+data[i].userId+"'/></td>"
			+"<td style='text-align: center'>"+data[i].userName+"</td>"
			+"<td style='text-align: center'>"+data[i].rolename+"</td>"
			+getcheckedPrivileges(data[i].menuids);
		}
		$("#userprivileges").html(html);
	}
}

function getcheckedPrivileges(menuids){
	var allmenuesarr = allmenues.split(",");
	var currentmenuesarr = menuids.split(",");
	var html = "";
	for(var i=0;i<allmenuesarr.length;i++){
		var havematch=false;
		for(var j=0;j<currentmenuesarr.length;j++){
			if(allmenuesarr[i]==currentmenuesarr[j]){
				havematch=true;
				break;
			}
		}
		if(havematch){
			html+="<td style='text-align: center'><input type='checkbox' disabled  checked /></td>"
		}else{
			html+="<td style='text-align: center'><input type='checkbox' disabled  /></td>"
		}
	}
	return html;
}

function saveUser(){
	var data = CommnUtil.formAjax("/privilegemana/configprivilege/adduser.do","addform","json");
	if("ok"==data){
		alert("添加成功！");
		initPrivilege();
		return true;
	}else{
		alert(data);
		return false;
	}
}

function goedit(){
	if(CommnUtil.isHaveSelectOneCheckbox("allusers")){
		var userid = CommnUtil.getCheckBoxValueOfSelect("allusers");
		var data = CommnUtil.normalAjax("/privilegemana/configprivilege/getuserInfo.do","userid="+userid,"json");
		if(CommnUtil.notNull(data)){
			$("#userid").val(data.userId);
			$("#username").val(data.userName);
			$("#role").find("option[value='"+data.roleid+"']").attr("selected",true);
			var menuids = data.menuids;
			if(CommnUtil.notNull(menuids)){
				var menuidsarr = menuids.split(",");
				for(var i=0;i<menuidsarr.length;i++){
					$("input[name='menuesitemes'][value='"+menuidsarr[i]+"']").attr("checked",true);
				}
		     }
		}
	}else{
		alert("请选择仅且一条记录！");
		return;
	}
    var dialogParent = $("#adduserdiv").parent();  
    var dialogOwn = $("#adduserdiv").clone();  
    dialogOwn.hide();  
	//此处二次注册弹出框的目的：经测试jquery的dialog插件存在问题，只能重新注册，
	$("#adduserdiv").dialog({
		autoOpen : false,// 设置对话框打开的方式 不是自动打开
		show : "bind",
		hide : "explode",
		modal : true,
		height : 500,
		width : 500,
		title: "编辑",
		buttons : {
			'保存' : function() {
				if(saveEdit()){
					$(this).dialog("close");
				}
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		},
		open : function(ev, ui) {
			
			// CommnUtil.cleanInputValue("addcargoname,org,irradtype,irradtime,timeorg");
		},
		close : function(ev, ui) {
            dialogOwn.appendTo(dialogParent);  
            $(this).dialog("destroy").remove(); 
			//CommnUtil.cleanInputValue("cargoname,cargocount,cargoweight,showcountorginfos,funguscount,reqreagent,irradtypes,irradtime,timeorgs,irradflags,irradflag");
		}
	});
	$('#adduserdiv').dialog('open');
}

function saveEdit(){
	var data = CommnUtil.formAjax("/privilegemana/configprivilege/saveedituser.do","addform","json");
	if("ok"==data){
		alert("更新成功！");
		initPrivilege();
		return true;
	}else{
		alert("更新失败！");
		return false;
	}
}

function deleteUser(){
	if(CommnUtil.isHaveSelectMoreCheckbox("allusers")){
		if(confirm("您确定要删除吗？")){
			var ids = CommnUtil.getAllCheckBoxesValue("allusers");
			var data = CommnUtil.normalAjax("/privilegemana/configprivilege/deleteuser.do","ids="+ids, "json");
			if("ok"==data){
				alert("删除成功！");
				initPrivilege();
			}else{
				alert(data);
			}
		}
	}else{
		alert("请选择一条或多条详情！");
	}
}

function cleanvalue(){
	$("#userid").val("");
	$("#username").val("");
	$("#role").find("option[value='1']").attr("selected",true);
	$("input[name='menuesitemes']").removeattr("checked",true);
}