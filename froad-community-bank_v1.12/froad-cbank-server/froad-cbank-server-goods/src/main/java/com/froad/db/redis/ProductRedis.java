package com.froad.db.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.OutletDetailSimple;
import com.froad.po.ProductDetailSimple;
import com.froad.po.SimpleProductDetail;
import com.froad.po.mongo.ProductActivitiesInfo;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.mongo.ProductImage;
import com.froad.thrift.vo.OutletDetailSimpleVo;
import com.froad.util.Arith;
import com.froad.util.Checker;
import com.froad.util.GoodsConstants;
import com.froad.util.RedisKeyUtil;
import com.alibaba.fastjson.JSON;

import org.apache.commons.collections.CollectionUtils;

public class ProductRedis {
    
    private static RedisManager redisManager = new RedisManager();

    public static long setLock(String key,int time) throws Exception {
        long result = redisManager.setnx(key, "1");
        if (result > 0) {
        	expire(key, time);
        }
        return result;
    }
    
    public static void expire(String key,int time) throws Exception {
    	redisManager.expire(key, time * 60); // 设置超时时间
        LogCvt.debug("设置锁[" + key + "]超时时间为" + time * 60 + "秒,成功!");
    }
    
    public static boolean set_cbbank_product_h5_redis(String key, List<ProductDetail> productDetails, int time) throws Exception {
        String[] serStrArr = new String[productDetails.size()] ;
        for (int i = 0; i < productDetails.size(); i++) {
            serStrArr[i] = JSON.toJSONString(formatProductDetail(productDetails.get(i)));
        }
        Long result = redisManager.rpush(key, serStrArr);
        if (result > 0) {
            int cacheTimeout = time * 60;// 设置超时时间 为 5分钟
            LogCvt.debug("设置个人H5附近搜索门缓存[" + key + "]成功,缓存数据条数[" + serStrArr.length + "]条!");
            redisManager.expire(key, cacheTimeout); // 设置超时时间
            LogCvt.debug("设置个人H5附近搜索门缓存[" + key + "]超时时间为" + cacheTimeout + "秒,成功!");
        }
        return result > 0;
    }
    
