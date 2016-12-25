<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.crazycode.org/commontaglib" prefix="crazy"%>
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="styles.css">
    <title>日报表</title>
<style type="text/css">
#prat1{
width: 50%;
height: 300px;
/* background: blue; */
float: left;
overflow:auto;
border: 1px;
border-color: red;
}
#prat2{
width: 50%;
height: 300px;
/* background: red; */
float: left;
overflow:auto;
border: 1px;
border-color: red;
}
#prat3{
width: 50%;
height: 300px;
/* background: yellow; */
float: left;
clear: left;
overflow:auto;
border: 1px;
border-color: red;
}
#prat4{
width: 50%;
height: 300px;
/* background: green; */
float: left;
overflow:auto;
border: 1px;
border-color: red;
}
</style>
    
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/report/report.js"></script>
<script type="text/javascript">
 var ismonth = "${ismoth}";
</script>
  </head>
  <body>
  <div class="m-b-md" >
<font> 
统计日期： <input type="text" id="printtime"  /> 
统计单位：<input type="text" id="countorgcompany"  /> <input type="button" value="query" id="query"  />
 <input type="button" value="PrintReceived" id="PrintReceived" />
 <input type="button" value="PrintOutCargoes" id="PrintOutCargoes" />
 <input type="button" value="PrintCharge" id="PrintCharge" />
  </div>
  <div class="m-b-md" id="printdiv"> 
	<div id="prat1">
		<table id="dalidyReceived" class="pp-list table  table-bordered" title="当${ismoth=='1'?'月':'日'}收获">
		<caption align="top"><font color="red"> 当${ismoth=='1'?'月':'日'}收货</font></caption> 
		<thead>
			<tr>
			<td><nobr>收货时间</nobr> </td>
			<td><nobr>送货单位 </nobr></td>
			<td><nobr>送货人</nobr></td>
			<td><nobr>货物名称</nobr></td>
			<td><nobr>数量</nobr></td>
			<td><nobr>数量单位</nobr></td>
			<td><nobr>重量(吨)</nobr></td>
			<td><nobr>辐照方式</nobr></td>
  			<td><nobr>辐照时间</nobr></td>
  			<td><nobr>剂量要求</nobr></td>
  			<td><nobr>照前菌数</nobr></td>
  			<td><nobr>费用(元)</nobr></td>
  			<td><nobr>备注</nobr></td>
 			<td><nobr>操作员</nobr></td>
			</tr>
		</thead>
		<tbody id="dalidyReceivedBody">
		
		</tbody>
		<tfoot>
		<tr>
		<td>合计</td>
		<td colspan="10"></td>
		<td  id="dalidyReceivedsumview" colspan="1"></td>
		<td colspan="2"></td>
		</tr>
		</tfoot>
		</table>
	</div>
	<div id="prat2">
		<table id="dalidyIrradation" class="pp-list table  table-bordered" title="当${ismoth=='1'?'月':'日'}辐照">
		<caption align="top"><font color="red"> 当${ismoth=='1'?'月':'日'}辐照</font></caption> 
		<thead>
		<tr>
		<td>货物名称</td>
		<td>送货单位</td>
		<td>数量</td>
		<td>单位</td>
		<td>重量(吨)</td>
		<td>吊具位置</td>
		<td>辐照方式</td>
		</tr>
		</thead>
		<tbody id="dalidyIrradationBody" > 
		
		</tbody>
		</table>
	</div>
	<div id="prat3">
		<table id="dalidyOutCargoes" class="pp-list table  table-bordered" title="当${ismoth=='1'?'月':'日'}出货" >
		<caption align="top"><font color="red"> 当${ismoth=='1'?'月':'日'}出货</font></caption>  
		<thead>
		<tr>
		<td>发货时间</td>
		<td>取货单位</td>
		<td>委托单位</td>
		<td>取货人</td>
		<td>货物名称</td>
		<td>数量</td>
		</tr>
		</thead>
		<tbody id="dalidyOutCargoesbody">
		
		</tbody>
		</table>
	</div>
	<div id="prat4">
		<table id="dalidyCharge"  class="pp-list table  table-bordered" title="当${ismoth=='1'?'月':'日'} 收款">
		<caption align="top"><font color="red"> 当${ismoth=='1'?'月':'日'}收款</font></caption> 
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
		 <td id="dalidyChargefeeview"></td>
		 <td></td>
		 </tr>
		</tfoot>
		</table>
	</div>
  </div>
  </body>
</html>
