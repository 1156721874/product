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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/businessmana/confirmationmana/list.js"></script>

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
							<legend>基本信息</legend>
							<form>
							    
							              确认完毕：<input type="checkbox" id="confirmover" name="confirmover" />
								送货单位：<input type="text" id="searchorg" name="searchorg"  /> <%-- <crazy:dictselect id="showorgs" name="showorgs" emptyKey="" emptyValue="请选择"  collection="${showorgs}" ></crazy:dictselect> --%>
								收货时间：<input id="receivetime" name="receivetime" type="text" class="input-medium ui-autocomplete-input" />
								送  货  人： <crazy:dictselect id="showBringTakeInfos" name="showBringTakeInfos" emptyKey="" emptyValue="请选择"   collection="${showBringTakeInfos}"></crazy:dictselect>
								<input id="query" name="query" type="button" value="Query"/>
								<input type="button" id="deleteconfirm" name="deleteconfirm" value="Delete" />
								<div align="left">
								<font size="2" color="red"  >
								双击完成收获确认<br>
								注意:(1)灰色为已确认的数据,再次确认将删除此货物已有的辐照、出货数据,然后重建数据;<br>
								    (2)删除操作:红色只删除当前收获确认菜单数据，灰色数据删除会删除当前以及连带删除后续的辐照、出货数据
								</font>
								</div>
								<table class="pp-list table  table-bordered">
									<thead>
										<tr>
											<td><input type="checkbox" id="checkall" /> 选择</td>
											<td>收获日期</td>
											<td>送货单位</td>
											<td>送货人</td>
											<td>联系电话 </td>
										</tr>
									</thead>
									<tbody id="cargoesbody">
										
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
		
		<div id="editconfirmdetaildiv" style="display: none" >
		<table>
		<tr>
		<td>货物名称：</td><td><crazy:dictselect id="cargoname" name="cargoname"  collection="${showcargoinfos}"  ></crazy:dictselect></td>
		</tr>
		<tr>
		<td>货物数量：</td><td> <input id="cargocount"  name="cargocount" /> </td>
		</tr>
		<tr>
		<td>货物重量：</td><td><input id="cargoweight" name="cargoweight" type="text"/> </td>
		</tr>
		<tr>
		<td>数量单位：</td><td><crazy:dictselect id="showcountorginfos" name="showcountorginfos" collection="${showcountorginfos }"/></td>
		</tr>
		<tr>		
		<td>含  菌  数 ：</td><td> <input id="funguscount" name="funguscount" type="text" /> </td>
		</tr>
		<tr>		
		<td>要求剂量 ：</td><td> <input id="reqreagent" name="reqreagent" type="text" /> </td>
		</tr>
		<tr>		
		<td>辐照方式 ：</td><td><crazy:dictselect id="irradtypes" name="irradtypes" collection="${irradtypes }"/></td>
		</tr>
		<tr>
		<td>辐照时间 ：</td><td> <input id="irradtime" name="irradtime" type="text" /> </td>
		</tr>
		<tr>
		<td>时间单位 ：</td><td> <crazy:dictselect id="timeorgs" name="timeorgs" collection="${timeorgs }"/> </td>
		</tr>
		<tr>
		<td>备注信息：</td><td><crazy:dictselect id="irradflags" name="irradflags" collection="${irradflags }"/></td>
		</tr>
		<tr>
		<td>收取费用：</td><td> <input type="text" id="fee" name="fee"  /> </td>
		</tr>
		<tr>
		<td>其他说明：</td><td> <input id="mask" name="mask" type="text" /> </td>
		</tr>
		</table>
		</div>
		
		<div id="confirmdialog" style="display: none;">
		<table>
		 <tr>
			  <td>
			   <table>
			   <tr>
				   <td><nobr>收获日期：</nobr> </td>
				   <td><input id="receivecargotime" type="text"  /> </td>
			   </tr>
			   <tr>
				   <td> <nobr>送货人：</nobr> </td>
				   <td><crazy:dictselect id="bringpeople" name="bringpeople" emptyKey="" emptyValue="请选择"   collection="${showBringTakeInfos}"></crazy:dictselect></td>
			   </tr>
			   <tr>
				   <td><nobr>送货单位：</nobr> </td>
				   <td><crazy:dictselect id="bringorg" name="bringorg" emptyKey="" emptyValue="请选择"  collection="${showorgs}" ></crazy:dictselect></td>
			   </tr>
			   <tr>
				   <td><nobr>联系电话：</nobr> </td>
				   <td><input id="bringcargopeopletel" type="text"> </td>
			   </tr>
			   </table>
			  </td>
		      <td><img id="confirmpicture" width="100%" height="100%" alt="picture" src="${picture}"></td>
		 </tr>
		</table>
		</div>
		
		
						<fieldset>
							<legend>详细信息</legend>
							<input name="editdetail"  id="editdetail" type="button" value="Edit" />
							 <input name="deletedetail"  id="deletedetail" type="button" value="Delete"/><br>
							 <font size="2" color="red"  >红色数据只删除当前菜单数据;对于灰色数据：会连带删除后续辐照、出货数据</font>
							<table class="pp-list table table-striped table-bordered" >
								<thead>
								<tr>
								<td>选择<input type="checkbox" id="checkalldetail" /></td>
								<td>货物名称</td>
								<td>数量</td>
								<td>单位</td>
								<td>重量(吨)</td>
								<td>费用(元)</td>
								<td>辐照方式</td>
								<td>辐照时间</td>
								<td>时间单位</td>
								<td>剂量要求</td>
								<td>照前含菌数</td>
								<td>备注</td>
								</tr>
								</thead>
								<tbody id="detailTable">
								
								</tbody>
							</table>
						</fieldset>
		
	</div>
</body>
</html>