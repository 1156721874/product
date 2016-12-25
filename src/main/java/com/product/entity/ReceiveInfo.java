package com.product.entity;
/**
 * 收获管理
 * @author wzq
 *
 */
public class ReceiveInfo  extends BaseEntity{
	private String id;             //receivemgrbase
	private String receivetime;    //receivemgrbase  收货日期
	private String receiveorgid; //receivemgrbase  送货单位 shippinginfo：id
	private String receiveorgname;//receivemgrbase  送货单位 shippinginfo：orgname
	private String receivepeopleid;  //receivemgrbase  送货人 bringtakeinfo：id
	private String receivepeoplename; //receivemgrbase  送货人 bringtakeinfo：name
	private String telnum;         //receivemgrbase  联系电话
	private String receivemgrid;   //receivemgrdetail 收获管理基本信息id ReceiveMgrBase：id
	private String cargoid;        //货物名称 cargoinfo：id
	private String cargoname;      //货物名称 cargoinfo：cargoname 
	private String cargocount;     //货物数量
	private String irradednum;     //辐照数量
	private String countorg;       //数量单位 orginfo：orgname
	private String cargoweight;    //货物重量(吨)
	private String funguscount;    //含菌数
	private String reqreagent;     //要求剂量(kgy)
	private String irradtype;      //辐照方式 cargoinfo：irradtype
	private String irradtime;      //辐照时间
	private String irradtimeorg;   //辐照时间单位
	private String irradflag;      //辐照类型：首次辐照、重新辐照、不辐照
	private String asCurrentRecord;//是否依照当前记录
	private double fee=0;             //收取的费用
	private String status;         //状态，是否指纹确认 0:未指纹确认，1:已指纹确认
	private String mask;           //说明
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAsCurrentRecord() {
		return asCurrentRecord;
	}
	public void setAsCurrentRecord(String asCurrentRecord) {
		this.asCurrentRecord = asCurrentRecord;
	}
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
	public String getTelnum() {
		return telnum;
	}
	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}
	public String getReceivemgrid() {
		return receivemgrid;
	}
	public void setReceivemgrid(String receivemgrid) {
		this.receivemgrid = receivemgrid;
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
	public String getCargoweight() {
		return cargoweight;
	}
	public void setCargoweight(String cargoweight) {
		this.cargoweight = cargoweight;
	}
	public String getFunguscount() {
		return funguscount;
	}
	public void setFunguscount(String funguscount) {
		this.funguscount = funguscount;
	}
	public String getReqreagent() {
		return reqreagent;
	}
	public void setReqreagent(String reqreagent) {
		this.reqreagent = reqreagent;
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
	public String getIrradtimeorg() {
		return irradtimeorg;
	}
	public void setIrradtimeorg(String irradtimeorg) {
		this.irradtimeorg = irradtimeorg;
	}
	public String getIrradflag() {
		return irradflag;
	}
	public void setIrradflag(String irradflag) {
		this.irradflag = irradflag;
	}
	public String getCargoid() {
		return cargoid;
	}
	public void setCargoid(String cargoid) {
		this.cargoid = cargoid;
	}
	public String getReceiveorgid() {
		return receiveorgid;
	}
	public void setReceiveorgid(String receiveorgid) {
		this.receiveorgid = receiveorgid;
	}
	public String getReceivepeopleid() {
		return receivepeopleid;
	}
	public void setReceivepeopleid(String receivepeopleid) {
		this.receivepeopleid = receivepeopleid;
	}
	public String getReceiveorgname() {
		return receiveorgname;
	}
	public void setReceiveorgname(String receiveorgname) {
		this.receiveorgname = receiveorgname;
	}
	public String getReceivepeoplename() {
		return receivepeoplename;
	}
	public void setReceivepeoplename(String receivepeoplename) {
		this.receivepeoplename = receivepeoplename;
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
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
}
