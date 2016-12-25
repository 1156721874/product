package com.product.entity;

public class ChargeInfo {
private String id="";
private String chargeid="";        //对receivemgrdetail表的那条记录收的款
private String chargetime="";        //对receivemgrdetail表的那条记录收的款
private String receivetime="";     //收获日期
private String paytime="";         //交款日期     
private String organizationname="";//交款单位名称
private String cargoname="";       //货物名称
private String takepeople="";      //送货人
private String cargocount="";      //货物数量
private String irradednum="";      //已照数量
private String takedccargocount="";//已取数量
private String countorgname="";    //数量单位
private String cargoweight="";     //货物重量
private String currentapid="";     //本次交款金额
private String fee="";             //货物辐照费用
private String paid="";            //已付
private String unpaid="";           //未付
private String mask="";            //费用说明
private String operater="";        //操作员
private String paytype="";         //付款方式
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getReceivetime() {
	return receivetime;
}
public void setReceivetime(String receivetime) {
	this.receivetime = receivetime;
}
public String getPaytime() {
	return paytime;
}
public void setPaytime(String paytime) {
	this.paytime = paytime;
}
public String getOrganizationname() {
	return organizationname;
}
public void setOrganizationname(String organizationname) {
	this.organizationname = organizationname;
}
public String getCargoname() {
	return cargoname;
}
public void setCargoname(String cargoname) {
	this.cargoname = cargoname;
}
public String getTakepeople() {
	return takepeople;
}
public void setTakepeople(String takepeople) {
	this.takepeople = takepeople;
}
public String getCargocount() {
	return cargocount;
}
public void setCargocount(String cargocount) {
	this.cargocount = cargocount;
}
public String getIrradednum() {
	return irradednum;
}
public void setIrradednum(String irradednum) {
	this.irradednum = irradednum;
}
public String getTakedccargocount() {
	return takedccargocount;
}
public void setTakedccargocount(String takedccargocount) {
	this.takedccargocount = takedccargocount;
}
public String getCountorgname() {
	return countorgname;
}
public void setCountorgname(String countorgname) {
	this.countorgname = countorgname;
}
public String getCargoweight() {
	return cargoweight;
}
public void setCargoweight(String cargoweight) {
	this.cargoweight = cargoweight;
}
public String getCurrentapid() {
	return currentapid;
}
public void setCurrentapid(String currentapid) {
	this.currentapid = currentapid;
}
public String getFee() {
	return fee;
}
public void setFee(String fee) {
	this.fee = fee;
}
public String getPaid() {
	return paid;
}
public void setPaid(String paid) {
	this.paid = paid;
}
public String getMask() {
	return mask;
}
public void setMask(String mask) {
	this.mask = mask;
}
public String getUnpaid() {
	return unpaid;
}
public void setUnpaid(String unpaid) {
	this.unpaid = unpaid;
}
public String getChargeid() {
	return chargeid;
}
public void setChargeid(String chargeid) {
	this.chargeid = chargeid;
}
public String getChargetime() {
	return chargetime;
}
public void setChargetime(String chargetime) {
	this.chargetime = chargetime;
}
public String getOperater() {
	return operater;
}
public void setOperater(String operater) {
	this.operater = operater;
}
public String getPaytype() {
	return paytype;
}
public void setPaytype(String paytype) {
	this.paytype = paytype;
}
}
