package com.froad.db.mysql.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadDBException;
import com.froad.po.Org;
import com.froad.po.PresellShip;
import com.froad.po.Product;
import com.froad.po.ProductMonthCount;
import com.froad.po.ProductSeckill;

/**
 * 订单
 */
public interface OrderMapper {
	
	/**
	 * 批量更新商品表库存和冻结库存
	 * @param List<SubOrder>
	 * @return Boolean    是否成功
	 */
	public Boolean updateProductStoreByBatch(List<Product> list) throws FroadDBException;
	
	/**
	 * 更新商品表库存和冻结库存
	 * @param List<SubOrder>
	 * @return Boolean    是否成功
	 */
	public Boolean updateProductStore(Product product) throws FroadDBException;
	
	/**
	 * 秒杀模块-更新商品表库存
	 * @param ProductSeckill
	 * @return Boolean    是否成功
	 */
	public Boolean updateSeckillProductStore(ProductSeckill product) throws FroadDBException;

	/**
	 * 更新商品销售数量
	 * @param product
	 */
	public void updateProductSellCount(Product product) throws FroadDBException;
	
	/**
	 * 更新秒杀商品销售数量
	 * @param productSeckill
	 */
	public void updateSeckillProductSellCount(ProductSeckill product) throws FroadDBException;
	
	/**
	 * 插入商品月度销售量
	 * @param product
	 */
	public void insertProductMonthCount(ProductMonthCount productMonthCount);

	/**
	 * 更新商品月度销售量
	 * @param product
	 */
	public void updateProductMonthCount(ProductMonthCount productMonthCount);

	/**
	 * 获取商品销售数量
	 * @param product
	 * @return
	 */
	public Integer getProductSellCount(Product product);
	
	/**
	 * 取门店名称
	 * @param clientId
	 * @param merchantId
	 * @param outletId
	 * @return
	 */
	public abstract String getOutletNameByOutletId(@Param("clientId")String clientId,@Param("merchantId")String merchantId, @Param("outletId")String outletId);

	/**
     * 预售配送订单配送信息分页查询
     * @param page
     * @param ProductFilter
     * @return List<ProductListInfo>
     */
    public abstract List<PresellShip> queryPresellShipByPage(Page<PresellShip> page,@Param(value = "p")PresellShip ship);
    
    /**
     * 根据商户用户ID查询商户用户名
     * @param list 商户用户ID集合
     * @return
     */
    public abstract List<Map<String,Object>> findMerchantUserNameByIdList(@Param(value = "list")List<Long> list);
    
    /**
     * 根据商户用户ID查询商户用户名
     * @param list 商户用户ID集合
     * @return
     */
    public abstract String findMerchantUserNameById(@Param(value = "id")Long id);
    
    /**
     * 根据地区ID查询地区所属省市区名
     * @param list 地区ID集合
     * @return
     */
    public abstract List<Map<String,Object>> findAreaNameById(@Param(value = "list")List<Long> list);
	
}
