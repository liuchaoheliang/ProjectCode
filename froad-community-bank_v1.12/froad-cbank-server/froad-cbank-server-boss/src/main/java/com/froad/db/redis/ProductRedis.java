package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ProductType;
import com.froad.logback.LogCvt;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.po.Product;
import com.froad.po.ProductGroup;
import com.froad.po.ProductInfo;
import com.froad.po.ProductPresell;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class ProductRedis {
    
    private static RedisManager redis = new RedisManager();

     /**
     * 商品redis缓存信息
     * @param productInfo
     * @return boolean
     */
    public static boolean addProductRedis(ProductInfo productInfo) {
        try{
            Product product = productInfo.getProduct();
            if(product!=null){
                LogCvt.debug("设置商品基本信息,库存,每个门店最大提货缓存开始……");
                ProductGroup groupProduct = null;
                ProductPresell presellProduct = null;
                if(product.getType().equals(ProductType.group.getCode())){
                    groupProduct = productInfo.getProductGroup();
                } else if(product.getType().equals(ProductType.presell.getCode())){
                    presellProduct = productInfo.getProductPresell();
                   
                }
                
                //库存redis缓存
                LogCvt.debug("product参数转换:"+product.getClientId()+product.getMerchantId()+"--"+ product.getProductId()+"--"+product.getStore());
                redis.putString(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId()), ObjectUtils.toString(product.getStore(), ""));
                
                //每个门店最大提货redis缓存
                if(presellProduct!=null && presellProduct.getMaxPerOutlet()!=null){
                    redis.putString(RedisKeyUtil.cbbank_product_presell_max_product_id(presellProduct.getProductId()), presellProduct.getMaxPerOutlet()+"");
                }
                
                //商品基本信息redis缓存 (改为调用Common公用设置redis方法 20151216 ll update)
                Map<String,Object> productInfoMap = new HashMap<String,Object>();
                productInfoMap.put("product", product);
                productInfoMap.put("productGroup", groupProduct);
                productInfoMap.put("productPresell", presellProduct);
                ProductCommonRedis.set_cbbank_product_client_id_merchant_id_product_id(productInfoMap);
                //new CommonLogicImpl().getProductRedis(product.getClientId(), product.getMerchantId(), product.getProductId());
                
//                Map<String, String> hash = new HashMap<String, String>();
//                hash.put("is_marketable", ObjectUtils.toString(product.getIsMarketable(), ""));
//                hash.put("product_type", ObjectUtils.toString(product.getType(), ""));
//                hash.put("price", ObjectUtils.toString(product.getPrice(), ""));
//                if(product.getMarketPrice()!=null){
//                    hash.put("market_price", ObjectUtils.toString(product.getMarketPrice(), ""));
//                }
//                hash.put("product_name", ObjectUtils.toString(product.getName(), ""));
//                if(product.getStartTime()!=null){
//                    hash.put("start_time", ObjectUtils.toString(product.getStartTime().getTime(), ""));
//                }
//                if(product.getEndTime()!=null){
//                    hash.put("end_time", ObjectUtils.toString(product.getEndTime().getTime(), ""));
//                }
//                if(product.getDeliveryMoney()!=null&&product.getDeliveryMoney()>0){
//                    hash.put("delivery_money", ObjectUtils.toString(product.getDeliveryMoney(), ""));
//                }
//                if(groupProduct!=null){
//                    hash.put("true_buyer_number", ObjectUtils.toString(groupProduct.getTrueBuyerNumber(), ""));
//                    hash.put("virtual_buyer_number", ObjectUtils.toString(groupProduct.getVirtualBuyerNumber(), ""));
//                    if(groupProduct.getExpireStartTime()!=null){
//                        hash.put("expire_start_time", ObjectUtils.toString(groupProduct.getExpireStartTime().getTime(), ""));
//                    }
//                    if(groupProduct.getExpireEndTime()!=null){
//                        hash.put("expire_end_time", ObjectUtils.toString(groupProduct.getExpireEndTime().getTime(), ""));
//                    }
//                }
//                if(presellProduct!=null){
//                    if(presellProduct.getDeliveryStartTime()!=null){
//                        hash.put("delivery_start_time", ObjectUtils.toString(presellProduct.getDeliveryStartTime().getTime(), ""));
//                    }
//                    if(presellProduct.getDeliveryEndTime()!=null){
//                        hash.put("delivery_end_time", ObjectUtils.toString(presellProduct.getDeliveryEndTime().getTime(), ""));
//                    }
//                    hash.put("true_buyer_number", ObjectUtils.toString(presellProduct.getTrueBuyerNumber(), ""));
//                    hash.put("virtual_buyer_number", ObjectUtils.toString(presellProduct.getVirtualBuyerNumber(), ""));
//                    if(presellProduct.getExpireStartTime()!=null){
//                        hash.put("expire_start_time", ObjectUtils.toString(presellProduct.getExpireStartTime().getTime(), ""));
//                    }
//                    if(presellProduct.getExpireEndTime()!=null){
//                        hash.put("expire_end_time", ObjectUtils.toString(presellProduct.getExpireEndTime().getTime(), ""));
//                    }
//                    hash.put("cluster_state", ObjectUtils.toString(BooleanUtils.toInteger(presellProduct.getClusterState(), 1, 0, 0), ""));
//                }
//                hash.put("is_point", ObjectUtils.toString(BooleanUtils.toInteger(product.getIsPoint(), 1, 0, 0), ""));
//                
//                String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
//                redis.putMap(key, hash);
            }
            return true;
        } catch (Exception e) {
            LogCvt.error("设置商品基本信息,库存,每个门店最大提货redis缓存异常：" +e.getMessage(),e);
            return false;
        }
    }
    
    /**
     * 商品redis缓存信息
     * @param productInfo
     * @return boolean
     */
    public static boolean updateProductRedis(ProductInfo productInfo,Product oldProduct) {
        try{
            Product product = productInfo.getProduct();
            if(product!=null){
            	LogCvt.debug("更新商品基本信息,库存,每个门店最大提货缓存开始……");
                ProductGroup groupProduct = null;
                ProductPresell presellProduct = null;
                if(oldProduct.getType().equals(ProductType.group.getCode())){
                    groupProduct = productInfo.getProductGroup();
                } else if(oldProduct.getType().equals(ProductType.presell.getCode())){
                    presellProduct = productInfo.getProductPresell();
                   
                }
                
                
                //库存redis缓存
                if(product.getStore()!=null && product.getStore()>0){
                    String storeKey = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(oldProduct.getClientId(), oldProduct.getMerchantId(), oldProduct.getProductId());
                    LogCvt.info("product参数转换:"+storeKey+"-"+product.getStore());
                    redis.putString(storeKey, ObjectUtils.toString(product.getStore(), ""));
                }
                
                //每个门店最大提货redis缓存
                if(presellProduct!=null && presellProduct.getMaxPerOutlet()!=null){
                    redis.putString(RedisKeyUtil.cbbank_product_presell_max_product_id(oldProduct.getProductId()), presellProduct.getMaxPerOutlet()+"");
                }
                
                //商品基本信息redis缓存(改为调用Common公用设置redis方法 20151216 ll update)
//                Map<String,Object> productInfoMap = new HashMap<String,Object>();
//                productInfoMap.put("product", product);
//                productInfoMap.put("productGroup", groupProduct);
//                productInfoMap.put("productPresell", presellProduct);
//                ProductCommonRedis.set_cbbank_product_client_id_merchant_id_product_id(productInfoMap);
                String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(oldProduct.getClientId(), oldProduct.getMerchantId(), oldProduct.getProductId());
                redis.del(key);
                new CommonLogicImpl().getProductRedis(product.getClientId(), product.getMerchantId(), product.getProductId());
                
//                Map<String, String> hash = redis.getMap(key);
//                if(hash!=null){
//                    hash.put("is_marketable", ObjectUtils.toString(product.getIsMarketable(), ""));
//                    if(product.getPrice()!=null){
//                        hash.put("price", ObjectUtils.toString(product.getPrice(), ""));
//                    }
//                    if(product.getMarketPrice()!=null){
//                        hash.put("market_price", ObjectUtils.toString(product.getMarketPrice(), ""));
//                    }
//                    if(Checker.isNotEmpty(product.getName())){
//                        hash.put("product_name", ObjectUtils.toString(product.getName(), ""));
//                    }
//                    if(product.getStartTime()!=null){
//                        hash.put("start_time", ObjectUtils.toString(product.getStartTime().getTime(), ""));
//                    }
//                    if(product.getEndTime()!=null){
//                        hash.put("end_time", ObjectUtils.toString(product.getEndTime().getTime(), ""));
//                    }
//                    if(product.getDeliveryMoney()!=null&&product.getDeliveryMoney()>0){
//                        hash.put("delivery_money", ObjectUtils.toString(product.getDeliveryMoney(), ""));
//                    }
//                    if(groupProduct!=null){
//                        if(groupProduct.getTrueBuyerNumber()!=null){
//                            hash.put("true_buyer_number", ObjectUtils.toString(groupProduct.getTrueBuyerNumber(), ""));
//                        }
//                        if(groupProduct.getVirtualBuyerNumber()!=null){
//                            hash.put("virtual_buyer_number", ObjectUtils.toString(groupProduct.getVirtualBuyerNumber(), ""));
//                        }
//                        if(groupProduct.getExpireStartTime()!=null){
//                            hash.put("expire_start_time", ObjectUtils.toString(groupProduct.getExpireStartTime().getTime(), ""));
//                        }
//                        if(groupProduct.getExpireEndTime()!=null){
//                            hash.put("expire_end_time", ObjectUtils.toString(groupProduct.getExpireEndTime().getTime(), ""));
//                        }
//                    }
//                    if(presellProduct!=null){
//                        if(presellProduct.getDeliveryStartTime()!=null){
//                            hash.put("delivery_start_time", ObjectUtils.toString(presellProduct.getDeliveryStartTime().getTime(), ""));
//                        }
//                        if(presellProduct.getDeliveryEndTime()!=null){
//                            hash.put("delivery_end_time", ObjectUtils.toString(presellProduct.getDeliveryEndTime().getTime(), ""));
//                        }
//                        if(presellProduct.getTrueBuyerNumber()!=null){
//                            hash.put("true_buyer_number", ObjectUtils.toString(presellProduct.getTrueBuyerNumber(), ""));
//                        }
//                        if(presellProduct.getVirtualBuyerNumber()!=null){
//                            hash.put("virtual_buyer_number", ObjectUtils.toString(presellProduct.getVirtualBuyerNumber(), ""));
//                        }
//                        if(presellProduct.getExpireStartTime()!=null){
//                            hash.put("expire_start_time", ObjectUtils.toString(presellProduct.getExpireStartTime().getTime(), ""));
//                        }
//                        if(presellProduct.getExpireEndTime()!=null){
//                            hash.put("expire_end_time", ObjectUtils.toString(presellProduct.getExpireEndTime().getTime(), ""));
//                        }
//                        if(presellProduct.getClusterState()!=null){
//                            hash.put("cluster_state", ObjectUtils.toString(BooleanUtils.toInteger(presellProduct.getClusterState(), 1, 0, 0), ""));
//                        }
//                    }
//                    redis.putMap(key, hash);
//                }
            }
            return true;
        } catch (Exception e) {
            LogCvt.error("更新商品基本信息,库存,每个门店最大提货redis缓存异常：" +e,e);
            return false;
        }
    }
	/**
	 * 更新商品缓存信息
	 * @Title: auditProduct 
	 * @Description: TODO
	 * @param audit
	 * @return
	 * @throws
	 */
    public static Boolean auditProduct(Product audit){
		String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(audit.getClientId(), audit.getMerchantId(), audit.getProductId());
		Long result=0l;
		if(Checker.isNotEmpty()){
			result = redis.hset(key, "is_marketable", audit.getIsMarketable());
		}
		
		return result != -1;
	}
    
    
    /**
	 * 更新商品分类缓存信息
	 * @Title: auditProductCategory 
	 * @Description: TODO
	 * @author: froad-ll 2015年12月16日
	 * @modify: froad-ll 2015年12月16日
	 * @param product商品对象
	 * @param productCategoryTreePath   分类treePath
	 * @return
	 * @throws
	 */
	public static Boolean auditProductCategory(Product product,String productCategoryTreePath){
		Map<String, String> hash = new HashMap<String, String>();
		
		String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
		if(Checker.isNotEmpty(productCategoryTreePath)){
			String categoryId=null;
            String[] pcs = productCategoryTreePath.split(" ");
            if(pcs!=null && pcs.length>0){
                categoryId = pcs[pcs.length-1];
            }
            hash.put("category_id", ObjectUtils.toString(categoryId, ""));
            hash.put("category_tree_path", ObjectUtils.toString(productCategoryTreePath, ""));
        } else {
        	hash.put("category_id", "");
         	hash.put("category_tree_path", "");
        }
         
		String result = redis.putMap(key, hash);
		return "OK".equals(result);
         
	}
	
	
	
}
