package com.xiahu.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xiahu.bos.domain.PageBean;

/**
 * 持久层通用接口
 * @author 17650
 *
 */
public interface IBaseDao<T> {
    public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public void saveOrUpdate(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
	public void executeUpdate(String query,Object...objects);
	public void getPageBean(PageBean pageBean);
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
}
