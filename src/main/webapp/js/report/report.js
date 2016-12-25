$(function (){
	var myDate = new Date();
	var month  = 0;
	if(parseInt(myDate.getMonth())<9){
		month = "0"+(parseInt(myDate.getMonth())+1);
	}else{
		month = parseInt(myDate.getMonth())+1;
	}
	var myday = 0;
	if(parseInt(myDate.getDate())<9){
		myday = "0"+parseInt(myDate.getDate());
	}else{
		myday = parseInt(myDate.getDate());
	}
	var defaultdate = myDate.getFullYear()+"-"+month+"-"+myday;
	$('#printtime').val(defaultdate);
	if(ismonth=="1"){
		$('#printtime').val(myDate.getFullYear()+"-"+month);
		$('#printtime').Zebra_DatePicker({
		    format: 'Y-m'   //  note that because there's no day in the format
		                    //  users will not be able to select a day!
		});
	}else
	{
		$('#printtime').Zebra_DatePicker();
    }
	
	$("#countorgcompany").autocomplete({
		source:function (request,response){
			$.ajax({
				url:"business/receivingmana/queryorgs.do",
				dataType:"json",
				data:{
					query:encodeURI($("#countorgcompany").val())
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
	
	$("#query").click("click", function() {
		refreshDalidyReceivedBody();
		refreshDalidyIrradationBody();
		refreshDalidyOutCargoesbody();
		refreshDalidyChargeBody();
	});
	$("#PrintReceived").click("click", function() {
		goprint();
	});
	$("#PrintCharge").click("click", function() {
		goPrintCharge();
	});
	$("#PrintOutCargoes").click("click", function() {
		goPrintOutCargoes();
	});
	
	refreshDalidyReceivedBody();
	refreshDalidyIrradationBody();
	refreshDalidyOutCargoesbody();
	refreshDalidyChargeBody();
});

function goprint(){
	
	 window.open("/product/report/goprintpage.do?time="+$("#printtime").val()+"&ismoth="+ismonth+"&countorg="+$("#countorgcompany").val().split(":")[0]+"&cleancache="+new Date(),'_blank');
}
function goPrintCharge(){
	window.open("/product/report/goPrintChargePage.do?time="+$("#printtime").val()+"&ismoth="+ismonth+"&countorg="+$("#countorgcompany").val().split(":")[0]+"&cleancache="+new Date(),'_blank');
}
function goPrintOutCargoes(){
	window.open("/product/report/goPrintOutCargoes.do?time="+$("#printtime").val()+"&ismoth="+ismonth+"&countorg="+$("#countorgcompany").val().split(":")[0]+"&cleancache="+new Date(),'_blank');
}

//刷新已收货物
function refreshDalidyReceivedBody(){
	var data = CommnUtil.normalAjax("/report/refreshDalidyReceivedBody.do","date="+$("#printtime").val()+"&ismoth="+ismonth+"&countorg="+$("#countorgcompany").val().split(":")[0],"json");
	if(CommnUtil.notNull(data)){
		var html = "";
		for(var i=0;i<data.length;i++){
			html = html+
			"<tr>"
			+"<td style='text-align: center'>"+data[i].receivetime+"</td>"
			+"<td style='text-align: center'>"+data[i].takecaroorg+"</td>"
			+"<td style='text-align: center'>"+data[i].bringcargopeople+"</td>"
			+"<td style='text-align: center'>"+data[i].cargoname+"</td>"
			+"<td style='text-align: center'>"+data[i].count+"</td>"
			+"<td style='text-align: center'>"+data[i].countorg+"</td>"
			+"<td style='text-align: center'>"+data[i].weight+"</td>"
			+"<td style='text-align: center'>"+data[i].irradtype+"</td>"
			+"<td style='text-align: center'>"+data[i].irradtime+"</td>"
			+"<td style='text-align: center'>"+data[i].reqreagent+"</td>"
			+"<td style='text-align: center'>"+data[i].funguscount+"</td>"
			+"<td name='dalidyReceivedsumfeees' style='text-align: center'>"+data[i].fee+"</td>"
			+"<td style='text-align: center'>"+data[i].mask+"</td>"
			+"<td style='text-align: center'>"+data[i].username+"</td>"
			+"</tr>";
		}
		$("#dalidyReceivedBody").html(html);
		var dalidyReceivedsumfeeessum = 0;
		$("[name='dalidyReceivedsumfeees']").each(function(){
			   var tdvalue =  $(this).text().trim();
			   if(''!=tdvalue && !isNaN(tdvalue)){
				   dalidyReceivedsumfeeessum +=parseFloat(tdvalue);
			   }
			  });
		$("#dalidyReceivedsumview").text(dalidyReceivedsumfeeessum);
	}
}

//刷新易福朝
function refreshDalidyIrradationBody(){
	var data = CommnUtil.normalAjax("/report/refreshDalidyIrradationBody.do","date="+$("#printtime").val()+"&ismoth="+ismonth+"&countorg="+$("#countorgcompany").val().split(":")[0],"json");
	if(CommnUtil.notNull(data)){
		var html = "";
		for(var i=0;i<data.length;i++){
			html = html+
			"<tr>"
			+"<td style='text-align: center'>"+data[i].cargoname+"</td>"
			+"<td style='text-align: center'>"+data[i].takecargoorg+"</td>"
			+"<td style='text-align: center'>"+data[i].count+"</td>"
			+"<td style='text-align: center'>"+data[i].countorg+"</td>"
			+"<td style='text-align: center'>"+data[i].weight+"</td>"
			+"<td style='text-align: center'>"+data[i].position+"</td>"
			+"<td style='text-align: center'>"+data[i].irradationtype+"</td>"
			+"</tr>";
		}
		$("#dalidyIrradationBody").html(html);
	}
}

//刷新当日出货
function refreshDalidyOutCargoesbody(){
	var data = CommnUtil.normalAjax("/report/refreshDalidyOutCargoesbody.do","date="+$("#printtime").val()+"&ismoth="+ismonth+"&countorg="+$("#countorgcompany").val().split(":")[0],"json");
	if(CommnUtil.notNull(data)){
		var html = "";
		for(var i=0;i<data.length;i++){
			html = html+
			"<tr>"
			+"<td style='text-align: center'>"+data[i].takecargotime+"</td>"
			+"<td style='text-align: center'>"+data[i].takecargoorg+"</td>"
			+"<td style='text-align: center'>"+data[i].takecargoproxyorg+"</td>"
			+"<td style='text-align: center'>"+data[i].takecargopeople+"</td>"
			+"<td style='text-align: center'>"+data[i].caroname+"</td>"
			+"<td style='text-align: center'>"+data[i].count+"</td>"
			+"</tr>";
		}
		$("#dalidyOutCargoesbody").html(html);
	}
}

//刷新当日收款
function refreshDalidyChargeBody(){
	var data = CommnUtil.normalAjax("/report/refreshDalidyChargeBody.do","date="+$("#printtime").val()+"&ismoth="+ismonth+"&countorg="+$("#countorgcompany").val().split(":")[0],"json");
	if(CommnUtil.notNull(data)){
		var html = "";
		for(var i=0;i<data.length;i++){
			html = html+
			"<tr>"
			+"<td style='text-align: center'>"+data[i].chargetime+"</td>"
			+"<td style='text-align: center'>"+data[i].chargeorg+"</td>"
			+"<td name='dalidyChargefeeviewsum' style='text-align: center'>"+data[i].chargeamount+"</td>"
			+"<td style='text-align: center'>"+data[i].operater+"</td>"
			+"</tr>";
		}
		$("#dalidyChargeBody").html(html);
		var dalidyChargefeeviewsum = 0;
		$("[name='dalidyChargefeeviewsum']").each(function(){
			   var tdvalue =  $(this).text().trim();
			   if(''!=tdvalue && !isNaN(tdvalue)){
				   dalidyChargefeeviewsum +=parseFloat(tdvalue);
			   }
			  });
		$("#dalidyChargefeeview").text(dalidyChargefeeviewsum);
	}
}

