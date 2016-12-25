package com.product.entity;

public class CountOrgInfo {
private String id;
private String orgname;
private String mask;
public CountOrgInfo(){}
public CountOrgInfo(String orgname , String mask ) {
	// TODO Auto-generated constructor stub
	this.mask=mask;
	this.orgname=orgname;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getOrgname() {
	return orgname;
}
public void setOrgname(String orgname) {
	this.orgname = orgname;
}
public String getMask() {
	return mask;
}
public void setMask(String mask) {
	this.mask = mask;
}

}
