package com.xiahu.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator 封装的是购物车,一个购物车有多个购物项
 *
 */
public class Cart {

	// 该购物车中存储的n个购物项
	private Map<String, CartItem> cartitrm = new HashMap<String, CartItem>();

	// 商品的总计
	private double total;

	public Map<String, CartItem> getCartitrm() {
		return cartitrm;
	}

	public void setCartitrm(Map<String, CartItem> cartitrm) {
		this.cartitrm = cartitrm;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
