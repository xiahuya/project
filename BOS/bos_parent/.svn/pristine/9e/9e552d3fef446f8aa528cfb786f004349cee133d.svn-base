package com.xiahu.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.dao.ISubareaDao;
import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Subarea;
import com.xiahu.bos.service.ISubareaService;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Autowired
	private ISubareaDao subareaDao;

	public void save(Subarea model) {
		subareaDao.save(model);
	}

	public void getPageBean(PageBean pageBean) {
		subareaDao.getPageBean(pageBean);
	}

	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	/*
	 * 获取没有被取派员分配的分区数据
	 */
	public List<Subarea> findSubareaNotDecidedzone() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		subareaDao.findByCriteria(detachedCriteria);
		return subareaDao.findByCriteria(detachedCriteria);
	}

	/*
	 * 根据定区ID获取相关联的分区信息
	 */
	public List<Subarea> getSubareaListByDecidedzoneId(String decidedzoneId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		List<Subarea> list = subareaDao.findByCriteria(detachedCriteria);
		return list;
	}

	/*
	 * 查询区域分区分布图数据
	 */
	public List<Object> findSubareaGroupByProvince() {
		return subareaDao.findSubareaGroupByProvince();
	}

	@Override
	public Subarea findById(String id) {
		return subareaDao.findById(id);
	}

	// 更新
	public void update(Subarea subarea) {
		subareaDao.saveOrUpdate(subarea);
	}

	// 批量删除
	public void deleteBatch(String ids) {
		// 分解字符串
		if (StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for (String id : split) {
				subareaDao.executeUpdate("subarea.delete", id);
			}
		}
	}

	// 导入分区数据
	public void saveBatch(List<Subarea> subareaList) {
		for (Subarea subarea : subareaList) {
			subareaDao.saveOrUpdate(subarea);
		}
	}

}
