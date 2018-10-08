package com.xiahu.dao.impl;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.xiahu.dao.SaleVisitDao;
import com.xiahu.domain.SaleVisit;

@Repository("saleVisitDao")
public class SaleVisitDaoImpl extends BaseDaoImpl<SaleVisit> implements SaleVisitDao {
	@Resource(name = "sessionFactory")
	public void setSF(SessionFactory sf) {
		super.setSessionFactory(sf);
	}
}
