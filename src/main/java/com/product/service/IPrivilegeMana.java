package com.product.service;

import java.util.List;

import com.product.bean.common.DictItem;
import com.product.entity.User;

public interface IPrivilegeMana {

	List<User> getAllUserList();

	User queryUserALlprivilege(String userid);

	String addUser(User user);

	List<DictItem> getRoles();

	String saveEditUser(User user);

	String deleteUser(String ids);

}
