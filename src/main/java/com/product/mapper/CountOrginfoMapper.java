package com.product.mapper;

import java.util.List;
import java.util.Map;

import com.product.base.BaseMapper;
import com.product.entity.Cargoinfo;
import com.product.entity.CountOrgInfo;
import com.product.entity.ShippingInfo;

/**
 * 计数单位信息mapper
 * @author xl
 *
 */
public interface CountOrginfoMapper extends BaseMapper{
List<CountOrgInfo> getAllOrginfo();
public  List<CountOrgInfo> getorginfo(Map params);


public Integer isrepeat(Map addcargoname);

public CountOrgInfo getOneCargo(Map params);

public void saveEditCargo(CountOrgInfo cargoinfo);
void addcountOrgInfo(CountOrgInfo countOrgInfo);
}
