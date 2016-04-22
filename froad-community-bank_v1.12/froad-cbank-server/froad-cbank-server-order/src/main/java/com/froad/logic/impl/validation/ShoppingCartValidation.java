package com.froad.logic.impl.validation;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.Constants;
import com.froad.common.beans.ResultBean;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.enums.SubOrderType;
import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Org;
import com.froad.po.Provider;
import com.froad.po.mongo.ProductActivitiesInfo;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductCityOutlet;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductOutlet;
import com.froad.po.shoppingcart.mongo.ShoppingCart;
import com.froad.po.shoppingcart.req.ShoppingCartReq;
import com.froad.support.CommonSupport;
import com.froad.support.ShoppingCartSupport;
import com.froad.support.impl.CommonSupportImpl;
import com.froad.support.impl.ShoppingCartSupportImpl;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;

import freemarker.template.utility.StringUtil;

/**
 *  购物车参数交易
  * @ClassName: ShoppingCartValidation
  * @Description: TODO
  * @author share 2015年3月21日
  * @modify share 2015年3月21日
 */
public class ShoppingCartValidation {
	
	private final static ShoppingCartSupport shoppingSupport = new ShoppingCartSupportImpl();
	
	private final static CommonSupport commonSupport = new CommonSupportImpl();
	
	//common项目公共
	private final static CommonLogic commonLogic = new CommonLogicImpl();
	
	/**
	 *  校验购物车商品
	  * @Title: validationShoppingCartProduct
	  * @Description: TODO
	  * @author: share 2015年3月21日
	  * @modify: share 2015年3月21日
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	public static ResultBean validationShoppingCartProduct(ShoppingCartReq req){
		
		long memberCode = req.getMemberCode();
		String clientId = req.getClientId();
		String merchantId = req.getMerchantId();
		String outletId = req.getOutletId();
		String productId = req.getProductId();
		int num = req.getNum();
		int vipLevel = req.getVipLevel();
		
		/**
		 * 1、redis获取商户信息
		 */
		Map<String,String> merchantMap = commonLogic.getMerchantRedis(clientId, merchantId);
		if(merchantMap == null){
			return new ResultBean(ResultCode.merchant_not_exist);
		}
		// 商户状态
		String enable = merchantMap.get("is_enable");
		boolean isEnable = BooleanUtils.toBoolean(Integer.parseInt(enable==null || "".equals(enable)?"0":enable)); 
		// 是否银行机构商户 0否：团购商户，1是：预售商户
		//boolean isBankMerchant = BooleanUtils.toBoolean(Integer.parseInt(merchantMap.get("merchant_status")));
		// 商户名称
		String merchantName = merchantMap.get("merchant_name");
		// 商户是否有效
		if(!isEnable){
			return new ResultBean(ResultCode.merchant_enable);
		}
		/**
		 * 2、redis获取商品信息，mongo获取商品详情信息
		 */
		Map<String,String> productMap = commonLogic.getProductRedis(clientId, merchantId, productId);
		// 剩余库存量
		int store = commonLogic.getStoreRedis(clientId, merchantId, productId);
		// mongo获取商品信息
		ProductDetail detail = commonSupport.queryProductDetail(productId);
		if(productMap == null || detail == null){
			return new ResultBean(ResultCode.prodcut_not_exist);
		}
		// 商户类型
		String merchantType = getMerchantType(detail.getProductType());
		if(merchantType == null){
			return new ResultBean(ResultCode.merchant_type_error);
		}
		// 0-未上架 1-上架 2-下架
		int status = Integer.parseInt(productMap.get("is_marketable"));
		if(status != 1){
			return new ResultBean(ResultCode.product_un_market);
		}
		// 销售价
		int price = detail.getPrice();	
		// 商品名称
		String productName = detail.getName();	
		// 是否限购
		boolean isLimit = BooleanUtils.toBoolean(detail.getIsLimit());	
		// 商品图片
		String productImage = detail.getImageInfo()==null? "" : detail.getImageInfo().get(0).getThumbnail();
		// 提货网点
		List<ProductCityOutlet> outletList = detail.getOrgOutlets();
		// 运费
		int devliveryMoney = productMap.get("delivery_money")==null?0:Integer.parseInt(productMap.get("delivery_money"));
		// vip价格
		Integer priceVip = 0;	
		// 机构网点
		String orgCode = null;	
		// 机构网点名称
		String orgName = null;	
		// 门店状态
		Integer orgStatus = null;
		
