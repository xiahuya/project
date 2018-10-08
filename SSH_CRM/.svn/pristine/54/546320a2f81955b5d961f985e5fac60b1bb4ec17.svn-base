package com.xiahu.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xiahu.dao.BaseDictDao;
import com.xiahu.domain.BaseDict;

@Repository("baseDictDao")
public class BaseDictDaoImpl extends BaseDaoImpl<BaseDict> implements BaseDictDao {
	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sf) {
		super.setSessionFactory(sf);
	}

	@Override
	public List<BaseDict> getListByTypeCode(String dict_type_code) {
		// 1.封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(BaseDict.class);
		// 2.添加条件
		dc.add(Restrictions.eq("dict_type_code", dict_type_code));
		// 3.查询
		List<BaseDict> list = (List<BaseDict>) getHibernateTemplate().findByCriteria(dc);
		return list;
	}

}
