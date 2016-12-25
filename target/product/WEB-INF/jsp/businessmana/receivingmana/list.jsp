<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.crazycode.org/commontaglib" prefix="crazy"%>
<html>
<head>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.18.custom.min.js"></script>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/css/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/notebook/notebook_files/bootstrap.css">

 <link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/default.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/reset.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/style.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/ZebraDatepicker/core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ZebraDatepicker/zebra_datepicker.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/businessmana/receivingmana/list.js"></script>


</head>
<body>
	<div class="m-b-md">
	  <div id="receivedCargosHeader">
			<table class="pp-list table table-striped table-bordered">
				<tr>
				 <td >
				 <fieldset>
				 <legend>基本信息</legend>
					 <table class="pp-list table table-striped table-bordered">
						<tr><td>收货日期：</td><td><input id="receivetime" name="receivetime" type="text" class="input-medium ui-autocomplete-input" /> <td></tr>
					    <tr> <td>送货单位：</td><td><input type="text" id="showorgs" name="showorgs" ></input> </td> </tr>
						<tr> <td>送货人：</td><td><crazy:dictselect id="showBringTakeInfos" name="showBringTakeInfos"  collection="${showBringTakeInfos}"></crazy:dictselect> </td> </tr>
						<tr> <td>联系电话 ：</td><td><input id="telnum" name="telnum" type="text" class="input-medium ui-autocomplete-input"  /> </td> </tr>
					 </table>
					</fieldset>
				 </td> 
				 <td>
				 <fieldset>
				 <legend>详细信息</legend>
				 		<table class="pp-list table table-striped table-bordered" id="detailTable">
							<tr>
							<td colspan="1">货物名称：</td>
							<td colspan="5"><crazy:dictselect id="cargoname" name="cargoname"  collection="${showcargoinfos}"  ></crazy:dictselect> </td>
							</tr>
							
							<tr>
							<td>货物数量：</td><td><input id="cargocount" name="cargocount" type="text" class="input-medium ui-autocomplete-input" /> </td>
							<td>数量单位：</td><td><crazy:dictselect id="showcountorginfos" name="showcountorginfos" collection="${showcountorginfos }"/></td>
							<td>要求剂量：</td><td><input id="reqreagent" name="reqreagent" type="text" class="input-medium ui-autocomplete-input" /> </td>
							</tr>
						
						    <tr>
							<td>货物重量：</td><td><input id="cargoweight" name="cargoweight" type="text" class="input-medium ui-autocomplete-input" /> </td>
							<td>含  菌   数：</td><td><input id="funguscount" name="funguscount" type="text" class="input-medium ui-autocomplete-input" /> </td>
							<td>辐照方式：</td> <td><crazy:dictselect id="irradtypes" name="irradtypes" collection="${irradtypes }"/>  </td>
						    </tr>
						
						    <tr>
						    <td>辐照时间：</td> <td><input id="irradtime" name="irradtime" type="text" class="input-medium ui-autocomplete-input" /></td>
						    <td>时间单位：</td> <td><crazy:dictselect id="timeorgs" name="timeorgs" collection="${timeorgs }"/></td>
						    <td>辐照类型：</td> <td><crazy:dictselect id="irradflags" name="irradflags" collection="${irradflags }"/></td>
						    </tr>
					    </table>
					</fieldset>
				 </td> 
				</tr>
                <tr> <td colspan="2">  <input type="button" id="save" value="Save"> 依照当前记录: <input type="checkbox"  id="asCurrentRecord" value="0" /> </td></tr>
			</table>
		</div>
		<input id="hiddenshowhr" type="button" value="︽︾" style="background-color:silver;width:100%" />
		<fieldset>
			<legend>已收货物</legend>
			<header>
				<div>
					<form action="" class="form-horizontal">
					送货单位：<input type="text" id="searchorg" name="searchorg"  />
					收货时间：<input id="receivetime1" name="receivetime1" type="text" class="input-medium ui-autocomplete-input" />
					送  货  人： <crazy:dictselect id="showBringTakeInfos1" name="showBringTakeInfos1" emptyKey="" emptyValue="请选择"   collection="${showBringTakeInfos}"></crazy:dictselect>
					最        近:<select name="lastdate" id="lastdate">
					<option value="">请选择</option> 
					<option value="1">1天</option> 
					<option value="2">2天</option> 
					<option value="3">3天</option> 
					</select>
					<input id="query" name="query" type="button" value="Query"/>
					<br/>
					<div align="left"><font size="2" color="red"  >双击完成收获(PS:灰色显示不可进行确认)</font></div>
						<table class="pp-list table  table-bordered"
							style="margin-bottom: -3px;">
							<thead>
								<tr style="line-height: 27px;">
									<!-- <td style="text-align: center"><input type="checkbox" id="checkall" /> 选择</td> -->
									<td style="text-align: center">收货日期</td>
									<td style="text-align: center">送货单位</td>
									<td style="text-align: center">送货人</td>
									<td style="text-align: center">联系电话</td>
								</tr>
							</thead>
							<tbody id="receivedCargosBody">
							
							</tbody>
						</table>
					</form>
				</div>
			</header>
			<div class="table-responsive">
				<div id="paging" class="pagclass">
				共 <span id="totalpage"></span> 页===第 <span id="pageNow"></span> 页===共<span id="pagerecode"></span> 条            
			    <input type="button" id="pre" value="pre"/>   <input type="button" id="next" value="next"/>
				</div>
		    </div>
		    <div>
		    	<table class="pp-list table table-striped table-bordered">
		    		<thead>
						<td style="text-align: center">货物名称</td>
						<td style="text-align: center">数量</td>
						<td style="text-align: center">重量(吨)</td>
						<td style="text-align: center">辐照方式</td>
						<td style="text-align: center">辐照时间</td>
						<td style="text-align: center">时间单位</td>
		    		</thead>
		    		<tbody id="receivedCargosDetail">
		    		
		    		</tbody>
		    	</table>
		    </div>
		</fieldset>
		
		<div id="receivedcagoprint" style="display: none;">
		<table class="pp-list table table-striped table-bordered">
			 <tr>
				  <td>
				   <table class="pp-list table table-striped table-bordered">
				   <tr>
					   <td><nobr>收获日期：</nobr> </td>
					   <td id="dialogreceivetime">  </td>
				   </tr>
				   <tr>
					   <td> <nobr>送货单位：</nobr> </td>
					   <td id="dialogbringcargoorg"> </td>
				   </tr>
				   <tr>
					   <td><nobr>送货人：</nobr> </td>
					   <td id="dialogbringcargoperson"> </td>
				   </tr>
				   <tr>
					   <td><nobr>联系电话：</nobr> </td>
					   <td id="dialogtel">  </td>
				   </tr>
				   </table>
				  </td>
			      <td><img id="printpicture" width="100%" height="100%" alt="picture" src="${picture}"></td>
			 </tr>
		</table>
		<table class="pp-list table table-striped table-bordered">
		<thead>
		 <tr>
		  <td>货物名称</td>
		  <td>数量</td>
		  <td>重量(吨)</td>
		  <td>辐照时间</td>
		  <td>时间单位</td>
		 </tr>
		</thead>
		<tbody id="dialogdetail">
		
		</tbody>
		</table>
		</div>
		
	</div>
</body>
</html>