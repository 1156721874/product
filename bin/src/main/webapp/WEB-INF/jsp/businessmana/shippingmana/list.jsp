<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.crazycode.org/commontaglib" prefix="crazy"%>
<html>
<head>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/businessmana/shippingmana/list.js"></script>

<script type="text/javascript">
</script>

</head>
  
<body>
<div id="cargoinfo"></div>
	<div class="m-b-md">
		<div>
		<fieldset>
		<legend>待出货物</legend>
			送货单位：<crazy:dictselect id="showorgs" name="showorgs" emptyKey="" emptyValue="请选择"  collection="${showorgs}" ></crazy:dictselect>
			货物名称： <crazy:dictselect id="cargoname" name="cargoname"  emptyKey="" emptyValue="请选择"  collection="${showcargoinfos}"  ></crazy:dictselect>
			<input id="query" name="query" type="button" value="Query"/>
			<table class="pp-list table  table-bordered" >
			<thead>
			<tr>
			<td>送货单位</td>
			<td>货物名称</td>
			<td>货物数量</td>
			<td>已取数量</td>
			</tr>
			</thead>
			<tbody id="alltakecargoes">
			
			</tbody>
			</table>
				<div class="table-responsive">
					<div id="paging" class="pagclass">
						共 <span id="totalpage"></span> 页===第 <span id="pageNow"></span>
						页===共<span id="pagerecode"></span> 条 <input type="button" id="pre" value="pre" /> <input type="button" id="next" value="next" />
					</div>
				</div>
		</fieldset>
		</div>
		<div class="m-b-md">
		<fieldset>
		<legend>出货信息</legend>
		<table class="pp-list table table-striped table-bordered">
			<tr>
			<td>出货日期</td> <td> <input type="text" id="taketime" /> </td>
		    </tr>
		    <tr>
			<td>取货单位</td> <td> <crazy:dictselect id="showorgs1" name="showorgs1"   collection="${showorgs}" ></crazy:dictselect> </td>
			</tr>
		    <tr>
			<td>委托单位</td> <td> <input type="text" id="proxyOrg" /> </td>
			</tr>
		    <tr>
			<td>取  货  人</td> <td> <crazy:dictselect id="showBringTakeInfos" name="showBringTakeInfos"  collection="${showBringTakeInfos}"></crazy:dictselect> </td>
			</tr>
		    <tr>			
			<td>联系电话</td> <td> <input type="text" id="telnum" /> </td>
			</tr>
		    <tr>			
			<td>发  货   人</td> <td> <input type="text" id="shippers" /> </td>
			</tr>
			<tr> <td  colspan="2"> <input type="button" id="save" value="Save"/> 依照当前记录：<input id="flowcurrentrecord" type="checkbox" />  </td> </tr>
		</table>
		</fieldset>
		</div>
		
		<div class="m-b-md">
			<fieldset>
			<legend>已出货物</legend>
			出货时间：<input type="text" id="takecargotime"  />
			取货单位：<crazy:dictselect id="showorgsoftaked" name="showorgsoftaked"  emptyKey="" emptyValue="请选择" collection="${showorgs}" ></crazy:dictselect>
			<input id="querytakedcargo"  type="button" value="Query"/>
			<table  class="pp-list table table-striped table-bordered">
			<thead>
			<tr>
			<td>出货日期</td>
			<td>取货单位</td>
			<td>委托单位</td>
			<td>取  货  人</td>
			<td>联系电话</td>
			<td>发  货   人</td>
			</tr>
			</thead>
			<tbody id="takedcargoes">
			
			</tbody>
			</table>
				<div class="table-responsive">
					<div id="paging" class="pagclass">
						共 <span id="takedtotalpage"></span> 页===第 <span id="takedpageNow"></span>
						页===共<span id="takedpagerecode"></span> 条 <input type="button" id="takedpre" value="pre" /> <input type="button" id="takednext" value="next" />
					</div>
				</div>
		</fieldset>
		</div class="m-b-md">
		<div class="m-b-md">
			<fieldset>
			<legend>货物详情</legend>
				<table  class="pp-list table table-striped table-bordered">
				<thead>
				<tr>
				<td>货物名称</td>
				<td>货物数量</td>
				<td>数量单位</td>
				<td>重量(吨)</td>
				</tr>
				</thead>
				<tbody id="takedcargoedetail">
				
				</tbody>
				</table>
			</fieldset>
		</div>
		
	</div>
</body>
</html>
