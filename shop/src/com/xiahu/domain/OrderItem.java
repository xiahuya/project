package com.xiahu.domain;

public class OrderItem {
	// itemid` varchar(32) NOT NULL,
	// `count` int(11) DEFAULT NULL,
	// `subtotal` double DEFAULT NULL,
	// `pid` varchar(32) DEFAULT NULL,
	// `oid` varchar(32) DEFAULT NULL,

	// 订单名
	private String itemid;

	// 数量
	private int count;

	// 小计
	private double subtotal;

	// 商品名称
	private Product product;

	//属于哪个订单
	private Order orders;

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrders() {
		return orders;
	}

	public void setOrders(Order orders) {
		this.orders = orders;
	}
	
}
