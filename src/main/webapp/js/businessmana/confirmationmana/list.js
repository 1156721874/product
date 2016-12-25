/**
 * 对象转成json
 * //对象转成json  
    var thing = {plugin: 'jquery-json', version: 2.3};//js对象  
     var str = $.toJSON(thing);//转换为json,结果: '{"plugin":"jquery-json","version":2.3}'  
     
     json转成对象
     //json转成对象  
	var  obj= $.evalJSON(str);  
	var name=obj.plugin;//js对象.属性,结果: "jquery-json"  
	var version =obj.version;//结果: 2.3  
 */
//次全局变量的作用是，保存当前获取的详细信息的Id，当编辑或者删除详情时，得到此全局刷新详情区域
var globalreceiveorgid = "";

$(function() {
	$('#receivetime').Zebra_DatePicker();
	$("#query").click("click", function() {
		search(0,"true");
	});
	$("#deleteconfirm").click("click", function() {
		deleteconfirm();
	});
	$("#pre").click("click", function() {
		dopre();
	});
	$("#next").click("click", function() {
		donext();
	});
	$("#editdetail").click("click", function() {
		initEditConfirmInfo();
		//解释：http://blog.csdn.net/flutterkey/article/details/9114723
	    var dialogParent = $("#editconfirmdetaildiv").parent();  
	    var dialogOwn = $("#editconfirmdetaildiv").clone();  
	    dialogOwn.hide();  
		//此处二次注册弹出框的目的：经测试jquery的dialog插件存在问题，只能重新注册，但是前边的不能删除，否则不能运行
		$("#editconfirmdetaildiv").dialog({
			autoOpen : false,// 设置对话框打开的方式 不是自动打开
			show : "bind",
			hide : "explode",
			modal : true,
			height : 500,
			width : 450,
			title: "编辑",
			buttons : {
				'保存' : function() {
					var fee = $("#fee").val();
					if(fee != null && fee != undefined && fee != "undefined" && fee != ""){
						if(isNaN(fee)){
							$("#fee").attr("style","border-color: red");
							alert("费用必须填写并且必须是数字！");
							return ;
						}
					}else{
						$("#fee").attr("style","border-color: red");
						alert("费用必须填写并且必须是数字！");
						return ;
					}
					if(!CommnUtil.isnumber("cargocount,cargoweight,funguscount,reqreagent,irradtime")){
						alert("标红部分必须是数字！");
						return;
					}
					saveEditdetailInfo();
					$(this).dialog("close");
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

		processeditdetail();
	});
	$("#checkalldetail").click("click", function() {
		allSelect();
	});
	$("#deletedetail").click("click", function() {
		deletedetail();
	});
	
	$("#searchorg").autocomplete({
		source:function (request,response){
			$.ajax({
				url:"business/receivingmana/queryorgs.do",
				dataType:"json",
				data:{
					query:encodeURI($("#searchorg").val())
				},
				success:function(data){
					response($.map(data,function(item){
						return item.dictid+":"+item.dictname;
					}));
				}
			});
		},
		minlength:0,
		minChars :0,
		cacheLength :1
	});
	
	search(0,"true");
});


function search(pagenow, isfromsearch) {
	var confirmover = CommnUtil.checkedBoxSetValueAndReturnValue("confirmover","1","0");
	var data = CommnUtil.normalAjax("/business/receivingmana/queryconfirms.do",
			        "pageNow=" + pagenow + "&pageSize=20&isfromsearch="
			        + isfromsearch
					+ "&receivetime=" + $("#receivetime").val()
					+ "&showorgs=" + $("#searchorg").val().split(":")[0]
					+ "&showBringTakeInfos=" + $("#showBringTakeInfos").val() 
					+ "&cargoname=" + $("#cargoname").val()
					+ "&confirmover="+confirmover,
					"json");
	//data = $.evalJSON(data);
	if ("null" != data && 'undefined' != data) {
		var html = "";
		if("true"==isfromsearch){
			$("#pagerecode").text(data.pagerecode);
			$("#pageNow").text(0);
			$("#totalpage").text(data.totalpage);
		}else{
			var totalpage = Math.ceil(parseInt(data.pagerecode) / 20);
			if (pagenow < 0 || pagenow >= totalpage) {
				return ;
			}
			$("#pagerecode").text(data.pagerecode);
			$("#pageNow").text(pagenow);
			$("#totalpage").text(data.totalpage);
		}
		for (var i = 0; i < data.confirms.length; i++) {
			// alert(data[i].id+"@_@"+data[i].cargoname+"@_@"+data[i].org+"@_@"+data[i].irradtype+"@_@"+data[i].irradtime+"@_@"+data[i].timeorg);
			var currentcolor = data.confirms[i].status == '2'? "style='background-color: silver'" :"";
			html = html
					+ "<tr "+currentcolor+" status='"+data.confirms[i].status+"' ondblclick='gofingerprints("+data.confirms[i].id+","+data.confirms[i].status+");' onclick='getDetailInfo("+data.confirms[i].id+",this);'>"
					+ "<td style='text-align: center'><input type='checkbox' name='confirmscheck' value='"+ data.confirms[i].id + "' /> </td>"
					+ "<td style='text-align: center'>" + data.confirms[i].receivetime + " </td>" 
					+ "<td style='text-align: center'>"+ data.confirms[i].receiveorgname + " </td>"
					+ "<td style='text-align: center'>"+ data.confirms[i].receivepeoplename + " </td>"
					+ "<td style='text-align: center'>"+ data.confirms[i].telnum + " </td>"
					+ "</tr>";
		}
		$("#cargoesbody").html(html);
		if(data.confirms.length>0){
			getDetailInfo(data.confirms[0].id,null);
		}
	}
}

function dopre() {
	var pageNow = $("#pageNow").text().trim();
	search(parseInt(pageNow) - 1, "false");
}

function donext() {
	var pageNow = $("#pageNow").text().trim();
	search(parseInt(pageNow) + 1, "false");
}

function deleteconfirm(){
	if(confirm("确认要删除吗？")){
		if(CommnUtil.isHaveSelectMoreCheckbox("confirmscheck")){
			var boxvalue = CommnUtil.getAllCheckBoxesValue("confirmscheck");
			var data = CommnUtil.normalAjax("/business/receivingmana/deleteconfirms.do","ids="+boxvalue, "json");
			if("ok"==data){
				alert("删除成功。");
				search(0,"true");
			}
			else{
				alert(data);
			}
		}else{
			alert("请选择一条记录");
		}
	}
} 

function getDetailInfo(receiveorgid,obj){
	globalreceiveorgid = receiveorgid;
	if(null!=obj){
		$("#cargoesbody").find("tr").attr("style","");
		if($(obj).attr("status")=='2'){
			$(obj).attr("style","background-color: silver");
		}else{
			$(obj).attr("style","background-color: red");
		}
	}
	var data = CommnUtil.normalAjax("/business/receivingmana/getReceivedDetailInfo.do","receiveorgid="+receiveorgid, "json");
	var html = "";
	if(CommnUtil.notNull(data)){
		for(var i=0;i<data.length;i++){
			html = html
			+ "<tr>"
			+ "<td style='text-align: center'><input type='checkbox' name='confirmsDetailcheck' value='"+ data[i].id +"@_@"+ data[i].receivemgrid +"@_@"+data[i].status+ "' /> </td>"
			+ "<td style='text-align: center'>" + data[i].cargoname + " </td>" 
			+ "<td style='text-align: center'>" + data[i].cargocount + " </td>" 
			+ "<td style='text-align: center'>" + data[i].countorg + " </td>" 
			+ "<td style='text-align: center'>" + data[i].cargoweight + " </td>" 
			+ "<td style='text-align: center'>" + data[i].fee + " </td>" 
			+ "<td style='text-align: center'>" + data[i].irradtype + " </td>" 
			+ "<td style='text-align: center'>" + data[i].irradtime + " </td>" 
			+ "<td style='text-align: center'>" + data[i].irradtimeorg + " </td>" 
			+ "<td style='text-align: center'>" + data[i].reqreagent + " </td>" 
			+ "<td style='text-align: center'>" + data[i].funguscount + " </td>" 
			+ "<td style='text-align: center'>" + data[i].irradflag + " </td>" 
			+ "</tr>";
		}
	}
	$("#detailTable").html(html);
}

function initEditConfirmInfo(){
	var idAndreceiveorgid = CommnUtil.getCheckBoxValueOfSelect("confirmsDetailcheck");
	var data = CommnUtil.normalAjax("/business/receivingmana/editDetailInfo.do","idAndreceiveorgid="+idAndreceiveorgid, "json");
	if(CommnUtil.notNull(data)){
	  $("#cargoname").find("option[value='"+data.cargoid+"']").attr("selected",true);
	  //$("#cargocount").val(data.cargocount);
	  document.getElementById("cargocount").value=data.cargocount;
	  //$("#cargoweight").val(data.cargoweight);
	  document.getElementById("cargoweight").value=data.cargoweight;
	  $("#showcountorginfos").find("option[value='"+data.countorg+"']").attr("selected",true);
	  //$("#funguscount").val(data.funguscount);
	  document.getElementById("funguscount").value=data.funguscount;
	  //$("#reqreagent").val(data.reqreagent);
	  document.getElementById("reqreagent").value=data.reqreagent;
	  $("#irradtypes").find("option[value='"+data.irradtype+"']").attr("selected",true);
	  //$("#irradtime").val(data.irradtime);
	  document.getElementById("irradtime").value=data.irradtime;
	  document.getElementById("fee").value=data.fee;
	  $("#timeorgs").find("option[value='"+data.irradtimeorg+"']").attr("selected",true);//时间单位
	  $("#irradflags").find("option[value='"+data.irradflag+"']").attr("selected",true);
	  document.getElementById("mask").value=data.mask;
	}
}

function processeditdetail(){
	if(CommnUtil.isHaveSelectOneCheckbox("confirmsDetailcheck")){
		$('#editconfirmdetaildiv').dialog('open');
	}else{
		alert("请选择而且只能选择一条详情记录！");
	}
}

function saveEditdetailInfo(){
	var cargoname = $("#cargoname").val();
	var cargocount = $("#cargocount").val();
	var cargoweight = $("#cargoweight").val();
	var showcountorginfos = $("#showcountorginfos").val();
	var funguscount = $("#funguscount").val();
	var reqreagent = $("#reqreagent").val();
	var irradtypes = $("#irradtypes").val();
	var irradtime = $("#irradtime").val();
	var timeorgs = $("#timeorgs").val();
	var irradflags = $("#irradflags").val();
	var mask = $("#mask").val();
	var fee = $("#fee").val();
	var idAndreceiveorgid = CommnUtil.getCheckBoxValueOfSelect("confirmsDetailcheck");
	var data = CommnUtil.normalAjax("/business/receivingmana/saveEditConfirDetailInfo.do",
			"cargoname="+cargoname
			+"&cargocount="+cargocount
			+"&cargoweight="+cargoweight
			+"&showcountorginfos="+showcountorginfos
			+"&funguscount="+funguscount
			+"&reqreagent="+reqreagent
			+"&irradtypes="+irradtypes
			+"&irradtime="+irradtime
			+"&timeorgs="+timeorgs
			+"&irradflags="+irradflags
			+"&idAndreceiveorgid="+idAndreceiveorgid
			+"&fee="+fee
			+"&mask="+mask,
			"json");
	if("ok"==data){
		alert("修改成功！");
		getDetailInfo(globalreceiveorgid);
	}
	else{
		alert(data);
	}
	
}

function deletedetail(){
	if(CommnUtil.isHaveSelectOneCheckbox("confirmsDetailcheck")){
		if(confirm("您确定要删除吗？")){
			var ids = CommnUtil.getCheckBoxValueOfSelect("confirmsDetailcheck");
			var data = CommnUtil.normalAjax("/business/receivingmana/deleteConfirDetailInfo.do","ids="+ids, "json");
			if("ok"==data){
				alert("删除成功！");
				getDetailInfo(globalreceiveorgid,null);
			}else{
				alert(data);
			}
		}
	}else{
		alert("请选择【一条】详情！");
	}
}

function gofingerprints(id,currentstatus){
	//alert("此处通过单位id:"+id+"到后台取得指纹仪录入的指纹进行验证，验证完毕将receivemgrbase表的status置为2，附属的详情表receivemgrdetail的状态改为2");
	if(confirm("是否完成收货确认?")){
		 if(currentstatus=='2'){
			 $("#confirmdialog").attr("style","background-color: silver");
		 }
		 $("#confirmpicture").attr("src","business/receivingmana/geprint.do?id="+id+"&date="+new Date());
		
	    var dialogParent = $("#confirmdialog").parent();  
	    var dialogOwn = $("#confirmdialog").clone();  
	    dialogOwn.hide();  
		//此处二次注册弹出框的目的：经测试jquery的dialog插件存在问题，只能重新注册，但是前边的不能删除，否则不能运行
		$("#confirmdialog").dialog({
			autoOpen : false,// 设置对话框打开的方式 不是自动打开
			show : "bind",
			hide : "explode",
			modal : true,
			height : 500,
			width : 500,
			title: "收货确认",
			buttons : {
				'保存' : function() {
					saveEditInfo(id,currentstatus);
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			},
			open : function(ev, ui) {
				var info = CommnUtil.normalAjax("/business/receivingmana/editconfirm.do","id="+id, "json");
				 $("#receivecargotime").val(info.receivetime);
				 $("#bringcargopeopletel").val(info.telnum);
				 $("#bringpeople").find("option[value='"+info.receivepeopleid+"']").attr("selected",true);
				 $("#bringorg").find("option[value='"+info.receiveorgid+"']").attr("selected",true);
			},
			close : function(ev, ui) {
	            dialogOwn.appendTo(dialogParent);  
	            $(this).dialog("destroy").remove(); 
			}
		});
		$("#confirmdialog").dialog("open");
	}
}

function saveEditInfo(id,currentstatus){
	var data = CommnUtil.normalAjax("/business/receivingmana/updateConfirmStatus.do",
			"id="+id+"&receivecargotime="+$("#receivecargotime").val()+"&bringcargopeopletel="+$("#bringcargopeopletel").val()+"&bringpeople="+$("#bringpeople").val()+"&bringorg="+$("#bringorg").val()+"&currentstatus="+currentstatus,
			"json");
	if("ok"==data){
		alert("更新成功！");
		search(0,"true");
	}else{
		alert("更新失败！"+data);
	}
}


//全选，全不选
function allSelect() {
    if ($("#confirmsDetailcheck").attr("checked") != "checked") {
        $("#confirmsDetailcheck").removeAttr("checked", "checked");
    }
    else {
        $("#confirmsDetailcheck").attr("checked");
    }
}