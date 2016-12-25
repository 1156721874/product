package com.product.entity;

/**
 * 货物信息表
 * @author wzq
 *
 */
public class Cargoinfo {

private int id=0;
private String cargoname="";//货物名称
private String org=""; //单位
private String irradtype="";//辐照方式
private String irradtime;//辐照时间
private String timeorg="";//时间单位
public Cargoinfo(){}
public Cargoinfo(String cargoname, String org, String irradtype,
		String irradtime, String timeorg) {
	super();
	this.cargoname = cargoname;
	this.org = org;
	this.irradtype = irradtype;
	this.irradtime = irradtime;
	this.timeorg = timeorg;
}
public String getIrradtime() {
	return irradtime;
}
public void setIrradtime(String irradtime) {
	this.irradtime = irradtime;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCargoname() {
	return cargoname;
}
public void setCargoname(String cargoname) {
	this.cargoname = cargoname;
}
public String getOrg() {
	return org;
}
public void setOrg(String org) {
	this.org = org;
}
public String getIrradtype() {
	return irradtype;
}
public void setIrradtype(String irradtype) {
	this.irradtype = irradtype;
}
public String getTimeorg() {
	return timeorg;
}
public void setTimeorg(String timeorg) {
	this.timeorg = timeorg;
}
@Override
public String toString() {
	return "Cargoinfo [id=" + id + ", cargoname=" + cargoname + ", org=" + org
			+ ", irradtype=" + irradtype + ", irradtime=" + irradtime
			+ ", timeorg=" + timeorg + "]";
}
}
