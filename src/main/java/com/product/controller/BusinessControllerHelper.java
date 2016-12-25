package com.product.controller;

import javax.servlet.http.HttpServletRequest;

import com.product.entity.RadiationInfo;
import com.product.entity.ReceiveInfo;
import com.product.entity.TakeCargoInfo;
import com.product.entity.User;
import com.product.util.Common;

public class BusinessControllerHelper {
	
	public static ReceiveInfo getReceiveInfoObject(HttpServletRequest request) {
		String receivetime = request.getParameter("receivetime");
		String showorgs = request.getParameter("showorgs");
		String showBringTakeInfos = request.getParameter("showBringTakeInfos");
		String telnum = request.getParameter("telnum");
		String cargoname = request.getParameter("cargoname");
		String cargocount = request.getParameter("cargocount");
		String showcountorginfos = request.getParameter("showcountorginfos");
		String reqreagent = request.getParameter("reqreagent");
		String cargoweight = request.getParameter("cargoweight");
		String funguscount = request.getParameter("funguscount");
		String irradtypes = request.getParameter("irradtypes");
		String irradtime = request.getParameter("irradtime");
		String timeorgs = request.getParameter("timeorgs");
		String irradflags = request.getParameter("irradflags");
		String asCurrentRecord = request.getParameter("asCurrentRecord");
		String userid =  ((User)request.getSession().getAttribute("user")).getUserId();
		
		ReceiveInfo receiveinfo = new ReceiveInfo();
		receiveinfo.setReceivetime(receivetime);
		receiveinfo.setReceiveorgid(showorgs);
		receiveinfo.setReceivepeopleid(showBringTakeInfos);
		receiveinfo.setTelnum(telnum);
		receiveinfo.setCargoid(cargoname);
		receiveinfo.setCargocount(cargocount);
		receiveinfo.setCountorg(showcountorginfos);
		receiveinfo.setReqreagent(reqreagent);
		receiveinfo.setCargoweight(cargoweight);
		receiveinfo.setFunguscount(funguscount);
		receiveinfo.setIrradtype(irradtypes);
		receiveinfo.setIrradtime(irradtime);
		receiveinfo.setIrradtimeorg(timeorgs);
		receiveinfo.setIrradflag(irradflags);
		receiveinfo.setAsCurrentRecord(asCurrentRecord);
		receiveinfo.setUserid(userid);
		return receiveinfo;
	}
	
	public static ReceiveInfo processSaveEditConfirDetailInfo(HttpServletRequest request){
		ReceiveInfo receiveinfo = new ReceiveInfo();
		String idAndreceiveorgid = request.getParameter("idAndreceiveorgid");
		receiveinfo.setCargoid(Common.stringDefaultOfEmpty(request.getParameter("cargoname"),"") );
		receiveinfo.setCargocount(Common.stringDefaultOfEmpty(request.getParameter("cargocount"),"") );
		receiveinfo.setCargoweight(Common.stringDefaultOfEmpty(request.getParameter("cargoweight"),""));
		receiveinfo.setCountorg(Common.stringDefaultOfEmpty(request.getParameter("showcountorginfos"),""));
		receiveinfo.setFunguscount(Common.stringDefaultOfEmpty(request.getParameter("funguscount"),""));
		receiveinfo.setReqreagent(Common.stringDefaultOfEmpty(request.getParameter("reqreagent"),""));
		receiveinfo.setIrradtype(Common.stringDefaultOfEmpty(request.getParameter("irradtypes"),""));
		receiveinfo.setIrradtime(Common.stringDefaultOfEmpty(request.getParameter("irradtime"),""));
		receiveinfo.setIrradtimeorg(Common.stringDefaultOfEmpty(request.getParameter("timeorgs"),""));
		receiveinfo.setIrradflag(Common.stringDefaultOfEmpty(request.getParameter("irradflags"),""));
		receiveinfo.setFee(Double.valueOf(Common.stringDefaultOfEmpty(request.getParameter("fee"),"0")) );
		receiveinfo.setMask(Common.stringDefaultOfEmpty(request.getParameter("mask"),""));
		receiveinfo.setId(idAndreceiveorgid.split("@_@")[0]);
		receiveinfo.setReceivemgrid(idAndreceiveorgid.split("@_@")[1]);
		return receiveinfo;
	}
	
	public static RadiationInfo precessSaveRadiation(HttpServletRequest request){
		RadiationInfo radiationinfo = new  RadiationInfo();
		radiationinfo.setDetailid(Common.stringDefaultOfEmpty(request.getParameter("id").split("-")[0],""));
		radiationinfo.setBaseid(Common.stringDefaultOfEmpty(request.getParameter("id").split("-")[1],""));
		radiationinfo.setReceiveorgid(Common.stringDefaultOfEmpty(request.getParameter("receiveorgid"),""));
		radiationinfo.setCargoname(Common.stringDefaultOfEmpty(request.getParameter("divcargoname"),""));//辐照货物
		radiationinfo.setIrradednum(Common.stringDefaultOfEmpty(request.getParameter("irradednum"),""));
		radiationinfo.setCountorg(Common.stringDefaultOfEmpty(request.getParameter("showcountorginfos"),""));
		radiationinfo.setCargoweight(Common.stringDefaultOfEmpty(request.getParameter("cargoweight"),""));
		radiationinfo.setIrradtype(Common.stringDefaultOfEmpty(request.getParameter("irradtypes"),""));
		radiationinfo.setIrradtime(Common.stringDefaultOfEmpty(request.getParameter("irradtime"),""));
		radiationinfo.setTimeorg(Common.stringDefaultOfEmpty(request.getParameter("timeorgs"),""));
		radiationinfo.setPosition(Common.stringDefaultOfEmpty(request.getParameter("position"),""));
		return radiationinfo;
	}

	public static TakeCargoInfo precessSaveTakeCargo(HttpServletRequest request) {
		TakeCargoInfo takecargoinfo = new TakeCargoInfo();
		takecargoinfo.setTaketime(Common.stringDefaultOfEmpty(request.getParameter("taketime"),""));
		takecargoinfo.setTakecargoorg(Common.stringDefaultOfEmpty(request.getParameter("showorgs"),""));
		takecargoinfo.setProxyorg(Common.stringDefaultOfEmpty(request.getParameter("proxyOrg"),""));
		takecargoinfo.setTakecargopeople(Common.stringDefaultOfEmpty(request.getParameter("showBringTakeInfos"),""));
		takecargoinfo.setTelnum(Common.stringDefaultOfEmpty(request.getParameter("telnum"),""));
		takecargoinfo.setShippers(Common.stringDefaultOfEmpty(request.getParameter("shippers"),""));
		
		String data = request.getParameter("data");
		String [] paramters = data.split("-");
		takecargoinfo.setIrradaedcargoid(paramters[0]);
		takecargoinfo.setCargoname(paramters[2]);
		takecargoinfo.setCargocount(Common.stringDefaultOfEmpty(request.getParameter("takecargocount"),""));
		takecargoinfo.setCountorg(paramters[5]);
		takecargoinfo.setWeight(paramters[6]);
		return takecargoinfo;
	}
	
}
