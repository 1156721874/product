package com.product.service;

import java.util.List;
import java.util.Map;

import com.product.entity.ChargeInfo;

public interface IChargeMana {

	List<ChargeInfo> getAllUnpaid(Map param);

	String doCharge(ChargeInfo chargeinfo);

	List<ChargeInfo> queryChargeLog(Map param);

}
