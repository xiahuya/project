package com.itheima.crm.service;

import java.util.List;

import javax.jws.WebService;

import com.itheima.crm.domain.Customer;

@WebService
public interface ICustomerService {
	public List<Customer> findAll();

	// 查询未关联到定区的客户
	public List<Customer> findListNotAssociation();

	// 查询已经关联到指定定区的客户
	public List<Customer> findListHasAssociation(String decidedzoneId);
	
	public void assigncustomerstodecidedzone(String decidedzoneId,Integer[] customerIds);
	
	//根据电话查询客户信息
	public Customer findCustomerByTelephone(String telephone);
	
	//根据客户地址查询定区ID
	public String findDecidedzoneIdByCustomerAddress(String address);
}
