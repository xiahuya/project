package com.xiahu.bos.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.domain.Region;
import com.xiahu.bos.domain.Staff;
import com.xiahu.bos.service.IStaffService;

/*
 * 取派员管理
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {

	@Autowired
	private IStaffService staffService;

	/*
	 * 添加取派员
	 */
	public String addStaff() {
		staffService.save(model);
		return LIST;
	}

	/*
	 * 获取已经被删除的取派员(逻辑上被删除)
	 */
	public String ajaxList() {
		List<Staff> list = staffService.getStaffWasDelete();
		this.java2Json(list, new String[] { "decidedzones" });
		return NONE;
	}

	// 属性驱动
	private String[] roleIds;

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	/*
	 * 还原取派员(更新)
	 */
	public String updateStaff() {
		staffService.updateStaff(roleIds);
		return LIST;
	}

	private int pages;
	private int rows;

	/*
	 * 分页查询方法
	 */
	// public String pageQuery() throws IOException {
	//
	// staffService.getPageBean(pageBean);
	// this.java2Json(pageBean, new String[] { "currentPage", "pageSize",
	// "detachedCriteria", "decidedzones", });
	// return NONE;
	// }

	public String pageQuery() throws IOException {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		// 动态添加过滤条件
		String name = model.getName();
		String telephone = model.getTelephone();
		if (StringUtils.isNotBlank(name)) {
			// 添加过滤条件，根据地址关键字模糊查询
			dc.add(Restrictions.like("name", "%" + name + "%"));
		}

		if (StringUtils.isNotBlank(telephone)) {
			// 添加过滤条件，根据地址关键字模糊查询
			dc.add(Restrictions.like("telephone", "%" + telephone + "%"));
		}

		staffService.getPageBean(pageBean);
		this.java2Json(pageBean, new String[] { "currentPage", "pageSize", "detachedCriteria", "decidedzones" });
		return NONE;
	}

	private String ids;

	/*
	 * 批量删除取派员
	 */

	@RequiresPermissions("staff-delete")
	public String deleteBatch() {
		staffService.deleteBatch(ids);
		return LIST;
	}

	/*
	 * 修改取派员信息
	 */
	@RequiresPermissions("staff-edit")
	public String editStaff() {
		Staff staff = staffService.findById(model.getId());
		// 使用页面提交的数据进行覆盖
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return LIST;
	}

	/*
	 * 获取没有分配分区的取派员
	 */
	public String getStaffAjaxList() {
		List<Staff> list = staffService.FindStaffNotSubarea();
		this.java2Json(list, new String[] { "decidedzones", "standard", "station", "deltag" });

		return NONE;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
