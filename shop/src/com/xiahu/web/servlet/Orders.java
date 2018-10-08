package com.xiahu.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Entity;

import com.xiahu.domain.Cart;
import com.xiahu.domain.CartItem;
import com.xiahu.domain.Order;
import com.xiahu.domain.OrderItem;
import com.xiahu.domain.Product;
import com.xiahu.domain.User;
import com.xiahu.service.OrderService;
import com.xiahu.utils.CommonsUtils;
import com.xiahu.utils.PaymentUtil;

public class Orders extends BaseServlet {

	// 我的订单
	public void myOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 判断用户是否登录
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		// 根据该用户uid查询该用户的所有的订单信息(单表查询orders表)
		OrderService service = new OrderService();
		List<Order> orderList = service.findAllOrders(user.getUid());
		// 循环所有的订单 为每个订单填充订单项集合信息
		if (orderList != null) {
			for (Order order : orderList) {
				// 获取每个订单的oid
				String oid = order.getOid();
				// 根据订单号查询该订单的所有订单项
				List<Map<String, Object>> orderItemList = service.findAllOrderItemByOid(oid);
				for (Map<String, Object> map : orderItemList) {
					// 从map中取出count subtotal 封装到OrderItem中
					OrderItem orderItem = new OrderItem();
					try {
						BeanUtils.populate(orderItem, map);
						// 从map中取出pimage pname shop_price 封装到Product中
						Product product = new Product();
						BeanUtils.populate(product, map);
						// 将product封装到OrderItem
						orderItem.setProduct(product);
						// 将orderitem封装到order中的orderItemList中
						order.getOrderItems().add(orderItem);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}

			}
		}
		// orderList封装完整了
		request.setAttribute("orderList", orderList);

		request.getRequestDispatcher("/order_list.jsp").forward(request, response);

	}

	// 确定订单----更新订单信息+在线支付
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.更新收货人信息
		Map<String, String[]> properties = request.getParameterMap();
		Order order = new Order();
		try {
			BeanUtils.populate(order, properties);
		} catch (Exception e) {
			e.printStackTrace();
		}

		OrderService service = new OrderService();
		service.updateOrderInfo(order);

		// 2、在线支付
		/*
		 * if(pd_FrpId.equals("ABC-NET-B2C")){ //介入农行的接口 }else
		 * if(pd_FrpId.equals("ICBC-NET-B2C")){ //接入工行的接口 }
		 */
		// .......

		// 只接入一个接口，这个接口已经集成所有的银行接口了 ，这个接口是第三方支付平台提供的
		// 接入的是易宝支付
		// 获得 支付必须基本数据
		String orderid = request.getParameter("oid");
		// String money = order.getTotal()+"";//支付金额
		String money = "0.01";// 支付金额
		// 银行
		String pd_FrpId = request.getParameter("pd_FrpId");

		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = orderid;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId=" + pd_FrpId + "&p0_Cmd=" + p0_Cmd
				+ "&p1_MerId=" + p1_MerId + "&p2_Order=" + p2_Order + "&p3_Amt=" + p3_Amt + "&p4_Cur=" + p4_Cur
				+ "&p5_Pid=" + p5_Pid + "&p6_Pcat=" + p6_Pcat + "&p7_Pdesc=" + p7_Pdesc + "&p8_Url=" + p8_Url
				+ "&p9_SAF=" + p9_SAF + "&pa_MP=" + pa_MP + "&pr_NeedResponse=" + pr_NeedResponse + "&hmac=" + hmac;

		// 重定向到第三方支付平台
		response.sendRedirect(url);
	}

	// 提交订单
	public void sumbitOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取session域中的cart
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		User user = (User) session.getAttribute("user");

		// 判断用户是否登录
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		// 封住Order对象提交给service层
		Order order = new Order();
		// 1.private String oid;
		String oid = CommonsUtils.getUUID();
		order.setOid(oid);

		// 2.private Date ordertime;
		order.setOrdertime(new Date());

		// 3.private double total;
		double total = cart.getTotal();
		order.setTotal(total);

		// 4.private int state;
		order.setState(0);

		// 5.private String address;
		order.setAddress(null);

		// 6.private String name;
		order.setName(null);

		// 7.private String telephone;
		order.setTelephone(null);

		// 8.private User user;
		order.setUser(user);

		// 9.private List<OrderItem> orderItems=new ArrayList<OrderItem>();
		Map<String, CartItem> cartItems = cart.getCartitrm();
		for (Entry<String, CartItem> entry : cartItems.entrySet()) {
			// 封装private List<OrderItem> orderItems=new ArrayList<OrderItem>();

			CartItem cartItem = entry.getValue();
			OrderItem orderItem = new OrderItem();

			// 1.private String itemid;
			orderItem.setItemid(CommonsUtils.getUUID());
			// 2.private int count;
			orderItem.setCount(cartItem.getBuyNum());
			// 3.private Product product;
			orderItem.setProduct(cartItem.getProduct());
			// 4.private double subtotal;
			orderItem.setSubtotal(cartItem.getSubtotal());
			// 5.private Order orders;
			orderItem.setOrders(order);

			// 封装
			order.getOrderItems().add(orderItem);

		}

		// order对象封装完毕
		// 将数据传递到service层
		OrderService service = new OrderService();
		service.submitOrder(order);

		// 提交成功

		session.setAttribute("order", order);
		response.sendRedirect(request.getContextPath() + "/order_info.jsp");

	}

}