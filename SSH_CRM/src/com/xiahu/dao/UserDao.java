package com.xiahu.dao;

import com.xiahu.domain.User;

public interface UserDao extends BaseDao<User> {
	User getUserByCode(String code);


}
