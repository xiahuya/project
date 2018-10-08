package com.xiahu.web.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xiahu.domain.Customer;
import com.xiahu.service.CustomerService;
import com.xiahu.utils.PageBean;

@Controller("customerAction")
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
	@Resource(name = "customerService")
	private CustomerService cs;

	private Customer customer = new Customer();

	// 接收图片
	private File photo;

	// 获取图片的完整名称
	private String photoFileName;

	// 在提交键名后加上固定后缀ContentType,文件MIME类型会自动封装到属性中
	private String photoContextType;

	// 根据行业获取客户数量
	public String getIndustryCount() throws Exception {
		// 调用service层,执行方法
		List<Object[]> list = cs.getIndustryCount();
		// 发送到request
		ActionContext.getContext().put("industryCount", list);
		return "industryCountList";

	}

	public void setCs(CustomerService cs) {
		this.cs = cs;
	}

	private Integer currentPage;
	private Integer pageSize;

	public String list() throws Exception {
		// 1.封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		// 判断并封装参数
		if (StringUtils.isNotBlank(customer.getCust_name())) {
			dc.add(Restrictions.like("cust_name", "%" + customer.getCust_name() + "%"));
		}

		// 1 调用Service查询分页数据(PageBean)
		PageBean pb = cs.getPageBean(dc, currentPage, pageSize);
		// 2 将PageBean放入request域,转发到列表页面显示
		ActionContext.getContext().put("pageBean", pb);
		return "list";

	}

	// 修改客户信息
	public String edit() throws Exception {
		// 获取cust_id,调用service操作dao
		Customer c = cs.getById(customer.getCust_id());
		// 转发到request域
		ActionContext.getContext().put("customer", c);
		return "edit";
	}

	public String add() throws Exception {
//		if (photo != null) {
//			System.out.println(photoContextType);
//			System.out.println(photoFileName);
//
//			// 将上传文件保存到指定位置
//			photo.renameTo(new File("D:/upload/" + photoFileName + ""));
//		}

		cs.save(customer);
		return "toList";
	}

	@Override
	public Customer getModel() {

		return customer;
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

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getPhotoContextType() {
		return photoContextType;
	}

	public void setPhotoContextType(String photoContextType) {
		this.photoContextType = photoContextType;
	}

}
