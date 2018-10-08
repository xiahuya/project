package com.xiahu.web.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.xiahu.domain.User;

public class MyInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation interector) throws Exception {
		// 获取session对象
		Map<String, Object> session = ActionContext.getContext().getSession();
		// 从中获取user对象
		User user = (User) session.get("user");
		// 判断user是否为空
		if (user != null) {
			// ===>不为空---放心
			return interector.invoke();
		}else{
			// ===>空-----跳转
			return "toLogin";
						
		}

	}

}
