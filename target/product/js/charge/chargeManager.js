var globalchargeid="";
$(function() {
	$("#query").click("click", function() {
		search();
		refresh();
	});
	$("#docharge").click("click", function() {
		docharge();
	});
	$("#printarea").click("click", function() {
		doprint();
	});
	search();
	refresh();
});

function doprint(){
	var inputorg = $("#inputorg").val();
	var showorgs = $("#showorgs").val();
	var asinputorg = CommnUtil.checkedBoxSetValueAndReturnValue("asinputorg","1","");
	var isunpaidcomplete = CommnUtil.checkedBoxSetValueAndReturnValue("isunpaidcomplete","1","");
	 window.open("collection/doprint.do?inputorg="+inputorg+"&showorgs="+showorgs+"&asinputorg="+asinputorg+"&isunpaidcomplete="+isunpaidcomplete,'_blank');
}

function search(){
	var inputorg = $("#inputorg").val();
	var showorgs = $("#showorgs").val();
	var asinputorg = CommnUtil.checkedBoxSetValueAndReturnValue("asinputorg","1","");
	var isunpaidcomplete = CommnUtil.checkedBoxSetValueAndReturnValue("isunpaidcomplete","1","");
	var data = CommnUtil.normalAjax("/collection/getallunpaid.do",
			"inputorg="+inputorg+"&showorgs="+showorgs+"&asinputorg="+asinputorg+"&isunpaidcomplete="+isunpaidcomplete,
			"json");
	if(CommnUtil.notNull(data)){
		var html = "";
		for(var i=0;i<data.length;i++){
			if(data[i].fee<=0){
				continue;
			}
			var param = data[i].id+"@_@"+data[i].fee+"@_@"+data[i].paid+"@_@"+data[i].unpaid+"@_@"+data[i].organizationname+"@_@"+data[i].receivetime+"@_@"+data[i].cargoname+"@_@"+data[i].cargocount+"@_@"+data[i].cargoweight;
			html = html+
			"<tr onclick=\"doselectview(this,'"+param+"');\">"
			+"<td style='text-align: center' >"+data[i].receivetime+"</td>"
			+"<td style='text-align: center'>"+data[i].takepeople+"</td>"
			+"<td style='text-align: center'>"+data[i].cargoname+"</td>"
			+"<td style='text-align: center'>"+data[i].cargocount+"</td>"
			+"<td style='text-align: center'>"+data[i].irradednum+"</td>"
			+"<td style='text-align: center'>"+data[i].takedccargocount+"</td>"
			+"<td style='text-align: center'>"+data[i].countorgname+"</td>"
			+"<td name='cargoweights' style='text-align: center'>"+data[i].cargoweight+"</td>"
			+"<td name='cargofees' style='text-align: center'>"+data[i].fee+"</td>"
			+"<td name='cargopaids' style='text-align: center'>"+data[i].paid+"</td>"
			+"<td name='cargounpaids' style='text-align: center'>"+data[i].unpaid+"</td>"
			+"</tr>";
		}
		$("#receivableinformation").html(html);
		var cargoweights = 0;
		var cargofees = 0;
		var cargopaids = 0;
		var cargounpaids = 0;
		$("[name='cargoweights']").each(function(){
		   var tdvalue =  $(this).text().trim();
		   if(''!=tdvalue && !isNaN(tdvalue)){
			   cargoweights +=parseFloat(tdvalue);
		   }
		  });
		$("[name='cargofees']").each(function(){
			var tdvalue =  $(this).text().trim();
			if(''!=tdvalue && !isNaN(tdvalue)){
				cargofees +=parseFloat(tdvalue);
			}
		});
		$("[name='cargopaids']").each(function(){
			var tdvalue =  $(this).text().trim();
			if(''!=tdvalue && !isNaN(tdvalue)){
				cargopaids +=parseFloat(tdvalue);
			}
		});
		$("[name='cargounpaids']").each(function(){
			var tdvalue =  $(this).text().trim();
			if(''!=tdvalue && !isNaN(tdvalue)){
				cargounpaids +=parseFloat(tdvalue);
			}
		});
		$("#weightsum").text(""+cargoweights);
		$("#irradtionsum").text("￥"+cargofees);
		$("#paidsum").text("￥"+cargopaids);
		$("#unpaidsum").text("￥"+cargounpaids);
	}
}

