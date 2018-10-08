package com.xiahu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiahu.dao.CustomerDao;
import com.xiahu.domain.Customer;
import com.xiahu.service.CustomerService;
import com.xiahu.utils.PageBean;
@Service("customerService")
@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
public class CustomerServiceImpl implements CustomerService {
	@Resource(name="customerDao")
	private CustomerDao cd;

	@Override
	// 按照行业统计客户数量
	public List<Object[]> getIndustryCount() {
		List<Object[]> list = cd.getIndustryCount();
		return list;
	}

	@Override
	public PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize) {
		// 1 调用Dao查询总记录数
		Integer totalCount = cd.getTotalCount(dc);
		// 2 创建PageBean对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		// 3 调用Dao查询分页列表数据

		List<Customer> list = cd.getPageList(dc, pb.getStart(), pb.getPageSize());
		// 4 列表数据放入pageBean中.并返回
		pb.setList(list);
		return pb;
	}

	public void setCd(CustomerDao cd) {
		this.cd = cd;
	}

	@Override
	public void save(Customer customer) {
		// 1 维护Customer与数据字典对象的关系,由于struts2参数封装,会将参数封装到数据字典的id属性.
		// 那么我们无需手动维护关系
		// 2.调用dao保存customer
		cd.saveOrUpdate(customer);

	}

	@Override
	public Customer getById(Long cust_id) {
		Customer customer = cd.getById(cust_id);
		return customer;
	}

}
