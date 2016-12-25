package com.product.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.entity.ChargeInfo;
import com.product.entity.User;
import com.product.mapper.CommonMapper;
import com.product.service.IChargeMana;
import com.product.service.IReceivingMana;
import com.product.util.Common;

@Controller
@RequestMapping("/collection/")
public class ChargeController {

	@Resource(name="ReceivingManaImpl")
	private IReceivingMana ireceivingmana;
	
	@Resource(name="ChargeManaImpl")
	private IChargeMana ichargemana;
	
	@Resource
	private CommonMapper commonmapper;
	
	@RequestMapping("init")
	public String init(Model model){
		model.addAttribute("showorgs", ireceivingmana.getAllOrgs());//送货单位
		model.addAttribute("paytype", commonmapper.getDictItemByGroupId("paytype"));
		return Common.BACKGROUND_PATH + "/charge/chargeManager"; 
	}


	@ResponseBody
	@RequestMapping("getallunpaid")
	public List<ChargeInfo> getAllUnpaid(HttpServletRequest request){
		String inputorgname = Common.stringDefaultOfEmpty(request.getParameter("inputorg"), "") ;//输入单位名称
		String showorgs = Common.stringDefaultOfEmpty(request.getParameter("showorgs"), "") ;//下拉选择单位
		String asinputorg = Common.stringDefaultOfEmpty(request.getParameter("asinputorg"), "") ;//是否根据输入单位查找
		String isunpaidcomplete = Common.stringDefaultOfEmpty(request.getParameter("isunpaidcomplete"), "") ;//是否只查询未付完费用的单位
		Map param = new HashMap();
		param.put("inputorgname", inputorgname);
		param.put("showorgs", showorgs);
		param.put("asinputorg", asinputorg);
		param.put("isunpaidcomplete", isunpaidcomplete);
		return ichargemana.getAllUnpaid(param);
	}
	
	@ResponseBody
	@RequestMapping("docharge")
	public String doCharge(HttpServletRequest request){
		String id= Common.stringDefaultOfEmpty(request.getParameter("id"),"");
		String thePayment= Common.stringDefaultOfEmpty(request.getParameter("thePayment"),"");
		/**
		 * 交款单位   交款日期   交款金额   备注   收获日期   货物名称    货物数量    重量(吨) 费用(元) 已付(元) 未付(元) 
		 */
		User user = (User)request.getSession().getAttribute("user");
		String param = Common.stringDefaultOfEmpty(request.getParameter("param"),"");
		String [] paramarr = param.split("@_@");
		String chargeid = Common.stringDefaultOfEmpty(paramarr[0],"");
		String chargeorg = Common.stringDefaultOfEmpty(paramarr[4],"");
		String chargeamount = Common.stringDefaultOfEmpty(request.getParameter("thePayment"),"");
		String receivetime = Common.stringDefaultOfEmpty(paramarr[5],"");
		String cargoname = Common.stringDefaultOfEmpty(paramarr[6],"");
		String cargocount = Common.stringDefaultOfEmpty(paramarr[7],"");
		String cargoweight = Common.stringDefaultOfEmpty(paramarr[8],"");
		String fee = Common.stringDefaultOfEmpty(paramarr[1],"");
		String chargeIntroduction = Common.stringDefaultOfEmpty(request.getParameter("chargeIntroduction"),"");
		String paytype = Common.stringDefaultOfEmpty(request.getParameter("paytype"),"");
		ChargeInfo chargeinfo = new ChargeInfo();
		chargeinfo.setChargeid(chargeid);
		chargeinfo.setOrganizationname(chargeorg);
		chargeinfo.setCurrentapid(chargeamount);
		chargeinfo.setReceivetime(receivetime);
		chargeinfo.setCargoname(cargoname);
		chargeinfo.setCargocount(cargocount);
		chargeinfo.setCargoweight(cargoweight);
		chargeinfo.setFee(fee);
		chargeinfo.setMask(chargeIntroduction);
		chargeinfo.setOperater(user.getUserName());
		chargeinfo.setPaytype(paytype);
		return ichargemana.doCharge(chargeinfo);
	}
	
	@ResponseBody
	@RequestMapping("querychargelog")
	public List<ChargeInfo> queryChargeLog(HttpServletRequest request){
		String organizationid  = Common.stringDefaultOfEmpty(request.getParameter("organizationid"),"");
		String asinputorg  = Common.stringDefaultOfEmpty(request.getParameter("asinputorg"),"");
		String inputorg  = Common.stringDefaultOfEmpty(request.getParameter("inputorg"),"");
		Map param = new HashMap();
		param.put("organizationid", organizationid);
		param.put("asinputorg", asinputorg);
		param.put("inputorg", inputorg);
		return ichargemana.queryChargeLog(param);
	}
	
	@RequestMapping("doprint")
	public String doprint(HttpServletRequest request) throws UnsupportedEncodingException{
		List<ChargeInfo> allunpaid =  getAllUnpaid(request);
		request.setAttribute("allunpaid", allunpaid);
		return Common.BACKGROUND_PATH + "/charge/printchargeManager"; 
	}
	
	public IReceivingMana getIreceivingmana() {
		return ireceivingmana;
	}

	public void setIreceivingmana(IReceivingMana ireceivingmana) {
		this.ireceivingmana = ireceivingmana;
	}

	public IChargeMana getIchargemana() {
		return ichargemana;
	}

	public void setIchargemana(IChargeMana ichargemana) {
		this.ichargemana = ichargemana;
	}


	public CommonMapper getCommonmapper() {
		return commonmapper;
	}


	public void setCommonmapper(CommonMapper commonmapper) {
		this.commonmapper = commonmapper;
	}
	
}
