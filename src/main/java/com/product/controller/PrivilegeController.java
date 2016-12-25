package com.product.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.bean.common.DictItem;
import com.product.entity.User;
import com.product.mapper.CommonMapper;
import com.product.service.IPrivilegeMana;
import com.product.util.Common;


@Controller
@RequestMapping("/privilegemana/")
public class PrivilegeController {

	@Resource(name="PrivilegeManaImpl")
	private IPrivilegeMana privilegemanaimpl;
	
	@Resource
	private CommonMapper commonmapper;
	
	/*
	 * 初始化操作
	 */
	@RequestMapping("configuration")
	public String configurationinit(Model model){
		List<DictItem> allmenu =  	commonmapper.getDictItemByGroupId("allmenu");
		//按照菜单id升序拍戏，PS：页面的排放是升序的
		Collections.sort(allmenu, new Comparator<DictItem>() {
			public int compare(DictItem o1, DictItem o2) {
				Integer o1id = Integer.valueOf(o1.getDictid());
				Integer o2id = Integer.valueOf(o2.getDictid());
				return o1id-o2id;
			}
		});
		
		StringBuffer sb = new StringBuffer("");
		for(int i=0;i<allmenu.size();i++){
			if(i==0){
				sb.append(allmenu.get(i).getDictid());
			}else{
				sb.append(",").append(allmenu.get(i).getDictid());
			}
		}
		
		model.addAttribute("allmenues", sb);
		model.addAttribute("menuesitem", allmenu);
		model.addAttribute("roles", privilegemanaimpl.getRoles());
		return Common.BACKGROUND_PATH + "/privilegemana/userRoleList";
	}
	
	/**
	 * 得到所有用户的列表
	 * @return
	 */
	@ResponseBody
    @RequestMapping("configprivilege/querygprivileges")	
	public List<User> querygprivileges(){
		List<User> users = privilegemanaimpl.getAllUserList();
		return users;
	}

	/**
	 * 编辑操作得到某个用户的信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("configprivilege/getuserInfo")
	public User getUserInfo(HttpServletRequest request){
		String userid = request.getParameter("userid");
		return privilegemanaimpl.queryUserALlprivilege(userid);
	}

	/**
	 * 执行添加用户操作
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("configprivilege/adduser")
	public String addUser(HttpServletRequest request){
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String roleid = request.getParameter("role");
		String [] menuesitemes = request.getParameterValues("menuesitemes");
		User user = new User(userid, username, roleid, Arrays.toString(menuesitemes).replace("[", "").replace("]", "").replace(" ", ""));
		String trsult = privilegemanaimpl.addUser(user);
		return trsult;
	}
	
	@ResponseBody
	@RequestMapping("configprivilege/saveedituser")
	public String saveEditUser(HttpServletRequest request){
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String roleid = request.getParameter("role");
		String [] menuesitemes = request.getParameterValues("menuesitemes");
		User user = new User(userid, username, roleid, Arrays.toString(menuesitemes).replace("[", "").replace("]", "").replace(" ", ""));
		String trsult = privilegemanaimpl.saveEditUser(user);
		return trsult;
	}
	
	@ResponseBody
	@RequestMapping("configprivilege/deleteuser")
	public String deleteUser(HttpServletRequest request){
		String ids = request.getParameter("ids");
		String [] idsarr = ids.split("@_@");
		StringBuffer sb  = new StringBuffer("");
		for(int i=0;i<idsarr.length;i++){
			if(i==0){
				sb.append("'").append(idsarr[i]).append("'");
			}else{
				sb.append(",").append("'").append(idsarr[i]).append("'");
			}
		}
		String dresult = privilegemanaimpl.deleteUser(sb.toString());
		return dresult;
	}
	
	public CommonMapper getCommonmapper() {
		return commonmapper;
	}

	public void setCommonmapper(CommonMapper commonmapper) {
		this.commonmapper = commonmapper;
	}

	public IPrivilegeMana getPrivilegemanaimpl() {
		return privilegemanaimpl;
	}

	public void setPrivilegemanaimpl(IPrivilegeMana privilegemanaimpl) {
		this.privilegemanaimpl = privilegemanaimpl;
	}
	
}
