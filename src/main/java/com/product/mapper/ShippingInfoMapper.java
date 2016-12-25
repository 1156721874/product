package com.product.mapper;

import java.util.List;
import java.util.Map;

import com.product.base.BaseMapper;
import com.product.entity.CountOrgInfo;
import com.product.entity.ShippingInfo;
/**
 * 公司mapper
 * @author wzq
 *
 */
public interface ShippingInfoMapper extends BaseMapper {

	List<ShippingInfo> getAllOrgs();
    List<ShippingInfo> fuzzyQueryByName(Map param);
	public  List<ShippingInfo> getshipinfo(Map params);
	public Integer isrepeat(Map addorgname);
	public void addship(ShippingInfo orginfo);
	public ShippingInfo getOneShip(Map params);
	public void saveEditShip(ShippingInfo orginfo);
}
