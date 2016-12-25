<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.18.custom.min.js"></script>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/css/jquery-ui-1.8.18.custom.css">

<%-- <link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/default.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/reset.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/style.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/ZebraDatepicker/core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ZebraDatepicker/zebra_datepicker.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system/cargoinfo/list.js"></script> 


</head>
<body>
<div class="m-b-md">
	<form class="form-inline" role="form" id="searchForm" name="searchForm">
		<div class="form-group">
			<label class="control-label"> <span
				class="h4 font-thin v-middle">货物名称:</span></label> <input
				class="input-medium ui-autocomplete-input" id="cargoname"
				name="cargoname">
		</div>
		<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
		<input type="button" id="addcargo" value="新增" class="btn btn-default"/>
		<input type="button" id="editcargo" value="编辑" class=" btn btn-default"/>
		<input type="button" id="deletecargo" value="删除" class="btn btn-default"/>
	</form>
</div>
<div id="adddialog" title="新增货物" style="display: none;">
			<div class="form-group">
				<label class="col-sm-3 control-label">*货物名称</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc" maxlength="64"
						placeholder="请输入货物名称" name="addcargoname" id="addcargoname">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">单位</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc" maxlength="128"
						placeholder="请输入单位" name="org" id="org">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">辐射方式</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入辐射方式" maxlength="32"
						name="irradtype" id="irradtype">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">辐照时间</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入辐照时间(格式:yyyy-mm-dd)"
						name="irradtime" id="irradtime">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">时间单位</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入时间单位" maxlength="32"
						name="timeorg" id="timeorg">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
</div>
<header>
	<div>
		<form action="" class="form-horizontal">
			<table class="pp-list table table-striped table-bordered"
				style="margin-bottom: -3px;">
				<thead>
					<tr style="line-height: 27px;">
						<td style="text-align: center"><input type="checkbox" id="checkall"/> 选择</td>
						<td style="text-align: center">货物名称</td>
						<td style="text-align: center">单位</td>
						<td style="text-align: center">辐照方式</td>
						<td style="text-align: center">辐照时间</td>
						<td style="text-align: center">时间单位</td>
					</tr>
				</thead>
				<tbody id="cargoes">
					<c:forEach items="${cargoes}" var="key">
						<tr>
							<td style="text-align: center"><input type="checkbox" name="cargoes" value="${key.id}@_@${key.cargoname}" /> </td>
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
	</div>
</header>
<div class="table-responsive">
	<div id="paging" class="pagclass">
	共 <span id="totalpage">${params.totalpage}</span> 页===第 <span id="pageNow">${params.pageNow}</span> 页===共<span id="pagerecode">${params.pagerecode}</span> 条            
    <input type="button" id="pre" value="pre"/>   <input type="button" id="next" value="next"/>
	</div>
</div>
</body>
</html>