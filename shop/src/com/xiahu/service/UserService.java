package com.xiahu.service;

import java.sql.SQLException;

import com.xiahu.dao.UserDao;
import com.xiahu.domain.User;

public class UserService {

	// 注册
	public boolean register(User user) {
		// 传输数据到dao层
		UserDao dao = new UserDao();
		int row = 0;
		try {
			row = dao.register(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return row > 0 ? true : false;
	}

	// 激活
	public void activeCode(String activeCode) {
		// 传递到dao层
		UserDao dao = new UserDao();
		try {
			dao.activeCode(activeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 校验用户名是否存在
	public boolean checkUsername(String username) {
		// 直接传递到dao层
		UserDao dao = new UserDao();
		Long row = 0L;
		try {
			row = dao.checkUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return row > 0 ? true : false;
	}

	//用户登录
	public User login(String username, String password) throws SQLException {
		//没有什么复杂的业务，直接传递
		UserDao dao = new UserDao();
		return dao.login(username,password); 
	}

}
