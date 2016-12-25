package com.product.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.product.bean.common.DictItem;
import com.product.entity.RadiationInfo;
import com.product.entity.ReceiveInfo;
import com.product.entity.TakeCargoInfo;
import com.product.mapper.CommonMapper;
import com.product.service.IReceivingMana;
import com.product.util.Common;
import com.product.util.JSONList;
import com.product.util.LogUtil;

/**
 * @author wzq
 *
 */
@Controller
@RequestMapping("/business/")
public class BusinessController {
	
	@Resource(name="ReceivingManaImpl")
	private IReceivingMana ireceivingmana;
	@Resource
	private CommonMapper commonmapper;
	
    private static final HttpHeaders HTTP_HEADERS;  
    static {  
        HTTP_HEADERS = new HttpHeaders();  
        HTTP_HEADERS.set("Pragma", "No-cache");  
        HTTP_HEADERS.set("Cache-Control", "No-cache");  
        HTTP_HEADERS.setDate("Expires", 0);  
        HTTP_HEADERS.setContentType(MediaType.IMAGE_JPEG); 
    }  
	


	//====================================================================================收货管理
	/**
	 * 信息填写部分数据组织
	 * @param model
	 * @return
	 */
	@RequestMapping("receivingmana")
	public String receivingmana(Model model){

		model.addAttribute("showorgs", ireceivingmana.getAllOrgs());//送货单位
		model.addAttribute("showBringTakeInfos", ireceivingmana.getAllBringTakeInfoPeople());//送货人
		model.addAttribute("showcargoinfos", ireceivingmana.getCargoinfo());//货物名称
		model.addAttribute("showcountorginfos", ireceivingmana.getAllOrginfo());//计数单位
		
		model.addAttribute("irradtypes", commonmapper.getDictItemByGroupId("irradtypes"));
		model.addAttribute("timeorgs", commonmapper.getDictItemByGroupId("timeorgs"));
		model.addAttribute("irradflags", commonmapper.getDictItemByGroupId("irradflags"));
		return Common.BACKGROUND_PATH + "/businessmana/receivingmana/list";
	}
	
