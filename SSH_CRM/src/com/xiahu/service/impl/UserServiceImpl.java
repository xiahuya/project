package com.xiahu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.dao.UserDao;
import com.xiahu.domain.User;
import com.xiahu.service.UserService;
import com.xiahu.utils.MD5Utils;
import com.xiahu.utils.PageBean;

@Service("userService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
public class UserServiceImpl implements UserService {
	@Resource(name = "userDao")
	private UserDao ud;

	@Override
	public User getUserByCode(User u) {
		// 1.从UserDao层获取use对象
		User exsitUser = ud.getUserByCode(u.getUser_code());
		// 2.判断users用户是否存在
		if (exsitUser == null) {
			throw new RuntimeException("用户名不存在");
		}
		// 3.判断user密码是否正确
		if (!exsitUser.getUser_password().equals(MD5Utils.md5(u.getUser_password()))) {
			throw new RuntimeException("密码错误！");
		}
		// 4.返回user
		return exsitUser;
	}

	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
	public void save(User u) {
		ud.save(u);

	}

	public void setUd(UserDao ud) {
		this.ud = ud;
	}

	@Override
	public void saveUser(User user) {
		// 1 调用Dao根据注册的登陆名获得用户对象
		User existUser = ud.getUserByCode(user.getUser_code());
		// 2判断返回值是否为空
		if (existUser != null) {
			// 证明用户名存在
			throw new RuntimeException("用户名已经存在");
		}
		// 使用MD5对密码进行加密
		user.setUser_password(MD5Utils.md5(user.getUser_password()));

		// 再数据库中添加该用户
		ud.save(user);

	}

	@Override
	public PageBean getPageBean(DetachedCriteria dc, int i, int j) {
		// 1 调用Dao查询总记录数
		Integer totalCount = ud.getTotalCount(dc);
		// 2 创建PageBean对象
		PageBean pb = new PageBean(i, totalCount, j);
		// 3 调用Dao查询分页列表数据

		List<User> list = ud.getPageList(dc, pb.getStart(), pb.getPageSize());
		// 4 列表数据放入pageBean中.并返回
		pb.setList(list);
		return pb;
	}

}