function refresh(){
	var asinputorg = CommnUtil.checkedBoxSetValueAndReturnValue("asinputorg","1","");
	var inputorg = $("#inputorg").val();
	var data = CommnUtil.normalAjax("/collection/querychargelog.do",
			"organizationid="+$("#showorgs").val()+"&asinputorg="+asinputorg+"&inputorg="+inputorg,"json");
	if(CommnUtil.notNull(data)){
		var html ="";
		for(var i=0;i<data.length;i++){
			html = html+
			"<tr>"
			+"<td style='text-align: center'>"+data[i].organizationname+"</td>"
			+"<td style='text-align: center'>"+data[i].chargetime+"</td>"
			+"<td name='currentamount' style='text-align: center'>"+data[i].currentapid+"</td>"
			+"<td style='text-align: center'>"+data[i].mask+"</td>"
			+"<td style='text-align: center'>"+data[i].receivetime+"</td>"
			+"<td style='text-align: center'>"+data[i].cargoname+"</td>"
			+"<td style='text-align: center'>"+data[i].cargocount+"</td>"
			+"<td style='text-align: center'>"+data[i].cargoweight+"</td>"
			+"<td style='text-align: center'>"+data[i].paytype+"</td>"
			+"<td name='currentfee' style='text-align: center'>"+data[i].fee+"</td>"
			+"<td name='currentpaid' style='text-align: center'>"+data[i].paid+"</td>"
			+"<td name='currentunpaid' style='text-align: center'>"+data[i].unpaid+"</td>"
			+"</tr>";
		}
		$("#currentchargeinfo").html(html);
		var currentamount = 0;
		var currentfee = 0;
		var currentpaid = 0;
		var currentunpaid = 0;
		$("[name='currentamount']").each(function(){
			   var tdvalue =  $(this).text().trim();
			   if(''!=tdvalue && !isNaN(tdvalue)){
				   currentamount +=parseFloat(tdvalue);
			   }
			  });
		$("[name='currentfee']").each(function(){
			var tdvalue =  $(this).text().trim();
			if(''!=tdvalue && !isNaN(tdvalue)){
				currentfee +=parseFloat(tdvalue);
			}
		});
		$("[name='currentpaid']").each(function(){
			var tdvalue =  $(this).text().trim();
			if(''!=tdvalue && !isNaN(tdvalue)){
				currentpaid +=parseFloat(tdvalue);
			}
		});
		$("[name='currentunpaid']").each(function(){
			var tdvalue =  $(this).text().trim();
			if(''!=tdvalue && !isNaN(tdvalue)){
				currentunpaid +=parseFloat(tdvalue);
			}
		});
		$("#currentamountsum").text("￥"+currentamount);
		$("#currentfeesum").text("￥"+currentfee);
		$("#currentpaidsum").text("￥"+currentpaid);
		$("#currentunpaidsum").text("￥"+currentunpaid);
	}
}

function doselectview(tr,param){
	globalchargeid = ""+param+"";
	$("#receivableinformation").find("tr").attr("style","");
	$(tr).attr("style","background-color: red");
}

function docharge(){
	var thePayment = $("#thePayment").val();
	var chargeIntroduction = $("#chargeIntroduction").val();
	if(!CommnUtil.notNull(globalchargeid)){
		alert("请选择一条收款项！");
		return;
	}
	var itemid = globalchargeid.split("@_@")[0];
	var unpaid = globalchargeid.split("@_@")[3];
	var organizationname = globalchargeid.split("@_@")[4];
	if(CommnUtil.notNull(thePayment)){
		if(isNaN(thePayment)){
			$("#thePayment").attr("style","background-color: red");
			return;
		}
		if(parseFloat(thePayment)<=0){
			$("#thePayment").attr("style","background-color: red");
			return;
		}
		if(parseFloat(thePayment)>parseFloat(unpaid)){
			alert("收款金额大于未付金额！");
			return ;
		}
		
		if(confirm("收款金额：￥ "+thePayment+"\n 交款单位："+organizationname+"\n 确认收款吗?")){
			var data = CommnUtil.normalAjax("/collection/docharge.do",
					"id="+itemid+"&thePayment="+thePayment+"&chargeIntroduction="+chargeIntroduction+"&param="+globalchargeid+"&paytype="+$("#paytype").val(),"json");
			if("ok"==data){
				alert("收款成功！");
				$("#chargeIntroduction").val("");
				$("#thePayment").val("");
				search();
				refresh();
			}
		}else{
			$("#chargeIntroduction").val("");
			$("#thePayment").val("");
		}
		
		$("#thePayment").attr("style","");
	}else{
		$("#thePayment").attr("style","background-color: red");
	}
}
