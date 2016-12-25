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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/businessmana/radiationmana/list.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>

<script type="text/javascript">
</script>

</head>
<body>
	<div class="m-b-md">
		<div id="receivedCargosHeader">
			<table class="pp-list table table-striped table-bordered">
				<tr>
					<td>
						<fieldset>
							<legend>待照货物</legend>
							<form>
								送货单位：<crazy:dictselect id="showorgs" name="showorgs" emptyKey="" emptyValue="请选择"  collection="${showorgs}" ></crazy:dictselect>
								收货时间：<input id="receivetime" name="receivetime" type="text" class="input-medium ui-autocomplete-input" />
								货物名称： <crazy:dictselect id="cargoname" name="cargoname"  emptyKey="" emptyValue="请选择"  collection="${showcargoinfos}"  ></crazy:dictselect>
								<input id="query" name="query" type="button" value="Query"/>
								<table class="pp-list table  table-bordered">
									<thead>
										<tr>
											<td>送货单位</td>
											<td>收获日期</td>
											<td>货物名称</td>
											<td>货物数量</td>
											<td>已照数量 </td>
											<td>数量单位 </td>
											<td>重量(吨) </td>
											<td>辐照方式</td>
											<td>辐照时间</td>
											<td>时间单位</td>
											<td>要求剂量</td>
											<td>照前菌数</td>
										</tr>
									</thead>
									<tbody id="radiationsbody">
										
									</tbody>
								</table>
							</form>
							<div class="table-responsive">
								<div id="paging" class="pagclass">
									共 <span id="totalpage"></span> 页===第 <span id="pageNow"></span>
									页===共<span id="pagerecode"></span> 条 <input type="button" id="pre" value="pre" /> <input type="button" id="next" value="next" />
								</div>
							</div>
						</fieldset>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="editradiationdiv" style="display: none">
		<table style="border:1px; border-color: red;width: 100%">
		<thead>
		<tr>
		<td>货物名称</td><td>辐照数量</td><td>辐照重量</td><td>辐照方式</td><td>辐照时间</td>
		</tr>
		</thead>
		 <tr>
		 <td><input id="divcargoname" name="divcargoname" type="text" disabled="disabled" /> <input type="hidden" id="receiveorgid" name="receiveorgid" /> </td>
		 <td> 
		 <input type="text" id="irradednum" name="irradednum" /> &nbsp;
		 <input type="hidden" id="hiddenoldirradednum"/> 
		 <crazy:dictselect id="showcountorginfos" name="showcountorginfos" collection="${showcountorginfos}"  ></crazy:dictselect> 
		 </td>
		 <td> <input type="text" id="cargoweight" name="cargoweight" /> </td>
		 <td><crazy:dictselect id="irradtypes" name="irradtypes" collection="${irradtypes}"  ></crazy:dictselect></td>
		 <td> <input type="text" id="irradtime" name="irradtime" /> <crazy:dictselect id="timeorgs" name="timeorgs" collection="${timeorgs}"  ></crazy:dictselect> </td>
		</tr>
		</table>
		
		<table>
		<thead>
		<tr>
		 <td>日期</td> 
		 <td>序号</td> 
		 <td>交接时间</td> 
		 <td>首位吊具号</td> 
		 <td>辐照批号</td> 
		 <td>入场时间</td> 
		 <td>吊具号码</td> 
		 <td>装载模式</td> 
		 <td>运行参数</td> 
		 <td>运行圈数</td> 
		 <td>变动说明</td> 
		 <td>下圈开始时间</td> 
		 <td>备注</td> 
		 </tr>
		</thead>
		<tr>
		<td> <input type="text" id="doirraddate" placeholder="格式：yyyy-mm-dd" /> </td>
		<td> <input type="text" id="ordernum" /> </td>
		<td> <input type="text" id="connecttime"/> </td>
		<td> <input type="text" id="firstspreadernum" /> </td>
		<td> <input type="text" id="irradbatchnum" /> </td>
		<td> <input type="text" id="entrancetime" /> </td>
		<td> <input type="text" id="spreadernum" /> </td>
		<td> <crazy:dictselect id="loadmodel" name="loadmodel" collection="${loadmodel}"  ></crazy:dictselect>  </td>
		<td> <input type="text" id="runparam" /> </td>
		<td> <input type="text" id="runcycle" /> </td>
		<td> <input type="text" id="changedesc" /> </td>
		<td> <input type="text" id="nextcyclestarttime" /> </td>
		<td> <input type="text" id="mask" /> </td>
		</tr>
		</table>
		
		</div>
		
						<fieldset>
							<legend>在照货物</legend>
							<table class="pp-list table table-striped table-bordered" >
								<thead>
								<tr>
								<td>货物名称</td>
								<td>送货单位</td>
								<td>货物数量</td>
								<td>数量单位</td>
								<td>重量(吨)</td>
								<td>放置位置/吊具号</td>
								<td>辐照方式</td>
								<td>开始时间</td>
								<td>辐照时间</td>
								<td>时间单位</td>
								</tr>
								</thead>
								<tbody id="radiatingcargo">
								
								</tbody>
							</table>
						</fieldset>
		
	</div>
</body>
</html>