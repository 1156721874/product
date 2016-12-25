
//此ID是每次双击一个辐照货物时，得到的详情表的ID 
var globalstillid = "";
$(function() {
	//注册弹出框
	/*$("#editradiationdiv").dialog({
		autoOpen : false,// 设置对话框打开的方式 不是自动打开
		show : "bind",
		hide : "explode",
		modal : true,
		height : 350,
		width : 900,
		title: "编辑",
		buttons : {
			'保存' : function() {
				var irradednum= document.getElementById("irradednum").value;
				var hiddenoldirradednum= document.getElementById("hiddenoldirradednum").value;
				if(parseInt(irradednum) > parseInt(hiddenoldirradednum)){
					alert("辐照数量不能超过已有货物数量！");
					return;
				}
				if(!CommnUtil.validatetime($("#doirraddate").val())){
					alert("日期格式错误!\n 格式：yyyy-mm-dd");
					return;
				}
				if(CommnUtil.haveOneTagIsNull("doirraddate,ordernum,connecttime,firstspreadernum,irradbatchnum,entrancetime,spreadernum,runparam,runcycle,changedesc,nextcyclestarttime"))
				{
					alert("标红信息必须填写！");
					return;
				}
				saveradiation();
				$(this).dialog("close");
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		},
		open : function(ev, ui) {
			initradiation();
			// CommnUtil.cleanInputValue("addcargoname,org,irradtype,irradtime,timeorg");
		},
		close : function(ev, ui) {
			//CommnUtil.cleanInputValue("divcargoname,receiveorgid,irradednum,hiddenoldirradednum,showcountorginfos,cargoweight,irradtypes,irradtime,timeorgs");
		}
	});*/
	$('#receivetime').Zebra_DatePicker();
	$("#query").click("click", function() {
		search(0,"true");
	});
	$("#pre").click("click", function() {
		dopre();
	});
	$("#next").click("click", function() {
		donext();
	});
	search(0,"false");
	initcurrentradiations();
});

function search(pagenow, isfromsearch) {
	var data = CommnUtil.normalAjax("/business/receivingmana/queryradiation.do",
			        "pageNow=" + pagenow + "&pageSize=20&isfromsearch="
			        + isfromsearch
					+ "&receivetime=" + $("#receivetime").val()
					+ "&showorgs=" + $("#showorgs").val()
					+ "&cargoname=" + $("#cargoname").val(),
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
		for (var i = 0; i < data.radiations.length; i++) {
			// alert(data[i].id+"@_@"+data[i].cargoname+"@_@"+data[i].org+"@_@"+data[i].irradtype+"@_@"+data[i].irradtime+"@_@"+data[i].timeorg);
			//var color = "1"==data.radiations[i].status  ? "background-color: red" : "";
			html = html
					+ "<tr onclick='doselectitem(this);'  ondblclick='goeditradiation("+data.radiations[i].id+","+data.radiations[i].receivemgrid+");'>"
					+ "<td style='text-align: center'>"+ data.radiations[i].receiveorgname + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].receivetime + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].cargoname + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].cargocount + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].irradednum + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].countorg + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].cargoweight + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].irradtype + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].irradtime + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].irradtimeorg + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].reqreagent + "</td>"
					+ "<td style='text-align: center'>"+ data.radiations[i].funguscount + "</td>"
					+ "</tr>";
		}
		$("#radiationsbody").html(html);
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

function doselectitem(obj){
	$("#radiationsbody").find("tr").attr("style","");
	$(obj).attr("style","background-color: red");
}


