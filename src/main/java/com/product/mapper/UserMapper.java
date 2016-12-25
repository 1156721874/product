package com.product.mapper;

import java.util.Map;
import java.util.Set;

import com.product.base.BaseMapper;
import com.product.entity.User;


public interface UserMapper extends BaseMapper {

	User querySingleUser(User user);
	int addUser(User user);
	Integer  isRepeatUser(User user);
	Set<String> getUserMenuids(Map param);
	int updatepassword(User user);
}
