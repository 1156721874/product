<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/notebook/notebook_files/font.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/notebook/notebook_files/app.v1.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lanyuan.css" type="text/css">  
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/css/jquery-ui-1.8.18.custom.css">
 	<link rel="stylesheet" href="${pageContext.servletContext.contextPath }/notebook/notebook_files/bootstrap.css"> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-ui-1.8.18.custom.min.js"></script>
	
		<script type="text/javascript" src="${pageContext.request.contextPath}/notebook/notebook_files/app.v1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/notebook/notebook_files/app.plugin.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-validation/jquery.validate.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-validation/messages_cn.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/layer-v1.9.2/layer/layer.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/common.js"></script>
	<script type="text/javascript" src=""></script>
    <title>修改密码</title>
    <script type="text/javascript">
   function  validate(){
	   if(CommnUtil.haveOneTagIsNull("oldpassword,newpassword,newpasswordconfirm")){
		   alert("标红部分必须填写！");
		   return false;
	   }
	   if($("#newpassword").val()!=$("#newpasswordconfirm").val()){
		   alert("新密码确认不一致！");
		   return false;
	   }
	   return true;
   }
    </script>
  </head>
  
  <body>
  <div class="m-b-md">
  <form action="${pageContext.servletContext.contextPath }/modifypassword.do" onsubmit="validate();" method="post" id="form1" class="form-horizontal">
  <span style="color: red;">${error}</span>
	  <table class="pp-list table table-striped table-bordered">
	  <tr>
	  <td style="text-align: center">账户名：</td>
	  <td>
	  <input type="hidden" id="userid" name="userid" value="${user.userId}"  /> 
	  <input type="text" id="showuserid" name="showuserid" value="${user.userId}" disabled="disabled" /> 
	  </td>
	  </tr>
	  <tr>
	  <td style="text-align: center">旧密码：</td><td> <input type="password" id="oldpassword" name="oldpassword" /> </td>
	  </tr>
	  <tr>
	  <td style="text-align: center">新密码：</td><td> <input type="password" id="newpassword" name="newpassword" /> </td>
	  </tr>
	  <tr>
	  <td style="text-align: center">新密码确认：</td><td> <input type="password" id="newpasswordconfirm" name="newpasswordconfirm" /> </td>
	  </tr>
	  <tr> 
	  <td  style="text-align: center" colspan="2"> <input type="button" name="back" value="返回"  onclick="window.history.go(-1)" /> &nbsp;&nbsp;&nbsp;&nbsp; <input type="submit" name="submit" value="提交"  /> </td>
	   </tr>
	  </table>
  </form>
  </div>
  </body>
</html>
