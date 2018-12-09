package com.xiahu.bos.service;

import java.util.List;

import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Role;

public interface IRoleService {

	public  void save(Role model, String functionIds);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
