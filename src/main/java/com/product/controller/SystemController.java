package com.product.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.product.entity.BringTakeInfo;
import com.product.entity.Cargoinfo;
import com.product.entity.CountOrgInfo;
import com.product.entity.ShippingInfo;
import com.product.mapper.BringTakeInfoMapper;
import com.product.mapper.CargoinfoMapper;
import com.product.mapper.CommonMapper;
import com.product.mapper.CountOrginfoMapper;
import com.product.mapper.ShippingInfoMapper;
import com.product.util.Common;
/**
 * 基本信息部分只是简单的增删改查，没有复杂业务逻辑简单起见不设置server层，直接在controller下调用mapper访问数据库
 * @author wzq
 *
 */

@Controller
@RequestMapping("/system/")
public class SystemController {

	@Resource
	private CargoinfoMapper cargoinfomapper;//货物信息
	@Resource
	private ShippingInfoMapper  shippinginfoMapper;//送货信息
	@Resource
	private BringTakeInfoMapper  bringtakeinfoMapper;//送取货人
	@Resource
	private CountOrginfoMapper  countOrginfoMapper;//单位信息
	@Resource
	private CommonMapper commonmapper;
	//==================================================================货物信息
	/**
	 * 得到货物信息列表
	 * @param model
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("cargoinfo")
	public String cargoinfo(Model model,
			@RequestParam(value = "pageNow") String pageNow,
			@RequestParam(value = "pageSize") String pageSize) {
		Map params = new HashMap();
		params.put("pageNow",
				Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		model.addAttribute("cargoes", cargoinfomapper.getCargoinfo(params));
		params.put("table", "cargoinfo");
		params.put("pagerecode", cargoinfomapper.getCount(params));
		params.put("totalpage", Common.getPagetotalByPageSize(
				(String) params.get("pagerecode"), pageSize));
		params.put("pageNow", Integer.valueOf(pageNow));
		model.addAttribute("params", params);
		return Common.BACKGROUND_PATH + "/system/cargoinfo/list";
	}
  
	@RequestMapping("orginfo")  //单位 shipping表
	public String orginfo(Model model,HttpServletRequest request) {
		Map params = new HashMap();
		params.put("pageNow",
				Integer.valueOf("0") * Integer.valueOf("20"));
		params.put("pageSize", Integer.valueOf("20"));
		model.addAttribute("orginfos", countOrginfoMapper.getorginfo(params));
		params.put("table", "orginfo");
		params.put("pagerecode", countOrginfoMapper.getCount(params));
		params.put("totalpage", Common.getPagetotalByPageSize(
				(String) params.get("pagerecode"), "20"));
		params.put("pageNow", Integer.valueOf("0"));
		model.addAttribute("params", params);
		return Common.BACKGROUND_PATH + "/system/orginfo/list";
	}
	
	//数量单位  orginfo表
	@RequestMapping("shippinginfo")  //单位 shipping表
	public String shippinginfo(Model model,HttpServletRequest request) {
		Map params = new HashMap();
		params.put("pageNow",
				Integer.valueOf("0") * Integer.valueOf("20"));
		params.put("pageSize", Integer.valueOf("20"));
		model.addAttribute("orginfos", shippinginfoMapper.getshipinfo(params));
		params.put("table", "orginfo");
		params.put("pagerecode", shippinginfoMapper.getCount(params));
		params.put("totalpage", Common.getPagetotalByPageSize(
				(String) params.get("pagerecode"), "20"));
		params.put("pageNow", Integer.valueOf("0"));
		model.addAttribute("params", params);
		return Common.BACKGROUND_PATH + "/system/shippinginfo/list";
	}
	//送取货人  bringtakeinfoMapper表
		@RequestMapping("bringtakeinfo")  //单位 shipping表
		public String bringtakeinfo(Model model,HttpServletRequest request) {
			Map params = new HashMap();
			params.put("pageNow",
					Integer.valueOf("0") * Integer.valueOf("20"));
			params.put("pageSize", Integer.valueOf("20"));
			model.addAttribute("bringtakeinfos", bringtakeinfoMapper.getBringtakeinfo(params));
			params.put("table", "bringtakeinfo");
			params.put("pagerecode", bringtakeinfoMapper.getCount(params));
			params.put("totalpage", Common.getPagetotalByPageSize(
					(String) params.get("pagerecode"), "20"));
			params.put("pageNow", Integer.valueOf("0"));
			model.addAttribute("params", params);
			return Common.BACKGROUND_PATH + "/system/bringtakeinfo/list";
		}
	/**
	 * 分页查询货物
	 * @param model
	 * @param cargoname
	 * @param pageNow
	 * @param pageSize
	 * @param isfromsearch
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/cargoinfo/findByPage")
	public List<Cargoinfo> findByPage(Model model,
			@RequestParam(value = "cargoname") String cargoname,
			@RequestParam(value = "pageNow") String pageNow,
			@RequestParam(value = "pageSize") String pageSize,
			@RequestParam(value = "isfromsearch") String isfromsearch)
			throws Exception {
		if ("true".equals(isfromsearch)) {
			pageNow = "0";
			pageSize = "20";
		}
		Map params = new HashMap();
		params.put("cargoname", cargoname);
		params.put("pageNow",
				Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		params.put("table", "cargoinfo");
		if (Common.isNotEmpty(cargoname)) {
			params.put("where", " cargoname like '%" + cargoname + "%'");
		}
		return cargoinfomapper.getCargoinfo(params);
	}
//返回list
	@ResponseBody
	@RequestMapping("/orginfo/findByPageofOrginfo")
	public List<CountOrgInfo> findByPageofOrginfo(Model model,
			@RequestParam(value = "orgname") String orgname,
			@RequestParam(value = "pageNow") String pageNow,
			@RequestParam(value = "pageSize") String pageSize,
			@RequestParam(value = "isfromsearch") String isfromsearch)
			throws Exception {
		if ("true".equals(isfromsearch)) {
			pageNow = "0";
			pageSize = "20";
		}
		Map params = new HashMap();
		params.put("orgname", orgname);
		params.put("pageNow",
				Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		params.put("table", "shipppinginfo");
		if (Common.isNotEmpty(orgname)) {
			params.put("where", " orgname like '%" + orgname + "%'");
		}
		return countOrginfoMapper.getorginfo(params);
	}
	
	//shipping 的orginfo表
	@ResponseBody
	@RequestMapping("/shippinginfo/findByPageofShipping")
	public List<ShippingInfo> findByPageofShipping(Model model,
			@RequestParam(value = "orgname") String orgname,
			@RequestParam(value = "pageNow") String pageNow,
			@RequestParam(value = "pageSize") String pageSize,
			@RequestParam(value = "isfromsearch") String isfromsearch)
			throws Exception {
		if ("true".equals(isfromsearch)) {
			pageNow = "0";
			pageSize = "20";
		}
		Map params = new HashMap();
		params.put("orgname", orgname);
		params.put("pageNow",
				Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		params.put("table", "shippinginfo");
		if (Common.isNotEmpty(orgname)) {
			params.put("where", " orgname like '%" + orgname + "%'");
		}
		return shippinginfoMapper.getshipinfo(params);
	}
	//js的后台处理逻辑
	@ResponseBody
	@RequestMapping("/bringtakeinfo/findByPageofBringtaking")
	public List<BringTakeInfo> findByPageofBringtaking(Model model,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "pageNow") String pageNow,
			@RequestParam(value = "pageSize") String pageSize,
			@RequestParam(value = "isfromsearch") String isfromsearch)
			throws Exception {
		if ("true".equals(isfromsearch)) {
			pageNow = "0";
			pageSize = "20";
		}
		Map params = new HashMap();
		params.put("name", name);
		params.put("pageNow",
				Integer.valueOf(pageNow) * Integer.valueOf(pageSize));
		params.put("pageSize", Integer.valueOf(pageSize));
		params.put("table", "bringtakeinfo");
		if (Common.isNotEmpty(name)) {
			params.put("where", " name like '%" + name + "%'");
		}
		return bringtakeinfoMapper.getBringtakeinfo(params);
	}
	/**
	 * 根据条件获取某张表的记录数
	 * @param cargoname
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cargoinfo/getCount")
	public String getCount(@RequestParam(value = "cargoname") String cargoname) {
		Map params = new HashMap();
		params.put("table", "cargoinfo");
		if (Common.isNotEmpty(cargoname)) {
			params.put("where", " cargoname like '%" + cargoname + "%'");
		}
		return cargoinfomapper.getCount(params);
	}
//单位
	@ResponseBody
	@RequestMapping("/orginfo/getCountofOrginfo")
	public String getCountofOrginfo(@RequestParam(value = "orgname") String orgname) {
		Map params = new HashMap();
		params.put("table", "shippinginfo");
		if (Common.isNotEmpty(orgname)) {
			params.put("where", " orgname like '%" + orgname + "%'");
		}
		return countOrginfoMapper.getCount(params);
	}
	//计量单位
		@ResponseBody
		@RequestMapping("/shippinginfo/getCountofShipinfo")
		public String getCountofShipinfo(@RequestParam(value = "orgname") String orgname) {
			Map params = new HashMap();
			params.put("table", "shippinginfo");
			if (Common.isNotEmpty(orgname)) {
				params.put("where", " orgname like '%" + orgname + "%'");
			}
			return shippinginfoMapper.getCount(params);
		}
		//送取货人
		@ResponseBody
		@RequestMapping("/orginfo/getCountofBringTake")
		public String getCountofBringTake(@RequestParam(value = "name") String name) {
			Map params = new HashMap();
			params.put("table", "bringtakeinfo");
			if (Common.isNotEmpty(name)) {
				params.put("where", " name like '%" + name + "%'");
			}
			return bringtakeinfoMapper.getCount(params);
		}
	/**
	 * 添加一条货物信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cargoinfo/doAddCargo")
	public String doAddCargo(HttpServletRequest request) {
		String rtn = "ok";
		try {
			String addcargoname = request.getParameter("addcargoname");
			Map addcargonameparam = new HashMap();
			addcargonameparam.put("addcargoname", addcargoname);
			    if(cargoinfomapper.isrepeat(addcargonameparam)>=1){
			    	rtn = "货物【"+addcargoname+"】已经存在!";
			    	return rtn;
			    }
				cargoinfomapper.addcargo(new Cargoinfo(
						Common.stringDefaultOfEmpty(addcargoname,""),
						Common.stringDefaultOfEmpty(request.getParameter("org"),""),
						Common.stringDefaultOfEmpty(request.getParameter("irradtype"),""),
						Common.stringDefaultOfEmpty(request.getParameter("irradtime"),""),
						Common.stringDefaultOfEmpty(request.getParameter("timeorg"),"")));
		} catch (Exception e) {
			e.printStackTrace();
			rtn = e.getMessage();
		}
		return rtn;
	}

	//单位
	@ResponseBody
	@RequestMapping("/orginfo/doAddOrg")
	public String doAddOrg(HttpServletRequest request) {
		String rtn = "ok";
		try {
			String addorgname = request.getParameter("addorgname");
			Map addcargonameparam = new HashMap();
			addcargonameparam.put("addorgname", addorgname);
			    if(countOrginfoMapper.isrepeat(addcargonameparam)>=1){
			    	rtn = "单位【"+addorgname+"】已经存在!";
			    	return rtn;
			    }
			    countOrginfoMapper.addcountOrgInfo(new CountOrgInfo (
						Common.stringDefaultOfEmpty(addorgname,""),
						Common.stringDefaultOfEmpty(request.getParameter("mask"),"")));
		} catch (Exception e) {
			e.printStackTrace();
			rtn = e.getMessage();
		}
		return rtn;
	}
	
	//计量单位
		@ResponseBody
		@RequestMapping("/shippinginfo/doAddShip")
		public String doAddShip(HttpServletRequest request) {
			String rtn = "ok";
			try {
				String addorgname = request.getParameter("addorgname");
				Map addcargonameparam = new HashMap();
				addcargonameparam.put("addorgname", addorgname);
				    if(shippinginfoMapper.isrepeat(addcargonameparam)>=1){
				    	rtn = "计量单位【"+addorgname+"】已经存在!";
				    	return rtn;
				    }
				    shippinginfoMapper.addship(new ShippingInfo (
							Common.stringDefaultOfEmpty(addorgname,""),
							Common.stringDefaultOfEmpty(request.getParameter("mask"),"")));
			} catch (Exception e) {
				e.printStackTrace();
				rtn = e.getMessage();
			}
			return rtn;
		}
		//送取货人
		@ResponseBody
		@RequestMapping("/bringtakeinfo/doAddBring")
		public String doAddBring(HttpServletRequest request) {
			String rtn = "ok";
			try {
				String addname = request.getParameter("addname");
				Map addcargonameparam = new HashMap();
				addcargonameparam.put("addorgname", addname);
				    if(countOrginfoMapper.isrepeat(addcargonameparam)>=1){
				    	rtn = "此人【"+addname+"】已经存在!";
				    	return rtn;
				    }
				    bringtakeinfoMapper.addBring(new BringTakeInfo (
							Common.stringDefaultOfEmpty(addname,""),
							Common.stringDefaultOfEmpty(request.getParameter("orgname"),""),
							Common.stringDefaultOfEmpty(request.getParameter("tel"),""),
							Common.stringDefaultOfEmpty(request.getParameter("mask"),"")));
			} catch (Exception e) {
				e.printStackTrace();
				rtn = e.getMessage();
			}
			return rtn;
		}
	/**
	 * 根据条件得到一条货物记录
	 * @param idAndName
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/cargoinfo/getOneCargo")
	public Cargoinfo getOneCargo(@RequestParam(value = "idAndName") String idAndName){
		Map params = new HashMap();
		params.put("id", idAndName.split("@_@")[0]);
		params.put("cargoname", idAndName.split("@_@")[1]);
		Cargoinfo cargoinfo =  cargoinfomapper.getOneCargo(params);
		return cargoinfo;
	}
	//单位
	@ResponseBody
    @RequestMapping("/orginfo/getOneOrg")
	public CountOrgInfo getOneOrg(@RequestParam(value = "idAndName") String idAndName){
		Map params = new HashMap();
		params.put("id", idAndName.split("@_@")[0]);
		params.put("orgname", idAndName.split("@_@")[1]);
		CountOrgInfo orginfo =  countOrginfoMapper.getOneCargo(params);
		return orginfo;
	}
	
	//计量单位
		@ResponseBody
	    @RequestMapping("/shippinginfo/getOneShip")
		public ShippingInfo getOneShip(@RequestParam(value = "idAndName") String idAndName){
			Map params = new HashMap();
			params.put("id", idAndName.split("@_@")[0]);
			params.put("orgname", idAndName.split("@_@")[1]);
			ShippingInfo orginfo =  shippinginfoMapper.getOneShip(params);
			return orginfo;
		}
		//送取货人
				@ResponseBody
			    @RequestMapping("/bringtakeinfo/getOneBring")
				public BringTakeInfo getOneBring(@RequestParam(value = "idAndName") String idAndName){
					Map params = new HashMap();
					params.put("id", idAndName.split("@_@")[0]);
					params.put("name", idAndName.split("@_@")[1]);
					/*params.put("orgname", idAndName.split("@_@")[2]);
					params.put("tel", idAndName.split("@_@")[3]);
					params.put("mask", idAndName.split("@_@")[4]);*/
					BringTakeInfo bringtakeinfo =  bringtakeinfoMapper.getOneBring(params);
					return bringtakeinfo;
				}
	/**
	 * 保存编辑后的货物
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/cargoinfo/saveEditCargo")
	public String saveEditCargo(HttpServletRequest request){
		String rtn = "ok";
		try {
			String addcargoname = request.getParameter("addcargoname");
				cargoinfomapper.saveEditCargo(new Cargoinfo(
						Common.stringDefaultOfEmpty(addcargoname,""),
						Common.stringDefaultOfEmpty(request.getParameter("org"),""),
						Common.stringDefaultOfEmpty(request.getParameter("irradtype"),""),
						Common.stringDefaultOfEmpty(request.getParameter("irradtime"),""),
						Common.stringDefaultOfEmpty(request.getParameter("timeorg"),"")));
		} catch (Exception e) {
			e.printStackTrace();
			rtn = e.getMessage();
		}
		return rtn;
	}
	
	//单位
	@ResponseBody
    @RequestMapping("/orginfo/saveEditOrg")
	public String saveEditOrg(HttpServletRequest request){
		String rtn = "ok";
		try {
			String addorgname = request.getParameter("addorgname");
			countOrginfoMapper.saveEditCargo(new CountOrgInfo(
						Common.stringDefaultOfEmpty(addorgname,""),
						Common.stringDefaultOfEmpty(request.getParameter("mask"),"")));
		} catch (Exception e) {
			e.printStackTrace();
			rtn = e.getMessage();
		}
		return rtn;
	}
  
	//计量单位
		@ResponseBody
	    @RequestMapping("/shippinginfo/saveEditShip")
		public String saveEditShip(HttpServletRequest request){
			String rtn = "ok";
			try {
				String addorgname = request.getParameter("addorgname");
				shippinginfoMapper.saveEditShip(new ShippingInfo(
							Common.stringDefaultOfEmpty(addorgname,""),
							Common.stringDefaultOfEmpty(request.getParameter("mask"),"")));
			} catch (Exception e) {
				e.printStackTrace();
				rtn = e.getMessage();
			}
			return rtn;
		}
		//送取货人
		@ResponseBody
	    @RequestMapping("/bringtakeinfo/saveEditBring")
		public String saveEditBring(HttpServletRequest request){
			String rtn = "ok";
			try {
				String addname = request.getParameter("addname");
				 bringtakeinfoMapper.saveEditBring(new BringTakeInfo (
							Common.stringDefaultOfEmpty(addname,""),
							Common.stringDefaultOfEmpty(request.getParameter("orgname"),""),
							Common.stringDefaultOfEmpty(request.getParameter("tel"),""),
							Common.stringDefaultOfEmpty(request.getParameter("mask"),"")));
			} catch (Exception e) {
				e.printStackTrace();
				rtn = e.getMessage();
			}
			return rtn;
		}
	/**
	 * 根据条件删除一条货物记录
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/cargoinfo/deleteCargo")
	public String deleteCargo(HttpServletRequest request){
		String rtn = "ok";
		String idAndName = request.getParameter("idAndName");
		try{
		if(Common.isNotEmpty(idAndName)){
			Map params = new HashMap();
			params.put("id", idAndName.split("@_@")[0]);
			params.put("cargoname", idAndName.split("@_@")[1]);
			cargoinfomapper.deleteByAttribute(params);
		}
		}catch (Exception e){
			rtn = e.getMessage();
		}
		return rtn;
	}
	
	//单位
	@ResponseBody
    @RequestMapping("/orginfo/deleteOrg")
	public String deleteOrg(HttpServletRequest request){
		String rtn = "ok";
		String idAndName = request.getParameter("idAndName");
		try{
		if(Common.isNotEmpty(idAndName)){
			Map params = new HashMap();
			params.put("id", idAndName.split("@_@")[0]);
			params.put("orgname", idAndName.split("@_@")[1]);
			countOrginfoMapper.deleteByAttribute(params);
		}
		}catch (Exception e){
			rtn = e.getMessage();
		}
		return rtn;
	}
	
	//计量单位
		@ResponseBody
	    @RequestMapping("/shippinginfo/deleteShip")
		public String deleteShip(HttpServletRequest request){
			String rtn = "ok";
			String idAndName = request.getParameter("idAndName");
			try{
			if(Common.isNotEmpty(idAndName)){
				Map params = new HashMap();
				params.put("id", idAndName.split("@_@")[0]);
				params.put("orgname", idAndName.split("@_@")[1]);
				shippinginfoMapper.deleteByAttribute(params);
			}
			}catch (Exception e){
				rtn = e.getMessage();
			}
			return rtn;
		}
		//送取货人
				@ResponseBody
			    @RequestMapping("/bringtakeinfo/deleteBring")
				public String deleteBring(HttpServletRequest request){
					String rtn = "ok";
					String idAndName = request.getParameter("idAndName");
					try{
					if(Common.isNotEmpty(idAndName)){
						Map params = new HashMap();
						params.put("id", idAndName.split("@_@")[0]);
						params.put("name", idAndName.split("@_@")[1]);
						bringtakeinfoMapper.deleteByAttribute(params);
					}
					}catch (Exception e){
						rtn = e.getMessage();
					}
					return rtn;
				}
	//==================================================================送货信息
				
				
				
/*
@RequestMapping("/bringtakeinfo/dofinger")
public String transform(HttpServletRequest request)throws  Exception {
				//response.setContentType("text/html");
				//PrintWriter out = response.getWriter();
				//factory对象为解析器提供解析时的缺省的配置
				String result="";
				Map<String,String> map=new HashMap<String,String>();
				Set<FileItem> fileSet=new  HashSet<FileItem>();
				FileItemFactory factory = new DiskFileItemFactory();
				//创建一个解析器
				ServletFileUpload sfu = new ServletFileUpload(factory);
				try {
					//将解析的结果封转到FileItem对象上，一个表单域对应一个FileItem对象。
					//只需要调用FileItem对象的提供的方法即可
					//获得相应表单域的数据
					String orgname=request.getParameter("orgname");
					List<FileItem> items = sfu.parseRequest(request);
					for(int i=0;i<items.size();i++){
						//遍历存储
						FileItem item = items.get(i);
						//普通的表单域
						if(item.isFormField()){
							String value = item.getString();
							String name = item.getFieldName();
							//System.out.println(username);
							if("addname".equals(name)){
								map.put(name,value);
							}
							if("orgname".equals(name)){
								map.put(name,value);
							}if("tel".equals(name)){
								map.put(name,value);
							}if("mask".equals(name)){
								map.put(name,value);
							}
						}else{
							//上传文件域
							
							//依据逻辑路径（“upload”）获得实际部署时的物理路径
							//String key=item.getName();
							fileSet.add(item);
						}
					}
					//保存数据
					BringTakeInfo bringtakeinfo=new BringTakeInfo(map.get("addname"),map.get("orgname"),map.get("tel"),map.get("mask"));
					BringTakeInfo querybrigntakeinfo =bringtakeinfoMapper.saveBringtakeinfo(bringtakeinfo);
					
					if(!"".equals(Common.stringDefaultOfEmpty(querybrigntakeinfo.getId(), ""))){
						//保存图片
					FileItem [] arryitem = (FileItem [])fileSet.toArray();
					for(int i=0;i<arryitem.length;i++){
						FileItem item = arryitem[i];
						if(null!=item && "jpg".equals(item.getName().split("\\.")[1])){
							try {
								File file = new File("E:\\Amusement\\"+ querybrigntakeinfo.getId()+".jpg");
								item.write(file);
							} catch (Exception e) {
								if(null!=item && "bmp".equals(item.getName().split("\\.")[1])){
									File file = new File("E:\\Amusement\\"+querybrigntakeinfo.getId()+".bmp");
									item.write(file);
								}
							}
						} else if(null!=item && "bmp".equals(item.getName().split("\\.")[1])){
							try {
								File file = new File("E:\\Amusement\\"+ querybrigntakeinfo.getId()+".bmp");
								item.write(file);
							} catch (Exception e) {
								if(null!=item && "jpg".equals(item.getName().split("\\.")[1])){
									File file = new File("E:\\Amusement\\"+querybrigntakeinfo.getId()+".jpg");
									item.write(file);
								}
								}
							}
						}
					}
					result="ok";
				} catch (FileUploadException e) {
					result="保存失败!\n"+e.getMessage();
					e.printStackTrace();
				} catch (Exception e) {
					result="保存失败\n"+e.getMessage();
					e.printStackTrace();
				}
				//out.flush();
				//out.close();
				return result;
				
}
*/
@ResponseBody	
@RequestMapping(value = "/bringtakeinfo/dofinger")  
public String upload(HttpServletRequest request, ModelMap model,@RequestParam(value = "adds1") MultipartFile[] files) {  
    //保存  
	String result="";
    try {  
	    String DEFAULTPRINTDIR = commonmapper.getChart("PRINTPICTURESAVEDIR");//"E:/Amusement/新建文件夹 (7)/";//指纹默认保存路径  select * from product_chart  where itemid= 'PRINTPICTURESAVEDIR'
    	Map<String,String> map=new HashMap<String,String>();
    	//Set<FileItem> fileSet=new  HashSet<FileItem>();
    	 String nameVal=request.getParameter("addname");
    	 String orgname=request.getParameter("orgname");
    	 String tel=request.getParameter("tel");
    	 String mask = request.getParameter("mask");
    	 map.put("name", nameVal);
    	map.put("orgname", orgname);
    	map.put("tel", tel);
    	map.put("mask", mask);
    	BringTakeInfo bringtakeinfo=new BringTakeInfo(map.get("name"),map.get("orgname"),map.get("tel"),map.get("mask"));
    	int querybrigntakeinfo =bringtakeinfoMapper.saveBringtakeinfo(bringtakeinfo);
    	
    	 if(!"".equals(Common.stringDefaultOfEmpty(bringtakeinfo.getId(), ""))&querybrigntakeinfo>0){
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//List<MultipartFile> files = multipartRequest.getFiles("adds1");
		for (MultipartFile mf : files) {
			if(!mf.isEmpty()){
				 //String fileName = mf.getOriginalFilename(); 
				  File targetFile = new File(DEFAULTPRINTDIR, bringtakeinfo.getId()+".bmp");
				  mf.transferTo(targetFile);
			}
		}	
    	 }
    	 result="<script>window.parent.uploadSuccess('ok');</script>";

    } catch (Exception e) {  
    	//result="保存失败\n"+e.getMessage();
    	result="<script>window.parent.uploadSuccess('error');</script>";

		e.printStackTrace(); 
    }  
    System.out.println("///////////////////////////////////////////////////////////"+result);
model.addAttribute("result", result);  

    return result;  
}  

	//================================================================js上传文件
	
	private ServletContext getServletContext() {
	// TODO Auto-generated method stub
	return null;
}

	public ShippingInfoMapper getShippinginfoMapper() {
		return shippinginfoMapper;
	}

	public void setShippinginfoMapper(ShippingInfoMapper shippinginfoMapper) {
		this.shippinginfoMapper = shippinginfoMapper;
	}

	public BringTakeInfoMapper getBringtakeinfoMapper() {
		return bringtakeinfoMapper;
	}

	public void setBringtakeinfoMapper(BringTakeInfoMapper bringtakeinfoMapper) {
		this.bringtakeinfoMapper = bringtakeinfoMapper;
	}


	public CountOrginfoMapper getCountOrginfoMapper() {
		return countOrginfoMapper;
	}

	public void setCountOrginfoMapper(CountOrginfoMapper countOrginfoMapper) {
		this.countOrginfoMapper = countOrginfoMapper;
	}

	public CargoinfoMapper getCargoinfomapper() {
		return cargoinfomapper;
	}

	public void setCargoinfomapper(CargoinfoMapper cargoinfomapper) {
		this.cargoinfomapper = cargoinfomapper;
	}
	public CommonMapper getCommonmapper() {
		return commonmapper;
	}

	public void setCommonmapper(CommonMapper commonmapper) {
		this.commonmapper = commonmapper;
	}
}
