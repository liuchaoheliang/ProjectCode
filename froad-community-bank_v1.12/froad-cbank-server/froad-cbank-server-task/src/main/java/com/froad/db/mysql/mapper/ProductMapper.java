package com.froad.db.mysql.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.po.Product;
import com.froad.po.PresellProduct;
import com.froad.po.ProductSeckill;


public interface ProductMapper {
	
	/**
	 * 
	 * @Title: updatePresellByProductId 
	 * @Description: 根据产品ID更新预售商品
	 * @author: froad-huangyihao 2015年3月19日
	 * @modify: froad-huangyihao 2015年3月19日
	 * @param p
	 * @return
	 * @throws
	 */
	public boolean updatePresellByProductId(PresellProduct p);
	
	/**
	 * 
	 * @Title: selectShouldClusterProductPresell 
	 * @Description: 查询需要成团还未成团的预售商品信息
	 * @author: froad-huangyihao 2015年3月18日
	 * @modify: froad-huangyihao 2015年3月18日
	 * @return
	 * @throws
	 */
	public List<Product> selectShouldClusterProductPresell();
	
	/**
	 * 
	 * @Title: selectAllEndSeckill 
	 * @Description: 查询所有结束秒杀的商品
	 * @author: froad-huangyihao 2015年4月28日
	 * @modify: froad-lf 2015年5月9日
	 * @return
	 * @throws
	 */
	public List<ProductSeckill> selectAllEndSeckillAndExistStore();
	
	/**
	 * 
	 * @Title: updateProductStore 
	 * @Description: 更新商品库存
	 * @author: froad-huangyihao 2015年4月28日
	 * @modify: froad-huangyihao 2015年4月28日
	 * @param product
	 * @return
	 * @throws
	 */
	public boolean updateProductStore(Product product);
	
	/**
	 * 
	 * @Title: updateProductSeckillStore 
	 * @Description: 更新秒杀商品库存
	 * @author: lf 20150510
	 * @param productSeckill
	 * @return
	 * @throws
	 */
	public boolean updateProductSeckillStore(ProductSeckill productSeckill);
	
	/**
	 * 
	 * @Title: findByProductId 
	 * @Description: 通过商品ID查询商品信息
	 * @author: froad-huangyihao 2015年4月28日
	 * @modify: froad-huangyihao 2015年4月28日
	 * @return
	 * @throws
	 */
	public Product findByProductId(String productId);
	
	/**
	 * 
	  * @Title: findAllPresell
	  * @Description: 查找所有预售配送商品信息
	  * @author: Yaren Liang 2015年6月15日
	  * @modify: Yaren Liang 2015年6月15日
	  * @param @return    
	  * @return List<Product>    
	  * @throws
	 */
	public List<PresellProduct> findAllPresell(@Param("startTime") String startTime, @Param("endTime") String endTime);
	
	/**
	 * 
	  * @Title: findAllPresellByHis
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月17日
	  * @modify: Yaren Liang 2015年6月17日
	  * @param @return    
	  * @return List<PresellProduct>    
	  * @throws
	 */
	public List<PresellProduct> findAllPresellByHis(); 
	
	/**
	 * 
	  * @Title: findAllPresellByCount
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月17日
	  * @modify: Yaren Liang 2015年6月17日
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int findAllPresellByCount();
	
}
