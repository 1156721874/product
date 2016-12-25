<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/privilegemana/userRoleList.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/laydate/laydate.js"></script>

</head>
  
  <body>
  <div class="m-b-md">
	  <div id="adduserdiv" style="display: none;">
		  <form id="addform">
			  <table class="pp-list table table-striped table-bordered">
			  <tr>
			  	<td>登陆账号</td><td><input type="text" id="userid" name="userid" /> </td>
			  </tr>
			  <tr>
			  	<td>真实姓名</td> <td> <input type="text" id="username" name="username" /> </td>
			  </tr>
			  <tr>
			  	<td>角色</td><td> <crazy:dictselect id="role" name="role" collection="${roles}"/> </td>
			  </tr>
			  <tr>
			  	<td>权限</td><td> <crazy:checkboxes  paramString="" collection="${menuesitem}" name="menuesitemes"/> </td>
			  </tr>
			  </table>
		   </form>
	  </div>
  <input type="button" id="add" value="Add" /> <input type="button" id="edit" value="Edit" /> <input id="delete" type="button" value="Delete" />
  <table class="pp-list table table-striped table-bordered">
  <thead>
  <tr>
  <td>选择</td>
  <td>用户名</td>
  <td>角色</td>
  <td>货物信息</td>
  <td>送货信息</td>
  <td>送取货人</td>
  <td>单位信息</td>
  <td>收货管理</td>
  <td>收货确认</td>
  <td>辐照管理</td>
  <td>出货管理</td>
  <td>权限配置</td>
  <td>收款</td>
  <td>日报表</td>
  <td>月报表</td>
  <td>应收已付</td>
  </tr>
  </thead>
  <tbody id="userprivileges">
  	
  </tbody>
  </table>
  </div>
  </body>
</html>
