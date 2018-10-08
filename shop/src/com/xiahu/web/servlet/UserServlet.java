package com.xiahu.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.xiahu.domain.User;
import com.xiahu.service.UserService;
import com.xiahu.utils.CommonsUtils;
import com.xiahu.utils.MailUtils;

public class UserServlet extends BaseServlet {

	/**
	 * 用户注销的方法
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		//从session中将user删除
		session.removeAttribute("user");
	
		
		Cookie cookie_username = new Cookie("cookie_username", "");
		Cookie cookie_password = new Cookie("cookie_password", "");
		// 设置cookie持久化事件
		cookie_username.setMaxAge(0);
		cookie_password.setMaxAge(0);
		// 设置携带访问路径-----整个WEB应用
		cookie_username.setPath(request.getContextPath());
		cookie_password.setPath(request.getContextPath());
		// 发布cookie
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
		
			
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		
		
	}

	/**
	 * 用户注册激活的方法
	 */
	public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String activeCode = request.getParameter("activeCode");

		// 写入Service层
		UserService service = new UserService();
		service.activeCode(activeCode);

		// 跳转到登录界面
		request.getRequestDispatcher("/login.jsp").forward(request, response);

	}

	/**
	 * 检验用户名是否已经存在
	 */
	public void checkUsername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取数据
		String username = request.getParameter("username");

		// 传递到service层
		UserService service = new UserService();
		boolean isExist = service.checkUsername(username);

		// 将参数传递会JSP页面
		String json = "{\"isExist\":" + isExist + "}";
		response.getWriter().write(json);
	}

	/**
	 * 用户注册
	 */
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置编码
		request.setCharacterEncoding("UTF-8");

		// 获取表单内容
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			// 指定一个类型转换器,将String ---->Date
			ConvertUtils.register(new Converter() {

				@Override
				public Object convert(Class clazz, Object value) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class);

			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		// 手动封装表单中没有的数据
		// private String uid;
		user.setUid(CommonsUtils.getUUID());

		// private String telephone;
		user.setTelephone(null);

		// private int state;
		user.setState(0);

		// private String code;
		String activeCode = CommonsUtils.getUUID();
		user.setCode(activeCode);

		// 传输数据到service层
		UserService service = new UserService();
		boolean isRegisterSuccess = service.register(user);

		if (isRegisterSuccess) {
			// 注册成功
			// 发送邮件
			String emailMsg = "恭喜你注册成功,请点击下面链接进行激活<a href='http://localhost:8080/shop/user?method=active&activeCode="
					+ activeCode + "'>" + "http://localhost:8080/shop/user?method=active&activeCode=" + activeCode
					+ "</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			// 跳转到注册成功页面
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		} else {
			// 注册失败
			// 跳转到注册失败页面
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}
	}

	/**
	 * 用户登录
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置编码模式
		request.setCharacterEncoding("UTF-8");

		String checkCode = request.getParameter("checkCode");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		/**
		 * 校验验证码
		 */

		// 从session中获取验证码的原本内容
		HttpSession session = request.getSession();
		String text = (String) session.getAttribute("checkcode_session");

		// 将二者做个比较
		if (!text.equals(checkCode)) {
			// 校验失败
			request.setAttribute("Info", "您的验证码不正确");
			// 转发到登录界面
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		} else {
			// 验证码校验成功
			// 开始校验用户名跟密码
			UserService service = new UserService();
			User user = null;
			try {
				user = service.login(username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (user != null) {
				// 登录成功
				/*
				 * 自动登录
				 */
				// 判断是否勾选登录成功checkbox
				String autologin = request.getParameter("autologin");
				if (autologin != null) {// 证明勾选了自动登录
					// 创建cookie存储username,password
					Cookie cookie_username = new Cookie("cookie_username", user.getUsername());
					Cookie cookie_password = new Cookie("cookie_password", user.getPassword());
					// 设置cookie持久化事件
					cookie_username.setMaxAge(60 * 60);
					cookie_password.setMaxAge(60 * 60);
					// 设置携带访问路径-----整个WEB应用
					cookie_username.setPath(request.getContextPath());
					cookie_password.setPath(request.getContextPath());
					// 发布cookie
					response.addCookie(cookie_username);
					response.addCookie(cookie_password);

				}
				// 将user添加到session域,以便供其他调用
				session.setAttribute("user", user);

				// 跳转到首页
				response.sendRedirect(request.getContextPath());
			} else {
				// 登录失败
				// 提示登录失败
				request.setAttribute("Info", "你的用户名或密码不正确");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}

		}
	}

}