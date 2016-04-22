package com.froad.cbank.coremodule.module.normal.user.support;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.enums.ProductType;
import com.froad.cbank.coremodule.module.normal.user.pojo.CartFullGivePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.CartResActivePojo;
import com.froad.cbank.coremodule.module.normal.user.utils.AmountUtils;
import com.froad.cbank.coremodule.module.normal.user.vo.CartViewVo;
import com.froad.cbank.coremodule.module.normal.user.vo.CartVo;
import com.froad.cbank.coremodule.module.normal.user.vo.ChangedProductVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PreferenceViewVo;
import com.froad.cbank.coremodule.module.normal.user.vo.ProductViewVo;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.ShoppingCartService;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ShoppingCartFullGiveVo;
import com.froad.thrift.vo.active.ShoppingCartReqProductVo;
import com.froad.thrift.vo.active.ShoppingCartReqVo;
import com.froad.thrift.vo.active.ShoppingCartResActiveVo;
import com.froad.thrift.vo.active.ShoppingCartResProductVo;
import com.froad.thrift.vo.active.ShoppingCartResVo;
import com.froad.thrift.vo.shopingcart.ShoppingCartVoReq;
import com.froad.thrift.vo.shopingcart.ShoppingCartVoRes;
import com.pay.user.helper.VIPStatus;

/**
 * 购物车服务类
 */
@Service
public class CartSupport {

	@Resource
	private ShoppingCartService.Iface shoppingCartService;

	@Resource
	private ProductService.Iface productService;

	@Resource
	private UserEngineSupport userEngineSupport;

	@Resource
	private OutletService.Iface outletService;
	
	@Resource
	private VipSupport vipSupport;
	
	@Resource
	private ActiveRunService.Iface activeRunService;

