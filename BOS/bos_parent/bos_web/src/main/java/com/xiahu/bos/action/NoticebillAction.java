package com.xiahu.bos.action;

import java.io.IOException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.Customer;
import com.xiahu.bos.ICustomerService;
import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.dao.IWorkbillDao;
import com.xiahu.bos.domain.Noticebill;
import com.xiahu.bos.service.INoticebillService;
import com.xiahu.bos.service.IWorkbillService;

/**
 * 用户通知单管理
 * 
 * @author 17650
 *
 */

@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
	// 注入crm客户端代理对象
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IWorkbillService workbillService;

	/**
	 * 远程调用crm服务，根据手机号查询客户信息
	 */
	public String findCustomerByTelephone() {
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelephone(telephone);
		this.java2Json(customer, new String[] {});
		return NONE;
	}

	@Autowired
	private INoticebillService noticebillService;

	/**
	 * 保存一个业务通知单，并尝试自动分单
	 */
	@RequiresPermissions("noticebill-add")
	public String add() {
		noticebillService.save(model);
		return "noticebill_add";
	}


}
