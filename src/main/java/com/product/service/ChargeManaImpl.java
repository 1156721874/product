package com.product.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product.entity.ChargeInfo;
import com.product.mapper.ChargeMapper;
import com.product.util.LogUtil;

@Service(value="ChargeManaImpl")
public class ChargeManaImpl implements IChargeMana {

	@Resource
	private ChargeMapper chargemapper;
	
	public List<ChargeInfo> getAllUnpaid(Map param) {
		
		return chargemapper.getAllUnpaidOrPaid(param);
	}

	@Transactional(rollbackFor=java.lang.Exception.class)
	public String doCharge(ChargeInfo chargeinfo) {
		String result = "";
		try {
			int unum = chargemapper.updateChargeFee(chargeinfo);
			if (unum > 0) {
				ChargeInfo querychargeinfo = chargemapper.queryChargemapper(chargeinfo.getChargeid());
				chargeinfo.setPaid(querychargeinfo.getPaid());
				chargeinfo.setUnpaid(querychargeinfo.getUnpaid());
			    int inum = chargemapper.doChargeLog(chargeinfo);
			    if(inum==1){
			    	result = "ok";
			    }
			} else {
				result = "更新已收未付失败！";
			}
		} catch (Exception e) {
			result = e.getMessage();
			LogUtil.getLog().error("ChargeManaImpl.doCharge:\n"+result);
			throw new RuntimeException(result);
		}
		return result;
	}


	public List<ChargeInfo> queryChargeLog(Map param) {
		return chargemapper.queryChargeLog(param);
	}
	
}