		/**
		 *  3、检查商品购买开始和结束时间
		 */
		// 开始日期
		long startTime = Long.parseLong(productMap.get("start_time").toString());
		// 结束日期
		long endTime = Long.parseLong(productMap.get("end_time").toString()); 
		long curTime = System.currentTimeMillis();
		if(curTime < startTime || curTime > endTime){
			return new ResultBean(ResultCode.product_not_in_time);
		}
		
		/**
		 * 4、如果是预售商品，检查门店信息
		 */
//		long activityId = 0l;
		if(ProductType.presell.getCode().equals(detail.getProductType())){
			if(outletId != null && !"".equals(outletId)){
				if(!checkOulet(outletList, outletId)){
					return new ResultBean(ResultCode.failed,"提货网点不在商品预售网点内");
				}
				Org org = commonLogic.queryByOutleId(clientId, outletId);
				orgStatus = BooleanUtils.toIntegerObject(org.getIsEnable());
				orgCode = org.getOrgCode();
				orgName = org.getOrgName();
				if(orgStatus == 0){
					return new ResultBean(ResultCode.org_enable);
				}
			}
			//2015.07.27 去掉VIP活动， VIP价格从mongo中取，如果字段为null,则商品没有VIP价格
			/*// 预售检查下VIP活动
			if(detail.getActivitiesInfo() !=null && !detail.getActivitiesInfo().isEmpty()){
				for(ProductActivitiesInfo activityInfo : detail.getActivitiesInfo()){
					// VIP价
					if("0".equals(activityInfo.getActivitiesType())){
						activityId = activityInfo.getActivitiesId();
						break;
					}
				}
			}*/
			// 预售下VIP价格
			if(EmptyChecker.isNotEmpty(detail.getVipPrice()) && detail.getVipPrice()>0){
				priceVip = detail.getVipPrice();
			}
			
		}
		
		/**
		 * 5、检查库存数量
		 */
		// 获取用户购物车商品存在的数量
		ShoppingCart product = shoppingSupport.queryShoppingCart(clientId, memberCode, productId);
		int dbQuantity = 0;
		int dbVipQuantity = 0;
		if(product != null){
			dbQuantity = product.getQuantity();
			dbVipQuantity = product.getVipQuantity();
		}
		int totalQuantity = num + dbQuantity + dbVipQuantity;
		if(store < totalQuantity){
			return new ResultBean(ResultCode.shopping_cart_buy_limit);
		}
		
