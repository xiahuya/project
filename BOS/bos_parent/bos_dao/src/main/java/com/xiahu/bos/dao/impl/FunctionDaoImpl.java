package com.xiahu.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.xiahu.bos.dao.IFunctionDao;
import com.xiahu.bos.dao.base.impl.BaseDaoImpl;
import com.xiahu.bos.domain.Function;
import com.xiahu.bos.domain.PageBean;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao {
	public List<Function> findAll() {
		String hql = "FROM Function f WHERE f.parentFunction IS NULL";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/*
	 * 根据用户ID查询权限
	 */
	public List<Function> findFunctionListByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles"
				+ " r LEFT OUTER JOIN r.users u WHERE u.id = ?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}

	/*
	 * 查找所有菜单
	 */
	public List<Function> findMenu() {
		String hql = "FROM Function f WHERE f.generatemenu = '1' ORDER BY f.zindex asc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/*
	 * 根据用户ID查找菜单
	 */
	public List<Function> findMenuByUserId(String id) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles"
				+ " r LEFT OUTER JOIN r.users u WHERE u.id = ? AND f.generatemenu = '1' "
				+ "ORDER BY f.zindex DESC";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, id);
		return list;
	}

}
