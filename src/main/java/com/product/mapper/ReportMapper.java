package com.product.mapper;

import java.util.List;
import java.util.Map;

import com.product.entity.DalidyCharge;
import com.product.entity.DalidyIrradation;
import com.product.entity.DalidyOutCargoes;
import com.product.entity.DalidyReceived;
import com.product.entity.ReceiveInfo;

public interface ReportMapper {

	List<ReceiveInfo> queryreportlist(Map param);

	List<DalidyReceived> refreshDalidyReceivedBody(Map param);

	List<DalidyIrradation> refreshDalidyIrradationBody(Map param);

	List<DalidyOutCargoes> refreshDalidyOutCargoesbody(Map param);

	List<DalidyCharge> refreshDalidyChargeBody(Map param);



}
