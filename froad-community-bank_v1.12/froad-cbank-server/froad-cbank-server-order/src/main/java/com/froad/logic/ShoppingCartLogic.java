package com.froad.logic;

import java.util.List;

import com.froad.common.beans.ResultBean;
import com.froad.po.shoppingcart.mongo.ShoppingCart;
import com.froad.po.shoppingcart.req.OrderShoppingListReq;
import com.froad.po.shoppingcart.req.ShoppingCartReq;

/**
 *  购物车逻辑
  * @ClassName: ShoppingCartLogic
  * @Description: TODO
  * @author share 2015年3月18日
  * @modify share 2015年3月18日
 */
public interface ShoppingCartLogic {
	
	/**
	 *  购物车添加
	  * @Title: addCart
	  * @Description: TODO
	  * @author: share 2015年3月18日
	  * @modify: share 2015年3月18日
	  * @param @param cart
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public abstract ResultBean addCart(ShoppingCartReq req);
	
	/**
	 *  更新购物车单个商品或数量
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
	  * @return boolean    
	  * @throws
	 */
	public abstract ResultBean updateCartByProductNum(long memberCode, String clientId,String merchantId,String productId, int num,int vipLevel);
	
	/**
	 *  清空购物车
	  * @Title: deleteCart
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @param productId
	  * @param @param num
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public abstract boolean deleteCart(long memberCode, String clientId);

	/**
	 *  获取购物车商品数量
	  * @Title: getCartCount
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public abstract int getCartCount(long memberCode, String clientId);

	/**
	 *  拉取用户购物车信息
	  * @Title: getCart
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return    
	  * @return ShoppingCartVoRes    
	  * @throws
	 */
	public abstract List<ShoppingCart> getCart(long memberCode, String clientId,int vipLevel);

	
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
	  * @return ShoppingCartVoRes    
	  * @throws
	 */
	public abstract ShoppingCart getCartByProductId(long memberCode,String clientId, String merchantId, String productId,int vipLevel);

	/**
	 *  更新购物车商品提货网点信息
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
	  * @return ResultBean    
	  * @throws
	 */
	public abstract ResultBean updateDelivery(String clientId, long memberCode,String merchantId, String productId, String outletId);
	
	
	/**
	 *  订单生产后清空购物车
	  * @Title: clearOrderShoppingCart
	  * @Description: TODO
	  * @author: share 2015年4月9日
	  * @modify: share 2015年4月9日
	  * @param @param shopingListReq
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	public abstract ResultBean clearOrderShoppingCart(List<OrderShoppingListReq> shopingListReq);

	/**
	 *  批量删除购物车接口
	  * @Title: deleteBatchCart
	  * @Description: TODO
	  * @author: share 2015年4月10日
	  * @modify: share 2015年4月10日
	  * @param @param reqs
	  * @param @return    
	  * @return ResultBean    
	  * @throws
	 */
	public abstract ResultBean deleteBatchCart(List<ShoppingCartReq> reqs);
	
	/**
	 *  获取购物车商品总数量
	  * @Title: getCartCount
	  * @Description: TODO
	  * @author: share 2015年3月20日
	  * @modify: share 2015年3月20日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public abstract int getCartProductCount(long memberCode, String clientId);
	
}

