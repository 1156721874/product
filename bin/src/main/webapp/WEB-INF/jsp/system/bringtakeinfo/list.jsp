<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-

1.8.18.custom.min.js"></script>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/css/jquery-ui-1.8.18.custom.css">

<%-- <link rel="stylesheet" href="${pageContext.servletContext.contextPath 

}/js/ZebraDatepicker/public/default.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/reset.css">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/js/ZebraDatepicker/public/style.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/ZebraDatepicker/core.js"></script>
<script type="text/javascript" 

src="${pageContext.request.contextPath}/js/ZebraDatepicker/zebra_datepicker.js"></script> --%>
<script type="text/javascript" 

src="${pageContext.request.contextPath}/js/system/bringtakeinfo/list.js"></script> 


</head>
<body>
<div class="m-b-md">
	<form class="form-inline" role="form" id="searchForm" name="searchForm">
		<div class="form-group">
			<label class="control-label"> <span
				class="h4 font-thin v-middle">送取货人:</span></label> <input
				class="input-medium ui-autocomplete-input" id="name"
				name="name">
		</div>
		<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
		<input type="button" id="addcargo" value="新增" class="btn btn-default"/>
		<input type="button" id="editcargo" value="编辑" class=" btn btn-default"/>
		<input type="button" id="deletecargo" value="删除" class="btn btn-default"/>
	</form>
</div>
<div id="adddialog" title="新增送取货人" style="display: none;">
<form id="demo"  enctype="multipart/form-data"  method="post" target='frameFile'
 action="${pageContext.request.contextPath}/system/bringtakeinfo/dofinger.do" >
			<div class="form-group">
				<label class="col-sm-3 control-label">*送取货人</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc" maxlength="64"
						placeholder="送取货人名称" name=addname id="addname">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">单位</label>
				<div class="col-sm-9">
					<input type="text" class="form-control checkacc" maxlength="128"
						placeholder="请输入单位" name="orgname" id="orgname">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">电话</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入电话" 
                       maxlength="32" name="tel" id="tel">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" placeholder="请输入备注"
						name="mask" id="mask">
				</div>
				<label class="col-sm-3 control-label">图片地址</label>
				<div class="col-sm-9">
					<input type="file"  class="form-control"  name="adds1" id="adds1">
					<input type="file"  style="display:none"   class="form-control"  name="adds2" id="adds2">
				</div>
				<div class="col-sm-9">
					<input type="button"  value="指纹获取" class="form-control" onclick="Showdemo()"
						name="gain" id="gain">
				</div>
			</div>
			<div class="line line-dashed line-lg pull-in"></div>
			</form>
</div>
<iframe id='frameFile' name='frameFile' style="display:none" >
<input type="text"  value="${result}"  id="result" >
</iframe>
<header>
	<div>
		<form action="" class="form-horizontal">
			<table class="pp-list table table-striped table-bordered"
				style="margin-bottom: -3px;">
				<thead>
					<tr style="line-height: 27px;">
						<td style="text-align: center"><input type="checkbox" 

id="checkall"/> 选择</td>
						<td style="text-align: center">送取货人</td>
						<td style="text-align: center">单位</td>
						<td style="text-align: center">电话	</td>
						<td style="text-align: center">备注</td>
					</tr>
				</thead>
				<tbody id="bringtakeinfos">
					<c:forEach items="${bringtakeinfos}" var="key">
						<tr>
							<td style="text-align: center"><input type="checkbox" 

name="bringtakeinfos" value="${key.id}@_@${key.name}" /> </td>
							<td style="text-align: center"> ${key.name} </td> 
							<td style="text-align: center"> ${key.orgname} </td>
							<td style="text-align: center"> ${key.tel} </td> 
							<td style="text-align: center">
							${key.mask}
							 <%-- <fmt:formatDate value="${key.irradtime}" 

pattern="yyyy/MM/dd  HH:mm:ss" /> --%>
							 </td>
		
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</header>
<div class="table-responsive">
	<div id="paging" class="pagclass">
	共 <span id="totalpage">${params.totalpage}</span> 页===第 <span id="pageNow">${params.pageNow}</span> 

页===共<span id="pagerecode">${params.pagerecode}</span> 条            
    <input type="button" id="pre" value="pre"/>   <input type="button" id="next" value="next"/>
	</div>
</div>
<script type="text/javascript">
$(function (){
	
});


 function   Showdemo()  
   {  
     
          new ActiveXObject("Wscript.Shell").run("Demo.exe");
          //获取内容
          $('#adds1').val("C:\Users\name\Documents\Tencent Files\825905961\FileRecv\UR开发程序和驱动\ZKFinger SDK_Ver2.3.3.5\Demo\fingerprint.bmp");
          $('#adds2').val("C:\Users\name\Documents\Tencent Files\825905961\FileRecv\UR开发程序和驱动\ZKFinger SDK_Ver2.3.3.5\Demo\fingerprint.jpg");

         //
         //new ActiveXObject("Wscript.Shell").run("D:\\工具\\Pb6安装\\Pb6安装\\SETUP.EXE");

    
   }

</script>	
</body>
</html>