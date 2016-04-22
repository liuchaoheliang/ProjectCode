package com.froad.fft.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.fft.controller.BaseController;

@Controller
@RequestMapping("/shop/helper")
public class HelperController extends BaseController{

	//**帮助中信首页
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public void index(){}
}
