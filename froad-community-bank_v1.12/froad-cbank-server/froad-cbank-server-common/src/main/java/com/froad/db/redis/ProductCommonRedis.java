package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ProductType;
import com.froad.po.Product;
import com.froad.po.ProductGroup;
import com.froad.po.ProductPresell;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class ProductCommonRedis {
    
    private static RedisManager redis = new RedisManager();

    /**
     * 将商品基础信息放到缓存里
     * @param productInfoMap
     * @return
     * @throws Exception
     * @return Map<String,String>
     */
    public static Map<String, String> set_cbbank_product_client_id_merchant_id_product_id(Map<String,Object> productInfoMap) throws Exception{
        Map<String, String> hash = new HashMap<String, String>();
        if(productInfoMap!=null){
            Product product = (Product)productInfoMap.get("product");
            if(Checker.isNotEmpty(product)){
                /* 缓存 */
                hash.put("is_marketable", ObjectUtils.toString(product.getIsMarketable(), ""));
                hash.put("product_type", ObjectUtils.toString(product.getType(), ""));
                hash.put("price", ObjectUtils.toString(product.getPrice(), "0"));
                if(product.getMarketPrice()!=null){
                    hash.put("market_price", ObjectUtils.toString(product.getMarketPrice(), "0"));
                }
                if(product.getVipPrice()!=null){
                	hash.put("vip_price", ObjectUtils.toString(product.getVipPrice(), "0"));
                }
                if(product.getPoint()!=null){
                	hash.put("point", ObjectUtils.toString(product.getPoint(), "0"));
                }
                hash.put("product_name", ObjectUtils.toString(product.getName(), ""));
                if(product.getStartTime()!=null){
                    hash.put("start_time", ObjectUtils.toString(product.getStartTime().getTime(), "0"));
                }
                if(product.getEndTime()!=null){
                    hash.put("end_time", ObjectUtils.toString(product.getEndTime().getTime(), "0"));
                }
                if(product.getDeliveryMoney()!=null){
                	hash.put("delivery_money", ObjectUtils.toString(product.getDeliveryMoney(), "0"));
                } else {
                	hash.put("delivery_money", "0");
                }
                if(product.getDeliveryTime()!=null){
                    hash.put("delivery_time", ObjectUtils.toString(product.getDeliveryTime().getTime(), "0"));
                }
                if(ProductType.group.getCode().equals(product.getType())){
                    ProductGroup groupProduct = (ProductGroup)productInfoMap.get("productGroup");
                    if(groupProduct!=null){
                        hash.put("true_buyer_number", ObjectUtils.toString(groupProduct.getTrueBuyerNumber(), "0"));
                        hash.put("virtual_buyer_number", ObjectUtils.toString(groupProduct.getVirtualBuyerNumber(), "0"));
                        if(groupProduct.getExpireStartTime()!=null){
                            hash.put("expire_start_time", ObjectUtils.toString(groupProduct.getExpireStartTime().getTime(), "0"));
                        }
                        if(groupProduct.getExpireEndTime()!=null){
                            hash.put("expire_end_time", ObjectUtils.toString(groupProduct.getExpireEndTime().getTime(), "0"));
                        }
                    }
                } else if(ProductType.presell.getCode().equals(product.getType())){
                    ProductPresell presellProduct = (ProductPresell)productInfoMap.get("productPresell");
                    if(presellProduct!=null){
                        if(presellProduct.getDeliveryStartTime()!=null){
                            hash.put("delivery_start_time", ObjectUtils.toString(presellProduct.getDeliveryStartTime().getTime(), "0"));
                        }
                        if(presellProduct.getDeliveryEndTime()!=null){
                            hash.put("delivery_end_time", ObjectUtils.toString(presellProduct.getDeliveryEndTime().getTime(), "0"));
                        }
                        hash.put("true_buyer_number", ObjectUtils.toString(presellProduct.getTrueBuyerNumber(), "0"));
                        hash.put("virtual_buyer_number", ObjectUtils.toString(presellProduct.getVirtualBuyerNumber(), "0"));
                        if(presellProduct.getExpireStartTime()!=null){
                            hash.put("expire_start_time", ObjectUtils.toString(presellProduct.getExpireStartTime().getTime(), "0"));
                        }
                        if(presellProduct.getExpireEndTime()!=null){
                            hash.put("expire_end_time", ObjectUtils.toString(presellProduct.getExpireEndTime().getTime(), "0"));
                        }
                        hash.put("cluster_state", ObjectUtils.toString(BooleanUtils.toInteger(presellProduct.getClusterState(), 1, 0, 0), "0"));
                    }
                }
                hash.put("is_point", ObjectUtils.toString(BooleanUtils.toInteger(product.getIsPoint(), 1, 0, 0), "0"));
                if(product.getCategoryTreePath()!=null){
                    hash.put("category_tree_path", ObjectUtils.toString(product.getCategoryTreePath(), ""));
                    String categoryId=null;
                    String[] pcs = product.getCategoryTreePath().split(" ");
                    if(pcs!=null && pcs.length>0){
                        categoryId = pcs[pcs.length-1];
                    }
                    hash.put("category_id", ObjectUtils.toString(categoryId, "0"));
                } else {
                	hash.put("category_tree_path", "");
                	hash.put("category_id","0");
                }
                String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId());
                redis.putMap(key, hash);
            }
        }
        return hash;
    }
	
}
