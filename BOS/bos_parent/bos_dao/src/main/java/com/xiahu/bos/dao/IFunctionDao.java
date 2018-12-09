package com.xiahu.bos.dao;

import java.util.List;

import com.xiahu.bos.dao.base.IBaseDao;
import com.xiahu.bos.domain.Function;

public interface IFunctionDao extends IBaseDao<Function> {

	List<Function> findFunctionListByUserId(String id);

	List<Function> findMenu();

	List<Function> findMenuByUserId(String id);

}