	/**
	 * 获取购物车列表
	 * 
	 * @param clientId
	 * @param cartVo
	 * @param memberCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getCartList(String clientId, long memberCode,List<String> ids) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			int vipLevel = vipSupport.getVipLevel(memberCode, clientId);
			
			List<ShoppingCartVoRes> res = shoppingCartService.getCart(memberCode, clientId, vipLevel);
			
			//营销平台购物车检验
			List<PreferenceViewVo> pvList = new ArrayList<PreferenceViewVo>();
			PreferenceViewVo preferenceViewVo = null;
			for( ShoppingCartVoRes carVoRes : res){
				//如果是选中部分购物车中商品，则只查询选中的商品
				if(ids == null || ids.contains(carVoRes.getProductId()) ){
					preferenceViewVo = new PreferenceViewVo();
					preferenceViewVo.setProductId(carVoRes.getProductId());
					preferenceViewVo.setProductName(carVoRes.getProductName());
					Double productTotalMoney=carVoRes.getMoney()*carVoRes.getQuantity();
					preferenceViewVo.setProductTotalMoney(productTotalMoney);
					Double vipTotalMoney=carVoRes.getVipMoney()*carVoRes.getVipQuantity();
					preferenceViewVo.setVipTotalMoney(vipTotalMoney);
					pvList.add(preferenceViewVo);
				}else {
					continue ;
				}
			}			
			Map<String, Object> map = carPreference(clientId, memberCode, pvList);			
			//营销平台购物车检验
			
			int changedCounts = 0;

			List<CartViewVo> cartViewVoList = new ArrayList<CartViewVo>();
			List<ProductViewVo> productViewVoList = null;
			CartViewVo cartViewVo = null;
			ProductViewVo productViewVo = null;
			// 总金额
			BigDecimal totalAmount = new BigDecimal("0.00");
			// 优惠总金额
			BigDecimal preAmount = new BigDecimal("0.00");
			// 商品总数
			int totalCounts = 0;
			HashMap<String, CartViewVo> mechant = new HashMap<String, CartViewVo>();
			List<String> merchantIds = new ArrayList<String>();

			for (ShoppingCartVoRes shopping : res) {

				totalCounts++;

				if (!"0000".equals(shopping.getErrCode())) {
					changedCounts++;
				}

				// 当商户集合里面不包含循环的商户ID时，做新增集合操作
				if (!mechant.containsKey(shopping.getMerchantId())) {

					cartViewVo = new CartViewVo();
					BeanUtils.copyProperties(cartViewVo, shopping);

					productViewVo = new ProductViewVo();
					BeanUtils.copyProperties(productViewVo, shopping);
					//组装活动列表
					List<CartResActivePojo> alist = (List<CartResActivePojo>) map.get(shopping.getProductId());
					productViewVo.setActiveList(alist);
					
					productViewVoList = new ArrayList<ProductViewVo>();
					productViewVoList.add(productViewVo);

					cartViewVo.setProductViewVoList(productViewVoList);

					mechant.put(shopping.getMerchantId(), cartViewVo);
					// 为排序记录
					merchantIds.add(shopping.getMerchantId());

				} else {// 当商户集合里面包含循环的商户ID时，做新增集合里面List<ProductViewVo> 操作

					// 取到对应的value
					cartViewVo = mechant.get(shopping.getMerchantId());

					// copy商品展示所需要属性
					productViewVo = new ProductViewVo();
					BeanUtils.copyProperties(productViewVo, shopping);
					//组装活动列表
					List<CartResActivePojo> alist = (List<CartResActivePojo>) map.get(shopping.getProductId());
					productViewVo.setActiveList(alist);
					
					// 取集合对应的value ,新加数据
					productViewVoList = cartViewVo.getProductViewVoList();
					productViewVoList.add(productViewVo);

					cartViewVo.setProductViewVoList(productViewVoList);

					mechant.put(shopping.getMerchantId(), cartViewVo);
				}
				// 只计算商品状态正常的金额，1，7，8均为正常状态
				if ("1".equals(shopping.getStatus())
						|| "7".equals(shopping.getStatus())
						|| "8".equals(shopping.getStatus())) {

					totalAmount = totalAmount.add(AmountUtils
							.getTotalAmount(shopping));

					// vip优惠金额
					preAmount = preAmount.add(AmountUtils.getVipAmount(
							shopping.getMoney(), shopping.getVipMoney(),
							shopping.getVipQuantity()));
				}

			}

			// 从排序记录List里面虚幻读取
			for (String key : merchantIds) {
				cartViewVoList.add(mechant.get(key));
			}

			result.put(Results.result, cartViewVoList);
			result.put("changedCounts", changedCounts);
			result.put("totalAmount", totalAmount);
			result.put("preAmount", preAmount);
			result.put("totalCounts", totalCounts);
			result.put("vipStatus", vipLevel > 0 ? VIPStatus.NORMAL.getValue() : null);
			result.put("fullGive",map.get("fullGive"));
		} catch (TException e) {
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			LogCvt.error("查看购物车列表服务异常", e);
		}
		return result;
	}



	/**
	 * 添加购物车
	 * 
	 * @param clientId
	 * @param cartVo
	 * @param memberCode
	 * @return
	 */
	public Map<String, Object> addCart(String clientId, CartVo cartVo,long memberCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (cartVo == null || cartVo.getNum() == 0 || StringUtil.isEmpty(cartVo.getMerchantId()) || StringUtil.isEmpty(cartVo.getProductId())) {
				LogCvt.info(String.format("缺少必要的请求参数:",JSON.toJSONString(cartVo)));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
				return result;
			}

			ShoppingCartVoReq req = new ShoppingCartVoReq();
			req.setClientId(clientId);
			req.setMemberCode(memberCode);
			req.setMerchantId(cartVo.getMerchantId());
			req.setProductId(cartVo.getProductId());
			req.setNum(cartVo.getNum());

			int vipLevel = vipSupport.getVipLevel(memberCode, clientId);

			req.setVipLevel(vipLevel);

			// 检查商品提货类型
			ProductOperateVoReq productOperateVo = new ProductOperateVoReq();
			productOperateVo.setClientId(clientId);
			productOperateVo.setProductId(cartVo.getProductId());
			productOperateVo.setMerchantId(cartVo.getMerchantId());

			ProductInfoVo productInfoVo = productService
					.getMerchantProductDetail(productOperateVo);

			if (productInfoVo != null) {
				ProductVo productVo = productInfoVo.getProduct();
				if (ProductType.presell.getCode().equals(
						productInfoVo.getProduct().getType())) {
					Date now = new Date();
					// 判断预售有效期
					if (productVo.isSetStartTime() || productVo.isSetEndTime()) {
						if (now.before(new Date(productVo.getStartTime()))) {
							result.put(Results.code, "1002");
							result.put(Results.msg, "[" + productVo.getName()
									+ "]预售活动尚未开始");
							return result;
						}
						if (now.after(new Date(productVo.getEndTime()))) {
							result.put(Results.code, "1003");
							result.put(Results.msg, "[" + productVo.getName()
									+ "]预售活动已结束");
							return result;
						}
					}
					if (productVo.getDeliveryOption() != null
							&& "1".equals(productVo.getDeliveryOption())) {
						if (StringUtil.isEmpty(cartVo.getOutletId())) {
							result.put(Results.code, "1001");
							result.put(Results.msg, "网点自提必须选择提货网点");
							return result;
						} else {
							req.setOutletId(cartVo.getOutletId());
						}

					} else if (productVo.getDeliveryOption() != null
							&& "2".equals(productVo.getDeliveryOption())) {
						if (StringUtil.isNotBlank(cartVo.getOutletId())) {
							req.setOutletId(cartVo.getOutletId());
						}
					} else {
						if (StringUtil.isNotBlank(cartVo.getOutletId())) {
							req.setOutletId(cartVo.getOutletId());
						}
					}
				} else if (ProductType.group.getCode().equals(
						productInfoVo.getProduct().getType())) {
					Date now = new Date();
					// 判断团购有效期
					if (productVo.isSetStartTime() || productVo.isSetEndTime()) {
						if (now.before(new Date(productVo.getStartTime()))) {
							result.put(Results.code, "1002");
							result.put(Results.msg, "[" + productVo.getName()
									+ "]团购活动尚未开始");
							return result;
						}
						if (now.after(new Date(productVo.getEndTime()))) {
							result.put(Results.code, "1003");
							result.put(Results.msg, "[" + productVo.getName()
									+ "]团购活动已结束");
							return result;
						}
					}
				}
			} else {
				result.put(Results.code, "9999");
				result.put(Results.msg, "查询商品信息失败");
				return result;
			}

			ResultVo vo = shoppingCartService.addCart(req);

			result.put(Results.code, vo.getResultCode());
			result.put(Results.msg, vo.getResultDesc());
			return result;

		} catch (TException e) {
			LogCvt.error("添加购物车失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			return result;
		}
	}

