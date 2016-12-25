package com.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.product.entity.ResFormMap;
import com.product.entity.User;
import com.product.mapper.PrivilegeMapper;
import com.product.mapper.ResourcesMapper;
import com.product.mapper.UserMapper;
import com.product.util.Common;
import com.product.util.TreeObject;
import com.product.util.TreeUtil;

@Controller
@RequestMapping("/")
public class UserController {
	
@Resource
private UserMapper usermapper;

@Resource
private ResourcesMapper resourcesMapper;
@Resource
private PrivilegeMapper privilegemapper;

@RequestMapping("login")
public String querySingleUser(Model model,@RequestParam(value="userId",required=false) String userId,@RequestParam(value="userPassword",required=false) String userPassword)
{
    System.out.println("UserController.querySingleUser()");
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    HttpSession session = request.getSession();
	if (Common.isEmpty(userId) || Common.isEmpty(userPassword)) {
		model.addAttribute("error", "用户名或密码不能为空！");
		return "/login";
	}
	User  user = new User();
	user.setUserId(userId);
	user.setUserPassword(userPassword);
	User puser = usermapper.querySingleUser(user);
	if(Common.isNotEmpty(puser))
	{
		User theuser = privilegemapper.queryUserALlprivilege(puser.getUserId());
		Map param = new HashMap();
		param.put("ids", theuser.getMenuids());
		Set<String> menuurl = usermapper.getUserMenuids(param);
	      Iterator<String> i =menuurl.iterator();
	      String newurl = "";
	      //货物信息的URL带有参数，给权限控制带来不便， 需要把参数去掉
	      while(i.hasNext()){
	    	  String url = i.next();
	    	  if(url.contains("?")){
	    		  newurl = url.replace("?pageNow=0&pageSize=20", "");
	    		  i.remove();
	    	  }
	      }
	      if(!"".equals(newurl)){
	    	  menuurl.add(newurl);
	      }
		puser.setMenuurls(menuurl);
		session.setAttribute("user", puser);
		return "redirect:/index.do";
	}
	return "/login";
}

/**
 * @return
 * @throws Exception
 */
@RequestMapping("index")
public String index(Model model) throws Exception {
	List<ResFormMap> mps = resourcesMapper.findByWhere(new ResFormMap());
	List<TreeObject> list = new ArrayList<TreeObject>();
	for (ResFormMap map : mps) {
		TreeObject ts = new TreeObject();
		Common.flushObject(ts, map);
		list.add(ts);
	}
	TreeUtil treeUtil = new TreeUtil();
	List<TreeObject> ns = treeUtil.getChildTreeObjects(list, 0);
	model.addAttribute("list", ns);
	return "/index";
}

@RequestMapping("password")
public String password(Model model){
	System.out.println("UserController.password()");
	return "/WEB-INF/jsp/user/password";
}

@RequestMapping("modifypassword")
public String modifypassword(Model model,HttpServletRequest request){
	String userid = request.getParameter("userid");
	String oldpassword = request.getParameter("oldpassword");
	String newpassword = request.getParameter("newpassword");
	User user = new User();
	user.setUserId(userid);
	user.setUserPassword(oldpassword);
	User puser = usermapper.querySingleUser(user);
	if(null==puser){
		model.addAttribute("error", "旧密码错误");
		return "/WEB-INF/jsp/user/password";
	}else{
		puser.setUserPassword(newpassword);
		int ucount = usermapper.updatepassword(puser);
		if(ucount>0){
			return "redirect:/";
		}
	}
	return "redirect:/";
}

@RequestMapping("logout")
public String logout(HttpServletRequest request){
	HttpSession session = request.getSession();
	session.removeAttribute("user");
	return "redirect:/";
}

public ResourcesMapper getResourcesMapper() {
	return resourcesMapper;
}

public void setResourcesMapper(ResourcesMapper resourcesMapper) {
	this.resourcesMapper = resourcesMapper;
}

public UserMapper getUsermapper() {
	return usermapper;
}


public void setUsermapper(UserMapper usermapper) {
	this.usermapper = usermapper;
}

}
