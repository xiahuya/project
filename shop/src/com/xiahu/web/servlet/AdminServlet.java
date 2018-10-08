package com.xiahu.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xiahu.domain.Category;
import com.xiahu.domain.Order;
import com.xiahu.domain.Product;
import com.xiahu.service.AdminService;
import com.xiahu.utils.CommonsUtils;

public class AdminServlet extends BaseServlet {

	// ***************************************************************************************************
	/*
	 * Category 修改,删除,添加
	 */

	// 添加分类管理项
	public void addCategoryName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cname = request.getParameter("cname");

		// 用解耦合的方式进行编码----解web层与service层的耦合
		// 使用工厂+反射+配置文件
		AdminService service = new AdminService();

		Category category = new Category();
		category.setCid(CommonsUtils.getUUID());
		category.setCname(cname);
		service.addCategoryName(category);

		request.getRequestDispatcher("admin?method=showCategoryList").forward(request, response);

	}

	// 删除分类管理项
	public void deleteCategoryByCid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cid = request.getParameter("cid");
		AdminService service = new AdminService();
		service.deleteCategoryByCid(cid);

		request.getRequestDispatcher("admin?method=showCategoryList").forward(request, response);
	}

	// 修改分类管理名称
	public void updateCategoryName(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String cid = request.getParameter("cid");
		String newName = request.getParameter("cname");

		// 传递到service层
		AdminService service = new AdminService();
		service.updateCategoryName(cid, newName);

		request.getRequestDispatcher("admin?method=showCategoryList").forward(request, response);

	}

	// 编辑修改商品管理(后台管理)
	// 根据cid查询商品分类，并将其回显到jsp页面
	public void findCategoryNameByCid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取该商品的id
		String cid = request.getParameter("cid");

		AdminService service = new AdminService();
		Category category = service.findCategoryNameByCid(cid);

		request.setAttribute("category", category);

		request.getRequestDispatcher("admin/category/edit.jsp").forward(request, response);

	}

	// ***************************************************************************************************
	/*
	 * Order:
	 */

	// 订单详情
	public void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oid = request.getParameter("oid");

		Thread.sleep(3000);

		AdminService service = new AdminService();
		List<Map<String, Object>> mapList = service.findOrderInfoByOid(oid);

		Gson gson = new Gson();
		String json = gson.toJson(mapList);
		response.setContentType("text/html;charset=UTF-8");

		response.getWriter().write(json);

	}

	// 订单管理功能
	// 获取所有的订单
	public void showOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 从数据库中查询所有订单
		AdminService service = new AdminService();
		List<Order> orderList = service.findAllOrder();

		request.setAttribute("orderList", orderList);

		request.getRequestDispatcher("admin/order/list.jsp").forward(request, response);

	}

	// 分类管理功能
	// 从数据库获取所有分类
	public void showCategoryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 从数据库中查询所有订单
		AdminService service = new AdminService();
		List<Category> categoryList = service.findAllCategory();

		request.setAttribute("categoryList", categoryList);

		request.getRequestDispatcher("admin/category/list.jsp").forward(request, response);
	}

	// 商品管理
	public void showProductList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Product product = new Product();
		// 传递到service层,获取数据
		AdminService service = new AdminService();
		List<Product> productList = service.findAllProductList();

		// 将数据转发到域
		request.setAttribute("productList", productList);
		// 重定向到productlist.jsp
		request.getRequestDispatcher("admin/product/list.jsp").forward(request, response);

	}

	// 查询导航栏
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AdminService service = new AdminService();
		List<Category> categoryList = service.findAllCategory();
		// 解析成json格式
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);

	}

}