package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.Constants;
import com.froad.common.beans.ResultBean;
import com.froad.db.redis.RedisCommon;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.ShoppingCartLogic;
import com.froad.logic.impl.validation.ShoppingCartValidation;
import com.froad.po.Org;
import com.froad.po.mongo.ProductBuyLimit;
import com.froad.po.mongo.ProductCityOutlet;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.shoppingcart.mongo.ShoppingCart;
import com.froad.po.shoppingcart.req.OrderShoppingListReq;
import com.froad.po.shoppingcart.req.ShoppingCartReq;
import com.froad.support.CommonSupport;
import com.froad.support.ShoppingCartSupport;
import com.froad.support.impl.CommonSupportImpl;
import com.froad.support.impl.ShoppingCartSupportImpl;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.PropertiesUtil;

/**
 *  购物车逻辑层
  * @ClassName: ShoppingCartLogicImpl
  * @Description: TODO
  * @author share 2015年3月18日
  * @modify share 2015年3月18日
 */
public class ShoppingCartLogicImpl implements ShoppingCartLogic{
	
	static{
		PropertiesUtil.load();
	}

	private final ShoppingCartSupport shoppingSupport = new ShoppingCartSupportImpl();
	
	private final CommonSupport commonSupport = new CommonSupportImpl();
	
	//common项目公共
	private final static CommonLogic commonLogic = new CommonLogicImpl();
	
	/**
	 *  添加进购物车
	  * @Title: addCart
	  * @Description: TODO
	  * @author: share 2015年3月18日
	  * @modify: share 2015年3月18日
	  * @param @param cart
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#addCart(com.froad.po.ShoppingCart)
	 */
	@Override
	public ResultBean addCart(ShoppingCartReq req) {
		/**
		 * 1、校验添加购物车信息
		 */
		LogCvt.info("校验购物车商品开始:"+JSonUtil.toJSonString(req));
		Map<String,String> productMap = commonLogic.getProductRedis(req.getClientId(), req.getMerchantId(), req.getProductId());
		if(productMap == null ){
			return new ResultBean(ResultCode.prodcut_not_exist);
		}
		String productType = productMap.get("product_type");
		ResultBean result = null;
		if(ProductType.boutique.getCode().equals(productType)) {
			//精品商城商品购物车校验
			result = ShoppingCartValidation.validationBoutiqueShoppingCart(req);
		} else {
			result = ShoppingCartValidation.validationShoppingCartProduct(req);
		}
		
		LogCvt.info("校验购物车商品结果:"+result.getCode()+","+result.getMsg());
		if(!ResultCode.success.getCode().equals(result.getCode())){
			return result;
		}
		/**
		 * 2、添加至购物车
		 */
		ShoppingCart cart = (ShoppingCart)result.getData();
		//客户端ID
		String clientId = cart.getClientId(); 
		//会员号
		long memberCode = cart.getMemberCode();
		String productId = cart.getProductId(); 
		// 检查用户购物车商品数量是否超限，商品最多50
		int totalProducts = shoppingSupport.queryMemberShoppingProductCount(clientId, memberCode);
		// 检查是否存在改商品
		int productCount = shoppingSupport.queryMemberShoppingByProductIdCount(clientId, memberCode, productId);
		if(totalProducts > Constants.SHOPPING_CART_CLIENT_PRODUCT_LIMIT && productCount == 0){
			return new ResultBean(ResultCode.shopping_cart_limit);
		}
		// 更新商品信息,
		boolean flag = shoppingSupport.insertOrUpdateProduct(cart);
		return getResultBean(flag);
	}

