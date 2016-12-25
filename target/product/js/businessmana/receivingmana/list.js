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
$(function() {
	$('#receivetime').Zebra_DatePicker();
	$('#receivetime1').Zebra_DatePicker();
	$('#hiddenshowhr').toggle(function(){$('#receivedCargosHeader').show('slow');},function(){$('#receivedCargosHeader').slideUp();});
	//CommnUtil.disableOrEnabledInputValue("detailTable,cargoname,cargocount,reqreagent,cargoweight,funguscount,irradtime,save",true);
	$("#save").click("click", function() {
		dosave();
	});
	$("#asCurrentRecord").click("click", function() {
		if($("#asCurrentRecord").attr("checked")==true){
			$("#asCurrentRecord").val("1");
		}
	});
	$("#query").click("click", function() {
		search(0,"false");
	});
	$("#pre").click("click", function() {
		dopre();
	});
	$("#next").click("click", function() {
		donext();
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
//基本信息的单位收货
$("#showorgs").autocomplete({
	source:function (request,response){
		$.ajax({
			url:"business/receivingmana/queryorgs.do",
			dataType:"json",
			data:{
				query:encodeURI($("#showorgs").val())
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
function dosave(){
	if(CommnUtil.haveOneTagIsNull("receivetime,showorgs,showBringTakeInfos,telnum,cargoname,irradtypes")){
		return;
	}
	if($("#cargocount").val()==""){
		alert("货物数量为必填项！");
		return;
	}
	if(!CommnUtil.isnumber("cargocount,reqreagent,cargoweight,funguscount,irradtime")){
		alert("详细信息中标红选项必须为数字！");
		return;
	}
	var param1 = "receivetime,showBringTakeInfos,telnum,cargoname,cargocount,showcountorginfos,reqreagent,irradtime,timeorgs,irradflags,asCurrentRecord,cargoweight,funguscount,irradtypes";
	var showorgs=$("#showorgs").val().split(":")[0];
	
	//var parm=parm1&showorgs;
	var data = CommnUtil.normalAjax("/business/receivingmana/saveReceiveCargo.do",CommnUtil.UrlOfInputValue(param1)+"&showorgs="+showorgs,"json");
	if(data.flag=='repeat'){
		alert(data.result);
		return;
	}
	if(data.flag=='inserterror'){
		alert(data.result);
		return;
	}
	if(data.flag=='insertsuccess'){
		alert("添加成功！");
		CommnUtil.cleanInputValue("detailTable,cargocount,reqreagent,cargoweight,funguscount,irradtime");
		search(0,"false");
	}
}


function search(pagenow, isfromsearch) {
	if(pagenow<0){return;}
	var data = CommnUtil.normalAjax("/business/receivingmana/queryReceivedCargos.do",
			        "pageNow=" + pagenow + "&pageSize=20&isfromsearch="
			        + isfromsearch
					+ "&receivetime1=" + $("#receivetime1").val()
					+ "&showorgs1=" + $("#searchorg").val().split(":")[0]
					+ "&showBringTakeInfos1=" + $("#showBringTakeInfos1").val()
					+ "&lastdate="+$("#lastdate").val(),
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
				$("#receivedCargosBody").html(html);
				return ;
			}
			$("#pagerecode").text(data.pagerecode);
			$("#pageNow").text(pagenow);
			$("#totalpage").text(data.totalpage);
		}
		for (var i = 0; i < data.receivedCargos.length; i++) {
			//var color = ("0"==data.receivedCargos[i].status || "" == data.receivedCargos[i].status || !CommnUtil.notNull(data.receivedCargos[i].status)) ? "background-color: red" : "";
			var color = "0"!=data.receivedCargos[i].status ? "style='background-color: silver'" : "";
			html = html
					+ "<tr "+color+"  onclick=\"getdetailinfo("+data.receivedCargos[i].id+",this,"+data.receivedCargos[i].status+");\" ondblclick='gofingerprints("+data.receivedCargos[i].id+","+data.receivedCargos[i].receivepeopleid+","+data.receivedCargos[i].status+");'>"
					+ "<td style='text-align: center'>" + data.receivedCargos[i].receivetime + " </td>" 
					+ "<td style='text-align: center'>"+ data.receivedCargos[i].receiveorgname + " </td>"
					+ "<td style='text-align: center'>"+ data.receivedCargos[i].receivepeoplename + " </td>"
					+ "<td style='text-align: center'>"+ data.receivedCargos[i].telnum + " </td>"
					+ "</tr>";
		}
		$("#receivedCargosBody").html(html);
	}else{
		$("#receivedCargosBody").html("");
	}
}

function setPageInfo(pagenow) {
	var data = CommnUtil.normalAjax("/business/receivingmana/getCount.do",
			+ "&receivetime1=" + $("#receivetime1").val()
			+ "&showorgs1=" + $("#searchorg").val().split(":")[0]
			+ "&showBringTakeInfos1=" + $("#showBringTakeInfos1").val()
			+ "&cargoname1=" + $("#cargoname1").val(),
			"json");
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
	search(parseInt(pageNow) - 1, "false");
}

function donext() {
	var pageNow = $("#pageNow").text().trim();
	search(parseInt(pageNow) + 1, "false");
}
//初始化对话框数据
function refreshdialogdata(receiveorgid){
	var info = CommnUtil.normalAjax("/business/receivingmana/getcargoinfoanddetailinfo.do","receiveorgid="+receiveorgid,"json");
	if(CommnUtil.notNull(info.baseinfo)){
		$("#dialogreceivetime").text(info.baseinfo.receivetime);
		$("#dialogbringcargoorg").text(info.baseinfo.receiveorgname);
		$("#dialogbringcargoperson").text(info.baseinfo.receivepeoplename);
		$("#dialogtel").text(info.baseinfo.telnum);
		$("#printpicture").attr("src","business/receivingmana/geprint.do?id="+info.baseinfo.receivepeopleid+"&date="+new Date());
	}
	if(CommnUtil.notNull(info.detaillist)){
		var html = "";
		for(var i=0;i<info.detaillist.length;i++){
			html = html +
			"<tr>"
				+"<td style='text-align: center'>"+info.detaillist[i].cargoname+"</td>"
				+"<td style='text-align: center'>"+info.detaillist[i].cargocount+"</td>"
				+"<td style='text-align: center'>"+info.detaillist[i].cargoweight+"</td>"
				+"<td style='text-align: center'>"+info.detaillist[i].irradtime+"</td>"
				+"<td style='text-align: center'>"+info.detaillist[i].irradtimeorg+"</td>"
			+"</tr>";
		}
		$("#dialogdetail").html(html);
	}	
}

function gofingerprints(receiveorgid,receivepeopleid,status){
	if("0"!=status){
		alert("当前货物不可确认！");
		return;
	}
	//alert("此处通过单位id:"+receiveorgid+"以及货物id:"+cargoid+"到后台取得指纹仪录入的指纹进行验证，验证完毕将receivemgrbase表的status置为1");
	if(confirm("是否完成收货?")){
		
	    var dialogParent = $("#receivedcagoprint").parent();  
	    var dialogOwn = $("#receivedcagoprint").clone();  
	    dialogOwn.hide();  
		//此处二次注册弹出框的目的：经测试jquery的dialog插件存在问题，只能重新注册，但是前边的不能删除，否则不能运行
		$("#receivedcagoprint").dialog({
			autoOpen : false,// 设置对话框打开的方式 不是自动打开
			show : "bind",
			hide : "explode",
			modal : true,
			height : 500,
			width : 500,
			title: "收货确认",
			buttons : {
				'保存' : function() {
					var data = CommnUtil.normalAjax("/business/receivingmana/updateReceivedCargoStatus.do","id="+receiveorgid,"json");
					if("ok"==data){
						alert("更新成功！");
						search(0,"false");
						$("#receivedCargosDetail").html("");
					}else{
						alert("操作失败！\n"+data);
					}
					$(this).dialog("close");
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			},
			open : function(ev, ui) {
				refreshdialogdata(receiveorgid);
			},
			close : function(ev, ui) {
	            dialogOwn.appendTo(dialogParent);  
	            $(this).dialog("destroy").remove(); 
			}
		});
		$("#receivedcagoprint").dialog("open");
	}
}

function getdetailinfo(id,obj,status){
	$("#receivedCargosBody").find("tr").attr("style","");
	if("0"!=status){
		$(obj).attr("style","background-color: silver");
	}else{
		$(obj).attr("style","background-color: red");
	}
	var data = CommnUtil.normalAjax("/business/receivingmana/getcargodetailinfo.do","id="+id,"json");
	var html = "";
	if("null" != data && 'undefined' != data){
		for (var i = 0; i < data.length; i++) {
			html = html
					+ "<tr>"
					+ "<td style='text-align: center'>" + data[i].cargoname + " </td>" 
					+ "<td style='text-align: center'>"+ data[i].cargocount + " </td>"
					+ "<td style='text-align: center'>"+ data[i].cargoweight + " </td>"
					+ "<td style='text-align: center'>"+ data[i].irradtype + " </td>"
					+ "<td style='text-align: center'>"+ data[i].irradtime + " </td>"
					+ "<td style='text-align: center'>"+ data[i].irradtimeorg + " </td>"
					+ "</tr>";
		}
		$("#receivedCargosDetail").html(html);
	}
}