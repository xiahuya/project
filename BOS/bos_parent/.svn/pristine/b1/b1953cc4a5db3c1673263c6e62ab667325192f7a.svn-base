package com.xiahu.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.dao.IWorkordermanagerDao;
import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Workordermanage;
import com.xiahu.bos.service.IWorkorderImportService;

@Service
@Transactional
public class WorkorderImportServiceImpl implements IWorkorderImportService {
	@Autowired
	private IWorkordermanagerDao workordermanagerDao;

	@Override
	public void getPageBean(PageBean pageBean) {
		workordermanagerDao.getPageBean(pageBean);
	}

	@Override
	public void saveBatch(List<Workordermanage> list) {
		for (Workordermanage workordermanage : list) {
			workordermanagerDao.save(workordermanage);
		}
	}
}
