package com.xiahu.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.dao.IRoleDao;
import com.xiahu.bos.domain.Function;
import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.Role;
import com.xiahu.bos.service.IRoleService;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private IRoleDao roleDao;

	/*
	 * 添加角色,同时还需要关联权限
	 */
	public void save(Role model, String functionIds) {
		roleDao.save(model);
		if (StringUtils.isNotBlank(functionIds)) {
			String[] split = functionIds.split(",");
			for (String functionId : split) {
				//手动构造一个权限对象，设置id，对象状态为托管状态
				Function function = new Function(functionId);
				//角色关联权限
				model.getFunctions().add(function);
			}
		}
	}

	/*
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		roleDao.getPageBean(pageBean);
	}

	public List<Role> findAll() {
		return roleDao.findAll();
	}

}