	/**
	 *  修改单个商品数量
	  * @Title: deleteCartByProductId
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @param productId
	  * @param @param num
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#deleteCartByProductId(long, long, long, long, int)
	 */
	@Override
	public ResultBean updateCartByProductNum(long memberCode, String clientId,String merchantId, String productId, int num,int vipLevel) {
		//判断是否是精品商城
		Map<String,String> tmproductMap = commonLogic.getProductRedis(clientId, merchantId, productId);
		String productType = tmproductMap.get("product_type");
		//如果是精品商城商品
		if(ProductType.boutique.getCode().equals(productType)) {
			return updateBoutiqueCartByProductNum(memberCode, clientId, merchantId, productId, num, vipLevel);
		} 
		
		LogCvt.info("修改用户购物车单个商品:memberCode="+memberCode+";clientId="+clientId+";productId="+productId+";num="+num);
		// 获取购物车商品信息
		ShoppingCart product = shoppingSupport.queryShoppingCart(clientId, memberCode, productId);
		if(product == null){
			return new ResultBean(ResultCode.failed,"未找到用户购物车商品信息");
		}
		int quantity = 0;
		int vipProdcutNum = 0;
		// mongo获取商品信息
		ProductDetail detail = commonSupport.queryProductDetail(productId);
		// 是否限购
		boolean isLimit = BooleanUtils.toBoolean(detail.getIsLimit());	
		// 剩余库存量
		int store = commonLogic.getStoreRedis(clientId, merchantId, productId);
		// 检查库存
		if(store < num){
			return new ResultBean(ResultCode.shopping_cart_store_over);
		}
		/**
		 * 如果是预售商品，是否有优惠活动
		 */
//		long activityId = 0l;
		// vip价格
		Integer priceVip = 0;
		if(ProductType.presell.getCode().equals(detail.getProductType())){
			// 检查商品活动VIP价格
			/*if(detail.getActivitiesInfo() !=null && !detail.getActivitiesInfo().isEmpty()){
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
				LogCvt.info("商品vip价发生变动，原价："+product.getVipMoney()+"，新价："+priceVip);
				product.setVipMoney(priceVip);
			}
		}else{
			quantity = num;
			vipProdcutNum = 0;
		}
		/**
		 *  检查限购信息，分配限购数量
		 */
		if(isLimit){
			ProductBuyLimit buyLimit = detail.getBuyLimit();
			long curTime = System.currentTimeMillis();
			
			// 查询用户订单表的购买记录
			Map<String,String> memberBuyMap = RedisCommon.getUserOrderCountRedis(clientId, memberCode, productId);
			int orderQuantity = 0;
			int orderVipQuantity = 0;
			if(memberBuyMap != null && memberBuyMap.size() > 0 ){
				if(EmptyChecker.isNotEmpty(memberBuyMap.get("count"))){
					orderQuantity = Integer.parseInt(memberBuyMap.get("count").toString());
				}
				if(EmptyChecker.isNotEmpty(memberBuyMap.get("vip_count"))){
					orderVipQuantity = Integer.parseInt(memberBuyMap.get("vip_count").toString());
				}
			}
			
			if((buyLimit.getStartTime() <= curTime && buyLimit.getEndTime() >= curTime) || (buyLimit.getStartTime() <= curTime && buyLimit.getEndTime() == 0)){
				// 检查商品是否在限购数量内
				if(num + orderQuantity+orderVipQuantity > buyLimit.getMax() && buyLimit.getMax() > 0){
					return new ResultBean(ResultCode.shopping_cart_limit_over);
				}
				// 如果是会员，并且在活动时间内有VIP优惠价，重新排列VIP优惠数量
				if(vipLevel > 0 && priceVip > 0){
					int maxVipLimit = buyLimit.getMaxVip();
					if(maxVipLimit > 0){
						int maxVip = maxVipLimit - orderVipQuantity > 0 ? maxVipLimit - orderVipQuantity : 0;
						vipProdcutNum = num > maxVip? maxVip : num;
						/*int max = buyLimit.getMax() - orderQuantity > 0 ? buyLimit.getMax() - orderQuantity : 0;
						quantity = num -vipProdcutNum > max ? max : num -vipProdcutNum;*/
						quantity = num -vipProdcutNum > 0 ? num -vipProdcutNum : 0;
					}else{//如果vip不限购
						vipProdcutNum = num;
						quantity = 0;
					}
				}else{
					vipProdcutNum = 0;
					/*int max = buyLimit.getMax() - orderQuantity > 0 ? buyLimit.getMax() - orderQuantity : 0;
					quantity = num -vipProdcutNum > max ? max : num -vipProdcutNum;*/
					quantity = num -vipProdcutNum > 0 ? num -vipProdcutNum : 0;
				}
			}else{
				//用户是vip、商品有VIP价
				if(vipLevel > 0 && priceVip > 0){
					int maxVipLimit = buyLimit.getMaxVip();
					if(maxVipLimit > 0){
						int maxVip = maxVipLimit - orderVipQuantity > 0 ? maxVipLimit - orderVipQuantity : 0;
						vipProdcutNum = num > maxVip? maxVip : num;
						quantity = num -vipProdcutNum > 0 ? num -vipProdcutNum : 0;
					}else{//如果vip不限购
						vipProdcutNum = num;
						quantity = 0;
					}
				}else{
					vipProdcutNum = 0;
					quantity = num -vipProdcutNum > 0 ? num -vipProdcutNum : 0;
				}
			}
		}else{
			//用户是vip、商品有VIP价
			if(vipLevel > 0 && priceVip > 0){
				vipProdcutNum = num;
				quantity = 0;
			}else{//如果不是vip 或  没有VIP价
				quantity = num;
				vipProdcutNum = 0;
			}
		}
		
		product.setQuantity(quantity);
		product.setVipQuantity(vipProdcutNum);
		// 更新商品信息
		boolean result = shoppingSupport.updateProductQuantity(product);
		return getResultBean(result);
	}

	/**
	 * 修改单个精品商城商品数量
	 */
	public ResultBean updateBoutiqueCartByProductNum(long memberCode, String clientId,String merchantId, String productId, int num,int vipLevel) {
		LogCvt.info("修改用户购物车单个精品商城商品:memberCode="+memberCode+";clientId="+clientId+";productId="+productId+";num="+num);
		// 获取购物车商品信息
		ShoppingCart product = shoppingSupport.queryShoppingCart(clientId, memberCode, productId);
		if(product == null){
			return new ResultBean(ResultCode.failed,"未找到用户购物车商品信息");
		}
		int quantity = 0;
		int vipProdcutNum = 0;
		// mongo获取商品信息
		ProductDetail detail = commonSupport.queryProductDetail(productId);
		// 是否限购
		boolean isLimit = BooleanUtils.toBoolean(detail.getIsLimit());	
		// 剩余库存量
		int store = commonLogic.getStoreRedis(clientId, merchantId, productId);
		// 检查库存
		if(store < num){
			return new ResultBean(ResultCode.shopping_cart_store_over);
		}
		
		// vip价格
		Integer priceVip = 0;
		if(EmptyChecker.isNotEmpty(detail.getVipPrice()) && detail.getVipPrice()>0){
			priceVip = detail.getVipPrice();
			LogCvt.info("精品商城商品vip价发生变动，原价："+product.getVipMoney()+"，新价："+priceVip);
			product.setVipMoney(priceVip);
		}
		
		/***  检查限购信息，分配限购数量*/
		if(isLimit){
			ProductBuyLimit buyLimit = detail.getBuyLimit();
			// 查询用户订单表的购买记录
			Map<String,String> memberBuyMap = RedisCommon.getUserOrderCountRedis(clientId, memberCode, productId);
			int orderQuantity = 0;
			int orderVipQuantity = 0;
			if(memberBuyMap != null && memberBuyMap.size() > 0 ){
				if(EmptyChecker.isNotEmpty(memberBuyMap.get("count"))){
					orderQuantity = Integer.parseInt(memberBuyMap.get("count").toString());
				}
				if(EmptyChecker.isNotEmpty(memberBuyMap.get("vip_count"))){
					orderVipQuantity = Integer.parseInt(memberBuyMap.get("vip_count").toString());
				}
			}
			
			// 检查商品是否在限购数量内
			if(num + orderQuantity+orderVipQuantity > buyLimit.getMax() && buyLimit.getMax() > 0){
				return new ResultBean(ResultCode.shopping_cart_limit_over);
			}
			// 如果是会员，并且在活动时间内有VIP优惠价，重新排列VIP优惠数量
			if(vipLevel > 0 && priceVip > 0){
				int maxVipLimit = buyLimit.getMaxVip();
				if(maxVipLimit > 0){
					int maxVip = maxVipLimit - orderVipQuantity > 0 ? maxVipLimit - orderVipQuantity : 0;
					vipProdcutNum = num > maxVip? maxVip : num;
					/*int max = buyLimit.getMax() - orderQuantity > 0 ? buyLimit.getMax() - orderQuantity : 0;
					quantity = num -vipProdcutNum > max ? max : num -vipProdcutNum;*/
					quantity = num -vipProdcutNum > 0 ? num -vipProdcutNum : 0;
				}else{//如果vip不限购
					vipProdcutNum = num;
					quantity = 0;
				}
			}else{
				vipProdcutNum = 0;
				/*int max = buyLimit.getMax() - orderQuantity > 0 ? buyLimit.getMax() - orderQuantity : 0;
				quantity = num -vipProdcutNum > max ? max : num -vipProdcutNum;*/
				quantity = num -vipProdcutNum > 0 ? num -vipProdcutNum : 0;
			}
		}else{
			//用户是vip、商品有VIP价
			if(vipLevel > 0 && priceVip > 0){
				vipProdcutNum = num;
				quantity = 0;
			}else{//如果不是vip 或  没有VIP价
				quantity = num;
				vipProdcutNum = 0;
			}
		}
		
		product.setQuantity(quantity);
		product.setVipQuantity(vipProdcutNum);
		// 更新商品信息
		boolean result = shoppingSupport.updateProductQuantity(product);
		return getResultBean(result);
	}
	
	/**
	 *  清空购物车
	  * @Title: deleteCart
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#deleteCart(long, long)
	 */
	@Override
	public boolean deleteCart(long memberCode, String clientId) {
		// TODO Auto-generated method stub
		LogCvt.info("清空用户购物车单个商品:memberCode="+memberCode+";clientId="+clientId);
		int result = shoppingSupport.deleteCart(memberCode, clientId);
		return result != -1;
	}

	/**
	 *  获取购物车商品数量
	  * @Title: getCartCount
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#getCartCount(long, long)
	 */
	@Override
	public int getCartCount(long memberCode, String clientId) {
		// TODO Auto-generated method stub
		LogCvt.info("获取用户购物商品数量:memberCode="+memberCode+";clientId="+clientId);
		return shoppingSupport.countMemberCarts(clientId, memberCode);
	}

	/**
	 *  拉取用户购物车信息
	  * @Title: getCart
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#getCart(long, long)
	 */
	@Override
	public List<ShoppingCart> getCart(long memberCode, String clientId,int vipLevel) {
		// TODO Auto-generated method stub
		LogCvt.info("拉取用户购物车商品，请求参数：{memberCode:"+memberCode+", clientId:"+clientId+", vipLevel:"+vipLevel+"}");
		List<ShoppingCart> cartList = shoppingSupport.queryShoppingCart(clientId, memberCode);
		if(cartList == null || cartList.isEmpty()){
			return cartList;
		}
		
		List<ShoppingCart> cartResList = new ArrayList<ShoppingCart>();
		
		// 拉取购物车的商品的信息
		for(ShoppingCart shopping : cartList){
			cartResList.add(getCartByProductId(memberCode, clientId, shopping.getMerchantId(), shopping.getProductId(), vipLevel));
		}
		
		return cartResList;
	}

	/**
	 *  拉取用户购物车单个商品信息
	  * @Title: getCartByProductId
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @param productId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#getCartByProductId(long, long, long, long)
	 */
	@Override
	public ShoppingCart getCartByProductId(long memberCode, String clientId,String merchantId, String productId,int vipLevel) {
		// TODO Auto-generated method stub
		//判断是否是精品商城
		Map<String,String> tmproductMap = commonLogic.getProductRedis(clientId, merchantId, productId);
		String productType = tmproductMap.get("product_type");
		//如果是精品商城商品
		if(ProductType.boutique.getCode().equals(productType)) {
			return getBoutiqueCartByProductId(memberCode, clientId, merchantId, productId, vipLevel);
		} 
		
		LogCvt.info("拉取用户购物车单个商品，请求参数：{memberCode:"+memberCode+",clientId:"+clientId+",merchantId="+merchantId+",productId:"+productId+",vipLevel:"+vipLevel+"}");
		ShoppingCart shoppingCart = null;
		boolean flag = false;
		try {
			shoppingCart = shoppingSupport.queryShoppingCart(clientId, memberCode, productId);
			if(shoppingCart == null){
				shoppingCart = new ShoppingCart();
				shoppingCart.setErrCode(ResultCode.failed.getCode());
				shoppingCart.setErrMsg("商品信息不存在");
				return shoppingCart;
			}
			boolean status = BooleanUtils.toBoolean(shoppingCart.getMerchantStatus());
			// redis获取商户信息
			Map<String,String> merchantMap = commonLogic.getMerchantRedis(clientId, merchantId);
			// 商户名称
			String merchantName = merchantMap.get("merchant_name");
			if(!merchantName.equals(shoppingCart.getMerchantName())){
				shoppingCart.setMerchantName(merchantName);
				flag = true;
			}
			// 商户状态
			// 商户状态
			String enable = merchantMap.get("is_enable");
			boolean isEnable = BooleanUtils.toBoolean(Integer.parseInt(enable==null || "".equals(enable)?"0":enable)); 
			if(status != isEnable){
				flag = true;
				shoppingCart.setMerchantStatus(BooleanUtils.toInteger(isEnable));
				// 检查商户状态是否无效
				if(!isEnable){
					shoppingCart.setStatus(Constants.PRODUCT_INVALID_6);
					shoppingCart.setErrCode(ResultCode.failed.getCode());
					shoppingCart.setErrMsg("商户状态无效");
					return shoppingCart;
				}
			}
			
			Map<String,String> productMap = commonLogic.getProductRedis(clientId, merchantId, productId);
			// 剩余库存量
			int store = commonLogic.getStoreRedis(clientId, merchantId, productId);
			// mongo获取商品信息
			ProductDetail detail = commonSupport.queryProductDetail(productId);
			// 0-未上架 1-上架 2-下架
			ProductStatus productStatus = ProductStatus.getType(productMap.get("is_marketable"));
			// 销售价
			int price = detail.getPrice();		
			// 商品名称
			String productName = detail.getName();	
			// 是否限购
			boolean isLimit = BooleanUtils.toBoolean(detail.getIsLimit());	
			// 商品图片
			String productImage = detail.getImageInfo()==null? "" : detail.getImageInfo().get(0).getThumbnail();
			// 运费
			int devliveryMoney = productMap.get("delivery_money")==null?0:Integer.parseInt(productMap.get("delivery_money"));
			// vip价格
			int priceVip = 0;
			
			/**
			 *  1、检查销售期
			 */
			long startTime = Long.parseLong(productMap.get("start_time").toString()); // 开始日期
			long endTime = Long.parseLong(productMap.get("end_time").toString()); // 结束日期
			long curTime = System.currentTimeMillis();
			if(curTime < startTime || curTime > endTime){
				flag = true;
				shoppingCart.setErrCode(ResultCode.product_not_in_time.getCode());
				shoppingCart.setErrMsg(ResultCode.product_not_in_time.getMsg());
				// 商品过期
				shoppingCart.setStatus(Constants.PRODUCT_EXPIRE_5);
				LogCvt.info("购物车商品过期，productId="+productId);
				return shoppingCart;
			}
			
			/**
			 *  2、产品名称
			 */
			if(!productName.equals(shoppingCart.getProductName())){
				shoppingCart.setProductName(productName);
				LogCvt.info("购物车商品名称变动，productId="+productId);
				flag = true;
			}
			/**
			 *  3、商品图片
			 */
			if(!productImage.equals(shoppingCart.getProductImage())){
				shoppingCart.setProductImage(productImage);
				LogCvt.info("购物车商品图片变动，productId="+productId);
				flag = true;
			}
			
			/**
			 * 4、检查库存，如果库存超限无需校验后项
			 */
			int dbQuantity = shoppingCart.getQuantity();
			int dbVipQuantity = shoppingCart.getVipQuantity();
			int totalQuantity = dbQuantity + dbVipQuantity;
			if(store < totalQuantity){
				flag = true;
				shoppingCart.setStatus(Constants.PRODUCT_STORE_NOT_ENOUGH_3);
				shoppingCart.setErrCode(ResultCode.failed.getCode());
				shoppingCart.setErrMsg("商品库存不足，库存量["+store+"]");
				LogCvt.info("购物车商品库存不足，productId="+productId);
				return shoppingCart;
			}
			
			/**
			 *  5、如果是预售商品，检查门店信息
			 */
//			long activityId = 0l;
			if(ProductType.presell.getCode().equals(detail.getProductType())){
				String orgCode = shoppingCart.getOrgCode();
				if(orgCode != null && !"".equals(orgCode)){
					Org org = commonLogic.queryByOrgCode(clientId, orgCode);
					Integer orgStatus = BooleanUtils.toIntegerObject(org.getIsEnable());
					String orgName = org.getOrgName();
					// 门店状态
					if(orgStatus.intValue() != shoppingCart.getOrgStatus().intValue()){
						flag = true;
						shoppingCart.setOrgStatus(orgStatus);
						// 如果门店为无效状态，无需检查后项
						if(orgStatus == 0){
							shoppingCart.setErrCode(ResultCode.failed.getCode());
							shoppingCart.setErrMsg("选择的提货网点无效");
							LogCvt.info("购物车商品提货网点无效，productId="+productId);
							return shoppingCart;
						}
					}
					// 门店名称
					if(!orgName.equals(shoppingCart.getOrgName())){
						shoppingCart.setOrgName(orgName);
						LogCvt.info("购物车商品提货门店名称变动，productId="+productId);
						flag = true;
					}
				}
				
				//2015.07.27 去掉VIP活动， VIP价格从mongo中取，如果字段为null,则商品没有VIP价格
				/*// 检查商品活动VIP价格
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
				if(EmptyChecker.isNotEmpty(detail.getVipPrice())){
					priceVip = detail.getVipPrice();
				}
				
			}
			
			 /**
			  * 6、检查限购信息
			  */
			if(isLimit){
				// 查询用户订单表的购买记录
				ProductBuyLimit buyLimit = detail.getBuyLimit();
				curTime = System.currentTimeMillis();
				
				// 查询用户订单表的购买记录
				Map<String,String> memberBuyMap = RedisCommon.getUserOrderCountRedis(clientId, memberCode, productId);
				int orderQuantity = 0;
				int orderVipQuantity = 0;
				if(memberBuyMap != null && memberBuyMap.keySet().size() > 0){
					if(EmptyChecker.isNotEmpty(memberBuyMap.get("count"))){
						orderQuantity = Integer.parseInt(memberBuyMap.get("count").toString());
					}
					if(EmptyChecker.isNotEmpty(memberBuyMap.get("vip_count"))){
						orderVipQuantity = Integer.parseInt(memberBuyMap.get("vip_count").toString());
					}
				}
				
				if((buyLimit.getStartTime() <curTime && buyLimit.getEndTime() > curTime) || (buyLimit.getStartTime() <curTime && buyLimit.getEndTime() == 0 )){
					// 检查商品是否在限购数量内
					if(totalQuantity + orderQuantity+orderVipQuantity > buyLimit.getMax() && buyLimit.getMax() > 0){
						flag = true;
						shoppingCart.setStatus(Constants.PRODUCT_LIMIT_4);
						shoppingCart.setErrCode(ResultCode.failed.getCode());
						shoppingCart.setErrMsg("商品限购数量超限，最多购买["+buyLimit.getMax()+"]");
						LogCvt.info("购物车商品购买数量超限，productId="+productId);
						return shoppingCart;
					}
					
					if(vipLevel > 0 && priceVip > 0){
						int maxVipLimit = buyLimit.getMaxVip();
						if(maxVipLimit > 0){
							int maxVip = maxVipLimit - orderVipQuantity > 0 ? maxVipLimit - orderVipQuantity : 0;
							dbVipQuantity = totalQuantity > maxVip? maxVip : totalQuantity;
							/*int max = buyLimit.getMax() - orderQuantity >0 ? buyLimit.getMax() - orderQuantity : 0;
							dbQuantity = totalQuantity -dbVipQuantity > max ? max : totalQuantity -dbVipQuantity;*/
							dbQuantity = totalQuantity -dbVipQuantity > 0 ? totalQuantity -dbVipQuantity : 0;
						}else{
							dbVipQuantity = totalQuantity;
							dbQuantity = 0;
						}
					}else{
						dbVipQuantity = 0;
						/*int max = buyLimit.getMax() - orderQuantity >0 ? buyLimit.getMax() - orderQuantity : 0;
						dbQuantity = totalQuantity > max ? max : totalQuantity;*/
						dbQuantity = totalQuantity -dbVipQuantity > 0 ? totalQuantity -dbVipQuantity : 0;
					}
				}else{//普通不限购
					if(vipLevel > 0 && priceVip > 0){//是vip、商品有VIP价
						int maxVipLimit = buyLimit.getMaxVip();
						if(maxVipLimit > 0){
							int maxVip = maxVipLimit - orderVipQuantity > 0 ? maxVipLimit - orderVipQuantity : 0;
							dbVipQuantity = totalQuantity > maxVip? maxVip : totalQuantity;
							dbQuantity = totalQuantity -dbVipQuantity > 0 ? totalQuantity -dbVipQuantity : 0;
						}else{
							dbVipQuantity = totalQuantity;
							dbQuantity = 0;
						}
					}else{//不是vip 或  没有VIP价
						dbQuantity = totalQuantity;
						dbVipQuantity = 0;
					}
				}
			}else{//普通不限购，VIP不限购
				if(vipLevel > 0 && priceVip > 0){
					dbVipQuantity = totalQuantity;
					dbQuantity = 0;
				}else{//如果不是vip 或  没有VIP价
					dbQuantity = totalQuantity;
					dbVipQuantity = 0;
				}
			}
			
			/**
			 *  7、检查活动信息,VIP价格
			 */
			/*if(activityId != 0 && vipLevel > 0){
				Map<String,String> activityMap = RedisCommon.getActivityRedis(clientId, activityId);
				if(activityMap != null){
					String priceJson = activityMap.get("vip_price");
					startTime = Long.parseLong(activityMap.get("begin_time"));
					endTime = Long.parseLong(activityMap.get("end_time"));
					curTime = System.currentTimeMillis();
					if(startTime <curTime && endTime > curTime){
						Object priceInfo = JSonUtil.toObject(priceJson, Map.class).get(vipLevel);
						if(priceInfo == null){
							shoppingCart.setErrCode(ResultCode.failed.getCode());
							shoppingCart.setErrMsg("商品活动VIP价不存在");
							return shoppingCart;
						}
						priceVip = Integer.parseInt(priceInfo.toString());
					}else{
						// 若活动结束，无VIP价格，VIP优惠购买数量清空
						dbQuantity = dbQuantity + dbVipQuantity;
						dbVipQuantity = 0;
					}
				}else{
					// 若无此活动，无VIP价格，VIP优惠购买数量清空
					dbQuantity = dbQuantity + dbVipQuantity;
					dbVipQuantity = 0;
				}
			}*/
			
			
			/**
			 *  8、商品数量
			 */
			if(dbQuantity != shoppingCart.getQuantity() || dbVipQuantity != shoppingCart.getVipQuantity()){
				shoppingCart.setQuantity(dbQuantity);
				shoppingCart.setVipQuantity(dbVipQuantity);
				LogCvt.info("购物车商品VIP优惠价数量["+dbVipQuantity+"],普通价数量["+dbQuantity+"]变动，productId="+productId);
				flag = true;
			}
		
			/**
			 * 9、价格
			 */
			if(price != shoppingCart.getMoney()){
				shoppingCart.setMoney(price);
				flag = true;
				shoppingCart.setErrCode(ResultCode.price_change.getCode());
				shoppingCart.setErrMsg(ResultCode.price_change.getMsg()); 
				LogCvt.info("购物车商品价格发生变动，productId="+productId);
			}
			/**
			 * 10、价格
			 */
			int vipMoney = shoppingCart.getVipMoney();
			if(priceVip != vipMoney){
				flag = true;
				shoppingCart.setVipMoney(priceVip);
				shoppingCart.setErrCode(ResultCode.tip_cart.getCode());
				shoppingCart.setErrMsg("商品价格发生变动"); 
				LogCvt.info("购物车商品VIP价格发生变动，productId="+productId);
			}
			
			/**
			 * 11、商品状态
			 */
			String prostatus = shoppingCart.getStatus();
			String prostatusOnShelf = shoppingCart.getStatus();
			if(Constants.PRODUCT_LIMIT_8.equals(prostatus) || Constants.PRODUCT_SKU_JZ_7.equals(prostatus)){
				prostatus = ProductStatus.onShelf.getCode();
			}
			if(!productStatus.getCode().equals(prostatus)){
				
				if(productStatus.getCode().equals(ProductStatus.isDeleted.getCode())){
					shoppingCart.setStatus(Constants.PRODUCT_INVALID_6);
					LogCvt.info("购物车商品状态变成无效，productId="+productId);
					flag = true;
					return shoppingCart;
				}else if(productStatus.getCode().equals(ProductStatus.offShelf.getCode()) || productStatus.getCode().equals(ProductStatus.disOffShelf.getCode())){
					shoppingCart.setStatus(String.valueOf(Constants.PRODUCT_MARKETABLE_2));
					LogCvt.info("购物车商品状态变成下架,商品状态："+productStatus+",商品ID："+productId);
					flag = true;
					return shoppingCart;
				}else if(isLimit && !Constants.PRODUCT_LIMIT_8.equals(shoppingCart.getStatus()) && Constants.PRODUCT_MARKETABLE_1.equalsIgnoreCase(productStatus.getCode())){
					shoppingCart.setStatus(Constants.PRODUCT_LIMIT_8);
					LogCvt.info("购物车商品成为限购商品，productId="+productId);
					flag = true;
				}else if(productStatus.getCode().equals(ProductStatus.noShelf.getCode())){
					shoppingCart.setStatus(Constants.PRODUCT_MARKETABLE_0);
					LogCvt.info("购物车商品状态变成未上架，productId="+productId);
					flag = true;
					return shoppingCart;
				}else if(productStatus.getCode().equals(ProductStatus.onShelf.getCode())){
					shoppingCart.setStatus(Constants.PRODUCT_MARKETABLE_1);
					LogCvt.info("购物车商品状态变成上架，productId="+productId);
					flag = true;
				}
				
			}
			
			/**
			 * 12、商品库存紧张
			 */
			if(store < 5 && !Constants.PRODUCT_SKU_JZ_7.equals(shoppingCart.getStatus()) && prostatusOnShelf.equals(ProductStatus.onShelf.getCode())){
				shoppingCart.setStatus(Constants.PRODUCT_SKU_JZ_7);
				LogCvt.info("购物车商品状态发生变动，productId="+productId);
				flag = true;
			}
			
			/**
			 * 13 运费
			 */
			if(devliveryMoney != shoppingCart.getDeliveryMoney()){
				shoppingCart.setDeliveryMoney(devliveryMoney);
				LogCvt.info("购物车商品运费发生变动[运费："+devliveryMoney+"]，productId="+productId);
				flag = true;
			}
			
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
			shoppingCart.setErrCode(ResultCode.failed.getCode());
			shoppingCart.setErrMsg("系统异常，获取购物车失败");
		}finally{
			if(flag){
				shoppingSupport.updateProduct(shoppingCart);
			}
			
		}
		return shoppingCart;
	}
	
	/**
	 * 拉取用户购物车单个精品商城商品信息
	 */
	public ShoppingCart getBoutiqueCartByProductId(long memberCode, String clientId,String merchantId, String productId,int vipLevel) {
		LogCvt.info("拉取用户购物车单个精品商城商品，请求参数：{memberCode:"+memberCode+",clientId:"+clientId+",merchantId="+merchantId+",productId:"+productId+",vipLevel:"+vipLevel+"}");
		ShoppingCart shoppingCart = null;
		boolean flag = false;
		try {
			shoppingCart = shoppingSupport.queryShoppingCart(clientId, memberCode, productId);
			if(shoppingCart == null){
				shoppingCart = new ShoppingCart();
				shoppingCart.setErrCode(ResultCode.failed.getCode());
				shoppingCart.setErrMsg("精品商城商品信息不存在");
				return shoppingCart;
			}
			
			
			/***redis获取供应商信息*/
			Map<String,String> providerMap = commonLogic.getProviderRedis(merchantId);
			// 1.供应商名称
			String merchantName = providerMap.get("merchant_name");
			if(!merchantName.equals(shoppingCart.getMerchantName())){
				shoppingCart.setMerchantName(merchantName);
				flag = true;
			}
			
			
			/***获取商品缓存信息*/
			Map<String,String> productMap = commonLogic.getProductRedis(clientId, merchantId, productId);
			// 剩余库存量
			int store = commonLogic.getStoreRedis(clientId, merchantId, productId);
			// mongo获取商品信息
			ProductDetail detail = commonSupport.queryProductDetail(productId);
			// 0-未上架 1-上架 2-下架
			ProductStatus productStatus = ProductStatus.getType(productMap.get("is_marketable"));
			// 销售价
			int price = detail.getPrice();		
			// 商品名称
			String productName = detail.getName();	
			// 是否限购
			boolean isLimit = BooleanUtils.toBoolean(detail.getIsLimit());	
			// 商品图片
			String productImage = EmptyChecker.isEmpty(detail.getImageInfo())? "" : detail.getImageInfo().get(0).getThumbnail();
			// 运费
			int devliveryMoney = EmptyChecker.isEmpty(productMap.get("delivery_money"))?0:Integer.parseInt(productMap.get("delivery_money"));
			// vip价格
			int priceVip = 0;
			
			 // 1.检查销售期
			String dtStr = productMap.get("delivery_time");
			if(StringUtils.isNotEmpty(dtStr)) {
				long deliveryTime = Long.parseLong(productMap.get("delivery_time"));
				String deliveryTimeStr = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, deliveryTime);
				if(DateUtil.dateCompare(deliveryTimeStr)){
					flag = true;
					shoppingCart.setErrCode(ResultCode.product_no_in_delivery_time.getCode());
					shoppingCart.setErrMsg(ResultCode.product_no_in_delivery_time.getMsg());
					// 商品过期
					shoppingCart.setStatus(Constants.PRODUCT_EXPIRE_5);
					LogCvt.info("购物车商品过期，productId="+productId);
					return shoppingCart;
				}
			}
			
			
			//2.产品名称
			if(!productName.equals(shoppingCart.getProductName())){
				shoppingCart.setProductName(productName);
				LogCvt.info("购物车商品名称变动，productId="+productId);
				flag = true;
			}
			
			//3.商品图片
			if(!productImage.equals(shoppingCart.getProductImage())){
				shoppingCart.setProductImage(productImage);
				LogCvt.info("购物车商品图片变动，productId="+productId);
				flag = true;
			}
			
			//4、检查库存，如果库存超限无需校验后项
			int dbQuantity = shoppingCart.getQuantity();
			int dbVipQuantity = shoppingCart.getVipQuantity();
			int totalQuantity = dbQuantity + dbVipQuantity;
			if(store < totalQuantity){
				flag = true;
				shoppingCart.setStatus(Constants.PRODUCT_STORE_NOT_ENOUGH_3);
				shoppingCart.setErrCode(ResultCode.failed.getCode());
				shoppingCart.setErrMsg("商品库存不足，库存量["+store+"]");
				LogCvt.info("购物车商品库存不足，productId="+productId);
				return shoppingCart;
			}
			if(EmptyChecker.isNotEmpty(detail.getVipPrice())){
				priceVip = detail.getVipPrice();
			}
			
			//5.检查限购信息
			if(isLimit){
				// 查询用户订单表的购买记录
				ProductBuyLimit buyLimit = detail.getBuyLimit();
				
				// 查询用户订单表的购买记录
				Map<String,String> memberBuyMap = RedisCommon.getUserOrderCountRedis(clientId, memberCode, productId);
				int orderQuantity = 0;
				int orderVipQuantity = 0;
				if(memberBuyMap != null && memberBuyMap.keySet().size() > 0){
					if(EmptyChecker.isNotEmpty(memberBuyMap.get("count"))){
						orderQuantity = Integer.parseInt(memberBuyMap.get("count").toString());
					}
					if(EmptyChecker.isNotEmpty(memberBuyMap.get("vip_count"))){
						orderVipQuantity = Integer.parseInt(memberBuyMap.get("vip_count").toString());
					}
				}
				
				// 检查商品是否在限购数量内
				if(totalQuantity + orderQuantity+orderVipQuantity > buyLimit.getMax() && buyLimit.getMax() > 0){
					flag = true;
					shoppingCart.setStatus(Constants.PRODUCT_LIMIT_4);
					shoppingCart.setErrCode(ResultCode.failed.getCode());
					shoppingCart.setErrMsg("商品限购数量超限，最多购买["+buyLimit.getMax()+"]");
					LogCvt.info("购物车商品购买数量超限，productId="+productId);
					return shoppingCart;
				}
				
				if(vipLevel > 0 && priceVip > 0){
					int maxVipLimit = buyLimit.getMaxVip();
					if(maxVipLimit > 0){
						int maxVip = maxVipLimit - orderVipQuantity > 0 ? maxVipLimit - orderVipQuantity : 0;
						dbVipQuantity = totalQuantity > maxVip? maxVip : totalQuantity;
						/*int max = buyLimit.getMax() - orderQuantity >0 ? buyLimit.getMax() - orderQuantity : 0;
						dbQuantity = totalQuantity -dbVipQuantity > max ? max : totalQuantity -dbVipQuantity;*/
						dbQuantity = totalQuantity -dbVipQuantity > 0 ? totalQuantity -dbVipQuantity : 0;
					}else{
						dbVipQuantity = totalQuantity;
						dbQuantity = 0;
					}
				}else{
					dbVipQuantity = 0;
					/*int max = buyLimit.getMax() - orderQuantity >0 ? buyLimit.getMax() - orderQuantity : 0;
					dbQuantity = totalQuantity > max ? max : totalQuantity;*/
					dbQuantity = totalQuantity -dbVipQuantity > 0 ? totalQuantity -dbVipQuantity : 0;
				}
			}else{//普通不限购，VIP不限购
				if(vipLevel > 0 && priceVip > 0){
					dbVipQuantity = totalQuantity;
					dbQuantity = 0;
				}else{//如果不是vip 或  没有VIP价
					dbQuantity = totalQuantity;
					dbVipQuantity = 0;
				}
			}
			
			//6.商品数量
			if(dbQuantity != shoppingCart.getQuantity() || dbVipQuantity != shoppingCart.getVipQuantity()){
				shoppingCart.setQuantity(dbQuantity);
				shoppingCart.setVipQuantity(dbVipQuantity);
				LogCvt.info("购物车商品VIP优惠价数量["+dbVipQuantity+"],普通价数量["+dbQuantity+"]变动，productId="+productId);
				flag = true;
			}
		
			//7.价格
			if(price != shoppingCart.getMoney()){
				shoppingCart.setMoney(price);
				flag = true;
				shoppingCart.setErrCode(ResultCode.price_change.getCode());
				shoppingCart.setErrMsg(ResultCode.price_change.getMsg()); 
				LogCvt.info("购物车商品价格发生变动，productId="+productId);
			}
			
			//8.价格
			int vipMoney = shoppingCart.getVipMoney();
			if(priceVip != vipMoney){
				flag = true;
				shoppingCart.setVipMoney(priceVip);
				shoppingCart.setErrCode(ResultCode.tip_cart.getCode());
				shoppingCart.setErrMsg("商品价格发生变动"); 
				LogCvt.info("购物车商品VIP价格发生变动，productId="+productId);
			}
			
			//9.商品状态
			String prostatus = shoppingCart.getStatus();
			String prostatusOnShelf = shoppingCart.getStatus();
			if(Constants.PRODUCT_LIMIT_8.equals(prostatus) || Constants.PRODUCT_SKU_JZ_7.equals(prostatus)){
				prostatus = ProductStatus.onShelf.getCode();
			}
			if(!productStatus.getCode().equals(prostatus)){
				if(productStatus.getCode().equals(ProductStatus.isDeleted.getCode())){
					shoppingCart.setStatus(Constants.PRODUCT_INVALID_6);
					LogCvt.info("购物车商品状态变成无效，productId="+productId);
					flag = true;
					return shoppingCart;
				}else if(productStatus.getCode().equals(ProductStatus.offShelf.getCode()) || productStatus.getCode().equals(ProductStatus.disOffShelf.getCode())){
					shoppingCart.setStatus(String.valueOf(Constants.PRODUCT_MARKETABLE_2));
					LogCvt.info("购物车商品状态变成下架,商品状态："+productStatus+",商品ID："+productId);
					flag = true;
					return shoppingCart;
				}else if(isLimit && !Constants.PRODUCT_LIMIT_8.equals(shoppingCart.getStatus()) && Constants.PRODUCT_MARKETABLE_1.equalsIgnoreCase(productStatus.getCode())){
					shoppingCart.setStatus(Constants.PRODUCT_LIMIT_8);
					LogCvt.info("购物车商品成为限购商品，productId="+productId);
					flag = true;
				}else if(productStatus.getCode().equals(ProductStatus.noShelf.getCode())){
					shoppingCart.setStatus(Constants.PRODUCT_MARKETABLE_0);
					LogCvt.info("购物车商品状态变成未上架，productId="+productId);
					flag = true;
					return shoppingCart;
				}else if(productStatus.getCode().equals(ProductStatus.onShelf.getCode())){
					shoppingCart.setStatus(Constants.PRODUCT_MARKETABLE_1);
					LogCvt.info("购物车商品状态变成上架，productId="+productId);
					flag = true;
				}
				
			}
			
			//10.商品库存紧张
			if(store < 5 && !Constants.PRODUCT_SKU_JZ_7.equals(shoppingCart.getStatus()) && prostatusOnShelf.equals(ProductStatus.onShelf.getCode())){
				shoppingCart.setStatus(Constants.PRODUCT_SKU_JZ_7);
				LogCvt.info("购物车商品状态发生变动，productId="+productId);
				flag = true;
			}
			
			//11 运费
			if(devliveryMoney != shoppingCart.getDeliveryMoney()){
				shoppingCart.setDeliveryMoney(devliveryMoney);
				LogCvt.info("购物车商品运费发生变动[运费："+devliveryMoney+"]，productId="+productId);
				flag = true;
			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
			shoppingCart.setErrCode(ResultCode.failed.getCode());
			shoppingCart.setErrMsg("系统异常，获取购物车失败");
		}finally{
			if(flag){
				shoppingSupport.updateProduct(shoppingCart);
			}
			
		}
		return shoppingCart;
	
	}
	
	
	
	/**
	 *  更新购物车提货网点信息
	  * @Title: updateDelivery
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @param merchantId
	  * @param @param productId
	  * @param @param outletId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#updateDelivery(long, long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResultBean updateDelivery(String clientId, long memberCode,String merchantId, String productId, String outletId) {
		// mongo获取商品信息
		ProductDetail detail = commonSupport.queryProductDetail(productId);
		if(detail == null){
			return new ResultBean(ResultCode.prodcut_not_exist);
		}
		/**
		 * 检查预售商品，检查门店信息
		 */
		if(!ProductType.presell.getCode().equals(detail.getProductType())){
			return new ResultBean(ResultCode.product_un_prell);
		}
		
		/**
		 *  检查提货网点是否正确
		 */
		List<ProductCityOutlet> outletList = detail.getOrgOutlets();
		if(!ShoppingCartValidation.checkOulet(outletList, outletId)){
			return new ResultBean(ResultCode.failed,"提货网点不在商品预售网点内");
		}
		
		Org org = commonLogic.queryByOutleId(clientId, outletId);
		Integer orgStatus = BooleanUtils.toIntegerObject(org.getIsEnable());
		String orgCode = org.getOrgCode();
		String orgName = org.getOrgName();
		if(orgStatus == 0){
			return new ResultBean(ResultCode.org_enable);
		}
		
		/**
		 *  更新商品的提货网点
		 */
		ShoppingCart product = shoppingSupport.queryShoppingCart(clientId, memberCode, productId);
		product.setOrgCode(orgCode);
		product.setOrgName(orgName);
		product.setOrgStatus(orgStatus);
		shoppingSupport.updateProduct(product);
		
		return new ResultBean(ResultCode.success);
	}
	
	/**
	 *  订单完成清空订单购物车
	  * @Title: clearOrderShoppingCart
	  * @Description: TODO
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @param shopingListReq
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#clearOrderShoppingCart(com.froad.po.shoppingcart.req.OrderShoppingListReq)
	 */
	@Override
	public ResultBean clearOrderShoppingCart(List<OrderShoppingListReq> shopingListReqs) {
		if(EmptyChecker.isNotEmpty(shopingListReqs)){
			for(OrderShoppingListReq shoppingReq : shopingListReqs){
				String clientId = shoppingReq.getClientId();
				long memberCode = shoppingReq.getMemberCode();
				String merchantId = shoppingReq.getMerchantId();
				String productId = shoppingReq.getProductId();
				shoppingSupport.removeProduct(clientId, memberCode, merchantId, productId);
			}
		}
		return new ResultBean(ResultCode.success);
	}
	
	/**
	 *  批量删除购物车接口
	  * @Title: deleteBatchCart
	  * @Description: TODO
	  * @author: share 2015年4月10日
	  * @modify: share 2015年4月10日
	  * @param @param reqs
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#deleteBatchCart(java.util.List)
	 */
	@Override
	public ResultBean deleteBatchCart(List<ShoppingCartReq> reqs) {
		LogCvt.info("用户选择批量删除购物车："+JSonUtil.toJSonString(reqs));
		for(ShoppingCartReq shoppingReq : reqs){
			String clientId = shoppingReq.getClientId();
			long memberCode = shoppingReq.getMemberCode();
			String merchantId = shoppingReq.getMerchantId();
			String productId = shoppingReq.getProductId();
			shoppingSupport.removeProduct(clientId, memberCode, merchantId, productId);
		}
		return new ResultBean(ResultCode.success);
	}
	
	private ResultBean getResultBean(boolean flag) {
		if(!flag){
			return new ResultBean(ResultCode.shopping_cart_inser_fail);
		}else{
			return new ResultBean(ResultCode.success);
		}
	}
	
	/**
	 *  获取购物车商品数量
	  * @Title: getCartCount
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.ShoppingCartLogic#getCartCount(long, long)
	 */
	@Override
	public int getCartProductCount(long memberCode, String clientId) {
		LogCvt.info("获取用户购物商品总数量:memberCode="+memberCode+";clientId="+clientId);
		return shoppingSupport.countMemberCartsTotal(clientId, memberCode);
	}
	
	public static void main(String[] args){
		ShoppingCartLogicImpl logic = new ShoppingCartLogicImpl();
		/*ShoppingCartReq req = new ShoppingCartReq();
		req.setClientId("anhui");
		req.setMemberCode(52001970855L);
		req.setMerchantId("07252DA48000");
		req.setNum(1);
		req.setProductId("083B3EB20000");
		ResultBean result = logic.addCart(req);*/
		
		logic.getCartProductCount(50001097285L, "chongqing");
	}

}

