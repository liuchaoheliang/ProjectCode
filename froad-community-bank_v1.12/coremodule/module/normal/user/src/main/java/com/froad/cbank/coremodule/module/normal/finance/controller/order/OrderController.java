package com.froad.cbank.coremodule.module.normal.finance.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.finance.vo.OrderCreateVo;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;

/**   
 * 订单相关接口
 * @author liuhuangle@f-road.com.cn   
 * com.froad.cbank.coremodule.module.normal.finance.controller.order
 * @date 2015-6-15 下午3:44:20
 */
@Controller
@RequestMapping("/finance/order")
public class OrderController extends BasicSpringController{

	/**   
	 * 创建订单接口
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015-6-15 下午3:44:34
	 * @throws  
	 */
	@Token
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public void create(ModelMap model, @RequestHeader Long memberCode, @RequestBody OrderCreateVo vo){
		 model.put("vo",vo.getBuyAmount());
	}
	
	/**
	 * 
	 * <p>功能简述：—— 个人中心 -我的交易记录列表接口</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-6-15上午10:33:06</p>
	 * <p>作者: 吴菲</p>
	 * @param model
	 * @param request
	 * @param pagePojo
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,PagePojo pagePojo){
		model.clear();
		
	}
	
	/**
	 * 
	 * <p>功能简述：—— 个人中心-我的交易记录详情接口</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-6-15上午10:37:10</p>
	 * <p>作者: 吴菲</p>
	 * @param model
	 * @param request
	 * @param orderId
	 */
	@RequestMapping(value="/{orderId}", method=RequestMethod.GET)
	public void detail(ModelMap model,HttpServletRequest request,@PathVariable String orderId){
		model.clear();
		
	}
	
	/**
	 * 
	 * <p>功能简述：—— 个人中心-理财产品交易取消(撤销)接口</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-6-15上午10:39:32</p>
	 * <p>作者: 吴菲</p>
	 * @param model
	 * @param request
	 * @param orderId
	 */
	@RequestMapping(value="/{orderId}", method=RequestMethod.DELETE)
	public void cancel(ModelMap model,HttpServletRequest request,@PathVariable String orderId){
		model.clear();
		
		
	}
}
