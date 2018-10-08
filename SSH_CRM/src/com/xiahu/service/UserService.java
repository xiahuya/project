package com.xiahu.service;

import org.hibernate.criterion.DetachedCriteria;

import com.xiahu.domain.User;
import com.xiahu.utils.PageBean;

public interface UserService {
	User getUserByCode(User u);

	void save(User u);

	void saveUser(User user);

	PageBean getPageBean(DetachedCriteria dc, int i, int j);
}
