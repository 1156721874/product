package com.product.entity;

public class RadiationInfo {
	private String id ="";
	private String detailid ="";// 货物详情的主键
	private String baseid ="";  // 货物基本信息表的主键
	private String receiveorgid="";//送货单位
	private String receiveorgname="";//送货单位名称
	private String cargoname =""; // 货物名称
	private String irradednum ="";// 辐照数量
	private String countorg ="";  //数量单位
	private String cargoweight =""; //货物重量
	private String irradtype  = ""; //辐照类型
	private String irradtime =""; //辐照时间
	private String  timeorg=""; // 时间单位'
	private String  position =""; // 位置
	private String  doirraddate =""; // 辐照日期
	private String  starttime =""; // 开始时间
	private String takecargocount="";//已取数量，出货时的取出数量，辐照添加时默认为0
	private String  mask =""; //备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDetailid() {
		return detailid;
	}
	public void setDetailid(String detailid) {
		this.detailid = detailid;
	}
	public String getBaseid() {
		return baseid;
	}
	public void setBaseid(String baseid) {
		this.baseid = baseid;
	}
	public String getReceiveorgid() {
		return receiveorgid;
	}
	public void setReceiveorgid(String receiveorgid) {
		this.receiveorgid = receiveorgid;
	}
	public String getReceiveorgname() {
		return receiveorgname;
	}
	public void setReceiveorgname(String receiveorgname) {
		this.receiveorgname = receiveorgname;
	}
	public String getCargoname() {
		return cargoname;
	}
	public void setCargoname(String cargoname) {
		this.cargoname = cargoname;
	}
	public String getIrradednum() {
		return irradednum;
	}
	public void setIrradednum(String irradednum) {
		this.irradednum = irradednum;
	}
	public String getCountorg() {
		return countorg;
	}
	public void setCountorg(String countorg) {
		this.countorg = countorg;
	}
	public String getCargoweight() {
		return cargoweight;
	}
	public void setCargoweight(String cargoweight) {
		this.cargoweight = cargoweight;
	}
	public String getIrradtype() {
		return irradtype;
	}
	public void setIrradtype(String irradtype) {
		this.irradtype = irradtype;
	}
	public String getIrradtime() {
		return irradtime;
	}
	public void setIrradtime(String irradtime) {
		this.irradtime = irradtime;
	}
	public String getTimeorg() {
		return timeorg;
	}
	public void setTimeorg(String timeorg) {
		this.timeorg = timeorg;
	}
 	public String getTakecargocount() {
		return takecargocount;
	}
	public void setTakecargocount(String takecargocount) {
		this.takecargocount = takecargocount;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getDoirraddate() {
		return doirraddate;
	}
	public void setDoirraddate(String doirraddate) {
		this.doirraddate = doirraddate;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
