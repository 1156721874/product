package com.product.mapper;

import java.util.List;
import java.util.Map;

import com.product.entity.RadiationInfo;
import com.product.entity.ReceiveInfo;
import com.product.entity.TakeCargoInfo;

/**
 * 收货管理mapper
 * @author wzq
 *
 */
public interface ReceivingManaMapper {

	int saveReceiveCargoBase(ReceiveInfo receiveinfo);
	int saveReceiveCargoDetail(ReceiveInfo receiveinfo);
	List<ReceiveInfo> isreapeat(ReceiveInfo receiveinfo);
	List<ReceiveInfo> getReceivedCargos(Map<String,String> params);
	List<ReceiveInfo> getconfirms(Map params);
	int deleteconfirms(Map baseinfoid);
	List<ReceiveInfo> getDetInfoByReceiveorgId(Map rtntValue);
	ReceiveInfo getEditDetailInfo(Map param);
	int deleteConfirDetailInfo(Map param);
	String checkhaveone(ReceiveInfo receiveinfo);
	int saveEditConfirDetailInfo(ReceiveInfo receiveinfo);
	List<ReceiveInfo> getradiations(Map params);
	ReceiveInfo getRadiationInfo(String id);
	int setRadiation(RadiationInfo radiationinfo);
	List<RadiationInfo> queryAllCurrentRadiations();
	int updateReceivedDetailIrradednum(RadiationInfo radiationinfo);
	int updateRadiationStatus(String id);
	int updateBaseStatus(Map id);
	int updateDetailStatus(Map id);
	List<RadiationInfo> getAllTakeCargoes(Map<String, Object> params);
	int updateHaveTakeCargoCount(TakeCargoInfo takecargoinfo);
	int saveTakeCargoBase(TakeCargoInfo takecargoinfo);
	int saveTakeCargoDetail(TakeCargoInfo takecargoinfo);
	Integer doRepeatTakeCargo(TakeCargoInfo takecargoinfo);
	List<TakeCargoInfo> queryHaveTakedCargoeDetailByBaseId(String id);
	List<TakeCargoInfo> getHaveTakedCargoes(Map<String, Object> params);
	ReceiveInfo getReceivedCargoInfo(String id);
	ReceiveInfo editConfirmBaseInfo(String id);
	int  updateConfirmInfo(Map idandstatus);
	ReceiveInfo getCargoBaseInfoById(Map param);
	TakeCargoInfo getHaveTakedCargoById(Map param);
	int updateTakedCargoBaseInfoStatus(String id);
    String queryReciveDetailIdByBaseId(String id);
	String queryIrradationIdsByBaseAndDetailID(Map qparam);
	int deleteIrradationIdsByBaseAndDetailID(Map qparam);
	int deleteTakecargodetailByIrradedId(Map param);
	int updateReceivemgrDetailirradednum(Map param);
	int  deleteconfirmDetail(Map id);
	int deleteconfirmDetails(Map receiveMgrDetailIds);

}
