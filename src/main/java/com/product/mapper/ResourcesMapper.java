package com.product.mapper;

import java.util.List;

import com.product.base.BaseMapper;
import com.product.entity.ResFormMap;

/**
 * 菜单构建mapper
 * @author wzq
 *
 */
public interface ResourcesMapper extends BaseMapper {
	public List<ResFormMap> findChildlists(ResFormMap map);

	public List<ResFormMap> findRes(ResFormMap map);

	public void updateSortOrder(List<ResFormMap> map);
	
	public List<ResFormMap> findUserResourcess(String userId);
	
}
