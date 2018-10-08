package com.xiahu.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.xiahu.dao.OrderDao;
import com.xiahu.domain.Order;
import com.xiahu.domain.OrderItem;
import com.xiahu.utils.DataSourceUtils;

public class OrderService {

	public void submitOrder(Order order) {
		OrderDao dao = new OrderDao();

		try {
			// 1.开启事务
			DataSourceUtils.startTransaction();
			// 2、调用dao存储order表数据的方法
			dao.addOrder(order);
			// 3.调用dao存储orderitem数据的方法
			dao.addOrderItem(order);
		} catch (SQLException e) {
			;
			try {
				// 4.回滚事务
				DataSourceUtils.rollback();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				// 5.提交事务
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public void updateOrderInfo(Order order) {
		OrderDao dao = new OrderDao();
		try {
			dao.updateOrderInfo(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateOrderState(String r6_Order) {
		OrderDao dao = new OrderDao();
		try {
			dao.updateOrderState(r6_Order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Order> findAllOrders(String uid) {
		OrderDao dao = new OrderDao();
		List<Order> orderList = null;
		try {
			orderList = dao.findAllOrders(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	public List<Map<String, Object>> findAllOrderItemByOid(String oid) {
		
		OrderDao dao = new OrderDao();
		List<Map<String, Object>> orderItemList = null;
		try {
			orderItemList = dao.findAllOrderItemByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderItemList;
	}

}
