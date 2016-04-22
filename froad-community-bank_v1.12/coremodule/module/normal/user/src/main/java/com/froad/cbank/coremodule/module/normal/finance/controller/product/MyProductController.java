package com.froad.cbank.coremodule.module.normal.finance.controller.product;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;

/**
 * <p>说明: —— 个人中心-我的理财产品接口</p>
 * <p>创建时间：2015-6-15上午10:21:22</p>
 * <p>作者: 吴菲</p>
 */
@Controller
@RequestMapping("/finance/myProduct")
public class MyProductController {

	/**
	 * 
	 * <p>功能简述：—— 我的理财初始接口</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-6-15上午10:00:03</p>
	 * <p>作者: 吴菲</p>
	 * @param model
	 * @param request
	 */
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public void index(ModelMap model, HttpServletRequest request){
		model.clear();
		
		
	}
	
	/**
	 * 
	 * <p>功能简述：—— 我的理财列表接口</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-6-15上午10:02:06</p>
	 * <p>作者: 吴菲</p>
	 * @param model
	 * @param request
	 * @param pagePojo
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request, PagePojo pagePojo){
		model.clear();
		
		
		
	}
	
	/**
	 * 
	 * <p>功能简述：—— 我的理财产品详情接口</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-6-15上午10:08:06</p>
	 * <p>作者: 吴菲</p>
	 * @param model
	 * @param request
	 * @param orderId
	 */
	@RequestMapping(value="/{orderId}", method=RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request, @PathVariable String orderId){
		model.clear();
		
		
	}
	
}
