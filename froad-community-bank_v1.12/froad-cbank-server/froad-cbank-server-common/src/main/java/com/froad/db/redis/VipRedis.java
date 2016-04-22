package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import com.froad.db.redis.impl.RedisManager;
import com.froad.po.VipProduct;
import com.froad.util.RedisKeyUtil;

public class VipRedis {

    private static RedisManager redis = new RedisManager();
    
    /**
     * 获取某个客户端的VIP规则
     * @param client_id
     * @return Map<String,String>
     */
    public static Map<String, String> get_cbbank_vip_product_client_id(String client_id){
        String key = RedisKeyUtil.cbbank_vip_product_client_id(client_id);
        Map<String, String> result = redis.getMap(key);
        return converRedisMap(result);
    }
    
    /**
     * 缓存某个客户端的VIP规则  每个客户端最多只有一条
     * @param vipProduct
     * @return Boolean
     */
    public static Boolean set_cbbank_vip_product_client_id(VipProduct vipProduct) {
        Map<String, String> hash = new HashMap<String, String>();
        
        hash.put("vip_id", vipProduct.getVipId());
        hash.put("activities_name", vipProduct.getActivitiesName());
        hash.put("create_time", ObjectUtils.toString(vipProduct.getCreateTime().getTime(), ""));
        hash.put("vip_price", ObjectUtils.toString(vipProduct.getVipPrice()));
        hash.put("status", vipProduct.getStatus());
        hash.put("count", ObjectUtils.toString(vipProduct.getCount()));
        hash.put("remark", ObjectUtils.toString(vipProduct.getRemark()));
        hash.put("client_name", vipProduct.getClientName());
        
        String key = RedisKeyUtil.cbbank_vip_product_client_id(vipProduct.getClientId());
        String result = redis.putMap(key, hash);
        return "OK".equals(result);
    }
    
    /**
     *  Rdis获取的Map，如果为空业务返回Map不是空对象
     *  如果size是空返回null，利于前端检查
      * @Title: converRedisMap
      * @author: share 2015年4月11日
      * @modify: share 2015年4月11日
      * @param @param redisMap
      * @param @return    
      * @return Map<String,String>    
      * @throws
     */
    public static Map<String,String> converRedisMap(Map<String,String> redisMap){
        if(redisMap != null && redisMap.isEmpty()){
            return null;
        }
        return redisMap;
    }
    
    /**
     * 重置缓存某个客户端的VIP规则为空
     * @param vipProduct
     * @return Boolean
     */
    public static Boolean reset_cbbank_vip_product_client_id(String clientId) {
        String key = RedisKeyUtil.cbbank_vip_product_client_id(clientId);
        Long result = redis.del(key);
        return result>0;
    }
    
}
