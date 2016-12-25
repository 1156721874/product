package com.product.mapper;

import java.util.List;
import java.util.Map;

import com.product.bean.common.DictItem;
import com.product.entity.User;

public interface PrivilegeMapper {
public List<User> getAllUserList();

public User queryUserALlprivilege(String userid);

public List<DictItem> getRoles();

public Integer isRepeatUser(User user);

public int addUser(User user);

public int  saveEditUser(User user);

public int deleteUser(Map ids);

}
