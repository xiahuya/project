package com.xiahu.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.xiahu.domain.Customer;
import com.xiahu.utils.PageBean;

public interface CustomerService {

	PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize);

	void save(Customer customer);

	Customer getById(Long cust_id);
	
	List<Object[]> getIndustryCount();


}
