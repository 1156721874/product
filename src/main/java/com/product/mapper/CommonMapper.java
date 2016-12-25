package com.product.mapper;

import java.util.List;
import java.util.Map;

import com.product.bean.common.DictItem;

public interface CommonMapper {
String getChart(String key);

String getCount(Map params);

List<DictItem> getDictItemByGroupId(String groupid);

List<DictItem> getDictItemByGidAndIid(Map param);

}
