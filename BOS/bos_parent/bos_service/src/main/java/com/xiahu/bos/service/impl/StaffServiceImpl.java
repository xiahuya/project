package com.xiahu.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.dao.IStaffDao;
import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Staff;
import com.xiahu.bos.service.IStaffService;

/**
 * 取派员管理
 * 
 * @author 17650
 *
 */
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	@Autowired
	private IStaffDao staffDao;

	public void save(Staff model) {
		staffDao.save(model);
	}

	public void getPageBean(PageBean pageBean) {
		staffDao.getPageBean(pageBean);
	}

	/*
	 * 批量删除取派员(逻辑删除,将deltag=0改成deltag=1)
	 */
	public void deleteBatch(String ids) {
		// 分解字符串
		if (StringUtils.isNotBlank(ids)) {
			String[] split = ids.split(",");
			for (String id : split) {
				staffDao.executeUpdate("staff.delete", id);
			}
		}

	}

	/*
	 * 根据id查询取派员
	 */
	public Staff findById(String id) {
		return staffDao.findById(id);
	}

	/*
	 * 更新取派员信息
	 */
	public void update(Staff staff) {
		staffDao.update(staff);
	}

	/*
	 * 获取没有分配分区的取派员
	 */
	public List<Staff> FindStaffNotSubarea() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.eq("deltag", "0"));

		return staffDao.findByCriteria(detachedCriteria);
	}

	// 获取已经被删除的取派员
	public List<Staff> getStaffWasDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.eq("deltag", "1"));

		return staffDao.findByCriteria(detachedCriteria);
	}

	// 更新取派员
	public void updateStaff(String[] roleTds) {
		if (roleTds != null && roleTds.length > 0) {
            for(String id:roleTds){
            	staffDao.executeUpdate("staff.update", id);
            }
		}
		

	}

}
