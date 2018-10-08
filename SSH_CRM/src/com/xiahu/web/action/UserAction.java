package com.xiahu.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xiahu.domain.User;
import com.xiahu.service.UserService;


@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User> {

	private User user = new User();
	@Resource(name = "userService")
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String login() throws Exception {
		// 调用userService的方法
		User u = userService.getUserByCode(user);		
		// 将user对象放入session域
		ActionContext.getContext().getSession().put("user", u);
		return "toHome";

	}
	
	public String regist() throws Exception {
		//调用service保存用户
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			ActionContext.getContext().put("error", e.getMessage());
			System.out.println(e.getMessage());
			return "regist";
		}

		return "toLogin";
	}



	@Override
	public User getModel() {

		return user;
	}

}
