package com.product.entity;

public class BringTakeInfo {
private String id;
private String name;
private String orgname;
private String tel;
private String mask;
public BringTakeInfo(){}
public BringTakeInfo(String name,String orgname,String tel,String mask){
	super();
	this.name=name;
	this.orgname=orgname;
	this.tel=tel;
	this.mask=mask;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getOrgname() {
	return orgname;
}
public void setOrgname(String orgname) {
	this.orgname = orgname;
}
public String getTel() {
	return tel;
}
public void setTel(String tel) {
	this.tel = tel;
}
public String getMask() {
	return mask;
}
public void setMask(String mask) {
	this.mask = mask;
}

}
