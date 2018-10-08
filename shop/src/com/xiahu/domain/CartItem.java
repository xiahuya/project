package com.xiahu.domain;

/**
 * @author Administrator 
 * 
 * 封装的是购物项
 */
public class CartItem {
	private Product product;
	// 商品的数量
	private int buyNum;

	// 商品单价(小计)
	private double subtotal;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
}
