package com.huohuo.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huohuo.common.utils.ExceptionUtil;
import com.huohuo.pojo.TbUser;
import com.huohuo.portal.pojo.CartItem;
import com.huohuo.portal.pojo.Order;
import com.huohuo.portal.service.CartService;
import com.huohuo.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		//取购物车商品列表
		List<CartItem> list = cartService.getCartItemList(request, response);
		//传递给页面
		model.addAttribute("cartList", list);
		
		return "order-cart";
	}
	
	@RequestMapping("/create")
	public String createOrder(Order order,Model model,HttpServletRequest request) {
		try {
			//从request中获取用户信息
			TbUser user = (TbUser) request.getAttribute("user");
			//在order对象中补全用户信息
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			
			String orderId = orderService.createOrder(order);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message","啊哦，创建订单失败了： "+ ExceptionUtil.getStackTrace(e));
			return "error/exception";
		}
	}

}
