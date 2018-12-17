package com.xiahu.bos.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xiahu.bos.action.base.BaseAction;
import com.xiahu.bos.domain.Role;
import com.xiahu.bos.domain.User;
import com.xiahu.bos.service.IUserService;
import com.xiahu.bos.utils.BOSUtils;
import com.xiahu.bos.utils.MD5Utils;

@Controller
@Scope("prototype") // 多例
public class UserAction extends BaseAction<User> {


	@Autowired
	private IUserService userService;

	// 属性驱动，接收页面输入的验证码
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String getCheckcode() {
		return checkcode;
	}

	/**
	 * 用户登陆方法,使用shiro框架进行登陆认证
	 * 
	 * @return
	 */
	public String login() {
		// 从Session中获取生成的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		System.out.println(checkcode);
		// 校验验证码是否输入正确
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)) {
			// 使用shiro框架提供的方式进行认证操作
			// 1.获取当前用户对象.状态为-----"未登录"
			Subject subject = SecurityUtils.getSubject();
			// 创建用户名密码令牌认证对象
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),
					MD5Utils.md5(model.getPassword()));
			try {
				// 执行login方法,进入安全管理器中执行BOSRealm
				subject.login(token);
			} catch (IncorrectCredentialsException e) {
				e.printStackTrace();
				this.addActionError("用户名或密码错误！");
				return LOGIN;
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				this.addActionError("用户名不存在！");
				return LOGIN;
			} catch (Exception e) {
				e.printStackTrace();
				return LOGIN;
			}

			// 从安全管理器中找到该从BOSRealm中的user对象
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return HOME;

		} else {
			// 输入的验证码错误,设置提示信息，跳转到登录页面
			this.addActionError("输入的验证码错误！");
			return LOGIN;
		}
	}

	/**
	 * 用户登陆方法
	 * 
	 * @return
	 */
	public String login_xx() {
		// 从Session中获取生成的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		System.out.println(checkcode);
		// 校验验证码是否输入正确
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)) {
			// 输入的验证码正确
			User user = userService.login(model);
			if (user != null) {
				// 登录成功,将user对象放入session，跳转到首页
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return HOME;
			} else {
				// 登录失败，,设置提示信息，跳转到登录页面
				// 输入的验证码错误,设置提示信息，跳转到登录页面
				this.addActionError("用户名或者密码输入错误！");
				return LOGIN;
			}
		} else {
			// 输入的验证码错误,设置提示信息，跳转到登录页面
			this.addActionError("输入的验证码错误！");
			return LOGIN;
		}
	}

	/*
	 * 注销登陆
	 */
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}

	public String editPassword() throws IOException {
		String flag = "1";
		// 获取当前登录用户
		User user = BOSUtils.getLoginUser();
		try {
			userService.editPassword(user.getId(), model.getPassword());
		} catch (Exception e) {
			flag = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(flag);

		return NONE;
	}

	// 属性驱动
	private String[] roleIds;

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	/*
	 * 添加用户
	 */
	public String add() {
		userService.save(model, roleIds);
		return LIST;
	}

	/*
	 * 分页查询
	 */
	public String pageQuery() {
		userService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "noticebills", "roles" });
		return LIST;
	}

	// 删除用户
	private String ids;

	public String deleteBatch() {
		userService.deleteBatch(ids);
		return LIST;
	}

	/*
	 * 修改
	 */
	public String editUser() {
		User user = userService.findById(model.getId());
		user.setUsername(model.getUsername());
		user.setPassword(MD5Utils.md5(model.getPassword()));
		user.setSalary(model.getSalary());
		user.setBirthday(user.getBirthday());
		user.setGender(model.getGender());
		user.setStation(model.getStation());
		user.setTelephone(model.getTelephone());
		user.setRemark(model.getRemark());
		user.setRoles(model.getRoles());
		userService.update(user);
		return LIST;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