	/**
	 * 清空购物车
	 * 
	 * @param clientId
	 * @param cartVo
	 * @param memberCode
	 * @return
	 */
	public Map<String, Object> truncateCart(String clientId, long memberCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			boolean isSuccess = shoppingCartService.deleteCart(memberCode, clientId);
			result.put(Results.result, isSuccess);
			return result;

		} catch (TException e) {
			LogCvt.error("清空购物车失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			return result;
		}
	}

	/**
	 * 更新购物车商品的提货网点
	 * 
	 * @param clientId
	 * @param cartVo
	 * @param memberCode
	 * @return
	 */
	public Map<String, Object> updateDelivery(String clientId, CartVo cartVo, long memberCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (cartVo == null || StringUtil.isEmpty(cartVo.getMerchantId()) || StringUtil.isEmpty(cartVo.getProductId()) || StringUtil.isEmpty(cartVo.getOutletId())) {
				LogCvt.info(String.format("缺少必要的请求参数:", JSON.toJSONString(cartVo)));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
				return result;
			}

			ResultVo vo = shoppingCartService.updateDelivery(memberCode, clientId, cartVo.getMerchantId(), cartVo.getProductId(), cartVo.getOutletId());

			if (!"0000".equals(vo.getResultCode())) {
				result.put(Results.code, vo.getResultCode());
				return result;
			}
			result.put(Results.msg, vo.getResultDesc());
			return result;
		} catch (TException e) {
			LogCvt.error("更新购物车商品提货网点失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			return result;
		}
	}