    private static SimpleProductDetail formatProductDetail(ProductDetail pd){
        SimpleProductDetail spd = new SimpleProductDetail();
        spd.setClientId(pd.getClientId());
        spd.setMerchantId(pd.getMerchantId());
        spd.setId(pd.getId());
        spd.setName(pd.getName());
        spd.setType(pd.getProductType());
        spd.setPrice(pd.getPrice());  // 价格
        spd.setSellCount(pd.getSellCount());
        spd.setMarketPrice(pd.getMarketPrice());
        spd.setStartTime(pd.getStartTime());
        spd.setEndTime(pd.getEndTime());
        spd.setIsSeckill(pd.getIsSeckill());// 是否秒杀
        spd.setFullName(pd.getFullName());  //商品全名
        spd.setBriefIntroduction(pd.getBriefIntroduction()); // 简介
        spd.setIsMarketable(pd.getIsMarketable());
        if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
            ProductImage imagepo = pd.getImageInfo().get(0);
            spd.setSmallImgUrl(imagepo.getThumbnail());
        }
        spd.setRackTime(pd.getRackTime());//上架时间
        spd.setVipPrice(pd.getVipPrice());//VIP价格
        List<ProductActivitiesInfo> activitiesPos = pd.getActivitiesInfo();
        if(activitiesPos!=null && activitiesPos.size()>0){
            //活动表缓存信息
            ProductActivitiesInfo activitiesPo = activitiesPos.get(0);
            spd.setActivitiesId(activitiesPo.getActivitiesId());
        }
        return spd;
    }
    
    private static ProductDetail formatSimplleProductDetail(SimpleProductDetail spd){
        ProductDetail pd = new ProductDetail();
        pd.setId(spd.getId());
        pd.setProductType(spd.getType());
        pd.setClientId(spd.getClientId());
        pd.setIsMarketable(spd.getIsMarketable());
        pd.setMerchantId(spd.getMerchantId());
        pd.setName(spd.getName());
        pd.setFullName(spd.getFullName());  //商品全名
        pd.setPrice(spd.getPrice());                  // 价格
        pd.setMarketPrice(spd.getMarketPrice());
        pd.setSellCount(spd.getSellCount());
        pd.setStartTime(spd.getStartTime());
        pd.setEndTime(spd.getEndTime());
        pd.setRackTime(spd.getRackTime());//上架时间
        pd.setIsSeckill(spd.getIsSeckill());// 是否秒杀
        pd.setBriefIntroduction(spd.getBriefIntroduction()); // 简介
        if(spd.getSmallImgUrl()!=null && !"".equals(spd.getSmallImgUrl())){
            ProductImage imagepo = new ProductImage();
            imagepo.setThumbnail(spd.getSmallImgUrl());
            List<ProductImage> imageInfo = new ArrayList<ProductImage>();
            imageInfo.add(imagepo);
            pd.setImageInfo(imageInfo);
        }
        pd.setVipPrice(spd.getVipPrice());
        if(spd.getActivitiesId()!=null && spd.getActivitiesId()>0){
            List<ProductActivitiesInfo> activitiesInfo = new ArrayList<ProductActivitiesInfo>();
            ProductActivitiesInfo activitiesPo = new ProductActivitiesInfo();
            activitiesPo.setActivitiesId(spd.getActivitiesId());
            pd.setActivitiesInfo(activitiesInfo);
        }
        return pd;
    }
    
    public static List<ProductDetail> get_cbbank_product_h5_redis(String key,long start,long end) throws Exception {
        List<ProductDetail> result = null;
        List<String> list = redisManager.lrange(key, start, end);
        LogCvt.debug("Redis缓存使用lrange分页查询H5商品key=[" + key + "]的数据, start=[" + start + "], end=[" + end + "], 共返回["+list.size()+"]条数据!");
        if (CollectionUtils.isNotEmpty(list)) {
            result = new ArrayList<ProductDetail>();
            for (String serStr : list) {
                result.add(formatSimplleProductDetail(JSON.parseObject(serStr, SimpleProductDetail.class)));
            }
        }
        return result;
    }
    
    /**
	 * 删除银行用户key缓存<key:cbbank:product_add_comment:lock:client_id:product_id:order_id>
	 * @param client_id 客户端Id
	 * @param product_id 商品Id
	 * @param order_id 订单Id
	 * @return
	 */
	public static boolean del_cbbank_product_add_comment_lock_client_id_product_id_order_id(String client_id,String product_id,String order_id) {
		String key = RedisKeyUtil.cbbank_product_add_comment_lock_client_id_product_id_order_id(client_id,product_id,order_id) ;
		return del_key(key);
	}
	
	public static boolean del_key(String key) {
        redisManager.del(key);
        return true;
    }
	
	/**
	 * 设置商户id的信息，特惠商品列表用到
	 * @param key
	 * @param outletDetailSimpleVos
	 * @throws Exception
	 * @return boolean
	 * 
	 */
	public static boolean set_cbbank_product_merchant_h5_redis(String key, List<OutletDetailSimpleVo> outletDetailSimpleVos,int time) throws Exception {
        LogCvt.debug("设置个人H5特惠商品列表搜索商户id缓存:");
        for (int i = 0; i < outletDetailSimpleVos.size(); i++) {
            zadd(time,i,key,outletDetailSimpleVos.get(i).getMerchantId());
        }
        LogCvt.debug("设置个人H5特惠商品列表搜索商户id缓存[" + key + "]成功,缓存数据条数[" + outletDetailSimpleVos.size() + "]条!");
        return true;
    }
	
	/**
     * 设置商户id的信息，特惠商品列表用到
     * @param key
     * @param outletDetailSimpleVos
     * @throws Exception
     * @return boolean
     * 
     */
    public static String set_cbbank_group_product_merchant_h5_redis(String key, List<String> productMerchantIds,int time) throws Exception {
    	LogCvt.debug("设置个人H5特惠商品列表搜索满足条件的商品商户id缓存:"+productMerchantIds);
    	Map<String,String> valueMap = new HashMap<String,String>();
		for(String productMerchantId : productMerchantIds){
			valueMap.put(productMerchantId, productMerchantId);
		}
		del_key(key);
		String result = redisManager.putMap(key, valueMap);
		if ("OK".equals(result)) {
            redisManager.expire(key, time * 60); // 设置超时时间
            LogCvt.debug("设置个人H5特惠商品列表搜索满足条件的商品商户id缓存[" + key + "],value:{"+redisManager.getMap(key)+"}成功,缓存数据条数[" + productMerchantIds.size() + "]条!");
        }
        return result;
    }
    
    private static void zadd(int time, double i, String key, String value) throws Exception {
        int cacheTimeout = time * 60;// 设置超时时间 为 time分钟
        Long result = redisManager.zadd(key, i, value);
        if (result > 0) {
            redisManager.expire(key, cacheTimeout); // 设置超时时间
            LogCvt.debug("设置缓存[" + key + "],value["+value+"]超时时间为" + cacheTimeout + "秒,成功!");
        }
    }
	
	/**
	 * 获取特惠商品的商户id信息
	 * @param key
	 * @param start
	 * @param end
	 * @throws Exception
	 * @return Set<String>
	 */
	public static Set<String> get_cbbank_product_merchant_h5_redis(String key,long start,long end) throws Exception {
        Set<String> sets = redisManager.zRange(key, start, end);
        LogCvt.debug("Redis缓存使用zrange分页查询H5特惠商品所在的商户门店key=[" + key + "]的数据, start=[" + start + "], end=[" + end + "], 共返回["+sets.size()+"]条数据!");
        return sets;
    }
	
	/**
	 * 设置特惠商品列表的商户门店信息
	 * @param key
	 * @param outletDetailSimpleVos
	 * @param time
	 * @throws Exception
	 * @return boolean
	 */
	public static boolean set_cbbank_product_merchant_outlet_h5_redis(String key, List<OutletDetailSimpleVo> outletDetailSimpleVos,int time) throws Exception {
        int cacheTimeout = time * 60;// 设置超时时间 为 time分钟
        LogCvt.debug("设置个人H5特惠商品列表搜索商户门店的信息缓存[" + key+":merchantid" + "]开始!");
        for (int i = 0; i < outletDetailSimpleVos.size(); i++) {
            set_cbbank_merchant_outlet_h5_redis(key+":"+outletDetailSimpleVos.get(i).getMerchantId(), outletDetailSimpleVos.get(i), cacheTimeout);
        }
        LogCvt.debug("设置个人H5特惠商品列表搜索商户门店的信息缓存[" + key+":merchantid" + "]成功,缓存数据条数[" + outletDetailSimpleVos.size() + "]条!");
        return true;
    }
	
	/**
	 * 
	 * set_cbbank_merchant_outlet_h5_redis:(设置特惠商品列表的商户门店信息).
	 *
	 * @author wangyan
	 * 2015-8-7 下午6:25:26
	 * @param key
	 * @param outletDetailSimpleVo
	 * @param cacheTimeout
	 * @return
	 * @throws Exception
	 *
	 */
	public static boolean set_cbbank_merchant_outlet_h5_redis(String key, OutletDetailSimpleVo outletDetailSimpleVo,int cacheTimeout) throws Exception {
        String result = "0";
        result = redisManager.putString(key, JSON.toJSONString(formatOutletDetailSimpleVo(outletDetailSimpleVo)));
        if (!"0".equals(result)) {
            redisManager.expire(key, cacheTimeout); // 设置超时时间
            LogCvt.debug("设置个人H5特惠商品列表搜索商户门店的信息缓存[" + key + "],value["+JSON.toJSONString(formatOutletDetailSimpleVo(outletDetailSimpleVo))+"]超时时间为" + cacheTimeout + "秒,成功!");
            return true;
        } else {
        	return false;
        }
            
        
    }
	
	private static OutletDetailSimple formatOutletDetailSimpleVo(OutletDetailSimpleVo odsvo){
	    OutletDetailSimple ods = new OutletDetailSimple();
	    ods.setMerchantId(odsvo.getMerchantId());//商户id
	    ods.setMerchantName(odsvo.getMerchantName());//商户名称
	    ods.setOutletId(odsvo.getOutletId());//门店Id
	    ods.setOutletName(odsvo.getOutletName());//门店名称
	    ods.setAddress(odsvo.getAddress());//地址
	    ods.setDefaultImage(odsvo.getDefaultImage());//门店默认图片(小图)
	    ods.setDis(odsvo.getDis());//计算出的距离
	    ods.setStarLevel(odsvo.getStarLevel());//星评
        return ods;
    }
	
	public static long zinterstore(String dstkey, String key1, String key2) throws Exception {
        Long result = redisManager.zinterstore(dstkey, key1, key2);
        return result;
    }
	
	/**
	 * 
	 * zsetinterstore:(求商户门店的商户ids和商品的商户ids的交集).
	 *
	 * @author wangyan
	 * 2015-8-17 下午3:54:50
	 * @param dstkey
	 * @param groupMerchantKey
	 * @param groupProductkey
	 * @param time
	 * @return 返回交集数
	 * @throws Exception
	 *
	 */
	public static long zsetinterstore(String dstkey, String groupMerchantKey, String groupProductkey,int time) throws Exception {
		int i = 0;
		//满足条件的商品的商户ids
		Map<String,String> productMerchantIdsMap = redisManager.getMap(groupProductkey);
		LogCvt.debug("设置个人H5特惠商品列表商户id交集缓存:"+productMerchantIdsMap);
		if(productMerchantIdsMap!=null && productMerchantIdsMap.size()>0){
			//满足条件的商户门店的商户ids
			Set<String> merchantIds = ProductRedis.get_cbbank_product_merchant_h5_redis(groupMerchantKey, 0, -1);
			if(merchantIds!=null && merchantIds.size()>0){
				for(String merchantId : merchantIds){
					if(productMerchantIdsMap.get(merchantId)!=null){
						zadd(time,i,dstkey,merchantId);
						i++;
					}
				}
			}
		}
		LogCvt.debug("设置个人H5特惠商品列表商户id交集缓存[" + dstkey + "]成功,缓存数据条数[" + i + "]条!");
        return i;
    }
	
	/**
	 * 获取商户门店信息
	 * @param key
	 * @throws Exception
	 * @return OutletDetailSimpleVo
	 */
	public static OutletDetailSimpleVo get_cbbank_product_merchant_outlet_h5_redis(String key) throws Exception {
        String serStr = redisManager.getString(key);
        if(Checker.isNotEmpty(serStr)){
        	LogCvt.debug("Redis缓存查询H5特惠商品所在的商户门店信息key=[" + key + "]的数据!");
            return formatOutletDetailSimple(JSON.parseObject(serStr, OutletDetailSimple.class));
        } else {
        	return null;
        }
    }
	
	private static OutletDetailSimpleVo formatOutletDetailSimple(OutletDetailSimple ods){
	    OutletDetailSimpleVo odsvo = new OutletDetailSimpleVo();
        odsvo.setMerchantId(ods.getMerchantId());//商户id
        odsvo.setMerchantName(ods.getMerchantName());//商户名称
        odsvo.setOutletId(ods.getOutletId());//门店Id
        odsvo.setOutletName(ods.getOutletName());//门店名称
        odsvo.setAddress(ods.getAddress());//地址
        odsvo.setDefaultImage(ods.getDefaultImage());//门店默认图片(小图)
        if(ods.getDis()!=null){
            odsvo.setDis(ods.getDis());//计算出的距离
        }
        odsvo.setStarLevel(ods.getStarLevel());//星评
        return odsvo;
    }
    
	/**
	 * 获取类目营销里特惠商品的zset里最后一条数据所属的商户id
	 * @param key
	 * @return
	 */
	public static String getLastGroupProductMerchartId(String key) {
        Set<String> groupProductStrs = zrevrange(key,0,0);//获取的zset里最后一条数据
        if(groupProductStrs!=null && groupProductStrs.size()>0){
        	for(String groupProductStr : groupProductStrs){
        		ProductDetailSimple pds = JSON.parseObject(groupProductStr, ProductDetailSimple.class);
        		return pds.getMerchantId();
        	}
        }
        return null;
    }
	
	/**
	 * 获取类目营销里特惠商品的zset里第一条数据所属的商户id
	 * @param key
	 * @return
	 */
	public static String getFirstGroupProductMerchartId(String key) {
        Set<String> groupProductStrs = redisManager.zRange(key, 0,0);//获取的zset里第一条数据
        if(groupProductStrs!=null && groupProductStrs.size()>0){
        	for(String groupProductStr : groupProductStrs){
        		ProductDetailSimple pds = JSON.parseObject(groupProductStr, ProductDetailSimple.class);
        		return pds.getMerchantId();
        	}
        }
        return null;
    }
	
	/**
	 * 获取的zset里最后一条数据
	 * @param key
	 * @return
	 */
	public static Set<String> zrevrange(String key,long start,long end) {
        Set<String> strs = redisManager.zrevrange(key, start, end);
        if(strs!=null && strs.size()>0){
        	return strs;
        }
        return null;
    }
	
	/**
	 * 获取类目营销里的特惠商品信息
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public static List<ProductDetailSimple> get_cbbank_group_product_h5_redis(String key,long start,long end) throws Exception {
        Set<String> sets = redisManager.zRange(key, start, end);
        List<ProductDetailSimple> productDetailSimples = new ArrayList<ProductDetailSimple>();
        for(String str : sets){
        	productDetailSimples.add(JSON.parseObject(str, ProductDetailSimple.class));
        }
        LogCvt.debug("Redis缓存使用zrange分页获取类目营销里的特惠商品信息key=[" + key + "]的数据, start=[" + start + "], end=[" + end + "], 共返回["+sets.size()+"]条数据!");
        return productDetailSimples;
    }
    
	/**
	 * 获取类目营销里特惠商品所属的商户id在商户门店缓存zset里的score
	 * @param key
	 * @return
	 */
	public static Double zscore(String key, String member) {
		Double score = redisManager.zscore(key, member);
        return score;
    }
	
	/**
	 * 获取商户门店缓存zset里的50条商户id
	 * @param key
	 * @return
	 */
	public static Set<String> getGroupProductMerchartIds(String key, double min, double max) {
        Set<String> merchartIds = redisManager.zrangeByScore(key, min, max);
        return merchartIds;
    }
	
	/**
	 * 设置商户id的信息，类目营销特惠商品列表用到
	 * @param key
	 * @param lastScore
	 * @param pds
	 * @param time
	 * @return
	 * @throws Exception
	 */
    public static boolean set_cbbank_group_product_h5_redis(String key, double lastScore, List<ProductDetail> pds) throws Exception {
    	LogCvt.debug("设置个人H5类目营销里的特惠商品列表缓存:[" + key + "]");
    	for (int i = 0; i < pds.size(); i++) {
    		//productid已经存过 删除 再加 Double zscore(String key, String member);
    		Double score = redisManager.zscore(key+":productid:score", pds.get(i).getId());
    		if(score!=null){
    			redisManager.zremrangebyscore(key+":productid:score", score, score);
    			redisManager.zremrangebyscore(key, score, score);
    		}
    		//保存productid和score关系标识
    		redisManager.zadd(key+":productid:score", i+1+lastScore, pds.get(i).getId());
    		
    		Long result = redisManager.zadd(key, i+1+lastScore, JSON.toJSONString(formatProductDetailToSimple(pds.get(i))));
            if (result > 0) {
            	LogCvt.debug("value["+JSON.toJSONString(formatProductDetailToSimple(pds.get(i)))+"],成功!");
            }
		}
        LogCvt.debug("设置个人H5类目营销里的特惠商品列表缓存[" + key + "]成功,缓存数据条数[" + pds.size() + "]条!");
        return true;
    }
    
    /**
     * ProductDetail转换成类目营销里面特惠商品需要的字段的缓存对象
     * @param pd
     * @return
     */
    public static ProductDetailSimple formatProductDetailToSimple(ProductDetail pd){
    	ProductDetailSimple pds = new ProductDetailSimple();
    	pds.setProductId(pd.getId());//商品id
    	pds.setClientId(pd.getClientId());
    	pds.setName(pd.getName());//商品名
    	pds.setFullName(pd.getFullName());//商品全名
    	pds.setPrice(Arith.div(pd.getPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));//销售价
    	pds.setMarketPrice(Arith.div(pd.getMarketPrice(), GoodsConstants.DOUBLE_INTGER_CHANGE));//市场价
    	if(pd.getSellCount()!=null){
    		pds.setSellCount(pd.getSellCount());//销售数量
    	}
    	pds.setBriefIntroduction(pd.getBriefIntroduction());//简介
    	if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
    		pds.setSmallImgUrl(pd.getImageInfo().get(0).getThumbnail());//小图片地址
    	}
    	if(pd.getStartTime()!=null){
    		pds.setStartTime(pd.getStartTime().getTime());//销售开始
    	}
    	if(pd.getEndTime()!=null){
    		pds.setEndTime(pd.getEndTime().getTime());//销售结束
    	}
    	pds.setIsSeckill(pd.getIsSeckill());//是否秒杀 0非秒杀,1秒杀,2秒杀未上架
    	pds.setMerchantId(pd.getMerchantId());//商户ID
    	return pds;
    }
	

	/**
	 * 更新商品缓存信息
	 * @Title: auditProduct 
	 * @Description: 
	 * @author: froad-huangyihao 2015年4月27日
	 * @modify: froad-huangyihao 2015年4月27日
	 * @param audit
	 * @return
	 * @throws
	 */
    public static Boolean auditProduct(String clientId,String merchantId,String productId,String isMarketable){
		String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(clientId, merchantId, productId);
		Long result=0l;
		if(Checker.isNotEmpty(isMarketable)){
			result = redisManager.hset(key, "is_marketable", isMarketable);
		}
		
		return result != -1;
	}
    
    /**
     * 缓存中商户门店的商户和商品的商户的交集数量
     * @param groupProductkey
     * @param groupMerchantKey
     * @return
     * @throws Exception
     */
    public static long unionMerchantId(String groupProductkey, String groupMerchantKey) throws Exception {
		int i = 0;
		//满足条件的商品的商户ids
		Map<String,String> productMerchantIdsMap = redisManager.getMap(groupProductkey);
		if(productMerchantIdsMap!=null && productMerchantIdsMap.size()>0){
			//满足条件的商户门店的商户ids
			Set<String> merchantIds = ProductRedis.get_cbbank_product_merchant_h5_redis(groupMerchantKey, 0, -1);
			if(merchantIds!=null && merchantIds.size()>0){
				for(String merchantId : merchantIds){
					if(productMerchantIdsMap.get(merchantId)!=null){
						i++;
					}
				}
			}
		}
        return i;
    }
    
    public static boolean set_cbbank_boutique_product_h5_redis(String key, List<ProductDetail> productDetails, int time) throws Exception {
        String[] serStrArr = new String[productDetails.size()] ;
        for (int i = 0; i < productDetails.size(); i++) {
            serStrArr[i] = JSON.toJSONString(formatBoutiqueProductDetail(productDetails.get(i)));
        }
        Long result = redisManager.rpush(key, serStrArr);
        if (result > 0) {
            int cacheTimeout = time * 60;// 设置超时时间 为 5分钟
            LogCvt.debug("设置个人H5精品商城商品列表缓存[" + key + "]成功,缓存数据条数[" + serStrArr.length + "]条!");
            redisManager.expire(key, cacheTimeout); // 设置超时时间
            LogCvt.debug("设置个人H5精品商城商品列表缓存[" + key + "]超时时间为" + cacheTimeout + "秒,成功!");
        }
        return result > 0;
    }
    
    /**
     * 保存 每个商品id被缓存过的key，以便上架或下架时候 让这些缓存过的key失效
     * @param clientId
     * @param lockKey
     * @param key
     * @param productDetails
     * @param time
     * @throws Exception
     */
    public static void set_cbbank_boutique_product_h5_productId_keys_redis(String clientId, String lockKey, String key, List<ProductDetail> productDetails, int time) throws Exception {
    	try{
    		if(productDetails!=null && productDetails.size()>0){
    			if (Checker.isNotEmpty(clientId)) {
    				String ckey = RedisKeyUtil.cbbank_product_boutique_h5_keys_client_id(clientId);
        			redisManager.rpush(ckey, key);
                	redisManager.rpush(ckey, lockKey);
                	redisManager.expire(ckey, time * 60); // 设置超时时间
                }
                String pikey = null;
                for (int i = 0; i < productDetails.size(); i++) {
                	pikey = RedisKeyUtil.cbbank_product_boutique_h5_keys_product_id(productDetails.get(i).getId());
                	redisManager.rpush(pikey, key);
                	redisManager.rpush(pikey, lockKey);
                	redisManager.expire(pikey, time * 60); // 设置超时时间
                }
    		}
        } catch (Exception e) {
            LogCvt.error("保存 每个商品id被缓存过的key，以便上架或下架时候 让这些缓存过的key失效异常：" +e,e);
        }
    }
    
    private static SimpleProductDetail formatBoutiqueProductDetail(ProductDetail pd){
        SimpleProductDetail spd = new SimpleProductDetail();
        spd.setId(pd.getId());
        spd.setName(pd.getName());
        spd.setFullName(pd.getFullName());  //商品全名
        spd.setPrice(pd.getPrice());  // 价格
        spd.setVipPrice(pd.getVipPrice());//VIP价格
        spd.setSellCount(pd.getSellCount());
        spd.setBriefIntroduction(pd.getBriefIntroduction()); // 简介
        if(pd.getImageInfo()!=null && pd.getImageInfo().size()>0){
            ProductImage imagepo = pd.getImageInfo().get(0);
            spd.setSmallImgUrl(imagepo.getThumbnail());
            spd.setMidImgUrl(imagepo.getMedium());
        }
        spd.setRackTime(pd.getRackTime());//上架时间
        spd.setIsRecommend(pd.getIsRecommend());
        spd.setIsHot(pd.getIsHot());
        spd.setIsNew(pd.getIsNew());
        spd.setIsSeckill(pd.getIsSeckill());
        return spd;
    }
    
    private static ProductDetail formatSimplleBoutiqueProductDetail(SimpleProductDetail spd){
        ProductDetail pd = new ProductDetail();
        pd.setId(spd.getId());
        pd.setName(spd.getName());
        pd.setFullName(spd.getFullName());  //商品全名
        pd.setPrice(spd.getPrice());// 价格
        pd.setVipPrice(spd.getVipPrice());//VIP价格
        pd.setSellCount(spd.getSellCount());
        pd.setBriefIntroduction(spd.getBriefIntroduction()); // 简介
        pd.setRackTime(spd.getRackTime());//上架时间
        List<ProductImage> imageInfo = new ArrayList<ProductImage>();
        ProductImage imagepo = new ProductImage();
        if(spd.getSmallImgUrl()!=null && !"".equals(spd.getSmallImgUrl())){
            imagepo.setThumbnail(spd.getSmallImgUrl());
        }
        if(spd.getMidImgUrl()!=null && !"".equals(spd.getMidImgUrl())){
            imagepo.setMedium(spd.getMidImgUrl());
        }
        imageInfo.add(imagepo);
        pd.setImageInfo(imageInfo);
        pd.setIsRecommend(spd.getIsRecommend());
        pd.setIsHot(spd.getIsHot());
        pd.setIsNew(spd.getIsNew());
        pd.setIsSeckill(spd.getIsSeckill());
        return pd;
    }
    
    public static List<ProductDetail> get_cbbank_boutique_product_h5_redis(String key,long start,long end) throws Exception {
        List<ProductDetail> result = null;
        List<String> list = redisManager.lrange(key, start, end);
        LogCvt.debug("Redis缓存使用lrange分页查询H5精品商城商品列表key=[" + key + "]的数据, start=[" + start + "], end=[" + end + "], 共返回["+list.size()+"]条数据!");
        if (CollectionUtils.isNotEmpty(list)) {
            result = new ArrayList<ProductDetail>();
            for (String serStr : list) {
                result.add(formatSimplleBoutiqueProductDetail(JSON.parseObject(serStr, SimpleProductDetail.class)));
            }
        }
        return result;
    }
}
