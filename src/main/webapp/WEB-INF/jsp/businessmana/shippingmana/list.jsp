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
<div id="cargoinfo" style="display: none;" >
<table class="pp-list table  table-bordered" >
<tr>
<td>送货日期：</td><td> <span id="receivetime"> </span> </td>
</tr>
<tr>
<td>货物名称：</td><td> <span id="divcargoname"> </span> </td>
</tr>
<tr>
<td>送货单位：</td><td> <span id="receiveorgname"> </span> </td>
</tr>
<tr>
<td>货物数量：</td><td> <span id="irradednum"> </span> </td>
</tr>
<tr>
<td>计量单位：</td><td> <span id="countorg"> </span> </td>
</tr>
<tr>
<td>已取数量：</td><td> <span id="cargoweight"> </span> </td>
</tr>
<tr>
<td>备注信息：</td><td> <span id="mask"> </span> </td>
</tr>
</table>
</div>
	<div class="m-b-md">
		<div>
		<fieldset>
		<legend>待出货物</legend>
			送货单位：<input type="text" id="searchorg" name="searchorg"  />
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
			<td>取货单位</td> <td> <input type="text" id="showorgs1" name="showorgs1" ></input> </td>
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
		    <tr>			
			<td>取货数量</td> <td> <input type="text" id="takecargocount" /> </td>
			</tr>
			<tr> <td  colspan="2"> <input type="button" id="save" value="Save"/> 依照当前记录：<input id="flowcurrentrecord" type="checkbox" />  </td> </tr>
		</table>
		</fieldset>
		</div>
		
		<div id="shoppingprintconfirm" style="display:none;">
		  <table class="pp-list table table-striped table-bordered">
		    <tr>
		      <td>
				<table class="pp-list table table-striped table-bordered">
					<tr>
					  <td>出货日期</td> <td id="shoppingprintconfirmouttime"></td>
					</tr>
					<tr>  
					  <td>取货单位</td>  <td id="shoppingprintconfirmtakeorg"></td>
					</tr>
					<tr>  					  
					  <td>委托单位</td>  <td id="shoppingprintconfirmproxyorg"></td>
					</tr>
					<tr>  					  
					  <td>取货人</td>  <td id="shoppingprintconfirmtakepeople"></td>
					</tr>
					<tr>  					  
					  <td>联系电话</td>  <td id="shoppingprintconfirmtel"></td>
					</tr>
				</table>
		      </td>
		      <td>
		       <img id="shoppingprintconfirmpicture" width="100%" height="100%" alt="picture" src="${picture}">
		      </td>
		    </tr>
		  </table>
			<table class="pp-list table table-striped table-bordered">
				<thead>
				  <tr>
				   <td>货物名称</td>
				   <td>数量</td>
				   <td>重量(吨)</td>
				  </tr>
				</thead>
				<tbody id="shoppingprintconfirmbody">
				  
				</tbody>
			</table>
		</div>
		
		<div class="m-b-md">
			<fieldset>
			<legend>已出货物</legend>
			出货时间：<input type="text" id="takecargotime"  />
			取货单位：<input type="text" id="showorgsoftaked" name="showorgsoftaked"  /><%-- <crazy:dictselect id="showorgsoftaked" name="showorgsoftaked"  emptyKey="" emptyValue="请选择" collection="${showorgs}" ></crazy:dictselect> --%>
			<input id="querytakedcargo"  type="button" value="Query"/>
			<br/>
			<div align="left"><font size="2" color="red"  >双击完成出货确认</font></div>
			<table  class="pp-list table  table-bordered">
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
			<tbody id="takedcargoes" >
			
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