	/**
	 * 获取购物车商品类目种数
	 * 
	 * @param clientId
	 * @param cartVo
	 * @param memberCode
	 * @return
	 */
	public Map<String, Object> cout(String clientId, long memberCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int cout = shoppingCartService.getCartCount(memberCode,clientId);
			result.put(Results.result, cout);
			return result;
		} catch (TException e) {
			LogCvt.error("获取购物车商品数量失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			return result;
		}
	}

	/**
	 * 批量删除购物车信息
	 * 
	 * @param clientId
	 * @param cartVo
	 * @param memberCode
	 * @return
	 */
	public Map<String, Object> deleteBatch(String clientId, CartVo[] cartVOs, long memberCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (cartVOs == null || cartVOs.length <= 0) {
				LogCvt.info(String.format("缺少必要的请求参数:", JSON.toJSONString(cartVOs)));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
				return result;
			}

			List<ShoppingCartVoReq> list = new ArrayList<ShoppingCartVoReq>();
			for (CartVo cartVo : cartVOs) {
				ShoppingCartVoReq req = new ShoppingCartVoReq();
				req.setClientId(clientId);
				req.setMemberCode(memberCode);
				req.setProductId(cartVo.getProductId());
				req.setMerchantId(cartVo.getMerchantId());
				list.add(req);
			}
			ResultVo vo = shoppingCartService.deleteBatchCart(list);

			result.put(Results.code, vo.getResultCode());
			result.put(Results.msg, vo.getResultDesc());
			return result;
		} catch (TException e) {
			LogCvt.error("批量删除购物车信息失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			return result;
		}
	}

	/**
	 * 删除购物车信息 -单个删除
	 * 
	 * @param clientId
	 * @param cartVo
	 * @param memberCode
	 * @return
	 */
	public Map<String, Object> delete(String clientId, CartVo cartVo, long memberCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (cartVo == null || StringUtil.isEmpty(cartVo.getProductId()) || StringUtil.isEmpty(cartVo.getMerchantId())) {
				LogCvt.info(String.format("缺少必要的请求参数:", JSON.toJSONString(cartVo)));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
				return result;
			}

			List<ShoppingCartVoReq> list = new ArrayList<ShoppingCartVoReq>();

			ShoppingCartVoReq req = new ShoppingCartVoReq();
			req.setClientId(clientId);
			req.setMemberCode(memberCode);
			req.setProductId(cartVo.getProductId());
			req.setMerchantId(cartVo.getMerchantId());
			list.add(req);

			ResultVo vo = shoppingCartService.deleteBatchCart(list);

			result.put(Results.code, vo.getResultCode());
			result.put(Results.msg, vo.getResultDesc());
			return result;
		} catch (TException e) {
			LogCvt.error("删除购物车信息失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			return result;
		}
	}

	/**
	 * 批量修改购物车商品数量
	 * 
	 * @param clientId
	 * @param cartVOs
	 * @param memberCode
	 * @return
	 */
	public Map<String, Object> quantity(String clientId, CartVo[] cartVOs, long memberCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (cartVOs == null || cartVOs.length <= 0) {
				LogCvt.info(String.format("缺少必要的请求参数:", JSON.toJSONString(cartVOs)));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
				return result;
			}

			ShoppingCartVoReq shoppingCartVoReq = null;

			// 检查是否存在数量更改失败商品
			boolean fail = false;
			ResultVo resultVo = null;
			for (CartVo cartVo : cartVOs) {
				shoppingCartVoReq = new ShoppingCartVoReq();
				shoppingCartVoReq.setProductId(cartVo.getProductId());
				shoppingCartVoReq.setClientId(clientId);
				shoppingCartVoReq.setMemberCode(memberCode);
				shoppingCartVoReq.setMerchantId(cartVo.getMerchantId());
				shoppingCartVoReq.setNum(cartVo.getNum());
				resultVo = shoppingCartService.updateCartByProductNum(shoppingCartVoReq);

				if (!fail && !"0000".equals(resultVo.getResultCode())) {
					fail = true;
					result.put(Results.code, resultVo.getResultCode());
					result.put(Results.msg, resultVo.getResultDesc());
				}

			}
			// 返回第一个错误商品的信息
			if (fail) {
				return result;
			}

			result.put(Results.code, "0000");
			result.put(Results.msg, "修改商品数量成功");
			return result;

		} catch (TException e) {
			LogCvt.error("修改购物车商品数量失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			return result;
		}
	}

