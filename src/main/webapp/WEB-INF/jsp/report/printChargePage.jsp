<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.crazycode.org/commontaglib" prefix="crazy"%>
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="styles.css">
    <title>收费报表</title>

<style type="text/css" media=print> 
	.noprint{display : none } 
	.thetitle{
	text-align:center;
	font-weight:bold;
	font-size:90%;
	}
	
	#dalidyCharge{border-collapse:collapse;border-left:1px solid #66BDFF;border-top:1px solid #66BDFF;}
	#dalidyCharge th,td{border-collapse:collapse;border-right:1px solid #66BDFF;border-bottom:1px solid #66BDFF;padding-left: 1px;padding-right: 1px;
	padding-top: 10px;padding-bottom: 10px;
</style> 

<style type="text/css">
	.thetitle{
	text-align:center;
	font-weight:bold;
	font-size:90%;
	}
	#dalidyCharge{border-collapse:collapse;border-left:1px solid #66BDFF;border-top:1px solid #66BDFF;}
	#dalidyCharge th,td{border-collapse:collapse;border-right:1px solid #66BDFF;border-bottom:1px solid #66BDFF;padding-left: 1px;padding-right: 1px;
	padding-top: 10px;padding-bottom: 10px;
</style>

<script type="text/javascript">
var rootPath = "${pageContext.request.contextPath}";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/common.js"></script>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/css/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/notebook/notebook_files/bootstrap.css"> 

	<script type="text/javascript">
	var ismoth = "${ismoth}";
	var date = "${date}";
	var countorg = "${countorg}";
	$(function (){
		initdata();
		printpreview();
	});
	
    var HKEY_Root,HKEY_Path,HKEY_Key;    
    HKEY_Root="HKEY_CURRENT_USER";    
    HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";    
        //设置网页打印的页眉页脚为空    
    function PageSetup_Null()   
     {    
       try {    
               var Wsh=new ActiveXObject("WScript.Shell");    
 
       HKEY_Key="footer";    
       Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");    
       }  catch(e){}    
     }    
     //恢复网页打印的页眉页脚   
     function PageSetup_default()   
     {    
       try {    
               var Wsh=new ActiveXObject("WScript.Shell");    
       HKEY_Key="header";    
       Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"&w&b页码,&p/&P");    
       HKEY_Key="footer";    
       Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"&u&b&d");    
       }  catch(e){}    
     }
	
 	function printsetup(){
		// 打印页面设置
		wb.execwb(8,1);
	}
	
	function printpreview(){
		// 打印页面预览
		PageSetup_Null();
		wb.execwb(7,1);
	}
	
	function printit()
	{
		if (confirm('确定打印吗？')) {
		   wb.execwb(6,6);
	    }
	}	
     
	function initdata(){
		var data = CommnUtil.normalAjax("/report/refreshDalidyChargeBody.do","date="+date+"&ismoth="+ismoth+"&countorg="+countorg,"json");
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
	
	function doprint(){
		document.body.innerHTML=document.getElementById("printdiv").innerHTML;
		window.print();
	}
	
	</script>
  </head>
  <body>
    <OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id=wb name=wb width=0></OBJECT>
    <input type="hidden" id="querydate" value="${date}" class="noprint" />
	<input class="noprint" type="button" name="button_print" value="打印" onclick="javascript:printit()">
	<input class="noprint" type="button"　name="button_setup" value="打印页面设置" onclick="javascript:printsetup();">
	<input class="noprint" type="button"　name="button_show" value="打印预览" onclick="javascript:printpreview();">
	<input class="noprint" type="button" name="button_fh" value="关闭" onclick="javascript:window.close();">
  <div id="printdiv" class="m-b-md"> 
  <div align="center"><font size="16">  辐照业务收费${ismoth == '1'?'月':'日'}统计报表</font></div>
  <div align="center"> <font size="3">统计时间：${date}，打印时间：${printtime}</font> </div>
		<table id="dalidyCharge"  width="100%"  class="pp-list table  table-bordered" title="当${ismoth=='1'?'月':'日'} 收款">
		<thead>
		<tr>
		<td>收货时间</td>
		<td>交款单位</td>
		<td>交款金额</td>
		<td>操作员</td>
		</tr>
		</thead>
		<tbody id="dalidyChargeBody">
		
		</tbody>
		<tfoot>
		 <tr>
		 <td>合计</td>
		 <td></td>
		 <td id="dalidyChargefeeview" style='text-align: center'></td>
		 <td></td>
		 </tr>
		</tfoot>
		</table>
  </div>
  
  </body>
</html>
