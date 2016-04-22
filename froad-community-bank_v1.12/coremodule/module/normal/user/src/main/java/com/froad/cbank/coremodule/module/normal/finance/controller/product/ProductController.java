package com.froad.cbank.coremodule.module.normal.finance.controller.product;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;

/**   
 * 理财产品接口 
 * @author liuhuangle@f-road.com.cn   
 * @date 2015-6-11 下午7:46:14
 */
@Controller
@RequestMapping("/finance/product")
public class ProductController extends BasicSpringController{

	/**
	 * 
	 * <p>功能简述：—— 商品列表页初始接口</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-6-12上午09:28:50</p>
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
	 * <p>功能简述：—— 精选商品分页接口</p> 
	 * <p>使用说明：—— 说明方法功能、使用注意事项等</p> 
	 * <p>创建时间：2015-6-12上午09:36:44</p>
	 * <p>作者: 吴菲</p>
	 * @param model
	 * @param request
	 * @param pagePojo
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,PagePojo pagePojo){
		model.clear();
		
		
	}
	
	/**  
	 * 商品详情接口 
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015-6-11 下午7:47:12  
	 * @param model
	 * @param request
	 * @param productId  
	 * @throws  
	 */
	@RequestMapping(value="/{productId}", method=RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,@PathVariable String productId){
		model.put("productId", productId);
		
	}
	
	/**   
	 * 产品购买校验接口
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015-6-15 下午2:14:24  
	 * @param model
	 * @param request
	 * @param productId  
	 * @throws  
	 * 描述：绑卡校验、风险评估等级校验、商品状态校验、用户购买额度校验
	 */
	@Token
	@RequestMapping(value="/verifyBuy", method=RequestMethod.POST)
	public void verifyBuy(ModelMap model, @RequestHeader Long memberCode,@RequestParam(value="productId", required=true) String productId){
		model.put("productId", productId);
		model.put("memberCode", memberCode);
	}
	
	/**   
	 * 理财产品协议书接口
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015-6-15 下午3:17:43  
	 * @param model
	 * @param productId
	 * @param amount  
	 * @throws  
	 */
	@RequestMapping(value="/bookLccpxys", method=RequestMethod.GET)
	public void bookLccpxys(ModelMap model, @RequestParam(value="productId", required=true) String productId,double amount){
		 
	}
}
