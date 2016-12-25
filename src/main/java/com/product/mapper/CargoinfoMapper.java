package com.product.mapper;

import java.util.List;
import java.util.Map;

import com.product.base.BaseMapper;
import com.product.entity.Cargoinfo;

/**
 * 货物信息mapper
 * @author wzq
 *
 */
public interface CargoinfoMapper extends BaseMapper{
public  List<Cargoinfo> getCargoinfo(Map params);

public void addcargo(Cargoinfo cargoinfo);

public Integer isrepeat(Map addcargoname);

public Cargoinfo getOneCargo(Map params);

public void saveEditCargo(Cargoinfo cargoinfo);
}