	/**
	 * 保存入库的货物，特性参数checkRepeatOfsaveReceive开启，则进行添加判重
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/saveReceiveCargo")
	public Map<String,String> saveReceiveCargo(HttpServletRequest request){
		Map<String,String> rtn = new HashMap<String,String>();
		try{
		ReceiveInfo receiveinfo = BusinessControllerHelper.getReceiveInfoObject(request);
		if("1".equals(commonmapper.getChart("checkRepeatOfsaveReceive"))){
			String  isreapeat = ireceivingmana.isreapeat(receiveinfo);
			if(Common.isNotEmpty(isreapeat)){
				rtn.put("result", "与以下数据存在重复：\n"+isreapeat);
				rtn.put("flag", "repeat");
				return rtn;
			}
		}
		ireceivingmana.saveReceiveCargo(receiveinfo);
		rtn.put("result", "insertsuccess!");
		rtn.put("flag", "insertsuccess");
		}catch (Exception e){
			e.printStackTrace();
			rtn.put("result", "inserterror :\n"+e.getMessage());
			rtn.put("flag", "inserterror");
			//throw new RuntimeException(e.getMessage());
		}
		return rtn;
	}
	
	/**
	 * 刚进入进货管理页面的已有货物列表展示部分的数据组织展示
	 */
	@ResponseBody
	@RequestMapping("receivingmana/queryReceivedCargos")
	public Map queryReceivedCargos(HttpServletRequest request){
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> returnValue = new HashMap<String,Object>();
		String pageSize = request.getParameter("pageSize");
		String pageNow = request.getParameter("pageNow");
		String receivetime = request.getParameter("receivetime1");
		String showorgs = request.getParameter("showorgs1");
		String showBringTakeInfos = request.getParameter("showBringTakeInfos1");
		String lastdate = request.getParameter("lastdate");
		params.put("pageNow",Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		params.put("receivetime", receivetime);
		params.put("receiveorgid", showorgs);
		params.put("receivepeopleid", showBringTakeInfos);
		params.put("lastdate", lastdate);
		returnValue.put("receivedCargos", ireceivingmana.getreceivedCargos(params));
		returnValue.put("pagerecode", getCount(request));
		returnValue.put("totalpage", Common.getPagetotalByPageSize((String) returnValue.get("pagerecode"), pageSize));
		returnValue.put("pageNow", Integer.valueOf(pageNow));
		//JSONMap map = JSONMap.parseJSONMap(JSONMap.toJSONString(returnValue) );
		//System.out.println(map);
		return  returnValue;
	}

	/**
	 * 得到记录条数
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/getCount")
	public String getCount(HttpServletRequest request) {
		String receivetime = request.getParameter("receivetime1");
		String showorgs = request.getParameter("showorgs1");
		String showBringTakeInfos = request.getParameter("showBringTakeInfos1");
		String lastdate = request.getParameter("lastdate");
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("table", " receivemgrbase a ");
		StringBuffer sb = new StringBuffer();
		sb.append(" and a.status='0' ");
		if (Common.isNotEmpty(receivetime)) {
			sb.append(" and a.receivetime= str_to_date('"+ receivetime +"','%Y-%m-%d')");
		}
		if (Common.isNotEmpty(showorgs)) {
			sb.append(" and a.receiveorgid= '"+ showorgs +"'");
		}
		if (Common.isNotEmpty(showBringTakeInfos)) {
			sb.append(" and a.receivepeopleid= '"+ showBringTakeInfos +"'");
		}//lastdate
		if(Common.isNotEmpty(lastdate)){
			sb.append(" or TO_DAYS(NOW()) - TO_DAYS(a.receivetime)<="+lastdate);
		}
		//params.put("where", " and  cargoname like '%" + cargoname + "%'");
		params.put("where", sb.toString());
		return commonmapper.getCount(params);
	}
	
	/**
	 * 根据货物基本信息表的id得到货物详细信息系
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/getcargodetailinfo")
	public List<ReceiveInfo> getCargoDetailInfo(HttpServletRequest request){
		String receiveorgid = request.getParameter("id");
		Map param = new HashMap();
		param.put("receiveorgid", receiveorgid);
		return ireceivingmana.getDetInfoByReceiveorgId(param);
	}
	
	@ResponseBody
	@RequestMapping("receivingmana/updateReceivedCargoStatus")
	public String updateReceivedCargoStatus(HttpServletRequest request){
		String id = request.getParameter("id");
		Map param = new HashMap();
		param.put("id", id);
		param.put("status", "1");
		return ireceivingmana.updateConfirmStatus(param);//借用收获确认的方法 与方法名字无关，只是使用其功能
	}
	
	/**
	 * 根据货物id得到货物详情和基本信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/getcargoinfoanddetailinfo")
	public Map getcargoinfoanddetailinfo(HttpServletRequest request){
		String receiveorgid = request.getParameter("receiveorgid");
		Map param = new HashMap();
		param.put("receiveorgid", receiveorgid);
		List<ReceiveInfo>  receivedetailinfoes = ireceivingmana.getDetInfoByReceiveorgId(param);//详情
		ReceiveInfo  receivebaseinfo= ireceivingmana.getCargoBaseInfoById(param);//基本信息
		Map returnValue = new HashMap();
		returnValue.put("baseinfo", receivebaseinfo);
		returnValue.put("detaillist", receivedetailinfoes);
		return returnValue;
	}
	
	
	//==============================================================================================收货确认
	
	/**
	 * 收货确认页面初始化
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("confirmation")
	public String confirmation(Model model){
		model.addAttribute("showcargoinfos", ireceivingmana.getCargoinfo());//货物名称
		model.addAttribute("showcountorginfos", ireceivingmana.getAllOrginfo());//计数单位
		model.addAttribute("irradtypes", commonmapper.getDictItemByGroupId("irradtypes"));
		model.addAttribute("timeorgs", commonmapper.getDictItemByGroupId("timeorgs"));
		model.addAttribute("irradflags", commonmapper.getDictItemByGroupId("irradflags"));
		model.addAttribute("showorgs", ireceivingmana.getAllOrgs());//送货单位
		model.addAttribute("showBringTakeInfos", ireceivingmana.getAllBringTakeInfoPeople());//送货人
		//model.addAttribute("picture", "images/zhifubao.png");
		return Common.BACKGROUND_PATH + "/businessmana/confirmationmana/list";
	}
	
	/**
	 * 依据条件查询带确认货物
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/queryconfirms")
	public Map queryconfirms(HttpServletRequest request){
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> returnValue = new HashMap<String,Object>();
		String pageSize = request.getParameter("pageSize");
		String pageNow = request.getParameter("pageNow");
		String showorgs = request.getParameter("showorgs");
		String receivetime = request.getParameter("receivetime");
		String showBringTakeInfos = request.getParameter("showBringTakeInfos");
		String confirmover = request.getParameter("confirmover");
		params.put("pageNow",Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		params.put("receivetime", receivetime);
		params.put("receiveorgid", showorgs);
		params.put("receivepeopleid", showBringTakeInfos);
		params.put("confirmover", confirmover);
		returnValue.put("confirms", ireceivingmana.getconfirms(params));
		returnValue.put("pagerecode", confirmsCount(request));
		returnValue.put("totalpage", Common.getPagetotalByPageSize((String) returnValue.get("pagerecode"), pageSize));
		returnValue.put("pageNow", Integer.valueOf(pageNow));
		return  returnValue;
	}
	
	/**
	 *得到基本信息表的记录数量 
	 */
	public String confirmsCount(HttpServletRequest request){
		String showorgs = request.getParameter("showorgs");
		String receivetime = request.getParameter("receivetime");
		String showBringTakeInfos = request.getParameter("showBringTakeInfos");
		String confirmover = request.getParameter("confirmover");

		Map<String,String> params = new HashMap<String,String>();
		params.put("table", " receivemgrbase ");
		StringBuffer sb = new StringBuffer();
		sb.append(" and status='1' ");
		if (Common.isNotEmpty(confirmover) & "1".equals(confirmover)) {
			sb.append("  or status='2'  ");
		}
		if (Common.isNotEmpty(receivetime)) {
			sb.append(" and receivetime= str_to_date('"+ receivetime +"','%Y-%m-%d')");
		}
		if (Common.isNotEmpty(showorgs)) {
			sb.append(" and receiveorgid= '"+ showorgs +"'");
		}
		if (Common.isNotEmpty(showBringTakeInfos)) {
			sb.append(" and receivepeopleid= '"+ showBringTakeInfos +"'");
		}
		params.put("where", sb.toString());
		return commonmapper.getCount(params);
	}
	
	/**
	 * 单位名称下拉查询
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	
	@ResponseBody
	@RequestMapping("receivingmana/queryorgs")
	public List<DictItem> queryorgs(HttpServletRequest request) throws UnsupportedEncodingException{
		String orgname =  java.net.URLDecoder.decode(request.getParameter("query"),"UTF-8");
		Map param = new HashMap();
		param.put("orgname", orgname);
		return ireceivingmana.fuzzyQueryOrgByName(param);
	}
	
	@ResponseBody
	@RequestMapping("receivingmana/editconfirm")
	public ReceiveInfo editConfirm(HttpServletRequest request){
		String id = request.getParameter("id");
		return  ireceivingmana.editConfirmBaseInfo(id);
	}
	
	/**
	 * 根据送货人Id得到送货人指纹
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/geprint")
	public ResponseEntity<byte[]> geprint(HttpServletRequest request) {
	    String DEFAULTPRINTDIR = commonmapper.getChart("PRINTPICTURESAVEDIR");//"E:/Amusement/新建文件夹 (7)/";//指纹默认保存路径  select * from product_chart  where itemid= 'PRINTPICTURESAVEDIR'
	    String NOPRINTDEFAULTPICTURE = DEFAULTPRINTDIR + commonmapper.getChart("NOPRINTPICTUREDEFAULT");//没指纹默认的显示图像,这张图片要加入文字：此人员没有注册并且没有录入指纹！ select * from product_chart  where itemid= 'NOPRINTPICTUREDEFAULT'
		String id = request.getParameter("id");
		 ByteArrayOutputStream out = new ByteArrayOutputStream();  
		 File filedir = null; 
		 File findFile = null;
		 BufferedImage bi;
		 ResponseEntity<byte[]> re = null;
		try {
			filedir = new File(DEFAULTPRINTDIR);
			File[] fileList = filedir.listFiles();
			for(int i=0;i<fileList.length;i++){
				if (fileList[i].isFile()) {
					String filename = fileList[i].getName().split("\\.")[0];
					if(filename.equals(id)){
						findFile = new File(DEFAULTPRINTDIR+fileList[i].getName());
					}
				}
			}
			if(null==findFile){
				findFile = new File(NOPRINTDEFAULTPICTURE);
			}
			bi = ImageIO.read(findFile);
			ImageIO.write(bi, "JPEG", out);  
			re = new ResponseEntity<byte[]>(out.toByteArray(), HTTP_HEADERS, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return re;
	}
	
	/**
	 * 删除收获确认的多条
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/deleteconfirms")
	public String deleteconfirms(HttpServletRequest request){
		String ids = request.getParameter("ids");
		StringBuffer sqlids = new  StringBuffer();
		String ret = "";
		int Affectnum =0;
		try{
		String [] idsarr = ids.split("@_@");
		for(int i=0;i<idsarr.length;i++){
			if(Common.isEmpty(idsarr[i])){continue;}
			Affectnum +=  ireceivingmana.deleteconfirms(idsarr[i]);
		}
		LogUtil.getLog().debug(sqlids.toString());
		LogUtil.getLog().debug("删除数据记录"+Affectnum+"条");
		ret = "ok";
		}catch (Exception e){
			ret=e.getMessage();
		}
		return ret;
	}
	
	/**
	 * 根据收获确认的基本信息得到其详细信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/getReceivedDetailInfo")
	public List<ReceiveInfo> getReceivedDetailInfo(HttpServletRequest request){
		Map rtntValue = new HashMap();
		String receiveorgid = request.getParameter("receiveorgid");
		rtntValue.put("receiveorgid", receiveorgid);
		List<ReceiveInfo> ReceiveInfoes =  ireceivingmana.getDetInfoByReceiveorgId(rtntValue);
		System.out.println(JSONList.toJSONString(ReceiveInfoes));
		return ReceiveInfoes;
	}
	
	/**
	 * 当编辑一条收货确认的详细信息时，得到其已有的详情
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/editDetailInfo")
	public ReceiveInfo editDetailInfo(HttpServletRequest request){
		String idAndreceiveorgid = request.getParameter("idAndreceiveorgid");
		Map<String,String> param = new HashMap<String,String>();
		param.put("id", idAndreceiveorgid.split("@_@")[0]);
		param.put("receiveorgid", idAndreceiveorgid.split("@_@")[1]);
		ReceiveInfo receiveinfo = ireceivingmana.getEditDetailInfo(param);
		return receiveinfo;
	}
	
	/**
	 * 删除一条确认明细
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/deleteConfirDetailInfo")
	public String deleteConfirDetailInfo(HttpServletRequest request){
		String ids = request.getParameter("ids");
		Map param = new HashMap();
		param.put("id", ids.split("@_@")[0]);
		param.put("receivemgrid", ids.split("@_@")[1]);
		param.put("status", ids.split("@_@")[2]);
		String result = ireceivingmana.deleteConfirDetailInfo(param);
		return result;
	}
	
    /**
     * 保存编辑的收获确认详情	
     * @param request
     * @return
     */
	@ResponseBody
	@RequestMapping("receivingmana/saveEditConfirDetailInfo")
	public String saveEditConfirDetailInfo(HttpServletRequest request){
		ReceiveInfo receiveinfo =  BusinessControllerHelper.processSaveEditConfirDetailInfo(request);
		return ireceivingmana.saveEditConfirDetailInfo(receiveinfo);
	}
	
	@ResponseBody
	@RequestMapping("receivingmana/updateConfirmStatus")
	public String updateConfirmStatus(HttpServletRequest request){
		String result  = "";
		String currentstatus = request.getParameter("currentstatus");
		String id = request.getParameter("id");
		Map param = new HashMap();
		param.put("id", id);
		param.put("status", "2");
		param.put("receivecargotime", Common.stringDefaultOfEmpty(request.getParameter("receivecargotime"), "") );
		param.put("bringcargopeopletel", Common.stringDefaultOfEmpty(request.getParameter("bringcargopeopletel"), ""));
		param.put("bringpeople", Common.stringDefaultOfEmpty(request.getParameter("bringpeople"), ""));
		param.put("bringorg", Common.stringDefaultOfEmpty(request.getParameter("bringorg"), ""));
		if(Common.isNotEmpty(currentstatus)&&"2".equals(currentstatus)){
			result  = ireceivingmana.precessBackProduct(param);
		}else{
			result  = ireceivingmana.updateConfirmStatusAndBaseInfo(param);
		}
		return result;
	}
	
	//===================================================================================辐照管理 
	/**
	 * 初始化辐照管理页面信息
	 * @param model
	 * @return
	 */
	@RequestMapping("Irradiationmana")
	public String initIrradiationmana(Model model){
		model.addAttribute("showcargoinfos", ireceivingmana.getCargoinfo());//货物名称
		model.addAttribute("showorgs", ireceivingmana.getAllOrgs());//送货单位
		model.addAttribute("showcountorginfos", ireceivingmana.getAllOrginfo());//计数单位
		model.addAttribute("irradtypes", commonmapper.getDictItemByGroupId("irradtypes"));//辐照方式
		model.addAttribute("timeorgs", commonmapper.getDictItemByGroupId("timeorgs"));//辐照时间单位
		model.addAttribute("loadmodel", commonmapper.getDictItemByGroupId("loadmodel"));//装载模式
		return Common.BACKGROUND_PATH + "/businessmana/radiationmana/list";
	}
	
    /**
     * 查询辐照管理信息
     */
	@ResponseBody
	@RequestMapping("receivingmana/queryradiation")
	public Map queryradiation(HttpServletRequest request){
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> returnValue = new HashMap<String,Object>();
		String pageSize = request.getParameter("pageSize");
		String pageNow = request.getParameter("pageNow");
		String showorgs = request.getParameter("showorgs");
		String receivetime = request.getParameter("receivetime");
		String cargoid = request.getParameter("cargoname");
		params.put("pageNow",Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		params.put("receivetime", receivetime);
		params.put("receiveorgid", showorgs);
		params.put("cargoid", cargoid);
		returnValue.put("radiations", ireceivingmana.getradiations(params));
		returnValue.put("pagerecode", radiationCount(request));
		returnValue.put("totalpage", Common.getPagetotalByPageSize((String) returnValue.get("pagerecode"), pageSize));
		returnValue.put("pageNow", Integer.valueOf(pageNow));
		return  returnValue;
	}
	
	/**
	 *得到基本信息表的记录数量 
	 */
	public String radiationCount(HttpServletRequest request){
		String showorgs = request.getParameter("showorgs");
		String receivetime = request.getParameter("receivetime");
		String cargoname = request.getParameter("cargoname");
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("table", " receivemgrbase t1,receivemgrdetail t2  ");
		StringBuffer sb = new StringBuffer();
		sb.append(" and  t1.id=t2.receivemgrid ");
		sb.append(" and t2.irradednum < t2.cargocount");
		sb.append(" and t2.status='2' ");
		if (Common.isNotEmpty(receivetime)) {
			sb.append(" and t1.receivetime= str_to_date('"+ receivetime +"','%Y-%m-%d')");
		}
		if (Common.isNotEmpty(showorgs)) {
			sb.append(" and t1.receiveorgid= '"+ showorgs +"'");
		}
		if (Common.isNotEmpty(cargoname)) {
			sb.append(" and t2.cargoid= '"+ cargoname +"'");
		}
		params.put("where", sb.toString());
		return commonmapper.getCount(params);
	}
	
	/**
	 * 得到当前货物的信息，弹出对话框填写辐照信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/goradiation")
	public ReceiveInfo goradiation(HttpServletRequest request){
		String id = request.getParameter("id");
		ReceiveInfo receiveinfo = ireceivingmana.getRadiationInfo(id);
		return receiveinfo;
	}
	
	/**
	 * 保存填写的信息，状态默认为0
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/setradiation")
	public String setRadiation(HttpServletRequest request){
		RadiationInfo radiationinfo =  BusinessControllerHelper.precessSaveRadiation(request);
		return ireceivingmana.setRadiation(radiationinfo);
	}
	
	/**
	 * 得到正在辐照的所有货物，即状态为0的货物
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/initcurrentradiations")
	public List<RadiationInfo>  initCurrentRadiations(HttpServletRequest request){
		return ireceivingmana.queryAllCurrentRadiations();
	}
	
	/**
	 * 辐照完成，将其状态改为1
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/completeradiation")
	public String completeRadiation(HttpServletRequest request){
		String id = Common.stringDefaultOfEmpty(request.getParameter("id"),"");
		return  ireceivingmana.updateRadiationStatus(id);
	}
	
	//===================================================================================出获管理
	
	/**
	 * 初始化出货管理界面
	 * @param model
	 * @return
	 */
	@RequestMapping("shoppingmana")
	public String initShippingMana(Model model){
		model.addAttribute("showcargoinfos", ireceivingmana.getCargoinfo());//货物名称
		model.addAttribute("showorgs", ireceivingmana.getAllOrgs());//送货单位
		model.addAttribute("showBringTakeInfos", ireceivingmana.getAllBringTakeInfoPeople());//送货人

		return Common.BACKGROUND_PATH + "/businessmana/shippingmana/list"; 
	}
	
	/**
	 * 得到可取获取列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/queryalltakecargoes")
	public Map queryAllTakeCargoes(HttpServletRequest request){
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> returnValue = new HashMap<String,Object>();
		String pageSize = request.getParameter("pageSize");
		String pageNow = request.getParameter("pageNow");
		String showorgs = request.getParameter("showorgs");
		//String receivetime = request.getParameter("receivetime");
		String cargoid = request.getParameter("cargoname");
		params.put("pageNow",Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		//params.put("receivetime", receivetime);
		params.put("receiveorgid", showorgs);
		params.put("cargoid", cargoid);
		returnValue.put("alltakecargoes", ireceivingmana.getAllTakeCargoes(params));
		returnValue.put("pagerecode", takeCargoesCount(request));
		returnValue.put("totalpage", Common.getPagetotalByPageSize((String) returnValue.get("pagerecode"), pageSize));
		returnValue.put("pageNow", Integer.valueOf(pageNow));
		return  returnValue;
	}
	
	
	public String takeCargoesCount(HttpServletRequest request){
		String showorgs = request.getParameter("showorgs");
		String cargoname = request.getParameter("cargoname");
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("table", " irradiation t  ");
		StringBuffer sb = new StringBuffer();
		sb.append(" and t.status = '1' and  t.takecargocount < t.irradednum ");
		if (Common.isNotEmpty(showorgs)) {
			sb.append(" and t.receiveorgid= '"+ showorgs +"'");
		}
		if (Common.isNotEmpty(cargoname)) {
			sb.append(" and t.cargoid= '"+ cargoname +"'");
		}
		params.put("where", sb.toString());
		return commonmapper.getCount(params);
	}
	
	/**
	 * 出货操作
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/savetakecargoe")
	public String saveTakeCargoe(HttpServletRequest request){
		TakeCargoInfo takecargoinfo = BusinessControllerHelper.precessSaveTakeCargo(request);
		String result = ireceivingmana.saveTakeCargoe(takecargoinfo);
		return result;
	}
	
	/**
	 * 查询已出货物列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/queryhavetakedcargoes")
	public Map queryHaveTakedCargoes(HttpServletRequest request){
		Map<String,Object> params = new HashMap<String,Object>();
		Map<String,Object> returnValue = new HashMap<String,Object>();
		String pageSize = request.getParameter("pageSize");
		String pageNow = request.getParameter("pageNow");
		String showorgsoftaked = request.getParameter("showorgsoftaked");
		String takecargotime = request.getParameter("takecargotime");
		params.put("pageNow",Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		params.put("takecargotime", takecargotime);
		params.put("showorgsoftaked", showorgsoftaked);
		returnValue.put("havetakedcargoes", ireceivingmana.getHaveTakedCargoes(params));
		returnValue.put("pagerecode", haveTakedCargoesCount(request));
		returnValue.put("totalpage", Common.getPagetotalByPageSize((String) returnValue.get("pagerecode"), pageSize));
		returnValue.put("pageNow", Integer.valueOf(pageNow));
		return  returnValue;
	}
	
	public String haveTakedCargoesCount(HttpServletRequest request){
		String takecargotime = request.getParameter("takecargotime");
		String showorgsoftaked = request.getParameter("showorgsoftaked");
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("table", " takecargobase t  ");
		StringBuffer sb = new StringBuffer();
		if (Common.isNotEmpty(takecargotime)) {
			sb.append(" and t.taketime= str_to_date('"+ takecargotime +"','%Y-%m-%d')");
		}
		if (Common.isNotEmpty(showorgsoftaked)) {
			sb.append(" and t.takecargoorg= '"+ showorgsoftaked +"'");
		}
		params.put("where", sb.toString());
		return commonmapper.getCount(params);
	}
	
	/**
	 * 查询已出货物的某条记录的详细信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/queryhavetakedcargoedetail")
	public List<TakeCargoInfo> queryHaveTakedCargoeDetail(HttpServletRequest request){
		String id = request.getParameter("id");
		return ireceivingmana.queryHaveTakedCargoeDetail(id);
	}
	
	/**
	 * 得到出货物信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/getreceivedcargoinfo")
    public ReceiveInfo getReceivedCargoInfo(HttpServletRequest request){
		String id = request.getParameter("id");
		return ireceivingmana.getReceivedCargoInfo(id);
    }
	
	/**
	 * 出货管理，出后确认初始化弹出框数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("receivingmana/gethavetakedcargoebaseanddetail")
	public Map getHaveTakedCargoeBaseAndDetail(HttpServletRequest request){
		String id = request.getParameter("takedcargoid");
		Map param = new HashMap();
		Map returnValue = new HashMap();
		param.put("id", id);
		TakeCargoInfo takecargoinfo = ireceivingmana.getHaveTakedCargoById(param);
		List<TakeCargoInfo> takecargodetailinfos = ireceivingmana.queryHaveTakedCargoeDetail(id);
		returnValue.put("takecargoinfo", takecargoinfo);
		returnValue.put("takecargodetailinfos", takecargodetailinfos);
		return returnValue;
		
	}
	
	@ResponseBody
	@RequestMapping("receivingmana/doConfirmOutCargo")
	public String  doConfirmOutCargo(HttpServletRequest request){
		String id = request.getParameter("takedcargoid");
		return ireceivingmana.updateTakedCargoBaseInfoStatus(id);
	}
	
	public IReceivingMana getIreceivingmana() {
		return ireceivingmana;
	}

	public void setIreceivingmana(IReceivingMana ireceivingmana) {
		this.ireceivingmana = ireceivingmana;
	}

	
	public CommonMapper getCommonmapper() {
		return commonmapper;
	}

	public void setCommonmapper(CommonMapper commonmapper) {
		this.commonmapper = commonmapper;
	}
}
