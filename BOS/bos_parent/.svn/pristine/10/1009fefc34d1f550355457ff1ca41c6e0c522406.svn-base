package com.xiahu.bos.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.xiahu.bos.dao.IRegionDao;
import com.xiahu.bos.dao.base.impl.BaseDaoImpl;
import com.xiahu.bos.domain.Region;

/*
 * 区域管理
 */
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	public List<Region> findAllByQ(String q) {
		String hql = "FROM Region r WHERE r.shortcode LIKE ? " + "	OR r.citycode LIKE ? OR r.province LIKE ? "
				+ "OR r.city LIKE ? OR r.district LIKE ?";
		List<Region> list = (List<Region>) this.getHibernateTemplate().find(hql, "%" + q + "%", "%" + q + "%",
				"%" + q + "%", "%" + q + "%", "%" + q + "%");
		return list;
	}

}
