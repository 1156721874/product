package com.product.service;

import java.util.List;
import java.util.Map;

import com.product.bean.common.DictItem;
import com.product.entity.RadiationInfo;
import com.product.entity.ReceiveInfo;
import com.product.entity.TakeCargoInfo;

public interface IReceivingMana {

	List<DictItem> getAllOrgs();
    List <DictItem> getAllBringTakeInfoPeople();
    List<DictItem> getCargoinfo();
    List<DictItem> getAllOrginfo();
	void saveReceiveCargo(ReceiveInfo receiveinfo);
	String isreapeat(ReceiveInfo receiveinfo);
	List<ReceiveInfo> getreceivedCargos(Map params);
	List<ReceiveInfo> getconfirms(Map params);
	int deleteconfirms(String  sqlids);
	List<ReceiveInfo> getDetInfoByReceiveorgId(Map rtntValue);
	ReceiveInfo getEditDetailInfo(Map param);
	String deleteConfirDetailInfo(Map param);
	String saveEditConfirDetailInfo(ReceiveInfo receiveinfo);
	List<ReceiveInfo> getradiations(Map params);
	ReceiveInfo getRadiationInfo(String id);
	String setRadiation(RadiationInfo radiationinfo);
	List<RadiationInfo> queryAllCurrentRadiations();
	String updateRadiationStatus(String id);
	String updateConfirmStatus(Map param);
	List<RadiationInfo> getAllTakeCargoes(Map<String, Object> params);
	String saveTakeCargoe(TakeCargoInfo takecargoinfo);
	List<TakeCargoInfo> queryHaveTakedCargoeDetail(String id);
	List<TakeCargoInfo> getHaveTakedCargoes(Map<String, Object> params);
	ReceiveInfo getReceivedCargoInfo(String id);
	ReceiveInfo editConfirmBaseInfo(String id);
	String updateConfirmStatusAndBaseInfo(Map param);
	ReceiveInfo getCargoBaseInfoById(Map param);
	TakeCargoInfo getHaveTakedCargoById(Map param);
	String updateTakedCargoBaseInfoStatus(String id);
	String precessBackProduct(Map param);
	List<DictItem> fuzzyQueryOrgByName(Map param);
    
}