		/**
		 *  6、检查限购信息，分配限购数量
		 */
		if(isLimit){
			// 限购商品
			status = 8;
			ProductBuyLimit buyLimit = detail.getBuyLimit();
			curTime = System.currentTimeMillis();
			
			// 查询用户订单表的购买记录
			Map<String,String> memberBuyMap = RedisCommon.getUserOrderCountRedis(clientId, memberCode, productId);
			int orderQuantity = 0;
			int orderVipQuantity = 0;
			int totalHistoryBuyQuantity = 0;
			if(memberBuyMap != null && memberBuyMap.size() > 0){
				if(EmptyChecker.isNotEmpty(memberBuyMap.get("count"))){
					orderQuantity = Integer.parseInt(memberBuyMap.get("count").toString());
				}
				if(EmptyChecker.isNotEmpty(memberBuyMap.get("vip_count"))){
					orderVipQuantity = Integer.parseInt(memberBuyMap.get("vip_count").toString());
				}
				totalHistoryBuyQuantity = orderQuantity + orderVipQuantity;
			}
			
			if((buyLimit.getStartTime() <= curTime && buyLimit.getEndTime() >= curTime) || (buyLimit.getStartTime() <= curTime && buyLimit.getEndTime() == 0)){
				// 检查商品是否在限购数量内
				if(totalQuantity + totalHistoryBuyQuantity > buyLimit.getMax() && buyLimit.getMax() > 0){
					return new ResultBean(ResultCode.shopping_cart_buy_limit);
				}
				
				//用户是vip、商品有VIP价
				if(vipLevel > 0 && priceVip > 0){
					int maxVipLimit = buyLimit.getMaxVip();
					if(maxVipLimit > 0){
						int maxVip = maxVipLimit - orderVipQuantity > 0 ? maxVipLimit - orderVipQuantity : 0;
						dbVipQuantity = totalQuantity > maxVip? maxVip : totalQuantity;
						/*int max = buyLimit.getMax() - orderQuantity > 0 ? buyLimit.getMax() - orderQuantity : 0;
						dbQuantity = totalQuantity -dbVipQuantity > max ? max : totalQuantity -dbVipQuantity;*/
						dbQuantity = totalQuantity -dbVipQuantity;
					}else{//如果vip不限购
						dbVipQuantity = totalQuantity;
						dbQuantity = 0;
					}
				}else{//如果不是vip 或  没有VIP价
					dbVipQuantity = 0;
					/*int max = buyLimit.getMax() - orderQuantity > 0 ? buyLimit.getMax() - orderQuantity : 0;
					dbQuantity = totalQuantity -dbVipQuantity > max ? max : totalQuantity -dbVipQuantity;*/
					dbQuantity = totalQuantity -dbVipQuantity;
				}
			}else{//如果不在限购期内，不限购
				//用户是vip、商品有VIP价
				int maxVipLimit = buyLimit.getMaxVip();
				if(maxVipLimit > 0){
					int maxVip = maxVipLimit - orderVipQuantity > 0 ? maxVipLimit - orderVipQuantity : 0;
					dbVipQuantity = totalQuantity > maxVip? maxVip : totalQuantity;
					dbQuantity = totalQuantity -dbVipQuantity > 0 ? totalQuantity -dbVipQuantity : 0;
				}else{//如果vip不限购
					dbVipQuantity = totalQuantity;
					dbQuantity = 0;
				}
			}
		}else{//都不限购
			//用户是vip、商品有VIP价
			if(vipLevel > 0 && priceVip > 0){
				dbVipQuantity = totalQuantity;
				dbQuantity = 0;
			}else{//如果不是vip 或  没有VIP价
				dbQuantity = totalQuantity;
				dbVipQuantity = 0;
			}
		}
		
		
		/**
		 *  7、检查活动信息
		 */
		/*if(activityId != 0 && vipLevel > 0){
			Map<String,String> activityMap = RedisCommon.getActivityRedis(clientId,activityId);
			if(activityMap == null){
				return new ResultBean(ResultCode.failed,"商品VIP活动信息不存在");
			}
			String priceJson = activityMap.get("vip_price");
			startTime = Long.parseLong(activityMap.get("begin_time"));
			endTime = Long.parseLong(activityMap.get("end_time"));
			curTime = System.currentTimeMillis();
			if(startTime <= curTime && endTime >= curTime){
				Object priceInfo = JSonUtil.toObject(priceJson, Map.class).get(vipLevel);
				if(priceInfo == null){
					return new ResultBean(ResultCode.failed,"商品VIP活动VIP价不存在");
				}
				priceVip = Integer.parseInt(priceInfo.toString());
			}else{
				dbQuantity = dbQuantity + dbVipQuantity;
				dbVipQuantity = 0;
			}
		}*/
		
		/**
		 *  8、检查商品数量
		 */
		if(dbQuantity + dbVipQuantity > Constants.SHOPPING_CART_CLIENT_PRODUCT_NUM_LIMIT){
			return new ResultBean(ResultCode.product_num_enough);
		}
		
		/**
		 * 9、组装购物车信息
		 */
		// 数据转换
		ShoppingCart cart = new ShoppingCart();
		cart.setClientId(clientId);
		cart.setMemberCode(memberCode);
		// 商户信息
		cart.setMerchantId(merchantId);
		cart.setMerchantName(merchantName);
		cart.setMerchantStatus(BooleanUtils.toInteger(isEnable));
		cart.setType(merchantType);
		// 商品信息
		cart.setProductId(productId);
		cart.setProductName(productName);
		cart.setProductImage(productImage);
		cart.setMoney(price);
		cart.setVipMoney(priceVip);
		cart.setQuantity(dbQuantity);
		cart.setVipQuantity(dbVipQuantity);
		cart.setStatus(String.valueOf(status));
		// 预售才有门店ID
		cart.setOrgCode(orgCode);
		cart.setOrgName(orgName);
		cart.setOrgStatus(orgStatus);
		cart.setTime(System.currentTimeMillis());
		cart.setDeliveryMoney(devliveryMoney);
		
