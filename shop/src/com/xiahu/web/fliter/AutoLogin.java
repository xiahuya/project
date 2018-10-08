package com.xiahu.web.fliter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xiahu.domain.User;
import com.xiahu.service.UserService;

import sun.awt.RequestFocusController;

public class AutoLogin implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 强转一下
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		// 获取cookie中的username,password
		String cookie_username = null;
		String cookie_password = null;
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			// 遍历cookie
			for (Cookie cookie : cookies) {
				// 获取cookie_username
				if ("cookie_username".equals(cookie.getName())) {
					cookie_username = cookie.getValue();
				}
				// 获取cookie_password
				if ("cookie_password".equals(cookie.getName())) {
					cookie_password = cookie.getValue();
				}
			}
		}

		// 判断cookie_username,cookie_password是否为空
		if (cookie_username != null && cookie_password != null) {
			// 执行登录操作
			// 将数据传递到service层
			UserService service = new UserService();
			User user = null;
			try {
				user = service.login(cookie_username, cookie_password);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// 将user添加到session域,以便供其他调用
			session.setAttribute("user", user);
		}

		// 放行
		chain.doFilter(req, resp);

	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
