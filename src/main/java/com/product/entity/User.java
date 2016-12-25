package com.product.entity;

import java.util.HashSet;
import java.util.Set;

/*
 * 登陆用户
 */
public class User {
public  User(){}
public User(String userId, String userName, String roleid, String menuids) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.roleid = roleid;
		this.menuids = menuids;
	}
//登陆账户
private String userId="";
private String userName="";
private String roleid="";
private String rolename="";
private String menuids="";
private Set menuurls = new HashSet();
//登陆密码
private String userPassword="";
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getRoleid() {
	return roleid;
}
public void setRoleid(String roleid) {
	this.roleid = roleid;
}
public String getRolename() {
	return rolename;
}
public void setRolename(String rolename) {
	this.rolename = rolename;
}
public String getMenuids() {
	return menuids;
}
public void setMenuids(String menuids) {
	this.menuids = menuids;
}
public String getUserPassword() {
	return userPassword;
}
public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}
public Set getMenuurls() {
	return menuurls;
}
public void setMenuurls(Set menuurls) {
	this.menuurls = menuurls;
}


}
