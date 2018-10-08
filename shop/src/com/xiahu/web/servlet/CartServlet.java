package com.xiahu.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xiahu.domain.Cart;
import com.xiahu.domain.CartItem;
import com.xiahu.domain.Product;
import com.xiahu.service.ProductService;

public class CartServlet extends BaseServlet {
	
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
		
	}
	
	
	// 删除购物车中的购物项
	public void delProductFromCret(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String pid = request.getParameter("pid");

		// 从获取session域中获取cart
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			Map<String, CartItem> cartitrm = cart.getCartitrm();
			//修改总计
			cart.setTotal(cart.getTotal()-cartitrm.get(pid).getSubtotal());			
			//删除该商品的pid
			cartitrm.remove(pid);
			cart.setCartitrm(cartitrm);
		}
		
		response.sendRedirect(request.getContextPath() + "/cart.jsp");

	}

	// 添加商品到购物车中
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		ProductService service = new ProductService();

		// 获取准备添加到购物车的商品的pid
		String pid = request.getParameter("pid");
		// 获取准备添加到购物车的商品的数量
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));

		// 根据pid查询该商品的信息
		Product productList = service.findProductByPid(pid);

		// 计算小计-------价格*数量
		double subtotal = productList.getShop_price() * buyNum;

		// 疯转cartItem对象
		CartItem cartItem = new CartItem();
		cartItem.setProduct(productList);
		cartItem.setBuyNum(buyNum);
		cartItem.setSubtotal(subtotal);

		// 获得购物车---判断是否在session中已经存在购物车
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			// 证明session域中没有该cart(购物车对象)
			// 创建一个cart对象
			cart = new Cart();
		}

		// 将购物项放到车中---key是pid
		// 先判断购物车中是否已将包含此购物项了 ----- 判断key是否已经存在
		// 如果购物车中已经存在该商品----将现在买的数量与原有的数量进行相加操作
		Map<String, CartItem> cartItems = cart.getCartitrm();
		double newSuntotal = 0.0;
		if (cartItems.containsKey(pid)) {
			// 包括该商品
			// 1.取出原有商品的数量
			CartItem cartItem2 = cartItems.get(pid);
			int oldBuyNum = cartItem2.getBuyNum();
			// 2.将原来的商品数量与现在的商品数量相加
			oldBuyNum += buyNum;
			// 3.封装到cart中
			cartItem2.setBuyNum(oldBuyNum);
			cart.setCartitrm(cartItems);

			// 修改小计
			// 4.获得原来商品的小计
			double oldSubtotal = cartItem2.getSubtotal();
			newSuntotal = buyNum * productList.getShop_price();
			cartItem2.setSubtotal(oldSubtotal + newSuntotal);

		} else {
			// 不包括该商品
			cart.getCartitrm().put(productList.getPid(), cartItem);
			newSuntotal = buyNum * productList.getShop_price();

		}

		double total = cart.getTotal() + newSuntotal;
		// 计算总计
		cart.setTotal(total);

		// 将购物车对象添加到session域
		session.setAttribute("cart", cart);

		response.sendRedirect(request.getContextPath() + "/cart.jsp");

	}

}