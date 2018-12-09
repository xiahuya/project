package com.xiahu.bos.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.domain.Role;
import com.xiahu.bos.service.IRoleService;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	// 属性驱动
	private String functionIds;
	@Autowired
	private IRoleService roleService;

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	/*
	 * 添加角色
	 */
	public String add() {
		roleService.save(model, functionIds);
		return LIST;
	}

	/*
	 * 分页查询
	 */
	public String pageQurty() {
		roleService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "functions", "users" });
		return NONE;
	}

	/*
	 * 获取全部角色信息
	 */
	public String ajaxList() {
		List<Role> list = roleService.findAll();
		this.java2Json(list, new String[] { "functions", "users" });
		return NONE;
	}
}