	/**
	 * 结算
	 * 
	 * @param clientId
	 * @param cartVOs
	 * @param memberCode
	 * @return
	 */
	public Map<String, Object> settleAccounts(String clientId, CartVo[] cartVOs, long memberCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			if (cartVOs == null || cartVOs.length <= 0) {
				LogCvt.info(String.format("缺少必要的请求参数:",JSON.toJSONString(cartVOs)));
				result.put(Results.code, "1001");
				result.put(Results.msg, "缺少必要的请求参数");
				return result;
			}

			int vipLevel = vipSupport.getVipLevel(memberCode, clientId);
			List<ShoppingCartVoRes> productList = new ArrayList<ShoppingCartVoRes>();
			List<ChangedProductVo> changedProductList = new ArrayList<ChangedProductVo>();
			ChangedProductVo changedProduct = null;
			int changedCounts = 0;

			ShoppingCartVoRes shoppingCartVoRes = null;
			
			for (CartVo cartVo : cartVOs) {
				shoppingCartVoRes = shoppingCartService.getCartByProductId(memberCode, clientId, cartVo.getMerchantId(),cartVo.getProductId(), vipLevel);

				if (!"0000".equals(shoppingCartVoRes.getErrCode())) {
					changedCounts++;

				} else if (!"1".equals(shoppingCartVoRes.getStatus()) // errCode为0000而商品状态正常 1商品正常 7库存紧张 8限购商品
						&& !"7".equals(shoppingCartVoRes.getStatus())
						&& !"8".equals(shoppingCartVoRes.getStatus())) {
					changedCounts++;
				}

				changedProduct = new ChangedProductVo();
				changedProduct.setProductId(shoppingCartVoRes.getProductId());
				changedProduct.setErrCode(shoppingCartVoRes.getErrCode());
				changedProduct.setStatus(shoppingCartVoRes.getStatus());
				changedProduct.setMoney(shoppingCartVoRes.getMoney());
				changedProduct.setVipMoney(shoppingCartVoRes.getVipMoney());
				changedProductList.add(changedProduct);

				productList.add(shoppingCartVoRes);
			}

			if (changedCounts > 0) {
				result.put(Results.result, changedProductList);
				result.put("changedCounts", changedCounts);
				result.put(Results.code, "1001");
				result.put(Results.msg, "商品状态已变动");
				return result;
			} else {

				BigDecimal totalAmount = new BigDecimal("0.00");
				for (ShoppingCartVoRes product : productList) {
					totalAmount = totalAmount.add(AmountUtils
							.getTotalAmount(product));
				}
				result.put("totalAmount", totalAmount);
				return result;
			}

		} catch (TException e) {
			LogCvt.error("购物车结算失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			return result;
		}
	}

	/**
	 * 检查购物车精品预售商品的配送方式
	 * 
	 * @param clientId
	 * @param cartVo
	 * @param memberCode
	 * @return flag 0:没加入过购物车
 	 *				1:加入过购物车，送货上门
 	 *				2:加入过购物车，网点自提方式 (同时返回网点信息)
	 */
	public Map<String, Object> checkDelivery(String clientId, CartVo cartVo, long memberCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (cartVo == null || StringUtil.isEmpty(cartVo.getMerchantId()) || StringUtil.isEmpty(cartVo.getProductId())) {
			LogCvt.info(String.format("缺少必要的请求参数:", JSON.toJSONString(cartVo)));
			result.put(Results.code, "1001");
			result.put(Results.msg, "缺少必要的请求参数");
			return result;
		}

		try {
			
			int vipLevel = vipSupport.getVipLevel(memberCode, clientId);
			
			// 商品信息
			ShoppingCartVoRes cartVoRes = shoppingCartService .getCartByProductId(memberCode, clientId, cartVo.getMerchantId(), cartVo.getProductId(), vipLevel);

			// 该商品没有加入过购物车
			if (StringUtil.empty(cartVoRes.getProductId())) {
				// 直接加入购物车，不需要提示
				result.put("flag", "0");
				result.put("desc", "商品未加入过购物车");
				return result;
			} else if (StringUtil.empty(cartVoRes.getOrgCode())) {
				// 加入过购物车，但没有提货点信息，则属于送货上门
				result.put("flag", "1");
				result.put("desc", "送货上门");
				return result;

			} else if(StringUtil.isNotEmpty(cartVoRes.getOrgCode())){ 
				// 加入过购物车，有提货点信息，则属于网点自提方式
				// 查询网点信息
				OutletVo outletVo = outletService.getBankOutlet(clientId,cartVoRes.getOrgCode());
				result.put("flag", "2");
				result.put("outletId", outletVo.getOutletId());
				result.put("orgCode", cartVoRes.getOrgCode());
				result.put("orgName", cartVoRes.getOrgName());
				return result;
			}else{
				LogCvt.info("购物车商品状态异常：" + JSON.toJSONString(cartVoRes));
				result.put(Results.code, "9999");
				result.put(Results.msg, "购物车商品状态异常");
				return result;
			}

		} catch (TException e) {
			LogCvt.error("检查购物车商品提货网点失败", e);
			result.put(Results.code, "9999");
			result.put(Results.msg, "系统错误");
			return result;
		}
	}
	
	
	
	
	

