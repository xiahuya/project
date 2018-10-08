package com.xiahu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xiahu.dao.UserDao;
import com.xiahu.domain.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sf) {
		super.setSessionFactory(sf);
	}

	@Override
	public User getUserByCode(final String code) {

		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("user_code", code));
		List<?> criteria = getHibernateTemplate().findByCriteria(dc);
		if (criteria != null && criteria.size() > 0) {
			return (User) criteria.get(0);
		} else {
			return null;
		}


	}

}
