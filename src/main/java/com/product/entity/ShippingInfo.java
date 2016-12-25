package com.product.entity;

/**
 * 送货信息
 * @author wzq
 *
 */
public class ShippingInfo {
private String id;
private String orgname;
private String mask;
public ShippingInfo(){}
public ShippingInfo(String orgname,String mask){
	super();
	this.orgname=orgname;
	this.mask=mask;
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
