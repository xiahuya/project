package com.xiahu.bos.action;

import java.io.IOException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.domain.Workordermanage;
import com.xiahu.bos.service.IWorkordermanagerService;

@Controller
@Scope("prototype")
public class WorkordermanagerAction extends BaseAction<Workordermanage> {
	@Autowired
	private IWorkordermanagerService workordermanagerService;

	@RequiresPermissions("workordermanager-add")
	public String add() throws IOException {
		String flag = "1";
		try {
			workordermanagerService.save(model);

		} catch (Exception e) {
			e.printStackTrace();
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);

		return NONE;
	}

}
