package com.xiahu.bos.dao;

import java.util.List;

import com.xiahu.bos.dao.base.IBaseDao;
import com.xiahu.bos.domain.Subarea;

public interface ISubareaDao extends IBaseDao<Subarea> {

	List<Object> findSubareaGroupByProvince();

}
