package com.xiahu.bos.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.domain.Function;
import com.xiahu.bos.service.IFunctionService;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {

	@Autowired
	private IFunctionService functionService;

	/*
	 * 获取所有权限
	 */
	public String ajaxList() {
		List<Function> list = functionService.findAll();
		this.java2Json(list, new String[] { "parentFunction", "roles" });
		return NONE;
	}

	/*
	 * 添加权限
	 */
	public String add() {
		functionService.save(model);
		return LIST;
	}

	/*
	 * 分页查询
	 */
	public String pageQuery() {
		String page = model.getPage();
		System.out.println(page);
		pageBean.setCurrentPage(Integer.parseInt(page));

		functionService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "parentFunction", "roles", "children" });
		return NONE;

	}

	/*
	 * 获取所有菜单
	 */
	public String findMenu() {
		List<Function> list = functionService.findMenu();
		this.java2Json(list, new String[] { "parentFunction", "roles", "children" });
		return NONE;
	}

	/*
	 * 删除权限
	 */
	private String ids;

	public String deleteBatch() {
		functionService.deleteBatch(ids);
		return LIST;
	}

	/*
	 * 修改权限
	 */
	public String editFunction() {
		Function function = functionService.findById(model.getId());
		function.setParentFunction(model.getParentFunction());
		function.setName(model.getName());
		function.setCode(model.getCode());
		function.setDescription(model.getDescription());
		function.setPage(model.getPage());
		function.setGeneratemenu(model.getGeneratemenu());
		function.setZindex(model.getZindex());
		functionService.update(function);
		return LIST;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
