package com.froad.fft.controller.shop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.fft.controller.BaseController;

/**
 * 会员注销
 * @author FQ
 *
 */

@Controller
@RequestMapping("/shop")
public class LogoutController extends BaseController {
	
	/**
	 * 注销
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		//删除用户 session
		
		//删除用户 cookie
		
		return "redirect:/";
	}
}
