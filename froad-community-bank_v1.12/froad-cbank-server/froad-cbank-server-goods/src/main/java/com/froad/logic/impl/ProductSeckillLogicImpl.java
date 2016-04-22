/**
 * 
 * @Title: ProductSeckillLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisDataException;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.db.mysql.mapper.ProductSeckillMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ProductStatus;
import com.froad.enums.ResultCode;
import com.froad.enums.SeckillFlagEnum;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.logic.ProductSeckillLogic;
import com.froad.po.Org;
import com.froad.po.Product;
import com.froad.po.ProductSeckill;
import com.froad.po.Store;
import com.froad.util.Checker;
import com.froad.util.MongoTableName;
import com.froad.util.RedisKeyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 
 * <p>@Title: ProductSeckillLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class ProductSeckillLogicImpl implements ProductSeckillLogic {

    
    /**
     * 增加 ProductSeckill
     * @param productSeckill
     * @return Long    主键ID
     */
	@Override
	public ResultBean addProductSeckill(List<ProductSeckill> productSeckillList) {
		long startTime = System.currentTimeMillis();
		SqlSession sqlSession = null;
	    ProductSeckillMapper productSeckillMapper = null;
	    ProductMapper productMapper = null;
	    
	    ResultBean resultBean = null;
        try {
        
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            productSeckillMapper = sqlSession.getMapper(ProductSeckillMapper.class);
            productMapper = sqlSession.getMapper(ProductMapper.class);
            List<ProductSeckill> productseckilllist = new ArrayList<ProductSeckill>();
            List<Store> storelist = new ArrayList<Store>();
            Date now = new Date();
            for (ProductSeckill productSeckill : productSeckillList) {
            	ProductSeckill ori_productSeckill = productSeckillMapper.getProductSeckillById(productSeckill);
            	boolean updateFlag = false;
            	if (ori_productSeckill != null) {
            		//存在删除的秒杀配置商品信息则进行更新操作
            		if (ori_productSeckill.getIsMarketable().equals(ProductStatus.isDeleted.getCode())) {
            			updateFlag = true;
					} else {
						 resultBean = new ResultBean(ResultCode.failed,"存在对应的秒杀商品信息");
						 return resultBean;
					}
				}
            	
            	Product p = new Product();
        		p.setProductId(productSeckill.getProductId());
        		p.setClientId(productSeckill.getClientId());
            	List<Product> pList = productMapper.getProductByProductId(p);
                if (pList == null || pList.size() == 0) {
					 resultBean = new ResultBean(ResultCode.failed,"未获取到商品信息[productId=" + productSeckill.getProductId() + ", clientId=" + productSeckill.getClientId() + "]");
					 return resultBean;
                }
            	Product product = pList.get(0);
            	
				//秒杀设置时间要在商品活动时间内
	            if (product.getStartTime().getTime() > productSeckill.getStartTime().getTime() || 
	            		product.getEndTime().getTime() < productSeckill.getEndTime().getTime()) {
					 resultBean = new ResultBean(ResultCode.failed, "秒杀活动时间超出商品活动时间");
					 return resultBean;
				}
            	
            	productSeckill.setMerchantId(product.getMerchantId());
            	productSeckill.setType(product.getType());
            	productSeckill.setCreateTime(now);
            	
            	Store store = new Store();
            	CommonLogic commonLogic = new CommonLogicImpl();
            	int storeCount = commonLogic.getStoreRedis(productSeckill.getClientId(), productSeckill.getMerchantId(), productSeckill.getProductId());
				int reduceStore = productSeckill.getSecStore();
				
            	//判断秒杀库存是否<=商品库存
				if (reduceStore <= storeCount) {
					//存在删除的秒杀配置信息则进行更新操作
					if (updateFlag) {
						productSeckillMapper.updateProductSeckill(productSeckill);
					} else {
						productSeckillMapper.addProductSeckill(productSeckill);
					}
					
	            	// 更改商品秒杀标识
					p = new Product();
					p.setClientId(productSeckill.getClientId());
					p.setProductId(productSeckill.getProductId());
					if (ProductStatus.onShelf.getCode().equals(productSeckill.getIsMarketable())) {
						p.setIsSeckill(SeckillFlagEnum.IsSeckill1.getCode()); // 秒杀已上架
					} else {
						p.setIsSeckill(SeckillFlagEnum.IsSeckill2.getCode()); // 秒杀未上架
					}
	            	productMapper.updateProductIsSeckill(p);
	            	
	            	store.setClientId(product.getClientId());
	            	store.setMerchantId(product.getMerchantId());
	            	store.setProductId(product.getProductId());
	            	store.setStore(storeCount);
	            	store.setReduceStore(reduceStore);
	            	storelist.add(store);
	            	
	            	productseckilllist.add(productSeckill);
				}else{
					resultBean = new ResultBean(ResultCode.failed, "秒杀配置库存不能大于商品库存");
					return resultBean;
				}
			}
            
            //秒杀商品库存redis缓存操作
            ResultBean secStoreRedis = productSeckillStoreRedis(productseckilllist);
            //商品库存redis缓存操作
            ResultBean storeRedis = reduceStore(storelist);
            //秒杀配置表redis缓存操作
            ResultBean seckillRedis = productSeckillRedis(productseckilllist);
            //根据商品库存redis同步商品库存到mysql
            ResultBean updateProductStore = updateProductStore(storelist, productSeckillMapper);
            //秒杀商品更新商品信息mongodb为可秒杀状态
            ResultBean updateProductIsSeckill = updateProductIsSeckill(productseckilllist);
            
            sqlSession.commit(true);
            
            resultBean = new ResultBean(ResultCode.success,"添加秒杀商品成功"
            		+(ResultCode.success.getCode().equals(secStoreRedis.getCode())?"":"，秒杀商品库存redis缓存操作失败")
            		+(ResultCode.success.getCode().equals(storeRedis.getCode())?"":"，商品库存redis缓存操作失败")
            		+(ResultCode.success.getCode().equals(seckillRedis.getCode())?"":"，秒杀配置表redis缓存操作失败")
            		+(ResultCode.success.getCode().equals(updateProductStore.getCode())?"":"，根据商品库存redis同步商品库存到mysql失败")
            		+(ResultCode.success.getCode().equals(updateProductIsSeckill.getCode())?"":"，秒杀商品更新商品信息mongodb为可秒杀状态失败"));
	    } catch (Exception e) { 
	    	if(null != sqlSession)
	    		sqlSession.rollback(true);  
            LogCvt.error("添加秒杀商品失败，原因:" + e.getMessage(), e); 
            resultBean = new ResultBean(ResultCode.failed, "添加秒杀商品失败");
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        long endTime = System.currentTimeMillis();
        LogCvt.info("-------addProductSeckill:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
		return resultBean;
	}


    /**
     * 删除 ProductSeckill
     * @param productSeckill
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean deleteProductSeckill(ProductSeckill productSeckill) {
		long startTime = System.currentTimeMillis();
		SqlSession sqlSession = null;
	    ProductSeckillMapper productSeckillMapper = null;
	    ProductMapper productMapper = null;
	    
	    ResultBean resultBean = null;
	    try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            productSeckillMapper = sqlSession.getMapper(ProductSeckillMapper.class);
            productMapper = sqlSession.getMapper(ProductMapper.class);
            
            //改为已删除状态,更新库存为0
            productSeckill.setIsMarketable(ProductStatus.isDeleted.getCode());
            productSeckillMapper.deleteProductSeckill(productSeckill);
            
            //更改商品可秒杀状态
            Product p = new Product();
			p.setClientId(productSeckill.getClientId());
			p.setProductId(productSeckill.getProductId());
			p.setIsSeckill(SeckillFlagEnum.notSeckill.getCode()); // 非秒杀
        	productMapper.updateProductIsSeckill(p);
            
            //秒杀商品更新商品信息mongodb为不可秒杀状态
            List<ProductSeckill> productseckilllist = new ArrayList<ProductSeckill>();
            productseckilllist.add(productSeckill);
            //秒杀商品更新商品信息mongodb
            updateProductIsSeckill(productseckilllist);
     
            //还秒杀商品库存
        	CommonLogic commonLogic = new CommonLogicImpl();
        	int reduceStore = commonLogic.getSeckillStoreRedis(productSeckill.getClientId(), productSeckill.getProductId());
            
        	Store store = new Store();
        	List<Store> storelist = new ArrayList<Store>();
        	p = new Product();
    		p.setProductId(productSeckill.getProductId());
    		p.setClientId(productSeckill.getClientId());
        	List<Product> pList = productMapper.getProductByProductId(p);
            if (pList == null || pList.size() == 0) {
            	resultBean = new ResultBean(ResultCode.failed,"未获取到商品信息[productId=" + productSeckill.getProductId() + ", clientId=" + productSeckill.getClientId() + "]");
            	return resultBean;
            }
        	Product product = pList.get(0);
        	
        	productSeckill.setMerchantId(product.getMerchantId());
        	
        	store.setClientId(product.getClientId());
        	store.setMerchantId(product.getMerchantId());
        	store.setProductId(product.getProductId());
        	store.setStore(product.getStore());
        	store.setReduceStore(reduceStore);
        	storelist.add(store);
            
			//还商品库存redis
        	ResultBean increaseStore = increaseStore(storelist);
            //根据商品库存redis同步商品库存到mysql
			ResultBean updateProductStore = updateProductStore(storelist, productSeckillMapper);

            sqlSession.commit(true);
        	
            //删除秒杀库存redis
            String seckillStoreKey  = RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(productSeckill.getClientId(), productSeckill.getProductId());
            ResultBean delsecStoreRedis = delRedisKey(seckillStoreKey);
            
            //删除秒杀商品redis
            String seckillkey = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(productSeckill.getClientId(), productSeckill.getProductId());
            ResultBean delseckillRedis = delRedisKey(seckillkey);
            
            resultBean = new ResultBean(ResultCode.success,"删除秒杀商品成功"
            		+(ResultCode.success.getCode().equals(increaseStore.getCode())?"":"，还商品库存redis缓存操作失败")
            		+(ResultCode.success.getCode().equals(updateProductStore.getCode())?"":"，根据商品库存redis同步商品库存到mysql操作失败")
            		+(ResultCode.success.getCode().equals(delsecStoreRedis.getCode())?"":"，删除秒杀库存redis缓存操作失败")
            		+(ResultCode.success.getCode().equals(delseckillRedis.getCode())?"":"，删除秒杀商品redis缓存操作失败"));
        } catch (Exception e) { 
            if(null != sqlSession)
            	sqlSession.rollback(true);  
            LogCvt.error("删除秒杀商品失败，原因:" + e.getMessage(), e);
            resultBean = new ResultBean(ResultCode.failed, "删除秒杀商品失败");
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
	    long endTime = System.currentTimeMillis();
	    LogCvt.info("-------deleteProductSeckill:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return resultBean;
	}

	// 秒杀商品更新商品信息mongodb
	public ResultBean updateProductIsSeckill(List<ProductSeckill> productseckilllist) {
		ResultBean result = new ResultBean(ResultCode.success,"商品更新是否可秒杀状态mongodb操作成功");
    	try {
    		for(ProductSeckill productSeckill : productseckilllist){
    			MongoManager manager = new MongoManager();
    			DBObject where = new BasicDBObject();
    			where.put("client_id", productSeckill.getClientId());
    			where.put("_id", productSeckill.getProductId());
    			
    			DBObject value = new BasicDBObject();
    			if (ProductStatus.onShelf.getCode().equals(productSeckill.getIsMarketable())) {
					value.put("is_seckill", SeckillFlagEnum.IsSeckill1.getCode()); // 秒杀已上架
    			} else if (ProductStatus.isDeleted.getCode().equals(productSeckill.getIsMarketable())) {
					value.put("is_seckill", SeckillFlagEnum.notSeckill.getCode()); // 非秒杀
    			} else {
					value.put("is_seckill", SeckillFlagEnum.IsSeckill2.getCode()); // 秒杀未上架
				}
    			
    			boolean updateResult = manager.update(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set") > -1;
    	        if (!updateResult) {
    	        	throw new Exception("商品更新是否可秒杀状态mongodb失败");
    			}
    		}
		} catch (Exception e) {
			LogCvt.error("秒杀商品更新商品信息mongodb操作异常" + e.getMessage(), e);
			return new ResultBean(ResultCode.failed,e.getMessage());
		}
    	return result;
	}


    /**
     * 修改 ProductSeckill
     * @param productSeckill
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateProductSeckill(ProductSeckill productSeckill) {
		long startTime = System.currentTimeMillis();
		SqlSession sqlSession = null;
	    ProductSeckillMapper productSeckillMapper = null;
	    ProductMapper productMapper = null;
	    
	    LogCvt.info("-------进入修改productSeckill取MerchantId:"+productSeckill.getMerchantId());
	    
	    ResultBean resultBean = null;
		try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            productSeckillMapper = sqlSession.getMapper(ProductSeckillMapper.class);
            productMapper = sqlSession.getMapper(ProductMapper.class);
            
            List<ProductSeckill> productseckilllist = new ArrayList<ProductSeckill>();
            List<Store> storelist = new ArrayList<Store>();
            Store store = new Store();
            //库存操作类型：1：减库存 2：还库存
            int storeMode = 0;
            
            ProductSeckill productSeckill_orig = productSeckillMapper.getProductSeckillById(productSeckill);
            
            if (productSeckill_orig.getIsMarketable().equals(ProductStatus.isDeleted.getCode()) ||
            		productSeckill_orig.getIsMarketable().equals(ProductStatus.onShelf.getCode())) {
     			resultBean = new ResultBean(ResultCode.failed, "秒杀商品上架或者删除状态下，不能修改秒杀配置信息");
     			return resultBean;
     			
			}
            
            productSeckill.setIsMarketable(productSeckill_orig.getIsMarketable());
            productSeckill.setAuditState(ProductAuditState.noAudit.getCode());//置为待审核状态
            
            Product p = new Product();
    		p.setProductId(productSeckill.getProductId());
    		p.setClientId(productSeckill.getClientId());
        	List<Product> pList = productMapper.getProductByProductId(p);
            if (pList == null || pList.size() == 0) {
     			resultBean = new ResultBean(ResultCode.failed, "未获取到商品信息[productId=" + productSeckill.getProductId() + ", clientId=" + productSeckill.getClientId() + "]");
     			return resultBean;
            }
        	Product product = pList.get(0);
        	
        	//秒杀设置时间要在商品活动时间内
        	if (product.getStartTime().getTime() > productSeckill.getStartTime().getTime() || 
        			product.getEndTime().getTime() < productSeckill.getEndTime().getTime()) {
        		resultBean = new ResultBean(ResultCode.failed, "秒杀活动时间超出商品活动时间");
        		return resultBean;
        	}
        	
        	//获取商品库存
        	CommonLogic commonLogic = new CommonLogicImpl();
        	int storeCount = commonLogic.getStoreRedis(productSeckill_orig.getClientId(), productSeckill_orig.getMerchantId(), productSeckill_orig.getProductId());
        	
            //修改库存大于原库存，要减商品库存
            if (productSeckill.getSecStore() > productSeckill_orig.getSecStore()){
            	//修改库存差额
            	int reduceStore = productSeckill.getSecStore() -  productSeckill_orig.getSecStore();
            	
            	//判断秒杀库存是否<=商品库存
				if (reduceStore <= storeCount) {
	            	store.setClientId(product.getClientId());
	            	store.setMerchantId(product.getMerchantId());
	            	store.setProductId(product.getProductId());
	            	store.setStore(storeCount);
	            	store.setReduceStore(reduceStore);
	            	storelist.add(store);

	            	storeMode = 1;
	            	
				}else{
	     			resultBean = new ResultBean(ResultCode.failed, "秒杀配置库存不能大于商品库存");
	     			return resultBean;
				}
				
			}else if (productSeckill.getSecStore() < productSeckill_orig.getSecStore()){
				//修改库存小于原库存，要还商品库存
				int reduceStore = productSeckill_orig.getSecStore() - productSeckill.getSecStore();
            	
            	store.setClientId(product.getClientId());
            	store.setMerchantId(product.getMerchantId());
            	store.setProductId(product.getProductId());
            	store.setStore(storeCount);
            	store.setReduceStore(reduceStore);
            	storelist.add(store);
            	
            	storeMode = 2;
	            	
			}
            productSeckill.setMerchantId(product.getMerchantId());
            productseckilllist.add(productSeckill);
            
            productSeckillMapper.updateProductSeckill(productSeckill);
            
            LogCvt.info("-------从product取MerchantId:"+productSeckill.getMerchantId());
            
            if (storeMode == 1) {
            	//减商品库存redis
                reduceStore(storelist);
                //根据商品库存redis同步商品库存到mysql
                updateProductStore(storelist, productSeckillMapper);
			} else if(storeMode == 2){
				//还商品库存redis
				increaseStore(storelist);
                //根据商品库存redis同步商品库存到mysql
                updateProductStore(storelist, productSeckillMapper);
			}
            sqlSession.commit(true);
            
            //秒杀商品库存redis缓存操作
            ResultBean productSeckillStoreRedis = productSeckillStoreRedis(productseckilllist);
            
            /* 更新商品秒杀配置信息redis缓存 */
            ResultBean productSeckillRedis = productSeckillRedis(productseckilllist);
            
            resultBean = new ResultBean(ResultCode.success,"修改秒杀商品成功"
            		+(ResultCode.success.getCode().equals(productSeckillStoreRedis.getCode())?"":"，秒杀商品库存redis缓存操作失败")
            		+(ResultCode.success.getCode().equals(productSeckillRedis.getCode())?"":"，更新商品秒杀配置信息redis缓存操作失败"));
            
        } catch (Exception e) { 
            if(null != sqlSession)
            	sqlSession.rollback(true);  
            LogCvt.error("修改秒杀商品失败，原因:" + e.getMessage(), e); 
            resultBean = new ResultBean(ResultCode.failed, "修改秒杀商品失败");
        } finally { 
            if(null != sqlSession)  
            	sqlSession.close();  
        }
		long endTime = System.currentTimeMillis();
		LogCvt.info("-------updateProductSeckill:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return resultBean;
	}

    /**
     * 修改上下架状态 ProductSeckill
     * @param productSeckill
     * @return Boolean    是否成功
     */
	@Override
	public ResultBean updateProductSeckillStatus(ProductSeckill productSeckill) {
		long startTime = System.currentTimeMillis();
		SqlSession sqlSession = null;
	    ProductSeckillMapper productSeckillMapper = null;
	    ProductMapper productMapper = null;
	    
	    ResultBean resultBean = null;
		try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            productSeckillMapper = sqlSession.getMapper(ProductSeckillMapper.class);
            productMapper = sqlSession.getMapper(ProductMapper.class);
            
            productSeckillMapper.updateProductSeckillStatus(productSeckill);
            
            // 商品表可秒杀状态
            Product p = new Product();
            p.setProductId(productSeckill.getProductId());
            p.setClientId(productSeckill.getClientId());
            String shelf = "";
            if (ProductStatus.onShelf.getCode().equals(productSeckill.getIsMarketable())) {
				p.setIsSeckill(SeckillFlagEnum.IsSeckill1.getCode()); // 秒杀已上架
				shelf = "上架";
			} else {
				p.setIsSeckill(SeckillFlagEnum.IsSeckill2.getCode()); // 秒杀未上架
				shelf = "下架";
			}
            productMapper.updateProductIsSeckill(p);
            
            sqlSession.commit(true);
            
            //秒杀商品更新商品信息mongodb的可秒杀状态
            List<ProductSeckill> productseckilllist = new ArrayList<ProductSeckill>();
            productseckilllist.add(productSeckill);
            //秒杀商品更新商品信息mongodb
            ResultBean updateProductIsSeckill = updateProductIsSeckill(productseckilllist);
            
            
            /* redis缓存 */
            String key = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(productSeckill.getClientId(), productSeckill.getProductId());
            RedisManager redis = new RedisManager();
            long result = redis.hset(key, "is_marketable", ObjectUtils.toString(productSeckill.getIsMarketable(), ""));
            
            LogCvt.debug("秒杀商品"+shelf+",key:"+key+",状态:"+ObjectUtils.toString(productSeckill.getIsMarketable(), "")+",redis result:"+result);
            resultBean = new ResultBean(ResultCode.success,"秒杀商品" + shelf + "状态操作成功"
            		+(ResultCode.success.getCode().equals(updateProductIsSeckill.getCode())?"":"，商品" + shelf + "状态mongodb操作失败")
            		+(result == 0 ?"":"，秒杀商品" + shelf + "状态redis缓存操作失败"));
            
        } catch (Exception e) { 
            if(null != sqlSession)
            	sqlSession.rollback(true);  
            LogCvt.error("修改秒杀商品上下架状态失败，原因:" + e.getMessage(), e); 
            resultBean = new ResultBean(ResultCode.failed, "修改秒杀商品上下架失败");
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
		long endTime = System.currentTimeMillis();
		LogCvt.info("-------updateProductSeckillStatus:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return resultBean;
	}
	

    /**
     * 查询 ProductSeckill
     * @param productSeckill
     * @return List<ProductSeckill>    结果集合 
     */
	@Override
	public ProductSeckill getProductSeckillDetail(ProductSeckill productSeckill) {
		long startTime = System.currentTimeMillis();
		SqlSession sqlSession = null;
	    ProductSeckillMapper productSeckillMapper = null;
	    
	    ProductSeckill pc = null;
	    try{
	        sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            productSeckillMapper = sqlSession.getMapper(ProductSeckillMapper.class);
            
	        pc = productSeckillMapper.getProductSeckillById(productSeckill); 
	        if(pc != null){
	        	return pc;
	        }
	    } catch (Exception e) {
            // TODO: handle exception
	        LogCvt.error("查询秒杀商品详情失败，原因:" + e.getMessage(), e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
	    long endTime = System.currentTimeMillis();
	    LogCvt.info("-------getProductSeckillDetail:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
		return null;
	}



    @Override
    public List<ProductSeckill> findSeckillByPage(
            Page<ProductSeckill> page,ProductSeckill productSeckill) {
    	long startTime = System.currentTimeMillis();
    	SqlSession sqlSession = null;
        ProductSeckillMapper productSeckillMapper = null;
        
        List<ProductSeckill> list = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            productSeckillMapper = sqlSession.getMapper(ProductSeckillMapper.class);

            if(Checker.isNotEmpty(productSeckill.getClientId()) && Checker.isNotEmpty(productSeckill.getOrgCode())){
                CommonLogic comLogic = new CommonLogicImpl();
                //根绝机构号查询该机构可以看到的商品
                Org bankOrg = comLogic.getOrgByOrgCode(productSeckill.getOrgCode(), productSeckill.getClientId());
                if(bankOrg!=null){
                	productSeckill.setProOrgCode(null);
                	productSeckill.setOrgCode(null);
                	productSeckill.setCityOrgCode(null);
                	productSeckill.setCountryOrgCode(null);
                    if(OrgLevelEnum.orgLevel_one.getLevel().equals(bankOrg.getOrgLevel())){
                    	productSeckill.setProOrgCode(bankOrg.getOrgCode());
                    } else if(OrgLevelEnum.orgLevel_two.getLevel().equals(bankOrg.getOrgLevel())){
                    	productSeckill.setProOrgCode(bankOrg.getProvinceAgency());
                    	productSeckill.setCityOrgCode(bankOrg.getOrgCode());
                    } else if(OrgLevelEnum.orgLevel_three.getLevel().equals(bankOrg.getOrgLevel())){
                    	productSeckill.setProOrgCode(bankOrg.getProvinceAgency());
                    	productSeckill.setCityOrgCode(bankOrg.getCityAgency());
                    	productSeckill.setCountryOrgCode(bankOrg.getOrgCode());
                    } else if(OrgLevelEnum.orgLevel_four.getLevel().equals(bankOrg.getOrgLevel())){
                    	productSeckill.setProOrgCode(bankOrg.getProvinceAgency());
                    	productSeckill.setCityOrgCode(bankOrg.getCityAgency());
                    	productSeckill.setCountryOrgCode(bankOrg.getCountyAgency());
                    	productSeckill.setOrgCode(bankOrg.getOrgCode());
                    }
                }
            }
            
            list =  productSeckillMapper.findSeckillByPage(page, productSeckill);
        }catch (Exception e) {
            LogCvt.error("分页查询 秒杀商品失败，原因:" + e.getMessage(), e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        long endTime = System.currentTimeMillis();
        LogCvt.info("-------findSeckillByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return list;
    }
    
    private ResultBean productSeckillRedis(List<ProductSeckill> productseckilllist){
    	ResultBean result = new ResultBean(ResultCode.success,"秒杀库存配置redis操作成功");
        /* redis缓存 */
    	try {
    		for(ProductSeckill productSeckill : productseckilllist){
    			Map<String, String> hash = new HashMap<String, String>();
    			hash.put("sec_store", ObjectUtils.toString(productSeckill.getSecStore(), ""));
    			hash.put("sec_price", ObjectUtils.toString(productSeckill.getSecPrice(),""));
    			hash.put("vip_sec_price", ObjectUtils.toString(productSeckill.getVipSecPrice(), ""));
    			hash.put("buy_limit", ObjectUtils.toString(productSeckill.getBuyLimit(), ""));
    			hash.put("is_marketable", ObjectUtils.toString(productSeckill.getIsMarketable(), ""));
    			hash.put("start_time", ObjectUtils.toString(productSeckill.getStartTime().getTime(), ""));
    			hash.put("end_time", ObjectUtils.toString(productSeckill.getEndTime().getTime(), ""));
    			hash.put("merchant_id", ObjectUtils.toString(productSeckill.getMerchantId(), ""));
    			String key = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(productSeckill.getClientId(), productSeckill.getProductId());
    			RedisManager redis = new RedisManager();
    			redis.putMap(key, hash);
    		}
			
		} catch (Exception e) {
			LogCvt.error("Redis库存操作异常" + e.getMessage(), e);
			return new ResultBean(ResultCode.failed,e.getMessage());
		}
    	return result;
    }

    private ResultBean productSeckillStoreRedis(List<ProductSeckill> productseckilllist){
    	ResultBean result = new ResultBean(ResultCode.success,"秒杀库存redis操作成功");
        /* 秒杀商品库存redis缓存 */
    	try {
    		for(ProductSeckill productSeckill : productseckilllist){
    			String key = RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(productSeckill.getClientId(), productSeckill.getProductId());
    			RedisManager redis = new RedisManager();
    			redis.putString(key, ObjectUtils.toString(productSeckill.getSecStore(), ""));
    		}
			
		} catch (Exception e) {
			LogCvt.error("秒杀商品库存操作异常" + e.getMessage(), e);
			return new ResultBean(ResultCode.failed,e.getMessage());
		}
    	return result;
    }

	public ResultBean reduceStore(List<Store> list) {
		//冻结库存
		ResultBean result = new ResultBean(ResultCode.success,"redis减库存成功");
		List<Store> sucessStoreList = new ArrayList<Store>();
		Jedis jedis = RedisManager.getJedis(RedisManager.write);
		try {
			for(Store store : list){
				String prodcutKey = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(store.getClientId(), store.getMerchantId(), store.getProductId());
				Long stock = jedis.decrBy(prodcutKey,store.getReduceStore());
				sucessStoreList.add(store);
				LogCvt.info("减库存操作：[prodcutKey=" + prodcutKey 
						+ ", oldStore=" + jedis.get(prodcutKey) 
						+ ", newStore=" + stock 
						+ ", decrBy=" + store.getReduceStore());
				
				if (stock < 0) {
					//减库存失败时，还库存
					LogCvt.info("减库存操作失败：[prodcutKey=" + prodcutKey);
					processStore(sucessStoreList,jedis,true);
					RedisManager.returnJedis(jedis,RedisManager.write);
					return new ResultBean(ResultCode.failed,"减库存失败，[商品ID:"+store.getProductId()+"]库存不足。");
				}
			}
		} catch (Exception e) {
			//减库存异常时，还库存
			LogCvt.error("库存操作异常" + e.getMessage(), e);
			processStore(sucessStoreList,jedis,true);
			RedisManager.returnJedis(jedis,RedisManager.write);
			return new ResultBean(ResultCode.failed,e.getMessage());
		}
		RedisManager.returnJedis(jedis,RedisManager.write);
		return result;
	}
	

	public ResultBean increaseStore(List<Store> list) {
		//还库存
		ResultBean result = new ResultBean(ResultCode.success,"redis还库存成功");
		List<Store> sucessStoreList = new ArrayList<Store>();
		Jedis jedis = RedisManager.getJedis(RedisManager.write);
		try {
			for(Store store : list){
				String prodcutKey = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(store.getClientId(), store.getMerchantId(), store.getProductId());
				Long stock = jedis.incrBy(prodcutKey, store.getReduceStore());
				sucessStoreList.add(store);
				LogCvt.info("还库存操作：[prodcutKey=" + prodcutKey 
						+ ", oldStore=" + jedis.get(prodcutKey) 
						+ ", newStore=" + stock 
						+ ", incrBy=" + store.getReduceStore());
				
				/*if (stock < 0) {
					//减库存失败时，还库存
					LogCvt.info("还库存操作失败");
					processStore(sucessStoreList,jedis,true);
					RedisManager.returnJedis(jedis);
					return new ResultBean(ResultCode.failed,"减库存失败，[商品ID:"+store.getProductId()+"]库存不足。");
				}*/
			}
		}  catch (JedisDataException e) {
			//还库存异常时，还库存
			LogCvt.error("还库存操作数据库异常" + e.getMessage(), e);
			processStore(sucessStoreList,jedis,false);
			RedisManager.returnJedis(jedis,RedisManager.write);
			return new ResultBean(ResultCode.failed,e.getMessage());
		} catch (Exception e) {
			//还库存异常时，还库存
			LogCvt.error("还库存操作异常" + e.getMessage(), e);
			processStore(sucessStoreList,jedis,false);
			RedisManager.returnJedis(jedis,RedisManager.write);
			return new ResultBean(ResultCode.failed,e.getMessage());
		}
		RedisManager.returnJedis(jedis,RedisManager.write);
		return result;
	}
	

	public ResultBean updateProductStore(List<Store> list ,ProductSeckillMapper productSeckillMapper) {
		//库存缓存从redis更新至mysql
		LogCvt.debug("mysql：商品库存缓存从redis同步至mysql");
		ResultBean result = new ResultBean(ResultCode.success,"mysql商品库存更新成功");
		RedisManager redis = new RedisManager();
		try {
			for(Store store : list){
				Product product = new Product();
				product.setClientId(store.getClientId());
				product.setMerchantId(store.getMerchantId());
				product.setProductId(store.getProductId());
				String prodcutKey  = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(store.getClientId(), store.getMerchantId(), store.getProductId());
				int storeCount = Integer.parseInt(redis.getString(prodcutKey));
				product.setStore(storeCount);
				LogCvt.info("mysql更新商品库存数：[product_id:"+store.getProductId()+" 数量："+storeCount+"]");
				Boolean flag = productSeckillMapper.updateProductStore(product);
				if(!flag){
					throw new FroadBusinessException(ResultCode.failed.getCode(),"mysql商品库存更新失败");
				}
			}
			result = new ResultBean(ResultCode.success,"mysql商品库存更新成功");
		}catch(Exception e){
			LogCvt.error("mysql商品库存更新失败" + e.getMessage(), e);
			return new ResultBean(ResultCode.failed,"mysql商品库存更新失败");
		}
		return result;
	}
    
	/**
	 * 库存操作
	 * @param list 库存信息
	 * @param jedis
	 * @param flag true为加库存，false为减库存
	 */
	public void processStore(List<Store> list,Jedis jedis,boolean flag){
		LogCvt.debug("还原库存操作开始...");
		for(Store store : list){
			int count = flag ? store.getReduceStore() : 0 - store.getReduceStore();
			String prodcutKey = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(store.getClientId(), store.getMerchantId(), store.getProductId());
			Long stock = jedis.incrBy(prodcutKey,count);
			LogCvt.info("减库存操作：[prodcutKey=" + prodcutKey 
					+ ", oldStore=" + jedis.get(prodcutKey) 
					+ ", newStore=" + stock 
					+ ", decrBy=" + store.getReduceStore());
			
		}
		LogCvt.debug("还原存操作结束.");
	}

	private ResultBean delRedisKey(String redisKey){
		ResultBean result = new ResultBean(ResultCode.success,"删除redisKey操作成功");
	    try {
    		RedisManager redis = new RedisManager();
    		redis.del(redisKey);
		} catch (Exception e) {
			LogCvt.error("redisKey删除操作异常" + e.getMessage(), e);
			return new ResultBean(ResultCode.failed,e.getMessage());
		}
    	return result;
	}
	

	@Override
	public ProductSeckill getH5ProductSeckillDetail(
			ProductSeckill productSeckill) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ProductSeckill> findH5SeckillByPage(Page<ProductSeckill> page,
			ProductSeckill productSeckill) {
		long startTime = System.currentTimeMillis();
		SqlSession sqlSession = null;
	    ProductSeckillMapper productSeckillMapper = null;
	    
		List<ProductSeckill> list = null;
        try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            productSeckillMapper = sqlSession.getMapper(ProductSeckillMapper.class);
            list =  productSeckillMapper.findH5SeckillByPage(page, productSeckill);
        }catch (Exception e) {
            LogCvt.error("分页查询H5秒杀商品失败，原因:" + e.getMessage(), e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        }
        long endTime = System.currentTimeMillis();
        LogCvt.info("-------findH5SeckillByPage:耗时"+(endTime - startTime)+"--startTime:"+startTime+"---endTime:"+endTime);
        return list;
	}
	

}