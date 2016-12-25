var globalselectedtakeinfo="";
$(function() {
	//注册弹出框
	$("#taketime").Zebra_DatePicker();
	$("#takecargotime").Zebra_DatePicker();
	$("#query").click("click", function() {
		search(0,"true");
	});
	$("#querytakedcargo").click("click", function() {
		takedsearch(0,"true");
	});
	$("#pre").click("click", function() {
		dopre();
	});
	$("#next").click("click", function() {
		donext();
	});
	$("#takedpre").click("click", function() {
		takeddopre();
	});
	$("#takednext").click("click", function() {
		takeddonext();
	});
	
	$("#save").click("click", function() {
		save();
	});
	$("#flowcurrentrecord").click("click", function() {
		if($("#flowcurrentrecord").attr("checked")==true){
			$("#flowcurrentrecord").val("1");
		}
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
	
	$("#showorgsoftaked").autocomplete({
		source:function (request,response){
			$.ajax({
				url:"business/receivingmana/queryorgs.do",
				dataType:"json",
				data:{
					query:encodeURI($("#showorgsoftaked").val())
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
	
	$("#showorgs1").autocomplete({
		source:function (request,response){
			$.ajax({
				url:"business/receivingmana/queryorgs.do",
				dataType:"json",
				data:{
					query:encodeURI($("#showorgs1").val())
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
	
	search(0,"false");
	takedsearch(0,"false");
});


function search(pagenow, isfromsearch) {
	var data = CommnUtil.normalAjax("/business/receivingmana/queryalltakecargoes.do",
			  "pageNow=" + pagenow + "&pageSize=20"
			  +"&showorgs="+$("#searchorg").val().split(":")[0]
			  +"&cargoname="+$("#cargoname").val(),
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
		for (var i = 0; i < data.alltakecargoes.length; i++) {
			// alert(data[i].id+"@_@"+data[i].cargoname+"@_@"+data[i].org+"@_@"+data[i].irradtype+"@_@"+data[i].irradtime+"@_@"+data[i].timeorg);
			var param = data.alltakecargoes[i].id+"-"+data.alltakecargoes[i].receiveorgname+"-"+data.alltakecargoes[i].cargoname+"-"+data.alltakecargoes[i].irradednum
			+"-"+data.alltakecargoes[i].takecargocount+"-"+data.alltakecargoes[i].countorg+"-"+data.alltakecargoes[i].cargoweight;
			//var color = "1"==data.radiations[i].status  ? "background-color: red" : "";
			html = html
					+ "<tr  onclick=\"doviewselect('"+param+"',this);\" ondblclick='getcargoinfo("+data.alltakecargoes[i].id+");' >"
					+ "<td style='text-align: center'>"+ data.alltakecargoes[i].receiveorgname + "</td>"
					+ "<td style='text-align: center'>"+ data.alltakecargoes[i].cargoname + "</td>"
					+ "<td style='text-align: center'>"+ data.alltakecargoes[i].irradednum + "</td>"
					+ "<td style='text-align: center'>"+ data.alltakecargoes[i].takecargocount + "</td>"
					+ "</tr>";
		}
		$("#alltakecargoes").html(html);
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

function getcargoinfo(id){
	initcargoinfo(id);
    var dialogParent = $("#cargoinfo").parent();  
    var dialogOwn = $("#cargoinfo").clone();  
    dialogOwn.hide();  
	$("#cargoinfo").dialog({
		autoOpen : false,// 设置对话框打开的方式 不是自动打开
		show : "bind",
		hide : "explode",
		modal : true,
		height : 350,
		width : 500,
		title: "编辑",
		buttons : {
			'关闭' : function() {
				$(this).dialog("close");
			},
		},
		open : function(ev, ui) {

			// CommnUtil.cleanInputValue("addcargoname,org,irradtype,irradtime,timeorg");
		},
		close : function(ev, ui) {
            dialogOwn.appendTo(dialogParent);  
            $(this).dialog("destroy").remove(); 
		}
	});
	$('#cargoinfo').dialog('open');
}

function initcargoinfo(id){
	var data = CommnUtil.normalAjax("/business/receivingmana/getreceivedcargoinfo.do","id="+id,"json");
	if(CommnUtil.notNull(data)){
		$("#receivetime").text(data.receivetime);
		$("#divcargoname").text(data.cargoname);
		$("#receiveorgname").text(data.receiveorgname);
		$("#irradednum").text(data.irradednum);
		$("#countorg").text(data.countorg);
		$("#cargoweight").text(data.cargoweight);
		$("#mask").text(data.mask);
	}
}

function doviewselect(id,obj){
	globalselectedtakeinfo = id;
	$("#alltakecargoes").find("tr").attr("style","");
	$(obj).attr("style","background-color: red");
}

function save(){
	if(globalselectedtakeinfo==""){
		alert("请选择一条货物！");
		return;
	}
	var taketime = $("#taketime").val();
	var proxyOrg = $("#proxyOrg").val();
	var showorgs1 = $("#showorgs1").val().split(":")[0];
	var takecargocount = $("#takecargocount").val();
	var allcount = globalselectedtakeinfo.split("-")[3];
	var havetake = globalselectedtakeinfo.split("-")[4];
	var gap = parseInt(allcount) - parseInt(havetake);
	if(!(takecargocount && ""!=takecargocount && parseFloat(takecargocount)>0 && parseFloat(takecargocount)<=gap)){
		alert("取货数量要大于0小于等于总数量并且必须填写！");
		return;
	}
	if(""==taketime){
		$("#taketime").attr("style","border-color: red");
		return ;
	}
	if(""==showorgs1 && ""==proxyOrg){
		$("#showorgs1").attr("style","border-color: red");
		$("#proxyOrg").attr("style","border-color: red");
		return;
	}
	var data = CommnUtil.normalAjax("/business/receivingmana/savetakecargoe.do",
			"data="+globalselectedtakeinfo
			+"&taketime="+$("#taketime").val()
			+"&showorgs="+showorgs1
			+"&proxyOrg="+$("#proxyOrg").val()
			+"&showBringTakeInfos="+$("#showBringTakeInfos").val()
			+"&telnum="+$("#telnum").val()
			+"&shippers="+$("#shippers").val()
			+"&takecargocount="+$("#takecargocount").val()
			+"&flowcurrentrecord="+$("#flowcurrentrecord").val(),
			"json");
	
	if("ok"==data){
		alert("保存成功！");
		CommnUtil.cleanInputValue("takecargocount");
		search(0, "false");
		takedsearch(0,"false");
	}else{
		alert(data);
	}
}


function takedsearch(pagenow, isfromsearch){
	var data = CommnUtil.normalAjax("/business/receivingmana/queryhavetakedcargoes.do",
			  "pageNow=" + pagenow + "&pageSize=20"
			  +"&takecargotime="+$("#takecargotime").val()
			  +"&showorgsoftaked="+$("#showorgsoftaked").val().split(":")[0],
			  "json");
	//data = $.evalJSON(data);
	if ("null" != data && 'undefined' != data) {
		var html = "";
		//alert("pagenow :"+pagenow+ " ; pagerecode :"+data.pagerecode +" ; totalpage"+data.totalpage+" ; "+data.havetakedcargoes.length);
		if("true"==isfromsearch){
			$("#takedpagerecode").text(data.pagerecode);
			$("#takedpageNow").text(0);
			$("#takedtotalpage").text(data.totalpage);
		}else{
			var totalpage = Math.ceil(parseInt(data.pagerecode) / 20);
			if (pagenow < 0 || pagenow >= totalpage) {
				return ;
			}
			$("#takedpagerecode").text(data.pagerecode);
			$("#takedpageNow").text(pagenow);
			$("#takedtotalpage").text(data.totalpage);
		}
		for (var i = 0; i < data.havetakedcargoes.length; i++) {
			html = html
					+ "<tr  onclick=\"doviewselectoftaked("+data.havetakedcargoes[i].id+",this);\"  ondblclick=\"cargoOutPrintConfirm("+data.havetakedcargoes[i].id+","+data.havetakedcargoes[i].takecargopeopleid+");\" >"
					+ "<td style='text-align: center'>"+ data.havetakedcargoes[i].taketime + "</td>"
					+ "<td style='text-align: center'>"+ data.havetakedcargoes[i].takecargoorg + "</td>"
					+ "<td style='text-align: center'>"+ data.havetakedcargoes[i].proxyorg + "</td>"
					+ "<td style='text-align: center'>"+ data.havetakedcargoes[i].takecargopeople + "</td>"
					+ "<td style='text-align: center'>"+ data.havetakedcargoes[i].telnum + "</td>"
					+ "<td style='text-align: center'>"+ data.havetakedcargoes[i].shippers + "</td>"
					+ "</tr>";
		}
		$("#takedcargoes").html(html);
	}
}	

function takeddopre() {
	var pageNow = $("#takedpageNow").text().trim();
	search(parseInt(pageNow) - 1, "false");
}

function takeddonext() {
	var pageNow = $("#takedpageNow").text().trim();
	search(parseInt(pageNow) + 1, "false");
}

function doviewselectoftaked(id,obj){
	$("#takedcargoes").find("tr").attr("style","");
	$(obj).attr("style","background-color: red");
	var data = CommnUtil.normalAjax("/business/receivingmana/queryhavetakedcargoedetail.do","id="+id,"json");
	if ("null" != data && 'undefined' != data) {
		var html = "";
		for(var i = 0; i < data.length; i++){
			html = html
			+ "<tr>" 
			+ "<td style='text-align: center'>"+ data[i].cargoname + "</td>"
			+ "<td style='text-align: center'>"+ data[i].cargocount + "</td>"
			+ "<td style='text-align: center'>"+ data[i].countorg + "</td>"
			+ "<td style='text-align: center'>"+ data[i].weight + "</td>"
			+"</tr>";
		}
		$("#takedcargoedetail").html(html);
	}
}

function refreshCargoOutPrintConfirmDialog(id,takecargopeopleid){
	var data = CommnUtil.normalAjax("/business/receivingmana/gethavetakedcargoebaseanddetail.do","takedcargoid="+id,"json");
	if(CommnUtil.notNull(data)){
		if(CommnUtil.notNull(data.takecargoinfo)){
			$("#shoppingprintconfirmouttime").text(data.takecargoinfo.taketime);
			$("#shoppingprintconfirmtakeorg").text(data.takecargoinfo.takecargoorg);
			$("#shoppingprintconfirmproxyorg").text(data.takecargoinfo.proxyorg);
			$("#shoppingprintconfirmtakepeople").text(data.takecargoinfo.takecargopeople);
			$("#shoppingprintconfirmtel").text(data.takecargoinfo.telnum);
			$("#shoppingprintconfirmpicture").attr("src","business/receivingmana/geprint.do?id="+takecargopeopleid+"&date="+new Date());
		}
		if(CommnUtil.notNull(data.takecargodetailinfos)){
			var html="";
			for(var i=0;i<data.takecargodetailinfos.length;i++){
				html=html+
				"<tr>"
				+"<td style='text-align: center'> "+data.takecargodetailinfos[i].cargoname+"</td>"
				+"<td style='text-align: center'> "+data.takecargodetailinfos[i].cargocount+"</td>"
				+"<td style='text-align: center'> "+data.takecargodetailinfos[i].weight+"</td>"
				+"</tr";
			}
			$("#shoppingprintconfirmbody").html(html);
		}
	}
}

function cargoOutPrintConfirm(id,takecargopeopleid){
	
    var dialogParent = $("#shoppingprintconfirm").parent();  
    var dialogOwn = $("#shoppingprintconfirm").clone();  
    dialogOwn.hide();
	$("#shoppingprintconfirm").dialog({
		autoOpen : false,// 设置对话框打开的方式 不是自动打开
		show : "bind",
		hide : "explode",
		modal : true,
		height : 500,
		width : 750,
		title: "出货确认",
		buttons : {
			'保存' : function() {
				var data = CommnUtil.normalAjax("/business/receivingmana/doConfirmOutCargo.do","takedcargoid="+id,"json");
				if("ok"==data){
					alert("确认成功！");
					takedsearch(0,"false");
				}else{
					alert(data);
				}
				$(this).dialog("close");
			},
			'取消' : function() {
				
				$(this).dialog("close");
			}
		},
		open : function(ev, ui) {
			refreshCargoOutPrintConfirmDialog(id,takecargopeopleid);
		},
		close : function(ev, ui) {
            dialogOwn.appendTo(dialogParent);  
            $(this).dialog("destroy").remove(); 
		}
	});
	$('#shoppingprintconfirm').dialog('open');
}
