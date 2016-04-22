package com.froad.cbank.coremodule.module.normal.user.controller.cart;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.annotation.FunctionModule;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.support.CartSupport;
import com.froad.cbank.coremodule.module.normal.user.support.ProductSupport;
import com.froad.cbank.coremodule.module.normal.user.vo.CartVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PreferenceViewVo;

/**
 * 购物车
 * 
 * @ClassName: CartController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Create Author: hjz
 * @Create Date: 2015-3-30 上午11:02:30
 * 
 */
@Controller
public class CartController extends BasicSpringController {

	@Resource
	private CartSupport cartSupport;
	
	@Resource
	private ProductSupport productSupport;
	
	/**
	 * 查看购物车
	 * 
	 * @Title: list
	 * @Project coremodule-user
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.cart
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015年4月8日 下午3:22:37
	 * @param vipLevel
	 * @throws Exception
	 * @url /cart/list
	 * @method get
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/cart/list", method = RequestMethod.GET)
	public void list(ModelMap model, @RequestHeader long memberCode,HttpServletRequest request) throws Exception {
		
		long startTime = System.currentTimeMillis();
		
		model.clear();
		String clientId=getClient(request);
		model.putAll(cartSupport.getCartList(clientId, memberCode,null));
		
		Monitor.send(MonitorEnums.user_cart_query_time, Long.toString(System.currentTimeMillis() - startTime));
	}



	/**
	 * 添加购物车
	 * 
	 * @Title: add
	 * @Project Froad Cbank CoreModule Module Normal User
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.cart
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015-3-30 上午11:50:08
	 * @param clientId
	 *            客户端ID
	 * @param memberCode
	 *            会员code
	 * @param merchantId
	 *            商户ID
	 * @param outletId
	 *            门店ID
	 * @param productId
	 *            商品编号
	 * @param num
	 *            数量
	 * @param vipLevel
	 *            vip等级
	 * @url /cart/add
	 * @method post
	 * @body {"merchantId":"0026cbd80000","productId":"0091716b8000","num":10}
	 */
	@Token
	@RequestMapping(value = "/cart/add", method = RequestMethod.POST)
	public void add(ModelMap model, @RequestBody CartVo cartVo,@RequestHeader long memberCode ,HttpServletRequest request) {
		String clientId = getClient(request);
		
		LogCvt.info(String.format("添加购物车请求参数:%s",JSON.toJSONString(cartVo)));
		model.putAll(cartSupport.addCart(clientId, cartVo, memberCode));
		
		//购物车商品数量
		Integer productCount = productSupport.getCount(clientId, memberCode);
		model.put("productCount", productCount);
		

	}

	/**
	 * 清空购物车
	 * 
	 * @Title: truncate
	 * @Project Froad Cbank CoreModule Module Normal User
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.cart
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015-3-30 上午11:13:50
	 * @url /cart/truncate
	 * @method delete
	 */
	@Token
	@RequestMapping(value = "/cart/truncate", method = RequestMethod.DELETE)
	public void truncate(ModelMap model, @RequestHeader long memberCode,HttpServletRequest request) {
		
		String clientId=getClient(request);
		model.putAll(cartSupport.truncateCart(clientId, memberCode));
	}

	/**
	 * 更新购物车商品的提货网点
	 * 
	 * @Title: update
	 * @Project coremodule-user
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.cart
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015年4月8日 下午3:28:35
	 * @param merchantId
	 * @param productId
	 * @param outletId
	 * @url /cart/update
	 * @method put
	 */
	@Token
	@RequestMapping(value = "/cart/update_delivery", method = RequestMethod.PUT)
	public void updateDelivery(ModelMap model, @RequestBody CartVo cartVo,@RequestHeader("memberCode") long memberCode,HttpServletRequest request) {
		String clientId=getClient(request);
		model.putAll(cartSupport.updateDelivery(clientId, cartVo, memberCode));
	}

	/**
	 * 获取购物车商品数量
	 * 
	 * @Title: cout
	 * @Project coremodule-user
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.cart
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015年4月10日 下午5:59:13
	 * @param clientId
	 * @param memberCode
	 * @url /cart/cout
	 * @method get
	 */
	
