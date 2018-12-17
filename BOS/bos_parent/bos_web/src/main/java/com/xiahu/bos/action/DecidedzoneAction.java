package com.xiahu.bos.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.Customer;
import com.xiahu.bos.ICustomerService;
import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.domain.Decidedzone;
import com.xiahu.bos.service.IDecidedzoneService;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	// 属性驱动，接收多个分区id
	private String[] subareaid;

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	@Autowired
	private IDecidedzoneService decidedzoneService;

	/*
	 * 添加定区信息
	 */
	@RequiresPermissions("decidedzone-add")
	public String save() {
		decidedzoneService.save(model, subareaid);

		return LIST;
	}

	/*
	 * 分页
	 */
	@RequiresPermissions("decidedzone-list")
	public String pageQuery() {
		decidedzoneService.getPageBean(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","subareas","decidedzones"});
		return NONE;
	}
	
	//代理对象
	@Autowired
	private ICustomerService proxy;
	
	//尚未分配的客户列表
	public String findListNotAssociationResponse(){
		List<Customer> list = proxy.findListNotAssociation();
		this.java2Json(list, new String[]{});
		return NONE;
	}
	
	//已经被分配的客户列表
	public String findListHasAssociation(){
		List<Customer> list = proxy.findListHasAssociation(model.getId());
		this.java2Json(list, new String[]{});
		return NONE;
	}
	
	//属性驱动获取客户编号
	private List<Integer> customerIds;
	
	
	/**
	 * 远程调用crm服务，将客户关联到定区
	 */
	public String assigncustomerstodecidedzone(){
		proxy.assigncustomerstodecidedzone(model.getId(),customerIds);
		return LIST;
	}

	public List<Integer> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
	
	

	
	
	
}
