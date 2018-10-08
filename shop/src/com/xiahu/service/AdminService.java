package com.xiahu.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xiahu.dao.AdminDao;
import com.xiahu.domain.Category;
import com.xiahu.domain.Order;
import com.xiahu.domain.Product;

public class AdminService {

	// 导航栏选项查询
		public List<Category> findAllCategory() {
			AdminDao dao = new AdminDao();
			try {
				return dao.findAllCategory();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		}

		// 后台添加商品
		public void addProdutInfo(Product product) {
			AdminDao dao = new AdminDao();
			try {
				dao.addProdutInfo(product);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		// 查找所有商品
		public List<Product> findAllProductList() {
			AdminDao dao = new AdminDao();
			try {
				return dao.findAllProductList();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		}

		// 获取所有订单
		public List<Order> findAllOrder() {
			AdminDao dao = new AdminDao();
			try {
				return dao.findAllOrder();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		// 根据cid查询cname,回显到category，edit.jsp页面
		public Category findCategoryNameByCid(String cid) {
			AdminDao dao = new AdminDao();
			try {
				return dao.findCategoryNameByCid(cid);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}

		// 后台管理----Category----修改(编辑)
		public void updateCategoryName(String cid, String newName) {
			AdminDao dao = new AdminDao();
			try {
				dao.updateCategoryName(cid, newName);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		// 后台管理----Category----删除
		public void deleteCategoryByCid(String cid) {
			AdminDao dao = new AdminDao();
			try {
				dao.deleteCategoryByCid(cid);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		// 后台管理----Category----添加
		public void addCategoryName(Category category) {
			AdminDao dao = new AdminDao();
			try {
				dao.addCategoryName(category);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		// 后台管理----订单详情
		public List<Map<String, Object>> findOrderInfoByOid(String oid) {
			AdminDao dao = new AdminDao();
			try {
				return dao.findOrderInfoByOid(oid);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}

		}

}
