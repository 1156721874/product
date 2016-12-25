package com.product.Interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.product.entity.User;
import com.product.util.Common;

public class GlobalInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String currenturl =  request.getServletPath();
		System.out.println("getServletPath : "+request.getServletPath());///business/receivingmana.do
		System.out.println("getContextPath :"+request.getContextPath());///product
		System.out.println("getRequestURI : "+request.getRequestURI());///product/business/receivingmana.do
		System.out.println("getRemoteAddr : "+request.getRemoteAddr());//0:0:0:0:0:0:0:1
        System.out.println("getRequestURL : "+request.getRequestURL().toString());//http://localhost:8080/product/business/receivingmana.do
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        User user = (User)request.getSession().getAttribute("user");
        if(null!=user){
        	Set menuurls = user.getMenuurls();
        	if(menuurls.contains(currenturl)){
        		System.out.println("privilege Ok>>>>>>>>>>>>>>>>>>>>>>>");
        		return true;
        	}else {
        		 request.getRequestDispatcher("/noPrivileges.jsp").forward(request, response);  
        		 System.out.println("privilege reject>>>>>>>>>>>>>>>>>>>>>>>");
        		 return false;
        	}
        }
        System.out.println("privilege reject>>>>>>>>>>>>>>>>>>>>>>>");
		return false;
	}

}
