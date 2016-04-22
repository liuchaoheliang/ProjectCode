package com.froad.support;

import java.util.List;

import com.froad.po.shoppingcart.mongo.ShoppingCart;

/**
 *  购物车数据接口API
  * @ClassName: ShoppingCartSupport
  * @Description: TODO
  * @author share 2015年3月25日
  * @modify share 2015年3月25日
 */
public interface ShoppingCartSupport {
	
	
	
	/**
	 *  获取购物车单个商品信息
	  * @Title: queryShoppingProduct
	  * @Description: TODO
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param cientId
	  * @param @param memberCode
	  * @param @param merchantId
	  * @param @param productId
	  * @param @return    
	  * @return ShoppingProduct    
	  * @throws
	 */
	public abstract ShoppingCart queryShoppingCart(String clientId,long memberCode,String productId);
	
	/**
	 *  获取会员购物车数量
	  * @Title: countMemberCarts
	  * @Description: TODO
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int countMemberCarts(String clientId,long memberCode);
	
	/**
	 *  检查商品是否存在
	  * @Title: queryMemberShoppingByProductIdCount
	  * @Description: TODO
	  * @author: share 2015年5月16日
	  * @modify: share 2015年5月16日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @param productId
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int queryMemberShoppingByProductIdCount(String clientId, long memberCode,String productId);
	
	
	/**
	 *  添加或修改商品信息
	  * @Title: insertOrUpdateProduct
	  * @Description: TODO
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @param cart
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public boolean insertOrUpdateProduct(ShoppingCart cart);
	
	
	
	/**
	 *  更新购物车单个商品信息
	  * @Title: updateProduct
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @param merchantId
	  * @param @param product
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public boolean updateProduct(ShoppingCart cart);
	
	/**
	 *  清除购物车单个商品信息
	  * @Title: removeProduct
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @param merchantId
	  * @param @param productId
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int removeProduct(String clientId,long memberCode,String merchantId,String productId);
	
	/**
	 *  清空购物车
	  * @Title: deleteCart
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param memberCode
	  * @param @param clientId
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int deleteCart(long memberCode, String clientId);
	
	/**
	 *  查询商品购物车
	  * @Title: queryShoppingCart
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @return    
	  * @return ShoppingCart    
	  * @throws
	 */
	public List<ShoppingCart> queryShoppingCart(String clientId,long memberCode);
	

	/**
	 *  更新商品数量
	  * @Title: updateProductQuantity
	  * @Description: TODO
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @param product
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public abstract boolean updateProductQuantity(ShoppingCart product);

	/**
	 *  检查用户购车商品数目
	  * @Title: queryMemberShoppingProductCount
	  * @Description: TODO
	  * @author: share 2015年5月4日
	  * @modify: share 2015年5月4日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @param merchantId
	  * @param @param productId
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public abstract int queryMemberShoppingProductCount(String clientId,long memberCode);
	
	/**
	 *  获取会员购物车商品总数量
	  * @Title: countMemberCarts
	  * @Description: TODO
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param clientId
	  * @param @param memberCode
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public abstract int countMemberCartsTotal(String clientId,long memberCode);
	
}

