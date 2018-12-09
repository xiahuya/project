package com.xiahu.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiahu.bos.dao.ISubareaDao;
import com.xiahu.bos.dao.base.impl.BaseDaoImpl;
import com.xiahu.bos.domain.Subarea;

@Repository
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements ISubareaDao {

	/*
	 * 获取区域分区数据图
	 */
	public List<Object> findSubareaGroupByProvince() {
		String hql = "SELECT r.province,count(*) FROM Subarea s LEFT OUTER JOIN s.region r GROUP BY r.province";

		return (List<Object>) this.getHibernateTemplate().find(hql);
	}

}
