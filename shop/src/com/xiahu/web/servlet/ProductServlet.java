package com.xiahu.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xiahu.domain.Category;
import com.xiahu.domain.PageBean;
import com.xiahu.domain.Product;
import com.xiahu.service.ProductService;
import com.xiahu.utils.JedisPoolUtil;

import redis.clients.jedis.Jedis;

public class ProductServlet extends BaseServlet {



	/**
	 * 显示商品的类别的的功能
	 */
	public void categoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 先从缓存中查询categoryList，如果有,就直接使用,如果没有，就从数据库中查询
		Jedis jedis = JedisPoolUtil.getJedis();
		String categoryListJson = jedis.get("categoryListJson");
		// 判断是否为空
		if (categoryListJson == null) {
			// 从数据库中查询
			ProductService service = new ProductService();
			List<Category> categoryList = service.findCategoryList();

			// 转换成json格式
			Gson gson = new Gson();
			categoryListJson = gson.toJson(categoryList);
			// 并且添加到redis数据库
			jedis.set("categoryListJson", categoryListJson);
		}

		// 设置字符码格式
		response.setContentType("text/html;charset=UTF-8");

		response.getWriter().write(categoryListJson);
	}

	/**
	 * 显示首页的功能 显示商品的类别的的功能
	 */
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 查询最热门商品
		ProductService service = new ProductService();
		List<Product> hotProductList = null;
		try {
			hotProductList = service.findHotProductlist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 查询最新商品
		List<Product> newProductList = null;
		try {
			newProductList = service.findNewProductlist();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// // 准备分类数据
		// List<Category> categoryList = service.findCategoryList();
		//
		// request.setAttribute("categoryList", categoryList);

		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);

		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * 商品详细信息显示功能
	 */
	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取cid
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage");

		// 获取pid
		String pid = request.getParameter("pid");

		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);

		request.setAttribute("product", product);
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);

		// 获取客户端携带的Cookie,名称是pids
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
					// 1-3-2 本次访问商品pid是8----->8-1-3-2
					// 1-3-2 本次访问商品pid是3----->3-1-2
					// 1-3-2 本次访问商品pid是2----->2-1-3
					// 将pids拆成一个数组
					String[] split = pids.split("-");
					// 将数组转成集合
					List<String> asList = Arrays.asList(split);
					// 将集合转成LinkedList集合
					LinkedList<String> list = new LinkedList<String>(asList);
					// 判断该LinkedList集合是否存在当前的pid
					if (list.contains(pid)) {
						// 存在当前的pid
						// 将当钱页面的pid删除
						list.remove(pid);
						list.addFirst(pid);
					} else {
						// 不存在当前页面的
						// 直接添加到集合
						list.addFirst(pid);
					}
					// 将[3,1,2]转成3-1-2字符串
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size() && i < 7; i++) {
						sb.append(list.get(i));
						sb.append("-");// 3-1-2-
					}
					// 去掉3-1-2-后的-
					pids = sb.substring(0, sb.length() - 1);

				}
			}
		}

		Cookie cookie_new = new Cookie("pids", pids);
		cookie_new.setMaxAge(60 * 60);
		cookie_new.setPath(request.getContextPath());
		response.addCookie(cookie_new);

		request.getRequestDispatcher("product_info.jsp").forward(request, response);
	}

	/**
	 * 根据商品类别获得商品列表
	 */
	public void productListByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 接收参数
		String cid = request.getParameter("cid");
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null) {
			currentPageStr = "1";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 12;

		// 传递数据到service层
		ProductService service = new ProductService();
		PageBean pageBean = null;
		try {
			pageBean = service.pageBean(cid, currentPage, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);

		// 定义一个记录历史商品信息的集合
		List<Product> historyProductList = new ArrayList<Product>();
		// 接收客户端传递过来的Cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for (String pid : split) {
						Product pro = service.findProductByPid(pid);
						historyProductList.add(pro);
					}
				}
			}
		}

		// 将历史记录的集合放到域中
		request.setAttribute("historyProductList", historyProductList);
		request.getRequestDispatcher("product_list.jsp").forward(request, response);
	}
}