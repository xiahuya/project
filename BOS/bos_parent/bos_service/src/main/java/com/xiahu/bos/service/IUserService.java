package com.xiahu.bos.service;

import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.User;

public interface IUserService {

	public User login(User model);

	public void editPassword(String id, String password);

	//添加用户
	public void save(User model, String[] roleIds);

	public void pageQuery(PageBean pageBean);

}
