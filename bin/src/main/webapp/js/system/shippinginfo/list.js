//$('#irradtime').Zebra_DatePicker();
var editfalg = "";
$(function() {

	$("#search").click("click", function() {// 绑定查询按扭
		search(0, "true");
	});
	$("#pre").click("click", function() {
		dopre();
	});
	$("#next").click("click", function() {
		donext();
	});
	$("#checkall").click("click", function() {
		allSelect();
	});
	$("#editcargo").click("click", function() {
		editfalg = "edit";
		
	    var dialogParent = $("#adddialog").parent();  
	    var dialogOwn = $("#adddialog").clone();  
	    dialogOwn.hide();  
		$("#adddialog").dialog({
			autoOpen : false,// 设置对话框打开的方式 不是自动打开
			show : "bind",
			hide : "explode",
			modal : true,
			height : 500,
			width : 450,
			buttons : {
				'保存' : function() {
					if("add"==editfalg){
						if (addorg()) {
							$(this).dialog("close");
							search(0, "true");
						}
				     }
					else if("edit"==editfalg){
						if(saveEditCargo()){
							$(this).dialog("close");
							search(0, "true");
						}
					}
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			},
			open : function(ev, ui) {
				if("edit"==editfalg){
					initeditcargo();
				}
				// CommnUtil.cleanInputValue("addcargoname,org,irradtype,irradtime,timeorg");
			},
			close : function(ev, ui) {
				$("#addorgname").attr('disabled',false);
				CommnUtil.cleanInputValue("addorgname,mask");
				dialogOwn.appendTo(dialogParent);  
				$(this).dialog("destroy").remove(); 
			}
		});
	//?	
		if(CommnUtil.isHaveSelectOneCheckbox("orginfos")){
			$('#adddialog ').dialog('open');
		}
		else{
			alert("只能选择一个单位！");
		}
	});
	$("#deleteorg").click("click", function() {
		deletecargo();
	});
	$('#addcargo').click(function() {
		editfalg = "add";
	    var dialogParent = $("#adddialog").parent();  
	    var dialogOwn = $("#adddialog").clone();  
	    dialogOwn.hide();  
		$("#adddialog").dialog({
			autoOpen : false,// 设置对话框打开的方式 不是自动打开
			show : "bind",
			hide : "explode",
			modal : true,
			height : 500,
			width : 450,
			buttons : {
				'保存' : function() {
					if("add"==editfalg){
						if (addcargo()) {
							$(this).dialog("close");
							search(0, "true");
						}
				     }
					else if("edit"==editfalg){
						if(saveEditCargo()){
							$(this).dialog("close");
							search(0, "true");
						}
					}
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			},
			open : function(ev, ui) {
				if("edit"==editfalg){
					initeditcargo();
				}
				// CommnUtil.cleanInputValue("addcargoname,org,irradtype,irradtime,timeorg");
			},
			close : function(ev, ui) {
				$("#addorgname").attr('disabled',false);
				CommnUtil.cleanInputValue("addorgname,mask");
				dialogOwn.appendTo(dialogParent);  
				$(this).dialog("destroy").remove(); 
			}
		});
		
		$('#adddialog').dialog('open');
	});
});

function search(pagenow, isfromsearch) {
	var data = CommnUtil.normalAjax("/system/shippinginfo/findByPageofShipping.do",
			"pageNow=" + pagenow + "&pageSize=20&isfromsearch=" + isfromsearch
					+ "&orgname=" + $("#orgname").val(), "json");
	if ("null" != data && 'undefined' != data) {
		var html = "";
		for (var i = 0; i < data.length; i++) {
			// alert(data[i].id+"@_@"+data[i].cargoname+"@_@"+data[i].org+"@_@"+data[i].irradtype+"@_@"+data[i].irradtime+"@_@"+data[i].timeorg);
			html = html
					+ "<tr>"
					+ "<td style='text-align: center'><input type='checkbox' name='orginfos' value='"+ data[i].id + "@_@" + data[i].orgname + "' /> </td>"
					+ "<td style='text-align: center'>" + data[i].orgname + " </td>" 
					+ "<td style='text-align: center'>"+ data[i].mask + " </td>" 
					+ "</tr>";
		}
		$("#orginfos").html(html);
		if("true"==isfromsearch){
			setPageInfo(0);
		}
	}
}

function setPageInfo(pagenow) {
	var data = CommnUtil.normalAjax("/system/shippinginfo/getCountofShipinfo.do","orgname=" + $("#orgname").val(), "json");
	if ("null" != data && 'undefined' != data) {
		var pagerecode = data;
/*		alert(pagenow + " : " + pagerecode + " : "
				+ Math.ceil(parseInt(pagerecode) / 20));*/
		var totalpage = Math.ceil(parseInt(pagerecode) / 20);
		if (pagenow < 0 || pagenow >= totalpage) {
			return false;
		}
		$("#pagerecode").text(pagerecode);
		$("#pageNow").text(pagenow);
		$("#totalpage").text(totalpage);
	}
	return true;
}

function dopre() {
	var pageNow = $("#pageNow").text().trim();
	if (setPageInfo(parseInt(pageNow) - 1)) {
		search(parseInt(pageNow) - 1, "false");
	}
}
function donext() {
	var pageNow = $("#pageNow").text().trim();
	if (setPageInfo(parseInt(pageNow) + 1)) {
		search(parseInt(pageNow) + 1, "false");
	}
}
function initeditcargo() {
		var idAndName = CommnUtil.getCheckBoxValueOfSelect("orginfos");
		var data = CommnUtil.normalAjax("/system/shippinginfo/getOneShip.do" , "idAndName="+idAndName , "json");
		if(CommnUtil.notNull(data)){
			$("#addorgname").attr('disabled',true);
			$("#addorgname").val(data.orgname);
			$("#mask").val(data.mask);
		}
}

function saveEditCargo(){
	if (!CommnUtil.notNull($("#addorgname").val())) {
		alert("计量单位必须填写！");
		return false;
	}
	var data = CommnUtil.normalAjax("/system/shippinginfo/saveEditShip.do",
			"addorgname=" + $("#addorgname").val() + 
			"&mask=" + $("#mask").val() ,
			"json");
	if (CommnUtil.notNull(data) && "ok" == data) {
		alert("编辑成功！");
		return true;
	} else {
		alert("编辑失败！\n 错误信息：\n " + data);
		return false;
	}
	return false;
}

function addcargo() {
	if (!CommnUtil.notNull($("#addorgname").val())) {
		alert("计量单位名称必须填写！");
		return false;
	}
	/*
	if(!validatetime($("#irradtime").val())){
		alert("时间输入格式不正确(格式:yyyy-mm-dd)！");
		return false;
		}
		*/
	
//?????
	var data = CommnUtil.normalAjax("/system/shippinginfo/doAddShip.do",
			"addorgname=" + $("#addorgname").val() + 
			"&mask=" + $("#mask").val() ,
			"json");
	if (CommnUtil.notNull(data) && "ok" == data) {
		alert("添加成功！");
		return true;
	} else {
		alert("添加失败！\n 错误信息：\n " + data);
		return false;
	}
	return false;
}
function deleteorg() {
	if(CommnUtil.isHaveSelectOneCheckbox("orginfos")){
		if(confirm("确认删除计量单位吗？")){
			var data = CommnUtil.normalAjax("/system/shippinginfo/deleteShip.do", "idAndName="+CommnUtil.getCheckBoxValueOfSelect("orginfos"), "json");
			if(CommnUtil.notNull(data) && "ok" == data){
				search(0, "true");
			}
			else{
				alert("删除失败!\n错误信息:\n"+data);
			}
		}
	}else{
		alert("请选择一个计量单位！");
	}
}

// ==========================================================添加========================================================
function validatetime(intime){
	var rex = /^(\d{4})\-(\d{2})\-(\d{2})$/;
	if(CommnUtil.notNull(intime)&&rex.test(intime)){
		return true;
	}
	return false;
}
//全选，全不选
function allSelect() {
    if ($("#checkall").attr("checked") != "checked") {
        $("#checkall").removeAttr("checked", "checked");
    }
    else {
        $("#checkall").attr("checked");
    }
}
//反选
function otherSelect() {
    $(":checkbox").each(function () {
        if ($(this).attr("checked") == "checked") {
            $(this).removeAttr("checked");
        }
        else {
            $(this).attr("checked", "checked");
        }
    });
}
