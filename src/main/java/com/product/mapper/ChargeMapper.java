package com.product.mapper;

import java.util.List;
import java.util.Map;

import com.product.entity.ChargeInfo;

public interface ChargeMapper {

	List<ChargeInfo> getAllUnpaidOrPaid(Map param);

	int doChargeLog(ChargeInfo chargeinfo);

	int updateChargeFee(ChargeInfo chargeinfo);

	ChargeInfo queryChargemapper(String chargeid);

	List<ChargeInfo> queryChargeLog(Map param);

}
