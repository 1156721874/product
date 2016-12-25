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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/charge/userRoleList.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>

</head>
  
  <body>
  <div class="m-b-md">
		交款单位：
		<input type="text" maxlength="32" id="time" name="time"/>
		<crazy:dictselect id="showorgs" name="showorgs" emptyKey="" emptyValue="请选择"  collection="${showorgs}" ></crazy:dictselect>
		<input type="button" id="query" name="query" value="Query"/>
		<fieldset>
		  <legend>应收款信息</legend>
		  <table class="pp-list table  table-bordered">
	
		<form action="" class="form-horizontal">
			<table class="pp-list table table-striped table-bordered"
				style="margin-bottom: -3px;">
				<thead>
					<tr style="line-height: 27px;">
						<td style="text-align: center">收货日期</td>
						<td style="text-align: center">货物名称</td>
						<td style="text-align: center">货物数量</td>
						<td style="text-align: center">重量(吨)</td>
						<td style="text-align: center">费用</td>
						<td style="text-align: center">已付(元)</td>
						<td style="text-align: center">未付(元)</td>
					</tr>
				</thead>
				<tbody id="cargoes">
					<c:forEach items="${cargoes}" var="key">
						<tr>
							<td style="text-align: center"> ${key.cargoname} </td> 
							<td style="text-align: center"> ${key.org} </td>
							<td style="text-align: center"> ${key.irradtype} </td> 
							<td style="text-align: center">
							${key.irradtime}
							 <%-- <fmt:formatDate value="${key.irradtime}" pattern="yyyy/MM/dd  HH:mm:ss" /> --%>
							 </td>
							<td style="text-align: center"> ${key.timeorg} </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>


		  费用说明：<input type="text" id="chargeIntroduction"  /> 本次交款：<input type="text" id="thePayment"  />元 &nbsp; &nbsp; &nbsp; &nbsp;
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
		    	</tr>
		    	</thead>
		    	<tbody id="currentchargeinfo">
		    	
		    	</tbody>
		    	<tfoot id="sum">
		    	<tr>
		    	<td colspan="2">合计</td>
		    	<td colspan="2">￥3640.00</td>
		    	</tr>
		    	</tfoot>
		    </table>
		    <hr>
		    <table class="pp-list table  table-bordered">
		    	<thead>
		    	<tr>
		    	<td>ID号</td>
		    	<td>收获日期</td>
		    	<td>货物名称</td>
		    	<td>货物数量</td>
		    	<td>重量(吨)</td>
		    	<td>费用(元)</td>
		    	<td>已付(元)</td>
		    	<td>未付(元)</td>
		    	</tr>
		    	</thead>
		    	<tbody id="detailInfoOfCharge">
		    	
		    	</tbody>
		    	<tfoot>
		    	<tr>
		    	<td>合计</td>
		    	<td></td>
		    	<td></td>
		    	<td></td>
		    	<td></td>
		    	<td>￥0</td>
		    	<td>￥0</td>
		    	<td>￥0</td>
		    	</tr>
		    	</tfoot>
		    </table>
		  </fieldset>
  </div>
  </body>
</html>
