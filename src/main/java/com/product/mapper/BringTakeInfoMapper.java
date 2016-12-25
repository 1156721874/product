package com.product.mapper;

import java.util.List;
import java.util.Map;

import com.product.base.BaseMapper;
import com.product.entity.BringTakeInfo;
import com.product.entity.Cargoinfo;

/**
 * 送取货人mapper
 * @author wzq
 *
 */
public interface BringTakeInfoMapper extends BaseMapper{
	List<BringTakeInfo> getAllBringTakeInfoPeople();
	public  List<BringTakeInfo> getBringtakeinfo(Map params);
	public void addBring(BringTakeInfo bringtakeinfo);
	public BringTakeInfo getOneBring(Map params);
	public void saveEditBring(BringTakeInfo bringtakeinfo);
	public Integer isrepeat(Map name);
	int saveBringtakeinfo(BringTakeInfo bringtakeinfo);
}
