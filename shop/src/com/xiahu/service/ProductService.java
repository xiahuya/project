package com.xiahu.service;

import java.sql.SQLException;
import java.util.List;

import com.xiahu.dao.ProductDao;
import com.xiahu.domain.Category;
import com.xiahu.domain.PageBean;
import com.xiahu.domain.Product;

public class ProductService {

	// 热门商品
	public List<Product> findHotProductlist() throws SQLException {
		ProductDao dao = new ProductDao();
		return dao.findHotProductlist();

	}

	// 最新商品
	public List<Product> findNewProductlist() throws SQLException {
		ProductDao dao = new ProductDao();
		return dao.findNewProductlist();
	}

	// 查询导航栏
	public List<Category> findCategoryList() {
		ProductDao dao = new ProductDao();
		List<Category> categoryList = null;
		try {
			categoryList = dao.findCategoryList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;

	}

	public PageBean pageBean(String cid, int currentPage, int currentCount) throws SQLException {
		ProductDao dao = new ProductDao();
		PageBean pageBean = new PageBean<>();
		// 封装PageBean
		// 1.private int currentPage;
		pageBean.setCurrentPage(currentPage);
		// 2.private int currentCount;
		pageBean.setCurrentCount(currentCount);
		// 3.private int totalCount;总条数
		int totalCount = dao.findProductCountByCid(cid);
		pageBean.setTotalCount(totalCount);
		// 4.private int totalPage;总页数
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);

		// 5.private List<T> productList;
		int index = (currentPage - 1) * currentCount;
		List<Product> productList = dao.findProductListByCid(cid, index, currentCount);
		pageBean.setProductList(productList);

		return pageBean;
	}

	// 根据pid查找商品
	public Product findProductByPid(String pid) {
		ProductDao dao = new ProductDao();
		Product productList = null;
		try {
			productList = dao.findProductByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;

	}

}
