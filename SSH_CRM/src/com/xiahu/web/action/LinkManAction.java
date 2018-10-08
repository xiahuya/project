package com.xiahu.web.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xiahu.domain.LinkMan;
import com.xiahu.service.LinkManService;
import com.xiahu.utils.PageBean;

@Controller("linkManAction")
@Scope("prototype")
public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan> {
	private LinkMan linkMan = new LinkMan();
	@Resource(name = "linkManService")
	private LinkManService lms;

	private Integer currentPage;
	private Integer pageSize;

	// 用户列表
	public String list() throws Exception {
		// 1.封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(LinkMan.class);
		// 判断并封装参数
		if (StringUtils.isNotBlank(linkMan.getLkm_name())) {
			dc.add(Restrictions.like("lkm_name", "%" + linkMan.getLkm_name() + "%"));
		}

		if (linkMan.getCustomer() != null && linkMan.getCustomer().getCust_id() != null) {
			dc.add(Restrictions.eq("customer.cust_id", linkMan.getCustomer().getCust_id()));
		}

		// 1 调用Service查询分页数据(PageBean)
		PageBean pb = lms.getPageBean(dc, currentPage, pageSize);
		// 2 将PageBean放入request域,转发到列表页面显示
		ActionContext.getContext().put("pageBean", pb);
		return "list";

	}

	// 修改联系人信息
	public String edit() throws Exception {
		LinkMan lk = lms.getById(linkMan.getLkm_id());
		//返回给request域
		ActionContext.getContext().put("linkMan", lk);

		return "add";
	}

	// 添加客户
	public String addLinkMan() throws Exception {
		// 调用service操作dao
		lms.save(linkMan);

		return "toList";
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setLms(LinkManService lms) {
		this.lms = lms;
	}

	@Override
	public LinkMan getModel() {

		return linkMan;
	}

}
