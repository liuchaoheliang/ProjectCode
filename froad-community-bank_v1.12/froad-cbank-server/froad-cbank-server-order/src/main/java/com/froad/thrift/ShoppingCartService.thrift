namespace java com.froad.thrift.service

include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift" 
include "ShoppingCartVo.thrift" 

/**
*3 对外接口定义
*
*/
service ShoppingCartService extends BizMonitor.BizMonitorService {
	/**
	* 拉取用户购物车单个商品信息
	*/
	ShoppingCartVo.ShoppingCartVoRes getCartByProductId(1:i64 memberCode,2:string clientId,3:string merchantId,4:string productId,5:i32 vipLevel);
	
	/**
	* 拉取用户购物车全部商品信息
	*/
	list<ShoppingCartVo.ShoppingCartVoRes> getCart(1:i64 memberCode,2:string clientId,3:i32 vipLevel);
	
	/**
	* 清空购物车
	*/
	bool deleteCart(1:i64 memberCode,2:string clientId);
	
	/**
	* 修改购物车单个商品数量
	*/
	Common.ResultVo updateCartByProductNum(1:ShoppingCartVo.ShoppingCartVoReq shoppingCartVoReq);
	
	/**
	* 批量删除购物车信息
	*/
	Common.ResultVo deleteBatchCart(1:list<ShoppingCartVo.ShoppingCartVoReq> shoppingCartVoReq);
	
	/**
	*添加商品和数量到购物车
	*/
	Common.ResultVo addCart(1:ShoppingCartVo.ShoppingCartVoReq shoppingCartVoReq);
	
	/**
	 * 获取购物车商品数量
	 */
	i32 getCartCount(1:i64 memberCode,2:string clientId);
	
	/**
	 *  更新购物车商品的提货网点
	 */
	Common.ResultVo updateDelivery(1:i64 memberCode,2:string clientId,3:string merchantId,4:string productId,5:string outletId);

        /**
	 * 获取购物车商品总数量
	 */
	i32 getCartProductCount(1:i64 memberCode,2:string clientId);

}