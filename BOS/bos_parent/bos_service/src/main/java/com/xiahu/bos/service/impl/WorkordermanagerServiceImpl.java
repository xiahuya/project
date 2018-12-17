package com.xiahu.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.dao.IWorkordermanagerDao;
import com.xiahu.bos.domain.Workordermanage;
import com.xiahu.bos.service.IWorkordermanagerService;

@Service
@Transactional
public class WorkordermanagerServiceImpl implements IWorkordermanagerService {
	@Autowired
	private IWorkordermanagerDao WorkordermanagerDao;

	public void save(Workordermanage model) {
		WorkordermanagerDao.save(model);
	}
	

}