	@RequestMapping(value = "/cart/cout", method = RequestMethod.GET)
	public void cout(ModelMap model, @RequestHeader("memberCode") long memberCode,HttpServletRequest request) {
		String clientId=getClient(request);
		model.putAll(cartSupport.cout(clientId, memberCode));
	}

	/**
	 * 批量删除购物车信息
	 * 
	 * @Title: delete_batch
	 * @Project coremodule-user
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.cart
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015年4月10日 下午6:14:49
	 * @param clientId
	 * @param memberCode
	 * @param productIds
	 *            商品ID集合
	 * @url /cart/delete_batch
	 * @method delete
	 */
	@Token
	@RequestMapping(value = "/cart/deleteBatch", method = RequestMethod.DELETE)
	public void deleteBatch(ModelMap model, @RequestBody CartVo[] cartVOs,@RequestHeader("memberCode") long memberCode,HttpServletRequest request) {
		String clientId=getClient(request);
		model.putAll(cartSupport.deleteBatch(clientId, cartVOs, memberCode));
	}

	/**
	 * 删除购物车信息 -单个删除
	 * 
	 * @param model
	 * @param cartVo
	 */
	@Token
	@RequestMapping(value = "/cart/delete", method = RequestMethod.DELETE)
	public void delete(ModelMap model, @RequestBody CartVo cartVo,@RequestHeader("memberCode") long memberCode,HttpServletRequest request) {
		String clientId=getClient(request);
		model.putAll(cartSupport.delete(clientId, cartVo, memberCode));
	}

	/**
	 * 批量修改购物车商品数量
	 * 
	 * @Title: quantity
	 * @Project coremodule-user
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.cart
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015年4月10日 下午6:17:53
	 * @param model
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 * @param merchantId
	 * @param num
	 * @url /cart/quantity
	 * @method put
	 */
	@Token
	@RequestMapping(value = "/cart/quantity", method = RequestMethod.PUT)
	public void quantity(ModelMap model, @RequestBody CartVo[] cartVOs,@RequestHeader("memberCode") long memberCode,HttpServletRequest request) {
		String clientId=getClient(request);
		model.putAll(cartSupport.quantity(clientId, cartVOs, memberCode));
	}

	/**
	 * 结算 
	 * 王炜华
	 * 
	 * @param model
	 * @param cartVo
	 */
	@Token
	@RequestMapping(value = "/cart/settleAccounts", method = RequestMethod.PUT)
	public void settleAccounts(ModelMap model, @RequestBody CartVo[] cartVOs,@RequestHeader("memberCode") long memberCode,HttpServletRequest request) {
		String clientId=getClient(request);
		model.putAll(cartSupport.settleAccounts(clientId, cartVOs, memberCode));
	}
	
	
	/**
	 * 检查购物车精品预售商品的配送方式
	 * @param model
	 * @param cartVo
	 * @param memberCode
	 * @param request
	 * @throws Exception
	 */
	@Token
	@RequestMapping(value = "/cart/checkDelivery", method = RequestMethod.GET)
	public void checkDelivery(ModelMap model,@RequestHeader("memberCode") long memberCode,HttpServletRequest request) throws Exception {
		
		String clientId=getClient(request);
		CartVo cartVo = convertMap(request, CartVo.class);
		LogCvt.info("检查购物车精品预售商品的配送方式传入参数："+JSON.toJSONString(cartVo));
		
		model.putAll(cartSupport.checkDelivery(clientId, cartVo, memberCode));
	}
	
	/**
	 * 购物车订单优惠查询接口
	 * @param model
	 * @param payOrdersVo
	 * @throws Exception
	 */
	@Token
	@RequestMapping(value = "/cart/preference", method = RequestMethod.POST)
	public void orderPreference(ModelMap model,@RequestBody List<PreferenceViewVo> list,@RequestHeader Long memberCode, HttpServletRequest req ) throws Exception {
		model.clear();
		String clientId=getClient(req);		
		if(StringUtil.isNotBlank(clientId)){ 	
			model.putAll(cartSupport.carPreference(clientId, memberCode, list));
			model.put(Results.result,"");
		}else{
			model.put(Results.code,"9999");
			model.put(Results.msg,"必要参数为空");
		}
	}
	
}
