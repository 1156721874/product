<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.crazycode.org/commontaglib" prefix="crazy"%>
<html>
<head>
<script type="text/javascript">
var allmenues = "${allmenues}";
</script>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/charge/chargeManager.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>
</head>
  
  <body>
  <div class="m-b-md">
		交款单位： <input id="inputorg" name="inputorg"  type="text" />  <crazy:dictselect id="showorgs" name="showorgs" emptyKey="" emptyValue="请选择"  collection="${showorgs}" ></crazy:dictselect>
		根据输入单位搜索：<input type="checkbox" id="asinputorg" name="asinputorg" />&nbsp;&nbsp;&nbsp;未付完：<input type="checkbox" id="isunpaidcomplete" name="isunpaidcomplete" />
		<input type="button" id="query" name="query" value="Query"/>  <input type="button" id="printarea" name="printarea" value="Print"/>
		<fieldset>
		  <legend>应收款信息</legend>
		  <div id="beanprintarea">
			  <table class="pp-list table  table-bordered">
			  	<thead>
			  	<tr>
			  	<td>收获日期</td>
			  	<td>送货人</td>
			  	<td>货物名称</td>
			  	<td>货物数量</td>
			  	<td>已照数量</td>
			  	<td>已取数量</td>
			  	<td>单位</td>
			  	<td>重量(吨)</td>
			  	<td>辐照费</td>
			  	<td>已付(元)</td>
			  	<td>未付(元)</td>
			  	</tr>
			  	</thead>
			  	<tbody id="receivableinformation">
			  		
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
		  </div>
		  费用说明：<input type="text" id="chargeIntroduction"  />
		  收费方式：<crazy:dictselect id="paytype" name="paytype" emptyKey="" emptyValue="请选择"  collection="${paytype}" ></crazy:dictselect> 
		   本次交款：<input type="text" placeholder="取值大于零" id="thePayment"  />元 &nbsp; &nbsp; &nbsp; &nbsp;
		  <input type="button"  id="docharge" value="收款" />
		  </fieldset> 
		  <hr>
		  
		  <fieldset>
		  <legend>当前客户交款情况</legend>
		    <table class="pp-list table  table-bordered">
		    	<thead>
		    	<tr>
		    	<td>交款单位</td>
		    	<td>交款日期</td>
		    	<td>交款金额</td>
		    	<td>备注</td>
		    	<td>收获日期</td>
		    	<td>货物名称</td>
		    	<td>货物数量</td>
		    	<td>重量(吨)</td>
		    	<td>收费方式</td>
		    	<td>费用(元)</td>
		    	<td>已付(元)</td>
		    	<td>未付(元)</td>
		    	</tr>
		    	</thead>
		    	<tbody id="currentchargeinfo">
		    	
		    	</tbody>
		    	<tfoot id="sum">
		    	<tr>
		    	<td colspan="2">合计</td>
		    	<td id="currentamountsum">￥0.00</td>
		    	<td colspan="6"></td>
		    	<td id="currentfeesum">￥0.00</td>
		    	<td id="currentpaidsum">￥0.00</td>
		    	<td id="currentunpaidsum">￥0.00</td>
		    	</tr>
		    	</tfoot>
		    </table>
		  </fieldset>
		  
  </div>
  </body>
</html>
