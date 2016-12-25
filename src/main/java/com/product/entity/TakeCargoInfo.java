package com.product.entity;

public class TakeCargoInfo {
	private String  id ="";
	private String baseid ="";
	private String irradaedcargoid="";//已经辐照完毕的货物Sid
	private String taketime  ="";//出获日期
	private String takecargoorg  ="";//取货单位
	private String proxyorg  ="";//委托单位
	private String takecargopeople  ="";//取货人
	private String takecargopeopleid  ="";//取货人
	private String telnum  ="";//联系电话
	private String shippers  ="";//发货人
	private String cargoname ="";//货物名称
	private String cargocount ="";//货物数量
	private String countorg ="";//数量单位
	private String weight ="";//重量单位(吨)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaketime() {
		return taketime;
	}
	public void setTaketime(String taketime) {
		this.taketime = taketime;
	}
	public String getTakecargoorg() {
		return takecargoorg;
	}
	public void setTakecargoorg(String takecargoorg) {
		this.takecargoorg = takecargoorg;
	}
	public String getProxyorg() {
		return proxyorg;
	}
	public void setProxyorg(String proxyorg) {
		this.proxyorg = proxyorg;
	}
	public String getTakecargopeople() {
		return takecargopeople;
	}
	public void setTakecargopeople(String takecargopeople) {
		this.takecargopeople = takecargopeople;
	}
	public String getTelnum() {
		return telnum;
	}
	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}
	public String getShippers() {
		return shippers;
	}
	public void setShippers(String shippers) {
		this.shippers = shippers;
	}
	public String getBaseid() {
		return baseid;
	}
	public void setBaseid(String baseid) {
		this.baseid = baseid;
	}
	public String getCargoname() {
		return cargoname;
	}
	public void setCargoname(String cargoname) {
		this.cargoname = cargoname;
	}
	public String getCargocount() {
		return cargocount;
	}
	public void setCargocount(String cargocount) {
		this.cargocount = cargocount;
	}
	public String getCountorg() {
		return countorg;
	}
	public void setCountorg(String countorg) {
		this.countorg = countorg;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getIrradaedcargoid() {
		return irradaedcargoid;
	}
	public void setIrradaedcargoid(String irradaedcargoid) {
		this.irradaedcargoid = irradaedcargoid;
	}
	public String getTakecargopeopleid() {
		return takecargopeopleid;
	}
	public void setTakecargopeopleid(String takecargopeopleid) {
		this.takecargopeopleid = takecargopeopleid;
	}
}
