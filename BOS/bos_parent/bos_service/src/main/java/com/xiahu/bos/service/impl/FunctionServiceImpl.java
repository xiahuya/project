package com.xiahu.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.bos.dao.IFunctionDao;
import com.xiahu.bos.domain.Function;
import com.xiahu.bos.domain.PageBean;
import com.xiahu.bos.domain.User;
import com.xiahu.bos.service.IFunctionService;
import com.xiahu.bos.utils.BOSUtils;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {

	@Autowired
	private IFunctionDao functionDao;

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	/*
	 * 添加一个权限
	 */
	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if (parentFunction != null && parentFunction.getId().equals("")) {
			model.setParentFunction(null);
		}
		functionDao.save(model);

	}

	/*
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		functionDao.getPageBean(pageBean);
	}

	/*
	 * 根据当前登录人查询对应的菜单数据，返回json
	 */
	public List<Function> findMenu() {
		List<Function> list = null;
		// 获取当前登陆对象
		User user = BOSUtils.getLoginUser();
		if (user.getUsername().equals("admin")) {
			// 如果是超级管理员,就查询所有权限
			list = functionDao.findMenu();
		} else {
			// 根据用户ID查找权限
			list = functionDao.findMenuByUserId(user.getId());
		}
		return list;
	}

	/*
	 * 删除权限
	 */
	public void deleteBatch(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
			functionDao.executeUpdate("function.delete", id);
		}
	}

	/*
	 * 根据ID查找权限
	 */
	public Function findById(String id) {
		return functionDao.findById(id);
	}

	@Override
	public void update(Function function) {
		functionDao.update(function);
	}

}