		ResultBean bean = new ResultBean(ResultCode.success, cart);
		return bean;
	}
	
	/**
	 * 精品商城商品购物车校验
	 * validationBoutiqueShoppingCart:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年11月26日 上午11:40:02
	 * @param req
	 * @return
	 *
	 */
	public static ResultBean validationBoutiqueShoppingCart(ShoppingCartReq req) {
		long memberCode = req.getMemberCode();
		String clientId = req.getClientId();
		String merchantId = req.getMerchantId();
		String productId = req.getProductId();
		int num = req.getNum();
		int vipLevel = req.getVipLevel();
		
		/*** 1.检查商品供应商是否存在*/
		Map<String, String>  providerMap = commonLogic.getProviderRedis(merchantId);
		if(providerMap == null) {
			return new ResultBean(ResultCode.provider_not_exist);
		}
		String merchantName = providerMap.get("merchant_name");
		
		
		/*** 2.检查商品信息是否存在*/
		//获取商品信息
		Map<String, String> productMap = commonLogic.getProductRedis(clientId, merchantId, productId);
		//剩余库存量
		int store = commonLogic.getStoreRedis(clientId, merchantId, productId);
		//mongo获取商品信息
		ProductDetail detail = commonSupport.queryProductDetail(productId);
		if(productMap == null || detail == null){
			return new ResultBean(ResultCode.prodcut_not_exist);
		}
		// 商户类型
		String merchantType = getMerchantType(detail.getProductType());
		if(merchantType == null){
			return new ResultBean(ResultCode.merchant_type_error);
		}
		
		
		/*** 3.检查商品上下架状态*/
		//0-未上架 1-上架 2-下架
		int status = Integer.parseInt(productMap.get("is_marketable"));
		if(status != 1){
			return new ResultBean(ResultCode.product_un_market);
		}
		//销售价
		int price = detail.getPrice();	
		//商品名称
		String productName = detail.getName();	
		//是否限购
		boolean isLimit = BooleanUtils.toBoolean(detail.getIsLimit());	
		//商品图片
		String productImage = EmptyChecker.isEmpty(detail.getImageInfo()) ? "" : detail.getImageInfo().get(0).getThumbnail();
		//运费
		int devliveryMoney = EmptyChecker.isEmpty(productMap.get("delivery_money"))?0:Integer.parseInt(productMap.get("delivery_money"));
		//vip价格
		Integer priceVip = 0;
		if(EmptyChecker.isNotEmpty(detail.getVipPrice()) && detail.getVipPrice() > 0) {
			priceVip = detail.getVipPrice();
		}
		
		/**
		 * 检查是否过了预计发货日期
		 */
		String dtStr = productMap.get("delivery_time");
		if(StringUtils.isNotEmpty(dtStr)) {
			long deliveryTime = Long.parseLong(productMap.get("delivery_time"));
			String deliveryTimeStr = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, deliveryTime);
			if(DateUtil.dateCompare(deliveryTimeStr)) {
				return new ResultBean(ResultCode.product_no_in_delivery_time);
			}
		}
		
		/*** 4.检查商品库存数量**/
		//获取用户购物车商品存在的数量
		ShoppingCart product = shoppingSupport.queryShoppingCart(clientId, memberCode, productId);
		//会员添加当前商品已添加购物车的普通购买数量
		int dbQuantity = 0;
		//会员添加当前商品已添加购物车的vip购买数量
		int dbVipQuantity = 0;
		if(product != null){
			dbQuantity = product.getQuantity();
			dbVipQuantity = product.getVipQuantity();
		}
		//本次添加购物车商品数量和购物车已存在的当前商品数量
		int totalQuantity = num + dbQuantity + dbVipQuantity;
		//比较当前商品库存量和当前购买数量
		if(store < totalQuantity){
			return new ResultBean(ResultCode.shopping_cart_buy_limit);
		}
		
		
		/***5.检查限购信息，分配限购数量；ps:精品商城商品最低限购现阶段业务没用到，故不作相关校验*/
		if(isLimit){
			status = 8;
			ProductBuyLimit buyLimit = detail.getBuyLimit();
			// 查询用户订单表的购买记录
			Map<String,String> memberBuyMap = RedisCommon.getUserOrderCountRedis(clientId, memberCode, productId);
			//会员普通购买已购买数量
			int orderQuantity = 0;
			//会员vip购买已购买数量
			int orderVipQuantity = 0;
			//会员购买当前商品的总购买历史数量
			int totalHistoryBuyQuantity = 0;
			if(memberBuyMap != null && memberBuyMap.size() > 0){
				if(EmptyChecker.isNotEmpty(memberBuyMap.get("count"))){
					orderQuantity = Integer.parseInt(memberBuyMap.get("count").toString());
				}
				if(EmptyChecker.isNotEmpty(memberBuyMap.get("vip_count"))){
					orderVipQuantity = Integer.parseInt(memberBuyMap.get("vip_count").toString());
				}
				totalHistoryBuyQuantity = orderQuantity + orderVipQuantity;
			}
			
			// 检查商品是否在限购数量内
			if(totalQuantity + totalHistoryBuyQuantity > buyLimit.getMax() && buyLimit.getMax() > 0){
				return new ResultBean(ResultCode.shopping_cart_buy_limit);
			}
			
			//用户是vip、商品有VIP价
			if(vipLevel > 0 && priceVip > 0){
				//vip限购数量
				int maxVipLimit = buyLimit.getMaxVip();
				if(maxVipLimit > 0){//vip限购
					int maxVip = maxVipLimit - orderVipQuantity > 0 ? maxVipLimit - orderVipQuantity : 0;
					dbVipQuantity = totalQuantity > maxVip? maxVip : totalQuantity;
					/*int max = buyLimit.getMax() - orderQuantity > 0 ? buyLimit.getMax() - orderQuantity : 0;
					dbQuantity = totalQuantity -dbVipQuantity > max ? max : totalQuantity -dbVipQuantity;*/
					dbQuantity = totalQuantity -dbVipQuantity;
				}else{//如果普通限购、vip不限购
					dbVipQuantity = totalQuantity;
					dbQuantity = 0;
				}
			}else{//如果不是vip 或  没有VIP价
				dbVipQuantity = 0;
				/*int max = buyLimit.getMax() - orderQuantity > 0 ? buyLimit.getMax() - orderQuantity : 0;
				dbQuantity = totalQuantity -dbVipQuantity > max ? max : totalQuantity -dbVipQuantity;*/
				dbQuantity = totalQuantity -dbVipQuantity;
			}
		}else{//都不限购
			//用户是vip、商品有VIP价
			if(vipLevel > 0 && priceVip > 0){
				dbVipQuantity = totalQuantity;
				dbQuantity = 0;
			}else{//如果不是vip 或  没有VIP价
				dbQuantity = totalQuantity;
				dbVipQuantity = 0;
			}
		}
		
		
		/*** 5.检查会员当前商品购物车商品数量是否超限*/
		if(dbQuantity + dbVipQuantity > Constants.SHOPPING_CART_CLIENT_PRODUCT_NUM_LIMIT){
			return new ResultBean(ResultCode.product_num_enough);
		}
		
		
		/*** 6.组装购物车信息*/
		ShoppingCart cart = new ShoppingCart();
		cart.setClientId(clientId);
		cart.setMemberCode(memberCode);
		// 供应商信息
		cart.setMerchantId(merchantId);
		cart.setMerchantName(merchantName);
		cart.setMerchantStatus(BooleanUtils.toInteger(true));
		cart.setType(merchantType);
		// 商品信息
		cart.setProductId(productId);
		cart.setProductName(productName);
		cart.setProductImage(productImage);
		cart.setMoney(price);
		cart.setVipMoney(priceVip);
		cart.setQuantity(dbQuantity);
		cart.setVipQuantity(dbVipQuantity);
		cart.setStatus(String.valueOf(status));
		cart.setTime(System.currentTimeMillis());
		cart.setDeliveryMoney(devliveryMoney);
		ResultBean bean = new ResultBean(ResultCode.success, cart);
		return bean;
	}
	
	
	/**
	 *  获取商户类型
	  * @Title: getMerchantType
	  * @Description: TODO
	  * @author: share 2015年4月1日
	  * @modify: share 2015年4月1日
	  * @param @param productType
	  * @param @return    
	  * @return String    
	  * @throws
	 */
	public static String getMerchantType(String productType){
		
		if(ProductType.presell.getCode().equals(productType)){
			// 预售
			return SubOrderType.presell_org.getCode();
		}else if(ProductType.special.getCode().equals(productType)){
			// 名优特惠
			return SubOrderType.special_merchant.getCode();
		}else if(ProductType.group.getCode().equals(productType)){
			// 团购
			return SubOrderType.group_merchant.getCode();
		}else if(ProductType.boutique.getCode().equals(productType)) {
			//精品商城
			return SubOrderType.boutique.getCode();
		}
		return null;
	}
	
	/**
	 *  检查提货网点是否正确
	  * @Title: checkOulet
	  * @Description: TODO
	  * @author: share 2015年4月14日
	  * @modify: share 2015年4月14日
	  * @param @param outletList
	  * @param @param outletId
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public static boolean checkOulet(List<ProductCityOutlet> outletList,String outletId){
		if(outletList == null || outletList.isEmpty()){
			return false;
		}
		
		for(ProductCityOutlet cityOutlet : outletList){
			if(EmptyChecker.isNotEmpty(cityOutlet.getOrgOutlets())){
				for(ProductOutlet outlet : cityOutlet.getOrgOutlets()){
					if(outlet.getOutletId().equals(outletId)){
						return true;
					}
				}
			}
		}
		return false;
		
	}
	
	
}

