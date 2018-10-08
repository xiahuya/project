package com.xiahu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.dao.BaseDictDao;
import com.xiahu.domain.BaseDict;
import com.xiahu.service.BaseDictService;
@Service("baseDictService")
@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
public class BaseDictServiceImpl implements BaseDictService {
	@Resource(name="baseDictDao")
	private BaseDictDao baseDictDao;

	@Override
	public List<BaseDict> getListByTypeCode(String dict_type_code) {
		// 调用dao层从数据库操作
		List<BaseDict> list = baseDictDao.getListByTypeCode(dict_type_code);

		return list;
	}

	public void setBaseDictDao(BaseDictDao baseDictDao) {
		this.baseDictDao = baseDictDao;
	}



}
