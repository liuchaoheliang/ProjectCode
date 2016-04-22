package com.froad.timer.task;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.db.redis.ProductSeckillRedisService;
import com.froad.logback.LogCvt;
import com.froad.po.Product;
import com.froad.po.ProductSeckill;
import com.froad.util.Checker;
import com.froad.util.ServiceFactory;

/**
 * 定时还库存-秒杀商品
 * @ClassName: TaskReturnStoreForSecKill 
 * @Description: TODO
 * @author: huangyihao
 * @modify: lf 20150510
 * @modify: lf 20150607
 * @date: 2015年4月27日
 */
public class TaskReturnStoreForSecKill implements Runnable {
	
	private ProductSeckillRedisService productSeckillRedisService = ServiceFactory.getProductSeckillRedisService();
//	private ProductStoreRedisService productStoreRedisService = ServiceFactory.getProductStoreRedisService();
	
	@Override
	public void run() {
		LogCvt.debug("定时任务: 秒杀商品退还库存======开始======");

		SqlSession session = null;
		ProductMapper productMapper = null;
		
		try {
			
			// 如果消息任务队列(缓存)中还有任务-结束此功能(待队列中没有任务再运行)
			Integer queueCount = productSeckillRedisService.getSecKillProductQueueCount();
			if( queueCount != null && queueCount > 0 ){
				LogCvt.debug("定时任务: 秒杀商品退还库存======完成(消息任务队列中还有任务)======");
				return ;
			}
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			productMapper = session.getMapper(ProductMapper.class);
			
			// 查询所有秒杀时间结束并还有库存数量的秒杀商品
			List<ProductSeckill> psks = productMapper.selectAllEndSeckillAndExistStore();
			if(Checker.isEmpty(psks)){
				LogCvt.debug("定时任务: 秒杀商品退还库存======完成(无过期秒杀商品)======");
				return ;
			}
			
			LogCvt.debug("共有 "+psks.size()+" 个过期秒杀商品待处理");
			
			boolean pResult = false;
			for( ProductSeckill psk : psks ){
				
				// 处理库存
				LogCvt.debug("秒杀商品 "+psk.getProductId()+" 库存处理:开始");
				pResult = processStore(psk);
				LogCvt.debug("秒杀商品 "+psk.getProductId()+" 库存处理:"+(pResult?"成功":"失败"));
				
			}
			
		} catch (Exception e) {
			LogCvt.error("定时任务: 秒杀商品退还库存======系统异常======");
			LogCvt.error(e.getMessage(), e);
		} finally {
			if(session != null) session.close();
			LogCvt.debug("定时任务: 秒杀商品退还库存======结束======");
		}
	}
	
	/**
	 * 处理库存
	 * <br>
	 * 
	 * 更新商品表(cb_produc) 和秒杀商品表(cb_product_seckill)
	 * 
	 * 处理秒杀的缓存(秒杀商品配置 & 秒杀商品库存)
	 * 
	 * @Title: processStore
	 * @Description: TODO
	 * @author: lf
	 * @param productSeckill
	 * @return boolean
	 * @throws
	 */
	private boolean processStore(ProductSeckill psk){
		
		boolean result = false;
		
		String clientId = psk.getClientId();
		String productId = psk.getProductId();
		Integer secStore = psk.getSecStore();
		
		// 处理秒杀的缓存(秒杀商品配置 & 秒杀商品库存)
		try{
					
			boolean dspsR = productSeckillRedisService.clearSeckillProductStore(clientId, productId);
			LogCvt.debug("秒杀商品 "+productId+" 库存处理 - 缓存 - 清空秒杀商品库存 - 处理结果:"+(dspsR?"成功":"失败"));
			
			if( dspsR ){ // 删除描述商品库存 成功
				
				boolean uspsR = productSeckillRedisService.updateSeckillProductOfStore(clientId, productId, "0");
				LogCvt.debug("秒杀商品 "+productId+" 库存处理 - 缓存 - 更新秒杀商品配置 - 处理结果:"+(uspsR?"成功":"失败"));
				
				if( uspsR ){ // 更新秒杀商品配置 成功
					result = true;
				}else{
					return result; // 更新秒杀商品配置 失败 立即返回 后续流程不处理
				}
			}else{
				return result;  // 删除描述商品库存 失败 立即返回 后续流程不处理
			}
					
		} catch (Exception e) {
			LogCvt.error("秒杀商品 "+productId+" 库存处理 - 缓存 - 出现异常:"+e.getMessage(), e);
		}
		LogCvt.debug("秒杀商品 "+productId+" 库存处理 - 缓存 - 结束");
				
		SqlSession session = null;
		ProductMapper productMapper = null;
		// 更新商品表(cb_produc) 和秒杀商品表(cb_product_seckill)
		try{
			
			session = MyBatisManager.getSqlSessionFactory().openSession();
			productMapper = session.getMapper(ProductMapper.class);
			
			// 更新 商品表(cb_produc)
			Product product = new Product();
			product.setClientId(clientId);
			product.setProductId(productId);
			product.setStore(secStore); // + 秒杀商品表的秒杀库存数量(sec_store)
			boolean upsR = productMapper.updateProductStore(product);
			LogCvt.debug("秒杀商品 "+productId+" 库存处理 - 数据库 - 商品表(cb_produc)处理结果:"+(upsR?"成功":"失败"));
			
			if( upsR ){ // 更新商品表 成功
				
				// 更新 秒杀商品表(cb_product_seckill)
				psk.setSecStore(-psk.getSecStore()); // - 秒杀商品表的秒杀库存数量(sec_store)
				boolean upssR = productMapper.updateProductSeckillStore(psk);
				LogCvt.debug("秒杀商品 "+productId+" 库存处理 - 数据库 - 秒杀商品表(cb_product_seckill)处理结果:"+(upsR?"成功":"失败"));
				
				if( upssR ){
					session.commit(true);
				}else{
					session.rollback(true);
				}
			}
						
		} catch (Exception e) {
			LogCvt.error("秒杀商品 "+productId+" 库存处理 - 数据库 - 出现异常:"+e.getMessage(), e);
		} finally {
			if(session != null) session.close();
			LogCvt.debug("秒杀商品 "+productId+" 库存处理 - 数据库 - 结束");
		}
		
		return result;
	}
		
}
