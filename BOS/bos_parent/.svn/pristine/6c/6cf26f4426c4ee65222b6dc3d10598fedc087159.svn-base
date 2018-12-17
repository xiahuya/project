package com.xiahu.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.dao.IWorkbillDao;
import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Workbill;
import com.xiahu.bos.service.IWorkbillService;

@Service
@Transactional
public class WorkbillServiceImpl implements IWorkbillService {
	@Autowired
	private IWorkbillDao workbillDao;

	/*
	 * 分页
	 */
	public void getPageBean(PageBean pageBean) {
		workbillDao.getPageBean(pageBean);
	}

	
	/*
	 * 追单
	 */
	//工单类型:新、追、改、销
	public void zhuidan(String ids) {
		String[] split = ids.split(",");
		for (String string : split) {
			//根据ID获取工单
			Workbill workbill = workbillDao.findById(string);
			workbill.setType(Workbill.TYPE_2);
			workbillDao.saveOrUpdate(workbill);
		}
		
	}


	@Override
	public void xiaodan(String ids) {
		String[] split = ids.split(",");
		for (String string : split) {
			//根据ID获取工单
			Workbill workbill = workbillDao.findById(string);
			workbill.setType(Workbill.TYPE_4);
			workbillDao.saveOrUpdate(workbill);
		}
		
		
	}

}
