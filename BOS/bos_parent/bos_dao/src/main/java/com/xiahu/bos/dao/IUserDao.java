package com.xiahu.bos.dao;

import com.xiahu.bos.dao.base.IBaseDao;
import com.xiahu.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	public User findUserByUsernameAndPassword(String username, String password);

	public User getUserByUsername(String username);


}
