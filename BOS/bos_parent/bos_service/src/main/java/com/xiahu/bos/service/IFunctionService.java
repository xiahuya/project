package com.xiahu.bos.service;

import java.util.List;

import com.xiahu.bos.domain.Function;
import com.xiahu.bos.domain.PageBean;

public interface IFunctionService {

	public List<Function> findAll();

	public void save(Function model);

	public void pageQuery(PageBean pageBean);

	public List<Function> findMenu();

	public void deleteBatch(String ids);

	public Function findById(String id);

	public void update(Function function);

}
