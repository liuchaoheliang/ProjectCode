package com.froad.fft.controller.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.fft.bean.page.PageDto;
import com.froad.fft.controller.BaseController;

@Controller
@RequestMapping("/merchant/user")
public class UserController extends BaseController {
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(PageDto page, ModelMap model) {
		model.addAttribute("page","");
		return "/merchant/user/list";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		
		return "/merchant/user/add";
	}
	
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save() {
		
		return "";
	}
}
