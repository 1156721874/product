package com.product.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.entity.DalidyCharge;
import com.product.entity.DalidyIrradation;
import com.product.entity.DalidyOutCargoes;
import com.product.entity.DalidyReceived;
import com.product.entity.ReceiveInfo;
import com.product.mapper.ReportMapper;
import com.product.util.Common;

@Controller
@RequestMapping("/report/")
public class ReportController {

	@Resource
    private ReportMapper reportmapper;
	
	@RequestMapping("dayreport")
	public String dayreport(Model model){
		return Common.BACKGROUND_PATH + "/report/dayreport";
	}
	
	@RequestMapping("goprintpage")
	public String printpage(Model model,HttpServletRequest request){
		model.addAttribute("date", Common.stringDefaultOfEmpty(request.getParameter("time"), "") );
		model.addAttribute("ismoth", Common.stringDefaultOfEmpty(request.getParameter("ismoth"), "") );
		model.addAttribute("countorg", Common.stringDefaultOfEmpty(request.getParameter("countorg"), "") );
		model.addAttribute("printtime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return Common.BACKGROUND_PATH + "/report/printpage";
	}
	
	@RequestMapping("goPrintChargePage")
	public String goPrintChargePage(Model model,HttpServletRequest request){
		model.addAttribute("date", Common.stringDefaultOfEmpty(request.getParameter("time"), "") );
		model.addAttribute("ismoth", Common.stringDefaultOfEmpty(request.getParameter("ismoth"), "") );
		model.addAttribute("countorg", Common.stringDefaultOfEmpty(request.getParameter("countorg"), "") );
		model.addAttribute("printtime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return Common.BACKGROUND_PATH + "/report/printChargePage";
	}
	
	@RequestMapping("goPrintOutCargoes")
	public String goPrintOutCargoes(Model model,HttpServletRequest request){
		model.addAttribute("date", Common.stringDefaultOfEmpty(request.getParameter("time"), "") );
		model.addAttribute("ismoth", Common.stringDefaultOfEmpty(request.getParameter("ismoth"), "") );
		model.addAttribute("countorg", Common.stringDefaultOfEmpty(request.getParameter("countorg"), "") );
		model.addAttribute("printtime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return Common.BACKGROUND_PATH + "/report/printOutCargoes";
	}
	
	@ResponseBody
	@RequestMapping("queryreportlist")
	public List<ReceiveInfo> queryreportlist(HttpServletRequest request){
		String ismoth = Common.stringDefaultOfEmpty(request.getParameter("ismoth"),"");
		Map param = new HashMap();
		param.put("date", request.getParameter("date"));
		param.put("countorg", Common.stringDefaultOfEmpty(request.getParameter("countorg"),""));
		if("1".equals(ismoth)){
			param.put("format", "%Y-%m");
		}else{
			param.put("format", "%Y-%m-%d");
		}
		return reportmapper.queryreportlist(param);
	}

	@RequestMapping("monthreport")
	public String monthreport(Model model){
		model.addAttribute("ismoth", "1");
		return Common.BACKGROUND_PATH + "/report/dayreport";
	}
	
	/**
	 * 刷新已收货物列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("refreshDalidyReceivedBody")
	public List<DalidyReceived> refreshDalidyReceivedBody(HttpServletRequest request){
		String ismoth = Common.stringDefaultOfEmpty(request.getParameter("ismoth"),"");
		System.out.println("refreshDalidyReceivedBody ismonth>>>>>>><<<<<<< "+ ismoth);
		Map param = new HashMap();
		param.put("date", Common.stringDefaultOfEmpty(request.getParameter("date"),""));
		param.put("countorg", Common.stringDefaultOfEmpty(request.getParameter("countorg"),""));
		if("1".equals(ismoth)){
			param.put("format", "%Y-%m");
		}else{
			param.put("format", "%Y-%m-%d");
		}
		return reportmapper.refreshDalidyReceivedBody(param);
	}
	
	/**
	 * 当日辐照列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("refreshDalidyIrradationBody")
	public List<DalidyIrradation> refreshDalidyIrradationBody(HttpServletRequest request){
		String ismoth = Common.stringDefaultOfEmpty(request.getParameter("ismoth"),"");
		System.out.println("refreshDalidyIrradationBody ismonth>>>>>>><<<<<<< "+ ismoth);
		Map param = new HashMap();
		param.put("date", Common.stringDefaultOfEmpty(request.getParameter("date"),""));
		param.put("countorg", Common.stringDefaultOfEmpty(request.getParameter("countorg"),""));
		if("1".equals(ismoth)){
			param.put("format", "%Y-%m");
		}else{
			param.put("format", "%Y-%m-%d");
		}
		return reportmapper.refreshDalidyIrradationBody(param);
	}
	
	/**
	 * 当日出货列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("refreshDalidyOutCargoesbody")
	public List<DalidyOutCargoes> refreshDalidyOutCargoesbody(HttpServletRequest request){
		String ismoth = Common.stringDefaultOfEmpty(request.getParameter("ismoth"),"");
		System.out.println("refreshDalidyOutCargoesbody ismonth>>>>>>><<<<<<< "+ ismoth);
		Map param = new HashMap();
		param.put("date", Common.stringDefaultOfEmpty(request.getParameter("date"),""));
		param.put("countorg", Common.stringDefaultOfEmpty(request.getParameter("countorg"),""));
		if("1".equals(ismoth)){
			param.put("format", "%Y-%m");
		}else{
			param.put("format", "%Y-%m-%d");
		}
		return reportmapper.refreshDalidyOutCargoesbody(param);
	}
	
	
	@ResponseBody
	@RequestMapping("refreshDalidyChargeBody")
	public List<DalidyCharge> refreshDalidyChargeBody(HttpServletRequest request){
		String ismoth = Common.stringDefaultOfEmpty(request.getParameter("ismoth"),"");
		System.out.println("refreshDalidyChargeBody ismonth>>>>>>><<<<<<< "+ ismoth);
		Map param = new HashMap();
		param.put("date", Common.stringDefaultOfEmpty(request.getParameter("date"),""));
		param.put("countorg", Common.stringDefaultOfEmpty(request.getParameter("countorg"),""));
		if("1".equals(ismoth)){
			param.put("format", "%Y-%m");
		}else{
			param.put("format", "%Y-%m-%d");
		}
		return reportmapper.refreshDalidyChargeBody(param);
	}
	
	public ReportMapper getReportmapper() {
		return reportmapper;
	}

	public void setReportmapper(ReportMapper reportmapper) {
		this.reportmapper = reportmapper;
	}
	
}
