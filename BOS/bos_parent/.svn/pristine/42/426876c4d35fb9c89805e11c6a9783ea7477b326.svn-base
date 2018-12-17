package com.xiahu.bos.action;

import java.io.IOException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.domain.Workbill;
import com.xiahu.bos.service.IWorkbillService;

/*
 * 工单操作Action
 */
@Controller
@Scope("prototype")
public class WorkbillAction extends BaseAction<Workbill>{
	
	@Autowired
	private IWorkbillService workbillService;
	/*
	 * 分页查询
	 */
	public String pageQuery() throws IOException {
		workbillService.getPageBean(pageBean);
		this.java2Json(pageBean, new String[] {"staff","noticebill"});
		return NONE;
	}
	
	/*
	 * 追单
	 */
	private String ids;
	@RequiresPermissions("noticebill-zhuidan")
	public String zhuidan(){
		workbillService.zhuidan(ids);
		return NONE;
		
	}
	
	/*
	 * 销单
	 */
	@RequiresPermissions("noticebill-xiaodan")
	public String xiaodan(){
		workbillService.xiaodan(ids);
		return NONE;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	

}
