package com.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product.bean.common.DictItem;
import com.product.entity.User;
import com.product.mapper.PrivilegeMapper;
import com.product.mapper.UserMapper;
import com.product.util.LogUtil;

/**                                                           》》》注意《《《
 * =================================================================================================================================
 * =                                                                                                                             	=
 * =权限管理模块其实可以简单处理，即一张表搞定，但是为了熟练当今流行的控权模型RBAC使用了此模型。                                                                                                                                                                                                                                 	=
 * =详细信息：http://baike.baidu.com/link?url=RZXQUudaw2cQ096bbzZGd3QPNmPwoAAS63lnt-iaci4tZq0NKLYTtU8-1sPxEii1DgUicp71Ziym1pU3EckRoq    =
 * =                                                                                                                                =
 * ==============================================================================================================================   =
 */

@Service(value="PrivilegeManaImpl")
public class PrivilegeManaImpl implements IPrivilegeMana {

	
	@Resource
	private PrivilegeMapper privilegemapper;
	
	@Resource
	private UserMapper usermapper;
	
	public List<User> getAllUserList() {
		return privilegemapper.getAllUserList();
	}

	public User queryUserALlprivilege(String userid) {
		return privilegemapper.queryUserALlprivilege(userid);
	}
	
	@Transactional(rollbackFor=java.lang.Exception.class)
	public String addUser(User user) {
		String result = "";
		try {
			Integer ispeat = privilegemapper.isRepeatUser(user);
			if (null != ispeat && !"null".equals(ispeat) && ispeat == 0) {
				//增加用户
				int addcount = privilegemapper.addUser(user);
				if (addcount > 0) {
					result = "ok";
				}
			} else {
				result = "登陆账号已被注册！";
			}
		} catch (Exception e) {
			result = e.getMessage(); 
			LogUtil.getLog().error("PrivilegeManaImpl.addUser:\n"+result);
			throw new RuntimeException(result);
		}
		return result;
	}
	
	@Transactional(rollbackFor=java.lang.Exception.class)
	public String saveEditUser(User user) {
		String result = "";
		try {
			Integer ispeat = privilegemapper.isRepeatUser(user);
			if(ispeat>0){
				result = "登陆账号已被注册！";
			}
			int ucount = privilegemapper.saveEditUser(user);
			if (ucount > 0) {
				result = "ok";
			} else {
				result = "更新失败！";
			}
		} catch (Exception e) {
			result = e.getMessage(); 
			LogUtil.getLog().error("PrivilegeManaImpl.saveEditUser:\n"+result);
			throw new RuntimeException(result);
		}
		return result;
	}
	
	@Transactional(rollbackFor=java.lang.Exception.class)
	public String deleteUser(String ids) {
		String result = "";
		try {
			Map param = new HashMap();
			param.put("ids", ids);
			int r = privilegemapper.deleteUser(param);
			if (r > 0) {
				result = "ok";
			}else{
				result="删除失败！";
			}
		} catch (Exception e) {
			result = e.getMessage(); 
			LogUtil.getLog().error("PrivilegeManaImpl.saveEditUser:\n"+result);
			throw new RuntimeException(result);
		}
		return result;
	}
	
	public List<DictItem> getRoles() {
		return privilegemapper.getRoles();
	}
	
	public PrivilegeMapper getPrivilegemapper() {
		return privilegemapper;
	}

	public void setPrivilegemapper(PrivilegeMapper privilegemapper) {
		this.privilegemapper = privilegemapper;
	}

	public UserMapper getUsermapper() {
		return usermapper;
	}

	public void setUsermapper(UserMapper usermapper) {
		this.usermapper = usermapper;
	}

}