	/**
	 * cashierPreference:(购物车活动优惠查询接口)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月9日 下午7:57:54
	 * @param clientId
	 * @param memberCode
	 * @param List<PreferenceViewVo>
	 * @return Map  key为商品Id，value 为商品对应活动列表
	 * 
	 */
	public Map<String, Object> carPreference(String clientId , Long memberCode,List<PreferenceViewVo> list ){
		Map<String, Object> result = new HashMap<String, Object>();
		//组装请求体
		ShoppingCartReqVo shoppingCartReqVo =new ShoppingCartReqVo();
		shoppingCartReqVo.setClientId(clientId);
		shoppingCartReqVo.setMemberCode(memberCode);
		//转换请求Vo
		List<ShoppingCartReqProductVo> plist = new ArrayList<ShoppingCartReqProductVo>();
		if(!ArrayUtil.empty(list)){
			ShoppingCartReqProductVo shoppingCartReqProduct= null;
			for(PreferenceViewVo temp : list){
				shoppingCartReqProduct=new ShoppingCartReqProductVo();
				BeanUtils.copyProperties(shoppingCartReqProduct, temp);
				plist.add(shoppingCartReqProduct);
			}
		}
		try{
			shoppingCartReqVo.setShoppingCartReqProductList(plist);		
			ShoppingCartResVo res = activeRunService.goShoppingCart(shoppingCartReqVo);
			if(!ArrayUtil.empty(res.getShoppingCartResProductList())){
				//活动
				for(ShoppingCartResProductVo cartResProductVo : res.getShoppingCartResProductList()  ){
					String pid = cartResProductVo.getProductId(); 
					List<CartResActivePojo> clist = new ArrayList<CartResActivePojo>();
					CartResActivePojo cartResActivePojo = null ;
					//循环取优惠活动信息
					if(!ArrayUtil.empty(cartResProductVo.getShoppingCartResActiveList())){
						for(ShoppingCartResActiveVo cartResActiveVo : cartResProductVo.getShoppingCartResActiveList()){
							cartResActivePojo = new CartResActivePojo();
							BeanUtils.copyProperties(cartResActivePojo, cartResActiveVo);
							clist.add(cartResActivePojo);
						}
					}
					result.put(pid, clist);
				}
				//符合条件的满赠活动	
				List<CartFullGivePojo> flist = new ArrayList<CartFullGivePojo>(); ; 
				CartFullGivePojo cartFullGivePojo;
				if(!ArrayUtil.empty(res.getShoppingCartFullGiveList())){
					for(ShoppingCartFullGiveVo cartFullGiveVo : res.getShoppingCartFullGiveList()){
						cartFullGivePojo = new CartFullGivePojo();
						BeanUtils.copyProperties(cartFullGivePojo, cartFullGiveVo);
						flist.add(cartFullGivePojo);
					}
				}		
				result.put("fullGive", flist);
			}
		}catch(Exception e){
			LogCvt.error("获取商品优惠详情出错", e);
		}		
		return result;
	}
}