function goeditradiation(id,receivemgrid){
	globalstillid = id+"-"+receivemgrid;
	initradiation();
	//解释：http://blog.csdn.net/flutterkey/article/details/9114723
    var dialogParent = $("#editradiationdiv").parent();  
    var dialogOwn = $("#editradiationdiv").clone();  
    dialogOwn.hide();  
	//在此初始化（存在bug）
	$("#editradiationdiv").dialog({
		autoOpen : false,// 设置对话框打开的方式 不是自动打开
		show : "bind",
		hide : "explode",
		modal : true,
		height : 350,
		width : 900,
		title: "编辑",
		buttons : {
			'保存' : function() {
				var irradednum= document.getElementById("irradednum").value;
				var hiddenoldirradednum= document.getElementById("hiddenoldirradednum").value;
				if(parseInt(irradednum) > parseInt(hiddenoldirradednum)){
					alert("辐照数量不能超过已有货物数量！");
					return;
				}
				if(!CommnUtil.validatetime($("#doirraddate").val())){
					alert("日期格式错误!\n 格式：yyyy-mm-dd");
					return;
				}
				if(CommnUtil.haveOneTagIsNull("doirraddate,ordernum,connecttime,firstspreadernum,irradbatchnum,entrancetime,spreadernum,runparam,runcycle,changedesc,nextcyclestarttime"))
				{
					alert("标红信息必须填写！");
					return;
				}
				saveradiation();
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
			//CommnUtil.cleanInputValue("divcargoname,receiveorgid,irradednum,hiddenoldirradednum,showcountorginfos,cargoweight,irradtypes,irradtime,timeorgs");
		}
	});
	$('#editradiationdiv').dialog('open');
}

function saveradiation(){
	var param = CommnUtil.UrlOfInputValue("divcargoname,receiveorgid,irradednum,showcountorginfos,cargoweight,irradtypes,irradtime,timeorgs,doirraddate," +
			"ordernum,connecttime,firstspreadernum,irradbatchnum,entrancetime,spreadernum,loadmodel," +
			"runparam,runcycle,changedesc,nextcyclestarttime,mask");
	var data = CommnUtil.normalAjax("/business/receivingmana/setradiation.do","id="+globalstillid+"&"+param, "json");
	if("ok"==data){
		alert("保存成功！");
		search(0,"false");
		initcurrentradiations();
	}
	else{
		alert(data);
	}
}

function initradiation(){
	var id = globalstillid.split("-")[0];
	var data = CommnUtil.normalAjax("/business/receivingmana/goradiation.do","id="+id, "json");
	if(CommnUtil.notNull(data)){
		$("#receiveorgid").val(data.receiveorgid);
		$("#divcargoname").val(data.cargoname);
		$("#hiddencargoid").val(data.cargoid);
		//$("#irradednum").val(data.cargocount);//jquery可能与jquery的弹框有冲突使用此种方式无效改用如下方式
		document.getElementById("irradednum").value=data.cargocount;
		$("#hiddenoldirradednum").val(data.cargocount);//
		$("#showcountorginfos").find("option[value='"+data.countorg+"']").attr("selected",true);
		//$("#cargoweight").val(data.cargoweight);//jquery可能与jquery的弹框有冲突使用此种方式无效改用如下方式
		document.getElementById("cargoweight").value=data.cargoweight;
		$("#irradtypes").find("option[value='"+data.irradtype+"']").attr("selected",true);
		//$("#irradtime").val(data.irradtime);//jquery可能与jquery的弹框有冲突使用此种方式无效改用如下方式
		document.getElementById("irradtime").value=data.irradtime;
		$("#timeorgs").find("option[value='"+data.irradtimeorg+"']").attr("selected",true);
	}
}

function initcurrentradiations(){
	var data = CommnUtil.normalAjax("/business/receivingmana/initcurrentradiations.do","", "json");
	if(CommnUtil.notNull(data)){
		var html = "";
		for(var i=0;i<data.length;i++){
			html = html
			+ "<tr  ondblclick='completeradiation("+data[i].id+");'>"
			+ "<td style='text-align: center'>"+ data[i].cargoname + "</td>"
			+ "<td style='text-align: center'>"+ data[i].receiveorgname + "</td>"
			+ "<td style='text-align: center'>"+ data[i].irradednum + "</td>"
			+ "<td style='text-align: center'>"+ data[i].countorg + "</td>"
			+ "<td style='text-align: center'>"+ data[i].cargoweight + "</td>"
			+ "<td style='text-align: center'>"+ data[i].spreadernum + "</td>"
			+ "<td style='text-align: center'>"+ data[i].irradtype + "</td>"
			+ "<td style='text-align: center'>"+ data[i].entrancetime + "</td>"
			+ "<td style='text-align: center'>"+ data[i].irradtime + "</td>"
			+ "<td style='text-align: center'>"+ data[i].timeorg + "</td>"
			+"</tr>";
		}
		$("#radiatingcargo").html(html);
	}
}


function completeradiation(id){
	if(confirm("您确定当前货物已完成辐照吗？")){
		var data = CommnUtil.normalAjax("/business/receivingmana/completeradiation.do","id="+id, "json");
		if("ok"==data){
			alert("操作成功！");
			initcurrentradiations();
		}else{
			alert(data);
		}
    }
}