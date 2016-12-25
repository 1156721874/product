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
	
	search(0,"true");
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
	var param = "receivetime,showorgs,showBringTakeInfos,telnum,cargoname,cargocount,showcountorginfos,reqreagent,irradtime,timeorgs,irradflags,asCurrentRecord,cargoweight,funguscount,irradtypes";
	var data = CommnUtil.normalAjax("/business/receivingmana/saveReceiveCargo.do",CommnUtil.UrlOfInputValue(param),"json");
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
	var data = CommnUtil.normalAjax("/business/receivingmana/queryReceivedCargos.do",
			        "pageNow=" + pagenow + "&pageSize=20&isfromsearch="
			        + isfromsearch
					+ "&receivetime1=" + $("#receivetime1").val()
					+ "&showorgs1=" + $("#showorgs1").val()
					+ "&showBringTakeInfos1=" + $("#showBringTakeInfos1").val(),
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
			html = html
					+ "<tr  onclick=\"getdetailinfo("+data.receivedCargos[i].id+",this);\" ondblclick='gofingerprints("+data.receivedCargos[i].id+","+data.receivedCargos[i].cargoid+");'>"
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
			+ "&receivetime1=" + $("#receivetime1").val(),
			+ "&showorgs1=" + $("#showorgs1").val(),
			+ "&showBringTakeInfos1=" + $("#showBringTakeInfos1").val(),
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

function gofingerprints(receiveorgid,cargoid){
	alert("此处通过单位id:"+receiveorgid+"以及货物id:"+cargoid+"到后台取得指纹仪录入的指纹进行验证，验证完毕将receivemgrbase表的status置为1");
	var data = CommnUtil.normalAjax("/business/receivingmana/updateReceivedCargoStatus.do","id="+receiveorgid,"json");
	if("ok"==data){
		alert("更新成功！");
		search(0,"false");
		$("#receivedCargosDetail").html("");
	}
	
}

function getdetailinfo(id,obj){
	$("#receivedCargosBody").find("tr").attr("style","");
	$(obj).attr("style","background-color: red");
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