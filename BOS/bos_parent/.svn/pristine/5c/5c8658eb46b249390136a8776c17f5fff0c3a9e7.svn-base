package com.xiahu.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.dao.IUserDao;
import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Role;
import com.xiahu.bos.domain.User;
import com.xiahu.bos.service.IUserService;
import com.xiahu.bos.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;

	/***
	 * 用户登录
	 */
	public User login(User user) {
		// 使用MD5加密密码
		String password = MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUsername(), password);
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/*
	 * 用户修改密码
	 */
	public void editPassword(String id, String password) {
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", password, id);

	}

	/*
	 * 添加用户,同时关联角色
	 */
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);
		if (roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				// 手动构造托管对象
				Role role = new Role(roleId);
				// 用户对象关联角色对象
				user.getRoles().add(role);
			}
		}
	}

	/*
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		userDao.getPageBean(pageBean);
	}

	/*
	 * 删除
	 */
	public void deleteBatch(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
			userDao.executeUpdate("user.delete", id);
		}
	}

	@Override
	public User findById(String id) {
		return userDao.findById(id);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

}