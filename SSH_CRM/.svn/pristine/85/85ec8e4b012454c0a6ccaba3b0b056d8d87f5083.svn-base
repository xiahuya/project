package com.xiahu.web.action;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xiahu.domain.SaleVisit;
import com.xiahu.domain.User;
import com.xiahu.service.SaleVisitService;
import com.xiahu.utils.PageBean;


@Controller("saleVisitAction")
@Scope("prototype")
public class SaleVisitAction extends ActionSupport implements ModelDriven<SaleVisit> {
	private SaleVisit saleVisit = new SaleVisit();
	@Resource(name = "saleVisitService")
	private SaleVisitService svs;

	// 去往编辑页面回显
	public String toEdit() throws Exception {
		// 1 调用Service根据id查询客户拜访对象
		SaleVisit sv = svs.getById(saleVisit.getVisit_id());
		// 2 将对象放入request域
		ActionContext.getContext().put("saleVisit", sv);
		// 3 转发到add.jsp
		return "add";
	}

	public String add() throws Exception {
		// 1.从session中获取当前登陆的用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		// 2.将获取的user封装到SaleVisit对象
		saleVisit.setUser(user);
		// 3.调用service层操作dao
		svs.save(saleVisit);
		return "toList";
	}

	private Integer currentPage;
	private Integer pageSize;

	public String list() throws Exception {
		// 1.封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(SaleVisit.class);
		// // 判断并封装参数
		if (saleVisit.getCustomer() != null && saleVisit.getCustomer().getCust_id() != null) {
			dc.add(Restrictions.eq("customer.cust_id", saleVisit.getCustomer().getCust_id()));
		}

		// 1 调用Service查询分页数据(PageBean)
		PageBean pb = svs.getPageBean(dc, currentPage, pageSize);
		// 2 将PageBean放入request域,转发到列表页面显示
		ActionContext.getContext().put("pageBean", pb);
		return "list";

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

	@Override
	public SaleVisit getModel() {
		return saleVisit;
	}

	public void setSvs(SaleVisitService svs) {
		this.svs = svs;
	}

}
