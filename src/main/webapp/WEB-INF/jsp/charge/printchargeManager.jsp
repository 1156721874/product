<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.crazycode.org/commontaglib" prefix="crazy"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="styles.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.18.custom.min.js"></script>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/css/jquery-ui-1.8.18.custom.css">
	 <link rel="stylesheet" href="${pageContext.servletContext.contextPath }/notebook/notebook_files/bootstrap.css"> 
	
	 <link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/default.css">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/reset.css">
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/style.css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ZebraDatepicker/core.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ZebraDatepicker/zebra_datepicker.js"></script> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
	<style type="text/css" media=print> 
		.noprint{display : none } 
		.thetitle{
		text-align:center;
		font-weight:bold;
		font-size:90%;
	     }
	
	#mytable{border-collapse:collapse;border-left:1px solid #66BDFF;border-top:1px solid #66BDFF;}
	#mytable th,td{border-collapse:collapse;border-right:1px solid #66BDFF;border-bottom:1px solid #66BDFF;padding-left: 1px;padding-right: 1px;
	padding-top: 10px;padding-bottom: 10px;
    </style> 
    
    <style type="text/css">
		.thetitle{
		text-align:center;
		font-weight:bold;
		font-size:90%;
		}
		#mytable{border-collapse:collapse;border-left:1px solid #66BDFF;border-top:1px solid #66BDFF;}
		#mytable th,td{border-collapse:collapse;border-right:1px solid #66BDFF;border-bottom:1px solid #66BDFF;padding-left: 1px;padding-right: 1px;
		padding-top: 10px;padding-bottom: 10px;
    </style>

    
	<script type="text/javascript">
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
	
	function initdata(){
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

	
	
	function printit()
	{
		if (confirm('确定打印吗？')) {
		   wb.execwb(6,6);
	    }
	}	
	
	
	</script>
	
    <title>打印收费</title>
  </head>
  
  <body>
      <OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 id=wb name=wb width=0></OBJECT>
	<input class="noprint" type="button" name="button_print" value="打印" onclick="javascript:printit()">
	<input class="noprint" type="button"　name="button_setup" value="打印页面设置" onclick="javascript:printsetup();">
	<input class="noprint" type="button"　name="button_show" value="打印预览" onclick="javascript:printpreview();">
	<input class="noprint" type="button" name="button_fh" value="关闭" onclick="javascript:window.close();">
	
    			<table class="pp-list table  table-bordered">
    			<caption align="top">收费报表</caption>  
			  	<thead>
			  	<tr>
			  	<td style='text-align: center'><nobr> 收获日期 </nobr></td>
			  	<td style='text-align: center'><nobr>送货人</nobr></td>
			  	<td style='text-align: center'><nobr>货物名称</nobr></td>
			  	<td style='text-align: center'><nobr>货物数量</nobr></td>
			  	<td style='text-align: center'><nobr>已照数量</nobr></td>
			  	<td style='text-align: center'><nobr>已取数量</nobr></td>
			  	<td style='text-align: center'><nobr>单位</nobr></td>
			  	<td style='text-align: center'><nobr>重量(吨)</nobr></td>
			  	<td style='text-align: center'><nobr>辐照费</nobr></td>
			  	<td style='text-align: center'><nobr>已付(元)</nobr></td>
			  	<td style='text-align: center'><nobr>未付(元)</nobr></td>
			  	</tr>
			  	</thead>
			  	<tbody id="receivableinformation">
    <c:forEach items="${allunpaid }" var="item">
			  		<tr>
						<td style='text-align: center' ><nobr>${item.receivetime }</nobr></td>
						<td style='text-align: center'><nobr>${item.takepeople }</nobr></td>
						<td style='text-align: center'><nobr>${item.cargoname }</nobr></td>
						<td style='text-align: center'>${item.cargocount }</td>
						<td style='text-align: center'>${item.irradednum }</td>
						<td style='text-align: center'>${item.takedccargocount }</td>
						<td style='text-align: center'>${item.countorgname }</td>
						<td name='cargoweights' style='text-align: center'>${item.cargoweight }</td>
						<td name='cargofees' style='text-align: center'>${item.fee }</td>
						<td name'cargopaids' style='text-align: center'>${item.paid }</td>
						<td name='cargounpaids' style='text-align: center'>${item.unpaid }</td>
			        </tr>
    </c:forEach>
			  	</tbody>
			  	<tfoot>
			  	<tr>
			  	
			  	<td>合计</td>
			  	<td></td>
			  	<td></td>
			  	<td></td>
			  	<td></td>
			  	<td></td>
			  	<td></td>
			  	<td id="weightsum">￥0.0</td>
			  	<td id="irradtionsum">￥0.0</td>
			  	<td id="paidsum">￥0.0</td>
			  	<td id="unpaidsum">￥0.0</td>
			  	 
	            </tr>
			  	</tfoot>
			  </table>
  </body>
</html>
